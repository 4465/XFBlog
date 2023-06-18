package com.ldf.demo.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author: 清峰
 * @date: 2020/9/24 16:03
 * @code: 愿世间永无Bug!
 * @description:
 */

@Controller
@Api(tags = "建站者信息模块")
public class AboutShowController {

    @GetMapping("/about")
    public String about(){
        return "about";
    }
}
