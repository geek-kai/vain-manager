package com.vain.manager.common.entity;

/**
 * @description: 所有数据对象的超类
 * @author  vain
 * @date 2017/8/31 11:41
 */
public class Entity implements IEntity {

    /**
     * 实体记录的id
     */
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
