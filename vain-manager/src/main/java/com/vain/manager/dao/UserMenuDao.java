package com.vain.manager.dao;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.vain.manager.common.dao.AbstractBaseDao;
import com.vain.manager.entity.UserMenu;
import com.vain.manager.entity.UserMenu;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author vain
 * @Descritpion user-menu 关联对应的dao类
 * @Date 21:47 2017/10/13
 */
@Repository
public class UserMenuDao extends AbstractBaseDao<UserMenu> {
    @Override
    public PageList<UserMenu> getPagedList(UserMenu entity, int curPage, int pageSize) {
        return null;
    }

    @Override
    public List<UserMenu> getList(UserMenu entity) {
        return null;
    }

    @Override
    public UserMenu get(UserMenu entity) {
        return null;
    }

    @Override
    public int insert(UserMenu entity) {
        return 0;
    }

    @Override
    public int update(UserMenu entity) {
        return 0;
    }

    @Override
    public int delete(UserMenu entity) {
        return sqlSession.delete("com.vain.manager.entity.UserMenu.delete", entity);
    }

    /**
     * 分配用户权限
     *
     * @param list
     */
    public int assignUserMenu(List<UserMenu> list) {
        return this.sqlSession.insert("com.vain.manager.entity.UserMenu.assignUserMenu", list);
    }
}
