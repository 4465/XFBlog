package com.ldf.demo.mapper;

import com.ldf.demo.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: 清峰
 * @date: 2020/9/22 13:46
 * @code: 愿世间永无Bug!
 * @description:
 */
@Repository
public interface UserMapper {

    User checkUsernameAndPassword(@Param("username") String username,@Param("password") String password);

    User getUserByUsername(String username);

    Boolean register(User user);

    Boolean addUser(User user);

    List<User> listUser();

}
