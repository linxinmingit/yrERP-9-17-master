package com.yr.department.controller;

import com.yr.department.service.DepartmentService;
import com.yr.entitys.Log.Log;
import com.yr.entitys.bo.departmentBo.Departmentbo;
import com.yr.entitys.page.Page;
import com.yr.entitys.user.User;
import com.yr.log.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 部门表Controller接口
 */
@RequestMapping(value = "/department")
@Controller
public class DepartmentController {
    @Qualifier("departmentServiceImpl")
    @Autowired
    private DepartmentService departmentService;//把epartmentService对象传过来
    @Autowired
    private LogService logService;

    //如果表单中带有ID 就执行这里
    //@RequestParam(value="id", required = false)表示执行该方法时不必要有id这个参数值，如果是ture则是必须要有id这个参数
    @ModelAttribute
    public void getAccount(@RequestParam (value="id" ,required=false) Integer id , Map<String ,Object> map){
        if(!"".equals(id) && id !=null){
            Departmentbo departmentbo = departmentService.departmentId(id);
            map.put("departmentbo", departmentbo);
        }
    }

    /**
     * 放回固定格式的字符串，生成菜单树形表
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/departmentTable/list",method=RequestMethod.GET,produces="application/json;charset=UTF-8")//防止ajxa页面出现乱码
    public String query(Departmentbo departmentbo,Page<Departmentbo> page){
        page.setT(departmentbo);
        return departmentService.queryDepartmentbo(page);
    }

    /**
     * 跳转操作页面
     * @return
     */
    @RequestMapping(value = "/departmentTable",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    public String List(){

        return "departmentListPage";
    }
    /**
     * 跳转添加页面
     * @returns
     */
    @RequestMapping(value="/departmentTable/add",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
    public String add(Map<String , Object>map){
        map.put("departmentbo", new Departmentbo());//传入一个空的对象
        map.put("supDepartment",departmentService.getDepartmentList());
        return "departmentAU";//返回新增 修改页面
    }
    /**
     * 保存添加
     */
    @ResponseBody
    @RequestMapping(value="/departmentTable",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
    public String add(Departmentbo departmentbo, HttpServletRequest request){
        try {

        //将时间戳设置进入创建时间
        departmentbo.getDepartment().setCreateTime(new Timestamp(System.currentTimeMillis()));
        //将修改时间设进修改时间，初始修改时间，后期会改
        departmentbo.getDepartment().setUpdateTime(new Timestamp(System.currentTimeMillis()));
        //获取session 当中当前登录用户，session属性名从login登录的传过来，
        User user = (User) request.getSession().getAttribute("user");
        departmentbo.getDepartment().setCreateEmp(user.getName());
        //这个初始的修改人，后期会改
        departmentbo.getDepartment().setUpdateEmp(user.getName());
        departmentService.add(departmentbo);//调用添加方法

        //日志
        Log log = new Log();
        log.setModular("部门");
        log.setTable("departmentbo");
        //模块的操作类型（0抛异常，1新增，2删除，3修改，4用户登录，5用户退出）
        log.setType(1);
        //log.setFieldOldValue();  //新增数据忽略前置
        log.setFieldNewValue(departmentbo.getDepartment().toString());
        log.setCreateTime(new Timestamp(System.currentTimeMillis()));
        log.setCreateEmp(user.getName());
        logService.addLog(log);
    } catch (Exception e) {
        Log log = new Log();
        log.setModular("部门");
        log.setTable("departmentbo");
        //模块的操作类型（0抛异常，1新增，2删除，3修改，4用户登录，5用户退出）
        log.setType(0);
        log.setFieldNewValue(departmentbo.getDepartment().toString());
        log.setCreateTime(new Timestamp(System.currentTimeMillis()));
        log.setContent(e.toString());
        User user1 = (User) request.getSession().getAttribute("user");
        log.setCreateEmp(user1.getName());
        logService.addLog(log);
        e.printStackTrace();
    }
        return "{\"code\":1,\"msg\":\"新增保存成功\"}";
    }
    /**
     * 根据ID 删除部门
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/departmentTable/{id}",method=RequestMethod.DELETE,produces="application/json;charset=UTF-8")
    public String delete(@PathVariable("id") Integer[] id){
        departmentService.delete(id);//调用删除方法
        return "{\"code\":1,\"msg\":\"删除成功\"}";
    }
    /**
     * 跳转修改 部门
     */
    @RequestMapping(value="/departmentTable/{id}",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
    public String update(@PathVariable("id") Integer id,Map<String , Object>map){
        map.put("departmentbo", departmentService.departmentId(id));//调用查询ID方法回显数据
        map.put("supDepartment",departmentService.getDepartmentList());
        return "departmentAU";//返回新增 修改页面
    }

    /**
     * 保存修改 部门
     */
    @ResponseBody
    @RequestMapping(value="/departmentTable",method=RequestMethod.PUT,produces="application/json;charset=UTF-8")
    public String updates(Departmentbo departmentbo,HttpServletRequest request) {
            //修改之前的值
            String depotLog =departmentService.departmentId(departmentbo.getDepartment().getId()).toString();
        try {
            //将时间戳设置进入创建时间
            departmentbo.getDepartment().setCreateTime(new Timestamp(System.currentTimeMillis()));
            //将修改时间设进修改时间，初始修改时间，后期会改
            departmentbo.getDepartment().setUpdateTime(new Timestamp(System.currentTimeMillis()));
            //获取session 当中当前登录用户，session属性名从login登录的传过来，
            User user = (User) request.getSession().getAttribute("user");
            departmentbo.getDepartment().setCreateEmp(user.getName());
            //这个初始的修改人，后期会改
            departmentbo.getDepartment().setUpdateEmp(user.getName());
            departmentService.update(departmentbo);

            //日志
            Log log = new Log();
            log.setModular("部门");
            log.setTable("departmentbo");
            //模块的操作类型（0抛异常，1新增，2删除，3修改，4用户登录，5用户退出）
            log.setType(3);
            //修改之前的值
            log.setFieldOldValue(depotLog);
            //修改之后的值
            log.setFieldNewValue(departmentbo.getDepartment().toString());
            log.setCreateTime(new Timestamp(System.currentTimeMillis()));
            log.setCreateEmp(user.getName());
            logService.addLog(log);
        } catch (Exception e)
        {
            e.printStackTrace();

            Log log = new Log();
            log.setModular("部门");
            log.setTable("departmentbo");
            //模块的操作类型（0抛异常，1新增，2删除，3修改，4用户登录，5用户退出）
            log.setType(0);
            log.setFieldOldValue(depotLog);
            log.setFieldNewValue(departmentbo.getDepartment().toString());
            log.setContent(e.getMessage());
            log.setCreateTime(new Timestamp(System.currentTimeMillis()));
            User user1 = (User) request.getSession().getAttribute("user");
            log.setCreateEmp(user1.getName());
            logService.addLog(log);
        }
        return "{\"code\":1,\"msg\":\"修改保存成功\"}";
    }
}