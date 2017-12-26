<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webpage/main/navigator.jsp" %>
<%@ include file="/context/mytags.jsp"%>

<link rel="stylesheet" href="assets/plugins/data-tables/DT_bootstrap.css" />

<script type="text/javascript" src="assets/plugins/data-tables/jquery.dataTables.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/DT_bootstrap.js"></script>

<div class="row-fluid">
	<div class="span12">
		<div class="portlet box box_usual">
			<div class="portlet-title">
				<div class="caption"><i class="icon-globe"></i></div>
			</div>
			<div class="portlet-body">
				<div class="alert alert-success">
					<form action="#" class="form-horizontal">
						<div class="clearfix">
							<div class="table-seach">
								<label class="help-inline seach-label">化学式:</label>
								<div class="seach-element">
									<input type="text" placeholder="" class="m-wrap small">
								</div>
							</div>
							<div class="table-seach">
								<label class="help-inline seach-label">中文名:</label>
								<div class="seach-element">
									<input type="text" placeholder="" class="m-wrap small">
								</div>
							</div>
							<div class="table-seach">
								<label class="help-inline seach-label">英文名:</label>
								<div class="seach-element">
									<input type="text" placeholder="" class="m-wrap small">
								</div>
							</div>
							<div class="table-seach">
								<label class="help-inline seach-label">CASRN:</label>
								<div class="seach-element">
									<input type="text" placeholder="" class="m-wrap small">
								</div>
							</div>
						</div>
					</form>
				</div>
				<div class="clearfix">
					<div class="btn-group">
						<a class="btn btngroup_usual" data-toggle="modal" href=""> <i class="icon-plus"></i>新增</a>
					</div>
					<div class="pull-right">
						<a href="#" class="btn btngroup_seach"><i class="icon-search"></i>搜索</a>
					</div>
				</div>
				<table class="table table-striped table-bordered table-hover dataTable" id="toxicologyInfoTable">
					<thead>
						<tr>
							<th class="hidden-480">风险名称</th>
							<th class="hidden-480">风险值</th>
							<th class="hidden-480">不确定系数</th>
							<th class="hidden-480">ADI</th>
							<th class="hidden-480">ARFD</th>
							<th class="hidden-480">化学式</th>
							<th class="hidden-480">中文名</th>
							<th class="hidden-480">英文名</th>
							<th class="hidden-480">CASRN</th>
							<th class="hidden-480"></th>
						</tr>
					</thead>
					<tbody>
						<tr class="odd gradeX">
							<td>123213</td>
							<td>3213213</td>
							<td>32121</td>
							<td>23112</td>
							<td>ARFD</td>
							<td>423323</td>
							<td>fdsfdsdfs</td>
							<td>21331</td>
							<td>safasfasd</td>
							<th>
								<a  class="btn green mini"><i class="icon-th-list"></i>详情</a>
							</th>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<script>
	registDataTable("toxicologyInfoTable",false,"9");
</script>