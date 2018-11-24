var locIds=null,locNames=null,curUpId=0;
function loadRegionType(){
	for(var i=0;i<parent.typeArray.length;i++){
		$('#regionType').append("<option value='"+parent.typeArray[i].itemValue+"'>"+parent.typeArray[i].itemCnname+"</option>");
  	}
}
function deleteData(){
	var id=$('#regionId').val();
	var upRegionId=$('#upRegionId').val();
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
					if(upRegionId==0||upRegionId=='0'){
						zTree.removeNode(snodes);
						parent.changeIframe('/inaction/region/none.html');
						return;
					}
					if(snodes.id==id){
						zTree.removeNode(snodes);
						zTree.selectNode(zTree.getNodeByParam("id", upRegionId, null));
						parent.changeIframe('/inaction/region/commonregion-list.html?id=' + upRegionId);
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
				curUpId=data.data.UP_REGION_ID
				initData(data.data);
				loadUpRegionList(curUpId);
			}else{
				alert(data.message);
			}
		}
	});
}
function initData(data){
	$('#regionId').val(data.COMMON_REGION_ID);
	$('#regionNbr').val(data.REGION_NBR);
	$('#regionName').val(data.REGION_NAME);
	$('#regionType').val(data.REGION_TYPE);
	$('#regionDesc').val(data.REGION_DESC);
	var locIdsArr=data.LOC_ID;
	locNames=data.LOC_NAME;
	locIds=data.LOC_ID;
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
function initLocTree(){
	$.ajax({
		url:'/region/politicalLocation/getTreePoliticalLocation',
		dataType:'json',
		type:'get',
		success:function(data){
			if(data.state==1000){
				console.log(locIds);
				for(var i=0;i<data.data.length;i++){
					if(inArr(locIds, data.data[i].id)){
						data.data[i].checked=true;
						console.log("checked:"+data.data[i]);
					}
				}
				$('#locDesc').val(locNames);
				var htmlstr="";
				for(var i=0;i<locNames.length;i++){
					htmlstr+="<li hid='"+locIds[i]+"' class=\"list-group-item\"><span onclick=\"removeChosed('"+locIds[i]+"')\" class=\"badge\">  X </span>"+locNames[i]+"</li>";
				}
				$('#chosedTreeNode ul').append(htmlstr);
				$.fn.zTree.init($("#locTree"), setting,data.data);
			}else{
				alert('加载行政区域失败，请刷新重试')
			}
		}
		
	});
	
}
function saveRegion(){
	if(!validFormData()){
		return;
	}
	var treeObj = $.fn.zTree.getZTreeObj("locTree");
	var checkeds=treeObj.getCheckedNodes();
	var polLocIds="";
	$('#upRegionId').removeAttr('disabled');
	for(var i=0;i<checkeds.length;i++){
		polLocIds+=checkeds[i].id;
		if(i!=checkeds.length-1){
			polLocIds+=",";
		}
	}
	$('#polLocIds').val(polLocIds);
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
function showMenu(){
	var treeObj = $.fn.zTree.getZTreeObj("locTree");
	var nodes=treeObj.getCheckedNodes(true);
	for(var i=0;i<nodes.length;i++){
		var node = nodes[i].getParentNode();
		treeObj.expandNode(node, true, false, true);
		console.log(node);
		while(node.pId!=null){
			node=node.getParentNode();
		}
	}
}
function loadUpRegionList(curUpId) {
	$.ajax({
		url : '/region/commonRegion/getTreeCommonRegion',
		dataType : 'json',
		type : 'get',
		success : function(tree) {
			if (tree.state == 1000) {
				console.log(curUpId);
				$.each(tree.data, function(i, item) {
					var up = 0;
					var html = "";
					html += "<option value='" + item.id + "'";
					if (curUpId == item.id) {
						html += " selected='selected'";
					}
					up = item.pId;
					html += " >" + item.name + "</option>"
					console.log(html);
					//某一层的 循环查找插入  那么久插入
					$('#upRegionId').append(html);
					/*$('#upRegionId option').each(function() {
						var id = $(this).val();
						if (id == up) {
							$(this).after(html);
						}
					});*/
				});
				$("#upRegionId").attr("disabled","disabled");
				

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
	$('#locDesc').val(str);
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