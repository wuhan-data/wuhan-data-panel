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
                            系统管理 <small>版本管理</small>
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
   <div class="btns col-md-4">
      <div class="btn btn-info" data-toggle="modal" data-target="#myAddModal" onclick="add()"><i class="fa fa-plus"></i>添加</div>
    </div>  
     <form class="form-inline" style="float:right" id="formSearch" method="post" accept-charset="UTF-8">
      <input class="form-control" type="search" placeholder="按版本号搜索" aria-label="Search" id="searchtname" value="">
      <button class="btn btn-success" onclick="search()">搜索</button>
    </form>
    </div>
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                        <tr>
                                            <th >id</th>
                                            <th >应用标识</th>
                                            <th >操作系统</th>
                                            <th >版本号</th>
                                            <th >发布时间</th>
                                            <th >操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
     <c:forEach items="${versionListByPage}" var="c" varStatus="st">
        <tr>
            <td >${c.id}</td>
            <td >${c.appid}</td>
            <td >${c.platform}</td>
            <td >${c.version}</td>
            <td>${c.timeString}</td>
            <td >
<%-- <div class="btn btn-warning btn-sm" style="margin-right:3px" data-toggle="modal" data-target="#myEditModal" onclick="edit(${c.theme_name})">
<i class="fa fa-edit"></i>修改
</div>
 --%>
 <div class="btn btn-success btn-sm" style="margin-right:3px" data-toggle="modal" data-target="#myLookModal" onclick="look(${c.id},'${c.appid}','${c.platform}','${c.version}','${c.text}','${c.url}')">
<i class="fa fa-edit"></i>查看
</div>
<%-- <div class="btn btn-warning btn-sm" style="margin-right:3px" data-toggle="modal" data-target="#myEditModal" onclick="edit(${c.id},'${c.appid}','${c.platform}','${c.version}','${c.text}','${c.url}')">
<i class="fa fa-edit"></i>修改
</div> --%>
<a href="#" onclick="delClick('${c.id }','deleteVersion')">
<div class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i>删除</div>
</a>
</td>
        </tr>
    </c:forEach>
                                    </tbody>
                                    

       <%--   <input type="hidden" value="${tname}" name="ctname"/> --%>
                                </table>
                                
                                  <!--查看 模态框（Modal） -->
<div class="modal fade" id="myLookModal" tabindex="-1" version="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
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
	<form class="form-inline" id="lookForm" method="post" accept-charset="UTF-8" action="#" >
			<div class="modal-body">		

	<input class="form-control" type="hidden" readonly name="lookID" id="lookID" >
   应用标识：<input class="form-control" type="text" readonly name="lookAppid" id="lookAppid" > <br> 
   操作系统：<input class="form-control" type="text" readonly name="lookPlatform" id="lookPlatform"> <br>
   版本号：<input class="form-control" type="text" readonly name="lookVersion" id="lookVersion"> <br>
   更新内容：<br><textarea class="form-control" type="text" readonly  name="lookText" id="lookText" style="width:500px;height:80px;"></textarea> <br>
   url：<input class="form-control" type="text"  readonly name="lookUrl" id="lookUrl"> <br>
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
<div class="modal fade" id="myEditModal" tabindex="-1" version="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
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
	<form class="form-inline" id="editForm" method="post" enctype="multipart/form-data" accept-charset="UTF-8" action="#" >
			<div class="modal-body">		

	<input class="form-control" type="hidden" name="editID" id="editID" >
   应用标识：<input class="form-control" type="text" name="editAppid" id="editAppid" readonly > <br>
 操作系统：<select class="form-control" id="editPlatform" name="editPlatform" >	
       		<option value="ios">ios</option>    
       		<option value="Android">Android</option> 
  	</select><br>
   版本号：<input class="form-control" type="text" name="editVersion" id="editVersion"> <br>
   更新内容：<br><textarea class="form-control" type="text" name="editText" id="editText" style="width:500px;height:80px;"> </textarea><br>
   url：<input class="form-control" type="text" name="editUrl" id="editUrl" readonly> <br>
   重新上传的文件：<input type="file" name="editFile" id="editFile" value="请选择文件" /><br>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭
				</button>
				<button type="submit" class="btn btn-primary" onclick="editClick('editVersion')">
					提交
				</button>
			</div>
			</form>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>



                                <!--添加 模态框（Modal） -->
<div class="modal fade" id="myAddModal" tabindex="-1" version="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
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
			
			<form class="form-inline" id="addForm" method="post" enctype="multipart/form-data" accept-charset="UTF-8" action="#" >
			<div class="modal-body">
				
  <!--    用户id：<input class="form-control" type="search" placeholder="用户id" name="addUserId"> -->
     应用标识：<input class="form-control" type="text" readonly  name="addAppid" id="addAppid" value="__UNI__8BAA13B" > <br> 
   操作系统：<!-- <input class="form-control" type="text" name="addPlatform" id="addPlatform"> <br> -->
        <select class="form-control" id="addPlatform" name="addPlatform" >	
       		<option value="ios">ios</option>    
       		<option value="Android" selected >Android</option> 
  	</select><br>
   版本号：<input class="form-control" type="text" name="addVersion" id="addVersion"> <br>
   更新内容：<br><textarea class="form-control" type="text" name="addText" id="addText" style="width:500px;height:80px;"> </textarea><br>
  <!--  url：<input class="form-control" type="text" name="addUrl" id="addUrl"> <br> -->
   上传的文件：<input type="file" name="addFile" id="addFile" value="请选择最新的安装文件" /><br>
   
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭
				</button>
				<button type="submit" class="btn btn-primary" onclick="addClick('addVersion')">
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
    <script src="<%=path %>/assets/js/jquery.metisMenu.js"></script>
      <!-- Custom Js -->
    <script src="<%=path %>/assets/js/custom-scripts.js"></script>
    
      <!-- Morris Chart Js -->
<!--     <script src="assets/js/morris/raphael-2.1.0.min.js"></script>
    <script src="assets/js/morris/morris.js"></script>  -->
    
    <script src="<%=path %>/assets/js/bootstrap-switch.min.js"></script>
   <script src="<%=path %>/assets/js/dataTables/jquery.dataTables.js"></script>
    <script src="<%=path %>/assets/js/dataTables/dataTables.bootstrap.js"></script>   
    <script src="<%=path %>/assets/js/bootstrap-fileupload.js"></script>
    <script>
            $(document).ready(function () {  
            });
            
            addClick = function(Url) {
          	   $('.modal-backdrop').remove();
          	    $('body').removeClass('modal-open');
               	var data = new FormData(document.getElementById("addForm"));
               	 $.ajax({
                          type: 'POST',
                          url:  Url,
                          dataType: "html",
                     	    data: data,
                     	    async : false,
                      	contentType: false, //不设置内容类型
                     	    processData: false,
                          cache:false,
                          success: function(data){
                    	  alert("添加成功");
                              $('#getNewData').html(data);
                          },
                          error : function(data){
                    	  alert("添加失败");
                          }
                      });    
               };
               editClick = function(Url) {
             	   $('.modal-backdrop').remove();
             	    $('body').removeClass('modal-open');
                  var data = new FormData(document.getElementById("editForm"));                	
                  $.ajax({
                             type: 'POST',
                             url:  Url,
                             dataType: "html",
                        	    data: data,
                        	    async : false,
                         	contentType: false, //不设置内容类型
                        	    processData: false,
                             cache:false,
                             success: function(data){
                        	 alert("编辑成功");
                                 $('#getNewData').html(data);
                             },
                             error : function(data){
                        	  alert("编辑失败");
                             }
                         });    
                  };
                  delClick = function(s_id,Url) {
                      $.ajax({
                                 type: 'GET',
                                 url:  Url+"?id="+s_id,
                                 dataType: "html",
                            	    async : false,
                             	contentType: false, //不设置内容类型
                            	    processData: false,
                                 cache:false,
                                 success: function(data){
                            	 	alert("删除成功");
                                     $('#getNewData').html(data);
                                 },
                                 error : function(data){
                            	 alert("删除失败");
                                 }
                             });    
                      };
                      
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
                                var version_name=encodeURI(encodeURI(searchName));
                              	$.ajax({
                                         type: 'GET',
                                         url:  "versionSearchByName?version_name="+version_name,
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
           
            function edit(id,appid,platform,version,text,url,create_time){
            	$("#editID").val(id);
            	$("#editAppid").val(appid);
            	$("#editPlatform").val(platform);
            	$("#editVersion").val(version);
            	$("#editText").val(text);
            	$("#editUrl").val(url);    	
            }
            function look(id,appid,platform,version,text,url,create_time){
            	$("#lookID").val(id);
            	$("#lookAppid").val(appid);
            	$("#lookPlatform").val(platform);
            	$("#lookVersion").val(version);
            	$("#lookText").val(text);
            	$("#lookUrl").val(url); 
            	var path=url;
            	var fileUrl="<a href=\"#\">没有下载链接</a>";
            	if(path!="" && path!=null)
            	{
            		fileUrl="<a href=\""+path+"\">下载链接</a>";
            	}
            	document.getElementById("lookFileUrl").innerHTML=fileUrl;
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
