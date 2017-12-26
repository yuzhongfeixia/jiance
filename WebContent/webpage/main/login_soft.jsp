<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>江苏省农产品质量安全监测信息系统</title>
<script src="assets/js/sysUtil.js" type="text/javascript"></script>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
		function beforeSumbit(){
		//	document.loginForm.action = getActionPath($("[class='form-vertical login-form']").attr("action"));
			document.loginForm.submit();
// 			$("[class='form-vertical login-form']").attr("action", getActionPath($("[class='form-vertical login-form']").attr("action")));

		}
	</script>
</head>

<body>
	<form action="loginController.do?login" name="loginForm" method="post">
<table width="524" height="313" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><img src="images/login_img_01.jpg" /></td>
   <td><img src="images/login_img_02.jpg" /></td>
   <td><img src="images/login_img_03.png" /></td>
  </tr>
  <tr>
   <td><img src="images/login_img_04.jpg" /></td>
   <td valign="bottom" background="images/login_img_05.jpg"><table width="247" height="108" border="0" align="center" cellpadding="0" cellspacing="0">
     <tr>
       <td width="52" height="36" align="left">用&nbsp;&nbsp;户：</td>
       <td width="177" align="left">
        
         <input type="text" name="textfield" id="textfield"   class="login_ipt" maxlength="20" />
        </td>
       <td width="18" align="left">&nbsp;</td>
     </tr>
     <tr>
       <td align="left">密&nbsp;&nbsp;码：</td>
       <td align="left"><input type="password" name="textfield2" id="textfield2" class="login_ipt" maxlength="20" /></td>
       <td align="left">&nbsp;</td>
     </tr>
     <tr>
       <td>&nbsp;</td>
       <td align="right" valign="bottom"><input name="" type="button" class="login_btn" value="登&nbsp;录" onclick="beforeSumbit();"/></td>
       <td>&nbsp;</td>
     </tr>
   </table></td>
   <td><img src="images/login_img_06.jpg" /></td>
  </tr>
  <tr>
   <td><img src="images/login_img_07.jpg" /></td>
   <td><img src="images/login_img_08.jpg" /></td>
   <td><img src="images/login_img_09.jpg" /></td>
  </tr>
</table>
</form>
</body>
</html>
