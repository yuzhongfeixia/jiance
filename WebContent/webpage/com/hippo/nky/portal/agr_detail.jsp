<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/webpage/com/hippo/nky/portal/common/top.jsp"%>
<script src="plug-in/pagination/jquery.pagination.js" type="text/javascript"></script>
<script src="plug-in/pagination/members.js" type="text/javascript"></script>
<link href="plug-in/pagination/pagination.css" rel="stylesheet" type="text/css" />

<div class="main">
	<div class="main_middle_ag2">
    	<div class="left">
        	<h7 class="box_header">${detailEntity.cname}</h7>   
        </div>   
        </div><div>
		<table width="100%" border="0" cellspacing="0">
		  <tr class="line">
		    <td width="17%" valign="top" class="font14_song_green" >图片</td>
		    <td width="83%"><img src=${detailEntity.imagepath} width="302" height="200" onerror="showOtherPic(this)"></td>
		  </tr>
		  <tr class="line">
		    <td valign="top" class="item"><p class="font14_song_green">编码<br></p></td>
		    <td valign="top"><p class="font14_song">${detailEntity.code}</p></td>
		  </tr>
		  <tr class="line">
		    <td valign="top" class="item"><p class="font14_song_green">GEMS</p></td>
		    <td valign="top"><p class="font14_song">${detailEntity.gems}</p></td>
		  </tr>
		  <tr class="line">
		    <td valign="top" class="item"><p class="font14_song_green">FOODEX_2<br></p></td>
		    <td valign="top"><p class="font14_song">${detailEntity.foodex}</p></td>
		  </tr>
		  <tr class="line">
		    <td valign="top" class="item"><p class="font14_song_green">中文名称</p></td>
		    <td valign="top"><p class="font14_song">${detailEntity.cname}</p></td>
		  </tr>
		  <tr class="line">
		    <td valign="top" class="item"><p class="font14_song_green">英文名称<br></p></td>
		    <td valign="top"><p class="font14_song">${detailEntity.ename}</p></td>
		  </tr>
		  <tr class="line">
		    <td valign="top" class="item"><p class="font14_song_green">中文别名<br></p></td>
		    <td valign="top"><p class="font14_song">${detailEntity.calias}</p></td>
		  </tr>
		  <tr class="line">
		    <td valign="top" class="item"><p class="font14_song_green">英文别名<br></p></td>
		    <td valign="top"><p class="font14_song">${detailEntity.ealias}</p></td>
		  </tr>
		  <tr class="line">
		    <td valign="top" class="item"><p class="font14_song_green">拉丁名<br></p></td>
		    <td valign="top"><p class="font14_song">${detailEntity.latin}</p></td>
		  </tr >
		  <tr >
		    <td valign="top" class="item"><p class="font14_song_green">描述<br></p></td>
		    <td valign="top"><p class="font14_song">${detailEntity.describe}</p></td>
		  </tr>
		</table>
	</div>
</div>
<%@include file="/webpage/com/hippo/nky/portal/common/footer.jsp"%>