var locIds=null,locNames=null,curUpId=0;
function loadRegionType(){
	for(var i=0;i<parent.typeArray.length;i++){
		$('#regionType').append("<option value='"+parent.typeArray[i].itemValue+"'>"+parent.typeArray[i].itemCnname+"</option>");
  	}
}
function deleteData(){
	var id=$('#regionId').val();
	var parentRegionId=$('#parentRegionId').val();
	if(confirm('确定删除这条数据？')){
		$.ajax({
			url:'/region/commonRegion/deleteCommonRegion',
			data:{"commonRegionId":id},
			dataType:'json',
			type:'post',
			success:function(data){
				alert(data.state==1000?'删除成功':data.message);
				//getRegionList(listId);
				if(data.state==1000){
					var zTree=parent.getTree();
					var snodes= parent.getCurrentSelectedNode()[0];
					var nodes=snodes.children;
					if(nodes!=null&&nodes.length>0)
					for(var i=0;i<nodes.length;i++){
						if(nodes[i].id==id)zTree.removeNode(nodes[i], parent.getCurrentSelectedNode()[0], "prev");
					}
					//如果选中的id=当前id  那么调到上一级id 如果不等于就不管  如果上一级id=0 那么。。。
					if(parentRegionId==0||parentRegionId=='0'){
						zTree.removeNode(snodes);
						parent.changeIframe('/inaction/region/none.html');
						return;
					}
					if(snodes.id==id){
						zTree.removeNode(snodes);
						zTree.selectNode(zTree.getNodeByParam("id", parentRegionId, null));
						parent.changeIframe('/inaction/region/commonregion-list.html?id=' + parentRegionId);
					}else{
						zTree.removeNode(zTree.getNodeByParam("id", id, null));
						//不等于。。。
						parent.changeIframe('/inaction/region/commonregion-list.html?id=' + snodes.id);
					}
					
				}
			}
		});
	}
	
}
function get(id){
	$.ajax({
		url:'/region/commonRegion/getCommonRegion/id='+id,
		dataType:'json',
		type:'get',
		success:function(data){
			console.log(data);
			if(data.state==1000){
				curUpId=data.data.PARENT_REGION_ID
				initData(data.data);
				loadUpRegionList(curUpId);
			}else{
				alert(data.message);
			}
		}
	});
}
function initData(data){
	$('#regionId').val(data.commonRegionId);
	$('#parentRegion').val(getQueryString('upRegionName'));
	$('#parentRegionId').val(getQueryString('upRegionId'));
	$('#regionNbr').val(data.regionNbr);
	$('#regionName').val(data.regionName);
	$('#regionType').val(data.regionType);
	$('#regionDesc').val(data.regionDesc);
	$('#areaCodeId').val(data.areaCode.areaCodeId);
	$('#areaCode').val(data.areaCode.areaCode);
	$('#polLocIds').val(data.locationIds);
	$('#locDesc').val(data.locationNames);
	
}
function inArr(arr,target){
	for(var j=0;j<arr.length;j++){
		if(target==arr[j]){
			return true;
		}
	}
	return false;
}
var nodes = parent.getCurrentSelectedNode();
var node = nodes[0];
var upid = node.id;
/*expUpRegionSelect();
function expUpRegionSelect() {
	deepTree(0, upid);
}*/
var st=0;
$(document).ready(function(){
	loadRegionType();
	var rid = getQueryString('id');
	get(rid);
	$('#saveBtn').bind('click',saveRegion);
	initLocTree();
	 
});
 
function saveRegion(){
	if(!validFormData()){
		return;
	}
	 
	$.ajax({
		type:'POST',
		dataType:'json',
		url:'/region/commonRegion/updateCommonRegion',
		data:$('#regionForm').serialize(),
		success:function(data){
			if(data.state==1000){
				//1 从列表进去的  2 从顶部进去的 如果移到别的up去了 那么就要两部操作 1 删除当前父节点的数据 2增加新节点的数据
				//列表进去的开var treeObj =parent.getTree();始！
				var treeObj =parent.getTree();
				/*var selectedUpId=$('#parentRegionId').val();
				var selected=treeObj.getSelectedNodes()[0];
				var newUpNodes=treeObj.getNodesByParam("id",selectedUpId,null);
				var oldUpNodes=treeObj.getNodesByParam("id",parseInt(curUpId),null);
				//查一下 有没有子节点，子节点也要移走
				var newNodes = [{name:$('#regionName').val(),id:$('#regionId').val(),parent:false,open:false,pId:selectedUpId}];
				treeObj.removeNode(treeObj.getNodesByParam("id",$('#regionId').val(),null));
				if(newUpNodes.length<=0){
					//插入到根目录
					newNodes = treeObj.addNodes(null,-1, newNodes);
				}else{
					newNodes = treeObj.addNodes(newUpNodes[0],-1, newNodes);
					parent.changeIframe('/inaction/region/commonregion-list.html?id='+upId);
				}
				
				if(parseInt(selectedUpId)==parseInt(curUpId)){
					
				}*/
				var updateNode=treeObj.getNodesByParam("id",$('#regionId').val())[0];
				updateNode.name=$('#regionName').val();
				treeObj.updateNode(updateNode);
				//parent.changeIframe('/inaction/region/commonregion-list.html?id='+upid);
				parent.changeIframe('/inaction/region/commonregion-list.html?id='+upid);
			}else{
				alert(data.message);
			}
		}
			
	});
	 
}
function validFormData(){
	var regionName=$('#regionName').val();
	if(regionName.length<1){
		$('#regionName').focus();
		$('#regionName').parent().addClass("has-error");
		return false;
	}
	var reg =/^\d{1,}$/;
	var regionNbr=$('#regionNbr').val();
	if (!reg.test(regionNbr)){
		$('#regionNbr').focus();
		$('#regionNbr').parent().addClass("has-error");
		return false;
	}
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