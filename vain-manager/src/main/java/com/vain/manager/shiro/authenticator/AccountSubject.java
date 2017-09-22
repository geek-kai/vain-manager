package com.vain.manager.shiro.authenticator;

import com.vain.manager.shiro.token.Token;

/**
 * Created by vain on 2017/9/19.
 */
public interface AccountSubject {

    void login(Token token);

    void logout();

    SubjectInfo getSubjectInfo();
}
