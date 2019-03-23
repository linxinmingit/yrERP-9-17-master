package com.yr.entitys.order;

import com.yr.common.entity.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name="sale_order")
@Entity
@Cacheable(true)
public class SaleOrder extends BaseEntity implements Serializable{//销售订单表
    //销售订单id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    @Column(name="id")
    private Integer id;

    //销售订单编号(唯一)
    @Column(unique = true, nullable = false)//设置为唯一约束，并且是不为null
    private String code;

    //购买客户
    @Column(name = "customer_buy",nullable = false)//不为null
    private String customerBuy;

    //销售员
    @Column(nullable = false)//不为null
    private String salesperson;

    //销售商品编号
    @Column(name = "ware_code",nullable = false)//不为null
    private String wareCode;

    //销售商品数量
    @Column(nullable = false)//不为null
    private Long number;

    //销售总金额
    @Column(nullable = false)//不为null
    private Double money;//(商品的单价ware.outUnitPrice*销售商品的数量number)

    //销售员联系电话
    //@Pattern(Pattern="1[3|4|5|7|8][0-9]\\d{8}")   暂留，这里考虑使用springmvc做效验
    @Column(name = "s_phoneNumber",nullable = false)//不为null
    private String sPhoneNumber;

    //备注
    private String remark;

    //销售单状态（0退货，1交易成功）
    @Column(nullable = false)//不为null
    private Integer states;

    //收货人
    private String consignee;
    //审批人
    private String approver;

    //申请退货人姓名
    @Column(name = "requ_name")
    private String requName;

    //申请退货人联系电话
    @Column(name = "r_phoneNumber")
    private String rPhoneNumber;

    //销售商品的仓库编号
    @Column(name = "depot_code",nullable = false)//不为null
    private String depotCode;

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

    public String getCustomerBuy() {
        return customerBuy;
    }

    public void setCustomerBuy(String customerBuy) {
        this.customerBuy = customerBuy;
    }

    public String getSalesperson() {
        return salesperson;
    }

    public void setSalesperson(String salesperson) {
        this.salesperson = salesperson;
    }

    public String getWareCode() {
        return wareCode;
    }

    public void setWareCode(String wareCode) {
        this.wareCode = wareCode;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getsPhoneNumber() {
        return sPhoneNumber;
    }

    public void setsPhoneNumber(String sPhoneNumber) {
        this.sPhoneNumber = sPhoneNumber;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStates() {
        return states;
    }

    public void setStates(Integer states) {
        this.states = states;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public String getRequName() {
        return requName;
    }

    public void setRequName(String requName) {
        this.requName = requName;
    }

    public String getrPhoneNumber() {
        return rPhoneNumber;
    }

    public void setrPhoneNumber(String rPhoneNumber) {
        this.rPhoneNumber = rPhoneNumber;
    }

    public String getDepotCode() {
        return depotCode;
    }

    public void setDepotCode(String depotCode) {
        this.depotCode = depotCode;
    }

    @Override
    public String toString() {
        return "SaleOrder{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", customerBuy='" + customerBuy + '\'' +
                ", salesperson='" + salesperson + '\'' +
                ", wareCode='" + wareCode + '\'' +
                ", number=" + number +
                ", money=" + money +
                ", sPhoneNumber='" + sPhoneNumber + '\'' +
                ", remark='" + remark + '\'' +
                ", states=" + states +
                ", consignee='" + consignee + '\'' +
                ", approver='" + approver + '\'' +
                ", requName='" + requName + '\'' +
                ", rPhoneNumber='" + rPhoneNumber + '\'' +
                ", depotCode='" + depotCode + '\'' +
                '}';
    }
}