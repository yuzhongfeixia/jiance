<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/webpage/com/hippo/nky/portal/common/top.jsp"%>
<div class="main">
	<div class="main_middle_ag">
		<div class="left">
			<h3>${entity.name}</h3>
		</div>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td height="38" class="font_green">中文通用名:</td>
				<td><input type="text" style="border: 1px solid #08822a"></td>
				<td class="font_green">英文通用名:</td>
				<td><input type="text" style="border: 1px solid #08822a"></td>
				<td class="font_green">中文商品名:</td>
				<td><input type="text" style="border: 1px solid #08822a"></td>
				<td rowspan="2" valign="middle"><img
					src="Portal/images/search1.jpg" width="77" height="60"></td>
			</tr>
			<tr>
				<td height="38" class="font_green">英文商品名:</td>
				<td><input type="text" style="border: 1px solid #08822a"></td>
				<td class="font_green">CAS编码:</td>
				<td><input type="text" style="border: 1px solid #08822a"></td>
				<td class="font_green">状态:</td>
				<td><input type="text" style="border: 1px solid #08822a"></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
		</table>
	</div>
	<div class="main_bottom_ag">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr bgcolor="#08822b">
				<td height="29" class="font_white">中文通用名</td>
				<td class="font_white">英文通用名</td>
				<td class="font_white">中文商品名</td>
				<td class="font_white">英文商品名</td>
				<td class="font_white">CAS编码</td>
				<td class="font_white">状态</td>
			</tr>
			<c:forEach var="list" items="${childList}" varStatus="status">
				<tr bgcolor="#e9f4ec">
					<td height="29" class="font_grey">氟氯氰菊酯</td>
					<td class="font_grey">cyfluthrin</td>
					<td class="font_grey">福利多;1605;巴拉松[台];...</td>
					<td class="font_grey">Parathion-ethyl;Thioph...</td>
					<td class="font_grey">56-38-2</td>
					<td class="font_grey">禁用</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>
<%@include file="/webpage/com/hippo/nky/portal/common/footer.jsp"%>