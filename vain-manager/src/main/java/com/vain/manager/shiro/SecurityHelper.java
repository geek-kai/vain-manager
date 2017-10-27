package com.vain.manager.shiro;

import com.vain.manager.shiro.authenticator.AccountSubject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

/**
 * @author vain
 * @date： 2017/10/27 16:51
 * @description： session获取attribute的key
 */
public class SecurityHelper {

    public static final String USER_KEY = "_Security_USER_KEY";

    public static final String USER_LIST_KEY = "_Security_USER_LIST_KEY";

    public static final String USER_RESOURCES_KEY = "_USER_RESOURCES_KEY";

    public static final String USER_PERMISSION_KEY = "_USER_PERMISSION_KEY";

    public static final String USER_ROLE_KEY = "_USER_ROLE_KEY";

    public static final String USER_SUBJECTINFO = "_USER_SUBJECTINFO";

    public static final String SERVER_SESSIONID_KEY = "_SERVER_SESSIONID_KEY";

    public static final String ACCOUNT_SUFFIX = "_ACCOUNT:";

    public static final String ORG_SYS_TIME = "ORG_SYS_TIME:";

    public static final String ORG_SYS = "ORG_SYS:";

    public static final String AuthenticationInfo_KEY = "_AuthenticationInfo_KEY";

    public static String getSessionId() {
        return SecurityUtils.getSubject().getSession().getId().toString();
    }

    public static AccountSubject getAccountSubject() {
        Session session = SecurityUtils.getSubject().getSession();
        if (session != null) {
            Object userObject = session.getAttribute(USER_KEY);
            return userObject == null ? null : (AccountSubject) userObject;
        }
        return null;
    }

}
