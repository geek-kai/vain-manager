package com.vain.manager.shiro.realm;

import com.vain.manager.shiro.SecurityHelper;
import com.vain.manager.shiro.authenticator.DefaultAccountSubject;
import com.vain.manager.shiro.authenticator.SubjectInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by vain on 2017/9/19.
 * 实现自己的shiro认证登录方式
 */
public class DefaultAuthorizingRealm extends AuthorizingRealm {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    List<AuthenticateRealm> authenticateRealms;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info("------------------获取授权信息---------------------");
        if (principals == null)
            throw new AuthenticationException("PrincipalCollection method argument cannot be null.");
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        logger.info("------------------获取认证信息---------------------");
        SubjectInfo subjectInfo = authenticate(token);
        if (subjectInfo == null)
            throw new AuthenticationException("SubjectInfo is null");
        AuthenticationInfo info = new SimpleAuthenticationInfo(subjectInfo.getIdentification(), token.getCredentials(), getName());
        DefaultAccountSubject accountSubject = new DefaultAccountSubject();
        accountSubject.setSubjectInfo(subjectInfo);
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        session.setAttribute(SecurityHelper.USER_KEY, accountSubject);

        // 保存用户认证信息
        this.getCacheManager().getCache(SecurityHelper.USER_SUBJECTINFO).put(subjectInfo.getIdentification(), subjectInfo);
        this.getCacheManager().getCache(SecurityHelper.AuthenticationInfo_KEY).put(subjectInfo.getUserId(), info.getPrincipals());

        Object key = this.getCacheManager().getCache(SecurityHelper.AuthenticationInfo_KEY).get(subjectInfo.getUserId());
        if (null != this.getAuthorizationCache().get(key)) {
            this.getAuthorizationCache().remove(key);
        }
        return info;
    }

    protected SubjectInfo authenticate(AuthenticationToken token) throws AuthenticationException {
        SubjectInfo subjectInfo = null;
        if (authenticateRealms == null) {
            logger.error("------------------realm为空请检查配置---------------------");
            throw new AuthenticationException();
        }
        //依次验证配置的realm
        for (AuthenticateRealm authenticateRealm : authenticateRealms) {
            if (authenticateRealm.accept(token)) {
                subjectInfo = authenticateRealm.login(token);
                break;
            }
        }
        return subjectInfo;
    }

    public void setAuthenticateRealms(List<AuthenticateRealm> authenticateRealms) {
        this.authenticateRealms = authenticateRealms;
    }
}
