package com.vain.manager.controller;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.vain.manager.common.controller.AbstractBaseController;
import com.vain.manager.common.entity.Response;
import com.vain.manager.log.entity.OperationLog;
import com.vain.manager.log.service.IOperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author vain
 * @date： 2017/11/6 14:58
 * @description： 操作日志类
 */
@RequestMapping(value = "/log")
@Controller
public class OperationLogController extends AbstractBaseController<OperationLog> {

    @Autowired
    private IOperationLogService operationLogService;

    @RequestMapping(value = "/getPagedList", method = RequestMethod.POST)
    @ResponseBody
    @Override
    public Response<OperationLog> getPagedList(@RequestBody OperationLog entity, HttpServletRequest request) throws Exception {
        Response<OperationLog> response = new Response<>();
        PageList<OperationLog> logs = operationLogService.getPagedList(entity);
        response.setDataList(logs);
        return response;
    }

    @Override
    public Response<OperationLog> getList(@RequestBody OperationLog entity, HttpServletRequest request) throws Exception {
        return null;
    }

    @Override
    public Response<OperationLog> get(@RequestBody OperationLog entity, HttpServletRequest request) throws Exception {
        return null;
    }

    @Override
    public Response<OperationLog> getById(@PathVariable long id, HttpServletRequest request) throws Exception {
        return null;
    }

    @Override
    public Response<OperationLog> add(@RequestBody OperationLog entity, HttpServletRequest request) throws Exception {
        return null;
    }

    @Override
    public Response<OperationLog> modify(@RequestBody OperationLog entity, HttpServletRequest request) throws Exception {
        return null;
    }

    @Override
    public Response<OperationLog> delete(@RequestBody OperationLog entity, HttpServletRequest request) throws Exception {
        return null;
    }
}
