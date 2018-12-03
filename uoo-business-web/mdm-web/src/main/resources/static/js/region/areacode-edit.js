var formValid;
function deleteData(){
	var id=$('#areaCodeId').val();
	if(confirm('确定删除这条数据？')){
		$.ajax({
			url:'/region/areaCode/deleteAreaCode',
			data:{"areaCodeId":id},
			dataType:'json',
			type:'post',
			success:function(data){
				alert(data.state==1000?'删除成功':data.message);
				if(data.state==1000){
					location.href="/inaction/region/areacode-list.html";
				}
			}
		});
	}
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
				alert('编辑成功');
				location.href="/inaction/region/areacode-list.html";
			}else{
				alert(data.message);
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
				$('#areaCodeNbr').val(data.data.areaCodeNbr);
			}else{
				alert('加载区号信息失败，请重试');
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