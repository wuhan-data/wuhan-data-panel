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
       
         td,th{
        text-align:center;
        }
        
         #dataTables-example tbody{
        display:block;
        height:250px;
        width:100%;
		overflow-y:scroll;        
        }
        #dataTables-example thead, tbody tr{
        display:table;
        width:100%;
        table-layout:fixed;
        }

    </style>
</head>
<body>
  <div id="wrapper">
        <div id="page-wrapper" >
            <div id="page-inner">
			 <div class="row">
                    <div class="col-md-12">
                        <h1 class="page-header">
                            栏目管理 
                            <div><small><a href="#" onclick="backFirstMenu('${type_name}')">${type_name}(${label_name })</a> > <a href="#" onclick="backSecondMenu('${label_id }','${type_name}','${label_name}','analysisSecondInit')">${theme_name}</a> > </small></div>
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

   <input type="hidden" value="${cid}" name="cid" />
  <div class="row" id="addElement">
     <div class="btns col-md-6">
      <div class="btn btn-info" data-toggle="modal" data-target="#myAddModal" onclick="add()"><i class="fa fa-plus"></i>添加版块</div>
      <button class="btn btn-primary" id="" onclick="dosaveSeq('colPlateUpdateWeight','${cid}')"><i class="fa fa-cog"></i>保存顺序</button>
    </div>  
 
<!--      <form class="form-inline col-md-5" style="float:right" id="formSearch" method="post" accept-charset="UTF-8">
      <input class="form-control" type="search" placeholder="PMI指数(全国)" aria-label="Search" id="searchtname" value="">
      <button class="btn btn-success" onclick="search()">搜索</button>
    </form> -->
  
  </div>

    
    <div width="10px"></div>
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                        <tr>
                                            <th width="15%">二级栏目</th>
                                            <th width="15%">板块</th>
                                            <th>展现形式</th>
                                            <th>期数</th>
                                   			<th>权重</th>
                                            <th width=40%>操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
     <c:forEach items="${indicolumnByPage}" var="c" varStatus="st">
        <tr id="${c.plate_weight }">
            <td width="15%">${c.cname}</td>
            <td width="15%">${c.pname}</td>
            <td>${c.show_type }</td>
            <td>${c.term }</td>
            <td>${c.plate_weight }</td>
            <td width=40%>
<%-- <div class="btn btn-warning btn-sm" style="margin-right:3px" data-toggle="modal" data-target="#myEditModal" onclick="edit(${c.theme_name})">
<i class="fa fa-edit"></i>修改
</div>
 --%>
  <a href="#" onclick="showIndiClick('${c.pid }','${plate_name }','${label_id }','${label_name }','${type_name }','${theme_name }','${theme_id }','plateIndiInit')">
<div class="btn btn-success btn-sm"><i class="fa fa-search"></i>查看指标
</div>
</a>
<div class="btn btn-warning btn-sm" style="margin-right:3px" data-toggle="modal" data-target="#myEditModal" onclick="edit('${c.pid}','${c.pname}','${c.show_type }','${cid }','${c.term }')">
<i class="fa fa-edit"></i>修改
</div>
<a href="#" onclick="delClick('${c.id }','${cid}','colPlateDel')">
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
         <a href="#" id="noPerShow" onclick="updateShowClick('1','${c.id }','${cid}','colPlateUpdateShow')">不展示</a>             
      </li>
    </c:if>
    <c:if test="${c.is_show==1 }">
      <li>
         <a href="#" id="perShow" onclick="updateShowClick('0','${c.id }','${cid}','colPlateUpdateShow')">展示</a>            
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
	<form class="" id="editForm" method="post" accept-charset="UTF-8" action="#">
			<div class="modal-body">		

	<input class="form-control" type="hidden" name="indi_id" id="indi_id">  
     板块名称：<input class="form-control" type="search"  name="pname" id="pnameEdit"> 
      展示形式：<input class="form-control" type="search" name="show_type" id="show_typeEdit"> 
      显示期数：<input class="form-control" type="search" name="term" id="termEdit"> 
     		 <input class="form-control"  type="hidden" value="${cid}" name="cid" id="cidEdit"/>
     		 <input class="form-control"  type="hidden" value="${pid}" name="pid" id="pidEdit"/>

			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭
				</button>
				<button type="submit" class="btn btn-primary" onclick="editClick('colPlateUpdate')">
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
			
			<form class="" id="addForm" method="post" accept-charset="UTF-8" action="colPlateAdd">
			<div class="modal-body">
				
<!-- <!--      指标  id：<input class="form-control" type="search" placeholder="请输入指标id" name="indi_id"> -->
      板块名称：<input class="form-control" type="search" placeholder="请输入指标名称" name="pname"> 
      展示形式：<input class="form-control" type="search" placeholder="请输入展示形式" name="show_type"> 
      显示期数：<input class="form-control" type="search" placeholder="请输入显示期数" name="term"> 
     		  <input class="form-control"  type="hidden" value="${cid}" name="cid"/>
     		  <input class="form-control"  type="hidden" value="${id}" name="id"/>
  
    
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭
				</button>
				<button type="submit" class="btn btn-primary" onclick="addClick('colPlateAdd')">
					提交
				</button>
			</div>
			</form>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>


                             <%--    <div class="row">

									 <div class='page fix'>
                    <form method="post" action="#" id="pageForm">
                        共 <b>${page.totalNumber}</b> 条
                        <c:if test="${page.currentPage != 1}">

                           <a href="#" class='first' onclick="pageClick('1','${cid }','colPlateInit')">首页</a>
                           <a href="#" class='pre' onclick="pageClick('${page.currentPage-1}','${cid }','colPlateInit')">上一页</a>
                        </c:if>
                        当前第<span>${page.currentPage}/${page.totalPage}</span>页
                        <c:if test="${page.currentPage != page.totalPage}">
                            <a href="#" class='next' onclick="pageClick('${page.currentPage+1}','${cid }','colPlateInit')">下一页</a>
                            <a href="#" class='last' onclick="pageClick('${page.totalPage}','${cid }','colPlateInit')">末页</a>
                        </c:if>
                        跳至&nbsp;

 						<input type="hidden" value="${cid }" name="id" id="pageCid">
                        <input id="currentPageText" type='text' value='${page.currentPage}' class='allInput w28' name="currentPage" />&nbsp;页&nbsp;
                        <input type="submit" value="GO" class="btn-primary btn-sm" onclick="pageGoClick('colPlateInit')">
                    </form>
                </div>
                
                                </div> --%>
                              
                                
                            </div>
                            
                        </div>
                    </div>
                    <!--End Advanced Tables -->
                </div>
            </div>
                 
				</div>
             <!-- /. PAGE INNER  -->
            </div>
            </div>
         <!-- /. PAGE WRAPPER  -->
    <!-- JS Scripts-->
    <!-- jQuery Js -->
<%--     <script src="<%=path %>/assets/js/jquery-1.10.2.js"></script>
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
    <script src="<%=path %>/assets/js/table.js"></script>
    <script>
           /*  $(document).ready(function () {
               
            });
            
            $("#dataTables-example").tableDnD({
                onDragClass:'highlight',
                onDrop:function(table,row){
                	alert(table)
                	alert(row)
                    console.log('AAA');
                }
          });
             */
            
            
            
            
            var initSeqArray = new Array();
    		var fieIdSeqArray;	
    		$(document).ready(function(){
    			//为table绑定排序事件
    			 $("#dataTables-example").tableDnD({
    		         onDragClass:"myDragClass",
    		         onDrop:function(table,row) {
    		             var rows = table.tBodies[0].rows;
    		             fieIdSeqArray = new Array();
    		             flag = 1;
    		             for (var i=0; i<rows.length; i++) {
    		                fieIdSeqArray.push(rows[i].id);
    		             }
    		             
    	             }
    	   		 });
    		});
    		    		
    		
    		dosaveSeq = function(Url,cid){
    	    /* 	if(fieIdSeqArray != undefined){ */   		    	
    		    		$.ajax({ 
    						 type: "GET",
    						 url: Url+"?cid="+cid+"&sort="+fieIdSeqArray,
    						 dataType: "html",
    						 success: function(data) {
    							 $('#getNewData').html(data);
    							/* if(msg == initSeqArray.length) {
    								alert("字段序列修改成功！");
    							}else {
    								alert("字段序列修改失败");
    							} */
    						 }
    					 });
    		    	/* 
    		    }
    	    	else {
    		    	alert("未发现变更记录");
    		    } */
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
                
                
                delClick = function(id,cid,Url) {
                   $.ajax({
                              type: 'GET',
                              url:  Url+"?id="+id+"&cid="+cid,
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
                
                
                   updateShowClick = function(is_show,id,cid,Url) {
                     $.ajax({
                                type: 'GET',
                                url:  Url+"?id="+id+"&is_show="+is_show+"&cid="+cid,
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

                           showIndiClick = function(id,plate_name,label_id,label_name,type_name,theme_name,theme_id,Url) { 
                        	   var plateName=encodeURI(encodeURI(plate_name)); 
                        	  var labelName=encodeURI(encodeURI(label_name));  
                        	   var typeName=encodeURI(encodeURI(type_name)); 
                        	   var themeName=encodeURI(encodeURI(theme_name)); 
                                $.ajax({
                                           type: 'GET',
                                           url:  Url+"?id="+id+"&plate_name="+plateName+"&label_id="+label_id+"&label_name="+labelName+"&type_name="+typeName+"&theme_name="+themeName+"&theme_id="+theme_id,
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
            function edit(pid,pname,show_type,cid,term){
            	$("#pidEdit").val(pid);
            	$("#pnameEdit").val(pname);
            	$("#show_typeEdit").val(show_type);
            	$("#cidEdit").val(cid);
            	$("#termEdit").val(term);
                	
            }
            
            function backFirstMenu(op){   
                
              	var o=encodeURI(encodeURI(op)); 
                   $.ajax({
                          type: 'GET',
                          url:  'initAnalysisList?op='+o,
                          dataType: "html", 
                     	    async : false,
                      	contentType: false, 
                     	    processData: false,
                          cache:false,
                          success: function(data){
                              $('#getNewData').html(data);
                          },
                          error : function(data){
                          }
                      });  
               } 
            
            backSecondMenu = function(id,typeName,labelName,Url) { 
             	var type=encodeURI(encodeURI(typeName));
             	var label=encodeURI(encodeURI(labelName));
                  $.ajax({
                             type: 'GET',
                             url:  Url+"?label_id="+id+"&type_name="+type+"&label_name="+label,
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
                  
                  
                  backThirdMenu = function(id,typeName,labelName,Url) { 
                
                  	var type=encodeURI(encodeURI(typeName));
                  	var label=encodeURI(encodeURI(labelName));
                       $.ajax({
                                  type: 'GET',
                                  url:  Url+"?theme_id="+id+"&type_name="+type+"&label_name="+label,
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
           
            
        
    </script>
    
   
</body>
</html>
