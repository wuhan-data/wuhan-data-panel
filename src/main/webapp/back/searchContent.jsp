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
    <!--  <link href="assets/css/bootstrap.css" rel="stylesheet" />  -->
     <!-- FontAwesome Styles-->
    <link href="assets/css/font-awesome.css" rel="stylesheet" />
        <!-- Custom Styles-->
    <link href="assets/css/custom-styles.css" rel="stylesheet" />
    
     <link href="assets/css/bootstrap-switch.min.css" rel="stylesheet" />
    
   <!--  <link href="assets/css/my.css" rel="stylesheet" /> -->
    
  
     <!-- Google Fonts-->
   <link href='https://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
<style>

li:hover{
background-color: #C4C4C4;
}
</style>

</head>
<body>
<ul>
<c:forEach items="${resultList}" var="c" varStatus="st">
 <li id="${c.search_indi_id }" onclick="resetContent('${c.indi_old_name }')">${c.indi_old_name }${c.search_indi_id }</li>
 </c:forEach>
</ul>

</body>
<script>

</script>
</html>
