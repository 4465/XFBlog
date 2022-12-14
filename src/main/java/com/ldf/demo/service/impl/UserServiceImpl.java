package com.ldf.demo.service.impl;

import com.ldf.demo.mapper.UserMapper;
import com.ldf.demo.pojo.User;
import com.ldf.demo.service.UserService;
import com.ldf.demo.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 清峰
 * @date: 2020/9/22 13:40
 * @code: 愿世间永无Bug!
 * @description:
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User checkUsernameAndPassword(String username, String password) {
        return userMapper.checkUsernameAndPassword(username, password);
    }

    @Override
    public Boolean register(User user) {
        return userMapper.register(user);
    }

    @Override
    public Boolean addUser(User user) {
        if(userMapper.addUser(user)){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<User> listUser() {
        List<User> userList = new ArrayList<>();
        userList = userMapper.listUser();
        return userList;
    }
}
