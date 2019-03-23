package com.yr.user.dao.impl;

import com.yr.entitys.bo.user.RoleBo;
import com.yr.entitys.page.Page;
import com.yr.entitys.user.Permission;
import com.yr.entitys.user.Role;
import com.yr.user.dao.RoleDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository("roleDaoImpl")
public class RoleDaoImpl implements RoleDao {

    //如何获取到和当前事务关联的 EntityManager 对象呢 ?通过 @PersistenceContext 注解来标记成员变量!
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 查询总条数
     * @param page
     * @return Integer
     */
    public Long getCount(Page<RoleBo> page){//@Param指定的是别名
        String jpql = "select count(*) from Role r where 1 = 1 ";
        if(!StringUtils.isEmpty(page.getT().getRole().getName())){//判断是否为null和空
            jpql += "and r.name like :name ";
        }
        /*if(!StringUtils.isEmpty(page.getT().getStartBirthday())){
            jpql += "and u.birthday >= :startBirthday ";
        }
        if(!StringUtils.isEmpty(page.getT().getEndBirthday())){
            jpql += "and u.birthday <= :endBirthday ";
        }
        if(!StringUtils.isEmpty(page.getT().getUser().getAddr())){
            jpql += "and u.addr like :addr ";
        }*/
        Query query = entityManager.createQuery(jpql);
        if(!StringUtils.isEmpty(page.getT().getRole().getName())){
            query.setParameter("name", "%"+page.getT().getRole().getName()+"%");
        }
        /*if(!StringUtils.isEmpty(page.getT().getStartBirthday())){
            query.setParameter("startBirthday", DateUtils.toDate(page.getT().getStartBirthday()));
        }
        if(!StringUtils.isEmpty(page.getT().getEndBirthday())){
            query.setParameter("endBirthday", DateUtils.toDate(page.getT().getEndBirthday()));
        }
        if(!StringUtils.isEmpty(page.getT().getUser().getAddr())){
            query.setParameter("addr", "%"+page.getT().getUser().getAddr()+"%");
        }*/
        Long count = (Long)query.getSingleResult();//获取到结果
        return count;//将long转int
    }

    /**
     * 分页的形式查询角色表的数据
     * @return List<RoleBo>
     */
    public List<RoleBo> query(Page<RoleBo> page){
        String jpql = "select r from Role r where 1 = 1 ";
        if(!StringUtils.isEmpty(page.getT().getRole().getName())){//判断是否为null和空
            jpql += "and r.name like :name ";
        }
        /*if(!StringUtils.isEmpty(page.getT().getStartBirthday())){
            jpql += "and u.birthday >= :startBirthday ";
        }
        if(!StringUtils.isEmpty(page.getT().getEndBirthday())){
            jpql += "and u.birthday <= :endBirthday ";
        }
        if(!StringUtils.isEmpty(page.getT().getUser().getAddr())){
            jpql += "and u.addr like :addr ";
        }
        if(page.getT().getOrder() == 0){
            jpql += "order by u.id asc ";
        }else if(page.getT().getOrder() == 1){
            jpql += "order by u.id desc ";
        }*/
        Query query = entityManager.createQuery(jpql);
        if(!StringUtils.isEmpty(page.getT().getRole().getName())){
            query.setParameter("name", "%"+page.getT().getRole().getName()+"%");
        }
       /* if(!StringUtils.isEmpty(page.getT().getStartBirthday())){
            query.setParameter("startBirthday", DateUtils.toDate(page.getT().getStartBirthday()));
        }
        if(!StringUtils.isEmpty(page.getT().getEndBirthday())){
            query.setParameter("endBirthday", DateUtils.toDate(page.getT().getEndBirthday()));//转sql date
        }
        if(!StringUtils.isEmpty(page.getT().getUser().getAddr())){
            query.setParameter("addr", "%"+page.getT().getUser().getAddr()+"%");
        }*/
        query.setFirstResult(page.getStart()).setMaxResults(page.getPageSize());//查询分页
        List<RoleBo> list = query.getResultList();//获得分页后的数据集合
        return list;
    }

    /**
     * 添加角色信息
     * @param role
     */
    public void add(Role role){
        entityManager.persist(role);
    }

    /**
     * 修改角色信息
     * @param role
     */
    public void update(Role role){
        entityManager.merge(role);
    }

    /**
     * 批量删除角色信息
     * @param id
     */
    public void delete(Integer[] id){
        List<Integer> list = Arrays.asList(id);//将数组转成list
        String jpql = "delete from Role r where r.id in(:ids)";
        Query query = entityManager.createQuery(jpql).setParameter("ids",list);
        query.executeUpdate();
    }

    /**
     * 根据id查询角色数据
     * @param id
     * @return ole
     */
    public Role getById(Integer id){
        Role role = entityManager.find(Role.class,id);
        return role;
    }

    /**
     * 删除权限关联表
     * @param id
     */
    public void deletePermissions(Integer id){
        Role role = entityManager.find(Role.class, id);//获得User对象
        Set<Permission> permission = role.getPermission();//获得user对象关联的role对象
        role.getPermission().removeAll(permission);//将关联集合删除掉
        entityManager.merge(role);
    }

    /**
     * 将角色关联权限
     * @param id
     * @param permissionIds
     */
    public void addPermissions(Integer id,Integer[] permissionIds){
        Role role = entityManager.find(Role.class,id);
        Set<Permission> permissions = new HashSet<>();
        for (int i=0;i<permissionIds.length;i++){
            Permission permission = entityManager.find(Permission.class,permissionIds[i]);
            permissions.add(permission);
        }
        role.setPermission(permissions);
        entityManager.merge(role);
    }

    /**
     * 返回所有角色列表
     * @return List<Role>
     */
    public List<Role> getRoleList(){
        String jpql = "select r from Role r where 1 = 1";
        List<Role> list = entityManager.createQuery(jpql).getResultList();
        return list;
    }

    /**
     * 根据角色获取所有的权限
     * @param id
     * @return List<Permission>
     */
    public List<Permission> getRole(Integer id){
        String jpql = "select p from Role r inner join r.permission p where r.id = ?1";
        List<Permission> list = entityManager.createQuery(jpql).setParameter(1,id).getResultList();
        return list;
    }
}
