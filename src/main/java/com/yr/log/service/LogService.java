package com.yr.log.service;

import com.yr.entitys.Log.Log;
import com.yr.entitys.bo.LogBO.LogBo;
import com.yr.entitys.page.Page;

import java.util.List;

public interface LogService {
    /**
     * 添加日志记录
     * @param log
     */
    void addLog(Log log);

    /**
     * 分页的形式查询log表的数据，并以字符串的形式返回回去
     * @param logBoPage
     * @return
     */
    String query(Page<LogBo> logBoPage);
}
