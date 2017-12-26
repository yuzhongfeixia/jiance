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
jQuery(document).ready(function() {
	   registAjaxDataTable({
		   	id:"selpollTable",
			//actionUrl:"pollDictionaryController.do?datagrid",
			actionUrl:"pollProductsController.do?datagrid&versionid=${pollVersionId}&categoryid=${categoryid}",
			search:true,
			aoColumns:[
			        {
			        	"mData" : 'id',
			        	"sWidth":"5%",
						bSortable : false,
						"mRender" : function(data, type, full) {
							return '<input id="chk_'+full.cas+'" type="checkbox" name="pollchkName" value="'+full.cas+'">';
						}
			        },
					{ "mDataProp": "cas"},
					{ "mDataProp": "popcname","sWidth":"30%"},
					{ "mDataProp": "popename"}],
			fnDrawCallback:function(){
				var pollArr = $("#pollArr").val();
				var oTable = $('#selpollTable').dataTable();
				var parmArray = oTable.fnSettings().aoData;
				$.each(parmArray,function(index,data){
					var cas = data._aData.cas;
					if(pollArr.indexOf(cas+ "#KV#") >= 0){
						$("#chk_"+cas).attr("checked",true);
					}
	    		});
			},
			fnCallBefore:function(){
				frfreshSelected();
			}
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
function frfreshSelected(){
	var pollArr = $("#pollArr").val();
	var oTable = $('#selpollTable').dataTable();
	var parmArray = oTable.fnSettings().aoData;
	$.each(parmArray,function(index,data){
		var cas = data._aData.cas;
		var cname = data._aData.popcname;
		if($("#chk_"+cas).attr("checked")){
			if(pollArr.indexOf(cas + "#KV#") < 0){
				pollArr = pollArr + cas + "#KV#" + cname + "#EM#";
			}
		}else{
			if(pollArr.indexOf(cas + "#KV#") >= 0){
				pollArr = pollArr.replace(cas + "#KV#" + cname + "#EM#","");
			}
		}
	});
	$("#pollArr").val(pollArr);
}
function confirm(){
	frfreshSelected();
	var pollArr = $("#pollArr").val();
	var name = "";
	var pollArrList = pollArr.split("#EM#");
	for(var i=0;i<pollArrList.length;i++){
		if(pollArrList[i] != ""){
			var pollCasList = pollArrList[i].split("#KV#");
			if(pollCasList[0]!= ""){
				name = name + pollCasList[1] + "、";
			}
		}
	}
	pollArr=pollArr.replace(/#EM#$/gi,"");
	name=name.replace(/、$/gi,"");
	$("#add_monitoringDectionTemplet_table").find("input[name='monitoringDectionTempletList["+$("#returnRows").val()+"].pollCas']").val(pollArr);
	$("#add_monitoringDectionTemplet_table").find("input[name='monitoringDectionTempletList["+$("#returnRows").val()+"].pollCas']").parent().find("span").text(name);
	
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
						<input id="returnRows" name="returnRows" type="hidden" value="${returnRows}">
						<input id="pollArr" name="pollArr" type="hidden" value="${pollArr }">
					</div>
				</form>
			</div>
			</div>
			<div class="clearfix">
				<div class="pull-right">
					<a id="searchBtn2" href="#" class="btn btngroup_seach"><i class="icon-search"></i>搜索</a>
				</div>
			</div>
			<table class="table table-striped table-bordered table-hover" id="selpollTable">
				<thead>
					<tr>
						<th style="width:8px;"><input type="checkbox" name="pollCheckAll" id="pollCheckAll" onclick="selectAll()"/></th>
						<th>CAS码</th>
						<th class="hidden-480">中文通用名称</th>
						<th class="hidden-480">英文通用名称</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
			<div class="modal-footer">
				<button type="button" data-dismiss="modal" class="btn popclose">关闭</button>
				<button id="addBtn" type="button" class="btn popenter"  data-dismiss="modal" onclick="confirm()">保存</button> 
			</div>
	</div>
</div>

