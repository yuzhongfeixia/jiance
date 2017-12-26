<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
			<div class="header_container">
				<div class="header clearfix">
						<div class="header_left">
						<a href="frontPortalIntroductionsController.do?frontPortalIntroductions" title="GYMBASE">
							<img src="Portal/images/logo.jpg" alt="logo" width="244" height="84">
						</a>
						</div>
					<ul id="topmenu" class="sf-menu header_right">
						<li class="submenu submenu2">
							<a href="frontPortalIntroductionsController.do?frontPortalIntroductions">
								<hgroup>
									<h2>首页</h2>
									<h4>Home</h4>
								</hgroup>
							</a>
						</li>

<c:set var="oldLevel" value="-11"/>
<c:forEach var="menuObj" items="${menuList}" varStatus="status">
	<c:if test="${menuObj.introductionleavel - oldLevel == 1 }">	<ul>	</c:if>
	<c:if test="${menuObj.introductionleavel  ==  oldLevel}">	</li>	</c:if>
	<c:if test="${menuObj.introductionleavel - oldLevel  == -1 }">	</li></ul></li>	</c:if>
	<c:if test="${menuObj.introductionleavel - oldLevel  == -2 }">	</li></ul></li></ul></li>	</c:if>
	<c:if test="${menuObj.introductionleavel ==  0}">
		<c:set var="oldLevel" value="${menuObj.introductionleavel}" />
		<c:if test="${menuObj.id eq selectedId}">
			<li class="submenu selected">
		</c:if>
		<c:if test="${menuObj.id ne selectedId}">
			<li class="submenu">
		</c:if>
			<c:if test="${menuObj.displaytype ne 0}">
				<a href="#" onclick="pageJump('${menuObj.id}')">
			</c:if>
			<c:if test="${menuObj.displaytype eq 0}">
			<a>
			</c:if>
				<hgroup>
					<h2>${menuObj.name}</h2>
					<c:if test="${menuObj.name eq '风险监测'}"> 
						<h4>Risk monitoring</h4>
					</c:if>
					<c:if test="${menuObj.name eq '风险评估'}"> 
						<h4>Risk assessment</h4>
					</c:if>
					<c:if test="${menuObj.name eq '风险预警'}"> 
						<h4>Risk Alert </h4>
					</c:if>
					<c:if test="${menuObj.name eq '标准与指南'}"> 
						<h4>Standard&Guide </h4>
					</c:if>
					<c:if test="${menuObj.name eq '政策与法规'}"> 
						<h4>Policy&Rule </h4>
					</c:if>
					<c:if test="${menuObj.name eq '关于平台'}"> 
						<h4>About us </h4>
					</c:if>
				</hgroup>
			</a>
	</c:if>
	<c:if test="${menuObj.introductionleavel gt  0}">
		<c:set var="oldLevel" value="${menuObj.introductionleavel}" />
		<li>
		<c:if test="${menuObj.displaytype ne 0}">
		<a href="#" onclick="pageJump('${menuObj.id}')">
		</c:if>
		<c:if test="${menuObj.displaytype eq 0}">
		<li><a>
		</c:if>
		${menuObj.name}</a>
	</c:if>
</c:forEach>
<c:if test="${menuObj.introductionleavel - oldLevel  == -1 }">	</li></ul></li>	</c:if>
<c:if test="${menuObj.introductionleavel - oldLevel  == -2 }">	</li></ul></li></ul></li>	</c:if>

					</ul>
                </div>
            </div>