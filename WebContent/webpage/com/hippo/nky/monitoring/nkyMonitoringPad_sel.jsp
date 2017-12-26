<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<%@ include file="../../../../../context/mytags.jsp"%>

<script  type="text/javascript">
	$('#searchBtn1').on('click', function(){
		setQueryParams('padSelTable',$('#searchForm1').getFormValue());
		$("#padSelTable").dataTable().fnPageChange('first'); 
	});
	jQuery(document).ready(function() {
		   registAjaxDataTable({
			   	id:"padSelTable",
			   	search:true,
				actionUrl:"organizationController.do?datagrid",
				fnDrawCallback: function(oSettings) {
				  	  // 样式修正
					  App.initUniform();
					  $('#ajax-modal1').modal('layout');
				},
				aoColumns:[
				        {
				        	"mData" : 'code',
				        	"sWidth":"5%",
							bSortable : false,
							"mRender" : function(data, type, full) {
								return '<input id="rio_'+data+'" type="radio" name="padSelRioName" value="'+data+","+full.ogrname+'">';
							}
				        },
						{ "mDataProp": "ogrname"},
						{ "mDataProp": "contacts"},
						{ "mDataProp": "contactstel"}
						]
				
			});
		});


	function confirma(){
		var checkFlg = false;
		var orgCode ="";
		var orgname="";
		var arrSon = document.getElementsByName("padSelRioName" );
		for (i=0;i<arrSon.length;i++) {
			if(arrSon[i].checked == true){
				var attArr = arrSon[i].value.split(",");
				orgCode = attArr[0];
				orgname = attArr[1];
				checkFlg = true;
				break;
			}
		}
		if (!checkFlg) {
			alert("请选择一个单位!");
			return;
		}
		$('#orgCode').val(orgCode);
		$('#orgname').val(orgname);
	 	var $modal = $('#ajax-modal1');
		$modal.modal('hide');
		$('#orgname').focus();
		
	}
</script>
<body class="page-header-fixed">
	<div class="row-fluid">
		<div class="span12">
			<div class="portlet box popupBox_usual">
				<div class="portlet-title">
					<div class="caption">
						<i class="icon-comments"></i>选择机构
					</div>
				</div>
			</div>
			<div class="portlet-body">
				<div class="alert alert-success">
						<div class="clearfix" name="searchForm1" id="searchForm1">
							<div class="table-seach">
								<label class="help-inline seach-label">机构名称:</label>
								<div class="seach-element">
									<input type="text" placeholder="" class="m-wrap small" name="ogrname">
								</div>
							</div>
						</div>
				</div>
				<div class="clearfix">
					<div class="pull-right">
						<a id="searchBtn1" href="#" class="btn btngroup_seach"><i class="icon-search"></i>搜索</a>
					</div>
				</div>
				<table class="table table-striped table-bordered table-hover" id="padSelTable">
					<thead>
						<tr>
							<th></th>
							<th>机构名称</th>
							<th class="hidden-480">联系人</th>
							<th class="hidden-480">联系电话</th>
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