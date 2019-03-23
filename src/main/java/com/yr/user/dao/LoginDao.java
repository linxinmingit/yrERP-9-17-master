package com.yr.user.dao;

import com.yr.entitys.user.User;

import java.util.List;

public interface LoginDao {
    /**
     * 查询用户 账号是否存在
     * @param loginUser
     * @return
     */
    List<User> queryLoginUserName(User loginUser);

    /**
     * 查询用户 用户账号是否正确
     * @param loginUser
     * @return
     */
    User queryLoginUser(User loginUser);
}
