package com.vain.manager.shiro.session;

import com.vain.manager.shiro.SecurityHelper;
import com.vain.manager.shiro.authenticator.AccountSubject;
import com.vain.manager.shiro.authenticator.SubjectInfo;
import com.vain.manager.shiro.authenticator.UserSubjectInfo;

/**
 * Created by vain on 2017/9/23.
 * shiro中的用户Session
 */
public class UserSession {

    /**
     * 获取UserSubjectInfo
     *
     * @return
     */
    private static UserSubjectInfo getUserSubjectInfo() {
        AccountSubject accountSubject = SecurityHelper.getAccountSubject();
        if (accountSubject != null) {
            SubjectInfo subjectInfo = accountSubject.getSubjectInfo();
            if (subjectInfo != null) {
                UserSubjectInfo userSubjectInfo = (UserSubjectInfo) subjectInfo;
                return userSubjectInfo;
            }
        }
        return null;
    }

    /**
     * 获取用户Id
     *
     * @return
     */
    public static long getUserId() {
        UserSubjectInfo info = getUserSubjectInfo();
        if (info != null)
            return info.getUserId();
        return 0;
    }

    /**
     * 获取用户昵称
     *
     * @return
     */
    public static String getNickName() {
        UserSubjectInfo info = getUserSubjectInfo();
        if (info != null)
            return info.getNickName();
        return null;
    }

    /**
     * 获取用户名
     *
     * @return
     */
    public static String getUserName() {
        UserSubjectInfo info = getUserSubjectInfo();
        if (info != null)
            return info.getUserName();
        return null;
    }

    /**
     * 获取用户类型
     *
     * @return
     */
    public static int getUserType() {
        UserSubjectInfo info = getUserSubjectInfo();
        if (info != null)
            return info.getUserType();
        return 0;
    }


}
