var formValid;
var toastr = window.top.toastr;
function deleteData(){
	var id=$('#areaCodeId').val();
	
	parent.layer.confirm('确定删除?', {
        icon: 0,
        title: '提示',
        btn: ['确定','取消']
    }, function(index, layero){
        parent.layer.close(index);
        $http.post('/region/areaCode/deleteAreaCode',JSON.stringify({"areaCodeId":id}),function(data){
        	toastr.success('操作成功');
			location.href="/inaction/region/areacode-list.html";
        })
         
    }, function(){

    });
	
	
 
}
function saveRegion(){
	if(!validFormData()){
		return;
	}
	 $http.post('/region/areaCode/updateAreaCode',JSON.stringify(serializeObject($('#regionForm'))),function(data){
     	toastr.success('操作成功');
			location.href="/inaction/region/areacode-list.html";
     })
}
function validFormData(){
	if (!formValid.isAllPass())
        return false;
	return true;
	
	//$('#regionNbrTooltip').tooltip('toggle')
}
function initData(){
	var id=getQueryString('id');
	$http.get('/region/areaCode/getAreaCode/id='+id,{},function(data){
		$('#areaCodeId').val(data.areaCodeId);
		$('#areaCode').val(data.areaCode);
		$('#areaNbr').val(data.areaNbr);
     })
}
 
$(document).ready(function(){
	$('#saveBtn').bind('click',saveRegion);
	initData();
	seajs.use('/vendors/lulu/js/common/ui/Validate', function (Validate) {
		formValid = new Validate($('#regionForm'));
		formValid.immediate();
	});
});