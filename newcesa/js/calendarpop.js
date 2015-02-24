<!--

function callCalendar(obj){
	/* 달력 컨트롤 */                           
	$("#"+obj.name).datepicker({ 
			dateFormat:'yy-mm-dd',
			nextText:'>',
			prevText:'<'
	   });
$("#"+obj.name).focus();
}
	/* 달력 컨트롤 끝 */            
	
$(document).ready(function(){
	$("#layerPop").draggable();

	$("#btn").click(function(){ 
	   $.blockUI({message: $('#jqueryPop'),css:{ width:'500px',border:'none' ,top:'150px'}});
	});

	$("#unblock").click(function(){ 
	   $.unblockUI();
	});

	$("#layerPop_btn").click(function(){ 
		var x = $(this).offset().left;
		var y = $(this).offset().top;
		var y_minus=550;
		var x_minus=200;
		$("#layerPop").css({display:'block',  position:'absolute', top:y-y_minus , left:x-x_minus });
	});

	$("#closeLayer").click(function(){
		$("#layerPop").css({display:'none'});
	});




});

-->
