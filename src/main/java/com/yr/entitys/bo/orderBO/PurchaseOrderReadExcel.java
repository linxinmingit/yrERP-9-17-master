package com.yr.entitys.bo.orderBO;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.yr.entitys.order.PurchaseOrder;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

public class PurchaseOrderReadExcel {
    private int totalRows = 0;//总行数
    private int totalCells = 0;//构造方法

    public PurchaseOrderReadExcel() {
    }

    public int getTotalRows() {
        return totalRows;
    }

    public int getTotalCells() {
        return totalCells;
    }

    /**
     * 读EXCEL文件，获取集合
     *
     * @param
     * @return
     */
    public List<PurchaseOrder> getExcelInfo(String filePath) {
        //初始化集合
        List<PurchaseOrder> purchaseOrderList = new ArrayList<PurchaseOrder>();
        //初始化输入流
        InputStream is = null;
        try {
            //根据文件名判断文件是2003版本还是2007版本
            boolean isExcel2003 = true;
            if (filePath.matches("^.+\\.(?i)(xlsx)$")) {
                isExcel2003 = false;
            }
            //根据新建的文件实例化输入流
            is = new FileInputStream(filePath);
            Workbook wb = null;
            //根据excel里面的内容读取客户信息
            if (isExcel2003) {//当excel是2003时
                wb = new HSSFWorkbook(is);
            } else {//当excel是2007时
                wb = new XSSFWorkbook(is);
            }
            //读取Excel里面客户的信息
            purchaseOrderList = readExcelValue(wb);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    is = null;
                    e.printStackTrace();
                }
            }
        }
        return purchaseOrderList;
    }


    /**
     * 读取Excel里面客户的信息
     *
     * @param wb
     * @return
     */
    private List<PurchaseOrder> readExcelValue(Workbook wb) {

        List<PurchaseOrder> purchaseOrderList = new ArrayList<PurchaseOrder>();
        //得到第一个shell
        Sheet sheet = wb.getSheetAt(0);

        //得到Excel的行数
        this.totalRows = sheet.getPhysicalNumberOfRows();

        //得到Excel的列数(前提是有行数)
        if ((totalRows >= 1) && (sheet.getRow(0) != null)) {
            int a = sheet.getRow(2).getPhysicalNumberOfCells();
            this.totalCells = sheet.getRow(2).getPhysicalNumberOfCells();
        }

        //循环Excel行数,从第3行开始。标题和字段不入库
        for (int r = 2; totalRows > r; r++) {
            Row row = sheet.getRow(r);
            if (row == null) continue;
            PurchaseOrder purchaseOrder = new PurchaseOrder();

            //循环Excel的列
            //忽略excel 表的序号和id,从下标为2的第三个单元格数据开始开始写入mysql数据库
            for (int c = 2; c < this.totalCells; c++) {
                Cell cell = row.getCell(c);
                if (null != cell) {
                    if (c == 2) {
                        purchaseOrder.setCode(cell.getStringCellValue());
                    }
                    if (c == 3) {
                        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                        purchaseOrder.setJobNumber(cell.getStringCellValue());
                    }
                    if (c == 4) {
                        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                        purchaseOrder.setDepartmentCode(cell.getStringCellValue());
                    }
                    if (c == 5) {
                        purchaseOrder.setApprover(cell.getStringCellValue());//审批人
                    }
                    if (c == 6) {
                        purchaseOrder.setPurchaseWareCode(cell.getStringCellValue());//供应商商品编号
                    }
                    if (c == 7) {
                       purchaseOrder.setPurchaseNumber(Long.parseLong(cell.getStringCellValue()));
                    }
                    if (c == 8) {
                        purchaseOrder.setSupplierCode(cell.getStringCellValue());//供应商编号
                    }
                    if (c == 9) {
                        purchaseOrder.setUnitPrice(Double.parseDouble(cell.getStringCellValue()));
                    }
                    if (c == 10) {

                        purchaseOrder.setTotalPrice(Double.parseDouble(cell.getStringCellValue()));
                    }
                    if (c == 11) {
                        purchaseOrder.setStatus(Integer.parseInt(cell.getStringCellValue()));
                    }
                    if (c == 12) {
                        purchaseOrder.setConsignee(cell.getStringCellValue());
                    }
                    if (c == 13) {
                        purchaseOrder.setDepotCode(cell.getStringCellValue());
                    }
                    //添加采购信息；
                    purchaseOrderList.add(purchaseOrder);
                }
            }
        }
        return purchaseOrderList;
    }
}