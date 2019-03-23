package com.yr.entitys.depot;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.xml.crypto.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 仓库模块实体类!
 */
@Entity
public class Depot implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//仓库id
    @Column(unique = true, nullable = false)//设置为唯一约束，并且是不为null
    private String code;//仓库编号
    @Column(unique = true, nullable = false)//设置为唯一约束，并且是不为null
    private String name;//仓库名称
    private String addr;//仓库存放地址
    private String jobnum;//仓库负责人工号
    //定义时间格式	自动更新时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;//仓库数据创建时间
    private String createEmp;//仓库数据创建人
    //定义时间格式	自动更新时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updateTime;//仓库数据修改时间
    private String updateEmp;//仓库数据修改人

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

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getJobnum() {
        return jobnum;
    }

    public void setJobnum(String jobnum) {
        this.jobnum = jobnum;
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
        return "Depot{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", addr='" + addr + '\'' +
                ", jobnum='" + jobnum + '\'' +
                ", createTime=" + createTime +
                ", createEmp='" + createEmp + '\'' +
                ", updateTime=" + updateTime +
                ", updateEmp='" + updateEmp + '\'' +
                '}';
    }
}
