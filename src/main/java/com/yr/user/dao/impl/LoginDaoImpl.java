package com.yr.user.dao.impl;

import com.yr.entitys.user.User;
import com.yr.user.dao.LoginDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository("loginDaoImpl")
public class LoginDaoImpl implements LoginDao {

    @PersistenceContext//注解来标记成员变量
    private EntityManager entityManager;

    /**
     * 根据用户表查询用户是否存在
     * @param loginUser
     * @return
     */
    @Override
    public List<User> queryLoginUserName(User loginUser) {
        String jpql="select u from User u where u.name=?";
        Query query=entityManager.createQuery(jpql).setParameter(1,loginUser.getName());
        List<User> list = query.getResultList();
        return list;
    }

    /**
     * 查询登录用户密码 账号是否正确
     * @param loginUser
     * @return
     */
    @Override
    public User queryLoginUser(User loginUser) {
        String jpql="select u from User u where u.name=? and u.password=?";
        Query query = entityManager.createQuery(jpql);
        query.setParameter(1,loginUser.getName());
        query.setParameter(2,loginUser.getPassword());
        User user = (User) query.getSingleResult();
        return user;
    }
}
