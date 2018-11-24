var typeArray=null;
var setting = {

	data : {
		simpleData : {
			enable : true
		}
	},
	view : {
		showLine : false,
		showIcon : false,
		dblClickExpand : false
	},
	callback : {
		onClick : zTreeOnClick
	}
};
$(document).ready(function() {

	$.ajax({
		url : '/region/commonRegion/getTreeCommonRegion',
		dataType : 'json',
		type : 'get',
		success : function(data) {
			if (data.state == 1000) {
				$.fn.zTree.init($("#standardTree"), setting, data.data);
			} else {
				alert('加载公共区域树失败，请重试');
			}
		}
	});
	loadTypeArr();
	/*$('#editBtn').bind('click', editPolLoc);
	$('#saveBtn').bind('click', savePolLoc);
	$('#addBtn').bind('click', addPolLoc);*/
	//asyncAll();
	//$("#expandAllBtn").bind("click", expandAll);
	/*$("#asyncAllBtn").bind("click", asyncAll);
	$("#resetBtn").bind("click", reset);*/
});
function searchNotWantFilter(node){
	 return ( node.name.indexOf($('#searchInput').val())<0);
}
function searchWantFilter(node){
	return ( node.name.indexOf($('#searchInput').val())>=0);
}
function searchLeftTree(){
	/*var key=$('#searchInput').val();
	if(key.length<1){
		return;
	}*/
	var zTree = $.fn.zTree.getZTreeObj("standardTree");
	var notWantNodes=zTree.getNodesByFilter(searchNotWantFilter);
	var wantNodes=zTree.getNodesByFilter(searchWantFilter);
	for(var i=0;i<wantNodes.length;i++){
		wantNodes[i].isHidden=false;
		zTree.updateNode(wantNodes[i]);
	}
	for(var i=0;i<notWantNodes.length;i++){
		notWantNodes[i].isHidden=true;
		zTree.updateNode(notWantNodes[i]);
	}
	console.log('notWantNodes:'+notWantNodes.length+",wantNodes:"+wantNodes.length);
}
function loadTypeArr(){
	$.ajax({
		url:'/tbDictionaryItem/getList/REGION_TYPE',
		dataType:"json",
		type:'get',
		success:function(data){
			if(data.state==1000){
				typeArray=data.data;
			}else{
				alert('加载区域类型失败，请刷新重试');
			}
		}
	});
}
function zTreeOnClick(event, treeId, treeNode) {
	changeIframe('/inaction/region/commonregion-list.html?id=' + treeNode.id);
};
function getCurrentSelectedNode() {
	var treeObj = $.fn.zTree.getZTreeObj("standardTree");
	var nodes = treeObj.getSelectedNodes();
	return nodes;
}
   
function changeIframe(url) {
	$('#myFrame').attr("src", url);
}
function getTree() {
	var zTree = $.fn.zTree.getZTreeObj("standardTree");
	return zTree;
}