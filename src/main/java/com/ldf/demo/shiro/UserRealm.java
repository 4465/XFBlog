package com.ldf.demo.shiro;

import com.ldf.demo.pojo.User;
import com.ldf.demo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    public UserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

/**
        System.out.println("执行了Shiro的认证");

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        User user = userService.checkUsernameAndPassword(token.getUsername(), String.valueOf(token.getPassword()));

        System.out.println("Shiro认证登录查询出的user" + user);

        if(user==null){
            return null;
        }

        Subject currentSubject = SecurityUtils.getSubject();

        Session session = currentSubject.getSession();

        session.setAttribute("user", user);
*/

        String jwtToken_str = (String) authenticationToken.getPrincipal();
        //解密获得username,用于和数据库比对
        String username = null;
        try{
            username = JwtUtils.getUserName(jwtToken_str);
            System.out.println("JwtToken解析出的username:" + username);

            User user = userService.getUserByName(username);

            if(user == null){
                System.out.println("用户不存在");
                return null;
            }
            boolean verify = JwtUtils.verify(jwtToken_str, username, user.getPassword());
            if(!verify){
                System.out.println("Token校验不正确");
                return null;
            }
            System.out.println("Shiro+JWT认证登录查询出的user" + user);
        }catch (Exception e){
            e.printStackTrace();
        }


        return new SimpleAuthenticationInfo(username, jwtToken_str,getName());
    }
}
