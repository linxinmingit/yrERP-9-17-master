package com.yr.department.dao;

import java.util.List;
import java.util.Map;

import com.yr.entitys.bo.departmentBo.Departmentbo;
import com.yr.entitys.department.Department;
import com.yr.entitys.page.Page;
import com.yr.entitys.user.User;

/**
 * 部门Dao接口类
 */
public interface DepartmentDao {

   /**
     * 查询所有
     * @param
     * @return
     */
     List<Department>query();

 /**
  * 分页查询
  * @param page
  * @return
  */
    List<Departmentbo> queryDepartmentbo(Page<Departmentbo> page);

    /**
     * 根据编号查询部门名称
     * @param code
     * @return
     */
    Department getByCode(String code);


     /**
     * 查询总条数
     * @return Integer
     */
    Long departmentCount(Page<Departmentbo> page);

    /**
     * 根据ID查询部门 并回显
     * @return
     */
    Department departmentId(Integer id);

    /**
     * 新增部门
     * @param department
     */
    void add(Department department);

    /**
     * 删除部门
     * @param id
     */
    void delete(Integer[] id);

    /**
     * 修改部门
     * @param department
     */
    void update(Department department);

    /**
     * 查询部门编号,提供给用户调用
     * @param code
     * @return
     */
    Map<String,Object>querys();

    /**
     * 根据部门名字返回部门编号
     * @param depaName
     * @return
     */
    String getDepaCode(String depaName);
}
