package com.vain.manager.common.dao;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.vain.manager.common.entity.Entity;

import java.util.List;

/**
 * @description: dao的公共接口
 * @author  vain
 * @date 2017/8/31 11:40
 */
public interface BaseDao<T extends Entity> {

	/**
	 * 获取分页数据
	 * 
	 * @param entity
	 *            参数实体
	 * @param curPage
	 *            当前页码
	 * @param pageSize
	 *            每页大小
	 * @return
	 */
	 PageList<T> getPagedList(T entity, int curPage, int pageSize);

	/**
	 * 获取所有数据
	 * 
	 * @param entity
	 *            参数实体
	 * @return
	 */
	 List<T> getList(T entity);

	/**
	 * 获取单条数据
	 * 
	 * @param entity
	 *            参数实体
	 * @return
	 */
	 T get(T entity);

	/**
	 * 插入数据
	 * 
	 * @param entity
	 *            参数实体
	 * @return
	 */
	 int insert(T entity);

	/**
	 * 更新数据
	 * 
	 * @param entity
	 *            参数实体
	 * @return
	 */
	 int update(T entity);

	/**
	 * 删除数据
	 * 
	 * @param entity
	 *            参数实体
	 * @return
	 */
	 int delete(T entity);
}
