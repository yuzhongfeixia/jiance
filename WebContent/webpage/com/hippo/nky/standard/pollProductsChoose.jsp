<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="assets/plugins/data-tables/DT_bootstrap.css" />
<script type="text/javascript" src="assets/plugins/select2/select2.min.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/jquery.dataTables.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/DT_bootstrap.js"></script>
<script src="assets/scripts/table-managed.js"></script>
<script src="assets/scripts/ui-modals.js"></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript" ></script>
<link href="assets/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css"/>

<script type="text/javascript">
$('#searchBtn2').on('click', function(){
	setQueryParams('selpollTable',$('#searchForm2').getFormValue());
	$("#selpollTable").dataTable().fnPageChange('first'); 
});
var $modal = $('#ajax-modal');
jQuery(document).ready(function() {
	   registAjaxDataTable({
		   	id:"selpollTable",
			actionUrl:"pollDictionaryController.do?subDatagrid&versionid=${versionid}",
			search:true,
			fnDrawCallback: function(oSettings) {
			  	  // 样式修正
				  App.initUniform();
				  $('#ajax-modal').modal('layout');
			},
			aoColumns:[
			        {
			        	"mData" : 'id',
			        	"sWidth":"5%",
						bSortable : false,
						"mRender" : function(data, type, full) {
							return '<input id="chk_'+data+'" type="checkbox" name="pollchkName" value="'+data+'">';
						}
			        },
					{ "mDataProp": "cas"},
					{ "mDataProp": "popcname","sWidth":"15%",},
					{ "mDataProp": "popename"},
					{ "mDataProp": "category"},
					{ "mDataProp": "use"},
					{ "mDataProp": "residuecname"},
					{ "mDataProp": "residueename"}],
		   	   initModals:[
	   	   			{
// 	               		"id" : "addBtn",
// 	               		"operation" : "windowsave",
// 						"url":"pollProductsController.do?save",
// 						"formId":"pollProSave",
	   	   			} 
	   	   		] 
		});
	});
	
function selectAll(){
	  var arrSon = document.getElementsByName("pollchkName" );
	  var cbAll = document.getElementById("pollCheckAll");
	  var tempState=cbAll.checked;
	  for(i=0;i<arrSon.length;i++) {
	   if(arrSon[i].checked!=tempState)
	            arrSon[i].click();
	  }
	 }

function confirm(){
	var tmpPollIds = "";
	var arrSon = document.getElementsByName("pollchkName" );
	for (i=0;i<arrSon.length;i++) {
		if(arrSon[i].checked == true){
			tmpPollIds = tmpPollIds + arrSon[i].value + ",";
		}
	}
	$("#pollIds").val(tmpPollIds);
	
	$.ajax({
        type:"POST",
        url:"pollProductsController.do?saveChoosePolls&categoryid=${categoryid }&versionid=${versionid }",
        data:"pollIds=" + tmpPollIds,
        success:function(data){
     	   var d = $.parseJSON(data);
			   if (d.success) {
				   $modal.modal('hide');
				   $("#pollCategoryTable").dataTable().fnPageChange('first');  
			   }else {
				   alert(d.msg);
			   }
         }
     });
}

</script>

<div class="row-fluid">
	<div class="span12">
		<div class="portlet box popupBox_usual">
			<div class="portlet-title">
				<div class="caption">
					<i class="icon-reorder"></i> <span class="hidden-480">选择污染物</span>
				</div>
			</div>
			<div class="portlet-body">
				<div class="alert alert-success">
					<form id="searchForm2" name="searchForm2" action="#" class="form-horizontal">
						<input id="cateid" name="cateid" type="hidden">
						<div class="clearfix">
							<div class="table-seach">
								<label class="help-inline seach-label">CAS码:</label>
								<div class="seach-element">
									<input type="text" placeholder="" name="cas" class="m-wrap small">
								</div>
							</div>
							<div class="table-seach">
								<label class="help-inline seach-label">中文通用名称:</label>
								<div class="seach-element">
									<input type="text" placeholder="" name="popcname" class="m-wrap small">
								</div>
							</div>
							<div class="table-seach">
								<label class="help-inline seach-label">英文通用名称 :</label>
								<div class="seach-element">
									<input type="text" placeholder="" name="popename" class="m-wrap small">
								</div>
							</div>
							<div class="table-seach">
								<label class="help-inline seach-label">类别:</label>
								<div class="seach-element">
									<input type="text" placeholder="" name="category" class="m-wrap small">
								</div>
							</div>
							<div class="table-seach">
								<label class="help-inline seach-label">主要用途 :</label>
								<div class="seach-element">
									<input type="text" placeholder="" name="use" class="m-wrap small">
								</div>
							</div>
							<div class="table-seach">
								<label class="help-inline seach-label">残留物中文名称 :</label>
								<div class="seach-element">
									<input type="text" placeholder="" name="residuecname" class="m-wrap small">
								</div>
							</div>
							<div class="table-seach">
								<label class="help-inline seach-label">残留物英文名称 :</label>
								<div class="seach-element">
									<input type="text" placeholder="" name="residueename" class="m-wrap small">
								</div>
							</div>
							<input id="versionid" name="versionid" type="hidden" value="${versionid}">
							<input id="categoryid" name="categoryid" type="hidden" value="${categoryid }">
							<input type="hidden" id="pollIds" name="pollIds" ignore="ignore" value="">
						</div>
					</form>
				</div>
				<div class="clearfix">
					<div class="pull-right">
						<a id="searchBtn2" href="#" class="btn btngroup_seach"><i class="icon-search"></i>搜索</a>
					</div>
				</div>
				<table class="table table-striped table-bordered table-hover" id="selpollTable">
					<thead>
						<tr>
							<th class="hidden-480"><input type="checkbox" name="pollCheckAll" id="pollCheckAll" onclick="selectAll()"/></th>
							<th class="hidden-480">CAS码</th>
							<th class="hidden-480">中文通用名称</th>
							<th class="hidden-480">英文通用名称</th>
							<th class="hidden-480">类别</th>
							<th class="hidden-480">主要用途</th>
							<th class="hidden-480">残留物中文名称</th>
							<th class="hidden-480">残留物英文名称</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
				<div class="modal-footer">
					<button type="button" data-dismiss="modal" class="btn popclose">关闭</button>
					<button id="addBtn" type="button" class="btn popenter" onclick="confirm()">保存</button> 
				</div>
			</div>
	</div>
</div>

