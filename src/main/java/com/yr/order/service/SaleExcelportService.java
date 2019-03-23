package com.yr.order.service;

import java.util.List;

import com.yr.entitys.bo.orderBO.SaleOrderBO;
import com.yr.entitys.order.SaleOrder;
import com.yr.entitys.page.Page;
import org.springframework.web.multipart.MultipartFile;


public interface SaleExcelportService {
	
	/**
	 * 导出Excel表格
	 * @return
	 */
	 List<SaleOrder> queryForList(Page<SaleOrderBO> page);

	/**
	 * 导入Excel表格
	 * @param filePath
	 * @return
	 */

	boolean addExcel(String filePath);
}
