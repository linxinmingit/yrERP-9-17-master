package com.yr.entitys.bo.depotBo;

import javax.persistence.Column;

/**
 * 仓库类型的对外拓展类
 */
public class WareTypeBo {
    private String code;
    private String name;
    private String supCode;//上级商品类型编号

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
        return "WareTypeBo{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", supCode='" + supCode + '\'' +
                '}';
    }
}
