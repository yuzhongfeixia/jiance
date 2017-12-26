<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
	$('#addMonitoringTaskInfoBtn').bind('click', function(){   
 		 var tr =  $("#add_monitoringTaskInfo_table_template tr").clone();
	 	 $("#add_monitoringTaskInfo_table").append(tr);
	 	 resetTrNum('add_monitoringTaskInfo_table');
    });
	$('#addMonitoringTaskInfoBtnCopy').bind('click', function(){  
		 var $table = $("#add_monitoringTaskInfo_table");
		 var length = $("#add_monitoringTaskInfo_table").find("tr").length - 1;
		 var areacode = $table.find("select[name='monitoringTaskList["+ length +"].areacode'] :selected").val();
		 var monitoringLink = $table.find("select[name='monitoringTaskList["+ length +"].monitoringLink'] :selected").val();
		 var agrCode = $table.find("select[name='monitoringTaskList["+ length +"].agrCode'] :selected").val();
		 var samplingCount = $table.find("input[name='monitoringTaskList["+ length +"].samplingCount']").val();
		 
	 	 var tr =  $("#add_monitoringTaskInfo_table_template tr").clone();
	 	 tr.find("select[name*='.areacode'] option[value='"+areacode+"']").attr("selected","selected");
		 tr.find("select[name*='.monitoringLink'] option[value='"+monitoringLink+"']").attr("selected","selected");
		 tr.find("select[name*='.agrCode'] option[value='"+agrCode+"']").attr("selected","selected");
		 tr.find("input[name*='.samplingCount']").val(samplingCount);
	 	 $("#add_monitoringTaskInfo_table").append(tr);
	 	 resetTrNum('add_monitoringTaskInfo_table');
   });
	//var cityCodeParam = taskAddedCount("${returnRows}");
	//alert(JSON.stringify(cityCodeParam));
    function delMonitoringTaskInfo(thisObj){
    	$(thisObj).parents("tr:eq(0)").remove();
        resetTrNum('add_monitoringTaskInfo_table'); 
	}
    function saveTask(){
	    var returnRows = "${returnRows}";
	    var samplingCountArr = "";
	    var areacodeArr = "";
	    var monitoringLinkArr = "";
	    var agrCodeArr = "";
	    var taskNameArr = "";
	    
	    var areacodeNameArr = "";
	    var monitoringLinkNameArr = "";
	    var agrCodeNameArr = "";
	    var totalCount = 0;
	    
	    // 抽样地区个数校验 已添加的地区个数
	    var cityCountCheck = true;
	    var cityCountParam = taskAddedCount("${returnRows}");
	    
    	$("#add_monitoringTaskInfo_table tr").each(function(index){
    		var $this = $(this);
    		// 抽样地区
    		var $areacode = $this.find("select[name*='.areacode']");
    		// 抽样环节
    		var $monitoringLink = $this.find("select[name*='.monitoringLink']");
    		// 抽样品种
    		var $agrCode = $this.find("select[name*='.agrCode']");
    		// 抽样数量
    		var $samplingCount = $this.find("input[name*='.samplingCount']");
    		
    		var areaName = $areacode.find("option[value='"+$areacode.val()+"']").text();
    		if($areacode.val() != "" && $areacode.val().substr(4,2) != "00"){
    			areaName = $areacode.find("option[value='"+$areacode.val().substr(0,4)+"00"+"']").text() + areaName;	
    		}
    		taskNameArr += index+"#KV#"+areaName+"_";
    		if(areacodeArr.indexOf("#KV#"+$areacode.val()+"#EM#") < 0){
    			areacodeNameArr += areaName + "、"; 
        	}
    		areacodeArr += index+"#KV#"+$areacode.val()+"#EM#";
    		
    		// 校验
    		if($areacode.val() == ""){
    			cityCountCheck = false;
    			modalAlert("抽样地区不能为空。");
    		}else if($samplingCount.val() == ""){
    			cityCountCheck = false;
    			modalAlert("抽样数量不能为空。");
    		}else if($samplingCount.val() < 1){
    			cityCountCheck = false;
    			modalAlert("抽样数量必须大于0。");
    		}else{
    			//抽样地区个数校验
	    		cityCountCheck = taskCityCountCheck(cityCountParam,$areacode.val(),$samplingCount.val(),areaName);	
    		}
    		if(cityCountCheck == false){
    			return false;
    		}
    		
    		var monitoringLinkName = $monitoringLink.find("option[value='"+$monitoringLink.val()+"']").text();
    		if($monitoringLink.val() != ""){
    			taskNameArr += monitoringLinkName+"_";
    		}
    		if($monitoringLink.val() != "" && monitoringLinkArr.indexOf("#KV#"+$monitoringLink.val()+"#EM#") < 0){
    			monitoringLinkNameArr += monitoringLinkName + "、"; 
        	}
    		monitoringLinkArr += index+"#KV#"+$monitoringLink.val()+"#EM#";
    		
    		var argName = $agrCode.find("option[value='"+$agrCode.val()+"']").text();
    		if($agrCode.val() != ""){
    			taskNameArr += argName+"_";
    		}
    		if($agrCode.val()!= "" && agrCodeArr.indexOf("#KV#"+$agrCode.val()+"#EM#") < 0){
    			agrCodeNameArr += argName + "、"; 
        	}
    		agrCodeArr += index+"#KV#"+$agrCode.val()+"#EM#";
    		
    		if($samplingCount.val() != ""){
    			totalCount +=   parseInt($samplingCount.val());
    		}
    		if($samplingCount.val() != ""){
	    		samplingCountArr += index+"#KV#"+$samplingCount.val()+"#EM#";
	    		taskNameArr += $samplingCount.val()+"#EM#";
    		}else{
    			samplingCountArr += index+"#KV#0#EM#";
    			taskNameArr += "0#EM#";
    		}
    		
    	});
    	
    	if(cityCountCheck==false){
    		return false;
    	}
    	
    	areacodeArr=areacodeArr.replace(/#EM#$/gi,"");
    	taskNameArr=taskNameArr.replace(/#EM#$/gi,"");
    	taskNameArr=taskNameArr.replace(/　/gi,"");
    	monitoringLinkArr=monitoringLinkArr.replace(/#EM#$/gi,"");
    	agrCodeArr=agrCodeArr.replace(/#EM#$/gi,"");
    	samplingCountArr=samplingCountArr.replace(/#EM#$/gi,"");
    	
    	areacodeNameArr=areacodeNameArr.replace(/、$/gi,"");
    	areacodeNameArr=areacodeNameArr.replace(/　/gi,"");
    	monitoringLinkNameArr=monitoringLinkNameArr.replace(/、$/gi,"");
    	agrCodeNameArr=agrCodeNameArr.replace(/、$/gi,"");
    	
    	
    	$("#add_monitoringTask_table").find("input[name='monitoringTaskList["+returnRows+"].areacode']").val(areacodeArr);
    	$("#add_monitoringTask_table").find("input[name='monitoringTaskList["+returnRows+"].taskname']").val(taskNameArr);
    	$("#add_monitoringTask_table").find("input[name='monitoringTaskList["+returnRows+"].monitoringLink']").val(monitoringLinkArr);
    	$("#add_monitoringTask_table").find("input[name='monitoringTaskList["+returnRows+"].agrCode']").val(agrCodeArr);
    	$("#add_monitoringTask_table").find("input[name='monitoringTaskList["+returnRows+"].samplingCounts']").val(samplingCountArr);
    	var $rows = $("#add_monitoringTask_table tr:eq("+returnRows+")");
    	$rows.find("td:eq(1)").text(totalCount);
    	$rows.find("td:eq(2)").text(areacodeNameArr);
    	$rows.find("td:eq(3)").text(monitoringLinkNameArr);
    	$rows.find("td:eq(4)").text(agrCodeNameArr);
    	
    	$("#monitoringTask_set").modal('hide');
    	
    }
    
    function taskAddedCount(row){
    	var param = {};
    	$("#add_monitoringAreaCount_table").find("tr").each(function(index){
    		var $this = $(this);
    		var cityCode = $this.find("select[name*='.citycode']").val();
    		var cityCount = $this.find("input[name*='.count']").val();
    		param["code_"+cityCode] = {"code":cityCode,"count":cityCount,"isCity":true,"addedCount":0};
		  	// 抽检县,市区
		  	var districtcode = $this.find("input[name='monitoringAreaCountList["+index+"].districtcode']").val();
		  	var districtArr = districtcode.split("#EM#");
		  	for(var i = 0;i < districtArr.length;i++){
		  		if(districtArr[i] != ""){
		  			var districtOne = districtArr[i].split("#KV#");
		  			param["code_"+districtOne[0]] = {"code":districtOne[0],"count":districtOne[1],"isCity":false,"addedCount":0,"cityCode":cityCode};
		  		}
		  	}
    	});  	
   		$("#add_monitoringTask_table tr").each(function(index){
   			if(row != index){
	   			$this = $(this);
	   			// 抽样数量
	   		  	var samplingCount = $this.find("input[name*='.samplingCounts']").val();
	   			// 抽样地区
	   		  	var areacode = $this.find("input[name*='.areacode']").val();
	   		 	var samplingCountMap = Map.strToMap(samplingCount);
	   		 	var areacodeMap = Map.strToMap(areacode);
			  	for(var i = 0;i < areacodeMap.size();i++){
			  		if(areacodeMap.get(i) != ""){
			  			var code = areacodeMap.get(i);
			  			var count = samplingCountMap.get(i);
			  			var codeObj = eval("param.code_"+code);
			  			if(codeObj != undefined){
			  				if(codeObj.isCity){
			  					codeObj["addedCount"] = codeObj["addedCount"] + parseInt(count);
			  				}else{
			  					codeObj["addedCount"] = codeObj["addedCount"] + parseInt(count);
			  					var cityCodeObj = eval("param.code_"+codeObj.cityCode);
			  					cityCodeObj["addedCount"] = cityCodeObj["addedCount"] + parseInt(count);
			  				}
			  			}else{
			  				modalTips("行政区划："+code+"，在第二步骤（地区及数量）中未设置！");
			  			}
			  		}
			  	}
	   		}
    	});
    	return param;
    }
    function taskCityCountCheck(cityCountParam,code,count,codeName){
    	codeName=codeName.replace(/　/gi,"");
    	//alert(JSON.stringify(cityCountParam));
    	var codeObj = eval("cityCountParam.code_"+code);
    	if(codeObj != undefined){
   			var addedCount = codeObj.addedCount + parseInt(count);
   			if(codeObj.count == 0 || codeObj.count >= addedCount){
   				codeObj["addedCount"] = addedCount;
   			}else{
   				modalAlert("抽样地区："+codeName+"，已设定的抽样数量总和（"+addedCount+"）超出在第二步骤（地区及数量）中的设定值（"+codeObj.count+"）。",{"width":"200px;"});
   				return false;
   			}
    		if(codeObj.isCity == false){
    			var cityCodeObj = eval("cityCountParam.code_"+codeObj.cityCode);
    			var cityAddedCount = cityCodeObj.addedCount + parseInt(count);
    			if(cityCodeObj.count >= cityAddedCount){
    				cityCodeObj["addedCount"] = cityAddedCount;
       			}else{
       				modalAlert("抽样地区："+codeName+"，所属抽检市已设定的抽样数量总和（"+cityAddedCount+"）超出在第二步骤（地区及数量）中抽检市的设定值（"+cityCodeObj.count+"）。",{"width":"200px;"});
       				return false;
       			}
    		}
    	}else{
    		modalAlert("地区："+codeName+"，在第二步骤（地区及数量）中未设置。");
    		return false;
    	}
    }
</script>
<div class="row-fluid">
	<div class="span12">  
		<div class="portlet box popupBox_usual">
			<div class="portlet-title">
				<div class="caption">
					<i class="icon-reorder"></i>
					<span class="hidden-480">任务详细</span>
				</div>
			</div>
			<div class="portlet-body form">
                  <div class="tab-content">
					<div class="tab-pane active">
						<form action="#" class="form-horizontal">
							<div>
								<a id="addMonitoringTaskInfoBtn" href="#"><i class="icon-plus"></i>添加</a> 
								<a id="addMonitoringTaskInfoBtnCopy" href="#"><i class="icon-book"></i>复制（上一条）</a> 
							</div> 
							<table class="table table-striped table-bordered table-hover" id="monitoringTaskInfo_table">
								<thead>
									<tr>
										<th class="hidden-480"><span style="color:red;">* </span>抽样地区</th>				
										<th class="hidden-480">抽样环节</th>
										<th class="hidden-480">抽样品种</th>
										<th class="hidden-480"><span style="color:red;">* </span>抽样数量</th>
										<th class="hidden-480"></th>
									</tr>
								</thead>
								<tbody id="add_monitoringTaskInfo_table">
									<c:if test="${fn:length(taskInfoList)  <= 0 }">
											<tr class="odd gradeX">
												<td>
													<t:dictSelect field="monitoringTaskList[0].areacode" hasLabel="false" customData="${areaCityStr}" ></t:dictSelect>
												</td>
												<td>
													<t:dictSelect field="monitoringTaskList[0].monitoringLink" hasLabel="false" customData="${samplelinkStr}" ></t:dictSelect>
												</td>
												<td>
													<t:dictSelect field="monitoringTaskList[0].agrCode" hasLabel="false" customData="${breedStr}" ></t:dictSelect>
												</td>
												<td><input type="text" class="m-wrap small" name="monitoringTaskList[0].samplingCount" /></td>
												<td>
													<ul style='list-style:none;'>
														<li style='float:left;'><a class="btn mini yellow" onclick="delMonitoringTaskInfo(this);"><i class="icon-trash"></i>删除</a></li>
													</ul>
												</td>
											</tr>
									</c:if>
									<c:if test="${fn:length(taskInfoList)  > 0 }">
										<c:forEach items="${taskInfoList}" var="poVal" varStatus="stuts">
											<tr class="odd gradeX">
												<td>
													<t:dictSelect field="monitoringTaskList[${stuts.index }].areacode" hasLabel="false" customData="${areaCityStr}" defaultVal="${poVal.areacode}" ></t:dictSelect>
												</td>
												<td>
													<t:dictSelect field="monitoringTaskList[${stuts.index }].monitoringLink" hasLabel="false" customData="${samplelinkStr}" defaultVal="${poVal.monitoringLink}" ></t:dictSelect>
												</td>
												<td>
													<t:dictSelect field="monitoringTaskList[${stuts.index }].agrCode" hasLabel="false" customData="${breedStr}" defaultVal="${poVal.agrCode}" ></t:dictSelect>
												</td>
												<td><input type="text" class="m-wrap small"  name="monitoringTaskList[${stuts.index }].samplingCount"  value="${poVal.samplingCount }"/></td>
												<td>
													<ul style='list-style:none;'>
														<li style='float:left;'><a class="btn mini yellow" onclick="delMonitoringTaskInfo(this);"><i class="icon-trash"></i>删除</a></li>
													</ul>
												</td>
											</tr>
										</c:forEach>
									</c:if>	

<!-- 									
									<tr class="odd gradeX">
										<td><select class="m-wrap" tabindex="1" style="width:150px;"><option value="" selected></option><optgroup label="南京市"><option>市辖区</option><option>白下区</option><option>玄武区</option></optgroup><optgroup label="无锡市"><option>崇安区</option><option>南长区</option><option>北塘区</option></optgroup></select></td>
										<td><select class="m-wrap" tabindex="1" style="width:100px;"><option value="" selected></option><option>生产基地</option><option>农贸市场</option><option>超市</option></select></td>
										<td><select class="m-wrap" tabindex="1" style="width:150px;"><option value="" selected></option><option>番茄</option><option>辣椒（青椒）</option><option>茄子</option><option>黄瓜</option></select></td>
										<td><input type="text" class="m-wrap small"/></td>
										<td>
											
										</td>
									</tr> -->
								</tbody>
							</table>
							
							
							<div class="modal-footer">
								<button type="button" data-dismiss="modal" class="btn popclose">关闭</button>
								<button type="button" class="btn popenter" onClick="saveTask()">确定</button>
							</div>
						</form> 
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<table style="display:none">
	<tbody id="add_monitoringTaskInfo_table_template">
		<tr class="odd gradeX">
			<td>
				<t:dictSelect field="monitoringTaskList[#index#].areacode" hasLabel="false" customData="${areaCityStr}" ></t:dictSelect>
			</td>
			<td>
				<t:dictSelect field="monitoringTaskList[#index#].monitoringLink" hasLabel="false" customData="${samplelinkStr}" ></t:dictSelect>
			</td>
			<td>
				<t:dictSelect field="monitoringTaskList[#index#].agrCode" hasLabel="false" customData="${breedStr}" ></t:dictSelect>
			</td>
			<td><input type="text" class="m-wrap small" name="monitoringTaskList[#index#].samplingCount" /></td>
			<td>
				<ul style='list-style:none;'>
					<li style='float:left;'><a class="btn mini yellow" onclick="delMonitoringTaskInfo(this);"><i class="icon-trash"></i>删除</a></li>
				</ul>
			</td>
		</tr>
	 </tbody>
 </table> 