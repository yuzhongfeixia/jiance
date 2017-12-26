<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/webpage/com/hippo/nky/portal/common/top.jsp"%>
<script src="plug-in/pagination/jquery.pagination.js" type="text/javascript"></script>
<link href="plug-in/pagination/pagination.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
var page = '${page}';
function getTestData(newPage,jq){
	if(newPage == page){
		//return false;
	}else{
		window.location.href="frontPortalIntroductionsController.do?prepareData&id="+lmid+"&page="+newPage;
	}
}
//绑定翻页条
$(document).ready(function() {
	 var optInit = {
		current_page : page,
		items_per_page:5,
		num_edge_entries:1,
		prev_text:'<',
		next_text:'>',
		callback:getTestData
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
				<p>${entity.content}</p>
			</li>
			</c:if>
			<c:forEach var="list" items="${childList}" varStatus="status">
				<li class="li2_1">
					<div class="title ">
						<h3>${list.title}</h3>
						<p>
							<span class="value"><fmt:formatDate pattern="yyyy.MM" value="${list.createdate}" type="both"/></span><span class="number"><fmt:formatDate pattern="dd" value="${list.createdate}" type="both"/></span>
						</p>
					</div>
					<p>${list.noHtmlContent}</p>
					<div class="show_all clearfix ">
						<a class="more_p" href="#" onclick="toANews('${list.id}','${entity.id}')"
							title="Show all"> 阅读全文>> </a>
					</div>
				</li>	
			</c:forEach>
			<li class="li5_1">
				<div class="yellow">
					<div id="Pagination" class="pagination"></div>
				</div> 
            </li>
		</ul>
	</div>
</div>
<%@include file="/webpage/com/hippo/nky/portal/common/footer.jsp"%>