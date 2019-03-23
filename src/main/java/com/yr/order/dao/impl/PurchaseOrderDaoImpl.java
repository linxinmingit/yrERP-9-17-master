package com.yr.order.dao.impl;

import com.yr.entitys.bo.orderBO.PurchaseOrderBo;
import com.yr.entitys.order.PurchaseOrder;
import com.yr.entitys.page.Page;
import com.yr.order.dao.PurchaseOrderDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.List;

@Repository
public class PurchaseOrderDaoImpl implements PurchaseOrderDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PurchaseOrder> query(Page<PurchaseOrderBo> page) {
        String jpql="select r from PurchaseOrder r where 1=1 ";
        if (page.getT().getPurchaseCode() != null && !page.getT().getPurchaseCode().equals(""))
        {
            jpql+= "and r.code like :purchaseCode ";
        }
        if (page.getT().getPurchaseWareCode() != null && !page.getT().getPurchaseWareCode().equals(""))
        {
            jpql+= "and r.purchaseWareCode like :purchaseWareCode";
        }
        if(page.getT().getPurchaseOrder().getStatus() != null && !page.getT().getPurchaseOrder().getStatus().equals(""))
        {
            jpql+= "and r.status = :status";
        }
        Query query =  entityManager.createQuery(jpql);
        if(page.getT().getPurchaseCode() != null && !page.getT().getPurchaseCode().equals(""))
        {
            query.setParameter("purchaseCode","%"+page.getT().getPurchaseCode()+"%");
        }
        if(page.getT().getPurchaseWareCode() != null && !page.getT().getPurchaseWareCode().equals(""))
        {
            query.setParameter("purchaseWareCode","%"+page.getT().getPurchaseWareCode()+"%");
        }
        if (page.getT().getPurchaseOrder().getStatus() != null && !page.getT().getPurchaseOrder().getStatus().equals(""))
        {
            query.setParameter("status",page.getT().getPurchaseOrder().getStatus());
        }
        //query.setFirstResult((page.getStart()-1) * page.getPageSize()).setMaxResults(page.getPageSize());//查询分页
        query.setFirstResult(page.getStart()).setMaxResults(page.getPageSize());//查询分页
        List<PurchaseOrder> list = query.getResultList();
        return list;
    }

    @Override
    public List<PurchaseOrder> queryForList() {
        String jpql ="select r from PurchaseOrder r";
        List<PurchaseOrder> list =  entityManager.createQuery(jpql).getResultList();
        return list;
    }
    @Override
    public Long getCount(PurchaseOrderBo purchaseOrderBO) {
        String jpql= "select count(*) from PurchaseOrder r where 1=1 ";
        if (purchaseOrderBO.getPurchaseCode() != null && !purchaseOrderBO.getPurchaseCode().equals(""))
        {
            jpql+= "and r.code like :purchaseCode ";
        }
        if (purchaseOrderBO.getPurchaseWareCode() != null && !purchaseOrderBO.getPurchaseWareCode().equals(""))
        {
            jpql+= "and r.purchaseWareCode like :purchaseWareCode";
        }
        if (purchaseOrderBO.getPurchaseOrder().getStatus() !=null && !purchaseOrderBO.getPurchaseOrder().getStatus().equals(""))
        {
            jpql+= "and r.status = :status ";
        }
        Query query =  entityManager.createQuery(jpql);
        if(purchaseOrderBO.getPurchaseCode() != null && !purchaseOrderBO.getPurchaseCode().equals(""))
        {
            query.setParameter("purchaseCode","%"+purchaseOrderBO.getPurchaseCode()+"%");
        }
        if(purchaseOrderBO.getPurchaseWareCode() != null && !purchaseOrderBO.getPurchaseWareCode().equals(""))
        {
            query.setParameter("purchaseWareCode","%"+purchaseOrderBO.getPurchaseWareCode()+"%");
        }
        if (purchaseOrderBO.getPurchaseOrder().getStatus() !=null && !purchaseOrderBO.getPurchaseOrder().getStatus().equals(""))
        {
            query.setParameter("status",purchaseOrderBO.getPurchaseOrder().getStatus());
        }
        Long value = (Long) query.getSingleResult();
        return value;
    }

    @Override
    public PurchaseOrder getRequisitionById(Integer id) {
        PurchaseOrder purchaseOrder = entityManager.find(PurchaseOrder.class,id);
        return purchaseOrder;
    }

    @Override
    public void add(PurchaseOrder purchaseOrder) {
        //添加数据
        entityManager.persist(purchaseOrder);
    }

    @Override
    public void update(PurchaseOrder purchaseOrder) {

        //修改
        entityManager.merge(purchaseOrder);

    }

    @Override
    public void delete(Integer id) {
        PurchaseOrder purchaseOrder =  entityManager.find(PurchaseOrder.class,id);
        entityManager.remove(purchaseOrder);

    }
    /**
     * 根据选中的id,批量删除
     * @param ids
     */
    @Override
    public void deleteBatch(Integer[] ids) {
        List<Integer> list = Arrays.asList(ids);//将数组转成list
        String jpql = "delete from PurchaseOrder u where u.id in(:ids)";
        Query query = entityManager.createQuery(jpql).setParameter("ids",list);
        query.executeUpdate();
    }


}
