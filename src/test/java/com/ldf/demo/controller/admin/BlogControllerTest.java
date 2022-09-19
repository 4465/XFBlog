package com.ldf.demo.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ldf.demo.queryVo.BlogQuery;
import com.ldf.demo.service.BlogService;
import com.ldf.demo.service.TypeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author 刘威甫
 * @date 2022/9/19 16:22
 * @description
 */
@SpringBootTest
class BlogControllerTest {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Test
    void listBlogs() {
        PageHelper.startPage(1,1);
        List<BlogQuery> blogs = blogService.listBlogs();
        PageInfo<BlogQuery> pageInfo = new PageInfo<>(blogs);
        System.out.println(pageInfo.toString());
        System.out.println(pageInfo.getTotal());
    }
}