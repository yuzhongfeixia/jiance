<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<SCRIPT type="text/javascript">
	$('#searchBtn1').on('click', function(){
		setQueryParams('srhpollCategoryTable',$('#searchForm1').getFormValue());
		$("#srhpollCategoryTable").dataTable().fnPageChange('first'); 
	});
	jQuery(document).ready(function() {
		   registAjaxDataTable({
			   	id:"srhpollCategoryTable",
			   	search:true,
				actionUrl:"pollProductsController.do?datagrid&versionid=${versionid}&query=${query}",
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
								return '<input id="rio_'+data+'" type="radio" name="pollRioName" value="'+data+','+full.categoryid+'">';
							}
				        },
						{ "mDataProp": "cas"},
						{ "mDataProp": "popcname"},
						{ "mDataProp": "popename"},
						{ "mDataProp": "use"},
						],
				
			});
		});

	function confirma(){
		var versionId = "${versionid}";
		var pollId = "";
		var categoryid = "";
		
		var checkFlg = false;
		var arrSon = document.getElementsByName("pollRioName" );
		for (i=0;i<arrSon.length;i++) {
			if(arrSon[i].checked == true){
				var attArr = arrSon[i].value.split(",");
				pollId = attArr[0];
				categoryid = attArr[1];
				checkFlg = true;
				break;
			}
		}
		if (!checkFlg) {
			alert("请选择污染物!");
			return;
		}
		$('#pollCategoryList').show();
		var zTree = $.fn.zTree.getZTreeObj("categoryTree");
		var nodes = zTree.getNodesByParam("id", categoryid ,null);
		if (nodes.length >0) {
			// 选中节点
			zTree.selectNode(nodes[0]);
			var pageNum = 0;
			// 取得污染物所在页
			$.ajax({
				type : 'POST',
				async: false,
				url : 'pollProductsController.do?getPollPage',
				data : {'categoryid':categoryid,'versionid':versionId,'pollId':pollId},
				success : function(data) {
					var dataJson = eval('(' + data + ')');
					pageNum = dataJson.attributes.pageNum;
				}
			});
			// 定位污染物
			var queryParams = getQueryParams('pollCategoryTable');
			queryParams['categoryid'] = categoryid;
			queryParams['versionid'] = versionId;
			queryParams['initPage'] = pageNum;
			queryParams['initPollId'] = pollId;
			setQueryParams('pollCategoryTable',queryParams);
			
			$("#pollCategoryTable").dataTable().fnPageChange(pageNum - 1);
 		 	var $modal = $('#ajax-modal');
 		 	$modal.modal('hide');
 		 	 
		}
 	}
</SCRIPT>

<body class="page-header-fixed">
	<div class="row-fluid">
		<div class="span12">
			<div class="portlet box popupBox_usual">
				<div class="portlet-title">
					<div class="caption">
						<i class="icon-reorder"></i>
						<span class="hidden-480">污染物分类</span>
					</div>
				</div>
			</div>
			<div class="portlet-body">
				<div class="alert alert-success">
					<form action="#" class="form-horizonta11l" name="searchForm1" id="searchForm1">
						<div class="clearfix">
							<div class="table-seach">
								<label class="help-inline seach-label">CAS码:</label>
								<div class="seach-element">
									<input type="text" placeholder="" class="m-wrap small" name="cas">
								</div>
							</div>
							<div class="table-seach">
								<label class="help-inline seach-label">中文通用名称:</label>
								<div class="seach-element">
									<input type="text" placeholder="" class="m-wrap small" name="popcname">
								</div>
							</div>
							<div class="table-seach">
								<label class="help-inline seach-label">英文通用名称 :</label>
								<div class="seach-element">
									<input type="text" placeholder="" class="m-wrap small" name="popename">
								</div>
							</div>
						</div>
						<input name="clickFlg" id="clickFlg" type="hidden" value="0"/>
					</form>
				</div>
				<div class="clearfix">
					<div class="pull-right">
						<a id="searchBtn1" href="#" class="btn btngroup_seach"><i class="icon-search"></i>搜索</a>
					</div>
				</div>
				<table class="table table-striped table-bordered table-hover" id="srhpollCategoryTable">
					<thead>
						<tr>
							<th></th>
							<th>CAS码</th>
							<th class="hidden-480">中文通用名称</th>
							<th class="hidden-480">英文通用名称</th>
							<th class="hidden-480">主要用途</th>
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
		<button id="locate" type="button" class="btn popenter green" onclick="javascript:confirma();">定位</button>
	</div>
</body>

</html>