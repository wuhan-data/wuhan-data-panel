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
       

    </style>
</head>
<body>
        <!-- /. NAV SIDE  -->
        <div id="page-wrapper" >
            <div id="page-inner">
			 <div class="row">
                    <div class="col-md-12">
                        <h1 class="page-header">
                            栏目管理 <small>栏目维护</small>
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
                     
<div class="form-group col-md-2">
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
</div>
  
  
   <div class="btns col-md-6">
      <div class="btn btn-info" data-toggle="modal" data-target="#myAddModal" onclick="add()"><i class="fa fa-plus"></i>添加</div>
      <button class="btn btn-primary" id="reset" onclick='showS(${json})'><i class="fa fa-cog"></i>设置</button>
    </div>  
 
     <form class="form-inline col-md-4" style="float:right" id="formSearch" method="post" accept-charset="UTF-8">
      <input class="form-control" type="search" placeholder="PMI指数(全国)" aria-label="Search" id="searchtname" value="${placeholder }">
      <button class="btn btn-success" onclick="search()">搜索</button>
    </form>
    
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                        <tr>
                                            <th>一级栏目</th>
                                            <th>栏目名称</th>
                                        <!--     <th>指标id</th>
                                            <th>指标名称</th> -->
                                            <th>操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
     <c:forEach items="${analysisListByPage}" var="c" varStatus="st">
        <tr>
            <td>${c.type_name}</td>
            <td>${c.theme_name}</td>
<!--             <td>1300048</td>
            <td>工业生产排放量</td> -->
            <td>
<%-- <div class="btn btn-warning btn-sm" style="margin-right:3px" data-toggle="modal" data-target="#myEditModal" onclick="edit(${c.theme_name})">
<i class="fa fa-edit"></i>修改
</div>
 --%>
 <a href="colPlateInit?id=${c.id }">
<div class="btn btn-success btn-sm"><i class="fa fa-search"></i>查看板块
</div>
</a>
<div class="btn btn-warning btn-sm" style="margin-right:3px" data-toggle="modal" data-target="#myEditModal" onclick="edit('${c.theme_name}',${c.id})">
<i class="fa fa-edit"></i>修改
</div>
<a href="deleteCol?id=${c.id }">
<div class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i>删除
</div>
</a>
<div class="btn-group">
    <button type="button" class="btn btn-info dropdown-toggle btn-sm "  data-toggle="dropdown">
     <c:if test="${c.is_show==0 }">
     展示
     </c:if>
     <c:if test="${c.is_show==1 }">
     不展示
     </c:if>
      <span class="caret"></span>         
    </button>
      
    <ul class="dropdown-menu" role="menu">
    <c:if test="${c.is_show==0 }">
     <li role="presentation">
         <a href="updateIsShow?id=${c.id }&is_show=1" id="noPerShow" onclick="noPerShow(${c.id})">不展示</a>             
      </li>
    </c:if>
    <c:if test="${c.is_show==1 }">
      <li>
         <a href="updateIsShow?id=${c.id }&is_show=0" id="perShow" onclick="perShow(${c.id})">展示</a>            
      </li>
    </c:if>
    </ul>
</div>   
</td>
        </tr>
    </c:forEach>
                                    </tbody>
                                    

         <input type="hidden" value="${tname}" name="ctname"/>
         <input type="hidden" value="${json}" name="json" id="jsoncontent"/>
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
	<form class="form-inline" id="editForm" method="post" accept-charset="UTF-8" action="editTheme">
			<div class="modal-body">		

	<input class="form-control" type="hidden" name="editThemeID" id="editThemeID">
   栏目名称：<input class="form-control" type="text" name="editThemeName" id="editThemeName">   
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
			
			<form class="form-inline" id="addForm" method="post" accept-charset="UTF-8" action="addTheme">
			<div class="modal-body">
				
     栏目名称：<input class="form-control" type="search" placeholder="请输入栏目名称" name="addThemeName">
    
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
            function edit(themename,ID){
            	$("#editThemeName").val(themename);
            	$("#editThemeID").val(ID);
                	
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
/*             
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
            }; */
            
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
            
    </script>
    
   
</body>
</html>
