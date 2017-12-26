var oTable;
var haveUpdate = false;
var haveDelete = false;
var haveFun = false;
haveUpdate = isCheckedAuth('updateBtn');
haveDelete = isCheckedAuth('delBtn');
haveFun = isCheckedAuth('funBtn');
var $modal = $('#ajax-modal');
$('#searchBtn').on('click', function(){
	setQueryParams('lst_table',$('#searchForm').getFormValue());
	refreshListToFirst($("#lst_table"));
});

oTable = registAjaxDataTable({
   	id:"lst_table",
	actionUrl:"roleController.do?roleGrid&rand="+Math.random(),
	aoColumns:[
	           { "mDataProp": "roleCode"},
	           { "mDataProp": "roleName"},
			   {
				"mData" : 'id',
				bSortable : false,
				"mRender" : function(data, type, full) {
					var rtnStr = '';
					if (haveUpdate) {
						rtnStr += '<a class="btn mini yellow" id="updateBtn" onclick="update(\''
								+ data + '\')">[修改]</a>&nbsp;';
					}
					if (haveDelete) {
						rtnStr += '<a class="btn mini yellow" id="delBtn" onclick="del(\''
								+ data + '\')">[删除]</a>&nbsp;';
					}
					if (haveFun) {
						rtnStr += '<a class="btn mini yellow" id="funBtn" onclick="setFun(\''
							+ data + '\')">[权限设置]</a>';
					}
					return rtnStr;
				}
			}
			],
	search:true,
	aoColumnDefs : [ {
	'bSortable' : false,
	'aTargets' : [ 0,1 ]
} ]
});

$('#ajax_add_btn').on('click', function(){
	var pageContent = $('.page-content');
	App.blockUI(pageContent, false);
	 //添加
     $modal.load('roleController.do?addorupdate', '', function(){
      $modal.modal();
      App.unblockUI(pageContent);
    });
});

$modal.on('click', '#add_btn', function(){
	var rtnBool = registerForm({
	  	id : "addForm",
	  	rules : {
			roleName : {
				stringCheck:true,
				required : true
			},
			roleCode : {
				stringCheck:true,
				required : true,
				minlength : 1
			}
		},
		messages : {
			roleName : {
				required : "角色范围2~8位字符之间,且不为空"
			},
			roleCode : {
				required : "角色编码2~15位字符之间,且不为空"
			}
		}
	});
	if(rtnBool){
		var saveArray = $('#addForm').serialize();
		//添加
		$.ajax({
           type:"POST",
           url:"roleController.do?saveRole&rand="+Math.random(),
           data:saveArray,
           success:function(data){
        	   var d = $.parseJSON(data);
        	   if (d.success) {
   				   modalTips(d.msg);	
   				   $modal.modal('hide');
   				   refreshList($("#lst_table"));
   			   }else {
   				   modalAlert(d.msg);
   			   }
            }
        });
	}
}); 


//删除信息
function del(data) {
	$("#confirmDiv").confirmModal({
		heading: '请确认操作',
		body: '你确认删除所选记录?',
		callback: function () {
			$.ajax({
				type : "POST",
				url : "roleController.do?delRole&rand=" + Math.random(),
				data : "id=" + data,
				success : function(data) {
					 var d = $.parseJSON(data);
					 if (d.success) {
		   				modalTips(d.msg);	
		   				refreshList($("#lst_table"));
		   			 }else {
		   				 modalAlert(d.msg);
		   			 }
				}
			});
		}
	});
}

function setFun(data) {
	//查看权限
	$('#menuLst').html("<iframe src=\'roleController.do?fun&rand=" + Math.random()+"&roleId="+data+"\' frameborder=\"0\" style=\"border:0;width:100%;height:99.4%;\"></iframe>");
}

function update(data) {
	//修改
	var pageContent = $('.page-content');
	App.blockUI(pageContent, false);
     $modal.load('roleController.do?addorupdate&id='+data, '', function(){
      $modal.modal();
      App.unblockUI(pageContent);
    });
}

//重置
function reset() {
	$('#roleName').val('');
}







