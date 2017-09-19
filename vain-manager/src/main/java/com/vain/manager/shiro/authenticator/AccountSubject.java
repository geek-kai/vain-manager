package com.vain.manager.shiro.authenticator;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * Created by vain on 2017/9/19.
 */
public interface AccountSubject {

    void login(AuthenticationToken token);

    void logout();

    SubjectInfo getSubjectInfo();
}
