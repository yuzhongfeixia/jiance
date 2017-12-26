<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<!-- BEGIN PAGE LEVEL STYLES -->
<link href="assets/plugins/jquery-file-upload/css/jquery.fileupload-ui.css" rel="stylesheet" />
<link rel="stylesheet" href="plug-in/jcrop/css/jquery.Jcrop.css" type="text/css"></link>
<!-- END PAGE LEVEL STYLES -->

<!-- BEGIN PAGE LEVEL PLUGINS -->
<!-- BEGIN:File Upload Plugin JS files-->
<!-- <script src="assets/plugins/jquery-file-upload/js/vendor/jquery.ui.widget.js"></script> -->
<!-- The Templates plugin is included to render the upload/download listings -->
<script src="assets/plugins/jquery-file-upload/js/vendor/tmpl.min.js"></script>
<!-- The Load Image plugin is included for the preview images and image resizing functionality -->
<script src="assets/plugins/jquery-file-upload/js/vendor/load-image.min.js"></script>
<!-- The Canvas to Blob plugin is included for image resizing functionality -->
<script src="assets/plugins/jquery-file-upload/js/vendor/canvas-to-blob.min.js"></script>
<!-- The Iframe Transport is required for browsers without support for XHR file uploads -->
<script src="assets/plugins/jquery-file-upload/js/jquery.iframe-transport.js"></script>
<!-- The basic File Upload plugin -->
<!-- <script src="assets/plugins/jquery-file-upload/js/jquery.fileupload.js"></script> -->
<!-- The File Upload file processing plugin -->
<!-- <script src="assets/plugins/jquery-file-upload/js/jquery.fileupload-fp.js"></script> -->
<!-- The File Upload user interface plugin -->
<script src="assets/plugins/jquery-file-upload/js/jquery.fileupload-ui.js"></script>
<!-- The XDomainRequest Transport is included for cross-domain file deletion for IE8+ -->
<!--[if gte IE 8]><script src="assets/plugins/jquery-file-upload/js/cors/jquery.xdr-transport.js"></script><![endif]-->
<!-- <script type="text/javascript" src="plug-in/jcrop/js/jquery.Jcrop.js"></script> -->
<!-- END:File Upload Plugin JS files-->
<style>
.attachmentTr .viewTd{
	padding: 5px;
	width: 50%;
}
#imageViewBorder{
	width: 300px;
	height: 300px;
	margin: 0px auto;
	border: 1px solid #c8c8c8;
	padding: 0px;
}
.attachmentTr .previewTd{
	width: 50%;
	padding: 5px;
	text-align: center;
	color: rgb(184, 75, 48);
}
#preview{
	width: 200px;
	height: 200px;
	border: solid 1px;
	border: 1px solid #c8c8c8;
	margin: 0px auto;
}
</style>
<script>
var attachmentType = "${uploadAttributes.attachmentType}";
var isImport = ${uploadAttributes.isImport};
var auto = "${uploadAttributes.auto}";
var multi = "${uploadAttributes.multi}";
var api;
function cutterImageInit(){
	api = $.Jcrop('#attachmentImage',{
		onChange:   showCoords,
		onSelect:   showCoords,
		onRelease:  clearCoords,
		boxWidth: "${uploadAttributes.defaultW}",
		boxHeight: "${uploadAttributes.defaultH}",
		minSize: ["${uploadAttributes.minW}", "${uploadAttributes.minH}"],
		maxSize: ["${uploadAttributes.maxW}", "${uploadAttributes.maxH}"]
	});
}
function setAttachmentPath(e, data){
	if('${uploadAttributes.attachmentType}' == 'image'){
		// 类别为图片时的回调操作
		var path = "/" + data.result.attributes.files[0].webkitRelativePath;
		$("#attachmentPath").val(path);
		cutterImageInit();
		api.setImage(getActionPath(path));
		api.setSelect([0, 0, "${uploadAttributes.minW}", "${uploadAttributes.minH}"]);
		var previewCss = {};
		previewCss["background"] = "url(" + getActionPath(path) + ")";
		$("#preview").css(previewCss);
	} else {
		// 类别为文件时的回调操作
		var muliti = "${uploadAttributes.multi}";
		if(muliti){
			var path = $("#attachmentPath").val() + ";/" + data.result.attributes.files[0].webkitRelativePath;
			$("#attachmentPath").val(path);
		} else {
			$("#attachmentPath").val("/" + data.result.attributes.files[0].webkitRelativePath);
		}
	}
}

function showCoords(c){
	$('#txtX1').val(c.x);
	$('#txtY1').val(c.y);
	$('#txtX2').val(c.x2);
	$('#txtY2').val(c.y2);
	$('#txtW').val(c.w);
	$('#txtH').val(c.h); 

	$('#preview').css({"backgroundPosition":"-"+c.x+"px -"+c.y+"px","width":c.w,"height":c.h}); 
}

function clearCoords(){
	$('#coords input').val('');
	$('#h').css({color:'red'});
	window.setTimeout(function(){
		$('#h').css({color:'inherit'});
	},500);
}
function deleteComplete(filePath){
	var path = ";/" + filePath;
	var attPath = $("#attachmentPath").val();
	$("#attachmentPath").val(attPath.replace(path, ""));
}
function importFile(){
	var versionId = "${versionId}";
	var categoryId = "${categoryId}";
	var url = "systemController.do?importExcel";
	if("" != versionId){
		url += "&versionId=" + versionId;
	}
	if("" != categoryId){
		url += "&categoryId=" + categoryId;
	}
	var attachmentPath = $("#attachmentPath").val();
	var importType = $("#importType").val();
	var returnData = null;
	$.ajax({
		type : 'POST',
		url : url,
		async: false,
		data : {'attachmentPath':attachmentPath,'importType':importType},
		success : function(data) {
			var dataJson = eval('(' + data + ')');
			if(dataJson.success){
				returnData = dataJson;
				modalTips(dataJson.msg);
			} else {
				modalAlert(dataJson.msg);
			}
		},
		error:function(){
			modalAlert("导入数据失败，请重试!");
		}
	});
	return returnData;
}
</script>
<div class="row-fluid">
	<div class="span12">
		<div class="portlet box popupBox_usual">
			<div class="portlet-title">
				<div class="caption"><i class="icon-globe"></i>${uploadAttributes.title}</div>
			</div>
			<div class="portlet-body">
				<!-- The file upload form used as target for the file upload widget -->
				<form id="fileupload"  method="POST" enctype="multipart/form-data">
					<input type="hidden" id="attachmentPath" name="attachmentPath"/>
					<!-- The fileupload-buttonbar contains buttons to add/delete files and start/cancel the upload -->
					<div class="row-fluid fileupload-buttonbar">
						<div class="row-fluid" style="line-height: 30px;">
							<!-- The fileinput-button span is used to style the file input field as button -->
							<span class="btn green fileinput-button">
							<i class="icon-plus icon-white"></i>
							<span>${uploadAttributes.title}</span>
							<c:if test="${uploadAttributes.multi}">
								<input type="file" name="files[]" multiple>
							</c:if>
							<c:if test="${! uploadAttributes.multi}">
								<input type="file" name="file">
							</c:if>
							</span>
							<c:if test="${uploadAttributes.attachmentType eq 'image'}">
								<span style="vertical-align: middle;">支持jpg、jpeg、png、gif、bmp、ico、tif格式的图片</span>
							</c:if>
							<c:if test="${uploadAttributes.attachmentType eq 'file'}">
								<c:if test="${uploadAttributes.isImport}">
									<t:dictSelect id="importType" field="importType" typeGroupCode="importType" hasLabel="false" defaultVal="${uploadAttributes.importType}" extend="{disabled:{value:${uploadAttributes.disabled}}}"></t:dictSelect>
									<span style="vertical-align: middle;">支持xlxs、xls格式的文件</span>
								</c:if>
								<c:if test="${!uploadAttributes.isImport}">
									<span style="vertical-align: middle;">不支持exe、dll格式的文件</span>
								</c:if>
							</c:if>
						</div>
						<!-- The global progress information -->
<!-- 						<div class="span5 fileupload-progress fade"> -->
<!-- 							The global progress bar -->
<!-- 							<div class="progress progress-success progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100"> -->
<!-- 								<div class="bar" style="width:0%;"></div> -->
<!-- 							</div> -->
<!-- 							The extended global progress information -->
<!-- 							<div class="progress-extended">&nbsp;</div> -->
<!-- 						</div> -->
					</div>
					<!-- The loading indicator is shown during file processing -->
					<div class="fileupload-loading"></div>
					<!-- The table listing the files available for upload/download -->
					<div>
						<c:if test="${uploadAttributes.attachmentType eq 'image'}">
							<table role="presentation" class="table table-striped table-bordered table-hover">
								<tbody class="files" data-toggle="modal-gallery" data-target="#modal-gallery">
								</tbody>
							</table>
						</c:if>
						<c:if test="${uploadAttributes.attachmentType eq 'file'}">
							<table role="presentation" class="table table-striped">
								<tbody class="files" data-toggle="modal-gallery" data-target="#modal-gallery"></tbody>
							</table>
						</c:if>
					</div>
				</form>
				<div class="modal-footer">
					<button type="button" data-dismiss="modal" class="btn popclose">关闭</button>
					<button type="button" action-mode="ajax" action-operation="popsave" action-form="#fileupload" action-url="systemController.do?saveUpload&type=${uploadAttributes.attachmentType}" action-after="${uploadAttributes.callback}" class="btn popenter">确定</button>
				</div>
			</div>
		</div>
	</div>
</div>
<script id="template-upload" type="text/x-tmpl">
	{% for (var i=0, file; file=o.files[i]; i++) { %}
	    <tr class="template-upload fade">
	        <%--<td class="preview"><span class="fade"></span></td>--%>
	        <td class="name" ><span>{%=file.name%}</span></td>
	        <td class="size"><span>{%=o.formatFileSize(file.size)%}</span></td>
	        {% if (file.error) { %}
	            <td class="error" colspan="2"><span class="label label-important">错误</span> {%=file.error%}</td>
	        {% } else if (o.files.valid && !i) { %}
	            <td>
	                <div class="progress progress-success progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0"><div class="bar" style="width:0%;"></div></div>
	            </td>
	            <td class="start">{% if (!o.options.autoUpload) { %}
	                <button class="btn mini">
	                    <i class="icon-upload icon-white"></i>
	                    <span>上传</span>
	                </button>
	            {% } %}</td>
	        {% } else { %}
	            <td colspan="2"></td>
	        {% } %}
	        <td class="cancel">{% if (!i) { %}
	            <%--<button class="btn mini red">
	                <i class="icon-ban-circle icon-white"></i>
	                <span>取消</span>
	            </button> --%>
	        {% } %}</td>
	    </tr>
	{% } %}
</script>
<!-- The template to display files available for download -->
<script id="template-download" type="text/x-tmpl">
	{% for (var i=0, file; file=o.files[i]; i++) { %}
	    <tr class="template-download fade">
	        {% if (file.error) { %}
	            <td></td>
	            <td class="name"><span>{%=file.name%}</span></td>
	            <td class="size"><span>{%=o.formatFileSize(file.size)%}</span></td>
	            <td class="error" colspan="2"><span class="label label-important">错误</span> {%=file.error%}</td>
	        {% } else { %}
	            <%--<td class="preview">
	            {% if (file.thumbnail_url) { %}
	                <a class="fancybox-button" data-rel="fancybox-button" href="{%=file.url%}" title="{%=file.name%}">
	                <img src="{%=file.thumbnail_url%}">
	                </a>
	            {% } %}</td>--%>
	            <td class="name">
	                <a href="{%=file.webkitRelativePath%}" title="{%=file.name%}" data-gallery="{%=file.thumbnail_url&&'gallery'%}" data-download="{%=file.webkitRelativePath%}">{%=file.name%}</a>
	            </td>
	            <td class="size"><span>{%=o.formatFileSize(file.size)%}</span></td>
	            <td colspan="2"></td>
	        {% } %}
	        <td class="delete">
	            <button class="btn mini red" data-type="{%=file.delete_type%}" data-url="{%=file.delete_url%}"{% if (file.delete_with_credentials) { %} data-xhr-fields='{"withCredentials":true}'{% } %}>
	                <i class="icon-trash icon-white"></i>
	                <span>删除</span>
	            </button>
	        </td>
	    </tr>
	{% } %}
</script>
<script id="template-image" type="text/x-tmpl">
	<tr class="template-download fade">
		<td width="100%">
			<div class="progress progress-success progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0" style="width:100% !important;">
				<div class="bar" style="width:0%;"></div>
			</div>
		</td>
	</tr>
</script>
<script id="template-cropImage" type="text/x-tmpl">
	{% for (var i=0, file; file=o.files[i]; i++) { %}
		<tr class="template-download fade attachmentTr">
			<td class="viewTd">
				<div id="imageViewBorder">
					<img id="attachmentImage" src="{%=file.webkitRelativePath%}" width='300px' height='300px'/>
				</div>
				<input type="hidden" id="txtX1" name="txtX1">
				<input type="hidden" id="txtY1" name="txtY1">
				<input type="hidden" id="txtX2" name="txtX2">
				<input type="hidden" id="txtY2" name="txtY2">
				<input type="hidden" id="txtW" name="txtW">
				<input type="hidden" id="txtH" name="txtH">
			</td>
			<td class="previewTd" style="">
				<div style="">
					<span>请注意预览图是否清晰</span>
					<div id="preview"></div>
				</div>
			</td>
	    </tr>
	{% } %}
</script>

<script type="text/javascript">
//Initialize the jQuery File Upload widget:
		var initParams = {};
		initParams["url"] = "systemController.do?saveAttachment&type=${uploadAttributes.attachmentType}&rename=${uploadAttributes.rename}";
		if(attachmentType.isNotEmpty() && "image" == attachmentType){
			initParams["acceptFileTypes"] = /(\.|\/)(jpe?g|png|gif|bmp|ico|tif)$/i;
			initParams["singleFileUploads"] = true;
			initParams["uploadTemplateId"] = "template-image";
			initParams["downloadTemplateId"] = "template-cropImage";
			initParams["echoRefresh"] = true;
		}
		if(attachmentType.isNotEmpty() && "file" == attachmentType){
			if(isImport){
				initParams["acceptFileTypes"] = /(\.|\/)(xls|xlsx)$/i;
			} else {
				initParams["acceptFileTypes"] = /\.(?!(exe|dll))/i;
			}
		}
		
		if(auto.isNotEmpty() && "true" == auto){
			initParams["autoUpload"] = true;
		}
		
	$('#fileupload').fileupload(initParams);
	$('#fileupload').unbind('fileuploadcompleted').bind('fileuploadcompleted', function (e, data) {
		$('#fileupload').closest(".modal.hide.fade").modal('layout');
		setTimeout(function(){setAttachmentPath(e, data)}, 300);
	});
	$('#fileupload').unbind('fileuploaddestroyed').bind('fileuploaddestroyed', function (e, data) {
		deleteComplete($(data.context).find(".name a").attr("href"));
	});
</script>