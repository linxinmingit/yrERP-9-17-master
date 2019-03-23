package com.yr.depot.dao;


import com.yr.entitys.bo.depotBo.Depotbo;
import com.yr.entitys.depot.Depot;
import com.yr.entitys.page.Page;

import java.util.List;
import java.util.Map;

public interface DepotDao {
    /**
     * 查询总数（模糊查询总数）
     * @param page
     * @return
     */
    Long getCount(Page<Depotbo> page);

    /**
     * 分页查询用户
     * @param page
     * @return
     */
    List<Depotbo> query(Page<Depotbo> page);

    /**
     * 为仓库添加数据
     * @param depot
     */
    void add(Depot depot);

    /**
     * 根据对象修改仓库数据
     * @param depot
     */
    void update(Depot depot);
    /**
     * 根据id查询仓库数据
     * @param id
     * @return
     */
    Depot getById(Integer id);

    /**
     * 批量删除 提供给销售调
     * @param id
     */
    void delete(Integer[] id);

    /**
     * 查询仓库 提供给销售
     * @param name
     * @return
     */
    List<Depot> getname();

    /**
     *根据编号 返回仓库对象
     * @param code
     * @return
     */
    Depot getcode(String code);

    /**
     * 将仓库对象集合封装到map中
     * @return
     */
    Map<String,Object> queryDepots();
}
