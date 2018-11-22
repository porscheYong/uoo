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
		url : '/region/politicalLocation/getTreePoliticalLocation',
		dataType : 'json',
		type : 'get',
		success : function(data) {
			if (data.state == 1) {
				$.fn.zTree.init($("#standardTree"), setting, data.data);
			} else {
				alert('加载行政区域树失败，请重试');
			}
		}
	});
	/*$('#editBtn').bind('click', editPolLoc);
	$('#saveBtn').bind('click', savePolLoc);
	$('#addBtn').bind('click', addPolLoc);*/
	//asyncAll();
	//$("#expandAllBtn").bind("click", expandAll);
	/*$("#asyncAllBtn").bind("click", asyncAll);
	$("#resetBtn").bind("click", reset);*/
	loadTypeArr();
	$("#searchInput").change( function() {
		searchLeftTree();
	});
});
function getFontCss(treeId, treeNode) {
	return (!!treeNode.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
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
	if(key.length<1){
		var nodes=zTree.getNodes();
		if(nodes.length>0)
		for(var i=0;i<nodes.length;i++){
			
		}
		return;
	}
	var notWantNodes=zTree.getNodesByFilter(searchNotWantFilter);
	var wantNodes=zTree.getNodesByFilter(searchWantFilter);
	for(var i=0;i<wantNodes.length;i++){
		wantNodes[i].highlight=true;
		zTree.updateNode(wantNodes[i]);
	}
	for(var i=0;i<notWantNodes.length;i++){
		notWantNodes[i].highlight=false;
		zTree.updateNode(notWantNodes[i]);
	}
	console.log('notWantNodes:'+notWantNodes.length+",wantNodes:"+wantNodes.length);
}

function loadTypeArr(){
	$.ajax({
		url:'/tbDictionaryItem/getList/LOC_TYPE',
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
	changeIframe('/inaction/region/polloc-list.html?id=' + treeNode.id);
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