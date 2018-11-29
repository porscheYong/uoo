
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
				alert('添加成功');
				location.href="/inaction/region/areacode-list.html";
			}else{
				alert(data.message);
			}
		}
			
	});
	 
}
function validFormData(){
	return true;
	
	//$('#regionNbrTooltip').tooltip('toggle')
}
 
$(document).ready(function(){
	$('#saveBtn').bind('click',saveRegion);
	
});