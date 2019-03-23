package com.yr.depot.service;

import com.yr.entitys.bo.departmentBo.Departmentbo;
import com.yr.entitys.page.Page;
import com.yr.entitys.depot.WareType;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import java.util.List;

public interface WareTypeService {
    /**
     * 根据id来获取数据
     *
     * @param id
     * @return
     */
    public WareType getWareType(Integer id);

    /**
     * 根据waretype 来给商品类型表添加数据
     *
     * @param wareType
     * @return
     */
    public boolean add(WareType wareType);

    /**
     * 根据id来给商品类型表删除数据（批量）
     *
     * @param id
     * @return
     */
    public boolean delete(Integer[] id);

    /**
     * 根据wareType来给商品类型表修改数据
     *
     * @param wareType
     * @return
     */
    public boolean update(WareType wareType);

    /**
     * 根据wareType来查询数据
     *
     * @param wareType
     * @return
     */
    public String query(Page<WareType> wareType);

    /**
     * 获取商品的list集合
     */
    public List<WareType> getWareType();

    /**
     * 用于判断编号是否已存在
     */
    public boolean query(WareType wareType);

}
