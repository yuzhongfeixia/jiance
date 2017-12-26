<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>检测方案</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="monitoringProjectController.do?save">
			<input id="id" name="id" type="hidden" value="${monitoringProjectPage.id }">
			<input id="projectCode" name="projectCode" type="hidden" value="${monitoringProjectPage.projectCode }">
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							方案ID:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="planCode" name="planCode" ignore="ignore"
							   value="${monitoringProjectPage.planCode}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							项目名称:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="name" name="name" ignore="ignore"
							   value="${monitoringProjectPage.name}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							牵头单位编号:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="leadunit" name="leadunit" ignore="ignore"
							   value="${monitoringProjectPage.leadunit}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							监测开始时间:
						</label>
					</td>
					<td class="value">
						<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  style="width: 150px" id="starttime" name="starttime" ignore="ignore"
							     value="<fmt:formatDate value='${monitoringProjectPage.starttime}' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							监测结束时间:
						</label>
					</td>
					<td class="value">
						<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  style="width: 150px" id="endtime" name="endtime" ignore="ignore"
							     value="<fmt:formatDate value='${monitoringProjectPage.endtime}' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							是否抽检分离:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="detached" name="detached" ignore="ignore"
							   value="${monitoringProjectPage.detached}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							状态:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="state" name="state" ignore="ignore"
							   value="${monitoringProjectPage.state}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							项目ID:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="projectCode" name="projectCode" ignore="ignore"
							   value="${monitoringProjectPage.projectCode}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>