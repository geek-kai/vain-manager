package com.vain.manager.controller;

import com.vain.manager.common.controller.AbstractBaseController;
import com.vain.manager.common.entity.Response;
import com.vain.manager.constant.SysConstants;
import com.vain.manager.entity.Role;
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
        response.setCode(SysConstants.Code.SUCCESS_CODE);
        response.setMsg(SysConstants.Code.SUCCESS_MSG);
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
        response.setCode(SysConstants.Code.SUCCESS_CODE);
        response.setMsg(SysConstants.Code.SUCCESS_MSG);
        return response;
    }

    @Override
    public Response<Role> add(@RequestBody Role entity, HttpServletRequest request) throws Exception {
        return null;
    }

    @Override
    public Response<Role> modify(@RequestBody Role entity, HttpServletRequest request) throws Exception {
        return null;
    }

    @Override
    public Response<Role> delete(@RequestBody Role entity, HttpServletRequest request) throws Exception {
        return null;
    }
}
