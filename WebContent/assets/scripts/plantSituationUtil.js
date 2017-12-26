function getProjectCode(jsonParam){
	$("#projectCode").html('');
	var projectLevel = $('#projectLevel').val();
	var year = $('#year').val();
	var projectCode = $('#projectCode').val();
	var jsonData = {'projectCode':projectCode,'year':year,'projectLevel':projectLevel};
	$.extend(jsonData, jsonParam);
	// 取得项目编码
	$.ajax({
		async: false,
		type : 'POST',
		url : 'plantSituationController.do?getProjectCode',
		data: jsonData,
		success : function(data) {
			var dataJson = eval('(' + data + ')');
			if(dataJson.success){
				var projectCodeList = dataJson.attributes.projectCodeList;
				if(null != projectCodeList){
					setCodeSelectOptions(projectCodeList, 'projectCode', dataJson.attributes.projectCode);
				}
			}
		}
	});
}

function setCodeSelectOptions(list, selectid, defaultVal) {
	var proCodeSelector = $("#"+selectid);
	proCodeSelector.append("<option value='' selected></option>");
	for(var i = 0; i < list.length; i++){
		var projectCode = list[i].projectCode;
		var projectName = list[i].projectName;
		if(isEmpty(projectCode)){
			projectCode = "";
		}
		if(isEmpty(projectName)){
			projectName = "";
		}
		if(defaultVal == projectCode){
			proCodeSelector.append("<option value='" + projectCode + "' selected>"  + projectName +  "</option>");
		} else {
			proCodeSelector.append("<option value='" + projectCode + "'>"  + projectName +  "</option>");
		}
	}
}
