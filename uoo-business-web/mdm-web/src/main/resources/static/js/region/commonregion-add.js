var toastr = window.top.toastr;
var nodes = parent.getCurrentSelectedNode();
var node = nodes[0];
var upid = node.id;
var formValid;
function loadRegionType(){
	for(var i=0;i<parent.typeArray.length;i++){
		$('#regionType').append("<option value='"+parent.typeArray[i].itemValue+"'>"+parent.typeArray[i].itemCnname+"</option>");
  	}
}
function saveRegion(){
	if(!validFormData()){
		return;
	}
	$http.post('/region/commonRegion/addCommonRegion',JSON.stringify(serializeObject($('#regionForm'))),function(data){
		toastr.success('操作成功');
		//在父节点增加数据啊
		var treeObj =parent.getTree();
		var upId=$('#parentRegionId').val();
		var myNodes=treeObj.getNodesByParam("id",upId,null);
		var newNodes = [{name:$('#regionName').val(),id:data.commonRegionId,parent:false,open:false,pId:upId.id}];
		if(myNodes.length<=0){
			//插入到根目录
			newNodes = treeObj.addNodes(null,-1, newNodes);
			parent.changeIframe('/inaction/region/commonregion-list.html?id='+upid);
		}else{
			newNodes = treeObj.addNodes(myNodes[0],-1, newNodes);
			parent.changeIframe('/inaction/region/commonregion-list.html?id='+upid);
		}
	});
	 
}
function validFormData(){
	if (!formValid.isAllPass())
        return false;
	
	var regionName=$('#regionName').val();
	if(regionName.length<1){
		$('#regionName').focus();
		$('#regionName').parent().addClass("has-error");
		return false;
	}
	var reg =/^\d{1,}$/;
	var regionNbr=$('#regionNbr').val();
	/*if (!reg.test(regionNbr)){
		$('#regionNbr').focus();
		$('#regionNbr').parent().addClass("has-error");
		return false;
	}*/
	return true;
	
	//$('#regionNbrTooltip').tooltip('toggle')
}
function selectAreaCode(){
	parent.layer.open({
        type: 2,
        title: '选中关联区号信息',
        shadeClose: true,
        shade: 0.8,
        area: ['50%', '80%'],
        maxmin: true,
        content: 'areacode-list.html?showCheck=1',
        btn: ['确认', '取消'],
        yes: function(index, layero){
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            var checkAreaCode = iframeWin.checkAreaCode;
            parent.layer.close(index);
            $('#areaCode').val(checkAreaCode.areaCode);
            $('#areaCodeId').val(checkAreaCode.areaCodeId);
        },
        btn2: function(index, layero){},
        cancel: function(){}
    });
}
function selectParentRegion(){
	parent.layer.open({
        type: 2,
        title: '选中上级区域',
        shadeClose: true,
        shade: 0.8,
        area: ['50%', '80%'],
        maxmin: true,
        content: 'commonRegionTreeModal.html',
        btn: ['确认', '取消'],
        yes: function(index, layero){
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            checkNode = iframeWin.checkNode;
            parent.layer.close(index);
            $('#parentRegion').val(checkNode.name);
            $('#parentRegionId').val(checkNode.id);
        },
        btn2: function(index, layero){},
        cancel: function(){}
    });
}
function selectLocs(){
	parent.layer.open({
		type: 2,
		title: '选中上级区域',
		shadeClose: true,
		shade: 0.8,
		area: ['50%', '80%'],
		maxmin: true,
		content: 'pollocTreeModal.html',
		btn: ['确认', '取消'],
		yes: function(index, layero){
			//获取layer iframe对象
			var iframeWin = parent.window[layero.find('iframe')[0].name];
			checkNode = iframeWin.checkNode;
			parent.layer.close(index);
			if(checkNode.length>0){
				var polLocIds="",polLocNames="";
				for(var i=0;i<checkNode.length;i++){
					polLocIds+=checkNode[i].id;
					polLocNames+=checkNode[i].name;
					if(i!=checkNode.length-1){
						polLocIds+=",";
						polLocNames+=",";
					}
				}
				$('#polLocIds').val(polLocIds);
				$('#locDesc').val(polLocNames);
				
			}
		},
		btn2: function(index, layero){},
		cancel: function(){}
	});
}

$(document).ready(function(){
	loadRegionType();
	if(getQueryString('upRegionId')!='0'){
		
		$('#parentRegion').val(getQueryString('upRegionName'));
		$('#parentRegionId').val(getQueryString('upRegionId'));
	}
	$('#saveBtn').bind('click',saveRegion);
	seajs.use('/vendors/lulu/js/common/ui/Validate', function (Validate) {
		formValid = new Validate($('#regionForm'));
		formValid.immediate();
	});
});