package com.vain.manager.shiro.token;

/**
 * Created by vain on 2017/9/21.
 * 自己token信息 保留自己的属性
 */
public class AccountToken implements Token {
    /**
     * 登录账号
     */
    private String userName;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 用户类型
     */
    private int type;

    /**
     * 是否记住
     */
    private boolean rememberMe;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public AccountToken(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }
}
