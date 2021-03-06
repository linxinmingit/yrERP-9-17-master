package com.yr.user.dao;

import com.yr.entitys.bo.user.PermissionBo;
import com.yr.entitys.bo.user.RoleBo;
import com.yr.entitys.page.Page;
import com.yr.entitys.user.Permission;
import com.yr.entitys.user.Role;

import java.util.List;
import java.util.Map;

public interface PermissionDao {

    /**
     * 查询总条数
     * @param page
     * @return Integer
     */
    Long getCount(Page<PermissionBo> page);//@Param指定的是别名

    /**
     * 分页的形式查询权限表的数据
     * @return List<PermissionBo>
     */
    List<PermissionBo> query(Page<PermissionBo> page);

    /**
     * 添加权限信息
     * @param permissionBo
     */
    void add(PermissionBo permissionBo);

    /**
     * 修改权限信息
     * @param permission
     */
    void update(Permission permission);

    /**
     * 删除权限信息
     * @param id
     */
    void delete(Integer[] id);

    /**
     * 根据id查询权限数据
     * @param id
     * @return Permission
     */
    Permission getById(Integer id);

    /**
     * 回显角色具有哪些权限
     */
    List<PermissionBo> getPermission(Integer id);

    /**
     * 根据父权限获得子权限
     * @param rid 角色id
     * @param pid 权限id
     * @return List<PermissionBo>
     */
    List<PermissionBo> getchildren(Integer rid, Integer pid);

    /**
     * 根据子id获取所有父级id
     * @param id
     * @return Permission
     */
    Permission getParent(Integer id);

    /**
     * 返回一个所有权限的map集合，键为权限的id,值为权限的名字
     * @return Map<String,Object>
     */
    Map<Integer,Object> getPermission();

    /**
     * 返回所有角色列表
     * @return List<Permission>
     */
    List<Permission> getRoleList();
}
