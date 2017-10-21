package com.vain.manager.controller;

import com.vain.manager.common.controller.AbstractBaseController;
import com.vain.manager.common.entity.Response;
import com.vain.manager.entity.Role;
import com.vain.manager.entity.UserRole;
import com.vain.manager.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author vain
 * @date 2017/10/9 20:04
 * @description 角色接口
 */
@RequestMapping("role")
@Controller
public class RoleController extends AbstractBaseController<Role> {

    @Autowired
    private IRoleService roleService;

    @Override
    public Response<Role> getPagedList(@RequestBody Role entity, HttpServletRequest request) throws Exception {
        return null;
    }

    /**
     * 获取角色参数列表
     *
     * @param entity  参数实体
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    @ResponseBody
    @Override
    public Response<Role> getList(@RequestBody Role entity, HttpServletRequest request) throws Exception {
        Response<Role> response = new Response<>();
        response.setDataList(roleService.getList(entity));
        return response;
    }

    @Override
    public Response<Role> get(@RequestBody Role entity, HttpServletRequest request) throws Exception {
        return null;
    }

    /**
     * 通过id 获取对应的角色信息
     *
     * @param id
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    @ResponseBody
    @Override
    public Response<Role> getById(@PathVariable long id, HttpServletRequest request) throws Exception {
        Role entity = new Role();
        entity.setId(id);
        Response<Role> response = new Response<>();
        response.setData(roleService.get(entity));
        return response;
    }

    /**
     * 根据角色id分配菜单权限
     *
     * @param entity
     * @return
     */
    @RequestMapping(value = "/assignRoleMenu", method = RequestMethod.POST)
    @ResponseBody
    public Response<Role> assignRoleMenu(@RequestBody Role entity) {
        Response<Role> response = new Response<>();
        response.setData(roleService.assignRoleMenu(entity));
        return response;
    }

    /**
     * 添加角色
     *
     * @param entity  参数实体
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    @Override
    public Response<Role> add(@RequestBody Role entity, HttpServletRequest request) throws Exception {
        Response<Role> response = new Response<>();
        response.setData(roleService.add(entity));
        return response;
    }

    /**
     * 修改角色
     *
     * @param entity  参数实体
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    @Override
    public Response<Role> modify(@RequestBody Role entity, HttpServletRequest request) throws Exception {
        Response<Role> response = new Response<>();
        response.setData(roleService.modify(entity));
        return response;
    }

    /**
     * 删除某一角色
     *
     * @param entity  参数实体
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    @Override
    public Response<Role> delete(@RequestBody Role entity, HttpServletRequest request) throws Exception {
        Response<Role> response = new Response<>();
        response.setData(roleService.delete(entity));
        return response;
    }

    /**
     * 给账号赋予角色
     *
     * @param entity  参数实体
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/grantUserRole", method = RequestMethod.POST)
    @ResponseBody
    public Response<UserRole> grantUserRole(@RequestBody UserRole entity, HttpServletRequest request) throws Exception {
        Response<UserRole> response = new Response<>();
        response.setData(roleService.grantUserRole(entity));
        return response;
    }
}
