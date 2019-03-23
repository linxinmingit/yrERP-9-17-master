package com.yr.log.controller;

import com.yr.entitys.bo.LogBO.LogBo;
import com.yr.entitys.page.Page;
import com.yr.log.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 *日志controller层
 * 因为日志不需要进行修改update，和删除delete操作
 * 故没有提供 @ModelAttribute
 *
 * 日志添加各个模块都需要用的到，故没有特意提供一个指定的controller层的新增数据的接口
 * 只需要在模块中注入LogService ，传入log对象进行新增操作即可
 * @author
 * @since
 */
@Controller
@RequestMapping("/log")
public class LogController {

    @Autowired
    private LogService logServiceImpl;

    /**
     * 进入日志列表页面
     * @return
     */
    @RequestMapping(value = "/logTable")
    public  String jumpList()
    {
        return "logs";
    }

    /**
     * 以分页的形势查询出log表的数据信息l
     * @return
     */
    @RequestMapping(value = "/logTable/list",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String query(LogBo logBo, Page<LogBo> page)
    {
        page.setT(logBo);
        //将查询出来的log数据集合返回一个json 数组
        String json = logServiceImpl.query(page);
        return  json ;
    }
}
