package com.yr.supplier.dao.impl;

import com.yr.entitys.bo.supplierBO.SupplierBo;
import com.yr.entitys.page.Page;
import com.yr.entitys.supplier.Supplier;
import com.yr.supplier.dao.SupplierDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *供应商模块数据层实现增删改查
 */
@Repository
public class SupplierDaoImpl implements SupplierDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(SupplierBo supplierBo) {
    /*String jpql="insert into supplier(code,name,phoneNumber,addr,rank,createTime,createEmp)values(?1,?2,?3,?4,?5,?6,?7)";
    Query query = (Query) entityManager.createNativeQuery(jpql).setParameter(1, supplier.getCode())
                .setParameter(2, supplier.getName()).setParameter(3,supplier.getPhoneNumber()).setParameter(4, supplier.getAddr())
                .setParameter(5,supplier.getRank()).setParameter(6,new Date()).setParameter(7,"吕");
    System.out.println(supplier.toString());
    query.executeUpdate();*/
        entityManager.persist(supplierBo.getSupplier());


    }

    @Override
    public void delete(Integer id) {
        String jpq= "delete from Supplier d where d.id = ?1";
        Query query = entityManager.createQuery(jpq).setParameter(1, id);
        query.executeUpdate();

    }

    @Override
    public void update(SupplierBo supplierBo) {
        entityManager.merge(supplierBo.getSupplier());
    }

    @Override
    public Long getCount(Page<SupplierBo>page) {
        String jpql="select count(*) from Supplier d where 1=1";
        String code=page.getT().getCode();
        String name=page.getT().getName();
        String addr=page.getT().getAddr();
        if (code !=null && code !=""){
            jpql +=" and d.code like :code ";
        }if(name != null && name !="" ){
            jpql +=" and d.name like :name ";
        }if (addr != null && addr !=""){

        }

        Query query =entityManager.createQuery(jpql);
        if(code !=null && code !=""){
            query.setParameter("code","%"+code.trim()+"%");
        }if(name != null && name !=""){
            query.setParameter("name","%"+name.trim()+"%");
        }if(addr !=null && addr !=""){
            query.setParameter("addr","%"+addr.trim()+"%");
        }
        Long count =(Long)query.getSingleResult();
        return count;
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public Supplier getById(Integer id) {
        Supplier supplier = entityManager.find(Supplier.class,id);
        return supplier;
    }
    @Override
    public Supplier getByCode(String code){
        String jpql = "SELECT a FROM Supplier a WHERE  a.code = ?1";
        Supplier supplier = (Supplier)entityManager.createQuery(jpql).setParameter(1,code).getSingleResult();

        return supplier;
    }
    @Override
    public List<SupplierBo> query(Page<SupplierBo> page) {
        String jpql="select d from Supplier d where 1=1 ";
        String code=page.getT().getCode();
        String name=page.getT().getName();
        String addr=page.getT().getAddr();
        if (code !=null && code !=""){
            jpql +=" and d.code like :code ";
        }if(name != null && name !="" ){
            jpql +=" and d.name like :name ";
        }if (addr != null && addr !=""){

        }

        Query query =entityManager.createQuery(jpql);
        if(code !=null && code !=""){
            query.setParameter("code","%"+code.trim()+"%");
        }if(name != null && name !=""){
            query.setParameter("name","%"+name.trim()+"%");
        }if(addr !=null && addr !=""){
            query.setParameter("addr","%"+addr.trim()+"%");
        }
        System.out.println(jpql);
        query.setFirstResult(page.getStart()).setMaxResults(page.getPageSize());//查询分页
        List<SupplierBo> list = query.getResultList();
        return list;
    }

    /**
     * 将供应商数据list集合封装到map集合中去
     * @return map
     */
    @Override
    public Map<String, Object> querySuppliers() {
        String jpql = "select d from Supplier d where 1=1 ";
        List<Supplier> supplierList = entityManager.createQuery(jpql).getResultList();
        Map<String,Object> map = new HashMap<>();
        for (Supplier sup: supplierList) {
            map.put(sup.getCode(),sup.getName());//key是供应商的编号
        }
        return map;
    }
}
