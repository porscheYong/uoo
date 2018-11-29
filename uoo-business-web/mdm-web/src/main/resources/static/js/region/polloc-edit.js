var locIds=null,locNames=null,curUpId=0;
function deleteData(){
	var id=$('#locId').val();
	var upRegionId=$('#parentLocId').val();
	if(confirm('确定删除这条数据？')){
		$.ajax({
			url:'/region/politicalLocation/deletePoliticalLocation',
			data:{"locId":id},
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
					if(upRegionId==0||upRegionId=='0'){
						zTree.removeNode(snodes);
						parent.changeIframe('/inaction/region/none.html');
						return;
					}
					if(snodes.id==id){
						zTree.removeNode(snodes);
						zTree.selectNode(zTree.getNodeByParam("id", upRegionId, null));
						parent.changeIframe('/inaction/region/polloc-list.html?id=' + upRegionId);
					}else{
						zTree.removeNode(zTree.getNodeByParam("id", id, null));
						//不等于。。。
						parent.changeIframe('/inaction/region/polloc-list.html?id=' + snodes.id);
					}
					
				}
			}
		});
	}
	
}
function get(id){
	$.ajax({
		url:'/region/politicalLocation/getPoliticalLocation/id='+id,
		dataType:'json',
		type:'get',
		success:function(data){
			console.log(data);
			if(data.state==1000){
				curUpId=data.data.parentLocId
				loadLocType();
				initData(data.data);
			}else{
				alert(data.message);
			}
		}
	});
}
function initData(data){
	$('#locId').val(data.locId);
	$('#locCode').val(data.locCode);
	$('#locName').val(data.locName);
	$('#locAbbr').val(data.locAbbr);
	$('#locType').val(data.locType);
	$('#locDesc').val(data.locDesc);
	$('#parentLoc').val(getQueryString('parentLocName'));
	$('#parentLocId').val(getQueryString('parentLocId'));
}
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
	var rid = getQueryString('id');
	get(rid);
	$('#saveBtn').bind('click',saveRegion);
	initLocTree();
	 
});
function loadLocType(){
	for(var i=0;i<parent.typeArray.length;i++){
		$('#locType').append("<option value='"+parent.typeArray[i].itemValue+"'>"+parent.typeArray[i].itemCnname+"</option>");
  	}
}
function validFormData(){
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
function saveRegion(){
	if(!validFormData()){
		return;
	}
	$('#parentLocId').removeAttr('disabled');
	$.ajax({
		type:'POST',
		dataType:'json',
		url:'/region/politicalLocation/updatePoliticalLocation',
		data:$('#regionForm').serialize(),
		success:function(data){
			if(data.state==1000){
				//1 从列表进去的  2 从顶部进去的 如果移到别的up去了 那么就要两部操作 1 删除当前父节点的数据 2增加新节点的数据
				//列表进去的开var treeObj =parent.getTree();始！
				var treeObj =parent.getTree();
				/*var selectedUpId=$('#upRegionId').val();
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
				var updateNode=treeObj.getNodesByParam("id",$('#locId').val())[0];
				updateNode.name=$('#locName').val();
				treeObj.updateNode(updateNode);
				//parent.changeIframe('/inaction/region/commonregion-list.html?id='+upid);
				parent.changeIframe('/inaction/region/polloc-list.html?id='+upid);
			}else{
				alert(data.message);
			}
		}
			
	});
	 
}

 
//tree
function changeLocDesc(){
	var zTree = $.fn.zTree.getZTreeObj("locTree");
	var nodes = zTree.getCheckedNodes(true);
	var str="";
	if(nodes.length>0){
		for(var i=0;i<nodes.length;i++){
			str+=nodes[i].name;
			if(i!=nodes.length-1){
				str+=",";
			}
		}
	}
//$('#locDesc').val(str);
}
function onCheck(event, treeId, treeNode) {
    if(treeNode.checked){
    	var html="<li hid='"+treeNode.id+"' class=\"list-group-item\"><span onclick=\"removeChosed('"+treeNode.id+"')\" class=\"badge\">  X </span>"+treeNode.name+"</li>";
    	$('#chosedTreeNode ul').append(html);
    }else{
    	removeChosed(treeNode.id);
    }
    changeLocDesc();
};
function removeChosed(id){
	$('#chosedTreeNode ul li').each(function (){
		if($(this).attr('hid')==id){
			$(this).remove();
		}
	});
	var zTree = $.fn.zTree.getZTreeObj("locTree");
	var nodes = zTree.getCheckedNodes(true);
	if(nodes.length>0){
		for(var i=0;i<nodes.length;i++){
			if(nodes[i].id==id){
				zTree.checkNode(nodes[i], false, true,true);
			}
		}
	}
	changeLocDesc();
}
var setting = {

	data : {
		simpleData : {
			enable : true
		}
	},
	view : {
		showLine : false,
		showIcon : false,
		dblClickExpand: false
	},
	check : {
		enable : true,
		chkStyle : "checkbox",
		chkboxType : {
			"Y" : "",
			"N" : ""
		}
	},
	callback: {
		onCheck: onCheck
	}
};
 
// tree end