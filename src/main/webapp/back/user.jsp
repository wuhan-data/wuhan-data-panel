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
     <script type="text/javascript">
    function checkUserTel(){
     	 var rolecode = document.getElementById("addUserTel").value;
     	 var flag=false;
     	 var reg = /^(13[0-9]|15[012356789]|17[0135678]|18[0-9]|14[579])[0-9]{8}$/;
          if (!reg.test(rolecode)) {
        	  span_userTel.innerHTML = "请填写正确的手机号码";
              return false;
          }
          else {
        	//判断tel是否存在
				roleCode=encodeURI(rolecode);
		    	$.ajax({
		    		url:"telIsExist",
		    		data:{tel:roleCode},
		    		async:false,
		    		success:function(data){
		    			if(data.data=="exist"){
		    				 span_userTel.innerHTML = "tel已经存在";
		    				 flag=false;
		          		  	return false;	
		    			}
		    			else{
		    				 span_userTel.innerHTML = "格式正确";
		    				 flag=true;
		              		return true;
		    			}	
		    		}
		    	}) 
		    	return flag;
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
        	   edit_span_userTel.innerHTML = "请填写正确的手机号码";
               return false;
           }
           else {
        	/*  //判断tel是否存在
				roleCode=encodeURI(rolecode);
		    	$.ajax({
		    		url:"telIsExist",
		    		data:{tel:roleCode},
		    		success:function(data){
		    			if(data.data=="exist"){
		    				edit_span_userTel.innerHTML = "tel已经存在";
		          		  	return false;	
		    			}
		    			else{
		    				edit_span_userTel.innerHTML = "格式正确";
		              		return true;
		    			}	
		    		}
		    	})  */
        	   edit_span_userTel.innerHTML = "格式正确";
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
<div  class="row" style="margin-bottom:7px;margin-right:2px">
   <div class="btns col-md-6">
      <div class="btn btn-info" data-toggle="modal" data-target="#myAddModal" onclick="add()"><i class="fa fa-plus"></i>添加</div>
    </div>  
     <form class="form-inline" style="float:right" id="formSearch" method="post" accept-charset="UTF-8">
      <input class="form-control mr-sm-2" type="search" placeholder="按tel搜索" aria-label="Search" id="searchtname" value="">
      <button class="btn btn-success my-2 my-sm-0" onclick="search()">搜索</button>
    </form>
    </div>
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                        <tr>
                                            <th width="5%">用户id</th>
                                            <th width="10%">联系方式</th>
                                            <th width="5%">用户状态</th>
                                            <th width="5%">用户性别</th>
                                            <th width="10%">用户权限</th>
                                            <th width="5%">角色</th>
                                            <th width="5%">部门</th>
                                            <th width="10%">真实姓名</th>
                                            <th width="10%">出生日期</th>
                                            <th width="10%">地区</th>
                                            <th width="20%">操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
     <c:forEach items="${userListByPage}" var="c" varStatus="st">
        <tr>
            <td>${c.id}</td>
            <td>${c.tel}</td>
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
            <td>${c.birthString}</td>
            <td>${c.city}
            <td >
<%-- <div class="btn btn-warning btn-sm" style="margin-right:3px" data-toggle="modal" data-target="#myEditModal" onclick="edit(${c.theme_name})">
<i class="fa fa-edit"></i>修改</div>--%>
 <div class="btn btn-warning btn-sm" style="margin-right:3px" data-toggle="modal" data-target="#myEditModal" onclick="edit('${c.username}','${c.id}','${c.password}','${c.status}','${c.gender}','${c.tel}','${c.real_name}','${c.role_list}','${c.role_id}','${c.department_id}','${c.birthday}','${c.city}')">
<i class="fa fa-edit"></i>修改
</div>
<a href="#" onclick="delClick('${c.id }','deleteUser')">
<div class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i>删除</div>
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
	<form class="form-inline" id="editForm" method="post" accept-charset="UTF-8" action="#" onsubmit="return edit_checkForm()">
			<div class="modal-body">		

	<input class="form-control" type="hidden" name="editUserID" id="editUserID"><br>
<!--    用户名称：<input class="form-control" type="text" name="editUserName" id="editUserName">  
   <br> 
   用户密码：<input class="form-control" type="text" name="editUserPassword" id="editUserPassword"> 
   <br> -->
      手机:<input class="form-control"  name="editUserTel" id="editUserTel" readonly onblur="edit_checkUserTel()">
    <!--  <span id="edit_span_userTel">填11位数字</span> --><br> 
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
 
      出生日期：<input class="form-control" type="date" value=""name="editBirthday" id="editBirthday"/><br>
     地区:<input class="form-control" type="search" placeholder="地区" name="editCity" id="editCity">
     <br>
</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭
				</button>
				<button type="submit" class="btn btn-primary" onclick="editClick('editUser')">
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
 <!--     用户名称：<input class="form-control" type="search" placeholder="用户名称" name="addUserName" id="addUserName">
     <br>
     用户密码：<input class="form-control" type="search" placeholder="用户密码" name="addUserPassword" id="addUserPassword">
     <br> -->
     手机:<input class="form-control" type="search" placeholder="联系方式" name="addUserTel" id="addUserTel" onblur="checkUserTel()">
     <span id="span_userTel">填11位数字</span><br>
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
   
     出生日期：<input class="form-control" type="date" value="2019-01-01"name="addBirthday" id="addBirthday"/><br>
     地区:<input class="form-control" type="search" placeholder="地区" name="addCity" id="addCity">
     <br>
  
</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭
				</button>
				<button type="submit" class="btn btn-primary" onclick="addClick('addUser')">
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
       
     <!-- /. WRAPPER  -->
    <!-- JS Scripts-->
    <!-- jQuery Js -->
<!--     <script src="assets/js/jquery-1.10.2.js"></script>
      Bootstrap Js
    <script src="assets/js/bootstrap.min.js"></script> -->
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
                              $('#getNewData').html(data);
                          },
                          error : function(data){
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
                                $('#getNewData').html(data);
                            },
                            error : function(data){
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
                           	 	alert(data);
                                    $('#getNewData').html(data);
                                },
                                error : function(data){
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
                             }; pageClick = function(currentPage,Url) {
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
            
                                  function search(){
                                  	var searchName=document.getElementById("searchtname").value;
                                  	var tel=encodeURI(encodeURI(searchName));
                                  	$.ajax({
                                            type: 'GET',
                                            url:  "userSearchByName?tel="+tel,
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
                                  }
            
            
            
            
            
            
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
