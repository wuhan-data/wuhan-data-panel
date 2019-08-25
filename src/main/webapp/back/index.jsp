﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
<%@page import="com.wuhan_data.pojo.Admin"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>WUHANDATA</title>
    <!-- Bootstrap Styles-->
    <link href="<%=path %>/assets/css/bootstrap.css" rel="stylesheet" />
    
    <link href="<%=path %>/assets/css/bootstrap-switch.min.css" rel="stylesheet" />
    <!-- FontAwesome Styles-->
    <link href="<%=path %>/assets/css/font-awesome.css" rel="stylesheet" />
    <!-- Morris Chart Styles-->
    <!-- <link href="back/assets/js/morris/morris-0.4.3.min.css" rel="stylesheet" /> -->
    <!-- Custom Styles-->
    <link href="<%=path %>/assets/css/custom-styles.css" rel="stylesheet" />
    
    <link href="<%=path %>/assets/css/bootstrap.css" rel="stylesheet" />
    <!-- FontAwesome Styles-->
    <link href="<%=path %>/assets/css/font-awesome.css" rel="stylesheet" />
    <!-- Morris Chart Styles-->
   <!--  <link href="assets/js/morris/morris-0.4.3.min.css" rel="stylesheet" /> -->
    <!-- Custom Styles-->
   <link href="<%=path %>/assets/css/custom-styles.css" rel="stylesheet" /> 
    
    <link href="//netdna.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <!-- Google Fonts-->
    <link href='https://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
    <link href="assets/css/bootstrap-order.min.css" rel="stylesheet" />
</head>

<body>
    <div id="wrapper">
    
        <nav class="navbar navbar-default top-navbar" role="navigation" style="background:url('assets/img/bimg.png');background-size:100%;height:auto;">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="back/index.jsp">WUHANDATA</a>
            </div>

            <ul class="nav navbar-top-links navbar-right">
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#" aria-expanded="false">
                        <i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-user">
                        <li class="divider"></li>
						<%out.print(((Admin)session.getAttribute("user")).getUsername()); %>你好   

                        <li><a href="#"><i class="fa fa-sign-out fa-fw"></i> Logout</a>
                        </li>
                    </ul>
<!--                     /.dropdown-user -->
                </li>
<!--                 /.dropdown -->
            </ul>
        </nav>
        <!--/. NAV TOP  -->
        
        
        <nav class="navbar-default navbar-side" role="navigation">
            <div class="sidebar-collapse">
                <ul class="nav" id="main-menu">

                    <li>
                        <a class="" href="toIndex"><i class="fa fa-dashboard"></i>首页</a>
                       <!--  <a class="active-menu" href="toIndex"><i class="fa fa-dashboard"></i>首页</a> -->
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-reorder"></i>数据管理<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level" >
                            <li>
                                <a a href="#" onclick="menuClick('listIndexManage')" id="listIndexManage"><i class="fa fa-bar-chart-o"></i>指标数据维护</a>
                            </li>
							<li>
                                <a href="#" onclick="menuClick('indexExport')" id="indexExport"><i class="fa fa-arrow-circle-down"></i>指标数据导出</a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <!-- <a href="init"><i class="fa fa-quote-left"></i>栏目管理</a> -->
                        <a href="#" onclick="menuClick('init')" id="init"><i class="fa fa-list"></i>栏目管理</a>
                    </li>
                    <li>
                        <!-- <a href="specialInit"><i class="fa fa-quote-left"></i>专题管理</a> -->
                        <a href="#" onclick="menuClick('specialInit')" id="specialInit"><i class="fa fa-line-chart"></i>专题管理</a>
                    </li>
                    
                    <li>
                        <!-- <a href="specialInit"><i class="fa fa-quote-left"></i>专题管理</a> -->
                        <a href="#" onclick="menuClick('indexPicInit')" id="indexPicInit"><i class="fa fa-picture-o"></i>轮播图管理</a>
                    </li>

                    <li>
                        <a href="#"><i class="fa fa-bell-o"></i>辅助功能<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            
                            <li>
                            <a href="#" onclick="menuClick('sysLogInit')" id="sysLogInit"><i class="fa fa-quote-left"></i>日志管理</a>
                                
                            </li>
                            <li>
                            <a href="#" onclick="menuClick('messageInit')" id="messageInit"><i class="fa fa-quote-left"></i>消息管理</a>
          
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-cogs"></i>系统管理<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            <li>
                            <a href="#" onclick="menuClick('departmentInit')" id="departmentInit"><i class="fa fa-quote-left"></i>组织架构管理</a>
                              
                            </li>
                            <li>
                            <a href="#" onclick="menuClick('userInit')" id="userInit"><i class="fa fa-quote-left"></i>用户管理</a>
                               
                            </li>
                            <li>
                            <a href="#" onclick="menuClick('roleInit')" id="roleInit"><i class="fa fa-quote-left"></i>角色管理</a>
                                
                            </li>
                            <li>
                            <a href="#" onclick="menuClick('adminInit')" id="adminInit"><i class="fa fa-quote-left"></i>管理员管理</a>
                                
                            </li>
                            <li>
                            <a href="#" onclick="menuClick('feedbackInit')" id="feedbackInit"><i class="fa fa-quote-left"></i>用户反馈</a>
                               
                            </li>
                            <li>
                            <a href="#" onclick="menuClick('versionInit')" id="versionInit"><i class="fa fa-quote-left"></i>版本更新</a>
                               
                            </li>
                        </ul>
                    </li>
                </ul>

            </div>

        </nav>
        <!-- /. NAV SIDE  -->
        <div id="getNewData" width="100%" height="100%">
        
        
         <div id="page-wrapper" >
            <div id="page-inner" >
            

			
<div id="welcome" width="100%" height="100%" >

<div class="row">
                    <div class="col-md-12">
                        <h1 class="page-header">
                            Home <small>Summary of the App</small>
                        </h1>
                    </div>
                </div>
                
                <div style="width:auto;height:300px">
                	 <h1 style="">欢迎登录湖北省宏观经济APP服务端</h1>
                </div>
               
</div>
                
                
                <!-- /. ROW  -->
            </div>
            <!-- /. PAGE INNER  -->
        </div>
</div>


       
        <!-- /. PAGE WRAPPER  -->
    </div>
    <!-- /. WRAPPER  -->
    <!-- JS Scripts-->
    <!-- jQuery Js -->
    <script src="<%=path %>/assets/js/jquery-1.10.2.js"></script>
      <!-- Metis Menu Js -->
    <script src="<%=path %>/assets/js/jquery.metisMenu.js"></script>
    <!-- Bootstrap Js -->
    <script src="<%=path %>/assets/js/bootstrap.min.js"></script>
  
    <!-- Morris Chart Js -->
    <script src="<%=path %>/assets/js/morris/raphael-2.1.0.min.js"></script>
    <script src="<%=path %>/assets/js/morris/morris.js"></script> 
    <!-- Custom Js -->
    <script src="<%=path %>/assets/js/custom-scripts.js"></script> 
    
   <script>

   
   
   menuClick = function(menuUrl) {
	   $(".active-menu").removeClass('active-menu');
	   $("#"+menuUrl).addClass('active-menu');
   	 $.ajax({
                type: 'GET',
                url:  menuUrl,
                dataType: "html",
                cache:false,
                success: function(data){
                    $('#getNewData').html(data);
                },
                error : function(data){
                }
            });           	
/*             $("#iframe-page-content").attr('src',menuUrl); */
   };
   </script>

	
</body>

</html>