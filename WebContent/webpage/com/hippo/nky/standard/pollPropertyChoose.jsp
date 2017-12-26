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
$('#searchBtn3').on('click', function(){
	setQueryParams('selpollTable',$('#searchForm').getFormValue());
	$("#selpollTable").dataTable().fnPageChange('first'); 
});
var $modal = $('#ajax-modal');
jQuery(document).ready(function() {
	   registAjaxDataTable({
		   	id:"selpollTable",
			actionUrl:"pollPropertyController.do?getPollProducts&disableFlg=${disableFlg}",
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
							return '<input id="chk_'+data+'" type="checkbox" name="pollchkName" value="'+full.cas+'">';
						}
			        },
					{ "mDataProp": "cas"},
					{ "mDataProp": "cname"},
					{ "mDataProp": "ename"}
					],
		   	   initModals:[
// 	   	   			{
// 	               		"id" : "addBtn",
// 	               		"operation" : "windowsave",
// 						"url":"pollPropertyController.do?save",
// 						"formId":"searchForm2",
// 	   	   			} 
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
	var disableFlg = "${disableFlg}";
	var tmpCas = "";
	var arrSon = document.getElementsByName("pollchkName" );
	for (i=0;i<arrSon.length;i++) {
		if(arrSon[i].checked == true){
			tmpCas = tmpCas + arrSon[i].value + ",";
		}
	}
	
	$.ajax({
      type:"POST",
      url:"pollPropertyController.do?saveChoosePolls",
      data:{'cas':tmpCas,'disableFlg':disableFlg},
      success:function(data){
   	   var d = $.parseJSON(data);
			   if (d.success) {
				   $modal.modal('hide');
				   refresh_pollProperty(disableFlg);
			   }else {
				   alert(d.msg);
			   }
       }
   });
}
function refresh_pollProperty(disableFlg) {
	if (disableFlg == "0") {
		$("#pollProperty_disable_table").dataTable().fnPageChange('first');
	} else {
		$("#pollProperty_enable_table").dataTable().fnPageChange('first');
	}
	
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
					<form id="searchForm" name="searchForm" action="#" class="form-horizontal">
						<input id="cateid" name="cateid" type="hidden">
						<div class="clearfix">
							<div class="table-seach">
								<label class="help-inline seach-label">CAS码:</label>
								<div class="seach-element">
									<input type="text" placeholder="" name="cas" class="m-wrap small">
								</div>
							</div>
							<div class="table-seach">
								<label class="help-inline seach-label">中文名:</label>
								<div class="seach-element">
									<input type="text" placeholder="" name="popcname" class="m-wrap small">
								</div>
							</div>
							<div class="table-seach">
								<label class="help-inline seach-label">英文名 :</label>
								<div class="seach-element">
									<input type="text" placeholder="" name="popename" class="m-wrap small">
								</div>
							</div>
						</div>
					</form>
				</div>
				<div class="clearfix">
					<div class="pull-right">
						<a id="searchBtn3" href="#" class="btn btngroup_seach"><i class="icon-search"></i>搜索</a>
					</div>
				</div>
				<table class="table table-striped table-bordered table-hover" id="selpollTable">
					<thead>
						<tr>
							<th class="hidden-480"><input type="checkbox" name="pollCheckAll" id="pollCheckAll" onclick="selectAll()"/></th>
							<th class="hidden-480">CAS码</th>
							<th class="hidden-480">中文名</th>
							<th class="hidden-480">英文名</th>
	
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
			<div class="modal-footer">
				<button type="button" data-dismiss="modal" class="btn popclose">关闭</button>
				<button id="addBtn" type="button" class="btn popenter" onclick="confirm()">保存</button> 
			</div>
	</div>
</div>
</div>

