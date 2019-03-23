package com.yr.common.service;

import com.yr.common.dao.BaseDao;
import com.yr.core.redis.JedisManager;
import com.yr.entitys.bo.user.UserBo;
import com.yr.entitys.page.Page;
import com.yr.entitys.user.Permission;
import com.yr.entitys.user.User;
import com.yr.user.dao.UserDao;
import com.yr.util.SerializeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import java.util.List;

@Transactional
public class BaseService<T> {
    @Autowired
    private BaseDao<T> baseDao;

    /**
     * 添加
     * @param t
     */
    @Transactional
    public void add(T t){
        baseDao.add(t);
    }

    /**
     * 修改
     * @param t
     */
    @Transactional
    public void update(T t){
        baseDao.update(t);
    }

    /**
     * 删除
     * @param id
     */
    @Transactional
    public void delete(Integer id){
        baseDao.delete(id);
    }

    /**
     * 根据id查询
     * @param id
     * @return T
     */
    public T getById(Integer id){
        return baseDao.getById(id);
    }

}
