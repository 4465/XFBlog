package com.ldf.demo.shiro;

import org.apache.shiro.authc.AuthenticationToken;

public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    //返回原来的字符串，解析交给JWTUtils实现
    @Override
    public Object getPrincipal() {
        return this.token;
    }

    //返回原来的字符串，解析交给JWTUtils实现
    @Override
    public Object getCredentials() {
        return this.token;
    }
}
