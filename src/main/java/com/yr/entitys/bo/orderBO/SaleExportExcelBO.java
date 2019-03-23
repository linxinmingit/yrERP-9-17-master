package com.yr.entitys.bo.orderBO;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yr.entitys.order.SaleOrder;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;


public class SaleExportExcelBO extends AbstractXlsxView{
    @Override
    protected void buildExcelDocument(Map<String, Object> map, Workbook workbook, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        String fileName = "销售列表excel.xls";
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/ms-excel");
        response.setHeader("Content-Disposition", "inline; filename="+new String(fileName.getBytes(),"iso8859-1"));
        OutputStream outputStream = response.getOutputStream();
		List<SaleOrder> SaleList =  (List<SaleOrder>) map.get("SaleList");
        // 产生Excel表头
        HSSFSheet sheet = (HSSFSheet) workbook.createSheet("基本信息");
        HSSFRow header = sheet.createRow(0);
        // 产生标题列
        header.createCell(0).setCellValue("订单id");
        header.createCell(1).setCellValue("销售订单编号");
        header.createCell(2).setCellValue("购买客户");
        header.createCell(3).setCellValue("销售员");
        header.createCell(4).setCellValue("销售商品编号");
        header.createCell(5).setCellValue("销售商品数量");
        header.createCell(6).setCellValue("销售金额");
        header.createCell(7).setCellValue("销售员联系电话");
        header.createCell(8).setCellValue("备注");
        header.createCell(9).setCellValue("销售单状态");
        header.createCell(10).setCellValue("收货人");
        header.createCell(11).setCellValue("审批人");
        header.createCell(12).setCellValue("申请退货人姓名");
        header.createCell(13).setCellValue("申请退货人联系电话");
        header.createCell(14).setCellValue("销售商品的仓库编号");
        header.createCell(15).setCellValue("创建人");
        header.createCell(16).setCellValue("创建时间");
        header.createCell(17).setCellValue("修改人");
        header.createCell(18).setCellValue("修改时间");

        HSSFCellStyle cellStyle = (HSSFCellStyle) workbook.createCellStyle();
        cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("mm/dd/yyyy"));
        int rowNumber = 1;
        for (SaleOrder sale: SaleList) {
            HSSFRow row = sheet.createRow(rowNumber++);
            // 产生标题列
            row.createCell(0).setCellValue(sale.getId());
            row.createCell(1).setCellValue(sale.getCode());
            row.createCell(2).setCellValue(sale.getCustomerBuy());
            row.createCell(3).setCellValue(sale.getSalesperson());
            row.createCell(4).setCellValue(sale.getDepotCode());
            row.createCell(5).setCellValue(sale.getNumber());
            row.createCell(6).setCellValue(sale.getMoney());
            row.createCell(7).setCellValue(sale.getsPhoneNumber());
            row.createCell(8).setCellValue(sale.getRemark());
            row.createCell(9).setCellValue(sale.getStates());
            row.createCell(10).setCellValue(sale.getConsignee());
            row.createCell(11).setCellValue(sale.getApprover());
            row.createCell(12).setCellValue(sale.getRequName());
            row.createCell(13).setCellValue(sale.getrPhoneNumber());
            row.createCell(14).setCellValue(sale.getDepotCode());
            row.createCell(15).setCellValue(sale.getCreateEmp());
            row.createCell(16).setCellValue(sale.getCreateTime());
            row.createCell(17).setCellValue(sale.getUpdateEmp());
            row.createCell(18).setCellValue(sale.getUpdateTime());
        }
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

}
