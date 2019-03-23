package com.yr.log.service.impl;

import com.yr.entitys.Log.Log;
import com.yr.entitys.bo.LogBO.LogBo;
import com.yr.entitys.page.Page;
import com.yr.log.dao.LogDao;
import com.yr.log.service.LogService;
import com.yr.util.JsonDateValueProcessor;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 *log service层即业务逻辑层
 * @author
 * @since
 */
@Service
@Transactional
public class LogServiceImpl implements LogService{

    @Autowired
    private LogDao logDaoImpl;

    /**
     * 以分页的形式查询出log表中的数据
     * @param logBoPage
     * @return json
     */
    @Override
    public String query(Page<LogBo> logBoPage) {
        //查询总条数加入page中
        logBoPage.setTotalRecord(logDaoImpl.getCount(logBoPage));
        //分页查询的数据
        List<LogBo> list = logDaoImpl.query(logBoPage);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        JSONArray jsonArray = JSONArray.fromObject(list,jsonConfig);
        String json = "{\"code\": 0,\"msg\": \"\",\"count\": " + logBoPage.getTotalRecord() + ",\"data\":" + jsonArray + "}";
        return json;
    }

    /**
     * 新增日志记录，
     * @param log
     */
    @Override
    public void addLog(Log log) {
        logDaoImpl.addLog(log);
    }
}
