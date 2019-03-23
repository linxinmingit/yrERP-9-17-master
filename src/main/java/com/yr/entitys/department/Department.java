package com.yr.entitys.department;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 部门实体类!
 */
@Entity//标记实体类
@Table(name = "department")//部门表名
public class Department implements Serializable{
    @Id//主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//部门id
    private String  code;//部门编号
    private String name;//部门昵称
    @Column(name = "sup_code",nullable = false)
    private String supCode;//父级部门编号
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;//创建时间
    private String createEmp;//创建人
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updateTime;//修改时间
    private String updateEmp;//修改人

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getCreateEmp() {
        return createEmp;
    }

    public void setCreateEmp(String createEmp) {
        this.createEmp = createEmp;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", supCode='" + supCode + '\'' +
                ", createTime=" + createTime +
                ", createEmp='" + createEmp + '\'' +
                ", updateTime=" + updateTime +
                ", updateEmp='" + updateEmp + '\'' +
                '}';
    }
}

