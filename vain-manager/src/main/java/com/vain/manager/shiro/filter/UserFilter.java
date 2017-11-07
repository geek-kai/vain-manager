package com.vain.manager.shiro.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author vain
 * @date： 2017/10/27 11:39
 * @description： 基于t-menu的menuKey来拦截(数据库配置的路径基于web配置的api之后 否则mapperValue会为空!!)
 */
public class UserFilter extends AuthorizationFilter {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object mapperValue) throws Exception {
        Subject subject = getSubject(servletRequest, servletResponse);
        //访问没有带身份principal信息 直接不允许
        if (subject.getPrincipal() == null) {
            servletRequest.setAttribute("NO_SESSION", 1);
            return false;
        }
        //验证menuKey
        String[] perms = (String[]) mapperValue;
        if (perms != null && perms.length > 0) {
            for (String perm : perms) {
                if (subject.isPermitted(perm)) {
                    logger.info(perm);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        if (request.getAttribute("NO_SESSION") != null) {
            HttpServletResponse res = WebUtils.toHttp(response);
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        HttpServletResponse res = WebUtils.toHttp(response);
        res.sendError(HttpServletResponse.SC_FORBIDDEN);
        return false;
    }
}
