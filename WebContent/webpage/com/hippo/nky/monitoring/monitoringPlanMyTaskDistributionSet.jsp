<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="row-fluid">
	<div class="span12">  
		<div class="portlet box popupBox_usual">
			<div class="portlet-title">
				<div class="caption">
					<i class="icon-reorder"></i>
					<span class="hidden-480">选择发布对象</span>
				</div>
			</div>
			<div class="portlet-body form">
				<form action="#" class="form-horizontal" id="monitoring_plan_mytask_set_form">
					<table class="table table-striped table-bordered table-hover" id="monitoring_plan_task_tb2">
						<thead>
							<tr>			
								<th class="hidden-480">抽样人员</th>
								<th class="hidden-480">分配数量</th>
								<th class="hidden-480">完成数量</th>
								<th class="hidden-480">查看任务分配情况</th>
							</tr>
						</thead>
						<tbody id="monitoring_plan_task_tb2_tbody">
						<c:if test="${fn:length(list)  > 0 }">
							<c:forEach items="${list}" var="poVal" varStatus="stuts">
								<tr class="odd gradeX"  data-toggle="modal" >
									 <td>${poVal.username }</td>
									 <td>
									 	<input name="monitoringTaskDetailsList[${stuts.index }].id" type="hidden" value="${poVal.taskDetailsId }">
									 	<input name="monitoringTaskDetailsList[${stuts.index }].padId" type="hidden" value="${poVal.padId }">
									 	<input name="monitoringTaskDetailsList[${stuts.index }].taskCode" type="hidden" value="${poVal.taskCode }">
									 	<input name="monitoringTaskDetailsList[${stuts.index }].taskCount" type="text" value="${poVal.taskCount }"  placeholder="" class="m-wrap" value="5" style="width:40px;">
									 </td>
									 <td >${poVal.reportNount }</td>
									 <td>
										<a action-mode="ajax" action-pop="#renwufa_fa1" action-before="taskDistributionInfo('${poVal.padId}','${poVal.taskCode}')" class="btn mini green" ><i class="icon-edit"></i> 查看</a>
									</td>
								</tr>
							</c:forEach>
						</c:if>	
						</tbody>
					</table>
					<div class="modal-footer">
						<button type="button" data-dismiss="modal" class="btn popclose">关闭</button>
						<button onclick="myTaskSetSave();" type="button" class="btn popenter" id="saveB1ut">确定</button>
					</div>
				</form> 
			</div>
		</div>
	</div>
</div>
<div id="renwufa_fa1" class="modal hide fade" tabindex="-1" data-width="700" >
	<div class="row-fluid">
		<div class="span12">  
			<div class="portlet box popupBox_usual">
				<div class="portlet-title">
					<div class="caption">
						<i class="icon-reorder"></i>
						<span class="hidden-480">任务分配情况</span>
					</div>
				</div>
				<div class="portlet-body form">
						<table class="table table-striped table-bordered table-hover" id="myTaskDistributionInfoTable">
							<thead>
								<tr>
									<th class="hidden-480">序号</th>				
									<th class="hidden-480">任务名称</th>
									<th class="hidden-480">分配数量</th>
									<th class="hidden-480">分配时间</th>
									<th class="hidden-480">完成情况</th>
								</tr>
							</thead>
							<tbody>
								<!-- <tr class="odd gradeX"  data-toggle="modal" >
									<td>1</td>
									<td >2013年江苏省第三次例行监测_玄武区_农贸市场_15</td>
									<td >5</td>
									<td >2013-08-04</td>
									<td >未完成</td>
								</tr> -->
							</tbody>
						</table>
						<div class="modal-footer">
							<button type="button" data-dismiss="modal" class="btn popclose">关闭</button>
						</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
jQuery(document).ready(function() {
	   registAjaxDataTable({
		   	id:"myTaskDistributionInfoTable",
			actionUrl:"monitoringPlanController.do?myTaskDistributionInfo",
			search:true,
			aoColumns:[
					{ "mData":"rowIndex"},
					{ "mData":"taskname"},
					{ "mData":"taskCount"},
					{ "mData":"assignTime","dateFormat":"yyyy-MM-dd"},
					{ "mData":"taskStatus","dataDic":"task_completed"}]
		});
	});
	
function taskDistributionInfo(jsonParam){
	var queryParams = getQueryParams("myTaskDistributionInfoTable");
	queryParams['padId'] = jsonParam.arguments_0;
	queryParams['taskCode'] = jsonParam.arguments_1;
	setQueryParams("myTaskDistributionInfoTable",queryParams);
	$("#myTaskDistributionInfoTable").dataTable().fnPageChange('first');
}
function myTaskSetSave(){
	var result = true;
	var totalCount = "${entity.samplingCount}";
	var setCount = 0;
	$("#monitoring_plan_task_tb2_tbody tr").each(function(index){
		var count = $(this).find("input[name*='.taskCount']").val();
		if(count == ""){
			count = 0;
		}else if(count == "0"){
		}else if(! /^\+?[1-9][0-9]*$/.test(count)){
			modalTips("数量【"+count+"】格式错误，只能输入整数！");
			result = false;
			return false;
		}
		setCount = setCount + parseInt(count);
	});
	if(!result){
		return false;
	}
	if(parseInt(totalCount) < setCount ){
		modalAlert("分配数量总和（"+setCount+"）大于任务抽样数量（"+totalCount+"）。",{"width":"200px;"});
		return false;
	}
	
	var saveArray = $('#monitoring_plan_mytask_set_form').getFormValue();
	var $modal = $("#mytask_distribution_set_window");
	//添加
	$.ajax({
       type:"POST",
       url:"monitoringPlanController.do?myTaskDistributionSetSave"+"&rand="+Math.random(),
       data:saveArray,
       success:function(data){
    	   var d = $.parseJSON(data);
		   if (d.success) {
			   $modal.modal('hide');
				refreshList($("#monitoring_plan_mytask_table"));
				modalTips(d.msg);
		   }else {
			   modalTips(d.msg);
		   }
        }
    });
}
</script>
