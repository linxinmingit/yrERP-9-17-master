package com.yr.supplier.service.impl;

import com.yr.entitys.bo.supplierBO.SupplierBo;
import com.yr.entitys.bo.supplierBO.SupplierWareBo;
import com.yr.entitys.page.Page;
import com.yr.entitys.supplier.SupplierWares;
import com.yr.supplier.dao.SupplierWareDao;
import com.yr.supplier.service.SupplierWareService;
import com.yr.util.JsonDateValueProcessor;
import com.yr.util.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 *供应商业务逻辑层的实现类
 */
@Service
@Transactional
public class SupplierWareServiceImpl implements SupplierWareService {
    @Autowired
    private SupplierWareDao swd;

    /**
     *根据sw来给供应商品表添加数据
     * @param sw
     * @return true表示添加成功，false表示添加失败
     */
    @Override
    public void add(SupplierWareBo sw) {
        swd.add(sw);
    }

    /**
     *根据id到供应商品表中删除对应的数据
     * @param id
     * @return
     */
    @Override
    public void delete(Integer id) {
        swd.delete(id);
    }

    /**
     *根据sw的数据到供应商品表中修改相应的数据
     * @param sw
     * @return
     */
    @Override
    public void update(SupplierWareBo sw) {
        swd.update(sw);
    }

    /**
     *根据page的数据到供应商品表中查询数据
     * @param page
     * @return
     */
    @Override
    public String query(Page<SupplierWareBo> page) {
        page.setTotalRecord(swd.getCount(page));
        List<SupplierWareBo> list=swd.query(page);

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        JSONArray jsonArray = JSONArray.fromObject(list,jsonConfig);
        String json = "{\"code\": 0,\"msg\": \"\",\"count\": "+page.getTotalRecord()+",\"data\":"+jsonArray+"}";
        return json;
    }

    /**
     *根据id到供应商品表中查询相应的数据
     * @param id
     * @return
     */
    @Override
    public SupplierWareBo getById(Integer id) {
        SupplierWares supplierWares=swd.getById(id);
        SupplierWareBo supplierWareBo=new SupplierWareBo();
        supplierWareBo.setSupplierWare(supplierWares);
         return supplierWareBo;
    }

    /**
     * 判断修改数据是否为null
     * @param supplierWares
     * @return
     */
    private boolean isUpdateParamNull(SupplierWares supplierWares) {
        boolean t = false;

        if (StringUtils.isNull(supplierWares.getName())) {
            t = true;
        }
        if (StringUtils.isNull(supplierWares.getAddr())) {
            t = true;
        }
        if (StringUtils.isNull(supplierWares.getBrand())) {
            t = true;
        }
        if (StringUtils.isNull(supplierWares.getCode())) {
            t = true;
        }
        if (StringUtils.isNull(supplierWares.getUpdateEmp())) {
            t = true;
        }
             return t;
    }
    /**
     * 判断添加时的字段是否为null
     * @param supplierWares
     * @return
     */
    private boolean isParamNull(SupplierWares supplierWares) {
        boolean t = false;

        if (StringUtils.isNull(supplierWares.getName())) {
            t = true;
        }
        if (StringUtils.isNull(supplierWares.getAddr())) {
            t = true;
        }
        if (StringUtils.isNull(supplierWares.getBrand())) {
            t = true;
        }
        if (StringUtils.isNull(supplierWares.getCode())) {
            t = true;
        }
        if (StringUtils.isNull(supplierWares.getCreateEmp())) {
            t = true;
        }

        return t;
    }
    /**
     * 根据商品编号去查供应商品
     * @param code
     * @return
     */
    public SupplierWares getSuppLierWareByCode(String code){
       return swd.getSuppLierWareByCode(code);
    };

    @Override
    public List<SupplierWares> queryList() {
        return swd.queryList();
    }

    @Override
    public String getSupplierWareCode(String name) {
        return swd.getSupplierWareCode(name);
    }
}
