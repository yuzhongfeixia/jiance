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
<script>
	$('#searchBtn').on('click', function(){
		setQueryParams('sample_obsolete_tb',$('#searchConditionForm').getFormValue());
		refresh_detectionSetBack();
	});

  $(document).ready(function() {
	   registAjaxDataTable({
		   	id:"sample_obsolete_tb",
			actionUrl:"detectionController.do?getDetectionSetBackCheck",	
			search:true,
			fnDrawCallback: function(oSettings) {
			  	  // 样式修正
				  App.initUniform();
				},
			aoColumns:[
			        {
			        	"mData" : 'id',
			        	"sWidth":"5%",
						"bSortable" : false,
						"mRender" : function(data, type, full) {
							return '<input id="chk_'+data+'" type="checkbox" name="samplechkName" value="'+data+'">';
						}
			        },
					{ "mDataProp": "rn"},
					{ "mDataProp": "code"},
					{ "mDataProp": "projectName"},
					{ "mDataProp": "agrName"},
					{ "mDataProp": "unitFullName"},
					{ "mDataProp": "samplingDate"},
					{ "mDataProp": "ogrName"}
					]

		});
	
	});
  
  function checkPast(){
	  var idArr = "";
		var arrSon = document.getElementsByName("samplechkName" );
		for (i=0;i<arrSon.length;i++) {
			if(arrSon[i].checked == true){
				idArr = idArr + arrSon[i].value + ",";
			}
		}
	
		if (idArr == "") {
			modalAlert("请指定记录!");
			return;
		}
		$("#confirmDiv").confirmModal({
			heading: '请确认操作',
			body: '是否确认退回？',
			callback: function () {
				$.ajax({
					type : "POST",
					url : "detectionController.do?detectionSetBackCheckPast",
					data : {'idArr' : idArr},
					success : function(data) {
						var d = $.parseJSON(data);
						if (d.success) {
							modalTips(d.msg);
							$("#sample_obsolete_tb").dataTable().fnPageChange('first');  
						} else {
							modalAlert(d.msg);
						}
					}
				});
				
			}
		});
		
  }
  
  function checksetBack(){
	  var idArr = "";
		var arrSon = document.getElementsByName("samplechkName" );
		for (i=0;i<arrSon.length;i++) {
			if(arrSon[i].checked == true){
				idArr = idArr + arrSon[i].value + ",";
			}
		}
	
		if (idArr == "") {
			modalAlert("请指定记录!");
			return;
		}
		$("#confirmDiv").confirmModal({
			heading: '请确认操作',
			body: '是否确认驳回申请？',
			callback: function () {
				$.ajax({
					type : "POST",
					url : "detectionController.do?detectionSetBackChecksetBack",
					data : {'idArr' : idArr},
					success : function(data) {
						var d = $.parseJSON(data);
						if (d.success) {
							modalTips(d.msg);
							$("#sample_obsolete_tb").dataTable().fnPageChange('first');  
						} else {
							modalAlert(d.msg);
						}
					}
				});
				
			}
		});
		
  }
  
  function selectAll(){
		  var arrSon = document.getElementsByName("samplechkName" );
		  var cbAll = document.getElementById("sampleCheckAll");
		  var tempState=cbAll.checked;
		  for(i=0;i<arrSon.length;i++) {
			   if(arrSon[i].checked!=tempState)
			            arrSon[i].click();
		  }
  }

 
  function refresh_detectionSetBack(){
	  $("#sample_obsolete_tb").dataTable().fnPageChange('first');  
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
					<!-- 			<form action="#" name="searchConditionForm" id="searchConditionForm"> -->
					<div class="controls">
						<div class="tab-content">
							<div class="controls">
								<div class="alert alert-success">
									<form action="#" name="searchConditionForm"
										id="searchConditionForm">
										<div class="clearfix">
											<div class="table-seach">
												<label class="help-inline seach-label">条码:</label>
												<div class="seach-element">
													<input type="text" placeholder="" name="dCode"
														class="m-wrap small">
												</div>
											</div>

											<div class="table-seach">
												<label class="help-inline seach-label">监测项目:</label>
												<div class="seach-element">
													<input type="text" placeholder="" name="projectName"
														class="m-wrap small">
												</div>
											</div>

											<div class="table-seach">
												<label class="help-inline seach-label">样品名称:</label>
												<div class="seach-element">
													<input type="text" placeholder="" name="sampleName"
														class="m-wrap small">
												</div>
											</div>

											<div class="table-seach">
												<label class="help-inline seach-label">受检单位:</label>
												<div class="seach-element">
													<input type="text" placeholder="" name="unitFullname"
														class="m-wrap small">
												</div>
											</div>
											<div class="table-seach">
												<label class="help-inline seach-label">申请单位:</label>
												<div class="seach-element">
													<input type="text" placeholder="" name="ogrName"
														class="m-wrap small">
												</div>
											</div>
										</div>
									</form>
								</div>
								<div class="clearfix">
									<div class="btn-group">
										<a class="btn btngroup_usual" data-toggle="modal"
											href="#" onclick="checkPast()"><i class="icon-plus"></i>确认退回</a>
									</div>
									<div class="btn-group">
										<a class="btn btngroup_usual" data-toggle="modal"
											href="#" onclick="checksetBack()"><i class="icon-plus"></i>驳回申请</a>
									</div>
									<div class="pull-right">
										<a href="#" class="btn btngroup_seach" id="searchBtn"><i
											class="icon-search"></i>搜索</a>
									</div>
								</div>
								<table class="table table-striped table-bordered table-hover"
									id="sample_obsolete_tb">
									<thead>
										<tr>
										    <th class="hidden-480"><input type="checkbox" name="sampleCheckAll" id="sampleCheckAll" onclick="selectAll()"/></th>
											<th class="hidden-480">序号</th>
											<th class="hidden-480">条码</th>
											<th class="hidden-480">监测项目</th>
											<th class="hidden-480">样品名称</th>
											<th class="hidden-480">受检单位</th>
											<th class="hidden-480">抽样时间</th>
											<th class="hidden-480">申请单位</th>
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
		</div>
	</div>

	<div id="ajax-modal" class="modal hide fade" tabindex="-1"></div>
	<div id="confirmDiv" class="modal hide fade"></div>

</body>
</html>