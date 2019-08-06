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
   <!-- Bootstrap Styles-->
    <link href="back/assets/css/bootstrap.css" rel="stylesheet" />
     <!-- FontAwesome Styles-->
    <link href="back/assets/css/font-awesome.css" rel="stylesheet" />
        <!-- Custom Styles-->
    <link href="back/assets/css/custom-styles.css" rel="stylesheet" />
     <!-- Google Fonts-->
    <link href='https://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
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

	
	
	
</head>


<body>
1600020
	<form action="export" method="post"> 
				<span class="text-info">输入指标代码</span><input type="text" class="form-control" id="IndiShowType" value="" name="id">
					<button type="submit" class="btn btn-primary">
						提交
					</button>
	</form> 
	
    <div class="indi">
			<span class="indiName"> 指标名称
			   <select id="indiName">
					<option value="" >
						请选择指标
					</option>
					<c:forEach items="${indexList}" var="in">
						<option value="${in.indi_name}">${in.indi_name}</option>
					</c:forEach>
				</select> 
			</span>
			<span class="indiSource"> <img src="images/pfeil.gif" alt="" />
				指标来源： <select></select> </span>
			<span class="freqCode"> <img src="images/pfeil.gif" alt="" />
				月/季度： <select></select> </span>
			<span class="startTime"> <img src="images/pfeil.gif" alt="" />
				开始日期： <select></select> </span>
			<span class="endTime"> <img src="images/pfeil.gif" alt="" />
				结束日期： <select></select> </span>
		</div>
		
<!-- 		表格 -->
		 <div class="navbar-header">
		 	<table style="width:100%;border:1px white solid" class="dd" id="toExcel">
    			<thead>
    			<tr bgcolor="#4F81BD"style="color: #fff;">
<%--     			<%=columns[0]%> --%>
        			<td style="text-align: center" >indi_code</td>
        			<td style="text-align: center" >indi_name</td>
        			<td style="text-align: center" >date_code</td>
        			<td style="text-align: center" >kjwdm</td>
        			<td style="text-align: center" >area_code</td>
        			<td style="text-align: center" >area_name</td>
        			<td style="text-align: center" >freq_code</td>
        			<td style="text-align: center" >time_point</td>
        			<td style="text-align: center" >indi_value</td>
    			</tr>
    			</thead>
			</table>
			
	
      <button class="btn btn-success my-2 my-sm-0" id="exportE" onclick="exportExcel('ceshi','toExcel')">导出到excel表格</button>
		 </div>
		 
		 

	


	

		
		
<script>
$(document).ready(function(){
	alert('进入js');
	//找到五个下拉框
	var indiNameSelect = $('.indiName').children('select');
	var indiSourceSelect = $('.indiSource').children('select');
	var freqCodeSelect = $(".freqCode").children("select");
	var startTimeSelect = $(".startTime").children("select");
	var endTimeSelect = $(".endTime").children("select");
	//给五个下拉框注册事件
	/**
	 * 第一个下拉框change事件
	 */
	indiNameSelect.change(function(){
		alert("进入indiNameSelect");
		//只要第一个下拉框有变化则隐藏第三个和第四个下拉框
		freqCodeSelect.parent().hide();
		startTimeSelect.parent().hide();
		endTimeSelect.parent().hide();
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
						$("<option value=''>" + "请选择指标来源"+ "</option>").appendTo(indiSourceSelect);
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
						$("<option value=''>" + "请选择指标频度"+ "</option>").appendTo(freqCodeSelect);
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
						$("<option value=''>" + "请选择指标频度"+ "</option>").appendTo(startTimeSelect);
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
						$("<option value=''>" + "请选择指标频度"+ "</option>").appendTo(endTimeSelect);
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
	 * 第五个下拉框事件
	 */
	 endTimeSelect.change(function(){
		//获取五个下拉框中的值，便于拼接图片名称
		var indiName = indiNameSelect.val();
		var indiSource = indiSourceSelect.val();
		var freqCode = freqCodeSelect.val();
		var startTime = startTimeSelect.val();
		var endTime = endTimeSelect.val();
		var tableBody = $(".dd");
		alert(tableBody);
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
			        	   "</tr>").appendTo(tableBody);
						
					}
					
				}
				else
				{
					alert("条件值为空");
				}
			},"json")
			
		}
		else
			alert("完了！")
		

	})
	
})
</script>

<script type="text/javascript">
	function exportE(){
		alert("进入");
		
		var tr = $("#table tr"); // 获取table中每一行内容
		
		var result = []; // 数组
		
		for (var i = 0; i < tr.length; i++) {// 遍历表格中每一行的内容
			var tds = $(tr[i]).find("td");
			if (tds.length > 0) {
				
				result.push({
					"indi_code" : $(tds)[0].innerHTML,
					"indi_name" : $(tds)[1].innerHTML,
					"date_code" : $(tds)[2].innerHTML,
					"kjwdm" : $(tds)[3].innerHTML,
					"area_code" : $(tds)[4].innerHTML,
					"area_name" : $(tds)[5].innerHTML,
					"freq_code" : $(tds)[6].innerHTML,
					"time_point" : $(tds)[7].innerHTML,
					"indi_value" : $(tds)[8].innerHTML,
				})
			}
		}
		
		var jsonData = { // json数据
			"indiAll" : result
		}
		var result=JSON.stringify(jsonData);
		
		window.open("<c:url value='ecxelTest?"+result+"'/>");
<%-- 		location.href="<%=basePath%>ecxelTest?result="+result; --%>

<%-- 		$.post("<%=basePath%>ecxelTest.action",{'result':result},function(data){ --%>
// // 			alert("不允许指标展示！");
// 			//window.location.reload();
// 		});
		

	}
	
</script>




</body>
</html>