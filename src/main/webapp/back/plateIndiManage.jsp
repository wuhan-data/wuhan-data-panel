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
        
      #addElement{
        margin-bottom:10px;
        }
       

    </style>
</head>
<body>
   
        <div id="page-wrapper" >
            <div id="page-inner">
			 <div class="row">
                    <div class="col-md-12">
                        <h1 class="page-header">
                            栏目管理 <small>指标配置</small>
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

   <input type="hidden" value="${cid}" name="cid"/>
  <div class="row" id="addElement">
     <div class="btns col-md-2">
      <div class="btn btn-info" data-toggle="modal" data-target="#myAddModal" onclick="add()"><i class="fa fa-plus"></i>添加</div>
    </div>  
  
  </div>

    
    <div width="10px"></div>
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                        <tr>
                                            <th width="10%">板块</th>
                                            <th width="15%">指标id</th>
                                            <th width="20%">指标名称</th>
                                            <th width="20%">指标别名</th>
                                            <th width="10%">展现形式</th>
                                            <th width="35%">操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
     <c:forEach items="${indicolumnByPage}" var="c" varStatus="st">
        <tr>
            <td >${c.plate_id}</td>
            <td>${c.search_indi_id }</td>
            <td >${c.indi_old_name}</td>
            <td >${c.indi_new_name}</td>
            <td>${c.show_type }</td>
            <td width=40%>
<%-- <div class="btn btn-warning btn-sm" style="margin-right:3px" data-toggle="modal" data-target="#myEditModal" onclick="edit(${c.theme_name})">
<i class="fa fa-edit"></i>修改
</div>
 --%>

<%-- <div class="btn btn-warning btn-sm" style="margin-right:3px" data-toggle="modal" data-target="#myEditModal" onclick="edit('${c.indi_id}','${c.indi_name}')">
<i class="fa fa-edit"></i>修改
</div> --%>
<a href="#" onclick="delClick('${c.plate_id }','${c.indi_id}','plateIndiDel')">
<div class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i>删除</div>
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
         <a href="#" id="noPerShow" onclick="updateShowClick('1','${c.indi_id }','${c.plate_id}','plateIndiUpdateShow')">不展示</a>             
      </li>
    </c:if>
    <c:if test="${c.is_show==1 }">
      <li>
         <a href="plateIndiUpdateShow?id=${c.indi_id }&is_show=0&pid=${c.plate_id}" id="perShow" onclick="updateShowClick('0','${c.indi_id }','${c.plate_id}','plateIndiUpdateShow')">展示</a>            
      </li>
    </c:if>
    </ul>
</div>   
</td>
        </tr>
    </c:forEach>
</tbody>

                                </table>
                                <input type="hidden" value="${cid}" name="cid_"/>
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
	<form class="form-inline" id="editForm" method="post" accept-charset="UTF-8" action="indiColumnUpdateOther">
			<div class="modal-body">		

	<input class="form-control" type="hidden" name="indi_id" id="indi_id">
   板块名称：<input class="form-control" type="text" name="pname" id="indi_name">   
   <input type="hidden" value="${pid}" name="cid2"/>
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
			
			<form class="" id="addForm" method="post" accept-charset="UTF-8" action="#">
			<div class="modal-body">
			
			 <div class="form-group">
                   <label>指标id</label>                  
                   <input class="form-control" type="text" placeholder="请输入指标id" name="indi_id">
             </div>
              <div class="form-group">
                   <label>指标名称</label>                  
                   <input class="form-control" type="text" placeholder="请输入指标名称" name="indi_old_name">
             </div>
             <div class="form-group">
                   <label>指标别名</label>                  
                   <input class="form-control" type="text" placeholder="请输入指标别名" name="indi_new_name">
             </div>
              <div class="form-group">
                   <label>展示类型</label>                  
                   <input class="form-control" type="text" placeholder="请输入展示类型" name="show_type">
             </div>
              <div class="form-group">
                   <label>展示颜色</label>                  
                   <input class="form-control" type="text" placeholder="请输入展示颜色" name="show_color">
             </div>
      
   
         <input type="hidden" value="${cid}" name="cid"/>
    
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭
				</button>
				<button type="submit" class="btn btn-primary" onclick="addClick('plateIndiAdd')">
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

                           <a href="#" class='first' onclick="pageClick('1','${cid }','plateIndiInit')">首页</a>
                           <a href="#" class='pre' onclick="pageClick('${page.currentPage-1}','${cid }','plateIndiInit')">上一页</a>
                        </c:if>
                        当前第<span>${page.currentPage}/${page.totalPage}</span>页
                        <c:if test="${page.currentPage != page.totalPage}">
                            <a href="#" class='next' onclick="pageClick('${page.currentPage+1}','${cid }','plateIndiInit')">下一页</a>
                            <a href="#" class='last' onclick="pageClick('${page.totalPage}','${cid }','plateIndiInit')">末页</a>
                        </c:if>
                        跳至&nbsp;

<input type="hidden" value="${cid }" name="id" id="pageCid">
                        <input id="currentPageText" type='text' value='${page.currentPage}' class='allInput w28' name="currentPage" />&nbsp;页&nbsp;
                        <input type="submit" value="GO" class="btn-primary btn-sm" onclick="pageGoClick('plateIndiInit')">
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
    <script>
            $(document).ready(function () {
              
               
            });
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
                 
                 
                 delClick = function(pid,id,Url) {
                    $.ajax({
                               type: 'GET',
                               url:  Url+"?pid="+pid+"&id="+id,
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
                 
                 
                    updateShowClick = function(is_show,id,pid,Url) {
                      $.ajax({
                                 type: 'GET',
                                 url:  Url+"?id="+id+"&is_show="+is_show+"&pid="+pid,
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
                      
                      pageClick = function(currentPage,id,Url) {
                        $.ajax({
                                   type: 'GET',
                                   url:  Url+"?currentPage="+currentPage+"&id="+id,
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
                       	var id = document.getElementById("pageCid").value;
                            $.ajax({
                                       type: 'GET',
                                       url:  Url+"?currentPage="+currentPage+"&id="+id,
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
       	
         
            function edit(ID,indiname){
            	$("#indi_id").val(ID);
            	$("#indi_name").val(indiname);
                	
            }
          
            
        
    </script>
    
   
</body>
</html>
