package com.yr.depot.service.impl;

import com.yr.depot.service.WareService;
import com.yr.depot.service.WareTypeService;
import com.yr.entitys.bo.depotBo.WareBo;
import com.yr.entitys.depot.WareType;
import com.yr.entitys.page.Page;
import com.yr.entitys.depot.Ware;
import com.yr.util.JsonDateValueProcessor;
import com.yr.util.StringUtils;
import com.yr.depot.dao.WareDao;
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
 * 业务逻辑实现类
 */
@Service
@Transactional
public class WareServiceImpl implements WareService {
    @Autowired
    private WareDao wd;

    /**
     * 根据id来查询商品数据
     *
     * @param id
     * @return ware 商品
     */
    @Override
    public Ware getWare(Integer id) {
        return wd.getWare(id);
    }

    /**
     * 根据前台传入WareSearchBo的数据来查询数据
     *
     * @param ware
     * @return 返回wareSearchBo
     */
    @Override
    public String query(Page<WareBo> ware) {
        ware.setTotalRecord(wd.getCount(ware));
        List<Ware> wareList = wd.query(ware);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        JSONArray jsonArray = JSONArray.fromObject(wareList, jsonConfig);
        String json = "{\"code\": 0,\"msg\": \"\",\"count\": " + ware.getTotalRecord() + ",\"data\":" + jsonArray + "}";
        return json;
    }

    /**
     * 判断添加商品时是否为空
     *
     * @param ware 商品
     * @return true 成功，false 失败
     */
    @Override
    public boolean add(Ware ware) {
        if (isParamNull(ware)) {
            return false;
        } else {

            wd.add(ware);
            return true;
        }
    }

    /**
     * 根据id来删除数据
     *
     * @param id
     * @return true表示删除成功，false表示修改失败
     */
    @Override
    public boolean delete(Integer[] id) {
        return wd.delete(id);
    }

    /**
     * 商品修改数据
     *
     * @param ware
     * @return true表示修改成功，false表示修改失败
     */
    @Override
    public boolean update(Ware ware) {
        if (isUpdateParamNull(ware)) {
            return false;
        } else {
            Date date = new Date();
            //设置要获取到什么样的时间SimpleDateFormat sdf = newSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //获取String类型的时间String createdate = sdf.format(date);
            String udpateDate = sdf.format(date);
            ware.setUpdateTime(udpateDate);
            wd.update(ware);
            return true;
        }
    }

    /**
     * 判断修改数据是否为null
     *
     * @param ware
     * @return
     */
    private boolean isUpdateParamNull(Ware ware) {
        boolean t = false;

        if (StringUtils.isNull(ware.getName())) {
            t = true;
        }
        if (StringUtils.isNull(ware.getAddr())) {
            t = true;
        }
        if (StringUtils.isNull(ware.getBarCode())) {
            t = true;
        }
        if (StringUtils.isNull(ware.getBrand())) {
            t = true;
        }
        if (StringUtils.isNull(ware.getCode())) {
            t = true;
        }
        if (StringUtils.isNull(ware.getUpdateEmp())) {
            t = true;
        }
        if (StringUtils.isNull(ware.getInUnitPrice())) {
            t = true;
        }
        if (StringUtils.isNull(ware.getOutUnitPrice())) {
            t = true;
        }
        return t;
    }

    /**
     * 判断添加时的字段是否为null
     *
     * @param ware
     * @return
     */
    private boolean isParamNull(Ware ware) {
        boolean t = false;

        if (StringUtils.isNull(ware.getName())) {
            t = true;
        }
        if (StringUtils.isNull(ware.getAddr())) {
            t = true;
        }
        if (StringUtils.isNull(ware.getBarCode())) {
            t = true;
        }
        if (StringUtils.isNull(ware.getBrand())) {
            t = true;
        }
        if (StringUtils.isNull(ware.getCode())) {
            t = true;
        }
        if (StringUtils.isNull(ware.getCreateEmp())) {
            t = true;
        }
        if (StringUtils.isNull(ware.getInUnitPrice())) {
            t = true;
        }
        if (StringUtils.isNull(ware.getOutUnitPrice())) {
            t = true;
        }
        return t;
    }

    @Override
    public List<Ware> getWare() {
        List<Ware> list = wd.getWare();
        List<Ware> lists = new ArrayList<>();
        for (Ware ware : list) {
            lists.add(ware);
        }
        Ware wares = new Ware();
        wares.setType("无");
        lists.add(wares);
        return lists;
    }

    @Override
    public boolean getWareByCode(Ware ware) {
        Long num = wd.getWareByCode(ware);
        if (num > 0) {
            return true;
        } else {
            return false;
        }
    }
}
