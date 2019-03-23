package com.yr.supplier.service;

import com.yr.entitys.bo.supplierBO.SupplierBo;
import com.yr.entitys.page.Page;
import com.yr.entitys.supplier.Supplier;

import java.util.Map;

/**
 *供应商模块
 */
public interface SupplierService {

    /**
     * 分页搜索查询供应商数据
     * @param page
     * @return
     */
    String  query(Page<SupplierBo> page);

    /**
     * 根据id查询供应商表
     * @param id
     * @return
     */
    SupplierBo getById(Integer id);

    /**
     * 根据编号查询供应商的对象
     */
    Supplier getByCode(String code);

    /**
     * 添加供应商数据
     * @param supplierBo
     */
    void add(SupplierBo supplierBo);

    /**
     * 修改供应商数据
     * @param supplierBo
     */

    void update(SupplierBo supplierBo);
    /**
     * 删除供应商数据
     * @param id
     */
    void delete(Integer id);

    /**
     * 判断添加供应商数据是否为null
     * @param supplier
     * @return
     */
    boolean isNullAdd(Supplier supplier);

    /**
     * 判断供应商修改数据是否为null
     * @param supplier
     * @return
     */
    boolean isNullUpdate(Supplier supplier);

    /**
     * 判断电话格式是否正确
     * @param tell
     * @return
     */
    boolean isTell(String tell);

    /**
     * 将供应商数据list集合封装到map集合中去
     * @return
     */
    Map<String,Object> querySuppliers();

}
