<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/webpage/com/hippo/nky/portal/common/top.jsp"%>
<div class="main">
	<%@include file="/webpage/com/hippo/nky/portal/common/main_left_navigat.jsp"%>
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
			<c:if test="${entity.displaytype gt 1}">
			<c:forEach var="list" items="${childList}" varStatus="status">
				<li class="li3">
					<h5 class="icon_small_greater_than margin_right_black">&nbsp;&nbsp;&nbsp;<c:out value="${list.name}"/></h5>
					<p><c:out value="${list.noHtmlContent}"/></p>
					<div class="item_footer clearfix">
						<c:if test="${list.displaytype gt 0}">
							<a class="more blue icon_small_arrow margin_right_white" href="#" onclick="pageJump('${list.id}')">
						</c:if>
						<c:if test="${list.displaytype eq 0}">
							<a class="more blue icon_small_arrow margin_right_white">
						</c:if>
						了解详情</a>
					</div>
				</li>
			</c:forEach>
			</c:if>
		</ul>
		<c:if test="${entity.id  eq '4028e41540523b3a0140525310290033' }">
			<div class="RAMAbutton">&nbsp;</div>
		</c:if>			          						
	</div>
	
</div>				
<%@include file="/webpage/com/hippo/nky/portal/common/footer.jsp"%>
      