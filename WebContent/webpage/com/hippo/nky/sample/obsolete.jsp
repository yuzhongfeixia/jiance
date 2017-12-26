<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<script>
		jQuery(document).ready(function() {
			$('#sBtn').on('click', function(){
				var dcode = $("#dcode").val();
				$.ajax({
					type : "POST",
					url : "samplingInfoController.do?obsoleteSearch&rand=" + Math.random(),
					data : "dcode="+dcode,
					success : function(data) {
						var d = $.parseJSON(data);
						if (d.success) {
							$("#nsiid").val(d.obj.ID);
							$("#DCODE").html(d.obj.D_CODE);
							$("#NAME").html(d.obj.NAME);
							$("#CNAME").html(d.obj.CNAME);
							$("#SAMPLINGDATE").html(d.obj.SAMPLING_DATE);
							$("#removeBtn").removeAttr("disabled"); 
						} else {
							modalAlert(d.msg);
						}
					}
				});
			});
			//确定打印
			$modal.on('click', '#removeBtn', function() {
				var disabled = $("#removeBtn").attr("disabled");
				if(disabled != undefined && disabled == disabled){
					return;
				}
				var DCODE = $('#DCODE').text();	
				if(isEmpty(DCODE)){
					modalAlert("请选择废除样品！");
					return;
				}
				var id = $("#nsiid").val();
				$("#confirmDiv").confirmModal({
					heading: '请确认操作',
					body: '确定要废除此样品么，废除后不在系统中显示',
					callback: function () {
						$.ajax({
							type : "POST",
							url : "samplingInfoController.do?obsoleteUpdate&rand=" + Math.random(),
							data : "id="+id,
							success : function(data) {
								var d = $.parseJSON(data);
								if (d.success) {
									modalTips(d.msg);	
									$modal.modal('hide');
									refreshListToFirst($('#obsoleteTable'));
								} else {
									modalAlert(d.msg);
								}
							}
						});
					}
				});
			});
		});
	</script>
</head>
<body>
	<div class="row-fluid">
		<div class="span12">
			<div class="portlet box popupBox_usual">
				<div class="portlet-title">
					<div class="caption">
						<i class="icon-reorder"></i> <span class="hidden-480">样品废除</span>
					</div>
				</div>
				<div class="portlet-body">
					<div class="tab-content">
						<div class="tab-pane active">
							<form action="#" class="form-horizontal">
								<input type="hidden" value="" id="nsiid">
								<div class="controls"  style="margin: 10px 0 10px 5px;">
									<div style="padding-top: 0px; float: left;">
										<span class="help-inline">条码:&nbsp;&nbsp;</span>
									</div>
									<div style="padding-top: 0px; float: left;">
										<input class=" m-wrap" type="text" value="" name="dcode" id="dcode"/>
									</div>
									<a class="btn btngroup_seach" id="sBtn" style="margin-left:5px;"><i class="icon-search"></i>搜索</a>
									<a class="btn btngroup_delete" id="removeBtn" style="margin-left:5px;" disabled="disabled"><i class="icon-trash"> 废除</i></a>
								</div>
								<table class="table  table-bordered styletd1"
									style="margin: 0 10px 0 10px; width: 600px;">
									<tr>
										<td width="100px;">条码</td>
										<td id=DCODE></td>
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