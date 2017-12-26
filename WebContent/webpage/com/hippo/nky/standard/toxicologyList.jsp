<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webpage/main/navigator.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="assets/plugins/data-tables/DT_bootstrap.css" />
<script type="text/javascript" src="assets/plugins/data-tables/jquery.dataTables.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/DT_bootstrap.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/jquery.jeditable.js"></script>

<script src="assets/plugins/bootstrap-modal/js/bootstrap-confirm.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript"></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript"></script>
<link href="assets/plugins/bootstrap-modal/css/bootstrap-modal.css"	rel="stylesheet" type="text/css" />
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-header-fixed">
	<div class="row-fluid">
		<div class="span12">
			<!-- BEGIN EXAMPLE TABLE PORTLET-->
			<div class="portlet box box_usual">
				<div class="portlet-title">
					<div class="caption">
						<i class="icon-globe"></i>毒理学
					</div>
				</div>
				<div class="portlet-body">
					<div class="alert alert-success">
						<form action="#" name="searchForm" id="searchForm" class="form-horizontal">
							<div class="clearfix">
								<div class="table-seach">
									<label class="help-inline seach-label">CAS编码 :</label>
									<div class="seach-element">
										<input type="text" placeholder="" class="m-wrap small"
											name="casCode" id="casCode">
									</div>
								</div>
								<div class="table-seach">
									<label class="help-inline seach-label">中文通用名称:</label>
									<div class="seach-element">
										<input type="text" placeholder="" class="m-wrap small"
											name="commonCname" id="commonCname">
									</div>
								</div>
								<div class="table-seach">
									<label class="help-inline seach-label">英文通用名称:</label>
									<div class="seach-element">
										<input type="text" placeholder="" class="m-wrap small"
											name="commonEname" id="commonEname">
									</div>
								</div>
								<div class="table-seach">
									<label class="help-inline seach-label">中文化学名称:</label>
									<div class="seach-element">
										<input type="text" placeholder="" class="m-wrap small"
											name="chemicalCname" id="chemicalCname">
									</div>
								</div>
								<div class="table-seach">
									<label class="help-inline seach-label">英文化学名:</label>
									<div class="seach-element">
										<input type="text" placeholder="" class="m-wrap small"
											name="chemicalEname" id="chemicalEname">
									</div>
								</div>
							</div>
						</form>
					</div>
					<div class="clearfix">
						<div class="btn-group">
							<t:authFilter name="ajax_add_btn"><a class="btn btngroup_usual" data-toggle="modal" id="addBtn"><i class="icon-plus"></i>新增</a></t:authFilter>
						</div>
						<div class="pull-right">
							<t:authFilter name="searchBtn">
								<a href="#" class="btn btngroup_seach" id="searchBtn"><i
									class="icon-search"></i>搜索</a>
							</t:authFilter>
<%-- 							<t:authFilter name="resetBtn"> --%>
<!-- 								<button type="button" class="btn btngroup_usual" -->
<!-- 									onclick="reset();" id="resetBtn"> -->
<!-- 									<i class="icon-reload"></i>重置 -->
<!-- 								</button> -->
<%-- 							</t:authFilter> --%>
						</div>
					</div>
					<table class="table table-striped table-bordered table-hover" id="lst_table">
						<thead>
							<tr>
								<th>CAS编码</th>
								<th class="hidden-480">中文通用名</th>
								<th class="hidden-480">英文通用名</th>
								<th class="hidden-480">中文化学名称</th>
								<th class="hidden-480">英文化学名称</th>
								<th class="hidden-480">操作</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</div>
			<!-- END EXAMPLE TABLE PORTLET-->
		</div>
	</div>

	<div id="ajax-modal" class="modal hide fade" tabindex="-1"></div>
	<div id="confirmDiv" class="modal hide fade" tabindex="-2"></div>
	<script>
		var oTable;
		// modal窗体
		$.fn.modalmanager.defaults.spinner = '<div class="loading-spinner fade" style="width: 200px; margin-left: -100px;"><img src="assets/img/ajax-modal-loading.gif" align="middle">&nbsp;<span style="font-weight:300; color: #eee; font-size: 18px; font-family:Open Sans;">&nbsp;Loading...</div>';
	   	var $modal = $('#ajax-modal'); 
		jQuery(document).ready(function() {
			oTable = registAjaxDataTable({
				id : "lst_table",
				actionUrl : "toxicologyController.do?datagrid&rand=" + Math.random(),
				aoColumns : [
							{
								"mData" : "casCode"
							},
							{
								"mData" : "commonCname"
							},
							{
								"mData" : "commonEname"
							},
							{
								"mData" : "chemicalCname"
							},
							{
								"mData" : "chemicalEname"
							},
							{
								"mData" : 'id',
								bSortable : false,
								bSearchable : false,
								"mRender" : function(data, type, rec) {
									var rtnStr = '';
								//	if(isCheckedAuth('updateBtn')){
										rtnStr += '<a class="btn mini yellow" id="updateBtn" onclick="update2(\''+ data+ '\')\">编辑</a>';
									//}
							
						//			if(isCheckedAuth('delBtn')){
										rtnStr += '&nbsp;<a class="btn mini yellow" id="delBtn" onclick="del2(\''+ data+ '\')\">删除</a>';
							//		}
									return rtnStr;
								}
							} ],
					search : true,
					aoColumnDefs : [ {
						'bSortable' : false,
						'aTargets' : [0,1,2,3,4]
					} ]
				});
		});
		
		//刷新列表  selfRefreshList
		function selfRefreshList() {  
			$("#lst_table").dataTable().fnPageChange('first');  
		}  

		//表单搜索
		$('#searchBtn').on('click', function(){
 			setQueryParams('lst_table',$('#searchForm').getFormValue());
			selfRefreshList();
		});
		
		//重置
		function reset() {
			$("#casCode").val("");
			$("#commonCname").val("");
			$("#commonEname").val(''); 
			$('#chemicalCname').val('');
			$('#chemicalEname').val('');
		}
		
		//添加跳转
		$('#addBtn').on('click', function(){
			var pageContent = $('.page-content');
			App.blockUI(pageContent, false);
			$modal.load('toxicologyController.do?addorupdate', '', function(){
			     $modal.modal({width:"1000px"});
			     App.unblockUI(pageContent);
			   });
		});
		
		function update2(data) {
			//修改
			var pageContent = $('.page-content');
			App.blockUI(pageContent, false);
		     $modal.load('toxicologyController.do?addorupdate&id='+data, '', function(){
		      $modal.modal({width:"1000px"});
		      App.unblockUI(pageContent);
		    });
		}

		
		//删除信息
		function del2(data) {
			$("#confirmDiv").confirmModal({
				heading: '请确认操作',
				body: '你确认删除所选记录?',
				callback: function () {
					$.ajax({
						type : "POST",
						url : "toxicologyController.do?del&rand=" + Math.random(),
						data : "id=" + data,
						success : function(data) {
							 var d = $.parseJSON(data);
				   			 if (d.success) {
				   				modalTips(d.msg);	
				   				 selfRefreshList();
				   			 }else {
				   				modalAlert(d.msg);
				   			 }
						}
					});
				}
			});
		}
		
		//毒理学添加
		$modal.on('click', '#add_btn', function() {
			var casCode = $('#cCode').val();
			if(isEmpty(casCode)){
				modalAlert("CAS码不能为空，请输入！");
				return;
			}
			
			var formData = $('#addForm').serialize();
			// 保存
			$.ajax({
				type : "POST",
				url : "toxicologyController.do?save&rand=" + Math.random(),
				data : formData,
				success : function(data) {
					var d = $.parseJSON(data);
					if (d.success) {
						modalTips(d.msg);
						$modal.modal('hide');
						selfRefreshList();
					} else {
						modalAlert(d.msg);
					}
				}
			});
		});
	</script>
</body>
</html>
