package com.yr.supplier.dao.impl;

import com.yr.entitys.bo.supplierBO.SupplierBo;
import com.yr.entitys.bo.supplierBO.SupplierWareBo;
import com.yr.entitys.page.Page;
import com.yr.entitys.supplier.Supplier;
import com.yr.entitys.supplier.SupplierWares;
import com.yr.supplier.dao.SupplierWareDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

/**
 *供应商品dao层的实现类
 */
@Repository
public class SupplierWareDaoImpl implements SupplierWareDao {


    //如何获取到和当前事务关联的 EntityManager 对象呢 ?
    //通过 @PersistenceContext 注解来标记成员变量!
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 根据sw来给供应商品表中添加数据
     * @param sw
     * @return
     */
    @Override
    public void add(SupplierWareBo sw) {
        entityManager.persist(sw.getSupplierWare());
    }

    /**
     * 根据id到供应商品表中删除指定数据
     * @param id
     * @return
     */
    @Override
    public void delete(Integer id) {

       SupplierWares supplierWares=entityManager.find(SupplierWares.class,id);
       entityManager.remove(supplierWares);

    }

    /**
     * 根据sw到供应商品表中修改相应数据
     * @param sw
     * @return
     */
    @Override
    public void update(SupplierWareBo sw) {
        entityManager.merge(sw.getSupplierWare());
    }

    /**
     * 跟句page中的查询条件到供应商品表中查询相应数据
     * @param page
     * @return
     */
    @Override
    public List<SupplierWareBo> query(Page<SupplierWareBo> page) {
        String jpql="select s from SupplierWares s where 1=1 ";
        String name = page.getT().getName();
        String type = page.getT().getType();
        Double minAge=page.getT().getMinUnitPrice();
        Double maxAge = page.getT().getMaxUnitPrice();
        if (name != null && !name.equals("")) {
            jpql+="and s.name like :name";
        }
        if (type != null && !type.equals("")) {
            jpql+="and s.type like :type";
        }
        if(minAge != null && !minAge.equals("") && minAge > 0){
            jpql +=" and s.unitPrice >= :minAge ";
        }
        if(maxAge != null && !maxAge.equals("") && maxAge > 0){
            jpql +=" and s.unitPrice <= :maxAge " ;
        }

        Query query =entityManager.createQuery(jpql);
        if (name != null && !name.equals("")) {
            query.setParameter("name","%"+name.trim()+"%");
        }
        if (type != null && !type.equals("")) {
            query.setParameter("type","%"+type.trim()+"%");
        }
        if(minAge != null && !minAge.equals("") && minAge > 0){
            query.setParameter("minAge",minAge);
        }if(maxAge != null && !maxAge.equals("") && maxAge > 0){
            query.setParameter("maxAge",maxAge);
        }
        System.out.println(jpql);
        query.setFirstResult(page.getStart()).setMaxResults(page.getPageSize());//查询分页
        List<SupplierWareBo> list = query.getResultList();
        return list;
    }

    /**
     * 根据id到供应商品表中获取指定数据
     * @param id
     * @return
     */
    @Override
    public SupplierWares getById(Integer id) {
        SupplierWares supplierWares = entityManager.find(SupplierWares.class,id);
        return supplierWares;
    }

    /**
     * 根据page中的查询条件到供应商品表中得到数据的数目
     * @param page
     * @return
     */
    @Override
    public Long getCount(Page<SupplierWareBo> page) {
        String jpql="select count(*) from SupplierWares s  where 1=1";
        String name = page.getT().getName();
        String type = page.getT().getType();
        Double minAge=page.getT().getMinUnitPrice();
        Double maxAge = page.getT().getMaxUnitPrice();
        if (name != null && !name.equals("")) {
            jpql+="and s.name like :name ";
        }
        if (type != null && !type.equals("")) {
            jpql+="and s.type like :type ";
        }
        if(minAge != null && !minAge.equals("") && minAge > 0){
            jpql +=" and s.unitPrice >= :minAge ";
        }
        if(maxAge != null &&!maxAge.equals("") && maxAge > 0){
            jpql +=" and s.unitPrice <= :maxAge " ;
        }

        Query query =entityManager.createQuery(jpql);
        if (name != null && !name.equals("")) {
            query.setParameter("name","%"+name.trim()+"%");
        }
        if (type != null && !type.equals("")) {
            query.setParameter("type","%"+type.trim()+"%");
        }
        if(minAge != null && !minAge.equals("") && minAge > 0){
            query.setParameter("minAge",minAge);
        }if(maxAge != null && !maxAge.equals("") && maxAge > 0){
            query.setParameter("maxAge",maxAge);
        }

        Long count =(Long)query.getSingleResult();
        return count;
    }

    /**
     * 根据编号去查商品
     * @param code
     * @return
     */
    @Override
    public SupplierWares getSuppLierWareByCode(String code) {
        StringBuffer jpql = new StringBuffer();
        jpql.append("select s from SupplierWares s where code =?");
        Query query = entityManager.createQuery(jpql.toString());
        query.setParameter(1, code);
        SupplierWares supplierWare = (SupplierWares) query.getSingleResult();
        return supplierWare;
    }

    @Override
    public List<SupplierWares> queryList() {
        String jpql="select s from SupplierWares s";
        Query query =entityManager.createQuery(jpql);
        List<SupplierWares> list = query.getResultList();
        return list;
    }
    /**
     * 根据供应商商品名称返回供应商商品编号
     * @param name
     * @return String
     */
    @Override
    public String getSupplierWareCode(String name) {
        String jpql = "select s.code from SupplierWares s where s.name like ?1";
        String wareName = (String) entityManager.createQuery(jpql).setParameter(1,"%"+name+"%").getSingleResult();
        return wareName;
    }
}
