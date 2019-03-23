package com.yr.entitys.bo.menuBO;

import com.yr.common.entity.BaseEntity;
import com.yr.entitys.menu.Menu;

import java.util.List;

public class MenuBO {
    private Integer id;//id
    private String title;//名称
    private String icon;//图标
    private String href;//url
    private Integer pid;
    private Boolean spread;//是否展开
    private String createTime;//创建时间
    private String createEmp;//创建人
    private String updateTime;//修改时间
    private String updateEmp;//修改人
    private Menu menu;
    private List<MenuBO> children;//子菜单集合
    private String method;//url的请求方式（要控制大写）

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Boolean getSpread() {
        return spread;
    }

    public void setSpread(Boolean spread) {
        this.spread = spread;
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

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateEmp() {
        return updateEmp;
    }

    public void setUpdateEmp(String updateEmp) {
        this.updateEmp = updateEmp;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public List<MenuBO> getChildren() {
        return children;
    }

    public void setChildren(List<MenuBO> children) {
        this.children = children;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "MenuBO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", icon='" + icon + '\'' +
                ", href='" + href + '\'' +
                ", pid=" + pid +
                ", spread=" + spread +
                ", createTime='" + createTime + '\'' +
                ", createEmp='" + createEmp + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", updateEmp='" + updateEmp + '\'' +
                ", menu=" + menu +
                ", children=" + children +
                ", method='" + method + '\'' +
                '}';
    }
}
