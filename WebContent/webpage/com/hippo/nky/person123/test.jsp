<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<html>
	<head>
	<%-- <script src="jquery-1.4.4.min.js" type="text/javascript"></script> --%>
	<script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.min.js"></script>
	<script src="plug-in/smartpaginator/smartpaginator.js" type="text/javascript"></script>
	<link href="plug-in/smartpaginator/smartpaginator.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<script type="text/javascript">
			//取得列表数据
			function getTestData(newPage){
				var url = "xDLTESTEntityController.do?splitPage&page=" + newPage;
				$.ajax({
					async : false,
					type : 'POST',
					url : url,
					error : function(data) {
							alert(data.msg);
							},
					success : function(data) {
							var jsonData = $.parseJSON(data);
							var s ="";
							if (jsonData.success) {
								$.each(jsonData.attributes.resultMap, function(index, item){
									s += "<tr><td>" + (index+1) + "</td><td>" + item.USERNAME + "</td><td>" + item.PASSWORD + "</td></tr>";
								});
								$("#tb").html(s.toString());
							}
						}
					});
			}
			
			//绑定翻页条
			$(document).ready(function() {
				$('#green').smartpaginator({
					totalrecords : '${totalrecords}',
					recordsperpage : '${recordsperpage}',
					length : 2,
					next : '下一页',
					prev : '上一页',
					first : '首页',
					last : '末页',
					go : '前往',
					theme : 'red',
					controlsalways : true,
					onchange : function(newPage) {
						getTestData(newPage);
					}
				});
				
				//初始化列表数据
				getTestData(1);
			});
		</script>
		<div class="easyui-layout" fit="true">
			<table>
				<th>序号</th>
				<th>用户名</th>
				<th>密码</th>
				</th>
				<tbody id="tb"></tbody>
			</table>
		 </div>
		<div id="green" style="text-size:12;"> </div>
	</body>
</html>