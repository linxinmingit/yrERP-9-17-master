package com.yr.order.dao;

import com.yr.entitys.bo.orderBO.SaleOrderBO;
import com.yr.entitys.order.SaleOrder;
import com.yr.entitys.page.Page;

import java.util.List;

public interface SaleDao {

    /**
     * 查询总条数
     * @param
     * @return Integer
     */
    Long getCount(Page<SaleOrderBO> page);//@Param指定的是别名

    /**
     * 分页的形式查询user表的数据
     * @return List<SaleOrder>
     */
    List<SaleOrderBO> query(Page<SaleOrderBO> page);

    /**
     * 添加用户信息
     * @param saleOrder
     */
    void add(SaleOrder saleOrder);

    /**
     * 修改用户信息
     * @param saleOrder
     */
    void update(SaleOrder saleOrder);

    /**
     * 删除用户信息
     * @param id
     */
    void delete(Integer id);

    /**
     * 根据id查询用户数据
     * @param id
     * @return SaleOrder
     */
    SaleOrder getById(Integer id);
}