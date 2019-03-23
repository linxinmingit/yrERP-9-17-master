package com.yr.depot.dao.impl;

import com.yr.depot.dao.WareTypeDao;
import com.yr.entitys.page.Page;
import com.yr.entitys.depot.WareType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Repository
public class WareTypeDaoImpl implements WareTypeDao {


    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 根据id来查询数据
     *
     * @param id
     * @return
     */
    @Override
    public WareType getWareType(Integer id) {
        StringBuffer jpql = new StringBuffer();
        jpql.append("select w from WareType w where id =?");
        Query query = entityManager.createQuery(jpql.toString());
        query.setParameter(1, id);
        WareType wareType = (WareType) query.getSingleResult();
        return wareType;
    }

    @Override
    public boolean addWareType(WareType wareType) {
        StringBuffer jpql = new StringBuffer();
        jpql.append("insert into ware_type(name,code,sup_code,createTime,createEmp) values(?,?,?,?,?)");
        Query query = entityManager.createNativeQuery(jpql.toString());
        query.setParameter(1, wareType.getName());
        query.setParameter(2, wareType.getCode());
        query.setParameter(3, wareType.getSupCode());
        query.setParameter(4, new Date());
        query.setParameter(5, wareType.getCreateEmp());
        try {
            query.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    /**
     * 删除仓库商品类型(批量)
     *
     * @param id
     * @return
     */
    @Override
    public boolean deleteWareType(Integer[] id) {
        List<Integer> list = Arrays.asList(id);//将数组转成list
        StringBuffer jpql = new StringBuffer();
        jpql.append("delete from WareType w where w.id in (:ids)");
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

    @Override
    public boolean updateWareType(WareType wareType) {
        try {
            entityManager.merge(wareType);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    /**
     * 查询商品类型
     *
     * @return
     */
    @Override
    public List<WareType> query() {
        StringBuffer jpql = new StringBuffer();
        jpql.append("select w from WareType w where 1=1");
        Query query = entityManager.createQuery(jpql.toString());
        List<WareType> wareTypes = query.getResultList();
        return wareTypes;
    }

    @Override
    public List<WareType> query(Page<WareType> wareType) {
        StringBuffer jpql = new StringBuffer();
        jpql.append("select w from WareType w where 1=1");
        String supCode = wareType.getT().getSupCode();
        String code = wareType.getT().getCode();
        String name = wareType.getT().getName();
        if (supCode != null && !supCode.equals("")) {
            jpql.append("and w.sup_code like '%" + supCode + "%'");
        }
        if (code != null && !code.equals("")) {
            jpql.append("and w.code like '%" + code + "%'");
        }
        if (name != null && !name.equals("")) {
            jpql.append("and w.name like '%" + name + "%'");
        }
        jpql.append("order by id desc");
        Query query = entityManager.createQuery(jpql.toString());
        query.setFirstResult(wareType.getStart());
        query.setMaxResults(wareType.getPageSize());
        List<WareType> wareTypes = query.getResultList();
        return wareTypes;
    }

    @Override
    public Long getCount(Page<WareType> wareType) {
        StringBuffer jpql = new StringBuffer();
        jpql.append("select count(*) from WareType where 1=1");
        String supCode = wareType.getT().getSupCode();
        String code = wareType.getT().getCode();
        String name = wareType.getT().getName();

        if (supCode != null && !supCode.equals("")) {
            jpql.append("and sup_code like '%" + supCode + "%'");
        }
        if (code != null && !code.equals("")) {
            jpql.append("and code like '%" + code + "%'");
        }
        if (name != null && !name.equals("")) {
            jpql.append("and name like '&" + name + "&'");
        }


        Query query = entityManager.createQuery(jpql.toString());
        Long count = (Long) query.getSingleResult();
        return count.longValue();
    }

    @Override
    public Long query(WareType wareType) {
        StringBuffer jpql = new StringBuffer();
        jpql.append("select count(*) from WareType where code = ? ");
        Query query = entityManager.createQuery(jpql.toString());
        query.setParameter(1, wareType.getCode());
        Long num = (Long) query.getSingleResult();
        return num.longValue();
    }
}
