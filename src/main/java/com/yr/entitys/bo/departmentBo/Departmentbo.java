package com.yr.entitys.bo.departmentBo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yr.entitys.department.Department;

import java.io.Serializable;

//部门业务拓展类
public class Departmentbo implements Serializable{
    private Integer id;//id
    private String name;//名称
    private String  code;//部门编号
    private String supCode;//父级部门编号
    private String createTime;//创建时间
    private String createEmp;//创建人
    private String updateTime;//修改时间
    private String updateEmp;//修改人
    private Department department;//部门
    private String depaNameOrCode;//部门名/编号

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getDepaNameOrCode() {
        return depaNameOrCode;
    }

    public void setDepaNameOrCode(String depaNameOrCode) {
        this.depaNameOrCode = depaNameOrCode;
    }

    @Override
    public String toString() {
        return "Departmentbo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", supCode='" + supCode + '\'' +
                ", createTime='" + createTime + '\'' +
                ", createEmp='" + createEmp + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", updateEmp='" + updateEmp + '\'' +
                ", department=" + department +
                ", depaNameOrCode='" + depaNameOrCode + '\'' +
                '}';
    }
}
