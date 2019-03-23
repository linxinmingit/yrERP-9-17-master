package com.yr.order.controller;

import com.yr.entitys.Log.Log;
import com.yr.entitys.bo.orderBO.PurchaseOrderBo;
import com.yr.entitys.order.PurchaseOrder;
import com.yr.entitys.order.SaleOrder;
import com.yr.entitys.page.Page;
import com.yr.entitys.user.User;
import com.yr.log.service.LogService;
import com.yr.order.service.PurchaseExcelService;
import com.yr.supplier.service.SupplierService;
import com.yr.supplier.service.SupplierWareService;
import com.yr.user.service.UserService;
import com.yr.util.ExportExcelUtils;
import com.yr.util.ImportExcelUtils;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 *
 * @author
 * @since
 */
@Controller
@RequestMapping("/purchaseExcel")
public class PurchaseExcelController {
    @Autowired
    private PurchaseExcelService purchaseExcelServiceImpl;

    @Autowired
    @Qualifier("supplierWareServiceImpl")
    private SupplierWareService supplierWareServiceSer;//供应商品

    @Autowired
    @Qualifier("logServiceImpl")
    private LogService logServices;//日志

    /**
     * 导出采购信息excel表；
     * @param request
     * @param response
     */
    @RequestMapping(value = "/export", method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String exportExcel(HttpServletRequest request, HttpServletResponse response, PurchaseOrderBo purchaseOrderBo, Page<PurchaseOrderBo> page) {
        User user = (User) request.getSession().getAttribute("user");
        String username = user.getName();//从session中获取登录用户；
        try {
            String purCode = purchaseOrderBo.getPurchaseCode().trim();
            purchaseOrderBo.setPurchaseCode(purCode);

            String wareCode = purchaseOrderBo.getPurchaseWareCode().trim();//获取商品名称/编号
            Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
            if (wareCode != null && !pattern.matcher(wareCode).matches())
            {
                purchaseOrderBo.setPurchaseWareCode(supplierWareServiceSer.getSupplierWareCode(wareCode));
            }

            //将bo实体set到page对象中
            page.setT(purchaseOrderBo);
            //查询需要导出到excel表的数据
            List<PurchaseOrder> purchaseOrderList = purchaseExcelServiceImpl.queryForList(page);

            String tableName = "采购订单";
            String[] rowsName = new String[]{"序号","采购id","采购编号","采购人工号","采购部门编号","审批人","采购商品编号","采购商品数量","供应商编号","商品单价","商品总价","采购单状态","收货人","收货仓库编号","创建人","创建时间","修改人","修改时间"};
            List<Object[]> dataList = new ArrayList<Object[]>();
            Object[] objects = null;
            for (int i = 0; i < purchaseOrderList.size(); i++) {
                PurchaseOrder pur = purchaseOrderList.get(i);
                objects = new Object[rowsName.length];
                objects[0] = i;
                objects[1] = pur.getId();
                objects[2] = pur.getCode();
                objects[3] = pur.getJobNumber();
                objects[4] = pur.getDepartmentCode();
                objects[5] = pur.getApprover();
                objects[6] = pur.getPurchaseWareCode();
                objects[7] = pur.getPurchaseNumber();
                objects[8] = pur.getSupplierCode();
                objects[9] = pur.getUnitPrice();
                objects[10] = pur.getTotalPrice();
                objects[11] = pur.getStatus();
                objects[12] = pur.getConsignee();
                objects[13] = pur.getDepotCode();
                objects[14] = pur.getCreateEmp();
                objects[15] = pur.getCreateTime();
                objects[16] = pur.getUpdateEmp();
                objects[17] = pur.getUpdateTime();
                dataList.add(objects);
            }
            ExportExcelUtils ex = new ExportExcelUtils(tableName, rowsName, dataList, response);
            ex.exportData();
        } catch (Exception e) {
            Log log = new Log();
            log.setCreateTime(new Timestamp(System.currentTimeMillis()));
            log.setCreateEmp(username);
            log.setTable("purchaseOrder");
            //模块的操作类型（0抛异常，1新增，2删除，3修改，4用户登录，5用户退出,6excel导出，7excel导入）
            log.setType(0);
            //log.setFieldNewValue(purchaseOrderBo.getPurchaseOrder().toString());
           //log.setFieldOldValue(purchaseOrderBo.getPurchaseOrder().toString());
            log.setContent(e.getMessage());
            log.setModular("采购订单模块");
            logServices.addLog(log);

            e.printStackTrace();
        }
        Log log = new Log();
        log.setCreateTime(new Timestamp(System.currentTimeMillis()));
        log.setCreateEmp(username);
        log.setTable("purchaseOrder");
        //模块的操作类型（0抛异常，1新增，2删除，3修改，4用户登录，5用户退出,6excel导出，7excel导入）
        log.setType(6);
        //log.setFieldNewValue(purchaseOrderBo.getPurchaseOrder().toString());
        //log.setFieldOldValue(purchaseOrderBo.getPurchaseOrder().toString());
        log.setModular("采购订单模块");
        logServices.addLog(log);

        return "{\"code\":1,\"msg\":\"导出成功\"}";
    }

    /**
     * 将采购excel 表数据 导入到mysql 数据库中
     * @param excelFile
     * @param request
     * @param response
     * @return map
     */
    @ResponseBody
    @RequestMapping(value = "/import", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public Map<String,Object> analyzeXml(@RequestParam("excelFile") MultipartFile excelFile, HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        String username = user.getName();//从session中获取当前登录对象；
        //上传xml文件
        InputStream inputs;
        boolean result = false;//导入标志
        Map<String,Object> map = new HashMap<String, Object>();
        try {
            //上传
            inputs = excelFile.getInputStream();
            String fileName = excelFile.getOriginalFilename();
            String filePath=request.getSession().getServletContext().getRealPath("excelFile");
            //将excel表写入到硬盘文件夹
            String uploadFileName = ImportExcelUtils.uploadFile(inputs, fileName, filePath);

            //解析Excel，导入MySQL
            result = purchaseExcelServiceImpl.addExcel(filePath+"\\"+uploadFileName);
        } catch (IOException e) {
            Log log = new Log();
            log.setCreateTime(new Timestamp(System.currentTimeMillis()));
            log.setCreateEmp(username);
            log.setTable("purchaseOrder");
            //模块的操作类型（0抛异常，1新增，2删除，3修改，4用户登录，5用户退出,6excel导出，7excel导入）
            log.setType(0);
             //log.setFieldNewValue(purchaseOrderBo.getPurchaseOrder().toString());
            // log.setFieldOldValue(purchaseOrderBo.getPurchaseOrder().toString());
            log.setContent(e.getMessage());
            log.setModular("采购订单模块");
            logServices.addLog(log);

            e.printStackTrace();
        }
        if(result){
            map.put("message", "成功");
        }else{
            map.put("message", "失败");
        }
        Log log = new Log();
        log.setCreateTime(new Timestamp(System.currentTimeMillis()));
        log.setCreateEmp(username);
        log.setTable("purchaseOrder");
        //模块的操作类型（0抛异常，1新增，2删除，3修改，4用户登录，5用户退出,6excel导出，7excel导入）
        log.setType(7);
       //log.setFieldNewValue(purchaseOrderBo.getPurchaseOrder().toString());
       // log.setFieldOldValue(purchaseOrderBo.getPurchaseOrder().toString());
        log.setModular("采购订单模块");
        logServices.addLog(log);
        return map;
    }
}
