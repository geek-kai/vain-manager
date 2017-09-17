package com.vain.manager.entity;

import com.vain.manager.common.entity.PagedEntity;

import java.sql.Timestamp;
/**
 * @description: 系统组件配置实体
 * @author  vain
 * @date 2017/8/31 11:57
 */
public class SystemConfig extends PagedEntity {

    /**
     * 配置项名字
     */
    private String name;

    /**
     * 配置项key
     */
    private String code;

    /**
     * 配置项值，如果值类型是String[]，使用英文逗号隔开
     */
    private String value;

    /**
     * 配置项值类型 1：String 2：INT 3：String[]
     */
    private Integer valueType;

    /**
     * 配置项类型 1：系统基础配置 2：鉴权配置 3：短信配置 4：推送配置 5：三方系统配置 6：爬虫配置
     */
    private Integer type;

    /**
     * 配置项描述
     */
    private String description;

    /**
     * 配置项是否可见
     */
    private Integer visible;

    /**
     * 配置项创建时间
     */
    private Timestamp createTime;

    /**
     * 配置项最后一次修改时间
     */
    private Timestamp modifyTime;

    public SystemConfig(){

    }

    public SystemConfig(Long id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getValueType() {
        return valueType;
    }

    public void setValueType(Integer valueType) {
        this.valueType = valueType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getVisible() {
        return visible;
    }

    public void setVisible(Integer visible) {
        this.visible = visible;
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
