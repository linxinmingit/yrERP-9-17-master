package com.yr.entitys.user;

import com.yr.common.entity.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * 权限实体类
 */
@Entity
@Table(name="u_permission")
public class Permission extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)//不为null
    private String url;//权限url
    @Column(nullable = false)
    private String name;//权限名字
    @Column(nullable = false)
    private String method;//权限方法
    @Column(name="sup_id",nullable = false)
    private Integer supId;//上级id

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Integer getSupId() {
        return supId;
    }

    public void setSupId(Integer supId) {
        this.supId = supId;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", method='" + method + '\'' +
                ", supId=" + supId +
                '}';
    }
}
