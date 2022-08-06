package com.ldf.demo.controller;

import com.ldf.demo.pojo.User;
import com.ldf.demo.service.impl.UserServiceImpl;
import com.ldf.demo.utils.MD5Utils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


@Controller
public class RegisterController {
    @Autowired
    UserServiceImpl userService;

    @GetMapping("/toRegister")
    @ApiOperation(value = "转向注册页面")
    public String toRegister(){
        return "register";
    }


    @PostMapping("/register")
    @ApiOperation(value = "用户注册")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           HttpSession session,
                           RedirectAttributes attributes){

        User user = new User();
        user.setUsername(username);
        user.setPassword(MD5Utils.code(password));

        System.out.println("需要注册的用户:" + user);

        if(userService.addUser(user) == true){
            System.out.println("注册成功");
            return "redirect:/admin";
        }
        else {
            return "redirect:/toRegister";
        }
    }
}
