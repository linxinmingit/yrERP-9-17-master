package com.yr.user.service;

import com.yr.entitys.user.User;

import java.util.List;

public interface LoginService {

    /**
     * 验证登录是否存在
     * @param loginUser
     * @return
     */
    List<User> queryLoginUserName(User loginUser);

    /**
     *  验证登录用户 账号跟密码是否正确
     */
    User queryLoginUser(User loginUser);
}
