package com.vain.manager.controller;

import com.vain.manager.common.controller.AbstractBaseController;
import com.vain.manager.common.entity.Response;
import com.vain.manager.common.exception.ErrorRCodeException;
import com.vain.manager.constant.SysConstants;
import com.vain.manager.entity.Menu;
import com.vain.manager.service.IMenuService;
import com.vain.manager.shiro.session.UserSession;
import com.vain.manager.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 获取用户自己的菜单
     *
     * @param entity
     * @return
     */
    @RequestMapping(value = "/getMyMenus", method = RequestMethod.POST)
    @ResponseBody
    public Response<Menu> getMyMenu(@RequestBody Menu entity) throws ErrorRCodeException {
        if (!StrUtil.isEmpty(entity.getMenukey())) {
            logger.info("菜单操作权限列表");
        } else {
            entity.setUserId(UserSession.getUserId());
            entity.setType(UserSession.getUserType());
            Response<Menu> response = new Response<>();
            response.setDataList(menuService.getMyMenus(entity));
            response.setCode(SysConstants.Code.SUCCESS_CODE);
            response.setMsg(SysConstants.Code.SUCCESS_MSG);
            return response;
        }
        return null;
    }

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
