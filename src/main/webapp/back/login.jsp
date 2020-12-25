<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html>
<html lang="en">
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<meta charset="utf-8">
	<title>Cloud Admin | Login</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1, user-scalable=no">
	<meta name="description" content="">
	<meta name="author" content="">
	<!-- STYLESHEETS --><!--[if lt IE 9]><script src="js/flot/excanvas.min.js"></script><script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script><script src="http://css3-mediaqueries-js.googlecode.com/svn/trunk/css3-mediaqueries.js"></script><![endif]-->
	<!-- <link rel="stylesheet" type="text/css" href="../assets/css/cloud-admin.css" > -->
	<link rel="stylesheet" type="text/css" href="back/assets/css/cloud-admin.css"  charset="utf-8">
	<link href="back/assets/font-awesome/css/font-awesome.min.css" rel="stylesheet" charset="utf-8">
	<style>
	#loginbtn{
		background-color: white;
		border: none;
	}
	#regbtn{
		background-color: white;
		border: none;
	}
	</style>
<script language="javascript">
        //防止页面后退
        history.pushState(null, null, document.URL);
        window.addEventListener('popstate', function () {
            history.pushState(null, null, document.URL);
        });
    </script>
</head>
<body class="login">

<!-- <form method="post" enctype="multipart/form-data" action="setHeadApp" >
		<input type="text" name="token" id="token" value="525d54dd4097a0d4722d4c4228eb0577" />
		<input type="file" name="file" id="file" value="请选择图片" />
		<button type="submit" >提交</button>
	</form> -->

	
	<!-- PAGE -->
	<section id="page">
			<!-- HEADER -->
			<header>
				<!-- NAV-BAR -->
				<div class="container">
					<div class="row">
						<div class="col-md-4 col-md-offset-4">
							<div id="logo">
								<!-- <a href="index.html"><img src="img/logo/logo-alt.png" height="40" alt="logo name" /></a> -->
							</div>
						</div>
					</div>
				</div>
				<!--/NAV-BAR -->
			</header>
			<!--/HEADER -->
			<!-- LOGIN -->
			<section id="login" class="visible">
			${msg}
			
				<div class="container">
					<div class="row">
						<div class="col-md-4 col-md-offset-4">
							<div class="login-box-plain">
								<h2 class="bigintro">登 录</h2>
								<div class="divide-40"></div>
								<form role="form" action="login" method="post">
								  <div class="form-group">
									<label for="exampleInputEmail1">用户名</label>
									<i class="fa fa-user"></i>
									<input type="text" class="form-control" id="exampleInputEmail1" name="username">
								  </div>
								  <div class="form-group"> 
									<label for="exampleInputPassword1">密码</label>
									<i class="fa fa-lock"></i>
									<input type="password" class="form-control" id="exampleInputPassword1" name="password">
								  </div>
									<div class="form-group">
										<label id="errorInfo" style="color: #ff4b42">${errorInfo}</label>
									</div>
									<div class="form-actions" id="loginbtn">
									<!-- <label class="checkbox"> <input type="checkbox" class="uniform" value=""> Remember me</label> -->
									<button type="submit" class="btn btn-danger">登录</button>
								  </div>
								</form>
								<!-- SOCIAL LOGIN -->
								<!-- <div class="divide-20"></div>
								<div class="center">
									<strong>Or login using your social account</strong>
								</div>
								<div class="divide-20"></div>
								<div class="social-login center">
									<a class="btn btn-primary btn-lg">
										<i class="fa fa-facebook"></i>
									</a>
									<a class="btn btn-info btn-lg">
										<i class="fa fa-twitter"></i>
									</a>
									<a class="btn btn-danger btn-lg">
										<i class="fa fa-google-plus"></i>
									</a>
								</div> -->
								<!-- /SOCIAL LOGIN -->
								<div class="login-helpers">
<!-- 									<a href="#" onclick="swapScreen('forgot');return false;">忘记密码</a> <br> -->
<!-- 									<a href="#" onclick="swapScreen('register');return false;">注册</a> -->
<!-- 								</div> -->
							</div>
						</div>
					</div>
				</div>
			</section>
			<!--/LOGIN -->
			<!-- REGISTER -->
			<section id="register">
				${msg}
				<div class="container">
					<div class="row">
						<div class="col-md-4 col-md-offset-4">
							<div class="login-box-plain">
								<h2 class="bigintro">注册</h2>
								<div class="divide-40"></div>
								<form role="form" action="register" method="post">
								  <div class="form-group">
									<label for="exampleInputUsername">用户名</label>
									<i class="fa fa-user"></i>
									<input type="text" class="form-control" id="exampleInputUsername" name="re_username">
								  </div>
						
								  <div class="form-group"> 
									<label for="exampleInputPassword1">密码</label>
									<i class="fa fa-lock"></i>
									<input type="password" class="form-control" id="exampleInputPassword1" name="re_password">
								  </div>
								  <div class="form-group"> 
									<label for="exampleInputPassword2">确认密码</label>
									<i class="fa fa-check-square-o"></i>
									<input type="password" class="form-control" id="exampleInputPassword2" name="re_confirmpassword">
								  </div>
								  <div class="form-actions" id="regbtn">
									<!-- <label class="checkbox"> <input type="checkbox" class="uniform" value=""> I agree to the <a href="#">Terms of Service</a> and <a href="#">Privacy Policy</a></label> -->
									<button type="submit" class="btn btn-success">注册</button>
								  </div>
								</form>
								<!-- SOCIAL REGISTER -->
					
								<!-- /SOCIAL REGISTER -->
								<div class="login-helpers">
									<a href="#" onclick="swapScreen('login');return false;">返回登录</a> <br>
								</div>
							</div>
						</div>
					</div>
				</div>
			</section>
			<!--/REGISTER -->
			<!-- FORGOT PASSWORD -->
			<section id="forgot">
				<div class="container">
					<div class="row">
						<div class="col-md-4 col-md-offset-4">
							<div class="login-box-plain">
								<h2 class="bigintro">Reset Password</h2>
								<div class="divide-40"></div>
								<form role="form">
								  <div class="form-group">
									<label for="exampleInputEmail1">Enter your Email address</label>
									<i class="fa fa-envelope"></i>
									<input type="email" class="form-control" id="exampleInputEmail1" >
								  </div>
								  <div class="form-actions">
									<button type="submit" class="btn btn-info">Send Me Reset Instructions</button>
								  </div>
								</form>
								<div class="login-helpers">
									<a href="#" onclick="swapScreen('login');return false;">Back to Login</a> <br>
								</div>
							</div>
						</div>
					</div>
				</div>
			</section>
			<!-- FORGOT PASSWORD -->
	</section>
	<!--/PAGE -->
	<!-- JAVASCRIPTS -->
	<!-- Placed at the end of the document so the pages load faster -->
	<!-- JQUERY -->
	<!-- <script src="js/jquery/jquery-2.0.3.min.js"></script> -->
	<script src="assets/js/jquery-1.10.2.js"></script>
	<!-- JQUERY UI-->
	<script src="assets/js/jquery-ui-1.10.3.custom.min.js"></script>
	<!-- BOOTSTRAP -->
	<script src="assets/js/bootstrap.min.js"></script>
	
	
	<!-- UNIFORM -->
	<script type="text/javascript" src="assets/js/jquery.uniform.min.js"></script>
	<!-- CUSTOM SCRIPT -->
	<script src="assets/js/script.js"></script>
	<script>
		jQuery(document).ready(function() {		
			App.setPage("login");  //Set current page
			App.init(); //Initialise plugins and elements
		});
	</script>
	<script type="text/javascript">
		function swapScreen(id) {
			jQuery('.visible').removeClass('visible animated fadeInUp');
			jQuery('#'+id).addClass('visible animated fadeInUp');
		}
	</script>
	<!-- /JAVASCRIPTS -->
</body>
</html>