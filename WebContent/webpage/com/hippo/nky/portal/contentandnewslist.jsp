<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/webpage/com/hippo/nky/portal/common/top.jsp"%>
<div class="main">
	<%@include
		file="/webpage/com/hippo/nky/portal/common/main_left_navigat.jsp"%>
	<div class="main_right">
		<ul>
			<li class="li1">
				<div class="title">
					<h3>${entity.name}</h3>
					<p><span class="value"><fmt:formatDate pattern="yyyy.MM" value="${entity.createdate}" type="both"/></span><span class="number"><fmt:formatDate pattern="dd" value="${entity.createdate}" type="both"/></span></p>
				</div>
				<p>${entity.content}</p>
			</li>

			<c:forEach var="list" items="${childList}" varStatus="status">
				<li class="li">
					<div class="title ">
						<h3>${list.title}</h3>
						<p>
							<span class="value"><fmt:formatDate pattern="yyyy.MM" value="${list.createdate}" type="both"/></span><span class="number"><fmt:formatDate pattern="dd" value="${list.createdate}" type="both"/></span>
						</p>
					</div>
					<p>${list.content}</p>
					<div class="show_all clearfix ">
						<a class="more_p" href="#" onclick="toANews('${list.id}','${list.type}')"
							title="Show all"> 阅读全文>> </a>
					</div>
			</c:forEach>

		</ul>
	</div>
</div>
<%@include file="/webpage/com/hippo/nky/portal/common/footer.jsp"%>