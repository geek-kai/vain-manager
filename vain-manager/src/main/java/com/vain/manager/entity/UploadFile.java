package com.vain.manager.entity;

import com.vain.manager.common.entity.PagedEntity;

import java.sql.Timestamp;

/**
 * @author vain
 * @date 2017/11/7 21:23
 * @description
 */
public class UploadFile extends PagedEntity{
    /**
     * 生成UUID
     */
    private String UUID;

    /**
     * 源名
     */
    private String name;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 文件长度
     */
    private Long length;

    /**
     * 文件上传用户
     */
    private Long userId;

    /**
     * 上传访问路径
     */
    private String url;

    /**
     * 服务器地址
     */
    private String path;


    private Timestamp createTime;

    private Timestamp modifyTime;

    /**
     * 文件类型 1：photo 2：audio 3：video
     */
    public static final int TYPE_PHOTO = 1;

    public static final int TYPE_AUDIO = 2;

    public static final int TYPE_VIDEO = 3;

    public UploadFile() {
    }

    public UploadFile(String UUID, String name, Integer type, Long length, Long userId, String url, String path) {
        this.UUID = UUID;
        this.name = name;
        this.type = type;
        this.length = length;
        this.userId = userId;
        this.url = url;
        this.path = path;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
}
