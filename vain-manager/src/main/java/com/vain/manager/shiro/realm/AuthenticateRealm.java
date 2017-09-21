package com.vain.manager.shiro.realm;

import com.vain.manager.shiro.authenticator.SubjectInfo;
import com.vain.manager.shiro.token.AccountToken;
import com.vain.manager.shiro.token.Token;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * Created by vain on 2017/9/19.
 */
public interface AuthenticateRealm {

    SubjectInfo login(Token token);

    boolean accept(Token token);
}
