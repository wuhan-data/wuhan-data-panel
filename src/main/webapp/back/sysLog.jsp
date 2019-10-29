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
	<!-- Bootstrap Styles-->
    <link href="<%=path %>/assets/css/bootstrap.css" rel="stylesheet" />
     <!-- FontAwesome Styles-->
    <link href="<%=path %>/assets/css/font-awesome.css" rel="stylesheet" />
        <!-- Custom Styles-->
    <link href="<%=path %>/assets/css/custom-styles.css" rel="stylesheet" />
    
     <link href="<%=path %>/assets/css/bootstrap-switch.min.css" rel="stylesheet" />
    
    <%-- <link href="<%=path %>/assets/css/my.css" rel="stylesheet" /> --%>
   
      <link href="<%=path %>/assets/css/bootstrap-fileupload.min.css" rel="stylesheet" />
      
    

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
        <div id="page-wrapper" >
            <div id="page-inner">
			 <div class="row">
                    <div class="col-md-12">
                        <h1 class="page-header">
                            
                            辅助管理 <small>日志管理</small>
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
   <div class="row" style="margin-bottom:7px;margin-right:2px">
   <div class="btns col-md-6">
     
    </div>  
     <form class="form-inline" style="float:right" id="formSearch" method="post" accept-charset="UTF-8">
    <!--   <input class="form-control" type="search" placeholder="按操作者id搜索" aria-label="Search" id="searchtname" value="">
      <button class="btn btn-success" onclick="search()">搜索</button> -->
     <select class="form-control"  name="searchtname" id="searchtname" onchange="search()">
      		<option value="" selected="selected" >全部错误日志 </option> 	
       		<option value="user" <c:if test="${type==1}"><c:out value="selected=\"selected\""></c:out></c:if> > 前端错误日志</option>    
       		<option value="admin" <c:if test="${type==2}"><c:out value="selected=\"selected\""></c:out></c:if> >后台错误日志</option> 
  	</select>
    </form>
    </div> 
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                        <tr>
                                            
                                            <th width="10%">日志id</th>
                                            <th width="10%">身份</th>
                                            <th width="30%">调用的接口</th>
                                            <th width="20%">时间</th>
                                            <th width="10%">操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
     <c:forEach items="${sysLogListByPage}" var="c" varStatus="st">
        <tr>
            <td >${c.id}</td>
            <td >${c.e_type}</td>
            <td >${c.e_interface}</td>
            <td >${c.timeString}</td>
            <td>
             <div class="btn btn-success btn-sm" style="margin-right:3px" data-toggle="modal" data-target="#myLookModal" onclick="lookLog('${c.operate_user_id}','${c.e_type}','${c.e_interface}','${c.e_parameter}','${c.e_msg}','${c.e_error}','${c.timeString}')">
<i class="fa fa-edit"></i>查看
</div>
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
					查看
				</h4>
			</div>
	<form class="form-inline" id="editForm" method="post" accept-charset="UTF-8" >
			<div class="modal-body">		

	
<!--  操作者id：<input class="form-control" type="text" name="lookUserId" id="lookUserId" readonly>   <br> -->
 身份：<input class="form-control" type="text" name="lookE_type" id="lookE_type" readonly>   <br>
 接口：<input class="form-control" type="text" name="lookE_interface" id="lookE_interface" readonly> <br>   
 时间：<input class="form-control" type="text" name="lookCreate_time" id="lookCreate_time" readonly><br>
 参数信息：<br>
 <textarea class="form-control" type="text" name="lookE_parameter" id="lookE_parameter"readonly style="width:500px;height:80px;">  
 </textarea><br>
返回信息：
<br>
<textarea class="form-control" type="text" name="lookE_msg" id="lookE_msg"readonly style="width:500px;height:80px;">  
</textarea><br>
系统错误信息：
<br>
<textarea class="form-control" type="text" name="lookE_error" id="lookE_error"readonly style="width:500px;height:160px;">  
</textarea><br>

			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭
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
			
			<form class="form-inline" id="addForm" method="post" accept-charset="UTF-8" action="">
			<div class="modal-body">
				
  <!--    用户id：<input class="form-control" type="search" placeholder="用户id" name="addUserId"> -->
     部门代码：<input class="form-control" type="search" placeholder="部门代码" name="addDepartmentCode">
     部门名称：<input class="form-control" type="search" placeholder="部门名称" name="addDepartmentName">
     部门描述：<input class="form-control" type="search" placeholder="部门描述" name="addDepartmentDescription">  
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭
				</button>
				<button type="submit" class="btn btn-primary" onclick="">
					提交
				</button>
			</div>
			</form>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>


                                <div class="row">
									<div class='page fix'>
                    <form method="post" action="#" id="pageForm">
                        共 <b>${page.totalNumber}</b> 条
                        <c:if test="${page.currentPage != 1}">

                           <a href="#" class='first' onclick="pageClick('1','${controlURL}')">首页</a>
                           <a href="#" class='pre' onclick="pageClick('${page.currentPage-1}','${controlURL}')">上一页</a>
                        </c:if>
                        当前第<span>${page.currentPage}/${page.totalPage}</span>页
                        <c:if test="${page.currentPage != page.totalPage}">
                            <a href="#" class='next' onclick="pageClick('${page.currentPage+1}','${controlURL}')">下一页</a>
                            <a href="#" class='last' onclick="pageClick('${page.totalPage}','${controlURL}')">末页</a>
                        </c:if>
                        跳至&nbsp;
                        <input id="currentPageText" type='text' value='${page.currentPage}' class='allInput w28' name="currentPage" />&nbsp;页&nbsp;
                        <input type="submit" value="GO" class="btn-primary btn-sm" onclick="pageGoClick('${controlURL}')">
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
            	
            });
            
            pageClick = function(currentPage,Url) {
                $.ajax({
                           type: 'GET',
                           url:  Url+"?currentPage="+currentPage,
                           dataType: "html",
                      	    async : false,
                       	contentType: false, //不设置内容类型
                      	    processData: false,
                           cache:false,
                           success: function(data){
                               $('#getNewData').html(data);
                           },
                           error : function(data){
                           }
                       });    
                };
         
                pageGoClick = function(Url) {
               	var currentPage = document.getElementById("currentPageText").value;
                    $.ajax({
                               type: 'GET',
                               url:  Url+"?currentPage="+currentPage,
                               dataType: "html",
                          	    async : false,
                           	contentType: false, //不设置内容类型
                          	    processData: false,
                               cache:false,
                               success: function(data){
                                   $('#getNewData').html(data);
                               },
                               error : function(data){
                               }
                           });    
                    };
         
                    search= function(){
                    	
                    	var searchName=document.getElementById("searchtname").value;
                    	var  e_type=encodeURI(searchName);
                    	$.ajax({
                               type:'GET',
                               url:  "sysLogSearchByName?e_type="+e_type,
                               dataType: "html",
                          	    async : false,
                           		contentType: false, //不设置内容类型
                          	    processData: false,
                               cache:false,
                               success: function(data){
                                   $('#getNewData').html(data);
                               },
                               error : function(data){
                               }
                           });    
                    };
                    	
            
            
            
            function f1(){
            	var select = document.getElementById("FormControlSelect1");
            	var op = select.value;
            	var form1=document.getElementById("form1");
            	var title=encodeURI(encodeURI(op));
            	form1.action="initAnalysisList?op="+title;
            	form1.submit();
            }
            function edit(){
            	
                	
            }
            function del(aid){
            	alert("sss")
            	/* var aid=document.getElementById("aid").value; */
            	alert(aid);
            	var id=encodeURI(encodeURI(aid));
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
           
           function lookLog(operate_user_id,e_type,e_interface,e_parameter,e_msg,e_error,create_time){
        	 
           	$("#lookUserId").val(operate_user_id);
           	$("#lookE_type").val(e_type);
           	$("#lookE_interface").val(e_interface);
           	$("#lookCreate_time").val(create_time);
           	$("#lookE_parameter").val(e_parameter);
           	$("#lookE_msg").val(e_msg);
           	$("#lookE_error").val(e_error);    	
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
