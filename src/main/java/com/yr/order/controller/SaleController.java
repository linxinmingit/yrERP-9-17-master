package com.yr.order.controller;


import com.yr.depot.service.DepotService;
import com.yr.depot.service.WareService;
import com.yr.entitys.Log.Log;
import com.yr.entitys.bo.orderBO.SaleOrderBO;
import com.yr.entitys.department.Department;
import com.yr.entitys.depot.Depot;
import com.yr.entitys.depot.Ware;
import com.yr.entitys.order.SaleOrder;
import com.yr.entitys.page.Page;
import com.yr.entitys.user.User;
import com.yr.log.service.LogService;
import com.yr.order.service.SaleService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.naming.Name;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

@Controller
@RequestMapping("/sale_order")
public class SaleController {//销售订单Controller
    @Qualifier("saleServiceImpl")
    @Autowired
    private SaleService saleService;
    @Autowired
    private WareService ws;//注入商品service的接口

    @Autowired
    private DepotService service;//注入仓库service的接口
    @Autowired
    private LogService logService;//注入日志接口

    /**
     * 如果检测到form表单提交代有id，直接将值存入request中
     * @param id
     * @param map
     */
    @ModelAttribute
    public void ModelAttribute(@RequestParam(value = "id",required = false)Integer id, Map<String,Object>map){
        if (id != null && id != 0){
            SaleOrder saleOrder = saleService.getById(id);
            map.put("saleOrder",saleOrder);
        }
    }

    /**
     * 销售订单表页面查询接口
     * @return
     */
    @RequestMapping(value = "/sale_orderTable",method = RequestMethod.GET, produces="application/json;charset=UTF-8")
    public String index(){
        return "saleOrderList";
    }

    /**
     * 分页的形式查询销售订单表的数据
     * @param saleOrderBO
     * @param page
     * @return
     */
    @RequestMapping(value = "/sale_orderTable/list",method = RequestMethod.GET, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String query(Map<String,Object>map,SaleOrderBO saleOrderBO, Page<SaleOrderBO>page){
        page.setT(saleOrderBO);//将bo类置入对象
        String json = saleService.query(page);//将list返回一个json字符串
        return json;

    }
    /**
     * 跳转添加销售订单表页面
     * @return
     */
    @RequestMapping(value = "/sale_orderTable/add",method = RequestMethod.GET)
    public String jumpAdd(Map<String,Object>map,HttpServletRequest request,SaleOrderBO saleOrderBO){
        saleOrderBO.setOrderType("1");//订单的类型，用于新增/修改页面
        Map<String,Object>map1 = new HashMap<>();
        map1.put("0","驳回");
        map1.put("1","销售成功");
        map1.put("2","申请退货");
        map1.put("3","退货成功");
        map.put("states",map1);
        User loginUser = (User) request.getSession().getAttribute("user");
        List<Ware> listW = ws.getWare();
        List<Depot> list = service.getname();
        map.put("wareList",listW);//销售商品
        map.put("depotList",list);//销售商品的仓库
        map.put("saleOrder", new SaleOrder());
        return "saleOrderAU";
    }

    /**
     * 申请退货
     * @param map
     * @return
     */
    @RequestMapping(value = "/sale_orderTable/add1",method = RequestMethod.GET)
    public String requAdd(@PathVariable Integer id,Map<String,Object>map,SaleOrderBO saleOraderBO){
        SaleOrder saleOrder = saleService.getById(id);
        if (saleOrder.getStates()==2){
            saleOraderBO.setOrderType("1");
        }
        return "saleOrderAU";
    }

    /**
     * 保存添加销售订单表
     * @return
     */
    @RequestMapping(value = "/sale_orderTable",method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String saveAdd(SaleOrder sale,HttpServletRequest request){
        //将时间戳设置进入创建时间
        sale.setCreateTime(new Timestamp(System.currentTimeMillis()));
        //将修改时间设进修改时间，初始修改时间，后期会改
        sale.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        //获取session 当中当前登录用户，session属性名从login登录的传过来，
        User user = (User) request.getSession().getAttribute("user");
        sale.setCreateEmp(user.getName());
        //这个初始的修改人，后期会改
        sale.setUpdateEmp(user.getName());
        Log log = new Log();
        try {//添加log日志
            log.setModular("销售订单模块");
            log.setTable("sale_order");
            //模块的操作类型（0抛异常，1新增，2删除，3修改，4用户登录，5用户退出）
            log.setType(1);
            //log.setFieldOldValue();  //新增数据忽略前置
            log.setFieldNewValue(user.toString());
            log.setCreateTime(new Date());
            log.setCreateEmp(user.getName());
        }catch (Exception e){
            log.setModular("销售订单模块");
            log.setTable("sale_order");
            //模块的操作类型（0抛异常，1新增，2删除，3修改，4用户登录，5用户退出）
            log.setType(0);
            log.setFieldNewValue(user.toString());
            log.setCreateTime(new Date());
            log.setContent(e.getMessage());
            log.setCreateEmp(user.getName());
        }
        logService.addLog(log);
        return "{\"code\":1,\"msg\":\"保存成功\"}";
    }

    /**
     * 跳转修改销售订单表页面
     * @return
     */
    @RequestMapping(value = "/sale_orderTable/{id}",method = RequestMethod.GET)
    public String jumpUpdate(@PathVariable("id") Integer id, Map<String, Object> map,SaleOrderBO saleOrderBO){
        Map<String,Object>map1 = new HashMap<>();
        map1.put("0","驳回");
        map1.put("1","销售成功");
        map1.put("2","申请退货");
        map1.put("3","退货成功");
        map.put("states",map1);
        saleOrderBO.setOrderType("1");//订单的类型，用于新增/修改页面
        SaleOrder saleOrder = saleService.getById(id);
        List<Ware> listW = ws.getWare();
        map.put("wareList",listW);
        List<Depot> list = service.getname();
        map.put("depotList",list);
        map.put("saleOrder",saleOrder);//根据id获取对象放入request中
        return "saleOrderAU";
    }

    /**
     * 保存修改销售订单表
     * @param sale
     * @return
     */
    @RequestMapping(value = "/sale_orderTable",method = RequestMethod.PUT, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String SaveOrUpdate(SaleOrder sale, Map<String, Object> map, HttpServletRequest request){
        User user1 = (User) request.getSession().getAttribute("user");//获得当前用户
        SaleOrder oldSale= saleService.getById(sale.getId());//修改之前的值
        sale.setUpdateTime(new Date());//获取修改当前时间
        sale.setCreateEmp(((User)request.getSession().getAttribute("user")).getName());//获取修改用户
        Log log = new Log();
        try {
            saleService.update(sale);
            //添加log日志
            log.setModular("销售订单模块");
            log.setTable("sale_order");
            //模块的操作类型（0抛异常，1新增，2删除，3修改，4用户登录，5用户退出）
            log.setType(3);
            log.setFieldOldValue(oldSale.toString());  //新增数据忽略前置
            log.setFieldNewValue(sale.toString());
            log.setCreateTime(new Timestamp(System.currentTimeMillis()));
            log.setCreateEmp(user1.getName());
        }catch (Exception e){
            //添加log日志
            log.setModular("销售订单模块");
            log.setTable("sale_order");
            //模块的操作类型（0抛异常，1新增，2删除，3修改，4用户登录，5用户退出）
            log.setType(0);
            log.setFieldOldValue(oldSale.toString());
            log.setFieldNewValue(sale.toString());
            log.setContent(e.getMessage());
            log.setCreateTime(new Timestamp(System.currentTimeMillis()));
            log.setCreateEmp(user1.getName());
        }
        logService.addLog(log);
        return "{\"code\":1,\"msg\":\"修改成功\"}";
    }

    /**
     * 根据id删除销售订单表
     * @param id
     */
    @RequestMapping(value = "/sale_orderTable/{id}",method = RequestMethod.DELETE, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String delete(@PathVariable Integer id,HttpServletRequest request){
        User user1 = (User) request.getSession().getAttribute("user");//获得当前用户
        Log log = new Log();
        try {
            saleService.delete(id);//删除用户
            log.setModular("销售订单模块");
            log.setTable("sale_order");
            //模块的操作类型（0抛异常，1新增，2删除，3修改，4用户登录，5用户退出）
            log.setType(3);
            log.setFieldOldValue(saleService.getById(id).toString());
            log.setCreateTime(new Timestamp(System.currentTimeMillis()));
            log.setCreateEmp(user1.getName());
        }catch (Exception e){
            log.setModular("销售订单模块");
            log.setTable("sale_order");
            //模块的操作类型（0抛异常，1新增，2删除，3修改，4用户登录，5用户退出）
            log.setType(0);
            log.setFieldOldValue(saleService.getById(id).toString());
            log.setContent(e.getMessage());
            log.setCreateTime(new Timestamp(System.currentTimeMillis()));
            log.setCreateEmp(user1.getName());
        }
        logService.addLog(log);

        return "{\"code\":1,\"msg\":\"删除成功\"}";
    }
}
