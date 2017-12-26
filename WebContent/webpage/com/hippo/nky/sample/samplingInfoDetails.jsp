<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<body>
	<div class="row-fluid">
		<div class="span12">
			<div class="portlet box popupBox_usual">
				<div class="portlet-title">
					<div class="caption">
						<i class="icon-reorder"></i> <span class="hidden-480">样品信息</span>
					</div>
				</div>
				<div class="portlet-body">
					<table class="table table-striped table-bordered table-hover ">
						<tr>
							<td><label class="control-label">条码</label></td>
							<td>${samplingInfo.D_CODE }</td>
							<td><label class="control-label">样品名称</label></td>
							<td>${samplingInfo.CNAME }</td>
						</tr>
						<tr>
							<td><label class="control-label">受检单位名称</label></td>
							<td style="width: 250px;">${samplingInfo.UNIT_FULLNAME }</td>
							<td style="width: 150px;"><label class="control-label">法定代表人或负责人</label></td>
							<td>${samplingInfo.LEGAL_PERSON }</td>
						</tr>
						<tr>
							<td><label class="control-label">通讯地址</label></td>
							<td colspan="3">${samplingInfo.UNIT_ADDRESS }</td>
						</tr>
						<tr>
							<td><label class="control-label">联系电话</label></td>
							<td>${samplingInfo.TELPHONE }</td>
							<td><label class="control-label">邮政编码</label></td>
							<td>${samplingInfo.ZIP_CODE }</td>
						</tr>
						<tr>
							<td><label class="control-label">抽样环节</label></td>
							<td>${samplingInfo.TYPENAME }</td>
							<td><label class="control-label">抽样地点</label></td>
							<td>${samplingInfo.AREANAME }</td>
						</tr>
						<tr>
							<td><label class="control-label">抽样时间</label></td>
							<td>${samplingInfo.SAMPLINGDATE }</td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td><label class="control-label">备 注</label></td>
							<td colspan="3">${samplingInfo.REMARK}</td>
						</tr>
					</table>
					<div class="modal-footer">
						<button type="button" data-dismiss="modal" class="btn popclose">关闭</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>