<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<script>
	var isSampleNameChange = ${isSampleNameChange};
	jQuery(document).ready(function() {
		if (isSampleNameChange) {
			$("#confirmText").text("修改了样品名称，已有检测信息将作废，是否确认提交？");
		} else {
			$("#confirmText").text("是否确认提交？");
		}
		$("#confirmText").css({"font-size" : "15px"});
		
	});

	
	function colseSamplingModal() {
		$("button[class='btn popenter']").attr("disabled",true);
		var $modal = $('#ajax-modal');
		$modal.modal('hide');
	}

</script>
</head>
<body>
	<div class="row-fluid">
		<div class="span12">
			<div class="portlet box popupBox_usual">
				<div class="portlet-title">
					<div class="caption">
						<i class="icon-reorder"></i> <span class="hidden-480">请确认操作</span>
					</div>
				</div>
				<div class="portlet-body">
					<div class="tab-content">
						<div class="tab-pane active">
							<div>
								<p id="confirmText"></p>
							</div>
							<div class="modal-footer">
								<button type="button" data-dismiss="modal" class="btn popclose">关闭</button>
								<button type="button" data-dismiss="modal" class="btn popenter green" href="samplingInfoController.do?save&vdcode=${vdcode}" action-mode="ajax" action-event="click" action-operation="popsave"
								 action-form="#saveForm"  action-before="colseSamplingModal" action-load="正在保存信息..." action-async="true" action-after="refresh_SamplingSetBack2">确认</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>