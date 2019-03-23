package com.yr.entitys.page;

import java.io.Serializable;
import java.util.List;

public class Page<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer currentPage = 1;// 当前页
	private Integer pageSize = 10;// 每页量
	private String orderField;// 排序字段,多个字段用","隔开
	private String sort = "DESC";// 排序方式,DESC降序;ASC升序
	private Integer start;// 分页开始记录条数
	private Long totalRecord;// 总记录数
	private Integer totalPage;// 总页数
	private List<T> pageData; // 页数据
	private T t;//分页记录的实体类
	//private StudentBO studentBO;
	
	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField == null ? null : orderField.trim();
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort == null ? null : sort.trim();
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Long getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(Long totalRecord) {
		if (totalRecord != null) {
			this.start = (this.currentPage - 1) * this.pageSize;
			this.totalPage = (int) ((totalRecord % this.pageSize) == 0 ? (totalRecord / this.pageSize): (totalRecord / this.pageSize) + 1);
		}
		this.totalRecord = totalRecord;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	
	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}

	public List<T> getPageData() {
		return pageData;
	}

	public void setPageData(List<T> pageData) {
		this.pageData = pageData;
	}

	@Override
	public String toString() {
		return "Page [currentPage=" + currentPage + ", pageSize=" + pageSize + ", orderField=" + orderField + ", sort="
				+ sort + ", start=" + start + ", totalRecord=" + totalRecord + ", totalPage=" + totalPage
				+ ", pageData=" + pageData + ", t=" + t +  "]";
	}

}
