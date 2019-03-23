package com.yr.user.controller;

import com.yr.entitys.Log.Log;
import com.yr.entitys.bo.user.PermissionBo;
import com.yr.entitys.page.Page;
import com.yr.entitys.user.Permission;
import com.yr.entitys.user.Role;
import com.yr.entitys.user.User;
import com.yr.log.service.LogService;
import com.yr.user.service.PermissionService;
import org.apache.tools.ant.taskdefs.condition.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 操纵权限
 */
@Controller
@RequestMapping("u_permission")
public class PermissionController {
    @Autowired
    @Qualifier("permissionServiceImpl")
    private PermissionService permissionService;
    @Autowired
    private LogService logService;

    /**
     * 如果检测到form表单提交带有id,直接将值存入request中
     * @param id
     * @param map
     */
    @ModelAttribute
    public void getAccount(@RequestParam(value="id",required=false) Integer id, Map<String, Object> map){
        if(id != null && id != 0){
            Permission permission = permissionService.getById(id);
            map.put("permission", permission);
        }
    }

    /**
     * 跳转列表
     * @return
     */
    @RequestMapping(value = "/permissionTable",method = RequestMethod.GET)
    public String jumpList(){
        return "permissionList";
    }

    /**
     * 分页的形式查询permission表的数据
     * @return List<PermissionBo>
     */
    @RequestMapping(value="/permissionTable/list", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String query(PermissionBo permissionBo, Page<PermissionBo> page){
        //去空格
        Permission permission = permissionBo.getPermission();
        permission.setName(permission.getName().trim());
        permission.setUrl(permission.getUrl().trim());
        permission.setMethod(permission.getMethod().trim().toUpperCase());//并且转成大写

        page.setT(permissionBo);
        return permissionService.query(page);
    }

    /**
     * 跳转添加页面
     * @return String
     */
    @RequestMapping(value="/permissionTable/add", method = RequestMethod.GET)
    public String jumpAdd(Map<String, Object> map){
        map.put("permissionBo", new PermissionBo());//传入一个空的permission对象
        Map<String,Object> methodMap = new HashMap<>();//请求方式
        methodMap.put("GET","GET");
        methodMap.put("PUT","PUT");
        methodMap.put("POST","POST");
        methodMap.put("DELETE","DELETE");
        map.put("methodMap",methodMap);
        Map<Integer, Object> permissions = permissionService.getPermission();//获取所有权限的集合
        map.put("permissions",permissions);
        return "permissionAU";
    }

    /**
     * 保存添加
     * @return String
     */
    @RequestMapping(value="/permissionTable", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String saveAdd(PermissionBo permissionBo, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");//获得当前用户

        permissionBo.getPermission().setCreateTime(new Date());//获取当前时间
        permissionBo.getPermission().setCreateEmp(((User)request.getSession().getAttribute("user")).getName());//获取当前用户名
        Log log = new Log();
        try {
            permissionService.add(permissionBo);
            log.setModular("权限模块");
            log.setTable("u_permission");
            //模块的操作类型（0抛异常，1新增，2删除，3修改，4用户登录，5用户退出）
            log.setType(1);
            log.setFieldNewValue(permissionBo.getPermission().toString());
            log.setCreateTime(new Timestamp(System.currentTimeMillis()));
            log.setCreateEmp(user.getName());
        } catch (Exception e) {
            log.setModular("权限模块");
            log.setTable("u_permission");
            //模块的操作类型（0抛异常，1新增，2删除，3修改，4用户登录，5用户退出）
            log.setType(0);
            log.setFieldNewValue(permissionBo.getPermission().toString());
            log.setCreateTime(new Timestamp(System.currentTimeMillis()));
            log.setContent(e.getMessage());
            log.setCreateEmp(user.getName());
        }
        logService.addLog(log);
        return "{\"code\":1,\"msg\":\"添加成功\"}";
    }

    /**
     * 跳转修改
     * @return String
     */
    @RequestMapping(value="/permissionTable/{id}",method=RequestMethod.GET)
    public String jumpUpdate(@PathVariable Integer id, Map<String, Object> map){
        PermissionBo permissionBos = new PermissionBo();//实例化一个对象
        Permission permission = permissionService.getById(id);//获得权限对象
        permissionBos.setPermission(permission);
        map.put("permissionBo", permissionBos);//根据id获取对象放入request中,进行修改
        Map<String,Object> methodMap = new HashMap<>();//请求方式
        methodMap.put("GET","GET");
        methodMap.put("PUT","PUT");
        methodMap.put("POST","POST");
        methodMap.put("DELETE","DELETE");
        map.put("methodMap",methodMap);
        Map<Integer, Object> permissions = permissionService.getPermission();//获取所有权限的集合
        permissions.remove(permission.getId());//删除该用户已经有的键，不能显示自己为自己的父级权限
        map.put("permissions",permissions);
        return "permissionAU";
    }

    /**
     * 保存修改
     * @return String
     */
    @RequestMapping(value="/permissionTable",method= RequestMethod.PUT, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String saveUpdate(PermissionBo permissionBo, Page<PermissionBo> page/*, Map<String, Object> map*/, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");//获得当前用户

        Permission oldPermission = permissionService.getById(permissionBo.getPermission().getId());//旧值
        permissionBo.getPermission().setUpdateTime(new Date());//获取修改当前时间
        permissionBo.getPermission().setCreateEmp(((User)request.getSession().getAttribute("user")).getName());//获取修改用户
        Log log = new Log();
        try {
            permissionService.update(permissionBo.getPermission());
            log.setModular("权限模块");
            log.setTable("u_permission");
            //模块的操作类型（0抛异常，1新增，2删除，3修改，4用户登录，5用户退出）
            log.setType(3);
            log.setFieldOldValue(oldPermission.toString());
            log.setFieldNewValue(permissionBo.getPermission().toString());
            log.setCreateTime(new Timestamp(System.currentTimeMillis()));
            log.setCreateEmp(user.getName());
        } catch (Exception e) {
            log.setModular("权限模块");
            log.setTable("u_permission");
            //模块的操作类型（0抛异常，1新增，2删除，3修改，4用户登录，5用户退出）
            log.setType(0);
            log.setFieldOldValue(oldPermission.toString());
            log.setFieldNewValue(permissionBo.getPermission().toString());
            log.setContent(e.getMessage());
            log.setCreateTime(new Timestamp(System.currentTimeMillis()));
            log.setCreateEmp(user.getName());
        }
        logService.addLog(log);
        return "{\"code\":1,\"msg\":\"修改成功\"}";
    }

    /**
     * 删除权限
     * @return String
     */
    @RequestMapping(value="/permissionTable/{id}",method=RequestMethod.DELETE)
    @ResponseBody
    public String delete(@PathVariable Integer[] id, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");//获得当前用户

        String permissionStr = "";//删除前的值
        for (int i=0;i<id.length;i++) {
            Permission permission = permissionService.getById(id[i]);
            permissionStr += permission.toString();
            if(i != id.length){//不为最后一个满足条件
                permissionStr += ";";
            }
        }
        Log log = new Log();
        try {
            permissionService.delete(id);
            log.setModular("权限模块");
            log.setTable("u_permission");
            //模块的操作类型（0抛异常，1新增，2删除，3修改，4用户登录，5用户退出）
            log.setType(2);
            log.setFieldOldValue(permissionStr);
            log.setCreateTime(new Timestamp(System.currentTimeMillis()));
            log.setCreateEmp(user.getName());
        } catch (Exception e) {
            log.setModular("权限模块");
            log.setTable("u_permission");
            //模块的操作类型（0抛异常，1新增，2删除，3修改，4用户登录，5用户退出）
            log.setType(0);
            log.setFieldOldValue(permissionStr);
            log.setContent(e.getMessage());
            log.setCreateTime(new Timestamp(System.currentTimeMillis()));
            log.setCreateEmp(user.getName());
        }
        logService.addLog(log);
        return "{\"code\":1,\"msg\":\"删除成功\"}";
    }

    /**
     * 回显角色所有的权限
     */
    @RequestMapping(value="/permissionTable/permissionList",method = RequestMethod.GET)
    @ResponseBody
    public List<PermissionBo> getPermission(Integer id){
        return permissionService.getPermission(id);
    }

    /**
     * 通过父级权限获得子级权限
     */
    @RequestMapping(value="/permissionTable/permissionChildren",method = RequestMethod.GET)
    public List<PermissionBo> getChildren(Integer rid, Integer pid){
        return permissionService.getchildren(rid, pid);
    }

    /**
     * 根据权限id获得所有父级权限
     */
    @RequestMapping(value="/permissionTable/getParent",method = RequestMethod.GET)
    public List<Permission> getParent(Integer id){
        return permissionService.getParent(id);
    }
}
