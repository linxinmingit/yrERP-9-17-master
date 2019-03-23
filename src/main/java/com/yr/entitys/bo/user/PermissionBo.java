package com.yr.entitys.bo.user;

import com.yr.entitys.user.Permission;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限的操作类
 */
public class PermissionBo{
    /**
     * 是否勾选
     */
    private String mark;
    /**
     * role Id
     */
    private String roleId;
    private List<PermissionBo> permissionBos = new ArrayList<>();
    private Permission permission;

    /**
     * 判断是否相等，返回true为勾选，false为没有勾选
     * @return boolean
     */
    public boolean ischeck(){
        return mark.equals(roleId);
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public List<PermissionBo> getPermissionBos() {
        return permissionBos;
    }

    public void setPermissionBos(List<PermissionBo> permissionBos) {
        this.permissionBos = permissionBos;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        return "PermissionBo{" +
                "mark='" + mark + '\'' +
                ", roleId='" + roleId + '\'' +
                ", permissionBos=" + permissionBos +
                ", permission=" + permission +
                '}';
    }
}
