var indexFlag1 = 1;

var TaskTableManaged = function () {
    return {
        init: function () {
        	
            if (!jQuery().dataTable) {
                return;
            }
            
/*            $('#monitoring_plan_task_tb1').dataTable({
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
            
            registDataTable("monitoring_plan_task_tb1",false,"0,4");
            
            $('#monitoring_plan_task_tb2').dataTable({
                "iDisplayLength": 100,
                "bSort": false,
                "bFilter": false,
                "bPaginate": false,
                "bInfo": false
            });
            
            $('#monitoring_plan_task_tb3').dataTable({
                "iDisplayLength": 100,
                "bSort": false,
                "bFilter": false,
                "bPaginate": false,
                "bInfo": false
            });
        }
    };

}();


function addLine(){
	$('#monitoring_plan_task_tb3').dataTable().fnAddData(['<select class="span6 m-wrap" tabindex="1" style="width:150px;"><option value=""></option><optgroup label="南京市"><option>市辖区</option><option>白下区</option><option>玄武区</option></optgroup><optgroup label="无锡市"><option>崇安区</option><option>南长区</option><option>北塘区</option></optgroup></select>',
	                                                      '<select class="span6 m-wrap" tabindex="1" style="width:100px;"><option value=""></option><option>生产基地</option><option>农贸市场</option><option>超市</option></select>',
	                                                      '<select class="span6 m-wrap" tabindex="1" style="width:200px;"><option value=""></option><option>蔬菜</option><option>&nbsp;&nbsp;&nbsp;&nbsp;白菜类</option><option>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;结球白菜</option></select>',
	                                                      '<input type="text" class="span6 m-wrap"/>'
	                                                      ]);
}

function saveTask(){
	$('#monitoring_plan_task_td' + indexFlag1).html('35');
	indexFlag1++;
	$('#monitoring_plan_task_td' + indexFlag1).html('浦口区、玄武区、建邺区');
	indexFlag1++;
	$('#monitoring_plan_task_td' + indexFlag1).html('生产基地、农贸市场');
	indexFlag1++;
	$('#monitoring_plan_task_td' + indexFlag1).html('');
	indexFlag1++;
}