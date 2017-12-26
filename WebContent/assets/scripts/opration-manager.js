var oTable;
var TableManaged = function() {
	return {
		init : function() {

			if (!jQuery().dataTable) {
				return;
			}
			var functionName = $('#functionName').val();
			oTable = registAjaxDataTable({
				id : "oprationTable",
				actionUrl : "functionController.do?opdategrid&functionId="+$("input[name='functionId']").val()+"&rand=" + Math.random(),
				aoColumns : [
						{
							"mDataProp" : "operationname"
						},
						{
							"mDataProp" : "operationcode"
						},
						{
							"mRender" : function() {
								return functionName;
							}
						},
						{
							"mData" : 'id',
							bSortable : false,
							"mRender" : function(data, type, full) {
								return '<a class="btn mini green" action-mode="ajax" action-event="click" action-pop="#ajax-modal" href="functionController.do?addorupdateop&id='
								+data+'&functionId='+$("input[name='functionId']").val()+'"><i class="icon-edit"></i>编辑</a></a>&nbsp;<a class="btn mini red" onclick="del(\''
								+ data + '\')"><i class="icon-remove"></i>删除</a>';
								
							}
						} ],
						aoColumnDefs : [ {
							'bSortable' : false,
							'aTargets' : [ 0,1,2 ]
						} ]
			});
		}
	};
}();


//刷新列表  
function refresh_operationList() {  

	$("#oprationTable").dataTable().fnPageChange('first');  
} 

//删除信息
function del(data) {
	$("#confirmDiv").confirmModal({
		heading: '请确认操作',
		body: '你确认删除所选记录?',
		callback: function () {
			$.ajax({
				type : "POST",
				url : "functionController.do?delop&rand=" + Math.random(),
				data : "id=" + data,
				success : function(data) {
					 var d = $.parseJSON(data);
		   			 if (d.success) {
		   				refresh_operationList();
		   			 }else {
		   				 alert(d.msg);
		   			 }
				}
			});
		}
	});
}