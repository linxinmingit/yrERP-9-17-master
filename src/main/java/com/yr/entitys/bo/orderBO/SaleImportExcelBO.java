package com.yr.entitys.bo.orderBO;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.yr.entitys.order.SaleOrder;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

public class SaleImportExcelBO {
    private int totalRows = 0;//总行数
    private int totalCells = 0;//构造方法

    public SaleImportExcelBO(){}
    public int getTotalRows()  { return totalRows;}
    public int getTotalCells() {  return totalCells;}


  public List<SaleOrder> getExcelInfo(String filePath){
      //初始化集合
      List<SaleOrder> saleOrderList=new ArrayList<SaleOrder>();
      //初始化输入流
      InputStream is = null;
      try{
          //根据文件名判断文件是2003版本还是2007版本
          boolean isExcel2003 = true;
          if(filePath.matches("^.+\\.(?i)(xlsx)$")){
              isExcel2003 = false;
          }
          //根据新建的文件实例化输入流
          is = new FileInputStream(filePath);
          Workbook wb = null;
          //根据excel里面的内容读取客户信息
          if(isExcel2003){//当excel是2003时
              wb = new HSSFWorkbook(is);
          }
          else{//当excel是2007时
              wb = new XSSFWorkbook(is);
          }
          //读取Excel里面销售订单表的信息
          saleOrderList=readExcelValue(wb);
          is.close();
      }catch(Exception e){
          e.printStackTrace();
      } finally{
          if(is !=null)
          {
              try{
                  is.close();
              }catch(IOException e){
                  is = null;
                  e.printStackTrace();
              }
          }
      }
      return saleOrderList;
  }
    /**
     * 读取Excel里面销售订单表的信息
     * @param wb
     * @return
     */
    private List<SaleOrder> readExcelValue(Workbook wb){

        List<SaleOrder> lList = new ArrayList<SaleOrder>();
        //得到第一个shell
        Sheet sheet = wb.getSheetAt(0);

        //得到Excel的行数
        this.totalRows=sheet.getPhysicalNumberOfRows();

        //得到Excel的列数(前提是有行数)
        if((totalRows >= 1) && (sheet.getRow(0) != null)) {
            int a = sheet.getRow(2).getPhysicalNumberOfCells();
            this.totalCells=sheet.getRow(2).getPhysicalNumberOfCells();
        }

        //循环Excel行数,从第二行开始。标题不入库
        for(int r = 2; totalRows > r; r++){
            Row row = sheet.getRow(r);
            if (row == null) continue;
            SaleOrder sale = new SaleOrder();

            //循环Excel的列
            for(int c = 2; c <this.totalCells; c++){//忽略excel 表的id,从下标为2的第三个单元格数据开始
                Cell cell = row.getCell(c);
                if (null != cell)
                {
                    if (c == 2)
                    {
                        cell.setCellType(CellType.STRING);
                        sale.setCode(cell.getStringCellValue());//销售订单编号(唯一)
                    }
                    if (c == 3)
                    {
                        cell.setCellType(CellType.STRING);
                        sale.setCustomerBuy(cell.getStringCellValue());//购买客户
                    }
                    if (c == 4)
                    {
                        cell.setCellType(CellType.STRING);
                        //销售员
                        sale.setSalesperson(cell.getStringCellValue());
                    }
                    if (c == 5)
                    {
                        cell.setCellType(CellType.STRING);
                        sale.setWareCode(cell.getStringCellValue());//销售商品编号

                    }
                    if (c == 6)
                    {

                        cell.setCellType(CellType.STRING);
                        sale.setNumber(Long.parseLong(cell.getStringCellValue()));//销售商品数量
                    }
                    if (c == 7)
                    {
                        cell.setCellType(CellType.STRING);
                        sale.setMoney(Double.parseDouble(cell.getStringCellValue()));//销售金额
                    }
                    if (c == 8)
                    {
                        cell.setCellType(CellType.STRING);
                        sale.setsPhoneNumber(cell.getStringCellValue());//销售员联系电话
                    }
                    if (c == 9)
                    {
                        cell.setCellType(CellType.STRING);
                        sale.setRequName(cell.getStringCellValue()); //申请退货人姓名
                    }
                    if (c == 10)
                    {
                        cell.setCellType(CellType.STRING);
                        sale.setrPhoneNumber(cell.getStringCellValue());//申请退货人联系电话
                    }
                    if (c == 11)
                    {
                        cell.setCellType(CellType.STRING);
                        sale.setDepotCode(cell.getStringCellValue()); //销售商品的仓库编号
                    }
                    if (c == 12)
                    {
                        cell.setCellType(CellType.STRING);
                        sale.setConsignee(cell.getStringCellValue());//收货人
                    }
                    if (c == 13)
                    {
                        cell.setCellType(CellType.STRING);
                        sale.setApprover(cell.getStringCellValue());//申请人
                    }
                    if (c == 14)
                    {
                        cell.setCellType(CellType.STRING);
                        sale.setStates(Integer.parseInt(cell.getStringCellValue()));//销售单状态（0退货，1交易成功）
                    }
                    if(c == 15)
                    {
                        cell.setCellType(CellType.STRING);
                        sale.setConsignee(cell.getStringCellValue());//申请人
                    }
                    if (c == 16)
                    {
                        cell.setCellType(CellType.STRING);
                        sale.setRemark(cell.getStringCellValue());//备注
                    }
                    //添加销售订单信息；
                    System.err.println("============="+sale.toString());
                    lList.add(sale);
                }
            }
        }
        return lList;
    }
}
