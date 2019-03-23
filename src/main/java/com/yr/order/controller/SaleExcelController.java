package com.yr.order.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yr.entitys.bo.orderBO.SaleOrderBO;
import com.yr.entitys.order.PurchaseOrder;
import com.yr.entitys.order.SaleOrder;
import com.yr.entitys.page.Page;
import com.yr.util.ImportExcelUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.yr.order.service.SaleExcelportService;
import com.yr.util.ExportExcelUtils;

@Controller
@RequestMapping("/SaleExcel")
public class SaleExcelController {// 销售订单的导出导入

    private static Log log = LogFactory.getLog(SaleExcelController.class);
    @Autowired
    private SaleExcelportService saleExcelport;

    /**
     * 导入Excel表
     * @param excelFile
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/import", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public Map<String,Object> analyzeXml(@RequestParam("excelFile") MultipartFile excelFile, HttpServletRequest request, HttpServletResponse response) {
        //上传xml文件
        InputStream inputs;
        boolean result = false;//导入标志
        Map<String,Object> map = new HashMap<String, Object>();
        try {
            //上传
            inputs = excelFile.getInputStream();
            String fileName = excelFile.getOriginalFilename();
            String filePath=request.getSession().getServletContext().getRealPath("excelFile");
            String uploadFileName = ImportExcelUtils.uploadFile(inputs, fileName, filePath);


            //解析Excel，导入MySQL
            result = saleExcelport.addExcel(filePath+"\\"+uploadFileName);

        } catch (IOException e) {
            e.printStackTrace();
        }
        if(result){
            map.put("message", "成功");
        }else{
            map.put("message", "上传失败，检查是否订单编号重复");
        }
        return map;
    }

    /**
     * Excel导出销售订单表记录
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/export", method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String exportExcel(HttpServletRequest request, HttpServletResponse response, SaleOrderBO saleOrderBO, Page<SaleOrderBO> page) {
        try {
            /*订单名称/订单编号去空格*/
            saleOrderBO.setCode(saleOrderBO.getCode().trim());
            saleOrderBO.setCustomerBuy(saleOrderBO.getCustomerBuy().trim());//购买客户
            // SaleOrder sale = new SaleOrder();
            // 查出用户数据


            //将bo实体set到page对象中
            page.setT(saleOrderBO);
            //查询需要导出到excel表的数据
            List<SaleOrder> salelist = saleExcelport.queryForList(page);


            String title = "销售订单表";
            String[] rowsName = new String[] { "序号","订单id","订单编号", "购买客户", "销售员", "商品编号", "销售商品数量", "销售总价", "销售员联系电话",
                    "申请退货人姓名","申请退货人联系电话",  "销售商品的仓库编号","收货人","申请人","订单状态", "退货收货人","备注"};

            List<Object[]> dataList = new ArrayList<Object[]>();
            Object[] objs = null;
            for (int i = 0; i < salelist.size(); i++) {
                SaleOrder po = salelist.get(i);
                objs = new Object[rowsName.length];
                objs[0] = i;// 序号
                objs[1] =po.getId();//销售订单id
                objs[2] = po.getCode();// 销售订单编号
                objs[3] = po.getCustomerBuy(); // 购买客户
                objs[4] = po.getSalesperson();// 销售员
                objs[5] = po.getWareCode();// 销售商品编号
                objs[6] = po.getNumber(); // 销售商品数量
                objs[7] = po.getMoney();// 销售总价
                objs[8] = po.getsPhoneNumber();// 销售员联系电话
                objs[9] = po.getRequName();// 申请退货人姓名
                objs[10] = po.getrPhoneNumber();// 申请退货人联系电话
                objs[11] = po.getDepotCode(); // 销售商品的仓库编号
                objs[12] = po.getConsignee();// 收货人
                objs[13] = po.getApprover();//申请人
                objs[14] = po.getStates();// 销售单状态（0退货，1交易成功）
                objs[15] = po.getConsignee();// 退货收货人
                objs[16] = po.getRemark(); // 备注
                dataList.add(objs);
            }
            // 工具类样式
            ExportExcelUtils ex = new ExportExcelUtils(title, rowsName, dataList, response);
            ex.exportData();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "{\"code\":1,\"msg\":\"导出成功\"}";
    }

}
