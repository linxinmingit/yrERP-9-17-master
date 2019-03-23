package com.yr.user.dao;

import com.yr.entitys.bo.user.RoleBo;
import com.yr.entitys.page.Page;
import com.yr.entitys.user.Permission;
import com.yr.entitys.user.Role;

import java.util.List;

public interface RoleDao {

    /**
     * 查询总条数
     * @param page
     * @return Integer
     */
    Long getCount(Page<RoleBo> page);//@Param指定的是别名

    /**
     * 分页的形式查询角色表的数据
     * @return List<oleBo>
     */
    List<RoleBo> query(Page<RoleBo> page);

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
     * 删除权限关联表
     * @param id
     */
    void deletePermissions(Integer id);

    /**
     * 将角色关联权限
     * @param id
     * @param permissionIds
     */
    void addPermissions(Integer id,Integer[] permissionIds);

    /**
     * 返回所有角色列表
     * @return List<Role>
     */
    List<Role> getRoleList();

    /**
     * 根据角色获取所有的权限
     * @param id
     * @return List<Permission>
     */
    List<Permission> getRole(Integer id);
}
