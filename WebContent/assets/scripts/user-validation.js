function register(){
	var validator = $('#addForm').validate({
//		debug : true,//调试模式取消submit的默认提交功能
		focusInvalid: true,//表单提交时,焦点会指向第一个没有通过验证的域
		errorElement : 'span', //default input error message container
		errorClass : 'help-inline', // default input error message class
		focusInvalid : false, // do not focus the last invalid input
		ignore : "",
		rules : {
			"userName" : {
				minlength : 2,
				maxlength : 10,
				remote : {
					url: "userController.do?checkUser",     //后台处理程序
				    type: "post",               //数据发送方式
				    dataType: "json",           //接受数据格式   
				    data: {                     //要传递的数据
				    	"userName" : function() {
				    		return $("#userName").val();
				    	}
				    },//使用ajax方法调用验证输入值
				},
				required : true
			},
			realName : {
				required : true
			},
			password : {
				minlength : 6,
				maxlength : 18,
				required : true
			},
			repassword : {
				required : true,
				equalTo : "#password"
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
		/**              url:true                    必须输入正确格式的网址
		 date:true                   必须输入正确格式的日期
		 number:true                 必须输入合法的数字(负数，小数)
		 digits:true                 必须输入整数
		 creditcard:                 必须输入合法的信用卡号
		 equalTo:"#field"            输入值必须和#field相同
		 accept:                     输入拥有合法后缀名的字符串（上传文件的后缀）
		 maxlength:5                 输入长度最多是5的字符串(汉字算一个字符)
		 minlength:10               输入长度最小是10的字符串(汉字算一个字符)
		 rangelength:[5,10]         输入长度必须介于 5 和 10 之间的字符串")(汉字算一个字符)
		 range:[5,10]               输入值必须介于 5 和 10 之间
		 max:5                      输入值不能大于5
		 min:10                     输入值不能小于10
		 **/
		},
		messages : {
			userName : {
				required : "用户名范围在2~10位字符",
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
			"TSDepart.id" : {
				required : "请选择部门"
			},
			roleid : {
				required : "角色可多选"
			},
		},
	
		invalidHandler : function(event, validator) { //display error alert on form submit              
		},
	
		highlight : function(element) { // hightlight error inputs
			$(element).closest('.help-inline').removeClass('ok'); // display OK icon
			$(element).closest('.control-group').removeClass('success').addClass(
					'error'); // set error class to the control group
		},
	
		unhighlight : function(element) { // revert the change dony by hightlight
			$(element).closest('.control-group').removeClass('error'); // set error class to the control group
		},
	
		success : function(label) {
			label.addClass('valid').addClass('help-inline ok') // mark the current input as valid and display OK icon
			.closest('.control-group').removeClass('error').addClass('success'); // set success class to the control group
		},
	
        errorPlacement: function (error, element) {
            error.appendTo(element.closest('.controls'));
        },
//		submitHandler : function(form) {
//		}
	});
	
//	$('#addForm input').keypress(function(e) {
//		 if (e.which == 13) {
//             if ($('.login-form').validate().form()) {
//             }
//             return false;
//         }
//	});
	
	if(validator.form()){ 
		return true;
	}
	return false;
}

