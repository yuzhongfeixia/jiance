<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webpage/main/navigator.jsp" %>
<link rel="stylesheet" href="assets/plugins/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="assets/plugins/data-tables/DT_bootstrap.css" />
<link rel="stylesheet" href="assets/plugins/data-tables/css/TableTools.css" />
<script type="text/javascript" src="assets/plugins/select2/select2.min.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/jquery.dataTables.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/TableTools.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/ZeroClipboard.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/DT_bootstrap.js"></script>
<script src="assets/scripts/table-managed.js"></script>
<script src="assets/scripts/ui-modals.js"></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript" ></script>
<link href="assets/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css"/>

<script type="text/javascript" src="assets/plugins/zTree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="assets/plugins/zTree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="assets/plugins/zTree/js/jquery.ztree.exedit-3.5.js"></script>
<style type="text/css">
.control-item{
	width:250px;
	float:left;
}
.control-label {
	float: left;
	padding-top: 5px;
}
.pollCategoryTreeTools{
background-color: #fafafa;
text-align: center;
padding: 0px 0px 2px 0px;
}
.fuelux{
border-top:1px solid #d2d2dc;
}
.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
.ztree li span.button.diy1_ico_open{margin-right:2px; background: url("assets/img/icons/bullet_orange.png") no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.diy1_ico_close{margin-right:2px; background: url("assets/img/icons/bullet_orange.png") no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.diy1_ico_docu{margin-right:2px; background: url("assets/img/icons/bullet_orange.png") no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.diy2_ico_open{margin-right:2px; background: url("assets/img/icons/bullet_green.png") no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.diy2_ico_close{margin-right:2px; background: url("assets/img/icons/bullet_green.png") no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.diy2_ico_docu{margin-right:2px; background: url("assets/img/icons/bullet_green.png") no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
</style>

<SCRIPT type="text/javascript">
	var isQueryOpen = 0;
	var versionId = "${versionId}";
	//页面隐藏分类id
	var setting = {
		view: {
			addHoverDom: addHoverDom,
			removeHoverDom: removeHoverDom,
			selectedMulti: false
		},
		edit: {
			enable: true,
			editNameSelectAll: true,
			showRemoveBtn: showRemoveBtn,
			removeTitle: "删除分类",
			showRenameBtn: showRenameBtn,
			renameTitle: "编辑分类"
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		async: {
			enable: true,
			url: "/pollCategoryController.do?pollCategoryTree?version="+versionId
		},
		callback: {
			beforeDrag: beforeDrag,
			beforeEditName: beforeEditName,
			beforeRemove: beforeRemove,
			beforeRename: beforeRename,
			onRemove: onRemove,
			onRename: onRename,
			onClick: zTreeOnClick
		}
	};

	var zNodes = ${zTreeData};
	var log, className = "dark";
	function beforeDrag(treeId, treeNodes) {
		return false;
	}
	function beforeEditName(treeId, treeNode) {
		className = (className === "dark" ? "":"dark");
		editLogicNode(treeNode);
		return false;
// 		var zTree = $.fn.zTree.getZTreeObj("categoryTree");
// 		zTree.selectNode(treeNode);
// 		//return confirm("进入节点 -- " + treeNode.name + " 的编辑状态吗？");
// 		return true;
	}
	function editLogicNode(treeNode){
		if(isPublished(versionId)){
			modalAlert("版本已经发布,不能再进行编辑分类的操作!");
			return;
		}
		if(null!=treeNode&&"" != treeNode){
			
		}
		
		if(isPublished(versionId)){
			createdetailwindow('查看','pollCategoryController.do?addorupdate&load=detail&id=' + treeNode.id);
		} else {
			//createwindow('修改污染物分类', 'pollCategoryController.do?addorupdate&id=' + treeNode.id);
		   	var $modal = $('#ajax-modal'); 
		   	var pageContent = $('.page-content');
		   	App.blockUI(pageContent, false);
		     $modal.load('pollCategoryController.do?addorupdate&id=' + treeNode.id, '', function(){
		      $modal.modal();
		      App.unblockUI(pageContent);
		      Validator.init();
		    });
		}
		
	}
	function beforeRemove(treeId, treeNode) {
		className = (className === "dark" ? "":"dark");
		if(isPublished(versionId)){
			modalAlert("版本已经发布,不能再进行删除分类的操作!");
			return false;
		}
// 		var msg = "确认删除 节点 : " + treeNode.name + " 吗？";
// 			confirm(msg,function() {
 		onRemove(null,treeId,treeNode);
// 		});
		return false;
	}

	function onRemove(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("categoryTree");
		$("#confirmDiv").confirmModal({
			heading: '请确认操作',
			body: '你确认删除所选记录?',
			callback: function () {
				$.ajax({
					type : 'POST',
					url : "pollCategoryController.do?del",
					data : {'id':treeNode.id,'pid':treeNode.pId},
					success : function(data) {
						var dataJson = eval('(' + data + ')');
						modalTips(dataJson.msg);
						//alert(dataJson.msg);
						// 删除节点后清空右侧list
						//$('#pool-info-panel').panel("refresh", "");
						zTree.removeNode(treeNode, false);
						$("#pollCategoryTable").dataTable().fnPageChange('first');  
					}
				});
			}
		});	
		
	}
	
	function beforeRename(treeId, treeNode, newName, isCancel) {
		className = (className === "dark" ? "":"dark");
		if (newName.length == 0) {
			modalAlert("节点名称不能为空.");
			var zTree = $.fn.zTree.getZTreeObj("categoryTree");
			setTimeout(function(){zTree.editName(treeNode)}, 10);
			return false;
		}
		return true;
	}
	function onRename(e, treeId, treeNode, isCancel) {
		var url = "pollCategoryController.do?save";
		$.ajax({
			type : 'POST',
			url : url,
			data : {'id':treeNode.id,'pid':treeNode.pId,'name':treeNode.name},
			success : function(data) {
				var dataJson = eval('(' + data + ')');
				modalTips(dataJson.msg);
			}
		});
	}
	function showRemoveBtn(treeId, treeNode) {
		return true;
	}
	function showRenameBtn(treeId, treeNode) {
		return true;
	}
	function setPollList(id,pollName) {
		var queryParams = getQueryParams('pollCategoryTable');
		queryParams['categoryid'] = id;
		queryParams['versionid'] = versionId;
		setQueryParams('pollCategoryTable',queryParams);
		$("#cateid").val(id);
		$("#categoryid").val(id);
		$("#pollCategoryTable").dataTable().fnPageChange('first');  
	}
	function zTreeOnClick(event, treeId, treeNode) {
		 $('#pollCategoryList').show();
		setPollList(treeNode.id,treeNode.name);
	}
	
	function showLog(str) {
		if (!log) log = $("#log");
		log.append("<li class='"+className+"'>"+str+"</li>");
		if(log.children("li").length > 8) {
			log.get(0).removeChild(log.children("li")[0]);
		}
	}
	function getTime() {
		var now= new Date(),
		h=now.getHours(),
		m=now.getMinutes(),
		s=now.getSeconds(),
		ms=now.getMilliseconds();
		return (h+":"+m+":"+s+ " " +ms);
	}

	var newCount = 1;
	function addHoverDom(treeId, treeNode) {
		var sObj = $("#" + treeNode.tId + "_span");
		if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
		var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
			+ "' title='增加子分类' onfocus='this.blur();'></span>";
		sObj.after(addStr);
		var treeNodeTId = treeNode.tId;
		var btn = $("#addBtn_"+treeNode.tId);
		if (btn) btn.bind("click", function(){
			addLogicData(treeNode);
			return false;
		});
	};
	function addLogicData(treeNode){
		if(isPublished(versionId)){
			modalAlert("版本已经发布,不能再进行新增分类的操作!");
			return;
		}
		if(null!=treeNode&&"" != treeNode){
			//createwindow('新增污染物分类', 'pollCategoryController.do?addorupdate&treeNodeId=' + treeNode.id + '&versionId=' + versionId);
		   	var $modal = $('#ajax-modal'); 
		   	var pageContent = $('.page-content');
		   	App.blockUI(pageContent, false);
		     $modal.load('pollCategoryController.do?addorupdate&treeNodeId=' + treeNode.id + '&versionId=' + versionId, '', function(){
		      $modal.modal();
		      App.unblockUI(pageContent);
		      Validator.init();
		    });

		} else {
			//createwindow('新增污染物分类', 'pollCategoryController.do?addorupdate&versionId=' + versionId);
		   	var $modal = $('#ajax-modal'); 
		   	var pageContent = $('.page-content');
		   	App.blockUI(pageContent, false);
		     $modal.load('pollCategoryController.do?addorupdate&versionId=' + versionId, '', function(){
		      $modal.modal();
		      App.unblockUI(pageContent);
		      Validator.init();
		    });
		}
	}
	
	/**
	* 用于category表单的回调函数
	* @param data 返回的ajaxJSON对象
	*/
	function saveDom(data){
		var dataJson = data; 
		var obj = dataJson.obj;
		if(0 == obj.saveDom){
			// data中id有值说明DB更新成功，则添加树的子节点
			if(obj.id != ""){
				var zTree = $.fn.zTree.getZTreeObj("categoryTree");
				if (zTree != null) {
					// 取得父节点
					var node = zTree.getNodesByParam("id",obj.pid,null);
					// 添加子节点,没有找到父节点id的情况（=null）为根节点的添加
					if (node.length >0) {
						zTree.addNodes(node[0], {id:obj.id, pId:obj.pid, name:obj.name});
					} else {
						zTree.addNodes(null, {id:obj.id, pId:obj.pid, name:obj.name});
					}
				} else {
					var node = {id:obj.id, pId:obj.pid, name:obj.name};
					zTree = $.fn.zTree.init($("#categoryTree"), setting, node);
				}
				modalTips("添加成功");
			} else {
				modalTips("添加失败");
			}
		} else {
	 		var zTree = $.fn.zTree.getZTreeObj("categoryTree");
	 		// 取得节点
			var node = zTree.getNodesByParam("id",obj.id,null);
			if (node.length >0) {
				node[0].name = obj.name;
				zTree.updateNode(node[0]);
				modalTips("修改成功");
			} else {
				modalTips("未能找到修改的节点，请刷新后确认数据正确性");
			}
		}

	}
	function removeHoverDom(treeId, treeNode) {
		$("#addBtn_" + treeNode.tId).unbind().remove();
	};
	function selectAll() {
		var zTree = $.fn.zTree.getZTreeObj("categoryTree");
		zTree.setting.edit.editNameSelectAll = $("#selectAll").attr("checked");
	}
	function showNodeAllData(){
		var id = "";
		var zTree = $.fn.zTree.getZTreeObj("categoryTree");
		nodes = zTree.getSelectedNodes();
		if (nodes.length == 0) {
			modalTips("没有选择任何节点");
			return;
		} else {
			id = nodes[0].id;
		}
		$('#pool-info-panel').panel("refresh", "pollProductsController.do?pollProducts&categoryId=" + id + '&versionId=' + versionId + "&showAllNoed=true");
	}

	$(document).ready(function() {
		if (isNotEmpty(zNodes)) {
			$.fn.zTree.init($("#categoryTree"), setting, zNodes);
		}
		$("#selectAll").bind("click", selectAll);
	});
	function expandNode(){
		iframe = this.iframe.contentWindow;
		var returnIds = iframe.getReturnIds();
		var ids = returnIds.split(",");

		var categoryId = ids[0];
		// 取得污染物ID
		var pollId = ids[1];
		var zTree = $.fn.zTree.getZTreeObj("categoryTree");
		var nodes = zTree.getNodesByParam("id", categoryId ,null);
		if (nodes.length >0) {
			// 选中节点
			zTree.selectNode(nodes[0]);
			var pageNum = 0;
			// 取得污染物所在页
			$.ajax({
				type : 'POST',
				async: false,
				url : 'pollProductsController.do?getPollPage',
				data : {'categoryid':categoryId,'versionid':versionId,'pollId':pollId},
				success : function(data) {
					var dataJson = eval('(' + data + ')');
					pageNum = dataJson.attributes.pageNum;
				}
			});
			// 定位污染物
			$('#pool-info-panel').panel("refresh", "pollProductsController.do?pollProducts&categoryId=" + categoryId + "&versionId=" + versionId + "&pollId=" + pollId +"&pageNum=" + pageNum);
// 			alert($("#locateId").attr("id"));
// 			$("#locateId").val(pollId);
// 			$("#pollProductsList").datagrid('reload');
		}
		return false;
	}
// 	function queryPoll(){
// 		// 判断只能弹出一个查询窗口
// 		if(0 == isQueryOpen){
// 			var url = 'url:pollProductsController.do?pollProducts&versionId=' + versionId + "&query=true";
// 			$.dialog({
// 				content: url
// 				,title: '污染物查询'
// 				,lock : false
// 				,button : [ {
// 					name : '定位'
// 					,callback : expandNode
// 					,focus : true}
// 					, {
// 					name : '关闭'
// 					,callback : function(){isQueryOpen = 0;}
// 				}]
// 				,close : function(){isQueryOpen = 0;}
// 			});
// 			isQueryOpen = 1;
// 		}
// 	}
	$('#searchBtn').on('click', function(){
		setQueryParams('pollCategoryTable',$('#searchForm').getFormValue());
		refresh_pollCategoryIframe();
	});
	var $modal = $('#ajax-modal');
	jQuery(document).ready(function() {
		   registAjaxDataTable({
			   	id:"pollCategoryTable",
				actionUrl:"pollProductsController.do?datagrid",
				search:true,
				tableTools:true,
 				fnDrawCallback: function(oSettings) {
					var queryParams = getQueryParams('pollCategoryTable');
					var page = queryParams.initPage;
					if(page != undefined && page > 0){
						queryParams['initPage'] = 0;
						setQueryParams('pollCategoryTable',queryParams);
						$("#pollCategoryTable").dataTable().fnPageChange(page - 1);
					} 
					var pollId = queryParams.initPollId;
					if(pollId != undefined && pollId != ""){
						queryParams['initPollId'] = "";
						setQueryParams('pollCategoryTable',queryParams);
						$("#pollCategoryTable").dataTable().fnHighlightRow(pollId);
					} 
				}, 
				aoColumns:[
						{ "mDataProp": "cas"},
						{ "mDataProp": "popcname","sWidth":"15%",},
						{ "mDataProp": "popename"},
						{ "mDataProp": "use"},
						{
						"mData" : 'id',"sWidth":"15%",
						bSortable : false,
						"mRender" : function(data, type, full) {
// 							return '<a id = "edit_org" data-toggle="modal" class="btn mini yellow" onclick="update(\''+data+ '\')">编辑</a>'+
// 							'&nbsp;<a class="btn mini yellow" onclick="del(\''+data+ '\')">删除</a>';
							return '<a class="btn mini yellow" onclick="del(\''+data+ '\')">删除</a>';
						}
						}],
			   	   initModals:[
// 			   	        {
// 		               		"id" : "addData",
// 							"operation" : "createwindow",
// 							"url":"pollProductsController.do?addorupdate&cateid="+$("#cateid").val(),
// 		   	   			},
// 		   	   			{
// 		               		"id" : "addBtn",
// 		               		"operation" : "windowsave",
// 							"url":"pollProductsController.do?save",
// 							"formId":"pollProSave"
// 		   	   			}
// 		   	   			{
// 		               		"id" : "addBtn1",
// 		               		"operation" : "windowsave",
// 							"url":"pollCategoryController.do?save",
// 							"refresh":false,		
// 							"callBack":function(data){
// 								saveDom(data);
// 							},
// 							"formId":"saveForm",
// 		   	   			} 
		   	   			
		   	   		]
			});
		});
	
	function addPollDetail() {
		if(isPublished(versionId)){
			modalAlert("版本已经发布,不能再进行选择操作!");
			return;
		}
	   	var $modal = $('#ajax-modal'); 
	   	var pageContent = $('.page-content');
	   	App.blockUI(pageContent, false);
	     $modal.load('pollProductsController.do?addorupdate&categoryid='+$("#categoryid").val()+'&versionid='+ versionId, '', function(){
	      $modal.modal();
	      App.unblockUI(pageContent);
	    });
	}
	
	function update(data) {
		if(isPublished(versionId)){
			modalAlert("版本已经发布,不能再进行编辑操作!");
			return;
		}
	   	var $modal = $('#ajax-modal'); 
	   	var pageContent = $('.page-content');
	   	App.blockUI(pageContent, false);
	     $modal.load('pollProductsController.do?addorupdate&id='+data, '', function(){
	      $modal.modal();
	      App.unblockUI(pageContent);
	      Validator.init();
	    });
	}
	//删除信息
	function del(data) {
		if(isPublished(versionId)){
			modalAlert("版本已经发布,不能再进行删除操作!");
			return;
		}
		$("#confirmDiv").confirmModal({
			heading: '请确认操作',
			body: '你确认删除所选记录?',
			callback: function () {
				$.ajax({
					type : "POST",
					url : "pollProductsController.do?del&rand=" + Math.random(),
					data : "id=" + data,
					success : function(data) {
						 var d = $.parseJSON(data);
			   			 if (d.success) {
			   				modalTips(d.msg);
			   				refresh_pollCategoryIframe();
			   			 }else {
			   				 alert(d.msg);
			   			 }
					}
				});
			}
		});
	}
	function refresh_pollCategoryIframe() {  
		$("#pollCategoryTable").dataTable().fnPageChange('first');  
	} 

	
	function checkPublishForChoose(){
		if(isPublished(versionId)){
			modalAlert("版本已经发布,不能再进行选择操作!");
			return;
		}
	   	var $modal = $('#ajax-modal'); 
	   	var pageContent = $('.page-content');
	   	App.blockUI(pageContent, false);
	     $modal.load('pollProductsController.do?pollProductsChoose&categoryid='+$("#categoryid").val()+ '&versionid=' + versionId, '', function(){
	      $modal.modal({width:"1200px"});
	      App.unblockUI(pageContent);
	    });
	}

	function queryPoll(){
	   	var $modal = $('#ajax-modal'); 
	   	var pageContent = $('.page-content');
	   	App.blockUI(pageContent, false);
	     $modal.load('pollProductsController.do?pollProducts&versionid=' + versionId + "&query=true", '', function(){
	      $modal.modal({width:"1000px"});
	      App.unblockUI(pageContent);
	    });
	}
</SCRIPT>

<body class="page-header-fixed">
	<div class="row-fluid">
		<div class="span6" style="width: 25%;">
			<div class="portlet box box_usual">
				<div class="portlet-title">
					<div class="caption">
						<i class="icon-comments"></i>污染物分类
					</div>
						<div class="tools">
                            <a style="color: #076148;!important" title="污染物分类录入" onclick="addLogicData('')"><i class="icon-plus"></i></a>
                            <a style="color: #076148;!important" id="queryPoll" title="污染物搜索" onclick="queryPoll();"><i class="icon-search"></i></a>
                        </div>
				</div>
<!-- 				<div> -->
<%-- 					<a id="add_pollCategory" href='pollProductsController.do?pollProducts&versionId=${versionId}' class="btn mini yellow" action-mode="ajax" action-event="click" action-pop="#ajax-modal" ><i class="icon-plus"></i></a> --%>
<!-- 				</div> -->
<!-- 				<div class=""> -->
<%-- 					<a id="seach_Poll" href='pollProductsController.do?pollProducts&versionId=${versionId}' class="btn mini yellow" action-mode="ajax" action-event="click" action-pop="#ajax-modal"><i class="icon-search"></i></a> --%>
<!-- 				</div> -->
				<div class="portlet-body fuelux">
					<ul class="ztree" id="categoryTree">
					</ul>
				</div>
			</div>
		</div>
		<div class="span9" style="margin-left: 0.3%">
			<div class="portlet box box_usual">
				<div class="portlet-title">
					<div class="caption">
						<i class="icon-comments"></i>污染物详情
					</div>
				</div>
				<div id="pollCategoryList" class="portlet-body" hidden="true">
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
								<input id="versionid" name="versionid" type="hidden" value="${versionId}">
								<input id="categoryid" name="categoryid" type="hidden">
							</div>
						</form>
					</div>
					<div class="clearfix">
						<div class="btn-group">
						    <a id="addData" class="btn btngroup_usual" data-toggle="modal" onclick="checkPublishForChoose();"><i class="icon-plus"></i>选择</a>
<!-- 						    <a id="addData" class="btn btngroup_usual" data-toggle="modal" onclick="addPollDetail();"><i class="icon-plus"></i>新增</a> -->
<!-- 							<a id="addData" class="btn btngroup_usual" data-toggle="modal" onclick="" ><i class="icon-plus"></i>导入</a> -->
						</div>
						<div class="pull-right">
							<a id="searchBtn" href="#" class="btn btngroup_seach"><i class="icon-search"></i>搜索</a>
						</div>
					</div>
					<table class="table table-striped table-bordered table-hover" id="pollCategoryTable">
						<thead>
							<tr>
								<th>CAS码</th>
								<th class="hidden-480">中文通用名称</th>
								<th class="hidden-480">英文通用名称</th>
								<th class="hidden-480">主要用途</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div id="ajax-modal" class="modal hide fade" tabindex="-1"></div>
	<div id="confirmDiv" class="modal hide fade"></div>
</body>
</html>