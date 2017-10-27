package com.vain.manager.model;

import com.vain.manager.common.entity.PagedEntity;

import java.sql.Timestamp;

/**
 * @author vain
 * @date： 2017/10/27 15:59
 * @description： 在线用户信息
 */
public class OnLineUser extends PagedEntity {

    private int userType;

    private String userName;

    private String nickName;

    private Timestamp lastAccessTime;

    private String loginIP;

    private Timestamp startTime;

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
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

    public Timestamp getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Timestamp lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public String getLoginIP() {
        return loginIP;
    }

    public void setLoginIP(String loginIP) {
        this.loginIP = loginIP;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }
}
