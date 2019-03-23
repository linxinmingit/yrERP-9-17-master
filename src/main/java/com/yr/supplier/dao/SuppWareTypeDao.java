package com.yr.supplier.dao;

import com.yr.entitys.bo.supplierBO.SuppWareTypeBo;
import com.yr.entitys.depot.WareType;
import com.yr.entitys.page.Page;
import com.yr.entitys.supplier.SuppWareType;

import java.util.List;

public interface SuppWareTypeDao {
    /**
     * 根据sw给供应商品类型表添加数据
     *
     * @param swt
     * @return
     */
    public boolean add(SuppWareType swt);

    /**
     * 根据id来给供应商品类型表删除数据
     *
     * @param id
     * @return
     */
    public boolean delete(Integer[] id);

    /**
     * 根据sw来给供应商品类型表修改数据
     *
     * @param sw
     * @return
     */
    public boolean update(SuppWareType sw);

    /**
     * 根据page中的信息去供应商品类型表中查询数据
     *
     * @param page
     * @return
     */
    public List<SuppWareTypeBo> query(Page<SuppWareTypeBo> page);

    /**
     * 根据id到供应商品类型表中获取对应的数据
     *
     * @param id
     * @return
     */
    public SuppWareType getSupplierWare(Integer id);

    /**
     * 根据page中的信息去供应商品类型表中查询数目
     *
     * @param page
     * @return
     */
    public Long getCount(Page<SuppWareTypeBo> page);

    /**
     * 根据wareType来查询商品类型
     *
     * @param
     * @return
     */
    public List<SuppWareType> query();
}
