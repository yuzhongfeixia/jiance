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
					<h3>${newsEntity.title}</h3>
					<p><span class="value"><fmt:formatDate pattern="yyyy.MM" value="${newsEntity.createdate}" type="both"/></span><span class="number"><fmt:formatDate pattern="dd" value="${newsEntity.createdate}" type="both"/></span></p>
				</div>
				<c:forEach var="portalAttachment" items="${portalAttachmentList}"  varStatus="row">
				<div class="attach">
					<a class="pdf" style="color:#000;" href="frontPortalIntroductionsController.do?attachmentDownload&id=${portalAttachment.id}">${portalAttachment.filename}</a>
				        <img class="pdfIco" src="Portal/images/ico_pdf.gif" border="0">
					<span class="size">(${portalAttachment.description}KB)</span>
				</div>
				</c:forEach>
				<p>${newsEntity.content}</p>
			</li>
		</ul>

		
	</div>
</div>
<%@include file="/webpage/com/hippo/nky/portal/common/footer.jsp"%>