<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %><!-- 引入form标签的标签库 -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/css/admin.css" media="all">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/css/eleTree.css" media="all">
    <style>
        html,body{
            width: 100%;
            height: 100%;
            background-color: #fff;
        }

    </style>
</head>
<body>
<div class="eleTree">

</div>
<button id="add">add</button>

<script src="<%=request.getContextPath() %>/layui/layui.js"></script>
<script>

    layui.config({
        base: "../../js/"
    }).use(['jquery','eleTree'], function(){
        var $ = layui.jquery;
        var eleTree = layui.eleTree;

        var data=[
            {
                "label": "a",
                "spread": true,
                "children": [
                    {
                        "label": "aa1",
                        "spread": true,
                        "disabled": true,
                        "children": [
                            {
                                "label": "aaa1",
                                "children": [
                                    {
                                        "label": "aaaa1",
                                        "checked": true
                                    },
                                    {
                                        "label": "bbbb1"
                                    }
                                ]
                            },
                            {
                                "label": "bbb1",
                                "spread": true,
                                "children": [
                                    {
                                        "label": "aaaa1",
                                        "checked": true
                                    }
                                ]
                            },
                            {
                                "label": "ccc1"
                            }
                        ]
                    },
                    {
                        "label": "bb1",
                        "children": [
                            {
                                "label": "aaaa1",
                                "checked": true
                            }
                        ]
                    },
                    {
                        "label": "cc1"
                    }
                ]
            },
            {
                "label": "c",
                "children": [
                    {
                        "label": "aa1",
                        "disabled": true
                    },
                    {
                        "label": "bb1",
                        "checked": true
                    }
                ]
            }

        ]
        eleTree.render({
            elem: '.eleTree',
            // url: "../../data/home/tree.json",
            // type: "post",
            data: data,
            showCheckbox: true,
            contextmenuList: ["copy","add","edit","remove"],
            drag: true,
            accordion: true
        });

        eleTree.on("add",function(data) {
            console.log(data);
            // 若后台修改，则重新获取后台数据，然后重载
            // eleTree.reload({where: {data: JSON.stringify(data.data)}})
        })
        eleTree.on("edit",function(data) {
            console.log(data);
        })
        eleTree.on("remove",function(data) {
            console.log(data);
        })
        eleTree.on("toggleSlide",function(data) {
            console.log(data);
        })
        eleTree.on("checkbox",function(data) {
            console.log(data);
        })
        eleTree.on("drag",function(data) {
            console.log(data);
        })

        $("#add").on("click",function() {
            console.log(eleTree.checkedData);
        })

    });
</script>

</body>
</html>