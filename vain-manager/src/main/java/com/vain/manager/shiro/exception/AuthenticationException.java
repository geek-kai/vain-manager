package com.vain.manager.shiro.exception;


/**
 * Created by vain on 2017/9/19.
 * 继承shiro的异常 在执行自己登录逻辑时 主动抛出的异常
 */
public class AuthenticationException extends org.apache.shiro.authc.AuthenticationException {

    /**
     * 响应码
     */
    private String msgCode;

    public AuthenticationException() {
        super();
    }

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String msgCode, String message) {
        super(message);
        this.msgCode = msgCode;
    }

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }
}
