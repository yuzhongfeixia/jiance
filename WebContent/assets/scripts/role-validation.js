function register(){
	var validator = $('#addForm').validate({
		errorElement : 'span', //default input error message container
		errorClass : 'help-inline', // default input error message class
		focusInvalid : false, // do not focus the last invalid input
		ignore : "",
		rules : {
			roleName : {
				required : true
			},
			roleCode : {
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
	});
	if(validator.form()){ 
		return true;
	}
	return false;
}

