package com.yr.entitys.bo.depotBo;

/**
 * 仓库商品对外拓展类
 */
public class WareBo {
    private String type;
    private String code;
    private String name;
    private String addr;
    private Double minOutUnitPrice;
    private Double maxOutUnitPrice;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Double getMinOutUnitPrice() {
        return minOutUnitPrice;
    }

    public void setMinOutUnitPrice(Double minOutUnitPrice) {
        this.minOutUnitPrice = minOutUnitPrice;
    }

    public Double getMaxOutUnitPrice() {
        return maxOutUnitPrice;
    }

    public void setMaxOutUnitPrice(Double maxOutUnitPrice) {
        this.maxOutUnitPrice = maxOutUnitPrice;
    }

    @Override
    public String toString() {
        return "WareBo{" +
                "type='" + type + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", addr='" + addr + '\'' +
                ", minOutUnitPrice=" + minOutUnitPrice +
                ", maxOutUnitPrice=" + maxOutUnitPrice +
                '}';
    }
}
