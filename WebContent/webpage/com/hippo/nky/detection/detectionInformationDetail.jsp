<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<style>
table.detailViewTable td.detailTitleTd{
	width:40%;
}
table.detailTable td.detailTd{
	border:none;
}
.detailScrollDiv{
	max-height:300px;
	overflow-y: auto;
}
</style>
<div class="row-fluid">
	<div class="span12">  
		<div class="portlet box popupBox_usual">
			<div class="portlet-title">
				<div class="caption">
					<i class="icon-reorder"></i>
					<span class="hidden-480">检测详情</span>
				</div>
			</div>
			<div class="portlet-body">
				<span>单位: <span
					style="color: red; font-weight: bold; font-size: large;">种植业：mg
						/ l</span> <span
					style="color: red; font-weight: bold; font-size: large; margin-left: 20px">畜禽：μg
						/ kg</span>
				</span>
				<table class="table table-bordered table-hover detailViewTable">
					<c:if test="${isSample}">
						<tr>
							<td class="detailTitleTd">实验室编码</td>
							<td>
								<span style="padding: 8px;">${labCode}</span>
							</td>
						</tr>
						<tr>
							<td class="detailTitleTd">样品名称</td>
							<td>
								<span style="padding: 8px;">${sampleName}</span>
							</td>
						</tr>
						<tr>
							<td>污染物</td>
							<td>
								<div class="detailScrollDiv">
									<table class="detailTable">
										<c:forEach items="${detInfoList}" var="detailInfo" varStatus="stuts">
											<tr>
												<td class="detailTd">${detailInfo.pollName}</td>
												<c:set var="detInfoValueStr" value="${detailInfo.detectionValue < 0?'未检':detailInfo.detectionValue == 0?'未检出':detailInfo.detectionValue}"/>
												<td class="detailTd">${detInfoValueStr}</td>
											</tr>
										</c:forEach>
									</table>
								</div>
							</td>
						</tr>
					</c:if>
					<c:if test="${!isSample}">
						<tr>
							<td class="detailTitleTd">污染物</td>
							<td>
								<span style="padding: 8px;">${pollName}</span>
							</td>
						</tr>
						<tr>
							<td>样品</td>
							<td>
								<div class="detailScrollDiv">
									<table class="detailTable">
										<c:forEach items="${detInfoList}" var="detailInfo" varStatus="stuts">
											<tr>
												<td class="detailTd">实验室编码：${detailInfo.labCode}</td>
												<c:set var="detInfoValueStr" value="${detailInfo.detectionValue < 0?'未检':detailInfo.detectionValue == 0?'未检出':detailInfo.detectionValue}"/>
												<td class="detailTd">检测值：${detInfoValueStr}</td>
											</tr>
										</c:forEach>
									</table>
								</div>
							</td>
						</tr>
					</c:if>
				</table>
				<div class="modal-footer">
					<button type="button" data-dismiss="modal" class="btn popclose">关闭</button>
				</div>
			</div>
		</div>
	</div>
</div>
