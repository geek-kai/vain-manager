package com.vain.manager.common.service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.vain.manager.common.entity.Entity;
import com.vain.manager.common.exception.ErrorCodeException;


import java.util.List;

/**
 * @author vain
 * @description: service的公共接口
 * @date 2017/8/31 11:49
 */
public interface BaseService<T extends Entity> {
    /**
     * 获取分页数据
     *
     * @param entity 参数实体
     * @return
     */
    PageList<T> getPagedList(T entity) throws ErrorCodeException;

    /**
     * 获取所有数据
     *
     * @param entity 参数实体
     * @return
     */
    List<T> getList(T entity) throws ErrorCodeException;

    /**
     * 获取单条数据详情
     *
     * @param entity 参数实体
     * @return
     */
    T get(T entity) throws ErrorCodeException;

    /**
     * 新增数据
     *
     * @param entity 参数实体
     * @return
     */
    int add(T entity) throws ErrorCodeException;

    /**
     * 修改数据
     *
     * @param entity 参数实体
     * @return
     */
    int modify(T entity) throws ErrorCodeException;

    /**
     * 删除数据
     *
     * @param entity 参数实体
     * @return
     */
    int delete(T entity) throws ErrorCodeException;
}
