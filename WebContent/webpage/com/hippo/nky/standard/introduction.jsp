<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<!DOCTYPE html>
<html>
<head>
<title>栏目信息</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link href="plug-in/kindeditor/themes/default/default.css" rel="stylesheet" />
<script charset="utf-8" src="plug-in/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="plug-in/kindeditor/lang/zh_CN.js"></script>
<script type="text/javascript">
	var editor2;
	$(function() {
		var options = {
				 width : '100%',
				  height : '200',
				  newlineTag : 'br',
				  fullscreenMode : false,
				  allowUpload : true,
				  allowFileManager : true
				  
		};
		
		editor2 = KindEditor.create('#editor_id',options);
		$('#parent').combotree({
			url : 'nkyPortalIntroductionsController.do?getTreeJson&introductionleavel=-1',
		});		
		if($('#introductionleavel').val()=='1'){
			$('#pfun').show();
		}else if($('#introductionleavel').val()=='2'){
			$('#pfun').show();
		}else{
			$('#pfun').hide();
		}
		
		$('#introductionleavel').change(function(){
			if($(this).val()=='1'){
				$('#pfun').show();
				$('#parent').combotree({
					url : 'nkyPortalIntroductionsController.do?getTreeJson&introductionleavel=0',
					required:true
				});
				var t = $('#parent').combotree('tree');
			    //var nodes = t.tree('getRoots');
				//for(var i=0;i<nodes.length;i++){
				$('#parent').combotree('setValue', '请选择父栏目！');
				//alert(nodes[0].text);
					//break;
				//}
			}else if($(this).val()=='2'){
				$('#pfun').show();
				$('#parent').combotree({
					url : 'nkyPortalIntroductionsController.do?getTreeJson&introductionleavel=1',
					required:true
				});
				var t = $('#parent').combotree('tree');
				//var nodes = t.tree('getRoots');
				//for(var i=0;i<nodes.length;i++){
				$('#parent').combotree('setValue', '请选择父栏目！');
				//alert(nodes[0].text);
					//break;
				//}
			}else{
				var t = $('#parent').combotree('tree');
				var node = t.tree('getSelected');
				if(node){
					$('#parent').combotree('setValue', null);
				}
				$('#pfun').hide();
			}
		});
	});
</script>
</head>
<body style="overflow-y: hidden" scroll="yes">
	<t:formvalid formid="formobj" layout="table" dialog="true"
		action="nkyPortalIntroductionsController.do?save"  beforeSubmit="editor2.sync();" tiptype="1">
		<%--
				<input type="hidden" name="functionOrder" value="${function.functionOrder}">
			--%>
		<input name="id" type="hidden" value="${function.id}">
		<table style="width: 700px;" cellpadding="0" cellspacing="1" class="formtable">
			<tr>
					<td align="right" style="width:60px;">
						<label class="Validform_label">
							栏目名称:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="name" name="name" datatype="s1-32"
							   value="${function.name}" maxlength="16" disabled="true"/>
						<span class="Validform_checktip"></span>
					</td>
					<td align="right" style="width:60px;">
						<label class="Validform_label">
							栏目等级:
						</label>
					</td>
					<td class="value">
						<select name="introductionleavel" id="introductionleavel" datatype="*"  disabled="true">
						<option value="0"
							<c:if test="${function.introductionleavel eq 0}">selected="selected"</c:if>>
							一级栏目
						</option>
						<option value="1"
							<c:if test="${function.introductionleavel eq 1}">selected="selected"</c:if>>
							二级栏目
						</option>
						<option value="2"
							<c:if test="${function.introductionleavel eq 2}">selected="selected"</c:if>>
							三级栏目
						</option>
					</select>
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				 <tr >
					<td align="right">
						<label class="Validform_label">
							显示类型:
						</label>
					</td>
					<td class="value" colspan="3">
						<t:dictSelect typeGroupCode="lm_video_type" field="displaytype" defaultVal="${function.displaytype}" hasLabel="false" ></t:dictSelect>
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr  id="pfun">
					<td align="right" >
						<label class="Validform_label">
							父栏目:
						</label>
					</td>
					<td class="value" colspan="3">
						<input id="parent" name="pid" value="${function.pid}"  style="width:156px;"  disabled="true">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<!--
				<tr >
					<td align="right">
						<label class="Validform_label">
							排序:
						</label>
					</td>
					<td class="value" colspan="3">
						<input class="inputxt" id="sort" name="sort" ignore="ignore" value="${function.sort}">
						<span class="Validform_checktip">排序需要填写数字</span>
					</td>
				</tr>
				<tr >
					<td align="right">
						<label class="Validform_label">
							列表来源:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="sourcelist" name="sourcelist" ignore="ignore"
					   value="${function.sourcelist}">
						<span class="Validform_checktip"></span>
					</td>
					<td align="right">
						<label class="Validform_label">
							列表显示类型:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="listdisplaytype" name="listdisplaytype" ignore="ignore"
					   value="${function.listdisplaytype}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr >
					<td align="right">
						<label class="Validform_label">
							关联条件:
						</label>
					</td>
					<td class="value" colspan="3">
						<input class="inputxt" id="associatecondition" name="associatecondition" ignore="ignore"
					   value="${function.associatecondition}">
						<span class="Validform_checktip"></span>
					</td>
				</tr> -->
				<tr>
					<td align="right">
						<label class="Validform_label">
							内容:
						</label>
					</td>
					<td class="value" colspan="3">
              			<textarea id="editor_id" name="content" rows="0" cols="125" class="inputArea">${function.content}</textarea>
						<span class="Validform_checktip"></span>
					</td>
				</tr>
		    </table>
	</t:formvalid>
</body>

</html>
