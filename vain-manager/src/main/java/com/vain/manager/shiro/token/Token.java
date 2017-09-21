package com.vain.manager.shiro.token;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * Created by vain on 2017/9/21.
 * 身份信息 继承shiro的token
 * <p>
 * AuthenticationToken下的子类
 *
 * @see org.apache.shiro.authc.UsernamePasswordToken
 * @see org.apache.shiro.authc.HostAuthenticationToken
 * @see org.apache.shiro.authc.RememberMeAuthenticationToken
 * <p>
 * usernamePasswordToken使用起来不方便
 * 所以封装自己的token
 */
public interface Token extends AuthenticationToken {
}
