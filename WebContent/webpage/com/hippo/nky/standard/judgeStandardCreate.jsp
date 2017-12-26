<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<link rel="stylesheet" href="plug-in/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="plug-in/zTree/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="plug-in/zTree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="plug-in/zTree/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="plug-in/zTree/jquery.ztree.exedit-3.5.js"></script>

	<SCRIPT type="text/javascript">
		<!--
		var setting = {
			check: {
				enable: true,
				chkDisabledInherit: true
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onCheck: onCheck
			}
		};

		var zNodes = ${treeData};
		
		function onCheck(e, treeId, treeNode) {
			var treeNodes = $.fn.zTree.getZTreeObj("forSelJudgeTree").getCheckedNodes(true);
			var idArray = "";
			var count = 0;
			if(treeNodes!=null && treeNodes!=""){
				for(var i=0;i<treeNodes.length;i++){
					var treeNode = treeNodes[i];
					if(treeNode.noteType == 'info'){
						idArray = idArray + treeNode.id + ','; 
						count++;
					}
				}
			}
			$('#array').val(idArray);
			$('#arrayCount').val(count);
		}

		$(document).ready(function(){
			$.fn.zTree.init($("#forSelJudgeTree"), setting, zNodes);
			
		});
		//-->
	</SCRIPT>
	<div class="zforSelJudgeTreeBackground left">
		<ul id="forSelJudgeTree" class="ztree"></ul>
	</div>
	<input type="hidden" id="array" name="attay"/>
	<input type="hidden" id="arrayCount" name="arrayCount"/>
	
