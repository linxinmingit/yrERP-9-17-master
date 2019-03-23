package com.yr.order.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.yr.entitys.order.SaleOrder;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.yr.entitys.bo.orderBO.SaleOrderBO;
import com.yr.entitys.page.Page;
import com.yr.order.dao.SaleDao;
@Repository("saleDaoImpl")
public class SaleDaoImpl implements SaleDao {

    //获取到和当前事务关联的 EntityManager对象,通过 @PersistenceContext 注解来标记成员变量
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 查询总条数
     * @param page
     * @return
     */
    @Override
    public Long getCount(Page<SaleOrderBO> page) {
        String jpql="select count(*) from SaleOrder s where 1=1";
        if(!StringUtils.isEmpty(page.getT().getSaleOrder().getCode())){//判断是否为空
            jpql +="and s.code like :code";//模糊查询编号
        }
        if (!StringUtils.isEmpty(page.getT().getSaleOrder().getCustomerBuy())){
            jpql +="and s.customerBuy like :customer_buy";//模糊查询购买客户
        }
        if(page.getT().getSaleOrder().getStates() != null && !page.getT().getSaleOrder().getStates().equals(""))
        {
            jpql+= "and s.states = :states";
        }
        Query query =entityManager.createQuery(jpql);
        if (!StringUtils.isEmpty(page.getT().getSaleOrder().getCode())){
            query.setParameter("code","%"+page.getT().getSaleOrder().getCode()+"%");
        }
        if (!StringUtils.isEmpty(page.getT().getSaleOrder().getCustomerBuy())){
            query.setParameter("customer_buy","%"+page.getT().getSaleOrder().getCustomerBuy()+"%");
        }
        if (page.getT().getSaleOrder().getStates() != null && !page.getT().getSaleOrder().getStates().equals(""))
        {
            query.setParameter("states",page.getT().getSaleOrder().getStates());
        }
        Long count = (Long) query.getSingleResult();
        return count;//将long转为int
    }

    /**
     * 以分页的形式查询sale表的数据
     * @param page
     * @return
     */
    @Override
    public List<SaleOrderBO> query(Page<SaleOrderBO> page) {
        String jpql = "select s from SaleOrder s where 1=1";
        if (!StringUtils.isEmpty(page.getT().getSaleOrder().getCode())){//判断是否为null
            jpql +="and s.code like :code";
        }
        if (!StringUtils.isEmpty(page.getT().getSaleOrder().getCustomerBuy())){
            jpql +="and s.customerBuy like :customer_buy";
        }
        if(page.getT().getSaleOrder().getStates() != null && !page.getT().getSaleOrder().getStates().equals(""))
        {
            jpql+= "and s.states = :states";
        }

       /* if (page.getSort() == "ASC") {//升序
            jpql +="order by s.id asc";
        }
        if (page.getSort() == "DESC"){//降序
            jpql +="order by s.id desc";
        }*/
        Query query = entityManager.createQuery(jpql);
        if (!StringUtils.isEmpty(page.getT().getSaleOrder().getCode())){
            query.setParameter("code","%"+page.getT().getSaleOrder().getCode()+"%");
        }
        if (!StringUtils.isEmpty(page.getT().getSaleOrder().getCustomerBuy())){
            query.setParameter("customer_buy","%"+page.getT().getSaleOrder().getCustomerBuy()+"%");
        }
        if (page.getT().getSaleOrder().getStates() != null && !page.getT().getSaleOrder().getStates().equals(""))
        {
            query.setParameter("states",page.getT().getSaleOrder().getStates());
        }
        query.setFirstResult(page.getStart()).setMaxResults(page.getPageSize());//查询分页
        List<SaleOrderBO> list = query.getResultList();
        return list;
    }

    /**
     * 根据对象对象添加销售订单信息
     * @param saleOrder
     */
    @Override
    public void add(SaleOrder saleOrder) {
        entityManager.persist(saleOrder);
    }

    /**
     * 根据对象修改销售订单信息
     * @param saleOrder
     */
    @Override
    public void update(SaleOrder saleOrder) {
        entityManager.merge(saleOrder);

    }

    /**
     * 根据id删除销售数据
     * @param id
     */
    @Override
    public void delete(Integer id) {
        SaleOrder saleOrder = entityManager.find(SaleOrder.class,id);
        entityManager.remove(saleOrder);
    }

    /**
     * 根据id查询销售数据
     * @param id
     * @return
     */
    @Override
    public SaleOrder getById(Integer id) {
        SaleOrder saleOrder = entityManager.find(SaleOrder.class,id);
        return saleOrder;
    }
}
