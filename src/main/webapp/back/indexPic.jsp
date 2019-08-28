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
    <link href="<%=path %>/assets/css/bootstrapValidator.css" rel="stylesheet" />
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
              
        #addElement{
        margin-bottom:10px;
        }
        
       

    </style>
</head>
<body>

        <!-- /. NAV SIDE  -->
        <div id="page-wrapper" >
            <div id="page-inner">
			 <div class="row">
                    <div class="col-md-12">
                        <h1 class="page-header">
                            首页管理 <small>轮播图维护</small>
                        </h1>
                    </div>
                </div> 
                 <!-- /. ROW  -->
                 
                 
                                
            <div class="row">
                <div class="col-md-12">
                    <!-- Advanced Tables -->
                    <div class="panel panel-default">
                        <div class="panel-heading">
                             轮播图
                        </div>
                        <div class="panel-body">

  <div class="row" id="addElement">
     <div class="btns col-md-2">
      <div class="btn btn-info" data-toggle="modal" data-target="#myAddModal" onclick="add()"><i class="fa fa-plus"></i>添加</div>
    </div>  
 
<!--      <form class="form-inline col-md-5" style="float:right" id="formSearch" method="post" accept-charset="UTF-8">
      <input class="form-control" type="search" placeholder="PMI指数(全国)" aria-label="Search" id="searchtname" value="">
      <button class="btn btn-success" onclick="search()">搜索</button>
    </form> -->
  
  </div>

    
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                        <tr>
                                            <th width="3%">id</th>
                                            <th width="22%">名称</th>
                                            <th width="15%">路径</th>
                                            <th width="10%">图片</th>
                                            <th width="50%">操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
     <c:forEach items="${specialByPage}" var="c" varStatus="st">
        <tr>
            <td >${c.id}</td>
            <td >${c.title}</td>
            <td>${c.image}</td>
            <td ><img src="${c.image}" width="80" height="42"></td>
            <td width=40%>
<%-- <div class="btn btn-warning btn-sm" style="margin-right:3px" data-toggle="modal" data-target="#myEditModal" onclick="edit(${c.theme_name})">
<i class="fa fa-edit"></i>修改
</div>
 --%>

<div class="btn btn-success btn-sm" style="margin-right:3px" data-toggle="modal" data-target="#myImageModal" onclick="imageShow('${c.image}')">
<i class="fa fa-search"></i>查看大图
</div>
<div class="btn btn-warning btn-sm" style="margin-right:3px" data-toggle="modal" data-target="#myEditModal" onclick="edit('${c.id}','${c.title}','${c.image}')">
<i class="fa fa-edit"></i>修改
</div>
<%-- <a href="indexPicDel?id=${c.id }"> --%>
<a  href="#" onclick="delClick('${c.id }','indexPicDel')">
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
         <a href="#" id="noPerShow" onclick="updateShowClick('1','${c.id }','indexPicUpdateShow')">不展示</a>             
      </li>
    </c:if>
    <c:if test="${c.is_show==1 }">
      <li>
         <a href="#" id="perShow" onclick="updateShowClick('0','${c.id }','indexPicUpdateShow')">展示</a>            
      </li>
    </c:if>
    </ul>
</div>   
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
			
			
			<form  id="editForm" method="post" accept-charset="UTF-8" action="#" enctype="multipart/form-data">
			<div class="modal-body">
			<input class="form-control" type="hidden" name="picid" id="picid">
			 <div class="form-group">
                   <label>名称</label>                  
                   <input class="form-control" type="text" placeholder="请输入轮播图名称" name="title" id="pictitle">
             </div>
             <div class="form-group">
                 <label class="control-label col-lg-pull-4">选择图片</label>
                    <div class="">
                     <div class="fileupload fileupload-new" data-provides="fileupload">
                       <div class="fileupload-new thumbnail" style="width: 200px; height: 150px;"><img src="" alt="" id="showPic"/></div>
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
				<button type="submit" class="btn btn-primary" onclick="editClick('indexPicUpdate')">
					提交
				</button>
			</div>
			</form>
			
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>

  <!-- 查看大图 模态框（Modal） -->
<div class="modal fade" id="myImageModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">

<!-- <div class="modal-dialog modal-lg" style="display: inline-block; width: auto;">
            <div class="modal-content">
             <img  id="imgInModalID" src="" >
            </div>
</div> -->

	<div class="modal-dialog">
		<div class="modal-content">
			<!-- <div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					大图
				</h4>
			</div> -->
	<form class="form-inline" id="" method="post" accept-charset="UTF-8" action="specialUpdate">
			<div class="modal-body">		
<img src="" width="550" height="257" id="image">
			</div>
			</form>
		</div>
	</div>
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
			
			<form  id="addForm" method="post" accept-charset="UTF-8" action="#" enctype="multipart/form-data">
			<div class="modal-body">
			 <div class="form-group">
                   <label>名称</label>
                   <input class="form-control" type="text" placeholder="请输入轮播图名称" name="title">
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
				<button type="submit" class="btn btn-primary" onclick="addClick('indexPicAdd')">
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

                           <a href="#" class='first' onclick="pageClick('1','indexPicInit')">首页</a>
                           <a href="#" class='pre' onclick="pageClick('${page.currentPage-1}','indexPicInit')">上一页</a>
                        </c:if>
                        当前第<span>${page.currentPage}/${page.totalPage}</span>页
                        <c:if test="${page.currentPage != page.totalPage}">
                            <a href="#" class='next' onclick="pageClick('${page.currentPage+1}','indexPicInit')">下一页</a>
                            <a href="#" class='last' onclick="pageClick('${page.totalPage}','indexPicInit')">末页</a>
                        </c:if>
                        跳至&nbsp;

                        <input id="currentPageText" type='text' value='${page.currentPage}' class='allInput w28' name="currentPage" />&nbsp;页&nbsp;
                        <input type="submit" value="GO" class="btn-primary btn-sm" onclick="pageGoClick('indexPicInit')">
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
  <%--   <script src="<%=path %>/assets/js/jquery-1.10.2.js"></script>
      <!-- Bootstrap Js -->
    <script src="<%=path %>/assets/js/bootstrap.min.js"></script> --%>
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
               
               
               delClick = function(id,Url) {
                  $.ajax({
                             type: 'GET',
                             url:  Url+"?id="+id,
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
               
               
                  updateShowClick = function(is_show,id,Url) {
                      $.ajax({
                                 type: 'GET',
                                 url:  Url+"?id="+id+"&is_show="+is_show,
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
  
            function edit(ID,title,image){
            	$("#picid").val(ID);
            	$("#pictitle").val(title);
            	$("#showPic").attr('src',image);
                	
            }
            function imageShow(image){
            	$("#image").attr('src',image);
                	
            }
            
        
    </script>
    
   
</body>
</html>
