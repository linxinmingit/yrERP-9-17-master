package com.yr.depot.service.impl;

import com.yr.depot.service.WareTypeService;
import com.yr.entitys.bo.departmentBo.Departmentbo;
import com.yr.entitys.department.Department;
import com.yr.entitys.page.Page;
import com.yr.entitys.depot.WareType;
import com.yr.util.JsonDateValueProcessor;
import com.yr.util.StringUtils;
import com.yr.depot.dao.WareTypeDao;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 商品类型的业务逻辑实现接口类
 */
@Service
@Transactional
public class WareTypeServiceImpl implements WareTypeService {
    @Autowired
    private WareTypeDao wt;

    /**
     * 根据id来查询商品类型数据
     *
     * @param id
     * @return wareType 商品类型
     */
    @Override
    public WareType getWareType(Integer id) {
        return wt.getWareType(id);
    }

    /**
     * 根据wareType来给商品类型表添加数据
     *
     * @param wareType
     * @return
     */
    @Override
    public boolean add(WareType wareType) {
        if (isParamNull(wareType)) {
            return false;
        } else {
            wt.addWareType(wareType);
            return true;
        }
    }

    /**
     * 根据id来给商品类型表删除数据(批量)
     *
     * @param id
     * @return
     */
    @Override
    public boolean delete(Integer[] id) {
        return wt.deleteWareType(id);
    }

    /**
     * 根据wareType来给商品类型表修改数据
     *
     * @param wareType
     * @return
     */
    @Override
    public boolean update(WareType wareType) {
        if (isUpdateParamNull(wareType)) {
            return false;
        } else {
            Date date = new Date();
            //设置要获取到什么样的时间SimpleDateFormat sdf = newSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //获取String类型的时间String createdate = sdf.format(date);
            String udpateDate = sdf.format(date);
            wareType.setUpdateTime(udpateDate);
            wt.updateWareType(wareType);
            return true;
        }
    }

    /**
     * 根据wareType来查询商品类型表的数据
     *
     * @param wareType
     * @return
     */
    @Override
    public String query(Page<WareType> wareType) {
        wareType.setTotalRecord(wt.getCount(wareType));
        List<WareType> wareList = wt.query(wareType);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        JSONArray jsonArray = JSONArray.fromObject(wareList, jsonConfig);
        String json = "{\"code\": 0,\"msg\": \"\",\"count\": " + wareType.getTotalRecord() + ",\"data\":" + jsonArray + "}";
        return json;
    }


    /**
     * 判断修改数据是否为null
     *
     * @param ware
     * @return
     */
    private boolean isUpdateParamNull(WareType ware) {
        boolean t = false;

        if (StringUtils.isNull(ware.getName())) {
            t = true;
        }
        if (StringUtils.isNull(ware.getCode())) {
            t = true;
        }
        if (StringUtils.isNull(ware.getUpdateEmp())) {
            t = true;
        }
        if (StringUtils.isNull(ware.getSupCode())) {
            ware.setSupCode("0");
        }

        return t;
    }

    /**
     * 判断添加时的字段是否为null
     *
     * @param ware
     * @return
     */
    private boolean isParamNull(WareType ware) {
        boolean t = false;

        if (StringUtils.isNull(ware.getName())) {
            t = true;
        }
        if (StringUtils.isNull(ware.getCode())) {
            t = true;
        }
        if (StringUtils.isNull(ware.getCreateEmp())) {
            t = true;
        }
        if (StringUtils.isNull(ware.getSupCode())) {
            ware.setSupCode("0");
        }

        return t;
    }

    /**
     * 获取商品的父级类型
     *
     * @return
     */
    @Override
    public List<WareType> getWareType() {
        List<WareType> list = wt.query();
        List<WareType> lists = new ArrayList<>();
        for (WareType wareType : list) {
            lists.add(wareType);
        }
        WareType wareTypes = new WareType();
        wareTypes.setSupCode("0");
        wareTypes.setCode("0");
        wareTypes.setName("无");
        lists.add(wareTypes);
        return lists;
    }

    @Override
    public boolean query(WareType wareType) {
        Long num = wt.query(wareType);
        if (num > 0) {
            return true;
        } else {
            return false;
        }
    }


}
