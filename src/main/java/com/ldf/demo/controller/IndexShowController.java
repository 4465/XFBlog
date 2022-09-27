package com.ldf.demo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ldf.demo.annotation.BlogViews;
import com.ldf.demo.pojo.Comment;
import com.ldf.demo.queryVo.*;
import com.ldf.demo.service.BlogService;
import com.ldf.demo.service.CommentService;
//import com.sun.org.apache.xpath.internal.operations.Mod;
import com.ldf.demo.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 清峰
 * @date: 2020/9/24 9:45
 * @code: 愿世间永无Bug!
 * @description:
 */
@Controller
public class IndexShowController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentService commentService ;

    @Autowired
    private RedisUtils redisUtils;


    @GetMapping("/")
    public String index(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum, Model model){
        PageHelper.startPage(pageNum,10);
        //第一页博客展示
        List<FirstPageBlog> firstPageBlogs = blogService.getFirstPageBlogs();
        //最热博客推荐
        List<RecommendBlog> recommendBlogs = blogService.recommendedBlogs();

        PageInfo<FirstPageBlog> pageInfo = new PageInfo<>(firstPageBlogs);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("recommendedBlogs",recommendBlogs);
        return "index";
    }

    //搜索博客
    @PostMapping("/search")
    public String search(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                         @RequestParam(name = "query")String query,Model model){
        PageHelper.startPage(pageNum,1000);
        List<FirstPageBlog> blogs = blogService.getSearchBlogs(query);
        PageInfo<FirstPageBlog> pageInfo = new PageInfo<>(blogs);
        model.addAttribute("pageInfo",pageInfo);
        return "search";
    }


    /**
     * 跳转到博客内容页面
     * 访问量加1
     *
     * @param id
     * @param model
     * @return
     */
    @BlogViews
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model){

        //获取博客的细节信息
        DetailedBlog blog = blogService.getDetailedBlogById(id);

        //获取博客分评论信息
        List<Comment> comments = commentService.listCommentByBlogId(id);

        //总访问量自增
        //redisUtils.hincr("blogMessage", "blogViewTotal", 1);

        model.addAttribute("blog",blog);
        model.addAttribute("comments",comments);
        return "blog";
    }


    //查询底部栏博客信息
    @GetMapping("/footer/blogmessage")
    public String blogMessage(Model model){

        int blogTotal;
        int blogViewTotal;
        int blogCommentTotal;
        int blogMessageTotal;

        if(redisUtils.hasKey("blogMessage")){

            Map<Object, Object> map = redisUtils.hmget("blogMessage");

            System.out.println(map.toString());
            blogTotal = (int) map.get("blogTotal");
            blogViewTotal = (int) map.get("blogViewTotal");
            blogCommentTotal = (int) map.get("blogCommentTotal");
            blogMessageTotal = (int) map.get("blogMessageTotal");


        }
        else {
            blogTotal = blogService.getBlogTotal();
            blogViewTotal = blogService.getBlogViewTotal();
            blogCommentTotal = blogService.getBlogCommentTotal() == null ? 0 : blogService.getBlogCommentTotal();
            blogMessageTotal = blogService.getBlogMessageTotal();

            Map<String, Object> blogMessage = new HashMap<>();
            blogMessage.put("blogTotal", blogTotal);
            blogMessage.put("blogViewTotal", blogViewTotal);
            blogMessage.put("blogCommentTotal", blogCommentTotal);
            blogMessage.put("blogMessageTotal", blogMessageTotal);

            redisUtils.hmset("blogMessage", blogMessage);
        }

        model.addAttribute("blogTotal",blogTotal);
        model.addAttribute("blogViewTotal",blogViewTotal);
        model.addAttribute("blogCommentTotal",blogCommentTotal);
        model.addAttribute("blogMessageTotal",blogMessageTotal);

        return "index :: blogMessage";
    }



}
