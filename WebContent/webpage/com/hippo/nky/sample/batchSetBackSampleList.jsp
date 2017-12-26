<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script>
	$('#searchBtn2').on('click', function(){
		setQueryParams('sampling_detail_table',$('#searchForm2').getFormValue());
		refresh_batchSetBackSampleList();
	});

	var $modal = $('#ajax-modal');
	jQuery(document).ready(function() {
		   registAjaxDataTable({
			   	id:"sampling_detail_table",
				actionUrl:"samplingInfoController.do?getBatchSetBackSampleList&projectCode=${projectCode}&samplingOrgCode=${ogrCode}",
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
								return '<input id="chk_'+data+'" type="checkbox" name="samplechkName" value="'+full.dcode+'">';
							}
				        },
						{ "mDataProp": "rn"},
				   		{"mDataProp" : "agrname",
							 "mRender" : function(data, type, full) {
							  		return '<a onclick=\"showSampleDetals(\''+full.projectCode+'\',\''+full.sampleCode+'\')\">'+data+'</a>';
							 }	
						},
						{ "mDataProp": "spCode"},
						{ "mDataProp": "dcode"},
						{ "mDataProp": "reportingDate"}

				],
				bPaginate:false
			});
	});
	
	function showSampleDetals(projectCode, sampleCode) {
		
	   	var $modal = $('#ajax-modal1'); 
	   	var pageContent = $('.page-content');
		App.blockUI(pageContent, false);
	     $modal.load('samplingInfoController.do?samplingInfoDetails&sampleCode='+sampleCode+'&projectCode='+projectCode, '', function(){
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
	
	function refresh_batchSetBackSampleList() {  
		$("#sampling_detail_table").dataTable().fnPageChange('first');  
	}
	
	function doBatchSetBack() {
		// 要分发的抽样信息
		var sampleIdArr = "";
		var arrSon = document.getElementsByName("samplechkName" );
		for (i=0;i<arrSon.length;i++) {
			if(arrSon[i].checked == true){
				sampleIdArr = sampleIdArr + arrSon[i].value + ",";
			}
		}
	
		if (sampleIdArr == "") {
			modalAlert("请指定要退回的记录!");
			return;
		}
		
		$("#confirmDiv").confirmModal({
			heading: '请确认操作',
			body: '信息将退回给抽样单位，是否确认操作？',
			callback: function () {
				$.ajax({
					type : "POST",
					url : "samplingInfoController.do?doBatchSetBack&rand=" + Math.random(),
					data : {'idArr':sampleIdArr},
					success : function(data) {
						 var d = $.parseJSON(data);
			   			 if (d.success) {
			   				refresh_batchSetBackSampleList();
			   				modalTips(d.msg);
			   			 }else {
			   				modalTips(d.msg);
			   			 }
					}
				});
			}
		});
	}
	
</script>
<div class="row-fluid">
	<div class="span12">
		<div class="portlet box popupBox_usual">
			<div class="portlet-title">
				<div class="caption">
					<i class="icon-reorder"></i> <span class="hidden-480">可批量退回样品</span>
				</div>
			</div>
			
			<div class="alert alert-success">
				<form id="searchForm2" name="searchForm2" action="#" class="form-horizontal">
					<div class="clearfix">	
						<div class="table-seach">
							<label class="help-inline seach-label">样品名称:</label>
							<div class="seach-element">
								<input type="text" id="sampleName" name="sampleName" placeholder="" class="m-wrap small">
							</div>
						</div>
						<div class="table-seach">
							<label class="help-inline seach-label">制样编码:</label>
							<div class="seach-element">
								<input type="text" id="spCode" name="spCode" placeholder="" class="m-wrap small">
							</div>
						</div>
						<div class="table-seach">
							<label class="help-inline seach-label">样品条码:</label>
							<div class="seach-element">
								<input type="text" id="dCode" name="dCode" placeholder="" class="m-wrap small">
							</div>
						</div>
<!-- 						<div class="table-seach"> -->
<!-- 							<label class="help-inline seach-label">抽样单位:</label> -->
<!-- 							<div class="seach-element"> -->
<!-- 								<input type="text" id="dcode2" name="dCode2" placeholder="" class="m-wrap small"> -->
<!-- 							</div> -->
<!-- 						</div> -->
					</div>
				</form>
			</div>
			</div>
			<div class="clearfix">
				<div class="pull-right">
					<a href="#" id="searchBtn2" class="btn btngroup_seach"><i class="icon-search"></i>搜索</a>
					<a href="#" id="searchBtn3" class="btn btngroup_seach" onclick="doBatchSetBack()"><i class="icon-external-link"></i>退回</a>
				</div>
			</div> 
			<table class="table table-striped table-bordered table-hover" id="sampling_detail_table">
				<thead>
					<tr>
						<th class="hidden-480"><input type="checkbox" name="sampleCheckAll" id="sampleCheckAll" onclick="selectAll()"/></th>
						<th class="hidden-480">序号</th>
					    <th class="hidden-480">样品名称</th>
					    <th class="hidden-480">制样编码</th>
						<th class="hidden-480">条码</th>
<!-- 						<th class="hidden-480">抽样单位</th> -->
						<th class="hidden-480">上报时间</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
			<div class="modal-footer">
				<button type="button" data-dismiss="modal" class="btn popclose">关闭</button>
			</div>
	</div>
</div>

<div id="ajax-modal1" class="modal hide fade" tabindex="-1"></div>
