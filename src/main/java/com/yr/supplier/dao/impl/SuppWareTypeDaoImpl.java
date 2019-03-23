package com.yr.supplier.dao.impl;

import com.yr.entitys.bo.supplierBO.SuppWareTypeBo;
import com.yr.entitys.page.Page;
import com.yr.entitys.supplier.SuppWareType;
import com.yr.supplier.dao.SuppWareTypeDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Repository
public class SuppWareTypeDaoImpl implements SuppWareTypeDao {
    //如何获取到和当前事务关联的 EntityManager 对象呢 ?
    //通过 @PersistenceContext 注解来标记成员变量!
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 根据sw来给供应商品类型表中添加数据
     *
     * @param swt
     * @return
     */

    @Override
    public boolean add(SuppWareType swt) {
        StringBuffer jpql = new StringBuffer();
        jpql.append("insert into suppwaretype(name,code,sup_code,createTime,createEmp) values(?,?,?,?,?)");
        Query query = entityManager.createNativeQuery(jpql.toString());
        query.setParameter(1, swt.getName());
        query.setParameter(2, swt.getCode());
        query.setParameter(3, swt.getSupCode());
        query.setParameter(4, swt.getCreateTime());
        query.setParameter(5, swt.getCreateEmp());
        try {
            query.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据id到供应商品类型表中删除指定数据
     *
     * @param id
     * @return
     */
    @Override
    public boolean delete(Integer[] id) {
        List<Integer> list = Arrays.asList(id);//将数组转变成list集合
        StringBuffer jpql = new StringBuffer();
        jpql.append("delete from SuppWareType s where s.id in(:ids)");
        Query query = entityManager.createQuery(jpql.toString());
        query.setParameter("ids", list);
        try {
            query.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据sw到供应商品类型表中修改相应数据
     *
     * @param sw
     * @return
     */
    @Override
    public boolean update(SuppWareType sw) {
       /* StringBuffer jpql = new StringBuffer();
        jpql.append("update SuppWareType set name = ?,code = ?,sup_code=?," +
                "updateEmp = ?,updateTime = ?");
        Query query = entityManager.createQuery(jpql.toString());
        query.setParameter(1, sw.getName());
        query.setParameter(2, sw.getCode());
        query.setParameter(3, sw.getSupCode());
        query.setParameter(4, new Date());
        query.setParameter(5, sw.getUpdateEmp());*/
        try {
            entityManager.merge(sw);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 跟句page中的查询条件到供应商品类型表中查询相应数据
     *
     * @param page
     * @return
     */
    @Override
    public List<SuppWareTypeBo> query(Page<SuppWareTypeBo> page) {
        StringBuffer jpql = new StringBuffer();
        jpql.append("select s from SuppWareType s where 1=1");
        String code = page.getT().getCode();
        String name = page.getT().getName();
        String suppCode = page.getT().getSupCode();
        if (code != null && !code.equals("")) {
            jpql.append("and s.code like '%" + code + "%'");
        }
        if (suppCode != null && !suppCode.equals("")) {
            jpql.append("and s.supp_code like '%" + suppCode + "%'");
        }
        if (name != null && !name.equals("")) {
            jpql.append("and s.name like '%" + name + "%'");
        }
        jpql.append("order by id desc");
        Query query = entityManager.createQuery(jpql.toString());
        query.setFirstResult(page.getStart());
        query.setMaxResults(page.getPageSize());
        List<SuppWareTypeBo> SuppWareType = query.getResultList();
        return SuppWareType;
    }

    /**
     * 根据id到供应商品类型表中获取指定数据
     *
     * @param id
     * @return
     */
    @Override
    public SuppWareType getSupplierWare(Integer id) {
        StringBuffer jpql = new StringBuffer();
        jpql.append("select s from SuppWareType s where id =?");
        Query query = entityManager.createQuery(jpql.toString());
        query.setParameter(1, id);
        SuppWareType suppWareType = (SuppWareType) query.getSingleResult();
        return suppWareType;
    }

    /**
     * 根据page中的查询条件到供应商品类型表中得到数据的数目
     *
     * @param page
     * @return
     */
    @Override
    public Long getCount(Page<SuppWareTypeBo> page) {
        StringBuffer jpql = new StringBuffer();
        jpql.append("select count(*) from SuppWareType s where 1=1");
        String code = page.getT().getCode();
        String name = page.getT().getName();
        String supCode = page.getT().getSupCode();
        if (code != null && code.equals("")) {
            jpql.append("and code like '%" + code + "%'");
        }
        if (supCode != null && supCode.equals("")) {
            jpql.append("and sup_code like '%" + supCode + "%'");
        }
        if (name != null && name.equals("")) {
            jpql.append("and name like '%" + name + "%'");
        }
        Query query = entityManager.createQuery(jpql.toString());

        Long count = (Long) query.getSingleResult();
        return count;
    }

    @Override
    public List<SuppWareType> query() {
        StringBuffer jpql = new StringBuffer();
        jpql.append("select s from SuppWareType s where 1=1");
        Query query = entityManager.createQuery(jpql.toString());
        List<SuppWareType> SuppWareType = query.getResultList();
        return SuppWareType;
    }

}
