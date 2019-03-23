package com.yr.entitys.bo.user;


import com.yr.entitys.user.Role;

public class RoleBo {
    private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "RoleBO{" +
                "role=" + role +
                '}';
    }
}
