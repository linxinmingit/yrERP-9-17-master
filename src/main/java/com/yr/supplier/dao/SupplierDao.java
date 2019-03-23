package com.yr.supplier.dao;

import com.yr.entitys.bo.depotBo.Depotbo;
import com.yr.entitys.bo.supplierBO.SupplierBo;
import com.yr.entitys.depot.Depot;
import com.yr.entitys.page.Page;
import com.yr.entitys.supplier.Supplier;

import java.util.List;
import java.util.Map;

public interface SupplierDao {
    /**
     * 为供应商表添加数据
     * @param supplierBo
     */
    void add(SupplierBo supplierBo);

    /**
     * 根据id删除供应商数 据
     * @param id
     */
    void delete(Integer id);

    /**
     * 根据对象修改供应商数据
     * @param
     */
    void update(SupplierBo supplierBo);

    /**
     * 查询总数（模糊查询总数）
     * @param page
     * @return
     */
    Long getCount(Page<SupplierBo>page);

    /**
     * 根据id查询供应商数据
     * @param id
     * @return
     */
    Supplier getById(Integer id);

    /**
     * 根据编号查询供应商的对象
     */
    Supplier getByCode(String code);
    /**
     * 分页查询供应商
     * @param page
     * @return
     */
    List<SupplierBo> query(Page<SupplierBo> page);

    /**
     * 将供应商数据list集合封装到map集合中去
     * @return
     */
    Map<String,Object> querySuppliers();


}
