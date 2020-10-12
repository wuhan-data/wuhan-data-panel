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
    
      <script type="text/javascript">
    function checkRoleCode(){
     	 var rolecode = document.getElementById("addAdminUsername").value;
     	 var reg = /\S/;
         if (!reg.test(rolecode)) {
         	span_rolecode.innerHTML = "不能为空，不能有空格";
             return false;
         }
         else{
        	  //判断name是否存在
			  roleCode=encodeURI(rolecode);
        	  var flag=false;
		    	$.ajax({
		    		url:"nameIsExist",
		    		data:{username:roleCode},
		    		async:false,
		    		success:function(data){
		    			if(data.data=="exist"){
		    				span_rolecode.innerHTML = "name已经存在";
		    				flag=false;
		          		  	return false;	
		    			}
		    			else{
		    				span_rolecode.innerHTML = "格式正确";
		    				flag=true;
		              		return true;
		    			}	
		    		}
		    	}) 
		    	return flag;
     }
    }
       function checkForm(){
       	var roleCode=checkRoleCode();
       	if (roleCode){
       		return true;
       	}
       	else
       		{
       		return false;
       		}
       }

      </script>
    
    
</head>
<body>
    <div id="wrapper">
       
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
<div class="row"  style="margin-bottom:7px;margin-right:2px">
   <div class="btns col-md-4">
      <div class="btn btn-info" data-toggle="modal" data-target="#myAddModal" onclick="add()"><i class="fa fa-plus"></i>添加</div>
      </div>  
     <form class="form-inline" style="float:right" id="formSearch" method="post" accept-charset="UTF-8">
      <input class="form-control" type="search" placeholder="按名称搜索" aria-label="Search" id="searchtname" value="">
      <button class="btn btn-success" onclick="search()">搜索</button>
    </form>
 </div>                           <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                        <tr>
                                            <th >id</th>
                                            <th >管理员名称</th>
                                            <th >管理员密码</th>
                                            <th >状态</th>
                                           
                                            <th >创建时间</th>
                                            <th >操作</th>
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
           
            <td >${c.timeString}</td>
            <td >
<div class="btn btn-warning btn-sm" style="margin-right:3px" data-toggle="modal" data-target="#myEditModal" onclick="editAdminhh('${c.id}','${c.username}','${c.password}','${c.status}','${c.role_list}')">
<i class="fa fa-edit"></i>修改
</div>
<a href="#" onclick="delClick('${c.id }','deleteAdmin')">
<div class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i>删除</div>
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
	<form class="form-inline" id="editForm" method="post" accept-charset="UTF-8" action="#" onsubmit="return edit_checkForm()">
			<div class="modal-body">		

	<input class="form-control" type="hidden" name="editAdminID" id="editAdminID">
   管理员账号：<input class="form-control" type="text" name="editAdminUsername" id="editAdminUsername" readonly onblur="edit_checkAdminCode()">  
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
				<button type="submit" class="btn btn-primary" onclick="editClick('editAdmin')">
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
			
			<form class="form-inline" id="addForm" method="post" accept-charset="UTF-8" action="#" onsubmit="return checkForm()">
			<div class="modal-body">
				
  <!--    用户id：<input class="form-control" type="search" placeholder="用户id" name="addUserId"> -->
     管理员账号：<input class="form-control" type="search" placeholder="管理员账号" name="addAdminUsername" id="addAdminUsername" onblur="checkRoleCode()">
     <span id="span_rolecode">填输入用户名</span>  <br>
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
				<button type="submit" class="btn btn-primary" onclick="addClick('addAdmin')">
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
          	    if(checkForm()){
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
               }
          	    else
          	    	{alert("请输入正确格式的值")}
            }
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
                        	 alert("修改成功");
                                 $('#getNewData').html(data);
                             },
                             error : function(data){
                        	 alert("修改失败");
                             }
                         });    
                  };
                  delClick = function(s_id,Url) {
                      var c = confirm("是否删除？");
                      if (!c) return;
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
                              	var adminname=encodeURI(encodeURI(searchName));   
                              	$.ajax({
                                         type: 'GET',
                                         url:  "adminSearchByName?adminname="+adminname,
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
            	                break;
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
