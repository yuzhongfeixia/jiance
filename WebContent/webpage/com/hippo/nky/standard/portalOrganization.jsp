<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>组织机构</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="portalOrganizationController.do?save"  tiptype="1">
			<input id="id" name="id" type="hidden" value="${portalOrganizationPage.id }">
			<table style="width: 700px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
				<td align="right">
						<label class="Validform_label">
							机构名称:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="ogrname" name="ogrname" datatype="*"
							   value="${portalOrganizationPage.ogrname}">
						<span class="Validform_checktip"></span>
					</td>
					<td align="right">
						<label class="Validform_label">
							性质:
						</label>
					</td>
					<td class="value">
					<t:dictSelect field="property" typeGroupCode="organizationproperty"
						hasLabel="false" defaultVal="${portalOrganizationPage.property}"></t:dictSelect>
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							代码:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="code" name="code" datatype="*"
							   value="${portalOrganizationPage.code}">
						<span class="Validform_checktip"></span>
					</td>
					<td align="right">
						<label class="Validform_label">
							邮编:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="zipcode" name="zipcode" ignore="ignore"
							   value="${portalOrganizationPage.zipcode}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							依托单位:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="supportinstitution" name="supportinstitution" ignore="ignore"
							   value="${portalOrganizationPage.supportinstitution}">
						<span class="Validform_checktip"></span>
					</td>
					<td align="right">
						<label class="Validform_label">
							详细地址:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="address" name="address" ignore="ignore"
							   value="${portalOrganizationPage.address}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							负责人:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="leader" name="leader" ignore="ignore"
							   value="${portalOrganizationPage.leader}">
						<span class="Validform_checktip"></span>
					</td>
					<td align="right">
						<label class="Validform_label">
							联系人:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="contacts" name="contacts" ignore="ignore"
							   value="${portalOrganizationPage.contacts}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							类型:
						</label>
					</td>
					<td class="value">
					<t:dictSelect field="type" typeGroupCode="organizationtype"
						hasLabel="false" defaultVal="${portalOrganizationPage.type}"></t:dictSelect>
						<span class="Validform_checktip"></span>
					</td>
					<td align="right">
						<label class="Validform_label">
							负责人电话:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="leadertel" name="leadertel" ignore="ignore"
							   value="${portalOrganizationPage.leadertel}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							联系人电话:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="contactstel" name="contactstel" ignore="ignore"
							   value="${portalOrganizationPage.contactstel}">
						<span class="Validform_checktip"></span>
					</td>
					<td align="right">
						<label class="Validform_label">
							传真:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="fax" name="fax" ignore="ignore"
							   value="${portalOrganizationPage.fax}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							电子邮箱:
						</label>
					</td>
					<td class="value" colspan="3">
						<input class="inputxt" id="email" name="email" ignore="ignore"
							   value="${portalOrganizationPage.email}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>