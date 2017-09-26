package com.vain.manager.controller;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.vain.manager.cache.PermissionCache;
import com.vain.manager.common.controller.AbstractBaseController;
import com.vain.manager.common.entity.Response;
import com.vain.manager.common.exception.ErrorRCodeException;
import com.vain.manager.constant.SysConstants;
import com.vain.manager.entity.BNews;
import com.vain.manager.entity.User;
import com.vain.manager.service.IUserService;
import com.vain.manager.shiro.authenticator.DefaultAccountSubject;
import com.vain.manager.shiro.session.UserSession;
import com.vain.manager.shiro.token.AccountToken;
import com.vain.manager.util.ReptileUtils;
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
        response.setCode(SysConstants.Code.SUCCESS_CODE);
        response.setMsg(SysConstants.Code.SUCCESS_MSG);
        response.setData(news);
        return response;
    }


    @RequestMapping(value = "/getPagedList", method = RequestMethod.POST)
    @ResponseBody
    @Override
    public Response<User> getPagedList(@RequestBody User entity, HttpServletRequest request) throws Exception {
        PageList<User> users = userService.getPagedList(entity);
        if (users.isEmpty())
            throw new ErrorRCodeException(SysConstants.Code.NOT_FOUND_CODE, SysConstants.Code.NOT_FOUND_MSG);
        Response<User> response = new Response<>();
        response.setDataList(users);
        response.setCode(SysConstants.Code.SUCCESS_CODE);
        response.setMsg(SysConstants.Code.SUCCESS_MSG);
        response.setTotalSize(users.getPaginator().getTotalCount());
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

    @Override
    public Response<User> add(@RequestBody User entity, HttpServletRequest request) throws Exception {
        return null;
    }

    @Override
    public Response<User> modify(@RequestBody User entity, HttpServletRequest request) throws Exception {
        return null;
    }

    @Override
    public Response<User> delete(@RequestBody User entity, HttpServletRequest request) throws Exception {
        return null;
    }

    /**
     * 用户登录
     *
     * @param entity
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Response<User> login(@RequestBody User entity) throws ErrorRCodeException {
        if (entity == null || entity.getUserName() == null || entity.getPasswd() == null)
            throw new ErrorRCodeException(SysConstants.Code.PARAM_ERROR_CODE, SysConstants.Code.PARAM_ERROR_MSG);
        Response<User> response = new Response<>();
        AccountToken token = new AccountToken(entity.getUserName(), entity.getPasswd());
        DefaultAccountSubject accountSubject = new DefaultAccountSubject();
        accountSubject.login(token);
        User data = new User();
        data.setUserName(UserSession.getUserName());
        data.setNickname(UserSession.getNickName());
        data.setId(UserSession.getUserId());
        data.setType(UserSession.getUserType());
        response.setData(data);
        response.setCode(SysConstants.Code.SUCCESS_CODE);
        response.setMsg(SysConstants.Code.SUCCESS_MSG);
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
}
