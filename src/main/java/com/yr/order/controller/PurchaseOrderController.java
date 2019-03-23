package com.yr.order.controller;

import com.yr.department.service.DepartmentService;
import com.yr.depot.service.DepotService;
import com.yr.entitys.Log.Log;
import com.yr.entitys.bo.orderBO.PurchaseOrderBo;
import com.yr.entitys.bo.orderBO.RandomUtil;
import com.yr.entitys.order.PurchaseOrder;
import com.yr.entitys.page.Page;
import com.yr.entitys.user.User;
import com.yr.log.service.LogService;
import com.yr.order.service.PurchaseOrderService;
import com.yr.supplier.service.SupplierService;
import com.yr.supplier.service.SupplierWareService;
import com.yr.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 这个是一个采购的controller层
 */
@Controller
@RequestMapping(value = "requisition")
@SessionAttributes(value = {"user"}, types = {Integer.class})//这里指定一下 Session 才能获得指定的数据
public class PurchaseOrderController {
    @Autowired
    private PurchaseOrderService purchaseOrderServiceImpl;

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

    @Autowired
    @Qualifier("logServiceImpl")
    private LogService logServices;//日志



    @ModelAttribute
    public void modelAttribute(@RequestParam(value = "id", required = false) Integer id, Map<String, Object> map) {
        if (id != null && id != 0) {
            PurchaseOrder purchaseOrder = purchaseOrderServiceImpl.getRequisitionById(id);
            //返回采购实体
            // map.put("purchaseOrder", purchaseOrder);

            //页面返回实体BO类
            map.put("purchaseOrderBO",new PurchaseOrderBo(purchaseOrder,userServices.getByJobNum(purchaseOrder.getJobNumber()),departmentServices.getByCode(purchaseOrder.getDepartmentCode()),supplierServices.getByCode(purchaseOrder.getSupplierCode()),supplierWareServices.getSuppLierWareByCode(purchaseOrder.getPurchaseWareCode()),depotServices.getcode(purchaseOrder.getDepotCode())));
        }
    }

    /**
     * 数据查询接口
     * 跳转查询页面
     * @return
     */
    @RequestMapping(value = "requisitionTable",method = RequestMethod.GET)
    public String index() {
        return "purchaseOrderList";
    }

    /**
     * 分页查询采购表数据，并且返回json 数据；
     * @param purchaseOrderBo
     * @param page
     * @return json
     *
     *
     */
    @RequestMapping(value = "/requisitionTable/list", method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String query(PurchaseOrderBo purchaseOrderBo, Page<PurchaseOrderBo> page) {

        /*订单名称/订单编号去空格*/
        purchaseOrderBo.setPurchaseCode(purchaseOrderBo.getPurchaseCode().trim());

        String wareCode = purchaseOrderBo.getPurchaseWareCode().trim();//获取商品名称/编号
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        if (wareCode != null && !pattern.matcher(wareCode).matches())
        {
            purchaseOrderBo.setPurchaseWareCode(supplierWareServices.getSupplierWareCode(wareCode));
        }

        page.setT(purchaseOrderBo);
        String json  = purchaseOrderServiceImpl.query(page);
        return json;
    }

    /**
     * 添加数据接口；
     * 跳转添加页面
     * @param map
     * @return
     */
    @RequestMapping(value = "/requisitionTable/add", method = RequestMethod.GET)
    public String jumpAdd(Map<String, Object> map) {
        //获取部门对象集合
        Map<String,Object> departmentList =  departmentServices.querys();
        //获取仓库对象集合0 = {HashMap$Node@7834} "1002" -> "大浪仓库"
        Map<String,Object> depotList =  depotServices.queryDepots();Map<String,Object> supplierList = supplierServices.querySuppliers();
        //获取供应商对象集合

        map.put("departmentList",departmentList);
        map.put("userList",userServices.queryUser());
        map.put("depotList",depotList);
        map.put("supplierList",supplierList);
        map.put("supplierWareList",supplierWareServices.queryList());
        map.put("purchaseOrderBO",new PurchaseOrderBo());

        return "purchaseOrderAU";
    }

    /**
     * 新增数据
     * @param purchaseOrderBo
     * @param request
     * @return
     */
    @RequestMapping(value = "/requisitionTable", method = RequestMethod.POST)
    public String add(PurchaseOrderBo purchaseOrderBo ,HttpServletRequest request) {
        try {
            //将时间戳设置进入创建时间
            purchaseOrderBo.getPurchaseOrder().setCreateTime(new Timestamp(System.currentTimeMillis()));

            //将修改时间设进修改时间，初始修改时间，后期会改
            purchaseOrderBo.getPurchaseOrder().setUpdateTime(new Timestamp(System.currentTimeMillis()));

            //获取session 当中当前登录用户，session属性名从login登录的传过来，
            User user = (User) request.getSession().getAttribute("user");
            purchaseOrderBo.getPurchaseOrder().setCreateEmp(user.getName());
            //这个初始的修改人，后期会改
            purchaseOrderBo.getPurchaseOrder().setUpdateEmp(user.getName());

            //获取采购单价和采购数量，计算采购商品总价格，并把它设入setTotalPrice();

            double total = purchaseOrderBo.getPurchaseOrder().getUnitPrice()*purchaseOrderBo.getPurchaseOrder().getPurchaseNumber();
            purchaseOrderBo.getPurchaseOrder().setTotalPrice(total);

            //随机数生成订单编号；
            String code = RandomUtil.generateUpperString(13);
            purchaseOrderBo.getPurchaseOrder().setCode(code);

            //所有订单申请表单初始化都是-->>待审核状态2
            // (0驳回，1交易成功，2待审核，3申请退货，4退货成功)
            purchaseOrderBo.getPurchaseOrder().setStatus(2);
            //修改人即使订单审批人；
            purchaseOrderBo.getPurchaseOrder().setApprover(user.getName());

            //因为暂时不会级联查询并显示，所以就根据界面上的申请人名称来决定申请人工号了
            purchaseOrderBo.getPurchaseOrder().setJobNumber(purchaseOrderBo.getUser().getJobNum());

            //因为暂时不会级联查询并显示，所以就根据界面上的采购商品名称俩决定采购商品编号了
            purchaseOrderBo.getPurchaseOrder().setPurchaseWareCode(purchaseOrderBo.getSupplierWares().getName());
            purchaseOrderServiceImpl.add(purchaseOrderBo.getPurchaseOrder());

            Log log = new Log();
            log.setModular("采购订单模块");
            log.setTable("purchaseOrder");
            //模块的操作类型（0抛异常，1新增，2删除，3修改，4用户登录，5用户退出）
            log.setType(1);
            //log.setFieldOldValue();  //新增数据忽略前置
            log.setFieldNewValue(purchaseOrderBo.getPurchaseOrder().toString());
            log.setCreateTime(new Timestamp(System.currentTimeMillis()));
            log.setCreateEmp(user.getName());
            logServices.addLog(log);
        } catch (Exception e)
        {
            Log log = new Log();
            log.setModular("采购订单模块");
            log.setTable("purchaseOrder");
            //模块的操作类型（0抛异常，1新增，2删除，3修改，4用户登录，5用户退出）
            log.setType(0);
            log.setFieldNewValue(purchaseOrderBo.getPurchaseOrder().toString());
            log.setCreateTime(new Timestamp(System.currentTimeMillis()));
            log.setContent(e.toString());
            User user1 = (User) request.getSession().getAttribute("user");
            log.setCreateEmp(user1.getName());
            logServices.addLog(log);

            e.printStackTrace();
        }
        return "{\"code\":1,\"msg\":\"新增保存成功\"}";
    }
    /**
     * 修改接口；
     *  跳转修改页面
     * @param id
     * @param map
     * @return
     */
    @RequestMapping(value = "/requisitionTable/{id}", method = RequestMethod.GET)
    public String jumpUpdate(@PathVariable Integer id, Map<String, Object> map) {

        //根据id查询数据库数据；
        PurchaseOrder purchaseOrder = purchaseOrderServiceImpl.getRequisitionById(id);

        //获取部门对象集合
        Map<String,Object> departmentList =  departmentServices.querys();
        //获取仓库对象集合0 = {HashMap$Node@7834} "1002" -> "大浪仓库"
        Map<String,Object> depotList =  depotServices.queryDepots();
        //获取供应商对象集合
        Map<String,Object> supplierList = supplierServices.querySuppliers();
        // (0驳回，1交易成功，2待审核，3申请退货，4退货成功)
        Map<Integer,String> statusMap = new HashMap<>();
        statusMap.put(0,"驳回");
        statusMap.put(1,"交易成功");
        statusMap.put(2,"待审核");
        statusMap.put(3,"申请退货");
        statusMap.put(4,"退货成功");
        map.put("status",statusMap);
        map.put("departmentList",departmentList);
        map.put("userList",userServices.queryUser());
        map.put("depotList",depotList);
        map.put("supplierList",supplierList);
        map.put("supplierWareList",supplierWareServices.queryList());
        map.put("purchaseOrderBO",new PurchaseOrderBo(purchaseOrder,userServices.getByJobNum(purchaseOrder.getJobNumber()),departmentServices.getByCode(purchaseOrder.getDepartmentCode()),supplierServices.getByCode(purchaseOrder.getSupplierCode()),supplierWareServices.getSuppLierWareByCode(purchaseOrder.getPurchaseWareCode()),depotServices.getcode(purchaseOrder.getDepotCode())));
        return "purchaseOrderAU";
    }

    @RequestMapping(value = "/requisitionTable", method = RequestMethod.PUT)
    public String update(@ModelAttribute("purchaseOrderBO") PurchaseOrderBo purchaseOrderBo ,HttpServletRequest request) {
          //修改之前的值
          String purchaseOrderlog = purchaseOrderServiceImpl.getRequisitionById(purchaseOrderBo.getPurchaseOrder().getId()).toString();
        try {
            //获取当前登录用户并把它设为修改人
            User user = (User) request.getSession().getAttribute("user");
            purchaseOrderBo.getPurchaseOrder().setUpdateEmp(user.getName());
            //获取当前时间为数据修改时间；
            purchaseOrderBo.getPurchaseOrder().setUpdateTime(new Timestamp(System.currentTimeMillis()));

            //获取采购单价和采购数量，计算采购商品总价格，并把它设入setTotalPrice();
            double total =  purchaseOrderBo.getPurchaseOrder().getUnitPrice()* purchaseOrderBo.getPurchaseOrder().getPurchaseNumber();
            purchaseOrderBo.getPurchaseOrder().setTotalPrice(total);

            //因为暂时不会级联查询并显示，所以就根据界面上的申请人名称来决定申请人工号了
            purchaseOrderBo.getPurchaseOrder().setJobNumber(purchaseOrderBo.getUser().getJobNum());

            //因为暂时不会级联查询并显示，所以就根据界面上的采购商品名称俩决定采购商品编号了
            purchaseOrderBo.getPurchaseOrder().setPurchaseWareCode(purchaseOrderBo.getSupplierWares().getName());
            purchaseOrderServiceImpl.update( purchaseOrderBo.getPurchaseOrder());


            Log log = new Log();
            log.setModular("采购订单模块");
            log.setTable("purchaseOrder");
            //模块的操作类型（0抛异常，1新增，2删除，3修改，4用户登录，5用户退出）
            log.setType(3);
            //修改之前的值
            log.setFieldOldValue(purchaseOrderlog);
            //修改之后的值
            log.setFieldNewValue(purchaseOrderBo.getPurchaseOrder().toString());
            log.setCreateTime(new Timestamp(System.currentTimeMillis()));
            log.setCreateEmp(user.getName());
            logServices.addLog(log);
        } catch (Exception e)
        {
            e.printStackTrace();

            Log log = new Log();
            log.setModular("采购订单模块");
            log.setTable("purchaseOrder");
            //模块的操作类型（0抛异常，1新增，2删除，3修改，4用户登录，5用户退出）
            log.setType(0);
            log.setFieldOldValue(purchaseOrderlog);
            log.setFieldNewValue(purchaseOrderBo.getPurchaseOrder().toString());
            log.setContent(e.getMessage());
            log.setCreateTime(new Timestamp(System.currentTimeMillis()));
            User user1 = (User) request.getSession().getAttribute("user");
            log.setCreateEmp(user1.getName());
            logServices.addLog(log);
        }
        return "{\"code\":1,\"msg\":\"修改成功\"}";
    }

    /**
     * 这个是ajax 请求不要跳转，删除接口
     * 删除接口
     * @param ids
     * @return
     */
   /* @RequestMapping(value = "/requisitionTable/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable Integer id) {
        purchaseOrderServiceImpl.delete(id);
        return "{\"code\":1,\"msg\":\"删除成功\"}";
    }*/

    @ResponseBody
    @RequestMapping(value = "/requisitionTable/{ids}",method = RequestMethod.DELETE)
    public String delete(@PathVariable Integer[] ids,HttpServletRequest request){
        try {
            for (Integer id : ids) {
                if (ids.length != 0)
                {
                    //采购订单删除操作记录日志
                    Log log = new Log();
                    log.setModular("采购订单模块");
                    log.setTable("purchaseOrder");
                    //模块的操作类型（0抛异常，1新增，2删除，3修改，4用户登录，5用户退出）
                    log.setType(2);

                    PurchaseOrder pur =  purchaseOrderServiceImpl.getRequisitionById(id);
                    log.setFieldOldValue(pur.toString());
                    //log.setFieldNewValue(purchaseOrderBo.getPurchaseOrder().toString());   //删除数据,日志忽略后置
                    log.setCreateTime(new Timestamp(System.currentTimeMillis()));
                    User user = (User) request.getSession().getAttribute("user");
                    log.setCreateEmp(user.getName());
                    logServices.addLog(log);
                }
            }
            //采购订单删除
            purchaseOrderServiceImpl.deleteBatch(ids);
        } catch (Exception e)
        {
            for (Integer id : ids) {
                if (ids.length != 0)
                {
                    //采购订单删除操作记录日志
                    Log log = new Log();
                    log.setModular("采购订单模块");
                    log.setTable("purchaseOrder");
                    //模块的操作类型（0抛异常，1新增，2删除，3修改，4用户登录，5用户退出）
                    log.setType(2);

                    PurchaseOrder pur =  purchaseOrderServiceImpl.getRequisitionById(id);
                    log.setFieldOldValue(pur.toString());
                    //log.setFieldNewValue(purchaseOrderBo.getPurchaseOrder().toString());   //删除数据,日志忽略后置
                    log.setCreateTime(new Timestamp(System.currentTimeMillis()));
                    User user = (User) request.getSession().getAttribute("user");
                    log.setCreateEmp(user.getName());
                    logServices.addLog(log);
                }
            }
            e.printStackTrace();
        }
        return "{\"code\":1,\"msg\":\"删除成功\"}";
    }
}
