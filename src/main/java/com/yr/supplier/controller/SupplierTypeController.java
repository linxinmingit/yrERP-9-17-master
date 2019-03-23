package com.yr.supplier.controller;

import com.yr.entitys.Log.Log;
import com.yr.entitys.bo.supplierBO.SuppWareTypeBo;
import com.yr.entitys.page.Page;
import com.yr.entitys.supplier.SuppWareType;
import com.yr.entitys.user.User;
import com.yr.log.service.LogService;
import com.yr.supplier.service.SuppWareTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Map;

/**
 * 供应商品类型页面和后台交互层（controller）
 */
@Controller
@RequestMapping(value = "suppWareType")
public class SupplierTypeController {
    @Autowired
    private SuppWareTypeService swts;
    @Autowired
    @Qualifier("logServiceImpl")
    private LogService logServices;//日志

    @ModelAttribute
    public void Pojo(@RequestParam(value = "id", required = false) Integer id, Map<String, Object> map) {
        if (id != null) {
            map.put("suppWareType", swts.getSuppWareType(id));
        }
    }

    /**
     * 用于跳转数据查询页面
     *
     * @return suppWareType/suppWareTypeTable
     */
    @RequestMapping(value = "suppWareTypeTable", method = RequestMethod.GET)
    public String getListPage() {
        return "supp_ware_typeList";
    }

    /**
     * 用于跳转到添加页面和修改页面
     *
     * @return
     */
    @RequestMapping(value = "suppWareTypeTable/add", method = RequestMethod.GET)
    public String getAddPage(Map<String, Object> map) {
        map.put("suppWareType", new SuppWareType());
        map.put("suppWareTypes", swts.getSuppWareType());
        return "supp_ware_typeAU";
    }

    @RequestMapping(value = "suppWareTypeTable/{id}", method = RequestMethod.GET)
    public String getUpdatePage(@PathVariable Integer id, Map<String, Object> map) {
        map.put("suppWareType", swts.getSuppWareType(id));
        map.put("suppWareTypes", swts.getSuppWareType());
        return "supp_ware_typeAU";
    }

    /**
     * 供应商品类型添加方法，用于前台添加数据
     *
     * @param suppWareType
     * @return
     */
    @RequestMapping(value = "suppWareTypeTable", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String addWare(@ModelAttribute("suppWareType") SuppWareType suppWareType, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        suppWareType.setCreateEmp(user.getName());
        boolean bool = false;
        try {
            bool = swts.add(suppWareType);
            Log log = new Log();
            log.setModular("供应商商品类型模块");
            log.setTable("suppWareType");
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
            log.setModular("供应商商品类型模块");
            log.setTable("suppWareType");
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
     * 根据id来删除供应商品类型
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "suppWareTypeTable/{id}", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String deleteWare(@PathVariable Integer[] id, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        boolean bool = false;
        try {
            bool = swts.delete(id);
            Log log = new Log();
            log.setModular("供应商商品类型模块");
            log.setTable("suppWareType");
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
            log.setModular("供应商商品类型模块");
            log.setTable("suppWareType");
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
     * 根据id来修改供应商品类型的信息
     *
     * @param suppWareType
     * @param map
     * @return
     */
    @RequestMapping(value = "suppWareTypeTable", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String updateWare(@ModelAttribute("suppWareType") SuppWareType suppWareType, Map<String, Object> map, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        suppWareType.setUpdateEmp(user.getName());
        String oldSuppWareType = swts.getSuppWareType(suppWareType.getId()).toString();
        boolean bool = false;
        try {
            bool = swts.update(suppWareType);
            Log log = new Log();
            log.setModular("供应商商品类型模块");
            log.setTable("suppWareType");
            //模块的操作类型（0抛异常，1新增，2删除，3修改，4用户登录，5用户退出）
            log.setType(3);
            //修改前的值
            log.setFieldOldValue(oldSuppWareType);  //新增数据忽略前置
            //修改后的值
            log.setFieldNewValue(suppWareType.toString());
            log.setCreateTime(new Timestamp(System.currentTimeMillis()));
            log.setCreateEmp(user.getName());
            logServices.addLog(log);
        } catch (Exception e) {
            Log log = new Log();
            log.setModular("供应商商品类型模块");
            log.setTable("suppWareType");
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
            return "{\"code\":1,\"msg\":\"修改成功\"}";
        } else {
            return "{\"code\":2,\"msg\":\"修改失败\"}";
        }
    }

    /**
     * 供应商品类型查询方法，前台可以通过这个方法进行数据的查询
     *
     * @param suppWareType
     * @param map
     * @return 查询数据
     */
    @RequestMapping(value = "suppWareTypeTable/list", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryWare(Page<SuppWareTypeBo> suppWareType, SuppWareTypeBo suppWareTypeBo, Map<String, Object> map) {
        suppWareType.setT(suppWareTypeBo);
        String json = swts.query(suppWareType);
        map.put("suppWareType", json);
        return json;
    }

}
