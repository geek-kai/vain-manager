package com.vain.manager.controller;

import com.vain.manager.cache.PermissionCache;
import com.vain.manager.common.controller.AbstractBaseController;
import com.vain.manager.common.entity.Response;
import com.vain.manager.common.exception.ErrorCodeException;
import com.vain.manager.constant.SysConstants;
import com.vain.manager.entity.Menu;
import com.vain.manager.entity.Role;
import com.vain.manager.entity.User;
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
        if (!StrUtil.isBlank(entity.getMenuKey())) {
            logger.info("菜单操作权限列表");
            Response<Menu> res = new Response<>(); //返回新对象 不修改缓存内数据
            res.setDataList(menuService.getChildMenu(response.getDataList(), entity.getMenuKey()));
            res.setCode(SysConstants.Code.SUCCESS_CODE);
            res.setData(SysConstants.Code.SUCCESS_MSG);
            return res;
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
            cache.setMenus(response);//重新缓存
        }
        return response;
    }

    /**
     * 根据role角色id获取角色权限
     *
     * @param entity
     * @return
     * @throws ErrorCodeException
     */
    @RequestMapping(value = "/getMenusByRoleId", method = RequestMethod.POST)
    @ResponseBody
    public Response<Menu> getMenusByRoleId(@RequestBody Role entity) throws ErrorCodeException {
        Response<Menu> response = new Response<>();
        response.setDataList(menuService.getMenusByRoleId(entity));
        return response;
    }

    /**
     * 获取用户的权限菜单
     *
     * @param entity
     * @return
     */
    @RequestMapping(value = "/getMenusByUserId", method = RequestMethod.POST)
    @ResponseBody
    public Response<Menu> getMenusByUserId(@RequestBody User entity) throws ErrorCodeException {
        Response<Menu> response = new Response<>();
        response.setDataList(menuService.getMenusByUser(entity));
        return response;
    }

    /**
     * 获取所有的菜单列表
     *
     * @param entity
     * @return
     * @throws ErrorCodeException
     */
    @RequestMapping(value = "/getMenuList", method = RequestMethod.POST)
    @ResponseBody
    public Response<Menu> getMenuList(@RequestBody Menu entity) throws ErrorCodeException {
        Response<Menu> response = new Response<>();
        response.setDataList(menuService.getList(entity, true));
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

    /**
     * 修改菜单
     *
     * @param entity  参数实体
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    @Override
    public Response<Menu> modify(@RequestBody Menu entity, HttpServletRequest request) throws Exception {
        Response<Menu> response = new Response<>();
        menuService.modify(entity);
        return response;
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
