function getProjects(queryParams){
	var monitorType = $('#monitorType').val();
	var year = $('#year').val();
	var params = {'monitorType':monitorType,'year':year};
	$.extend(params, queryParams);
	// 取得省市县项目
	$.ajax({
		async: false,
		type : 'POST',
		url : 'samplingInfoController.do?getProjects',
		data: params,
		success : function(data) {
			var dataJson = eval('(' + data + ')');
			if(dataJson.success){
				// 省项目
				var proTaskList = dataJson.attributes.proTaskList;
				// 市项目
				var cityTaskList = dataJson.attributes.cityTaskList;
				// 县项目
				var areaTaskList = dataJson.attributes.areaTaskList;
				if(null != proTaskList){
					setProjectSelectOptions(proTaskList, 'proselect', dataJson.attributes.proDefVal);
				}
				if(null != cityTaskList){
					setProjectSelectOptions(cityTaskList, 'cityselect', dataJson.attributes.citDefVal);
				}
				if(null != areaTaskList){
					setProjectSelectOptions(areaTaskList, 'areaselect', dataJson.attributes.areDefVal);
				}
			}
		}
	});
}
function setProjectSelectOptions(list, select, defaultVal) {
	var tabSelector = $("#"+select);
	for(var i = 0; i < list.length; i++){
		if('first' == defaultVal && 0 == i){
			tabSelector.append("<option value='" + list[i].projectCode + "' selected>"  + list[i].name +  "</option>");
			continue;
		}
		if(defaultVal == list[i].projectCode){
			tabSelector.append("<option value='" + list[i].projectCode + "' selected>"  + list[i].name +  "</option>");
		} else {
			tabSelector.append("<option value='" + list[i].projectCode + "'>"  + list[i].name +  "</option>");
		}
	}
}

function setTabSelect(){
	var tab1 = $('#tab_1_1');
	var tab2 = $('#tab_1_2');
	var tab3 = $('#tab_1_3');
	var sel_tab1 = $('#proselect');
	var sel_tab2 = $('#cityselect');
	var sel_tab3 = $('#areaselect');
	if (tab1.attr('class') == 'tab-pane active') {
		sel_tab2.val(null);
		sel_tab3.val(null);
	} else if (tab2.attr('class') == 'tab-pane active') {
		sel_tab1.val(null);
		sel_tab3.val(null);
	} else if (tab3.attr('class') == 'tab-pane active'){
		sel_tab1.val(null);
		sel_tab2.val(null);
	}
}
function checkReport(projectCode){
	var checkResult = false;
	var url = "samplingInfoController.do?checkReport";
	$.ajax({
		async: false,
		type : "POST",
		url : url,
		data : {'projectCode':projectCode},
		success : function(data) {
			var dataJson = eval('(' + data + ')');
   			 checkResult = dataJson.success;
		}
	});

	return checkResult;
}