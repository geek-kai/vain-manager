package com.vain.manager.controller;

import com.vain.manager.common.controller.AbstractBaseController;
import com.vain.manager.common.entity.Response;
import com.vain.manager.common.exception.ErrorCodeException;
import com.vain.manager.constant.SysConstants;
import com.vain.manager.entity.ScheduleJob;
import com.vain.manager.service.IScheduleJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author vain
 * @date 2017/10/31 21:26
 * @description 定时任务接口
 * 如果在实际中只是单纯的使用定时任务做一些简单的任务
 * 不需要动态及时修改执行表达式的时候，或者执行任务并没有多个执行方法可供选择的的时候
 * 其实可以使用spring自带的task来完成定时
 * <bean id="taskJob" class="com.vain.manager.quartz.AutoFinishJob"/>
 * <task:scheduled-tasks>
 * <task:scheduled ref="taskJob" method="autoTask" cron="0 0 0 * * ?"/>
 * </task:scheduled-tasks>
 */
@RequestMapping("/scheduleJob")
@Controller
public class ScheduleJobController extends AbstractBaseController<ScheduleJob> {

    @Autowired
    private IScheduleJobService scheduleJobService;

    @Override
    public Response<ScheduleJob> getPagedList(@RequestBody ScheduleJob entity, HttpServletRequest request) throws Exception {
        return null;
    }

    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    @ResponseBody
    @Override
    public Response<ScheduleJob> getList(@RequestBody ScheduleJob entity, HttpServletRequest request) throws Exception {
        Response<ScheduleJob> response = new Response<>();
        response.setDataList(scheduleJobService.getList(entity));
        return response;
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    @ResponseBody
    @Override
    public Response<ScheduleJob> get(@RequestBody ScheduleJob entity, HttpServletRequest request) throws Exception {
        Response<ScheduleJob> response = new Response<>();
        response.setData(scheduleJobService.get(entity));
        return response;
    }

    @Override
    public Response<ScheduleJob> getById(@PathVariable long id, HttpServletRequest request) throws Exception {
        return null;
    }

    @Override
    public Response<ScheduleJob> add(@RequestBody ScheduleJob entity, HttpServletRequest request) throws Exception {
        return null;
    }

    /**
     * 修改定时任务（只做了修改表达式）
     * 如果要修改任务名和任务组 可以先移除，添加 ，修改
     *
     * @param entity  参数实体
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    @Override
    public Response<ScheduleJob> modify(@RequestBody ScheduleJob entity, HttpServletRequest request) throws Exception {
        if (entity == null || entity.getId() == null)
            throw new ErrorCodeException(SysConstants.Code.PARAM_ERROR_CODE, SysConstants.Code.PARAM_ERROR_MSG);
        Response<ScheduleJob> response = new Response<>();
        response.setData(scheduleJobService.modify(entity));
        return response;
    }

    /**
     * 移除定时任务
     *
     * @param entity  参数实体
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    @Override
    public Response<ScheduleJob> delete(@RequestBody ScheduleJob entity, HttpServletRequest request) throws Exception {
        if (entity == null || entity.getId() == null)
            throw new ErrorCodeException(SysConstants.Code.PARAM_ERROR_CODE, SysConstants.Code.PARAM_ERROR_MSG);
        Response<ScheduleJob> response = new Response<>();
        response.setData(scheduleJobService.delete(entity));
        return response;
    }

    /**
     * 触发一次定时任务
     *
     * @param entity  参数实体
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/triggerJob", method = RequestMethod.POST)
    @ResponseBody
    public Response<ScheduleJob> triggerJob(@RequestBody ScheduleJob entity, HttpServletRequest request) throws Exception {
        if (entity == null || entity.getId() == null)
            throw new ErrorCodeException(SysConstants.Code.PARAM_ERROR_CODE, SysConstants.Code.PARAM_ERROR_MSG);
        Response<ScheduleJob> response = new Response<>();
        scheduleJobService.triggerJob(entity);
        response.setData("");
        return response;
    }


    /**
     * 重新开启定时任务
     *
     * @param entity  参数实体
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("all")
    @RequestMapping(value = "/resumeJob", method = RequestMethod.POST)
    @ResponseBody
    public Response<ScheduleJob> resumeJob(@RequestBody ScheduleJob entity, HttpServletRequest request) throws Exception {
        if (entity == null || entity.getId() == null)
            throw new ErrorCodeException(SysConstants.Code.PARAM_ERROR_CODE, SysConstants.Code.PARAM_ERROR_MSG);
        Response<ScheduleJob> response = new Response<>();
        ScheduleJob job = scheduleJobService.get(entity);
        if (job.getJobStatus() != SysConstants.ENUMTASK.ISRUN.getState()) { //不在运行状态
            job.setJobStatus(SysConstants.ENUMTASK.ISRUN.getState());
            scheduleJobService.add(job); //添加到schedule中
            job.setCronExpression(null);
            scheduleJobService.modify(job);
        } else {
            scheduleJobService.resumeJob(entity); //重新开启
        }
        response.setData("");
        return response;
    }


    /**
     * 暂停定时任务
     *
     * @param entity  参数实体
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/pauseJob", method = RequestMethod.POST)
    @ResponseBody
    public Response<ScheduleJob> pauseJob(@RequestBody ScheduleJob entity, HttpServletRequest request) throws Exception {
        if (entity == null || entity.getId() == null)
            throw new ErrorCodeException(SysConstants.Code.PARAM_ERROR_CODE, SysConstants.Code.PARAM_ERROR_MSG);
        Response<ScheduleJob> response = new Response<>();
        scheduleJobService.pauseJob(entity);
        entity.setJobStatus(SysConstants.ENUMTASK.NOTRUN.getState());
        entity.setCronExpression(null);
        scheduleJobService.modify(entity);
        response.setData("");
        return response;
    }

}
