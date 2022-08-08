package com.ldf.demo.controller.admin;

import com.ldf.demo.pojo.User;
import com.ldf.demo.service.UserService;
import com.ldf.demo.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author: 清峰
 * @date: 2020/9/22 13:31
 * @code: 愿世间永无Bug!
 * @description: 管理员登录界面
 */
@Controller
@RequestMapping(value = "/admin")
@Api(tags = "登录相关接口")
public class LoginController {
    @Autowired
    private UserService userService;

    //跳转到登录页面
    @GetMapping
    @ApiOperation(value = "跳转到登录页面")
    public String loginPage(){
        return "admin/login";
    }
    //跳转到首页
    @GetMapping("/index")
    @ApiOperation(value = "跳转到首页")
    public String index(){
        return "admin/index";
    }

    //登录检验
    @PostMapping("/login")
    @ApiOperation(value = "登录")
    public String login(@RequestParam String username,
                        @RequestParam String password) {
        System.out.println("前端获取的数据===同户名：" + username + "密码：" + password);

        //获取当前的用户
        Subject subject = SecurityUtils.getSubject();

        //封装用户登录数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, MD5Utils.code(password));

        System.out.println("token:" + token);
        
        //执行登录的方法，如果没有异常说明ok
        try{
            subject.login(token);
        }catch (UnknownAccountException e){
            System.out.println("用户名错误");
            return "redirect:/admin";
        }catch (IncorrectCredentialsException e){
            System.out.println("密码错误");
            return "redirect:/admin";
        }
        return "redirect:/";
    }

    //注销功能
    @GetMapping("/logout")
    @ApiOperation("注销")
    public String logout(HttpSession session){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/";
    }

    @GetMapping("/toRegister")
    @ApiOperation(value = "转向注册页面")
    public String toRegister(){
        return "admin/register";
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

        if(userService.addUser(user) == true){
            System.out.println("注册成功");
            return "admin/index";
        }
        else {
            return "redirect:/admin";
        }
    }

}
