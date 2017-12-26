var indexFlag1 = 1;
var indexFlag2 = 5;
var indexFlag3 = 9;

var ProjectTableManaged = function () {
    return {

        //main function to initiate the module
        init: function () {
            
            if (!jQuery().dataTable) {
                return;
            }

/*            $('#monitoring_plan_project_tb1').dataTable({
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
            
            registDataTable("monitoring_plan_project_tb1",false,"0,4");
            
            $('.date-picker').datepicker({
                rtl : App.isRTL(),
                format: "yyyy-mm-dd"
            });
            
            $(".chosen").chosen();
            
            $('#monitoring_plan_project_btn2').on('click', function(){
            	$('#monitoring_plan_project_txt1').val('2013年江苏省第三次例行监测');
            	$('#monitoring_plan_project_select1').val('1');
            	$('#monitoring_plan_project_txt11').val('05/01/2013 to 06/30/2013');
            	
			}); 
            

            $('#monitoring_plan_project_wizard1').bootstrapWizard({
                'nextSelector': '.button-next',
                'previousSelector': '.button-previous',
                onTabClick: function (tab, navigation, index) {
                    return false;
                },
                onNext: function (tab, navigation, index) {
                    var total = navigation.find('li').length;
                    var current = index + 1;
                    //alert(total + ',' + index);
                    // set wizard title
                    $('.step-title', $('#monitoring_plan_project_wizard1')).text('步骤 ' + (index + 1) + ' / ' + total);
                    // set done steps
                    jQuery('li', $('#monitoring_plan_project_wizard1')).removeClass("done");
                    var li_list = navigation.find('li');
                    for (var i = 0; i < index; i++) {
                        jQuery(li_list[i]).addClass("done");
                    }

                    if (current == 1) {
                        $('#monitoring_plan_project_wizard1').find('.button-previous').hide();
                    } else {
                        $('#monitoring_plan_project_wizard1').find('.button-previous').show();
                    }

                    if (current >= total) {
                        $('#monitoring_plan_project_wizard1').find('.button-next').hide();
                        $('#monitoring_plan_project_wizard1').find('.button-submit').show();
                        //displayConfirm();
                    } else {
                        $('#monitoring_plan_project_wizard1').find('.button-next').show();
                        $('#monitoring_plan_project_wizard1').find('.button-submit').hide();
                    }
                    App.scrollTo($('.page-title'));
                },
                onPrevious: function (tab, navigation, index) {
                    var total = navigation.find('li').length;
                    var current = index + 1;
                    // set wizard title
                    $('.step-title', $('#monitoring_plan_project_wizard1')).text('Step ' + (index + 1) + ' of ' + total);
                    // set done steps
                    jQuery('li', $('#monitoring_plan_project_wizard1')).removeClass("done");
                    var li_list = navigation.find('li');
                    for (var i = 0; i < index; i++) {
                        jQuery(li_list[i]).addClass("done");
                    }
                    if (current == 1) {
                        $('#monitoring_plan_project_wizard1').find('.button-previous').hide();
                    } else {
                        $('#monitoring_plan_project_wizard1').find('.button-previous').show();
                    }

                    if (current >= total) {
                        $('#monitoring_plan_project_wizard1').find('.button-next').hide();
                        $('#monitoring_plan_project_wizard1').find('.button-submit').show();
                    } else {
                        $('#monitoring_plan_project_wizard1').find('.button-next').show();
                        $('#monitoring_plan_project_wizard1').find('.button-submit').hide();
                    }

                    App.scrollTo($('.page-title'));
                },
                onTabShow: function (tab, navigation, index) {
                    var total = navigation.find('li').length;
                    var current = index + 1;
                    var $percent = (current / total) * 100;
                    $('#monitoring_plan_project_wizard1').find('.bar').css({
                        width: $percent + '%'
                    });
                }
            });

            $('#monitoring_plan_project_wizard1').find('.button-previous').hide();
            $('#monitoring_plan_project_wizard1 .button-submit').click(function () {
/*            	$('#monitoring_plan_project_wizard1').find('.button-next').show();
            	$('#monitoring_plan_project_wizard1').find('.button-previous').hide();
            	$('#monitoring_plan_project_wizard1').find('.button-submit').hide();
            	
            	jQuery('li', $('#monitoring_plan_project_wizard1')).removeClass("done");
            	jQuery('li', $('#monitoring_plan_project_wizard1')).removeClass("active");
            	var li_list = $('#monitoring_plan_project_wizard1 .navbar-inner').find('li');
            	jQuery(li_list[0]).addClass("active");
            	$('#monitoring_plan_project_wizard1').find('.bar').css({
                    width: (1 / 6) * 100 + '%'
                });
            	
            	$('#submit_form')[0].reset();*/
            	
            }).hide();
            
/*            $('#monitoring_plan_project_wizard1 #btnClose').click(function () {
            	$('#monitoring_plan_project_wizard1').find('.button-next').show();
            	$('#monitoring_plan_project_wizard1').find('.button-previous').hide();
            	$('#monitoring_plan_project_wizard1').find('.button-submit').hide();
            	
            	jQuery('li', $('#monitoring_plan_project_wizard1')).removeClass("done");
            	jQuery('li', $('#monitoring_plan_project_wizard1')).removeClass("active");
            	var li_list = $('#monitoring_plan_project_wizard1 .navbar-inner').find('li');
            	jQuery(li_list[0]).addClass("active");
            	$('#monitoring_plan_project_wizard1').find('.bar').css({
                    width: (1 / 6) * 100 + '%'
                });
            	
            	$('#submit_form')[0].reset();
            	
            });*/
            
            $('.date-range').daterangepicker(
                {
                    opens: (App.isRTL() ? 'left' : 'right'),
                    format: 'MM/dd/yyyy',
                    separator: ' to ',
                    startDate: Date.today().add({
                        days: -29
                    }),
                    endDate: Date.today(),
                    minDate: '01/01/2012',
                    maxDate: '12/31/2014',
                }
            );
            
            $('#monitoring_plan_project_tb2').dataTable({
                "iDisplayLength": 100,
                "bSort": false,
                "bFilter": false,
                "bPaginate": false,
                "bInfo": false
            });
            
            $('#monitoring_plan_project_tb3').dataTable({
                "iDisplayLength": 100,
                "bSort": false,
                "bFilter": false,
                "bPaginate": false,
                "bInfo": false
            });
            
        }

    };

}();

function ConfirmContry(){
	$('#monitoring_plan_project_td' + indexFlag1).html('浦口区(15)、玄武区(15)、建邺区(10)');
	$('#monitoring_plan_project_txt' + (indexFlag1 + 1)).val('35');
	$('#monitoring_plan_project_span1').html(35 * indexFlag1);
	
	$('#monitoring_plan_project_tb2').dataTable().fnAddData(['<select class="span6 m-wrap" tabindex="1" style="height:10px; width:200px;"><option value=""></option><option>南京市</option><option>无锡市</option><option>徐州市</option></select>', '', '<input  type="text" class="span6 m-wrap" style="width:100px;"/>']);

	indexFlag1++;
}

function ConfirmWR(){
	$('#monitoring_plan_project_td' + indexFlag2).html('甲胺磷、氧乐果、甲拌磷、对硫磷、甲基对硫磷、甲基异柳磷、水胺硫磷');
	indexFlag2++;
}

function ConfirmWRP(){
	$('#monitoring_plan_project_td' + indexFlag3).html('蔬菜类');
	$('#monitoring_plan_project_tb3').dataTable().fnAddData(['<span class="span6 m-wrap"></span>','']);
	indexFlag3++;
}
