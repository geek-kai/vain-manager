package com.vain.manager.controller;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.vain.manager.common.controller.AbstractBaseController;
import com.vain.manager.common.entity.Response;
import com.vain.manager.common.exception.ErrorCodeException;
import com.vain.manager.component.SysConfigComponent;
import com.vain.manager.constant.SysConstants;
import com.vain.manager.entity.SystemConfig;
import com.vain.manager.service.ISystemConfigService;
import com.vain.manager.util.OSUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author vain
 * @date 2017/10/14 21:41
 * @description 系统配置 接口
 * 系统配置在项目初始化就已经加载
 * 所以允许修改 清空 重新加载数据
 */
@RequestMapping(value = "/systemConfig")
@Controller
public class SystemConfigController extends AbstractBaseController<SystemConfig> {

    @Autowired
    private ISystemConfigService systemConfigService;

    @Autowired
    private SysConfigComponent sysConfigComponent;

    /**
     * 获取系统配置信息
     *
     * @param entity  参数实体
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getPagedList", method = RequestMethod.POST)
    @ResponseBody
    @Override
    public Response<SystemConfig> getPagedList(@RequestBody SystemConfig entity, HttpServletRequest request) throws Exception {
        PageList<SystemConfig> configs = systemConfigService.getPagedList(entity);
        Response<SystemConfig> response = new Response<>();
        response.setDataList(configs);
        return response;
    }

    @Override
    public Response<SystemConfig> getList(@RequestBody SystemConfig entity, HttpServletRequest request) throws Exception {
        return null;
    }

    /**
     * 获取单个系统配置
     *
     * @param entity  参数实体
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    @ResponseBody
    @Override
    public Response<SystemConfig> get(@RequestBody SystemConfig entity, HttpServletRequest request) throws Exception {
        if (entity == null || entity.getId() == null)
            throw new ErrorCodeException(SysConstants.Code.PARAM_ERROR_CODE, SysConstants.Code.PARAM_ERROR_MSG);
        Response<SystemConfig> response = new Response<>();
        response.setData(systemConfigService.get(entity));
        return response;
    }

    @Override
    public Response<SystemConfig> getById(@PathVariable long id, HttpServletRequest request) throws Exception {
        return null;
    }

    @Override
    public Response<SystemConfig> add(@RequestBody SystemConfig entity, HttpServletRequest request) throws Exception {
        return null;
    }

    /**
     * 修改系统配置信息
     *
     * @param entity  参数实体
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    @Override
    public Response<SystemConfig> modify(@RequestBody SystemConfig entity, HttpServletRequest request) throws Exception {
        Response<SystemConfig> response = new Response<>();
        sysConfigComponent.reload();
        response.setData(systemConfigService.modify(entity));
        return response;
    }

    /**
     * 获取服务器静态信息
     *
     * @return
     */
    @RequiresPermissions("system.info.getSystemInfo")
    @RequestMapping(value = "/getSystemInfo", method = RequestMethod.POST)
    @ResponseBody
    public Response getSystemInfo() {
        Response response = new Response();
        response.setData(OSUtils.getInfo());
        return response;
    }

    /**
     * 获取服务器动态信息(cpu) 默认检测时长为5s
     *
     * @return
     */
    @RequiresPermissions("xxxxxxxxxx")
    @RequestMapping(value = "/getSystemCpuInfo", method = RequestMethod.POST)
    @ResponseBody
    public Response getSystemCpuInfo() {
        Response response = new Response();
        response.setData(OSUtils.getCpuRatioForWindows());
        return response;
    }


    @Override
    public Response<SystemConfig> delete(@RequestBody SystemConfig entity, HttpServletRequest request) throws Exception {
        return null;
    }
}
