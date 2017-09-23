package com.vain.manager.controller;

import com.vain.manager.common.controller.AbstractBaseController;
import com.vain.manager.common.controller.BaseController;
import com.vain.manager.common.entity.Response;
import com.vain.manager.entity.Menu;
import com.vain.manager.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author vain
 * @date 2017/9/23 23:12
 * @description 获取菜单的controller
 */
@Controller
@RequestMapping("menu")
public class MenuController extends AbstractBaseController<Menu> {

    @Autowired
    private IMenuService menuService;

    @Override
    public Response<Menu> getPagedList(@RequestBody Menu entity, HttpServletRequest request) throws Exception {
        return null;
    }

    @Override
    public Response<Menu> getList(@RequestBody Menu entity, HttpServletRequest request) throws Exception {
        return null;
    }

    @Override
    public Response<Menu> get(@RequestBody Menu entity, HttpServletRequest request) throws Exception {
        return null;
    }

    @Override
    public Response<Menu> getById(@PathVariable long id, HttpServletRequest request) throws Exception {
        return null;
    }

    @Override
    public Response<Menu> add(@RequestBody Menu entity, HttpServletRequest request) throws Exception {
        return null;
    }

    @Override
    public Response<Menu> modify(@RequestBody Menu entity, HttpServletRequest request) throws Exception {
        return null;
    }

    @Override
    public Response<Menu> delete(@RequestBody Menu entity, HttpServletRequest request) throws Exception {
        return null;
    }
}
