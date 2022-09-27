package com.ldf.demo.aspect;

import com.ldf.demo.service.BlogService;
import com.ldf.demo.utils.RedisUtils;
import io.swagger.models.auth.In;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 刘威甫
 * @date 2022/9/27 13:29
 * @description
 */
@Component
@Aspect
public class ViewsAndCommentsAspect {

    //获取日志类对象
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private BlogService blogService;

    //定义切面方法 -- 访问量
    @Pointcut("@annotation(com.ldf.demo.annotation.BlogViews)")
    public void views(){ }

    //定义切面方法 -- 评论数
    @Pointcut("@annotation(com.ldf.demo.annotation.BlogComments)")
    public void comments(){ }

    //定义前置通知
    @Before("views()")
    public void doBefore(JoinPoint jp){

    }

    //定义后置通知
    @After("views()")
    public void doAfter(JoinPoint jp){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();

        Object[] args = jp.getArgs();
        String host = request.getRemoteHost();
        String value = "IP: " + host;

        int blogViewTotal;
        logger.info(args.toString());
        /**
         * 总访问量自增
         */
        if(redisUtils.hasKey("blogMessage")){
            Map<Object, Object> map = redisUtils.hmget("blogMessage");
            logger.info(map.toString());
            redisUtils.hincr("blogMessage","blogViewTotal", 1);
        }else {
            blogViewTotal = blogService.getBlogViewTotal();
            redisUtils.hset("blogMessage","blogViewTotal", blogViewTotal + 1);
        }
        logger.info("浏览量加1");
        Long blogId = (Long) args[0];
        /**
         * 分访问量自增
         */
        if(redisUtils.hHasKey("blogViews", blogId.toString())){
            redisUtils.hincr("blogViews", blogId.toString(), 1);
        }
        else {
            int views = blogService.getBlogViewById(blogId);
            System.out.println(blogId.toString() + " views:" + views);
            Map<String,Object> mp = new HashMap<>();
            mp.put(blogId.toString(), views);
            redisUtils.hmset("blogViews", mp);
        }
    }

}