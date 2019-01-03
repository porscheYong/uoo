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
        $.ajax({
			url:'/region/areaCode/deleteAreaCode',
			data:{"areaCodeId":id},
			dataType:'json',
			type:'post',
			success:function(data){
				if(data.state==1000){
					toastr.success('操作成功');
					location.href="/inaction/region/areacode-list.html";
				}else{
					toastr.error('操作失败'+data.message);
				}
			}
		});
    }, function(){

    });
	
	
 
}
function saveRegion(){
	if(!validFormData()){
		return;
	}
	$.ajax({
		type:'POST',
		dataType:'json',
		url:'/region/areaCode/updateAreaCode',
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
function initData(){
	var id=getQueryString('id');
	$.ajax({
		url:'/region/areaCode/getAreaCode/id='+id,
		dataType:'json',
		type:'get',
		success:function(data){
			if(data.state==1000){
				$('#areaCodeId').val(data.data.areaCodeId);
				$('#areaCode').val(data.data.areaCode);
				$('#areaNbr').val(data.data.areaNbr);
			}else{
				toastr.error('加载区号信息失败，请重试');
			}
		}
	});
}
 
$(document).ready(function(){
	$('#saveBtn').bind('click',saveRegion);
	initData();
	seajs.use('/vendors/lulu/js/common/ui/Validate', function (Validate) {
		formValid = new Validate($('#regionForm'));
		formValid.immediate();
	});
});