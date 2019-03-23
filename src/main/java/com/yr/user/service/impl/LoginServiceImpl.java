package com.yr.user.service.impl;

import com.yr.entitys.user.User;
import com.yr.user.dao.LoginDao;
import com.yr.user.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("loginServiceImpl")
public class LoginServiceImpl implements LoginService {

    @Qualifier("loginDaoImpl")
    @Autowired
    private LoginDao loginDao;

    /**
     * 查询用户 账号是否存在
     * @param loginUser
     * @return
     */
    @Override
    public List<User> queryLoginUserName(User loginUser) {
        return loginDao.queryLoginUserName(loginUser);
    }

    /**
     * 查询登录用户密码 账号是否正确
     * @param loginUser
     * @return
     */
    @Override
    public User queryLoginUser(User loginUser) {
        return loginDao.queryLoginUser(loginUser);
    }
}

