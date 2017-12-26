(function ($) {
	$.fn.extend({
		confirmModal: function (options) {
			var templete = '<div class="row-fluid">'
				+	'<div class="span12">'
				+		'<div class="portlet box popupBox_usual">'
				+			'<div class="portlet-title">'
				+				'<div class="caption">'
				+					'<span class="hidden-480">#TITLE#</span>'
				+				'</div>'
				+				'<div class="tools">'
				+					'<a data-dismiss="modal"  class="closed"></a>'
				+				'</div>'
				+			'</div>'
				+			'<div class="portlet-body">'
				+				'<div class="portlet-body-div" style="font-size:15px;padding:5px 15px 0px 15px;">'
				+					'#BODY#'
				+				'</div>'
				+				'<div class="modal-footer">'
				+					'#FOOT#'
				+				'</div>'
				+			'</div>'
				+		'</div>'
				+	'</div>'
				+'</div>';
	
			var defaults = {
				title : '请确认操作',
				tsbody : '',
				bodyIsText : true,
				hasTitleClose : true,
				hasFootClose : true,
				hasFootConfim : true,
				footClose : '<button type="button" data-dismiss="modal" class="btn popclose">关闭</button>',
				footConfim : '<button type="button" class="btn popenter popConfim">确定</button>',
				customButton: '',
				callback : null
			};
			var html = templete;
			var options = $.extend(defaults, options);
			html = html.replace('#TITLE#', options.heading || options.title);
			html = html.replace('#BODY#', options.body || options.tsbody);
			html = html.replace("#FOOT#", options.footClose + options.footConfim + options.customButton);
			
			$(this).html(html);
			
			if(!options.bodyIsText){
				$(this).find('div[class="portlet-body-div"]').attr('style', '');
			}
			
			if(!options.hasTitleClose){
				$(this).find('div[class="tools"]').remove();
			}
			if(!options.hasFootClose){
				$(this).find('button[class="btn popclose"]').remove();
			}
			if(!options.hasFootConfim){
				$(this).find('button[class="btn popenter popConfim"]').remove();
			}
			
			$(this).modal('show');
			
			var context = $(this);
			
			$($(context).find('button[class="btn popenter popConfim"]')).off('click').on('click', function(){
				var result = true;
				if(options.callback != null && typeof(options.callback) == "function"){
					result = options.callback() ;
				} else {
					result = eval(options.callback)();
				}
				
				if(false != result){
					$(context).modal('hide');
				}
			});
		}

	});

})(jQuery);