<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="assets/plugins/data-tables/DT_bootstrap.css" />
<script type="text/javascript" src="assets/plugins/data-tables/jquery.dataTables.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/DT_bootstrap.js"></script>

<link href="assets/plugins/bootstrap-modal/css/bootstrap-modal.css"	rel="stylesheet" type="text/css" />
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript"></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript"></script>

<div class="row-fluid">
	<div class="span12">
		<div class="portlet box popupBox_usual">
			<div class="portlet-title">
				<div class="caption">
					<i class="icon-reorder"></i><span class="hidden-480">限量标准参照</span>
				</div>
			</div>
			<div class="portlet-body">
				<div class="alert alert-success">
					<form action="#" name="sForm" id="sForm" class="form-horizontal">
						<div class="clearfix">
							<div class="table-seach">
								<label class="help-inline seach-label">农产品名称 :</label>
								<div class="seach-element">
									<input type="text" placeholder="" class="m-wrap small"
										name="agrname" id="aname">
								</div>
							</div>
							<div class="table-seach">
								<label class="help-inline seach-label">污染物名称:</label>
								<div class="seach-element">
									<input type="text" placeholder="" class="m-wrap small"
										name="pollname" id="pname">
								</div>
							</div>
							<div class="table-seach">
								<label class="help-inline seach-label">最大残留限量:</label>
								<div class="seach-element">
									<input type="text" placeholder="" class="m-wrap small"
										name="pollname" id="poname">
								</div>
							</div>
						</div>
					</form>
				</div>
				<div class="clearfix">
					<div class="pull-right">
						<a href="#" class="btn btngroup_seach" id="sBtn"><i class="icon-search"></i>搜索</a>
<!-- 						<button type="button" class="btn btngroup_usual" onclick="resetX();" id="resetBtn"><i class="icon-reload"></i>重置</button> -->
					</div>
				</div>
				<table class="table table-striped table-bordered table-hover" id="xlbzcz">
					<thead>
						<tr>
							<th>污染物</th>
							<th>农产品</th>
							<th>最大残留限量</th>
							<th>单位</th>
							<th class="hidden-480">操作</th>
						</tr>
					</thead>
					<tbody>				
					</tbody>
				</table>
			</div>
			<div class="modal-footer">
				<button type="button" data-dismiss="modal" class="btn popclose">关闭</button>
				<button id="sureBtn" type="button" class="btn popenter">确定</button> 
			</div>
		</div>
	</div>
</div>

<script>
		var oTable;
		jQuery(document).ready(function() {       
			$('#sBtn').on('click', function(){
	 			setQueryParams('xlbzcz',$('#sForm').getFormValue());
	 			refreshListToFirst($("#xlbzcz"));
			});

			oTable = registAjaxDataTable({
				id : "xlbzcz",
				actionUrl : "limitStandardController.do?datagrid&versionid=${versionId}&cas=${cas}&rand=" + Math.random(),
				aoColumns : [
							{
								"mData" : "pollnameZh",
								"sWidth" : "148px"
							},
							{
								"mData" : "agrname",
								"sWidth" : "148px"
							},
							{
								"mData" : "mrl",
								"sWidth" : "148px"
							},
							{
								"mData" : "unit",
								"sWidth" : "148px"
							},
							{
								"mData" : "id",
								"sWidth" : "148px",
								"mRender" : function(data, type, rec) {
									return '<input type="hidden" value="'+data+'" id="idval"></input><a id=\'vBtn\' class=\'btn mini yellow\' action-mode=\'ajax\' action-url=\'limitStandardController.do?addorupdate&id='+data+'\' action-pop=\'ajax-modal1\'>[查看]</a>';
								}
							}
						],
					search : true,
				  	"aoColumnDefs":
					    [{
					            'bSortable': false,
								'bSearchable' : false,
					            'aTargets': [0,1,2,3,4]
					        }
					    ]
				});

		});
		
		//重置
		function resetX() {
			$('#aname').val('');
			$('#pname').val('');
			$('#poname').val('');
		}
		
		//限量标准参照确认
		$('#sureBtn').on('click', function(){
			if(!$("#xlbzcz tbody tr").hasClass('active')){
				modalAlert("至少要选择一条数据");
				return false;
			}
			//最大残留限量
            var mrl = $("#xlbzcz .active").parent().find('td:eq(2)').text();
			//农产品名称
			var argTempName = $("#xlbzcz .active").parent().find('td:eq(1)').text();
			var agrname = '${agrname}';
			var valuefrom;
			if(argTempName == agrname){
				valuefrom = 0;
			}else{
				valuefrom = 1
			}
			// 保存判断标准值
			$.ajax({
				type : "POST",
				url : "judgeStandardController.do?saveCZ&rand=" + Math.random(),
				data : {'id': '${jId}','value':mrl,'valuefrom':valuefrom},
				success : function(data) {
					var d = $.parseJSON(data);
					if (d.success) {
						modalTips(d.msg);
						$modal.modal('hide');
						fatherRefreshList();
					} else {
						modalAlert(d.msg);
					}
				}
			});
		});
	</script>
	<div id="ajax-modal1" class="modal hide fade" tabindex="-1"></div>

