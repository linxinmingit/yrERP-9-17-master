package com.yr.entitys.user;

import com.yr.common.entity.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 角色实体类
 */
@Entity
@Table(name = "u_role")
public class Role extends BaseEntity implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, nullable = false)//设置为唯一约束，并且是不为null
    private String code;//角色编号
    @Column(unique = true, nullable = false)
    private String name;//角色名
    @JoinTable(name="u_role_permission",//中间表的名字
            joinColumns={ @JoinColumn(name="rid",referencedColumnName="id")},//name连接字段的名字,该字段对应本实体类的字段(默认是id)
            inverseJoinColumns={@JoinColumn(name="pid",referencedColumnName="id")})//另一个连接字段的名字,对应实体类的字段(默认是id)
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)//放弃本端的维护，使用role维护
    private Set<Permission> permission;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Set<Permission> getPermission() {
        return permission;
    }

    public void setPermission(Set<Permission> permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
