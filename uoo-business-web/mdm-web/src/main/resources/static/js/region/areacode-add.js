var formValid;
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
				parent.layer.confirm('操作成功', {
			        icon: 0,
			        title: '提示',
			        btn: ['确定' ]
			    }, function(index, layero){
			        parent.layer.close(index);
			        location.href="/inaction/region/areacode-list.html";
			    }, function(){
			    });
			}else{
				parent.layer.confirm('操作失败，'+data.message, {
			        icon: 0,
			        title: '提示',
			        btn: ['确定' ]
			    }, function(index, layero){
			        parent.layer.close(index);
			    }, function(){
			    });
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