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
    <link href="<%=path %>/assets/css/bootstrap.css" rel="stylesheet" />
     <!-- FontAwesome Styles-->
    <link href="<%=path %>/assets/css/font-awesome.css" rel="stylesheet" />
        <!-- Custom Styles-->
    <link href="<%=path %>/assets/css/custom-styles.css" rel="stylesheet" />
    
     <link href="<%=path %>/assets/css/bootstrap-switch.min.css" rel="stylesheet" />
    
    <link href="<%=path %>/assets/css/my.css" rel="stylesheet" />
    
    <link href="<%=path %>/assets/css/bootstrap-order.min.css" rel="stylesheet" />
      <link href="<%=path %>/assets/css/bootstrap-fileupload.min.css" rel="stylesheet" />
    
    
   


    <style type="text/css" rel="stylesheet">

		a{
		text-decoration:none;
		}
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
        
        #dataTables-example{
        margin-top:10px;
        }
       

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
                        <li class="divider"></li>
                        <li><a href="#"><i class="fa fa-sign-out fa-fw"></i> Logout</a>
                        </li>
                    </ul>
<!--                     /.dropdown-user -->
                </li>
                <!-- /.dropdown -->
            </ul>
        </nav>
        <!--/. NAV TOP  -->
        <nav class="navbar-default navbar-side" role="navigation">
        
         <div class="sidebar-collapse">
                <ul class="nav" id="main-menu">

                    <!-- <li>
                        <a class="active-menu" href="index.html"><i class="fa fa-dashboard"></i> 首页</a>
                    </li> -->
                    <li>
                        <a href="toIndex"><i class="fa fa-dashboard"></i>首页</a>
                    </li>
                    <li>
                        <a href="toMetaDataManage"><i class="fa fa-list-alt"></i>元数据管理</a>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-bar-chart-o"></i>数据管理<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a href="listIndexManage">指标数据维护</a>
                            </li>

                        </ul>
                    </li>
                    <li>
                         <a href="init"><i class="fa fa-quote-left"></i>栏目管理</a>

                    </li>
                    <li>
                        <a href="#" onclick="menuClick('specialInit')"><i class="fa fa-quote-left"></i>专题管理</a>

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
<%--            
<jsp:include page="navbar.jsp" flush="true" /> --%>

        </nav>
        <!-- /. NAV SIDE  -->
        <div id="page-wrapper" >
            <div id="page-inner">
			 <div class="row">
                    <div class="col-md-12">
                        <h1 class="page-header">
                            专题管理 <small>专题维护</small>
                        </h1>
                    </div>
                </div> 
                 <!-- /. ROW  -->                
                                
            <div class="row">
                <div class="col-md-12">
                    <!-- Advanced Tables -->
                    <div class="panel panel-default">
                        <div class="panel-heading">
                             专题
                        </div>
                        <div class="panel-body">

  <div class="row">
     <div class="btns col-md-6">
      <div class="btn btn-info" data-toggle="modal" data-target="#myAddModal" onclick="add()"><i class="fa fa-plus"></i>添加</div>
     <%--  <button class="btn btn-primary" id="reset" onclick='showS(${json})'><i class="fa fa-cog"></i>排序</button> --%>
    </div>  
 
    <!--  <form class="form-inline col-md-5" style="float:right" id="formSearch" method="post" accept-charset="UTF-8">
      <input class="form-control" type="search" placeholder="PMI指数(全国)" aria-label="Search" id="searchtname" value="">
      <button class="btn btn-success" onclick="search()">搜索</button>
    </form>
   -->
  </div>

<div id="getNewData" width="100%" height="100%">
</div>

                            
                                                            
                                  <!-- 查看大图 模态框（Modal） -->
<div class="modal fade" id="myImageModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">

	<div class="modal-dialog">
		<div class="modal-content">
	<form class="form-inline" id="editForm" method="post" accept-charset="UTF-8" action="#">
			<div class="modal-body">		
<img src="" width="550" height="257" id="image">
			</div>
			</form>
		</div>
	</div>
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
			
		<form  id="editForm" method="post" accept-charset="UTF-8" action="specialUpdate" enctype="multipart/form-data">
			<div class="modal-body">
			<input class="form-control" type="hidden" name="id" id="topicid">
			 <div class="form-group">
                   <label>名称</label>                  
                   <input class="form-control" type="text" name="title" id="topictitle">
             </div>
             <div class="form-group">
                 <label class="control-label col-lg-pull-4">选择图片</label>
                    <div class="">
                     <div class="fileupload fileupload-new" data-provides="fileupload">
                       <div class="fileupload-new thumbnail" style="width: 200px; height: 150px;"><img src="" alt="" id="showTopic"/></div>
                       <div class="fileupload-preview fileupload-exists thumbnail" style="max-width: 200px; max-height: 150px; line-height: 20px;"></div>
                     <div>
                  <span class="btn btn-file btn-primary"><span class="fileupload-new">选择图片</span><span class="fileupload-exists">更换</span><input type="file" name="pic1"></span>
                  <a href="#" class="btn btn-danger fileupload-exists" data-dismiss="fileupload">移除</a>
                 </div>
               </div>
             </div>
          </div>
    <!--   轮播图名称：<input class="form-control" type="search" placeholder="请输入指标名称" name="title"> -->
    
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
			
			<form  id="addForm" method="post" accept-charset="UTF-8" action="specialAdd" enctype="multipart/form-data">
			<div class="modal-body">
			 <div class="form-group">
                   <label>专题名称</label>
                   <input class="form-control" type="text" placeholder="请输入专题名称" name="title">
             </div>
             <div class="form-group">
                 <label class="control-label col-lg-pull-4">选择图片</label>
                    <div class="">
                     <div class="fileupload fileupload-new" data-provides="fileupload">
                       <div class="fileupload-new thumbnail" style="width: 200px; height: 150px;"><img src="assets/img/demoUpload.jpg" alt="" /></div>
                       <div class="fileupload-preview fileupload-exists thumbnail" style="max-width: 200px; max-height: 150px; line-height: 20px;"></div>
                     <div>
                  <span class="btn btn-file btn-primary"><span class="fileupload-new">选择图片</span><span class="fileupload-exists">更换</span><input type="file" name="pic1"></span>
                  <a href="#" class="btn btn-danger fileupload-exists" data-dismiss="fileupload">移除</a>
                 </div>
               </div>
             </div>
          </div>
    <!--   轮播图名称：<input class="form-control" type="search" placeholder="请输入指标名称" name="title"> -->
    
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
    <script src="<%=path %>/assets/js/jquery-1.10.2.js"></script>
      <!-- Bootstrap Js -->
    <script src="<%=path %>/assets/js/bootstrap.min.js"></script>
    <!-- Metis Menu Js -->
    <script src="<%=path %>/assets/js/jquery.metisMenu.js"></script>
      <!-- Custom Js -->
    <script src="<%=path %>/assets/js/custom-scripts.js"></script>
    
    <script src="<%=path %>/assets/js/bootstrap-switch.min.js"></script>
   <script src="<%=path %>/assets/js/dataTables/jquery.dataTables.js"></script>
    <script src="<%=path %>/assets/js/dataTables/dataTables.bootstrap.js"></script>   
    <script src="<%=path %>/assets/js/bootstrap-order.min.js"></script>
    <script src="<%=path %>/assets/js/bootstrap-fileupload.js"></script>
    <script>
            $(document).ready(function () {
                $('#dataTables-example').dataTable();
              /*   var height=document.documentElement.clientHeight;
                document.getElementById('iframe-page-content').style.height='800px'; */
                
            });           
            
            var menuClick = function(menuUrl) {
            	alert(menuUrl)
            	 $.ajax({
                         type: 'GET',
                         url:  menuUrl,
                         dataType: "html",
                         cache:false,
                         success: function(data){
                    	 alert(data);
                             $('#getNewData').html(data);
                         },
                         error : function(data){
                         }
                     });           	
/*             $("#iframe-page-content").attr('src',menuUrl); */
            };
           
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
            	var theme_name=encodeURI(encodeURI(searchName));
            	
            	var formSearch=document.getElementById("formSearch");
            	formSearch.action="searchCol?theme="+theme_name;
            	formSearch.submit();
            	
            }
  
         /*    function add(themename){
            	alert(themename);
            	var addForm=document.getElementById("");
            	addForm.action="";
            	addFrom.submit();
            } */
  	
            
            function edit(ID,title,image){
            	$("#topicid").val(ID);
            	$("#topictitle").val(title);
            	$("#showTopic").attr('src',image);
                	
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
            
            
            function showS(json) {             	
            	for(var i=0,l=json.length;i<l;i++){
            		order.addItem(json[i]);
            		}
            	order.toggleShow();         	
            }
        
            function addSort(item) {
            	order.addItem(item);
            }
        
            function getData(){
            	var data = order.getData();
                alert(JSON.stringify(data)); 
            }
            function imageShow(image){
            	$("#image").attr('src',image);
                	
            }
            
        
    </script>
    
   
</body>
</html>
