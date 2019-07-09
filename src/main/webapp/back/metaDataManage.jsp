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
    <title>Dream</title>
	<!-- Bootstrap Styles-->
    <link href="back/assets/css/bootstrap.css" rel="stylesheet" />
     <!-- FontAwesome Styles-->
    <link href="back/assets/css/font-awesome.css" rel="stylesheet" />
        <!-- Custom Styles-->
    <link href="back/assets/css/custom-styles.css" rel="stylesheet" />
     <!-- Google Fonts-->
   <link href='https://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
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
                <a class="navbar-brand" href="index.html">Dream</a>
            </div>

            <ul class="nav navbar-top-links navbar-right">
<!--                 <li class="dropdown"> -->
<!--                     <a class="dropdown-toggle" data-toggle="dropdown" href="#" aria-expanded="false"> -->
<!--                         <i class="fa fa-envelope fa-fw"></i> <i class="fa fa-caret-down"></i> -->
<!--                     </a> -->
<!--                     <ul class="dropdown-menu dropdown-messages"> -->
<!--                         <li> -->
<!--                             <a href="#"> -->
<!--                                 <div> -->
<!--                                     <strong>John Doe</strong> -->
<!--                                     <span class="pull-right text-muted"> -->
<!--                                         <em>Today</em> -->
<!--                                     </span> -->
<!--                                 </div> -->
<!--                                 <div>Lorem Ipsum has been the industry's standard dummy text ever since the 1500s...</div> -->
<!--                             </a> -->
<!--                         </li> -->
<!--                         <li class="divider"></li> -->
<!--                         <li> -->
<!--                             <a href="#"> -->
<!--                                 <div> -->
<!--                                     <strong>John Smith</strong> -->
<!--                                     <span class="pull-right text-muted"> -->
<!--                                         <em>Yesterday</em> -->
<!--                                     </span> -->
<!--                                 </div> -->
<!--                                 <div>Lorem Ipsum has been the industry's standard dummy text ever since an kwilnw...</div> -->
<!--                             </a> -->
<!--                         </li> -->
<!--                         <li class="divider"></li> -->
<!--                         <li> -->
<!--                             <a href="#"> -->
<!--                                 <div> -->
<!--                                     <strong>John Smith</strong> -->
<!--                                     <span class="pull-right text-muted"> -->
<!--                                         <em>Yesterday</em> -->
<!--                                     </span> -->
<!--                                 </div> -->
<!--                                 <div>Lorem Ipsum has been the industry's standard dummy text ever since the...</div> -->
<!--                             </a> -->
<!--                         </li> -->
<!--                         <li class="divider"></li> -->
<!--                         <li> -->
<!--                             <a class="text-center" href="#"> -->
<!--                                 <strong>Read All Messages</strong> -->
<!--                                 <i class="fa fa-angle-right"></i> -->
<!--                             </a> -->
<!--                         </li> -->
<!--                     </ul> -->
<!--                     /.dropdown-messages -->
<!--                 </li> -->
<!--                 /.dropdown -->
<!--                 <li class="dropdown"> -->
<!--                     <a class="dropdown-toggle" data-toggle="dropdown" href="#" aria-expanded="false"> -->
<!--                         <i class="fa fa-tasks fa-fw"></i> <i class="fa fa-caret-down"></i> -->
<!--                     </a> -->
<!--                     <ul class="dropdown-menu dropdown-tasks"> -->
<!--                         <li> -->
<!--                             <a href="#"> -->
<!--                                 <div> -->
<!--                                     <p> -->
<!--                                         <strong>Task 1</strong> -->
<!--                                         <span class="pull-right text-muted">60% Complete</span> -->
<!--                                     </p> -->
<!--                                     <div class="progress progress-striped active"> -->
<!--                                         <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%"> -->
<!--                                             <span class="sr-only">60% Complete (success)</span> -->
<!--                                         </div> -->
<!--                                     </div> -->
<!--                                 </div> -->
<!--                             </a> -->
<!--                         </li> -->
<!--                         <li class="divider"></li> -->
<!--                         <li> -->
<!--                             <a href="#"> -->
<!--                                 <div> -->
<!--                                     <p> -->
<!--                                         <strong>Task 2</strong> -->
<!--                                         <span class="pull-right text-muted">28% Complete</span> -->
<!--                                     </p> -->
<!--                                     <div class="progress progress-striped active"> -->
<!--                                         <div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="28" aria-valuemin="0" aria-valuemax="100" style="width: 28%"> -->
<!--                                             <span class="sr-only">28% Complete</span> -->
<!--                                         </div> -->
<!--                                     </div> -->
<!--                                 </div> -->
<!--                             </a> -->
<!--                         </li> -->
<!--                         <li class="divider"></li> -->
<!--                         <li> -->
<!--                             <a href="#"> -->
<!--                                 <div> -->
<!--                                     <p> -->
<!--                                         <strong>Task 3</strong> -->
<!--                                         <span class="pull-right text-muted">60% Complete</span> -->
<!--                                     </p> -->
<!--                                     <div class="progress progress-striped active"> -->
<!--                                         <div class="progress-bar progress-bar-warning" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%"> -->
<!--                                             <span class="sr-only">60% Complete (warning)</span> -->
<!--                                         </div> -->
<!--                                     </div> -->
<!--                                 </div> -->
<!--                             </a> -->
<!--                         </li> -->
<!--                         <li class="divider"></li> -->
<!--                         <li> -->
<!--                             <a href="#"> -->
<!--                                 <div> -->
<!--                                     <p> -->
<!--                                         <strong>Task 4</strong> -->
<!--                                         <span class="pull-right text-muted">85% Complete</span> -->
<!--                                     </p> -->
<!--                                     <div class="progress progress-striped active"> -->
<!--                                         <div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="85" aria-valuemin="0" aria-valuemax="100" style="width: 85%"> -->
<!--                                             <span class="sr-only">85% Complete (danger)</span> -->
<!--                                         </div> -->
<!--                                     </div> -->
<!--                                 </div> -->
<!--                             </a> -->
<!--                         </li> -->
<!--                         <li class="divider"></li> -->
<!--                         <li> -->
<!--                             <a class="text-center" href="#"> -->
<!--                                 <strong>See All Tasks</strong> -->
<!--                                 <i class="fa fa-angle-right"></i> -->
<!--                             </a> -->
<!--                         </li> -->
<!--                     </ul> -->
<!--                     /.dropdown-tasks -->
<!--                 </li> -->
<!--                 /.dropdown -->
<!--                 <li class="dropdown"> -->
<!--                     <a class="dropdown-toggle" data-toggle="dropdown" href="#" aria-expanded="false"> -->
<!--                         <i class="fa fa-bell fa-fw"></i> <i class="fa fa-caret-down"></i> -->
<!--                     </a> -->
<!--                     <ul class="dropdown-menu dropdown-alerts"> -->
<!--                         <li> -->
<!--                             <a href="#"> -->
<!--                                 <div> -->
<!--                                     <i class="fa fa-comment fa-fw"></i> New Comment -->
<!--                                     <span class="pull-right text-muted small">4 min</span> -->
<!--                                 </div> -->
<!--                             </a> -->
<!--                         </li> -->
<!--                         <li class="divider"></li> -->
<!--                         <li> -->
<!--                             <a href="#"> -->
<!--                                 <div> -->
<!--                                     <i class="fa fa-twitter fa-fw"></i> 3 New Followers -->
<!--                                     <span class="pull-right text-muted small">12 min</span> -->
<!--                                 </div> -->
<!--                             </a> -->
<!--                         </li> -->
<!--                         <li class="divider"></li> -->
<!--                         <li> -->
<!--                             <a href="#"> -->
<!--                                 <div> -->
<!--                                     <i class="fa fa-envelope fa-fw"></i> Message Sent -->
<!--                                     <span class="pull-right text-muted small">4 min</span> -->
<!--                                 </div> -->
<!--                             </a> -->
<!--                         </li> -->
<!--                         <li class="divider"></li> -->
<!--                         <li> -->
<!--                             <a href="#"> -->
<!--                                 <div> -->
<!--                                     <i class="fa fa-tasks fa-fw"></i> New Task -->
<!--                                     <span class="pull-right text-muted small">4 min</span> -->
<!--                                 </div> -->
<!--                             </a> -->
<!--                         </li> -->
<!--                         <li class="divider"></li> -->
<!--                         <li> -->
<!--                             <a href="#"> -->
<!--                                 <div> -->
<!--                                     <i class="fa fa-upload fa-fw"></i> Server Rebooted -->
<!--                                     <span class="pull-right text-muted small">4 min</span> -->
<!--                                 </div> -->
<!--                             </a> -->
<!--                         </li> -->
<!--                         <li class="divider"></li> -->
<!--                         <li> -->
<!--                             <a class="text-center" href="#"> -->
<!--                                 <strong>See All Alerts</strong> -->
<!--                                 <i class="fa fa-angle-right"></i> -->
<!--                             </a> -->
<!--                         </li> -->
<!--                     </ul> -->
<!--                     /.dropdown-alerts -->
<!--                 </li> -->
                <!-- /.dropdown -->
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#" aria-expanded="false">
                        <i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-user">
<!--                         <li><a href="#"><i class="fa fa-user fa-fw"></i> User Profile</a> -->
<!--                         </li> -->
<!--                         <li><a href="#"><i class="fa fa-gear fa-fw"></i> Settings</a> -->
<!--                         </li> -->
                        <li class="divider"></li>
                        <li><a href="#"><i class="fa fa-sign-out fa-fw"></i> Logout</a>
                        </li>
                    </ul>
                    <!-- /.dropdown-user -->
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
                        <a class="active-menu" href="toIndex"><i class="fa fa-dashboard"></i>首页</a>
                    </li>
                    <li>
                        <a href="toMetaDataManage"><i class="fa fa-list-alt"></i>元数据管理</a>
                        <!-- <ul class="nav nav-second-level">
                            <li>
                                <a href="#">指标设计</a>
                            </li>
                            <li>
                                <a href="#">指标关联关系维护</a>
                            </li>
                        </ul> -->
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-bar-chart-o"></i>数据管理<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a href="listIndexManage">指标数据维护</a>
                            </li>
<!--                             <li> -->
<!--                                 <a href="dataReview.html">数据审核</a> -->
<!--                             </li> -->
<!--                             <li> -->
<!--                                 <a href="reportDataManage.html">报告、报表数据管理</a> -->
<!--                             </li> -->
                        </ul>
                    </li>
                    <li>
                         <a href="init"><i class="fa fa-quote-left"></i>栏目管理</a>
<!--                         <ul class="nav nav-second-level"> -->
<!--                             <li> -->
<!--                                 <a href="columnManage.html">栏目维护</a> -->
<!--                             </li> -->
<!--                             <li> -->
<!--                                 <a href="columnPowerManage.html">栏目权限维护</a> -->
<!--                             </li> -->
<!--                             <li> -->
<!--                                 <a href="columnContentManage.html">内容配置</a> -->
<!--                             </li> -->
<!--                             <li> -->
<!--                                 <a href="columnContPowerManage.html">内容权限设置</a> -->
<!--                             </li> -->
<!--                         </ul> -->
                    </li>
                    <li>
                        <a href="specialInit"><i class="fa fa-quote-left"></i>专题管理</a>
<!--                         <ul class="nav nav-second-level"> -->
<!--                             <li> -->
<!--                                 <a href="specialManage.html">专题维护</a> -->
<!--                             </li> -->
<!--                             <li> -->
<!--                                 <a href="specialPowerManage.html">专题权限维护</a> -->
<!--                             </li> -->
<!--                             <li> -->
<!--                                 <a href="specialContentManage.html">内容配置</a> -->
<!--                             </li> -->
<!--                             <li> -->
<!--                                 <a href="specialContPowerManage.html">内容权限设置</a> -->
<!--                             </li> -->
<!--                         </ul> -->
                    </li>
<!--                     <li> -->
<!--                         <a href="#"><i class="fa fa-laptop"></i>发布管理<span class="fa arrow"></span></a> -->
<!--                         <ul class="nav nav-second-level"> -->
<!--                             <li> -->
<!--                                 <a href="columnPublish.html">栏目发布</a> -->
<!--                             </li> -->
<!--                             <li> -->
<!--                                 <a href="publishedManage.html">已发布内容管理</a> -->
<!--                             </li> -->
<!--                         </ul> -->
<!--                     </li> -->
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

        </nav>
        <!-- /. NAV SIDE  -->
        <div id="page-wrapper" >
            <div id="page-inner">
			 <div class="row">
                    <div class="col-md-12">
                        <h1 class="page-header">
                            元数据管理 <small>指标设计/关联关系维护</small>
                        </h1>
                        
                    
                   <div class="row">
                <div class="col-md-12">
                    <!-- Advanced Tables -->
                    <div class="panel panel-default">
                        <div class="panel-heading">
                        相关指标
                        </div>
                        <div class="panel-body">
                     
  
  
  <div class="btns col-md-2">
      <div class="btn btn-info" data-toggle="modal" data-target="#myAddModal"><i class="fa fa-plus"></i>添加</div>
    </div>  
<!--     <form class="form-inline my-2 my-lg-0" style="float:right"> -->
<!--       <input class="form-control mr-sm-2" type="search" placeholder="PMI指数(全国)" aria-label="Search"> -->
<!--       <button class="btn btn-success my-2 my-sm-0" type="submit">搜索</button> -->
<!--     </form> -->
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                        <tr>
                                            <th>id</th>
                                            <th>栏目id</th>
                                            <th>相关指标id</th>
                                            <th>相关指标名字</th>
                                            <th>是否展示</th>
                                            <th>操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
     <c:forEach items="${IndiCorrelativeList}" var="c" varStatus="st">
        <tr>
            <td>${c.id}</td>
            <td>${c.column_id}</td>
            <td>${c.indi_id}</td>
            <td>${c.indi_name}</td>
            <td>${c.is_show}</td>
<%-- 			onclick='updateCorrelativeIndi("${c.id}","${c.column_id}","${c.indi_id}", "${c.indi_name}")' --%>
            <td ><div class="btn btn-warning btn-sm" style="margin-right:3px" data-toggle="modal" data-target="#myEditModal" onclick='updateCorrelativeIndi("${c.id}","${c.column_id}","${c.indi_id}", "${c.indi_name}", "${c.is_show}")'><i class="fa fa-edit"></i> 修改</div><div class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i> <a href="#" id="deleteIndi" onclick="deleteCorrelativeIndi(${c.id})">删除</a></div>
            	
				<div class="btn-group">
				    <button type="button" class="btn btn-info dropdown-toggle btn-sm"  data-toggle="dropdown">
				    <c:if test="${c.is_show==0 }"> &nbsp;&nbsp;&nbsp; 展示</c:if>
				    <c:if test="${c.is_show==1 }">不展示</c:if>	
				    <span class="caret"></span>         
				    </button>
				    <ul class="dropdown-menu" role="menu">
				    <c:if test="${c.is_show==1 }">
				      <li>
				         <a href="#" id="perShow" onclick="perShowCorreIndi(${c.id})">展示</a>            
				      </li>
				    </c:if>
				    <c:if test="${c.is_show==0 }">
				      <li role="presentation">
				         <a href="#" id="noPerShow" onclick="noPerShowCorreIndi(${c.id})">不展示</a>             
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
			 <form action="updateCorrelativeIndi" method="post"> 
			<div class="modal-body">
				<input type="hidden" class="form-control" id="IndiId" value="" name="Id">
<!-- 				<span class="text-info">栏目id</span><input type="text" class="form-control" id="IndiCode" value="" name="ColumnId"> -->
				<span class="text-info">栏目</span>
				     <select class="form-control" id="IndiCode" name="ColumnId"> 
						<c:forEach items="${InitAnalysisManageList}" var="c1" varStatus="st">
							        <option value="${c1.id}" selected>${c1.theme_name}</option>    
						</c:forEach>
					</select>
<!-- 				<span class="text-info">相关指标id</span> -->
				<input type="hidden" class="form-control" id="IndiName" value="" name="CorrelativeIndiId">
<!-- 				<span class="text-info">相关指标名称</span><input type="text" class="form-control" id="IndiFreqCode" value="" name="CorrelativeIndiName"> -->
				<span class="text-info">相关指标名称</span>
					<select class="form-control" id="IndiFreqCode1" name="CorrelativeIndiCode"> 
						<c:forEach items="${InitIndexManageList}" var="c1" varStatus="st">
							        <option value="${c1.indi_code}" selected>${c1.indi_name}</option> 
							       
						</c:forEach>
						
					</select>
<!-- 				<span class="text-info">是否展示</span> -->
				<input type="hidden" class="form-control" id="IndiIsshow" value="" name="indiIsshow">
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭
				</button>
				
					<button type="submit" class="btn btn-primary" onclick="updatebutton()">
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
			 <form action="addCorrelativeIndiData" method="post"> 
			<div class="modal-body">
				<span class="text-info">栏目</span>
					<select class="form-control" id="SelectColumn" name="addCorrelativeCoumnId"> 
						<c:forEach items="${InitAnalysisManageList}" var="c1" varStatus="st">
							        <option value="${c1.id}" selected>${c1.theme_name}</option>    
						</c:forEach>
					</select>
				<span class="text-info">相关指标</span>
					<select class="form-control" id="SelectIndi" name="addCorrelativeIndiCode"> 
						<c:forEach items="${InitIndexManageList}" var="c1" varStatus="st">
							        <option value="${c1.indi_code}" selected>${c1.indi_name}</option> 
							       
						</c:forEach>
						
					</select>
				
<!-- 				<span class="text-info">栏目id</span><input type="text" class="form-control" id="addCorrelativeCoumnId" value="" name="addCorrelativeCoumnId"> -->
<!-- 				<span class="text-info">相关指标id</span><input type="text" class="form-control" id="addCorrelativeIndiId" value="" name="addCorrelativeIndiId"> -->
<!-- 				<span class="text-info">相关指标名称</span><input type="text" class="form-control" id="addCorrelativeIndiName" value="" name="addCorrelativeIndiName"> -->
<!-- 				<span class="text-info">是否展示</span><input type="text" class="form-control" id="addCorrelativeIndiIsshow" value="" name="addCorrelativeIndiIsshow"> -->
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭
				</button>
				
					<button type="submit" class="btn btn-primary" onclick="addbutton()">
						提交
					</button>
				
			</div>
			 </form> 
			
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>


       <div class="row">
       
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
                    
<!--          <div style="text-align:center"> -->
<!-- 	        <a href="?start=0">首  页</a> -->
<%-- 	        <a href="?start=${page.start-page.count}">上一页</a> --%>
<%-- 	        <a href="?start=${page.start+page.count}">下一页</a> --%>
<%-- 	        <a href="?start=${page.last}">末  页</a> --%>
<!--     	</div> -->
                    
                    
                    
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
    <script src="back/assets/js/jquery-1.10.2.js"></script>
      <!-- Bootstrap Js -->
    <script src="back/assets/js/bootstrap.min.js"></script>
    <!-- Metis Menu Js -->
    <script src="back/assets/js/jquery.metisMenu.js"></script>
      <!-- Custom Js -->
    <script src="back/assets/js/custom-scripts.js"></script>
    
     <script src="assets/js/bootstrap-switch.min.js"></script>
   <script src="assets/js/dataTables/jquery.dataTables.js"></script>
    <script src="assets/js/dataTables/dataTables.bootstrap.js"></script>   
    <script src="assets/js/bootstrap-order.min.js"></script>
    <script type="text/javascript">
	//id,indi_code,indi_name,freq_code,indi_value,time_point,status,source
		function updateCorrelativeIndi(id,column_id,indi_id,indi_name,is_show){
			
 			 $("#IndiId").val(id);
			$("#IndiCode").val(column_id);
			$("#IndiName").val(indi_id);
			$("#IndiFreqCode").val(indi_name);
			$("#IndiIsshow").val(is_show);
		}
		function updatebutton(){
			alert("更新成功！");
		}
		
		
		/* 删除相关联指标 */
		function deleteCorrelativeIndi(id){
			if(confirm('确实要删除该指标吗?')) {
				$.post("<%=basePath%>CorrelativeDelete.action",{"id":id},function(data){
					alert("指标删除成功！");
					window.location.reload();
				});
			}
		}
		
		function addbutton(){
			alert("相关指标添加成功！");
		}
		
		function f()
		{
			
			alert("初始化");
			var form=document.getElementById("formAdd");
        	
        	form1.action="searchIndiCorre";
        	form1.submit();
		}
		
		
		/* 展示指标*/
		function perShowCorreIndi(id){
			
				$.post("<%=basePath%>per_show_correIndi.action",{"id":id},function(data){
					alert("允许指标展示！");
					window.location.reload();
				});
			
		}
		
		function noPerShowCorreIndi(id){
			
			$.post("<%=basePath%>no_per_show_correIndi.action",{"id":id},function(data){
				alert("不允许指标展示！");
				window.location.reload();
			});
		
	}
		
		
   </script>
</body>

</html>
