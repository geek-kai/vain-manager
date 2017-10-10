package com.vain.manager.service;

import com.vain.manager.common.exception.ErrorCodeException;
import com.vain.manager.common.service.BaseService;
import com.vain.manager.entity.Menu;
import com.vain.manager.entity.Role;
import com.vain.manager.entity.User;

import java.util.HashSet;
import java.util.List;

/**
 * Created by vain on 2017/9/23.
 * 菜单
 */
public interface IMenuService extends BaseService<Menu> {


    List<Menu> getUserMenuByUser(User user) throws ErrorCodeException;

    HashSet<Menu> getMenusByUserId(Long userId, Integer userType);

    /**
     * 获取所有数据
     *
     * @param entity      参数实体
     * @param isHierarchy 是否返回层级结构 父子
     * @return
     */
    List<Menu> getList(Menu entity, boolean isHierarchy) throws ErrorCodeException;


    List<Menu> getMyMenus(Menu entity) throws ErrorCodeException;

    /**
     * 通过角色id获取角色所有的菜单权限
     *
     * @param entity
     * @return
     */
    List<Menu> getMenusByRoleId(Role entity) throws ErrorCodeException;
}
