<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>农科院新闻</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <%@include file="/context/header.jsp"%>
  <link href="plug-in/kindeditor/themes/default/default.css" rel="stylesheet" />
  <script charset="utf-8" src="plug-in/kindeditor/kindeditor-min.js"></script>
  <script charset="utf-8" src="plug-in/kindeditor/lang/zh_CN.js"></script>
  <script type="text/javascript">
  var editor;
  var options = {
		  width : '100%',
		  height : '200',
		  newlineTag : 'br',
		  allowUpload : true,
		  allowFileManager : true 
  };
  KindEditor.ready(function(K) {
	  editor = K.create('#editor_id',options);
	  
  });
  
	function setAttachmentList(d, file, response){
		// 类别为文件时的回调操作
		var muliti = true;
		if(muliti){
			var path = $("#attachmentList").val() + ";"+d.attributes.oldFileName+"|"+d.attributes.fileSize+"|/" + d.attributes.filePath;
			//var path = $("#attachmentList").val() + ";"+d.attributes.oldFileName+"|/test.txt";
			$("#attachmentList").val(path);
		} else {
			$("#attachmentList").val("/" + d.attributes.filePath);
		}
	}
	
	function cancelComplete(filePath){
		var attPath = $("#attachmentList").val();
		// 用正则删除   ;+名|路径
		var parter= eval("/(;[^;]+"+"\\/"+filePath.replace(/(\/)/g, "\\/")+")/g");
		$("#attachmentList").val(attPath.replace(parter , ""));
	}
	
	function delOldUpload(id){
		$("#delOldUploadList").val("," + id);
	}

  </script>
 </head>
 <body style="overflow-y: hidden;" scroll="yes">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="newsController.do?save" beforeSubmit="editor.sync();" tiptype="1">
			<input id="id" name="id" type="hidden" value="${newsPage.id }">
			<table style="width: 700px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right"style="width: 70px;">
						<label class="Validform_label">
							新闻标题:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="title" name="title" datatype="byterange" min="1" max="128"
							   value="${newsPage.title}">
						<span class="Validform_checktip"></span>
					</td>
					<td align="right">
						<label class="Validform_label">
						        新闻类型:
						</label>
					</td>
					<td class="value">
						<t:dictSelect typeGroupCode="newstype" field="type" defaultVal="${newsPage.type}" hasLabel="false" ></t:dictSelect>
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							            新闻关键字:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="keywords" name="keywords" datatype="s1-128"
							   value="${newsPage.keywords}">
						<span class="Validform_checktip"></span>
					</td>
					<td align="right">
						<label class="Validform_label">
							是否置顶:
						</label>
					</td>
					<td class="value">
						<t:dictSelect typeGroupCode="newssort" field="sort" defaultVal="${newsPage.sort}" hasLabel="false" ></t:dictSelect>
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right" >
						<label class="Validform_label">
							文章来源:
						</label>
					</td>
					<td class="value"  colspan="3">
						<input class="inputxt" id="author" name="author" datatype="s1-32"
							   value="${newsPage.author}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							内容:
						</label>
					</td>
					<td class="value" colspan="3">
              			<textarea id="editor_id" name="content" rows="0" cols="125" class="inputArea">${newsPage.content}</textarea>
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr style="height:150px">
					<td align="right">
						<label class="Validform_label">
							上传附件:
						</label>
					</td>
					<td class="value" colspan="3" valign="top">
              			<t:upload name="attachment" dialog="false" buttonText="选择文件" uploader="systemController.do?saveAttachment&type=file" extend="*" id="attachment" auto="true" onUploadSuccess="setAttachmentList"
									multi="true" removeCompleted="false" completedCancel="cancelComplete"></t:upload>
						<input type="hidden" id="attachmentList" name="attachmentList"/>
						<input type="hidden" id="delOldUploadList" name="delOldUploadList"/>
						<div id="filediv" style="overflow-y: auto;">
						<c:forEach var="portalAttachment" items="${portalAttachmentList}"  varStatus="row">
							<div id="upload_${portalAttachment.id}" class="uploadify-queue-item">
								<div class="cancel">
									<a href="javascript:$('#attachment').uploadify('cancel', 'upload_${portalAttachment.id}');delOldUpload('${portalAttachment.id}');">X</a>
								</div>
								<span class="fileName"><a href="newsController.do?attachmentDownload&id=${portalAttachment.id}"><c:out value="${portalAttachment.filename}" /> (${portalAttachment.description}KB)</a></span>
								<span class="data"> - 已上传</span>
								<div class="uploadify-progress"></div>
							</div>
    					</c:forEach>
						

						</div>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>