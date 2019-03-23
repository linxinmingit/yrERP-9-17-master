package com.yr.common.dao;

import com.yr.entitys.bo.user.UserBo;
import com.yr.entitys.page.Page;
import com.yr.entitys.user.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class BaseDao<T> {
    //如何获取到和当前事务关联的 EntityManager 对象呢 ?通过 @PersistenceContext 注解来标记成员变量!
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 添加用户信息
     * @param t
     */
    public void add(T t){
        entityManager.persist(t);
    }

    /**
     * 修改用户信息
     * @param t
     */
    public void update(T t){
        entityManager.merge(t);
    }

    /**
     * 删除用户信息
     * @param id
     */
    public void delete(Integer id){
        Class<T> tClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];//获取T类型的class
        T t = entityManager.find(tClass, id);
        entityManager.remove(t);
    }

    /**
     * 根据id查询用户数据
     * @param id
     * @return T
     */
    public T getById(Integer id){
        Class<T> tClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];//获取T类型的class
        return entityManager.find(tClass, id);
    }
}
