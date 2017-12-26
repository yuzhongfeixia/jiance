<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webpage/main/navigator.jsp" %>
<link rel="stylesheet" type="text/css" href="assets/plugins/select2/select2_metro.css" />
<link rel="stylesheet" href="assets/plugins/data-tables/DT_bootstrap.css" />
<link href="assets/plugins/bootstrap-datepicker/css/datepicker.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="assets/plugins/select2/select2.min.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/jquery.dataTables.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/DT_bootstrap.js"></script>
<script src="assets/scripts/ui-modals.js"></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-confirm.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript" ></script>
<link href="assets/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="assets/plugins/jquery-ui/jquery-ui-1.10.1.custom.min.css"/>
<script src="assets/scripts/ui-jqueryui.js"></script> 
<script src="assets/js/curdtools.js" type="text/javascript" ></script>
<script type="text/javascript" src="assets/scripts/plantSituationUtil2.js"></script>
<script>
	$('#searchBtn').on('click', function(){
		var jsonRes = getSelectedProject();
		var projectCode = jsonRes.selectedProjectCode;
		$('#projectCode').val(projectCode);
		setQueryParams('sample_blind_tb',$('#searchConditionForm').getFormValue());
		refresh_SamplingBlind();
	});

  $(document).ready(function() {
	   registAjaxDataTable({
		   	id:"sample_blind_tb",
			actionUrl:"samplingInfoController.do?getSamplingBlindData",	
			search:true,
			aoColumns:[
					{ "mDataProp": "rn"},
					{ "mData": "projectCode","dataHidden":true},
					{ "mDataProp": "projectName"},
					{ "mDataProp": "areaName"},
					{ "mDataProp": "cname"},
					{ "mDataProp": "spCode"},
					{ "mDataProp": "sogrName"},
					{ "mDataProp": "dogrName"},
					{
					    "mData" : 'sampleCode',
						bSortable : false,
						"mRender" : function(data, type, full) {
							if (full.sampleStatus == '5') {
								return '<a class="btn green mini" action-mode="ajax" action-url="samplingInfoController.do?replaceSamplingBlind" action-before="replaceSamplingBlind(\''+data+'\',\''+full.projectCode+'\',\''+full.cname+'\',\''+full.sogrName+'\',\''+full.dogrName+'\')">替换检测信息</a>';
							}
							return "";
						 }
						}
					]
		});
	   getProject();
	
	});
  
  function addSamplingBlind(){
	 	var $modal = $('#ajax-modal'); 
	   	var pageContent = $('.page-content');
		App.blockUI(pageContent, false);
		$modal.load('samplingInfoController.do?addSamplingBlind', '', function(){
		    $modal.modal({width:"960px"});
		    App.unblockUI(pageContent);
		    Validator.init();
	    });
  }
  
  function replaceSamplingBlind(data){
	  
	  var sampleCode = data.arguments_0;
	  var projectCode = data.arguments_1;
	  var cname = data.arguments_2;
	  var sogrName = data.arguments_3;
	  var dogrName = data.arguments_4;
	  
	  data.params["sampleCode"] = sampleCode;
	  data.params["projectCode"] = projectCode;
	  data.params["cname"] = cname;
	  data.params["sogrName"] = sogrName;
	  data.params["dogrName"] = dogrName;
	  
	  return data;
  }
  
  function updateSetBackForSamplingInfo(code){
	 // 取得样品项目code和id
	 var projectCode="";
	 var id="";
	 $.ajax({
		 async: false,
		type : "POST",
		url : "samplingInfoController.do?getSamplingKeyInfo",
		data : {'dcode':code},
		success : function(data) {
			 var d = $.parseJSON(data);
   			 if (d.success) {
   				projectCode = d.attributes.projectCode;
   				id = d.attributes.id;
   			 }
		}
	 });

	 var $modal = $('#ajax-modal'); 
	 var pageContent = $('.page-content');
	 App.blockUI(pageContent, false);
	 $modal.load('samplingInfoController.do?addorupdate&id='+id+'&projectCode='+projectCode+'&setBackFlg=true&vdcode='+code, '', function(){
	    $modal.modal({width:"920px"});
	    App.unblockUI(pageContent);
	    Validator.init();
	    $("#saveForm").find("input[type='checkbox'],[type='radio']").uniform();
	 });
  }
  
  
  function refresh_SamplingBlind(){
	  $("#sample_blind_tb").dataTable().fnPageChange('first');  
  }
  
function getProject(){
	getProjectCode({"industryCode" : "f", "type" : "1"});
}

function exportExcelCosForSamplingBind() {
	confirmAndClose();
	var jsonRes = getSelectedProject();
	var projectCode = jsonRes.selectedProjectCode;
	$('#projectCode').val(projectCode);

	exportExcelByCustom('sample.SamplingInfoService.exportSamplingBlindInfo','','searchConditionForm');
}	

</script>
<body class="page-header-fixed">
	<div class="row-fluid">
		<div class="span12">
			<div class="portlet box box_usual">
				<div class="portlet-title">
					<div class="caption">
						<i class="icon-globe"></i>
					</div>
				</div>
			<div class="portlet-body">
			
					<div class="alert alert-success">
					<form action="#" class="form-horizontal" name="searchConditionForm" id="searchConditionForm">
					<input type="hidden" id="projectCode" /> 
						<div class="clearfix">
							<div class="table-seach">
								<label class="help-inline seach-label">级别:</label>
								<div class="seach-element">
									<select class="m-wrap small" tabindex="1" name="projectLevel"
										id="projectLevel" onchange="getProject();">
										<option value="1">省任务</option>
										<option value="2">市任务</option>
										<option value="3">县任务</option>
									</select>
								</div>
							</div>
							<div class="table-seach">
								<label class="help-inline seach-label">年份:</label>
								<div class="seach-element">
									<select class="m-wrap small" tabindex="2" name="year" id="year"
										onchange="getProject();">
										<c:forEach items="${yearList}" var="year">
											<option value="${year}" <c:if test="${year eq currYear}">selected</c:if>>${year}年度</option>
										</c:forEach>	
									</select>
								</div>
							</div>
							<div class="table-seach">
								<label class="help-inline seach-label">项目:</label>
								<div class="seach-element">
									<input class="m-wrap" type="text" name="" id="projectNames" placeholder="选择项目" onclick="showProjectDiv()" readonly="readonly"></input><span style="color: red;">*点击选择项目</span>
									<div id="showProject" hidden="hidden" style="min-width:320px;background-color: white;position:absolute;z-index:99;border:1px solid;color: black"></div>
								</div>
							</div>
							<div class="table-seach">
								<label class="help-inline seach-label">项目名称:</label>
								<div class="seach-element">
									<input type="text" placeholder="" name="projectName" class="m-wrap small">
								</div>
							</div>
 							<div class="table-seach">
								<label class="help-inline seach-label">抽样地区:</label>
								<div id="areasDiv1" class="seach-element">
								<input type="text" placeholder="" name="cityAndCountry" class="m-wrap small">
								</div>
							</div>
							<div class="table-seach">
								<label class="help-inline seach-label">样品名称:</label>
								<div class="seach-element">
									<input type="text" placeholder="" name="sampleName" class="m-wrap small">
								</div>
							</div>
							<div class="table-seach">
								<label class="help-inline seach-label">制样编码:</label>
								<div class="seach-element">
									<input type="text" placeholder="" class="m-wrap small" name="spCode">
								</div>
							</div>
							<div class="table-seach">
								<label class="help-inline seach-label">抽样单位:</label>
								<div class="seach-element">
									<input type="text" placeholder="" name="unitFullname" class="m-wrap small">
								</div>
							</div>
							
							<div class="table-seach">
								<label class="help-inline seach-label">检测单位:</label>
								<div class="seach-element">
									<input type="text" placeholder="" name="remark" class="m-wrap small">
								</div>
							</div>
						</div>
						</div>
					</form>
					</div>
			<%-- 
					<div class="alert alert-success">
						<form action="#" name="searchConditionForm" id="searchConditionForm">
						<div class="clearfix">
							
							<div class="table-seach">
								<label class="help-inline seach-label">抽样地区:</label>
								<div id="areasDiv1" class="seach-element">
									<t:dictSelect id="areacode" field="areacode" hasLabel="false" customData="${areacodeList}" defaultVal="" extend="{data-set:{value:'#areasDiv1 .areaSelect'},link-Change:{value:'true'}}"></t:dictSelect>
									<t:dictSelect id="areacode2" field="areacode2" hasLabel="false" defaultVal=""  extend="{class:{value:'small areaSelect'}}"></t:dictSelect>
								</div>
							</div>
						</div>
						</form>
					</div> --%>
					<div class="clearfix">
						<div class="btn-group">
							<a class="btn btngroup_usual" data-toggle="modal" onclick="addSamplingBlind()"><i class="icon-plus"></i>添加盲样</a>
						</div>
						<div class="pull-right">
							<a href="#" class="btn btngroup_seach" id="searchBtn"><i class="icon-search"></i>搜索</a>
						    <a class="btn btngroup_usual" onclick="exportExcelCosForSamplingBind()">导出Excel<i class="icon-share"></i></a>
						</div>
					</div>
					<table class="table table-striped table-bordered table-hover"
						id="sample_blind_tb">
						<thead>
							<tr>
								<th class="hidden-480">序号</th>
								<th class="hidden-480">项目名称</th>
								<th class="hidden-480">抽样地区</th>
								<th class="hidden-480">样品名称</th>
								<th class="hidden-480">制样编码</th>
								<th class="hidden-480" style="width:200px;">抽样单位</th>
								<th class="hidden-480" style="width:200px;">检测单位</th>
								<th class="hidden-480" style="width:100px;">操作</th>
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
	<div id="confirmDiv" class="modal hide fade"></div>

</body>
</html>