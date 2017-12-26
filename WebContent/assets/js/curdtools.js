
//修改之前进行版本是否发布的check
function checkPublishForAdd(optUrl, versionid, datagrid){
	if(isPublished(versionid)){
		modalAlert("版本已经发布,不能再进行录入操作!");
		return;
	}
	add("录入", optUrl, datagrid);
}
//修改之前进行版本是否发布的check
function checkPublishForUpdate(optUrl, versionid, datagrid){
	var rowData = $('#'+ datagrid).datagrid('getSelected');
	if (null == rowData) {
		tip('请选择编辑项目');
		return;
	}
	if(isPublished(versionid)){
		modalAlert("版本已经发布,不能再进行编辑操作!");
		return;
	}
	update("编辑", optUrl, datagrid);
}
//判断是否发布
function isPublished(versionid){
	var isPublished = false;
	var url = "standardVersionController.do?isPublished";
	$.ajax({
		async: false,
		type : 'POST',
		url : url,
		data : {'id':versionid},
		success : function(data) {
			var dataJson = eval('(' + data + ')');
			isPublished = dataJson.success;
		},
		error : function() {
			modalAlert("取得版本信息失败,请刷新后重试!");
		}
	});
	return isPublished;
}
//判断是否停用
function isStoped(versionid){
	var isStoped = false;
	var url = "standardVersionController.do?isStoped";
	$.ajax({
		async: false,
		type : 'POST',
		url : url,
		data : {'id':versionid},
		success : function(data) {
			var dataJson = eval('(' + data + ')');
			isStoped = dataJson.success;
		},
		error : function() {
			modalAlert("取得版本信息失败,请刷新后重试!");
		}
	});
	return isStoped;
}
//禁用所有form中的控件
function disableForm(formId){
	 $("#" + formId + " input").attr("disabled", "disabled"); 
	 $("#" + formId + " select").attr("disabled", "disabled");
	 $("#" + formId + " textarea").attr("disabled", "disabled");
//	 $("#imageDiv").css({"display" : 'none'});
}
//禁用所有form中的控件
function unDisableForm(formId){
	 $("#" + formId + " input").removeAttr("disabled"); 
	 $("#" + formId + " select").removeAttr("disabled");
	 $("#" + formId + " textarea").removeAttr("disabled");
//	 $("#imageDiv").css({"display" : ''});
}
//修改之前进行版本是否发布的check
function judgePublishForAdd(optUrl, versionid, datagrid){
	if(judgePublish(versionid)){
		modalAlert("版本已经发布,不能再进行录入操作!");
		return;
	}
	add("录入", optUrl, datagrid);
}
//修改之前进行版本是否发布的check
function judgePublishForUpdate(optUrl, versionid, datagrid){
	var rowData = $('#'+ datagrid).datagrid('getSelected');
	if (null == rowData) {
		tip('请选择编辑项目');
		return;
	}
	if(judgePublish(versionid)){
		modalAlert("版本已经发布,不能再进行编辑操作!");
		return;
	}
	update("编辑", optUrl, datagrid);
}
//复制之前进行版本是否发布的check
function judgePublishForCopy(optUrl, versionid, datagrid){
	var rowData = $('#'+ datagrid).datagrid('getSelected');
	if (null == rowData) {
		tip('请选择复制项目');
		return;
	}
	if(judgePublish(versionid)){
		modalAlert("版本已经发布,不能再进行复制操作!");
		return;
	}
	update("复制", optUrl + "&isCopy=true", datagrid);
}
//判断是否发布
function judgePublish(versionid){
	var judgePublish = false;
	var url = "limitStandardVersionController.do?judgePublish";
	$.ajax({
		async: false,
		type : 'POST',
		url : url,
		data : {'id':versionid},
		success : function(data) {
			var dataJson = eval('(' + data + ')');
			judgePublish = dataJson.success;
		},
		error : function() {
			modalAlert("取得版本信息失败,请刷新后重试!");
		}
	});
	return judgePublish;
}
//判断是否停用
function judgeStop(versionid){
	var judgeStop = false;
	var url = "limitStandardVersionController.do?judgeStop";
	$.ajax({
		async: false,
		type : 'POST',
		url : url,
		data : {'id':versionid},
		success : function(data) {
			var dataJson = eval('(' + data + ')');
			judgeStop = dataJson.success;
		},
		error : function() {
			modalAlert("取得版本信息失败,请刷新后重试!");
		}
	});
	return judgeStop;
}
