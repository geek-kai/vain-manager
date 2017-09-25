package com.vain.manager.controller;

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
import java.util.List;

/**
 * @author vain
 * @Date
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
    public
    @RequestMapping("/getNews/{type}")
    @ResponseBody
    Response<BNews> getNews(@PathVariable("type") int type) throws Exception {
        List<BNews> news = type == 1 ? ReptileUtils.getNowNews() : ReptileUtils.getTodayNews();
        Response<BNews> response = new Response<>();
        response.setCode(SysConstants.Code.SUCCESS_CODE);
        response.setMsg(SysConstants.Code.SUCCESS_MSG);
        response.setData(news);
        return response;
    }

    /**
     * @author vain
     * @Descritpion
     * @Date 23:04 2017/9/23
     */
    @Override
    public Response<User> getPagedList(@RequestBody User entity, HttpServletRequest request) throws Exception {
        return null;
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
}
