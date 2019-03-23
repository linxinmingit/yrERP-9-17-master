package com.yr.order.service;

import com.yr.entitys.bo.orderBO.PurchaseOrderBo;
import com.yr.entitys.order.PurchaseOrder;
import com.yr.entitys.order.SaleOrder;
import com.yr.entitys.page.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 *
 * @author
 * @since
 */
public interface PurchaseExcelService {
    /**
     * 查询数据库所有数据信息用于导出Excel表格
     * @return list
     */
    List<PurchaseOrder> queryForList(Page<PurchaseOrderBo> page);

    /**
     * 导入excel表
     * @param filePath
     * @return
     */
    //boolean batchImport(String name, MultipartFile file);
    public boolean addExcel(String filePath);
}
