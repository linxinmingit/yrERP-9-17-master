package com.yr.supplier.service.impl;

import com.yr.depot.dao.DepotDao;
import com.yr.entitys.bo.depotBo.Depotbo;
import com.yr.entitys.bo.supplierBO.SupplierBo;
import com.yr.entitys.page.Page;
import com.yr.entitys.supplier.Supplier;
import com.yr.supplier.dao.SupplierDao;
import com.yr.supplier.service.SupplierService;
import com.yr.util.JsonDateValueProcessor;
import com.yr.util.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    private SupplierDao dao;
    /**
     * 分页搜索查询供应商数据
     * @param page
     * @return
     */
    @Override
    public String query(Page<SupplierBo> page) {
        page.setTotalRecord(dao.getCount(page));
        List<SupplierBo> list=dao.query(page);
       // page.setPageData(list);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        JSONArray jsonArray = JSONArray.fromObject(list,jsonConfig);
        String json = "{\"code\": 0,\"msg\": \"\",\"count\": "+page.getTotalRecord()+",\"data\":"+jsonArray+"}";
        return json;
    }
    /**
     * 根据id查询供应商表
     * @param id
     * @return
     */
    @Override
    public SupplierBo getById(Integer id) {
        Supplier supplier = dao.getById(id);
        SupplierBo supplierBo = new SupplierBo();
        supplierBo.setSupplier(supplier);
        return supplierBo;
    }

    /**
     * 根据编号查询供应商对象
     * @param code
     * @return
     */
    @Override
    public Supplier getByCode(String code){
        Supplier supplier=dao.getByCode(code);
        return supplier;
    }

    /**
     * 添加供应商数据
     * @param supplierBo
     */
    @Override
    public void add(SupplierBo supplierBo) {
        dao.add(supplierBo);
    }
    /**
     * 修改供应商数据
     * @param supplierBo
     */
    @Override
    public void update(SupplierBo supplierBo) {
        dao.update(supplierBo);
    }
    /**
     * 删除供应商数据
     * @param id
     */
    @Override
    public void delete(Integer id) {
        dao.delete(id);
    }
    /**
     * 判断添加供应商数据是否为null
     * @param supplier
     * @return
     */
    @Override
    public boolean isNullAdd(Supplier supplier) {
        if(StringUtils.isNull(supplier.getCode())){
            return false;
        }if (StringUtils.isNull(supplier.getName())) {
            return false;
        }if (StringUtils.isNull(supplier.getPhoneNumber())){
            return false;
        }
        if(StringUtils.isNull(supplier.getAddr())){
            return false;
        }if (StringUtils.isNull(supplier.getCreateEmp())){
            return false;
        }
        return true;
    }
    /**
     * 判断供应商修改数据是否为null
     * @param supplier
     * @return
     */
    @Override
    public boolean isNullUpdate(Supplier supplier) {
        if (StringUtils.isNull(supplier.getName())) {
            return false;
        }if(StringUtils.isNull(supplier.getAddr())){
            return false;
        }if (StringUtils.isNull(supplier.getPhoneNumber())){
            return false;
        }if (StringUtils.isNull(supplier.getUpdateEmp())){
            return false;
        }
        return true;
    }
    /**
     * 判断是否是手机号
     *电信号 133、149、153、173、177、180、181、189、199
     * 联通号 130、131、132、145、155、156、166、175、176、185、186
     * 移动号 134(0-8)、135、136、137、138、139、147、150、151、152、157、158、159、178、182、183、184、187、188、198
     * @param tell
     * @return
     */
    public boolean isTell(String tell) {
        String regex = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(tell);
        if(!m.matches()){
            return true;
        }
        return false;
    }

    /**
     * 将供应商数据list集合封装到map集合中去
     * @return
     */
    @Override
    public Map<String, Object> querySuppliers() {
        return dao.querySuppliers();
    }
}
