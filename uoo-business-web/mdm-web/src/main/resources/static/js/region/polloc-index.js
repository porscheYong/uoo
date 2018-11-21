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
});
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