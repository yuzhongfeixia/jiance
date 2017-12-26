<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>农产品基础信息</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <style type="text/css">
.image{ 
position: relative;
width: 50px;
height: 50px;
left: 140px;
top: -112px;
}
.preview{
position:absolute;

}
/* .image small:hover big{ */
/* visibility: visible; */
/* } */
 
 </style>
  <script type="text/javascript">
	$(function () {
		$("img").dblclick(function (e) {
			if(this.src.indexOf(window.location.href) <= 0){
				modalAlert("没有对应的图片!");
				return;
			}
			$.dialog({
				content: "<img src = " + this.src + " />",
				title: '原图',
				lock : true,
				ok: function(){
					return true;
				}
			});
		});
	});
	function uploadcomplete(){
		iframe = this.iframe.contentWindow;
		var path = iframe.getAttachmentPath();
		var attElement = $("#attachmentImage");
		if(attElement.length > 0){
			attElement.attr("src", getActionPath(path));
		} else {
			$("#imageView").append("<img src='" + getActionPath(path) + "' id='attachmentImage'/>");
		}
		$("#imagepath").val(getActionPath(path));
		return true;
	}
	function callUpload(){
		$.dialog({
			content: 'url:systemController.do?callUpload&type=image&auto=true'
			,zIndex: 1999
			,title: '上传图片'
			,lock : true
			,button : [ {
				name : '确认'
				,callback : uploadcomplete
				,focus : true}
				, {
				name : '取消'
				,callback : function(){}
			}]
		});
	}
  </script>
 <body style="overflow: auto; overflow-x:hidden">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="agrProductsController.do?save">
			<input id="id" name="id" type="hidden" value="${agrProductsPage.id }">
			<table style="width: 700px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							英文名称:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="ename" name="ename" ignore="ignore" datatype="/^[\dA-Za-z_]{1,32}$/"
							   value="${agrProductsPage.ename}">
						<span class="Validform_checktip">英文名称需要0~32位英文字符(不可包含空白或特殊字符)</span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							中文名称:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="cname" name="cname" datatype="s1-32"
							   value="${agrProductsPage.cname}">
						<span class="Validform_checktip">中文名称需要1~32位字符(不可包含空白或特殊字符)</span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							拉丁文名称:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="latin" name="latin" ignore="ignore"  datatype="/^[\dA-Za-z_]{1,32}$/"
							   value="${agrProductsPage.latin}">
						<span class="Validform_checktip">拉丁文名称需要0~32位英文字符(不可包含空白或特殊字符)</span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							农产品编码:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="agrcode" name="agrcode" ignore="ignore" datatype="/^[\dA-Za-z_]{1,32}$/"
							   value="${agrProductsPage.agrcode}">
						<span class="Validform_checktip">农产品编码需要0~32位英文字符(不可包含空白或特殊字符)</span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							国际编码:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="codex" name="codex" ignore="ignore" datatype="/^[\dA-Za-z_]{1,32}$/"
							   value="${agrProductsPage.codex}">
						<span class="Validform_checktip">国际编码需要0~32位英文字符(不可包含空白或特殊字符)</span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							FOODEX:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="foodex" name="foodex" ignore="ignore" datatype="/^[\dA-Za-z_]{1,32}$/"
							   value="${agrProductsPage.foodex}">
						<span class="Validform_checktip">FOODEX需要0~32位英文字符(不可包含空白或特殊字符)</span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							描述:
						</label>
					</td>
					<td class="value">
						<textarea id="describe" name="describe" ignore="ignore" rows="10" cols="60" class="inputArea" datatype="*1-1000">${agrProductsPage.describe}</textarea>
						<span class="Validform_checktip">描述需要0~1000位字符</span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							图片:
						</label>
					</td>
					<td class="value">
						<div id="imageView"></div>
						<a onclick="callUpload();">上传图片</a>
						<input  type="hidden" id="imagepath" name="imagepath">
						<span class="Validform_checktip">
						</span>
					</td>
				</tr>
				<tr hidden="hidden">
					<td align="right">
						<label class="Validform_label">
							分类id:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="categoryid" name="categoryid" ignore="ignore"
							   value="${agrProductsPage.categoryid}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr hidden="hidden">
					<td align="right">
						<label class="Validform_label">
							版本id:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="versionid" name="versionid" ignore="ignore"
							   value="${agrProductsPage.versionid}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>