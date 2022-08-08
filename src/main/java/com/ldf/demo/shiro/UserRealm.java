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

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

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

        return new SimpleAuthenticationInfo(user, user.getPassword(),"");
    }
}
