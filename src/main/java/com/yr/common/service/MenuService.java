package com.yr.common.service;

import com.yr.entitys.bo.menuBO.MenuBO;
import com.yr.entitys.menu.Menu;
import com.yr.entitys.page.Page;
import com.yr.entitys.user.User;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MenuService {

    /**
     * 查询
     * @return String
     */
    String query(List<String> list);


    String queryMenus(Page<MenuBO> page);

    //根据id查询菜单记录
    MenuBO getOneMenu(Integer id);

    //查询父菜单
    List<Menu> querySupMenu(Integer pid);

    //删除菜单记录
    String delete(Integer id);

    //修改菜单记录
    String update(MenuBO menuBO);

    /**
     * 添加
     * @param menuBO
     */
    String add(MenuBO menuBO,User loginUser);


}
