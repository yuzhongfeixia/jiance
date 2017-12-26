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

<script type="text/javascript" src="assets/scripts/sampleInfoUtil.js"></script>
</head>
<!-- BEGIN BODY -->
<body class="page-header-fixed">
	<input type="hidden" name="projectCode" id="projectCode" value="">
	<div class="row-fluid">
		<div class="span12">
			<!-- BEGIN EXAMPLE TABLE PORTLET-->
			<div class="portlet box box_usual">
				<div class="portlet-title">
					<div class="caption"><i class="icon-globe"></i></div>
				</div>
				<div class="portlet-body">				
					<form action="">
						<div class="alert alert-success">
							<div class="controls">
								<div class="tabbable tabbable-custom">
									<ul class="nav nav-tabs">
										<li class="active"><a href="#tab_1_1" data-toggle="tab">省项目</a></li>
										<li><a href="#tab_1_2" data-toggle="tab">市项目</a></li>
										<li><a href="#tab_1_3" data-toggle="tab">县项目</a></li>
									</ul>
									<div class="tab-content">
										<div class="tab-pane active" id="tab_1_1">
											<div class="controls">
												<select id="proselect" class="span12 m-wrap" size="5" onchange="queryData('proselect');"></select>
											</div>
											<!-- <div style="float:right;"><span class="message">2013年第三次例行监测:<font color="red">230个样品，2013年8月10号前上报</font></span></div> -->
										</div>
										<div class="tab-pane" id="tab_1_2">
											<div class="controls">
												<select id="cityselect" class="span12 m-wrap" size="5" onchange="queryData('cityselect');"></select>
											</div>
											<!-- <div style="float:right;"><span class="message">2013年第三次例行监测:<font color="red">230个样品，2013年8月10号前上报</font></span></div> -->
										</div>
										<div class="tab-pane" id="tab_1_3">
											<div>
												<select id="areaselect" class="span12 m-wrap" size="5" onchange="queryData('areaselect');"></select>
											</div>
										</div>
									</div>
								</div>	
							</div>
						</div>
						<div class="clearfix">
							<div class="pull-left">
								<a id="set_complete" class="btn btngroup_usual"><i class="icon-upload-alt"></i>标记为抽样完成</a>
							</div>
							<div class="pull-right">
								<a id="sample_reporting_btn1" class="btn btngroup_usual"><i class="icon-upload-alt"></i>抽样信息上报</a>
							</div>
						</div>
						<div id="sample_reporting_tb_div">
						<table class="table table-striped table-bordered table-hover"  id="sample_reporting_tb" style="margin-top: 5px;" >
							<thead>
								<tr>
									<th class="hidden-480" id="chkTh"><input type="checkbox" name="sampleCheckAll" id="sampleCheckAll" onclick="selectAll()"/></th>
									<th class="hidden-480">序号</th>
									<th class="center hidden-480">条码</th>
									<th class="center hidden-480" >样品名称</th>
									<th class="center hidden-480" >受检单位</th>
									<th class="center hidden-480" >抽样地</th>
									<th class="center hidden-480" >抽样环节</th>
									<th class="center hidden-480" >抽样时间</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
						</div>
						<div id="sample_reporting_tb2_div">
						<table class="table table-striped table-bordered table-hover"  id="sample_reporting_tb2" style="margin-top: 5px;" >
							<thead>
								<tr>
									<th class="hidden-480">序号</th>
									<th class="center hidden-480">条码</th>
									<th class="center hidden-480" >样品名称</th>
									<th class="center hidden-480" >受检单位</th>
									<th class="center hidden-480" >抽样地</th>
									<th class="center hidden-480" >抽样环节</th>
									<th class="center hidden-480" >抽样时间</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div id="confirmDiv" class="modal hide fade"></div>
	<div id="sample_reporting_confirm" class="modal hide fade" tabindex="-1">
		<div class="row-fluid">
			<div class="span12">
				<div class="portlet box popupBox_usual">
					<div class="portlet-title">
						<div class="caption">
							<span class="hidden-480">上报数据确认</span>
						</div>
						<div class="tools">
							<a data-dismiss="modal"  class="closed"></a>
						</div>
					</div>
					<div class="portlet-body">
						<div class="portlet-body-div" style="font-size:15px;padding:5px 15px 0px 15px;">
							<p>本次数据上报将提交<span style="color:red;" id="allcount"></span>个样品数据</p>
							<p>其中,有<span style="color:red;" id="minuscount"></span>个样品数据填写完全,有<span style="color:red;" id="subcount"></span>个样品数据未填写完全，实际上报<span style="color:red;" id="minuscount2"></span>个样品数据</p>
						</div>
						<div class="modal-footer">
							<button type="button" data-dismiss="modal" class="btn popclose">关闭</button><button id="okBtn" type="button" class="btn popenter popConfim">确定</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script>
		
		jQuery(document).ready(function(){
			$("#sample_reporting_tb_div").hide();
			$("#sample_reporting_tb2_div").show();
			// 取得省市县项目
			getProjects();
			getTableData($("#projectCode").val());
		});
		
		$("#okBtn").click(function(){
			var projectCode = $("#projectCode").val();
			var sampleIdArr = getSelectedSample();

// 			if(isEmpty(projectCode)){
// 				modalAlert("需要统计的项目不能为空!");
// 				return;
// 			}
			var rtnValue = $("#minuscount2").text();
				if(rtnValue == 0){
					$("#sample_reporting_confirm").modal('hide');
				}else{
					$.ajax({
						type : "POST",
						url : "samplingInfoController.do?toReport&rand=" + Math.random(),
						data : {'projectCode':projectCode,'sampleIdArr':sampleIdArr},
						success : function(data) {
							 var d = $.parseJSON(data);
				   			 if (d.success) {
				   				modalTips(d.msg);	
						   		$("#sample_reporting_confirm").modal('hide');
								$("#sample_reporting_tb").dataTable().fnPageChange('first');
								$("#sample_reporting_tb2").dataTable().fnPageChange('first');
				   			 }else {
				   				 modalAlert(d.msg);
				   			 }
						}
					});
				}
		});
		
		function getDetecthedFlg(projectCode) {
			var detecthedFlg = "";
			$.ajax({
				async: false,
				type : "POST",
				url : "samplingInfoController.do?getDetecthedFlg",
				data : "projectCode="+projectCode,
				success : function(data) {
					 var d = $.parseJSON(data);
					 detecthedFlg = d.attributes.detecthedFlg;
				}
			});
			return detecthedFlg;
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
		
		function getSelectedSample() {
			// 要上报的抽样信息
			var sampleIdArr = "";
			var arrSon = document.getElementsByName("samplechkName" );
			for (i=0;i<arrSon.length;i++) {
				if(arrSon[i].checked == true){
					sampleIdArr = sampleIdArr + arrSon[i].value + ",";
				}
			}
			return sampleIdArr;
		}
		
		function getSelectedSampleCount() {
			// 要上报的抽样信息
			var count = 0;
			var arrSon = document.getElementsByName("samplechkName" );
			for (i=0;i<arrSon.length;i++) {
				if(arrSon[i].checked == true){
					count++;
				}
			}
			return count;
		}
		
		
		$("#set_complete").click(function(){
			// 按钮如果是不可用的则不可点击
			var btn_enable = $(event.target).attr("disabled");
			if("disabled" == btn_enable || false == btn_enable){
				return;
			}
			var projectCode = $("#projectCode").val();
			if(isEmpty(projectCode)){
				modalAlert("请选择要设置的项目!");
				return;
			}
			// 校验任务完成情况（抽样数量大于等于任务数，检测数量等于任务数）
			var checkRtn = checkSamplingComlete(projectCode,2);
			var eCheckRtn = eval("("+checkRtn+")");
			if (eCheckRtn.result == "1") {
				modalAlert("[项目抽样总数："+eCheckRtn.allCount+"抽样完成数："+eCheckRtn.cyCount+"],抽样完成数量必须大于等于任务总数!");
				return;
			} 
			
			
			$("#confirmDiv").confirmModal({
				heading: '请确认操作',
				body: '标记为完成后，将不能再录入和上报样品信息，您确定吗?',
				callback: function () {
					$.ajax({
						type : "POST",
						url : "samplingInfoController.do?setProjectComplete&rand=" + Math.random(),
						data : {'projectCode':projectCode},
						success : function(data) {
							 var d = $.parseJSON(data);
				   			 if (d.success) {
				   				$('#set_complete').attr("disabled", true);
				   				modalTips(d.msg);
				   			 } else {
				   				modalTips(d.msg);
				   			 }
						}
					});
				}
			});
		});
		
		$("#sample_reporting_btn1").click(function(){
			var projectCode = $("#projectCode").val();
			if(isEmpty(projectCode)){
				modalAlert("没有可上报的样品数据!");
				return;
			}
			
			// 取抽检分离flg，0:抽检不分离;1:抽检分离
			var detecthedFlg = getDetecthedFlg(projectCode);
			
			// 抽检不分离时
			if (detecthedFlg == '0') {
				var trid = $("#sample_reporting_tb tbody tr:eq(0)").attr("id");
				if(isEmpty(trid)){
					modalAlert("没有可上报的样品数据!");
					return;
				}

				// 要上报的抽样信息
				var count = getSelectedSampleCount();
				if (count == 0) {
					modalAlert("请指定抽样记录!");
					return;
				}
				var sampleIdArr = getSelectedSample();
				$.ajax({
   					type : "POST",
   					url : "samplingInfoController.do?sampleReportCount&rand=" + Math.random(),
   					data : {'projectCode':projectCode,'sampleIdArr':sampleIdArr},
   					success : function(data) {
   						 var d = $.parseJSON(data);
   			   			 if (d.success) { 	
   			   				$("#allcount").html(count);
   			   				$("#subcount").html(d.obj.subcount);
			   				$("#minuscount").html(count-d.obj.subcount);
			   				$("#minuscount2").html(count-d.obj.subcount);
   			   			    //$(".portlet-body-div p:eq(1)").hide();
   							$("#sample_reporting_confirm").modal('show');//.dialog("open");
   			   			 }else {
   			   				 modalAlert(d.msg);
   			   			 }
   					}
   				});
				
			// 抽检分离时	
			} else { 
				var trid = $("#sample_reporting_tb2 tbody tr:eq(0)").attr("id");
				if(isEmpty(trid)){
					modalAlert("没有可上报的样品数据!");
					return;
				}
				
				// 校验任务完成情况（抽样数量大于等于任务数，检测数量等于任务数）
				var checkRtn = checkSamplingComlete(projectCode,1);
				var eCheckRtn = eval("("+checkRtn+")");
				if (eCheckRtn.result == "1") {
					modalAlert("[项目抽样总数："+eCheckRtn.allCount+"抽样完成数："+eCheckRtn.cycmCount+"],抽样完成数量必须大于等于任务总数!");
					return;
				} 
		
				//样品加一个抽检分离的制样编码是否为空的验证
				$.ajax({
					type : "POST",
					url : "samplingInfoController.do?checkSampleDetached&rand=" + Math.random(),
					data : "projectCode="+projectCode,
					success : function(data) {
						 var d = $.parseJSON(data);
			   			 if (d.success) {
			   				$.ajax({
			   					type : "POST",
			   					url : "samplingInfoController.do?sampleReportCount&rand=" + Math.random(),
			   					data : "projectCode="+projectCode,
			   					success : function(data) {
			   						 var d = $.parseJSON(data);
			   			   			 if (d.success) {
			   			   				$("#allcount").html(d.obj.allcount);
			   			   				$("#subcount").html(d.obj.subcount);
			   			   				$("#minuscount").html(d.obj.minuscount);
			   			   				$("#minuscount2").html(d.obj.minuscount);
			   			   			   // $(".portlet-body-div p:eq(1)").show();
			   							$("#sample_reporting_confirm").modal('show');//.dialog("open");
			   			   			 }else {
			   			   				 modalAlert(d.msg);
			   			   			 }
			   					}
			   				});
			   			 }else {
			   				modalTips("此项目为抽检分离项目，当前含有样品信息制样编码为空记录！");
			   			 }
					}
				});
			}			
		});
		
		function getTableData(projectCode){

				registAjaxDataTable({
					id : "sample_reporting_tb",
					search : true,
					actionUrl : "samplingInfoController.do?sampleReportDatagrid&sampleStatus=1&projectCode="+projectCode+"&rand=" + Math.random(),
					fnDrawCallback: function(oSettings) {
					  	  // 样式修正
						  App.initUniform();
						},
					aoColumns:[
							    {
						        	"mData" : 'id',
						        	"sWidth":"5%",
									"mRender" : function(data, type, full) {
										return '<input id="chk_'+data+'" type="checkbox" name="samplechkName" value="'+data+'">';
									}
						        },
								{"mDataProp" : "rn"},
								{"mDataProp" : "dcode"},
								{"mDataProp" : "agrname"},
								{"mDataProp" : "unitFullname"},
								{"mDataProp" : "cityAndCountry"},
								{"mData": "monitoringLink",
									"mRender" : function(data, type, full) {
										return full.monitoringLink_name;
									}
								},
								{"mDataProp" : "samplingDate"}
							]
					

	
				});

				registAjaxDataTable({
					id : "sample_reporting_tb2",
					search : true,
					actionUrl : "samplingInfoController.do?sampleReportDatagrid&sampleStatus=1&projectCode="+projectCode+"&rand=" + Math.random(),
					aoColumns:[
								{"mDataProp" : "rn"},
								{"mDataProp" : "dcode"},
								{"mDataProp" : "agrname"},
								{"mDataProp" : "unitFullname"},
								{"mDataProp" : "cityAndCountry"},
								{"mData": "monitoringLink",
									"mRender" : function(data, type, full) {
										return full.monitoringLink_name;
									}
								},
								{"mDataProp" : "samplingDate"}
							]
					
				});

			
			
		}
		
		function getProjectComplete(projectCode){
			var compFlg = false;
			$.ajax({
				async: false,
				type : "POST",
				url : "samplingInfoController.do?getProjectComplete",
				data : "projectCode="+projectCode,
				success : function(data) {
					 var d = $.parseJSON(data);
					 compFlg = d.attributes.isSetted;
				}
			});
			return compFlg;
		}
		
		function queryData(data){
			var taskselect = $('#'+data);
			var projectCode = taskselect.val();
			$('#projectCode').val(projectCode);
			var detecthedFlg = getDetecthedFlg(projectCode);
			// 抽检分离时
			if (detecthedFlg == '1') {
				$('#set_complete').attr("disabled", true);
				//$('#chkTh').hide();
				$("#sample_reporting_tb2_div").show();
				$("#sample_reporting_tb_div").hide();
				var queryParams = getQueryParams('sample_reporting_tb2');
				queryParams['projectCode'] = projectCode;
				setQueryParams('sample_reporting_tb2',queryParams);
				$("#sample_reporting_tb2").dataTable().fnPageChange('first');
				
			} else {
				if (getProjectComplete(projectCode)) {
					$('#set_complete').attr("disabled", true);
				} else {
					$('#set_complete').attr("disabled", false);
				}
				//$('#chkTh').show();
				$("#sample_reporting_tb_div").show();
				$("#sample_reporting_tb2_div").hide();
				var queryParams = getQueryParams('sample_reporting_tb');
				queryParams['projectCode'] = projectCode;
				setQueryParams('sample_reporting_tb',queryParams);
				$("#sample_reporting_tb").dataTable().fnPageChange('first');
			}
		
		}
		
		// 抽样信息上报，验证任务完成数量
		function checkSamplingComlete(data,flg) {
			var projectStatisticalInfo = "";
			var	paramUrl = "monitoringProjectController.do?samplingComleteStatistical&projectCode="+data;
	
			$.ajax({
				async: false,
				type : "POST",
				url : paramUrl,
				data : "",
				success : function(data) {
					 var d = $.parseJSON(data);
					 projectStatisticalInfo = d.attributes.projectStatisticalInfo;
				}
			});
			if (flg == '1') {
				if (parseInt(projectStatisticalInfo.cycmCount) < parseInt(projectStatisticalInfo.allCount)) {
					//return "1";// 抽样完成数量没达标
					return "{'result':'1','allCount':'"+projectStatisticalInfo.allCount+"','cycmCount':'"+projectStatisticalInfo.cycmCount+"','cyCount':'"+projectStatisticalInfo.cyCount+"'}";
				}
			}
			if (flg == '2') {
				if (parseInt(projectStatisticalInfo.cyCount) < parseInt(projectStatisticalInfo.allCount)) {
					//return "1";// 抽样完成数量没达标
					return "{'result':'1','allCount':'"+projectStatisticalInfo.allCount+"','cycmCount':'"+projectStatisticalInfo.cycmCount+"','cyCount':'"+projectStatisticalInfo.cyCount+"'}";
				}
			}
			return "{'result':'0'}";
		}
	</script> 
</body>
</html>				