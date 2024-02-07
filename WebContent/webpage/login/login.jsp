<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>江苏省农产品质量安全监测信息系统</title>
<script src="assets/js/sysUtil.js" type="text/javascript"></script>
<link href="css/style.css" rel="stylesheet" type="text/css" />

<link href="plug-in/login/css/zice.style.css" rel="stylesheet" type="text/css" />
<style type="text/css">
input[type=text],input[type=password] {
	height:27px
}
</style>
</head>

<body>
  <div id="alertMessage"></div>
  <div id="successLogin"></div>
  <div class="text_success">
   <img src="plug-in/login/images/loader_green.gif" alt="Please wait" />
   <span>登陆成功!请稍后....</span>
  </div>
	<form action="loginController.do?login" name="loginForm" method="post"  check="loginController.do?checkuser">
		<table width="524" border="0" align="center"
			cellpadding="0" cellspacing="0">
			<tr>
				<td><img src="images/login_img_01.jpg" /></td>
				<td><img src="images/login_img_02.jpg" /></td>
				<td><img src="images/login_img_03.png" /></td>
			</tr>
			<tr>
				<td><img src="images/login_img_04.jpg" /></td>
				<td valign="bottom" background="images/login_img_05.jpg"><table
						width="247px"  border="0" align="center"
						cellpadding="0" cellspacing="0">
						<tr>
							<td width="60" align="left">用&nbsp;&nbsp;户：</td>
							<td width="177" align="left"><input type="text"
								name="userName" id="userName" nullmsg="请输入用户名！"
								class="login_ipt" maxlength="20" /></td>
							<td width="10" align="left">&nbsp;</td>
						</tr>
						<tr>
							<td align="left">密&nbsp;&nbsp;码：</td>
							<td align="left"><input type="password" name="password"
								id="password" nullmsg="请输入密码！" class="login_ipt" maxlength="20" /></td>
							<td align="left">&nbsp;</td>
						</tr>
						<tr>
							<td align="left">验证码：</td>
							<td align="left"><input type="text" name="code" id="code" style="width: 75px;"
								nullmsg="请输入验证码！" class="login_ipt" maxlength="20" /><img
								id="codeImg" onclick="resetCode()" width="100px"
								style="vertical-align: bottom;" /></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td align="right" valign="bottom"><input name="but_login"
								type="button" class="login_btn" value="登&nbsp;录" id="but_login" /></td>
							<td>&nbsp;</td>
						</tr>
					</table></td>
				<td><img src="images/login_img_06.jpg" /></td>
			</tr>
			<tr>
				<td><img src="images/login_img_07.jpg"/></td>
				<td><img src="images/login_img_08.jpg" /></td>
				<td><img src="images/login_img_09.jpg" /></td>
			</tr>
		</table>
	</form>
	    <!-- Link JScript-->
  <script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.min.js"></script>
  <script type="text/javascript" src="plug-in/login/js/login.js"></script>
</body>
</html>
