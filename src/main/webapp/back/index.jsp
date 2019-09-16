﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
  
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
    
    
    <!-- Google Fonts-->
    <link href='https://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
</head>

<body >
    <div id="wrapper">
        <nav class="navbar navbar-default top-navbar" role="navigation">
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
            </li>
            <c:forEach items="${menuList}" var="c" varStatus="st">
           		 <li>
           		 
                        <a href="#" ><i class="${c.level_twoInOneList.get(0).perm}"></i>${c.level_one}<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                        	<c:forEach items="${c.level_twoInOneList}" var="cc" varStatus="status">
                        
                            	<li>
                                	<a href="#" onclick="menuClick('${cc.url}')" id="${cc.url}" ><i class="fa fa-quote-left"></i>${cc.level_two}</a>
                            	</li>
                        	 </c:forEach>
                        </ul>
                  </li>
            </c:forEach> 
            </ul>
            </div>
        </nav>
        
        <!-- /. NAV SIDE  -->
        <div id="getNewData" width="100%" height="100%">
         <div id="page-wrapper">
            <div id="page-inner">
            

			
<div id="welcome" width="100%" height="100%">

<div class="row">
                    <div class="col-md-12">
                        <h1 class="page-header">
                            Home <small>Summary of your App</small>
                        </h1>
                    </div>
                </div>
                <h1>欢迎登录湖北省宏观经济APP服务端</h1>
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