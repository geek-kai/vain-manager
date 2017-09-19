package com.vain.manager.shiro.realm;

import com.vain.manager.shiro.authenticator.SubjectInfo;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * Created by vain on 2017/9/19.
 */
public interface AuthenticateRealm {

    SubjectInfo login(AuthenticationToken token);

    boolean accept(AuthenticationToken token);
}
