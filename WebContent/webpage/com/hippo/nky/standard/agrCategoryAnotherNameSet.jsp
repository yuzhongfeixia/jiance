<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script type="text/javascript">

$('#addAgrCategoryAnotherNameBtn').bind('click', function(){
	 var tr =  $("#add_agrCategory_anotherName_table_template tr").clone();
	 $("#add_agrCategory_anotherName_table").append(tr);
});
function delAgrCategoryAnotherNameBtn(obj){
 	$(obj).closest("tr").remove();
}
function agrcategoryAnotherNameSave(){
	var caliasArr = "";
	var result = true;
	$("#add_agrCategory_anotherName_table tr").find("input[name='calias']").each(function(index){
		var thisname = $(this).val();
		if(thisname == ""){
			result = false;
			modalTips("别名不能为空！");
			return false;
		}else if(! /^[\u4E00-\u9FA5\uf900-\ufa2d\w\.\s\-]+$/.test(thisname) ){
			result = false;
			modalTips("别名【"+thisname+"】不能含有特殊字符！");
			return false;
		}
		caliasArr += thisname +",";
	});
	if(result){
		caliasArr=caliasArr.replace(/,$/gi,"");
		$.ajax({
		       type:"POST",
		       url:"agrCategoryController.do?save&rand="+Math.random(),
		       data:{
		    	   id : "${id}",
		    	   calias : caliasArr
		       },
		       success:function(data){
		    	   var d = $.parseJSON(data);
				   if (d.success) {
					   $("#arCategoryAnotherName").modal('hide');
					   $("#calias").val(caliasArr);
					   modalTips(d.msg);
				   }else {
					   modalTips(d.msg);
				   }
		        }
	 }); 
	}else{
		return false;
	}
}
</script>
<div class="portlet box popupBox_usual">
	<div class="portlet-title">
		<div class="caption"><i class="icon-reorder"></i>农产品别名列表</div>
	</div>
	<div class="portlet-body"style="OVERFLOW-Y: auto;  OVERFLOW-X: hidden;height: 344px;">
		<div>
			<a id="addAgrCategoryAnotherNameBtn" href="#"><i class="icon-plus"></i>添加</a> 
		</div> 
		<table class="table  table-bordered " id="agrCategory_anotherName_table">
			<tr>
				<th class="hidden-480">农产品别名</th>
				<th class="hidden-480"></th>
			</tr>
			<tbody id="add_agrCategory_anotherName_table">	
			<c:if test="${fn:length(caliasArr)  == 0 }">
				<tr>
					<td>
						<input name="calias" type="text">
					</td>
					<td width="70px;"><a class="btn mini yellow" onclick="delAgrCategoryAnotherNameBtn(this)"><i class="icon-trash"></i>删除</a></td>
				</tr>
			</c:if>	
			<c:if test="${fn:length(caliasArr)  > 0 }">
				<c:forEach items="${caliasArr}" var="calias" varStatus="stuts">
					<tr>
						<td>
							<input name="calias" type="text" value="${calias}">
						</td>
						<td width="70px;"><a class="btn mini yellow" onclick="delAgrCategoryAnotherNameBtn(this)"><i class="icon-trash"></i>删除</a></td>
					</tr>
				</c:forEach>
			</c:if>	
			</tbody>
		</table>
	</div>
	<div class="modal-footer">
		<button type="button" data-dismiss="modal" class="btn popclose">关闭</button>
		<button id="add_btn" type="button" class="btn popenter" onclick="agrcategoryAnotherNameSave()">保存</button>
	</div>
</div>
<table style="display:none">
<tbody id="add_agrCategory_anotherName_table_template">
	<tr>
		<td>
			<input name="calias" type="text">
		</td>
		<td width="70px;"><a class="btn mini yellow" onclick="delAgrCategoryAnotherNameBtn(this)"><i class="icon-trash"></i>删除</a></td>
	</tr>
 </tbody>
 </table>