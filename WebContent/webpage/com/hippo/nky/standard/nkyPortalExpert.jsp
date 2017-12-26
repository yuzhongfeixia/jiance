<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>专家委员会</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="div" action="nkyPortalExpertController.do?save">
		<input id="id" name="id" type="hidden" value="${nkyPortalExpertPage.id }">
		<fieldset class="step">
			<div class="form">
		      <label class="Validform_label">专家名称:</label>
		      <input class="inputxt" id="name" name="name" ignore="ignore"
					   value="${nkyPortalExpertPage.name}">
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">专家简介:</label>
		      <input class="inputxt" id="description" name="description" ignore="ignore"
					   value="${nkyPortalExpertPage.description}">
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">工作业绩:</label>
		      <input class="inputxt" id="achievement" name="achievement" ignore="ignore"
					   value="${nkyPortalExpertPage.achievement}">
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">研究方向:</label>
		      <input class="inputxt" id="orientation" name="orientation" ignore="ignore"
					   value="${nkyPortalExpertPage.orientation}">
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">邮箱:</label>
		      <input class="inputxt" id="email" name="email" ignore="ignore"
					   value="${nkyPortalExpertPage.email}" datatype="e" errormsg="邮箱格式不正确!">
		      <span class="Validform_checktip"></span>
		    </div>
		    <div class="form">
		      <label class="Validform_label">单位:</label>
		      <input class="inputxt" id="unit" name="unit" ignore="ignore"
					   value="${nkyPortalExpertPage.unit}">
		      <span class="Validform_checktip"></span>
		    </div>
		    <div class="form">
		      <label class="Validform_label">地址:</label>
		      <input class="inputxt" id="address" name="address" ignore="ignore"
					   value="${nkyPortalExpertPage.address}" >
		      <span class="Validform_checktip"></span>
		    </div>
		    <div class="form">
		      <label class="Validform_label">电话:</label>
		      <input class="inputxt" id="telephone" name="telephone" ignore="ignore"
					   value="${nkyPortalExpertPage.telephone}"  errormsg="电话格式不正确!">
		      <span class="Validform_checktip"></span>
		    </div>
		    <div class="form">
		      <label class="Validform_label">传真:</label>
		      <input class="inputxt" id="faxNumber" name="faxNumber" ignore="ignore"
					   value="${nkyPortalExpertPage.faxNumber}"  errormsg="传真格式不正确!">
		      <span class="Validform_checktip"></span>
		    </div>
		    <div class="form">
		      <label class="Validform_label">手机:</label>
		      <input class="inputxt" id="mobilePhone" name="mobilePhone" ignore="ignore"
					   value="${nkyPortalExpertPage.mobilePhone}"  errormsg="手机格式不正确!">
		      <span class="Validform_checktip"></span>
		    </div>
		    <div class="form">
		      <label class="Validform_label">职称:</label>
		      <input class="inputxt" id="positionaltitle" name="positionaltitle" ignore="ignore"
					   value="${nkyPortalExpertPage.positionaltitle}" >
		      <span class="Validform_checktip"></span>
		    </div>
		    <div class="form">
		      <label class="Validform_label">职务:</label>
		      <input class="inputxt" id="duty" name="duty" ignore="ignore"
					   value="${nkyPortalExpertPage.duty}" >
		      <span class="Validform_checktip"></span>
		    </div>
	    </fieldset>
  </t:formvalid>
 </body>