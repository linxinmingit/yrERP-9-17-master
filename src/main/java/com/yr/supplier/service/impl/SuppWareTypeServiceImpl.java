package com.yr.supplier.service.impl;

import com.yr.entitys.bo.supplierBO.SuppWareTypeBo;
import com.yr.entitys.depot.WareType;
import com.yr.entitys.page.Page;
import com.yr.entitys.supplier.SuppWareType;
import com.yr.supplier.dao.SuppWareTypeDao;
import com.yr.supplier.service.SuppWareTypeService;
import com.yr.util.JsonDateValueProcessor;
import com.yr.util.StringUtils;
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
 * 供应商品类型业务逻辑层的实现类
 */
@Service
@Transactional
public class SuppWareTypeServiceImpl implements SuppWareTypeService {
    @Autowired
    private SuppWareTypeDao swtd;

    /**
     * 根据sw来给供应商品类型表添加数据
     *
     * @param sw
     * @return true表示添加成功，false表示添加失败
     */
    @Override
    public boolean add(SuppWareType sw) {
        if (isParamNull(sw)) {
            return false;
        } else {
            Date date = new Date();
            //设置要获取到什么样的时间SimpleDateFormat sdf = newSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //获取String类型的时间String createdate = sdf.format(date);
            String udpateDate = sdf.format(date);
            sw.setCreateTime(udpateDate);
            swtd.add(sw);
            return true;
        }
    }

    /**
     * 根据id到供应商品类型表中删除对应的数据
     *
     * @param id
     * @return
     */
    @Override
    public boolean delete(Integer[] id) {
        return swtd.delete(id);
    }

    /**
     * 根据sw的数据到供应商品类型表中修改相应的数据
     *
     * @param sw
     * @return
     */
    @Override
    public boolean update(SuppWareType sw) {
        if (isUpdateParamNull(sw)) {
            return false;
        } else {
            Date date = new Date();
            //设置要获取到什么样的时间SimpleDateFormat sdf = newSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //获取String类型的时间String createdate = sdf.format(date);
            String udpateDate = sdf.format(date);
            sw.setUpdateTime(udpateDate);
            System.out.println(sw);
            swtd.update(sw);
            return true;
        }
    }

    /**
     * 根据page的数据到供应商品类型表中查询数据
     *
     * @param page
     * @return
     */
    @Override
    public String query(Page<SuppWareTypeBo> page) {
        page.setTotalRecord(swtd.getCount(page));
        List<SuppWareTypeBo> suppWareTypeList = swtd.query(page);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        JSONArray jsonArray = JSONArray.fromObject(suppWareTypeList, jsonConfig);
        String json = "{\"code\": 0,\"msg\": \"\",\"count\": " + page.getTotalRecord() + ",\"data\":" + jsonArray + "}";
        return json;
    }

    /**
     * 根据id到供应商品类型表中查询相应的数据
     *
     * @param id
     * @return
     */
    @Override
    public SuppWareType getSuppWareType(Integer id) {
        return swtd.getSupplierWare(id);
    }

    @Override
    public List<SuppWareType> getSuppWareType() {
        List<SuppWareType> list = swtd.query();
        List<SuppWareType> lists = new ArrayList<>();
        for (SuppWareType suppWareType : list) {
            lists.add(suppWareType);
        }
        SuppWareType suppWareTypes = new SuppWareType();
        suppWareTypes.setSupCode("0");
        suppWareTypes.setCode("0");
        suppWareTypes.setName("无");
        lists.add(suppWareTypes);
        return lists;
    }

    /**
     * 判断修改数据是否为null
     *
     * @param suppWareType
     * @return
     */
    private boolean isUpdateParamNull(SuppWareType suppWareType) {
        boolean t = false;

        if (StringUtils.isNull(suppWareType.getName())) {
            t = true;
        }
        if (StringUtils.isNull(suppWareType.getCode())) {
            t = true;
        }
        if (StringUtils.isNull(suppWareType.getUpdateEmp())) {
            t = true;
        }
        if (StringUtils.isNull(suppWareType.getSupCode())) {
            suppWareType.setSupCode("0");
        }
        return t;
    }

    /**
     * 判断添加时的字段是否为null
     *
     * @param suppWareType
     * @return
     */
    private boolean isParamNull(SuppWareType suppWareType) {
        boolean t = false;

        if (StringUtils.isNull(suppWareType.getName())) {
            t = true;
        }
        if (StringUtils.isNull(suppWareType.getCode())) {
            t = true;
        }
        if (StringUtils.isNull(suppWareType.getCreateEmp())) {
            t = true;
        }
        if (StringUtils.isNull(suppWareType.getSupCode())) {
            suppWareType.setSupCode("0");
        }
        return t;
    }
}
