<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/webpage/com/hippo/nky/portal/common/top.jsp"%>
<script src="plug-in/pagination/jquery.pagination.js" type="text/javascript"></script>
<script src="plug-in/pagination/members.js" type="text/javascript"></script>
<link href="plug-in/pagination/pagination.css" rel="stylesheet" type="text/css" />

<div class="main">
	<div class="main_middle_ag2">
    	<div class="left">
        	<h7 class="box_header">${detailEntity.popcname}</h7>   
        </div>   
        </div><div>
		<table width="100%" border="0" cellspacing="0">
		  <tr class="line">
		    <td width="17%" valign="top" class="font14_song_green" >结构式</td>
		    <td width="83%"><img src=${detailEntity.structure} width="302" height="200" onerror="showOtherPic(this)"></td>
		  </tr> 
		  <tr class="line">
		    <td width="17%" valign="top" class="item"><p class="font14_song_green">CAS码<br></p></td>
		    <td width="83%" valign="top" ><p class="font14_song">${detailEntity.cas}</p></td>
		  </tr>
		  <tr class="line">
		    <td valign="top" class="item"><p class="font14_song_green">中文通用名称</p></td>
		    <td valign="top"><p class="font14_song">${detailEntity.popcname}</p></td>
		  </tr>
		  <tr class="line">
		    <td valign="top" class="item"><p class="font14_song_green">英文通用名称<br></p></td>
		    <td valign="top"><p class="font14_song">${detailEntity.popename}</p></td>
		  </tr>
		  <tr class="line">
		    <td valign="top" class="item"><p class="font14_song_green">中文化学名称<br></p></td>
		    <td valign="top"><p class="font14_song">${detailEntity.checname}</p></td>
		  </tr>
		  <tr class="line">
		    <td valign="top" class="item"><p class="font14_song_green">英文化学名称<br></p></td>
		    <td valign="top"><p class="font14_song">${detailEntity.cheename}</p></td>
		  </tr>
		  <tr class="line">
		    <td valign="top" class="item"><p class="font14_song_green">类别<br></p></td>
		    <td valign="top"><p class="font14_song">${detailEntity.category}</p></td>
		  </tr >
		  <tr class="line">
		    <td valign="top" class="item"><p class="font14_song_green">主要用途<br></p></td>
		    <td valign="top"><p class="font14_song">${detailEntity.use}</p></td>
		  </tr >
		  <tr class="line">
		    <td valign="top" class="item"><p class="font14_song_green">残留物中文名称<br></p></td>
		    <td valign="top"><p class="font14_song">${detailEntity.residuecname}</p></td>
		  </tr >
		  <tr >
		    <td valign="top" class="item"><p class="font14_song_green">残留物英文名称<br></p></td>
		    <td valign="top"><p class="font14_song">${detailEntity.residueename}</p></td>
		  </tr >
		</table>
	</div>
</div>
<%@include file="/webpage/com/hippo/nky/portal/common/footer.jsp"%>