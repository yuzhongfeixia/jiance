<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webpage/main/navigator.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="assets/plugins/data-tables/DT_bootstrap.css" />
<script type="text/javascript" src="assets/plugins/data-tables/jquery.dataTables.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/DT_bootstrap.js"></script>

<script src="assets/plugins/bootstrap-modal/js/bootstrap-confirm.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript"></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript"></script>
<link href="assets/plugins/bootstrap-modal/css/bootstrap-modal.css"	rel="stylesheet" type="text/css" />
</head>
<body class="page-header-fixed">
	<div class="row-fluid">
		<div class="span12">
			<div class="portlet box box_usual">
				<div class="portlet-title">
					<div class="caption"><i class="icon-globe"></i>条码打印列表</div>
				</div>
				<div class="portlet-body">
					<div class="clearfix">
						<div class="pull-right">
							<a id="ComPlayBtn" class="btn btngroup_usual" data-toggle="modal"><i class="icon-print"></i>条码补打</a>
						</div>
					</div>
					<table class="table table-striped table-bordered table-hover" id="lst_table">
						<thead>
							<tr>	
								<th class="hidden-480">序号</th>
								<th class="hidden-480">项目名称</th>
								<th class="hidden-480">监测时间</th>
								<th class="hidden-480">抽样数量</th>
								<th class="hidden-480">打印数量</th>
								<th class="hidden-480">操作</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

	<div id="ajax-modal" class="modal hide fade" tabindex="-1"></div>
	<script>
		var oTable;
		// modal窗体
		$.fn.modalmanager.defaults.spinner = '<div class="loading-spinner fade" style="width: 200px; margin-left: -100px;"><img src="assets/img/ajax-modal-loading.gif" align="middle">&nbsp;<span style="font-weight:300; color: #eee; font-size: 18px; font-family:Open Sans;">&nbsp;Loading...</div>';
	   	var $modal = $('#ajax-modal'); 
		jQuery(document).ready(function() {
			oTable = registAjaxDataTable({
				id : "lst_table",
				actionUrl : "barcodePrintController.do?datagrid&rand=" + Math.random(),
				aoColumns : [
							{
								"mData" : "rn",
								"bSortable" : false
							},
							{
								"mData" : "name"
							},
							{
								"mData" : "starttime"
							},
							{
								"mData" : "sumcount"
							},
							{
								"mData" : "printCount"
							},
							{
								"mData" : 'projectCode',
								bSortable : false,
								bSearchable : false,
								"mRender" : function(data, type, rec) {
//									if(isCheckedAuth('printBtn')){
										return  '&nbsp; <a id="printBtn" class="btn green mini" data-toggle="modal" onclick="printCode(\''+data+'\',\''+rec.barcodeId+'\')\"><i class="icon-print"></i>打印条码</a>';
//									}
								}
							} ],
					search : true,
					aoColumnDefs : [ {
						'bSortable' : false,
						'aTargets' : [0,1,2]
					} ]
				});
		});
		
		//刷新列表  fatherRefreshList
		function fatherRefreshList() {  
			$("#lst_table").dataTable().fnPageChange('first');  
		}  
		
		//条码补打
		$('#ComPlayBtn').on('click', function(){
			var pageContent = $('.page-content');
		   	App.blockUI(pageContent, false);
		    $modal.load('barcodePrintController.do?complementplay', '', function(){
		     $modal.modal();
		     App.unblockUI(pageContent);
		   });
		});
		
		//打印条码
		function printCode(projectCode,barcodeId) {
			var pageContent = $('.page-content');
		   	App.blockUI(pageContent, false);
			$modal.load('barcodePrintController.do?addorupdate&projectCode='+projectCode+'&id='+barcodeId, '', function(){
		    	$modal.modal({width:"600px"});
		    	App.unblockUI(pageContent);
		    });
		}

		//确定打印
		$modal.on('click', '#printBtn', function() {
			var rtnBool = registerForm({
			  	id : "addForm",
				rules : {
					"printNumberCopies" : {
						minlength : 1,
						maxlength : 10,
						digits:true,
						required : true
					},
					"printCount" : {
						minlength : 1,
						maxlength : 10,
						digits:true,
						required : true
					}
				},
				messages : {
					printNumberCopies : {
						required : "打印标签份数范围在1~10位字符，注：3个贴样品，1个贴抽样单"
					},
					printCount : {
						required : "打印标签个数范围在1~10位字符"
					}
				}
			});

// 			if(rtnBool){
// 				var formData = $('#addForm').serialize();
// 				//生成二维码
// 				$.ajax({
// 					type : "POST",
// 					url : "barcodePrintController.do?save&rand=" + Math.random(),
// 					data : formData,
// 					success : function(data) {
// 						var d = $.parseJSON(data);
// 						if (d.success) {
// 							window.open("barcodePrintController.do?printView&projectCode="+d.obj.projectCode+"&titles="+d.obj.titles+"&printNumberCopies="+d.obj.copies,"_blank");//要新的窗口打开链接
// 							$modal.modal('hide');
// 							fatherRefreshList();
// 						} else {
// 							modalAlert(d.msg);
// 						}
// 					}
// 				});
// 			}

			if(rtnBool){
				$modal.modal('hide');
				//var formData = $('#addForm').serialize();
				var id = $("#id").val();
				var projectCode = $("#projectCode").val();
				var printNumberCopies = $("#printNumberCopies").val();
				var printCount = $("#printCount").val();
				window.open("barcodePrintController.do?printView&projectCode="+projectCode+"&id="+id+"&printNumberCopies="+printNumberCopies+"&printCount="+printCount,"_blank");//要新的窗口打开链接
	
			}
			else{
				return;
			}
		});
	</script>
</body>
</html>