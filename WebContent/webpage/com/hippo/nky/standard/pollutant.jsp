<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>污染物基础信息</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="pollutantController.do?save&categoryId=${categoryId}&versionId=${versionId}">
			<input id="id" name="id" type="hidden" value="${pollutantPage.id }">
			<table style="width: 700px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							污染物(中文名):
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="cname" name="cname" ignore="ignore" datatype="s1-128"
							   value="${pollutantPage.cname}">
						<span class="Validform_checktip">中文名需要1~128位字符</span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							污染物(英文名):
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="ename" name="ename" ignore="ignore" datatype="/^[\dA-Za-z_]{1,128}$/"
							   value="${pollutantPage.ename}">
						<span class="Validform_checktip">英文名需要0~128位英文字符(不可包含空白或特殊字符)</span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							污染物(拉丁文名):
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="latin" name="latin" ignore="ignore" datatype="/^[\dA-Za-z_]{1,128}$/"
							   value="${pollutantPage.latin}">
						<span class="Validform_checktip">拉丁文名需要0~128位英文字符(不可包含空白或特殊字符)</span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							污染物序号:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="porder" name="porder" ignore="ignore" datatype="n"
							   value="${pollutantPage.porder}" datatype="n1-9">
						<span class="Validform_checktip">污染物序号需要0-9位数字</span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							化学结构:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="structure" name="structure" ignore="ignore" datatype="s1-128"
							   value="${pollutantPage.structure}">
						<span class="Validform_checktip">化学结构需要0~128位字符</span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							分类一:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="sort1" name="sort1" ignore="ignore" datatype="s1-32"
							   value="${pollutantPage.sort1}">
						<span class="Validform_checktip">分类一需要0~32位字符</span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							分类二:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="sort2" name="sort2" ignore="ignore" datatype="s1-32"
							   value="${pollutantPage.sort2}">
						<span class="Validform_checktip">分类二需要0~32位字符</span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							分类标准编号（污染物）:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="sortcode" name="sortcode" ignore="ignore" datatype="/^[\dA-Za-z_]{1,128}$/"
							   value="${pollutantPage.sortcode}">
						<span class="Validform_checktip">分类标准编号需要0~32位英文字符(不可包含空白或特殊字符)</span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							CAS码:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="cascode" name="cascode" ignore="ignore" datatype="/^[\dA-Za-z_]{1,128}$/"
							   value="${pollutantPage.cascode}">
						<span class="Validform_checktip">CAS码需要0~32位英文字符(不可包含空白或特殊字符)</span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							类别:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="category" name="category" ignore="ignore" datatype="s1-32"
							   value="${pollutantPage.category}">
						<span class="Validform_checktip">类别需要0~32位字符</span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							用途:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="use" name="use" ignore="ignore" datatype="s1-128"
							   value="${pollutantPage.use}">
						<span class="Validform_checktip">用途需要0~128位字符</span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							每日允许摄入量:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="adi" name="adi" ignore="ignore" datatype="s1-32"
							   value="${pollutantPage.adi}">
						<span class="Validform_checktip">每日允许摄入量需要0~32位字符</span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							残留物:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="residue" name="residue" ignore="ignore" datatype="s1-32"
							   value="${pollutantPage.residue}">
						<span class="Validform_checktip">残留物需要0~32位字符</span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							中文商品名:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="pname" name="pname" ignore="ignore" datatype="s1-32"
							   value="${pollutantPage.pname}">
						<span class="Validform_checktip">中文商品名需要0~32位字符</span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							药物类型:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="medtype" name="medtype" ignore="ignore" datatype="s1-32"
							   value="${pollutantPage.medtype}">
						<span class="Validform_checktip">药物类型需要0~32位字符</span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							描述:
						</label>
					</td>
					<td class="value">
						<textarea id="describe" name="describe" ignore="ignore" rows="8" cols="70" class="inputArea" datatype="*1-1000">${pollutantPage.describe}</textarea>
						<span class="Validform_checktip">描述需要0~1000位字符</span>
					</td>
				</tr>
				<tr style="display:none;">
					<td align="right">
						<label class="Validform_label">
							版本ID:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="versionid" name="versionid" ignore="ignore"
							   value="${pollutantPage.versionid}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr style="display:none;">
					<td align="right">
						<label class="Validform_label">
							分类ID:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="categoryid" name="categoryid" ignore="ignore"
							   value="${pollutantPage.categoryid}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>