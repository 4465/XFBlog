package com.ldf.demo.controller.admin;

import com.ldf.demo.mapper.UserMapper;
import com.ldf.demo.pojo.User;
import com.ldf.demo.service.UserService;
import com.ldf.demo.service.impl.UserServiceImpl;
import com.ldf.demo.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
//@RequestMapping("/admin")
@Api(tags = "用户相关接口")
public class UserController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserServiceImpl userService;

    @GetMapping("/addUser")
    @ApiOperation("添加用户")
    public String addUser(@RequestParam String username,
                          @RequestParam String password){

//        System.out.println("输入的密码为：" + password);

        User user = new User();
        user.setUsername(username);
        user.setPassword(MD5Utils.code(password));
//        System.out.println("用户信息" + user);

        userService.addUser(user);


        return "";
    }

}
