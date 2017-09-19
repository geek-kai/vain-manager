package com.vain.manager.shiro.authenticator;


import com.vain.manager.shiro.Constants;

import java.io.Serializable;

/**
 * 登录用户的信息
 */
public class UserSubjectInfo implements SubjectInfo, Serializable {

    /**
     * 登录名
     */
    private String userName;

    /**
     * 名字
     */
    private String nickName;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * @see com.vain.manager.shiro.Constants 管理员、机构管理员、普通用户
     */
    private int userType = Constants.ORDINARY_USER;


    @Override
    public String getIdentification() {
        return "USER:" + this.userId;
    }

    public String getUserName() {
        return userName;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getNickName() {
        return nickName;
    }


    public void setNickName(String nickName) {
        this.nickName = nickName;
    }


    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

}
