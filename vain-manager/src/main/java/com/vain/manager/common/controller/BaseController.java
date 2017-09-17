package com.vain.manager.common.controller;

import com.vain.manager.common.entity.Entity;
import com.vain.manager.common.entity.Response;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
/**
 * @description: controller的公共接口
 * @author  vain
 * @date 2017/8/31 11:39
 */
public interface BaseController<T extends Entity> {
    /**
     * 获取分页数据
     * 
     * @param entity
     *            参数实体
     * @return
     */
    @ResponseBody Response<T> getPagedList(@RequestBody T entity, HttpServletRequest request) throws Exception;

    /**
     * 获取所有数据
     * 
     * @param entity
     *            参数实体
     * @return
     */
    @ResponseBody Response<T> getList(@RequestBody T entity, HttpServletRequest request) throws Exception;

    /**
     * 获取单条数据详情
     * 
     * @param entity
     *            参数实体
     * @return
     * @throws Exception
     */
     @ResponseBody Response<T> get(@RequestBody T entity, HttpServletRequest request) throws Exception;

    /**
     * 根据id获取单条数据
     * 
     * @param id
     * @return
     */
     @ResponseBody Response<T> getById(@PathVariable long id, HttpServletRequest request) throws Exception;

    /**
     * 新增数据
     * 
     * @param entity
     *            参数实体
     * @return
     */
     @ResponseBody Response<T> add(@RequestBody T entity, HttpServletRequest request) throws Exception;

    /**
     * 修改数据
     * 
     * @param entity
     *            参数实体
     * @return
     */
     @ResponseBody Response<T> modify(@RequestBody T entity, HttpServletRequest request) throws Exception;

    /**
     * 删除数据
     * 
     * @param entity
     *            参数实体
     * @return
     */
     @ResponseBody Response<T> delete(@RequestBody T entity, HttpServletRequest request) throws Exception;
}
