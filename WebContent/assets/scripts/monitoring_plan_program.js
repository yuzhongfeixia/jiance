var ProgramTableManaged = function () {
    return {

        //main function to initiate the module
        init: function () {
            
            if (!jQuery().dataTable) {
                return;
            }

/*            $('#monitoring_plan_program_tb1').dataTable({
                "aLengthMenu": [
                    [5, 15, 20, -1],
                    [5, 15, 20, "All"] // change per page values here
                ],
                // set the initial value
                "iDisplayLength": 5,
                "bSort": false,
                "bFilter": false,
                "bLengthChange": false,
                "bInfo": false,
                //"sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span6'i><'span6'p>>",
                "sPaginationType": "bootstrap",
                "oLanguage": {
                    "sLengthMenu": "_MENU_ records per page",
                    "oPaginate": {
                    	"sPrevious": "Prev",
                        "sNext": "Next"
                    }
                }
            });*/
            
            registDataTable("monitoring_plan_program_tb1",false,"0,4");
            
            $('#monitoring_plan_program_btn1').on('click', function(){
            	$('#monitoring_plan_program_txt1').val('');
            	$('#monitoring_plan_program_select1').val('');
            	$('#monitoring_plan_program_textarea1').val('');
            	$("#monitoring_plan_program_btn5").css("display","inline");
			}); 
            
            $('#monitoring_plan_program_btn2').on('click', function(){
            	$('#monitoring_plan_program_txt1').val('2013年江苏省农产品质量安全监督抽查方案');
            	$('#monitoring_plan_program_select1').val('2');
            	$('#monitoring_plan_program_textarea1').val('');
            	$("#monitoring_plan_program_btn5").css("display","inline");
			}); 
            
            $('#monitoring_plan_program_btn3').on('click', function(){
            	$('#monitoring_plan_program_txt1').val('2013年全省农产品质量安全例行监测');
            	$('#monitoring_plan_program_select1').val('1');
            	$('#monitoring_plan_program_textarea1').val('');
            	$("#monitoring_plan_program_btn5").css("display","none");
			}); 
            
            $('#monitoring_plan_program_btn4').on('click', function(){
            	$('#monitoring_plan_program_txt1').val('2012年江苏省农产品质量安全普查方案');
            	$('#monitoring_plan_program_select1').val('3');
            	$('#monitoring_plan_program_textarea1').val('');
            	$("#monitoring_plan_program_btn5").css("display","none");
			}); 
            
        }

    };

}();