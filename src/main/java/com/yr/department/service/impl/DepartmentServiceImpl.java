package com.yr.department.service.impl;


import com.yr.department.dao.DepartmentDao;
import com.yr.department.service.DepartmentService;
import com.yr.entitys.bo.departmentBo.Departmentbo;
import com.yr.entitys.department.Department;
import com.yr.entitys.page.Page;
import com.yr.util.DateUtils;
import com.yr.util.JsonDateValueProcessor;
import com.yr.util.JsonUtils;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 部门Service实现类
 */
@Service("departmentServiceImpl")
@Transactional
public class DepartmentServiceImpl implements DepartmentService {
    @Qualifier("departmentDaoImpl")
    @Autowired
    private DepartmentDao departmentDao;
    /**
     * 查询所有部门
     */
    @Override
    public String query(){
        List<Departmentbo> departmentboList= new ArrayList<Departmentbo>();
        for (Department department : departmentDao.query()) {
            //这里new对象，要使用自动注入吗？？
            Departmentbo departmentbo = new Departmentbo();
            departmentbo.setId(department.getId());
            departmentbo.setName(department.getName());
            departmentbo.setCode(department.getCode());
            departmentbo.setSupCode(department.getSupCode());
            departmentbo.setCreateEmp(department.getCreateEmp());
            departmentbo.setCreateTime(DateUtils.dateToStr(department.getCreateTime(),"dd-MMM-yyyy HH:mm:ss:SSS"));
            departmentbo.setUpdateEmp(department.getUpdateEmp());
            departmentbo.setUpdateTime(DateUtils.dateToStr(department.getUpdateTime(),"dd-MMM-yyyy HH:mm:ss:SSS"));
            departmentboList.add(departmentbo);
        }
        String menuJsonStr = JSONArray.fromObject(departmentboList).toString();
        String strJson = "{" +
                "\"msg\": \"\"," +
                "\"code\": 0," +
                "\"data\":"+menuJsonStr+"," +
                "\"count\": 924," +
                "\"is\": true," +
                "\"tip\": \"操作成功！\"" +
                "}";
        return strJson;
    }

    /**
     * 查询所有部门
     */
    @Override
    public String queryDepartmentbo(Page<Departmentbo> page){
        Long count = departmentDao.departmentCount(page);
        page.setTotalRecord(count);
        List<Departmentbo> list = departmentDao.queryDepartmentbo(page);
        //page.setPageData(list);
        // pageJson = JsonUtils.beanToJson(page);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        JSONArray jsonArray = JSONArray.fromObject(list,jsonConfig);
        String json = "{\"code\": 0,\"msg\": \"\",\"count\": "+page.getTotalRecord()+",\"data\":"+jsonArray+"}";
        return json;
    }

    @Override
    public Department getByCode(String code) {
        return departmentDao.getByCode(code);
    }

    @Override
    public List<Departmentbo> getDepartmentList() {
        List<Department> list = departmentDao.query();
        List<Departmentbo> listBO = new ArrayList<>();
        for (Department department:list) {
            Departmentbo departmentbo = new Departmentbo();
            departmentbo.setDepartment(department);
            listBO.add(departmentbo);
        }
        Department department1 = new Department();
        department1.setName("无");
        department1.setSupCode("0");
        department1.setCode("0");
        Departmentbo departmentbo1 = new Departmentbo();
        departmentbo1.setDepartment(department1);
        listBO.add(departmentbo1);
        return listBO;
    }


    /**
     * 根据ID查询部门 并回显
     */
    @Override
    public Departmentbo departmentId(Integer id) {
        Department department=departmentDao.departmentId(id);
        Departmentbo departmentbo = new Departmentbo();
        departmentbo.setDepartment(department);
        return departmentbo;
    }

    /**
     * 新增部门
     */
    @Override
    public void add(Departmentbo departmentbo) {

        departmentDao.add(departmentbo.getDepartment());
    }

    /**
     * 删除部门
     */
    @Override
    public void delete(Integer[] id) {

        departmentDao.delete(id);

    }

    /**
     * 修改部门
     */
    @Override
    public void update(Departmentbo departmentbo) {

        departmentDao.update(departmentbo.getDepartment());

    }

    /**
     * 查询部门编号,提供给用户调用
     * @param
     * @return
     */
    public Map<String,Object> querys(){

        return departmentDao.querys();
    }

    /**
     * 根据部门名字返回部门编号
     * @param depaName
     * @return
     */
    public String getDepaCode(String depaName){
        return departmentDao.getDepaCode(depaName);
    }
}