package com.yr.entitys.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yr.common.entity.BaseEntity;
import com.yr.entitys.department.Department;
import com.yr.entitys.depot.Depot;
import com.yr.entitys.supplier.Supplier;
import com.yr.entitys.supplier.SupplierWares;
import com.yr.entitys.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "purchaseOrder")
public class PurchaseOrder extends BaseEntity implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //申请订单编号
    private String code;

    //申请人工号
    @Column(name = "job_num")
    private String jobNumber;

    //部门编号
    @Column(name = "depa_code")
    private String departmentCode;

    //审批人
    private String approver;
  //采购商品编号
    private  String  purchaseWareCode;

    //采购商品数量
    @Column(name = "purc_ware_num")
    private Long purchaseNumber;

    //供应商编号
    @Column(name = "supp_code")
    private String supplierCode;

    //单价

    @Column(name = "unit_price")
    private Double unitPrice;

    //总价
    @Column(name = "total_price")
    private  Double totalPrice;

    //采购单状态(0驳回，1交易成功，2待审核，3申请退货，4退货成功)
    private  Integer status;

    //收货人
    private  String consignee;

    //收货仓库id;
    @Column(name = "depot_code")
    private String  depotCode;

    public String getPurchaseWareCode() {
        return purchaseWareCode;
    }

    public void setPurchaseWareCode(String purchaseWareCode) {
        this.purchaseWareCode = purchaseWareCode;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public void setPurchaseNumber(Long purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public void setDepotCode(String depotCode) {
        this.depotCode = depotCode;
    }

    public Integer getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public String getApprover() {
        return approver;
    }

    public Long getPurchaseNumber() {
        return purchaseNumber;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public Integer getStatus() {
        return status;
    }

    public String getConsignee() {
        return consignee;
    }

    public String getDepotCode() {
        return depotCode;
    }

    public PurchaseOrder() {

    }
    public PurchaseOrder(String code, String jobNumber, String departmentCode, String approver, String purchaseWareCode, Long purchaseNumber, String supplierCode, Double unitPrice, Double totalPrice, Integer status, String consignee, String depotCode) {
        this.code = code;
        this.jobNumber = jobNumber;
        this.departmentCode = departmentCode;
        this.approver = approver;
        this.purchaseWareCode = purchaseWareCode;
        this.purchaseNumber = purchaseNumber;
        this.supplierCode = supplierCode;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.status = status;
        this.consignee = consignee;
        this.depotCode = depotCode;
    }

    @Override
    public String toString() {
        return "PurchaseOrder{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", jobNumber='" + jobNumber + '\'' +
                ", departmentCode='" + departmentCode + '\'' +
                ", approver='" + approver + '\'' +
                ", purchaseWareCode='" + purchaseWareCode + '\'' +
                ", purchaseNumber=" + purchaseNumber +
                ", supplierCode='" + supplierCode + '\'' +
                ", unitPrice=" + unitPrice +
                ", totalPrice=" + totalPrice +
                ", status=" + status +
                ", consignee='" + consignee + '\'' +
                ", depotCode='" + depotCode + '\'' +
                '}';
    }
}
