/**

 @Name：treeGrid树状表格
 @Author：lrd
 */
//获得项目名字path，在js中可以直接用path调用
strFullPath = window.document.location.href,
    strPath = window.document.location.pathname,
    pos = strFullPath.indexOf(strPath),
    prePath = strFullPath.substring(0, pos),
    path = strPath.substring(0, strPath.substr(1).indexOf('/') + 1)+"/";
layui.config({
    base: path+'layui/extend/'
}).extend({
    dltable:'dltable'
}).define(['laytpl', 'laypage','dltable', 'layer', 'form'], function(exports){
    "use strict";
    var $ = layui.jquery;
    var layer = layui.layer;
    var dltable = layui.dltable;
    var MOD_NAME='treeGrid';
    var treeGrid=$.extend({},dltable);
    treeGrid._render=treeGrid.render;
    treeGrid.render=function(param){//重写渲染方法
        param.isTree=true;//是树表格
        treeGrid._render(param);
    };
    exports(MOD_NAME, treeGrid);
});