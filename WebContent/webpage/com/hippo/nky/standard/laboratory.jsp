<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>风险评估实验室</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="laboratoryController.do?save">
			<input id="id" name="id" type="hidden" value="${laboratoryPage.id }">
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							机构名称:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="ogrname" name="ogrname" ignore="ignore"
							   value="${laboratoryPage.ogrname}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							行业:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="industry" name="industry" ignore="ignore"
							   value="${laboratoryPage.industry}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							地址:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="address" name="address" ignore="ignore"
							   value="${laboratoryPage.address}">
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
							   value="${laboratoryPage.leader}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							联系人:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="contacts" name="contacts" ignore="ignore"
							   value="${laboratoryPage.contacts}">
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
						<input class="inputxt" id="type" name="type" ignore="ignore"
							   value="${laboratoryPage.type}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							创建时间:
						</label>
					</td>
					<td class="value">
						<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  style="width: 150px" id="createdate" name="createdate" ignore="ignore"
							     value="<fmt:formatDate value='${laboratoryPage.createdate}' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							创建人:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="createby" name="createby" ignore="ignore"
							   value="${laboratoryPage.createby}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							邮编:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="zipcode" name="zipcode" ignore="ignore"
							   value="${laboratoryPage.zipcode}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							负责人电话:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="leadertel" name="leadertel" ignore="ignore"
							   value="${laboratoryPage.leadertel}">
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
							   value="${laboratoryPage.contactstel}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							负责人座机:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="leaderphone" name="leaderphone" ignore="ignore"
							   value="${laboratoryPage.leaderphone}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							传真:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="fax" name="fax" ignore="ignore"
							   value="${laboratoryPage.fax}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							电子邮箱:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="email" name="email" ignore="ignore"
							   value="${laboratoryPage.email}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							联系人座机:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="contactsphone" name="contactsphone" ignore="ignore"
							   value="${laboratoryPage.contactsphone}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							专业性风险评估实验室依托单位:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="professionalsupportunit" name="professionalsupportunit" ignore="ignore"
							   value="${laboratoryPage.professionalsupportunit}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							区域性风险评估实验室依托单位:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="regionalsupportunit" name="regionalsupportunit" ignore="ignore"
							   value="${laboratoryPage.regionalsupportunit}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>