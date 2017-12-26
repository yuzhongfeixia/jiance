<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<div class="main_left2">
		<div id="accordion">
<c:set var="oldLeavel" value="0"/>
<c:set var="oneLevelCount" value="0"/>
<c:set var="lmid_level1" value=""/>
<c:forEach var="introductionsObj" items="${introductionslList}" varStatus="status">
	<c:if test="${introductionsObj.introductionleavel  gt  oldLeavel and oldLeavel  ne 0}">
                         	<div lmid="${lmid_level1}">
								<ul>
	</c:if>
	<c:if test="${introductionsObj.introductionleavel  lt  oldLeavel and oldLeavel ne 0}">
			<ul>
		</div>
	</c:if>
	<c:if test="${introductionsObj.introductionleavel  ==  oldLeavel and oldLeavel == 1}">
		<div lmid="${lmid_level1}"></div>
	</c:if>
	<c:if test="${introductionsObj.introductionleavel ==  1}">
		<c:set var="oldLeavel" value="${introductionsObj.introductionleavel}" />
		<c:set var="lmid_level1" value="${introductionsObj.id}" />
		<h4><a lmid="${introductionsObj.id}" count="${oneLevelCount}" class="icon_small_arrow_risk icon_small_arrow margin_right_black2 border" href="#" onclick="pageJump('${introductionsObj.id}')" >&nbsp;${introductionsObj.name}</a></h4>
		<c:set var="oneLevelCount" value="${oneLevelCount + 1}" />
	</c:if>
	<c:if test="${introductionsObj.introductionleavel ==  2}">
		<c:set var="oldLeavel" value="${introductionsObj.introductionleavel}" />
                          <li><h6>
                          <c:if test="${introductionsObj.displaytype  ne 0}">
                          		<a  lmid="${introductionsObj.id}" class="margin_right_black" href="#" onclick="pageJump('${introductionsObj.id}')" >
                          </c:if>
                          <c:if test="${introductionsObj.displaytype  eq 0}">
                          		<a  lmid="${introductionsObj.id}" class="margin_right_black" >
                          </c:if>
                          &nbsp;&nbsp;&nbsp;${introductionsObj.name}</a></h6></li>
	</c:if>
</c:forEach>
<c:if test="${oldLeavel ==  2 and  introductionsObj.introductionleavel ne 0 }">
		<ul>
	</div>
</c:if>
		</div>
	</div>