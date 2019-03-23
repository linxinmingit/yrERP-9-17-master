package com.yr.user.controller;

import com.yr.core.redis.JedisManager;
import com.yr.entitys.user.Permission;
import com.yr.entitys.user.Role;
import com.yr.entitys.user.User;
import com.yr.user.service.LoginService;
import com.yr.user.service.UserService;
import com.yr.util.SerializeUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Set;


//SessionAttributes里的参数 names 这是一个字符串数组 里面应写需要存储到session中数据的名称
@SessionAttributes(value = {"user"}, types = {Integer.class})//这里指定一下 Session 才能获得指定的数据
@RequestMapping(value = "/u_user")
@Controller
public class LoginController {

    @Qualifier("loginServiceImpl")
    @Autowired
    private LoginService loginService;
    @Autowired
    private JedisManager jedisManager;
    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    @RequestMapping(value = "/userTables", method = RequestMethod.GET)//跳转登录页面
    public String logins() {
        return "index";
    }

    /*
       验证码*/
    @RequestMapping(value = "/userTable/getVerifyCode", method = RequestMethod.GET)
    public void Post(HttpServletResponse resp, HttpServletRequest req) throws Exception {
        // 设置响应的类型格式为图片格式
        resp.setContentType("image/jpeg");
        // 禁止图像缓存。
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setDateHeader("Expires", 0);

        // 自定义宽、高、字数和干扰线的条数
        IdentifyCode code = new IdentifyCode(100, 30, 6, 10);
        // 存入session
        HttpSession session = req.getSession();
        session.setAttribute("verifyCode", code.getCode());
        // 响应图片
        ServletOutputStream out = resp.getOutputStream();
        code.write(out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }

    @RequestMapping(value = "/userTable/login", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String gotoIndex(User loginUser, String loginVerifyCode, HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
        String str = "";
        String verifyCode = (String) request.getSession().getAttribute("verifyCode");// 获取正确的验证码


        // if(loginVerifyCode != null && !"".equals(loginVerifyCode)){
        if (!loginVerifyCode.isEmpty()) {//判断验证码不等于空
            if ((loginVerifyCode.trim()).equalsIgnoreCase(verifyCode.trim())) {//先去掉验证码前后空格 在比较验证码是否正确
                List<User> loginUserNameList = loginService.queryLoginUserName(loginUser);//判断用户 账号是否存在
                if (!loginUserNameList.isEmpty() && loginUserNameList.size() >= 1) {//判断用户不能等于空 长度要大于1
                    Subject subject = SecurityUtils.getSubject();//获得subject对象
                    UsernamePasswordToken token = new UsernamePasswordToken(loginUser.getName(), loginUser.getPassword());//根据账号密码获得token令牌
                    try {
                        subject.login(token);//进入权限的认证
                        // 登录验证通过，把对象存进session
                        User user = userService.getByName(loginUser.getName());
                        request.getSession().setAttribute("user", user);// 获取session对象并赋值
                        //将权限存入redis
                        List<Permission> permissions = userService.getPermissions(user.getId());
                            //将对象序列化后放入redis中
                        Jedis jedis = jedisManager.getJedis();
                        jedis.set("permissions".getBytes(), SerializeUtil.serialize(permissions));
                        //将角色存入redis
                        List<String> roles = userService.getRoles(user.getId());
                        jedis.set("roles".getBytes(), SerializeUtil.serialize(roles));

                        jedisManager.returnResource(jedis,true);//关闭redis连接
                        str = "{\"code\":1,\"msg\":\"登录成功\"}";// 账号登录成功
                        //Cookie cookie = new Cookie("name", URLEncoder.encode( user.getName(),"UTF-8"));//转码
                        Cookie cookie = new Cookie("name", new String(user.getName().getBytes("iso-8859-1"), "utf-8"));//转码
                        //Cookie cookie1 = new Cookie("password",URLEncoder.encode(user.getPassword(),"UTF-8"));
                        Cookie cookie1 = new Cookie("password",new String(user.getPassword().getBytes("iso-8859-1"), "utf-8"));
                        cookie.setMaxAge(60*60);
                        cookie1.setMaxAge(60*60);
                        /*cookie.setPath("/");
                        cookie1.setPath("/");*/
                        response.addCookie(cookie);
                        response.addCookie(cookie1);
                        /*URLDecoder.decode(user.getName(),"UTF-8");//取码
                        URLDecoder.decode(user.getPassword(),"UTF-8");*/
                    } catch (Exception ae) {
                        ae.printStackTrace();
                        System.out.println("登陆失败: " + ae.getMessage());
                        str = "{\"code\":4,\"msg\":\"账号或密码错误\"}";
                    }
                }else{
                    str = "{\"code\":4,\"msg\":\"账号无法登录\"}";//账号不存在
                }
            }else {
                str = "{\"code\":5,\"msg\":\"验证码错误\"}";// 验证码错误

            }
        } else {
            str = "{\"code\":6,\"msg\":\"请输入验证码\"}";// 验证码是空的
        }
        return str;

    }

    /**
     * 跳转没有权限的页面
     * @return String
     */
    @RequestMapping("/userTable/unauthorized")
    public String unauthorized(){
        return "unauthorized";
    }

    /**
     * 跳转没有登陆的页面
     * @return String
     */
    @RequestMapping("/unlogin")
    public String unlongin(){
        return "unlogin";
    }
}
