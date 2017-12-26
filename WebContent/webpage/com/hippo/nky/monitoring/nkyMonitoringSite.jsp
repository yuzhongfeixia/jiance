<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<script type="text/javascript">
	LinkChange.init();
	
	function checkRange(data){
		var tmpVar = data.value;
		var validator = $("#saveForm").data("validator");
		if (data.value != "") {
			if(data.value.indexOf('.') < 0) {
				if (data.id == 'longitude') {
					if (parseInt(data.value) < -180 || parseInt(data.value) > 180) {
						data.value="范围在-180到180之间";
					}
				}
				if (data.id == 'latitude') {
					if (parseInt(data.value) < -90 || parseInt(data.value) > 90) {
						data.value="范围在-90到90之间";
					}
				}
			} else {
				var zs = data.value.split(".");
				if (data.id == 'longitude') {
					if (parseInt(zs[0])  <= -180 || parseInt(zs[0]) >= 180) {
						data.value="范围在-180到180之间";
					}
				}
				if (data.id == 'latitude') {
					if (parseInt(zs[0]) <= -90 || parseInt(zs[0])  >= 90) {
						data.value="范围在-90到90之间";
					}
				}
				 
			}		
		}
	}
</script>
<div class="row-fluid">
	<div class="span12">
		<div class="portlet box popupBox_usual">
			<div class="portlet-title">
				<div class="caption">
					<i class="icon-reorder"></i> <span class="hidden-480">受检单位信息维护</span>
				</div>
			</div>
			<div class="portlet-body">
				<div class="tab-content">
					<div class="tab-pane active" id="monitoring_plan_program_tab1">
						<form action="#" class="form-horizontal" name="saveForm" id="saveForm" validate="true">
							<input id="id" name="id" type="hidden" value="${nkyMonitoringSite.id }">
							<div class="control-group">
								<label class="control-label"><span style="color:red;">* </span>受检单位代码:</label>
								<div class="controls">
									<input id="" class="m-wrap medium" type="text" name="code" value="${nkyMonitoringSite.code }"
										datatype="s2-32" ajaxurl="nkyMonitoringSiteController.do?isExsitCheck&flg=1&id=${nkyMonitoringSite.id }"/>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label"><span style="color:red;">* </span>受检单位名称:</label>
								<div class="controls">
									<input id="" class="m-wrap medium" type="text" name="name" value="${nkyMonitoringSite.name }"
										datatype="s2-32" ajaxurl="nkyMonitoringSiteController.do?isExsitCheck&flg=2&id=${nkyMonitoringSite.id }"/>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label"><span style="color:red;">* </span>法定代表人或负责人:</label>
								<div class="controls">
									<input id="" type="text" name="legalPerson" value="${nkyMonitoringSite.legalPerson }"
										class="m-wrap medium" datatype="s2-32"/> 
								</div>
							</div>
							<div class="control-group">
								<label class="control-label"><span style="color:red;">* </span>邮编:</label>
								<div class="controls">
									<input id="" class="m-wrap medium" type="text" name="zipcode" value="${nkyMonitoringSite.zipcode }"
										 datatype="/^\d{6}?$/" errormsg="请填写正确的信息！"/>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label"><span style="color:red;">* </span>联系人:</label>
								<div class="controls">
									<input id="" type="text" name="contactPerson" value="${nkyMonitoringSite.contactPerson }"
										class="m-wrap medium" datatype="s2-32"/>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label"><span style="color:red;">* </span>联系电话:</label>
								<div class="controls">
									<input id="" type="text" name="contact" value="${nkyMonitoringSite.contact }"
										class="m-wrap medium" datatype="s1-15"/> 
								</div>
							</div>
										<div class="control-group">
								<label class="control-label">传真:</label>
								<div class="controls">
									<input id="" type="text" name="fax" value="${nkyMonitoringSite.fax }"
										class="m-wrap medium" datatype="s1-15" ignore="ignore" errormsg="请填写1到15位字符！(特殊字符除外)"/>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">主管单位:</label>
								<div class="controls">
									<input id="" type="text" name="unit" value="${nkyMonitoringSite.unit }"
										class="m-wrap medium" datatype="s1-128" ignore="ignore" errormsg="请填写1到128位字符！(特殊字符除外)"/> 
								</div>
							</div>
							<div class="control-group">
								<label class="control-label"><span style="color:red;">* </span>所属区域:</label>
								<div id="areasDiv" class="controls">
									<t:dictSelect id="areacode" field="areacode" hasLabel="false" customData="${areacodeList}" defaultVal="${nkyMonitoringSite.areacode}" extend="{data-set:{value:'#areasDiv .areaSelect'},link-Change:{value:'true'},datatype:{value:'*'}}"></t:dictSelect>
									<t:dictSelect id="areacode2" field="areacode2" hasLabel="false" customData="${areacodeList2}" defaultVal="${nkyMonitoringSite.areacode2}"  extend="{class:{value:'small areaSelect'}}"></t:dictSelect>
								</div>
							</div>
							<!-- 20140930 -->
							<div class="control-group">
								<label class="control-label">乡镇/街道:</label>
								<div class="controls">
									<input id="" type="text" name="townandstreet" value="${nkyMonitoringSite.townandstreet }"
										class="m-wrap medium"/> 
								</div>
							</div>
							<div class="control-group">
								<label class="control-label"><span style="color:red;">* </span>监测环节:</label>
								<div class="controls">
									<t:dictSelect id="monitoringLink" field="monitoringLink" typeGroupCode="allmonLink" hasLabel="false" defaultVal="${nkyMonitoringSite.monitoringLink}" extend="{datatype:{value:'*'}}"></t:dictSelect>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">企业性质:</label>
								<div class="controls">
									<t:dictSelect id="enterprise" field="enterprise" typeGroupCode="enterprise" hasLabel="false" defaultVal="${nkyMonitoringSite.enterprise}"></t:dictSelect>
								</div>
							</div>
							<!-- 20140930 -->
							<div class="control-group">
								<label class="control-label">规模类型:</label>
								<div class="controls">
									<t:dictSelect id="scaletype" field="scaletype" typeGroupCode="scaletype" hasLabel="false" defaultVal="${nkyMonitoringSite.scaletype}"></t:dictSelect>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">规模单位:</label>
								<div class="controls">
									<t:dictSelect id="scaleunit" field="scaleunit" typeGroupCode="scaleunit" hasLabel="false" defaultVal="${nkyMonitoringSite.scaleunit}"></t:dictSelect>
								</div>
							</div>
							
							<div class="control-group">
								<label class="control-label">企业规模:</label>
								<div class="controls">
									<input id="" type="text" name="scale" value="${nkyMonitoringSite.scale }" class="m-wrap medium"/> 
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">经度:</label>
								<div class="controls">
									<input id="longitude" type="text" name="longitude" value="${nkyMonitoringSite.longitude }"
										class="m-wrap medium" ignore="ignore" datatype="/^\d{1,3}(\.\d{1,6})?$/" errormsg="输入的格式不对或超出范围！" onchange="checkRange(this)"/>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">纬度:</label>
								<div class="controls">
									<input id="latitude" type="text" name="latitude" value="${nkyMonitoringSite.latitude }"
										class="m-wrap medium" ignore="ignore" datatype="/^\d{1,2}(\.\d{1,6})?$/" errormsg="输入的格式不对或超出范围！" onchange="checkRange(this)"/>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label"><span style="color:red;">* </span>详细地址:</label>
								<div class="controls">
									<textarea id="" class="m-wrap" name="address"
										rows="5" datatype="s1-255">${nkyMonitoringSite.address}</textarea>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" data-dismiss="modal" class="btn popclose">关闭</button>
								<button id="addBtn" type="button" class="btn popenter" href="nkyMonitoringSiteController.do?save" action-operation="popsave" action-mode="ajax"
									action-event="click" action-form="#saveForm" action-after="refresh_nkyMonitoringSiteList">保存</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
