package com.yr.common.dao;

import com.yr.entitys.bo.menuBO.MenuBO;
import com.yr.entitys.menu.Menu;
import com.yr.entitys.page.Page;
import com.yr.entitys.user.User;

import java.util.List;

public interface MenuDao {

    /**
     * 查询
     * @return List<Menu>
     */
    //查询菜单数据用来生成菜单导航
    List<Menu> query(List<String> list);

    Long queryCount(Page<MenuBO> page);

    List<Menu> querySupMenu(Integer pid);

    //查询菜单数据用来生成菜单表格
    List<MenuBO> queryList(Page<MenuBO> page);

    Menu getOneMenu(Integer id);
    //新增菜单
    void add(MenuBO menuBO,User loginUser);
    //根据fontClass查询图标
    String queryIcon(String fontClass);
    //查询图标unicode
    String queryIconUnicode(String str);
    //根据url查询url的总数
    Long queryUrl(Menu menu);
    //根据id删除菜单表记录
    void delete(Integer id);
    //修改菜单记录
    void update(Menu menu);

    Integer getCount(String url);
}
