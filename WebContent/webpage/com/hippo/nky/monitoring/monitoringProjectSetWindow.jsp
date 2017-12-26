<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript" src="plug-in/tools/Map.js"></script>
<t:base type="ztree"></t:base>
<style type="text/css">
.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
.ztree li span.button.diy1_ico_open{margin-right:2px;background:transparent url("assets/img/icons/bullet_orange.png") no-repeat 0 0 scroll; vertical-align:top; *vertical-align:middle}
.ztree li span.button.diy1_ico_close{margin-right:2px; background:transparent url("assets/img/icons/bullet_orange.png") no-repeat 0 0 scroll; vertical-align:top; *vertical-align:middle}
.ztree li span.button.diy1_ico_docu{margin-right:2px; background:transparent url("assets/img/icons/bullet_orange.png") no-repeat 0 0 scroll; vertical-align:top; *vertical-align:middle}
.ztree li span.button.diy2_ico_open{margin-right:2px; background:transparent url("assets/img/icons/bullet_green.png") no-repeat 0 0 scroll; vertical-align:top; *vertical-align:middle}
.ztree li span.button.diy2_ico_close{margin-right:2px; background:transparent url("assets/img/icons/bullet_green.png") no-repeat 0 0 scroll; vertical-align:top; *vertical-align:middle}
.ztree li span.button.diy2_ico_docu{margin-right:2px; background:transparent url("assets/img/icons/bullet_green.png") no-repeat 0 0 scroll; vertical-align:top; *vertical-align:middle}


</style>
<!-- 第五步，列表经过变色 -->
<style>
.organizationtable {border-collapse:collapse;border:solid #6AA70B;border-width:0px 0 0 0px;OVERFLOW-Y: auto; OVERFLOW-X:hidden;height:300px;margin-top:15px;}
.organizationtable ul {margin:0 0 0 0; border-bottom: #6AA70B 1px dotted;}
.organizationtable ul li {padding-top:5px;text-indent:1em;list-style:none;border-top:#6AA70B 1px dotted ;/* font-family: "Verdana,宋体"; */font-size: 12px;/* color:#008000; */text-align:left;height:30px; line-height:30px;cursor:pointer;}
.organizationtable ul li a{ color:#333; text-decoration:none;}
.organizationtable ul li.t0 {background-color:#fff;/* background-color:#EFFEDD; */}/* 第一行的背景色 */
.organizationtable ul li.t1{background-color:#fff;}/* 第二行的背景色 */
.organizationtable ul li.t2{background-color:#f5f5f5;}/* 鼠标经过时的背景色 */
.organizationtable ul li.t3{background-color:#FFB951;}/* 单击行的背景色 */

</style>

<!-- <style>
.organizationtable {border-collapse:collapse;border:solid #6AA70B;border-width:0px 0 0 0px;OVERFLOW-Y: auto; OVERFLOW-X:hidden; height:300px;margin-top:10px;}
.organizationtable ul li {padding-top:5px;text-indent:1em;list-style:none;border-bottom:#6AA70B 1px dotted ;font-family: "Verdana,宋体";font-size: 12px;color:#008000;text-align:left;height:25px; line-height:25px; margin-bottom:10px;margin:10px 0 0 0;}
.organizationtable ul li a{ color:#333; text-decoration:none;}

</style> -->
<script type="text/javascript">
	// 第四步 污染物-农产品 树
	var settingTree1 = {	
		view:{ dblClickExpand:false },
		data: { simpleData: { enable: true } }
	};
	var zNodesTree1 = ${zTreeData} ;

	// 第三步 抽样品种树
	var settingTree2 = {	
			view:{ dblClickExpand:false },
			data: { simpleData: { enable: true } },
			callback: { onDblClick: zTree2OnDblClick }
	};
	var zNodesTree2 = ${zTreeData} ;
	function zTree2OnDblClick(event, treeId, treeNode) {
		
		if(find_input_length('add_monitoringBreed_table','input',treeNode.code) <= 0){
			addLogicData(treeNode);
			// 取消只能选择叶子结点
			//if(treeNode.children==null||treeNode.children.length==null||treeNode.children.length == 0){
			//}
		}
	}
	// 时间控件
	$('.date-picker').datepicker({
	    rtl : App.isRTL(),
	    language: "zh",
	    autoClose: true,
	    format: "yyyy-mm-dd",
	    todayBtn: true,
	    clearBtn:true
	});

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
		$("#submit_form").find("input[type='checkbox']").uniform();
	}
	// 解析出行数编号
	function getRowsNum(name){
	  	var s = name.indexOf("[");
	  	var e = name.indexOf("]");
	  	// 行数
	  	return name.substring(s+1,e);
	}
	// 删除重复的数据
	function removeDuplicate(str,separator){
		if(str != "" && str.indexOf(separator) > 0){
	        var s = str.split(separator);
	        var dic = {};
	        for (var i = s.length; i--; ) {
	            dic[s[i]]=s[i];
	        }
	        var r = [];
	        for (var v in dic) {
	        	r.push(dic[v]);
	        }
	        var newStr = r.join();
	        return newStr.replace(/,/gi,separator);
		}else{
			return str;
		}
	}
	/* 基本属性 开始 */
    // 数据校验处理
   	function checkTab1(){
		if($("#starttime").val() == ""){
			modalTips("监测时间不能为空！");
		}else if($("#endtime").val() == ""){
			modalTips("监测时间不能为空！");
		}else if($("select[name='sampleTemplet']").val() == ""){
			modalTips("请选择抽样单模板！");
		}else if($("#tab1").find("input[name*='.monitoringLink']:checked").length <= 0){
			modalTips("请选择检测环节！");
		}else{
			var starttime=new Date($("#starttime").val().replace(/[-\.,]/g,"/")),
			endtime=new Date($("#endtime").val().replace(/[-\.,]/g,"/"));
			if(starttime>=endtime){
				modalTips("监测结束时间必须大于开始时间！");
				return false;
			}
			return true;
		}
	}
   	/* 基本属性 结束 */
   	
	/* 检测地区及数量 开始 */
	$('#addMonitoringAreaCountBtn').bind('click', function(){
 		 var tr =  $("#add_monitoringAreaCount_table_template tr").clone();
	 	 $("#add_monitoringAreaCount_table").append(tr);
	 	 resetTrNum('add_monitoringAreaCount_table');
    });
	$('#delMonitoringAreaCountBtn').bind('click', function(){
      	$("#add_monitoringAreaCount_table").find("input:checked").closest("tr").remove();
        resetTrNum('add_monitoringAreaCount_table'); 
    });
	/* 总数 */
	function monitoringAreaCount_all(){
		var count = 0;
		$("#add_monitoringAreaCount_table tr").each(function(index){
			var cityCount = $(this).find("input[name*='.count']").val();
			if(cityCount == ""){
				cityCount = 0;	
			}else if(! /^\+?[1-9][0-9]*$/.test(cityCount)){
				cityCount = 0;
			}
			count += parseInt(cityCount);
		});
		$("#monitoring_plan_project_span1").text(count);
	}
	// 选择抽检市
	function monitoringAreaCount_selectCity(obj){
		var selectCityCode = $(obj).val();
		var selectCityRows = getRowsNum($(obj).attr("name"));	
		if (selectCityCode == "") {
			return true;
		}
		if($("#add_monitoringAreaCount_table").find("select[name!='monitoringAreaCountList["+selectCityRows+"].citycode']").find("option[value='"+selectCityCode+"']:selected").length <= 0 ){
			// 抽检市
			// 抽检县,市区(编码)
			$("#add_monitoringAreaCount_table").find("input[name='monitoringAreaCountList["+selectCityRows+"].districtcode']").val("");
			$("#add_monitoringAreaCount_table").find("input[name='monitoringAreaCountList["+selectCityRows+"].districtcodeName']").val("");
			// 抽检县,市区(名称)
			$("#add_monitoringAreaCount_table").find("input[name='monitoringAreaCountList["+selectCityRows+"].districtcode']").parent().find("span").text("");
			$(obj).attr("back-up",selectCityCode);
		}else{
			modalTips("抽样市已选择！");
			var backup = $(obj).attr("back-up");
			if(backup != undefined && backup != "" && $(obj).find("option[value='"+backup+"']").length > 0){
				$(obj).find("option[value='"+backup+"']").attr("selected","selected");
			}else{
				$(obj).find("option:eq(0)").attr("selected","selected");
				// 抽检市
				// 抽检县,市区(编码)
				$("#add_monitoringAreaCount_table").find("input[name='monitoringAreaCountList["+selectCityRows+"].districtcode']").val("");
				$("#add_monitoringAreaCount_table").find("input[name='monitoringAreaCountList["+selectCityRows+"].districtcodeName']").val("");
				// 抽检县,市区(名称)
				$("#add_monitoringAreaCount_table").find("input[name='monitoringAreaCountList["+selectCityRows+"].districtcode']").parent().find("span").text("");
			}
		}
		
	}
	// 地区及数量设置弹出窗口  
	function projectAreaCountSet(thisObj){
	   	var $this = $(thisObj);
	  	// 行数
	  	var new_name = getRowsNum($this.find("input").attr("name"));
	  	// 抽检市
	   	var citycode = $this.parent().find("select[name='monitoringAreaCountList["+new_name+"].citycode']").val();
	  	// 抽检县,市区
	  	var districtcode = $this.parent().find("input[name='monitoringAreaCountList["+new_name+"].districtcode']").val();
	
	  	if(citycode == "" || citycode == undefined ){
	  		return;
	  	}
	  	
	  	$.ajax({
	  		type : 'POST',
	  		url : "monitoringProjectController.do?projectAreaCountSet",
	  		data : {"code":citycode,"areaCount":districtcode,"returnRows":new_name},
	  		success : function(data) {
	  			$("#monitoring_plan_project_window2").html(data);
	  			$("#monitoring_plan_project_window2").modal();
	  		}
	  	}); 
	}
	function checkTab2(){
		var result = true;
		$("#add_monitoringAreaCount_table").find("tr").each(function(index){
			var $this = $(this);
		  	// 抽检市
		   	var citycode = $this.find("select[name='monitoringAreaCountList["+index+"].citycode']").val();
		  	// 抽检县,市区
		  	var districtcode = $this.find("input[name='monitoringAreaCountList["+index+"].districtcode']").val();
		  	var cityCount = $this.find("input[name='monitoringAreaCountList["+index+"].count']").val();
		  	var districtCount = 0;
		  	var districtArr = districtcode.split("#EM#");
		  	for(var i = 0;i < districtArr.length;i++){
		  		if(districtArr[i] != ""){
		  			var tempCount = districtArr[i].split("#KV#")[1];
		  			districtCount = districtCount + parseInt(tempCount);
		  		}
		  	}
		  	if(citycode == ""){
		  		result = false;
				modalTips("请选择抽检市！");
				return false;
		  	}
/* 		  	else if(districtcode == ""){
		  		result = false;
				modalTips("请选择抽检县（市、区）！");
				return false;
		  	} */
		  	else if(cityCount == ""){
		  		result = false;
		  		modalTips("请填写抽样数量！");
		  		return false;
		  	}else if(cityCount < 1){
		  		result = false;
		  		modalTips("抽样数量必须大于0！");
		  		return false;
		  	}else if(! /^\+?[1-9][0-9]*$/.test(cityCount)){
		  		result = false;
		  		modalTips("数量【"+cityCount+"】格式错误，只能输入整数！");
		  		return false;
			}else if(cityCount < districtCount){
		  		result = false;
		  		modalTips("抽样数量不能小于抽检县（市、区）数量总和！");
		  		return false;
		  	}
		});
		return result;
	}
    /* 检测地区及数量 结束 */
    
    /* 抽样品种数据 开始 */
	function addLogicData(treeNode){
		var tr =  $("#add_monitoringBreed_table_template tr").clone();
		tr.find("input:eq(0)").val(treeNode.code);
		//tr.find("input:eq(0)").attr("id","agrGG"+treeNode.code);
		
		tr.find("span:eq(0)").html(treeNode.cname);
	 	$("#add_monitoringBreed_table").append(tr);
	 	resetTrNum('add_monitoringBreed_table');
	}
    function delLogicData(thisObj){
    	$(thisObj).closest("tr").remove();
        resetTrNum('add_monitoringBreed_table'); 
	}
   	function checkTab3(){
   		// 不校验
   		//return true;
   		if($("#add_monitoringBreed_table").find("tr").length < 1){
   			modalTips("请添加抽样品种！");
	  		return false;
   		}else{
			return true; 
   		}
	}
   	//抽样品种树结构检索功能
   	var count=0; 
   	var lastName; 
   	function searchTree(){ 
	   	if(count==0){
	   		lastName=$("#agrCategorySearch").val(); 
	   	} 
	   	if(lastName!=$("#agrCategorySearch").val()){
	   		count=0;
	   		lastName=$("#agrCategorySearch").val(); 
	   	} 
   		var treeObj = $.fn.zTree.getZTreeObj("monitoring_plan_project_tree2"); 
   		treeObj.cancelSelectedNode();
   		//通过名称模糊搜索，也可通过Id查找 
   		var nodes =   treeObj.transformToArray(treeObj.getNodesByParamFuzzy("name", lastName, null)); 
   	    for(var i = count; i < nodes.length; i++) {
	   		count++; 
		   	if(count>=nodes.length){ 
		   		count=0; 
		   	} 
	   		if(nodes[i].name.indexOf(lastName)!=-1){ 
	   			treeObj.selectNode(nodes[i]); 
	   			return; 
   			}
   		}
   	}
 	/* 抽样品种数据 结束 */
 	
    /* 检测污染物模板数据 开始 */
	$('#addMonitoringDectionTempletBtn').bind('click', function(){   
 		 var tr =  $("#add_monitoringDectionTemplet_table_template tr").clone();
	 	 $("#add_monitoringDectionTemplet_table").append(tr);
	 	 resetTrNum('add_monitoringDectionTemplet_table');
    });  
	$('#delMonitoringDectionTempletBtn').bind('click', function(){   
      	$("#add_monitoringDectionTemplet_table").find("input:checked").closest("tr").remove();
        resetTrNum('add_monitoringDectionTemplet_table'); 
    });
	// 农产品选择的行
	var ypzlSelectedRows = -1;
	// 弹出样品种类窗口事前处理
	function agrWindow(jsonParam){
		var $this = jsonParam.thisElem;
	  	// 行数
	  	ypzlSelectedRows = getRowsNum($this.find("input[name*='.agrCode']").attr("name"));  
	  	return true;	
	}
	// 弹出污染物选择窗口事前处理
	function pollWindow(jsonParam){
		var $this = jsonParam.thisElem;
	  	// 行数
	  	jsonParam.params['returnRows'] = getRowsNum($this.find("input[name*='.pollCas']").attr("name"));
	  	var pollArr = $this.find("input[name*='.pollCas']").val();
	  	if(pollArr != ""){
	  		pollArr += "#EM#";
	  	}
	  	jsonParam.params['pollArr'] = pollArr;
	  	jsonParam.params['projectCode'] = "${monitoringProjectPage.projectCode }";
	  	jsonParam.params['projectId'] = "${monitoringProjectPage.id }";
		jsonParam.params['industryCode'] = "${monitoringProjectPage.industryCode }";
	  	
	  	return jsonParam;
	}
	// 选择农产品
	function ypzlselected(){
		var zTree = $.fn.zTree.getZTreeObj("monitoring_plan_project_tree1");
		var treeNode = zTree.getSelectedNodes();
		if (treeNode.length == 0) {
			modalTips("请选择样品种类！");
			return false;
		}
		if( ypzlSelectedRows >= 0 && $("#add_monitoringDectionTemplet_table").find("input[name!='monitoringDectionTempletList["+ypzlSelectedRows+"].agrCode'][value='"+treeNode[0].code+"']").length <= 0 ){
			$("#add_monitoringDectionTemplet_table").find("input[name='monitoringDectionTempletList["+ypzlSelectedRows+"].agrCode']").val(treeNode[0].code);
			$("#add_monitoringDectionTemplet_table").find("input[name='monitoringDectionTempletList["+ypzlSelectedRows+"].agrCode']").attr("id","agrTT"+treeNode[0].code);
			$("#add_monitoringDectionTemplet_table").find("input[name='monitoringDectionTempletList["+ypzlSelectedRows+"].agrCode']").next().text(treeNode[0].cname);
			ypzlSelectedRows = -1;
			$("#monitoringDectionTemplet_agr_selected").modal('hide');
		}else{
			modalTips("样品种类已选择！");
			return false;
		}
	}

   	// 筛选品种树,没用到的隐藏
   	function init_zNodesTree1(){
   		var treeObj = $.fn.zTree.getZTreeObj("monitoring_plan_project_tree1"); 
   		var all_nodes = treeObj.transformToArray(treeObj.getNodes());
   		if($("#add_monitoringBreed_table").find("tr").length > 0){
   	   		treeObj.hideNodes(all_nodes);
   		}else{
   			treeObj.showNodes(all_nodes);
   		}
   		$("#add_monitoringBreed_table").find("tr").each(function(index){
   			$this = $(this);
   			var agrCode = $this.find("input[name='monitoringBreedList["+index+"].agrCode']").val();
   		   	//通过CODE精确搜索
   		   	var nodes =   treeObj.transformToArray(treeObj.getNodesByParam("code", agrCode, null)); 
   		   	if(nodes.length > 0){
   		   		var temp_node = nodes[0];
   		   		treeObj.showNode(temp_node);
   				while(temp_node.parentTId != null){
   					temp_node = temp_node.getParentNode();
   			   		 if(temp_node.isHidden){
   			   			treeObj.showNode(temp_node);
   					}
   		 		}
   		   	}
   		});
   		// 添加提示信息
		var promptStr = "";
		$("#add_monitoringBreed_table").find("tr").each(function(index) {
			$this = $(this);
			promptStr = promptStr + $this.find("span").html() + "、";
		});
		promptStr = promptStr.replace(/、$/gi, "");
		$("#prompt_4").html(promptStr);
   	}
   	
   	function checkTab4(){
   		var result = true;
   		if($("#add_monitoringDectionTemplet_table").find("tr").length < 1){
   			result = false;
	  		modalTips("请添加污染物！");
   		}
   		$("#add_monitoringDectionTemplet_table").find("tr").each(function(index){
   			$this = $(this);
   			var agrCode = $this.find("input[name='monitoringDectionTempletList["+index+"].agrCode']").val();
   			var pollCas = $this.find("input[name='monitoringDectionTempletList["+index+"].pollCas']").val();
   			if(agrCode == ""){
		  		result = false;
		  		modalTips("样品种类不能为空！");
		  		return false;
   			}else if(pollCas == ""){
   				result = false;
		  		modalTips("污染物不能为空！");
		  		return false;
   			}
   		});
   		
   		if(result){
   	   		// 污染物设置样品种类必须是抽样品种或者起父节点(循环抽样品种)
   	   		$("#add_monitoringBreed_table").find("tr").each(function(index){
   	   			$this = $(this);
   	   			var agrCode = $this.find("input[name='monitoringBreedList["+index+"].agrCode']").val();
   	   		   	var treeObj = $.fn.zTree.getZTreeObj("monitoring_plan_project_tree2"); 
   	   		   	//通过CODE精确搜索
   	   		   	var nodes =   treeObj.transformToArray(treeObj.getNodesByParam("code", agrCode, null)); 
   	   		   	if(nodes.length > 0){
   	   		   		result = checkTab4_recursive(nodes[0]);
   	   		   	}
   	   		   	if(!result){
   	   		   		modalTips("请添加抽样品种【"+nodes[0].cname+"】的样品种类和污染物！");
   	   		   		return false;
   	   		   	}
   	   		});
   		}
   		
   		if(result){
   	   		// 污染物设置样品种类必须是抽样品种或者起父节点(循环样品种类)
   	   		$("#add_monitoringDectionTemplet_table").find("tr").each(function(index){
   	   			$this = $(this);
   	   			var agrCode = $this.find("input[name='monitoringDectionTempletList["+index+"].agrCode']").val();
   	   		   	var treeObj = $.fn.zTree.getZTreeObj("monitoring_plan_project_tree2"); 
   	   		   	//通过CODE精确搜索
   	   		   	var nodes =   treeObj.transformToArray(treeObj.getNodesByParam("code", agrCode, null)); 
   	   		   	if(nodes.length > 0){
   	   		   		result = checkTab4_recursiveDown(nodes[0]);
   	   		   	}
   	   		   	if(!result){
   	   		   		modalTips("样品种类【"+nodes[0].cname+"】没有设置抽样品种，请删除！");
   	   		   		return false;
   	   		   	}
   	   		});
   		}
		return result; 
	}
   	// 污染物设置样品种类必须是抽样品种或者起父节点
   	function checkTab4_recursive(node){
		var result = false;
		if(find_input_length('add_monitoringDectionTemplet_table','input',node.code) > 0 ){
			return true;
	 	}
		while(node.parentTId != null){
	   		node = node.getParentNode();
	   		if(find_input_length('add_monitoringDectionTemplet_table','input',node.code) > 0){
	   			result = true;
	   			break;
			}
 		}
		return result;
	}
	
   	// 抽样品种必须是污染物设置样品种类或者其子节点
   	function checkTab4_recursiveDown(node){
		var result = false;
		if(find_input_length('add_monitoringBreed_table','input',node.code) > 0 ){
			return true;
	 	}
		$("#add_monitoringBreed_table").find("input[name*='.agrCode']").each(function(index,data){
			var text = $(this).val();
			var node2 = $.fn.zTree.getZTreeObj("monitoring_plan_project_tree2").getNodeByParam("code", text, node);
			if(node2 != undefined){
				result = true;
 				return true;
			}
		});
		return result;
	}
	/* 检测污染物模板数据 结束 */
	
	/* 质检机构 开始 */
	function addMonitoringOrganization(id,name){
		var tr =  $("#add_monitoringOrganization_table_template tr").clone();
		tr.find("input:eq(0)").val(id);
		tr.find("span:eq(0)").html(name);
	 	$("#add_monitoringOrganization_table").append(tr);
	 	resetTrNum('add_monitoringOrganization_table');
	}
    function delMonitoringOrganization(thisObj){
    	$(thisObj).closest("tr").remove();
    	resetTrNum('add_monitoringOrganization_table');  
	}
	// 质检机构列表得到焦点
 	$('#organizationtable').find("li").each(function(){
/* 		this.onmouseover = function() {
			this.tmpClass = this.className;
			this.className = "t2";
		};
		this.onmouseout = function() {
			this.className = this.tmpClass;
		}; */
		this.onclick = function() {
			if(this.className == "t3"){
				$('#organizationtable').find("li").attr("class","t0");	
			}else{
				$('#organizationtable').find("li").attr("class","t0");
				this.className = "t3";
			}
		};
		this.ondblclick = function() {
			if( $("#add_monitoringOrganization_table").find("input[value='"+$(this).attr("code")+"']").length <= 0 ){
				addMonitoringOrganization($(this).attr("code"),$(this).html());
			}
		};
	});
	
    //质检机构列表检索功能
   	var orgCount=0; 
   	var orgLastName; 
   	function searchOrgList(){
	   	if(count==0){
	   		orgLastName=$("#orgSearch").val(); 
	   	} 
	   	if(orgLastName!=$("#orgSearch").val()){
	   		count=0;
	   		orgLastName=$("#orgSearch").val(); 
	   	} 
	   	$('#organizationtable').find("li").attr("class","t0");
   		//通过名称模糊搜索，也可通过Id查找 
   		var nodes =   $('#organizationtable').find("li");
   	    for(var i = count; i < nodes.length; i++) {
	   		count++; 
		   	if(count>=nodes.length){ 
		   		count=0; 
		   	} 
	   		if($(nodes[i]).html().indexOf(orgLastName)!=-1){
	   			$(nodes[i]).attr("class","t3"); 
	   			var ex = document.getElementById("organizationtable");
	   			if(i >= 8){
	   				ex.scrollTop = 36 * (i - 7);
	   			}else{
	   				ex.scrollTop = 0;
	   			}
	   			return; 
   			}
   		}
   	}
	 
   	function checkTab5(){
   		if($("#add_monitoringOrganization_table").find("tr").length < 1){
   			result = false;
	  		modalTips("请添质检机构！");
   		}else{
			return true; 
   		}
	}
	/* 质检机构 结束 */

	/* 任务分配 开始 */
	function add_monitoringOrganization(){
		// 清理多余的质检机构
		$("#add_monitoringTask_table").find("tr").each(function(index){
			var orgCode = $(this).find("input[name*='orgCode']").val();
			if($("#add_monitoringOrganization_table").find("input[name*='orgCode'][value='"+orgCode+"']").length < 1){
				$(this).remove();
			};
		});
		// 添加没有的质检机构
		$("#add_monitoringOrganization_table").find("tr").each(function(index){
			var orgCode = $(this).find("input:eq(0)").val();
			var orgName = $(this).find("span:eq(0)").html();
			if($("#add_monitoringTask_table").find("input[name*='orgCode'][value='"+orgCode+"']").length < 1){
				var tr =  $("#add_monitoringTask_table_template tr").clone();
				tr.find("input[name*='orgCode']").val(orgCode);
				tr.find("span:eq(0)").html(orgName);
			 	$("#add_monitoringTask_table").append(tr);
			};
		});
		resetTrNum('add_monitoringTask_table');	
	}
	
	// 弹出任务设置窗口事前处理
	function taskTableSetWindow(jsonParam){
		
 		var $this = jsonParam.thisElem;
	  	// 行数
	  	jsonParam.params['returnRows'] = getRowsNum($this.find("input:eq(0)").attr("name"));
	  	// 抽样环节
	  	var samplelinkStr = "";
	  	$("#add_monitoringSamplelink_table").find("input:checked").each(function (index){
	  		samplelinkStr = samplelinkStr + $(this).val() + "#KV#"+$(this).closest("label").text()+"#EM#";
	  	});
	  	samplelinkStr=samplelinkStr.replace(/#EM#$/gi,"");
	  	jsonParam.params['samplelinkStr'] = samplelinkStr;
	  	
	  	// 抽样品种
	  	var breedStr = "";
	  	$("#add_monitoringBreed_table").find("input[name*='.agrCode']").each(function (index){
	  		breedStr = breedStr + $(this).val() + "#KV#"+$(this).parent().find("span").text()+"#EM#";
	  	});
	  	breedStr=breedStr.replace(/#EM#$/gi,"");
	  	jsonParam.params['breedStr'] = breedStr;
	  	
	  	//地区数量
	  	var areaCityStr = "";
	  	$("#add_monitoringAreaCount_table").find("select[name*='.citycode']").each(function (index){
	  		if($(this).val().length == 6){
	  			var citycode = $(this).val();
	  			var cityName = $(this).find("option[value='"+citycode+"']").html();
	  			var row = getRowsNum($(this).attr("name"));
	  			var districtcode = $("#add_monitoringAreaCount_table").find("input[name='monitoringAreaCountList["+row+"].districtcodeName']").val();
				if(districtcode != ""){
					areaCityStr =  areaCityStr +  citycode + "#KV#" + cityName  + "#GKV#" + districtcode + "#GEM#";
				}else{
					areaCityStr =  areaCityStr +  citycode + "#KV#" + cityName  + "#GKV#" + "#GEM#";
				}
	  		}
	  	});
	  	areaCityStr=areaCityStr.replace(/#GEM#$/gi,"");
	  	jsonParam.params['areaCityStr'] = areaCityStr;
	  	
	  	
	  	// 取得点击的任务设置信息
	  	var orgCode = $this.find("input[name*='.orgCode']").val();
	  	jsonParam.params['orgCode'] = orgCode;
	 	// 抽样数量
	  	var samplingCount = $this.find("input[name*='.samplingCounts']").val();
	  	jsonParam.params['samplingCount'] = samplingCount;
	  	// 抽样地区
	  	var areacode = $this.find("input[name*='.areacode']").val();
	  	jsonParam.params['areacode'] = areacode;
	  	// 监测环节
	  	var monitoringLink = $this.find("input[name*='.monitoringLink']").val();
	  	jsonParam.params['monitoringLink'] = monitoringLink;
	  	// 抽样品种
	  	var agrCode = $this.find("input[name*='.agrCode']").val();
	  	jsonParam.params['agrCode'] = agrCode;
	 	// 详情个数
	  	var infoCount = areacode.split("#EM#").length;
	  	jsonParam.params['infoCount'] = infoCount;

	  	return jsonParam;
	}
	/* 任务分配 结束 */
	
	/* 预览  开始 */
	function preview(){
		
		// 抽检分离
		$("#monitoring_basic_preview_table").find("tr:eq(0) td:eq(3)").text($("input[name='detached']:checked").closest("label").text());
		// 检测时间
		var timeStr = $("#starttime").val() + "～" + $("#endtime").val();
		$("#monitoring_basic_preview_table").find("tr:eq(1) td:eq(1)").text(timeStr);
		// 检测环节
		var samplelinkStr = "";
	  	$("#add_monitoringSamplelink_table").find("input:checked").each(function (index){
	  		samplelinkStr += $(this).closest("label").text()+"、";
	  	});
	  	samplelinkStr=samplelinkStr.replace(/、$/gi,"");
		$("#monitoring_basic_preview_table").find("tr:eq(1) td:eq(3)").text(samplelinkStr);
		
		
		// 监测品种
	  	var breedStr = "";
	  	$("#add_monitoringBreed_table").find("input[name*='.agrCode']").each(function (index){
	  		breedStr += $(this).parent().find("span").text()+"、";
	  	});
	  	breedStr=breedStr.replace(/、$/gi,"");
		 $("#monitoring_basic_preview_table").find("tr:eq(2) td:eq(1)").text(breedStr);
		

		// 抽样地区
		var areaCountStr = "";
		 $("#add_monitoringAreaCount_table").find("tr").each(function (index){
			 var cityCode = $(this).find("select[name*='.citycode']").val();
			 if(cityCode.length == 6){
		  			var cityName = $(this).find("option[value='"+cityCode+"']").html();
		  			cityName += "("+$(this).find("input[name*='.count']").val()+")"; 
		  			var districtName = $(this).find("input[name*='.districtcode']").parent().find("span").text();
					if(districtName != ""){
						areaCountStr = areaCountStr + "<tr><td style=\"border:0;background-color: #fff!important;\">" + cityName + "——" + districtName + "</td></tr>";
					}else{
						areaCountStr = areaCountStr + "<tr><td style=\"border:0;background-color: #fff!important;\">" + cityName + "</td></tr>";
					};
		  	};
		});
		 $("#cydq table tbody").html(areaCountStr);
		
		// 污染物模板
 		var pollStr = "";
		 $("#add_monitoringDectionTemplet_table").find("tr").each(function (index){
			 var agrName = $(this).find("td:eq(1) span").text();
			 if(agrName != ""){
		  			var pollName = $(this).find("td:eq(2) span").text();
		  			pollStr = pollStr + "<tr><td style=\"border:0\">" + agrName + "——" + pollName + "</td></tr>";
		  	}
		}); 
		$("#wrwmb table tbody").html(pollStr);
		
		// 任务内容
		var taskStr = "";
		$("#add_monitoringTask_table").find("tr").each(function (index){
			taskStr += "<tr>";
			taskStr += "<td>" + $(this).find("td:eq(0) span").text() + "</td>"; 
			taskStr += "<td>" + $(this).find("td:eq(1)").text() + "</td>";
			taskStr += "<td>" + $(this).find("td:eq(2)").text() + "</td>";
			taskStr += "<td>" + $(this).find("td:eq(3)").text() + "</td>";
			taskStr += "<td>" + $(this).find("td:eq(4)").text() + "</td>";
			taskStr += "</tr>";
		});
		$("#monitoring_task_preview_table").find("tbody").html(taskStr);
		
	}
   	function checkTab6(){
   		var result = true;
   		$("#add_monitoringTask_table").find("tr").each(function(index){
   			$this = $(this);
   			var areacode = $this.find("input[name='monitoringTaskList["+index+"].areacode']").val();
   			var samplingCounts = $this.find("input[name='monitoringTaskList["+index+"].samplingCounts']").val();
   			var text = $this.find("td:eq(0)").find("span").text();
   			if(areacode == ""){
		  		result = false;
		  		modalTips("请设置【"+text+"】的抽样地区！");
		  		return false;
   			}else if(samplingCounts == ""){
   				result = false;
		  		modalTips("请设置【"+text+"】的抽样数量！");
		  		return false;
   			}
   		});
		return result; 
	} 
	/* 任务分配 结束*/
	
	//提交时 校验1-6步
	function checkTabAll(){
 	   var checkResult = false;
	   checkResult = checkTab1();
	   if(checkResult != true){
		   $("a[href='#tab1']").click();
		   return false;
	   }
	   checkResult = checkTab2();
	   if(checkResult != true){
		   $("a[href='#tab2']").click();
		   return false;
	   }
	   checkResult = checkTab3();
	   if(checkResult != true){
		   $("a[href='#tab3']").click();
		   return false;
	   }
	   checkResult = checkTab4();
	   if(checkResult != true){
		   $("a[href='#tab4']").click();
		   return false;
	   }
	   checkResult = checkTab5();
	   if(checkResult != true){
		   $("a[href='#tab5']").click();
		   return false;
	   }
	   checkResult = checkTab6();
	   if(checkResult != true){
		   $("a[href='#tab6']").click();
		   return false;
	   }
	   return true;
	}
	
	// 窗口保存 事前处理
	function checkTaskSet(jsonParam){
		if(checkTabAll() != true){
			return false;
		}
		var result = true;
    	var param = {};
    	$("#add_monitoringAreaCount_table").find("tr").each(function(index){
    		var $this = $(this);
    		var cityCode = $this.find("select[name='monitoringAreaCountList["+index+"].citycode']").val();
    		var cityName = $this.find("select[name='monitoringAreaCountList["+index+"].citycode']").find("option[value='"+cityCode+"']").text();
    		var cityCount = $this.find("input[name='monitoringAreaCountList["+index+"].count']").val();
    		param["code_"+cityCode] = {"code":cityCode,"count":cityCount,"isCity":true,"addedCount":0,"name":cityName};
		  	// 抽检县,市区
		  	var districtcode = $this.find("input[name='monitoringAreaCountList["+index+"].districtcode']").val();
		  	var districtcodeName = $this.find("input[name='monitoringAreaCountList["+index+"].districtcodeName']").val();
		  	var districtArr = districtcode.split("#EM#");
		  	var districtNameArr = districtcodeName.split("#EM#");
		  	for(var i = 0;i < districtArr.length;i++){
		  		if(districtArr[i] != ""){
		  			var districtOne = districtArr[i].split("#KV#");
		  			var districtNameOne = districtNameArr[i].split("#KV#");
		  			param["code_"+districtOne[0]] = {"code":districtOne[0],"count":districtOne[1],"isCity":false,"addedCount":0,"cityCode":cityCode,"name":cityName + districtNameOne[1]};
		  		}
		  	}
    	});  	
   		$("#add_monitoringTask_table tr").each(function(index){
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
		  				modalTips("抽样地区："+codeObj.name+"，在第二步骤（地区及数量）中未设置！");
		  			}
		  		}
		  	}
    	});
   		if(result == false){
   			return false;
   		}
   		for(var key in param){
   			var codeObj = param[key];
   			if(codeObj.addedCount != codeObj.count && codeObj.count != 0){
   				result = false;
   				modalAlert("抽样地区："+codeObj.name+"，已设定的抽样数量总和（"+codeObj.addedCount+"）与在第二步骤（地区及数量）中的设定值（"+codeObj.count+"）不符，请修改。",{"width":"200px;"});
   				return false;
   			}
   		}
   		jsonParam.params['state'] = "1";
		return result;
	}
	
	// 复制数据功能
	function projectSelectEdit(thisObj){
		var code = $(thisObj).val();
		$.ajax({
			type : "POST",
			async : false,
			url : "monitoringProjectController.do?projectSelectEdit",
			data : {"code" : code},
			success : function(data) {
				var d = $.parseJSON(data);
				$("#monitorProject").html(d.attributes.data);
			}
		});
	}
 	
</script>

<div class="row-fluid" >
		<div class="portlet box popupBox_usual" id="monitoring_plan_project_wizard1" >
			<div class="portlet-title">
				<div class="caption">
					<i class="icon-reorder"></i> 项目设置 - <span class="step-title">步骤 1 / 6</span>
				</div>
			</div>
			<div class="portlet-body form" >
				<form action="#" class="form-horizontal" id="submit_form">
					<div class="form-wizard">
						<div class="navbar steps">
							<div class="navbar-inner">
								<ul class="row-fluid">
									<li class="stepItem">
										<a href="#tab1" data-toggle="tab" class="step active">
										<span class="number" >1</span>
										<span class="desc">基本属性<i class="icon-ok"></i></span>   
										</a>
									</li>
									<li class="stepItem">
										<a href="#tab2" data-toggle="tab" class="step">
										<span class="number">2</span>
										<span class="desc">地区及数量<i class="icon-ok"></i></span>   
										</a>
									</li>
									<li class="stepItem">
										<a href="#tab3" data-toggle="tab" class="step">
										<span class="number">3</span>
										<span class="desc">抽样品种<i class="icon-ok"></i></span>   
										</a>
									</li>
									<li class="stepItem">
										<a href="#tab4" data-toggle="tab" class="step">
										<span class="number">4</span>
										<span class="desc">污染物<i class="icon-ok"></i></span>   
										</a>
									</li>
									<li class="stepItem">
										<a href="#tab5" data-toggle="tab" class="step">
										<span class="number">5</span>
										<span class="desc">质检机构<i class="icon-ok"></i></span>   
										</a>
									</li>
									<li class="stepItem">
										<a href="#tab6" data-toggle="tab" class="step">
										<span class="number">6</span>
										<span class="desc">任务分配<i class="icon-ok"></i></span>   
										</a>
									</li>
									<li class="stepItem">
										<a href="#tab7" data-toggle="tab" class="step">
										<span class="number">7</span>
										<span class="desc">预&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;览<i class="icon-ok"></i></span>   
										</a> 
									</li>
								</ul>
							</div>
						</div>
						<div id="bar" class="progress progress-success progress-striped" style="margin-bottom: 0px;margin-top: 0px;">
							<div class="bar"></div>
						</div>
						
						<div class="tab-content" id="projectForm">
							<div class="tab-pane active" id="tab1" style="margin: 10px 0 10px 0;">
								<input id="project_id" name="id" type="hidden" value="${monitoringProjectPage.id }">
								<div class="control-group">
									<label class="control-label">项目名称</label>
									<div class="controls"><label class="help-inline">${monitoringProjectPage.name}</label></div>
								</div>
								<div class="control-group">
									<label class="control-label">监测时间</label>
									<div class="controls">
										<input class="m-wrap m-ctrl-medium date-picker" readonly size="16" type="text" id="starttime" name="starttime" style="cursor: pointer;" 
											value="<fmt:formatDate value='${monitoringProjectPage.starttime}' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>">
										<label class="help-inline">～</label>
										<input class="m-wrap m-ctrl-medium date-picker" readonly size="16" type="text"  id="endtime" name="endtime" style="cursor: pointer;" 
										  	value="<fmt:formatDate value='${monitoringProjectPage.endtime}' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>">
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">监测环节</label>
									<div class="controls" id="add_monitoringSamplelink_table">
									<c:forEach items="${monLinkList}" var="monObj" varStatus="status">
										<label class="checkbox" style="float:left; width:130px;"><input type="checkbox" name="monitoringSamplelinkList[${status.index}].monitoringLink" value="${monObj.typecode }"  ${monitoringSamplelinkMap[monObj.typecode]}/>${monObj.typename }</label>
									</c:forEach>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">抽样单模板</label>
									<select style="display:none;"></select>
									<div class="controls">
										<t:dictSelect field="sampleTemplet" hasLabel="false" typeGroupCode="${monitoringProjectPage.industryCode}sampleTemplet"  extend="{class:{value:'m-wrap'}}" defaultVal="${monitoringProjectPage.sampleTemplet}"></t:dictSelect>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">抽检分离</label>
									<div class="controls">
										<c:if test="${monitoringProjectPage.detached == '1'}">
											<label class="radio"><input type="radio" id="detached" name="detached" value="1" checked/>是</label>
											<label class="radio"><input type="radio" id="detached" name="detached" value="0" />否</label>
										</c:if>
										<c:if test="${monitoringProjectPage.detached != '1'}">
											<label class="radio"><input type="radio" id="detached" name="detached" value="1"/>是</label>
											<label class="radio"><input type="radio" id="detached" name="detached" value="0" checked />否</label>
										</c:if>	
									</div>
								</div>
									<div class="control-group">
										<label class="control-label">附件</label>
										<div class="fileLinked" style="margin-top: 4px;">
											<ul id="attachmentFile" class="attachmentFile-line">
												<c:if test="${fn:length(monitoringAttachmentList)  > 0 }">
													<c:forEach items="${monitoringAttachmentList}" var="attVal" varStatus="stuts">
														<li class="attachmentFile-items">
															<a class="attachmentFile-item" href="${attVal.path}" data-download="${attVal.path}" view="true">${attVal.pathName}</a>
														</li>
													</c:forEach>
												</c:if>	
											</ul>
										</div>
									</div>								
								
							</div>
							<div class="tab-pane" id="tab2"  style="margin: 10px 0 10px 0;">
								<div style="float:right; margin-right:30px; font-size:15px;font-weight:bold;">数量合计:<span id="monitoring_plan_project_span1"></span></div>
			 					<div>
									<a id="addMonitoringAreaCountBtn" href="#"><i class="icon-plus"></i>添加</a> <a id="delMonitoringAreaCountBtn" href="#"><i class="icon-remove"></i>删除</a> 
								</div> 
								<table class="table table-striped table-hover table-bordered" id="monitoring_plan_project_tb2" style=" margin-top:5px;">
									<thead>
										<tr>
											<th style="width: 20px;"></th>
											<th style="width: 300px;">抽检市</th>
											<th style="width: 600px;">抽检县（市、区）</th>
											<th>抽样数量</th>
										</tr>
									</thead>
									<tbody id="add_monitoringAreaCount_table">	
									<c:if test="${fn:length(monitoringAreaCountList)  <= 0 }">
											<tr>
												 <td align="center"><input style="width:20px;" type="checkbox" name="ck"/></td>
												 <td align="left">
												   <select style="display:none;"></select>
													<select  name="monitoringAreaCountList[0].citycode" class="m-wrap" tabindex="1" style="height:10px; width:200px;" onchange="monitoringAreaCount_selectCity(this);">
														<option value=""></option>
														<c:forEach items="${sysAreaCodeList}" var="areacode">
															<option value="${areacode.code}">${areacode.areaname}</option>
														</c:forEach>
												  	</select>
												 </td>
												 <td align="left" onclick="projectAreaCountSet(this);" style="cursor: pointer;">
												 	<input name="monitoringAreaCountList[0].districtcode" type="hidden"/>
												 	<input name="monitoringAreaCountList[0].districtcodeName" type="hidden"/>
												 	<span></span>
												 </td>
												 <td align="left"><input name="monitoringAreaCountList[0].count" type="text" class="m-wrap" style="width:100px;" onkeyup="monitoringAreaCount_all();" onkeydown="monitoringAreaCount_all();"/></td>
											</tr>
									</c:if>
									<c:if test="${fn:length(monitoringAreaCountList)  > 0 }">
										<c:forEach items="${monitoringAreaCountList}" var="poVal" varStatus="stuts">
											<tr>
												 <td align="center"><input style="width:20px;" type="checkbox" name="ck"/></td>
												 <td align="left">
												    <select style="display:none;"></select>
													<select name="monitoringAreaCountList[${stuts.index }].citycode" class="m-wrap" tabindex="1" style="height:10px; width:200px;" onchange="monitoringAreaCount_selectCity(this);"  back-up="${poVal.citycode}">
														<option value=""></option>
														<c:forEach items="${sysAreaCodeList}" var="areacode">
															<c:if test="${poVal.citycode == areacode.code}" >
																<option value="${areacode.code}" selected>${areacode.areaname}</option>
															</c:if>
															<c:if test="${poVal.citycode != areacode.code}" >
																<option value="${areacode.code}">${areacode.areaname}</option>
															</c:if>
														</c:forEach>
												  	</select>
												 </td>
												 <td align="left" onclick="projectAreaCountSet(this);" style="cursor: pointer;">
												 	<input name="monitoringAreaCountList[${stuts.index }].districtcode" type="hidden" value="${poVal.districtcodeCount }">
												 	<input name="monitoringAreaCountList[${stuts.index }].districtcodeName" type="hidden" value="${poVal.districtcodeName }">
												 	<span>${poVal.nameCount }</span>
												 </td>
												 <td align="left"><input name="monitoringAreaCountList[${stuts.index }].count" type="text" class="m-wrap" style="width:100px;" value="${poVal.count }"  onkeyup="monitoringAreaCount_all();" onkeydown="monitoringAreaCount_all();" /></td>
											</tr>
										</c:forEach>
									</c:if>	
									</tbody>
								</table>
								<font color="red">*点击单元格设置!</font>
							</div>
							<div class="tab-pane" id="tab3"  style="margin: 10px 0 10px 0;">
						    	<div class="span6" style="width:45%;margin-left:0.3%;">
									<div class="portlet box box_usual">
										<div class="portlet-title">
											<div class="caption"><i class="icon-comments"></i>农产品分类</div>
										</div>
										
										<div class="portlet-body fuelux">
											<div class="seachDiv">
												<div>
													<input id="agrCategorySearch" type="text" placeholder="" class="m-wrap seachElement small" style="margin-bottom:0px;"  size="38"/>
													<a href="#" class="btn btngroup_seach" style="margin-top: 3px;padding: 2px 8px!important;" onclick="searchTree()"><i class="icon-search"></i>搜索</a>
												</div>
											</div>
											<div class="control-group" style="OVERFLOW-Y: auto;OVERFLOW-X: hidden;height: 314px;">
												<ul class="ztree" id="monitoring_plan_project_tree2"></ul>
											</div>
										</div>
									</div>
								</div>
								
						    	<div class="span6" style="width:45%;margin-left:0.3%;">
									<div class="portlet box box_usual">
										<div class="portlet-title">
											<div class="caption"><i class="icon-comments"></i>选择结果</div>
										</div>
										<div class="portlet-body" style="OVERFLOW-Y: auto;  OVERFLOW-X: hidden;height: 344px;">
											<table  class="table  table-bordered " id="monitoringBreed_table">
												<tr>
													<th class="hidden-480">农产品分类</th>
													<th class="hidden-480"></th>
												</tr>
												<tbody id="add_monitoringBreed_table">
												<c:if test="${fn:length(monitoringBreedList)  > 0 }">
													<c:forEach items="${monitoringBreedList}" var="poVal" varStatus="stuts">
														<tr>
															  <td align="left">
															  	<input name="monitoringBreedList[${stuts.index }].agrCode" value="${poVal.agrCode }" type="hidden">
															  	<span>${poVal.agrName }</span>
															  </td>
															  <td align="left" width="70px;"><a class="btn mini yellow" onclick="delLogicData(this);"><i class="icon-trash"></i>删除</a></td>
											   			</tr>
													</c:forEach>
												</c:if>
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>

							<div class="tab-pane" id="tab4"  style="margin: 10px 0 10px 0;">
								<div>
									<a id="addMonitoringDectionTempletBtn" href="#"><i class="icon-plus"></i>添加</a> <a id="delMonitoringDectionTempletBtn" href="#"><i class="icon-remove"></i>删除</a> 
								</div> 
								<table class="table table-striped table-hover table-bordered" id="monitoringDectionTemplet_table" >
									<thead>
										<tr>
											<th style="width: 2%;"></th>
											<th style="width: 40%;">样品种类</th>
											<th style="width: 58%;">污染物</th>
										</tr>
									</thead>
									<tbody id="add_monitoringDectionTemplet_table">	
									<c:if test="${fn:length(monitoringDectionTempletList)  <= 0 }">
											<tr style="height:40px;">
												  <td align="center"><input style="width:20px;"  type="checkbox" name="ck"/></td>
												  <td align="left" style="cursor: pointer;" action-mode="ajax" action-pop="#monitoringDectionTemplet_agr_selected" action-before="agrWindow">
												  	<input name="monitoringDectionTempletList[0].agrCode" type="hidden" >
												  	<span></span>
												  	<!-- <input name="monitoringDectionTempletList[0].agrName" type="text" style="width:100%;" disabled="disabled" > -->
												  </td>
												  <td align="left" style="cursor: pointer;" action-mode="ajax" action-pop="#monitoringDectionTemplet_poll_selected" action-url="monitoringProjectController.do?projectAreaCountPollSet" action-before="pollWindow">
												  	<input name="monitoringDectionTempletList[0].pollCas" type="hidden">
												  	<span></span>
												  	<!-- <input name="monitoringDectionTempletList[0].pollName" type="text" style="width:100%;" disabled="disabled"> -->
												  </td>
								   			</tr>
									</c:if>
									<c:if test="${fn:length(monitoringDectionTempletList)  > 0 }">
										<c:forEach items="${monitoringDectionTempletList}" var="poVal" varStatus="stuts">
											<tr style="height:40px;">
												   <td align="center"><input style="width:20px;"  type="checkbox" name="ck" /></td>
								 				   <td align="left" style="cursor: pointer;" action-mode="ajax" action-pop="#monitoringDectionTemplet_agr_selected" action-before="agrWindow">
								 				   	<input name="monitoringDectionTempletList[${stuts.index }].agrCode" value="${poVal.agrCode }" type="hidden">
								 				   	<span>${poVal.agrName }</span>
								 				   </td>
												   <td align="left" style="cursor: pointer;" action-mode="ajax" action-pop="#monitoringDectionTemplet_poll_selected" action-url="monitoringProjectController.do?projectAreaCountPollSet" action-before="pollWindow">
												   	<input name="monitoringDectionTempletList[${stuts.index }].pollCas" value="${poVal.pollCas }" type="hidden" >
												   	<span>${poVal.pollName }</span>
												   </td>
								   			</tr>
										</c:forEach>
									</c:if>	
									</tbody>
								</table>
								<font>提示信息（上一步设置的农产品分类）：<font id="prompt_4"></font></font><br>
								<br> <font color="red">*点击单元格设置!</font>
							</div>

							<div class="tab-pane" id="tab5"  style="margin: 10px 0 10px 0;">
						    	<div class="span6" style="width:45%;margin-left:0.3%;" >
									<div class="portlet box box_usual">
										<div class="portlet-title">
											<div class="caption"><i class="icon-comments"></i>质检机构列表</div>
										</div>
										<div class="portlet-body" style="font-size:11pt;">
											<div class="seachDiv">
												<div>
													<input type="text" placeholder="" class="m-wrap seachElement medium" style="margin-bottom:0px;"id="orgSearch"/>
													<a href="#" class="btn btngroup_seach" style="margin-top: 3px;padding: 2px 8px!important;" onclick="searchOrgList()"><i class="icon-search"></i>搜索</a>
												</div>
											</div>
											<div id="organizationtable" class="organizationtable">
												<ul>
												<c:forEach items="${organizationEntityList}" var="poVal" varStatus="stuts">
													<li code="${poVal.code}" class="t${stuts.index % 2}">${poVal.ogrname}</li>
												</c:forEach>
												</ul>
											</div>
										</div>
									</div>
								</div>
								
						    	<div class="span6" style="width:45%;margin-left:0.3%;">
									<div class="portlet box box_usual">
										<div class="portlet-title">
											<div class="caption"><i class="icon-comments"></i>选择结果</div>
										</div>
										<div class="portlet-body"style="OVERFLOW-Y: auto;  OVERFLOW-X: hidden;height: 344px;">
											<table class="table  table-bordered " id="monitoringOrganization_table">
												<tr>
													<th class="hidden-480">质检机构</th>
													<th class="hidden-480"></th>
												</tr>
												<tbody id="add_monitoringOrganization_table">	
												<c:if test="${fn:length(monitoringOrganizationList)  > 0 }">
													<c:forEach items="${monitoringOrganizationList}" var="poVal" varStatus="stuts">
														<tr>
															<td>
																<input name="monitoringOrganizationList[${stuts.index }].orgCode" type="hidden"  value="${poVal.orgCode }">
																<span>${poVal.orgName }</span>
															</td>
															<td width="70px;"><a class="btn mini yellow" onclick="delMonitoringOrganization(this);"><i class="icon-trash"></i>删除</a></td>
														</tr>
													</c:forEach>
												</c:if>	
												</tbody>
											</table>
										</div>
									</div>
								</div>
								<div class="portlet-body form">
								<font color="red">*双击选择!</font>
								</div>
							</div>

				    		<div class="tab-pane" id="tab6"  style="margin: 10px 0 10px 0;">
								<div class="portlet-body form">
									<div class="form-horizontal form-view">
										<div class="row-fluid">
											<div class="tab-content">
												<div class="tab-pane active">
													<table class="table table-striped table-bordered table-hover" id="monitoringTask_table">
														<thead>
															<tr>
																<th class="hidden-480">抽样机构</th>				
																<th class="hidden-480">抽样数量</th>
																<th class="hidden-480">抽样地区</th>
																<th class="hidden-480">抽样环节</th>
																<th class="hidden-480">抽样品种</th>
															</tr>
														</thead>
														<tbody id="add_monitoringTask_table">
															<c:if test="${fn:length(monitoringTaskList)  > 0 }">
																<c:forEach items="${monitoringTaskList}" var="poVal" varStatus="stuts">
																	<tr style="cursor: pointer;" action-mode="ajax" action-pop="#monitoringTask_set" action-url="monitoringProjectController.do?projectTaskSet" action-before="taskTableSetWindow">
																	   <td align="left">
																		   <input name="monitoringTaskList[${stuts.index }].orgCode" type="hidden" value="${poVal.orgCode }">
																		   <input name="monitoringTaskList[${stuts.index }].taskname" type="hidden" value="${poVal.taskname }">
																		   <input name="monitoringTaskList[${stuts.index }].areacode" type="hidden" value="${poVal.areacode }">
																		   <input name="monitoringTaskList[${stuts.index }].monitoringLink" type="hidden" value="${poVal.monitoringLink }">
																		   <input name="monitoringTaskList[${stuts.index }].agrCode" type="hidden" value="${poVal.agrCode }">
																		   <input name="monitoringTaskList[${stuts.index }].samplingCounts" type="hidden" value="${poVal.samplingCounts }">
																		   <span>${poVal.ogrName }</span>
																	   </td>
																	   <td align="left">${poVal.samplingCount }</td>
																	   <td align="left">${poVal.areaName }</td>
																	   <td align="left">${poVal.monitoringlinkName }</td>
																	   <td align="left">${poVal.agrName }</td>
																	</tr>
																</c:forEach>
															</c:if>	
														</tbody>
													</table>
												</div>
											</div>
										</div>
									</div>
								</div>
								<font color="red">*点击行设置!</font>
							</div>
							
							<div class="tab-pane" id="tab7"  style="margin: 10px 0 10px 0;">
								<div class="portlet-body form">
									<div class="form-horizontal form-view">
										<div class="row-fluid">
											<div class="tab-content">
												<div class="tab-pane active" id="monitoring_preview">
														<table  class="table table-striped table-bordered table-hover" id="monitoring_basic_preview_table">
														 <tbody>
															<tr>
																<td><label class="control-label" style="font-weight:bold;">项目名称</label></td>
																<td>${monitoringProjectPage.name}</td>
																<td><label class="control-label" style="font-weight:bold;">抽检分离</label></td>
																<td></td>
															</tr>
															<tr>
																<td><label class="control-label" style="font-weight:bold;">监测时间</label></td>
																<td></td>
																<td><label class="control-label" style="font-weight:bold;">监测环节</label></td>
																<td></td>
															</tr>
															<tr>
																<td><label class="control-label" style="font-weight:bold;">监测品种</label></td>
																<td colspan="3"></td>
															</tr>
															<tr>
																<td style="background-color: #fff!important"><label class="control-label" style="font-weight:bold;">抽样地区</label></td>
																<td colspan="3" id="cydq" style="background-color: #fff!important"><table><tbody></tbody></table></td>
															</tr>
															<tr>
																<td><label class="control-label" style="font-weight:bold;">污染物模板</label></td>
																<td colspan="3" id="wrwmb"><table><tbody></tbody></table></td>
															</tr>
															<tr>
																<td><label class="control-label" style="font-weight:bold;">方案附件</label></td>
																<td colspan="3" id="wrwmb">
																	<div class="fileLinked" style="margin-top: 4px;margin-left: 5px;">
																		<ul id="attachmentFile" class="attachmentFile-line">
																			<c:if test="${fn:length(monitoringAttachmentList)  > 0 }">
																				<c:forEach items="${monitoringAttachmentList}" var="attVal" varStatus="stuts">
																					<li class="attachmentFile-items">
																						<a class="attachmentFile-item" href="${attVal.path}" data-download="${attVal.path}" view="true">${attVal.pathName}</a>
																					</li>
																				</c:forEach>
																			</c:if>	
																		</ul>
																	</div>
																</td>
															</tr>
															</tbody>
														</table>
														
														<table  class="table table-striped table-bordered table-hover" id="monitoring_task_preview_table">
															<thead>
																<tr>
																	<th style="width:224px">抽样机构</th>				
																	<th style="width:80px">抽样数量</th>
																	<th style="width:300px">抽样地区</th>
																	<th style="width:200px">抽样环节</th>
																	<th>抽样品种</th>
																</tr>
															</thead>
															<tbody>
															</tbody>
														</table>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>						
						</div>
						<div class="modal-footer">
							<a data-toggle="modal" class="btn green" id="copyOldProject" style="float:left;" href="#monitoring_project_copy">复制历史项目</a>
							<a class="btn popenter" data-toggle="modal" action-mode="ajax" action-url="monitoringProjectController.do?save" action-form="projectForm" action-operation="popsave" action-fresh="monitoring_plan_project_tb1">保存</a>
							<a href="javascript:;" class="btn button-previous"><i class="m-icon-swapleft"></i> 上一步 </a>
							<a href="javascript:;" class="btn button-next">下一步 <i class="m-icon-swapright m-icon-white"></i></a>
							<a class="button-submit btn"  action-mode="ajax" action-url="monitoringProjectController.do?save" action-form="projectForm" action-operation="popsave" action-fresh="monitoring_plan_project_tb1" action-before="checkTaskSet"> 发布 <i class="m-icon-swapright m-icon-white"></i></a>
							<button id="btnClose" type="button" data-dismiss="modal" class="button-close btn" >关闭</button>
						</div>
					</div>
				</form>
			</div>
		</div>
</div>
<!--  模版区域  开始  -->
<table style="display:none">
<tbody id="add_monitoringSamplelink_table_template">
	<tr>
	 <td align="center"><input style="width:20px;" type="checkbox" name="ck"/></td>
		  <td align="left"><input name="monitoringSamplelinkList[#index#].monitoringLink" maxlength="50" type="text" style="width:120px;"></td>
	</tr>
 </tbody>
<tbody id="add_monitoringAreaCount_table_template">
	<tr>
	 <td align="center"><input style="width:20px;" type="checkbox" name="ck"/></td>
	 <td align="left">
	    <select style="display:none;"></select>
		<select  name="monitoringAreaCountList[#index#].citycode" class="m-wrap" tabindex="1" style="height:10px; width:200px;" onchange="monitoringAreaCount_selectCity(this);">
			<option value=""></option>
			<c:forEach items="${sysAreaCodeList}" var="areacode">
				<option value="${areacode.code}">${areacode.areaname}</option>
			</c:forEach>
	  	</select>
	 </td>
	 <td align="left" onclick="projectAreaCountSet(this);" style="cursor: pointer;">
	 	<input name="monitoringAreaCountList[#index#].districtcode" type="hidden"/>
	 	<input name="monitoringAreaCountList[0].districtcodeName" type="hidden"/>
	 	<span></span>
	 	<!-- <input name="monitoringAreaCountList[#index#].districtname" type="text" style="width:100%;height: 100%;" disabled="disabled"> -->
	 </td>
	 <td align="left"><input name="monitoringAreaCountList[#index#].count" type="text" class="m-wrap" style="width:100px;"  onkeyup="monitoringAreaCount_all();" onkeydown="monitoringAreaCount_all();" /></td>
	</tr>	
 </tbody>
<tbody id="add_monitoringBreed_table_template">
	<tr>
		  <td align="left">
		  	<input name="monitoringBreedList[#index#].agrCode" type="hidden">
		  	<span></span>
		  </td>
		  <td align="left" width="70px;"><a class="btn mini yellow" onclick="delLogicData(this);"><i class="icon-trash"></i>删除</a></td>
 	</tr>
 </tbody>
<tbody id="add_monitoringDectionTemplet_table_template">
	<tr style="height:40px;">
		    <td align="center"><input style="width:20px;"  type="checkbox" name="ck" /></td>
			<td align="left" style="cursor: pointer;" action-mode="ajax" action-pop="#monitoringDectionTemplet_agr_selected" action-before="agrWindow">
			 <input name="monitoringDectionTempletList[#index#].agrCode" type="hidden" >
			 <span></span>
		   </td>
		   <td align="left" style="cursor: pointer;" action-mode="ajax" action-pop="#monitoringDectionTemplet_poll_selected" action-url="monitoringProjectController.do?projectAreaCountPollSet" action-before="pollWindow">
		   	<input name="monitoringDectionTempletList[#index#].pollCas" type="hidden" >
		   	<span></span>
		   </td>
 	</tr>
 </tbody>
<tbody id="add_monitoringOrganization_table_template">
	<tr>
		<td>
			<input name="monitoringOrganizationList[#index#].orgCode" type="hidden">
			<span></span>
		</td>
		<td width="70px;"><a class="btn mini yellow" onclick="delMonitoringOrganization(this);"><i class="icon-trash"></i>删除</a></td>
	</tr>
 </tbody>
<tbody id="add_monitoringTask_table_template"> 
	<tr style="cursor: pointer;" action-mode="ajax" action-pop="#monitoringTask_set" action-url="monitoringProjectController.do?projectTaskSet" action-before="taskTableSetWindow">
	   <td align="left">
		   <input name="monitoringTaskList[#index#].orgCode" type="hidden">
		   <input name="monitoringTaskList[#index#].taskname" type="hidden">
		   <input name="monitoringTaskList[#index#].areacode" type="hidden">
		   <input name="monitoringTaskList[#index#].monitoringLink" type="hidden">
		   <input name="monitoringTaskList[#index#].agrCode" type="hidden">
		   <input name="monitoringTaskList[#index#].samplingCounts" type="hidden">
		   <span></span>
	   </td>
	   <td align="left"></td>
	   <td align="left"></td>
	   <td align="left"></td>
	   <td align="left"></td>
	</tr>
 </tbody> 
</table>
<!--  模版区域  结束  -->

<!--  弹出框  开始  -->
<!-- 样品种类选择 -->
<div id="monitoringDectionTemplet_agr_selected" class="modal hide fade centerDiv" tabindex="-1" data-width="520" style="max-width: 520px;" >
	<div class="row-fluid">
		<div class="span12">  
			<div class="portlet box popupBox_usual">
				<div class="portlet-title">
					<div class="caption">
						<i class="icon-reorder"></i>
						<span class="hidden-480">选择品种</span>
					</div>
				</div>
				<div class="portlet-body">
	                  <div class="tab-content">
						<div class="tab-pane active">
							<form action="#" class="form-horizontal">
								<div class="portlet-body fuelux">
									<ul class="ztree" id="monitoring_plan_project_tree1">
									</ul>
								</div>
								<div class="modal-footer">
									<button type="button" data-dismiss="modal" class="btn popclose">关闭</button>
									<button type="button" class="btn popenter" onclick="ypzlselected()">确定</button>
								</div>
							</form> 
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 污染物选择 -->
<div id="monitoringDectionTemplet_poll_selected" class="modal hide fade centerDiv" tabindex="-1" data-width="900" style="max-width: 900px;"></div>

<!-- 任务设置 -->
<div id="monitoringTask_set" class="modal hide fade centerDiv" tabindex="-1" data-width="900" style="max-width: 900px;"></div>

<!-- 任务复制 -->
<div id="monitoring_project_copy" class="modal hide fade centerDiv" tabindex="-1" data-width="900" style="max-width: 900px;" >
	<div class="row-fluid">
		<div class="span12">  
			<div class="portlet box popupBox_usual">
				<div class="portlet-title">
					<div class="caption">
						<i class="icon-reorder"></i>
						<span class="hidden-480">复制历史项目</span>
					</div>
				</div>				
				<div class="portlet-body">
					<div class="tab-content">
						<div class="tab-pane active">
							<form action="#" class="form-horizontal">
									<div class="control-group">
										<label class="control-label">监测类型</label>
										<div class="controls">
											<t:dictSelect id="id" field="monitorType" hasLabel="false" typeGroupCode="monitorType"  extend="{style:{value:'width:460px;'},onchange:{value:'projectSelectEdit(this)'},class:{value:'dfdsfsds'} }" defaultVal=""></t:dictSelect>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">监测项目</label>
										<div class="controls">
											<select id="monitorProject" class="m-wrap" data-placeholder="选择监测类型" tabindex="1" style="width:460px;" size="10">
												<option value=""></option>
											</select>
										</div>
									</div>
							</form>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" data-dismiss="modal" class="btn popclose">关闭</button>
					<button type="button" class="btn popenter" onclick="projectSetCopy();">载入</button>
				</div>
			</div>
		</div>
	</div>
</div>

<!--  弹出框  结束  -->
<script>

jQuery(document).ready(function() { 
	//抽样品种选择
	$.fn.zTree.init($("#monitoring_plan_project_tree2"), settingTree2, zNodesTree2);
	// 农产品对应污染物选择
	$.fn.zTree.init($("#monitoring_plan_project_tree1"), settingTree1, zNodesTree1);
   $('#monitoring_plan_project_wizard1').bootstrapWizard({
       'nextSelector': '.button-next',
       'previousSelector': '.button-previous',
       onTabClick: function (tab, navigation, index) {
    	   return false;
    	   if(index == 2){
        	   init_zNodesTree1();
           }if(index == 4){
    		   add_monitoringOrganization();
    	   }
    	   return false;
    	   
/*     	   index = index + 1;
    	   var checkResult = false;
    	   if(index == 1){
    		   checkResult = checkTab1();
    	   } else if(index == 2){
    		   checkResult = checkTab2();
    	   }else if(index == 3){
    		   checkResult = checkTab3();
    	   }else if(index == 4){
    		   checkResult = checkTab4();
    	   }else if(index == 5){
    		   checkResult = checkTab5();
    	   }else if(index == 6){
    		   checkResult = checkTab6();
    	   }else if(index == 7){
    		   checkResult = true;
    	   }
    	   if(checkResult != true){
    		   return false;
    	   } */
       },
       onNext: function (tab, navigation, index) {
    	   var checkResult = false;
    	   if(index == 1){
    		   checkResult = checkTab1();
    	   } else if(index == 2){
    		   checkResult = checkTab2();
    	   }else if(index == 3){
    		   checkResult = checkTab3();
    	   }else if(index == 4){
    		   checkResult = checkTab4();
    	   }else if(index == 5){
    		   checkResult = checkTab5();
    	   }else if(index == 6){
    		   checkResult = checkTab6();
    	   }
    	   if(checkResult != true){
    		   return false;
    	   }
           App.scrollTo($('.page-title'));
       },
       onPrevious: function (tab, navigation, index) {
           App.scrollTo($('.page-title'));
       },
       onTabShow: function (tab, navigation, index) {
           var total = navigation.find('li').length;
           var current = index + 1;
           $('.step-title', $('#monitoring_plan_project_wizard1')).text('步骤 ' + (index + 1) + ' / ' + total);
           jQuery('li', $('#monitoring_plan_project_wizard1')).removeClass("done");
           var li_list = navigation.find('li');
           for (var i = 0; i < index; i++) {
               jQuery(li_list[i]).addClass("done");
           }

           if (current == 1) {
        	   $('#copyOldProject').show();
               $('#monitoring_plan_project_wizard1').find('.button-previous').hide();
           } else {
        	   $('#copyOldProject').hide();
               $('#monitoring_plan_project_wizard1').find('.button-previous').show();
           }

           if (current >= total) {
               $('#monitoring_plan_project_wizard1').find('.button-next').hide();
               $('#monitoring_plan_project_wizard1').find('.button-submit').show();
               //displayConfirm();
           } else {
               $('#monitoring_plan_project_wizard1').find('.button-next').show();
               $('#monitoring_plan_project_wizard1').find('.button-submit').hide();
           }
           if(index == 3){
        	   init_zNodesTree1();
           }else if(index == 5){
    		   add_monitoringOrganization();
    	   }else if(index == 6){
    		   preview();
    	   }

           var $percent = (current / total) * 100;
           $('#monitoring_plan_project_wizard1').find('.bar').css({
               width: $percent + '%'
           });
       }
   });
   $('#monitoring_plan_project_wizard1').find('.button-previous').hide();
   $('#monitoring_plan_project_wizard1 .button-submit').click(function () {
   }).hide();
	// 样式修正
	$("#submit_form").find("input[type='checkbox']").uniform();
  $("#add_monitoringTask_table tr").each(function(index){
	  $(this).find("td:eq(2)").html(removeDuplicate($(this).find("td:eq(2)").html(),"、")); 
	  $(this).find("td:eq(3)").html(removeDuplicate($(this).find("td:eq(3)").html(),"、"));
	  $(this).find("td:eq(4)").html(removeDuplicate($(this).find("td:eq(4)").html(),"、"));
  });
  monitoringAreaCount_all();
  
});


</script>