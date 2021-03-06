package com.vain.manager.shiro.filter;

import com.vain.manager.constant.SysConstants;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by vain on 2017/9/20.
 * referer拦截
 */
public class UrlFilter extends UserFilter {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * UserFilter 当前用户必须登录或者是通过之前的登录时的remember me可以获得principalCollection，也就是必须知道用户是谁才可以。否则返回false
     * 通过获取请求的referer来保证 除了shiro配置了anno的路径或文件 其余的都需要保证是项目内部跳转 并且通过UserFilter的验证
     * 只拦截了页面跳转
     * @see org.apache.shiro.web.filter.authc.UserFilter
     * @see org.apache.shiro.web.filter.authc.FormAuthenticationFilter
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        String referer = WebUtils.toHttp(request).getHeader("Referer");
        logger.info("referer = " + referer);
        return (referer != null && referer.contains(SysConstants.RefererConstant)) && (super.isAccessAllowed(request, response, mappedValue));
    }

    /**
     * 返回403码
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse res = WebUtils.toHttp(response);
        res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }

    /**
     * 从定向登录页面
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        String loginUrl = getLoginUrl();//获取没设拦截的登录页面地址
        String requestUrl = httpRequest.getRequestURL().toString();
        WebUtils.issueRedirect(request, response,
                (loginUrl.indexOf("?") > 0 ? "&auth_backurl=" : "?auth_backurl=") + requestUrl);
    }
}
