package com.yr.entitys.bo.supplierBO;

import com.yr.entitys.supplier.Supplier;

/**
 * 供应商的实体拓展类
 */
public class SupplierBo {
    private Supplier supplier ;
    private String code;
    private String name;
    private String addr;
    private String rank;//供应商级别

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "SupplierBo{" +
                "supplier=" + supplier +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", addr='" + addr + '\'' +
                ", rank='" + rank + '\'' +
                '}';
    }
}
