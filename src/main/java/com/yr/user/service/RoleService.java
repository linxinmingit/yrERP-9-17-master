package com.yr.user.service;

import com.yr.entitys.bo.user.RoleBo;
import com.yr.entitys.page.Page;
import com.yr.entitys.user.Role;

import java.util.List;

public interface RoleService {

    /**
     * 分页的形式查询role表的数据
     * @param page
     * */
    String query(Page<RoleBo> page);

    /**
     * 添加角色信息
     * @param role
     */
    void add(Role role);

    /**
     * 修改角色信息
     * @param role
     */
    void update(Role role);

    /**
     * 删除角色信息
     * @param id
     */
    void delete(Integer[] id);

    /**
     * 根据id查询角色数据
     * @param id
     * @return Role
     */
    Role getById(Integer id);

    /**
     * 给角色赋权限
     * @param id
     * @param permissionIds
     */
    void setPermissions(Integer id,Integer[] permissionIds);

    /**
     * 返回所有角色列表
     * @return String
     */
    String getRoleList();

    /**
     * 根据角色获取所有的权限
     * @param id
     * @return String
     */
    String getRole(Integer id);


}
