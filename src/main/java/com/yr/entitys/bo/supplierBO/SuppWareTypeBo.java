package com.yr.entitys.bo.supplierBO;

import com.yr.entitys.supplier.SuppWareType;

import javax.persistence.Column;

/**
 * 供应商品类别的对外拓展类
 */
public class SuppWareTypeBo {
    private SuppWareType suppWareType;
    private String code;//供应商品的类型编号
    private String name;//供应商品类型的名称
    private String supCode;//供应商品类型的父级类型编号

    public SuppWareType getSuppWareType() {
        return suppWareType;
    }

    public void setSuppWareType(SuppWareType suppWareType) {
        this.suppWareType = suppWareType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSupCode() {
        return supCode;
    }

    public void setSupCode(String supCode) {
        this.supCode = supCode;
    }

    @Override
    public String toString() {
        return "SuppWareTypeBo{" +
                "suppWareType=" + suppWareType +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", supCode='" + supCode + '\'' +
                '}';
    }
}
