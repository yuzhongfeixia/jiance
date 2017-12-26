<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<script>
		jQuery(document).ready(function() {
			$('#searchBtn').on('click', function(){
				var dcode = $("#dcode").val();
				$.ajax({
					type : "POST",
					url : "barcodePrintController.do?complementsearch&rand=" + Math.random(),
					data : "dcode="+dcode,
					success : function(data) {
						var d = $.parseJSON(data);
						if (d.success) {
							$("#DCODE").html(d.obj.TITLE);
							$("#CNAME").html(d.obj.CNAME);
							$("#SAMPLINGDATE").html(d.obj.SAMPLING_DATE);
						} else {
							modalAlert(d.msg);
						}
					}
				});
			});
			//确定打印
			$modal.on('click', '#pBtn', function() {
				var DCODE = $('#DCODE').text();
				
				if(isEmpty(DCODE)){
					modalAlert("选择打印编码!");
					return;
				}
				window.open("barcodePrintController.do?printView1&dcode="+DCODE);//要新的窗口打开链接
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
						<i class="icon-reorder"></i> <span class="hidden-480">条码补打</span>
					</div>
				</div>
				<div class="portlet-body">
					<div class="tab-content">
						<div class="tab-pane active">
							<form action="#" class="form-horizontal">
								<div class="controls" style="margin: 10px 0 20px 5px;">
									<div style="padding-top: 0px; float: left;">
										<span class="help-inline">条码:&nbsp;&nbsp;</span>
									</div>
									<div style="padding-top: 0px; float: left;">
										<input class=" m-wrap" type="text" value="" name="dcode" id="dcode"/>
									</div>
									<a class="btn btngroup_seach" id="searchBtn" style="margin-left: 20px;"><i class="icon-search"></i>搜索</a> 
									<a class="btn btngroup_usual" id="pBtn" style="margin-left: 20px;"><i class="icon-print"></i>打印</a>
									<span class="help-inline">注:只有与抽样信息已经关联的条码才能被补打</span>
								</div>
								<table class="table  table-bordered styletd1"
									style="margin: 0 10px 0 10px; width: 730px;">
									<tr>
										<td width="100px;">条码</td>
										<td id=DCODE></td>
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