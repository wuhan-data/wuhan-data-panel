<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>  
<html>  
<head>  
    <meta charset="utf-8">  
    <meta name="robots" content="noindex,nofollow">  
    <title>404 - 页面找不到了</title>  
    <style>  
        body{font-size: 14px;font-family: 'helvetica neue',tahoma,arial,'hiragino sans gb','microsoft yahei','Simsun',sans-serif; background-color:#fff; color:#808080;}  
        .wrap{margin:200px auto;width:510px;}  
        td{text-align:left; padding:2px 10px;}  
        td.header{font-size:22px; padding-bottom:10px; color:#000;}  
        td.check-info{padding-top:20px;}  
        a{color:#328ce5; text-decoration:none;}  
        a:hover{text-decoration:underline;}  
    </style>  
</head>  
<body>  
    <div class="wrap">  
        <table>  
            <tr>  
                <td rowspan="5" style=""><img src='<%=path %>/assets/img/error.png'></td>  
                <td class="header">很抱歉！当前页面找不到了</td>  
            </tr>  
            <tr><td>原因一：你敲错了网址</td></tr>  
            <tr><td>原因二：这个功能正在开发中</td></tr>
            <tr><td>原因三：这个页面被管理员（也就是我）删掉了</td></tr>               
            <tr><td>温馨提示:无法访问只是暂时性的,我们努力修复ing</td></tr>
        </table>  
    </div>  
</body>  
</html>