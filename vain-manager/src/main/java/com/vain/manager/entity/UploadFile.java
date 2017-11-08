package com.vain.manager.entity;

import com.vain.manager.common.entity.PagedEntity;
import com.vain.manager.util.DateUtil;
import com.vain.manager.util.StrUtil;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.UUID;

/**
 * @author vain
 * @date 2017/11/7 21:23
 * @description 上传文件实体
 */
public class UploadFile extends PagedEntity {
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

    /**
     * 图片宽度
     */
    private Integer width;

    /**
     * 图片高度
     */
    private Integer height;


    private Timestamp createTime;

    private Timestamp modifyTime;

    /**
     * 文件类型 1：图片 2：音频 3：视频  4：其他
     */
    public static final int TYPE_PHOTO = 1;

    public static final int TYPE_AUDIO = 2;

    public static final int TYPE_VIDEO = 3;

    public static final int TYPE_OTHER = 4;

    public UploadFile() {
    }

    public UploadFile(String UUID, String name, Integer type, Long length, Long userId) {
        this.name = name;
        this.type = type;
        this.length = length;
        this.userId = userId;
        if (StrUtil.isBlank(UUID)) {
            //日期/类别/UUID
            this.UUID = DateUtil.dateToStr(Calendar.getInstance().getTime(), "yyyy-MM-dd") + "/" + type + "/" + java.util.UUID.randomUUID().toString();
            // UUID+后缀名组成新的UUID
        } else {
            this.UUID = UUID + "/" + type + "/" + java.util.UUID.randomUUID().toString();
        }
        if (name != null)
            this.UUID += name.substring(name.lastIndexOf("."));

    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
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
