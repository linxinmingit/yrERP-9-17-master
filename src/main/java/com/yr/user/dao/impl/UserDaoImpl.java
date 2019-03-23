package com.yr.user.dao.impl;


import com.yr.entitys.bo.user.UserBo;
import com.yr.entitys.page.Page;
import com.yr.entitys.user.Permission;
import com.yr.entitys.user.Role;
import com.yr.entitys.user.User;
import com.yr.user.dao.UserDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository("userDaoImpl")
public class UserDaoImpl implements UserDao {

    //如何获取到和当前事务关联的 EntityManager 对象呢 ?通过 @PersistenceContext 注解来标记成员变量!
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 查询总条数
     * @param page
     * @return Integer
     */
    public Long getCount(Page<UserBo> page){//@Param指定的是别名
        String jpql = "select count(*) from User u where 1 = 1 ";
        if(!StringUtils.isEmpty(page.getT().getUser().getName())){//判断是否为null和空
            jpql += "and u.name like :name ";
        }
        if(!StringUtils.isEmpty(page.getT().getUser().getDepaCode())){//部门编号
            jpql += "and u.depaCode like :depaCode ";
        }
        if(page.getT().getMinAge() != null){//最小年龄
            jpql += "and u.age >= :minAge ";
        }
        if(page.getT().getMaxAge() != null){//最大年龄
            jpql += "and u.age <= :maxAge ";
        }
        Query query = entityManager.createQuery(jpql);
        if(!StringUtils.isEmpty(page.getT().getUser().getName())){
            query.setParameter("name", "%"+page.getT().getUser().getName()+"%");
        }
        if(!StringUtils.isEmpty(page.getT().getUser().getDepaCode())){//部门编号
            query.setParameter("depaCode", "%"+page.getT().getUser().getDepaCode()+"%");
        }
        if(page.getT().getMinAge() != null){//最小年龄
            query.setParameter("minAge", page.getT().getMinAge());
        }
        if(page.getT().getMaxAge() != null){//最大年龄
            query.setParameter("maxAge", page.getT().getMaxAge());
        }
        Long count = (Long)query.getSingleResult();//获取到结果
        return count;
    }

    /**
     * 分页的形式查询user表的数据
     * @return List<User>
     */
    public List<User> query(Page<UserBo> page){
        String jpql = "select u from User u where 1 = 1 ";
        if(!StringUtils.isEmpty(page.getT().getUser().getName())){//判断是否为null和空
            jpql += "and u.name like :name ";
        }
        if(!StringUtils.isEmpty(page.getT().getUser().getDepaCode())){//部门编号
            jpql += "and u.depaCode like :depaCode ";
        }
        if(page.getT().getMinAge() != null){//最小年龄
            jpql += "and u.age >= :minAge ";
        }
        if(page.getT().getMaxAge() != null){//最大年龄
            jpql += "and u.age <= :maxAge ";
        }
        /*
        if(page.getT().getOrder() == 0){
            jpql += "order by u.id asc ";
        }else if(page.getT().getOrder() == 1){
            jpql += "order by u.id desc ";
        }*/
        Query query = entityManager.createQuery(jpql);
        if(!StringUtils.isEmpty(page.getT().getUser().getName())){
            query.setParameter("name", "%"+page.getT().getUser().getName()+"%");
        }
        if(!StringUtils.isEmpty(page.getT().getUser().getDepaCode())){//部门编号
            query.setParameter("depaCode", "%"+page.getT().getUser().getDepaCode()+"%");
        }
        if(page.getT().getMinAge() != null){//最小年龄
            query.setParameter("minAge", page.getT().getMinAge());
        }
        if(page.getT().getMaxAge() != null){//最大年龄
            query.setParameter("maxAge", page.getT().getMaxAge());
        }
        query.setFirstResult(page.getStart()).setMaxResults(page.getPageSize());//查询分页
        List<User> list = query.getResultList();//获得分页后的数据集合
        return list;
    }

    /**
     * 添加用户信息
     * @param user
     */
    public void add(User user){
        entityManager.persist(user);
    }

    /**
     * 修改用户信息
     * @param user
     */
    public void update(User user){
        entityManager.merge(user);
    }

    /**
     * 根据id查询用户数据
     * @param id
     * @return User
     */
    public User getById(Integer id){
        User user =  entityManager.find(User.class, id);
        return user;
    }

    /**
     * 根据用户id返回角色名集合
     * @param id
     * @return User
     */
    public List<String> getRoles(Integer id){
        String sql = "select * from (select u_user_role.rid from u_user_role where u_user_role.uid = ?1)ur inner join u_role r on ur.rid = r.id ";
        List<String> list = entityManager.createNativeQuery(sql).setParameter(1,id).getResultList();
        return list;
    }

    /**
     * 根据用户id返回权限集合
     * @param id
     * @return List<Permission>
     */
    public List<Permission> getPermissions(Integer id){
        String sql = "select p.id,p.method,p.name,p.url,p.sup_id,p.createEmp,p.createTime,p.updateEmp,p.updateTime from (select u_user_role.rid from u_user_role where u_user_role.uid = ?1)ur inner join u_role r on ur.rid = r.id inner join u_role_permission up on r.id = up.rid inner join u_permission p on up.pid = p.id";
        List<Permission> list = entityManager.createNativeQuery(sql,Permission.class).setParameter(1,id).getResultList();
        return list;
}

    /**
     * 根据用户id返回权限集合
     * @param id
     * @return List<Permission>
     */
    /*public List<Permission> getPermissions(Integer id){
        String jpql = "select p from User u inner join u.role r inner join r.permission p where u.id = ?1";
        List<Permission> list = entityManager.createQuery(jpql).setParameter(1,id).getResultList();
        return list;
    }*/



    /**
     * 删除角色关联表
     * @param id
     */
    public void deleteRoles(Integer id){
        User user = entityManager.find(User.class, id);//获得User对象
        Set<Role> role = user.getRole();//获得user对象关联的role对象
        user.getRole().removeAll(role);//从集合里删除
        entityManager.merge(user);//这里再将他修改user
    }

    /**
     * 将用户关联角色
     * @param id
     * @param roleIds
     */
    public void addRoles(Integer id,Integer[] roleIds){
        User user = entityManager.find(User.class,id);
        Set<Role> role = new HashSet<>();
        for (int i=0;i<roleIds.length;i++){
            Role role1 = entityManager.find(Role.class,roleIds[i]);
            role.add(role1);
        }
        user.setRole(role);
        entityManager.merge(user);
    }

    /**
     * 根据用户名获得User对象
     * @param name
     * @return User
     */
    public User getByName(String name){
        String jpql = "select u from User u where u.name = ?1";
        User user = (User)entityManager.createQuery(jpql).setParameter(1,name).getSingleResult();
        return user;
    }

    /**
     * 部门删除时调用,根据部门编号删除用户
     */
    public void delete(String department){
        String jpql = "select u from User u where depa_code = ?1";
        entityManager.createQuery(jpql).setParameter(1,department).executeUpdate();
    }

    /**
     * 用户进行批量删除
     * @param id
     */
    public void delete(Integer[] id){
        List<Integer> list = Arrays.asList(id);//将数组转成list
        String jpql = "delete from User u where u.id in(:ids)";
        Query query = entityManager.createQuery(jpql).setParameter("ids",list);
        query.executeUpdate();
    }

    /**
     * 修改用户的状态
     * @param id
     * @param state
     */
    public void updateState(Integer id, Integer state){
        String jpql;
        if(state == 1){//判断是否是停用
            jpql = "update User u set states=0 where u.id = ?1";
        }else{//否则就是启用
            jpql = "update User u set states=1 where u.id = ?1";
        }
        entityManager.createQuery(jpql).setParameter(1,id).executeUpdate();
    }

    /**
     * 根据员工工号查询user对象
     * @param jobNum
     * @return User
     */
    public User getByJobNum(String jobNum){
        String jpql = "select u from User u where u.jobNum = ?1";
        return (User)entityManager.createQuery(jpql).setParameter(1,jobNum).getSingleResult();
    }

    /**
     * 根据用户Id获取角色
     */
    public List<Role> getRole(Integer id){
        String jpql = "select r from User u inner join u.role r where u.id = ?1";
        //String jpql = "";
        List<Role> list = entityManager.createQuery(jpql).setParameter(1,id).getResultList();
        return list;
    }

    /**
     * 返回user数据集合
     * @return
     */
    public List<User> queryUser(){
        String jpql = "select u from User u";
        List<User> list = entityManager.createQuery(jpql).getResultList();
        return list;
    }
}
