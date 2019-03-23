package com.yr.depot.service.impl;

import com.yr.depot.dao.DepotDao;
import com.yr.depot.service.DepotService;
import com.yr.entitys.bo.depotBo.Depotbo;
import com.yr.entitys.depot.Depot;
import com.yr.entitys.page.Page;
import com.yr.util.JsonDateValueProcessor;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("depotServiceImpl")
@Transactional
public class DepotServiceImpl implements DepotService {

    @Qualifier("depotDaoImpl")
    @Autowired
    private DepotDao depotDao;

    /**
     * 分页查询仓库
     * @param page
     * @return
     */
    @Override
    public String query(Page<Depotbo> page) {
        page.setTotalRecord(depotDao.getCount(page));
        List<Depotbo> list=depotDao.query(page);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        JSONArray jsonArray = JSONArray.fromObject(list,jsonConfig);
        String json = "{\"code\": 0,\"msg\": \"\",\"count\": "+page.getTotalRecord()+",\"data\":"+jsonArray+"}";
        return json;
    }
    /**
     * 根据id查询仓库数据
     * @param id
     * @return
     */
    @Override
    public Depot getById(Integer id) {

        return depotDao.getById(id);
    }
    /**
     * 为仓库添加数据
     * @param depot
     */
    @Override
    public void add(Depot depot) {
        depotDao.add(depot);
    }
    /**
     * 根据对象修改仓库数据
     * @param depot
     */
    @Override
    public void update(Depot depot) {
        depotDao.update(depot);
    }

    /**
     * 删除 和批量删除
     * @param id
     */
    public void delete(Integer[] id){
        depotDao.delete(id);
    }
    /**
     * 查询仓库 提供给销售调
     * @param name
     * @return
     */
    public List<Depot> getname(){

        return depotDao.getname();
    }

    /**
     *根据编号 返回仓库对象
     * @param code
     * @return
     */
    @Override
    public Depot getcode(String code) {

        return depotDao.getcode(code);
    }

    /**
     *  将仓库对象集合封装到map中
     * @return  Map<String, Object>
     */
    @Override
    public Map<String, Object> queryDepots() {
        return depotDao.queryDepots();
    }
}
