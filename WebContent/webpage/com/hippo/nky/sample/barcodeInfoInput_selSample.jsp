<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style type="text/css">
.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
.ztree li span.button.diy1_ico_open{margin-right:2px; background: url("assets/img/icons/bullet_orange.png") no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.diy1_ico_close{margin-right:2px; background: url("assets/img/icons/bullet_orange.png") no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.diy1_ico_docu{margin-right:2px; background: url("assets/img/icons/bullet_orange.png") no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.diy2_ico_open{margin-right:2px; background: url("assets/img/icons/bullet_green.png") no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.diy2_ico_close{margin-right:2px; background: url("assets/img/icons/bullet_green.png") no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.diy2_ico_docu{margin-right:2px; background: url("assets/img/icons/bullet_green.png") no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
</style>
<link rel="stylesheet" href="assets/plugins/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script src="assets/scripts/ui-modals.js"></script>
<script type="text/javascript" src="assets/plugins/zTree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="assets/plugins/zTree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="assets/plugins/zTree/js/jquery.ztree.exedit-3.5.js"></script>


<script>
	var view = {addHoverDom: null,
		removeHoverDom: null,
		selectedMulti: false};
	var setting = {
			view: view,

			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				//beforeDrag: beforeDrag,
				//beforeEditName: beforeEditName,
				//beforeRemove: beforeRemove,
				//beforeRename: beforeRename,
				//onRemove: onRemove,
				//onRename: onRename,
				onClick: zTreeOnClick
			}
		};
	var zNodes = ${zTreeData};
	$(document).ready(function(){
		$.fn.zTree.init($("#sampleTree"), setting, zNodes);
	});
	
	function zTreeOnClick(event, treeId, treeNode) {
		 if (treeNode.children == null) {
			 $("#selectednodeId").val(treeNode.code);
			 $("#selectednodeName").val(treeNode.name);
		 }
		 
	}
	
	function comfirma(data) {
		
		var treeObj = $.fn.zTree.getZTreeObj("sampleTree");
		var sNodes = treeObj.getSelectedNodes();
		if (sNodes.length >0) {
			if (sNodes[0].children != null) {
				modalAlert("请选择一个叶子节点！");
				return false;
			}
		    
		    data.params["code"] = $("#selectednodeId").val();
		    data.params["name"] = $("#selectednodeName").val();
// 		    asibling[0].val($("#selectednodeId").val());
// 		    asibling[1].val($("#selectednodeName").val());

// 		 	var $modal = $('#ajax-modal1');
// 		 	$modal.modal('hide');
		} else {
			modalAlert("您还没有选择一个样品！");
			return false;
		}
		return data;
	}
</script>

<div class="row-fluid">
	<div class="span12">
		<div class="portlet box popupBox_usual">
			<div class="portlet-title">
				<div class="caption">
					<i class="icon-reorder"></i> <span class="hidden-480">样品信息</span>
				</div>
			</div>
			<div class="portlet-body">
				<div class="tab-content">
					<div class="tab-pane active" id="monitoring_plan_program_tab1">
						<form action="#" class="form-horizontal" name="saveForm" id="saveForm">
							<input id="selectednodeId" type="hidden"/>
							<input id="selectednodeName" type="hidden"/>
							<div id="sampleTreeDiv" class="portlet-body">
								<ul class="ztree" id="sampleTree">
								</ul>
							</div>
							<div class="modal-footer">
								<button type="button" data-dismiss="modal" class="btn popclose">关闭</button>
								<button id="" type="button" class="btn popenter" action-mode="ajax"  action-before="comfirma" action-after="callSetId" action-operation="popsave" action-pop="ajax-modal1">保存</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>