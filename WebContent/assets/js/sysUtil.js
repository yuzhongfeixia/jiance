var Sys = {}; 
var userAgenteName = navigator.userAgent.toLowerCase(); 
var osResult; 
// 取得浏览器类型及版本
(osResult = userAgenteName.match(/msie ([\d.]+)/)) ? Sys.ie = osResult[1] : 
(osResult = userAgenteName.match(/firefox\/([\d.]+)/)) ? Sys.firefox = osResult[1] : 
(osResult = userAgenteName.match(/chrome\/([\d.]+)/)) ? Sys.chrome = osResult[1] : 
(osResult = userAgenteName.match(/opera.([\d.]+)/)) ? Sys.opera = osResult[1] : 
(osResult = userAgenteName.match(/version\/([\d.]+).*safari/)) ? Sys.safari = osResult[1] : 0; 
var localObj = window.location;
var paths = localObj.pathname.split("/");
var path = "";
if(paths.length>=3){
	path = localObj.pathname.split("/")[1];
}
var basePath = localObj.protocol+"//"+localObj.host+"/"+path;
function getActionPath(url){
	if(!path.startWith("/")){
		path = "/" + path;
	}
	if(url.startWith("/")){
		if("/" == path){
			return url;
		}
		// 没有指定工程名的情况
		return path + url;
	} else {
		if("/" == path){
			return "/" + url;
		}
		// 指定了工程名的情况
		return path + "/" + url;
	}
}
/** 给String方法添加trim函数 */
String.prototype.trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, ""); 
};
String.prototype.lTrim = function() {
	return this.replace(/(^\s*)/g, ""); 
};
String.prototype.rTrim = function() {
	return this.replace(/(\s*$)/g, ""); 
};
/** 给String方法添加startWith函数 */
String.prototype.startWith=function(str){
	if(this==null||str==""||this.length==0||str.length>this.length){
		return false;
	}
	if(this.substr(0,str.length)==str){
		return true;
	} else {
		return false;
	}
	return true;
};
/** 给String方法添加endWith函数 */
String.prototype.endWith=function(str){
	if(str==null||str==""||this.length==0||str.length>this.length){
		return false;
	}
	if(this.substring(this.length-str.length)==str){
		return true;
	} else {
		return false;
	}
	return true;
};
/** 给String方法添加isEmpty函数 */
String.prototype.isEmpty=function(){
	if(null == this || "" == this || "undefined" == this){
		return true;
	} else {
		return false;
	}
};
/** 给String方法添加isNotEmpty函数 */
String.prototype.isNotEmpty=function(){
	if(null == this || "" == this || "undefined" == this){
		return false;
	} else {
		return true;
	}
};

function isEmpty(obj){
	if(null == obj || "" == obj || "undefined" == obj){
		return true;
	} else {
		return false;
	}
}
function isNotEmpty(obj){
	if(null == obj || "" == obj || "undefined" == obj){
		return false;
	} else {
		return true;
	}
};
/**
 * 加载Content内容
 * @param eventElem 点击的元素名或元素对象
 * @param url 加载链接
 */
function loadContent(eventElem, url){
	var element = null;
	if(url.isEmpty() || "#" == url){
		return;
	}
	if(typeof(eventElem) == 'undefined'){
		return;
	}
	if(typeof(eventElem) == 'string'){
		element = $("#" + eventElem);
	} else {
		element = $(eventElem);
	}
	if(element.length <= 0){
		return;
	}
	
	var parentItems = "";
	$(".breadcrumb li").each(function(){
		if(!$(this).children("i").hasClass("icon-home")){
			if($(this).children("a").attr("src") == url){
				return false;
			}
			parentItems += "'" + $(this).children("a").text().trim() + "':'" + $(this).children("a").attr("src") + "',";
		}
	});
	var pageTitle = $(".page-title").html();
	var titles = "";
	var menuName = "";
	var menuCaption = "";

	if(Sys.ie){
		titles = pageTitle.split("<SMALL>");
		menuCaption = titles[1].replace("</SMALL>", "");
		if(element[0].tagName == "A" || element[0].tagName == "BUTTON"){
			parentItems += "'" + element.text().trim() + "':'" + url + "',";
		} else if(element[0].tagName == "INPUT"){
			parentItems += "'" + element.val().trim() + "':'" + url + "',";
		}
	} else {
		titles = pageTitle.split("<small>");
		menuCaption = titles[1].replace("</small>", "");
		if(element[0].localName == "a" || element[0].localName == "button"){
			parentItems += "'" + element.text().trim() + "':'" + url + "',";
		} else if(element[0].localName == "input"){
			parentItems += "'" + element.val().trim() + "':'" + url + "',";
		}
	}
	menuName = titles[0].trim();
	var jsonArray = {};
	jsonArray["menuName"] = menuName;
	jsonArray["menuCaption"] = menuCaption;
	jsonArray["parentItems"] = parentItems;
	
	$.extend(jsonArray,getURLParams(url));
	
	var pageContent = $('.page-content');
	var pageContentBody = $('#contenter');
	App.blockUI(pageContent, false);
	$.post(url, jsonArray, function(res){
		App.unblockUI(pageContent);
		pageContentBody.html(res);
		App.fixContentHeight(); // fix content height
		App.initUniform(); // initialize uniform elements
		Validator.init();
    });
}
function getURLParams(url){
	 var pattern = /(\w+)=(\w+)/ig;
	 var parames = {};
	 url.replace(pattern, function(a, b, c){
	  parames[b] = c;
	 });
	 return parames;
	}
/**
 * 注册datatable组件
 * 
 * @param id table的ID
 * @param haveCheckBox 是否有CheckBox
 * @param unSortColArray 不进行排序的数据列(用逗号分隔，不填默认都进行排序)
 */
function registDataTable(id, haveCheckBox, unSortColArray){
//	var JSONObj = jQuery.parseJSON(json);
	var array = new Array();
	var sort = null;
	if(haveCheckBox){
		sort = {'bSortable': false,'aTargets': [0]};
		array.push(sort);
	}
	
	if(unSortColArray != undefined && unSortColArray.isNotEmpty()){
		var sortIndexs = unSortColArray.split(",");
		for(var col in sortIndexs){
			if(sortIndexs[col].isEmpty()){
				continue;
			}
			array.push({'bSortable': false,'aTargets': [parseInt(sortIndexs[col])]});
		}
	}
	$('#' + id).dataTable({
        "iDisplayLength": 10, // 每页显示数量
        "sDom": "t<'row-fluid'<'span_paginate'p>>", // 显示分页条
        "sPaginationType": "bootstrap", // 分页类型为bootstrao
        "oLanguage": {
            "sLengthMenu": "_MENU_ 每页显示条数",
            "oPaginate": {
                "sPrevious": "上一页",
                "sNext": "下一页"
            }
        },
        "aoColumnDefs": array //定义不进行排序的列 
    });
	// 如果含有checkbox 则继续注册checkbox组控制
	if(haveCheckBox){
		groupCheckBoxAction(jQuery('#'+ id + ' .group-checkable'));
	}
}

/**
 * checkbox组控制，由checkBoxId控制样式为checkBoxGroup的checkbox组统一的选中和取消
 * 在初始化datatable时如果haveCheckBox=true，checkBoxId却为空时，会按默认checkBox
 * 
 * @param checkBoxId 主控制checkbox的ID 或者 选择器对象
 */
function groupCheckBoxAction(checkBoxId){
	var selector = null;
	// 如果是字符串则取得选择器 如果是object认为是选择器对象
	if(typeof(checkBoxId) == 'string'){
		if(checkBoxId.isEmpty()){
			return;
		}
		selector = $("#" + checkBoxId);
	} else if(typeof(checkBoxId) == 'object'){
		selector = checkBoxId;
	} else {
		return;
	}
	if(selector.length <= 0){
		return;
	}
    jQuery(selector).change(function () {
    	// 取得主控制checkbox的data-set属性
        var set = jQuery(this).attr("data-set");
        var checked = jQuery(this).is(":checked");
        // 循环set中所有class在同一个group中的元素
        jQuery(set).each(function () {
            if (checked) {
                $(this).attr("checked", true);
            } else {
                $(this).attr("checked", false);
            }
        });
        // 调用uiform函数美化和设置值
        jQuery.uniform.update(set);
    });
}



/**
 * 动态注册datatable组件
 * 
 * @param id table的ID
 * @param haveCheckBox 是否有CheckBox
 * @param unSortColArray 不进行排序的数据列(用逗号分隔，不填默认都进行排序)
 */
function registAjaxDataTable(param){
	// *必填项：id,actionUrl,aoColumns
	// *选填项：initModals,bPaginate,search
	//初始化 dataDic,dataHidden,dateFormat
	param = initDataConversion(param);
	var sDom = "t<'row-fluid'<'span6'i><'span6'p>>";
	if(param.bPaginate == false){
		sDom = "t";
	}
	if(param.tableTools){
		sDom = "T" + sDom;
	}
	var _initModals = param.initModals;
	var oTable = $('#' + param.id).dataTable({
//		"bProcessing": true, //设置异步请求时，是否有等待框。
		"bStateSave": false, //保存状态到cookie
	//  'bFilter':false,//是否使用内置的过滤功能。
		'bPaginate':param.bPaginate == false ?param.bPaginate:true,//是否分页。
		"sScrollY":param.sScrollY?param.sScrollY:"", //设定竖向滚动(指定高度)
		"bAutoWidth": false,
		"sAjaxSource": param.actionUrl,//获取数据的ajax方法的URL        
		"bServerSide":true,    //服务器端必须设置为true 异步请求必须设置//服务端处理分页
		"fnDrawCallback": param.fnDrawCallback?param.fnDrawCallback:function( oSettings ) {
			var oTable = $('#' + param.id).dataTable();
			var parmArray = oTable.fnSettings().aoColumns;
			$.each(parmArray,function(index,data){
				if(!data.bVisible){
					oTable.fnSetColumnVis(index, false);
					return;
				}
    		});
		},
		"fnRowCallback": param.fnRowCallback?param.fnRowCallback:function( oSettings ) {},
//		"sAjaxDataProp":"msgJson.list",
		"fnServerData": function (sSource, aoData, fnCallback ) { //与后台交互获取数据的处理函数      
			//组合查询条件
			//var searchArray = $('#searchForm').serialize();
			var queryParams = {};
			if(param.search == true ){
				queryParams = getQueryParams(param.id);
			}
			if(param.fnCallBefore?true:false){
					param.fnCallBefore(queryParams,aoData);
			}
			//aoData.push({"name": "pageSum", "value": "44"});
			if(param.dataDic != undefined){
				aoData.push({"name": "dataDic", "value": param.dataDic});
			}
			if(param.dataHidden != undefined){
				aoData.push({"name": "dataHidden", "value": param.dataHidden});
			}
			if(param.dateFormat != undefined){
				aoData.push({"name": "dateFormat", "value": param.dateFormat});
			}
			queryParams['aoData'] = JSON.stringify(aoData);
			 $.ajax({
				 "dataType" : 'json',
				 "type" : "post",
				 "url" : sSource,
				 "data" :$.extend(param.queryParams, queryParams),//以json格式传递
				 "success" : fnCallback
		    });             
		},
		//表字段说明 设定是否参加排序
	    "aoColumns": param.aoColumns,
	    // set the initial value
	    //"iDisplayStart": 0, 
	    "iDisplayLength": param.iDisplayLength ? param.iDisplayLength : 10,
	    "sDom": sDom,
	    "oTableTools": {
	    	"sSwfPath": "assets/plugins/data-tables/swf/copy_csv_xls_pdf.swf"
			,"sRowSelect": "single"
			,"aButtons": []
		},
	    "sPaginationType": "bootstrap",
	    "oLanguage": param.oLanguage?param.oLanguage:{
	    	"sProcessing": "正在加载数据...",
	        "sLengthMenu": "_MENU_",
	        "sZeroRecords": "没有符合项件的数据...",
	        "sInfo": "当前数据为从第 _START_ 到第 _END_ 项数据；总共有 _TOTAL_ 项记录",
	        "sInfoEmpty": "当前数据为从第0 到第 0 项数据；总共有 0 项记录",
	        "sInfoFiltered": "(_MAX_)",
	        "oPaginate": {
	            "sPrevious": "上一页",
	            "sNext": "下一页"
	        }
	    },
	    "aoColumnDefs":  param.aoColumnDefs?param.aoColumnDefs:
	    [{
	            'bSortable': false,
	            'aTargets': [0]
	        }
	    ]
	});
	if(_initModals != undefined && _initModals != "" && _initModals.length > 0){
		initModalsPage(_initModals,param.id);
	}
//	oTable.click(function(event) {
//		$(oTable.fnSettings().aoData).each(function (){
//			$(this.nTr).removeClass('row_selected');
//		});
//		$(event.target.parentNode).addClass('row_selected');
//	});
	return oTable;
}

/**
 * registAjaxDataTable附属方法
 * 初始化数据转换
 */
function initDataConversion(param){
	var aoColumns = param.aoColumns;
	/*dataDic : {"stopflag":"stopstart","publishmark":"publish"},
	dataHidden:"category",
	dateFormat:{"begindate":"yyyy-MM-dd"},*/
	var dataHidden = param.dataHidden?param.dataHidden+",":"";
	var dataDic = param.dataDic?param.dataDic:{};
	var dateFormat = param.dateFormat?param.dateFormat:{};
	for(var i=0;i<aoColumns.length;i++){
		// 添加数据字典功能
		if(aoColumns[i].dataDic != undefined){
			dataDic[aoColumns[i].mData] = aoColumns[i].dataDic;
		}
		// 添加格式化功能
		if(aoColumns[i].dateFormat != undefined){
			dateFormat[aoColumns[i].mData] = aoColumns[i].dateFormat;
		}
		if(aoColumns[i].dataHidden != undefined && aoColumns[i].dataHidden){
			dataHidden = dataHidden + aoColumns[i].mData + ",";
			aoColumns.splice(i,1);
			i = i - 1;
		}else{
			// 添加显示效果
			if(aoColumns[i].mRender == undefined){
				if(aoColumns[i].dataDic != undefined){
					eval("var mRender = function(data, type, full){ return full."+aoColumns[i].mData+"_name; }");
					aoColumns[i].mRender =  mRender;
				}
				if(aoColumns[i].button != undefined){
					if(aoColumns[i].button.constructor != Array){
						aoColumns[i].button = [aoColumns[i].button];
					};
					var jsStr = ' var resultStr = "";  ';
					for(var j=0;j<aoColumns[i].button.length;j++){
						//取得一个按钮进行处理
						var oneButtonObj = aoColumns[i].button[j];
						// 拼接按钮代码
						var oneButtonString = "<a ";
						if(oneButtonObj.className != undefined){
							oneButtonString += 'class="'+oneButtonObj.className+'" ';
						}
						if(oneButtonObj.onclick != undefined){
							oneButtonString += 'onclick="'+paramsConversion(oneButtonObj.onclick)+'" ';
						}
						oneButtonString += ">" +oneButtonObj.buttonName + "</a>&nbsp;";
						
						// 判断权限
						if(oneButtonObj.dataAuthority != undefined){
							jsStr += 'if(dataAuthorityCheck(full,\''+JSON.stringify(oneButtonObj.dataAuthority)+'\')){ resultStr += \''+oneButtonString+'\'; }';
						}else{
							jsStr += ' resultStr +=  \''+oneButtonString+'\'; ';
						}
					}
					jsStr += 'resultStr=resultStr.replace(/&nbsp;$/gi,""); return resultStr;';
					eval('var mRender = function(data, type, full){ ' + jsStr + ' }');
					aoColumns[i].mRender =  mRender;
				}
			}else{
				
			}
			// 不排序
			aoColumns[i].bSortable =  false;
		}
	}
	dataHidden=dataHidden.replace(/,$/gi,"");
	param.dataHidden = dataHidden;
	param.dataDic = dataDic;
	param.dateFormat = dateFormat;
	param.tableTools = param.tableTools || false;
	return param;
}

/**
 * registAjaxDataTable附属方法
 * 数据权限校验 校验规则
 * 例 [{"publishmark":"0,1","stopflag":"1"},{"publishmark":"1","stopflag":"2"}]
 * [{"publishmark":"0,1","stopflag":"1"},{"publishmark":"1","stopflag":"2"}] 满足其中一套即可
 * {"publishmark":"0,1","stopflag":"1"} 中 publishmark,stopflag都得有满足的值
 * "publishmark":"0,1" 中 publishmark至少满足一个值
 */
function dataAuthorityCheck(full,dataAuthorityStr){
	var dataAuthority  = jQuery.parseJSON(dataAuthorityStr);
	if(dataAuthority.constructor != Array){
		dataAuthority = [dataAuthority];
	};
	var dataAuthorityCheck = false;
	for(var i=0;i<dataAuthority.length;i++){
		var oneAuthorityObj = dataAuthority[i];
		var oneAuthorityCheck = true;
		for(var key in oneAuthorityObj){
			var values = oneAuthorityObj[key].split(",");
			var valuesCheck  = false;
			for(var j=0;j<values.length;j++){
				if(values[j] == eval("full."+key)){
					valuesCheck = true;
					break;
				}
			}
			if(!valuesCheck){
				oneAuthorityCheck = false;
				break;
			}
		}
		if(oneAuthorityCheck){
			dataAuthorityCheck = true;
			break;
		}
	}
	return dataAuthorityCheck;
}

/**
 * registAjaxDataTable附属方法
 * 参数转换
 * toArgCategory(id,category,'2')
 * To
 * toArgCategory('4028e4024274a5a5014274f741e20027','0','2')
 */
var paramsConversion = function(method){
	// 执行method(param)函数
	if(method != undefined){
		var data = "(";
		// 拼接需要执行的after函数，并将data作为参数传入
		var methodFullName = "";
		var pStart = method.indexOf("(");
		var pEnd = method.indexOf(")");
		if(pStart > 0 && (pEnd > (pStart + 1))){
			methodFullName = method.substring(0, pStart);
			// 截取时不需要"("，所以+1
			var paramStr = method.substring((pStart + 1), pEnd);
			var params = paramStr.split(",");
			for(var i = 0; i < params.length; i++){
				if(params[i] == '\'this\''){
					params[i] = ''+'this'+'';
				}else if(params[i].indexOf("'") < 0){
					params[i] = '\\\'\'+'+'full.'+params[i]+'+\'\\\'';
				}else{
					var paramsTemp = params[i].replace(/(^\')|(\'$)/g,'');
					params[i] = '\\\''+paramsTemp+'\\\'';
				}
			}
			data += params.toString();
		} else {
			// 没有括号的情况或者参数为空的情况
			method = method.replace("()","");
			methodFullName = method;
		}
		data += ")";
		// 执行after函数
		return methodFullName+data;
	}
};

/**
 * 初始化
 */
function initModalsPage(_initModals,_dataTableId) {
	for(var i=0;i<_initModals.length;i++){
		if(_initModals[i].id != ""){
			_initModals[i]['dataTableId'] = _dataTableId;
			addEvent(_initModals[i]);
		}
	}
}
// 取得存放参数list
function getQueryParams(id){
		var settings = $("#"+id).dataTable().fnSettings();
		return settings.queryParams?settings.queryParams:{};
	
}
//设置存放参数list
function setQueryParams(id,queryParams){
	var settings = $("#"+id).dataTable().fnSettings();
	settings['queryParams'] = queryParams;	
}
/**
 * 添加事件列表
 */
function addEvent(_modals){
	// 默认值部分
	var _modalId = _modals.modalId?_modals.modalId:'ajax-modal'; 
	var _$modal = $('#'+_modalId);
	var _$dataTable = $('#'+_modals.dataTableId);
	var _event = _modals.event?_modals.event:"click";
	var _refresh = _modals.refresh == false?_modals.refresh:true;
	var _check = _modals.check == undefined ?false:_modals.check;
	// 分歧
	if(_modals.operation == "defined"){  //自定义
		addDefinedEvent(_event,_modals.id,_modals.callBack);
	}else if(_modals.operation == "createwindow"){ //创建窗口
		addCreateWindowEvent(_$modal,_event,_modals.id,_modals.url,_modals.callBefore);
	}else if(_modals.operation == "windowsave"){ //保存按钮
		if(_check){
			addWindowSaveEvent2(_$modal,_event,_modals.id,_modals.url,_modals.formId,_modals.callBack,_refresh,_$dataTable);			
		}else{
			addWindowSaveEvent(_$modal,_event,_modals.id,_modals.url,_modals.formId,_modals.callBack,_refresh,_$dataTable);
		}
	}
}

/**
 * 添加自定义事件
 */
function addDefinedEvent(event,id,callBack){
	$('#'+id).on(event, function(){
		callBack();
	});
}
/**
 * 添加弹出窗口事件
 */
function addCreateWindowEvent($modal,event,id,url,callBefore){ 
	$('#'+id).unbind(event).on(event, function(){
		if(callBefore != undefined){
			if(callBefore() == false){
				return;				
			}
		}
		var pageContent = $('.page-content');
		App.blockUI(pageContent, false);
		 //添加
		 $modal.load(url, '', function(){
	     	$modal.modal();
	     	App.unblockUI(pageContent);
	     	Validator.init();
		 });
		     /*$.post(url, '', function(res){
		    	 $modal.html(res);
		    	 $modal.modal();
			 });*/
	});
}
/**
 * 添加弹出窗保存事件
 */
function addWindowSaveEvent($modal,event,id,url,formId,callBack,refresh,$dataTable){
	$modal.on(event, '#'+id, function(){
		var saveArray = $('#'+formId).getFormValue();
		//添加
		$.ajax({
	       type:"POST",
	       url:url+"&rand="+Math.random(),
	       data:saveArray,
	       success:function(data){
	    	   var d = $.parseJSON(data);
			   if (d.success) {
				   $modal.modal('hide');
				   if(refresh){
					   refreshList($dataTable);
					   modalTips(d.msg);
				   }
			   }else {
				   modalTips(d.msg);
			   }
			   if(callBack != undefined){
				   callBack(d);
			   }
	        }
	    });
	});
}

function addWindowSaveEvent2($modal,event,id,url,formId,callBack,refresh,$dataTable){
	$modal.on(event, '#'+id, function(){
		$("#"+formId).Validform({
			tiptype:function(msg,o,cssctl){
			    //msg：提示信息;
			    //o:{obj:*,type:*,curform:*},
			    //obj指向的是当前验证的表单元素（或表单对象，验证全部验证通过，提交表单时o.obj为该表单对象），
			    //type指示提示的状态，值为1、2、3、4， 1：正在检测/提交数据，2：通过验证，3：验证失败，4：提示ignore状态, 
			    //curform为当前form对象;
			    //cssctl:内置的提示信息样式控制函数，该函数需传入两个参数：显示提示信息的对象 和 当前提示的状态（既形参o中的type）;
				//modalTips(msg);
				$(o.obj).nextAll("span[class*='help-inline']").remove();
				if(o.type == 3){
					$(o.obj).closest("div[class*='control-group']").attr("class","control-group error");
					$(o.obj).after('<span class="help-inline">'+msg+'</span>');
				}else if(o.type == 2){
					$(o.obj).closest("div[class*='control-group']").attr("class","control-group success");
					$(o.obj).after('<span class="help-inline ok valid"></span>');
				}
				/* modalTips(msg); */
			},
			btnSubmit:"#"+id,
			ajaxPost:true,
			showAllError:true,
			beforeSubmit:function(curform){
				//在验证成功后，表单提交前执行的函数，curform参数是当前表单对象。
				//这里明确return false的话表单将不会提交;
				//添加
				var saveArray = $('#'+formId).getFormValue();
				$.ajax({
			       type:"POST",
			       url:url+"&rand="+Math.random(),
			       data:saveArray,
			       success:function(data){
			    	   var d = $.parseJSON(data);
					   if (d.success) {
						   $modal.modal('hide');
						   if(refresh){
							   refreshList($dataTable);
						   }
					   }else {
						   modalTips(d.msg);
					   }
					   if(callBack != undefined){
						   callBack(d);
					   }
			        }
			    });
				return false;
			}
		});
		$("#"+id).click();
	});		
}
// 创建窗口
function createwindow(title,url,modalId){
	modalId = modalId?modalId:'ajax-modal'; 
	var $modal = $('#'+modalId);
	$modal.html('');
	var pageContent = $('.page-content');
	App.blockUI(pageContent, false);
	 //添加
	 $modal.load(url, '', function(){
     	$modal.modal();
     	App.unblockUI(pageContent);
	 });
}


//刷新列表  
function refreshList($dataTable) {
	$dataTable.dataTable().fnClearTable(true);
} 
//刷新列表跳转到第一页
function refreshListToFirst($dataTable) {
	$dataTable.dataTable().fnPageChange('first');  
} 
function update(url,modalId){
	modalId = modalId?modalId:'ajax-modal'; 
	var $modal = $('#'+modalId);
	//$modal.attr("data-width", 760);
	var pageContent = $('.page-content');
	App.blockUI(pageContent, false);
	 //添加
	 $modal.load(url, '', function(){
     	$modal.modal();
     	App.unblockUI(pageContent);
     	Validator.init();
	 });
}
function view(url,modalId){
	modalId = modalId?modalId:'ajax-modal'; 
	var $modal = $('#'+modalId);
	//$modal.attr("data-width", 760);
	var pageContent = $('.page-content');
	App.blockUI(pageContent, false);
	 //添加
	 $modal.load(url, '', function(){
		$modal.find("input,select").attr("disabled","");
		$modal.find("a[view!='true'],button[class!='btn popclose'][view!='true']").remove();
     	$modal.modal();
     	App.unblockUI(pageContent);
     	Validator.init();
	 });
}

//删除信息
function del(url,data,tableId) {
	var $dataTable = $('#'+tableId);
	$("#ajax-modal").confirmModal({
		body: '你确认删除所选记录?',
		callback: function () {
			$.ajax({
				type : "POST",
				url : url,
				data : data,
				success : function(data) {
					 var d = $.parseJSON(data);
		   			 if (d.success) {
		   				 if(tableId != undefined){
		   					refreshList($dataTable);
		   					modalTips(d.msg);
		   				 }
		   			 }else {
		   				modalTips(d.msg);
		   			 }
				}
			});
		}
	});
}

//状态更新
function stateUpdate(url,context,data,tableId) {
	var $dataTable = $('#'+tableId);
	$("#ajax-modal").confirmModal({
		body: context,
		callback: function () {
			$.ajax({
				type : "POST",
				url : url,
				data : data,
				success : function(data) {
					 var d = $.parseJSON(data);
		   			 if (d.success) {
		   				 if(tableId != undefined){
		   					refreshList($dataTable);
		   					modalTips(d.msg);
		   				 }
		   			 }else {
		   				modalTips(d.msg);
		   			 }
				}
			});
		}
	});
}

//判断是否具有按钮操作权限
function isCheckedAuth(btnId) {
	var isChecked = false;
	var clickFunctionId = $('#clickFunctionId').val();
	$.ajax({
		type : "POST",
		url : "commonController.do?isCheckedAuth&rand=" + Math.random(),
		data : "functionId=" + clickFunctionId+"&btnId="+btnId,
		async:false,
		success : function(data) {
			var d = $.parseJSON(data);
			if (d.success) {
				isChecked = true;
			} else {
				isChecked = false;
			}
		}
	});
	return isChecked;
}
// 检索
function dataTabelSearch(tableid,formid){
	setQueryParams(tableid,$('#'+formid).getFormValue());
	$("#"+tableid).dataTable().fnPageChange('first');
}

/***
 * js验证form表单元素
 * @param param
 * @returns {Boolean}
 */
function registerForm(param){
	var validator = $('#' + param.id).validate({
		errorElement : 'span', //default input error message container
		errorClass : 'help-inline', // default input error message class
		focusInvalid : false, // do not focus the last invalid input
		ignore : "",
		rules : param.rules,
		messages : param.messages,
	
		invalidHandler : function(event, validator) { //display error alert on form submit              
		},
	
		highlight : function(element) { // hightlight error inputs
			$(element).closest('.help-inline').removeClass('ok'); // display OK icon
			$(element).closest('.control-group').removeClass('success').addClass(
					'error'); // set error class to the control group
		},
	
		unhighlight : function(element) { // revert the change dony by hightlight
			$(element).closest('.control-group').removeClass('error'); // set error class to the control group
		},
	
		success : function(label) {
			label.addClass('valid').addClass('help-inline ok') // mark the current input as valid and display OK icon
			.closest('.control-group').removeClass('error').addClass('success'); // set success class to the control group
		},
	
        errorPlacement: function (error, element) {
            error.appendTo(element.closest('.controls'));
        }
	});
	if(validator.form()){ 
		return true;
	}
	return false;
}
function getSysAreaSelectList(areaCode, defaultVal){
	var areaString = "";
	$.ajax({
		type : "POST",
		async : false,
		url : "commonController.do?getSysAreaSelectList",
		data : {"areaCode" : areaCode, "defaultVal" : defaultVal},
		success : function(data) {
			var d = $.parseJSON(data);
			if (d.success) {
				areaString = d.attributes.sysAreaSelect;
			}
		}
	});
	
	return areaString;
}
function exportWordByCustom(customService, exportFileName, formId, templete){
	exportWord('', exportFileName, formId, customService, templete);
}
function exportWord(sqlId, exportFileName, formId, customService, templete){
	var exportPath = "systemController.do?exportWord&sqlId=" + sqlId + "&exportFileName=" + exportFileName + "&customService=" + customService 
	
	// 导出sqlId
	if(isEmpty(sqlId)){
		sqlId = "";
	}
	// 取得导出文件名
	if(isEmpty(exportFileName)){
		exportFileName = "";
	}
	
	// 取得导出文件名
	if(isEmpty(customService)){
		customService = "";
	}
	
	// 取得模板
	if(isNotEmpty(templete)){
		exportPath += "&templete=" + templete;
	}
	
	var dframe = document.createElement("iframe");
	dframe.name = "exportWordFrame";
	dframe.style.display = "none";
	// 设置frame的编码格式，否则中文的文件名会乱码
	var charset="<html><head><meta charset=\"utf-8\"><title>exportWord<\/title></head><body><\/body><\/html>";
	document.body.appendChild(dframe);
	dframe.contentWindow.document.write(charset); 
	
	var temp = document.createElement("form");
	temp.action = exportPath;
	temp.method = "post";
	temp.target = "exportWordFrame";
	temp.style.display = "none";
	
	if(isNotEmpty(formId)){
		var expform = document.getElementById(formId);
		var expFormJSON = $(expform).getFormValue();
		for ( var i in expFormJSON) {
			var tempInput = document.createElement("input");
			$(tempInput).attr("type", "hidden");
			$(tempInput).attr("name", i);
			$(tempInput).val(expFormJSON[i]);
			temp.appendChild(tempInput);
		}
	}

	dframe.appendChild(temp);
	temp.submit();
	return false;
}
function exportExcelByCustom(customService, exportFileName, formId){
	exportExcel('', exportFileName, formId, customService);
}
function exportExcel(sqlId, exportFileName, formId, customService){
	// 导出sqlId
	if(isEmpty(sqlId)){
		sqlId = "";
	}
	// 取得导出文件名
	if(isEmpty(exportFileName)){
		exportFileName = "";
	}
	
	// 取得导出文件名
	if(isEmpty(customService)){
		customService = "";
	}
	
	var exportPath = "systemController.do?exportExcel&sqlId=" + sqlId  + "&customService=" + customService;
	
	var dframe = document.createElement("iframe");
	dframe.name = "exportExcelFrame";
	dframe.style.display = "none";
	// 设置frame的编码格式，否则中文的文件名会乱码
	var charset="<html><head><meta charset=\"utf-8\"><title>exportExcel<\/title></head><body><\/body><\/html>";
	document.body.appendChild(dframe);
	dframe.contentWindow.document.write(charset); 
	
	var temp = document.createElement("form");
	temp.action = exportPath;
	temp.method = "post";
	temp.target = "exportExcelFrame";
	temp.style.display = "none";
	
	if(isNotEmpty(formId)){
		var expform = document.getElementById(formId);
		var expFormJSON = $(expform).getFormValue();
		for ( var i in expFormJSON) {
			var tempInput = document.createElement("input");
			$(tempInput).attr("type", "hidden");
			$(tempInput).attr("name", i);
			$(tempInput).val(expFormJSON[i]);
			temp.appendChild(tempInput);
		}
	}
	
	var tempInput = document.createElement("input");
	$(tempInput).attr("type", "hidden");
	$(tempInput).attr("name", "exportFileName");
	$(tempInput).val(exportFileName);
	temp.appendChild(tempInput);

	dframe.appendChild(temp);
	temp.submit();
	return false;
}
// 校验form
function checkForm(param){
	$("#"+param.formId).Validform({
		tiptype:function(msg,o,cssctl){
		    //msg：提示信息;
		    //o:{obj:*,type:*,curform:*},
		    //obj指向的是当前验证的表单元素（或表单对象，验证全部验证通过，提交表单时o.obj为该表单对象），
		    //type指示提示的状态，值为1、2、3、4， 1：正在检测/提交数据，2：通过验证，3：验证失败，4：提示ignore状态, 
		    //curform为当前form对象;
		    //cssctl:内置的提示信息样式控制函数，该函数需传入两个参数：显示提示信息的对象 和 当前提示的状态（既形参o中的type）;
			//modalTips(msg);
			$(o.obj).nextAll("span[class*='help-inline']").remove();
			if(o.type == 3){
				$(o.obj).closest("div[class*='control-group']").attr("class","control-group error");
				$(o.obj).after('<span class="help-inline">'+msg+'</span>');
			}else if(o.type == 2){
				$(o.obj).closest("div[class*='control-group']").attr("class","control-group success");
				$(o.obj).after('<span class="help-inline ok valid"></span>');
			}
		},
		btnSubmit:"#"+param.btnId,
		ajaxPost:true,
		showAllError:true,
		beforeSubmit:function(curform){
			//在验证成功后，表单提交前执行的函数，curform参数是当前表单对象。
			//这里明确return false的话表单将不会提交;
			//添加
			param.success();
			return false;
		}
	});
}
function getMaxZIndex(){
	// 返回最大的z-index
	return $("body").children("div[class='modal-scrollable']:last").css("z-index") || 10000;
}
//模态贴士
function modalTips(msg){
	// 弹出高于弹出层的tips
	var maxZIndex = getMaxZIndex();
	$.dialog.setting.zIndex = parseInt(maxZIndex + 1) ;
	$.dialog.tips(msg);
}
// 模态确认窗口
function modalConfirm(msg, options){
	var templete = '<div id="modalConfim" class="modal hide fade" tabindex="-1" data-width="360">'
		+	'<div class="row-fluid">'
		+		'<div class="span12">'
		+			'<div class="portlet box popupBox_usual">'
		+				'<div class="portlet-title">'
		+					'<div class="caption">'
		+						'<span class="hidden-480">#TITLE#</span>'
		+					'</div>'
		+				'<div class="tools">'
		+						'<a data-dismiss="modal"  class="closed"></a>'
		+					'</div>'
		+				'</div>'
		+				'<div class="portlet-body">'
		+					'<div class="portlet-body-div" style="font-size:15px;padding:5px 15px 0px 15px;">'
		+						'#BODY#'
		+					'</div>'
		+					'<div class="modal-footer">'
		+						'#FOOT#'
		+					'</div>'
		+				'</div>'
		+			'</div>'
		+		'</div>'
		+	'</div>'
		+'</div>';

	var defaults = {
		title : '请确认操作',
		tsbody : '',
		bodyIsText : true,
		hasTitleClose : true,
		hasFootClose : true,
		hasFootConfim : true,
		footClose : '<button type="button" data-dismiss="modal" class="btn popclose">关闭</button>',
		footConfim : '<button type="button" class="btn popenter popConfim">确定</button>',
		customButton: '',
		callback : null
	};
	var html = templete;
	var options = $.extend(defaults, options);
	html = html.replace('#TITLE#', options.heading || options.title);
	html = html.replace('#BODY#',  msg || options.body || options.tsbody);
	html = html.replace("#FOOT#", options.footClose + options.footConfim + options.customButton);
	
	$($('body')[0]).append(html);
	
	if(!options.bodyIsText){
		$("#modalConfim").find('div[class="portlet-body-div"]').attr('style', '');
	}
	
	if(!options.hasTitleClose){
		$("#modalConfim").find('div[class="tools"]').remove();
	}
	if(!options.hasFootClose){
		$("#modalConfim").find('button[class="btn popclose"]').remove();
	}
	if(!options.hasFootConfim){
		$("#modalConfim").find('button[class="btn popenter popConfim"]').remove();
	}
	
	$("#modalConfim").modal({'isAlert':true});
	
	$($("#modalConfim").find('button[class="btn popenter popConfim"]')).off('click').on('click', function(){
		var result = true;
		if(options.callback != null && typeof(options.callback) == "function"){
			result = options.callback() ;
		} else {
			result = eval(options.callback)();
		}
		
		if(false != result){
			$("#modalConfim").modal('hide');
		} else {
			return result;
		}
	});
}
//模态对话框
function modalAlert(msg, options){
	var templete = '<div id="modalAlert" class="modal hide fade" tabindex="-1" data-width="360">'
					+	'<div class="row-fluid">'
					+		'<div class="span12">'
					+			'<div class="portlet box popupBox_usual">'
					+				'<div class="portlet-title">'
					+					'<div class="caption">'
					+						'<span class="hidden-480">#TITLE#</span>'
					+					'</div>'
					+					'<div class="tools">'
					+						'<a data-dismiss="modal" class="closed"></a>'
					+					'</div>'
					+				'</div>'
					+				'<div class="portlet-body">'
					+					'<div class="portlet-body-div" style="font-size:15px;padding:5px 15px 0px 15px;height: 30px;vertical-align: middle;">'
					+						'#ICON#'
					+						'#BODY#'
					+					'</div>'
					+					'<div class="modal-footer">'
					+						'#FOOT#'
					+					'</div>'
					+				'</div>'
					+			'</div>'
					+		'</div>'
					+	'</div>'
					+'</div>';
	
	var defaults = {
			title : '警告',
			tsbody : '',
			bodyIsText : true,
			hasTitleClose : true,
			hasWarningIco : true,
			hasFootConfim : true,
			icon : '<label style="display:inline-block;vertical-align: middle;margin-bottom: 0px;margin-right: 15px;"><span class="icon icon-warning-sign" style="font-size:40px;color: #F09719;"></span></label>',
			customIco : '', 
			footConfim : '<button type="button" data-dismiss="modal" class="btn popenter popConfim">#FOOTCONFIMTEXT#</button>',
			footConfimText : '',
			customButton: '',
			callBack:null
		};
	var html = templete;
	var options = $.extend(defaults, options);
	
	html = html.replace('#TITLE#', options.heading || options.title);
	html = html.replace('#ICON#', options.icon || options.customIco);
	html = html.replace('#BODY#', msg || options.body || options.tsbody);
	html = html.replace("#FOOT#", options.footConfim + options.customButton);
	html = html.replace("#FOOTCONFIMTEXT#", options.footConfimText || '确定');
	
	$($('body')[0]).append(html);
	
	if(options.width){
		$("#modalAlert").attr("data-width", options.width);
	}
	
	if(!options.bodyIsText){
		$("#modalAlert").find('div[class="portlet-body-div"]').css({"height":''});
	}
	
	if(!options.hasWarningIco){
		$("#modalAlert").find('div[class="portlet-body-div"] span.icon-warning-sign').remove();
	}
	
	if(!options.hasTitleClose){
		$("#modalAlert").find('div[class="tools"]').remove();
	}
	if(!options.hasFootConfim){
		$("#modalAlert").find('button[class="btn popenter popConfim"]').remove();
	}
	
	// 如果设置了callback函数，则执行该函数，可以是字符串或者是function对象
	if(options.callBack){
		$("#modalAlert button.popConfim").on('click',function(){
			if(typeof(options.callBack) == 'function'){
				options.callBack.call();
			} else {
				eval(options.callBack)();
			}
		});
	}
	
	$("#modalAlert").modal({'isAlert':true});
}

/**
 * 解析返回的list生成table
 * 
 * theadNum 表头行数 默认：1
 * 
 * hasRowIndex 是否有行号 默认：true
 * 
 * dataList 表数据
 */
function getDataToTableHTMLS(theadNum, hasRowIndex, dataList){
	theadNum = theadNum || 1;
	if(false != hasRowIndex){
		hasRowIndex = true;
	}
	var htmls = '';
	// 表头部分
	htmls += '<thead>';
	// theadNum行表头title
	for(var titleRow = 0; titleRow < theadNum; titleRow++){
		htmls += '<tr>';
		if(hasRowIndex && titleRow == 0){
			htmls += '<th class="center hidden-480" rowspan="' + theadNum + '">序号</th>';
		}
		for(var titleCol in dataList[titleRow]){
			htmls += getCellHTMLS('th', dataList[titleRow][titleCol]);
		}
		// 完成一行标签
		htmls += '</tr>';
	}
	// 表头部分结束
	htmls += '</thead>';
	
	// 表体部分
	var rowInde = 1;
	htmls += '<tbody>';
	for(theadNum; theadNum < dataList.length; theadNum++){
		htmls += '<tr class="odd gradeX">';
		if(hasRowIndex){
			htmls += '<td class="center hidden-480">' + rowInde + '</td>';
			rowInde++;
		}
		for(var bodyCol in dataList[theadNum]){
			htmls += getCellHTMLS('td', dataList[theadNum][bodyCol]);
		}
		htmls += '</tr>';
	}
	// 表体部分结束
	htmls += '</tbody>';
	return htmls;
}

/**
 * 解析单元格属性
 * 
 * tagName 标签名 th 或 td
 * cellStr 单元格属性值
 */
function getCellHTMLS(tagName, cellStr){
	if(isEmpty(tagName) || isEmpty(cellStr)){
		return;
	}
	var htmls = '<' + tagName;
	var colTitle = "";
	// 如果cell属性没有title则认为不是mapStr
	if(cellStr.indexOf('title') == -1){
		colTitle = cellStr;
		htmls += ' class="center hidden-480" ';
	} else {
		var titeColMap = Map.strToMap(cellStr);
		
		// 如果是空则设置空串
		if(isEmpty(titeColMap)){
			htmls += '></' + tagName + '>';
			return htmls;
		}
		// 取得title
		colTitle = titeColMap.get("title") || '';
		var cssClass = titeColMap.get("cssClass") || '';
		// 取得class
		var colClass = ' class="center hidden-480 ' + cssClass +  '"';
		// 拼接class
		htmls += colClass;
		// 计算需要合并的单元格
		var mergeFirstRow = titeColMap.get("mergeFirstRow") || 0;
		var mergeLastRow = titeColMap.get("mergeLastRow") || 0;
		var mergeFirstCol = titeColMap.get("mergeFirstCol") || 0;
		var mergeLastCol = titeColMap.get("mergeLastCol") || 0;
		var rowSpan = (mergeLastRow - mergeFirstRow) + 1;
		var colSpan = (mergeLastCol - mergeFirstCol) + 1;
		if(rowSpan > 1){
			htmls += ' rowspan="' + rowSpan + '"';
		}
		if(colSpan > 1){
			htmls += ' colspan="' + colSpan + '"';
		}
	}
	
	htmls += '>' + colTitle + '</' + tagName + '>';
	return htmls;
}
function getTableTitleColumnsNumArray(tableId){
	var array = new Array();
	for(var i = 0; i < $("#" + tableId).find("thead tr:last th").length; i++){
		array.push(i);
	}
	return array;
}
/**
 * 创建一个DOM节点，以JSON格式为参数
 * tagName ： 标签名
 * tagProperty ：标签属性
 * tagEvent ： 标签事件
 * tagContents : 标签内容
 * 
 * 例：
 * {'tagName': 'div','tagProperty':{'name':'divName','id':'divId'},'tagEvent':{'click':functinon OR 'functionName'},'tagContents':{JSON OR 'content'}}
 */
function createDocumentNode(jsonNode){
	if(isEmpty(jsonNode)){
		return null;
	}
	// 取得标签名
	var tagName = jsonNode["tagName"];
	if(isEmpty(tagName)){
		return null;
	}
	
	// 创建一个DOM元素
	var newNode = document.createElement(tagName);
	
	// 取得标签属性
	var tagProperty = jsonNode["tagProperty"];
	if(isNotEmpty(tagProperty)){
		for(var property in tagProperty){
			if(isNotEmpty(tagProperty[property])){
				if('style' == tagProperty[property]){
					$(newNode).css(tagProperty[property]);
				} else {
					$(newNode).attr(property, tagProperty[property]);
				}
			}
		}
	}

	// 取得标签事件
	var tagEvent = jsonNode["tagEvent"];
	if(isNotEmpty(tagEvent)){
		for(var tEvent in tagEvent){
			if(isNotEmpty(tagEvent[tEvent])){
				$(newNode).on(property, function(e){
					if(typeof(tagEvent) != "function"){
						eval(tagEvent)(e);
					} else {
						tagEvent(e);
					}
				});
			}
		}
	}
	
	// 标签内容
	var contents = jsonNode["tagContents"];
	if(isNotEmpty(contents)){
		// 判断内容是否为JSON数组
		if($.isArray(contents)){
			for(var item in contents){
				// 递归调用
				var contentNode = createDocumentNode(contents[item]);
				if(null != contentNode){
					newNode.appendChild(contentNode);
				}
			}
		} else {
			// 判断是JSON
			if(typeof(contents) == "object" && Object.prototype.toString.call(contents).toLowerCase() == "[object object]" && !contents.length){
				newNode.appendChild(createDocumentNode(contents));
			} else {
				// 添加文字说明
				newNode.appendChild(document.createTextNode(contents));
			}
		}
	}
	
	return newNode;
}
/**
 * 创建一个图例层
 */ 
function createLegendPanel(){
	return createDocumentNode({"tagName":'div', "tagProperty":{"style":'background-color:white;border:1px solid #C8C8C8'}});
}
/**
 * 创建一个图例控件
 */
function createLegendControl(color, title, width){
	if(isEmpty(width)){
		width = 115;
	}
	return createDocumentNode({"tagName":'div', "tagProperty":{'style':'width:' + width + 'px;height:15px;margin: 5px;'}, "tagContents":[{"tagName":'div', "tagProperty":{'style':'float:left;height:15px;width: 50px;border:1px solid #C8C8C8;margin-right: 5px;background-color:' + color + ';'}},{"tagName":'div', "tagProperty":{'style':'float:left;line-height: 17px;vertical-align: middle;'}, "tagContents":{"tagName":'span', "tagProperty":{'style':'font-size: 13px;'}, "tagContents":title}}]});
}

//删除信息
function dataTableDel(url,id,tableId) {
	var $dataTable = $('#'+tableId);
	$("#ajax-modal").confirmModal({
		body: '你确认删除所选记录?',
		callback: function () {
			$.ajax({
				type : "POST",
				url : url,
				data : {
					"id":id
				},
				success : function(data) {
					 var d = $.parseJSON(data);
		   			 if (d.success) {
		   				 if(tableId != undefined){
		   					refreshList($dataTable);
		   					modalTips(d.msg);
		   				 }
		   			 }else {
		   				modalTips(d.msg);
		   			 }
				}
			});
		}
	});
}
// 修改信息
function dataTableUpdate(url,id,modalId){
	modalId = modalId?modalId:'ajax-modal'; 
	var $modal = $('#'+modalId);
	var pageContent = $('.page-content');
	App.blockUI(pageContent, false);
	 //添加
	 $modal.load(url+"&id="+id, '', function(){
     	$modal.modal();
     	App.unblockUI(pageContent);
     	Validator.init();
	 });
}
//查看信息
function dataTableView(url,id,modalId){
	modalId = modalId?modalId:'ajax-modal'; 
	var $modal = $('#'+modalId);
	//$modal.attr("data-width", 760);
	var pageContent = $('.page-content');
	App.blockUI(pageContent, false);
	 //添加
	 $modal.load(url+"&id="+id, '', function(){
		$modal.find("input,select,textarea").attr("disabled","disabled");
		$modal.find("a[view!='true'],button[class!='btn popclose'][view!='true']").remove();
     	$modal.modal();
     	App.unblockUI(pageContent);
     	Validator.init();
	 });
}
// 重置地图frame的高
function resetMapFrameSize(frameId_selector, mapMaxDiv_selector, mapRightDiv_selector, width, height){
	if(isEmpty(width)){
		if(isNotEmpty(mapRightDiv_selector) && isNotEmpty(mapMaxDiv_selector)){
			// -2=去掉边框-10=2个margin：5的距离
			width = $(mapMaxDiv_selector).width() - $(mapRightDiv_selector).width() - 2 - 10;
			width += "px";
		} else {
			width = "800px"
		}
	}
	if(isEmpty(height)){
		if(isNotEmpty(mapMaxDiv_selector)){
			// -2=去掉边框 -10=margin：5的距离
			height = ($(mapMaxDiv_selector).height() - $(mapMaxDiv_selector).prop("scrollHeight") - 2 - 15);
			if(height < 600){
				height = 600;
			}
			if(height > 1000){
				height = 1000;
			}
			height += "px";
		} else {
			height = "600px";
		}
	}
	if(isNotEmpty(frameId_selector)){
		$(frameId_selector).css({"width":width,"height":height});
	}
	
//	$("#" + frameId).height(height);
}
function showSampleImage(samplePath){
	modalAlert("",{
		 title : '样品图片'
		,tsbody : '<img src="' + samplePath + '"/>'
		,bodyIsText : false
		,hasWarningIco : false
		,hasFootConfim : false
	});
}

//find方法长度
function find_input_length(id,type,value){
	var count = 0;
	$("#"+id).find(type).each(function(index,data){
		//alert($(this).val()+":"+value);
		var text = $(this).val();
		if(text == value){
			count = count + 1;
		}
	});
	return count;
}

// 设置项目完成，验证任务完成数量
function checkTaskStatistical(data, type) {
	var projectStatisticalInfo = "";
	var paramUrl = "monitoringProjectController.do?projectStatistical&id="+data;
	if (type == "2") {
		paramUrl = "monitoringProjectController.do?projectStatistical&projectCode="+data;
	}
	$.ajax({
		async: false,
		type : "POST",
		url : paramUrl,
		data : "",
		success : function(data) {
			 var d = $.parseJSON(data);
			 projectStatisticalInfo = d.attributes.projectStatisticalInfo;
		}
	});
	if (parseInt(projectStatisticalInfo.cyCount) < parseInt(projectStatisticalInfo.allCount)) {
		//return "1";// 抽样完成数量没达标
		return "{'result':'1','allCount':'"+projectStatisticalInfo.allCount+"','cyCount':'"+projectStatisticalInfo.cyCount+"','jcCount':'"+projectStatisticalInfo.jcCount+"'}";
	}
	if (parseInt(projectStatisticalInfo.jcCount) != parseInt(projectStatisticalInfo.allCount)) {
		//return "2";// 检测完成数量没达标
		return "{'result':'2','allCount':'"+projectStatisticalInfo.allCount+"','cyCount':'"+projectStatisticalInfo.cyCount+"','jcCount':'"+projectStatisticalInfo.jcCount+"'}";
	}
	return "{'result':'0'}";
}

// ajax返回慢时，增加友好提示
function showTips( tips, height, time ){
	  var windowWidth  = document.documentElement.clientWidth;
	  var tipsDiv = '<div class="tipsClass">' + tips + '</div>';

	  $('body').append( tipsDiv );
	  $('div.tipsClass').css({
	      'top'       : height + 'px',
	      'left'      : ( windowWidth / 2 ) - ( tips.length * 13 / 2 ) + 'px',
	      'position'  : 'absolute',
	      'padding'   : '3px 5px',
	      'background': '#8FBC8F',
	      'font-size' : 12 + 'px',
	      'margin'    : '0 auto',
	      'text-align': 'center',
	      'width'     : 'auto',
	      'color'     : '#fff',
	      'opacity'   : '0.8',
	      'z-index'	  : '19000'
	  }).show();
	  //setTimeout( function(){$( 'div.tipsClass' ).fadeOut();}, ( time * 1000 ) );
}
