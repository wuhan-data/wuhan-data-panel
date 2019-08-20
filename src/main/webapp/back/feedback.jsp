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
        <div id="page-wrapper" >
            <div id="page-inner">
			 <div class="row">
                    <div class="col-md-12">
                        <h1 class="page-header">
                            系统管理 <small>用户反馈管理</small>
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

     <form class="form-inline" style="float:right" id="formSearch" method="post" accept-charset="UTF-8">
      <input class="form-control" type="search" placeholder="按标题搜索" aria-label="Search" id="searchtname" value="">
      <button class="btn btn-success" onclick="search()">搜索</button>
    </form>
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                        <tr>
                                            <th>反馈id</th>
                                            <th>反馈用户id</th>
                                            <th>标题</th>
                                            <th>联系方式</th>
                                            <th>状态</th>
                                            <th>创建时间</th>
                                            <th>操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
     <c:forEach items="${feedbackListByPage}" var="c" varStatus="st">
        <tr>
            <td >${c.id}</td>
            <td >${c.uid}</td>
            <td >${c.title}</td>
            <td >${c.contact}</td>
            <td >${c.state}</td>
            <td >${c.create_time}</td>
            <td >
<%-- <div class="btn btn-warning btn-sm" style="margin-right:3px" data-toggle="modal" data-target="#myEditModal" onclick="edit(${c.theme_name})">
<i class="fa fa-edit"></i>修改
</div>
 --%>
 
 <div class="btn btn-danger btn-sm" style="margin-right:3px" data-toggle="modal" data-target="#myLookModal" onclick="look('${c.id}','${c.uid}','${c.title}','${c.text}','${c.img}','${c.contact}','${c.state}','${c.create_time}')">
<i class="fa fa-edit"></i>查看
</div>
 
<div class="btn btn-warning btn-sm" style="margin-right:3px" data-toggle="modal" data-target="#myEditModal" onclick="edit('${c.id}','${c.uid}','${c.title}','${c.text}','${c.img}','${c.contact}','${c.state}','${c.create_time}')">
<i class="fa fa-edit"></i>修改
</div>
<a href="deleteFeedback?id=${c.id}">
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
			<h4 class="modal-title" id="myModalLabel">修改</h4>
			</div>
	<form class="form-inline" id="editForm" method="post" accept-charset="UTF-8" action="editFeedback" onsubmit="return edit_checkForm()">
			<div class="modal-body">		

	<input class="form-control" type="hidden" name="editFeedbackID" id="editFeedbackID">
   反馈用户id：<input class="form-control" type="text" name="editFeedbackUid" id="editFeedbackUid"readonly>  <br>
   反馈标题：<input class="form-control" type="text" name="editFeedbackTitle" id="editFeedbackTitle"readonly>  <br>
   正文：<input class="form-control" type="text" name="editFeedbackText" id="editFeedbackText"readonly>  <br>
   图片：<input class="form-control" type="text" name="editFeedbackImg" id="editFeedbackImg"readonly>  <br>
   联系方式：<input class="form-control" type="text" name="editFeedbackContact" id="editFeedbackContact"readonly>  <br>
   反馈时间：<input class="form-control" type="text" name="editFeedbackCreate_time" id="editFeedbackCreate_time"readonly>  <br>
   反馈状态：<select class="form-control" id="editFeedbackState" name="editFeedbackState" >	
       		<option value="未解决">未解决</option>    
       		<option value="已解决">已解决</option> 
  	</select><br> 
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



         
                                <!--查看 模态框（Modal） -->
<div class="modal fade" id="myLookModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
			<h4 class="modal-title" id="myModalLabel">修改</h4>
			</div>
	<form class="form-inline" id="lookForm" method="post" accept-charset="UTF-8" action="lookFeedback" onsubmit="return look_checkForm()">
			<div class="modal-body" id="chakan">		

	<input class="form-control" type="hidden" name="lookFeedbackID" id="lookFeedbackID">
   反馈用户id：<input class="form-control" type="text" name="lookFeedbackUid" id="lookFeedbackUid" readonly>  <br>
   反馈标题：<input class="form-control" type="text" name="lookFeedbackTitle" id="lookFeedbackTitle" readonly>  <br>
   正文：<input class="form-control" type="text" name="lookFeedbackText" id="lookFeedbackText" readonly>  <br>
   图片：<input class="form-control" type="text" name="lookFeedbackImg" id="lookFeedbackImg" readonly>  <br>
   联系方式：<input class="form-control" type="text" name="lookFeedbackContact" id="lookFeedbackContact" readonly>  <br>
   反馈时间：<input class="form-control" type="text" name="lookFeedbackCreate_time" id="lookFeedbackCreate_time" readonly>  <br>
   反馈状态：<input class="form-control" type="text" name="lookFeedbackState" id="lookFeedbackState"readonly>   
   <div id="lookImgs"></div>
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
            	var role_name=encodeURI(encodeURI(searchName));
            	var formSearch=document.getElementById("formSearch");
            	formSearch.action="feedbackSearchByUid?title="+role_name;
            	formSearch.submit();
            	
            }
            function edit(id,uid,title,text,img,contact,state,create_time){
            	$("#editFeedbackID").val(id);
            	$("#editFeedbackUid").val(uid);
            	$("#editFeedbackTitle").val(title);
            	$("#editFeedbackText").val(text);
            	$("#editFeedbackImg").val(img);
            	$("#editFeedbackContact").val(contact);
            	$("#editFeedbackState").val(state);
            	$("#editFeedbackCreate_time").val(create_time);
            }
            function look(id,uid,title,text,img,contact,state,create_time){
            	$("#lookFeedbackID").val(id);
            	$("#lookFeedbackUid").val(uid);
            	$("#lookFeedbackTitle").val(title);
            	$("#lookFeedbackText").val(text);
            	$("#lookFeedbackImg").val(img);
            	var vote=document.getElementById("chakan");
       			var button=document.getElementById("lookFeedbackState");
            	var array=img.split(";");
            	var imgsUrl="";
            	for(var i=0;i<array.length;i++)
            	{	
            		var img="\"<img src=\""+array[i]+"\" height=\"200\" width=\"200\">\"";
            		imgsUrl=imgsUrl+img;
            	}
            	document.getElementById("lookImgs").innerHTML=imgsUrl;
            	$("#lookFeedbackContact").val(contact);
            	$("#lookFeedbackState").val(state);
            	$("#lookFeedbackCreate_time").val(create_time);
            }
            function del(aid){
            	alert("sss")
            	/* var aid=document.getElementById("aid").value; */
            	alert(aid);
            	var id=encodeURI(encodeURI(aid));
          	    window.location.href="http://localhost:8089/wuhan_data1/delCol?id="+id;          	
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