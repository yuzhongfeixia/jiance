//bPaginate: 是否分页，默认为 true，分页
//iDisplayLength : 每页的行数，每页默认数量:10
//sPaginationType: 分页样式，支持两种内置方式，two_button 和 full_numbers, 默认使用 two_button。
//bLengthChange : 是否允许用户通过一个下拉列表来选择分页后每页的行数。行数为 10，25，50，100。这个设置需要 bPaginate 支持。默认为 true。
//bFilter: 启用或禁止数据过滤，默认为 true。 注意，如果使用过滤功能，但是希望关闭默认的过滤输入框，应使用 sDom
//bInfo: 允许或者禁止表信息的显示，默认为 true，显示信息。


<script type="text/javascript">
  $(document).ready(function() {
	$('#example').dataTable({
          "bProcessing": true
          <%#*,iDisplayLength :5%>
          ,sPaginationType: "full_numbers" // 分页方式
          ,bLengthChange: true
          ,"oLanguage": {                          // 汉化
                "sLengthMenu": "每页显示 _MENU_ 条记录",
                "sZeroRecords": "没有检索到数据",
                "sInfo": "当前数据为从第 _START_ 到第 _END_ 条数据；总共有 _TOTAL_ 条记录",
                "sInfoEmtpy": "没有数据",
                "sProcessing": "正在加载数据...",
                "oPaginate": {
                    "sFirst": "首页",
                    "sPrevious": "前页",
                    "sNext": "后页",
                    "sLast": "尾页"
                }
            }
            , "aoColumnDefs": [
              {
                  "fnRender": function ( oObj ) {
                      // 获取了行 oObj
                      return oObj.aData[0] +' '+ oObj.aData[3];
                  },
                  "aTargets": [ 0 ]// 把第一列改掉
              },
              { "bVisible": false,  "aTargets": [ 3 ] },// 隐藏
              { "sClass": "center", "aTargets": [ 4 ] }// 排列
            ]
           , "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
            /* Append the grade to the default row class name */
            // nRow行对像,aData行的对像 iDisplayIndex行数
            if ( aData[1] == "aaa" )// 第二列等于aaa
            {
                $('td:eq(4)', nRow).html('A');// 第四列改为A
            }
            return nRow;
        },
        "aoColumnDefs": [ {
                "sClass": "center",// 排列
                "aTargets": [ 2, 3 ]// 二列跟三列
        } ]
          ,"fnFooterCallback": function ( nRow, aaData, iStart, iEnd, aiDisplay ) {  // 列的总数
																						// 计算
            /*
			 * Calculate the total market share for all browsers in this table
			 * (ie inc. outside the pagination)
			 */
            var iTotalMarket = 0;
            for ( var i=0 ; i<aaData.length ; i++ )
            {
                iTotalMarket += aaData[i][1]*1;
            }

            /* Calculate the market share for browsers on this page */
            var iPageMarket = 0;
            for ( var i=iStart ; i<iEnd ; i++ )
            {
                iPageMarket += aaData[ aiDisplay[i] ][1]*1;
            }
            /* Modify the footer row to match what we want */
            var nCells = nRow.getElementsByTagName('th');
            nCells[1].innerHTML = parseInt(iPageMarket * 100)/100 +
                '% ('+ parseInt(iTotalMarket * 100)/100 +'% total)';
        }
        







        
        });
  
} );
var giCount = 1;
function fnClickAddRow() {
    $('#example').dataTable().fnAddData( [
        giCount+".1",
        giCount+".2",
        giCount+".3",
        giCount+".4",
        giCount+".4"] );

    giCount++;
}
</script>




删除行
        $("#example tbody").click(function(event) {
            $(oTable.fnSettings().aoData).each(function (){
                $(this.nTr).removeClass('row_selected');
            });
            $(event.target.parentNode).addClass('row_selected');
        });

        /* Add a click handler for the delete row */
        $('#delete').click( function() {
            var anSelected = fnGetSelected( oTable );
            oTable.fnDeleteRow( anSelected[0] );
        } );
function fnGetSelected( oTableLocal )
{
    var aReturn = new Array();
    var aTrs = oTableLocal.fnGetNodes();

    for ( var i=0 ; i<aTrs.length ; i++ )
    {
        if ( $(aTrs[i]).hasClass('row_selected') )
        {
            aReturn.push( aTrs[i] );
        }
    }
    return aReturn;
}
======

$(document).ready(function () {
        var oTable = $('#example').dataTable().makeEditable({
                            sUpdateURL: "UpdateData.php",
                            sAddURL: "AddData.php",
                            sDeleteURL: "DeleteData.php",
                            oAddNewRowButtonOptions: { 
				label: "Add...",
                                icons: { primary: 'ui-icon-plus' }
                            },
                            oDeleteRowButtonOptions: {
				label: "Remove",
                                icons: { primary: 'ui-icon-trash' }
                            },
                            oAddNewRowOkButtonOptions: {
				label: "Confirm",
                                icons: { primary: 'ui-icon-check' },
                                name: "action",
                                value: "add-new"
                            },
                            oAddNewRowCancelButtonOptions: { 
				label: "Close",
                                class: "back-class",
                                name: "action",
                                value: "cancel-add",
                                icons: { primary: 'ui-icon-close' }
                            },
                            oAddNewRowFormOptions: {
				title: 'Add a new browser - form',
                                show: "blind",
                                hide: "explode"
                            }
        });

    });
	