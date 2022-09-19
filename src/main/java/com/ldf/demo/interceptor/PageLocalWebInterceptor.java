package com.ldf.demo.interceptor;

import com.github.pagehelper.PageHelper;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 刘威甫
 * @date 2022/9/19 17:34
 * @description
 */
public class PageLocalWebInterceptor implements HandlerInterceptor {
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        PageHelper.clearPage();
    }
}
