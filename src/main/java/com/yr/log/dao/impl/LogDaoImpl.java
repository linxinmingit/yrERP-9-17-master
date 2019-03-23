package com.yr.log.dao.impl;

import com.yr.entitys.Log.Log;
import com.yr.entitys.bo.LogBO.LogBo;
import com.yr.entitys.page.Page;
import com.yr.log.dao.LogDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class LogDaoImpl implements LogDao {

    @PersistenceContext
    private EntityManager entityManager;
    /**
     * 以分页的形式查出log表的数据
     * @param page
     * @return
     */
    @Override
    public List<LogBo> query(Page<LogBo> page) {
        String jpql = "select g from Log g where 1 = 1 ";
        //查询分页
        List<LogBo> logBoList = entityManager.createQuery(jpql).setFirstResult(page.getStart()).setMaxResults(page.getPageSize()).getResultList();
        return logBoList;
    }

    /**
     * 查询日志数据条数
     * @param page
     * @return
     */
    @Override
    public Long getCount(Page<LogBo> page) {
        String jpql = "select  count(*) from Log g where 1 = 1 ";
        Long result = (Long) entityManager.createQuery(jpql).getSingleResult();
        return result;
    }

    /**
     * 添加日志记录
     * @param log
     */
     @Override
    public void addLog(Log log) {
         //添加日志数据
        entityManager.persist(log);

        /*String jpql="insert into log(count,fieldNewValue,fieldOldValue,modular,table,type,createTime,createEmp,updateTime,updateEmp) " +
                "value(?,?,?,?,?,?,?,?,?,?)";
        entityManager.createQuery(jpql).setParameter(1,log.getContent())
                .setParameter(2,log.getFieldNewValue())
                .setParameter(3,log.getFieldOldValue())
                .setParameter(4,log.getModular())
                .setParameter(5,log.getTable())
                .setParameter(6,log.getType())
                .setParameter(7,log.getCreateTime())
                .setParameter(8,log.getCreateEmp())
                .setParameter(9,log.getUpdateTime())
                .setParameter(10,log.getUpdateEmp())
                .executeUpdate();*/
    }
}
