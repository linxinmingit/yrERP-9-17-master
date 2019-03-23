package com.yr.supplier.dao;

import com.yr.entitys.bo.supplierBO.SupplierWareBo;
import com.yr.entitys.page.Page;
import com.yr.entitys.supplier.SupplierWares;

import java.util.List;

/**
 * 供应商品dao层的接口
 */
public interface SupplierWareDao {
    /**
     * 根据sw给供应商品表添加数据
     *
     * @param sw
     * @return
     */
     public void add(SupplierWareBo sw);

    /**
     * 根据id来给供应商品表删除数据
     *
     * @param id
     * @return
     */
    public void delete(Integer id);

    /**
     * 根据sw来给供应商品表修改数据
     *
     * @param sw
     * @return
     */
   public void update(SupplierWareBo sw);

    /**
     * 根据page中的信息去供应商品表中查询数据
     *
     * @param page
     * @return
     */
    public List<SupplierWareBo> query(Page<SupplierWareBo> page);

    /**
     * 根据id到供应商品表中获取对应的数据
     * @param id
     * @return
     */
    public SupplierWares getById(Integer id);

    /**
     * 根据page中的信息去供应商品表中查询数目
     *
     * @param page
     * @return
     */
    public Long getCount(Page<SupplierWareBo> page);

    /**
     * 根据商品编号去查供应商品
     * @param code
     * @return
     */
    public SupplierWares getSuppLierWareByCode(String code);
    /**
     * 供应商查询所有数据存放list中
     */
    List<SupplierWares>  queryList();

    /**
     * 根据供应商商品名称返回供应商商品编号
     * @param name
     * @return String
     */
    String getSupplierWareCode(String name);
}
