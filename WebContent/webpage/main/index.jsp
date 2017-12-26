<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
	<meta charset="utf-8" />
	<title>江苏省农产品质量安全监测信息系统</title>
	<meta content="width=device-width, initial-scale=1.0" name="viewport" />
	<meta content="" name="description" />
	<meta content="" name="author" />
	<!-- BEGIN GLOBAL MANDATORY STYLES -->
	<link href="assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
	<link href="assets/plugins/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet" type="text/css"/>
	<link href="assets/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
	<link href="assets/css/style-metro.css" rel="stylesheet" type="text/css"/>
	<link href="assets/css/style.css" rel="stylesheet" type="text/css"/>
	<link href="assets/css/style-responsive.css" rel="stylesheet" type="text/css"/>
	<link href="assets/css/themes/light.css" rel="stylesheet" type="text/css" id="style_color"/>
	<link href="assets/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>
	<!-- END GLOBAL MANDATORY STYLES -->
	<!-- BEGIN PAGE LEVEL STYLES --> 
<!-- 	<link href="assets/plugins/gritter/css/jquery.gritter.css" rel="stylesheet" type="text/css"/> -->
	<link href="assets/plugins/bootstrap-datepicker/css/datepicker.css" rel="stylesheet" type="text/css">
	<link href="assets/plugins/bootstrap-daterangepicker/daterangepicker.css" rel="stylesheet" type="text/css" />
	<link href="assets/plugins/fullcalendar/fullcalendar/fullcalendar.css" rel="stylesheet" type="text/css"/>
	<link href="assets/plugins/jqvmap/jqvmap/jqvmap.css" rel="stylesheet" type="text/css" media="screen"/>
	<link href="assets/plugins/jquery-easy-pie-chart/jquery.easy-pie-chart.css" rel="stylesheet" type="text/css" media="screen"/>
	<link href="plug-in/lhgDialog/skins/default.css" rel="stylesheet" id="lhgdialoglink">
	<!-- END PAGE LEVEL STYLES -->
	<link rel="shortcut icon" href="favicon.ico" />
	<!-- BEGIN JAVASCRIPTS -->
	<%@ include file="../common/scripts.jsp" %>
<!-- 	<script src="assets/scripts/index.js" type="text/javascript"></script>         -->
	<script>
	$(document).ready(function() {    
	   App.init(); // initlayout and core plugins
	});
</script>

	<!-- END JAVASCRIPTS -->
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-header-fixed page-sidebar-closed">
	<!-- BEGIN HEADER -->
	<div class="header navbar navbar-inverse navbar-fixed-top">
		<%@ include file="header.jsp" %>
	</div>
	<!-- END HEADER -->
	<!-- BEGIN CONTAINER -->
	<div class="page-container">
		<!-- BEGIN SIDEBAR -->
		<div class="page-sidebar nav-collapse collapse">
			<jsp:include page="/loginController/left.do"/>
		</div>
		<!-- END SIDEBAR -->
		<!-- BEGIN CONTENTER -->
		<div class="page-content">
			<!-- BEGIN PAGE CONTAINER-->
			<div id="contenter" class="container-fluid">
				<%@ include file="contenter.jsp" %>
			</div>
			<!-- END PAGE CONTAINER-->       
		</div>
		<!-- END CONTENTER -->
	</div>
	<!-- END CONTAINER -->
	<!-- BEGIN FOOTER -->
<!-- 	<div class="footer"> -->
<%-- 		<%@ include file="footer.jsp" %> --%>
<!-- 	</div> -->
	<!-- END FOOTER -->
</body>
<!-- END BODY -->
</html>