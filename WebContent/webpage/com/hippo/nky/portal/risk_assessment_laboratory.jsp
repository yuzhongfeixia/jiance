<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/webpage/com/hippo/nky/portal/common/top.jsp"%>
<script src="plug-in/pagination/jquery.pagination.js" type="text/javascript"></script>
<link href="plug-in/pagination/pagination.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
var page = '${page}';
function getTestData(newPage, jq){
	newPage = newPage + 1;
	var url = "frontPortalIntroductionsController.do?splitPage";
	$.ajax({
		async : false,
		type : 'POST',
		url : url,
		data : {
			id : lmid,
			page : newPage
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
						"<td height=\"29\" class=\"font_grey\">" +  toString(item.CODE) + "</td>"+
						"<td class=\"font_grey\">" + toString(item.OGRNAME) + "</td>"+
						"<td class=\"font_grey\">" + toString(item.PROPERTY) + "</td>"+
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
		items_per_page:10,
		num_display_entries:5,
		num_edge_entries:1,
		prev_text:'<',
		next_text:'>'
	 };
     $("#Pagination").pagination('${totalrecords}', optInit);
});
</script>
<div class="main">
	<%@include
		file="/webpage/com/hippo/nky/portal/common/main_left_navigat.jsp"%>
	<div class="main_right">
		<ul>
			<c:if test="${entity.displaytype == 1 or entity.displaytype == 3}">
			<li class="li1">
				<div class="title">
					<h3>${entity.name}</h3>
					<!-- 
					<p><span class="value"><fmt:formatDate pattern="yyyy.MM" value="${entity.createdate}" type="both"/></span><span class="number"><fmt:formatDate pattern="dd" value="${entity.createdate}" type="both"/></span></p>
					 -->
				</div>
			</li>
			</c:if>	
			<li class="li5">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr bgcolor="#08822b">
					<td height="29" class="font_white">代码</td>
					<td class="font_white">机构名称</td>
					<td class="font_white">性质</td>
				</tr>

				<tbody id="tb" align="right"></tbody>
			</table>
			</li>
			<li class="li5">
           		<div class="yellow">
					<div id="Pagination" class="pagination"></div>
            	</div> 
            </li>
		</ul>
	</div>
</div>
<%@include file="/webpage/com/hippo/nky/portal/common/footer.jsp"%>