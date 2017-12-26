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
			<li class="li2"><img
				src="Portal/images/samples/629x258/image_01.jpg" alt="" /></li>
		</ul>
	</div>
</div>
<%@include file="/webpage/com/hippo/nky/portal/common/footer.jsp"%>