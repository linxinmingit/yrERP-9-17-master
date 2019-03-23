package com.yr.user.dao.impl;

import com.yr.entitys.bo.user.PermissionBo;
import com.yr.entitys.page.Page;
import com.yr.entitys.user.Permission;
import com.yr.entitys.user.Role;
import com.yr.user.dao.PermissionDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.security.Permissions;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("permissionDaoImpl")
public class PermissionDaoImpl implements PermissionDao {
    //如何获取到和当前事务关联的 EntityManager 对象呢 ?通过 @PersistenceContext 注解来标记成员变量!
    @PersistenceContext
    private EntityManager entityManager;


    /**
     * 查询总条数
     * @param page
     * @return Integer
     */
    public Long getCount(Page<PermissionBo> page){//@Param指定的是别名
        String jpql = "select count(*) from Permission p where 1 = 1 ";
        if(!StringUtils.isEmpty(page.getT().getPermission().getName())){//判断是否为null和空
            jpql += "and p.name like :name ";
        }
        if(!StringUtils.isEmpty(page.getT().getPermission().getUrl())){
            jpql += "and p.url like :url ";
        }
        if(!StringUtils.isEmpty(page.getT().getPermission().getMethod())){//最小年龄
            jpql += "and p.method like :method ";
        }
        Query query = entityManager.createQuery(jpql);
        if(!StringUtils.isEmpty(page.getT().getPermission().getName())){
            query.setParameter("name", "%"+page.getT().getPermission().getName()+"%");
        }
        if(!StringUtils.isEmpty(page.getT().getPermission().getUrl())){
            query.setParameter("url", "%"+page.getT().getPermission().getUrl()+"%");
        }
        if(!StringUtils.isEmpty(page.getT().getPermission().getMethod())){//最小年龄
            query.setParameter("method", "%"+page.getT().getPermission().getMethod()+"%");
        }
        Long count = (Long)query.getSingleResult();//获取到结果
        return count;//将long转int
    }

    /**
     * 分页的形式查询权限表的数据
     * @return List<PermissionBo>
     */
    public List<PermissionBo> query(Page<PermissionBo> page){
        String jpql = "select p from Permission p where 1 = 1 ";
        if(!StringUtils.isEmpty(page.getT().getPermission().getName())){//判断是否为null和空
            jpql += "and p.name like :name ";
        }
        if(!StringUtils.isEmpty(page.getT().getPermission().getUrl())){
            jpql += "and p.url like :url ";
        }
        if(!StringUtils.isEmpty(page.getT().getPermission().getMethod())){//最小年龄
            jpql += "and p.method like :method ";
        }
        /*if(page.getT().getOrder() == 0){
            jpql += "order by u.id asc ";
        }else if(page.getT().getOrder() == 1){
            jpql += "order by u.id desc ";
        }*/
        Query query = entityManager.createQuery(jpql);
        if(!StringUtils.isEmpty(page.getT().getPermission().getName())){
            query.setParameter("name", "%"+page.getT().getPermission().getName()+"%");
        }
        if(!StringUtils.isEmpty(page.getT().getPermission().getUrl())){
            query.setParameter("url", "%"+page.getT().getPermission().getUrl()+"%");
        }
        if(!StringUtils.isEmpty(page.getT().getPermission().getMethod())){//最小年龄
            query.setParameter("method", "%"+page.getT().getPermission().getMethod()+"%");
        }
        query.setFirstResult(page.getStart()).setMaxResults(page.getPageSize());//查询分页
        List<PermissionBo> list = query.getResultList();//获得分页后的数据集合
        return list;
    }

    /**
     * 添加权限信息
     * @param permissionBo
     */
    public void add(PermissionBo permissionBo){
        entityManager.persist(permissionBo.getPermission());
    }

    /**
     * 修改用户信息
     * @param permission
     */
    public void update(Permission permission){
        entityManager.merge(permission);
    }

    /**
     * 批量删除权限信息
     * @param id
     */
    public void delete(Integer[] id){
        List<Integer> list = Arrays.asList(id);//将数组转成list
        String jpql = "delete from Permission u where u.id in(:ids)";
        Query query = entityManager.createQuery(jpql).setParameter("ids",list);
        query.executeUpdate();
    }

    /**
     * 根据id查询权限数据
     * @param id
     * @return ole
     */
    public Permission getById(Integer id){
        Permission permission = entityManager.find(Permission.class,id);
        return permission;
    }

    /**
     * 回显角色具有哪些权限
     */
    public List<PermissionBo> getPermission(Integer id){
        String sql = "select p.id,rp.rid roleId,p.name,p.url,p.sup_id,ifnull(rp.rid,0) mark from u_permission p left join (select * from u_role_permission where rid = 1) rp on p.id = rp.pid";
        Query query = entityManager.createNativeQuery(sql).setParameter(1,id);
        List<PermissionBo> permissions = query.getResultList();
        return permissions;
    }

    /**
     * 根据父权限获得子权限
     * @param rid 角色id
     * @param pid 权限id
     * @return List<PermissionBo>
     */
    public List<PermissionBo> getchildren(Integer rid, Integer pid){
        String sql = "select p.id,rp.rid roleId,p.name,p.url,p.sup_id,ifnull(rp.rid,0) mark from (select * from u_permission where sup_id = ?1) p left join (select * from u_role_permission where rid = ?2) rp on p.id = rp.pid";
        Query query = entityManager.createQuery(sql).setParameter(1,pid).setParameter(2,rid);
        List<PermissionBo> permissions = query.getResultList();
        return permissions;
    }

    /**
     * 根据子id获取所有父级id
     * @param id
     * @return Permission
     */
    public Permission getParent(Integer id){
        String sql = "select * from u_permission where id = (select sup_id from u_permission where id = ?1)";
        Query query = entityManager.createNativeQuery(sql).setParameter(1,id);
        Permission permission = (Permission)query.getSingleResult();
        return permission;
    }

    /**
     * 返回一个所有权限的map集合，键为权限的id,值为权限的名字
     * @return Map<String,Object>
     */
    public Map<Integer,Object> getPermission(){
        String jpql = "select p from Permission p";
        List<Permission> permissions = entityManager.createQuery(jpql).getResultList();
        Map<Integer,Object> map = new HashMap<>();
        for (Permission permission:permissions) {
            map.put(permission.getId(),permission.getName());
        }
        map.put(0,"无");
        return map;
    }

    /**
     * 返回所有角色列表
     * @return List<Permission>
     */
    public List<Permission> getRoleList(){
        String jpql = "select p from Permission p";
        List<Permission> list = entityManager.createQuery(jpql).getResultList();
        return list;
    }
}
