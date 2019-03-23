package com.yr.core.shiro.filter;

import com.yr.core.redis.JedisManager;
import com.yr.entitys.user.Permission;
import com.yr.util.SerializeUtil;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PermissionFilter extends AccessControlFilter {
    @Autowired
    private JedisManager jedisManager;

    @Override
    protected boolean isAccessAllowed(ServletRequest request,
                                      ServletResponse response, Object mappedValue) throws Exception {

        //先判断带参数的权限判断
        Subject subject = getSubject(request, response);
        if(null != mappedValue){
            String[] arra = (String[])mappedValue;
            for (String permission : arra) {
                if(subject.isPermitted(permission)){
                    return Boolean.TRUE;
                }
            }
        }
        HttpServletRequest httpRequest = ((HttpServletRequest)request);//获取request
        String method = httpRequest.getMethod();//获取请求的方式
        String uri = httpRequest.getRequestURI();//获取URI
        String basePath = httpRequest.getContextPath();//获取basePath
        if(null != uri && uri.startsWith(basePath)){
            uri = uri.replaceFirst(basePath, "");
        }
        //将redis的序列化后的值拿出来
        Jedis jedis = jedisManager.getJedis();
        byte[] bytes = jedis.get("permissions".getBytes());
        jedisManager.returnResource(jedis,true);//关闭redis连接
        List<Permission> permissions = (List<Permission>) SerializeUtil.deserialize(bytes);
        if(permissions != null){
            for (Permission permission:permissions) {
                String permissionUrl = permission.getUrl();
                String permissionMethod = permission.getMethod();
                String tfMethod = httpRequest.getParameter("_method");
                if (tfMethod != null) {//如果不等于null则表示这个是个put或者delete请求
                    method = tfMethod.toUpperCase();//转成大写
                }
                if (!permissionUrl.contains("*")) {//表示不包含正则
                    if (permissionUrl.equals(uri) && method.equals(permissionMethod)) {
                        return Boolean.TRUE;
                    }
                } else {//表示包含正则
                    //通过正则表达式验证
                    permissionUrl = permissionUrl.replace("*", "\\d.*(,\\d.*)*");//判断是否符合指定的数字
                    Pattern pattern = Pattern.compile(permissionUrl);
                    Matcher matcher = pattern.matcher(uri);
                    if (matcher.matches() && method.equals(permissionMethod)) {//为true结束
                        return Boolean.TRUE;
                    }
                }
            }
        }else{
            if(subject.isPermitted(uri)){
                return Boolean.TRUE;
            }
        }
        if(ShiroFilterUtils.isAjax(request)){//如果是ajax则返回没有权限的信息
            Map<String,String> resultMap = new HashMap<String, String>();
            resultMap.put("login_status", "300");
            resultMap.put("message", "\u5F53\u524D\u7528\u6237\u6CA1\u6709\u767B\u5F55\uFF01");//当前用户没有登录！
            ShiroFilterUtils.out(response, resultMap);
        }
        return Boolean.FALSE;
    }

    /**
     * isAccessAllowed返回false进入这里
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request,
                                     ServletResponse response) throws Exception {
        if(!ShiroFilterUtils.isAjax(request)){
            Subject subject = getSubject(request, response);
            if (null == subject.getPrincipal()) {//表示没有登录，重定向到登录页面
                saveRequest(request);
                WebUtils.issueRedirect(request, response, ShiroFilterUtils.LOGIN_URL);
            } else {
                if(StringUtils.hasText(ShiroFilterUtils.UNAUTHORIZED)) {//如果有未授权页面跳转过去
                    WebUtils.issueRedirect(request, response, ShiroFilterUtils.UNAUTHORIZED);
                } else {//否则返回401未授权状态码
                    WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
            }
        }
        return Boolean.FALSE;
    }
}