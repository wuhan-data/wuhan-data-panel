<%@page import="com.wuhan_data.pojo.Admin"%>
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
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>WUHANDATA</title>
	<!-- Bootstrap Styles-->
    <link href="assets/css/bootstrap.css" rel="stylesheet" />
     <!-- FontAwesome Styles-->
    <link href="assets/css/font-awesome.css" rel="stylesheet" />
        <!-- Custom Styles-->
    <link href="assets/css/custom-styles.css" rel="stylesheet" />
    
     <link href="assets/css/bootstrap-switch.min.css" rel="stylesheet" />
    
    <link href="assets/css/my.css" rel="stylesheet" />
    
    <link href="assets/css/bootstrap-order.min.css" rel="stylesheet" />
     <!-- Google Fonts-->
   <link href='https://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />


    <style type="text/css" rel="stylesheet">

		a{
		hover:text-decoration:none;}
        .page { float:right; margin:10px 40px; line-height:25px;}
        .w28 { width:28px; height:23px; line-height:23px; text-align:center;}

        .page .pre, .page .next { width:57px; height:25px; background:url(images/page.gif) no-repeat; line-height:23px; text-indent:-9999px;/* border:1px solid #ccc; */ }
        .page .pre { margin:0 10px 0 0;}
        .page .next { background-position:left bottom; margin:0 2px 0 7px;}

        .page .first, .page .last { width:57px; height:25px; background:url(images/page_be.gif) no-repeat; line-height:25px; text-indent:-9999px;/* border:1px solid #ccc; */ }
        .page .first { margin:0 10px 0 15px;}
        .page .last { background-position:left bottom; margin:0 20px 0 7px;}

        .page .num { width:25px; height:23px; line-height:23px; margin-right:3px; text-align:center; border:1px solid #ccc; color:#333;}
        .page .cur { display:inline-block; width:25px; height:23px; margin-right:3px; line-height:25px; text-align:center;  /* border:1px solid #468bbe; */ color:#468bbe;}
        .page .num:hover { background:#4c8ccc; border:1px solid #4c8ccc; color:#fff; text-decoration:none;}
       /*  .page .go { display:inline-block; width:25px; height:23px; line-height:23px; text-align:center; border:1px solid #ccc;} */
        .page b{ color:#2979b4}

    </style>
     <script type="text/javascript">
    function checkUserTel(){
     	 var rolecode = document.getElementById("addUserTel").value;
     	 var reg = /^(13[0-9]|15[012356789]|17[0135678]|18[0-9]|14[579])[0-9]{8}$/;
          if (!reg.test(rolecode)) {
        	  span_userTel.innerHTML = "请填写正确的手机号码";
              return false;
          }
          else {
        	  span_userTel.innerHTML = "格式正确";
              return true;
          }
     }
      function checkForm(){
      	var roleCode=checkUserTel();
      	if (roleCode)
      		return true;
      	else
      		return false;
      }
            
      function edit_checkUserTel(){
      	 var rolecode = document.getElementById("editUserTel").value;
      	 var reg = /^(13[0-9]|15[012356789]|17[0135678]|18[0-9]|14[579])[0-9]{8}$/;
           if (!reg.test(rolecode)) {
         	  span_userTel.innerHTML = "请填写正确的手机号码";
               return false;
           }
           else {
         	  span_userTel.innerHTML = "格式正确";
               return true;
           }
      }
       function edit_checkForm(){
       	var roleCode=edit_checkUserTel();
       	if (roleCode)
       		return true;
       	else
       		return false;
       }
      </script>
</head>
<body>
    <div id="wrapper">
        <nav class="navbar navbar-default top-navbar" role="navigation">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="index.html">WUHANDATA</a>
            </div>

            <ul class="nav navbar-top-links navbar-right">
                <!-- <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#" aria-expanded="false">
                        <i class="fa fa-envelope fa-fw"></i> <i class="fa fa-caret-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-messages">
                        <li>
                            <a href="#">
                                <div>
                                    <strong>John Doe</strong>
                                    <span class="pull-right text-muted">
                                        <em>Today</em>
                                    </span>
                                </div>
                                <div>Lorem Ipsum has been the industry's standard dummy text ever since the 1500s...</div>
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="#">
                                <div>
                                    <strong>John Smith</strong>
                                    <span class="pull-right text-muted">
                                        <em>Yesterday</em>
                                    </span>
                                </div>
                                <div>Lorem Ipsum has been the industry's standard dummy text ever since an kwilnw...</div>
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="#">
                                <div>
                                    <strong>John Smith</strong>
                                    <span class="pull-right text-muted">
                                        <em>Yesterday</em>
                                    </span>
                                </div>
                                <div>Lorem Ipsum has been the industry's standard dummy text ever since the...</div>
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a class="text-center" href="#">
                                <strong>Read All Messages</strong>
                                <i class="fa fa-angle-right"></i>
                            </a>
                        </li>
                    </ul>
                    /.dropdown-messages
                </li>
                /.dropdown
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#" aria-expanded="false">
                        <i class="fa fa-tasks fa-fw"></i> <i class="fa fa-caret-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-tasks">
                        <li>
                            <a href="#">
                                <div>
                                    <p>
                                        <strong>Task 1</strong>
                                        <span class="pull-right text-muted">60% Complete</span>
                                    </p>
                                    <div class="progress progress-striped active">
                                        <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%">
                                            <span class="sr-only">60% Complete (success)</span>
                                        </div>
                                    </div>
                                </div>
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="#">
                                <div>
                                    <p>
                                        <strong>Task 2</strong>
                                        <span class="pull-right text-muted">28% Complete</span>
                                    </p>
                                    <div class="progress progress-striped active">
                                        <div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="28" aria-valuemin="0" aria-valuemax="100" style="width: 28%">
                                            <span class="sr-only">28% Complete</span>
                                        </div>
                                    </div>
                                </div>
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="#">
                                <div>
                                    <p>
                                        <strong>Task 3</strong>
                                        <span class="pull-right text-muted">60% Complete</span>
                                    </p>
                                    <div class="progress progress-striped active">
                                        <div class="progress-bar progress-bar-warning" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%">
                                            <span class="sr-only">60% Complete (warning)</span>
                                        </div>
                                    </div>
                                </div>
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="#">
                                <div>
                                    <p>
                                        <strong>Task 4</strong>
                                        <span class="pull-right text-muted">85% Complete</span>
                                    </p>
                                    <div class="progress progress-striped active">
                                        <div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="85" aria-valuemin="0" aria-valuemax="100" style="width: 85%">
                                            <span class="sr-only">85% Complete (danger)</span>
                                        </div>
                                    </div>
                                </div>
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a class="text-center" href="#">
                                <strong>See All Tasks</strong>
                                <i class="fa fa-angle-right"></i>
                            </a>
                        </li>
                    </ul>
                    /.dropdown-tasks
                </li>
                /.dropdown
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#" aria-expanded="false">
                        <i class="fa fa-bell fa-fw"></i> <i class="fa fa-caret-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-alerts">
                        <li>
                            <a href="#">
                                <div>
                                    <i class="fa fa-comment fa-fw"></i> New Comment
                                    <span class="pull-right text-muted small">4 min</span>
                                </div>
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="#">
                                <div>
                                    <i class="fa fa-twitter fa-fw"></i> 3 New Followers
                                    <span class="pull-right text-muted small">12 min</span>
                                </div>
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="#">
                                <div>
                                    <i class="fa fa-envelope fa-fw"></i> Message Sent
                                    <span class="pull-right text-muted small">4 min</span>
                                </div>
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="#">
                                <div>
                                    <i class="fa fa-tasks fa-fw"></i> New Task
                                    <span class="pull-right text-muted small">4 min</span>
                                </div>
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="#">
                                <div>
                                    <i class="fa fa-upload fa-fw"></i> Server Rebooted
                                    <span class="pull-right text-muted small">4 min</span>
                                </div>
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a class="text-center" href="#">
                                <strong>See All Alerts</strong>
                                <i class="fa fa-angle-right"></i>
                            </a>
                        </li>
                    </ul>
                    /.dropdown-alerts
                </li>
                /.dropdown -->
                <li class="dropdown">
                
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#" aria-expanded="false">
                        <i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-user">
                       <!--  <li><a href="#"><i class="fa fa-user fa-fw"></i> User Profile</a>
                        </li>
                        <li><a href="#"><i class="fa fa-gear fa-fw"></i> Settings</a>
                        </li>
                        <li class="divider"></li> -->
                        <%out.print(((Admin)session.getAttribute("user")).getUsername()); %>你好         
                        <li><a href="adminLogout"><i class="fa fa-sign-out fa-fw"></i> 退出</a>
                        </li>
                    </ul>
                    <!-- /.dropdown-user -->
                </li>
                <!-- /.dropdown -->
            </ul>
        </nav>
        <nav class="navbar-default navbar-side" role="navigation">
            <div class="sidebar-collapse">
            <ul class="nav" id="main-menu">
            <li>
                <a class="active-menu" href="back/index.jsp"><i class="fa fa-dashboard"></i>首页</a>
            </li>
            <c:forEach items="${menuList}" var="c" varStatus="st">
           		 <li>
                        <a href="#"><i class="${c.level_twoInOneList.get(0).perm}"></i>${c.level_one}<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                        	<c:forEach items="${c.level_twoInOneList}" var="cc" varStatus="status">
                        
                            	<li>
                                	<a href="${cc.url}">${cc.level_two}</a>
                            	</li>
                        	 </c:forEach>
                        </ul>
                  </li>
            </c:forEach> 
            </ul>
            </div>
            </nav>
        <!--/. NAV TOP  -->
        <!-- 
        <nav class="navbar-default navbar-side" role="navigation">
            <div class="sidebar-collapse">
                <ul class="nav" id="main-menu">

                    <li>
                        <a class="active-menu" href="index.html"><i class="fa fa-dashboard"></i> 首页</a>
                    </li>
                    <li>
                        <a href="index.html"><i class="fa fa-dashboard"></i>首页</a>
                    </li>
                    <li>
                        <a href="metaDataManage.html"><i class="fa fa-list-alt"></i>元数据管理</a>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-bar-chart-o"></i>数据管理<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a href="indiDataManage.html">指标数据维护</a>
                            </li>
                            <li>
                                <a href="dataReview.html">数据审核</a>
                            </li>
                            <li>
                                <a href="reportDataManage.html">报告、报表数据管理</a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-tasks"></i>栏目管理<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            <li class="active-menu">
                                <a href="columnManage.html">栏目维护</a>
                            </li>
                            <li>
                                <a href="columnPowerManage.html">栏目权限维护</a>
                            </li>
                            <li>
                                <a href="columnContentManage.html">内容配置</a>
                            </li>
                            <li>
                                <a href="columnContPowerManage.html">内容权限设置</a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-quote-left"></i>专题管理<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a href="specialManage.html">专题维护</a>
                            </li>
                            <li>
                                <a href="specialPowerManage.html">专题权限维护</a>
                            </li>
                            <li>
                                <a href="specialContentManage.html">内容配置</a>
                            </li>
                            <li>
                                <a href="specialContPowerManage.html">内容权限设置</a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-laptop"></i>发布管理<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a href="columnPublish.html">栏目发布</a>
                            </li>
                            <li>
                                <a href="publishedManage.html">已发布内容管理</a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-bell-o"></i>辅助功能<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a href="noticeInit">通知管理</a>
                            </li>
                            <li>
                                <a href="sysLogInit">日志管理</a>
                            </li>
                            <li>
                                <a href="messageManage.html">消息管理</a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-cogs"></i>系统管理<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a href="departmentInit">组织结构管理</a>
                            </li>
                            <li>
                                <a href="userInit">用户管理</a>
                            </li>
                            <li>
                                <a href="roleInit">角色管理</a>
                            </li>
                        </ul>
                    </li>
                </ul>

            </div>

        </nav> -->
        <!-- /. NAV SIDE  -->
        <div id="page-wrapper" >
            <div id="page-inner">
			 <div class="row">
                    <div class="col-md-12">
                        <h1 class="page-header">
                            系统管理 <small>用户管理</small>
                        </h1>
                    </div>
                </div> 
                 <!-- /. ROW  -->
                 
                 
                                
            <div class="row">
                <div class="col-md-12">
                    <!-- Advanced Tables -->
                    <div class="panel panel-default">
                        <div class="panel-heading">
                             栏目
                        </div>
                        <div class="panel-body">
                     
<%-- <div class="form-group col-md-2">
<form method="post" id="form1" accept-charset="UTF-8">
<select class="form-control" id="FormControlSelect1" name="parentListName" onchange="f1()"> 
   
<c:forEach items="${analysisListParent}" var="c" varStatus="st">
<c:choose>

    <c:when test="${c.type_name==tname}">
        <option value="${c.type_name}" selected>${c.type_name}</option>    
    </c:when>

    <c:otherwise>
        <option value="${c.type_name}">${c.type_name}</option>    
    </c:otherwise>
</c:choose>

</c:forEach>
</select>
</form>
</div> --%>
  
  
   <div class="btns col-md-4">
      <div class="btn btn-info" data-toggle="modal" data-target="#myAddModal" onclick="add()"><i class="fa fa-plus"></i>添加</div>
      <!-- <button class="btn btn-primary" onclick="showSort()"><i class="fa fa-cog"></i>设置</button> -->
    </div>  
  <!--   <form class="form-inline my-2 my-lg-0" style="float:right" id="formSearch">
      <input class="form-control mr-sm-2" type="search" placeholder="PMI指数(全国)" aria-label="Search" id="searchCol">
      <button class="btn btn-success my-2 my-sm-0" onclick="search()">搜索</button>
    </form> -->
    
     <form class="form-inline" style="float:right" id="formSearch" method="post" accept-charset="UTF-8">
      <input class="form-control" type="search" placeholder="按名称搜索" aria-label="Search" id="searchtname" value="">
      <button class="btn btn-success" onclick="search()">搜索</button>
    </form>
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                        <tr>
                                            <th>用户id</th>
                                            <th>用户名称</th>
                                            <th>用户密码</th>
                                            <th>用户状态</th>
                                            <th>用户性别</th>
                                            <th>用户权限</th>
                                            <th>角色</th>
                                            <th>部门</th>
                                            <th>真实姓名</th>
                                            <th>联系方式</th>
                                            <th>出生日期</th>
                                            <th>地区</th>
                                            <th>操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
     <c:forEach items="${userListByPage}" var="c" varStatus="st">
        <tr>
            <td>${c.id}</td>
            <td>${c.username}</td>
            <td>${c.password}</td>
            <td>
            <c:if test="${c.status==0}" var="statusflag">
            	<c:out value="正常"></c:out>
            	</c:if>
            	<c:if test="${not statusflag}" >
            	<c:out value="封禁"></c:out>
            	</c:if>
            </td>
            <td><c:if test="${c.gender==0}" var="genderflag">
            	<c:out value="女"></c:out>
            	</c:if>
            	<c:if test="${not genderflag}" >
            	<c:out value="男"></c:out>
            	</c:if>
            </td>
            <td>${c.role_list}</td>
            <td>${c.role_id}</td>
            <td>${c.department_id}</td>
            <td>${c.real_name}</td>
            <td>${c.tel}</td>
            <td>${c.birthday}</td>
            <td>${c.city}
            <td >
<%-- <div class="btn btn-warning btn-sm" style="margin-right:3px" data-toggle="modal" data-target="#myEditModal" onclick="edit(${c.theme_name})">
<i class="fa fa-edit"></i>修改</div>--%>
 <div class="btn btn-warning btn-sm" style="margin-right:3px" data-toggle="modal" data-target="#myEditModal" onclick="edit('${c.username}','${c.id}','${c.password}','${c.status}','${c.gender}','${c.tel}','${c.real_name}','${c.role_list}','${c.role_id}','${c.department_id}','${c.birthday}','${c.city}')">
<i class="fa fa-edit"></i>修改
</div>
<a href="deleteUser?id=${c.id }">
<div class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i>删除
</div>
</a>
</td>
        </tr>
    </c:forEach>
                                    </tbody>
                                    

       <%--   <input type="hidden" value="${tname}" name="ctname"/> --%>
                                </table>
                                
                                <!--修改 模态框（Modal） -->
<div class="modal fade" id="myEditModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					修改
				</h4>
			</div>
	<form class="form-inline" id="editForm" method="post" accept-charset="UTF-8" action="editUser" onsubmit="return edit_checkForm()">
			<div class="modal-body">		

	<input class="form-control" type="hidden" name="editUserID" id="editUserID">
	<br>
   用户名称：<input class="form-control" type="text" name="editUserName" id="editUserName">  
   <br> 
   用户密码：<input class="form-control" type="text" name="editUserPassword" id="editUserPassword"> 
   <br>
   用户状态：<select class="form-control" type="text" name="editstatus" id="editstatus"> 
   			<option value="0" >正常</option>    
       		<option value="1" >封禁</option>
   </select>
   <br>
   性别：
     <select class="form-control"  name="editgenderSelect" id="editgenderSelect">	
       		<option value="男" > 男</option>    
       		<option value="女" >女</option> 
  	</select>
  	<br>
    用户权限:<input class="form-control" type="search"  name="editUserRole_list" id="editUserRole_list">
     <br>
    用户角色：
  <select class="form-control" name="editroleListSelect" id="editroleListSelect">
  	<c:forEach items="${roleList}" var="c" varStatus="st">
        <option value="${c.role_name}" >${c.role_name}</option>    
	</c:forEach>
  </select>
  <br>
   用户部门：
  <select class="form-control"  name="editdepartmentListSelect" id="editdepartmentListSelect">
  	<c:forEach items="${departmentList}" var="c" varStatus="st">
        <option value="${c.department_name}" >${c.department_name}</option>    
	</c:forEach>
  </select>
  <br>
   真实姓名:<input class="form-control"  name="editUserReal_name" id="editUserReal_name">
     <br>
   手机:<input class="form-control"  name="editUserTel" id="editUserTel" onblur="edit_checkUserTel()">
     <span id="edit_span_userTel">填11位数字</span><br>  
      出生日期：<input class="form-control" type="date" value=""name="editBirthday" id="editBirthday"/><br>
     地区:<input class="form-control" type="search" placeholder="地区" name="editCity" id="editCity">
     <br>
</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭
				</button>
				<button type="submit" class="btn btn-primary" onclick="add()">
					提交
				</button>
			</div>
			</form>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>



                                <!--添加 模态框（Modal） -->
<div class="modal fade" id="myAddModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					添加
				</h4>
			</div>
			
			<form class="form-inline" id="addForm" method="post" accept-charset="UTF-8" action="addUser" onsubmit="return checkForm()">
			<div class="modal-body">
				
  <!--    用户id：<input class="form-control" type="search" placeholder="用户id" name="addUserId"> -->
     用户名称：<input class="form-control" type="search" placeholder="用户名称" name="addUserName" id="addUserName">
     <br>
     用户密码：<input class="form-control" type="search" placeholder="用户密码" name="addUserPassword" id="addUserPassword">
     <br>
     性别：
     <select class="form-control" id="genderSelect" name="genderSelect" id="genderSelect">	
       		<option value="男" selected> 男</option>    
       		<option value="女" >女</option> 
  	</select>
  	<br>
    用户权限:<input class="form-control" type="search" placeholder="用户权限" name="addUserRole_list" id="addUserRole_list">
     <br>
    用户角色：
  <select class="form-control" id="roleListSelect" name="roleListSelect" id="roleListSelect">
  	<c:forEach items="${roleList}" var="c" varStatus="st">
        <option value="${c.role_name}" >${c.role_name}</option>    
	</c:forEach>
  </select>
  <br>
   用户部门：
  <select class="form-control" id="departmentListSelect" name="departmentListSelect" id="departmentListSelect">
  	<c:forEach items="${departmentList}" var="c" varStatus="st">
        <option value="${c.department_name}" >${c.department_name}</option>    
	</c:forEach>
  </select>
  <br>
   真实姓名:<input class="form-control" type="search" placeholder="真实姓名" name="addUserReal_name" id="addUserReal_name">
     <br>
   手机:<input class="form-control" type="search" placeholder="联系方式" name="addUserTel" id="addUserTel" onblur="checkUserTel()">
     <span id="span_userTel">填11位数字</span><br>
     出生日期：<input class="form-control" type="date" value="2019-01-01"name="addBirthday" id="addBirthday"/><br>
     地区:<input class="form-control" type="search" placeholder="地区" name="addCity" id="addCity">
     <br>
  
</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭
				</button>
				<button type="submit" class="btn btn-primary" onclick="add()">
					提交
				</button>
			</div>
			</form>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>


                                <div class="row">
                                
                                	  <!-- <ul class="col-lg-4"></ul> -->
                                	<%--   <ul class="pagination col-lg-4">
                                	      <form method="post" action="#">
                                	       <c:if test="${page.current!=1 }">
                                	        <li><a href="#">首页</a></li>
                                	        <li></li>
                                	       </c:if>
                                	      </form>
										  <li><a href="#">«</a></li>
										  <li class="active"><a href="#">1</a></li>
										  <li><a href="#">2</a></li>
										  <li><a href="#">3</a></li>
										  <li><a href="#">4</a></li>
										  <li><a href="#">5</a></li>
										  <li><a href="#">»</a></li>
									</ul> --%>
									
									 <div class='page fix'>
                    <form method="post" action="${controlURL}" id="pageForm">
                        共 <b>${page.totalNumber}</b> 条
                        <c:if test="${page.currentPage != 1}">

                           <a href="${controlURL}?currentPage=1" class='first'>首页</a>
                           <a href="${controlURL}?currentPage=${page.currentPage-1}" class='pre'>上一页</a>
                        </c:if>
                        当前第<span>${page.currentPage}/${page.totalPage}</span>页
                        <c:if test="${page.currentPage != page.totalPage}">
                            <a href="${controlURL}?currentPage=${page.currentPage+1}" class='next'>下一页</a>
                            <a href="${controlURL}?currentPage=${page.totalPage}" class='last'>末页</a>
                        </c:if>
                        跳至&nbsp;

                        <input id="currentPageText" type='text' value='${page.currentPage}' class='allInput w28' name="currentPage" />&nbsp;页&nbsp;
                        <input type="submit" value="GO" class="btn-primary btn-sm">
                    </form>
                </div>
								<!-- 	<ul class="col-lg-4"></ul> -->
                                </div>
                              
                                
                            </div>
                            
                        </div>
                    </div>
                    <!--End Advanced Tables -->
                </div>
            </div>
                 
				</div>
             <!-- /. PAGE INNER  -->
            </div>
         <!-- /. PAGE WRAPPER  -->
        </div>
     <!-- /. WRAPPER  -->
    <!-- JS Scripts-->
    <!-- jQuery Js -->
    <script src="assets/js/jquery-1.10.2.js"></script>
      <!-- Bootstrap Js -->
    <script src="assets/js/bootstrap.min.js"></script>
    <!-- Metis Menu Js -->
    <script src="assets/js/jquery.metisMenu.js"></script>
      <!-- Custom Js -->
    <script src="assets/js/custom-scripts.js"></script>
    
    <script src="assets/js/bootstrap-switch.min.js"></script>
   <script src="assets/js/dataTables/jquery.dataTables.js"></script>
    <script src="assets/js/dataTables/dataTables.bootstrap.js"></script>   
    <script src="assets/js/bootstrap-order.min.js"></script>
    <script>
            $(document).ready(function () {
                $('#dataTables-example').dataTable();
               
            });
          //定义Format方法  dateTime--后台传输过来的Date类型  fmt--你要转换的格式
          //返回的是对应fmt时间格式的字符串
          function Format(datetime,fmt) {
                  if (parseInt(datetime)==datetime) {
                      if (datetime.length==10) {
                          datetime=parseInt(datetime)*1000;
                      } else if(datetime.length==13) {
                          datetime=parseInt(datetime);
                      }
                  }
                  datetime=new Date(datetime);
                  var o = {
                      "M+" : datetime.getMonth()+1,                 //月份
                      "d+" : datetime.getDate(),                    //日
                      "h+" : datetime.getHours(),                   //小时
                      "m+" : datetime.getMinutes(),                 //分
                      "s+" : datetime.getSeconds(),                 //秒
                      "q+" : Math.floor((datetime.getMonth()+3)/3), //季度
                      "S"  : datetime.getMilliseconds()             //毫秒
                  };
                  if(/(y+)/.test(fmt))
                      fmt=fmt.replace(RegExp.$1, (datetime.getFullYear()+"").substr(4 - RegExp.$1.length));
                  for(var k in o)
                      if(new RegExp("("+ k +")").test(fmt))
                          fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
                  return fmt;
          }
            function f1(){
            	var select = document.getElementById("FormControlSelect1");
            	var op = select.value;
            	var form1=document.getElementById("form1");
            	var title=encodeURI(encodeURI(op));
            	form1.action="initAnalysisList?op="+title;
            	form1.submit();
            }
            function search(){
            	var searchName=document.getElementById("searchtname").value;
            	alert(searchName)
            	var username=encodeURI(encodeURI(searchName));
            	
            	var formSearch=document.getElementById("formSearch");
            	formSearch.action="userSearchByName?username="+username;
            	formSearch.submit();
            	
            }
  
         /*    function add(themename){
            	alert(themename);
            	var addForm=document.getElementById("");
            	addForm.action="";
            	addFrom.submit();
            } */
            function edit(username,ID,password,status,gender,tel,real_name,role_list,role_id,department_id,birthday,city){
            	$("#editUserID").val(ID);
            	$("#editUserName").val(username);
            	$("#editUserPassword").val(password);
            	$("#editstatus").val(status);
            	if(gender=="0")
            		{$("editgenderSelect").val(0);}
            	else
            		{$("editgenderSelect").val(1);}
            	
            	$("#editUserTel").val(tel);
            	$("#editUserReal_name").val(real_name);
            	$("#editUserRole_list").val(role_list);
            	$("#editroleListSelect").val(role_id);
            	$("#editdepartmentListSelect").val(department_id);
            	$("#editBirthday").val(Format(birthday,"yyyy-MM-dd"));
            	$("#editCity").val(city)
                	
            }
            function del(aid){
            	alert("sss")
            	/* var aid=document.getElementById("aid").value; */
            	alert(aid);
            	var id=encodeURI(encodeURI(aid));
          	    window.location.href="http://localhost:8089/wuhan_data1/delCol?id="+id;  
            /* 	$.ajax({
                    type: "POST",
                    data: {"id":id},
                    url: "deleteCol", 
                    success:function(){
                    	alert("删除成功！");
                    }
            	})  */
            	
            }
            
           var order =  new BootstrapOrder();
           
           
            function showSort() {       
            	order.addItem({id: 1, name: '综合', num: 1});
                order.addItem({id: 2, name: '工业', num: 2})
            	order.addItem({id: 3, name: '农业', num: 3})
            	order.addItem({id: 4, name: '服务业', num: 4})
            	order.addItem({id: 5, name: '投资', num: 5})
            	order.addItem({id: 6, name: '消费', num: 6})
            	order.addItem({id: 7, name: '对外开放', num: 7})
            	order.addItem({id: 8, name: '新经济', num: 8})
            	order.addItem({id: 9, name: '绿色发展', num: 9})
            	order.addItem({id: 10, name: '民生', num: 10})
            	order.toggleShow();
            }
        
            function addSort(item) {
            	order.addItem(item);
            }
        
            function getData(){
                var data = order.getData();
                alert(JSON.stringify(data));
            }
            
                     
            
    </script>
    
   
</body>
</html>
