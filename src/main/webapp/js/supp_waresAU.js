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


   //新增页面头像上传
    //入口函数
    /*upload.render({
        elem: '#test1',//绑定标签ID
        url: path+ 'supp_wares/supplierTable/upload',//文件上传的接口路径
        method : 'post',//文件上传/form表单提交必须是post
        accept : 'images',
        exts : 'jpg|png|jpeg',//给定文件上传的格式
        number : 1,//文件上传的数量
        before: function(obj){//文件上传前的函数
            obj.preview(function(index, file, result){
                $('#demo1').attr('src', result);//头像回显
            });
        },done: function(res, index, upload){//文件上传成功的回调函数，res中包含后台返回文件上传的所有信息
            if(1==res.code){
                layer.msg(res.msg);
                $("#headUrl").val(res.data.src);//把返回的URL保存到jsp页面form表单中的隐藏框
            }else{
                layer.msg(res.msg,{icon:2});
            }
        }
    });*/

    //修改页面头像上传
    //入口函数
    /*upload.render({
        elem: '#test2',//绑定标签ID
        url: path+'upload/img',//文件上传的接口路径
        method : 'post',//文件上传/form表单提交必须是post
        accept : 'images',
        exts : 'jpg|png|jpeg',//给定文件上传的格式
        number : 1,//文件上传的数量
        before: function(obj){//文件上传前的函数
            obj.preview(function(index, file, result){
                $('#demo2').attr('src', result);//头像回显
            });
        },done: function(res, index, upload){//文件上传成功的回调函数，res中包含后台返回文件上传的所有信息
            if(1==res.code){
                layer.msg(res.msg);
                $("#headUrl2").val(res.data.src);//把返回的URL保存到jsp页面form表单中的隐藏框
            }else{
                layer.msg(res.msg,{icon:2});
            }
        }
    });*/

    form.on("submit(addUser)",function(data){
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});

        $.ajax({
            type: 'post',
            url: path+'supp_wares/supplierTable',
            dataType : 'json',
            data: $('#form2').serialize(),
            success: function(data){
                layer.close(index);
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


    form.on("submit(updateUser)",function(data){
        $.ajax({
            type: 'post',
            url: path+'supp_wares/supplierTable',
            dataType : 'json',
            data: $('#form2').serialize(),
            success: function(data){
                if(data.code==1){
                    layer.msg(data.msg,{icon:1});
                    setTimeout(function(){
                        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                        parent.layer.close(index); //再执行关闭
                    },1200);
                }else {
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

    $("#resetAdd").click(function () {
        $(".layui-input").val("");
        $("#filesCopy").val("");
        $("#suppWareType").val("");
    })

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