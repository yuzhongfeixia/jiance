<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/webpage/com/hippo/nky/portal/common/top.jsp"%>
<script src="plug-in/pagination/jquery.pagination.js" type="text/javascript"></script>
<script src="plug-in/pagination/members.js" type="text/javascript"></script>
<link href="plug-in/pagination/pagination.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
 
function getTestData(newPage, jq){
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
			versionid : '${agrEntity.versionid}',
			gems : '${agrEntity.gems}',
			ename : '${agrEntity.ename}',
			cname : '${agrEntity.cname}',
			code : '${agrEntity.code}',
			calias : '${agrEntity.calias}',
			foodex : '${agrEntity.foodex}'
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
						"<td height=\"29\" class=\"font_grey\" style=\"text-decoration: underline;\"><a href=\"#\" onclick=\"toStandardDetail('"+item.ID+"','"+lmid+"')\">" + toString(item.CODE) + "</a></td>"+
						"<td class=\"font_grey\">" + toString(item.GEMS) + "</td>"+
						"<td class=\"font_grey\">" + toString(item.FOODEX) + "</td>"+
						"<td class=\"font_grey\">" + toString(item.CNAME) + "</td>"+
						"<td class=\"font_grey\">" + toString(item.ENAME) + "</td>"+
						"<td class=\"font_grey\">" + toString(item.CALIAS) + "</td>"+
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
	<div class="agr_middle_ag">
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
				<td height="38" class="font_green">标准版本:</td>
				<td>
					<select name="versionid" id="versionid" datatype="*"  type="text" style="border: 1px solid #08822a;width:150px;">
					<c:forEach var="list" items="${versionList}" varStatus="status">
						<option value="${list.id}" 
						<c:if test="${list.id eq agrEntity.versionid}">selected="selected"</c:if>>
							${list.cname}
						</option>
					</c:forEach>
					</select>
				</td>	
			</tr>
			<tr>
				<td height="38" class="font_green">编&nbsp;&nbsp;&nbsp;&nbsp;码:</td>
				<td><input name="code" id="code" value="${agrEntity.code}" type="text" style="border: 1px solid #08822a"></td>
				<td height="38" class="font_green">G&nbsp;E&nbsp;M&nbsp;S:</td>
				<td><input name="gems" id="gems" value="${agrEntity.gems}" type="text" style="border: 1px solid #08822a"></td>
				<td height="38" class="font_green">FOODEX_2:</td>
				<td><input name="foodex" id="foodex" value="${agrEntity.foodex}" type="text" style="border: 1px solid #08822a"></td>
				<td rowspan="2" valign="middle"><input type="image" src="Portal/images/search1.jpg" width="77" height="60"></td>
			</tr>
			<tr>
				
				<td height="38" class="font_green">中文名称:</td>
				<td><input name="cname" id="cname" value="${agrEntity.cname}" type="text" style="border: 1px solid #08822a"></td>
				<td height="38" class="font_green">英文名称:</td>
				<td><input  name="ename" id="ename" value="${agrEntity.ename}" type="text" style="border: 1px solid #08822a"></td>
				<td height="38" class="font_green">别&nbsp;&nbsp;&nbsp;&nbsp;名:</td>
				<td><input name="calias" id="calias" value="${agrEntity.calias}" type="text" style="border: 1px solid #08822a"></td>
			</tr>

			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			</form>
		</table>
	</div>
	<div class="main_bottom_ag">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr bgcolor="#08822b">
				<td height="29" class="font_white">编码</td>
				<td class="font_white">GEMS</td>
				<td class="font_white">FOODEX_2</td>
				<td class="font_white">中文名称</td>
				<td class="font_white">英文名称</td>
				<td class="font_white">别名</td>
			</tr>
			<tbody id="tb" align="right"></tbody>
		</table>
	</div>
	<div class="yellow">
		<div id="Pagination" class="pagination" style="text-size:12;"></div>
	</div>	
</div>
<%@include file="/webpage/com/hippo/nky/portal/common/footer.jsp"%>