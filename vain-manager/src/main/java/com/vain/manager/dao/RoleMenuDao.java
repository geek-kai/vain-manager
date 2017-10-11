package com.vain.manager.dao;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.vain.manager.common.dao.AbstractBaseDao;
import com.vain.manager.entity.RoleMenu;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author vain
 * @date： 2017/10/11 15:14
 * @description： role-menu关联dao类
 */
@Repository
public class RoleMenuDao extends AbstractBaseDao<RoleMenu> {
    @Override
    public PageList<RoleMenu> getPagedList(RoleMenu entity, int curPage, int pageSize) {
        return null;
    }

    @Override
    public List<RoleMenu> getList(RoleMenu entity) {
        return null;
    }

    @Override
    public RoleMenu get(RoleMenu entity) {
        return null;
    }

    @Override
    public int insert(RoleMenu entity) {
        return 0;
    }

    @Override
    public int update(RoleMenu entity) {
        return 0;
    }

    @Override
    public int delete(RoleMenu entity) {
        return sqlSession.delete("com.vain.manager.entity.RoleMenu.delete", entity);
    }

    /**
     * 分配角色权限
     *
     * @param list
     */
    public void assignRoleMenu(List<RoleMenu> list) {
        this.sqlSession.insert("com.vain.manager.entity.RoleMenu.assignRoleMenu", list);
    }
}
