package com.yr.entitys.supplier;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 供应商品实体类
 */
@Entity
@Table(name = "supp_wares")
public class SupplierWares implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer  id ;//自动增长
    @Column(nullable = false,unique = true)
    private String code;//供应商品的编号，不能为null，唯一约束
    @Column(nullable = false)
    private String name;//供应商品的名称，不能为 null
    @Column(nullable = false,name="supp_code")
    private String suppCode;
    private String suppPhoto;
    @Column(nullable = false)
    private String type;//供应商品的类型，不能为null
    @Column(nullable = false,name = "total_inventory")
    private Long totalInventory;//供应商品的总价格，不能为null
    @Column(nullable = false,name = "unit_price")
    private Double unitPrice;//供应商品的单价，不能为null
    @Column(nullable = false)
    private String brand;//供应商品的品牌，不能为null
    @Column(nullable = false)
    private String addr;//供应商品的地址，不能为null
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;
    @Column(nullable = false)
    private String createEmp;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updateTime;
    private  String updateEmp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getSuppCode() {
        return suppCode;
    }

    public void setSuppCode(String suppCode) {
        this.suppCode = suppCode;
    }

    public String getSuppPhoto() {
        return suppPhoto;
    }

    public void setSuppPhoto(String suppPhoto) {
        this.suppPhoto = suppPhoto;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getTotalInventory() {
        return totalInventory;
    }

    public void setTotalInventory(Long totalInventory) {
        this.totalInventory = totalInventory;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateEmp() {
        return createEmp;
    }

    public void setCreateEmp(String createEmp) {
        this.createEmp = createEmp;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateEmp() {
        return updateEmp;
    }

    public void setUpdateEmp(String updateEmp) {
        this.updateEmp = updateEmp;
    }

    @Override
    public String toString() {
        return "SupplierWares{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", suppCode='" + suppCode + '\'' +
                ", suppPhoto='" + suppPhoto + '\'' +
                ", type='" + type + '\'' +
                ", totalInventory=" + totalInventory +
                ", unitPrice=" + unitPrice +
                ", brand='" + brand + '\'' +
                ", addr='" + addr + '\'' +
                ", createTime=" + createTime +
                ", createEmp='" + createEmp + '\'' +
                ", updateTime=" + updateTime +
                ", updateEmp='" + updateEmp + '\'' +
                '}';
    }
}
