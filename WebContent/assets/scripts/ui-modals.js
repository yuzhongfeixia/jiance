var UIModals = function () {

    
    var initModals = function () {
       
       	$.fn.modalmanager.defaults.resize = true;
		$.fn.modalmanager.defaults.spinner = '<div class="loading-spinner fade" style="width: 200px; margin-left: -100px;"><img src="assets/img/ajax-modal-loading.gif" align="middle">&nbsp;<span style="font-weight:300; color: #eee; font-size: 18px; font-family:Open Sans;">&nbsp;Loading...</div>';


       	var $modal = $('#ajax-modal');
 
		$('#modal_ajax_demo_btn').on('click', function(){
		  // create the backdrop and wait for next modal to be triggered
			var pageContent = $('.page-content');
			App.blockUI(pageContent, false);
		     $modal.load('ui_modals_ajax_sample.html', '', function(){
		      $modal.modal();
		      App.unblockUI(pageContent);
		    });
		});
		 
		$modal.on('click', '.update', function(){
			var pageContent = $('.page-content');
			App.blockUI(pageContent, false);
		    $modal
		      .modal('loading')
		      .find('.modal-body')
		        .prepend('<div class="alert alert-info fade in">' +
		          'Updated!<button type="button" class="close" data-dismiss="alert"></button>' +
		        '</div>');
		    App.unblockUI(pageContent);
		}); 
       
    }

    return {
        //main function to initiate the module
        init: function () {
            initModals();
        }

    };

}();