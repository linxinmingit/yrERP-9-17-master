package com.yr.entitys.depot;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 商品类型实体类
 */
@Entity
@Table(name = "ware_type")
public class WareType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//主键自动增长
    private Integer id;
    @Column(nullable = false,unique = true)
    private String code;//商品类型的编号，不能为null，唯一约束
    @Column(nullable = false,unique = true)
    private String name;//商品类型的名称，不能为null，唯一约束
    @Column(name = "sup_code",nullable = false)
    private String supCode;//上级商品类型编号
    @Column(nullable = false,columnDefinition = "DATE")
    private String createTime;//
    @Column(nullable = false)
    private String createEmp;//创建人
    @Column(columnDefinition = "DATE")
    private String updateTime;//修改时间
    private  String updateEmp;//修改人

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
        return "WareType{" +
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
