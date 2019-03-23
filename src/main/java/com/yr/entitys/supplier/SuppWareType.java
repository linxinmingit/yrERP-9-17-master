package com.yr.entitys.supplier;

import javax.persistence.*;

/**
 * 供应商品类型实体类
 */
@Entity
public class SuppWareType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false,unique = true)
    private String code;//供应商品类型的编号，不能为null，唯一约束
    @Column(nullable = false,unique = true)
    private String name;//供应商品类型的名称，不能为null，唯一约束
    @Column(nullable = false,name = "sup_code")
    private String supCode;//供应商品类型的父级的商品编号，不能为null
    @Column(nullable = false,columnDefinition = "DATE")
    private String createTime;//创建时间，不能为null
    @Column(nullable = false)
    private String createEmp;//创建人，不能为null
    @Column(columnDefinition = "DATE")
    private String updateTime;
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

    public String getSupCode() {
        return supCode;
    }

    public void setSupCode(String supCode) {
        this.supCode = supCode;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateEmp() {
        return createEmp;
    }

    public void setCreateEmp(String createEmp) {
        this.createEmp = createEmp;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
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
        return "SuppWareType{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", supCode='" + supCode + '\'' +
                ", createTime='" + createTime + '\'' +
                ", createEmp='" + createEmp + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", updateEmp='" + updateEmp + '\'' +
                '}';
    }
}
