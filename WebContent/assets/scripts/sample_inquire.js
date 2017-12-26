var SampleInquireTableManaged = function () {
    return {
        init: function () {
        	
            if (!jQuery().dataTable) {
                return;
            }
            
/*            var nCloneTh = document.createElement( 'th' );
            var nCloneTd = document.createElement( 'td' );
            nCloneTd.innerHTML = '<span class="row-details row-details-close"></span>';
             
            $('#sample_inquire_tb2 thead tr').each( function () {
                this.insertBefore( nCloneTh, this.childNodes[0] );
            } );
             
            $('#sample_inquire_tb2 tbody tr').each( function () {
                this.insertBefore(  nCloneTd.cloneNode( true ), this.childNodes[0] );
            } );
            
            $('#sample_inquire_tb1').dataTable({
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
            });
            
            var oTable = $('#sample_inquire_tb2').dataTable({
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
            });
            
            $('#sample_inquire_tb2').on('click', ' tbody td .row-details', function () {
                var nTr = $(this).parents('tr')[0];
                if ( oTable.fnIsOpen(nTr) )
                {
                     This row is already open - close it 
                    $(this).addClass("row-details-close").removeClass("row-details-open");
                    oTable.fnClose( nTr );
                }
                else
                {
                     Open this row                 
                    $(this).addClass("row-details-open").removeClass("row-details-close");
                    oTable.fnOpen( nTr, fnFormatDetails(oTable, nTr), 'details' );
                }
            });*/
        }
    };

}();

function fnFormatDetails ( oTable, nTr )
{
    var aData = oTable.fnGetData( nTr );
    var sOut = '<table>';
    sOut += '<tr><td>条码:</td><td colspan="3">1cc04342-998e-4f08-aae1-35a67b5d4fb3</td></tr>';
    sOut += '<tr><td>样品类别:</td><td>蔬菜-白菜类</td><td>样品名称:</td><td>大白菜</td></tr>';
    sOut += '<tr><td>抽样地区:</td><td>苏州吴中区</td><td>抽样环节:</td><td>批发市场</td></tr>';
    sOut += '<tr><td>受检单位:</td><td>南环桥批发市场A001</td><td>法人代表:</td><td></td></tr>';
    sOut += '<tr><td>通讯地址:</td><td></td><td>联系电话:</td><td></td></tr>';
    sOut += '<tr><td>邮政编码:</td><td></td><td>企业性质:</td><td></td></tr>';
    sOut += '<tr><td>企业规模:</td><td></td><td>主管单位:</td><td></td></tr>';
    sOut += '<tr><td>规格型号:</td><td></td><td>生产批号:</td><td></td></tr>';
    sOut += '<tr><td>检验类别:</td><td></td><td>抽样时间:</td><td></td></tr>';
    sOut += '</table>';
     
    return sOut;
}