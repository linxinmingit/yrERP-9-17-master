  
layui.use(['form','layer','upload','table'],function(){
    var form = layui.form
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table,
        upload = layui.upload,
        
      //获得项目名字path，在js中可以直接用path调用
        strFullPath = window.document.location.href,
      	strPath = window.document.location.pathname,
    	pos = strFullPath.indexOf(strPath),
    	prePath = strFullPath.substring(0, pos),
    	path = strPath.substring(0, strPath.substr(1).indexOf('/') + 1)+"/";
        ;
        
    
    form.on("submit(addMenu)",function(data){
    	//弹出loading
    	//var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
    	
    	$.ajax({
    		type: 'post',
    		url: path+'menu/menuTable',
    		dataType : 'json',
    		data: $('#form2').serialize(),
            error: function() {
                layer.msg("操作失败",{icon:2});
                setTimeout(function(){
                    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    parent.layer.close(index); //再执行关闭
                },1200);
            },
    		success: function(data){
    			if(data.code==1){
                    layer.msg(data.msg,{icon:1});
                    setTimeout(function(){
                        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                        parent.layer.close(index); //再执行关闭
                    },1200);
    			}else{
    				layer.msg(data.msg,{icon:2});
                    setTimeout(function(){
                        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                        parent.layer.close(index); //再执行关闭
                    },1200);
    			}
    		}
    	});
    	return false;
    })

    form.on("submit(updateMenu)",function(data){
    	//弹出loading
    	//var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
    	
    	$.ajax({
    		type: 'post',
    		url: path+'menu/menuTable',
    		dataType : 'json',
    		data: $('#form2').serialize(),
            error: function() {
                layer.msg("操作失败",{icon:2});
                setTimeout(function(){
                    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    parent.layer.close(index); //再执行关闭
                },1200);
            },
    		success: function(data){
    			if(data.code==1){
    				layer.msg(data.msg,{icon:1});
                    setTimeout(function(){
                        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                        parent.layer.close(index); //再执行关闭
                    },1200);
    			}else {
    				layer.msg("修改操作失败",{icon:2});
                    setTimeout(function(){
                        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                        parent.layer.close(index); //再执行关闭
                    },1200);
    			}
    		}
    	});
    	
    	return false;
    })
    
/*$("#updatePic").click(function(){
    layer.open({
  title: '修改图标'
  ,content: '可以填写任意的layer代码'
});     
});  */  
    //格式化时间
    function filterTime(val){
        if(val < 10){
            return "0" + val;
        }else{
            return val;
        }
    }
    //定时发布
    var time = new Date();
    var submitTime = time.getFullYear()+'-'+filterTime(time.getMonth()+1)+'-'+filterTime(time.getDate())+' '+filterTime(time.getHours())+':'+filterTime(time.getMinutes())+':'+filterTime(time.getSeconds());

})