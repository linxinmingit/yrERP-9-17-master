package com.yr.entitys.bo.user;

import com.yr.entitys.user.User;

import javax.persistence.Cacheable;
import java.io.Serializable;

public class UserBo implements Serializable{
    private static final long serialVersionUID = 1L;
    private Integer minAge;//最小年龄
    private Integer maxAge;//最大年龄
    private String depaName;//部门名字
    private User user;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDepaName() {
        return depaName;
    }

    public void setDepaName(String depaName) {
        this.depaName = depaName;
    }

    @Override
    public String toString() {
        return "UserBo{" +
                "minAge=" + minAge +
                ", maxAge=" + maxAge +
                ", user=" + user +
                ", depaName='" + depaName + '\'' +
                '}';
    }
}
