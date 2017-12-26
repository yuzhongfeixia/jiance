<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../main/navigator.jsp" %>
<%@ include file="../../../../../context/mytags.jsp" %>
<!-- <link rel="stylesheet" href="assets/plugins/data-tables/DT_bootstrap.css" /> -->
<!-- <script type="text/javascript" src="assets/plugins/select2/select2.min.js"></script> -->
<!-- <script type="text/javascript" src="assets/plugins/data-tables/jquery.dataTables.js"></script> -->
<!-- <script type="text/javascript" src="assets/plugins/data-tables/DT_bootstrap.js"></script> -->
<!-- <script src="assets/scripts/table-managed.js"></script> -->
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-confirm.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript" ></script>
<link href="assets/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css"/>
<link href="assets/plugins/treetable/stylesheets/screen.css" media="screen" />
<link href="assets/plugins/treetable/stylesheets/jquery.treetable.css" rel="stylesheet" type="text/css"/>
<link href="assets/plugins/treetable/stylesheets/jquery.treetable.theme.default.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="assets/plugins/treetable/jquery.treetable.js"></script>
<style>
table.treetable tr.selected {
  background-color: #3875d7;
  color: #fff;
}
</style>
<!-- END HEAD -->
<!-- BEGIN BODY -->

	<div class="row-fluid">
		<div class="span12">
			<!-- BEGIN EXAMPLE TABLE PORTLET-->
			<div class="portlet box box_usual">
				<div class="portlet-title">
					<div class="caption"><i class="icon-globe"></i></div>
				</div>
				<div class="portlet-body">
					<table id="areaCodeTable" class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th>行政区划</th>
								<th class="hidden-480">操作</th>
							</tr>
						</thead>
<!-- 						<tbody> -->
<%--  							<c:forEach items="${areacodeList}" var="areacode"> --%>
<%--  								<tr data-tt-id="${areacode.id}" data-tt-parent-id="${areacode.parentId}"> --%>
<%-- 									<td>${areacode.text}</td> --%>
<!-- 									<td> -->
<%-- 										<c:if test="${areacode.src == 'isPro'}"> --%>
<!-- 											<a class="btn mini green" action-mode="ajax" action-event="click" action-pop="#ajax-modal"  -->
<%-- 												href="sysAreaCodeController.do?add&pid=${areacode.id}"><i class="icon-plus"></i>新增</a> --%>
<%-- 										</c:if> --%>
<%-- 										<c:if test="${areacode.src == 'isCity'}"> --%>
<!-- 											<a class="btn mini green" action-mode="ajax" action-event="click" action-pop="#ajax-modal"  -->
<%-- 												href="sysAreaCodeController.do?update&id=${areacode.id}&parentId=${areacode.parentId}"><i class="icon-plus"></i>编辑</a> --%>
<!-- 											<a class="btn mini green" action-mode="ajax" action-event="click" action-pop="#ajax-modal"  -->
<%-- 												href="sysAreaCodeController.do?add&pid=${areacode.id}"><i class="icon-plus"></i>新增</a> --%>
<%-- 										</c:if> --%>
<%-- 										<c:if test="${areacode.src == 'isArea'}"> --%>
<!-- 											<a class="btn mini green" action-mode="ajax" action-event="click" action-pop="#ajax-modal"  -->
<%-- 												href="sysAreaCodeController.do?update&id=${areacode.id}&parentId=${areacode.parentId}"><i class="icon-plus"></i>编辑</a> --%>
<%-- 										</c:if> --%>
<!-- 									</td> -->
<!-- 								</tr> -->
<%-- 							</c:forEach> --%>
<!-- 					    </tbody> -->
	     			</table>
				</div>
			</div>	
			<!-- END EXAMPLE TABLE PORTLET-->
		</div>
	</div>

	<div id="ajax-modal" class="modal hide fade" tabindex="-1"></div>
	<div id="confirmDiv" class="modal hide fade"></div>
	<script>
		//$("#areaCodeTable").treetable({ expandable: true });
		var treeTable = null;
		jQuery(document).ready(function() {
			refresh_sysAreaCodeList();
		});
		
		function refresh_sysAreaCodeList(ad) {
			$.ajax({
				async: false,
				type : 'POST',
				url : 'sysAreaCodeController.do?areagrid',
				data:{},
				success : function(data) {
					var dataJson = eval('(' + data + ')');
					if(dataJson.success){
						// 总抽样数统计列表取得
						var areacodeList = dataJson.attributes.areacodeList;
						drewTableCells(areacodeList);
					}
				}
			});
			
			if (treeTable != null) {
				$("#areaCodeTable").removeData("treetable").removeClass("treetable");
			}
			treeTable = $("#areaCodeTable").treetable({ expandable: true });
			

			if (ad != undefined) {
				//var adJ = eval('(' + ad + ')');
				//alert(ad.attributes.expendId);
				var expendId = ad.attributes.expendId;
				
				// 递归展开父节点
				expendParent(expendId);
				$("#areaCodeTable").treetable("expandNode", expendId);

			}
			
			function expendParent(id) {
				var parentId = $("#"+id).attr("data-tt-parent-id");
				if (parentId != 'null') {
					$("#areaCodeTable").treetable("expandNode", parentId);
					expendParent(parentId);
				}
			}
		}
		

		
		function drewTableCells(list){	

			$("#areaCodeTable").html("");
			var htmls = '<thead><tr>';
			htmls += '<th class="hidden-480">行政区划</th>';
			htmls += '<th class="hidden-480">操作</th>';
			htmls += '</thead></tr>';
			htmls += '<tbody>';
			if(list != null && list.length > 0){
				for (var i=0;i<list.length;i++) {
					htmls += '<tr id="'+list[i].id+'" data-tt-id="'+list[i].id+'" data-tt-parent-id="'+list[i].parentId+'">';
					htmls += '<td>'+list[i].text+'</td> ';
					htmls += '<td>';
					if (list[i].src == 'isPro') {
						htmls += '<a class="btn mini green" action-mode="ajax" action-event="click" action-pop="#ajax-modal" href="sysAreaCodeController.do?add&pid='+list[i].id+'"><i class="icon-plus"></i>新增</a>';
					}
					if (list[i].src == 'isCity') {
						htmls += '<a class="btn mini green" action-mode="ajax" action-event="click" action-pop="#ajax-modal" href="sysAreaCodeController.do?update&id='+list[i].id+'&parentId='+list[i].parentId+'"><i class="icon-plus"></i>编辑</a>';
						htmls += '\n<a class="btn mini green" action-mode="ajax" action-event="click" action-pop="#ajax-modal" href="sysAreaCodeController.do?add&pid='+list[i].id+'"><i class="icon-plus"></i>新增</a>';
					}
					if (list[i].src == 'isArea') {
						htmls += '<a class="btn mini green" action-mode="ajax" action-event="click" action-pop="#ajax-modal" href="sysAreaCodeController.do?update&id='+list[i].id+'&parentId='+list[i].parentId+'"><i class="icon-plus"></i>编辑</a>';
					}
					htmls += '</td>'
					htmls += '</tr>';
				}
			} 
			htmls += '</tbody>';
			$("#areaCodeTable").html(htmls);
			
			
		}
	</script>


