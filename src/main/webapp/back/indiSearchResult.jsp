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
    <link href="back/assets/css/bootstrap.css" rel="stylesheet" />
     <!-- FontAwesome Styles-->
    <link href="back/assets/css/font-awesome.css" rel="stylesheet" />
        <!-- Custom Styles-->
    <link href="back/assets/css/custom-styles.css" rel="stylesheet" />
     <!-- Google Fonts-->
    <link href='https://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
   	<script src="back/assets/laydate/laydate.js"></script> 
   
  
   <script>
   	//日期
			//执行一个laydate实例
			laydate.render({
			  elem: '#addstarttime' //指定元素
			});
			laydate.render({
			  elem: '#addendtime' //指定元素
			});
			
	</script>
   
   <script>
   	//日期
			//执行一个laydate实例
			laydate.render({
			  elem: '#starttime' //指定元素
			});
			laydate.render({
			  elem: '#endtime' //指定元素
			});
			
	</script>
	
   
   
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
        
        #deleteIndi{
        color:#FFF;
        }

    </style>
   
   
</head>
<body >
    <div id="wrapper">


        <!-- /. NAV SIDE  -->
        <div id="page-wrapper" >
            <div id="page-inner">
			 <div class="row">
                    <div class="col-md-12">
                        <h1 class="page-header">
                            数据管理 <small>指标数据维护</small>
                        </h1>
                    </div>
                </div> 
                 <!-- /. ROW  -->
                 
                 
                 
                   <div class="row">
                <div class="col-md-12">
                    <!-- Advanced Tables -->
			   <div class="panel panel-default">
                        <div class="panel-heading">
                             指标
                        </div>
                        <div class="panel-body">
                     
  
  
   <div class="btns col-md-2" style="margin:5px">
      <div class="btn btn-info" data-toggle="modal" data-target="#myAddModal">添加</div>
   </div>
    <form class="form-inline my-2 my-lg-0" style="float:right" method="post" id="formSearch" accept-charset="UTF-8">
      <input class="form-control mr-sm-2" type="search" placeholder="指标搜索关键字" aria-label="Search" id="searchKeyWord">
      <button class="btn btn-success my-2 my-sm-0" onclick="search('IndiSearch')">搜索</button>
    </form>

                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                        <tr>
                                            <th>指标数据id</th>
                                            <th>指标代码</th>
                                            <th>指标名称</th>
                                            <th>状态</th>
                                            <th>展示类型</th>
                                            <th>来源</th>
                                            <th>操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
     <c:forEach items="${indexManageList}" var="c" varStatus="st">
        <tr>
            <td>${c.id}</td>
            <td>${c.indi_code}</td>
            <td>${c.indi_name}</td>
            <td>${c.status}</td>
            <td>${c.show_type}</td>
            <td>${c.source}</td>

            <td >
            	<div class="btn btn-warning btn-sm" style="margin-right:3px" data-toggle="modal" data-target="#myEditModal" onclick='updateIndi("${c.id}","${c.indi_code}","${c.indi_name}","${c.show_type}","${c.status}")'><i class="fa fa-edit"></i> 修改</div>
            	<div class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i> <a href="#" id="deleteIndi" onclick="deleteIndi('${c.id}','searchDelete')">删除</a></div>

	<div class="btn-group">
    <button type="button" class="btn btn-info dropdown-toggle btn-sm"  data-toggle="dropdown">
    <c:if test="${c.status==0 }"> &nbsp;&nbsp;&nbsp; 展示</c:if>
    <c:if test="${c.status==1 }">不展示</c:if>	
    <span class="caret"></span>         
    </button>
    <ul class="dropdown-menu" role="menu">
    <c:if test="${c.status==1 }">
      <li>
         <a href="#" id="perShow" onclick="perShow('${c.id}','search_per_show')">展示</a>            
      </li>
    </c:if>
    <c:if test="${c.status==0 }">
      <li role="presentation">
         <a href="#" id="noPerShow" onclick="noPerShow('${c.id}','search_no_per_show')">不展示</a>             
      </li>
  	</c:if>
    </ul>
</div>    
				   
				  				
            </td>
        </tr>
    </c:forEach>
                                    </tbody>
                                    
        <input type="hidden" id="currentPage" value="${cPage}"/>
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
			 <form action="#" method="post"  id="editForm" accept-charset="UTF-8" enctype="multipart/form-data"> 
			<div class="modal-body">
				<span class="text-info">指标数据id</span><input type="text" class="form-control" id="IndiId" value="" name="Id" readonly>
				<span class="text-info">指标代码</span><input type="text" class="form-control" id="IndiCode" value="" name="Code" readonly>
				<span class="text-info">指标名称</span><input type="text" class="form-control" id="IndiName" value="" name="Name">
<!-- 				<span class="text-info">频度代码</span><input type="text" class="form-control" id="IndiFreqCode" value="" name="FreqCode"> -->
				
<!-- 				<span class="text-info">报告期</span><input type="text" class="demo-input" placeholder="请选择日期" id="starttime" name="StartTime"><input type="text" class="demo-input" placeholder="请选择日期" id="endtime" name="EndTime"><br> -->
				
				
<!-- 				<span class="text-info">指标值</span><input type="text" class="form-control" id="IndiValue" value="" name="Value"> -->
				<span class="text-info">展示类型</span><input type="text" class="form-control" id="IndiShowType" value="" name="ShowType">
				<span class="text-info">状态</span><input type="text" class="form-control" id="IndiStatus" value="" name="Status">
<!-- 				<span class="text-info">来源</span><input type="text" class="form-control" id="IndiSource" value="" name="Source"> -->
				
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭
				</button>
				
					<button type="submit" class="btn btn-primary" onclick="updatebutton('searchUpdateIndiData')">
						提交
					</button>
				
			</div>
			 </form> 
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>



                                <!--添加 模态框（Modal） -->
<div class="modal" id="myAddModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
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
			 <form action="#" method="post" id="addForm" accept-charset="UTF-8"enctype="multipart/form-data"> 
			<div class="modal-body">
<!-- 				<span class="text-info">指标数据id</span><input type="text" class="form-control" id="addIndiId" value="" name="addId"> -->
				<span class="text-info">指标代码</span><input type="text" class="form-control" id="addIndiCode" value="" name="addCode">
				<span class="text-info">指标名称</span><input type="text" class="form-control" id="addIndiName" value="" name="addName">
<!-- 				<span class="text-info">频度代码</span><input type="text" class="form-control" id="addIndiFreqCode" value="" name="addFreqCode"> -->
				
<!-- 				<span class="text-info">报告期</span><input type="text" class="demo-input" placeholder="请选择日期" id="addstarttime" name="addStartTime"><input type="text" class="demo-input" placeholder="请选择日期" id="addendtime" name="addEndTime"><br> -->
				
				
<!-- 				<span class="text-info">指标值</span><input type="text" class="form-control" id="addIndiValue" value="" name="addValue"> -->
<!-- 				<span class="text-info">时点</span><input type="text" class="form-control" id="addIndiTimePoint" value="" name="addTimePoint"> -->
				<span class="text-info">状态</span><input type="text" class="form-control" id="addIndiStatus" value="" name="addStatus">
				<span class="text-info">来源</span><input type="text" class="form-control" id="addIndiSource" value="" name="addSource">
				
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭
				</button>
				
					<button type="submit" class="btn btn-primary" onclick="addbutton('searchAddIndiData')">
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

                           <a href="#" class='first' onclick="pageClick('1','IndiSearchPage')">首页</a>
                           <a href="#" class='pre' onclick="pageClick('${page.currentPage-1}','IndiSearchPage')">上一页</a>
                        </c:if>
                        当前第<span>${page.currentPage}/${page.totalPage}</span>页
                        <c:if test="${page.currentPage != page.totalPage}">
                             <a href="#" class='next' onclick="pageClick('${page.currentPage+1}','IndiSearchPage')">下一页</a>
                            <a href="#" class='last' onclick="pageClick('${page.totalPage}','IndiSearchPage')">末页</a>
                        </c:if>
                        跳至&nbsp;

                        <input id="currentPageText" type='text' value='${page.currentPage}' class='allInput w28' name="currentPage" />&nbsp;页&nbsp;
                        <input type="submit" value="GO" class="btn-primary btn-sm" onclick="pageGoClick('IndiSearchPage')">
                    </form>
                </div>
                    
    </div>
                    
                    
                    
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
         
     <!-- /. WRAPPER  -->
    <!-- JS Scripts-->
    <!-- jQuery Js -->
    
<!--      <script src="assets/js/jquery-1.10.2.js"></script> -->
<!--       Bootstrap Js -->
<!--     <script src="assets/js/bootstrap.min.js"></script> -->
    <!-- Metis Menu Js -->
    <script src="assets/js/jquery.metisMenu.js"></script>
      <!-- Custom Js -->
    <script src="assets/js/custom-scripts.js"></script>
    
    <script src="assets/js/bootstrap-switch.min.js"></script>
   <script src="assets/js/dataTables/jquery.dataTables.js"></script>
    <script src="assets/js/dataTables/dataTables.bootstrap.js"></script>   
    <script src="assets/js/bootstrap-order.min.js"></script>
<!--        <script> -->
//             $(document).ready(function () {
//                 $("#dataTables-example").dataTable();
//             });
            
<!--     </script>  -->
    

	<script type="text/javascript">
	
	$(document).ready(function () {
	     
    });
	
	addbutton = function(Url) {
   	   $('.modal-backdrop').remove();
   	    $('body').removeClass('modal-open');
          	alert(Url);
        	var data = new FormData(document.getElementById("addForm"));
        	console.log(data.get("title"));
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
              	 	alert(data);
                       $('#getNewData').html(data);
                   },
                   error : function(data){
                   }
               });    
        };
        
        
        deleteIndi = function(indi_id,Url) {
            alert(indi_id);
          $.ajax({
                     type: 'GET',
                     url:  Url+"?indi_id="+indi_id,
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
        
          updatebutton = function(Url) {
        	   $('.modal-backdrop').remove();
        	    $('body').removeClass('modal-open');
               alert(Url);
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
                       	 	alert(data);
                                $('#getNewData').html(data);
                            },
                            error : function(data){
                            }
                        });    
                 };
          
                 pageGoClick = function(Url) {
                	 alert(Url);
                	var currentPage = document.getElementById("currentPageText").value;
                	 alert(currentPage);
                     $.ajax({
                                type: 'GET',
                                url:  Url+"?currentPage="+currentPage,
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
             
                     perShow = function(indi_id,Url) {
                         alert(indi_id);
                         $.ajax({
                                    type: 'GET',
                                    url:  Url+"?indi_id="+indi_id,
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
                         
                         noPerShow = function(indi_id,Url) {
                             alert(indi_id);
                             $.ajax({
                                        type: 'GET',
                                        url:  Url+"?indi_id="+indi_id,
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
             
             
                             search = function(Url) {
                            	 var searchName=document.getElementById("searchKeyWord").value;
                             	var keyWord=encodeURI(encodeURI(searchName));
                               $.ajax({
                                          type: 'GET',
                                          url:  Url+"?keyword="+keyWord,
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
             
             
	
		function updateIndi(id,indi_code,indi_name,show_type,status){
			
 			 $("#IndiId").val(id);
			$("#IndiCode").val(indi_code);
			$("#IndiName").val(indi_name);
			
			$("#IndiShowType").val(show_type);
			$("#IndiStatus").val(status);
			
}
		
		function updatebutton(){
				alert("更新成功！");
			}
		
// 		function addbutton(){
// 			alert("添加成功！");
// 		}
		
		/* 删除指标 */
// 		function deleteIndi(id){
// 			if(confirm('确实要删除该指标吗?')) {
<%-- 				$.post("<%=basePath%>delete.action",{"id":id},function(data){ --%>
// 					alert("指标删除成功！");
// 					window.location.reload();
// 				});
// 			}
// 		}
		
		/* 展示指标*/
// 		function perShow(id){
			
<%-- 				$.post("<%=basePath%>per_show.action",{"id":id},function(data){ --%>
// 					alert("允许指标展示！");
// 					window.location.reload();
// 				});
			
// 		}
		
// 		function noPerShow(id){
			
<%-- 			$.post("<%=basePath%>no_per_show.action",{"id":id},function(data){ --%>
// 				alert("不允许指标展示！");
// 				window.location.reload();
// 			});
		
// 	}
		function search1(){
        	var searchName=document.getElementById("searchKeyWord").value;
        	alert(searchName)
        	var keyWord=encodeURI(encodeURI(searchName));
        	
        	var formSearch=document.getElementById("formSearch");
        	formSearch.action="IndiSearch?keyword="+keyWord;
        	formSearch.submit();
        	
        }
		
	
</script>
   
</body>
</html>
