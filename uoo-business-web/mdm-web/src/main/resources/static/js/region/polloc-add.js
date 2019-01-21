var toastr = window.top.toastr;
var nodes = parent.getCurrentSelectedNode();
var node = nodes[0];
var upid = node.id;
var formValid;
function selectParentLoc() {
	parent.layer.open({
        type: 2,
        title: '选中上级区域',
        shadeClose: true,
        shade: 0.8,
        area: ['50%', '80%'],
        maxmin: true,
        content: 'pollocTreeModal-radio.html',
        btn: ['确认', '取消'],
        yes: function(index, layero){
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            checkNode = iframeWin.checkNode;
            parent.layer.close(index);
            $('#parentLoc').val(checkNode[0].name);
            $('#parentLocId').val(checkNode[0].id);
        },
        btn2: function(index, layero){},
        cancel: function(){}
    });
}
$(document).ready(function(){
	if(getQueryString('upLocId')!='0'){
		$('#parentLoc').val(getQueryString('upLocName'));
		$('#parentLocId').val(getQueryString('upLocId'));
	}
	loadLocType();
	
	$('#saveBtn').bind('click',saveRegion);
	seajs.use('/vendors/lulu/js/common/ui/Validate', function (Validate) {
		formValid = new Validate($('#regionForm'));
		formValid.immediate();
	});
	
});
function loadLocType(){
	for(var i=0;i<parent.typeArray.length;i++){
		$('#locType').append("<option value='"+parent.typeArray[i].itemValue+"'>"+parent.typeArray[i].itemCnname+"</option>");
  	}
}

function saveRegion(){
	if(!validFormData()){
		return;
	}
	$http.post('/region/politicalLocation/addPoliticalLocation',JSON.stringify(),function(data){
		toastr.success('操作成功');
		//在父节点增加数据啊
        var treeObj =parent.getTree();
        var upId=$('#parentLocId').val();
        var myNodes=treeObj.getNodesByParam("id",upId,null);
        var newNodes = [{name:$('#locName').val(),id:data.locId,parent:false,open:false,pId:upId.id}];
        if(myNodes.length<=0){
        	//插入到根目录
        	newNodes = treeObj.addNodes(null,-1, newNodes);
        	parent.changeIframe('/inaction/region/polloc-list.html?id='+upid);
        }else{
        	newNodes = treeObj.addNodes(myNodes[0],-1, newNodes);
        	parent.changeIframe('/inaction/region/polloc-list.html?id='+upid);
        }
	});
	 
}
function validFormData(){
	if (!formValid.isAllPass())
        return false;
	var reg =/^\d{1,}$/;
	var locCode=$('#locCode').val();
	if (!reg.test(locCode)){
		$('#locCode').focus();
		$('#locCode').parent().addClass("has-error");
		return false;
	}
	var locName=$('#locName').val();
	if(locName.length<1){
		$('#locName').focus();
		$('#locName').parent().addClass("has-error");
		return false;
	}
	var locAbbr=$('#locAbbr').val();
	if(locAbbr.length<1){
		$('#locAbbr').focus();
		$('#locAbbr').parent().addClass("has-error");
		return false;
	}
	
	return true;
	
	//$('#regionNbrTooltip').tooltip('toggle')
}
