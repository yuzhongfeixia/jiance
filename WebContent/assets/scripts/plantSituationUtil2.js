
function getProjectCode(jsonParam){
//	$("#projectCode").html('');
	var projectLevel = $('#projectLevel').val();
	var year = $('#year').val();
	//var projectCode = $('#projectCode').val();
	var jsonData = {'year':year,'projectLevel':projectLevel};
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
					setCodeSelectOptions(projectCodeList);
				} 
			} else {
				$("#showProject").html("");
				modalTips("没有相关项目！");
				$("#showProject").hide();
			}
		}
	});
	
	$(document).bind("click", function (e) {
		if ($(e.target).attr("id") == "projectNames") {
			return;
		}
		if ($(e.target).attr("name") == "projectChk" || $(e.target).attr("name") =='nspan') {
			return;
		}
		if ($(e.target).attr("id") == undefined || $(e.target).attr("id") != "showProject") {
			confirmAndClose();
		}
	 });
}

function setCodeSelectOptions(list) {
	var htmls = "";
	for(var i = 0; i < list.length; i++){
		var projectCode = list[i].projectCode;
		var projectName = list[i].projectName;
		htmls += "<input type='checkbox' name='projectChk' value='"+projectCode+"'/>&nbsp;<span name='nspan' id='"+projectCode+"'>"+projectName+"</span><br>";
		
	}
//	htmls += "<button type='button' onclick='confirmAndClose()' style='float:right'>关闭</button><button type='button' onclick='confirmAndClose()' style='float:right'>确定</button>";
	htmls += "<button type='button' onclick='confirmAndClose()' style='float:right'>确定</button>";
	$("#showProject").html(htmls);
	 App.initUniform();
}

function confirmAndClose() {
	var jsonRes = getSelectedProject();
	var projectNames = jsonRes.selectedProjectName;
	$("#projectNames").val(projectNames);
	$("#showProject").hide();
}

function showProjectDiv(){
	if ($("#showProject").text() == '') {
		modalTips("没有相关项目！");
	} else {
		$("#showProject").show();
	}
	
}

function getSelectedProject() {
	var jsonRes = {'selectedProjectCode':'','selectedProjectName':''};
	var selectedProjectCode = "";
	var selectedProjectName = "";
	//var parray = new Array();
	$("input[name='projectChk']").each(function(i,ele) {
		if ($(this).attr("checked") == "checked") {
			var projectCode = $(this).val();
			//parray.push(projectCode);
			selectedProjectCode += projectCode + ",";
			selectedProjectName += $('#'+projectCode).text() + ",";
			
		}
	});
	if (selectedProjectCode.indexOf(",") > 0) {
		jsonRes['selectedProjectCode'] =selectedProjectCode.substring(0, selectedProjectCode.length -1);
		jsonRes['selectedProjectName'] =selectedProjectName.substring(0, selectedProjectName.length -1);
	}
	//jsonRes['projectArray'] = parray;
	return jsonRes;
}

