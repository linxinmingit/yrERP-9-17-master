package com.yr.department.dao.impl;

import com.yr.department.dao.DepartmentDao;
import com.yr.entitys.bo.departmentBo.Departmentbo;
import com.yr.entitys.department.Department;
import com.yr.entitys.page.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门Dao接口实现类
 */
@Repository("departmentDaoImpl")
public class DepartmentDaoImpl implements DepartmentDao {

    @PersistenceContext //@PersistenceContext 注解来标记成员变量
    private EntityManager entityManager;

    /**
     * 查询所有数据 返回list
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Department> query (){
        String sql= "select d from Department d where 1=1";
        Query query = entityManager.createQuery(sql);
        List<Department>list=query.getResultList();
        return list;
    }

    @Override
    public List<Departmentbo> queryDepartmentbo(Page<Departmentbo> page) {
        String jpql = "select d from Department d where 1 = 1 ";
        if(!StringUtils.isEmpty(page.getT().getDepaNameOrCode())){//判断是否为null和空
            jpql += "and d.name like :name or d.code like :code ";
        }
        Query query = entityManager.createQuery(jpql);
        if(!StringUtils.isEmpty(page.getT().getDepaNameOrCode())){
            query.setParameter("name", "%"+page.getT().getDepaNameOrCode()+"%");
            query.setParameter("code","%"+page.getT().getDepaNameOrCode()+"%");
        }
        query.setFirstResult(page.getStart()).setMaxResults(page.getPageSize());//查询分页
        List<Departmentbo> list = query.getResultList();//获得分页后的数据集合
        return list;
    }

    @Override
    public Department getByCode(String code) {
        String jpql = "select d from Department d where d.code = ? ";
        Query query = entityManager.createQuery(jpql);
        query.setParameter(1,code);
        Department department = (Department) query.getSingleResult();
        return department;
    }

    /**
     * 查询总数
     * @return
     */
    @Override
    public Long departmentCount(Page<Departmentbo> page) {
        String jpql = "select count(*) from Department d where 1 = 1 ";
        if(!StringUtils.isEmpty(page.getT().getDepaNameOrCode())){//判断是否为null和空
            jpql += "and d.name like :name or d.code like :code ";
        }
        Query query = entityManager.createQuery(jpql);
        if(!StringUtils.isEmpty(page.getT().getDepaNameOrCode())){
            query.setParameter("name", "%"+page.getT().getDepaNameOrCode()+"%");
            query.setParameter("code","%"+page.getT().getDepaNameOrCode()+"%");
        }
        Long count = (Long)query.getSingleResult();//获取到结果
        return count;
    }


    /**
     * 根据ID查询部门 并回显
     */
    @Override
    public Department departmentId(Integer id) {
        Department department=entityManager.find(Department.class, id);

        return department;
    }

    /**
     * 新增部门
     */
    @Override
    public void add(Department department) {
        entityManager.persist(department);
    }

    /**
     * 删除 和批量删除
     * @param id
     */
    public void delete(Integer [] id){
        List<Integer> list = Arrays.asList(id);//将数组转成list
        String jpql = "delete from Department u where u.id in(:ids)";
        Query query = entityManager.createQuery(jpql).setParameter("ids",list);
        query.executeUpdate();
    }

    /**
     * 修改部门
     */
    @Override
    public void update(Department department) {
        entityManager.merge(department);
    }

    /**
     * 查询部门编号,提供给用户调用
     * @param
     * @return
     */
    public Map<String,Object> querys(){
        String sql="select d from Department d where 1 = 1";
        List<Department> list = entityManager.createQuery(sql).getResultList();
        Map<String,Object>map=new HashMap<>();
        for (Department department : list) {
            map.put( department.getCode(),department.getName() );//编号key 名字为值
        }
        return map;
    }

    /**
     * 根据部门名字返回部门编号
     * @param depaName
     * @return
     */
    public String getDepaCode(String depaName){
        String sql = "select d.code from Department d where name = ?1";
        String name = (String) entityManager.createQuery(sql).setParameter(1,depaName).getSingleResult();
        return name;
    }
}
