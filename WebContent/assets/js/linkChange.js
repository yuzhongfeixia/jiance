var LinkChange = function () {
	var handleInit = function() {
		$(document).find('[link-Change="true"]').each(function(){
			var elem = $(this);
			var selector = $(elem).attr("data-set");
			if(null == selector || "" == selector || "undefined" == selector){
				return;
			}
			var len = $(selector).length;
			if(len > 0){
				$(elem).on('change', function(){
					var optionsHTML = getSysAreaSelectList($(elem).val(), $($(selector)[0]).attr("defaultVal"));
					$($(selector)[0]).html(optionsHTML);
				});
				$(selector).each(function(i){
					$(this).on('change', function(){
						var optionsHTML = getSysAreaSelectList($(this).val(), $($(selector)[i+1]).attr("defaultVal"));
						$($(selector)[i+1]).html(optionsHTML);
					});
				});
			}
		});
	};
	
	return {
		init : function(){
			handleInit();
		}
	};
}();
