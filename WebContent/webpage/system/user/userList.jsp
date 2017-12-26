<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../main/navigator.jsp" %>
<%@ include file="../../../context/mytags.jsp" %>

<link rel="stylesheet" href="assets/plugins/data-tables/DT_bootstrap.css" />
<script type="text/javascript" src="assets/plugins/data-tables/jquery.dataTables.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/DT_bootstrap.js"></script>

<script src="assets/plugins/bootstrap-modal/js/bootstrap-confirm.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript" ></script>
<link href="assets/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css"/>
<!-- END HEAD -->
<style type="text/css">
.control-item{
	width:350px;
	float:left;
}
</style>

<div class="row-fluid">
	<div class="span12">
		<!-- BEGIN EXAMPLE TABLE PORTLET-->
		<div class="portlet box box_usual">
			<div class="portlet-title">
				<div class="caption"><i class="icon-globe">用户信息</i></div>
			</div>
			<div class="portlet-body">
			<input type="hidden" value="${requestScope.clickFunctionId}" id="clickFunctionId">
			<form action="#" class="form-horizontal" name="searchForm" id="searchForm">
				<div class="alert alert-success">
						<div class="clearfix">
							<div class="table-seach">
								<label class="help-inline seach-label">用户名:</label>
								<div class="seach-element">
									<input type="text" placeholder="" class="m-wrap small" id="searchName" name="searchName">
								</div>
							</div>
							<div class="table-seach">
								<label class="help-inline seach-label">单位:</label>
								<div class="seach-element">
									<select id="TSDepart.id"  name="TSDepart.id" class="m-wrap small">
								       <option id="op" value="" selected="selected"></option>
								       <c:forEach items="${departList}" var="depart">
								        <option value="${depart.id }">${depart.departname}</option>
								       </c:forEach>
									</select>
								</div>
							</div>
							<div class="table-seach">
								<label class="help-inline seach-label">质检机构:</label>
								<div class="seach-element">
									<select id="organization.id"  name="organization.id" class="m-wrap small">
								       <option id="op" value="" selected="selected"></option>
								       <c:forEach items="${orgList}" var="org">
								        <option value="${org.id }">${org.ogrname}</option>
								       </c:forEach>
									</select>
								</div>
							</div>
							<div class="table-seach">
								<label class="help-inline seach-label">真实姓名:</label>
								<div class="seach-element">
									<input type="text" placeholder="" class="m-wrap small" id="realName" name="realName">
								</div>
							</div>
					
						</div>
				</div>
				<div class="clearfix">
					<div class="btn-group">
						<t:authFilter name="ajax_add_btn"><a class="btn btngroup_usual" data-toggle="modal" id="ajax_add_btn"><i class="icon-plus"></i>新增</a></t:authFilter>
						
					</div>
					<div class="pull-right">
						<t:authFilter name="searchBtn"><a href="#" class="btn btngroup_seach" id="searchBtn"><i class="icon-search"></i>搜索</a></t:authFilter>
<%-- 						<t:authFilter name="resetBtn"><button type="button" class="btn btngroup_usual" onclick="reset();" id="resetBtn"><i class="icon-reload"></i>重置</button></t:authFilter> --%>
					</div>
				</div>
				</form>
				<table class="table table-striped table-bordered table-hover" id="lst_table">
					<thead>
						<tr>
							<th>用户名</th>
							<th>单位</th>
							<th class="hidden-480">真实姓名</th>
							<th class="hidden-480">状态</th>
							<th class="hidden-480">操作</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
		<!-- END EXAMPLE TABLE PORTLET-->
	</div>
</div>
<div id="ajax-modal" class="modal hide fade" tabindex="-1"></div>
<div id="confirmDiv" class="modal hide fade" tabindex="-1"></div>

<script src="assets/scripts/user-managed.js"></script>
