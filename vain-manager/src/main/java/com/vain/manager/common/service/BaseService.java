package com.vain.manager.common.service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.vain.manager.common.entity.Entity;
import com.vain.manager.common.exception.ErrorRCodeException;


import java.util.List;
/**
 * @description:  service的公共接口
 * @author  vain
 * @date 2017/8/31 11:49
 */
public interface BaseService<T extends Entity> {
    /**
     * 获取分页数据
     * 
     * @param entity
     *            参数实体
     * @return
     */
     PageList<T> getPagedList(T entity) throws ErrorRCodeException;

    /**
     * 获取所有数据
     * 
     * @param entity
     *            参数实体
     * @return
     */
     List<T> getList(T entity) throws ErrorRCodeException;

    /**
     * 获取单条数据详情
     * 
     * @param entity
     *            参数实体
     * @return
     */
     T get(T entity) throws ErrorRCodeException;

    /**
     * 新增数据
     * 
     * @param entity
     *            参数实体
     * @return
     */
     void add(T entity) throws ErrorRCodeException;

    /**
     * 修改数据
     * 
     * @param entity
     *            参数实体
     * @return
     */
    void modify(T entity) throws ErrorRCodeException;

    /**
     * 删除数据
     * 
     * @param entity
     *            参数实体
     * @return
     */
     void delete(T entity) throws ErrorRCodeException;
}
