package com.yr.user.controller;

import com.hazelcast.console.Echo;
import com.yr.core.shiro.filter.MyRealm;
import com.yr.entitys.Log.Log;
import com.yr.entitys.bo.user.RoleBo;
import com.yr.entitys.page.Page;
import com.yr.entitys.user.Permission;
import com.yr.entitys.user.Role;
import com.yr.entitys.user.User;
import com.yr.log.service.LogService;
import com.yr.user.service.PermissionService;
import com.yr.user.service.RoleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

/**
 * 操纵角色
 */
@Controller
@RequestMapping("/u_role")
public class RoleController {
    @Autowired
    @Qualifier("roleServiceImpl")
    private RoleService roleService;
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
            Role role = roleService.getById(id);
            map.put("Role", role);
        }
    }

    /**
     * 跳转列表
     * @return String
     */
    @RequestMapping(value = "/roleTable",method = RequestMethod.GET)
    public String jumpList(){
        return "roleList";
    }

    /**
     * 分页的形式查询role表的数据
     * @return List<RoleBo>
     */
    @RequestMapping(value="/roleTable/list", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String query(RoleBo roleBo, Page<RoleBo> page){
        //去空格
        roleBo.getRole().setName(roleBo.getRole().getName().trim());//搜索的值去空格

        page.setT(roleBo);
        String json = roleService.query(page);
        return json;
    }

    /**
     * 跳转添加页面
     * @return String
     */
    @RequestMapping(value="/roleTable/add", method = RequestMethod.GET)
    public String jumpAdd(Map<String, Object> map){
        map.put("roleBo", new RoleBo());//传入一个空的role对象
        return "roleAU";
    }

    /**
     * 保存添加
     * @return String
     */
    @RequestMapping(value="/roleTable", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String saveAdd(RoleBo roleBo, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");//获得当前用户

        Role role = roleBo.getRole();
        role.setCreateTime(new Date());//获取当前时间
        role.setCreateEmp(((User)request.getSession().getAttribute("user")).getName());//获取当前用户名
        Log log = new Log();
        try {
            roleService.add(role);
            log.setModular("角色模块");
            log.setTable("u_role");
            //模块的操作类型（0抛异常，1新增，2删除，3修改，4用户登录，5用户退出）
            log.setType(1);
            log.setFieldNewValue(role.toString());
            log.setCreateTime(new Timestamp(System.currentTimeMillis()));
            log.setCreateEmp(user.getName());
        }catch (Exception e){
            log.setModular("角色模块");
            log.setTable("u_role");
            //模块的操作类型（0抛异常，1新增，2删除，3修改，4用户登录，5用户退出）
            log.setType(0);
            log.setFieldNewValue(role.toString());
            log.setCreateTime(new Timestamp(System.currentTimeMillis()));
            log.setContent(e.getMessage());
            log.setCreateEmp(user.getName());
        }
        logService.addLog(log);
        return "{\"code\":1,\"msg\":\"保存成功\"}";
    }

    /**
     * 跳转修改
     * @return String
     */
    @RequestMapping(value="/roleTable/{id}",method=RequestMethod.GET)
    public String jumpUpdate(@PathVariable Integer id, Map<String, Object> map){
        RoleBo roleBo = new RoleBo();
        Role role = roleService.getById(id);
        roleBo.setRole(role);
        map.put("roleBo", roleBo);//根据id获取对象放入request中
        return "roleAU";
    }

    /**
     * 保存修改
     * @return String
     */
    @RequestMapping(value="/roleTable",method= RequestMethod.PUT, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String saveUpdate(RoleBo roleBo, Map<String, Object> map, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");//获得当前用户

        Role oldRole = roleService.getById(roleBo.getRole().getId());//没修改之前
        roleBo.getRole().setUpdateTime(new Date());//获取修改当前时间
        roleBo.getRole().setCreateEmp(((User)request.getSession().getAttribute("user")).getName());//获取修改用户
        Log log = new Log();
        try {
            roleService.update(roleBo.getRole());
            log.setModular("角色模块");
            log.setTable("u_role");
            //模块的操作类型（0抛异常，1新增，2删除，3修改，4用户登录，5用户退出）
            log.setType(3);
            log.setFieldOldValue(oldRole.toString());
            log.setFieldNewValue(roleBo.getRole().toString());
            log.setCreateTime(new Timestamp(System.currentTimeMillis()));
            log.setCreateEmp(user.getName());
        }catch (Exception e){
            log.setModular("角色模块");
            log.setTable("u_role");
            //模块的操作类型（0抛异常，1新增，2删除，3修改，4用户登录，5用户退出）
            log.setType(0);
            log.setFieldOldValue(oldRole.toString());
            log.setFieldNewValue(roleBo.getRole().toString());
            log.setContent(e.getMessage());
            log.setCreateTime(new Timestamp(System.currentTimeMillis()));
            log.setCreateEmp(user.getName());
        }
        logService.addLog(log);
        return "{\"code\":1,\"msg\":\"修改成功\"}";
    }

    /**
     * 删除角色
     * @return String
     */
    @RequestMapping(value="/roleTable/{id}",method=RequestMethod.DELETE)
    @ResponseBody
    public String delete(@PathVariable Integer[] id, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");//获得当前用户

        String roleStr = "";//删除前的值
        for (int i=0;i<id.length;i++) {
            Role role = roleService.getById(id[i]);
            roleStr += role.toString();
            if(i != id.length){//不为最后一个满足条件
                roleStr += ";";
            }
        }
        Log log = new Log();
        try {
            roleService.delete(id);
            log.setModular("角色模块");
            log.setTable("u_role");
            //模块的操作类型（0抛异常，1新增，2删除，3修改，4用户登录，5用户退出）
            log.setType(2);
            log.setFieldOldValue(roleStr);
            log.setCreateTime(new Timestamp(System.currentTimeMillis()));
            log.setCreateEmp(user.getName());
        } catch (Exception e) {
            log.setModular("角色模块");
            log.setTable("u_role");
            //模块的操作类型（0抛异常，1新增，2删除，3修改，4用户登录，5用户退出）
            log.setType(0);
            log.setFieldOldValue(roleStr);
            log.setContent(e.getMessage());
            log.setCreateTime(new Timestamp(System.currentTimeMillis()));
            log.setCreateEmp(user.getName());
        }
        logService.addLog(log);
        return "{\"code\":1,\"msg\":\"删除成功\"}";
    }

    /**
     * 给角色赋权限
     * @param id
     * @param roleIds
     */
    @RequestMapping(value="/roleTable/setPermissions",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String setRoles(Integer id,Integer[] roleIds){
        roleService.setPermissions(id,roleIds);
        return "{\"code\":1,\"msg\":\"修改成功\"}";
    }

    /**
     * 返回所有角色列表
     */
    @RequestMapping(value="/roleTable/getPermission", produces="application/json;charset=UTF-8")
    @ResponseBody
    public String getRole(){
        return permissionService.getRoleList();
    }

    /**
     * 根据id获取当前角色的权限
     * @param uid
     * @return String
     */
    @RequestMapping(value="/roleTable/getPermissionById", produces="application/json;charset=UTF-8")
    @ResponseBody
    public String getRole(Integer uid){
        return roleService.getRole(uid);
    }
}
