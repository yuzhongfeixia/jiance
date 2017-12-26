<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/webpage/com/hippo/nky/portal/common/top.jsp"%>
<script src="plug-in/pagination/jquery.pagination.js" type="text/javascript"></script>
<script src="plug-in/pagination/members.js" type="text/javascript"></script>
<link href="plug-in/pagination/pagination.css" rel="stylesheet" type="text/css" />

<div class="main">
	<div class="main_middle_ag2">
    	<div class="left">
        	<h7 class="box_header">${detailEntity.nameZh}</h7>   
        </div>   
        </div><div>
		<table width="100%" border="0" cellspacing="0">
		  <tr class="line">
		    <td valign="top" class="item"><p><span class="font14_song_green">国别</span><br>
		      </p></td>
		    <td valign="top"> <p class="font14_song">${detailEntity.standardCountry}</p></td>
		  </tr>
		  <tr class="line">
		    <td valign="top" class="item"><p class="font14_song_green">类别</p></td>
		    <td valign="top"><p class="font14_song">${detailEntity.standardType}</p></td>
		  </tr>
		  <tr class="line">
		    <td valign="top" class="item"><p><span class="font14_song_green">标准号</span><br>
		    </p></td>
		    <td valign="top"><p class="font14_song">${detailEntity.standardCode}</p></td>
		  </tr>
		  <tr class="line">
		    <td valign="top" class="item"><p class="font14_song_green">中文名称</p></td>
		    <td valign="top"><p class="font14_song">${detailEntity.nameZh}</p></td>
		  </tr>
		  <tr class="line">
		    <td valign="top" class="item"><p><span class="font14_song_green">英文名称</span><br>
		    </p></td>
		    <td valign="top"><p class="font14_song">${detailEntity.nameEn}</p></td>
		  </tr>
		  <tr class="line">
		    <td valign="top" class="item"><p><span class="font14_song_green">发布日期</span><br>
		    </p></td>
		    <td valign="top"><p class="font14_song"><fmt:formatDate pattern="yyyy-MM-dd" value="${detailEntity.publishDate}" type="both"/></p></td>
		  </tr>
		  <tr class="line">
		    <td valign="top" class="item"><p><span class="font14_song_green">实施日期</span><br>
		    </p></td>
		    <td valign="top"><p class="font14_song"><fmt:formatDate pattern="yyyy-MM-dd" value="${detailEntity.implementDate}" type="both"/></p></td>
		  </tr>
		  <tr class="line">
		    <td valign="top" class="item"><p><span class="font14_song_green">发布机构</span><br>
		    </p></td>
		    <td valign="top"><p class="font14_song">${detailEntity.publishOrg}</p></td>
		  </tr >
		  <tr class="line">
		    <td valign="top" class="item"><p><span class="font14_song_green">替代标准</span><br>
		    </p></td>
		    <td valign="top"><p class="font14_song">${detailEntity.substitute}</p></td>
		  </tr >
		  <tr class="line">
		    <td valign="top" class="item"><p><span class="font14_song_green">作废标准</span><br>
		    </p></td>
		    <td valign="top"><p class="font14_song">${detailEntity.invalid}</p></td>
		  </tr >
		  <tr class="line">
		    <td valign="top" class="item"><p><span class="font14_song_green">状态</span><br>
		    </p></td>
		    <td valign="top"><p class="font14_song">${detailEntity.stopflag}</p></td>
		  </tr >
		  <tr >
		    <td valign="top" class="item"><p><span class="font14_song_green">备注</span><br>
		    </p></td>
		    <td valign="top"><p class="font14_song">${detailEntity.describe}</p></td>
		  </tr >
		</table>
	</div>
</div>
<%@include file="/webpage/com/hippo/nky/portal/common/footer.jsp"%>