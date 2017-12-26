!function ($) {
	$.fn.sysUtil = function () {
		var publish = function(id){
			publishmark = 1;
			var url = "standardVersionController.do?save&publish=true";
			if(isStoped(id)){
				alert(dataJson.msg);
				//modalAlert("已经停用的版本,不能再进行发布操作!");
			} else {
				if(!isPublished(id)){
					$("#ajax-modal").confirmModal({
						heading: '请确认操作',
						body: '确定发布该版本吗?',
						callback: function () {
							$.ajax({
								type : 'POST',
								url : url,
								data : {'id':id,'publishmark':publishmark},
								success : function(data) {
									var dataJson = eval('(' + data + ')');
									if(dataJson.success){
										refreshList();
										alert(dataJson.msg);
										//modalTips(dataJson.msg);
									} else {
										alert(dataJson.msg);
										//modalTips(dataJson.msg);
									}
								}
							});
						}
					});
				} else {
					alert("已经发布的版本，不需要重新发布!");
					//modalTips("已经发布的版本，不需要重新发布!");
				}
			}
		};
		
		return {
			init : function(type){
				handleInit(type);
			}
		};
	}();
	
}(jQuery);