<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<body>
<div class="row-fluid">
	<div class="span12">  
		<div class="portlet box popupBox_usual">
			<div class="portlet-title">
				<div class="caption">
					<i class="icon-reorder"></i>
					<span class="hidden-480">样品信息</span>
				</div>
			</div>
			<div class="portlet-body">
					<div class="tab-content">
						<table class="table table-striped table-bordered table-hover "
							style="margin: 10px 10px 10px 10px;">
							<tr>
								<td style="width: 150px;"><label class="control-label">条码</label></td>
								<td colspan="3">${infoEntity.dCode}</td>
							</tr>
							<tr>
								<td><label class="control-label">受检单位名称</label></td>
								<td style="width: 250px;">${infoEntity.unitFullname }</td>
								<td style="width: 150px;"><label class="control-label">法定代表人或负责人</label></td>
								<td>${infoEntity.legalPerson }</td>
							</tr>
							<tr>
								<td><label class="control-label">通讯地址</label></td>
								<td>${infoEntity.unitAddress }</td>
								<td><label class="control-label">邮政编码</label></td>
								<td>${infoEntity.zipCode }</td>
							</tr>
							<tr>
								<td><label class="control-label">联系电话</label></td>
								<td>${infoEntity.telphone }</td>
								<td><label class="control-label">企业性质</label></td>
								<td>私营</td>
							</tr>
							<tr>
								<td><label class="control-label">企业规模</label></td>
								<td>小微企业</td>
								<td><label class="control-label">主管单位</label></td>
								<td></td>
							</tr>
							<tr>
								<td><label class="control-label">产品类别</label></td>
								<td>白菜类</td>
								<td><label class="control-label">产品名称</label></td>
								<td>大白菜</td>
							</tr>
							<tr>
								<td><label class="control-label">规格型号</label></td>
								<td></td>
								<td><label class="control-label">生产批号</label></td>
								<td></td>
							</tr>
							<tr>
								<td><label class="control-label">检验类别</label></td>
								<td></td>
								<td><label class="control-label">抽样时间</label></td>
								<td><fmt:formatDate value="${infoEntity.samplingDate }"
										type="date" /></td>
							</tr>
							<tr>
								<td><label class="control-label">抽样地点</label></td>
								<td>${infoEntity.cityCode }</td>
								<td><label class="control-label">商 标</label></td>
								<td></td>
							</tr>
							<tr>
								<td><label class="control-label">样本大小（n）/样本基数（N）</label></td>
								<td></td>
								<td><label class="control-label">批 量</label></td>
								<td></td>
							</tr>
							<tr>
								<td><label class="control-label">产品执行标准</label></td>
								<td></td>
								<td><label class="control-label">抽样环节</label></td>
								<td>${infoEntity.monitoringLink }</td>
							</tr>
							<tr>
								<td><label class="control-label">备 注</label></td>
								<td colspan="3">${infoEntity.remark}</td>
							</tr>
						</table>
						<div class="modal-footer">
							<button type="button" data-dismiss="modal" class="btn popclose">关闭</button>
						</div>
					</div>
				</div>
		</div>
	</div>
</div>
</body>
</html>