layui.use(['table'],function(){
	var table = layui.table;

	/*lin  添加*/
    //获得项目名字path，在js中可以直接用path调用
    var strFullPath = window.document.location.href,
        strPath = window.document.location.pathname,
        pos = strFullPath.indexOf(strPath),
        prePath = strFullPath.substring(0, pos),
        path = strPath.substring(0, strPath.substr(1).indexOf('/') + 1) + "/";
	;

	//系统日志
    table.render({
        elem: '#logs',
        url : path+'/log/logTable/list',
        //request下面是请求后台的参数的别名,response是响应的别名
        request: {
            pageName: 'currentPage' //页码的参数名称，默认：page
            , limitName: 'pageSize' //每页数据量的参数名，默认：limit
        },
        cellMinWidth : 95,
        page : true,
        height : "full-20",
        limit :15,
        limits : [10,15,20,25],
        id : "systemLog",
        cols : [[
            {type: "checkbox", fixed:"left", width:50},
            {field: 'id', title: '序号', width:60, align:"center"},
            {field: 'modular', title: '日志产生的模块', width:350},
            {field: 'table', title: '日志产生的表名',  align:'center',minWidth:130},
            {field: 'type', title: '模块操作类型',  align:'center',minWidth:130, unresize: true,templet:function(d){
                    if(d.type == 0){
                        return "抛异常";
                    }else if(d.type == 1){
                        return "新增";
                    }else if(d.type == 2){
                        return "删除";
                    }else if(d.type == 3){
                        return "修改";
                    }else if(d.type == 4){
                        return "用户登录";
                    }else if (d.type == 5)
                    {
                        return "用户退出";
                    }else if (d.type == 6)
                    {
                        return "excel导出";
                    }else  if (d.type == 7)
                    {
                        return "excel导入";
                    }
                }},
            {field: 'fieldOldValue', title: '操作的字段旧值',  align:'center',minWidth:130},
            {field: 'fieldNewValue', title: '操作的字段新值',  align:'center',minWidth:130},
            {field: 'content', title: '异常内容',  align:'center',minWidth:130},
            {field: 'createEmp',title: '操作人', minWidth:100, templet:'#newsListBar',align:"center"},
            {field: 'createTime', title: '操作时间', align:'center', width:170}
        ]]
    });
})

