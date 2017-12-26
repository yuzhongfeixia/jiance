<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/webpage/com/hippo/nky/portal/common/top.jsp"%>
<script src="plug-in/pagination/jquery.pagination.js" type="text/javascript"></script>
<link href="plug-in/pagination/pagination.css" rel="stylesheet" type="text/css" />
                          <script type="text/javascript">
                          var page = '${page}';
                          var searchText = '${searchText}';
                          $("#searchForm").attr("action","frontPortalIntroductionsController.do?searchData&searchText="+encodeURIComponent(encodeURIComponent(searchText)));
                          function getTestData(newPage,jq){
                          	if(newPage == page){
                          		//return false;
                         	}else{

                          	  var lms = "";
                      	      $("img[value='0']").each(function(){
                       	    	 if($(this).attr("src")=="Portal/images/searchresult-06.png"){
                       	    		lms += $(this).attr("name");
                         		  	lms += "_";
                      		 	  }
                       		   });
	                      	   $("#lms").val(lms);
	                      	   $("#page").val(newPage);
	                   	       $("#searchForm").submit();
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
                          function pageJump(id){
                      		window.location.href="frontPortalIntroductionsController.do?prepareData&id="+id;
                      	}
                          function toANews(id,lmid){
                      		window.location.href="frontPortalIntroductionsController.do?toANews&id="+id+"&lmid="+lmid;
                      	}
                          function changeImg(imgObj) {
                        	  var lms = "";
                        	  if( $(imgObj).attr("src") == "Portal/images/searchresult-06.png"){
                        		  $(imgObj).attr("src","Portal/images/searchresult.png");
                        	  }else{
                        		  $(imgObj).attr("src","Portal/images/searchresult-06.png");
                              }
                      	      $("img[value='0']").each(function(){
                      	    	  //alert($(this).attr("src"));
                      	    	  //alert($(this).attr("src")=="Portal/images/searchresult-06.png");
                      	    	 if($(this).attr("src")=="Portal/images/searchresult-06.png"){
                      	    		lms += $(this).attr("name");
                        		  	lms += "_";
                     		 	  }
                      		   });
                      	       $("#lms").val(lms);
                      	       $("#searchForm").submit();
                          }
                    
                          function searchDate(){
                        	  var lms = "";
                      	      $("img[value='0']").each(function(){
                       	    	 if($(this).attr("src")=="Portal/images/searchresult-06.png"){
                       	    		lms += $(this).attr("name");
                         		  	lms += "_";
                      		 	  }
                       		   });
	                      	   $("#lms").val(lms);
	                   	       $("#searchForm").submit();
                          }
					</script>
                    <div class="main">
                    <div class="main_left3">
<form method="post" id="searchForm">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <input type="hidden" id="lms" name="lms" value=""/>
  <input type="hidden" id="page" name="page" value=""/>
  <tr>
    <td height="30" bgcolor="#08822a" class="font16_white" >&nbsp;&nbsp;精确检索</td>
  </tr>
  <tr>
    <td height="35" class="line font_grey3">&nbsp;&nbsp;范围</td>
  </tr>
  <tr>
    <td height="170" align="center" valign="middle"><table width="90%" border="0" align="center" cellpadding="0" cellspacing="0" class="font12_song_black">
    <c:forEach var="list" items="${firstLevelList}" varStatus="status">
    <tr>
        <td width="21%"><img id="${list.ID}" onclick="changeImg(this)" value="0" 
        <c:if test="${list.ISSELECTED eq 0}">
        	name = "${list.ID}" src="Portal/images/searchresult.png"
        </c:if>
        <c:if test="${list.ISSELECTED eq 1}">
        	name = "${list.ID}" src="Portal/images/searchresult-06.png"
        </c:if>
       	width="26" height="26"><br></td>
        <td width="79%">${list.NAME}</td>
      </tr>
    </c:forEach>
    </table></td>
  </tr>
  <tr class="line font_grey3">
    <td height="35">&nbsp;&nbsp;日期</td>
  </tr>
  <tr>
    <td height="168"><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td height="50"><input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  placeholder="请选择开始日期" style="width: 210px;height:30px" id="startDate" name="startDate" ignore="ignore"
							      value="${startDate}"></td></td>
        </tr>
      <tr>
        <td height="50"><input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="请选择结束日期"  style="width: 210px;height:30px" id="endDate" name="endDate" ignore="ignore"
							     value="${endDate}"></td></td>
        </tr>
      <tr>
        <td height="50"><input type = "image" src="Portal/images/searchresult-12.jpg" width="221" height="46" onClick="searchDate()"></td>
        </tr>
    </table></td>
  </tr>
</table>
</form>
</div>
					<div class="main_right">						
                        <ul>
                            <li class="li5">                              
                          </li>
                            <li class="li5">
                            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="border3">
  <tr>
    <td height="40" valign="top"><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="line2">
      <tr>
        <td width="87%" height="42"><a style="color:#08822a; font-size: 18px; font-family: '微软雅黑';"><b>搜索结果: ${searchText}</b></a></td>
        <td width="13%" align="right" valign="bottom"><a class="font12_song_green">全部${totalrecords}个结果</a></td>
      </tr>
    </table></td>
  </tr>
   <c:forEach var="list" items="${childList}" varStatus="status">
  <tr>
  <td height="140">
    <table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td height="30" colspan="2"><h4>
        <c:if test="${list.createby ne null}">
        <a style="color:#08822a" class = "id" href="#" onclick="toANews('${list.id}','${list.createby}')">
        </c:if>
        <c:if test="${list.createby eq null}">
        <a style="color:#08822a" class = "id" href="#" onclick="pageJump('${list.id}')">
        </c:if>
        ${list.title}
        </a></h4></td>
      </tr>
      <tr>
        <td height="50" colspan="2" class="font14_song">${list.noHtmlContent}</td>
      </tr>
      <tr class="font_grey3">
        <td>日期：<fmt:formatDate pattern="yyyy/MM/dd" value="${list.createdate}" type="both"/></td>
        <c:if test="${list.keywords ne null}">
        <td height="26" class="font_grey3">关键字：${list.keywords}</td>
        </c:if>
      </tr>
    </table>
    </td>
  </tr>
    </c:forEach>
 
</table>
                    
                          </li>
                          <li class="li5">
                          <div class="yellow">
		<div id="Pagination" class="pagination" style="text-size:12;"></div>
	</div>	
                          </li>
                      </ul>			          						
					</div>
                </div>				
<%@include file="/webpage/com/hippo/nky/portal/common/footer.jsp"%>