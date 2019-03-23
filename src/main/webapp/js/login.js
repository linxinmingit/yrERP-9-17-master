layui.use(['form','layer','jquery'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;

    //获得项目名字path，在js中可以直接用path调用
    strFullPath = window.document.location.href,
        strPath = window.document.location.pathname,
        pos = strFullPath.indexOf(strPath),
        prePath = strFullPath.substring(0, pos),
        path = strPath.substring(0, strPath.substr(1).indexOf('/') + 1)+"/";

    $(".loginBody .seraph").click(function(){
        layer.msg("这只是做个样式，至于功能，你见过哪个后台能这样登录的？还是老老实实的找管理员去注册吧",{
            time:5000
        });
    })

    //登录按钮
    form.on("submit(login)",function(data){
        //$(this).text("登录中...").attr("disabled","disabled").addClass("layui-disabled");
        /*setTimeout(function(){
            window.location.href = "../../index.html";
        },1000);*/
    	 

    	//$.post(); //一般在这里发送ajax
    	
    	//登录请求
    	  $.ajax({
			type: 'post',
			url: path+'u_user/userTable/login',//请求登录验证接口
			dataType : 'json',
			data: $('#form1').serialize(),
			success: function(data){
                if(data.code==1) {
                    layer.msg(data.msg, {icon: 1});
                    setTimeout(function(){
                        window.location.href = path+"index";
                    },1500)
                }else {
                    layer.msg(data.msg, {icon: 2});
                    changeCode();
				}
			}
		});

    	  return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    })

    //表单输入效果
    $(".loginBody .input-item").click(function(e){
        e.stopPropagation();
        $(this).addClass("layui-input-focus").find(".layui-input").focus();
    })
    $(".loginBody .layui-form-item .layui-input").focus(function(){
        $(this).parent().addClass("layui-input-focus");
    })
    $(".loginBody .layui-form-item .layui-input").blur(function(){
        $(this).parent().removeClass("layui-input-focus");
        if($(this).val() != ''){
            $(this).parent().addClass("layui-input-active");
        }else{
            $(this).parent().removeClass("layui-input-active");
        }
    })
})