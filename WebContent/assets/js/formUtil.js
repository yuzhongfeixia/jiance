/*
 * 表单设置/取得JSON格式的值到控件/JSON变量
 */

!function ($) {
    /**
     * 将表单中的输入控件的值转换成键值对对象的格式，以下是转换规则：
     * 1. 文本框(type=text, type=password)、文本区域、不包含multiple属性的下拉选择框、name相同的一组单选框转换为字符串
     *    注意：一组单选框如果都没有点选，则为null
     * 2. 数值类型的文本框(type=number)转换为数字(HTML5 Only)
     * 3. 不包含value属性的复选框转换为布尔值
     *    注意：不应该出现多个name相同而又不包含value属性的复选框，否则结果不可预料
     * 4. name相同且都包含value属性的复选框、包含multiple属性的多重选择框(按ctrl键多选)的所选项转换为字符串数组
     *    注意：当前只能转成字符串数组
     * @return {*}
     */
    $.fn.getFormValue = function () {
        var ret = {};

        // 文本类型
        this.find('input[type=text], input[type=password], input[type=hidden],textarea, select:not([multiple])').each(function () {
            var name = $(this).prop('name') || $(this).prop('id');
            ret[name] = $(this).val();
        });

        this.find('input[type=radio]').each(function () {
            var name = $(this).prop('name')|| $(this).prop('id');
            if (!ret[name]) {
                ret[name] = null;
            }
            if ($(this).prop('checked')) {
                ret[name] = $(this).prop('value');
            }
        });

        // 数值类型
        this.find('input[type=number]').each(function () {
            var name = $(this).prop('name') || $(this).prop('id');
            ret[name] = Number($(this).val());
        });

        // 布尔类型
        this.find('input[type=checkbox]:not([value])').each(function () {
            var name = $(this).prop('name');
            ret[name] = $(this).prop('checked');
        });


        // 数组
        this.find('select[multiple]').each(function () {
            var name = $(this).prop('name') || $(this).prop('id');
            ret[name] = $(this).val() || [];
        });

        this.find('input[type=checkbox][value]').each(function () {
            var name = $(this).prop('name') || $(this).prop('id');
            /*if (!ret[name]) {
                ret[name] = [];
            }
            if ($(this).prop('checked')) {
                ret[name].push($(this).prop('value'));
            }*/
            // 修改coeckbox 为不选中不提交
            if ($(this).prop('checked')) {
            	if (!ret[name]) {
            		ret[name] = $(this).prop('value');
            	}else{
            		if(!(ret[name] instanceof Array)){
            			var temp = ret[name];
            			ret[name] = [];
            			//ret[name] = temp;
            			ret[name].push(temp);	
            		}
            		
            		ret[name].push($(this).prop('value'));	
            	}
            }
            
        });

        return ret;
    };

    /**
     * getFormValue的逆操作，设置符合类型的控件的值
     * @param val
     */
    $.fn.setFormValue = function (val) {
        // 文本类型
        this.find('input[type=text], input[type=password], input[type=hidden], textarea, select:not([multiple])').each(function () {
            var name = $(this).prop('name') || $(this).prop('id');
            if (val[name] && typeof(val[name]) == 'string') {
                $(this).val(val[name]);
            }
        });

        this.find('input[type=radio]').each(function () {
            var name = $(this).prop('name') || $(this).prop('id');
            if (val[name] && typeof(val[name]) == 'string' && $(this).prop('value') == val[name]) {
                $(this).prop('checked', true);
            }
        });

        // 数值类型
        this.find('input[type=number]').each(function () {
            var name = $(this).prop('name') || $(this).prop('id');
            if (val[name] && typeof(val[name]) == 'number') {
                $(this).val(val[name]);
            }
        });

        // 布尔类型
        this.find('input[type=checkbox]:not([value])').each(function () {
            var name = $(this).prop('name') || $(this).prop('id');
            if (val[name] && typeof(val[name]) == 'boolean') {
                $(this).prop('checked', val[name]);
            }
        });


        // 数组
        this.find('select[multiple]').each(function () {
            var name = $(this).prop('name') || $(this).prop('id');
            if (val[name] && val[name] instanceof Array) {
                $(this).val(val[name]);
            }
        });

        this.find('input[type=checkbox][value]').each(function () {
            var name = $(this).prop('name') || $(this).prop('id');
            if (val[name] && val[name] instanceof Array && $(this).prop('value') in val[name]) {
                $(this).prop('checked', true);
            }
        });
    };
}(jQuery);

var Validator = function () {
	var handleInit = function(){
		$(document).find("form").each(function(){
			var hasValidate = $(this).attr("validate") || false;
			if(hasValidate){
				var validator = $(this).data("validator");
				if(validator == null || validator == undefined){
					$(this).Validform({
						showAllError:true,
						tiptype:function(msg,o,cssctl){
						    //obj指向的是当前验证的表单元素（或表单对象，验证全部验证通过，提交表单时o.obj为该表单对象），
						    //type指示提示的状态，值为1、2、3、4， 1：正在检测/提交数据，2：通过验证，3：验证失败，4：提示ignore状态, 
						    //curform为当前form对象;
						    //cssctl:内置的提示信息样式控制函数，该函数需传入两个参数：显示提示信息的对象 和 当前提示的状态（既形参o中的type）;
							//modalTips(msg);
							$(o.obj).nextAll("span[class*='help-inline']").remove();
							if(o.type == 3){
								$(o.obj).closest("div[class*='control-group']").attr("class","control-group error");
								$(o.obj).after('<span class="help-inline">'+msg+'</span>');
							}else if(o.type == 2){
								$(o.obj).closest("div[class*='control-group']").attr("class","control-group success");
								$(o.obj).after('<span class="help-inline ok valid"></span>');
							}
						}
					});
				}
			}
		});
	}
	
	var handleDestory = function(selector){
		var hasValidate = selector.attr("validate");
		if(hasValidate){
			var validator = selector.data("validator");
			selector[0].validform_inited = null;
			selector.removeData("validator");
		}
	}
	return {
		init : function(){
			handleInit();
		},
		destory : function(selector){
			if(selector == null || selector == undefined || selector == ""){
				$(document).find("form").each(function(){
					handleDestory($(this));
				});
			} else {
				handleDestory($(selector));
			}
		}
	};
}();

// 注册附件下载方法
$(document).on('click','[data-download]',function(e){
	// 取消事件的默认动作
	if (e.preventDefault) {
		// IE以外
		e.preventDefault();
	} else {
		//IE
		e.returnValue = false;
	}
	var dframe = document.createElement("iframe");
	dframe.name = "downloadAttFrame";
	dframe.style.display = "none";
	// 设置frame的编码格式，否则中文的文件名会乱码
	var charset="<html><head><meta charset=\"utf-8\"><title>attachmentDownload<\/title></head><body><\/body><\/html>";
	document.body.appendChild(dframe);
	dframe.contentWindow.document.write(charset); 
	
	var temp = document.createElement("form");
	temp.action = "systemController.do?attachmentDownload";
	temp.method = "post";
	temp.target = "downloadAttFrame";
	temp.style.display = "none";
	var opt = document.createElement("input");
	opt.name = 'url';
	opt.value = $(e.target).attr("href") || $(e.target).data("download");
	temp.appendChild(opt);
	dframe.appendChild(temp);
	temp.submit();
	return false;
});