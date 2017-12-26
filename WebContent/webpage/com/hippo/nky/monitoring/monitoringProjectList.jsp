<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webpage/main/navigator.jsp" %>

<style type="text/css">
.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
.ztree li span.button.diy1_ico_open{margin-right:2px;background:transparent url("assets/img/icons/bullet_orange.png") no-repeat 0 0 scroll; vertical-align:top; *vertical-align:middle}
.ztree li span.button.diy1_ico_close{margin-right:2px; background:transparent url("assets/img/icons/bullet_orange.png") no-repeat 0 0 scroll; vertical-align:top; *vertical-align:middle}
.ztree li span.button.diy1_ico_docu{margin-right:2px; background:transparent url("assets/img/icons/bullet_orange.png") no-repeat 0 0 scroll; vertical-align:top; *vertical-align:middle}
.ztree li span.button.diy2_ico_open{margin-right:2px; background:transparent url("assets/img/icons/bullet_green.png") no-repeat 0 0 scroll; vertical-align:top; *vertical-align:middle}
.ztree li span.button.diy2_ico_close{margin-right:2px; background:transparent url("assets/img/icons/bullet_green.png") no-repeat 0 0 scroll; vertical-align:top; *vertical-align:middle}
.ztree li span.button.diy2_ico_docu{margin-right:2px; background:transparent url("assets/img/icons/bullet_green.png") no-repeat 0 0 scroll; vertical-align:top; *vertical-align:middle}

.unstyled li {margin-bottom:10px;}
</style>
<!-- BEGIN PAGE LEVEL STYLES -->
<link href="assets/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css"/>
<link href="assets/plugins/data-tables/DT_bootstrap.css"  rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="assets/plugins/zTree/css/zTreeStyle/zTreeStyle.css">
<link rel="stylesheet" type="text/css" href="assets/plugins/gritter/css/jquery.gritter.css" />
<link rel="stylesheet" type="text/css" href="assets/plugins/chosen-bootstrap/chosen/chosen.css" />
<link rel="stylesheet" type="text/css" href="assets/plugins/jquery-tags-input/jquery.tagsinput.css" />
<link rel="stylesheet" type="text/css" href="assets/plugins/clockface/css/clockface.css" />
<link rel="stylesheet" type="text/css" href="assets/plugins/bootstrap-wysihtml5/bootstrap-wysihtml5.css" />
<!-- END PAGE LEVEL STYLES -->

<script type="text/javascript" src="assets/plugins/data-tables/jquery.dataTables.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/DT_bootstrap.js"></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-confirm.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript" ></script>
<script type="text/javascript" src="assets/plugins/bootstrap-wizard/jquery.bootstrap.wizard.min.js"></script>
<script type="text/javascript" src="assets/plugins/chosen-bootstrap/chosen/chosen.jquery.min.js"></script>


<body class="page-header-fixed">
	<div class="row-fluid">
		<div class="span12">
			<div class="portlet box box_usual">
				<div class="portlet-title">
					<div class="caption"><i class="icon-globe"></i></div>
				</div>
				<div class="portlet-body">
					<div class="alert alert-success">
						<form action="#" class="form-horizontal" id="monitoring_project_search_form" onSubmit="dataTabelSearch('monitoring_plan_project_tb1','monitoring_project_search_form');return false;">
							<div class="clearfix">
								<div class="table-seach">
									<label class="help-inline seach-label">项目名称:</label>
									<div class="seach-element">
										<input type="text" placeholder="" class="m-wrap small" name="name">
									</div>
								</div>
							</div>
						</form>
					</div>
					<div class="clearfix">
						<div class="pull-right">
							<a href="#" class="btn btngroup_seach" onclick="dataTabelSearch('monitoring_plan_project_tb1','monitoring_project_search_form')"><i class="icon-search"></i>搜索</a>
						</div>
					</div>
					<div id="div_monitoring_plan_project_tb1">
						<table class="table table-striped table-bordered table-hover" id="monitoring_plan_project_tb1">
							<thead>
								<tr>
									<th class="hidden-480">序号</th>
									<th class="hidden-480">项目名称</th>
									<th class="hidden-480">监测时间</th>
									<th class="hidden-480">发布时间</th>
									<th class="hidden-480">状态</th>	
									<th ></th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
		
	<div id="monitoring_plan_project_window1" class="modal hide fade centerDiv" tabindex="-1" data-width="1050" style="max-width: 1050px;"></div>
	<div id="monitoring_plan_project_window2" class="modal hide fade centerDiv" tabindex="-1" data-width="540" style="max-width: 540px;"></div>
	<div id="monitoring_plan_project_window3" class="modal hide fade centerDiv" tabindex="-1" data-width="700" style="max-width: 700px;"></div>
	<div id="monitoring_plan_project_window5" class="modal hide fade centerDiv" tabindex="-1" data-width="900" style="max-width: 900px;" ></div>
	<div id="monitoring_plan_task_window1" class="modal hide fade centerDiv" tabindex="-1" data-width="900"  style="max-width: 900px;"></div>
	<div id="monitoring_plan_project_window6" class="modal hide fade centerDiv" tabindex="-1" data-width="900" style="max-width: 900px;" ></div>
	<div id="ajax-modal" class="modal hide fade" tabindex="-1"></div>
	
<script>
    $('.date-picker').datepicker({
        rtl : App.isRTL(),
        language: 'zh',
        autoClose: true,
        format: 'yyyy-mm-dd',
        todayBtn: true,
        clearBtn:true
    });
	jQuery(document).ready(function() {
		   registAjaxDataTable({
			   	id:"monitoring_plan_project_tb1",
				actionUrl:"monitoringProjectController.do?datagrid",
				search:true,
				aoColumns:[
						{ mData: "rowIndex"},
						{ mData: "userType","dataHidden":true},
						{ mData: "plevel","dataHidden":true},
						{ mData: "name"},
						{ mData: "starttime","dataHidden":true,"dateFormat":"yyyy年MM月dd日"},
						{ 
						  mData: "endtime","dateFormat":"yyyy年MM月dd日",
						  mRender : function(data, type, full) {
							  var rt = full.starttime+'——'+full.endtime;
							  if(rt == "——"){
								  return "";  
							  }else{
								  return rt;
							  }
						  }
						},
						{ mData: "publishDate","dateFormat":"yyyy-MM-dd"},
						{ mData: "state","dataDic":"project_state"},
						{
						mData : 'id',
						 "button":[
   							{
   								"className":"btn mini green",
   								"onclick":"projectSet(id)",
   								"buttonName":"设置",
   								"dataAuthority":[{"state":"0","userType":"1"}]
   							},{
   								"className":"btn mini green",
   								"onclick":"finishProject(id)",
   								"buttonName":"完成",
   								"dataAuthority":[{"state":"1","userType":"1"}]
   							},{
   								"className":"btn mini green",
   								"onclick":"reportProject(id)",
   								"buttonName":"数据上报",
   								"dataAuthority":[{"state":"2","userType":"1","plevel":"2,3"}]
   							},{
   								"className":"btn mini yellow",
   								"onclick":"stopProject(id)",
   								"buttonName":"废止",
   								"dataAuthority":[{"state":"0,1","userType":"1"}]
   							},{
   								"className":"btn mini yellow",
   								"onclick":"viewProject(id)",
   								"buttonName":"查看",
   								"dataAuthority":[{"state":"1,2,3"}]
   							}]
						}]
			});
		});
	
	
	// 完成
	function finishProject(id){
	    var data = {id:id,state:"2"};
		// 校验任务完成情况（抽样数量大于等于任务数，检测数量等于任务数）
		var checkRtn = checkTaskStatistical(id, 1);
		var eCheckRtn = eval("("+checkRtn+")");
		if (eCheckRtn.result == "1") {
			modalAlert("[项目抽样总数："+eCheckRtn.allCount+"抽样完成数："+eCheckRtn.cyCount+"],抽样完成数量必须大于等于任务总数!");
			return;
		} 
		if (eCheckRtn.result == "2") {
			modalAlert("[项目抽样总数："+eCheckRtn.allCount+"检测完成数："+eCheckRtn.jcCount+"],检测完成数量必须等于任务总数!");
			return;
		}
		stateUpdate('monitoringProjectController.do?statusSave','确定完成该任务吗?',data,'monitoring_plan_project_tb1');
	};
	// 上报
	function reportProject(id){
		var data = {id:id,state:"3"};
		stateUpdate('monitoringProjectController.do?statusSave','确定上报该任务吗?',data,'monitoring_plan_project_tb1');
	}
	// 废止
	function stopProject(id){
		var data = {id:id,state:"4"};
		stateUpdate('monitoringProjectController.do?statusSave','确定废止该任务吗?',data,'monitoring_plan_project_tb1');
	};
	// 查看
	function viewProject(id){
		var $modal = $('#monitoring_plan_project_window1');
		var pageContent = $('.page-content');
	   	App.blockUI(pageContent, false);
		 $modal.load("monitoringProjectController.do?projectSet&id="+id, '', function(){
			$modal.find(".modal-footer a[class!='btn button-previous'][class!='btn button-next']").remove();
			$($modal.find("#tab3").children()[0]).remove();
			$modal.find("#monitoringBreed_table tr").find("th:eq(1),td:eq(1)").remove();
			$($modal.find("#tab5").children()[0]).remove();
			$modal.find("#monitoringOrganization_table tr").find("th:eq(1),td:eq(1)").remove();
			$modal.find("#projectForm input,select").attr("disabled","");
			$modal.find("#projectForm").find("a[class!='attachmentFile-item']").remove();
			$modal.find("#add_monitoringAreaCount_table td").removeAttr("onclick");
			$modal.find("#projectForm").find("tr,td").removeAttr("action-mode");
	     	$modal.modal();
	     	$('#monitoring_plan_project_wizard1').find('.button-next').click();
	     	$('#monitoring_plan_project_wizard1').find('.button-next').click();
	     	$('#monitoring_plan_project_wizard1').find('.button-next').click();
	     	$('#monitoring_plan_project_wizard1').find('.button-next').click();
	     	$('#monitoring_plan_project_wizard1').find('.button-next').click();
	     	$('#monitoring_plan_project_wizard1').find('.button-next').click();
	     	App.unblockUI(pageContent);
		});
	};

 	// 项目设置
	function projectSet(id){
		var pageContent = $('.page-content');
	   	App.blockUI(pageContent, false);
		 //添加
		 $("#monitoring_plan_project_window1").load("monitoringProjectController.do?projectSet&id="+id, '', function(){
	     	$("#monitoring_plan_project_window1").modal();
	     	App.unblockUI(pageContent);
		 });
	}
	// 项目引用
	function projectSetCopy(){
		var id = $("#project_id").val();
		var cid = $("#monitorProject").val();
		if(cid == "" || cid == null || cid == undefined){
			modalTips('请选择要载入的检测项目！');
			return;
		}
		$("#monitoring_project_copy").modal('hide');
		$("#monitoring_plan_project_window1").modal('hide');
		// 添加
		$('body').modalmanager('loading');
		setTimeout(function(){
		 $("#monitoring_plan_project_window1").load("monitoringProjectController.do?projectSet&id="+id+"&cid="+cid, '', function(){
		   	$("#monitoring_plan_project_window1").modal();
		 });
		}, 300);
	}
	
	// 设置项目完成，验证任务完成数量
// 	function checkTaskStatistical(id) {
// 		var projectStatisticalInfo = "";
// 		$.ajax({
// 			async: false,
// 			type : "POST",
// 			url : "monitoringProjectController.do?projectStatistical&rand=" + Math.random(),
// 			data : {'id':id},
// 			success : function(data) {
// 				 var d = $.parseJSON(data);
// 				 projectStatisticalInfo = d.attributes.projectStatisticalInfo;
// 			}
// 		});
// 		if (parseInt(projectStatisticalInfo.cyCount) < parseInt(projectStatisticalInfo.allCount)) {
// 			//return "1";// 抽样完成数量没达标
// 			return "{'result':'1','allCount':'"+projectStatisticalInfo.allCount+"','cyCount':'"+projectStatisticalInfo.cyCount+"','jcCount':'"+projectStatisticalInfo.jcCount+"'}";
// 		}
// 		if (parseInt(projectStatisticalInfo.jcCount) != parseInt(projectStatisticalInfo.allCount)) {
// 			//return "2";// 检测完成数量没达标
// 			return "{'result':'2','allCount':'"+projectStatisticalInfo.allCount+"','cyCount':'"+projectStatisticalInfo.cyCount+"','jcCount':'"+projectStatisticalInfo.jcCount+"'}";
// 		}
// 		return "{'result':'0'}";
// 	}
</script>
	
</body>
</html>

