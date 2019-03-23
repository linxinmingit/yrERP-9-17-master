package com.yr.order.dao;

import java.util.List;

import com.yr.entitys.bo.orderBO.SaleOrderBO;
import com.yr.entitys.order.SaleOrder;
import com.yr.entitys.page.Page;

public interface SaleExportDao {

    int addExcel(List<SaleOrder> saleOrderList);

	 /**
     * 查询数据库，并以List集合的方式返回回来
     * @return
     */
    List<SaleOrder> queryForList(Page<SaleOrderBO> page);

}
