<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>WUHANDATA</title>
    <!-- Bootstrap Styles-->
    <link href="back/assets/css/bootstrap.css" rel="stylesheet" />
    <!-- FontAwesome Styles-->
    <link href="back/assets/css/font-awesome.css" rel="stylesheet" />
    <!-- Morris Chart Styles-->
    <link href="back/assets/js/morris/morris-0.4.3.min.css" rel="stylesheet" />
    <!-- Custom Styles-->
    <link href="back/assets/css/custom-styles.css" rel="stylesheet" />
    
    <link href="assets/css/bootstrap.css" rel="stylesheet" />
    <!-- FontAwesome Styles-->
    <link href="assets/css/font-awesome.css" rel="stylesheet" />
    <!-- Morris Chart Styles-->
    <link href="assets/js/morris/morris-0.4.3.min.css" rel="stylesheet" />
    <!-- Custom Styles-->
    <link href="assets/css/custom-styles.css" rel="stylesheet" />
    <!-- Google Fonts-->
    <link href='https://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
    
   
    <!-- Bootstrap Js -->
    <script src="assets/js/bootstrap.min.js"></script>
    <!-- Metis Menu Js -->
    <script src="assets/js/jquery.metisMenu.js"></script>
    <!-- Morris Chart Js -->
    <script src="assets/js/morris/raphael-2.1.0.min.js"></script>
    <script src="assets/js/morris/morris.js"></script>
    <!-- Custom Js -->
    <script src="assets/js/custom-scripts.js"></script>
    
    
    <script src="back/assets/laydate/laydate.js"></script> 
   	<script src="back/assets/js/jquery-1.10.2.js"></script> 
<!--    	<script language="javascript" src="back/assets/js/chainSelect.js"></script> -->
	<script type="text/javascript" src="back/assets/js/jquery.tabletojson.js"></script>  
	<title>指标数据导出到Excel</title>
	<script type="text/javascript">  
  
    function exportExcel(fileName,tableId){  
        var table = $("#"+tableId).tableToJSON();  
        console.log(table);  
        var json = JSON.stringify(table);  
        var nodes = $("#"+tableId+" thead tr").children();  
        var headers = "";  
        $.each(nodes,function(i,item){  
            headers += item.innerHTML+",";  
        })  
       //调用post方法       
			post('<%=basePath%>ecxelTest.action', {fileName :fileName,headers:headers,json:json});
        
    }
	function post(url, params) {
		var temp = document.createElement("form");
		temp.action = url;
		temp.method = "post";
		temp.style.display = "none";
		for (var x in params) {
			var opt = document.createElement("input");
			opt.name = x;
			opt.value = params[x];
			temp.appendChild(opt);
		}
		document.body.appendChild(temp);
		temp.submit();
		return temp;
	}  

  
</script> 

<style type="text/css">
	.com-sel {
    line-height: 2rem;
    cursor: pointer;        /*鼠标上移变成小手*/
}

.com-opt {
    padding-right: 1.8rem;
    color: #afbac0;
    font-size: 1.6rem;
    border: none;
    outline: none;
    /*去掉默认的下拉三角*/
    appearance:none;  
    -moz-appearance:none;  
    -webkit-appearance:none;
    /*添加下拉三角图标*/
/*     background: url("../img/task5-2_07.jpg") no-repeat right center transparent; */
}
	

</style>
    
</head>

<body >
    <div id="wrapper">
       
        <!-- /. NAV SIDE  -->
        <div id="page-wrapper">
            <div id="page-inner">


                <div class="row"  style="margin-bottom:10px">
                    <div class="col-md-12">
                        <h1 class="page-header">
                            指标<small>数据导出</small>
                        </h1>
                    </div>
                </div>
                
                 <div class="row">
                <div class="col-md-12">
                    <!-- Advanced Tables -->
			   <div class="panel panel-default">
                        <div class="panel-heading">
                             指标
                        </div>
                        <div class="panel-body">
                
                
<!--                 导出到excel style="float:right"-->
		 <div>
		  <form class="form-inline my-2 my-lg-0"  method="post" id="formSearch">
		      <input class="form-control mr-sm-2" type="search" placeholder="请输入指标搜索关键字" value="${keyword}" aria-label="Search" id="searchKeyWord">
		      <button class="btn btn-success my-2 my-sm-0" onclick="search('dbToEcxel')">搜索</button>
    	</form>
    	</div>
                
         <div style="margin-top:10px;margin-bottom:10px">
			<span class="indiName" style="margin-right:5px"><img src="assets/img/pfeil.gif" alt="" />指标名称：
			   <select id="indiName" style="width:100px">
					<option value="" >
						请选择指标
					</option>
					<c:forEach items="${indexList}" var="in">
						<option value="${in.indi_name}">${in.indi_name}</option>
					</c:forEach>
				</select> 
			</span>
			<span class="indiSource"> <img src="assets/img/pfeil.gif" alt="" />
				指标来源： <select text-align-last:center style="width:110px"></select> </span>
			<span class="freqCode"> <img src="assets/img/pfeil.gif" alt="" />
				月/季度： <select style="width:70px" text-align-last:center></select> </span>
			<span class="timePoint"> <img src="assets/img/pfeil.gif" alt="" />
				时点： <select style="width:70px" text-align-last:center></select> </span>
				
				
			<span class="startTime"> <img src="assets/img/pfeil.gif" alt="" />
				开始日期： <select  style="width:100px" text-align-last:center></select> </span>
			<span class="endTime"> <img src="assets/img/pfeil.gif" alt="" />
				结束日期： <select style="width:100px" text-align-last:center></select> </span>
				
				
				<input type="button" value="删除" id="delectBu" style="float:right">
		</div>
		
<!-- 		表格 -->
		 <div class="table-responsive">
		 	<table style="width:100%;border:1px white solid" class="table table-striped table-bordered table-hover dd" id="toExcel">
    			<thead>
    			<tr bgcolor="#4F81BD"style="color: #fff;">
<%--     			<%=columns[0]%> --%>
        			<td style="text-align: center" >指标代码</td>
        			<td style="text-align: center" >指标名称</td>
        			<td style="text-align: center" >日期代码</td>
        			<td style="text-align: center" >空间维度码</td>
        			<td style="text-align: center" >区域代码</td>
        			<td style="text-align: center" >区域名称</td>
        			<td style="text-align: center" >频度代码</td>
        			<td style="text-align: center" >时点</td>
        			<td style="text-align: center" >指标值</td>
        			<td style="text-align: center" >操作</td>
    			</tr>
    			</thead>
			</table>
			

      <button class="btn btn-success my-2 my-sm-0" id="exportE" onclick="exportExcel('指标数据','toExcel')" style="float:right;margin-top:10px">导出到excel表格</button>
		 </div>
                
                  </div>
                              
                                
                            </div>
                            
                        </div>
                    </div>
                
                
                <!-- /. ROW  -->

                <!-- /. ROW  -->
            </div>
            <!-- /. PAGE INNER  -->
        </div>
        <!-- /. PAGE WRAPPER  -->
    </div>
    <!-- /. WRAPPER  -->
    <!-- JS Scripts-->
    <!-- jQuery Js -->
   
	
	<script>
$(document).ready(function(){
	//找到五个下拉框
	var indiNameSelect = $('.indiName').children('select');
	var indiSourceSelect = $('.indiSource').children('select');
	var freqCodeSelect = $(".freqCode").children("select");
	var timePointSelect = $(".timePoint").children("select");
	var startTimeSelect = $(".startTime").children("select");
	var endTimeSelect = $(".endTime").children("select");
	var delectBu=$("#delectBu");
	
	indiSourceSelect.parent().hide();
	freqCodeSelect.parent().hide();
	timePointSelect.parent().hide();
	startTimeSelect.parent().hide();
	endTimeSelect.parent().hide();
	delectBu.hide();
	//给五个下拉框注册事件
	/**
	 * 第一个下拉框change事件
	 */
	indiNameSelect.change(function(){
// 		alert("进入indiNameSelect");
		//只要第一个下拉框有变化则隐藏第三个和第四个下拉框
		freqCodeSelect.parent().hide();
		startTimeSelect.parent().hide();
		endTimeSelect.parent().hide();
		delectBu.hide();
		//隐藏汽车图片 attr：先清空上次src图片路径避免下一次先显示一次
//		carimg.hide().attr("src","");
		//1、找到下拉框的值
		var indiNameValue = $("#indiName option:selected").text();
		//2、如果下拉框所选值不为空，则将该值传送给服务器
		if(indiNameValue != "")
		{
			/**
			 * 如果缓存为空 则请求服务器端数据
			 */
		    	$.post("getIndiSourceByIndiName",{indiName:indiNameValue},function(data){
					//接收服务器返回汽指标来源---
					// 如果返回的数据不为空
					if(data.length !=0) 
					{
						//解析返回的指标来源数据，并填充到指标来源的下拉框中
						//先清空上次请求数据	
						indiSourceSelect.html("");
						freqCodeSelect.html("");
						startTimeSelect.html("");
						endTimeSelect.html("");
						$("<option value=''>" + "请选择"+ "</option>").appendTo(indiSourceSelect);
						for(var i = 0;i<data.length;i++)
						{
							$("<option value='" + data[i]+ "'>" + data[i]+ "</option>").appendTo(indiSourceSelect);
						}
						//让第二个下拉框显示
						indiSourceSelect.parent().show();
						//让第一个后面的指示图片显示出来
						indiNameSelect.next().hide();
					}
					else
					{
						alert(indiNameValue+"为空");
					}
					/*
					 * 将从服务器中获取的数据缓存起来
					 * data("缓存名称","缓存的对象")
					 */
					indiNameSelect.data(indiNameValue,data);
					//alert("缓存了数据……");
					
				},"json");

			
		}else
		{
			//如果下拉框所选的值为空，则要隐藏第二个下拉框的span要隐藏以来，
			indiSourceSelect.parent().hide();
			//第一个下拉框后面指示的图片也要隐藏起来
			indiSourceSelect.next().hide();		
		}	
	})
	
    /**
     *  第二个下拉框change事件
     */
	indiSourceSelect.change(function() {
		
		//隐藏汽车图片 attr：先清空上次src图片路径避免下一次先显示一次
//		carimg.hide().attr("src","");
		var indiNameValue = indiNameSelect.val();
		
		var indiSourceValue = $(this).val();
	
		if(indiSourceValue != ""&&indiNameValue != "")
		{
			//如果没有缓存
				  $.post("getIndiFreqCode",{indiName:indiNameValue,indiSource:indiSourceValue},function(data){
					if(data.length !=0) 
					{
						freqCodeSelect.html("");
						startTimeSelect.html("");
						endTimeSelect.html("");
						$("<option value=''>" + "请选择"+ "</option>").appendTo(freqCodeSelect);
						for(var i = 0;i<data.length;i++)
						{
							$("<option value='" + data[i]+ "'>" + data[i] + "</option>").appendTo(freqCodeSelect);
						}
						freqCodeSelect.parent().show();
						freqCodeSelect.next().hide();
					}
					else
					{
						alert(indiSourceValue+"为空");
					}
					/**
					 * 缓存数据
					 */
					indiSourceSelect.data(indiSourceValue,data);
					//alert("缓存了数据……");
				},"json");		
			
		}else
		{
			freqCodeSelect.parent().hide();
			freqCodeSelect.next().hide();
			
		}
	})
	/**
	 * 第三个下拉框事件
	 */
	freqCodeSelect.change(function(){
		//获取三个下拉框中的值
		var indiNameValue = indiNameSelect.val();
		var indiSourceValue = indiSourceSelect.val();
		var freqCode = $(this).val();
		if(indiSourceValue != ""&&indiNameValue != ""&&freqCode!="")
		{
				  $.post("getIndiStartTime",{indiName:indiNameValue,indiSource:indiSourceValue,freqCode:freqCode},function(data){
					if(data.length !=0) 
					{
						startTimeSelect.html("");
						endTimeSelect.html("");
						$("<option value=''>" + "请选择"+ "</option>").appendTo(startTimeSelect);
						for(var i = 0;i<data.length;i++)
						{
							$("<option value='" + data[i]+ "'>" + data[i] + "</option>").appendTo(startTimeSelect);
						}
						startTimeSelect.parent().show();
						startTimeSelect.next().hide();
					}
					else
					{
						alert("条件值为空");
					}
					/**
					 * 缓存数据
					 */
					 freqCodeSelect.data(freqCode,data);
					//alert("缓存了数据……");
				},"json");		
			
		}else
		{
			startTimeSelect.parent().hide();
			startTimeSelect.next().hide();
		}
	})
	
	
	/**
	 * 第四个下拉框事件
	 */
	timePointSelect.change(function(){
		//获取四个下拉框中的值
		var indiNameValue = indiNameSelect.val();
		var indiSourceValue = indiSourceSelect.val();
		var freqCode = freqCodeSelect.val();
		var timePoint = $(this).val();
		if(indiSourceValue != ""&&indiNameValue != ""&&freqCode!=""&&timePoint!="")
		{
				  $.post("getIndiStartTime",{indiName:indiNameValue,indiSource:indiSourceValue,freqCode:freqCode},function(data){
					if(data.length !=0) 
					{
						startTimeSelect.html("");
						endTimeSelect.html("");
						$("<option value=''>" + "请选择"+ "</option>").appendTo(startTimeSelect);
						for(var i = 0;i<data.length;i++)
						{
							$("<option value='" + data[i]+ "'>" + data[i] + "</option>").appendTo(startTimeSelect);
						}
						startTimeSelect.parent().show();
						startTimeSelect.next().hide();
					}
					else
					{
						alert("条件值为空");
					}
					/**
					 * 缓存数据
					 */
					 freqCodeSelect.data(freqCode,data);
					//alert("缓存了数据……");
				},"json");		
			
		}else
		{
			startTimeSelect.parent().hide();
			startTimeSelect.next().hide();
		}
	})
	
	
	/**
	 * 第五个下拉框事件
	 */
	 startTimeSelect.change(function(){
		//获取四个下拉框中的值
		var indiNameValue = indiNameSelect.val();
		var indiSourceValue = indiSourceSelect.val();
		var freqCode = freqCodeSelect.val();
		var startTime = $(this).val();
		
		if(indiSourceValue != ""&&indiNameValue != ""&&freqCode!=""&&startTime!="")
		{
				  $.post("getIndiEndTime",{indiName:indiNameValue,indiSource:indiSourceValue,freqCode:freqCode,startTime:startTime},function(data){
					if(data.length !=0) 
					{
						endTimeSelect.html("");
						$("<option value=''>" + "请选择"+ "</option>").appendTo(endTimeSelect);
						for(var i = 0;i<data.length;i++)
						{
							$("<option value='" + data[i]+ "'>" + data[i] + "</option>").appendTo(endTimeSelect);
						}
						endTimeSelect.parent().show();
						endTimeSelect.next().hide();
					}
					else
					{
						alert("条件值为空");
					}
					/**
					 * 缓存数据
					 */
					 startTimeSelect.data(startTime,data);
					//alert("缓存了数据……");
				},"json");		
			
		}else
		{
			endTimeSelect.parent().hide();
			endTimeSelect.next().hide();
		}
		
		

	})
	
	/**
	 * 第六个下拉框事件
	 */
	 endTimeSelect.change(function(){
		//获取六个下拉框中的值，便于拼接图片名称
		var indiName = indiNameSelect.val();
		var indiSource = indiSourceSelect.val();
		var freqCode = freqCodeSelect.val();
		var startTime = startTimeSelect.val();
		var endTime = endTimeSelect.val();
		var tableBody = $(".dd");
// 		alert(tableBody);
		if(indiSource != ""&&indiName != ""&&freqCode!=""&&startTime!=""&&endTime!="")
		{
			$.post("getSelectIndex",{indiName:indiName,indiSource:indiSource,freqCode:freqCode,startTime:startTime,endTime:endTime},function(data){
				if(data.length !=0) 
				{
					for(var i = 0;i<data.length;i++)
					{
						$("<tr bgcolor='#D0D8E8'>"+
				           " <td align='center'>"+data[i].indi_code+"</td>"+
				           " <td align='center'>"+data[i].indi_name+"</td>"+
				           " <td align='center'>"+data[i].date_code+"</td>"+
				           " <td align='center'>"+data[i].kjwdm+"</td>"+
				           " <td align='center'>"+data[i].area_code+"</td>"+
				           " <td align='center'>"+data[i].area_name+"</td>"+
				           " <td align='center'>"+data[i].freq_code+"</td>"+
				           " <td align='center'>"+data[i].time_point+"</td>"+
				           " <td align='center'>"+data[i].indi_value+"</td>"+
				           " <td align='center'><input type='checkbox' name='test'>删除</td>"+
			        	   "</tr>").appendTo(tableBody);
// 						<td><input type="checkbox" name="test"></td>
					}
					
				}
				else
				{
					alert("条件值为空");
				}
			},"json")
			
		}
		else
			alert("选择条件不对！")
			
			
			delectBu.show();
		

	})
	
})


$(function(){  
    $("#delectBu").click(function() {
        $("input[name='test']:checked").each(function() { // 遍历选中的checkbox
            n = $(this).parents("tr").index()+1;  // 获取checkbox所在行的顺序
            $("table#toExcel").find("tr:eq("+n+")").remove();
        });
    });
});


// function search(){
// 	var searchName=document.getElementById("searchKeyWord").value;
// 	var keyWord=encodeURI(encodeURI(searchName));
	
// 	var formSearch=document.getElementById("formSearch");
// 	formSearch.action="dbToEcxel?keyWord="+keyWord;
// 	formSearch.submit();
// 	alert("搜索成功，请选择要导出的指标名字！")
// }

search = function(Url) {
	 var searchName=document.getElementById("searchKeyWord").value;
// 	alert(searchName)
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
//         	 	alert(data);
                 $('#getNewData').html(data);
             },
             error : function(data){
             }
         });  
  
  alert("搜索成功，请选择要导出的指标名字！");
  };
	
</script>
	
	
</body>

</html>