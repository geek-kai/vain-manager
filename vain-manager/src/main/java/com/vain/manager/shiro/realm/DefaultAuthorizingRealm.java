package com.vain.manager.shiro.realm;

import com.vain.manager.shiro.Constants;
import com.vain.manager.shiro.PermissionContext;
import com.vain.manager.shiro.SecurityHelper;
import com.vain.manager.shiro.authenticator.AccountSubject;
import com.vain.manager.shiro.authenticator.DefaultAccountSubject;
import com.vain.manager.shiro.authenticator.SubjectInfo;
import com.vain.manager.shiro.service.IAuthorizationService;
import com.vain.manager.shiro.token.Token;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.permission.AllPermission;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

/**
 * Created by vain on 2017/9/19.
 * 实现自己的shiro认证登录方式
 */
public class DefaultAuthorizingRealm extends AuthorizingRealm {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    List<AuthenticateRealm> authenticateRealms;

    IAuthorizationService authorizationServiceImpl;

    /**
     * 构造传入token类否则会爆出 UnSupportAuthrizingRealmException
     *
     * @see AuthorizationException
     */
    public DefaultAuthorizingRealm() {
        super();
        this.setAuthenticationTokenClass(Token.class);
    }

    /**
     * 用户权限分为： 角色权限和菜单权限
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info("------------------获取授权信息---------------------");
        if (principals == null)
            throw new AuthenticationException("PrincipalCollection method argument cannot be null.");
        AccountSubject accountSubject = SecurityHelper.getAccountSubject();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        int userType = accountSubject == null ? -1 : accountSubject.getSubjectInfo().getUserType();
        if (userType == Constants.SUPER_ADMIN) {
            info.addObjectPermission(new AllPermission());
        } else {
            PermissionContext permissionContext = accountSubject == null ? null : authorizationServiceImpl.getPermissions(accountSubject.getSubjectInfo());
            info.setStringPermissions(permissionContext == null ? null : permissionContext.getPermissions());
        }
        if (accountSubject != null)
            info.setRoles(getSubjectRoles(accountSubject.getSubjectInfo()));
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        logger.info("------------------获取认证信息---------------------");
        SubjectInfo subjectInfo = authenticate((Token) token);
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

    private SubjectInfo authenticate(Token token) throws AuthenticationException {
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

    /**
     * 获取角色信息
     *
     * @param accountSubject
     * @return
     */
    private Set<String> getSubjectRoles(SubjectInfo accountSubject) {
        return null;
    }

    public void setAuthenticateRealms(List<AuthenticateRealm> authenticateRealms) {
        this.authenticateRealms = authenticateRealms;
    }
}
