package com.yr.log.dao;

import com.yr.entitys.Log.Log;
import com.yr.entitys.bo.LogBO.LogBo;
import com.yr.entitys.bo.user.UserBo;
import com.yr.entitys.page.Page;

import java.util.List;

public interface LogDao {
    /**
     * 添加日志记录
     * @param log
     */
    public void addLog(Log log);

    /**
     * 查询总条数
     * @param page
     * @return Integer
     */
    Long getCount(Page<LogBo> page);

    /**
     * 分页的形式查询log表的数据
     * @return List<UserBo>
     */
    List<LogBo> query(Page<LogBo> page);
}
