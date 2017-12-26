var TableManaged = function () {
	return {
        //main function to initiate the module
        init: function () {
            
            if (!jQuery().dataTable) {
                return;
            }

            // begin first table
            $('#lst_table').dataTable({
//            	"bProcessing": true, //设置异步请求时，是否有等待框。
//            	"bStateSave": true, //保存状态到cookie
//              'bFilter':false,//是否使用内置的过滤功能。
//            	'bPaginate':true,//是否分页。
            	"bAutoWidth": false,     
            	"sAjaxSource": "organizationController.do?lstOrganization&rand="+Math.random(),//获取数据的ajax方法的URL        
            	"bServerSide":true,    //服务器端必须设置为true 异步请求必须设置//服务端处理分页
//            	"sAjaxDataProp":"msgJson.list",
            	"fnServerData": function (sSource, aoData, fnCallback ) { //与后台交互获取数据的处理函数      
            		//组合查询条件
            		//var searchArray = $('#searchForm').serialize();
//            		console.log(searchArray);
            		//$(currentNode).attr("id")
//            	     aoData.push({"name": "pageNo", "value": "12"});                
//            	     aoData.push({"name": "pageSum", "value": "44"}); 
            	   
					 $.ajax({
						 "dataType" : 'json',
						 "type" : "post",
						 "url" : sSource,
						 "data" : 
						 {
							 aoData:JSON.stringify(aoData)
//							 // "type":5,
//							 // "flag":flag
						 },//  以json格式传递
						 "success" : fnCallback
//							 function(resp){	
//							 var aoData = {
//										sEcho : resp.sEcho,
//										aaData : resp.aaData,
//										iTotalDisplayRecords : resp.iTotalDisplayRecords,
//										iTotalRecords : resp.iTotalRecords
//									};
//						
//							 fnCallback(resp);//服务器端返回的对象的returnObject部分是要求的格式  
//							var row_id,row_edit;
//							var trs = "";
//								$.each(resp.aaData,function(index,content){
//									trs += "<tr class='odd gradeX'>";
//						            trs += "<td><input type='checkbox' class='checkboxes' value=''/></td>";
//						            trs += "<td>1</td>";
//						            trs += "<td>"+content.ogrname+"</td>";
//						            trs += "<td>"+content.createby+"</td>";
//						            trs += "<td ><a id='ajax_del_btn' class='btn mini red' data-confirm='你确认删除所选记录?' href='NewFile.jsp'><i class='icon-remove'></i>删除</a></td>";
//						            trs += "</tr>";
//					            
//									row_id ="<input type='checkbox' class='checkboxes' value='"+content.id+"' name='data_id'/>";
//									row_edit="<a href=\"javascript:void(0);\"> 删除</a>&nbsp;<img src=\"/resources/images/icons/cross.png\" alt=\"删除\" align=\"absmiddle\" />";
//									datatables.fnAddData(["3","3","4","5","6","7"]);//Datatables自带的添加数据方法,你可以搜下fnAddData
//								});
//							 $("#lst_table tbody").html(trs);

//						 } 
            	    });             
            	},
            	//表字段说明 设定是否参加排序
                "aoColumns": [
//                  { "mDataProp": "id",
//                	 bSortable : false,
//                	 "bVisible": false,// 隐藏
//                	 "fnRender" : function(oObj) {
//						var id = oObj.aData.id;
//						return "<input type='checkbox' class='checkboxes' value='"+id+"'/>";
//					}  
//                  },
                  { "mDataProp": "zipcode" },
                  { "mDataProp": "ogrname", 
                	 "mRender" : function(data, type, full) {
  						return '<a onclick="view(\''+data+ '\')">'+data+'</a>';
  					} 
                  },
                  { "mDataProp": "type" },
                  { "mDataProp": "createby" },
                  {
					"mData" : 'id',
//					"sWidth" : "23%",
					bSortable : false,
//					"fnRender" : function(oObj) {
//						var id = oObj.aData.id;
//						return "<a id='ajax_del_btn' class='btn mini red' onClick=\"del(\'"+id+"\');\"><i class='icon-remove'></i>删除</a>";
//					}
					"mRender" : function(data, type, full) {
						return '<a class="btn mini red" onclick="update(\''+data+ '\')">修改</a>&nbsp;<a class="btn mini red" onclick="del(\''+data+ '\')">删除</a>';
					}
				 },
                ],
//                "aLengthMenu": [
//                    [5, 15, 20, -1],
//                    [5, 15, 20, "All"] // change per page values here
//                ],
                // set the initial value
                //"iDisplayStart": 0, 
                "iDisplayLength": 10,
                //"sDom": "t<'row-fluid'<'span_paginate'p>>",
                "sDom": "t<'row-fluid'<'span6'i><'span6'p>>",
                "sPaginationType": "bootstrap",
                "oLanguage": {
                	"sProcessing": "正在加载数据...",
                    "sLengthMenu": "_MENU_",
                    "sZeroRecords": "没有符合项件的数据...",
                    "sInfo": "当前数据为从第 _START_ 到第 _END_ 项数据；总共有 _TOTAL_ 项记录",
                    "sInfoEmpty": "显示 0 至 0 共 0 项",
                    "sInfoFiltered": "(_MAX_)",
                    "oPaginate": {
                        "sPrevious": "上一页",
                        "sNext": "下一页",
                    }
                },
                "aoColumnDefs": [{
                        'bSortable': false,
                        'aTargets': [0]
                    }
                ]
            });

            jQuery('#lst_table .group-checkable').change(function () {
                var set = jQuery(this).attr("data-set");
                var checked = jQuery(this).is(":checked");
                jQuery(set).each(function () {
                    if (checked) {
                        $(this).attr("checked", true);
                    } else {
                        $(this).attr("checked", false);
                    }
                });
                jQuery.uniform.update(set);
            });
            

       	
          //删除记录
	        jQuery('#ajax_del_btn').click(function () {  
	        	//判断是否选中记录信息
	        	//$("#sample_1 .checkboxes").is(":checked")
	        	//$("table td input[type='checkbox']").is(":checked")
//	        	if(!$("#sample_1 .checkboxes").is(":checked")){
//	        		alert("请至少选择一条记录！");
//	        		return false;
//	        	}
	        	
	        	/**取得质检机构id值
	        	var selectRecords = "";
	        	jQuery("#sample_1 .checkboxes").each(function () {
	        		if($(this).attr("checked")){
	        			selectRecords += $(this).val()+",";
	        		}
                });**/

	        	var href = $(this).attr('href');
//	    		if (!$('#dataConfirmModal').length) {
//	    			$('body').append('<div id="dataConfirmModal" class="modal" role="dialog" aria-labelledby="dataConfirmLabel" aria-hidden="true"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button><h3 id="dataConfirmLabel">请确认操作</h3></div><div class="modal-body"></div><div class="modal-footer"><button class="btn" data-dismiss="modal" aria-hidden="true">取消</button><a class="btn btn-primary" id="dataConfirmOK">确定</a></div></div>');
//	    		} 
//	    		$('#dataConfirmModal').find('.modal-body').text($(this).attr('data-confirm'));
//	    		$('#dataConfirmOK').attr('href', href);
//	    		$('#dataConfirmModal').modal({show:true});
	    		
	    	
	        	//删除
	    		$.ajax({
		           type:"POST",
		           url:href +"&rand="+Math.random(),
		            data:"ids="+selectRecords,
		            success:function(data){
		            	if (data.result == 'error') {
							alert(data.message);
						} else {
		            		alert(data.message);
		            		//$("#cenFrame",parent.document).attr("src","newController.do?lstNewsT");
		            		refreshList();
		            	}
		            }
		        });
	        });
	        
	        
            jQuery('#sample_1_wrapper .dataTables_filter input').addClass("m-wrap medium"); // modify table search input
            jQuery('#sample_1_wrapper .dataTables_length select').addClass("m-wrap small"); // modify table per page dropdown
            //jQuery('#sample_1_wrapper .dataTables_length select').select2(); // initialzie select2 dropdown
            
            initModals();
        }
    };
}();


var initModals = function () {
	//modal窗体
     
   	$.fn.modalmanager.defaults.resize = true;
	$.fn.modalmanager.defaults.spinner = '<div class="loading-spinner fade" style="width: 200px; margin-left: -100px;"><img src="assets/img/ajax-modal-loading.gif" align="middle">&nbsp;<span style="font-weight:300; color: #eee; font-size: 18px; font-family:Open Sans;">&nbsp;Loading...</div>';

   	var $modal = $('#ajax-modal'); 
	$('#ajax_add_btn').on('click', function(){
	  // create the backdrop and wait for next modal to be triggered
	var pageContent = $('.page-content');
	App.blockUI(pageContent, false);
	     $modal.load('organizationController.do?ajax', '', function(){
	      $modal.modal();
	      App.unblockUI(pageContent);
	    });
	});
	 
	$modal.on('click', '#add_btn', function(){
		var ogrname = $("#ogrname").val();
		console.info(ogrname+" == 12312");
		//添加
		$.ajax({
           type:"POST",
           url:"organizationController.do?ajaxAdd&rand="+Math.random(),
           data:"ogrname="+ogrname,
           success:function(data){
        	   if (data == 'succ') {
        		   $modal.modal('hide');
        		   refreshList();
        	   } else {
        		  $modal.modal('hidden');
            	  //$("#cenFrame",parent.document).attr("src","newController.do?lstNewsT");
            	  refreshList();
        	   }
            }
        });
//	  $modal.modal('loading');
//	  setTimeout(function(){
//	    $modal
//	      .modal('loading')
//	      .find('.modal-body')
//	        .prepend('<div class="alert alert-info fade in">' +
//	          'Updated!<button type="button" class="close" data-dismiss="alert"></button>' +
//	        '</div>');
//	  }, 1000);
	}); 
	
}



