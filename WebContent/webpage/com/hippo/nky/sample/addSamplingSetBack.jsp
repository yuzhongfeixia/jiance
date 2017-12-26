<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<script>
		jQuery(document).ready(function() {
			//$("#setBackBtn").attr("disabled", true);
			if ($("#dcode").val() != '') {
				getSamplingInfo();
			}
		});

		$('#sBtn').on('click', function(){
			if ( $("#dcode").val() == '') {
				return;
			}
			getSamplingInfo();
		});
		
		function getSamplingInfo(){
			$("#setBackBtn").attr("disabled", true);
			var dcode = $("#dcode").val();
			$.ajax({
				type : "POST",
				url : "samplingInfoController.do?getAddSetBackData",
				data : "dcode="+dcode,
				success : function(data) {
					var d = $.parseJSON(data);
					if (d.success) {
						var attr = d.attributes;
						$("#DCODE").html(attr.D_CODE);
						$("#NAME").html(attr.NAME);
						$("#CNAME").html(attr.CNAME);
						$("#SAMPLINGDATE").html(attr.SAMPLING_DATE);
						if (checkSamplingSetBack(dcode)) {
							$("#setBackBtn").attr("disabled", false);
						}
						
					} else {
						modalAlert(d.msg);
						$("#DCODE").html("");
						$("#NAME").html("");
						$("#CNAME").html("");
						$("#SAMPLINGDATE").html("");
					}
				}
			});
			
		}
		
		$('#setBackBtn').on('click', function(){
			var btn_enable = $("#setBackBtn").attr("disabled");
			// 按钮如果是不可用的则不可点击
			if("disabled" == btn_enable || false == btn_enable){
				return;
			}
			
			var dcode = $("#DCODE").text();
			$("#confirmDiv").confirmModal({
				heading: '请确认操作',
				body: '确认申请退回该抽样信息？',
				callback: function () {
					$.ajax({
						type : "POST",
						url : "samplingInfoController.do?doSamplingSetBack",
						data : {'dcode' : dcode, 'id' : $("#id").val()},
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
		
		function checkSamplingSetBack(data){
			var isNotSetBack = true;
			$.ajax({
				async: false,
				type : "POST",
				url : "samplingInfoController.do?checkSamplingSetBack",
				data : "dcode="+data,
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
								<div class="controls"  style="margin: 10px 0 10px 5px;">
									<div style="padding-top: 0px; float: left;">
										<span class="help-inline">条码:&nbsp;&nbsp;</span>
									</div>
									<div style="padding-top: 0px; float: left;">
										<input class=" m-wrap" type="text" name="dcode" id="dcode" value="${code}"/>
									</div>
									<a class="btn btngroup_seach" id="sBtn" style="margin-left:5px;"><i class="icon-search"></i>搜索</a>
									<a class="btn btngroup_delete" id="setBackBtn" style="margin-left:5px;" disabled><i class="icon-trash">申请退回</i></a>
								</div>
								<table class="table  table-bordered styletd1"
									style="margin: 0 10px 0 10px; width: 600px;">
									<tr>
										<td width="100px;">条码</td>
										<td id="DCODE"></td>
									</tr>
									<tr>
										<td>监测项目</td>
										<td id="NAME"></td>
									</tr>
									<tr>
										<td>样品名称</td>
										<td id="CNAME"></td>
									</tr>
									<tr>
										<td>抽样时间</td>
										<td id="SAMPLINGDATE"></td>
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