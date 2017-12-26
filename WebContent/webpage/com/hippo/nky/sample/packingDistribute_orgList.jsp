<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link href="plug-in/lhgDialog/skins/default.css" rel="stylesheet" id="lhgdialoglink">
<script type="text/javascript" src="plug-in/lhgDialog/lhgdialog.min.js"></script>
<script>
	$('#searchBtn1').on('click', function(){
		setQueryParams('packingDistribute_orgList_table',$('#searchForm1').getFormValue());
		$('#packingDistribute_orgList_table').dataTable().fnClearTable(true);
	});

	var $modal = $('#ajax-modal');
	jQuery(document).ready(function() {
		   registAjaxDataTable({
			   	id:"packingDistribute_orgList_table",
				actionUrl:"samplingInfoController.do?projectdatagrid&projectCode=${projectCode}&orgCode=${orgCode}&sampleIdList=${sampleIdList}",
				search:true,
				tableTools:true,
				aoColumns:[
				        { "mDataProp": "id","bVisible":false},
						{ "mDataProp": "ogrname"},
						{ "mDataProp": "leader"},
						{ "mDataProp": "contactstel"}

				],
			});
	});
	
	function comfirma(){
		var checkFlg = false;
		// 抽样机构
		var orgCode = "${orgCode}";
		// 项目code
		var projectCode = "${projectCode}";
		// 拆包被选中样品
		var sampleIdList = "${sampleIdList}";
		// 城市代码
		var cityCode = "${cityCode}";
		// 区县代码
		var countyCode = "${countyCode}";
		// 检测机构
		var detectOrgid = "";
		var oTT = TableTools.fnGetInstance( 'packingDistribute_orgList_table' );
	    var aSelectedTrs = oTT.fnGetSelected();
	    if(aSelectedTrs.length != 0){
	    	detectOrgid = aSelectedTrs[0].id;
	    	checkFlg = true;
	    }
		if (!checkFlg) {
			modalAlert("请指定一条记录!");
			return;
		}
		$.ajax({
			type : 'POST',
			async: false,
			url : 'samplingInfoController.do?saveSamplingDistribute',
			data : {'orgCode':orgCode,'projectCode':projectCode,'detectOrgid':detectOrgid,'sampleIdList':sampleIdList,'cityCode':cityCode,'countyCode':countyCode},
			success : function(data) {
				var dataJson = eval('(' + data + ')');
				modalTips(dataJson.msg);
			}
		});
		if (isNotEmpty(orgCode)) {
			$("#packingDistribution_table").dataTable().fnPageChange('first'); 
		} else {
			$("#unpackingDistribution_table").dataTable().fnPageChange('first'); 
		}
		
		var $modal = $('#ajax-modal');
 		$modal.modal('hide');
	}
	
</script>
<div class="row-fluid">
	<div class="span12">
		<div class="portlet box popupBox_usual">
			<div class="portlet-title">
				<div class="caption">
					<i class="icon-reorder"></i> <span class="hidden-480">分发样品</span>
				</div>
			</div>
			<div class="portlet-body">
				<div class="alert alert-success">
					<form id="searchForm1" name="searchForm1" action="#" class="form-horizontal">
						<div class="clearfix">	
							<div class="table-seach">
								<label class="help-inline seach-label">机构名称:</label>
								<div class="seach-element">
									<input type="text" name="ogrname" placeholder="" class="m-wrap small">
								</div>
							</div>
						</div>
					</form>
				</div>
				<div class="clearfix">
					<div class="pull-right">
						<a href="#" id="searchBtn1" class="btn btngroup_seach"><i class="icon-search"></i>搜索</a>
					</div>
				</div> 
				<table class="table table-striped table-bordered table-hover" id="packingDistribute_orgList_table">
					<thead>
						<tr>
						    <th></th>
							<th>机构名称</th>
							<th class="hidden-480">负责人</th>
							<th class="hidden-480">联系人电话</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
			<div class="modal-footer">
				<button type="button" data-dismiss="modal" class="btn popclose">关闭</button>
				<button type="button" class="btn popenter green" onclick="comfirma();">确定</button>
			</div>
	</div>
</div>
</div>
