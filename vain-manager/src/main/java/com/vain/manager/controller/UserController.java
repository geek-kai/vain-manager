package com.vain.manager.controller;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.vain.manager.cache.PermissionCache;
import com.vain.manager.common.controller.AbstractBaseController;
import com.vain.manager.common.entity.Response;
import com.vain.manager.common.exception.ErrorCodeException;
import com.vain.manager.constant.SysConstants;
import com.vain.manager.entity.BNews;
import com.vain.manager.entity.User;
import com.vain.manager.log.OperationLog;
import com.vain.manager.log.constant.LogConstants;
import com.vain.manager.model.OnLineUser;
import com.vain.manager.service.IUserService;
import com.vain.manager.shiro.authenticator.DefaultAccountSubject;
import com.vain.manager.shiro.exception.AuthenticationException;
import com.vain.manager.shiro.session.UserSession;
import com.vain.manager.shiro.token.AccountToken;
import com.vain.manager.util.ReptileUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author vain
 * @Description 账号信息操作controller
 * @date 2017/8/31 11:54
 */
@RequestMapping("/user")
@Controller
public class UserController extends AbstractBaseController<User> {

    @Autowired
    private IUserService userService;

    /**
     * 获取新闻数据
     *
     * @return
     */
    @RequestMapping("/getNews/{type}")
    @ResponseBody
    public Response<BNews> getNews(@PathVariable("type") int type) throws Exception {
        List<BNews> news = type == 1 ? ReptileUtils.getNowNews() : ReptileUtils.getTodayNews();
        Response<BNews> response = new Response<>();
        response.setDataList(news);
        return response;
    }


    /**
     * 获取账号列表 （超级管理员不在此列） 分页
     *
     * @param entity  参数实体
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getPagedList", method = RequestMethod.POST)
    @ResponseBody
    @Override
    public Response<User> getPagedList(@RequestBody User entity, HttpServletRequest request) throws Exception {
        PageList<User> users = userService.getPagedList(entity);
        if (users.isEmpty())
            throw new ErrorCodeException(SysConstants.Code.NOT_FOUND_CODE, SysConstants.Code.NOT_FOUND_MSG);
        Response<User> response = new Response<>();
        response.setDataList(users);
        return response;
    }

    @Override
    public Response<User> getList(@RequestBody User entity, HttpServletRequest request) throws Exception {
        return null;
    }

    @Override
    public Response<User> get(@RequestBody User entity, HttpServletRequest request) throws Exception {
        return null;
    }

    @Override
    public Response<User> getById(@PathVariable long id, HttpServletRequest request) throws Exception {
        return null;
    }

    /**
     * 添加新账号
     *
     * @param entity  参数实体
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    @Override
    public Response<User> add(@RequestBody User entity, HttpServletRequest request) throws Exception {
        if (entity == null || entity.getPasswd() == null || entity.getUserName() == null || entity.getType() == 0)
            throw new ErrorCodeException(SysConstants.Code.PARAM_ERROR_CODE, SysConstants.Code.PARAM_ERROR_MSG);
        Response<User> response = new Response<>();
        response.setData(userService.add(entity));
        return response;
    }

    /**
     * 修改账号部分信息
     *
     * @param entity  参数实体
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    @Override
    public Response<User> modify(@RequestBody User entity, HttpServletRequest request) throws Exception {
        if (entity == null || entity.getId() == null)
            throw new ErrorCodeException(SysConstants.Code.PARAM_ERROR_CODE, SysConstants.Code.PARAM_ERROR_MSG);
        Response<User> response = new Response<>();
        response.setData(userService.modify(entity));
        return response;
    }

    /**
     * 删除账号 （更新标识符）
     *
     * @param entity  参数实体
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    @Override
    public Response<User> delete(@RequestBody User entity, HttpServletRequest request) throws Exception {
        if (entity == null || (entity.getId() == null && entity.getIds() == null))
            throw new ErrorCodeException(SysConstants.Code.PARAM_ERROR_CODE, SysConstants.Code.PARAM_ERROR_MSG);
        Response<User> response = new Response<>();
        response.setData(userService.delete(entity));
        return response;
    }

    /**
     * 用户登录
     *
     * @param entity
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    @OperationLog(operationType = LogConstants.OperationLogType.OPERATION_LOGIN)
    public Response<User> login(@RequestBody User entity) throws ErrorCodeException {
        if (entity == null || entity.getUserName() == null || entity.getPasswd() == null)
            throw new ErrorCodeException(SysConstants.Code.PARAM_ERROR_CODE, SysConstants.Code.PARAM_ERROR_MSG);
        Response<User> response = new Response<>();
        AccountToken token = new AccountToken(entity.getUserName(), entity.getPasswd());
        DefaultAccountSubject accountSubject = new DefaultAccountSubject();
        try {
            accountSubject.login(token);
        } catch (AuthenticationException authenticationException) {
            if (authenticationException.getMessage() != null) {
                response.setCode(Integer.parseInt(authenticationException.getMsgCode()));
                response.setMsg(authenticationException.getMessage());
            } else {
                response.setCode(SysConstants.Code.ACCOUNT_OR_PASSWORD_ERROR_CODE);
                response.setMsg(SysConstants.Code.ACCOUNT_OR_PASSWORD_ERROR_MSG);
            }
            return response;
        }
        User data = new User();
        data.setUserName(UserSession.getUserName());
        data.setNickname(UserSession.getNickName());
        data.setId(UserSession.getUserId());
        data.setType(UserSession.getUserType());
        response.setData(data);
        return response;
    }

    /**
     * 用户注销 移除缓存
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public Response logout(HttpSession session) {
        session.removeAttribute(PermissionCache.SESSION_KEY_PERMISSION_CACHE);//移除权限缓存
        DefaultAccountSubject subject = new DefaultAccountSubject();
        subject.logout();
        Response response = new Response<>();
        response.setData("");
        response.setCode(SysConstants.Code.SUCCESS_CODE);
        response.setMsg(SysConstants.Code.SUCCESS_MSG);
        return response;
    }

    /**
     * 重置用户密码
     *
     * @param entity
     * @return
     * @throws ErrorCodeException
     */
    @RequestMapping(value = "/resetPwd", method = RequestMethod.POST)
    @ResponseBody
    public Response resetPwd(@RequestBody User entity) throws ErrorCodeException {
        if (entity == null || entity.getNewpasswd() == null)
            throw new ErrorCodeException(SysConstants.Code.PARAM_ERROR_CODE, SysConstants.Code.PARAM_ERROR_MSG);
        Response response = new Response<>();
        response.setData(userService.resetPwd(entity));
        return response;
    }


    /**
     * 锁定 / 解锁 账号
     *
     * @param entity
     * @return
     */
    @RequestMapping(value = "/lock", method = RequestMethod.POST)
    @ResponseBody
    public Response lockUser(@RequestBody User entity) throws ErrorCodeException {
        if (entity == null || (entity.getId() == null && entity.getIds() == null))
            throw new ErrorCodeException(SysConstants.Code.PARAM_ERROR_CODE, SysConstants.Code.PARAM_ERROR_MSG);
        userService.lock(entity);
        Response response = new Response<>();
        response.setData(userService.lock(entity));
        return response;
    }

    /**
     * 分配用户权限
     *
     * @param entity
     * @return
     */
    @RequestMapping(value = "/assignUserMenu", method = RequestMethod.POST)
    @ResponseBody
    public Response<User> assignUserMenu(@RequestBody User entity) {
        Response<User> response = new Response<>();
        response.setData(userService.assignUserMenu(entity));
        return response;
    }

    /**
     * 获取在线用户
     *
     * @return
     */
    @RequestMapping(value = "/onLineUser", method = RequestMethod.POST)
    @ResponseBody
    public Response<OnLineUser> onLineUser() {
        Response<OnLineUser> response = new Response<>();
        response.setDataList(userService.getOnLineUser());
        return response;
    }

    /**
     * 在线用户强制下线
     *
     * @return
     */
    @RequestMapping(value = "/forcedOffLine", method = RequestMethod.POST)
    @ResponseBody
    public Response<OnLineUser> forcedOffLine(@RequestBody OnLineUser entity) {
        if (entity == null || entity.getId() == null)
            throw new ErrorCodeException(SysConstants.Code.PARAM_ERROR_CODE, SysConstants.Code.PARAM_ERROR_MSG);
        Response<OnLineUser> response = new Response<>();
        response.setData(userService.forcedOffLine(entity));
        return response;
    }
}
