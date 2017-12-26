<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" >
		<title>NICAS</title>
		<!--meta-->
		<meta charset="UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
		<meta name="format-detection" content="telephone=no" />
		<meta name="keywords" content="Medic, Medical Center" />
		<meta name="description" content="Responsive Medical Health Template" />
		<!--style-->
		<link href='http://fonts.googleapis.com/css?family=PT+Sans' rel='stylesheet' type='text/css'>
		<link href='http://fonts.googleapis.com/css?family=Volkhov:400italic' rel='stylesheet' type='text/css'>
		<link rel="stylesheet" type="text/css" href="Portal/style/reset.css" />
		<link rel="stylesheet" type="text/css" href="Portal/style/superfish.css" />
		<link rel="stylesheet" type="text/css" href="Portal/style/fancybox/jquery.fancybox.css" />
		<link rel="stylesheet" type="text/css" href="Portal/style/jquery.qtip.css" />
		<link rel="stylesheet" type="text/css" href="Portal/style/jquery-ui-1.9.2.custom.css" />
		<link rel="stylesheet" type="text/css" href="Portal/style/style.css" />
		<link rel="stylesheet" type="text/css" href="Portal/style/responsive.css" />
		<link rel="shortcut icon" href="Portal/images/favicon.ico" />
		<!--js-->
		<script type="text/javascript" src="Portal/js/jquery-1.8.3.min.js"></script>
		<script type="text/javascript" src="Portal/js/jquery.ba-bbq.min.js"></script>
		<script type="text/javascript" src="Portal/js/jquery-ui-1.9.2.custom.min.js"></script>
		<script type="text/javascript" src="Portal/js/jquery.easing.1.3.js"></script>
		<script type="text/javascript" src="Portal/js/jquery.carouFredSel-5.6.4-packed.js"></script>
		<script type="text/javascript" src="Portal/js/jquery.sliderControl.js"></script>
		<script type="text/javascript" src="Portal/js/jquery.linkify.js"></script>
		<script type="text/javascript" src="Portal/js/jquery.timeago.js"></script>
		<script type="text/javascript" src="Portal/js/jquery.hint.js"></script>
		<script type="text/javascript" src="Portal/js/jquery.isotope.min.js"></script>
		<script type="text/javascript" src="Portal/js/jquery.isotope.masonry.js"></script>
		<script type="text/javascript" src="Portal/js/jquery.fancybox-1.3.4.pack.js"></script>
		<script type="text/javascript" src="Portal/js/jquery.qtip.min.js"></script>
		<script type="text/javascript" src="Portal/js/jquery.blockUI.js"></script>
		<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
		<script type="text/javascript" src="Portal/js/main.js"></script>
		<style type="text/css">
   			 .666 { color: #666; }
    	</style>
    	<script type="text/javascript">
	    	function pageJump(id){
	    		window.location.href="frontPortalIntroductionsController.do?prepareData&id="+id;
	    	}
	    	function getTestData(){
	    		var searchText = $("#searchText").val().trim();
	    		if(searchText==''||searchText==null){
	    			alert("请输入查询字段！！！");
	    		}else{
	    		window.location.href="frontPortalIntroductionsController.do?searchData&searchText="+encodeURIComponent(encodeURIComponent(searchText));
	    		}
	    		return false;
	    	}
	    	
    	</script>
	</head>
<body>
		<div class="site_container">
			<div class="header_container">
				<div class="header clearfix">
					<div class="header_left">
						<a href="frontPortalIntroductionsController.do?frontPortalIntroductions">
							<img src="Portal/images/logo.jpg" alt="logo" width="244" height="84">
						</a>
					</div>	
					<ul class="sf-menu header_right">
						<li class="submenu selected submenu2">
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
		<li class="submenu">
			<c:if test="${menuObj.displaytype ne 0}">
				<a href="#" onclick="pageJump('${menuObj.id}')">
			</c:if>
			<c:if test="${menuObj.displaytype eq 0}">
				<a >
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
		<li><a >
		</c:if>
		${menuObj.name}</a>
	</c:if>
</c:forEach>
<c:if test="${menuObj.introductionleavel - oldLevel  == -1 }">	</li></ul></li>	</c:if>
<c:if test="${menuObj.introductionleavel - oldLevel  == -2 }">	</li></ul></li></ul></li>	</c:if>
					</ul>
				</div>
			</div>
			<!-- slider -->
			<div class="background">
			</div>
			<div class="page relative noborder">
				<!-- home box -->
				
				<div class="page_layout page_margin_top clearfix">
					<div class="page_left">
						<h3 class="box_header">
							新闻通报
						</h3>
						<div class="columns clearfix">

							<ul class="blog column_left">
                            <li><a class="right" ><img src="Portal/images/samples/300x190/image_01.jpg" alt="" /></a></li>
								<li class="post">
									<ul class="comment_box clearfix">
										<li class="date">
											<div class="value"><fmt:formatDate value="${newsEntities[0].createdate }" pattern="yyyy.MM"/> </div>
											
										</li>
										<li class="comments_number">
											<a href="post.html#comments_list">
												<fmt:formatDate value="${newsEntities[0].createdate }" pattern="dd"/> 
											</a>
										</li>
									</ul>
                                   
									<div class="post_content">
                                    <h4>
											<a href="frontPortalIntroductionsController.do?toANews&id=${newsEntities[0].id }&lmid=${lms[0].id }" class="strong">
												${newsEntities[0].title }
											</a>
										</h4>
										<p class="content" style="height:160px;">
											${newsEntities[0].indexNoHtmlContent } 
										</p>
                                        <div class="show_all clearfix">
							            <a class="more" href="frontPortalIntroductionsController.do?toANews&id=${newsEntities[0].id }&lmid=${lms[0].id }">
								        阅读全文>>
							            </a>
						                </div>
                                        <div class="post_footer">
											<ul class="post_footer_details">
												<li>分类： </li>
                                                ${dhNames[0] }	
											</ul>
										</div>
									</div>
								</li>
								<li class="post">
									<ul class="comment_box clearfix">
										<li class="date">
											<div class="value"><fmt:formatDate value="${newsEntities[1].createdate }" pattern="yyyy.MM"/></div>										
										</li>
										<li class="comments_number">
											<a href="post.html#comments_list">
												<fmt:formatDate value="${newsEntities[1].createdate }" pattern="dd"/>
											</a>
										</li>
									</ul>
									<div class="post_content">
										<h4>
											<a href="frontPortalIntroductionsController.do?toANews&id=${newsEntities[1].id }&lmid=${lms[1].id }" class="strong">
												${newsEntities[1].title }
											</a>
										</h4>
										<p class="content" style="height:160px;">
											${newsEntities[1].indexNoHtmlContent }
										</p>
                                        <div class="show_all clearfix">
							            <a class="more" href="frontPortalIntroductionsController.do?toANews&id=${newsEntities[1].id }&lmid=${lms[1].id }">
								        阅读全文>>
							            </a>
						                </div>
                                        <div class="post_footer">
											<ul class="post_footer_details">
												<li>分类： </li>
                                               ${dhNames[1] }	
											</ul>
										</div>
									</div>
								</li>
							</ul>
							<ul class="blog column_right">
								<li class="post">
									<ul class="comment_box clearfix">
										<li class="date">
											<div class="value"><fmt:formatDate value="${newsEntities[2].createdate }" pattern="yyyy.MM"/></div>										
										</li>
										<li class="comments_number">
											<a href="post.html#comments_list">
												<fmt:formatDate value="${newsEntities[2].createdate }" pattern="dd"/>
											</a>
										</li>
									</ul>
									<div class="post_content">
										<h4>
											<a href="frontPortalIntroductionsController.do?toANews&id=${newsEntities[2].id }&lmid=${lms[2].id }" class="strong">
												${newsEntities[2].title }

											</a>
						        </h4>
										<p class="content" style="height:160px;">
											${newsEntities[2].indexNoHtmlContent } 
										</p>
                                        <div class="show_all clearfix">
							            <a class="more" href="frontPortalIntroductionsController.do?toANews&id=${newsEntities[2].id }&lmid=${lms[2].id }">
								        阅读全文>>
							            </a>
						                </div>
                                        <div class="post_footer">
											<ul class="post_footer_details">
												<li>分类： </li>
                                                ${dhNames[2] }	
											</ul>
										</div>
									</div>
								</li>
								<li class="post">
									<ul class="comment_box clearfix">
                                    <div class="postimage"><a>
											<img src="Portal/images/samples/300x190/image_02.jpg" alt="" />
										</a></div>
										<li class="date">
											<div class="value"><fmt:formatDate value="${newsEntities[3].createdate }" pattern="yyyy.MM"/></div>											
										</li>
										<li class="comments_number">
											<a href="post.html#comments_list">
												<fmt:formatDate value="${newsEntities[3].createdate }" pattern="dd"/>
											</a>
										</li>
									</ul>
									<div class="post_content">

									  <h4>
											<a href="frontPortalIntroductionsController.do?toANews&id=${newsEntities[3].id }&lmid=${lms[3].id }" class="strong">
												${newsEntities[3].title } 
											</a>
									  </h4>
										<p class="content" style="height:160px;">
											${newsEntities[3].indexNoHtmlContent } 
										</p>
                                        <div class="show_all clearfix">
							            <a class="more" href="frontPortalIntroductionsController.do?toANews&id=${newsEntities[3].id }&lmid=${lms[3].id }">
								        阅读全文>>
							            </a>
						                </div>
										<div class="post_footer">
											<ul class="post_footer_details">
												<li>分类： </li>
                                                ${dhNames[3] }	
											</ul>
										</div>
									</div>
								</li>
							</ul>
						</div>
					</div>
					<div class="page_right">
						<div class="sidebar_box first">
							<h3 class="box_header">
								检索
						  </h3>
                          <div id="search_box"> 
                            <form method="post" action="#">
							  <input type="text" placeholder="请输入要查找的内容" class="text" id="searchText" name="searchText"/>
							  <input type="submit" value="" id="go"  onClick="return getTestData();"/>                              
                            </form> 
                          </div> 
						  <h3 class="box_header">快速入口</h3>
						  <div class="imgLogin" > 
							<img src="Portal/images/login.jpg" width="312" height="70">
						 </div> 
			          <ul class="accordion">
			          	<c:forEach var="topMenu" items="${topMenus}" varStatus="vsts">
								<li>
									<div id="accordion-primary-health-care${vsts.index }">
										<h3>${topMenu.name }</h3>
									</div>
									<div class="clearfix">
										<div class="item_content clearfix">	
											<p>	
											<c:if test="${ !empty topMenu.content   }">${topMenu.noHtmlContent }</c:if>
										  </p>
										</div>
										<div class="item_footer clearfix">
											<c:choose>
												<c:when test="${!empty topMenu.content }">
													<a class="more blue icon_small_arrow medium margin_right_white" href="frontPortalIntroductionsController.do?prepareData&id=${topMenu.id }">了解详情</a>
												</c:when>
												<c:when test="${empty topMenu.content }">
													<a class="more blue icon_small_arrow medium margin_right_white">了解详情</a>
												</c:when>
												<c:otherwise>
													
												</c:otherwise>
											</c:choose>
											
										</div>
									</div>
								</li>
						</c:forEach>		
							</ul>
						</div>
						<div class="sidebar_box">
							<h3 class="box_header">
								联系我们
							</h3>
							<p class="info">
							<font >农业部农产品质量标准研究中心</font>
								
							</p>
							<ul class="contact_data">
								<li class="clearfix">
									<span class="social_icon phone"></span>
									<p class="value">
										电话: 010-88888888
									</p>
								</li>
								<li class="clearfix">
									<span class="social_icon form"></span>
									<p class="value">
										地址:北京市海淀区中关村南大街12号
									</p>
								</li>
							</ul>
						</div>
					</div>
				</div>
<%@include file="/webpage/com/hippo/nky/portal/common/footer.jsp"%>