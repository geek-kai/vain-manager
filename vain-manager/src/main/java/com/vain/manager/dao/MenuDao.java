package com.vain.manager.dao;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.vain.manager.common.dao.AbstractBaseDao;
import com.vain.manager.entity.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by vain on 2017/9/23.
 */
@Repository
public class MenuDao extends AbstractBaseDao<Menu> {

    @Override
    public PageList<Menu> getPagedList(Menu entity, int curPage, int pageSize) {
        return null;
    }

    @Override
    public List<Menu> getList(Menu entity) {
        return this.sqlSession.selectList("com.vain.manager.entity.Menu.getList", entity);
    }

    @Override
    public Menu get(Menu entity) {
        return null;
    }

    @Override
    public int insert(Menu entity) {
        return 0;
    }

    @Override
    public int update(Menu entity) {
        return this.sqlSession.update("com.vain.manager.entity.Menu.update", entity);
    }

    @Override
    public int delete(Menu entity) {
        return 0;
    }

    /**
     * 获取用户下t_user_menu所有的菜单
     *
     * @param menu userId
     * @return
     */
    public List<Menu> getUserAllMenus(Menu menu) {
        return this.sqlSession.selectList("com.vain.manager.entity.Menu.getUserAllMenus", menu);
    }


    /**
     * 获取角色下所有菜单
     *
     * @param roleKey roleKey
     * @return
     */
    public List<Menu> getMenusByRoleKey(String roleKey) {
        return this.sqlSession.selectList("com.vain.manager.entity.Menu.getMenusByRoleKey", roleKey);
    }

    /**
     * 获取用户下所有菜单 带hasPermission判断
     *
     * @param menu userId
     * @return
     */
    public List<Menu> getMenusByUserId(Menu menu) {
        return this.sqlSession.selectList("com.vain.manager.entity.Menu.getMenusByUserId", menu);
    }

    /**
     * 获取角色下所有菜单 带hasPermission判断
     *
     * @param menu roleId
     * @return
     */
    public List<Menu> getMenusByRoleId(Menu menu) {
        return this.sqlSession.selectList("com.vain.manager.entity.Menu.getMenusByRoleId", menu);
    }

    /**
     * 查询用户下所有角色菜单集合
     *
     * @param menu userId
     * @return
     */
    public List<Menu> getMenusByUserRoles(Menu menu) {
        return this.sqlSession.selectList("com.vain.manager.entity.Menu.getMenusByUserRoles", menu);
    }


}
