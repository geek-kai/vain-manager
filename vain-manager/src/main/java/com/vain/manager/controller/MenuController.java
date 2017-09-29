package com.vain.manager.controller;

import com.vain.manager.cache.PermissionCache;
import com.vain.manager.common.controller.AbstractBaseController;
import com.vain.manager.common.entity.Response;
import com.vain.manager.common.exception.ErrorCodeException;
import com.vain.manager.constant.SysConstants;
import com.vain.manager.entity.Menu;
import com.vain.manager.service.IMenuService;
import com.vain.manager.shiro.session.UserSession;
import com.vain.manager.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
    public Response<Menu> getMyMenu(@RequestBody Menu entity, HttpServletRequest request) throws ErrorCodeException {
        PermissionCache cache = getPermissionCacheFromSession(request);
        Response<Menu> response = cache.getMenus(); //获取session中的缓存menu
        if (!StrUtil.isEmpty(entity.getMenukey())) {
            logger.info("菜单操作权限列表");
        } else {
            if (response != null) {
                logger.info("获取菜单权限缓存数据");
                response.setCode(SysConstants.Code.SUCCESS_CODE);
                response.setData(SysConstants.Code.SUCCESS_MSG);
                return response;
            }
            entity.setUserId(UserSession.getUserId());
            entity.setType(UserSession.getUserType());
            response = new Response<>();
            response.setDataList(menuService.getMyMenus(entity));
            response.setCode(SysConstants.Code.SUCCESS_CODE);
            response.setMsg(SysConstants.Code.SUCCESS_MSG);
            return response;
        }
        return response;
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


    /**
     * 获取session中的menu缓存数据
     *
     * @param request
     * @return
     */
    private PermissionCache getPermissionCacheFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        PermissionCache perCache = (PermissionCache) session.getAttribute(PermissionCache.SESSION_KEY_PERMISSION_CACHE);
        if (perCache == null) {
            perCache = PermissionCache.create();
            session.setAttribute(PermissionCache.SESSION_KEY_PERMISSION_CACHE, perCache);
        }
        return perCache;
    }
}
