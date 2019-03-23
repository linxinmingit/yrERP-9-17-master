package com.yr.entitys.bo.depotBo;

import com.yr.entitys.depot.Depot;

import java.io.Serializable;

/**
 * 仓库扩展类
 */
public class Depotbo implements Serializable{
    private Depot depot;//仓库实体类
    private String code;//仓库编号
    private String name;//仓库名
    private String addr;//地址
    private String jobnum;
    private String createTime;//创建时间
    private String createEmp;//创建人

    public Depot getDepot() {
        return depot;
    }

    public void setDepot(Depot depot) {
        this.depot = depot;
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

    public String getJobnum() {
        return jobnum;
    }

    public void setJobnum(String jobnum) {
        this.jobnum = jobnum;
    }

    @Override
    public String toString() {
        return "Depotbo{" +
                "depot=" + depot +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", addr='" + addr + '\'' +
                ", jobnum ='" + jobnum  + '\'' +
                ", createTime='" + createTime + '\'' +
                ", createEmp='" + createEmp + '\'' +
                '}';
    }
}
