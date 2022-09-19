package com.ldf.demo.config;


import com.ldf.demo.interceptor.PageLocalWebInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 刘威甫
 * @date 2022/9/19 17:30
 * @description
 */
public class FrameworkMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PageLocalWebInterceptor());
    }
}
