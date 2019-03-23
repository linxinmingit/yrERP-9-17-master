package com.yr.common.controller;

import com.yr.common.service.MenuService;
import com.yr.core.redis.JedisManager;
import com.yr.entitys.bo.menuBO.MenuBO;
import com.yr.entitys.page.Page;
import com.yr.entitys.user.Permission;
import com.yr.entitys.user.User;
import com.yr.user.service.UserService;
import com.yr.util.SerializeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 菜单模块请求controller接口
 */
@Controller
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;
    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;
    @Autowired
    private JedisManager jedisManager;

    /**
     * 查询菜单表，返回json格式字符串，生成主页面的左侧垂直菜单
     * @return List<User>
     */
    @RequestMapping(value="/menuTable/json", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String query(HttpServletRequest request){
        //从redis取出用户拥有的权限
        Jedis jedis = jedisManager.getJedis();//获得redis对象
        byte[] permissionsByte = jedis.get("permissions".getBytes());//获得权限集合
        List<Permission> list = (List<Permission>) SerializeUtil.deserialize(permissionsByte);//将序列化后的byte数组转成对象
        List<String> permissionList = new ArrayList<>();//加入新的list中
        for (Permission permission:list) {
            if("GET".equals(permission.getMethod())){//所有请求为get请求
                permissionList.add(permission.getUrl());
            }
        }
        jedisManager.returnResource(jedis,true);//关闭redis连接
        return menuService.query(permissionList);
    }



    /**
     * 放回固定格式的字符串，生成菜单树形表
     * @return
     */
    @RequestMapping(value="/menuTable/list", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String queryMenus(MenuBO menuBO, Page<MenuBO> page){
        page.setT(menuBO);
        return menuService.queryMenus(page);
    }

    /**
     * 跳转到菜单树形列表页面
     * @return
     */
    @RequestMapping(value="/menuTable", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
    public String gotoMenuList(){
        return "menu";
    }


    /**
     * 调用这个类的所有方法前都要执行有@ModelAttribute的方法
     * @RequestParam(value="id", required = false)表示执行该方法时不必要有id这个参数值，如果是ture则是必须要有id这个参数
     * 因此要增加判断，当页面有表单提交且提交的表单中存在id这个属性时才执行相应的业务，
     * 业务需求只能一个一个来修改，这里的目的是为了修改完成提交表单后先进入这个方法
     * 用提交表单中的id来查询出修改的哪个对象并把修改前的数据保存在Map中（也就是request中），
     * 接着进入修改方法 update(@ModelAttribute("user") User user,Model model)中，
     * 这时@ModelAttribute("user")注解会把不同的字段覆盖掉实现了对象的修改
     * @param id
     * @param map
     * @throws SQLException
     */
    @ModelAttribute
    public void getOneMenu(@RequestParam(value="id", required = false) Integer id,Map<String,Object> map) throws SQLException {

        if(id != null && !"".equals(id)){
            MenuBO menuBO = menuService.getOneMenu(id);
            map.put("menuBO", menuBO);
        }
    }

    /**
     * 新增前需要先进来此方法来跳转页面，目的是给页面的modelAttribute属性传值
     * @return
     */
    @RequestMapping(value="/menuTable/add", method = RequestMethod.GET)
    public String jumpAdd(Map<String, Object> map){
        map.put("menuBO", new MenuBO());//传入一个空的user对象
        map.put("supMenu",menuService.querySupMenu(0));//查询获取父菜单list集合（父菜单的pid为0）
        return "menuAU";
    }

    /**
     * 保存添加
     * @return String
     */
    @ResponseBody
    @RequestMapping(value="/menuTable", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String saveAdd(MenuBO menuBO,HttpServletRequest request){
        //获取页面登录对象
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("user");
        return menuService.add(menuBO,loginUser);
    }

    /**
     * 跳转修改
     * @return String
     */
    @RequestMapping(value="/menuTable/{id}",method=RequestMethod.GET)
    public String jumpUpdate(@PathVariable Integer id, Map<String, Object> map){
        map.put("menuBO", menuService.getOneMenu(id));//根据id获取对象放入request中
        map.put("supMenu",menuService.querySupMenu(0));//查询获取父菜单list集合（父菜单的pid为0）
        return "menuAU";
    }

    /**
     * 保存修改
     * @return String
     */
    @ResponseBody
    @RequestMapping(value="/menuTable",method=RequestMethod.PUT, produces="application/json;charset=UTF-8")
    public String saveUpdate(@ModelAttribute("menuBO")MenuBO menuBO){
        return menuService.update(menuBO);
    }

    /**
     * 删除用户
     * @return String
     */
    @RequestMapping(value="/menuTable/{id}",method=RequestMethod.DELETE, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String delete(@PathVariable Integer id){
        return menuService.delete(id);
    }

}
