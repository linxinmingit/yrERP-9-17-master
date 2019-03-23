package com.yr.order.service.impl;

import com.yr.department.service.DepartmentService;
import com.yr.depot.service.DepotService;
import com.yr.entitys.bo.orderBO.PurchaseOrderBo;
import com.yr.entitys.department.Department;
import com.yr.entitys.depot.Depot;
import com.yr.entitys.order.PurchaseOrder;
import com.yr.entitys.page.Page;
import com.yr.entitys.supplier.Supplier;
import com.yr.entitys.supplier.SupplierWares;
import com.yr.entitys.user.User;
import com.yr.order.dao.PurchaseOrderDao;
import com.yr.order.service.PurchaseOrderService;
import com.yr.supplier.service.SupplierService;
import com.yr.supplier.service.SupplierWareService;
import com.yr.user.service.UserService;
import com.yr.util.JsonDateValueProcessor;
import com.yr.util.JsonUtils;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    @Autowired
    private PurchaseOrderDao purchaseOrderDaoImpl;

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userServices;//人员

    @Qualifier("departmentServiceImpl")
    @Autowired
    private DepartmentService departmentServices;//部门

    @Autowired
    @Qualifier("supplierWareServiceImpl")
    private SupplierWareService supplierWareServices;//供应商品

    @Autowired
    @Qualifier("depotServiceImpl")
    private DepotService depotServices;//仓库

    @Autowired
    @Qualifier("supplierServiceImpl")
    private SupplierService supplierServices;//供应商


    @Override
    public String query(Page<PurchaseOrderBo> page) {
        Long count = purchaseOrderDaoImpl.getCount(page.getT());
        //设置总条数
        page.setTotalRecord(count);

        //定义一个空的list<PurchaseOrderBo>集合,用来存放转换好的数据
        List<PurchaseOrderBo> boList = new ArrayList<>();

        //页数据
        List<PurchaseOrder> list = purchaseOrderDaoImpl.query(page);
        for ( PurchaseOrder purchaseOrder : list) {
            PurchaseOrderBo purchaseOrderBo = new PurchaseOrderBo();
            purchaseOrderBo.setPurchaseOrder(purchaseOrder);

            //根据供应商编号获取供应商对象
            Supplier supplier = supplierServices.getByCode(purchaseOrder.getSupplierCode());
            purchaseOrderBo.setSupplier(supplier);

            //根据申请人工号获取user对象
            String jobNum = purchaseOrder.getJobNumber();
            User user = userServices.getByJobNum(jobNum);
            purchaseOrderBo.setUser(user);

            //根据部门编号获取部门对象
            Department department = departmentServices.getByCode(purchaseOrder.getDepartmentCode());
            purchaseOrderBo.setDepartment(department);

            //根据供应商品code 获取供应商品对象；
            SupplierWares supplierWares = supplierWareServices.getSuppLierWareByCode(purchaseOrder.getPurchaseWareCode());
            purchaseOrderBo.setSupplierWares(supplierWares);

            //根据仓库code 获取仓库对象
            Depot depot = depotServices.getcode(purchaseOrder.getDepotCode());
            purchaseOrderBo.setDepot(depot);

            boList.add(purchaseOrderBo);
        }
        String wareCode = page.getT().getPurchaseWareCode();

        JsonConfig jsonConfig = new JsonConfig();
        //设置忽略的属性
        jsonConfig.setExcludes(new String[]{"role"});  //此处是亮点，只要将所需忽略字段加到数组中即可
        jsonConfig.setIgnoreDefaultExcludes(false);  //设置默认忽略
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        //能够将时间类型转date
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        JSONArray jsonArray = JSONArray.fromObject(boList,jsonConfig);
        String json = "{\"code\": 0,\"msg\": \"\",\"count\": " + count+",\"data\":" + jsonArray + "}";
        return json;
    }

    @Override
    public List<PurchaseOrder> queryForLists() {
        return purchaseOrderDaoImpl.queryForList();
    }


    @Override
    public PurchaseOrder getRequisitionById(Integer id) {
        return purchaseOrderDaoImpl.getRequisitionById(id);
    }

    /* @Override
    public Integer getCount(RequisitionDao requisitionDao) {
        return requisitionDaoImpl.getCount();
    }*/

    @Override
    public void add(PurchaseOrder purchaseOrder) {
        purchaseOrderDaoImpl.add(purchaseOrder);
    }

    @Override
    public void update(PurchaseOrder purchaseOrder) {
        purchaseOrderDaoImpl.update(purchaseOrder);
    }

    @Override
    public void delete(Integer id) {
        purchaseOrderDaoImpl.delete(id);
    }

    @Override
    public void deleteBatch(Integer[] ids) {
        purchaseOrderDaoImpl.deleteBatch(ids);
       /* for (int i = 0; i < ids.length; i++) {
            purchaseOrderDaoImpl.delete(ids[i]);
        }*/
    }
}
