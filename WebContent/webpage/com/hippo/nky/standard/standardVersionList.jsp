<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webpage/main/navigator.jsp" %>
<link rel="stylesheet" type="text/css" href="assets/plugins/select2/select2_metro.css" />
<link rel="stylesheet" href="assets/plugins/data-tables/DT_bootstrap.css" />
<link href="assets/plugins/bootstrap-datepicker/css/datepicker.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="assets/plugins/data-tables/css/TableTools.css" />

<script type="text/javascript" src="assets/plugins/select2/select2.min.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/jquery.dataTables.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/DT_bootstrap.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/TableTools.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/ZeroClipboard.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/DT_bootstrap.js"></script>
<!-- <script src="assets/scripts/table-managed.js"></script> -->
<script src="assets/scripts/ui-modals.js"></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-confirm.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript" ></script>
<link href="assets/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css"/>
<link href="assets/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>
<!-- END HEAD -->
<script src="assets/scripts/ui-jqueryui.js"></script> 

<script src="assets/js/curdtools.js" type="text/javascript" ></script>

<!-- BEGIN BODY -->
	<div class="row-fluid">
		<div class="span12">
			<!-- BEGIN EXAMPLE TABLE PORTLET-->
			<div class="portlet box box_usual">
				<div class="portlet-title">
					<div class="caption"><i class="icon-globe"></i></div>
<!-- 								<div class="tools"> -->
<!-- 									<a href="javascript:;" class="collapse"></a> -->
<!-- 									<a href="#portlet-config" data-toggle="modal" class="config"></a> -->
<!-- 									<a href="javascript:;" class="reload"></a> -->
<!-- 									<a href="javascript:;" class="remove"></a> -->
<!-- 								</div> -->
				</div>
				<div class="portlet-body">
					<div class="alert alert-success">
						<form action="#" id="searchForm" class="form-horizontal">
							<div class="clearfix">
								<div class="table-seach">
									<label class="help-inline seach-label">版本名称:</label>
									<div class="seach-element">
										<input type="text" placeholder="" class="m-wrap small"  id="cname" name="cname">
									</div>
								</div>
								<div class="table-seach">
									<label class="help-inline seach-label">发布时间:</label>
									<div class="seach-element">
										<input class="m-wrap small m-ctrl-medium date-picker" type="text"  id="begindate_begin" name="begindate_begin"/>
										<label class="help-inline">～</label>
										<input class="m-wrap small m-ctrl-medium date-picker" type="text" id="begindate_end" name="begindate_end"/>
									</div>
								</div>
							</div>
						</form>
					</div>
					<div class="clearfix">
<!-- 						<div class="btn-group">
							<a id="confirm_opener" class="btn btngroup_usual" onclick="dataSynchronization()"><i class="icon-random"></i>同步数据 </a>
						</div> --> 
						<c:if test="${userType eq 0 || (userType ne 0 && category ne 2)}">
							<div class="btn-group">
								<a id="create_standard" class="btn btngroup_usual" ><i class="icon-plus"></i>新建标准 </a>
							</div>
						</c:if>
						<c:if test="${category ne 2}">
							<div class="btn-group">
								<a id="copy_standard" class="btn btngroup_usual" onclick="standardversionlist_copyData();"><i class="icon-random"></i>更新标准</a>
							</div>
						</c:if>
						<div class="pull-right">
							<a class="btn btngroup_seach"  onclick="dataTabelSearch('ncpflbz','searchForm')"><i class="icon-search"></i>搜索</a>
<!-- 										<a href="#" class="btn green"><i class="icon-share"></i> 导出Excel</a> -->
						</div>
					</div>
					<table class="table table-striped table-bordered table-hover" id="ncpflbz">
						<thead>
							<tr>
<!-- 											<th style="width:8px;"><input type="checkbox" class="group-checkable" data-set="#sample_1 .checkboxes" /></th> -->
								<th class="hidden-480">版本名称</th>
								<th class="hidden-480">发布机构</th>
								<th class="hidden-480">发布日期</th>
								<th class="hidden-480">发布标识</th>
								<th class="hidden-480">状态</th>
								<th class="hidden-480"></th>
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
		
		
		<div id="flbz" class="modal hide fade" tabindex="-1" data-width="560">
		<div class="row-fluid">
			<div class="span12">  
				<div class="portlet box popupBox_usual">
					<div class="portlet-title">
						<div class="caption">
							<i class="icon-reorder"></i>
							<span class="hidden-480">版本信息</span>
						</div>
					</div>
					<div class="portlet-body">
		                  <div class="tab-content">
							<div class="tab-pane active" id="monitoring_plan_program_tab1">
								<form action="#" class="form-horizontal">
									<div class="control-group">
										<label class="control-label">版本名称:</label>
										<div class="controls">
											<input id="monitoring_plan_program_txt1" type="text" class="m-wrap medium" value="农产品分类标准2013" readonly="readonly"/>
											<span class="help-inline"></span>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">发布机构:</label>
										<div class="controls">
											<input id="monitoring_plan_program_txt2" type="text" class="m-wrap medium" value="国家农产品质量安全检测信息平台" readonly="readonly"/>
											<span class="help-inline"></span>
										</div>
									</div>
									<div class="modal-footer">
										<button type="button" data-dismiss="modal" class="btn popclose">关闭</button>
<!-- 										<button id="monitoring_plan_program_btn5" type="button" class="btn popenter" onclick="">保存</button> -->
									</div>
								</form> 
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
		
		<div id="dialog_info" title="Download complete" class="hide">
			<p>
				<span class="icon icon-bullhorn"></span>
				Your files have downloaded successfully into the My Downloads folder.
			</p>
			Currently using 36% of your storage space.
			<div class="progress progress-striped">
				<div style="width: 36%;" class="bar"></div>
			</div>
		</div>
		
		<div id="dialog_confirm" title="是否要同步部级平台数据?" class="hide">
				<p>
<!-- 					<span class="icon icon-warning-sign"></span> -->
<!-- 					是否要同步部级平台数据? -->
				</p>
		</div>
		
		<div id="dialog_confirm_del" title="是否要同删除此条数据?" class="hide">
				<p>
				</p>
		</div>
		
		
		<div id="ajax-modal" class="modal hide fade" tabindex="-1"></div>
<script>

	$('.date-picker').datepicker({
	    rtl : App.isRTL(),
	    language: "zh",
	    autoClose: true,
	    format: "yyyy-mm-dd",
	    todayBtn: true,
	    clearBtn:true
	});
	var $modal = $('#ajax-modal');
	jQuery(document).ready(function() {
	   var userType = "${userType}";
	   var category = "${category}";
	   var buttonData = [
							{
								"className":"btn mini yellow",
								"onclick":"standardversionlist_toInfo('this',id,category)",
								"buttonName":"详情",
								"dataAuthority":[{"publishmark":"0,1","stopflag":"0,1"}]
							},{
								"className":"btn mini yellow",
								"onclick":"standardversionlist_stop(id)",
								"buttonName":"停用",
								"dataAuthority":[{"publishmark":"1","stopflag":"0"}]
							},{
								"className":"btn mini yellow",
								"onclick":"standardversionlist_publish(id)",
								"buttonName":"发布",
								"dataAuthority":[{"publishmark":"0","stopflag":"0"}]
							},{
								"className":"btn mini yellow",
								"onclick":"dataTableUpdate('standardVersionController.do?addorupdate&category=${category}',id,'ajax-modal')",
								"buttonName":"编辑",
								"dataAuthority":[{"publishmark":"0","stopflag":"0"}]
							},{
								"className":"btn mini yellow",
								"onclick":"dataTableDel('standardVersionController.do?del',id,'ncpflbz')",
								"buttonName":"删除",
								"dataAuthority":[{"publishmark":"0","stopflag":"0"}]
							}];
	   if (category == "2") {//不为管理员且为判定标准
		   if (userType != "0") {
			   buttonData = [
								{
									"className":"btn mini yellow",
									"onclick":"standardversionlist_toInfo('this',id,category)",
									"buttonName":"详情",
									"dataAuthority":[{"publishmark":"0,1","stopflag":"0,1"}]
								}];
		   }
	   }
		
	   registAjaxDataTable({
		   	id:"ncpflbz",
			actionUrl:"standardVersionController.do?datagrid&category=${category}",
			search:true,
			tableTools:true,
			aoColumns:[
					{ "mData":"category","dataHidden":true},
					{ "mData":"cname"},
					{ "mData":"publishorg"},
					{ "mData":"begindate","dateFormat":"yyyy-MM-dd"},
					{ "mData":"publishmark","dataDic":"publish"},
					{ "mData":"stopflag","dataDic":"stopstart"},
					{
					  "mData" : 'id',
					  "button": buttonData
// 					  "button":[
// 						{
// 							"className":"btn mini yellow",
// 							"onclick":"standardversionlist_toInfo('this',id,category)",
// 							"buttonName":"详情",
// 							"dataAuthority":[{"publishmark":"0,1","stopflag":"0,1"}]
// 						},{
// 							"className":"btn mini yellow",
// 							"onclick":"standardversionlist_stop(id)",
// 							"buttonName":"停用",
// 							"dataAuthority":[{"publishmark":"1","stopflag":"0"}]
// 						},{
// 							"className":"btn mini yellow",
// 							"onclick":"standardversionlist_publish(id)",
// 							"buttonName":"发布",
// 							"dataAuthority":[{"publishmark":"0","stopflag":"0"}]
// 						},{
// 							"className":"btn mini yellow",
// 							"onclick":"dataTableUpdate('standardVersionController.do?addorupdate&category=${category}',id,'ajax-modal')",
// 							"buttonName":"编辑",
// 							"dataAuthority":[{"publishmark":"0","stopflag":"0"}]
// 						},{
// 							"className":"btn mini yellow",
// 							"onclick":"dataTableDel('standardVersionController.do?del',id,'ncpflbz')",
// 							"buttonName":"删除",
// 							"dataAuthority":[{"publishmark":"0","stopflag":"0"}]
// 						}]
					}],
		   	   initModals:[{
	               		"id" : "create_standard",
						"operation" : "createwindow",
						"url":"standardVersionController.do?addorupdate&category=${category}",
	   	   			},{
	               		"id" : "copy_standard",
	               		"operation" : "defined",
						"callBack" : function(){

						}
	   	   			}
// 	   	   			,{
// 	               		"id" : "save_btn",
// 	               		"operation" : "windowsave",
// 						"url":"standardVersionController.do?save",
// 						"formId":"form1"
// 	   	   			},{
// 						"id" : "copy_Btn",
// 						"operation" : "windowsave",
// 						"url":"standardVersionController.do?copy",
// 						"formId":"form1"
// 					}
	   	   			] 
		});
	});
  
   function standardversionlist_toInfo($this,id,category) {
		if("0" == category){
			url = "agrCategoryController.do?agrCategoryTree&versionId=";
			title = "农产品版本名称:";
		}else if("1" == category){
			url = "pollCategoryController.do?pollCategoryTree&versionId=";
			title = "污染物版本名称:";
		}else if("2" == category){
			url = "judgeStandardController.do?judgeStandard&versionId=";
			title = "判定标准版本名称:";
		}else if("3" == category){
			url = "limitStandardController.do?limitStandard&versionId=";
			title = "限量标准版本名称:";
		}else{
			return;
		}
		loadContent($this,url+id);
	}
 
	function standardversionlist_copyData() { 
		var checkFlg = false;
		var vid = "";
		var oTT = TableTools.fnGetInstance( 'ncpflbz' );
	    var aSelectedTrs = oTT.fnGetSelected();
	    if(aSelectedTrs.length != 0){
	    	vid = aSelectedTrs[0].id;
	    	checkFlg = true;
	    }
		if (!checkFlg) {
			alert("请指定一个版本!");
			return;
		}
	   	var $modal = $('#ajax-modal'); 
	   	var pageContent = $('.page-content');
	   	App.blockUI(pageContent, false);
	     $modal.load('standardVersionController.do?addorupdate&type=copy&id='+vid, '', function(){
	      $modal.modal();
	      App.unblockUI(pageContent);
	      Validator.init();
	    });
	}

	function standardversionlist_publish(id){
		$("#ajax-modal").confirmModal({
			body: '确定发布该版本吗?',
			callback: function () {
				$.ajax({
					type : 'POST',
					url : "standardVersionController.do?save&publish=true",
					data : {'id':id,'publishmark':1},
					success : function(data) {
						var dataJson = eval('(' + data + ')');
						if(dataJson.success){
							refreshList($("#ncpflbz"));
							modalTips(dataJson.msg);
						} else {
							modalTips(dataJson.msg);
						}
					}
				});
			}
		});
	}
	//停用之前进行版本是否已经停用的check
	function standardversionlist_stop(versionid){
		$("#ajax-modal").confirmModal({
			heading: '请确认操作',
			body: '确定停用该版本吗?',
			callback: function () {
				$.ajax({
					type: "POST",
					url : "standardVersionController.do?save",
					data : {'id':versionid,'stopflag':1},
					success : function(data) {
						var dataJson = eval('(' + data + ')');
						if(dataJson.success){
							refreshList($("#ncpflbz"));
							modalTips(dataJson.msg);
						} else {
							modalTips(dataJson.msg);
						}
					}
				});
			}
		});
	}

	// 同步数据
	function dataSynchronization(){
		publishmark = 1;
		var url = "standardVersionController.do?dataSynchronization";
		$("#ajax-modal").confirmModal({
			body: '将会同步标准库所有推送的数据，确定进行同步吗?',
			callback: function () {
				var pageContent = $('.page-content');
				App.blockUI(pageContent, false);
				$.ajax({
					type : 'POST',
					url : url,
					data : {},
					success : function(data) {
						var dataJson = eval('(' + data + ')');
						if(dataJson.success){
							App.unblockUI(pageContent);
							refreshList($("#ncpflbz"));
							modalTips(dataJson.msg);
						} else {
							modalTips(dataJson.msg);
						}
					}
				});
			}
		});
	}
</script>