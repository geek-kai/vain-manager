package com.vain.manager.shiro.service;

import com.vain.manager.entity.Menu;
import com.vain.manager.service.IMenuService;
import com.vain.manager.shiro.PermissionContext;
import com.vain.manager.shiro.authenticator.SubjectInfo;
import com.vain.manager.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

/**
 * Created by vain on 2017/9/19.
 * 用过用户id获取到菜单列表
 */
@Service
public class AuthorizationServiceImpl implements IAuthorizationService {

    @Autowired
    private IMenuService menuService;

    @Override
    public PermissionContext getPermissions(SubjectInfo subjectInfo) {
        PermissionContext context = new PermissionContext();
        HashSet<String> keys = new HashSet<>(); //菜单key的集合
        HashSet<Menu> menus = menuService.getMenusByUserId(subjectInfo.getUserId(), subjectInfo.getUserType());
        if (menus != null) {
            for (Menu data : menus) {
                if (!StrUtil.isEmpty(data.getMenukey()))
                    keys.add(data.getMenukey());
            }
        }
        context.setPermissions(keys);
        return context;
    }
}
