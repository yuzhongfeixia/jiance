<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webpage/main/navigator.jsp"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
select{width:130px;background-color:#fff;border:1px solid #ccc}
</style>
<link rel="stylesheet" type="text/css" href="assets/plugins/select2/select2_metro.css" />
<script type="text/javascript" src="assets/plugins/select2/select2.min.js"></script>

<link rel="stylesheet" href="assets/plugins/data-tables/DT_bootstrap.css" />
<script type="text/javascript" src="assets/plugins/data-tables/jquery.dataTables.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/DT_bootstrap.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/jquery.jeditable.js"></script>

<script src="assets/plugins/bootstrap-modal/js/bootstrap-confirm.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript"></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript"></script>
<link href="assets/plugins/bootstrap-modal/css/bootstrap-modal.css"	rel="stylesheet" type="text/css" />
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-header-fixed">
	<div class="row-fluid">
		<div class="span12">
			<!-- BEGIN EXAMPLE TABLE PORTLET-->
			<div class="portlet box box_usual">
				<div class="portlet-title">
					<div class="caption">
						<i class="icon-globe"></i>判定标准版本
					</div>
				</div>
				<div class="portlet-body">
					<div class="alert alert-success">
						<form action="#" name="searchForm" id="searchForm" class="form-horizontal">
							<div class="clearfix">
								<div class="table-seach">
									<label class="help-inline seach-label">农产品名称 :</label>
									<div class="seach-element">
										<input type="text" placeholder="" class="m-wrap small"
											name="agrname" id="agrname">
									</div>
								</div>
								<div class="table-seach">
									<label class="help-inline seach-label">污染物名称:</label>
									<div class="seach-element">
										<input type="text" placeholder="" class="m-wrap small"
											name="pollname" id="pollname">
									</div>
								</div>
								<div class="table-seach">
									<label class="help-inline seach-label">单位:</label>
									<div class="seach-element">
										<t:dictSelect id="units" field="units" typeGroupCode="unit" hasLabel="false"></t:dictSelect>
									</div>
								</div>
								<div class="table-seach">
									<label class="help-inline seach-label">判定值来源:</label>
									<div class="seach-element">
										<t:dictSelect id="valuefrom" field="valuefrom" typeGroupCode="valuefrom" hasLabel="false"></t:dictSelect>
									</div>
								</div>
								<div class="table-seach">
									<label class="help-inline seach-label">使用规定:</label>
									<div class="seach-element">
										<t:dictSelect id="stipulate" field="stipulate" typeGroupCode="stipulate" hasLabel="false"></t:dictSelect>
									</div>
								</div>
							</div>
						</form>
					</div>
					<div class="clearfix">
						<c:if test="${userType eq 0}">
							<div class="btn-group">
								 <c:if test="${isPublished == false}"><t:authFilter name="fast_set"><a class="btn btngroup_usual" data-toggle="modal" id="fast_set"><i class="icon-plus"></i>快速设定</a></t:authFilter></c:if>
							</div>
						</c:if>
						<div class="pull-right">
							<t:authFilter name="searchBtn">
								<a href="#" class="btn btngroup_seach" id="searchBtn"><i
									class="icon-search"></i>搜索</a>
							</t:authFilter>
<%-- 							<t:authFilter name="resetBtn"> --%>
<!-- 								<button type="button" class="btn btngroup_usual" -->
<!-- 									onclick="reset();" id="resetBtn"> -->
<!-- 									<i class="icon-reload"></i>重置 -->
<!-- 								</button> -->
<%-- 							</t:authFilter> --%>
						</div>
					</div>
					<table class="table table-striped table-bordered table-hover" id="lst_table">
						<thead>
							<tr>
								<th><input type="checkbox" class="group-checkable" data-set="#lst_table .checkboxes" /></th>
								<th>农产品名称</th>
								<th class="hidden-480">污染物名称</th>
								<th class="hidden-480">判定标准值</th>
								<th class="hidden-480">单位</th>
								<th class="hidden-480">判定值来源</th>
								<th class="hidden-480">使用规定</th>
								<!-- <th class="hidden-480">标准号</th> -->
								<th class="hidden-480">操作</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</div>
			<!-- END EXAMPLE TABLE PORTLET-->
		</div>
	</div>

	<div id="ajax-modal" class="modal hide fade" tabindex="-1"></div>
	<div id="confirmDiv" class="modal hide fade"></div>
	<script>
		var oTable;
		// modal窗体
		$.fn.modalmanager.defaults.spinner = '<div class="loading-spinner fade" style="width: 200px; margin-left: -100px;"><img src="assets/img/ajax-modal-loading.gif" align="middle">&nbsp;<span style="font-weight:300; color: #eee; font-size: 18px; font-family:Open Sans;">&nbsp;Loading...</div>';
	   	var $modal = $('#ajax-modal'); 
		jQuery(document).ready(function() {
			//全选
			jQuery('#lst_table .group-checkable').change(function () {
		          var set = jQuery(this).attr("data-set");
		          var checked = jQuery(this).is(":checked");
		          jQuery(set).each(function () {
		        	  var chk = $(this).parent().parent().find("span");
		        	  //$(this).attr("checked", false);
		        	  if (chk.hasClass("checked")) {
		            	  chk.removeClass('checked');
		            	  $(this).attr("checked", false);
		              } else {
		            	  chk.addClass('checked');
		            	  $(this).attr("checked", true);
		              }
		          });
		          jQuery.uniform.update(set);
		    });
			
			oTable = registAjaxDataTable({
				id : "lst_table",
				actionUrl : "judgeStandardController.do?datagrid&vid=${versionId}&rand=" + Math.random(),
				aoColumns : [
							{
								"mData" : "id",
								"sWidth" : "8px",
								"bSortable" : false,
								"mRender" : function(data, type, rec) {
									return "<div class='checker'><span class=''><input type='checkbox' class='checkboxes' value='"+data+"' onclick='chkThis(this);'/></span></div>"
									//return "<input type='checkbox' class='checkboxes' value='"+data+"'/>";
								}
							},
							{
								"mData" : "agrname",
								"sWidth" : "15%"
							},
							{
								"mData" : "pollname",
								"sWidth" : "15%"
							},
							{
								"mData" : "value",
								"sClass" : "clsVal"
							},
							{
								"mData" : "units",
								"sClass" : "clsUnits",
								"sWidth" : "15%",
 								"mRender" : function(data, type, full) {
									return full.units_name;
								} 
							},
							{
								"mData" : "valuefrom",
								"sClass" : "clsValFrom",
								"sWidth" : "15%",
					 			"mRender" : function(data, type, full) {
									return full.valuefrom_name;
								}
							},
							{
								"mData" : "stipulate",
								"sClass" : "clsStip",
								"sWidth" : "15%",
		 						"mRender" : function(data, type, full) {
									return full.stipulate_name;
								}
							},
							{
								"mData" : 'id',
								bSortable : false,
								bSearchable : false,
								"mRender" : function(data, type, rec) {
									var rtnStr = '';
									if ("${userType}" != '0') {
										return rtnStr;
									}
//									if(isCheckedAuth('updateBtn')){
										rtnStr += ' <c:if test="${isPublished == false}"><a class="btn mini yellow" id="czBtn" onclick="canzhao(\''+ rec.agrname + '\',\''+ data + '\',\''+ rec.cas+ '\',\''+rec.lid+'\')\">[参照]</a></c:if>';
//									}
//									if(isCheckedAuth('delBtn')){
										rtnStr += '&nbsp; <c:if test="${isPublished == false}"><a class="btn mini yellow" id="delBtn" onclick="del(\''+ data+ '\')\">删除</a></c:if>';
//									}
									return rtnStr;
										}
							} ],
					"fnDrawCallback" : function(oSettings){
						var oTable = $('#lst_table').dataTable();
						//隐藏列
						var parmArray = oTable.fnSettings().aoColumns;
						$.each(parmArray,function(index,data){
							if(!data.bVisible){
								oTable.fnSetColumnVis(index, false);
								return;
							}
			    		});
						var isPublished = '${isPublished}';
						//双击编辑
						$('#lst_table tbody tr').each(function(index){
							if(isPublished == 'false' && "${userType}" == "0"){
							//判定标准值
							$($(this).find('td.clsVal')).editable('judgeStandardController.do?save', {
//									id    : 'id',
								    name  : 'jvalue',
								    method: 'POST',
								    tooltip   : '双击修改信息....',
							        event     : 'dblclick',
							        onblur   : 'submit',
							        placeholder: '',
					                "callback": function(sValue, y) {
					                	var aPos = oTable.fnGetPosition( this );
					                    oTable.fnUpdate(sValue.replace(/^\"|\"/g,''), aPos[0], aPos[1] );
					                	oTable.fnDraw();
					                },
					                "submitdata": function (value, settings) {
					                    return {
					                        "row_id": this.parentNode.getAttribute('id'),
					                        "column": oTable.fnGetPosition(this)[2]
					                    };
					                },
					                "height": "20px",
					                "width": "100px"
						    });
							
							//单位
							$($(this).find('td.clsUnits')).editable('judgeStandardController.do?save', {
//								id    : 'id',
							    name  : 'junits',
							    method: 'POST',
								"type":"select",
								loadurl : 'judgeStandardController.do?getUnitsForJson',
								loadtype  : 'GET',
							   	loaddata : function(value, settings) {
							    	return {type: "tunits",sel_data : value};
							   	},
								indicator : 'Saving...',
						        tooltip   : '双击修改信息....',
						        //style : "inherit", cssclass
						        event     : 'dblclick',
						        onblur   : 'submit',
						        placeholder: '',
				                "callback": function(sValue, y) {
				                	var aPos = oTable.fnGetPosition( this );
				                	$('#lst_table').dataTable().fnUpdate(sValue, aPos[0], aPos[1]);
				                	oTable.fnDraw();
				                },
				                "submitdata": function (value,settings) {
				                	var tdData = oTable.fnGetData( this );
				                    return {
				                        "row_id": this.parentNode.getAttribute('id'),
				                        "column": oTable.fnGetPosition(this)[2]
				                    };
				                }
					    });
							
						/**判定值来源
						$($(this).find('td.clsValFrom')).editable('judgeStandardController.do?save', {
//								id    : 'id',
							    name  : 'jvaluefrom',
							    method: 'POST',
								"type":"select",
								loadurl : 'judgeStandardController.do?getUnitsForJson',
								loadtype  : 'GET',
							   	loaddata : function(value, settings) {
							    	return {type: "tvaluefrom",sel_data : value};
							   	},
								indicator : 'Saving...',
						        tooltip   : '双击修改信息....',
						        event     : 'dblclick',
						        onblur   : 'submit',
						        placeholder: '',
				                "callback": function(sValue, y) {
				                	var aPos = oTable.fnGetPosition( this );
				                    oTable.fnUpdate(sValue, aPos[0], aPos[1] );
			                		oTable.fnDraw();
				                },
				                "submitdata": function ( value, settings ) {
				                    return {
				                        "row_id": this.parentNode.getAttribute('id'),
				                        "column": oTable.fnGetPosition(this)[2]
				                    };
				                }
					    });**/
							
						//使用规定
						$($(this).find('td.clsStip')).editable('judgeStandardController.do?save', {
//								id    : 'id',
							    name  : 'jstipulate',
							    method: 'POST',
								"type":"select",
								loadurl : 'judgeStandardController.do?getUnitsForJson',
								loadtype  : 'GET',
							   	loaddata : function(value, settings) {
							    	return {type: "tstipulate",sel_data : value};
							   	},
								indicator : 'Saving...',
						        tooltip   : '双击修改信息....',
						       // style : "inherit",
						        event     : 'dblclick',
						        onblur   : 'submit',
						        placeholder: '',
				                "callback": function(sValue, y) {
				                	var aPos = oTable.fnGetPosition( this );
				                    oTable.fnUpdate(sValue, aPos[0], aPos[1] );
				                	oTable.fnDraw();
				                },
				                "submitdata": function ( value, settings ) {
				                    return {
				                        "row_id": this.parentNode.getAttribute('id'),
				                        "column": oTable.fnGetPosition(this)[2]
				                    };
				                }
					    });
						}
					});
					},
					search : true,
					iDisplayLength : 50,
					aoColumnDefs : [ {
						'bSortable' : false,
						'aTargets' : [0,1,2,3,4,5,6,7]
					} ]
				});
		});
		
		//刷新列表  fatherRefreshList
		function fatherRefreshList() {  
			$("#lst_table").dataTable().fnPageChange('first');  
		}  

		//表单搜索
		$('#searchBtn').on('click', function(){
 			setQueryParams('lst_table',$('#searchForm').getFormValue());
			fatherRefreshList();
		});
		
		//重置
		function reset() {
			$("#units").val("");
			$("#valuefrom").val("");
			$("#stipulate").val(''); 
			$('#agrname').val('');
			$('#pollname').val('');
		}
		
		//跳转到快速设定
		$('#fast_set').on('click', function(){
			var bool = false;
			$("#lst_table .checkboxes").each(function(index){ 
            	if($(this).attr("checked") != 'undefined' && $(this).attr("checked") == "checked"){
            		bool = true;
            		return false;
                } 
            })

			if (!bool) {
		          modalAlert("至少要选择一条数据");
		          return;
		     }
			var pageContent = $('.page-content');
			App.blockUI(pageContent, false);
		    $modal.load('judgeStandardController.do?addorupdate', '', function(){
		     $modal.modal();
			App.unblockUI(pageContent);
		   });
		});
		
		//参照函数
		function canzhao(agrname,jId,cas,lid) {
			setTimeout(function(){
				$modal.load('judgeStandardController.do?limitStandard&jId='+jId+'&versionId='+lid+'&cas='+cas+'&agrname='+encodeURI(encodeURI(agrname)), '', function(){
			    	$modal.modal({width:"850px",height:"800px"});
			    });
			}, 300);
		}
		
		//删除信息
		function del(data) {
			$("#confirmDiv").confirmModal({
				heading: '请确认操作',
				body: '你确认删除所选记录?',
				callback: function () {
					$.ajax({
						type : "POST",
						url : "judgeStandardController.do?del&rand=" + Math.random(),
						data : "id=" + data,
						success : function(data) {
							 var d = $.parseJSON(data);
				   			 if(d.success) {
				   			 	modalTips(d.msg);
				   				fatherRefreshList();
				   			 }else {
				   				modalAlert(d.msg);
				   			 }
						}
					});
				}
			});
		}
		
		//快速设定添加
		$modal.on('click', '#add_btn', function() {
			var ids = '';
			$("#lst_table .checkboxes").each(function(index){ 
            	if($(this).attr("checked") != 'undefined' && $(this).attr("checked") == "checked"){
            		ids += $(this).val()+","
                } 
            })
			var formData = $('#addForm').serialize();
			// 保存
			if (ids!== undefined &&ids!=""){
				// 保存判断标准值
				$.ajax({
					type : "POST",
					url : "judgeStandardController.do?saveMany&rand=" + Math.random(),
					data : formData +'&ids='+ids,
					success : function(data) {
						var d = $.parseJSON(data);
						if (d.success) {
							modalTips(d.msg);
							$modal.modal('hide');
							fatherRefreshList();
						} else {
							modalAlert(d.msg);
						}
					}
				});
			}
		});
		
		function chkThis(obj){
      	  var chk = $(obj).parent().parent().find("span");
    	  if (chk.hasClass("checked")) {
        	  chk.removeClass('checked');
        	  $(this).attr("checked", false);
          } else {
        	  chk.addClass('checked');
        	  $(this).attr("checked", true);
          }
		}
	</script>
</body>
</html>
