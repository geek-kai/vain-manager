package com.vain.manager.service.impl;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.vain.manager.common.exception.ErrorCodeException;
import com.vain.manager.common.service.AbstractBaseService;
import com.vain.manager.dao.RoleDao;
import com.vain.manager.dao.RoleMenuDao;
import com.vain.manager.dao.UserRoleDao;
import com.vain.manager.entity.Menu;
import com.vain.manager.entity.Role;
import com.vain.manager.entity.RoleMenu;
import com.vain.manager.entity.UserRole;
import com.vain.manager.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vain
 * @date 2017/10/9 20:01
 * @description 角色管理具体service类
 */
@Service
public class RoleServiceImpl extends AbstractBaseService implements IRoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RoleMenuDao roleMenuDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public PageList<Role> getPagedList(Role entity) throws ErrorCodeException {
        return null;
    }

    @Override
    public List<Role> getList(Role entity) throws ErrorCodeException {
        return roleDao.getList(entity);
    }

    @Override
    public Role get(Role entity) throws ErrorCodeException {
        return roleDao.get(entity);
    }

    @Override
    public int add(Role entity) throws ErrorCodeException {
        return roleDao.insert(entity);
    }

    @Override
    public int modify(Role entity) throws ErrorCodeException {
        return roleDao.update(entity);
    }

    @Override
    public int delete(Role entity) throws ErrorCodeException {
        return roleDao.delete(entity);
    }

    /**
     * 分配角色菜单权限时候 先删除原有权限在添加新的权限
     *
     * @param role
     */
    @Override
    public int assignRoleMenu(Role role) {
        roleMenuDao.delete(new RoleMenu(role.getId()));//删除
        List<Menu> menus = role.getMenus();
        List<RoleMenu> roleMenus = new ArrayList<>();
        for (Menu menu : menus) {
            assignRoleMenu(menu, role, roleMenus);
        }
        if (!roleMenus.isEmpty())
            return roleMenuDao.assignRoleMenu(roleMenus); //批量插入
        return 0;
    }

    /**
     * 之前有角色就更新 没有就添加
     *
     * @param userRole
     * @return
     */
    @Override
    public int grantUserRole(UserRole userRole) {
        UserRole dbUserRole = new UserRole();
        dbUserRole.setUserId(userRole.getUserId());
        dbUserRole = userRoleDao.get(dbUserRole);//获取userRole在数据库的ID
        if (dbUserRole.getId() != null) {
            userRole.setId(dbUserRole.getId());
            return userRoleDao.update(userRole);
        } else {
            return userRoleDao.insert(userRole);
        }
    }

    //递归遍历menu  不受菜单层级限制
    private void assignRoleMenu(Menu menu, Role role, List<RoleMenu> roleMenus) {
        if (menu.getHasPermission()) {
            roleMenus.add(new RoleMenu(role.getId(), menu.getId())); //添加
            if (menu.getChildren() != null && !menu.getChildren().isEmpty()) {
                for (Menu data : menu.getChildren()) {
                    assignRoleMenu(data, role, roleMenus);
                }
            }
        }

    }
}
