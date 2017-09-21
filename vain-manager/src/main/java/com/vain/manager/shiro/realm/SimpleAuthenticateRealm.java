package com.vain.manager.shiro.realm;

import com.vain.manager.common.exception.ErrorRCodeException;
import com.vain.manager.constant.SysConstants;
import com.vain.manager.entity.User;
import com.vain.manager.service.IUserService;
import com.vain.manager.shiro.authenticator.SubjectInfo;
import com.vain.manager.shiro.authenticator.UserSubjectInfo;
import com.vain.manager.shiro.exception.AuthenticationException;
import com.vain.manager.shiro.token.AccountToken;
import com.vain.manager.shiro.token.Token;
import com.vain.manager.util.StrUtil;
import org.apache.poi.ss.formula.functions.T;
import org.apache.shiro.authc.AuthenticationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by vain on 2017/9/19.
 * 简单认证 只认证账号密码 然后获取信息
 */
@Service
public class SimpleAuthenticateRealm implements AuthenticateRealm {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IUserService userService;

    @Override
    public SubjectInfo login(Token token) {
        UserSubjectInfo userSubjectInfo = null;
        String principal = (String) token.getPrincipal();
        String password = (String) token.getCredentials();
        User user = new User();
        if (StrUtil.isNumeric(principal)) {
            user.setPhone(principal);
        } else if (StrUtil.isEmail(principal)) {
            user.setEmail(principal);
        } else {
            user.setUserName(principal);
        }
        user.setPasswd(password);
        User dbUser;
        try {
            dbUser = userService.login(user);
            if (dbUser != null) {
                if (dbUser.getState() == SysConstants.Code.ACCOUNT_IS_LOCKED_CODE) {
                    logger.error("login failure，this account is locked");
                    // 登录失败，账户被禁用 后期可根据state来扩展
                    throw new AuthenticationException(String.valueOf(SysConstants.Code.ACCOUNT_IS_EXISTS_CODE), "this account is locked");
                }
                userSubjectInfo = new UserSubjectInfo();
                // 账号Id
                userSubjectInfo.setUserId(dbUser.getId());
                // 登录名
                userSubjectInfo.setUserName(dbUser.getUserName());
                userSubjectInfo.setNickName(dbUser.getNickname());
                userSubjectInfo.setUserType(dbUser.getType());
            } else {
                logger.error("login failure，account does not exist");
                throw new AuthenticationException(SysConstants.Code.ACCOUNT_IS_NOT_EXISTS_MSG);// 登录失败，账号不存在
            }
        } catch (ErrorRCodeException e) {
            logger.error("login failure", e);
            throw new AuthenticationException(String.valueOf(e.getCode()), "occur ErrorRCodeException");
        }

        return userSubjectInfo;
    }

    @Override
    public boolean accept(Token token) {
        return true;
    }
}
