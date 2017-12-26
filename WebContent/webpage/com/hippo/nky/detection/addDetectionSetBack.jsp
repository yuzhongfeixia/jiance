<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<script>
		jQuery(document).ready(function() {
			//$("#setBackBtn").attr("disabled", true);
			if ($("#labCode").val() != '' || $("#dCode").val() != '') {
				getSamplingInfo();
			}
		});

		$('#sBtn').on('click', function(){
			if ( $("#labCode").val() == '' && $("#dCode").val() == '') {
				return;
			}
			getSamplingInfo();
		});
		
		function getSamplingInfo(){
			$("#setBackBtn").attr("disabled", true);
			var labCode = $("#labCode").val();
			var dCode = $("#dCode").val();
			$.ajax({
				type : "POST",
				url : "detectionController.do?getAddSetBackData",
// 				data : "labCode="+labCode,
				data : {'labCode':labCode,'dCode':dCode},
				success : function(data) {
					var d = $.parseJSON(data);
					if (d.success) {
						var attr = d.attributes;
						var detectionInfoList = d.attributes.detectionInfo;
						$("#LABCODE").html(attr.LAB_CODE);
						if (attr.SP_CODE == null) {
							$("#SPCODE").html(attr.D_CODE);
						} else {
							$("#SPCODE").html(attr.SP_CODE);
						}
						$("#CNAME").html(attr.CNAME);
						
						var htmls = "<table style='border:none'>"
						var outtext = "";
						for (var i=0; i<detectionInfoList.length; i++) {
							if (detectionInfoList[i].detectionValue == 0) {
								outtext = "未检出";
							} else if (detectionInfoList[i].detectionValue < 0) {
								outtext = "未检";
							} else {
								outtext = detectionInfoList[i].detectionValue;
							}
							htmls += "<tr><td style='border:none'>"+detectionInfoList[i].pollName+"&nbsp;&nbsp;&nbsp;&nbsp;"+outtext+"</td></tr>";
						}
						htmls += "</table>";
						$("#POLL").html(htmls);
						
						if (checkDetectionSetBack(labCode)) {
							$("#setBackBtn").attr("disabled", false);
						}
						$("#ajax-modal").modal('layout');
					} else {
						modalAlert(d.msg);
						$("#LABCODE").html("");
						$("#SPCODE").html("");
						$("#CNAME").html("");
						$("#POLL").html("");
						$("#ajax-modal").modal('layout');
					}
				}
			});
			//$("#ajax-modal").modal('layout');
		}
		
		$('#setBackBtn').on('click', function(){
			var btn_enable = $("#setBackBtn").attr("disabled");
			// 按钮如果是不可用的则不可点击
			if("disabled" == btn_enable || false == btn_enable){
				return;
			}
			
			var labCode = $("#LABCODE").text();
			$("#confirmDiv").confirmModal({
				heading: '请确认操作',
				body: '确认申请退回该检测信息？',
				callback: function () {
					$.ajax({
						type : "POST",
						url : "detectionController.do?doDetectionSetBack",
						data : {'labCode' : labCode, 'id' : $("#id").val()},
						success : function(data) {
							var d = $.parseJSON(data);
							if (d.success) {
								modalTips(d.msg);
								$("#sample_obsolete_tb1").dataTable().fnPageChange('first');  
								$("#setBackBtn").attr("disabled", true);
							} else {
								modalAlert(d.msg);
							}
						}
					});
					
				}
			});
			
		});
		
		function checkDetectionSetBack(data){
			var isNotSetBack = true;
			$.ajax({
				async: false,
				type : "POST",
				url : "detectionController.do?checkDetectionSetBack",
				data : "labCode="+data,
				success : function(data) {
					var d = $.parseJSON(data);
					isNotSetBack = d.success;
				}
			});
			return isNotSetBack;
		}

</script>
</head>
<body>
	<div class="row-fluid">
		<div class="span12">
			<div class="portlet box popupBox_usual">
				<div class="portlet-title">
					<div class="caption">
						<i class="icon-reorder"></i> <span class="hidden-480">添加退回申请</span>
					</div>
				</div>
				<div class="portlet-body">
					<div class="tab-content">
						<div class="tab-pane active">
							<form action="#" class="form-horizontal">
							    <input type="hidden" value="${id}" id="id">
								<input type="hidden" value="" id="nsiid">
								<div class="controls"  style="margin: 10px 0 10px 5px;">
									<div style="padding-top: 0px; float: left;">
										<span class="help-inline">样品条码:&nbsp;&nbsp;</span>
									</div>
									<div style="padding-top: 0px; float: left;">
										<input class=" m-wrap" type="text" name="dCode" id="dCode" value="${dCode}"/>
									</div>
									<div style="padding-top: 0px; float: left;">
										<span class="help-inline">实验室编码:&nbsp;&nbsp;</span>
									</div>
									<div style="padding-top: 0px; float: left;">
										<input class=" m-wrap" type="text" name="labCode" id="labCode" value="${labCode}"/>
									</div>
									<a class="btn btngroup_seach" id="sBtn" style="margin-left:5px;"><i class="icon-search"></i>搜索</a>
									<a class="btn btngroup_delete" id="setBackBtn" style="margin-left:5px;" disabled><i class="icon-trash">申请退回</i></a>
								</div>
								<table class="table  table-bordered styletd1"
									style="margin: 0 10px 0 10px; width: 600px;">
									<tr>
										<td width="150px;">实验室编码</td>
										<td id="LABCODE"></td>
									</tr>
									<tr>
										<td>样品条码/制样编码</td>
										<td id="SPCODE"></td>
									</tr>
									<tr>
										<td>样品名称</td>
										<td id="CNAME"></td>
									</tr>
									<tr>
										<td>污染物</td>
										<td id="POLL"></td>
									</tr>
								</table>
								<div class="modal-footer">
									<button type="button" data-dismiss="modal" class="btn popclose">关闭</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>