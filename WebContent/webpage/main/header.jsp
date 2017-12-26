<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- BEGIN TOP NAVIGATION BAR -->
<div class="navbar-inner">
	<div class="navbar-content" style="background: url('assets/img/header_bg.png') repeat-x !important;">
		<!-- BEGIN LOGO -->
		<img src="assets/img/jiangsu_logo.png" href="index.jsp"/>
		<!-- END LOGO -->
		<!-- BEGIN TOP NAVIGATION MENU -->              
		<ul class="nav pull-right" style="margin: 44px 0px 0px 0px;">
			<!-- END TODO DROPDOWN -->
			<!-- BEGIN USER LOGIN DROPDOWN -->
			<li class="dropdown user">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown">
				<img alt="" src="assets/img/avatar_small.png" />
				<span class="username">${userName}</span>
				<i class="icon-angle-down"></i>
				</a>
				<ul class="dropdown-menu">
					<li><a action-mode="ajax" action-pop="#password_modal" action-url="userController.do?changepassword"><i class="icon-lock"></i>修改密码</a></li>
					<li><a href="loginController.do?logout"><i class="icon-key"></i>登录注销</a></li>
					<li><a href="assets/downloadApk/JS_jiance.apk"><i class="icon-download"></i>下载App</a></li>
					<li><a href="assets/downloadUserOper/UserOper_manual.rar"><i class="icon-download"></i>下载操作手册</a></li>
				</ul>
			</li>
			<!-- END USER LOGIN DROPDOWN -->
		</ul>
		<!-- END TOP NAVIGATION MENU --> 
	</div>
</div>
<div id="password_modal" class="modal hide fade" tabindex="-1"></div>
<!-- END TOP NAVIGATION BAR -->
