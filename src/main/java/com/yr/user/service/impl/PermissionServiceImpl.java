package com.yr.user.service.impl;

import com.yr.entitys.bo.user.PermissionBo;
import com.yr.entitys.page.Page;
import com.yr.entitys.user.Permission;
import com.yr.user.dao.PermissionDao;
import com.yr.user.service.PermissionService;
import com.yr.util.JsonDateValueProcessor;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("permissionServiceImpl")
@Transactional
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    @Qualifier("permissionDaoImpl")
    private PermissionDao permissionDao;

    /**
     * 分页的形式查询permission表的数据
     * @param page
     */
    public String query(Page<PermissionBo> page){
        page.setTotalRecord(permissionDao.getCount(page));//查询总条数加入page中
        List<PermissionBo> list = permissionDao.query(page);//分页查询的数据
        //将对象转为json
        JsonConfig jsonConfig = new JsonConfig();
        //设置忽略的属性
        jsonConfig.setExcludes(new String[]{"user","permission"});  //此处是亮点，只要将所需忽略字段加到数组中即可
        jsonConfig.setIgnoreDefaultExcludes(false);  //设置默认忽略
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        //能够将时间类型转date
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        JSONArray jsonArray = JSONArray.fromObject(list,jsonConfig);
        String json = "{\"code\": 0,\"msg\": \"\",\"count\": "+page.getTotalRecord()+",\"data\":"+jsonArray+"}";
        return json;
    }

    /**
     * 添加权限信息
     * @param permissionBo
     */
    public void add(PermissionBo permissionBo){
        //permission.setMethod(permission.getMethod().toUpperCase());//将method转为大写
        permissionDao.add(permissionBo);
    }

    /**
     * 修改权限信息
     * @param permission
     */
    public void update(Permission permission){
        permissionDao.update(permission);
    }

    /**
     * 删除权限信息
     * @param id
     */
    public void delete(Integer[] id){
        permissionDao.delete(id);
    }

    /**
     * 根据id查询权限数据
     * @param id
     * @return Role
     */
    public Permission getById(Integer id){
        return permissionDao.getById(id);
    }

    /**
     * 使用集合回显角色具有哪些权限
     */
    public List<PermissionBo> getPermission(Integer id){
        List<PermissionBo> oldPermissionBo = permissionDao.getPermission(id);//获得该角色所有的权限
        List<PermissionBo> newPermissionBo = new ArrayList<>();//重新获得一个数组
        for (PermissionBo permissionBo:oldPermissionBo) {//循环获得的list集合
            if(permissionBo.getPermission().getSupId() == 0){//表示是顶级权限
                Integer permissionId = permissionBo.getPermission().getId();//顶级权限id
                newPermissionBo.add(permissionBo);//加入新的权限list
            }else{//表示非顶级权限
                for (PermissionBo permissionBos:newPermissionBo) {//循环新的集合
                    if(permissionBo.getPermission().getSupId() == permissionBos.getPermission().getId()){//当父级id等于id的时候就满足条件
                        permissionBos.getPermissionBos().add(permissionBo);//将这个对象加入到新的对象中
                    }
                }
            }
        }
        return newPermissionBo;
    }

    /**
     * 根据父权限获得子权限
     * @param rid 角色id
     * @param pid 权限id
     * @return List<PermissionBo>
     */
    public List<PermissionBo> getchildren(Integer rid, Integer pid){
        return permissionDao.getchildren(rid,pid);
    }

    /**
     * 根据子id获取所有父级id
     * @param id
     * @return List<Permission>
     */
    public List<Permission> getParent(Integer id){
        List<Permission> list = new ArrayList<>(); //实例化一个空的集合
        while(true){
            Permission permission = permissionDao.getParent(id);
            if(permission == null || "".equals(permission.getId())){
                break;
            }else{
                list.add(permission);
            }
        }
        return list;
    }

    /**
     * 返回一个所有权限的map集合，键为权限的id,值为权限的名字
     * @return Map<String,Object>
     */
    public Map<Integer,Object> getPermission(){
        return permissionDao.getPermission();
    }

    /**
     * 返回所有角色列表
     * @return String
     */
    public String getRoleList(){
        List<Permission> list = permissionDao.getRoleList();
        //将对象转为json
        JsonConfig jsonConfig = new JsonConfig();
        //设置忽略的属性
        jsonConfig.setExcludes(new String[]{"user","permission"});  //此处是亮点，只要将所需忽略字段加到数组中即可
        jsonConfig.setIgnoreDefaultExcludes(false);  //设置默认忽略
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        //能够将时间类型转date
        JSONArray jsonArray = JSONArray.fromObject(list,jsonConfig);
        return jsonArray.toString();
    }
}