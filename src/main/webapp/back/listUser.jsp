<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>  
<html>  
<head lang="en">  
    <meta charset="UTF-8">  
    <title>this is a bootstrap-switch test</title>  
   <link href="back/assets/css/bootstrap.min.css" rel="stylesheet" />
    	<link href="back/assets/css/bootstrap-switch.min.css" rel="stylesheet" />
   		 <script src="back/assets/js/jquery.min.js"></script>
   		 <script src="back/assets/js/bootstrap.min.js"></script>
    	<script src="back/assets/js/bootstrap-switch.min.js"></script>

</head>  
<body>
		<input type="checkbox" class="status" id="status_1" data-id="1" data-status="0" />
<input type="checkbox" class="status" id="status_2" data-id="2" data-status="1" />
 
<script type="text/javascript">
$(function(){
    	// 用于开关组件初始化
        $('.status').each(function(){
            var id = $(this).data('id');
            var status = $(this).data('status');
 
            $(this).bootstrapSwitch({
                onColor : "success",
                offColor : "warning",
            }).bootstrapSwitch('size', "mini").bootstrapSwitch('state', status);
        });
 
        // 监听开关状态发生变化时，触发事件
        // 如果与初始化放在一起编辑，会导致此事件重复调用
        $('.status').bootstrapSwitch("onSwitchChange",function(event,state){
            var id = $(this).data('id');
            var status = parseInt($(this).attr('data-status'));
 
            if (status != state) {
                var check = false;
                // 修改状态
                $.ajax({
                    url: "test.php",
                    async: false,
                    type: "post",
                    dataType: "json",
                    data: {'id': id, 'status': status},
                    success: function(data){
                        if (data.code == 1) {
                            check = true;
                            $('#status_'+id).attr('data-status', status ? 0 : 1);
                            layer.msg('更新成功');    //这里是调用了layer弹窗组件
                        } else {
                            layer.msg(data.msg);
                        }
                    }
                });
 
                return check;
            }
        })
    });
</script>

	</body>

</html>  