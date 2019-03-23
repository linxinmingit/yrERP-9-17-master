package com.yr.order.service;

import com.yr.entitys.bo.orderBO.PurchaseOrderBo;
import com.yr.entitys.order.PurchaseOrder;
import com.yr.entitys.page.Page;

import java.util.List;

public interface PurchaseOrderService {
    /**
     * 分页查询
     * @param page
     * @return
     */
    //Page<PurchaseOrder> query(Page<purchaseOrderBO> page);
     String query(Page<PurchaseOrderBo> page);

    /**
     * 查询数据库，并以List集合的方式返回回来
     * @return
     */
    List<PurchaseOrder> queryForLists();

    /**
     * 根据采购表id 查询数据
     * @param id
     * @return
     */
    PurchaseOrder getRequisitionById(Integer id);

    /**
     * 查询数据库表总条数
     * @param requisitionDao
     * @return
     */
    //Integer getCount(RequisitionDao requisitionDao);

    /**
     * 添加
     * @param purchaseOrder
     */
    void  add(PurchaseOrder purchaseOrder);

    /**
     * 修改
     * @param purchaseOrder
     */
    void  update(PurchaseOrder purchaseOrder);

    /**
     * 删除
     * @param id
     */
    void  delete(Integer id);

    /**
     * 根据id批量删除
     * @param ids
     */
    void deleteBatch(Integer[] ids);
}
