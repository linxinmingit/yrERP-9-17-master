package com.yr.user.service.impl;

import com.yr.core.redis.JedisManager;
import com.yr.core.shiro.filter.MyRealm;
import com.yr.department.service.DepartmentService;
import com.yr.entitys.bo.user.UserBo;
import com.yr.entitys.page.Page;
import com.yr.entitys.user.Permission;
import com.yr.entitys.user.Role;
import com.yr.entitys.user.User;
import com.yr.user.dao.UserDao;
import com.yr.user.service.UserService;
import com.yr.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.JSONUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

    @Autowired
    @Qualifier("userDaoImpl")
    private UserDao userDao;
    @Autowired
    private JedisManager jedisManager;
    @Autowired
    @Qualifier("departmentServiceImpl")
    private DepartmentService departmentService;


    /**
     * 分页的形式查询user表的数据
     * @param page
     */
    public String query(Page<UserBo> page){
        page.setTotalRecord(userDao.getCount(page));//查询总条数加入page中
        List<User> list = userDao.query(page);//分页查询的数据
        Map<String,Object> map = departmentService.querys();
        for (User user:list) {
            if(!StringUtils.isEmpty(user.getDepaCode())){
                user.setDepaCode((String) map.get(user.getDepaCode()));
            }
        }
        JsonConfig jsonConfig = new JsonConfig();
        //设置忽略的属性
        jsonConfig.setExcludes(new String[]{"role"});  //此处是亮点，只要将所需忽略字段加到数组中即可
        jsonConfig.setIgnoreDefaultExcludes(false);  //设置默认忽略
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        //能够将时间类型转date
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        JSONArray jsonArray = JSONArray.fromObject(list,jsonConfig);
        String json = "{\"code\": 0,\"msg\": \"\",\"count\": "+page.getTotalRecord()+",\"data\":"+jsonArray+"}";
        return json;
    }

    /**
     * 添加用户信息
     * @param user
     */
    @Transactional//注意：事务注解如果加在查询的方法上，对对象进行操作，会造成将对象持久化，修改数据库的值
    public void add(User user){
        user.setPassword(Md5Utils.getMd5Password(user.getPassword()));//将密码加密后置入user中
        userDao.add(user);
    }

    /**
     * 修改用户信息
     * @param user
     */
    @Transactional
    public void update(User user){
        userDao.update(user);
    }

    /**
     * 根据id查询用户数据
     * @param id
     * @return User
     */
    public User getById(Integer id){
        return userDao.getById(id);
    }

    /**
     * 根据用户id返回角色名集合
     * @param id
     * @return List<String>
     */
    public List<String> getRoles(Integer id){
        return userDao.getRoles(id);
    }

    /**
     * 根据用户id返回权限集合
     * @param id
     * @return List<Permission>
     */
    public List<Permission> getPermissions(Integer id){
        return userDao.getPermissions(id);
    }

    /**
     * 给用户赋角色
     * @param id
     * @param roleIds
     */
    @Transactional
    public void setRoles(Integer id,Integer[] roleIds,User user){
        userDao.deleteRoles(id);
        userDao.addRoles(id,roleIds);
        List<String> roles = getRoles(id);//获得角色对象
        Jedis jedis = jedisManager.getJedis();
        jedisManager.returnResource(jedis,true);//关闭redis连接
        //将角色字符串序列化后放入redis中
        jedis.set("roles".getBytes(), SerializeUtil.serialize(roles));

        User users = userDao.getById(id);//根据id查询user
        if(user.getName().equals(users.getName())){//如果修改的是当前账号,就重新登陆
            //清空缓存，重新加载
            RealmSecurityManager rsm = (RealmSecurityManager)SecurityUtils.getSecurityManager();
            MyRealm realm = (MyRealm)rsm.getRealms().iterator().next();
            realm.clearAuthz();
            Subject subject = SecurityUtils.getSubject();//获取subject对象
            subject.hasRole("admin");//重新调用进入授权方法，避免之后可以手动输入url进入访问
        }
    }

    /**
     * 根据用户名获得User对象
     * @param name
     * @return User
     */
    public User getByName(String name){
        return userDao.getByName(name);
    }

    /**
     * 部门删除时调用,根据部门编号删除用户
     */
    @Transactional
    public void delete(String department){
        userDao.delete(department);
    }

    /**
     * 用户进行批量删除
     * @param id
     */
    @Transactional
    public void delete(Integer[] id){
        userDao.delete(id);
    }

    /**
     * 修改用户的状态
     * @param id
     * @param state
     */
    @Transactional
    public void updateState(Integer id, Integer state){
        userDao.updateState(id,state);
    }

    /**
     * 根据员工工号查询user对象
     * @param jobNum
     * @return User
     */
    public User getByJobNum(String jobNum){
        return userDao.getByJobNum(jobNum);
    }

    /**
     * 返回所有角色列表
     * @return String
     */
    public String getRole(Integer id){
        List<Role> list =  userDao.getRole(id);
        JsonConfig jsonConfig = new JsonConfig();
        //设置忽略的属性
        jsonConfig.setExcludes(new String[]{"user","permission"});  //此处是亮点，只要将所需忽略字段加到数组中即可
        jsonConfig.setIgnoreDefaultExcludes(false);  //设置默认忽略
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        //能够将时间类型转date
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        JSONArray jsonArray = JSONArray.fromObject(list,jsonConfig);
        return jsonArray.toString();
    }

    /**
     * 返回user数据集合
     *
     * @return List<User>
     */
    @Override
    public List<User> queryUser() {
        return userDao.queryUser();
    }
}
