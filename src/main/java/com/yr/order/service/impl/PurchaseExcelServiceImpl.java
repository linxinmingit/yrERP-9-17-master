package com.yr.order.service.impl;

import com.yr.entitys.bo.orderBO.PurchaseOrderBo;
import com.yr.entitys.bo.orderBO.PurchaseOrderReadExcel;
import com.yr.entitys.order.PurchaseOrder;
import com.yr.entitys.page.Page;
import com.yr.order.dao.PurchaseExcelDao;
import com.yr.order.service.PurchaseExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author
 * @since
 */
@Service
@Transactional
public class PurchaseExcelServiceImpl implements PurchaseExcelService {
    @Autowired
    private PurchaseExcelDao purchaseExcelDaoImpl;

    /**
     * 查询数据库所有数据信息用于导出Excel表格
     *
     * @return list
     */
    @Override
    public List<PurchaseOrder> queryForList(Page<PurchaseOrderBo> page) {
        return purchaseExcelDaoImpl.queryForList(page);
    }

    /**
     * 导出excel表中的数据信息
     *
     * @param filePath
     * @return
     */
    @Override
    public boolean addExcel(String filePath) {

        int result = 0;
        //解析xml文件
        //ReadExcel readExcel = new ReadExcel();
        PurchaseOrderReadExcel readExcel = new PurchaseOrderReadExcel();
        List<PurchaseOrder> requisitionList = readExcel.getExcelInfo(filePath);
        result = purchaseExcelDaoImpl.addExcel(requisitionList);
        if (result > 0) {
            return true;
        }
        return false;
    }
}
