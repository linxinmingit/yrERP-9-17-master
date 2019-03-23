package com.yr.order.dao.impl;

import com.yr.entitys.bo.orderBO.PurchaseOrderBo;
import com.yr.entitys.order.PurchaseOrder;
import com.yr.entitys.page.Page;
import com.yr.order.dao.PurchaseExcelDao;
import com.yr.order.dao.PurchaseOrderDao;
import com.yr.util.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 *采购表excel 导入导出service
 *
 * @author
 * @since
 */
@Repository
public class PurchaseExcelDaoImpl implements PurchaseExcelDao {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 查询数据库所有的用于excel 导出的数据信息
     * @return
     */
    @Override
    public List<PurchaseOrder> queryForList(Page<PurchaseOrderBo> page) {
        String jpql = "select p from PurchaseOrder p where 1 = 1 ";

        if (!StringUtils.isNull(page.getT().getPurchaseWareCode())) {
            jpql += "and p.purchaseWareCode like :purchaseWareCode ";
        }
        if (!StringUtils.isNull(page.getT().getPurchaseCode())) {
            jpql += "and p.code like :code ";
        }
        if (!StringUtils.isNull(page.getT().getPurchaseOrder().getStatus())) {
            jpql += "and p.status = :status ";
        }
        Query query =  entityManager.createQuery(jpql);
        if (!StringUtils.isNull(page.getT().getPurchaseWareCode())) {
            query.setParameter("purchaseWareCode", "%"+page.getT().getPurchaseWareCode()+"%");
        }
        if (!StringUtils.isNull(page.getT().getPurchaseCode())) {
            query.setParameter("code","%"+page.getT().getPurchaseCode()+"%");
        }
        if (!StringUtils.isNull(page.getT().getPurchaseOrder().getStatus())) {
            query.setParameter("status",page.getT().getPurchaseOrder().getStatus());
        }
        List<PurchaseOrder> purchaseOrderList =  query.getResultList();
        return purchaseOrderList;
    }

    /**
     * 将采购excel表中的数据信息，导入到mysql 数据库中
     * @param purchaseOrderList
     * @return
     */
    @Override
    public int addExcel(List<PurchaseOrder> purchaseOrderList) {
        int values = 0 ;
        for (PurchaseOrder purchaseOrder:purchaseOrderList) {
            entityManager.persist(purchaseOrder);
            values ++;
        }
        return values;
    }
}
