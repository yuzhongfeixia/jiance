<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript" src="plug-in/tools/Map.js"></script>
<script type="text/javascript">
	//样式修正
	//App.initUniform();
	$("#form1").find("input[type='checkbox']").uniform();
	//初始化下标
	function resetTrNum(tableId) {
		$tbody = $("#"+tableId+"");
		$tbody.find('>tr').each(function(i){
			$(':input, select', this).each(function(){
				var $this = $(this), name = $this.attr('name'), val = $this.val();
				if(name!=null){
					if (name.indexOf("#index#") >= 0){
						$this.attr("name",name.replace('#index#',i));
					}else{
						var s = name.indexOf("[");
						var e = name.indexOf("]");
						var new_name = name.substring(s+1,e);
						$this.attr("name",name.replace(new_name,i));
					}
				}
			});
		});
		// 样式修正
		$("#form1").find("input[type='checkbox']").uniform();
	}
	//初始化下标
	function resetAttachmentNum() {
		$("#attachmentFile").find("li.attachmentFile-items").each(function(index){
			$($(this).find("input[type='hidden']")[0]).attr("name","monitoringAttachmentList[" + index + "].path");
		});
	}
	$('#addMonitoringProjectBtn').bind('click', function(){   
 		 var tr =  $("#add_monitoringProject_table_template tr").clone();
	 	 $("#add_monitoringProject_table").append(tr);
	 	 resetTrNum('add_monitoringProject_table');
	 	resetLayout();
    });  
	$('#delMonitoringProjectBtn').bind('click', function(){   
      	$("#add_monitoringProject_table").find("input:checked").closest("tr").remove();
        resetTrNum('add_monitoringProject_table'); 
        resetLayout();
    }); 
	function callbackForFile(data){
		if(data.success){
			var fileLinked = $("#attachmentFile");
			var attProperties = data.attributes.attProperties;
			for(var i = 0; i<attProperties.length; i++){
				var fileTemplete = $("#attachmentFile-line-templete li").clone();
				var path = attProperties[i].fileRelativePath + attProperties[i].fileName;
				$($(fileTemplete).find("input[type='hidden']")[0]).val(path);
				$($(fileTemplete).find("a[class='attachmentFile-item']")[0]).attr("href",path).html(attProperties[i].fileName);
				fileLinked.append(fileTemplete);
			}
			resetAttachmentNum(); 
			resetLayout();
		}
	}
	function achmentDownload(obj){
		var temp = document.createElement("form");  
	    temp.action = "monitoringPlanController.do?attachmentDownload";         
	    temp.method = "post";
	    temp.style.display = "none";
        var opt = document.createElement("input");
        opt.name = 'url';          
        opt.value = $(obj).attr("href");            
        temp.appendChild(opt);
	    document.body.appendChild(temp);
	    temp.submit();
	    return false;
	}
	function setWidth(data){
		$("#addFile").attr("data-width", data.arguments_0);
		return true;
	}
	function deleteAttachmentFile(e){
		$(event.target).closest("[class='attachmentFile-items']").remove();
		resetAttachmentNum(); 
		resetLayout();
	}
	function resetLayout(){
		$("#monitoring_plan_program_window1").modal('layout');
	}
	function monitoringPlanCheck(param){
		var fnName = $("#monitoring_plan_program_tab1").find("input[name='name']").val();
		var type = $("#monitoring_plan_program_tab1").find("select[name='type']").val();
		if(fnName == ""){
			modalTips("方案名称不能为空！");
			return false;
		}else if(type == ""){
			modalTips("请选择监测类型！");
			return false;
		}else if($("#add_monitoringProject_table").find("tr").length == 0){
			modalTips("请添加监测项目！");
			return false;
		}
		var result = true;
		var projectNameMap = new Map();
		$("#add_monitoringProject_table tr").each(function(index){
			$this = $(this);
			var name = $this.find("input[name='monitoringProjectList["+index+"].name']").val();
			var leadunit = $this.find("select[name='monitoringProjectList["+index+"].leadunit']").val();
			var industryCode = $this.find("select[name='monitoringProjectList["+index+"].industryCode']").val();
			var judgeVersionId = $this.find("select[name='monitoringProjectList["+index+"].judgeVersionId']").val();
			if(name == ""){
				result = false;
				modalTips("项目名称不能为空！");
				return false;
			} else if(leadunit == ""){
				result = false;
				modalTips("请选择牵头单位！");
				return false;
			} else if(industryCode == ""){
				result = false;
				modalTips("请选择行业！");
				return false;
			} else if(judgeVersionId == ""){
				result = false;
				modalTips("请选择标准版本！");
				return false;
			} else if(projectNameMap.get(name) != undefined && projectNameMap.get(name) != ""){
				result = false;
				modalTips("项目名称【"+name+"】有重复！");
				return false;
			} else if(! /^[\u4E00-\u9FA5\uf900-\ufa2d\w\.\s\-—\(\)（）]+$/.test(name) ){
				result = false;
				modalTips("项目名称【"+name+"】不能含有特殊字符！");
				return false;
			}
			//校验项目名称
			$.ajax({ 
		       type:"POST",
		       async:false,
		       url:"monitoringPlanController.do?isProjectNameCheck&rand="+Math.random(),
		       data:{
		    	   param : name,
		    	   id    : "${monitoringPlanPage.planCode}" 
		       },
		       success:function(data){
		    	   var d = $.parseJSON(data);
				   if (!d.success) {
					   result = false;
			    	   modalTips("项目名称【"+name+"】有重复！");
					   return false;
				   }
		        }
		    });
			projectNameMap.put(name,name);
		});
		return result;
	}
</script>
		<div class="row-fluid">
			<div class="span12">  
				<div class="portlet box popupBox_usual">
					<div class="portlet-title">
						<div class="caption">
							<i class="icon-reorder"></i>
							<span class="hidden-480">添加方案</span>
						</div>
					</div>
					<div class="portlet-body">
		                  <div class="tab-content">
							<div class="tab-pane active" id="monitoring_plan_program_tab1">
								<form id="form1" action="#" class="form-horizontal" validate="true">
								<input id="id" name="id" type="hidden" value="${monitoringPlanPage.id }">
								<input id="planCode" name="planCode" type="hidden" value="${monitoringPlanPage.planCode }">
									<div class="control-group">
										<label class="control-label">方案名称</label>
										<div class="controls">
											<input id="name" name="name" type="text" class="m-wrap" style="width:460px;" value="${monitoringPlanPage.name}" 
											datatype="s1-32" ajaxurl="monitoringPlanController.do?isNameCheck&id=${monitoringPlanPage.id}"/>
											<span class="help-inline"></span>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">监测类型</label>
										<div class="controls">
											<t:dictSelect field="type" hasLabel="false" typeGroupCode="monitorType"  defaultVal="${monitoringPlanPage.type}"  extend="{class:{value:'m-wrap'},datatype:{value:'*'},style:{value:'width:460px;'}}"></t:dictSelect>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">监测项目</label>
										<div class="controls">
<!-- 											<textarea id="monitoring_plan_program_textarea1" class="span6 m-wrap" rows="5" style="width:300px;"></textarea> -->
											<div style="padding: 3px; height: 25px;width:auto;" class="datagrid-toolbar">
												<a id="addMonitoringProjectBtn" href="#">添加</a> <a id="delMonitoringProjectBtn" href="#">删除</a> 
											</div>
											<table class="table table-striped table-bordered table-hover" style="width:90%;clear:none;">
												<thead>
													<tr>
														<th></th>
														<th>项目名称</th>
														<th>牵头单位</th>
														<th>行业</th>
														<th>标准选择</th>
													</tr>
												</thead>
												<tbody id="add_monitoringProject_table">	
												<c:if test="${fn:length(monitoringProjectList)  <= 0 }">
														<tr>
															<td align="center"><input style="width:20px;" type="checkbox" name="ck"/></td>
															  <td align="left"><input name="monitoringProjectList[0].name" type="text"></td>
															  <td align="left"><t:dictSelect field="monitoringProjectList[0].leadunit" hasLabel="false" customData="${organizationData}"  extend="{class:{value:'m-wrap'},style:{value:'width:368px;'}}"></t:dictSelect></td>
															  <td align="left"><t:dictSelect field="monitoringProjectList[0].industryCode" hasLabel="false" typeGroupCode="industry"  extend="{class:{value:'m-wrap'},style:{value:'width:60px;'}}"></t:dictSelect></td>
															  <td align="left"><t:dictSelect field="monitoringProjectList[0].judgeVersionId" hasLabel="false" customData="${judgeVersionData}"  extend="{class:{value:'m-wrap'},style:{value:'width:150px;'}}"></t:dictSelect></td>
											   			</tr>
												</c:if>
												<c:if test="${fn:length(monitoringProjectList)  > 0 }">
													<c:forEach items="${monitoringProjectList}" var="poVal" varStatus="stuts">
														<tr>
															<td align="center"><input  style="width:20px;" type="checkbox" name="ck"/></td>
															   <td align="left"><input name="monitoringProjectList[${stuts.index }].name" value="${poVal.name }" type="text"></td>
															   <td align="left"><t:dictSelect field="monitoringProjectList[${stuts.index }].leadunit" hasLabel="false" customData="${organizationData}" defaultVal="${poVal.leadunit}"  extend="{class:{value:'m-wrap'},style:{value:'width:368px;'}}"></t:dictSelect></td>
															   <td align="left"><t:dictSelect field="monitoringProjectList[${stuts.index }].industryCode" hasLabel="false" typeGroupCode="industry" defaultVal="${poVal.industryCode}"  extend="{class:{value:'m-wrap'},style:{value:'width:60px;'}}"></t:dictSelect></td>
															   <td align="left"><t:dictSelect field="monitoringProjectList[${stuts.index }].judgeVersionId" hasLabel="false" customData="${judgeVersionData}" defaultVal="${poVal.judgeVersionId}" extend="{class:{value:'m-wrap'},style:{value:'width:150px;'}}"></t:dictSelect></td>
											   			</tr>
													</c:forEach>
												</c:if>	
												</tbody>
											</table>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">附件</label>
										<div class="controls">
											<a class="btn green"action-mode="ajax" action-url="systemController.do?callUpload&type=file&callback=callbackForFile&multi=true&rename=false" action-pop="addFile" action-before="setWidth(730)">上传文件</a>
										</div>
									</div>
									<div class="control-group">
										<div class="fileLinked">
											<ul id="attachmentFile" class="attachmentFile-line">
												<c:if test="${fn:length(monitoringAttachmentList)  > 0 }">
													<c:forEach items="${monitoringAttachmentList}" var="attVal" varStatus="stuts">
														<li class="attachmentFile-items">
															<input type="hidden" name="monitoringAttachmentList[${stuts.index}].path" value="${attVal.path}"/>
															<a class="attachmentFile-item" href="${attVal.path}" data-download="${attVal.path}" view="true">${attVal.pathName}</a>
															<a class="attachmentFile-item" onclick="deleteAttachmentFile();"><i class="icon-remove"></i></a>
														</li>
													</c:forEach>
												</c:if>	
											</ul>
										</div>
									</div>
								</form> 
							</div>
							<div class="modal-footer">
								<button type="button" data-dismiss="modal" class="btn popclose">关闭</button>
								<button action-mode="ajax" action-operation="popsave" action-url="monitoringPlanController.do?save" action-form="#form1" type="button" action-before="monitoringPlanCheck" class="btn popenter"  action-fresh="#monitoring_plan_program_tb1">保存</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<!-- 添加 项目 模版 -->
		<table style="display:none">
		<tbody id="add_monitoringProject_table_template">
			<tr>
			 <td align="center"><input style="width:20px;" type="checkbox" name="ck"/></td>
				  <td align="left"><input name="monitoringProjectList[#index#].name" type="text"></td>
				  <td align="left"><t:dictSelect field="monitoringProjectList[#index#].leadunit" hasLabel="false" customData="${organizationData}"  extend="{class:{value:'m-wrap'},style:{value:'width:368px;'}}"></t:dictSelect></td>
				  <td align="left"><t:dictSelect field="monitoringProjectList[#index#].industryCode" hasLabel="false" typeGroupCode="industry"  extend="{class:{value:'m-wrap'},style:{value:'width:60px;'}}"></t:dictSelect></td>
				  <td align="left"><t:dictSelect field="monitoringProjectList[#index#].judgeVersionId" hasLabel="false" customData="${judgeVersionData}"  extend="{class:{value:'m-wrap'},style:{value:'width:150px;'}}"></t:dictSelect></td>
			</tr>
		 </tbody>
		<tbody id="add_monitoringAttachment_table_template">
			<tr>
			 <td align="center"><input style="width:20px;" type="checkbox" name="ck"/></td>
				  <td align="left"><input name="monitoringAttachmentList[#index#].path" maxlength="128" type="text" style="width:120px;"></td>
			</tr>
		 </tbody>
		</table>
		<ul id="attachmentFile-line-templete" style="display:none">
			<li class="attachmentFile-items">
				<input type="hidden" name="monitoringAttachmentList"/>
				<a class="attachmentFile-item" onclick="achmentDownload(this);return false;" view="true"></a>
				<a class="attachmentFile-item" onclick="deleteAttachmentFile();"><i class="icon-remove"></i></a>
			</li>
		 </ul>
		<div id="addFile" class="modal hide fade" tabindex="-1"></div>