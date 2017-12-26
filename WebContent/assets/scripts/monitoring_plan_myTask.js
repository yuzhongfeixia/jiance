var MyTaskTableManaged = function () {
    return {
        init: function () {
        	
            if (!jQuery().dataTable) {
                return;
            }
            
/*            $('#monitoring_plan_myTask_tb1').dataTable({
                "iDisplayLength": 5,
                "bSort": false,
                "bFilter": false,
                "bLengthChange": false,
                "bInfo": false,
                "sPaginationType": "bootstrap",
                "oLanguage": {
                    "sLengthMenu": "_MENU_ records per page",
                    "oPaginate": {
                    	"sPrevious": "Prev",
                        "sNext": "Next"
                    }
                }
            });*/
            
            registDataTable("monitoring_plan_myTask_tb1",false,"0,4");
        }
    };

}();