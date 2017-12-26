<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webpage/main/navigator.jsp"%>

<link href="assets/plugins/select2/select2_metro.css" rel="stylesheet" type="text/css" />
<link href="assets/plugins/data-tables/DT_bootstrap.css" rel="stylesheet" type="text/css" />
<link href="assets/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css"/>
<link href="assets/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>
	
<script src="assets/plugins/select2/select2.min.js" type="text/javascript"></script>
<script src="assets/plugins/data-tables/jquery.dataTables.js" type="text/javascript"></script>
<script src="assets/plugins/data-tables/DT_bootstrap.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript" ></script>
<script type="text/javascript" src="plug-in/tools/Map.js"></script>
<script type="text/javascript">
//自适应带有GIS的frame的Div大小
App.resetMapFrameDivSize();

function getMarkerFun(){
	var markeJson = {};
	var mapDatasjson = {};
	<c:forEach items="${organizationEntity}" var="entity" varStatus="status" >
		mapDatasjson["${status.index}"] = {
				"id":"${entity.id}",
				"longitude":"${entity.longitude}",
				"latitude":"${entity.latitude}",
				"info":"${entity.ogrname}<p>地址:${entity.address}<p>电话:${entity.leadertel}<p>"
		};
	</c:forEach> 
	markeJson["mapDatas"] = mapDatasjson;
    return markeJson;
}
function openInfo(id){
	window.frames["mapiframe"].openInfo(id); 
}
function organizationDistributionSearch(){
	var serarchText = $("#serarchText").val().trim();
	if(serarchText != ""){
		$("#organization_distribution_table tbody tr").each(function(index){
			if($(this).find("span").text().indexOf(serarchText) >= 0){
				$(this).css("display","");
			}else{
				$(this).css("display","none");
			}
		});	
	}else{
		$("#organization_distribution_table tbody tr").each(function(index){
			$(this).css("display","");
		});	
	}
	
}
</script>
<div class="row-fluid">
	<div class="span12">
		<div class="portlet box box_usual">
			<div class="portlet-title">
				<div class="caption"><i class="icon-globe"></i></div>
			</div>
			<div class="portlet-body">
				<div class="controls">
					<div class="row-fluid gisMapMaxDiv">
						<div class="span6" style="width:72.7%;">
							<iframe src="webpage/com/hippo/nky/report/map.html" frameborder="0" id="mapiframe" name="mapiframe" class="gisMapIframe"></iframe>
						</div>
						<div class="span6" style="width:27%;margin-left:0.3%;">
<!-- 							<div class="table-seach">
								<label class="help-inline seach-label">名称:</label>
								<div class="seach-element">
									<input type="text" placeholder="" class="m-wrap">
								</div>
							</div>
							<div class="pull-right">
								<a href="#" class="btn btngroup_seach"><i
									class="icon-search"></i>搜索</a>
							</div> -->
							<div class="clearfix">
								<div class="table-seach">
									<label class="help-inline seach-label" style="width:50px;">名称:</label>
									<div class="seach-element">
										<input type="text" placeholder="" class="m-wrap medium" name="serarchText" id="serarchText">
									</div>
								</div>
								<div class="pull-right">
									<a class="btn btngroup_seach" onclick="organizationDistributionSearch()"><i class="icon-search"></i>搜索</a>
								</div>
							</div>
							<table class="table table-striped table-bordered table-hover" id="organization_distribution_table" style="width: 380px">
								<thead>
									<tr>		
										<th class="hidden-480">质检机构</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${organizationEntity}" var="entity">
										<tr class="odd gradeX" onclick="openInfo('${entity.id}')">
											<td width="100px">
												<div class="blog-twitter-block">
													<!-- <img src="assets/img/u11_normal.png" alt="" /> -->
													<span>${entity.ogrname}</span><br/>
													地址：${entity.address}<br/>
													电话：${entity.leadertel}
												</div>										
											</td>
										</tr>
									</c:forEach> 
								</tbody>
							</table>
							<!-- <div class=pull-right>
								<div class="blog-twitter-block">
									<img src="assets/img/u11_normal.png" alt="" />
									江苏省农产品质量检验测试中心<br/>
									地址：草场门大街124号<br/>
									电话：025-12345678
								</div>
								<div class="space20"></div>
								<div class="blog-twitter-block">
									<img src="assets/img/u29_normal.png" alt="" />
									江苏省农产品质量检验测试中心<br/>
									地址：草场门大街124号<br/>
									电话：025-12345678
								</div>
								<div class="space20"></div>
								<div class="blog-twitter-block">
									<img src="assets/img/u31_normal.png" alt="" />
									江苏省农产品质量检验测试中心<br/>
									地址：草场门大街124号<br/>
									电话：025-12345678
								</div>
								<div class="space20"></div>
								<div class="blog-twitter-block">
									<img src="assets/img/u37_normal.png" alt="" />
									江苏省农产品质量检验测试中心<br/>
									地址：草场门大街124号<br/>
									电话：025-12345678
								</div>
								<div class="space20"></div>
								<div class="blog-twitter-block">
									<img src="assets/img/u39_normal.png" alt="" />
									江苏省农产品质量检验测试中心<br/>
									地址：草场门大街124号<br/>
									电话：025-12345678
								</div>
							</div> 
							<div class="pagination pagination-right">
								<ul>
									<li><a href="#">Prev</a></li>
									<li><a href="#">1</a></li>
									<li class="active"><a href="#">2</a></li>
									<li><a href="#">3</a></li>
									<li><a href="#">Next</a></li>
								</ul>
							</div>
							-->
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
jQuery(document).ready(function() {    
	dataTable = $('#organization_distribution_table').dataTable({
		"bAutoWidth": true,
		"bPaginate" : false,
		"sScrollY":"600px", //设定竖向滚动(指定高度)
		"sDom" : "Tt<'row-fluid'<'span6'><'span6'p>>",
		"aoColumnDefs" : [{'bSortable': false,"aTargets": [0] }]
	});

});
</script>
