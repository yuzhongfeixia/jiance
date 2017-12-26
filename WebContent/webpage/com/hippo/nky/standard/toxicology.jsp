<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
 <script>
function callbackStructureImg(data){
	var path = data.attributes.attProperties[0].fileRelativePath + data.attributes.attProperties[0].fileName;
	var attElement = $("#structureImage");
	if(attElement.length > 0){
		attElement.attr("src", getActionPath(path));
	}
	$("#structuralPath").val(getActionPath(path));
}
</script>
 </head>
<body>
<div class="row-fluid">
	<div class="span12">  
		<div class="portlet box popupBox_usual">
			<div class="portlet-title">
				<div class="caption">
					<i class="icon-reorder"></i>
					<span class="hidden-480">毒理学信息</span>
				</div>
			</div>
			<div class="portlet-body">
                  <div class="tab-content">
					<div class="tab-pane active" id="tab1">
						<form id="addForm" name="addForm" class="form-horizontal">
							<input id="id" name="id" type="hidden" value="${toxicologyPage.id }">
							<table class="table table-bordered ">
								<tr>
									<td  style="vertical-align:middle;">
										<div class="fileupload fileupload-new" data-provides="fileupload">
											<div>
												<img id="structureImage" name="structureImage" class="attachment-img" 
												src="${toxicologyPage.structuralPath}" title="上传结构式图片"
												action-mode="ajax" action-url="systemController.do?callUpload&type=image&auto=true&callback=callbackStructureImg" action-pop="uploadPop"/>
											</div>
											<input type="hidden" id="structuralPath" name="structuralPath" value="${toxicologyPage.structuralPath}">
											<div class="fileupload-preview fileupload-exists thumbnail" style="max-width: 200px; max-height: 150px; line-height: 20px;"></div>
										</div>
									</td>
									<td colspan="5">
										<div class="table-seach">
											<label class="help-inline seach-label" style="width:80px;">CAS码:</label>
											<div class="seach-element">
												<input type="text" placeholder="" class="m-wrap medium" id="cCode" name="casCode" value="${toxicologyPage.casCode}">
											</div>
										</div>
										
										<div class="table-seach">
											<label class="help-inline seach-label" style="width:80px;">分子式:</label>
											<div class="seach-element">
												<input type="text" class="m-wrap medium" id="formula"
													name="formula" value="${toxicologyPage.formula}" />
											</div>
										</div>
										<div class="table-seach">
											<label class="help-inline seach-label" style="width:80px;">分子量:</label>
											<div class="seach-element">
												<input type="text" class="m-wrap medium" id="formulaWeight"
													name="formulaWeight" value="${toxicologyPage.formulaWeight}" />
											</div>
										</div>
										<div class="table-seach">
											<label class="help-inline seach-label" style="width:80px;">中文通用名:</label>
											<div class="seach-element">
												<input type="text" class="m-wrap medium" id="commonCname"
													name="commonCname" value="${toxicologyPage.commonCname}" />
											</div>
										</div>
										<div class="table-seach">
											<label class="help-inline seach-label" style="width:80px;">英文通用名:</label>
											<div class="seach-element">
												<input type="text" class="m-wrap medium" id="commonEname"
													name="commonEname" value="${toxicologyPage.commonEname}" />
											</div>
										</div>					
									</td>
								</tr>
								<tr>
									<td style="width:207px;"><label class="control-label" style="width:210px;">中文化学名：</label></td>
									<td colspan="2"><input type="text" class="m-wrap medium" id="chemicalCname" name="chemicalCname" value="${toxicologyPage.chemicalCname}" /></td>
									<td style="width:207px;"><label class="control-label" style="width:210px;">英文化学名：</label></td>
									<td colspan="2"><input type="text" class="m-wrap medium" id="chemicalEname" name="chemicalEname" value="${toxicologyPage.chemicalEname}" /></td>
								</tr>
								<tr>
									<td><label class="control-label" style="width:210px;">中文商品名：</label></td>
									<td colspan="2"><input type="text" class="m-wrap medium" id="tradeCname" name="tradeCname" value="${toxicologyPage.tradeCname}" /></td>
									<td><label class="control-label" style="width:210px;">英文商品名：</label></td>
									<td colspan="2"><input type="text" class="m-wrap medium" id="tradeEname" name="tradeEname" value="${toxicologyPage.tradeEname}" /></td>
								</tr>
								<tr>
									<td><label class="control-label" style="width:210px;">LC50（半数致死浓度）：</label></td>
									<td colspan="2"><input type="text" class="m-wrap medium" id="lc50" name="lc50" value="${toxicologyPage.lc50}" /></td>
									<td><label class="control-label" style="width:210px;">LD50（半数致死剂量）：</label></td>
									<td colspan="2"><input type="text" class="m-wrap medium" id="ld50" name="ld50" value="${toxicologyPage.ld50}" /></td>
								</tr>
								<tr>
									<td><label class="control-label" style="width:210px;">NOEL（最大无作用剂量）：</label></td>
									<td colspan="2"><input type="text" class="m-wrap medium" id="noel" name="noel" value="${toxicologyPage.noel}" /></td>
									<td><label class="control-label" style="width:210px;">NOAEL（无可见效应作用水平）：</label></td>
									<td colspan="2"><input type="text" class="m-wrap medium" id="noael" name="noael" value="${toxicologyPage.noael}" /></td>
								</tr>
								<tr>
									<td><label class="control-label" style="width:210px;">LOAEL（最低可见效应作用水平）：</label></td>
									<td colspan="2"><input type="text" class="m-wrap medium" id="loael" name="loael" value="${toxicologyPage.loael}" /></td>
									<td><label class="control-label" style="width:210px;">ADI（每日允许摄入量）：</label></td>
									<td colspan="2"><input type="text" class="m-wrap medium" id="adi" name="adi" value="${toxicologyPage.adi}" /></td>
								</tr>
								<tr>
									<td><label class="control-label" style="width:210px;">BMD（基准剂量）：</label></td>
									<td colspan="2"><input type="text" class="m-wrap medium" id="bmd" name="bmd" value="${toxicologyPage.bmd}" /></td>
									<td><label class="control-label" style="width:210px;">PTMI（暂定每月耐受摄入量）：</label></td>
									<td colspan="2"><input type="text" class="m-wrap medium" id="ptmi" name="ptmi" value="${toxicologyPage.ptmi}" /></td>
								</tr>
								<tr>
									<td><label class="control-label" style="width:210px;">PTWI（暂定每周耐受摄入量）：</label></td>
									<td colspan="2"><input type="text" class="m-wrap medium" id="ptwi" name="ptwi" value="${toxicologyPage.ptwi}" /></td>
									<td><label class="control-label" style="width:210px;">PTDI（暂定每日耐受摄入量）：</label></td>
									<td colspan="2"><input type="text" class="m-wrap medium" id="ptdi" name="ptdi" value="${toxicologyPage.ptdi}" /></td>
								</tr>
								<tr>
									<td><label class="control-label" style="width:210px;">RfD（参考剂量）：</label></td>
									<td colspan="2"><input type="text" class="m-wrap medium" id="rfd" name="rfd" value="${toxicologyPage.rfd}" /></td>
									<td><label class="control-label" style="width:210px;">RfC（参考浓度）：</label></td>
									<td colspan="2"><input type="text" class="m-wrap medium" id="rfc" name="rfc" value="${toxicologyPage.rfc}" /></td>
								</tr>
								<tr>
									<td><label class="control-label" style="width:210px;">MTD（最大耐受剂量）：</label></td>
									<td colspan="2"><input type="text" class="m-wrap medium" id="mtd" name="mtd" value="${toxicologyPage.mtd}" /></td>
									<td><label class="control-label" style="width:210px;">Acute RfD（急性参考剂量）：</label></td>
									<td colspan="2"><input type="text" class="m-wrap medium" id="acuteRfd" name="acuteRfd" value="${toxicologyPage.acuteRfd}" /></td>
								</tr>
																<tr>
									<td><label class="control-label" style="width:210px;">是否致癌：</label></td>
									<td colspan="2"><input type="text" class="m-wrap medium" id="isCarcinogenic" name="isCarcinogenic" value="${toxicologyPage.isCarcinogenic}" /></td>
									<td><label class="control-label" style="width:210px;">是否基因致癌物：</label></td>
									<td colspan="2"><input type="text" class="m-wrap medium" id="isGeneCarcinogen" name="isGeneCarcinogen" value="${toxicologyPage.isGeneCarcinogen}" /></td>
								</tr>
								<tr>
									<td><label class="control-label" style="width:210px;">其他：</label></td>
									<td colspan="4"><input type="text" class="m-wrap medium" id="others" name="others" value="${toxicologyPage.others}" /></td>
								</tr>
								<tr>
									<td><label class="control-label" style="width:210px;">性状描述：</label></td>
									<td colspan="4"><textarea id="description" name="description" class="span8 m-wrap" rows="5" cols="50">${toxicologyPage.description}</textarea></td>
								</tr>
							</table>
							<div class="modal-footer">
								<button type="button" data-dismiss="modal" class="btn popclose">关闭</button>
 								<button id="add_btn" type="button" class="btn popenter" >保存</button>
							</div>
						</form> 
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div id="uploadPop" class="modal hide fade" tabindex="-1" data-width="636"></div>
</body>
</html>