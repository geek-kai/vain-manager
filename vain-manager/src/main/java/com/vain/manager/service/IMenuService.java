package com.vain.manager.service;

import com.vain.manager.common.exception.ErrorRCodeException;
import com.vain.manager.common.service.BaseService;
import com.vain.manager.entity.Menu;
import com.vain.manager.entity.User;

import java.util.HashSet;
import java.util.List;

/**
 * Created by vain on 2017/9/23.
 * 菜单
 */
public interface IMenuService extends BaseService<Menu> {


    List<Menu> getUserMenuByUser(User user) throws ErrorRCodeException;

    HashSet<Menu> getMenusByUserId(Long userId, Integer userType);


}
