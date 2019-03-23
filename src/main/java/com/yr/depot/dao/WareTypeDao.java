package com.yr.depot.dao;

import com.yr.entitys.page.Page;
import com.yr.entitys.depot.WareType;

import java.util.List;

/**
 * 商品类型接口
 */
public interface WareTypeDao {
    /**
     * 根据id来查询商品类型数据
     *
     * @param id
     * @return
     */
    public WareType getWareType(Integer id);

    /**
     * 根据waretype来添加数据
     *
     * @param wareType
     * @return
     */
    public boolean addWareType(WareType wareType);

    /**
     * 根据id来删除商品类型
     *
     * @param id
     * @return
     */
    public boolean deleteWareType(Integer[] id);

    /**
     * 根据wareType来修改数据
     *
     * @param wareType
     * @return
     */
    public boolean updateWareType(WareType wareType);

    /**
     * 根据wareType来查询数据
     *
     * @param wareType
     * @return
     */
    public List<WareType> query(Page<WareType> wareType);

    /**
     * 根据wareType来查询商品类型
     *
     * @param
     * @return
     */
    public List<WareType> query();

    /**
     * 根据wareType来查询数据的数目
     *
     * @param wareType
     * @return
     */
    Long getCount(Page<WareType> wareType);

    /**
     * 用来判断商品类型编号是否已存在
     */
    public Long query(WareType wareType);
}
