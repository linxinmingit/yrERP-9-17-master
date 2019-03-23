package com.yr.core.shiro.filter;

import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 跳到没有权限的页面
 */
public class LoginFilter extends AccessControlFilter {

    final static Class<LoginFilter> CLASS = LoginFilter.class;
    @Override
    protected boolean isAccessAllowed(ServletRequest request,
                                      ServletResponse response, Object mappedValue) throws Exception {
        return Boolean.FALSE ;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
            throws Exception {
        //保存Request和Response 到登录后的链接
        saveRequestAndRedirectToLogin(request, response);
        return Boolean.FALSE ;
    }
}
