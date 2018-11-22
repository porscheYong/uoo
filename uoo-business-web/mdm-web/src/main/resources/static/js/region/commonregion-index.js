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
		dblClickExpand : false,
		fontCss: getFontCss
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
			if (data.state == 1) {
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
	$("#searchInput").change( function() {
		searchLeftTree();
	});
});
function getFontCss(treeId, treeNode) {
	return (!!treeNode.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
}
function getAllNodesFilter(node){
	return true;
}
function searchNotWantFilter(node){
	 return ( node.name.indexOf($('#searchInput').val())<0);
}
function searchWantFilter(node){
	return ( node.name.indexOf($('#searchInput').val())>=0);
}
function searchLeftTree(){
	var key=$('#searchInput').val();
	var zTree = $.fn.zTree.getZTreeObj("standardTree");
	var nodes = zTree.getNodesByFilter(getAllNodesFilter);
	if(key.length<1){
		for(var i=0;i<nodes.length;i++){
			nodes[i].highlight=false;
			//zTree.showNode(nodes[i]);
			zTree.updateNode(nodes[i]);
		}
		return;
	}
	//先把所有隐藏，再挨个显示
	for(var i=0;i<nodes.length;i++){
		nodes[i].highlight=false;
		//zTree.hideNode(nodes[i]);
		zTree.updateNode(nodes[i]);
	}
	
	
	
	//var notWantNodes=zTree.getNodesByFilter(searchNotWantFilter);
	var wantNodes=zTree.getNodesByFilter(searchWantFilter);
	for(var i=0;i<wantNodes.length;i++){
		wantNodes[i].highlight=true;
		zTree.updateNode(wantNodes[i]);
	}
	//收紧所有
	zTree.expandAll(false);
	//先把扩展打开  再显示
	for(var i=0;i<wantNodes.length;i++){
		var myPN=wantNodes[i].getParentNode();
		while(myPN!=null){
			//zTree.showNode(myPN);
			zTree.expandNode(myPN,true);
			myPN=myPN.getParentNode();
		}
		//zTree.showNode(wantNodes[i]);
	}
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