/**
 * AjaxMode 属性标签ajax提交数据工具类
 * <p>
 * 使用该类可以通过自动注册的方式实现ajax的提交，可以自定义参数或传递表单的元素值
 * <p>
 * 引用说明:该类引用时，需要放在assets/js/formUtil.js之后，assets/js/sysUtil.js之前
 * <script type=""text/javascript"" src=""assets/js/formUtil.js"" ></script>
 * <script type=""text/javascript"" src=""assets/js/ajaxMode.js"" ></script>
 * <script type=""text/javascript"" src=""assets/js/sysUtil.js""></script>
 * 
 * action-mode String 必须 默认:无
 * 当标签中属性含有action-mode="ajax" 且action-event不为空时，则会自动注册ajax调用
 * 
 * action-operation String 非必须 默认：pop
 * action-operation指定了两种action方式：pop和popsave。Pop方式可以弹出一个模态的窗口，不论action-url/href是否有值。Popsave方式会以ajax方式提交数据，并关闭弹出层，如
 * 果action-fresh指定了相应的datatable或ztree等，就会在save后调用刷新方法。
 * 
 * 属性已失效 action-event String 必须 默认:click
 * 支持Jquery语法的绑定事件名称，例如：[click：单击事件][dblclick:双击事件][change:改变事件][focus:焦点事件][select:选择事件]……
 * 属性已失效
 * 
 * action-url/href String 非必须 默认:无
 * ajax提交的action路径，可以使用action-url 也可以直接使用href
 * 
 * action-form  String 非必须 默认:无
 * 提交时需要提交的表单选择器，例如：action-form="#seachForm"或者action-form=".seachForm"或 action-form=":seachForm"(其效果等价于action-form="#seachForm"，不写选择器前缀的话，默认按#查找)
 * 提交的数据也可以通过action-url/href的参数进行传递，例如：action-url/href="userController.do?addorupdate&aa=11"。如果同时写了参数的话，会将两个参数组合并成一个，且相同名字的参数会被替换掉。
 * 如果此属性设置，且对应form的validate="true"时，则会对该form的控件进行check，check方法使用datatype方式，如果不设置，默认不check。
 * ※ Form的check优先于action-before方法。
 * 
 * action-pop String 非必须 默认:#ajax-modal
 * 如果是弹出框，则需要指定弹出框的选择器，例如action-pop="#ajax-modal"或者action-pop=".ajax-modal"或者action-pop="ajax-modal"(其效果等价于action-form="#seachForm"，不写选择器前缀的话，默认按#查找)
 * Pop方式时，会对#ajax-modal的div进行弹出处理，Popsave时会对#ajax-modal进行关闭处理
 * 
 * action-fresh String 非必须 默认：无
 * 自动刷新action-fresh值指定的datatable，也可以传入以逗号分隔的数组，例如:action-fresh="datatable1"或action-fresh="datatable1,datatable2"
 * 
 * action-before String 非必须 默认:无
 * 执行action-before的js函数，并且会将action-url/href，action-pop,async,action-async，action-fresh，action-after以及action-form中的表单值，按照JSON的格式在最后一
 * 个参数传入，被调用的函数可以返回JSON或者boolean值，当action-before对应的js函数返回false时不再继续执行方法，如果返回的是JSON，则会把返回的JSON中params的数据作为最终
 * 传入action中的request的数据。data.thisElem：事件发生的对象，相当于this;data.arguments_i：会按照参数顺序生成
 * 例：action-before="aa('vb2','11')" function aa(data){//this对象：data.thisElem //vb2 = data.arguments_0 //11 = data.arguments_1 }
 * 
 * action-after String 非必须 默认:无
 * 相当于callback函数，在ajax.success时调用action-after对应的js函数，执行action-url/href后的返回的数据，会传入action-after的函数中。例：action-after="bb()"->bb(data)
 * 
 * action-async String 非必须 默认:false
 * ajax的异步模式，action-async="true"为异步模式，action-async="false"为同步模式,不写次属性的话，默认为同步模式
 * 
 * ===============================================================================================================================================================
 * 一组弹出层与关闭按钮的实例：
 * a.jsp <a href="#" class="btn btngroup_seach" action-mode="ajax" action-url="monitoringPlanController.do?addorupdate"><i class="icon-search"></i>搜索</a>
 *       <a href="#" class="btn btngroup_seach" action-mode="ajax" action-url="pollCategoryController.do?addorupdate&versionId=4028e409420252930142028726680003"><i class="icon-search"></i>搜索</a>
 * 
 * b.jsp <button action-mode="ajax" action-url="monitoringPlanController.do?save" action-form="form1" action-operation="popsave" action-fresh="monitoring_plan_program_tb1" type="button" class="btn popenter">保存</button>
 *       <button action-mode="ajax" action-url="pollCategoryController.do?save" type="button" action-form="#saveForm" class="btn popenter" action-operation="popsave" action-fresh="#categoryTree">保存</button>
 * 
 * 
 * @version 1.0.2
 * @author xudl
 */
var AjaxMode = function () {
	var handleInit = function() {
		var actEvent = $(this).attr("action-event") || "click";
		$(document).off(actEvent + '.ajaxMode').on(actEvent + '.ajaxMode.data-api', '[action-mode="ajax"]', function ( e ) {
			var $this = $(this),
			href = $this.attr('href'),
			actOperation = $(this).attr("action-operation") || "pop";
			$actionform = handleGetSelector($this.attr('action-form')),
			$before = $this.attr('action-before');
			
			// 取消事件的默认动作
			if (e.preventDefault) {
				// IE以外
				e.preventDefault();
			} else {
				//IE
				e.returnValue = false;
			}
			
			var validFlg = true;
			// 如果action-form有值 则进行判断是否进行check
			if($actionform != null){
				var hasValidate = $($actionform).attr("validate") || false;
				if(hasValidate){
					var validator = $($actionform).data("validator");
					if(validator != null){
						// 如果已经注册了validateForm插件，则进行check
						validFlg = validator.checkAll(false, false);
						var errorLength = $($actionform).find('div[class="control-group error"]').length;
						if(!validFlg || errorLength > 0){
							return;
						}
					}
				}
			}
			
			// 取得form表单中的参数
			var params = $($actionform).getFormValue();
			
			var jsonParam = {};
			jsonParam["targetUrl"] = $this.attr('action-url') || (href && href.replace(/.*(?=#[^\s]+$)/, ''));
			jsonParam["async"] = $this.attr('action-async') || false;
			jsonParam["refresh"] = $this.attr('action-fresh');
			jsonParam["after"] = $this.attr('action-after');
			jsonParam["onload"] = $this.attr('action-load');
			jsonParam["params"] = params;
			// 事件发生的组件
			jsonParam["thisElem"] = $this; 
			// 判断需要关闭的窗口的id
			var $popElem = $this.attr('action-pop');
			var closetPopId = $this.closest("div .modal.hide.fade").attr("id");
			// 如果action-pop不为空则设置成action-pop
			if(handleIsNotNull($popElem)){
				jsonParam["popElem"] = handleGetSelector($popElem);
			} else {
				// 距离按钮最近的[div .modal.hide.fade]的id不为空，则设置成其id；如果为空设置为默认值
				if(handleIsNotNull(closetPopId)){
					jsonParam["popElem"] = handleGetSelector(closetPopId);
				} else {
					// 
					jsonParam["popElem"] = "#ajax-modal";
				}
			}
			
		
			// 执行before函数
			if($before != null){
				// 执行before函数取得返回结果
				var beforeReturn = handleCallFunction($before, jsonParam);
				// 如果返回false，正不继续执行
				if(false == beforeReturn){
					return;
				}
				// 判断是否是JSON
				if(typeof(beforeReturn) == "object" && Object.prototype.toString.call(beforeReturn).toLowerCase() == "[object object]" && !beforeReturn.length){
					jsonParam = beforeReturn;
				}
			}
	
			// 判断打开方式时Pop、Popsave还是普通的action
			if("pop" == actOperation.toLowerCase()){
				handleCreateWindow(e, jsonParam);
			} else if("popsave" == actOperation.toLowerCase()){
				handleSaveWindow(e, jsonParam);
			} else if("nomal" == actOperation.toLowerCase()){
				handleNomalAction(jsonParam)
			}
		});
	};
	
	var handleNomalAction = function(jsonParam){
		$.ajax({
			async: jsonParam.async || false,
			type : 'POST',
			url : jsonParam.targetUrl + "&rand="+Math.random(),
			data : jsonParam.params || {},
			success : function(data) {
				var dataJson = eval('(' + data + ')');
				if(dataJson.success){
					// 拼接
					$.extend(jsonParam, dataJson);
				}
				
				// 执行after(callback)函数
				handleCallFunction(jsonParam.after, jsonParam);
			}
		});
	};
	
	var handleCreateWindow = function(e, jsonParam){
		var pageContent = $('.page-content');
		App.blockUI(pageContent, false);
		if(!handleIsNotNull(jsonParam.targetUrl) || "#" == jsonParam.targetUrl){
			$(jsonParam.popElem).modal();
			App.unblockUI(pageContent);
			// 执行after(callback)函数
			handleCallFunction(jsonParam.after, jsonParam);
			return;
		}
		$.ajax({
			async: jsonParam.async,
			type : 'POST',
			url : jsonParam.targetUrl + "&rand="+Math.random(),
			data : jsonParam.params,
			success : function(data) {
				App.unblockUI(pageContent);
				if(handleIsNotNull(jsonParam.popElem)){
					$(jsonParam.popElem).html(data);
					handleInit();
					// 样式修正
					App.initUniform();
					$(jsonParam.popElem).modal();
					App.unblockUI(pageContent);
					Validator.init();
					// 执行after(callback)函数
					handleCallFunction(jsonParam.after, jsonParam);
				} else {
					var dataJson = eval('(' + data + ')');
					if(dataJson.success){
						// 弹出tips消息  暂时没有组件
						modalTips(dataJson.msg);
						
						// 执行after(callback)函数
						handleCallFunction(jsonParam.after, jsonParam);
					} else {
						// 弹出alert消息
						modalAlert(dataJson.msg);
					}
					App.unblockUI(pageContent);
				}
			},
			error : function(event,request,settings){
				App.unblockUI(pageContent);
				// 弹出alert消息 
				modalAlert(settings.url + "中返回错误，数据不能被正确加载。");
			}

		});
	};
	
	var handleSaveWindow = function(e, jsonParam){
		if(!handleIsNotNull(jsonParam.targetUrl)){
			// 执行after(callback)函数
			handleCallFunction(jsonParam.after, jsonParam);
			
			if(handleIsNotNull(jsonParam.popElem)){
				$(jsonParam.popElem).modal('hide');
			}
			
			if(handleIsNotNull(jsonParam.refresh)){
				handleRefresh(jsonParam.refresh);
			}
			return;
		}
		
		$.ajax({
			async: jsonParam.async,
			type : 'POST',
			url : jsonParam.targetUrl + "&rand="+Math.random(),
			data : jsonParam.params,
			success : function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
					if(handleIsNotNull(jsonParam.popElem)){
						$(jsonParam.popElem).modal('hide');
					}
					
					if(handleIsNotNull(jsonParam.refresh)){
						handleRefresh(jsonParam.refresh);
					}
					// 弹出tips消息  暂时没有组件
					modalTips("操作成功");
				} else {
					modalAlert(d.msg);
				}
				// 拼接
				$.extend(jsonParam ,d);
				// 执行after(callback)函数
				handleCallFunction(jsonParam.after, jsonParam);
				
			},
			error : function(settings){
				// 弹出alert消息 
				modalAlert(settings.url + "中返回错误，数据不能被正确加载。");
			},
			beforeSend :function(data){
				if(jsonParam.onload != null){
					$("button[class='btn popenter']").css({"background-color":"#c2c2c2"});
					showTips(jsonParam.onload,"400",1000);
				}
			}
		});
	};
	
	var handleCallFunction = function(method, data){
		// 执行method(param)函数
		if(handleIsNotNull(method)){
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
					data["arguments_" + i + ""] = params[i].replace(/(^\')|(\'$)/g,'');
				}
			} else {
				// 没有括号的情况或者参数为空的情况
				method = method.replace("()","");
				methodFullName = method;
			}
			// 执行after函数
			return eval(methodFullName)(data);
		}
	};
	
	var handleIsNotNull = function(obj){
		if(obj == null || obj == "" || obj == "undefined"){
			return false;
		}
		return true;
	};
	
	var handleGetSelector = function(selectorName){
		if(handleIsNotNull(selectorName)){
			var seletorPrefix = selectorName.substring(0,1);
			if("#" != seletorPrefix && "." != seletorPrefix){
				selectorName = "#" + selectorName;
			}
		}
		return selectorName;
	};
	
	var handleRefresh = function(refreshName){
		if(handleIsNotNull(refreshName)){
			var refreshList =  refreshName.split(",");
			for(var i = 0; i < refreshList.length; i++){
				var refElemClass = $(handleGetSelector(refreshList[i])).attr("class");
				if(handleIsNotNull(refElemClass)){
					// 判断是datatable
					if(refElemClass.indexOf("dataTable") >= 0){
						$(handleGetSelector(refreshList[i])).dataTable().fnClearTable(true);
						continue;
					}
//					// 判断是ztree
//					if(refElemClass.indexOf("ztree") >= 0){
//						var treeName = refreshList[i];
//						if(treeName.indexOf("#") == 0){
//							treeName = treeName.substring(1);
//						}
//						$.fn.zTree.getZTreeObj(treeName).reAsyncChildNodes(null, "refresh", true);
//						continue;
//					}
				}
			}
		}
	};
	
	return {
		init : function(){
			handleInit();
		},
		nomalAction : function(jsonParam){
			handleNomalAction(jsonParam);
		},
		callRefresh : function(jsonParam){
			handleRefresh(jsonParam);
		}
	};
}();

