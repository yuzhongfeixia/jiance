<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@ include file="/webpage/main/navigator.jsp" %>
<link rel="stylesheet" href="assets/plugins/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="assets/plugins/data-tables/DT_bootstrap.css" />
<script type="text/javascript" src="assets/plugins/select2/select2.min.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/jquery.dataTables.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/DT_bootstrap.js"></script>
<script src="assets/scripts/table-managed.js"></script>
<script src="assets/scripts/ui-modals.js"></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-confirm.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript" ></script>
<link href="assets/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css"/>

<script type="text/javascript" src="assets/plugins/zTree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="assets/plugins/zTree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="assets/plugins/zTree/js/jquery.ztree.exedit-3.5.js"></script>
<t:base type="validform"></t:base> 
<style type="text/css">
.control-item{
	width:250px;
	float:left;
}
.control-label {
	float: left;
	padding-top: 5px;
}
.seachDiv{
background-color: #fafafa;
-webkit-box-shadow: none !important;
box-shadow: none !important;
padding: 2px;
}
.lineFloat{
width:115px;
float:left;
}
.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
.ztree li span.button.diy1_ico_open{margin-right:2px; background: url("assets/img/icons/bullet_orange.png") no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.diy1_ico_close{margin-right:2px; background: url("assets/img/icons/bullet_orange.png") no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.diy1_ico_docu{margin-right:2px; background: url("assets/img/icons/bullet_orange.png") no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.diy2_ico_open{margin-right:2px; background: url("assets/img/icons/bullet_green.png") no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.diy2_ico_close{margin-right:2px; background: url("assets/img/icons/bullet_green.png") no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.diy2_ico_docu{margin-right:2px; background: url("assets/img/icons/bullet_green.png") no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
</style>
<script>
// 		jQuery(document).ready(function() {       
// 		  // TableManaged.init();
// 		   //UIModals.init();
// 		});
		var versionId = "${versionId}";
		
		var $modalAgrCategory = $("responsive");
		
		var view = null;
		
		if(isPublished(versionId)){
			view = {addHoverDom: null,
					removeHoverDom: null,
					selectedMulti: false};
		}else{
			view = {addHoverDom: addHoverDom,
					removeHoverDom: removeHoverDom,
					selectedMulti: false};
		}
		
		var setting = {
				view: view,
				edit: {
					enable: true,
					editNameSelectAll: true,
					showRemoveBtn: showRemoveBtn,
					removeTitle: "删除",
					showRenameBtn: showRenameBtn,
					renameTitle: "编辑"
				},
				data: {
					simpleData: {
						enable: true
					}
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
		}
		function editLogicNode(treeNode){
			createwindow('111', 'agrCategoryController.do?addorupdate&id=' + treeNode.id);
			initModalsPage([
			    {
               		"id" : "save_btn",
               		"operation" : "windowsave",
					"url":"agrCategoryController.do?save",
					"formId":"addAgrCategoryInfomation"
   	   			} 
   	   		]);
			
			
/* 			if(isPublished(versionId)){
				createdetailwindow('查看','agrCategoryController.do?addorupdate&load=detail&id=' + treeNode.id);
			} else {
				$.dialog({
					content: 'url:'+'agrCategoryController.do?addorupdate&id=' + treeNode.id,
					zIndex: 1996,
					weight:600,
					modal: true,
					title:'修改农产品',
					opacity : 0.3,
					cache:false,
					button : [
					    {
							name : '确认',
							callback : function() {
						    	iframe = this.iframe.contentWindow;
								saveObj();
								return false;
							},
							focus : true,
						}, 
						{
							name : '取消',
							callback : function() {}
						}
					],
					cancel: false
					
				});
				//createwindow2('修改农产品', 'agrCategoryController.do?addorupdate&id=' + treeNode.id);
			} */
		}
		
		function beforeRemove(treeId, treeNode) {
			className = (className === "dark" ? "":"dark");
			if(isPublished(versionId)){
				modalAlert("版本已经发布,不能再进行删除分类的操作!");
				return false;
			}
			var msg = "确认删除 节点 : " + treeNode.name + " 吗？";
			$("#ajax-modal").confirmModal({
				heading: '请确认操作',
				body: msg,
				callback: function () {
					onRemove(null,treeId,treeNode);
				}
			});
			 return false;
		}
		function onRemove(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("agrCategoryTree");
			$.ajax({
				type : "POST",
				url : "agrCategoryController.do?del",
				data : {'id':treeNode.id,'pid':treeNode.pId},
				success : function(data) {
					 var d = $.parseJSON(data);
		   			 if (d.success) {
		   				zTree.removeNode(treeNode, false);
		   				modalTips(d.msg);
		   			 }else {
		   				modalTips(d.msg);
		   			 }
				}
			});
		}
		function beforeRename(treeId, treeNode, newName, isCancel) {
			className = (className === "dark" ? "":"dark");
			if (newName.length == 0) {
				modalAlert("节点名称不能为空.");
				var zTree = $.fn.zTree.getZTreeObj("agrCategoryTree");
				setTimeout(function(){zTree.editName(treeNode)}, 10);
				return false;
			}
			return true;
		}
		function onRename(e, treeId, treeNode, isCancel) {
			var url = "agrCategoryController.do?save";
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
			return !isPublished(versionId);
		}
		function showRenameBtn(treeId, treeNode) {
			return false;
		}
		
		function zTreeOnClick(event, treeId, treeNode) {
			loadAgrInfo(treeNode.id,treeNode.name);
		}
		
		var newCount = 1;
		
		function addHoverDom(treeId, treeNode) {
			var sObj = $("#" + treeNode.tId + "_span");
			if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
			var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
				+ "' title='增加子类' onfocus='this.blur();'></span>";
			sObj.after(addStr);
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
				createwindow('新增农产品分类', 'agrCategoryController.do?addorupdate&load=add&treeNodeId=' + treeNode.id + '&versionId=' + versionId,"responsive");
			} else {
				createwindow('新增农产品分类', 'agrCategoryController.do?addorupdate&load=add&versionId=' + versionId,"responsive");
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
					var zTree = $.fn.zTree.getZTreeObj("agrCategoryTree");
					// 取得父节点
					var node = zTree.getNodesByParam("id", obj.pid, null);
					// 添加子节点,没有找到父节点id的情况（=null）为根节点的添加
					if (null != node) {
						zTree.addNodes(node[0], {id:obj.id, pId:obj.pid, name:obj.cname +"("+obj.code+")",iconSkin:"diy"+obj.agrcategorytype});
					} else {
						zTree.addNodes(null, {id:obj.id, pId:obj.pid, name:obj.cname +"("+obj.code+")",iconSkin:"diy"+obj.agrcategorytype});
					}
					modalTips("添加成功");
				} else {
					modalTips("添加失败");
				}
			} else {
				var zTree = $.fn.zTree.getZTreeObj("agrCategoryTree");
		 		// 取得节点
				var node = zTree.getNodesByParam("id",obj.id,null);
				if (node.length >0) {
					node[0].name = obj.cname +"("+obj.code+")";
					node[0].iconSkin = "diy"+obj.agrcategorytype;
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
			var zTree = $.fn.zTree.getZTreeObj("agrCategoryTree");
			zTree.setting.edit.editNameSelectAll =  $("#selectAll").attr("checked");
		}

		// 展开/折叠
		function expandNode(type) {
			var zTree = $.fn.zTree.getZTreeObj("agrCategoryTree"),
			nodes = zTree.getSelectedNodes();
			if (nodes.length == 0) {
				modalTips("请先选择一个父节点");
			} else {
				var callbackFlag = false;//$("#callbackTrigger").attr("checked");
				for (var i=0, l=nodes.length; i<l; i++) {
					zTree.setting.view.fontCss = {};
					if (type == "expand") {
						zTree.expandNode(nodes[i], true, null, null, callbackFlag);
					} else if (type == "collapse") {
						zTree.expandNode(nodes[i], false, null, null, callbackFlag);
					} else if (type == "toggle") {
						zTree.expandNode(nodes[i], null, true, null, callbackFlag);
					} else if (type == "expandSon") {
						zTree.expandNode(nodes[i], true, true, null, callbackFlag);
					} else if (type == "collapseSon") {
						zTree.expandNode(nodes[i], false, true, null, callbackFlag);
					}
				}
			}
		}  
		
		//左侧搜索
		function searchList(){
			var  searchValue = $("#searchValue").val();
			if(searchValue == ''){
				modalTips("请输入检索内容");
				return;
			}
 			var queryParams = getQueryParams('agrNodeSeachTable');
 			$("#ncpjcxxSeach").find("input[name='searchRange']").each(function(){
				if($(this).attr('checked') == 'checked'){
					queryParams[$(this).val()] = searchValue;
				}else{
					queryParams[$(this).val()] = "";
				}
			}); 
 			setQueryParams('agrNodeSeachTable',queryParams);
 			$("#agrNodeSeachTable").dataTable().fnPageChange('first');
 			//$("#agrNodeSeachTable").dataTable().fnClearTable(true);
		}
		
		// 左侧选中节点，并打开所有子节点
		function nodeOpen(id){
			var zTree = $.fn.zTree.getZTreeObj('agrCategoryTree');
			var nodes = zTree.getNodesByParam("id", id, null);
			if (nodes.length>0) {
				zTree.selectNode(nodes[0]);
				expandNode('expandSon');
				loadAgrInfo(id,nodes[0].name);
			}
		}
		
		// 右侧农产品
		function loadAgrInfo(id,agrName) {
			$.post("agrCategoryController.do?addorupdate&load=detail&id=" + id + "&versionid="+versionId, '', function(res){
		    	 $("#agrCategoryInfo").html(res);
					if(isPublished(versionId) || isStoped(versionId)){
						$("#agrCategorySaveBtn").remove();
						$("#agrCategoryInfomation").find("input,textarea").attr("disabled","disabled");
						$("#agrCategoryInfomation").find("img[id='agrImage']").removeAttr("action-mode");
					}else{
						$("#agrCategoryInfomation").find("a[id='caliasUpdate']").remove();
		    	 		saveAgrCategory();
					}
/* 	    		$('#agrCategorySaveBtn').on('click', function(){
	    			
	    		}); */
			 });
		}
		// 右侧农产品保存按钮
		function saveAgrCategory(){
			checkForm({
				formId : "agrCategoryInfomation",
				btnId : "agrCategorySaveBtn",
				success : function(){
					var saveArray = $('#agrCategoryInfomation').serialize();
					//添加
					$.ajax({
				       type:"POST",
				       url:'agrCategoryController.do?save',
				       data:saveArray,
				       success:function(data){
				    	   var d = $.parseJSON(data);
				    	   modalTips(d.msg);
				    	   $("#agrNodeSeachTable").dataTable().fnClearTable(true);
				    	   saveDom(d);
				        }
				    });
				}
			});
		}
		
		$(document).ready(function(){
			$.fn.zTree.init($("#agrCategoryTree"), setting, zNodes);
			$("#selectAll").bind("click", selectAll);
		});
		function callbackStructureImgR(data){
			var path = data.attributes.attProperties[0].fileRelativePath + data.attributes.attProperties[0].fileName;
			var attElement = $("#agrCategoryInfomation").find("img[id='agrImage']");
			if(attElement.length > 0){
				attElement.attr("src", getActionPath(path));
			}
			$("#agrCategoryInfomation").find("input[name='imagepath']").val(getActionPath(path));
		}
		function callbackStructureImgO(data){
			var path = data.attributes.attProperties[0].fileRelativePath + data.attributes.attProperties[0].fileName;
			var attElement = $("#addAgrCategoryInfomation").find("img[id='agrImage']"); 
			if(attElement.length > 0){
				attElement.attr("src", getActionPath(path));
			}
			$("#addAgrCategoryInfomation").find("input[name='imagepath']").val(getActionPath(path));
		}
</script>

<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-header-fixed">
	     <div class="row-fluid">
	     			<div class="span6" style="width:26%;">
						<div class="portlet box box_usual">
							<div class="portlet-title">
								<div class="caption"><i class="icon-comments"></i>农产品查询</div>
							</div>
							<div id="agrSeach" class="portlet-body">
								<form action="" method="post" onSubmit="searchList();return false;">
									<div class="seachDiv">
										<div style="margin: 0px auto;">
											<label class="control-label" style="margin-left: 15px;margin-right: 5px;">搜索</label>
											<input type="text" placeholder="" class="m-wrap seachElement small" style="margin-bottom:0px;" id="searchValue" name="searchValue"/>
											<a href="#" class="btn btngroup_seach" style="margin-top: 3px;padding: 2px 8px!important;"  onclick="searchList()"><i class="icon-search"></i>搜索</a>
										</div>
									</div>
									<div class="control-group">
										<div class="controls">
											<div class="row-fluid">
												<div id="ncpjcxxSeach" style="margin: 0px auto;padding: 5px 0px 0px 20px;">
													<label class="checkbox line lineFloat">
														<span><input id="ncpjcxxSeachCheckGroup" type="checkbox" class="group-checkable" data-set="#ncpjcxxSeach .checkboxes" /></span> 全选/反选
													</label>
													<label class="checkbox line lineFloat">
														<span><input type="checkbox" class="checkboxes" name="searchRange" value="code" /></span> 编号
													</label>
													<label class="checkbox line lineFloat">
														<span><input type="checkbox" class="checkboxes" name="searchRange" value="cname"/></span> 中文名称
													</label>
													<label class="checkbox line lineFloat">
														<span><input type="checkbox" class="checkboxes" name="searchRange" value="ename" /></span> 英文名称
													</label>
													<label class="checkbox line lineFloat">
														<span><input type="checkbox" class="checkboxes" name="searchRange" value="latin" /></span> 拉丁名称
													</label>
													<label class="checkbox line lineFloat">
														<span><input type="checkbox" class="checkboxes" name="searchRange" value="calias" /></span> 中文别名
													</label>
													<label class="checkbox line lineFloat">
														<span><input type="checkbox" class="checkboxes" name="searchRange" value="ealias" /></span> 英文别名
													</label>
													<label class="checkbox line lineFloat">
														<span><input type="checkbox" class="checkboxes" name="searchRange" value="foodex" /></span> FOODEX_2编码
													</label>
													<label class="checkbox line lineFloat">
														<span><input type="checkbox" class="checkboxes" name="searchRange" value="gems" /></span> GEMS编码
													</label>
													<label class="checkbox line lineFloat">
														<span><input type="checkbox" class="checkboxes" name="searchRange" value="describe" /></span> 备注
													</label>
												</div>
											</div>
										</div>
									</div>
								</form>
								<table class="table table-striped table-bordered table-hover" id="agrNodeSeachTable" style="margin-bottom: 0px!important;">
									<thead>
										<tr>
											<th style="width:10px;text-align: center;">类型</th>
											<th>名称</th>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				    <div class="span6" style="width:26%;margin-left:0.3%;">
						<div class="portlet box box_usual">
							<div class="portlet-title">
								<div class="caption"><i class="icon-comments"></i>农产品分类</div>
								<div class="tools">
									<a style="color: #076148;!important" title="添加" onclick="addLogicData('')"><i class="icon-plus"></i></a>
								</div>
							</div>
							<div id="agrTree" class="portlet-body" style="OVERFLOW-Y: auto;  OVERFLOW-X: hidden;/* height: 663px; */">
								<ul class="ztree" id="agrCategoryTree">
								</ul>
							</div>
						</div>
					</div>
					<div class="span6" style="width:47%;margin-left:0.3%;" id="agrCategoryInfo">
						<div class="portlet box box_usual">
								<div class="portlet-title">
									<div class="caption"><i class="icon-comments"></i>农产品分类详情</div>
								</div>
								<div class="portlet-body">
									<form action="#" class="form-horizontal" id="agrCategoryInfomation">
										<div class="control-group">
											<label class="control-label">编号（CODE）:</label>
											<div class="controls">
												<input type="text" placeholder="" class="m-wrap medium">
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">是否参与判定标准:</label>
											<div class="controls">
												<label class="radio"><input type="radio" name="optionsRadios1" value="option1" style=""/>是</label>
												<label class="radio"><input type="radio" name="optionsRadios1" value="option2" style="" checked >否</label>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">农产品中文名:</label>
											<div class="controls">
												<input type="text" placeholder="" class="m-wrap medium">
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">农产品英文名:</label>
											<div class="controls">
												<input type="text" placeholder="" class="m-wrap medium">
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">农产品拉丁名:</label>
											<div class="controls">
												<input type="text" placeholder="" class="m-wrap medium">
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">农产品中文别名:</label>
											<div class="controls">
												<input type="text" placeholder="" class="m-wrap medium">
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">农产品英文别名:</label>
											<div class="controls">
												<input type="text" placeholder="" class="m-wrap medium">
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">GEMS编码:</label>
											<div class="controls">
												<input type="text" placeholder="" class="m-wrap medium">
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">FOODEX_2编码:</label>
											<div class="controls">
												<input type="text" placeholder="" class="m-wrap medium">
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">图片路径:</label>
											<div class="controls">
												<span>
													<input id="agrImagePath" type="hidden"/>
													<img id="agrImage" src="<%=basePath%>/assets/systemImages/NOIMAGE.jpg" style="border: 1px solid #c8c8c8;" title="单击上传" title="单击上传" />
												</span>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">描述:</label>
											<div class="controls">
												<textarea class="span6 m-wrap" rows="1" style="width: 280px;"></textarea>
											</div>
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>
		</div>
		<!-- 弹出层 -->
		<div id="uploadPop" class="modal hide fade" tabindex="-1" data-width="636"></div>
		<div id="ajax-modal" class="modal hide fade" tabindex="-1"></div>
		<div id="responsive" class="modal hide fade" tabindex="-1" data-width="760"></div>
		<div id="arCategoryAnotherName" class="modal hide fade" tabindex="-1" data-width="350"></div>

		
		
		<script>
		jQuery(document).ready(function() {       
		var test = $("#ncpjcxxSeach input[type=checkbox]:not(.toggle), input[type=radio]:not(.toggle, .star)");
			if (test.size() > 0) {
				test.each(function () {
					$(this).show();
					$(this).uniform();
				});
			}
		groupCheckBoxAction('ncpjcxxSeachCheckGroup');
		var agrSeachHeight = $('#agrCategoryInfomation').height()+5;
		$("#agrSeach").css({"height":agrSeachHeight + "px"});
		$("#agrTree").css({"height":agrSeachHeight + "px"});
		
		     registAjaxDataTable({
			   	id:"agrNodeSeachTable",
			   	bPaginate: false,
			   	search:true,
			   	sScrollY:"400px",
			   	oLanguage:{
			    	"sProcessing": "正在加载数据...",
			        "sLengthMenu": "_MENU_",
			        "sZeroRecords": "没有符合项件的数据...",
			        "sInfo": "总共有 _TOTAL_ 项记录",
			        "sInfoEmpty": ""
			   	},
				actionUrl:'agrCategoryController.do?datagrid&versionid='+versionId,
				fnCallBefore: function (queryParams,aoData){
					return queryParams; 
				},
				aoColumns:[
						{ "mData": "agrcategorytype",
						  "sWidth": "30px",
						  "mRender" : function(data, type, full) {
							  var str = "";
							  if(full.agrcategorytype == 1){
								  str = '<span><img src="' + basePath + '/assets/img/icons/bullet_orange.png"/></span>';
							  }else{
								  str = '<span><img src="' + basePath + '/assets/img/icons/bullet_green.png"/></span>';  
							  }
							  return str;
						  }
						},
						{ "mData": "cname",
							"mRender" : function(data, type, full) {
							      return  '<a onclick="nodeOpen(\''+full.id+ '\')">'+data + '('+full.code+')'+'</a>' ;
							  }
						}]
			});
		});
		initModalsPage([{
       		"id" : "save_btn",
       		"operation" : "windowsave",
       		"modalId":"responsive",
       		"check":true,
			"url":"agrCategoryController.do?save",
			"formId":"addAgrCategoryInfomation",
			"callBack":function(data){ saveDom(data);},
			"refresh":false
		}]); 
		window.onresize = function(){
			var agrSeachHeight = $('#agrCategoryInfomation').height()+5;
			$("#agrSeach").css({"height":agrSeachHeight + "px"});
			$("#agrTree").css({"height":agrSeachHeight + "px"});
		};
		if(isPublished(versionId)){
			$("#agrCategoryInfomation").find("input,textarea").attr("disabled","disabled");
		}
		</script>
</body>
</html>				