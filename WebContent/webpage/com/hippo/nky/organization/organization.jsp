<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../context/mytags.jsp"%>

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
					<i class="icon-reorder"></i> <span class="hidden-480">质检机构信息维护</span>
				</div>
			</div>
			<div class="portlet-body">
				<div class="tab-content">
					<div class="tab-pane active" id="monitoring_plan_program_tab1">
						<form action="#" class="form-horizontal" name="saveForm" id="saveForm" validate="true">
						<input id="id" name="id" type="hidden" value="${organization.id }">
							<div class="control-group">
								<label class="control-label"><span style="color:red;">* </span>机构代码:</label>
								<div class="controls">
									<input id="" name="code" type="text" value="${organization.code}"
										class="m-wrap medium"  datatype="s2-32" ajaxurl="organizationController.do?isExsitCheck&flg=1&id=${organization.id }"/>
				
								</div>
							</div>
							<div class="control-group">
								<label class="control-label"><span style="color:red;">* </span>机构名称:</label>
								<div class="controls">
									<input id="" name="ogrname" type="text" value="${organization.ogrname}"
										class="m-wrap medium" datatype="s2-100" ajaxurl="organizationController.do?isExsitCheck&flg=2&id=${organization.id }"/>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">计量认定证书:</label>
								<div class="controls">
									<input id="" name="msmtcertificate" type="text" value="${organization.msmtcertificate}"
										class="m-wrap medium" datatype="s1-100" ignore="ignore" errormsg="请填写1到100位字符！(特殊字符除外)"/>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">机构考核证书:</label>
								<div class="controls">
									<input id="" name="inscertificate" type="text" value="${organization.inscertificate}"
										class="m-wrap medium" datatype="s1-100" ignore="ignore" errormsg="请填写1到100位字符！(特殊字符除外)"/>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label"><span style="color:red;">* </span>负责人:</label>
								<div class="controls">
									<input id="" name="leader" type="text" value="${organization.leader}"
										class="m-wrap medium" datatype="s2-32"/>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label"><span style="color:red;">* </span>负责人电话:</label>
								<div class="controls">
									<input id="" name="leadertel" type="text" value="${organization.leadertel}"
										class="m-wrap medium" datatype="s1-16"/>
								</div>
							</div>

							<div class="control-group">
								<label class="control-label"><span style="color:red;">* </span>联系人:</label>
								<div class="controls">
									<input id="" name="contacts" type="text" value="${organization.contacts}"
										class="m-wrap medium" datatype="s2-32"/>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label"><span style="color:red;">* </span>联系人电话:</label>
								<div class="controls">
									<input id="" name="contactstel" type="text" value="${organization.contactstel}"
										class="m-wrap medium" datatype="s1-16"/>
								</div>
							</div>

							<div class="control-group">
								<label class="control-label">电子邮箱:</label>
								<div class="controls">
									<input id="" name="email" type="text" value="${organization.email}"
										class="m-wrap medium" ignore="ignore" datatype="e"/>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">传真:</label>
								<div class="controls">
									<input id="" name="fax" type="text" value="${organization.fax}"
										class="m-wrap medium" datatype="s1-32" ignore="ignore" errormsg="请填写1到32位字符！(特殊字符除外)"/>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label"><span style="color:red;">* </span>所属区域:</label>
								<div id="areasDiv" class="controls">
									<t:dictSelect id="areacode" field="areacode" hasLabel="false" customData="${areacodeList}" defaultVal="${organization.areacode}" extend="{data-set:{value:'#areasDiv .areaSelect'},link-Change:{value:'true'},datatype:{value:'*'}}"></t:dictSelect>
									<t:dictSelect id="areacode2" field="areacode2" hasLabel="false" customData="${areacodeList2}" defaultVal="${organization.areacode2}"  extend="{class:{value:'small areaSelect'}}"></t:dictSelect>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">经度:</label>
								<div class="controls">
									<input id="longitude" type="text" name="longitude" value="${organization.longitude }"
										class="m-wrap medium" ignore="ignore" datatype="/^\d{1,3}(\.\d{1,6})?$/" errormsg="输入的格式不对或超出范围！" onchange="checkRange(this)"/>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">纬度:</label>
								<div class="controls">
									<input id="latitude" type="text" name="latitude" value="${organization.latitude }"
										class="m-wrap medium" ignore="ignore" datatype="/^\d{1,2}(\.\d{1,6})?$/" errormsg="输入的格式不对或超出范围！" onchange="checkRange(this)"/>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">邮编:</label>
								<div class="controls">
									<input id="" name="zipcode" type="text" value="${organization.zipcode}"
										class="m-wrap medium" ignore="ignore" datatype="/^\d{6}?$/" errormsg="请填写正确的信息！"/>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">地址:</label>
								<div class="controls">
									<textarea id="" name="address" class="m-wrap" rows="3" datatype="s1-1000" ignore="ignore" errormsg="请填写1到1000位字符！(特殊字符除外)">${organization.address}</textarea>
								</div>
							</div>

							<div class="modal-footer">
								<button type="button" data-dismiss="modal" class="btn popclose">关闭</button>
								<button id="addBtn" type="button" class="btn popenter" href="organizationController.do?save" action-operation="popsave" action-mode="ajax"
									action-event="click" action-form="#saveForm" action-after="refresh_organizationList">保存</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>