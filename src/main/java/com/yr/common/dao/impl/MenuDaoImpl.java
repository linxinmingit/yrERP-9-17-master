package com.yr.common.dao.impl;

import com.yr.common.dao.MenuDao;
import com.yr.entitys.bo.menuBO.MenuBO;
import com.yr.entitys.menu.Menu;
import com.yr.entitys.page.Page;
import com.yr.entitys.user.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.awt.SystemColor.menu;

@Repository
public class MenuDaoImpl implements MenuDao {
    //如何获取到和当前事务关联的 EntityManager 对象呢 ?通过 @PersistenceContext 注解来标记成员变量!
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Long queryCount(Page<MenuBO> page) {
        String jpql = "select count(*) from Menu m where 1 = 1 ";
        if(!StringUtils.isEmpty(page.getT().getMenu().getName())){//判断是否为null和空
            jpql += "and m.name like :name ";
        }
        Query query = entityManager.createQuery(jpql);
        if(!StringUtils.isEmpty(page.getT().getMenu().getName())){
            query.setParameter("name", "%"+page.getT().getMenu().getName()+"%");
        }
        Long count = (Long)query.getSingleResult();//获取到结果
        return count;
    }

    @Override
    public List<Menu> querySupMenu(Integer pid) {
        String jpql = "select m from Menu m where m.pid =?";
        Query query = entityManager.createQuery(jpql);
        query.setParameter(1,pid);
        List<Menu> list = query.getResultList();//获取到结果
        return list;
    }

    /*@Override
    public List<Menu> query(List<String> permissionList) {
        String jpql = "select m from Menu m";
        Query query = entityManager.createQuery(jpql);
        List<Menu> list = query.getResultList();//获取到结果
        return list;
    }
*/
    @Override
    public List<Menu> query(List<String> permissionList) {
        List<Menu> list = new ArrayList<>();
        for (String string:permissionList) {
            string = string.substring(1,string.length());
            Integer count = getCount(string);
            if(count != 0){
                String jpql = "select m from Menu m where m.url = :url";
                Query query = entityManager.createQuery(jpql).setParameter("url",string);
                Menu menu = (Menu)query.getSingleResult();//获取到结果
                list.add(menu);
            }
        }
        return list;
    }

    @Override
    public List<MenuBO> queryList(Page<MenuBO> page) {
        String jpql = "select m from Menu m where 1 = 1 ";
        if(!StringUtils.isEmpty(page.getT().getMenu().getName())){//判断是否为null和空
            jpql += "and m.name like :name ";
        }
        Query query = entityManager.createQuery(jpql);
        if(!StringUtils.isEmpty(page.getT().getMenu().getName())){
            query.setParameter("name", "%"+page.getT().getMenu().getName()+"%");
        }
        query.setFirstResult(page.getStart()).setMaxResults(page.getPageSize());//查询分页
        List<MenuBO> list = query.getResultList();//获取到结果
        return list;
    }

    @Override
    public Menu getOneMenu(Integer id) {
        Menu menu=entityManager.find(Menu.class,id);
        return menu;
    }

    @Override
    public void add(MenuBO menuBO,User loginUser) {
        menuBO.getMenu().setCreateEmp("刘柏江");
        menuBO.getMenu().setCreateTime(new Timestamp(System.currentTimeMillis()));
        menuBO.getMenu().setUpdateEmp("刘柏江");
        menuBO.getMenu().setUpdateTime(new Timestamp(System.currentTimeMillis()));
        menuBO.getMenu().setMethod("GET");//应为所有的菜单请求后台的方式只能是GET，所有这里写死了
        entityManager.persist(menuBO.getMenu());
    }

    //根据fontClass查询图标
    @Override
    public String queryIcon(String fontClass) {
        String sql = "select i.unicode from Icon i where i.fontClass=?";
        Query query = entityManager.createQuery(sql);
        query.setParameter(1,fontClass);
        String str = (String) query.getSingleResult();
        return str;
    }

    //查询图标unicode
    @Override
    public String queryIconUnicode(String str) {
        String sql = "select i.unicode from Icon i where i.fontClass=? or i.unicode=?";
        Query query = entityManager.createQuery(sql);
        query.setParameter(1,str);
        query.setParameter(2,str);
        String unicodeStr = (String) query.getSingleResult();
        return unicodeStr;
    }



    @Override
    public Long queryUrl(Menu menu) {
        String sql = "select count(m) from Menu m where m.url=? or m.name=?";
        Query query = entityManager.createQuery(sql);
        query.setParameter(1,menu.getUrl());
        query.setParameter(2,menu.getName());
        Long urlCount = (Long) query.getSingleResult();
        return urlCount;
    }

    @Override
    public void delete(Integer id) {
        Menu menu = entityManager.find(Menu.class,id);
        entityManager.remove(menu);
    }

    @Override
    public void update(Menu menu) {
        entityManager.merge(menu);
    }

    public Integer getCount(String url){
        String jpql = "select count(*) from Menu m where m.url = ?1";
        Number count = (Number)entityManager.createQuery(jpql).setParameter(1,url).getSingleResult();
        return count.intValue();
    }

}