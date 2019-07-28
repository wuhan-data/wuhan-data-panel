$(document).ready(function(){
	alert("进入js");
	//找到三个下拉框
	var indiNameSelect = $(".indiName").children("select");
	var indiSourceSelect = $(".indiSource").children("select");
	var freqCodeSelect = $(".freqCode").children("select");
	
	
	//给三个下拉框注册事件
	
	/**
	 * 第一个下拉框change事件
	 */
	indiNameSelect.change(function(){
		alert("进入indiNameSelect");
		//只要第一个下拉框有变化则隐藏第三个下拉框
		freqCodeSelect.parent().hide();
		
		//隐藏汽车图片 attr：先清空上次src图片路径避免下一次先显示一次
//		carimg.hide().attr("src","");
		alert("进了吗");
		//1、找到下拉框的值
		var indiNameValue = $(this).val();
		alert("indiNameValue有值吗？："+indiNameValue);
		alert(indiNameValue);
		//2、如果下拉框所选值不为空，则将该值传送给服务器
		if(indiNameValue != "")
		{
			/**
			 * 如果缓存为空 则请求服务器端数据
			 */
		    if(!indiNameSelect.data(indiNameValue))
		    {
		    	$.post("getIndiSourceByIndiName",{indiName:indiNameValue},function(data){
					
					//接收服务器返回汽指标来源---
					// 如果返回的数据不为空
					if(data.length !=0) 
					{
						//解析返回的指标来源数据，并填充到指标来源的下拉框中
						//先清空上次请求数据	
						indiSourceSelect.html("");
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
		    }
		    else
		    {
		    	//---获取缓存中的数据
		    	var data = indiNameSelect.data(indiNameValue);
				if(data.length !=0) 
				{
					indiSourceSelect.html("");
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
		    }
			
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
			if(!indiSourceSelect.data(indiSourceValue))
			{

				  $.post("getIndiFreqCode",{indiName:indiNameValue,indiSource:indiSourceValue},function(data){
					if(data.length !=0) 
					{
						freqCodeSelect.html("");
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
				//获取缓存数据
				var data = indiSourceSelect.data(indiSourceValue);
				if(data.length !=0) 
				{
					//alert("得到了缓存数据……");
					freqCodeSelect.html("");
					$("<option value=''>" + "请选择车轮类型"+ "</option>").appendTo(freqCodeSelect);
					for(var i = 0;i<data.length;i++)
					{
						$("<option value='" + data[i] + "'>" + data[i] + "</option>").appendTo(freqCodeSelect);
					}
					freqCodeSelect.parent().show();
					freqCodeSelect.next().hide();
				}
				else
				{
					alert(indiSourceValue+"为空");
				}
			}
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
		//获取三个下拉框中的值，便于拼接图片名称
		var indiName = indiNameSelect.val();
		var indiSource = indiSourceSelect.val();
		var freqCode = $(this).val();
		
		
		
		//拼接图片名称
//		var carimageSrc = "images/"+ carname +"_"+ cartypename +"_"+ wheelname + ".jpg";
		
		
        //先隐藏上次装载的图片
//		carimg.hide();
		
		//显示loading图片
//		$(".carloading").show();
		
		//通过javaScript的Image对象预装载显示图片
//		var carimage = new Image();
//		$(carimage).attr("src",carimageSrc).load(function(){	
//			 //-------------加载完成后执行的
//			
//			//隐藏loading图片
//			$(".carloading").hide();
//			
//			//显示汽车图片
//			 //carimg.attr("src",carimageSrc).show();
//			
//			//实现淡出动画效果
//			carimg.attr("src",carimageSrc);
//			carimg.animate({
//				   left: 50, opacity: 'show'
//			 }, { duration: 900 });
//		});
	})
	//给数据装载中的节点定义ajax事件，实现动画提示效果
//	$(".loading").ajaxStart(function(){
//		$(this).css("visibility","visible");
//		//animate：用于创建自定义动画的函数。
//		//opacity:0看不见 1:看得见
//		$(this).animate({
//			opacity:1
//		},0)
//	
//	}).ajaxStop(function(){
//
//		$(this).animate({
//			opacity:0
//		},500); //500毫秒逐渐淡出
//	});

})