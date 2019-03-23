package com.yr.supplier.service;

import com.yr.entitys.bo.supplierBO.SuppWareTypeBo;
import com.yr.entitys.depot.WareType;
import com.yr.entitys.page.Page;
import com.yr.entitys.supplier.SuppWareType;

import java.util.List;

/**
 * 供应商品类型的业务逻辑层的接口
 */
public interface SuppWareTypeService {
    /**
     * 根据sw给供应商品类别表添加数据
     *
     * @param sw
     * @return
     */
    public boolean add(SuppWareType sw);

    /**
     * 根据id来给供应商品类别表删除数据
     *
     * @param id
     * @return
     */
    public boolean delete(Integer[] id);

    /**
     * 根据sw来给供应商品类别表修改数据
     *
     * @param sw
     * @return
     */
    public boolean update(SuppWareType sw);

    /**
     * 根据page中的信息去供应商品类别表中查询数据
     *
     * @param page
     * @return
     */
    public String query(Page<SuppWareTypeBo> page);

    /**
     * 根据id到供应商品类别表中获取对应的数据
     *
     * @param id
     * @return
     */
    public SuppWareType getSuppWareType(Integer id);

    /**
     * 获取商品的list集合
     */
    public List<SuppWareType> getSuppWareType();
}
