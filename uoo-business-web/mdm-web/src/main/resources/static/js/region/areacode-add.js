var formValid;
var toastr = window.top.toastr;
function saveRegion(){
	if(!validFormData()){
		return;
	}
	$.ajax({
		type:'POST',
		dataType:'json',
		url:'/region/areaCode/addAreaCode',
		data:$('#regionForm').serialize(),
		success:function(data){
			if(data.state==1000){
				toastr.success('操作成功');
				location.href="/inaction/region/areacode-list.html";
			}else{
				toastr.error('操作失败'+data.message);
			}
		}
			
	});
	 
}
function validFormData(){
	if (!formValid.isAllPass())
        return false;
	return true;
	
	//$('#regionNbrTooltip').tooltip('toggle')
}
 
$(document).ready(function(){
	$('#saveBtn').bind('click',saveRegion);
	seajs.use('/vendors/lulu/js/common/ui/Validate', function (Validate) {
		formValid = new Validate($('#regionForm'));
		formValid.immediate();
	});
});