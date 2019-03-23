package com.yr.common.service.impl;

import com.yr.common.dao.MenuDao;
import com.yr.common.service.MenuService;
import com.yr.entitys.bo.menuBO.MenuBO;
import com.yr.entitys.menu.Menu;
import com.yr.entitys.page.Page;
import com.yr.entitys.user.User;
import com.yr.user.service.PermissionService;
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
@Transactional
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuDao menuDaoImp;
    @Autowired
    @Qualifier("permissionServiceImpl")
    private PermissionService permissionService;

    /**
     * 返回固定格式，固定字段名的json字符串到页面，用来动态显示主页面右边的二级菜单
     */
    @Override
    public String query(List<String> list) {
        List<MenuBO> menuTurnMenuBOList = new ArrayList<MenuBO>();
        for (Menu menu : menuDaoImp.query(list)) {
            //这里new对象，要使用自动注入吗？？
            MenuBO menuBO = new MenuBO();
            menuBO.setId(menu.getId());
            menuBO.setTitle(menu.getName());
            menuBO.setIcon(menu.getPic());
            menuBO.setPid(menu.getPid());
            menuBO.setHref(menu.getUrl());
            menuBO.setMethod(menu.getMethod());
            menuTurnMenuBOList.add(menuBO);
        }

        List<MenuBO> menuBOList = new ArrayList<MenuBO>();
        for (MenuBO menuBO : menuTurnMenuBOList) {
            if (menuBO.getPid() == 0){
                List<MenuBO> subMenuList = new ArrayList<MenuBO>();
                for (MenuBO menuBO1 : menuTurnMenuBOList) {
                    if (menuBO1.getPid() == menuBO.getId()){
                        subMenuList.add(menuBO1);
                    }
                }
                menuBO.setChildren(subMenuList);
                menuBOList.add(menuBO);
            }
        }
        String menuJsonStr = JSONArray.fromObject(menuBOList).toString();
        String contentManagement = "{\"contentManagement\":"+menuJsonStr+"}";

        System.out.println(contentManagement);
        return contentManagement;
    }

    /**
     * 把Menu转成MenuBO，再转成json字符串返回，用于页面菜单主页生成菜单树形表
     */
    @Override
    public String queryMenus(Page<MenuBO> page) {
        Long count = menuDaoImp.queryCount(page);
        page.setTotalRecord(count);
        List<MenuBO> list = menuDaoImp.queryList(page);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        JSONArray jsonArray = JSONArray.fromObject(list,jsonConfig);
        String strJson = "{" +
                "\"msg\": \"\"," +
                "\"code\": 0," +
                "\"data\":"+jsonArray+"," +
                "\"count\":"+count+"," +
                "\"is\": true," +
                "\"tip\": \"操作成功！\"" +
                "}";
        return strJson;
    }

    @Override
    public MenuBO getOneMenu(Integer id) {
        Menu menu = menuDaoImp.getOneMenu(id);
        MenuBO menuBO = new MenuBO();
        menuBO.setMenu(menu);
        return menuBO;
    }

    @Override
    public List<Menu> querySupMenu(Integer pid) {
        List<Menu> list = menuDaoImp.querySupMenu(pid);
        Menu menu1 = new Menu();
        menu1.setName("无");
        menu1.setPid(0);
        menu1.setId(0);
        list.add(menu1);
        return list;
    }

    @Override
    public String delete(Integer id) {
        menuDaoImp.delete(id);
        return "{\"code\":1,\"msg\":\"删除成功\"}";
    }

    @Override
    public String update(MenuBO menuBO) {
        String unicode = menuDaoImp.queryIconUnicode(menuBO.getMenu().getPic());
        menuBO.getMenu().setPic(unicode);
        menuDaoImp.update(menuBO.getMenu());
        return "{\"code\":1,\"msg\":\"修改保存成功\"}";
    }

    /**
     * 添加
     * @param menuBO
     */
    public String add(MenuBO menuBO,User loginUser){
        String str = "{\"code\":0,\"msg\":\"新增保存失败\"}";
        String unicode = menuDaoImp.queryIcon(menuBO.getMenu().getPic());
        menuBO.getMenu().setPic(unicode);
        if ((menuDaoImp.queryUrl(menuBO.getMenu()) == 0)){
            menuDaoImp.add(menuBO,loginUser);
            str = "{\"code\":1,\"msg\":\"新增保存成功\"}";
        }else {
            str = "{\"code\":2,\"msg\":\"菜单名/url重复了，请重新输入\"}";
        }

        return str;
    }

}
