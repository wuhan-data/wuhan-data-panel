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
    
     <link href="<%=path %>/assets/css/bootstrap-colorpicker.css" rel="stylesheet" />
   


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
        .showColor{
        width:20px;
        height:20px;
        float:left;}
        
        #addElement{
        margin-bottom:10px;
        }
        
          td,th{
        text-align:center;
        }
       
       #tips{
        margin-top:0px;
        display: none;
        width: 558px;
        height:150px;
        overflow:auto;
        border:1px black;
       }
       #chooseColor,#chooseColor_edit{
        width: 20px;
        height: 20px;
        border: 1px solid #fff;
        border-radius: 4px;
        background-color:#000;
        text-indent: 20px;;
       }
       .search_indi_id,.indi_old_name,indi_new_name{
        text-align:center;
        margin:auto;
        word-wrap:break-word;  
    	word-break:break-all;
    	overflow: auto; 
       width:200px;
       }
       
       li{
       list-style:none;}
       
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
   
        <div id="page-wrapper" >
            <div id="page-inner">
			 <div class="row">
                    <div class="col-md-12">
                        <h1 class="page-header">
                            栏目管理 
                            <div><small><a href="#" onclick="backFirstMenu('${type_name}')">${type_name}(${ label_name})</a> > <a href="#" onclick="backSecondMenu('${label_id }','${type_name}','${label_name}','analysisSecondInit')">${theme_name}</a> > <a href="#" onclick="backThirdMenu('${theme_id }','${label_id }','${type_name}','${label_name }','colPlateInit')">${plate_name }</a> </small></div>
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
                                            <th width="5%">板块</th>
                                            <th>指标id</div></th>
                                            <th>指标名称</th>
                                            <th>指标别名</th>
                                            <th width="5%">展现形式</th>
                                            <th width="10%">展示颜色</th>
                                            <th>操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
     <c:forEach items="${indicolumnByPage}" var="c" varStatus="st">
        <tr>
            <td width="5%">${c.plate_id}</td>
            <td ><div class="search_indi_id">${c.search_indi_id }</div></td>
            <td ><div class="indi_old_name">${c.indi_old_name}</div></td>
            <td ><div class="indi_new_name">${c.indi_new_name}</div></td>
            <td width="5%">
            <c:if test="${c.show_type==''}">
     			空
            </c:if>
             <c:if test="${c.show_type!=''}">
     			${c.show_type }
            </c:if>
             </td>
            <td width="10%">
            <c:if test="${c.show_color==''}">
            空
            </c:if>
             <c:if test="${c.show_color!=''}">
            ${c.show_color }<div style="background-color:${c.show_color}" class="showColor"></div>
            </c:if>     
            </td>
            <td >
<%-- <div class="btn btn-warning btn-sm" style="margin-right:3px" data-toggle="modal" data-target="#myEditModal" onclick="edit(${c.theme_name})">
<i class="fa fa-edit"></i>修改
</div>
 --%>

<div class="btn btn-warning btn-sm" style="margin-right:3px" data-toggle="modal" data-target="#myEditModal" onclick="edit('${c.indi_id}','${c.indi_new_name}','${c.show_color }','${c.show_type }')">
<i class="fa fa-edit"></i>修改
</div>  
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
         <a href="#" id="perShow" onclick="updateShowClick('0','${c.indi_id }','${c.plate_id}','plateIndiUpdateShow')">展示</a>            
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
	<form class="" id="editForm" method="post" accept-charset="UTF-8" action="">
			<div class="modal-body">		

   			<input type="hidden" value="" name="indi_id_edit" id="indi_id_edit"/>

			<div class="form-group" id="indi_name_group">
                   <label>指标别名</label>                  
                   <input class="form-control" type="text" placeholder="请输入指标别名" id="indi_new_name_edit" name="indi_new_name">
             </div>
             
              <div class="form-group">
                   <label>展示类型</label>
                   <select class="form-control" name="show_type" onchange="f1()" placeholder="" id="show_type_edit">
                    <option value="" id="nullOption">请选择展示类型</option>   
                    <option value="line" id="lineOption">line</option>
                    <option value="bar" id="barOPtion">bar</option>
                   </select>
              
                   <!-- <input class="form-control" type="text" placeholder="请输入展示类型" name="show_type"> -->
             </div>
              <div class="form-group">
                   <label>展示颜色<input id="chooseColor_edit" type="text" value="" readonly/></label>                                  
                   <input id="color_edit" class="form-control"  type="text" placeholder="请输入展示颜色"  name="show_color"> 
                  
             </div>
  			 <input type="hidden" value="${pid}" name="pid_edit"/>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭
				</button>
				<button type="submit" class="btn btn-primary" onclick="editClick('plateIndiUpdate')">
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
			
			
              <div class="form-group" id="indi_name_group">
                   <label>指标名称</label>                  
                   <input class="form-control" type="text" placeholder="请输入指标名称（xxx::xxx::xxx）" name="indi_old_name" id="indi_old_name" autocomplete="off" >
             </div>
             
              <div id="tips" style=""></div>
              
             <div class="form-group" id="indi_name_group">
                   <label>指标别名</label>                  
                   <input class="form-control" type="text" placeholder="请输入指标别名" name="indi_new_name">
             </div>
             
              <div class="form-group">
                   <label>展示类型</label>
                   <select class="form-control" name="show_type" onchange="f1()" placeholder="">
                    <option value="" selected>请选择展示类型</option>   
                    <option value="line">line</option>
                    <option value="bar">bar</option>
                   </select>
              
                   <!-- <input class="form-control" type="text" placeholder="请输入展示类型" name="show_type"> -->
             </div>
              <div class="form-group">
                   <label>展示颜色<input id="chooseColor" type="text" value="" readonly/></label>                                  
                   <input id="color" class="form-control"  type="text" placeholder="请输入展示颜色" name="show_color"> 
                  
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

<%-- 
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
     <script src="<%=path %>/assets/js/bootstrap-colorpicker.js"></script>
    <script src="<%=path %>/assets/js/table.js"></script>
    
    <script>
    
    var initSeqArray = new Array();
	var fieIdSeqArray;	
            $(document).ready(function () {
            	 // 基本实例化:
                $('#chooseColor').colorpicker();
                $('#chooseColor_edit').colorpicker();
                $('#chooseColor').on('change', function (event) {
                    $('#chooseColor').css('background-color', event.color.toString()).val('');
                    $("#color").val(event.color.toString());
                });
                
               $('#chooseColor_edit').on('change', function (event) {
                    $('#chooseColor_edit').css('background-color', event.color.toString()).val('');
                    $("#color_edit").val(event.color.toString());
                });

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
            
            
            var textElment = document.getElementById("indi_old_name");
            var div = document.getElementById("tips");
            $(function(){  
            	  $('#indi_old_name').bind('input propertychange', function() {  
            		  console.log("1");
            		  if(textElment.value!=""){
            			  div.style.display="block";
            		  }else{
            			  div.style.display="none";
            		  }
          	      
            		  var content=encodeURI(encodeURI($(this).val()));
            	      $.ajax({
                              type: 'GET',
                              url:  'searchAddIndi?field='+content,
                              dataType: "html",
                         	  async : false,
                          	  contentType: false, //不设置内容类型
                         	  processData: false,
                              cache:false,
                              success: function(data){
                        		  /* $('#tips').html($(this).val().length + ' characters');  */ 
                        		  $('#tips').html(data);  
                              },
                              error : function(data){
                              }
                          }); 
            	      
            	  });
            	})
            	
            	
/*           textElment.onkeyup=function(){
            	console.log("dff")
 				//获取用户输入的值
 				var text = textElment.value;
 				//如果文本框中没有值，则下拉框被隐藏，不显示
 				if(text==""){
 					div.style.display="none";
 					return;
 				}else{
 					div.style.display="block";
 				}
            }  */
            
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
       	
         
            function edit(indi_id,indi_new_name,show_color,show_type){
            	$("#indi_id_edit").val(indi_id);
            	$("#indi_new_name_edit").val(indi_new_name);
                $('#chooseColor_edit').css('background-color',show_color);
                $("#color_edit").val(show_color);
                var opts = document.getElementById("show_type_edit");
                for(var i=0;i<opts.options.length;i++){         
                	if(show_type==opts.options[i].value){
                		opts.options[i].selected='selected';
                		break;
                	}
                }             	
            }
          
            
            function resetContent(content){
            	var indi_old_name = document.getElementById("indi_old_name");           	 
            	indi_old_name.value=content;
            	div.style.display="none";
            	
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
            
            backSecondMenu = function(label_id,typeName,labelName,Url) { 
            	alert(label_id)
            	var type=encodeURI(encodeURI(typeName));
              	var label=encodeURI(encodeURI(labelName));
                   $.ajax({
                              type: 'GET',
                              url:  Url+"?label_id="+label_id+"&type_name="+type+"&label_name="+label,
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
                  
                  
                  backThirdMenu = function(id,label_id,typeName,labelName,Url) { 
                  	var type=encodeURI(encodeURI(typeName));
                  	var label=encodeURI(encodeURI(labelName));
                       $.ajax({
                                  type: 'GET',
                                  url:  Url+"?theme_id="+id+"&label_id="+label_id+"&type_name="+type+"&label_name="+label,
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
