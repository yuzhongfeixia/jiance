<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<%@ include file="../../../../../context/mytags.jsp"%>

<script  type="text/javascript">
	$('#searchBtn1').on('click', function(){
		setQueryParams('samplingSelTable',$('#searchForm1').getFormValue());
		$("#samplingSelTable").dataTable().fnPageChange('first'); 
	});
	jQuery(document).ready(function() {
		   registAjaxDataTable({
			   	id:"samplingSelTable",
			   	search:true,
				actionUrl:"samplingInfoController.do?getSelectSamplingBlind&taskCode=${taskCode}",
				fnDrawCallback: function(oSettings) {
				  	  // 样式修正
					  App.initUniform();
					  $('#ajax-modal1').modal('layout');
				},
				aoColumns:[
				        {
				        	"mData" : 'sampleCode',
				        	"sWidth":"5%",
							bSortable : false,
							"mRender" : function(data, type, full) {
								return '<input id="rio_'+data+'" type="radio" name="padSelRioName" value="'+data+'">';
							}
				        },
				        { "mDataProp": "rn"},
						{ "mDataProp": "cname"},
						{ "mDataProp": "spCode"},
						{ "mDataProp": "ogrName"},
						{ "mData": "monitoringLink",
							"mRender" : function(data, type, full) {
								return full.monitoringLink_name;
							}
						}
				]
				
			});
		});


	function confirma(){
		var checkFlg = false;
		var sampleCode ="";
		var arrSon = document.getElementsByName("padSelRioName" );
		for (i=0;i<arrSon.length;i++) {
			if(arrSon[i].checked == true){
				sampleCode = arrSon[i].value;
				checkFlg = true;
				break;
			}
		}
		if (!checkFlg) {
			alert("请选择一个样品!");
			return;
		}
		
		$.ajax({
			type : "POST",
			url : "samplingInfoController.do?saveSamplingBlind&rand=" + Math.random(),
			data : {'sampleCode':sampleCode},
			success : function(data) {
				 var d = $.parseJSON(data);
	   			 if (d.success) {
	   				modalTips(d.msg);
	   				refresh_SamplingBlind();
	   			 }else {
	   				modalTips(d.msg);
	   			 }
			}
		});

	 	var $modal = $('#ajax-modal1');
		$modal.modal('hide');	
	}
</script>
<body class="page-header-fixed">
	<div class="row-fluid">
		<div class="span12">
			<div class="portlet box popupBox_usual">
				<div class="portlet-title">
					<div class="caption">
						<i class="icon-comments"></i>选择样品
					</div>
				</div>
			</div>
			<div class="portlet-body">
				<div class="alert alert-success">
						<div class="clearfix" name="searchForm1" id="searchForm1">
							<div class="table-seach">
								<label class="help-inline seach-label">样品名称:</label>
								<div class="seach-element">
									<input type="text" placeholder="" class="m-wrap small" name="sampleName">
								</div>
							</div>
							<div class="table-seach">
								<label class="help-inline seach-label">制样编码:</label>
								<div class="seach-element">
									<input type="text" placeholder="" class="m-wrap small" name="spCode">
								</div>
							</div>
						</div>
				</div>
				<div class="clearfix">
					<div class="pull-right">
						<a id="searchBtn1" href="#" class="btn btngroup_seach"><i class="icon-search"></i>搜索</a>
					</div>
				</div>
				<table class="table table-striped table-bordered table-hover" id="samplingSelTable">
					<thead>
						<tr>
							<th></th>
							<th class="hidden-480">序号</th>
							<th class="hidden-480">样品名称</th>
							<th class="hidden-480">制样编码</th>
							<th class="hidden-480">抽样单位</th>
							<th class="hidden-480">抽样环节</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
	</div>

	<div class="modal-footer">
		<button type="button" data-dismiss="modal" class="btn">关闭</button>
		<button id="locate" type="button" class="btn popenter green" onclick="javascript:confirma();">确定</button>
	</div>
</body>