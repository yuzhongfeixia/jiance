<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
	<title>NICAS</title>
	<t:base type="jquery,DatePicker"></t:base>
	<meta charset="utf-8" >
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
	<link rel="stylesheet" type="text/css" href="Portal/style/site.css" />
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
	<script type="text/javascript">
/* 	jQuery().ready(function(){  	 
		$( "#accordion" ).accordion({  
	  		active: false, 
		    navigation: true, 
		    event: 'mouseover',
	        collapsible: true
	      });   
	  }); */
	var lmid = "${entity.id}";
	jQuery().ready(function(){
			$( "#accordion" ).accordion({
				collapsible: true,
		  		active: false, 
			    navigation: true, 
			    "header":"h4",
			    event: "dblclick hoverintent"
		       
		      });
			// 取得栏目等级
			var level = ${entity.introductionleavel};
			// 设置选中
			$(".main_left2").find("a[lmid='"+lmid+"']").attr("class","icon_small_arrow_risk icon_small_arrow margin_right_black2 active");
			// 如果是2级目录，打开上级目录
			if(level == 2){
				// 取得上级目录id
				var parentLmid = $(".main_left2").find("a[lmid='"+lmid+"']").parents("div").attr("lmid");
				// 取得上级目录连番
				var count = $(".main_left2").find("a[lmid='"+parentLmid+"']").attr("count");
				// 打开目录
				$("#accordion").accordion("activate", eval(count));
			}
	  });

	$.event.special.hoverintent = {
	    setup: function() {
	      $( this ).bind( "mouseover", jQuery.event.special.hoverintent.handler );
	    },
	    teardown: function() {
	      $( this ).unbind( "mouseover", jQuery.event.special.hoverintent.handler );
	    },
	    handler: function( event ) {
		    var lmid = $(event.toElement).attr('lmid');
		    if(lmid == undefined){
		    	lmid = $(event.toElement).find('a').attr('lmid');		    	    
			}
		    if(lmid != undefined && $("div[lmid='"+lmid+"']").html() == ""){
			    return false;
			}
	      var currentX, currentY, timeout,
	        args = arguments,
	        target = $( event.target ),
	        previousX = event.pageX,
	        previousY = event.pageY;
	 
	      function track( event ) {
	        currentX = event.pageX;
	        currentY = event.pageY;	
	      };
	 
	      function clear() {
	        target
	          .unbind( "mousemove", track )
	          .unbind( "mouseout", clear );
	        clearTimeout( timeout );
	      }
	 
	      function handler() {
	        var prop,
	          orig = event;
	 
	        if ( ( Math.abs( previousX - currentX ) +
	            Math.abs( previousY - currentY ) ) < 7 ) {
	          clear();
	 
	          event = $.Event( "hoverintent" );
	          for ( prop in orig ) {
	            if ( !( prop in event ) ) {
	              event[ prop ] = orig[ prop ];
	            }
	          }
	          delete event.originalEvent;
	 
	          target.trigger( event );
	        } else {
	          previousX = currentX;
	          previousY = currentY;
	          timeout = setTimeout( handler, 100 );
	        }
	      }
	 
	      timeout = setTimeout( handler, 100 );
	      target.bind({
	        mousemove: track,
	        mouseout: clear
	      });
	    }
	};
	
	function pageJump(id){
		window.location.href="frontPortalIntroductionsController.do?prepareData&id="+id;
	}
	
	function toANews(id,lmid){
		window.location.href="frontPortalIntroductionsController.do?toANews&id="+id+"&lmid="+lmid;
	}
	function toStandardDetail(id,lmid){
		window.location.href="frontPortalIntroductionsController.do?toStandardDetail&id="+id+"&lmid="+lmid;
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
	function toString(str){
		if(str == undefined || str == '' ){
			return "";
		}else{
			return str;
		}
	}
	function showOtherPic(obj){
        obj.src='systemImages/NOIMAGE.jpg';
        obj.onerror = null;
	}
	</script>
	</head>
    <body>
		<div class=".site_container_fengxian">
			<%@include file="/webpage/com/hippo/nky/portal/common/top_navigat.jsp"%>
            <div class="risk_content clearfix">
                <div class="topBar"></div>
				<div class="page_top">
                    <div class="left">
                        <span class="barner"><a href="frontPortalIntroductionsController.do?frontPortalIntroductions">首页</a></span>${dhName}
                    </div>						
                    <div id="search"> 
                    	<form method="post">
							<input type="text" placeholder="请输入要查找的内容" class="text" id="searchText" name="searchText"/>
							<input type="submit" value="" id="go" onClick="return getTestData();"/>
						</form> 		                       
                    </div> 
                </div>