var formValid;
var toastr = window.top.toastr;
function saveRegion(){
	if(!validFormData()){
		return;
	}
	$http.post('/region/areaCode/addAreaCode',JSON.stringify(serializeObject($('#regionForm'))),function(data){
		toastr.success('操作成功');
		location.href="/inaction/region/areacode-list.html";
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