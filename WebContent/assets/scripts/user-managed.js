var haveUpdate = false;
var haveDelete = false;
haveUpdate = isCheckedAuth('updateBtn');
haveDelete = isCheckedAuth('delBtn');
var $modal = $('#ajax-modal'); 
$('#searchBtn').on('click', function(){
	setQueryParams('lst_table',$('#searchForm').getFormValue());
	refreshListToFirst($("#lst_table"));
});

registAjaxDataTable({
	id : "lst_table",
	actionUrl : "userController.do?datagrid&rand=" + Math.random(),
	aoColumns : [
			{
				"mDataProp" : "userName"
			},
			{
				"mDataProp" : "TSDepart_departname"
			},
			{
				"mDataProp" : "realName"
			},
			{
				"mData" : "status",
				"mRender" : function(source, type, val) {
					if (source == 1) {
						return "正常";
					} else if (type == 0) {
						return "禁用 ";
					} else if (type == -1) {
						return "超级管理员 ";
					}
				}
			},
			{
				"mData" : 'id',
				bSortable : false,
				bSearchable : false,
				"mRender" : function(data, type, rec) {
					var rtnStr = '';
					if(haveUpdate){
						rtnStr += '<a class="btn mini yellow" id="updateBtn" onclick="update(\''+ data+ '\')\">编辑</a>';
					}
			
					if(haveDelete){
						rtnStr += '&nbsp;<a class="btn mini yellow" id="delBtn" onclick="del(\''+ data+ '\')\">删除</a>';
					}
					return rtnStr;
				}
			} ],
	search:true
});

$('#ajax_add_btn').on('click', function(){
	var pageContent = $('.page-content');
	App.blockUI(pageContent, false);
	//添加
     $modal.load('userController.do?addorupdate', '', function(){
      $modal.modal({width:"800px",height:"800px"});
      App.unblockUI(pageContent);
    });
});

$modal.on('click', '#add_btn', function(){
	var rtnBool = registerForm({
	  	id : "addForm",
		rules : {
			"userName" : {
				minlength : 2,
				maxlength : 30,
				remote : {
					url: "userController.do?checkUser",     //后台处理程序
				    type: "post",               //数据发送方式
				    async: false,
				    dataType: "json",           //接受数据格式   
				    data: {                     //要传递的数据
				    	"userName" : function() {
				    		return $("#userName").val();
				    	}
				    },//使用ajax方法调用验证输入值
				},
				stringCheck:true,
				required : true
			},
			realName : {
				stringCheck:true,
				required : true
			},
			password : {
				minlength : 6,
				maxlength : 100,
				required : true
			},
			repassword : {
				required : true,
				equalTo : "#password"
			},
			usertype : {
				required : true
			},
			"TSDepart.id" :{
				required : true
			},
			roleid : {
				required : true,
				minlength : 1
			},
			mobilePhone : {
				required : false
			},
			officePhone : {
				required : false
			},
			email : {
				required : true,
				email : true
			}
		},
		messages : {
			userName : {
				required : "用户名范围在2~30位字符",
				remote : "该用户名已存在"
			},
			realName : "填写个人真实姓名",
			email : {
				required : "请输入Email地址",
				email : "请输入正确的email地址"
			},
			password : {
				required : "密码至少6个字符,最多18个字符"
			},
			repassword : {
				required : "请输入确认密码",
				minlength : "确认密码不能小于6个字符",
				equalTo : "两次输入密码不一致不一致"
			},
			"usertype" : {
				required : "请选择用户类型"
			},
			"TSDepart.id" : {
				required : "请选择部门"
			},
			roleid : {
				required : "角色可多选"
			}
		}
	});
	
	if(rtnBool){
		var saveArray;
		var saveStr = $('#addForm').serialize();
		var parmArray = saveStr.split("&");
		var parmStringNew = "";
		var tempRoleId = "";
		var tempDeptId = ""; 
		$.each(parmArray,function(index,data){
			if(data.indexOf("roleid") != -1){
				tempRoleId += data.substr(data.indexOf("=")+1) + ',';
			}else if(data.indexOf("TSDepart") != -1){
				if(data.substr(data.indexOf("=")+1)){
					tempDeptId += '&TSDepart.id='+data.substr(data.indexOf("=")+1);
				}
			}else{
				parmStringNew += data + '&';
			}
		});
		saveArray = parmStringNew + "roleid=" +tempRoleId+tempDeptId;
		//添加
		$.ajax({
           type:"POST",
           url:"userController.do?saveUser&rand="+Math.random(),
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
				url : "userController.do?del&rand=" + Math.random(),
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

function update(data) {
   	var pageContent = $('.page-content');
	App.blockUI(pageContent, false);
     $modal.load('userController.do?addorupdate&id='+data, '', function(){
      $modal.modal({width:"800px",height:"800px"});
      App.unblockUI(pageContent);
    });
}

//重置
function reset() {
	$("#op").attr("SELECTED","true");
	$('#realName').val('');
	$('#userName').val('');
}





