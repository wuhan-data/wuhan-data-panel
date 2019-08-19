<%@page import="com.wuhan_data.pojo.Admin"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Admin ad=(Admin)session.getAttribute("user");
    String username=ad.getUsername();
    int adminId=ad.getId();
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
        
        <div id="page-wrapper" >
            <div id="page-inner">
			 <div class="row">
                    <div class="col-md-12">
                        <h1 class="page-header">
                            辅助管理<small>消息管理</small>
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
                     
  <div class="btns col-md-2">
      <div class="btn btn-info" data-toggle="modal" data-target="#myAddModalByRole" onclick="add()"><i class="fa fa-plus"></i>批量添加</div>
      <!-- <button class="btn btn-primary" onclick="showSort()"><i class="fa fa-cog"></i>设置</button> -->
    </div> 
   <div class="btns col-md-4">
      <div class="btn btn-info" data-toggle="modal" data-target="#myAddModal" onclick="add()"><i class="fa fa-plus"></i>添加</div>
      <!-- <button class="btn btn-primary" onclick="showSort()"><i class="fa fa-cog"></i>设置</button> -->
    </div>  
     <form class="form-inline" style="float:right" id="formSearch" method="post" accept-charset="UTF-8">
      <input class="form-control" type="search" placeholder="按照标题搜索" aria-label="Search" id="searchtname" value="">
      <button class="btn btn-success" onclick="search()">搜索</button>
    </form>
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                        <tr>
                                            <th>通知id</th>
                                            <th>发送人id</th>
                                            <th>接收人id</th>
                                            <th>标题</th>
                                            <th>消息类型</th>
                                            <th>内容类型</th>
                                            <th>创建时间</th>
                                            <th>操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
     <c:forEach items="${messagesListByPage}" var="c" varStatus="st">
        <tr>
            <td >${c.id}</td>
            <td >${c.sender_id}</td>
            <td >${c.receiver_id}</td>
            <td >${c.title}</td>
            <td >${c.label}</td>
            <td >${c.type}</td>
            <td >${c.timeString}</td>
            <td >
<%-- <div class="btn btn-warning btn-sm" style="margin-right:3px" data-toggle="modal" data-target="#myEditModal" onclick="edit(${c.theme_name})">
<i class="fa fa-edit"></i>修改
</div>
 --%>
 <div class="btn btn-warning btn-sm" style="margin-right:3px" data-toggle="modal" data-target="#myLookModal" onclick="lookrole('${c.id}','${c.sender_id}','${c.receiver_id}','${c.title}','${c.label}','${c.content}','${c.m_text}','${c.type}','${c.path}')">
<i class="fa fa-edit"></i>查看
</div>
<div class="btn btn-warning btn-sm" style="margin-right:3px" data-toggle="modal" data-target="#myEditModal" onclick="editrole('${c.id}','${c.sender_id}','${c.receiver_id}','${c.title}','${c.label}','${c.content}','${c.m_text}','${c.type}','${c.path}')">
<i class="fa fa-edit"></i>修改
</div>
<a href="deleteMessage?id=${c.id}">
<div class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i>删除
</div>
</a>
</td>
        </tr>
    </c:forEach>
                                    </tbody>
                                    

       <%--   <input type="hidden" value="${tname}" name="ctname"/> --%>
                                </table>
                                
                                <!--查看 模态框（Modal） -->
<div class="modal fade" id="myLookModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
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
	<form class="form-inline" id="lookForm" method="post" accept-charset="UTF-8" >
			<div class="modal-body">		

		<input class="form-control" type="hidden" name="lookMessageID" id="lookMessageID">
 发送人id：<input class="form-control" type="text" name="lookSender_id" id="lookSender_id"  readonly>  <br> 
 接收人id：<input class="form-control" type="text" name="lookReceiver_id" id="lookReceiver_id"readonly> <br>
 标题：<input class="form-control" type="text" name="lookTitle" id="lookTitle"readonly> <br>
 消息类型：
 <select class="form-control" id="lookLabel" name="lookLabel" readonly>	
       		<option value="系统消息" >系统消息</option>    
       		<option value="核心推送">核心推送</option> 
  	</select><br>
 内容缩略：<input class="form-control" type="text" name="lookContent" id="lookContent"readonly> <br>
 正文：<input class="form-control" type="text" name="lookM_text" id="lookM_text"readonly>  <br>
 内容类型：
 	 <select class="form-control" id="lookType" name="lookType"readonly >	
       		<option value="message">message</option>    
       		<option value="pdf">pdf</option> 
       		<option value="excel">excel</option> 
  	</select><br>
 路径：<a><input class="form-control" type="text" name="lookPath" id="lookPath"> <br></a>
 	<div id=lookFileUrl></div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭
				</button>
			</div>
			</form>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>
                                
                                
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
	<form class="form-inline" id="editForm" method="post" accept-charset="UTF-8" action="editMessage">
			<div class="modal-body">		

		<input class="form-control" type="hidden" name="editMessageID" id="editMessageID">
 发送人id：<input class="form-control" type="text" name="editSender_id" id="editSender_id" >  <br> 
 接收人id：<input class="form-control" type="text" name="editReceiver_id" id="editReceiver_id"> <br>
 标题：<input class="form-control" type="text" name="editTitle" id="editTitle"> <br>
 消息类型：
 <select class="form-control" id="editLabel" name="editLabel" >	
       		<option value="系统消息" >系统消息</option>    
       		<option value="核心推送">核心推送</option> 
  	</select><br>
 内容缩略：<input class="form-control" type="text" name="editContent" id="editContent"> <br>
 正文：<input class="form-control" type="text" name="editM_text" id="editM_text">  <br>
 内容类型：
 	 <select class="form-control" id="editType" name="editType" >	
       		<option value="message">message</option>    
       		<option value="pdf">pdf</option> 
       		<option value="excel">excel</option> 
  	</select><br>
 路径：<input class="form-control" type="text" name="editPath" id="editPath"> <br>
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
<div class="modal fade" id="myAddModal" tabindex="-1" role="dialog"  aria-labelledby="myModalLabel" aria-hidden="true">
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
			
			<form class="form-inline" id="addForm" method="post"  enctype="multipart/form-data" accept-charset="UTF-8" action="addMessage">
			<div class="modal-body">
				
 		
 发送人id：<input class="form-control" type="text" name="addSender_id" id="addSender_id" readonly value=<%out.print(adminId);%> >  <br> 
 接收人id：<input class="form-control" type="text" name="addReceiver_id" id="addReceiver_id"> <br>
 标题：<input class="form-control" type="text" name="addTitle" id="addTitle"> <br>
 消息类型：
  <select class="form-control" id="addLabel" name="addLabel" >	
       		<option value="系统消息" selected>系统消息</option>    
       		<option value="核心推送">核心推送</option> 
  	</select><br>
 内容缩略：<input class="form-control" type="text" name="addContent" id="addContent"> <br>
 正文：<input class="form-control" type="text" name="addM_text" id="addM_text">  <br>
 内容类型：
 <select class="form-control" id="addType" name="addType" >	
       		<option value="message" selected>message</option>    
       		<option value="pdf">pdf</option> 
       		<option value="excel">excel</option> 
  	</select><br>
 路径：<input class="form-control" type="text" name="addPath" id="addPath"> <br>
  上传的文件：<input type="file" name="messageAddFile" id="messageAddFile" value="请选择文件" /><br>
    
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
 											<!--批量添加模态框（Modal） -->
<div class="modal fade" id="myAddModalByRole" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					批量添加
				</h4>
			</div>
			
			<form class="form-inline" id="addForm" method="post"  enctype="multipart/form-data" accept-charset="UTF-8" action="addMessageByRole">
			<div class="modal-body">
				
  <!--    用户id：<input class="form-control" type="search" placeholder="用户id" name="addUserId"> -->
  
     接收人角色名字：<!-- <input class="form-control" type="search" placeholder="接收人角色名字" name="addByRoleid"><br> -->
     
  <select class="form-control" id="addByRoleReceiver_id" name="addByRoleReceiver_id" onchange="">
  	<c:forEach items="${roleList}" var="c" varStatus="st">
        <option value="${c.role_name}" >${c.role_name}</option>    
	</c:forEach>
  </select> <br>
    发送人名字：<input class="form-control" type="search" placeholder="发送人名字" name="addByRoleByRolesender_name" readonly value=<%out.print(username); %> ><br>
 发送人id：<input class="form-control" type="text" name="addByRoleSender_id" id="addByRoleSender_id" readonly value=<%out.print(adminId);%>>  <br> 
<!--  接收人id：<input class="form-control" type="text" name="addByRoleReceiver_id" id="addByRoleReceiver_id"> --> <br>
 标题：<input class="form-control" type="text" name="addByRoleTitle" id="addByRoleTitle"> <br>
 消息类型：
  <select class="form-control" id="addByRoleLabel" name="addByRoleLabel" >	
       		<option value="系统消息" selected>系统消息</option>    
       		<option value="核心推送">核心推送</option> 
  	</select><br>
 内容缩略：<input class="form-control" type="text" name="addByRoleContent" id="addByRoleContent"> <br>
 正文：<input class="form-control" type="text" name="addByRoleM_text" id="addByRoleM_text">  <br>
 内容类型：
 <select class="form-control" id="addByRoleType" name="addByRoleType" >	
       		<option value="message" selected>message</option>    
       		<option value="pdf">pdf</option> 
       		<option value="excel">excel</option> 
  	</select><br>
 路径：<input class="form-control" type="text" name="addByRolePath" id="addByRolePath"> <br>
  上传的文件：<input type="file" name="messageAddByRoleFile" id="messageAddByRoleFile" value="请选择文件" /><br>
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
            	var title=encodeURI(encodeURI(searchName));
            	var formSearch=document.getElementById("formSearch");
            	formSearch.action="messageSearchByTitle?title="+title;
            	formSearch.submit();
            	
            }
            function editrole(id,sender_id,receiver_id,title,label,content,m_text,type,path){
            	$("#editMessageID").val(id);
            	$("#editSender_id").val(sender_id);
            	$("#editReceiver_id").val(receiver_id);
            	$("#editTitle").val(title);
            	$("#editLabel").val(label);
            	$("#editContent").val(content);
            	$("#editM_text").val(m_text);
            	$("#editType").val(type);
            	$("#editPath").val(path);
                	
            }
            function lookrole(id,sender_id,receiver_id,title,label,content,m_text,type,path){
            	$("#lookMessageID").val(id);
            	$("#lookSender_id").val(sender_id);
            	$("#lookReceiver_id").val(receiver_id);
            	$("#lookTitle").val(title);
            	$("#lookLabel").val(label);
            	$("#lookContent").val(content);
            	$("#lookM_text").val(m_text);
            	$("#lookType").val(type);
            	$("#lookPath").val(path);
            	var fileUrl="<a href=\"#\">没有下载链接</a>";
            	if(path!="" && path!=null)
            	{
            		fileUrl="<a href=\""+path+"\">下载链接</a>";
            	}
            	document.getElementById("lookFileUrl").innerHTML=fileUrl;	
            	//$("#lookPath").href(path)
                	
            }
            
            $("#selectName").click((function){
            	var realName=$("#realName").val();
            	$.ajax({
            		url:"",
            		data:{realName:realName},
            		type:"POST",
            		dataType:"TEXT",
            		success:function(data){
            			if(data.trim()="OK")
            				{
            				alert("OK");
            				}
            			else
            				{
            				alert("sorry")
            				}
            		}
            	})
            })
            
            
            
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
