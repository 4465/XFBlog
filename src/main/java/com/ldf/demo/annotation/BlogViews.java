package com.ldf.demo.annotation;

import java.lang.annotation.*;

/**
 * @author 刘威甫
 * @date 2022/9/27 13:42
 * @description
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface BlogViews {
    String type() default "";
}
