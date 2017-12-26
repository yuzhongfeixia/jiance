<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<script src="assets/plugins/jquery-1.10.1.min.js" type="text/javascript"></script>
<script src="assets/plugins/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>
<script type="text/javascript" src="plug-in/lodop/LodopFuncs.js"></script>
<object  id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0> 
       <embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed>
</object>
<script type="text/javascript">
var LODOP; //声明为全局变量
var id = "${barcodePrint.id}";
function println() {
//	var id = "${barcodePrint.id}";
	var projectCode = "${barcodePrint.projectCode}";
	var printNumberCopies = "${barcodePrint.printNumberCopies}";
	var printCount = "${barcodePrint.printCount}";
	var printCopies = document.getElementById('printNumberCopies').value;
	var printTime = '';
	var titles =  document.all('myiframe').contentWindow.document.getElementById("titles").value;  
	if(titles.length > 0){
		titles = titles.substring(0,titles.length-1);
	}
	var Options=new Array(); 
	Options=titles.split(",");     

	// 将缓存中的二维码存入db
	$.ajax({
		type : "POST",
		async: false,
		url : "barcodePrintController.do?saveCode&rand=" + Math.random(),
		data : {'id':id,'projectCode':projectCode,'printNumberCopies':printNumberCopies,'printCount':printCount,'title':Options[0]},
		success : function(data) {
			window.opener.fatherRefreshList();
			var d = $.parseJSON(data);
			id = d.attributes.id;
			printTime = d.attributes.printTime;

		}
	});
	
	LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));  
	LODOP.PRINT_INIT("二维码打印");
	//1---纵向打印，固定纸张； 2---横向打印，固定纸张；  
	LODOP.SET_PRINT_PAGESIZE(1,"8cm","7cm","");	
	//var printCopies = document.getElementById('printNumberCopies').value;
	//var titles = document.getElementById('titles').value;
	//var titles = $("#titles",window.opener.document).val();
	//var titles =  document.all('myiframe').contentWindow.document.getElementById("titles").value;           
// 	if(titles.length > 0){
// 		titles = titles.substring(0,titles.length-1);
// 	}
// 	var Options=new Array(); 
// 	Options=titles.split(",");  
	for (var i in Options)    
	{
		if(i%2 == 1){
			for (var j = 0; j < printCopies; j++) {
				LODOP.NewPage();
				LODOP.ADD_PRINT_TEXT("0.2cm","0.2cm",200,15,"${barcodePrint.projectName}");
				LODOP.ADD_PRINT_TEXT("0.8cm","0.2cm",200,15,printTime);
				LODOP.ADD_PRINT_BARCODE("2.5cm","2.5cm",100,100,"QRCode",Options[i]);
				LODOP.ADD_PRINT_TEXT("5cm","2.4cm",120,20,"■"+Options[i]);	
			}
		}else{
			for (var j = 0; j < printCopies; j++) {
				LODOP.NewPage();
				LODOP.ADD_PRINT_TEXT("0.2cm","0.2cm",200,15,"${barcodePrint.projectName}");
				LODOP.ADD_PRINT_TEXT("0.8cm","0.2cm",200,15,printTime);
				LODOP.ADD_PRINT_BARCODE("2.5cm","2.5cm",100,100,"QRCode",Options[i]);
				LODOP.ADD_PRINT_TEXT("5cm","2.4cm",120,20,"●"+Options[i]);
			}
		}
	} 
	LODOP.PREVIEW();
	
	//opener.location="javascript:fatherRefreshList()";
	

	//LODOP.PRINT();
};	
</script>
</head>
<body>
<%-- <input type="hidden" value="${titles }" id="titles"> --%>
<input type="hidden" value="${barcodePrint.printNumberCopies }" id="printNumberCopies">
<%-- <iframe name="myiframe" src="barcodePrintController.do?print&projectCode=${barcodePrint.projectCode }&titles=${titles}&printNumberCopies=${barcodePrint.printNumberCopies }" width="60%" height="400" > </iframe> --%>
<iframe id="myiframe" name="myiframe" src="barcodePrintController.do?print&projectCode=${barcodePrint.projectCode }&printNumberCopies=${barcodePrint.printNumberCopies }&printCount=${barcodePrint.printCount}&id=${barcodePrint.id}" width="60%" height="400" > </iframe>
<div id="span2">
	<input type="button" name="close" onclick="javascript:window.close();" value="关闭" />
	<input type="button" name="print" onclick="println();" value="打印" />
</div>
</body>
</html>