package com.vain.manager.common.controller;


import com.vain.manager.common.entity.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @description:  controller 的抽象类
 * @author  vain
 * @date 2017/8/31 11:38
 */
public abstract class AbstractBaseController<T extends Entity> implements BaseController<T> {
	protected Logger logger = LoggerFactory.getLogger(getClass());

}
