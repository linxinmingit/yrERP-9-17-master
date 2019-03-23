package com.yr.depot.controller;

import com.yr.depot.service.WareTypeService;
import com.yr.entitys.Log.Log;
import com.yr.entitys.bo.depotBo.WareTypeBo;
import com.yr.entitys.depot.Ware;
import com.yr.entitys.depot.WareType;
import com.yr.entitys.page.Page;
import com.yr.entitys.user.User;
import com.yr.log.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Map;

/**
 * 商品类型
 */
@Controller
@RequestMapping(value = "ware_type")
public class WareTypeController {
    @Autowired
    private WareTypeService wts;
    @Autowired
    @Qualifier("logServiceImpl")
    private LogService logServices;//日志

    @ModelAttribute
    public void Pojo(@RequestParam(value = "id", required = false) Integer id, Map<String, Object> map) {
        if (id != null) {
            map.put("wareType", wts.getWareType(id));
        }
    }

    /**
     * ware_type/ware_typeTable
     * 用于跳转数据查询页面
     *
     * @return
     */
    @RequestMapping(value = "ware_typeTable", method = RequestMethod.GET)
    public String getListPage() {
        return "/wareTypeList";
    }

    /**
     * 用于跳转到添加页面
     *
     * @return
     */
    @RequestMapping(value = "ware_typeTable/add", method = RequestMethod.GET)
    public String getAddPage(Map<String, Object> map) {
        map.put("wareType", new WareType());
        map.put("supCode", wts.getWareType());
        return "wareTypeAU";
    }

    /**
     * 用于跳转到修改页面
     */
    @RequestMapping(value = "ware_typeTable/{id}", method = RequestMethod.GET)
    public String getUpdatePage(@PathVariable Integer id, Map<String, Object> map) {
        map.put("wareType", wts.getWareType(id));
        map.put("supCode", wts.getWareType());
        return "wareTypeAU";
    }

    /**
     * 商品类型添加方法，用于前台添加数据
     *
     * @param waretype
     * @return
     */
    @RequestMapping(value = "ware_typeTable", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String addWare(@ModelAttribute("wareType") WareType waretype, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        waretype.setCreateEmp(user.getName());
        boolean bool = false;
        try {
            bool = wts.add(waretype);
            Log log = new Log();
            log.setModular("仓库商品类型模块");
            log.setTable("ware_type");
            //模块的操作类型（0抛异常，1新增，2删除，3修改，4用户登录，5用户退出）
            log.setType(1);
            //修改前的值
            //log.setFieldOldValue(oldWare);  //新增数据忽略前置
            //修改后的值
            // log.setFieldNewValue(ware.toString());
            log.setCreateTime(new Timestamp(System.currentTimeMillis()));
            log.setCreateEmp(user.getName());
            logServices.addLog(log);
        } catch (Exception e) {
            Log log = new Log();
            log.setModular("仓库商品类型模块");
            log.setTable("ware_type");
            //模块的操作类型（0抛异常，1新增，2删除，3修改，4用户登录，5用户退出）
            log.setType(0);
            //修改前的值
            //log.setFieldOldValue(oldWare);  //新增数据忽略前置
            //修改后的值
            // log.setFieldNewValue(ware.toString());
            log.setCreateTime(new Timestamp(System.currentTimeMillis()));
            log.setCreateEmp(user.getName());
            logServices.addLog(log);
            e.printStackTrace();
        }
        if (bool) {
            return "{\"code\":1,\"msg\":\"添加成功\"}";
        } else {
            return "{\"code\":2,\"msg\":\"添加失败\"}";
        }
    }

    /**
     * 根据id来删除商品类型
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "ware_typeTable/{id}", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String deleteWare(@PathVariable Integer[] id, HttpServletRequest request) {
        boolean bool = false;
        User user = (User) request.getSession().getAttribute("user");
        try {
            bool = wts.delete(id);
            Log log = new Log();
            log.setModular("仓库商品类型模块");
            log.setTable("ware_type");
            //模块的操作类型（0抛异常，1新增，2删除，3修改，4用户登录，5用户退出）
            log.setType(2);
            //修改前的值
            //log.setFieldOldValue(oldWare);  //新增数据忽略前置
            //修改后的值
            // log.setFieldNewValue(ware.toString());
            log.setCreateTime(new Timestamp(System.currentTimeMillis()));
            log.setCreateEmp(user.getName());
            logServices.addLog(log);
        } catch (Exception e) {
            Log log = new Log();
            log.setModular("仓库商品类型模块");
            log.setTable("ware_type");
            //模块的操作类型（0抛异常，1新增，2删除，3修改，4用户登录，5用户退出）
            log.setType(0);
            //修改前的值
            //log.setFieldOldValue(oldWare);  //新增数据忽略前置
            //修改后的值
            // log.setFieldNewValue(ware.toString());
            log.setCreateTime(new Timestamp(System.currentTimeMillis()));
            log.setCreateEmp(user.getName());
            logServices.addLog(log);
            e.printStackTrace();
        }
        if (bool) {
            return "{\"code\":1,\"msg\":\"删除成功\"}";
        } else {
            return "{\"code\":2,\"msg\":\"删除失败\"}";
        }
    }

    /**
     * 根据id来修改商品类型的信息
     *
     * @param wareType
     * @param map
     * @return
     */
    @RequestMapping(value = "ware_typeTable", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String updateWare(@ModelAttribute("wareType") WareType wareType, Map<String, Object> map, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        String oldWareType = wts.getWareType(wareType.getId()).toString();
        wareType.setUpdateEmp(user.getName());
        boolean bool = false;
        try {
            bool = wts.update(wareType);
            Log log = new Log();
            log.setModular("仓库商品类型模块");
            log.setTable("ware_type");
            //模块的操作类型（0抛异常，1新增，2删除，3修改，4用户登录，5用户退出）
            log.setType(3);
            //修改前的值
            log.setFieldOldValue(oldWareType);  //新增数据忽略前置
            //修改后的值
            log.setFieldNewValue(wareType.toString());
            log.setCreateTime(new Timestamp(System.currentTimeMillis()));
            log.setCreateEmp(user.getName());
            logServices.addLog(log);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (bool) {
            return "{\"code\":1,\"msg\":\"修改成功\"}";
        } else {
            return "{\"code\":2,\"msg\":\"修改失败\"}";
        }
    }

    /**
     * 商品类型查询方法，前台可以通过这个方法进行数据的查询
     *
     * @param wareType
     * @param map
     * @return 查询数据
     */
    @RequestMapping(value = "ware_typeTable/list", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryWare(Page<WareType> page, WareType wareType, Map<String, Object> map) {
        page.setT(wareType);
        String json = wts.query(page);
        System.out.println("==========================="+json);
        map.put("wareType", json);
        return json;
    }

    /**
     * 用于判断编号是否存在
     *
     * @param wareType
     * @return
     */
    @RequestMapping(value = "ware_typeTable/checkTypeCode", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String checkTypeCode(WareType wareType) {
        boolean bool = wts.query(wareType);
        if (bool) {
            return "true";
        } else {
            return "false";
        }
    }
}
