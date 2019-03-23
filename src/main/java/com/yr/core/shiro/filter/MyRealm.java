package com.yr.core.shiro.filter;

import com.yr.core.redis.JedisManager;
import com.yr.entitys.user.Permission;
import com.yr.entitys.user.User;
import com.yr.user.service.LoginService;
import com.yr.user.service.UserService;
import com.yr.util.Md5Utils;
import com.yr.util.SerializeUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import redis.clients.jedis.Jedis;

import java.util.List;

public class MyRealm extends AuthorizingRealm {
    @Autowired
    @Qualifier("loginServiceImpl")
    private LoginService loginService;
    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;
    @Autowired
    private JedisManager jedisManager;

    /**
     * 授权方法saveRequestAndRedirectToLogin
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Jedis jedis = jedisManager.getJedis();//获得redis对象
        byte[] rolesByte = jedis.get("roles".getBytes());//获得角色集合
        List<String> roles = (List<String>) SerializeUtil.deserialize(rolesByte);//将序列化后的byte数组转成对象
        info.addRoles(roles);

        byte[] permissionsByte = jedis.get("permissions".getBytes());//获得权限集合
        List<Permission> permissions = (List<Permission>) SerializeUtil.deserialize(permissionsByte);//将序列化后的byte数组转成对象
        for (Permission permission :permissions) {
            info.addStringPermission(permission.getUrl() + "/" + permission.getMethod());//将名字与方法名重合
        }
        jedisManager.returnResource(jedis,true);//关闭redis连接
        return info;
    }


    /**
     * 认证账号密码
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        Object principal = token.getPrincipal();//获得账号
        //认证信息: 从数据库中查询出来的信息. 密码的比对交给 shiro 去进行比较
        User user = userService.getByName(principal.toString());//根据账号获取到对象
        String credentials = user.getPassword();//获得密码

        //设置盐值:
        ByteSource credentialsSalt = new Md5Hash(Md5Utils.source);

        //当前 Realm 的 name
        String realmName = getName();
        SimpleAuthenticationInfo info =
                new SimpleAuthenticationInfo(principal, credentials,
                        credentialsSalt, realmName);// 参数: 用户名,密码,加密的盐值对象,该类的路径	将正确用户名密码传入,由shiro进行比较验证
        return info;
    }

    //设置md5加密
    public void setCredentialMatcher(){
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("MD5");//使用md5算法加密
        credentialsMatcher.setHashIterations(Md5Utils.hashIterations);//设置1024次迭代加密
        setCredentialsMatcher(credentialsMatcher);//应用
    }

    /**
     * 清除用户缓存
     */
    public void clearAuthz(){
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }

}
