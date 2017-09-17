package com.vain.manager.common.dao;


import com.vain.manager.common.entity.Entity;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description: dao的抽象类
 * @author  vain
 * @date 2017/8/31 11:39
 */
public abstract class AbstractBaseDao<T extends Entity> implements BaseDao<T> {

	protected Logger logger = LoggerFactory.getLogger(getClass());
  
	/**
	 * 数据库操作对象
	 */
	@Autowired
	protected SqlSession sqlSession;

}
