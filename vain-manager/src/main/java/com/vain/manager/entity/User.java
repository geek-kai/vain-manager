package com.vain.manager.entity;

import com.vain.manager.common.entity.PagedEntity;

import java.sql.Timestamp;

/**
 * @description: 用户信息实体类
 * @author  vain
 * @date 2017/8/31 11:57
 */
public class User extends PagedEntity {

    /**
     * 用户类型 1-管理员2-普通用户
     */
    private int type;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String passwd;
    /**
     * 密码盐值
     */
    private String salt;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 电话
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 头像url
     */
    private String head;
    /**
     * 性别1-男2-女
     */
    private int sex;
    /**
     * 生日
     */
    private Timestamp birthday;

    /**
     * 是否锁定0-正常1-锁定
     */
    private int state;
    /**
     * 扩展字段1
     */
    private String attr1;
    /**
     * 扩展字段2
     */
    private String attr2;
    /**
     * 扩展字段3
     */
    private String attr3;
    /**
     * 扩展字段4
     */
    private String attr4;
    /**
     * 扩展字段5
     */
    private String attr5;
    /**
     * 删除标识位0-正常1-已删除
     */
    private int deleted;
    /**
     * 创建时间
     */
    private Timestamp createTime;
    /**
     * 最后修改时间
     */
    private Timestamp modifyTime;

    /**
     * token, 后续的接口请求需要携带作为令牌
     */
    private String token;

    /**
     * refreshToken，用于刷新token，延迟有效期
     */
    private String refreshToken;

    /**
     * 新密码
     */
    private String newpasswd;

    /**
     * 用户的角色
     */
    private String roleName;

    public User() {

    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public User(Long id) {
        this.id = id;
    }

    public User(String phone) {
        this.phone = phone;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Timestamp getBirthday() {
        return birthday;
    }

    public void setBirthday(Timestamp birthday) {
        this.birthday = birthday;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getAttr1() {
        return attr1;
    }

    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }

    public String getAttr2() {
        return attr2;
    }

    public void setAttr2(String attr2) {
        this.attr2 = attr2;
    }

    public String getAttr3() {
        return attr3;
    }

    public void setAttr3(String attr3) {
        this.attr3 = attr3;
    }

    public String getAttr4() {
        return attr4;
    }

    public void setAttr4(String attr4) {
        this.attr4 = attr4;
    }

    public String getAttr5() {
        return attr5;
    }

    public void setAttr5(String attr5) {
        this.attr5 = attr5;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getNewpasswd() {
        return newpasswd;
    }

    public void setNewpasswd(String newpasswd) {
        this.newpasswd = newpasswd;
    }

    /**
     * 清楚敏感字段
     */
    public void clearSecretField() {
        this.passwd = null;
        this.salt = null;
    }
}
