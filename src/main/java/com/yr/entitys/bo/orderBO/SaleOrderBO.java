package com.yr.entitys.bo.orderBO;

import com.yr.entitys.depot.Ware;
import com.yr.entitys.order.SaleOrder;
import com.yr.entitys.user.User;

import java.io.Serializable;
import java.util.List;

public class SaleOrderBO implements Serializable{//销售的业务操作
    private SaleOrder saleOrder;
    //销售订单编号
    private String code;
    //购买客户
    private String customerBuy;
    //销售总额
    private Double salePrice;
    //销售数量
    private Long number;
    //当前登录用户
    private User loginUser;

    //订单的类型，用于新增/修改页面
    private String orderType;

    //仓库商品list
    private List<Ware> wareList;

    //登录用户名
    private String loginName;

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
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
    public SaleOrder getSaleOrder() {
        return saleOrder;
    }

    public void setSaleOrder(SaleOrder saleOrder) {
        this.saleOrder = saleOrder;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public User getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(User loginUser) {
        this.loginUser = loginUser;
    }

    public List<Ware> getWareList() {
        return wareList;
    }

    public void setWareList(List<Ware> wareList) {
        this.wareList = wareList;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
}