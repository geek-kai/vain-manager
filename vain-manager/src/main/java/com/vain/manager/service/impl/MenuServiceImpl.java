package com.vain.manager.service.impl;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.vain.manager.common.exception.ErrorRCodeException;
import com.vain.manager.common.service.AbstractBaseService;
import com.vain.manager.constant.SysConstants;
import com.vain.manager.dao.MenuDao;
import com.vain.manager.entity.Menu;
import com.vain.manager.entity.User;
import com.vain.manager.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

/**
 * Created by vain on 2017/9/23.
 */
@Service
public class MenuServiceImpl extends AbstractBaseService implements IMenuService {

    @Autowired
    private MenuDao menuDao;

    @Override
    public List<Menu> getUserMenuByUser(User user) throws ErrorRCodeException {
        return null;
    }

    @Override
    public HashSet<Menu> getMenusByUserId(Long userId, Integer userType) {
        HashSet<Menu> userOwnedMenus = new HashSet<>();
        Menu menu = new Menu(userId);
        if (userType == SysConstants.AccountConstant.ACCOUNT_TYPE_USER) {
            List<Menu> menusByUserRoles = menuDao.getMenusByUserRoles(menu);
            if (menusByUserRoles.size() > 0)
                userOwnedMenus.addAll(menusByUserRoles);
            List<Menu> userAllMenus = menuDao.getUserAllMenus(menu);
            if (userAllMenus.size() > 0)
                userOwnedMenus.addAll(userAllMenus);
        } else {
            List<Menu> menusByUserRoles = menuDao.getMenusByUserRoles(menu);
            if (menusByUserRoles.size() > 0)
                userOwnedMenus.addAll(menusByUserRoles);
        }
        return userOwnedMenus;
    }

    @Override
    public PageList<Menu> getPagedList(Menu entity) throws ErrorRCodeException {
        return null;
    }

    @Override
    public List<Menu> getList(Menu entity) throws ErrorRCodeException {
        return null;
    }

    @Override
    public Menu get(Menu entity) throws ErrorRCodeException {
        return null;
    }

    @Override
    public void add(Menu entity) throws ErrorRCodeException {

    }

    @Override
    public void modify(Menu entity) throws ErrorRCodeException {

    }

    @Override
    public void delete(Menu entity) throws ErrorRCodeException {

    }
}
