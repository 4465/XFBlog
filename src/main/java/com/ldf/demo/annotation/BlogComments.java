package com.ldf.demo.annotation;

import java.lang.annotation.*;

/**
 * @author 刘威甫
 * @date 2022/9/27 16:44
 * @description
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface BlogComments {
    String type() default "";
}