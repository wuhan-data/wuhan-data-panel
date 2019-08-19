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
                 <li class="dropdown">
                
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#" aria-expanded="false">
                        <i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-user">
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
        <!-- /. NAV SIDE  -->
        <div id="page-wrapper" >
            <div id="page-inner">
			 <div class="row">
                    <div class="col-md-12">
                        <h1 class="page-header">
                            系统管理 <small>管理员管理</small>
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

   <div class="btns col-md-4">
      <div class="btn btn-info" data-toggle="modal" data-target="#myAddModal" onclick="add()"><i class="fa fa-plus"></i>添加</div>
      </div>  
     <form class="form-inline" style="float:right" id="formSearch" method="post" accept-charset="UTF-8">
      <input class="form-control" type="search" placeholder="按名称搜索" aria-label="Search" id="searchtname" value="">
      <button class="btn btn-success" onclick="search()">搜索</button>
    </form>
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                        <tr>
                                            <th>id</th>
                                            <th>管理员名称</th>
                                            <th>管理员密码</th>
                                            <th>管理员状态</th>
                                            <th>管理员权限</th>
                                            <th>创建时间</th>
                                            <th>操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
     <c:forEach items="${adminListByPage}" var="c" varStatus="st">
        <tr>
            <td >${c.id}</td>
            <td >${c.username}</td>
            <td >${c.password}</td>
            <td >
            	<c:if test="${c.status==0}" var="flag">
            	<c:out value="正常"></c:out>
            	</c:if>
            	<c:if test="${not flag}" >
            	<c:out value="不正常"></c:out>
            	</c:if>
           <%--  ${c.status} --%>
            </td>
            <td >${c.role_list}</td>
            <td >${c.timeString}</td>
            <td >
<div class="btn btn-warning btn-sm" style="margin-right:3px" data-toggle="modal" data-target="#myEditModal" onclick="editAdminhh('${c.id}','${c.username}','${c.password}','${c.status}','${c.role_list}')">
<i class="fa fa-edit"></i>修改
</div>
<a href="deleteAdmin?id=${c.id }">
<div class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i>删除
</div>
</a>
</td>
        </tr>
    </c:forEach>
   </tbody>
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
	<form class="form-inline" id="editForm" method="post" accept-charset="UTF-8" action="editAdmin" onsubmit="return edit_checkForm()">
			<div class="modal-body">		

	<input class="form-control" type="hidden" name="editAdminID" id="editAdminID">
   管理员账号：<input class="form-control" type="text" name="editAdminUsername" id="editAdminUsername" onblur="edit_checkAdminCode()">  
      <br>
   管理员密码：<input class="form-control" type="text" name="editAdminPassword" id="editAdminPassword">   
   <br>
   <!-- 管理员状态：<input class="form-control" type="text" name="editAdminStatus" id="editAdminStatus">   <br> -->
    管理员状态：<select class="form-control" type="text" name="editAdminStatus" id="editAdminStatus"> 
   			<option value="0" >正常</option>    
       		<option value="1" >不正常</option>
   </select>
   <br>
    管理员权限：<!-- <input class="form-control" type="text" name="editAdminRole_list" id="editAdminRole_list">    -->
   <br>
    <c:forEach items="${allMenuList}" var="c" varStatus="st">
           		 <li>
                        ${c.level_one}
                        <ul >
                        	<c:forEach items="${c.level_twoInOneList}" var="cc" varStatus="status">
                            		<input type="checkbox" name="editMenuLevelTwo" value="${cc.role_name}">${cc.level_two}  
                        	 </c:forEach>
                        </ul>
                  </li>
            </c:forEach> 
   
   
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭
				</button>
				<button type="submit" class="btn btn-primary">
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
			
			<form class="form-inline" id="addForm" method="post" accept-charset="UTF-8" action="addAdmin" onsubmit="return checkForm()">
			<div class="modal-body">
				
  <!--    用户id：<input class="form-control" type="search" placeholder="用户id" name="addUserId"> -->
     管理员账号：<input class="form-control" type="search" placeholder="管理员账号" name="addAdminUsername" id="addAdminUsername" onblur="checkAdminCode()">
       <br>
     管理员密码：<input class="form-control" type="search" placeholder="管理员密码 "name="addAdminPassword"><br>
    <!--  管理员状态：<input class="form-control" type="search" placeholder="管理员状态 "name="addAdminStatus">  <br> -->
     管理员状态：<select class="form-control" type="text" name="addAdminStatus" id="addAdminStatus"> 
   			<option value="0" >正常</option>    
       		<option value="1" >不正常</option>
   </select>
   <br>
  <!--    管理员权限：<input class="form-control" type="search" placeholder="管理员权限 "name="addAdminRole_list"><br> -->
  管理员权限：<br>
   <c:forEach items="${allMenuList}" var="c" varStatus="st">
           		 <li>
                        ${c.level_one}
                        <ul >
                        	<c:forEach items="${c.level_twoInOneList}" var="cc" varStatus="status">
                            		<input type="checkbox" name="addMenuLevelTwo" value="${cc.role_name}" checked>${cc.level_two}  
                        	 </c:forEach>
                        </ul>
                  </li>
            </c:forEach> 
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
            	var adminname=encodeURI(encodeURI(searchName));
            	var formSearch=document.getElementById("formSearch");
            	formSearch.action="adminSearchByName?adminname="+adminname;
            	formSearch.submit();	
            }
            function editAdminhh(id,username,password,status,role_list){
            	
            	$("#editAdminID").val(id);
            	$("#editAdminUsername").val(username);
            	$("#editAdminPassword").val(password);
            	$("#editAdminStatus").val(status);
            	$("#editAdminRole_list").val(role_list);
            	
            	var boxes = document.getElementsByName("editMenuLevelTwo");
        	   	for(i=0;i<boxes.length;i++){  	           
        	                boxes[i].checked = false;
        	    }
            	
            	 var val = role_list.split(",");
            	 //var boxes = document.getElementsByName("editMenuLevelTwo");
            	   	for(i=0;i<boxes.length;i++){
            	        for(j=0;j<val.length;j++){
            	            if(boxes[i].value == val[j]){
            	                boxes[i].checked = true;
            	                break
            	            }
            	        }
            	    }
            	
            	
            	
            	
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
