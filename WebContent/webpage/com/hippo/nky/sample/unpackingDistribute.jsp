<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webpage/main/navigator.jsp" %>
<link rel="stylesheet" type="text/css" href="assets/plugins/select2/select2_metro.css" />
<link rel="stylesheet" href="assets/plugins/data-tables/DT_bootstrap.css" />
<link href="assets/plugins/bootstrap-datepicker/css/datepicker.css" rel="stylesheet" type="text/css" />
<!-- <link rel="stylesheet" href="assets/plugins/data-tables/css/TableTools.css" /> -->

<script type="text/javascript" src="assets/plugins/select2/select2.min.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/jquery.dataTables.js"></script>
<!-- <script type="text/javascript" src="assets/plugins/data-tables/TableTools.js"></script> -->
<script type="text/javascript" src="assets/plugins/data-tables/ZeroClipboard.js"></script>
<!-- <script type="text/javascript" src="assets/plugins/data-tables/DT_bootstrap.js"></script> -->
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
		setQueryParams('unpackingDistribution_table',$('#searchConditionForm').getFormValue());
		$('#unpackingDistribution_table').dataTable().fnClearTable(true);
	});

	var $modal = $('#ajax-modal');
	jQuery(document).ready(function() {
		   registAjaxDataTable({
			   	id:"unpackingDistribution_table",
				actionUrl:"samplingInfoController.do?unpackingDistributeDataGrid&projectCode=${projectCode}",
				search:true,
				//tableTools:true,
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
						{ "mDataProp": "cname",
							 "mRender" : function(data, type, full) {
							  		return '<a onclick=\"showSampleDetals(\''+full.projectCode+'\',\''+full.sampleCode+'\')\">'+data+'</a>';
							 }	
						},
						{ "mDataProp": "spCode"},
						{ "mDataProp": "dCode"},
						{ "mDataProp": "ogrname"},
						{ "mDataProp": "reportingDate"},
						{ "mDataProp": "oname"}
				]
			});
	});
	
	function showSampleDetals(projectCode, sampleCode) {

	   	var $modal = $('#ajax-modal'); 
	   	var pageContent = $('.page-content');
	 	App.unblockUI(pageContent);
	     $modal.load('samplingInfoController.do?samplingInfoDetails&sampleCode='+sampleCode+'&projectCode=${projectCode}', '', function(){
	      $modal.modal({width:"800px"});
	      App.unblockUI(pageContent);
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

	// 样品分发
	function sampleDistribute() {
		var projectCode = "${projectCode}";

		// 要分发的抽样信息
		var sampleIdArr = "";
		var arrSon = document.getElementsByName("samplechkName" );
		for (i=0;i<arrSon.length;i++) {
			if(arrSon[i].checked == true){
				sampleIdArr = sampleIdArr + arrSon[i].value + ",";
			}
		}
	
		if (sampleIdArr == "") {
			modalAlert("请指定抽样单位记录!");
			return;
		}
		
    	// 已经被质检机构接收的样品不可以再分发
    	$.ajax({
			async: false,
			type : "POST",
			url : 'samplingInfoController.do?checkRecvUnPacking',
			data : {'projectCode':projectCode,'sampleIdArr':sampleIdArr},
			success : function(data) {
				var dataJson = eval('(' + data + ')');
				checkFlg = dataJson.success;
			}
		});
    	if (!checkFlg) {
			modalAlert("选择的样品中有已经接收的样品，不可以再分发!");
			return;
		}
   
		//质检中心列表页面跳转
	   	var $modal = $('#ajax-modal'); 
	   	var pageContent = $('.page-content');
	 	App.unblockUI(pageContent);
	     $modal.load('samplingInfoController.do?samplingorg&flg=unpacking&sampleIdArr='+sampleIdArr+'&projectCode='+projectCode, '', function(){
	      $modal.modal({width:"800px"});
	      App.unblockUI(pageContent);
	    });
	}
	

	
</script>

<!-- BEGIN BODY -->
<body class="page-header-fixed">
	<div class="row-fluid">
		<div class="span12">
			<!-- BEGIN EXAMPLE TABLE PORTLET-->
			<div class="portlet box box_usual">
				<div class="portlet-title">
					<div class="caption"><i class="icon-globe"></i></div>
				</div>
				<div class="portlet-body">
					<div class="alert alert-success">
						<form action="#" class="form-horizontal" name="searchConditionForm" id="searchConditionForm">
							<div class="clearfix">
								<div class="table-seach">
									<label class="help-inline seach-label">样品名称:</label>
									<div class="seach-element">
										<input type="text" name="sampleName" placeholder="" class="m-wrap small">
									</div>
								</div>
								<div class="table-seach">
									<label class="help-inline seach-label">样品编码:</label>
									<div class="seach-element">
										<input type="text" name="spCode" placeholder="" class="m-wrap small">
									</div>
								</div>
								<div class="table-seach">
									<label class="help-inline seach-label">条码:</label>
									<div class="seach-element">
										<input type="text" name="dCode" placeholder="" class="m-wrap small">
									</div>
								</div>
								<div class="table-seach">
									<label class="help-inline seach-label">抽样单位:</label>
									<div class="seach-element">
										<input type="text" name="ogrname" placeholder="" class="m-wrap small">
									</div>
								</div>
							</div>
						</form>
					</div>
					<div class="clearfix">
						<div class="pull-right">
							<a href="#" id="searchBtn" class="btn btngroup_seach"><i class="icon-search"></i>搜索</a>
					        <a class="btn btngroup_usual" onclick="sampleDistribute();"><i class="icon-external-link"></i>样品分发</a>
						</div>
					</div>
					<table class="table table-striped table-bordered table-hover" id="unpackingDistribution_table">
						<thead>
							<tr>
								<th class="hidden-480"><input type="checkbox" name="sampleCheckAll" id="sampleCheckAll" onclick="selectAll()"/></th>
								<th class="hidden-480">序号</th>
								<th class="hidden-480">样品名称</th>
								<th class="hidden-480">样品编码</th>
								<th class="hidden-480">条码</th>
								<th class="hidden-480">抽样单位</th>
								<th class="hidden-480">上报时间</th>
								<th class="hidden-480">接收单位</th>
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
</body>
</html>				