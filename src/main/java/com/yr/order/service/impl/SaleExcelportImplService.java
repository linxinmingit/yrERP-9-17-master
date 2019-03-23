package com.yr.order.service.impl;

import java.util.List;

import com.yr.entitys.bo.orderBO.SaleImportExcelBO;
import com.yr.entitys.bo.orderBO.SaleOrderBO;
import com.yr.entitys.order.SaleOrder;
import com.yr.entitys.page.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yr.order.dao.SaleExportDao;
import com.yr.order.service.SaleExcelportService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Service
@Transactional
public class SaleExcelportImplService implements SaleExcelportService{
	@Autowired
	private SaleExportDao saleExportDao;
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Excel批量操作导入的销售订单
	 * @param filePath
	 * @return
	 */
	@Override
	public boolean addExcel(String filePath) {


		int result = 0;
		//解析xml文件
		SaleImportExcelBO readExcel=new SaleImportExcelBO();
		List<SaleOrder> saleOrderList = readExcel.getExcelInfo(filePath);
		System.out.println("**********"+saleOrderList.size());
		result = saleExportDao.addExcel(saleOrderList);
		if(result > 0){
			return true;
		}
		return false;
	}

	/**
     * 销售订单表的Excel导出
     */
	@Override
	public List<SaleOrder> queryForList(Page<SaleOrderBO> page) {
		String jpql = "select s from SaleOrder s where 1=1";
		if (!StringUtils.isEmpty(page.getT().getSaleOrder().getCode())){//判断是否为null
			jpql +="and s.code like :code";
		}
		if (!StringUtils.isEmpty(page.getT().getSaleOrder().getCustomerBuy())){
			jpql +="and s.customerBuy like :customer_buy";
		}
		if(page.getT().getSaleOrder().getStates() != null && !page.getT().getSaleOrder().getStates().equals(""))
		{
			jpql+= "and s.states = :states";
		}
		Query query = entityManager.createQuery(jpql);
		if (!StringUtils.isEmpty(page.getT().getSaleOrder().getCode())){
			query.setParameter("code","%"+page.getT().getSaleOrder().getCode()+"%");
		}
		if (!StringUtils.isEmpty(page.getT().getSaleOrder().getCustomerBuy())){
			query.setParameter("customer_buy","%"+page.getT().getSaleOrder().getCustomerBuy()+"%");
		}
		if (page.getT().getSaleOrder().getStates() != null && !page.getT().getSaleOrder().getStates().equals(""))
		{
			query.setParameter("states",page.getT().getSaleOrder().getStates());
		}
		List<SaleOrder> list = query.getResultList();
		return list;
		//return saleExportDao.queryForList(page);
	}
}
