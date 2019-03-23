package com.yr.entitys.Log;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yr.common.entity.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "log")
public class Log extends BaseEntity implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;

    //产生日志的模块
    @Column(nullable = false)
    private String modular;
    /**
     *
     */ //产生日志的表名
    @Column(nullable = false,name = "`table`")

    private String table;

    //模块的操作类型（0抛异常，1新增，2删除，3修改，4用户登录，5用户退出）
    @Column(nullable = false)
    private Integer type;

    //操作的字段旧值（多个字段使用：字段名1=值1，字段名2=值2...）格式，中间用，隔开
    private String fieldOldValue;

    //操作的字段新值（多个字段使用：字段名1=值1，字段名2=值2...）格式，中间用，隔开
    private String fieldNewValue;

    //如果有异常，记录异常内容
    private String  content;
    
    public void setId(Integer id) {
        this.id = id;
    }

    public void setModular(String modular) {
        this.modular = modular;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setFieldOldValue(String fieldOldValue) {
        this.fieldOldValue = fieldOldValue;
    }

    public void setFieldNewValue(String fieldNewValue) {
        this.fieldNewValue = fieldNewValue;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public Integer getId() {
        return id;
    }

    public String getModular() {
        return modular;
    }

    public String getTable() {
        return table;
    }

    public Integer getType() {
        return type;
    }

    public String getFieldOldValue() {
        return fieldOldValue;
    }

    public String getFieldNewValue() {
        return fieldNewValue;
    }

    public String getContent() {
        return content;
    }

    public Log() {
    }

    public Log(String modular, String table, Integer type, String fieldOldValue, String fieldNewValue, String content) {
        this.modular = modular;
        this.table = table;
        this.type = type;
        this.fieldOldValue = fieldOldValue;
        this.fieldNewValue = fieldNewValue;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", modular='" + modular + '\'' +
                ", table='" + table + '\'' +
                ", type=" + type +
                ", fieldOldValue='" + fieldOldValue + '\'' +
                ", fieldNewValue='" + fieldNewValue + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
