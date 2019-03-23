package com.yr.entitys.bo.orderBO;

import com.yr.entitys.department.Department;
import com.yr.entitys.depot.Depot;
import com.yr.entitys.order.PurchaseOrder;
import com.yr.entitys.supplier.Supplier;
import com.yr.entitys.supplier.SupplierWares;
import com.yr.entitys.user.User;

import java.io.Serializable;

public class PurchaseOrderBo implements Serializable{
    //申请采购
    private PurchaseOrder purchaseOrder;
    private  User user;
    private Department department;
    private Supplier supplier;
    private SupplierWares supplierWares;
    private Depot depot;


    /*private String  purchaseName;
    private String purchaseType;*/
    private String purchaseCode;
    private  String purchaseWareCode;

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public void setSupplierWares(SupplierWares supplierWares) {
        this.supplierWares = supplierWares;
    }

    public void setDepot(Depot depot) {
        this.depot = depot;
    }

    public Department getDepartment() {
        return department;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public SupplierWares getSupplierWares() {
        return supplierWares;
    }

    public Depot getDepot() {
        return depot;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }
    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }


    /*分页条件数据查询*/
    public String getPurchaseCode() {
        return purchaseCode;
    }

    public void setPurchaseCode(String purchaseCode) {
        this.purchaseCode = purchaseCode;
    }

    public String getPurchaseWareCode() {
        return purchaseWareCode;
    }

    public void setPurchaseWareCode(String purchaseWareCode) {
        this.purchaseWareCode = purchaseWareCode;
    }

    public PurchaseOrderBo() {
    }
    public PurchaseOrderBo(PurchaseOrder purchaseOrder, User user, Department department, Supplier supplier, SupplierWares supplierWares, Depot depot) {
        this.purchaseOrder = purchaseOrder;
        this.user = user;
        this.department = department;
        this.supplier = supplier;
        this.supplierWares = supplierWares;
        this.depot = depot;
    }
}
