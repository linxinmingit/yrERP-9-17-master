package com.yr.entitys.menu;

import javax.persistence.*;

@Entity
@Table(name = "icon")
public class Icon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(unique = true, nullable = false)//设置为唯一约束，并且是不为null
    private String unicode;
    @Column(unique = true, nullable = false)//设置为唯一约束，并且是不为null
    private String fontClass;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnicode() {
        return unicode;
    }

    public void setUnicode(String unicode) {
        this.unicode = unicode;
    }

    public String getFontClass() {
        return fontClass;
    }

    public void setFontClass(String fontClass) {
        this.fontClass = fontClass;
    }

    @Override
    public String toString() {
        return "Icon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", unicode='" + unicode + '\'' +
                ", fontClass='" + fontClass + '\'' +
                '}';
    }
}
