<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>专家组</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="portalExpertController.do?save"  tiptype="1">
		<input id="id" name="id" type="hidden" value="${portalExpertPage.id }">
		<table style="width: 700px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 专家名称:
				</label></td>
				<td class="value"><input class="inputxt" id="name" name="name"
					 value="${portalExpertPage.name}" datatype="*"> <span
					class="Validform_checktip"></span></td>
				<td align="right"><label class="Validform_label"> 性别: </label>
				</td>
				<td class="value"><t:dictSelect field="sex" typeGroupCode="sex"
						hasLabel="false" defaultVal="${portalExpertPage.sex}"></t:dictSelect>
					<span class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 专家简介:
				</label></td>
				<td class="value"><input class="inputxt" id="description"
					name="description" ignore="ignore"
					value="${portalExpertPage.description}"> <span
					class="Validform_checktip"></span></td>
				<td align="right"><label class="Validform_label"> 邮箱: 
				</label></td>
				<td class="value"><input class="inputxt" id="email"
					name="email" ignore="ignore" value="${portalExpertPage.email}">
					<span class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 邮编: </label>
				</td>
				<td class="value"><input class="inputxt" id="postcode"
					name="postcode" ignore="ignore"
					value="${portalExpertPage.postcode}"> <span
					class="Validform_checktip"></span></td>
				<td align="right"><label class="Validform_label"> 职称: </label>
				</td>
				<td class="value"><input class="inputxt" id="positionaltitle"
					name="positionaltitle" ignore="ignore"
					value="${portalExpertPage.positionaltitle}"> <span
					class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 职务: </label>
				</td>
				<td class="value"><input class="inputxt" id="duty" name="duty"
					ignore="ignore" value="${portalExpertPage.duty}"> <span
					class="Validform_checktip"></span></td>
				<td align="right"><label class="Validform_label"> 单位: </label>
				</td>
				<td class="value"><input class="inputxt" id="unit" name="unit"
					ignore="ignore" value="${portalExpertPage.unit}"> <span
					class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 地址: </label>
				</td>
				<td class="value"><input class="inputxt" id="address"
					name="address" ignore="ignore" value="${portalExpertPage.address}">
					<span class="Validform_checktip"></span></td>
				<td align="right"><label class="Validform_label"> 手机: </label>
				</td>
				<td class="value"><input class="inputxt" id="mobilephone"
					name="mobilephone" ignore="ignore"
					value="${portalExpertPage.mobilephone}"> <span
					class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 电话: </label>
				</td>
				<td class="value"><input class="inputxt" id="telephone"
					name="telephone" ignore="ignore"
					value="${portalExpertPage.telephone}"> <span
					class="Validform_checktip"></span></td>
				<td align="right"><label class="Validform_label"> 传真 :
				</label></td>
				<td class="value"><input class="inputxt" id="faxnumber"
					name="faxnumber" ignore="ignore"
					value="${portalExpertPage.faxnumber}"> <span
					class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 研究方向:
				</label></td>
				<td class="value" colspan="3">
				<textarea id="orientation" name="orientation" rows="5" cols="100" class="inputArea"  ignore="ignore">${portalExpertPage.orientation}</textarea>
				<span class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 工作业绩:
				</label></td>
				<td class="value" colspan="3">
				<textarea id="achievement" name="achievement" rows="5" cols="100" class="inputArea"  ignore="ignore">${portalExpertPage.achievement}</textarea>
				<span class="Validform_checktip"></span></td>
			</tr>
		</table>
	</t:formvalid>
</body>