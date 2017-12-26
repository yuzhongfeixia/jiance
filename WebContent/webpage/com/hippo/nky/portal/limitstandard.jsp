<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/webpage/com/hippo/nky/portal/common/top.jsp"%>
<script src="plug-in/pagination/jquery.pagination.js" type="text/javascript"></script>
<script src="plug-in/pagination/members.js" type="text/javascript"></script>
<link href="plug-in/pagination/pagination.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
function getTestData(newPage){
	newPage = newPage + 1;
	var url = "frontPortalIntroductionsController.do?splitPage";
	$.ajax({
		async : false,
		type : 'POST',
		url : url,
		data : {
			id : lmid,
			page : newPage,
			rows : 30,
			versionid : '${limitEntity.versionid}',
			cas : '${limitEntity.cas}',
			pollnameZh : '${limitEntity.pollnameZh}',
			agrname : '${limitEntity.agrname}',
			agrcategory : '${limitEntity.agrcategory}'
		},
		error : function(data) {
				alert(data.msg);
				},
		success : function(data) {
				var jsonData = $.parseJSON(data);
				var s ="";
				if (jsonData.success) {
					$.each(jsonData.attributes.resultMap, function(index, item){
						if( index%2 == 0){
							s += "<tr bgcolor=\"#e9f4ec\">";	
						}else{
							s += "<tr>";
						}
						s +=
						"<td height=\"29\" class=\"font_grey\">" + toString(item.CAS) + "</td>"+
						"<td class=\"font_grey\">" + toString(item.POLLNAMEZH) + "</td>"+
						"<td class=\"font_grey\">" + toString(item.AGRCATEGORY) + "</td>"+
						"<td class=\"font_grey\">" + toString(item.AGRNAME) + "</td>"+
						"<td class=\"font_grey\">" + toString(item.MRL)+"</td>"+
						"<td class=\"font_grey\" style=\"text-decoration: underline;\"><a href=\"#\" onclick=\"toStandardDetail('"+item.VERSIONID+"','"+lmid+"')\">" + toString(item.STANDARDCODE) + "</a></td>"+
						"</tr>";
					});
					$("#tb").html(s.toString());
				}
			}
		});
}
//绑定翻页条
$(document).ready(function() {
	var optInit = {
			callback: getTestData,
			items_per_page:30,
			num_display_entries:5,
			num_edge_entries:1,
			prev_text:'<',
			next_text:'>'
	};
	$("#Pagination").pagination(
			'${totalrecords}', 
			optInit
	);
});
</script>
<div class="main">
	<div class="main_middle_ag">
		<div class="left">
			<h7 class="box_header">${entity.name}</h7>
		</div>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<form method="POST" action="frontPortalIntroductionsController.do?prepareData&id=${entity.id}" name="form1">
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td height="38" class="font_green">限量标准:</td>
				<td><select name="versionid" id="versionid" datatype="*"  type="text" style="border: 1px solid #08822a;width:150px;">
				<c:forEach var="list" items="${versionList}" varStatus="status">
						<option value="${list.id}" 
						<c:if test="${list.id eq limitEntity.versionid}">selected="selected"</c:if>>
							${list.standardCode}${list.nameZh}
						</option>
				</c:forEach>
					</select>
					</td>
				<!-- <td><input type="text" style="border: 1px solid #08822a"></td> -->
				<td class="font_green">污染物CAS码:</td>
				<td><input  name="cas" id="cas" value="${limitEntity.cas}" type="text" style="border: 1px solid #08822a"></td>
				<td class="font_green">污染物名称:</td>
				<td><input name="pollnameZh" id="pollnameZh" value="${limitEntity.pollnameZh}" type="text" style="border: 1px solid #08822a"></td>
				<td rowspan="2" valign="middle"><input type="image" src="Portal/images/search1.jpg" width="77" height="60"></td>
			</tr>
			<tr>
				<td class="font_green">农产品类别:</td>
				<td><input name="agrcategory" id="agrcategory" value="${limitEntity.agrcategory}" type="text" style="border: 1px solid #08822a"></td>
				<td class="font_green">农产品名称:</td>
				<td><input name="agrname" id="agrname"  value="${limitEntity.agrname}" type="text" style="border: 1px solid #08822a"></td>
				<td></td><td></td>
			</tr>
			</form>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
		</table>
	</div>
	<div class="main_bottom_ag">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr bgcolor="#08822b">
				<td height="29" class="font_white">污染物CAS编码</td>
				<td class="font_white">污染物名称</td>
				<td class="font_white">农产品类别</td>
				<td class="font_white">农产品名称</td>
				<td class="font_white">限量(mg/kg)</td>
				<td class="font_white">标准号</td>
			</tr>
			<tbody id="tb" align="right"></tbody>
		</table>
	</div>
	<div class="yellow">
		<div id="Pagination" class="pagination" style="text-size:12;"></div>
	</div>
</div>
<%@include file="/webpage/com/hippo/nky/portal/common/footer.jsp"%>