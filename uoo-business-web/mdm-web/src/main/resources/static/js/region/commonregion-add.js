var nodes = parent.getCurrentSelectedNode();
var node = nodes[0];
var upid = node.id;

function expUpRegionSelect() {
	deepTree(0, upid);
}
$(document).ready(function(){
	expUpRegionSelect();
	initLocTree();
	
	//$('#editBtn').bind('click',editPolLoc);
	$('#saveBtn').bind('click',saveRegion);
	/*$('#addBtn').bind('click',addPolLoc);*/
	//asyncAll();
	//$("#expandAllBtn").bind("click", expandAll);
	/*$("#asyncAllBtn").bind("click", asyncAll);
	$("#resetBtn").bind("click", reset);*/
});
function showMenu(){$('#treeDiv').show();}
function initLocTree(){
	$.ajax({
		url:'/region/politicalLocation/getTreePoliticalLocation',
		dataType:'json',
		type:'get',
		success:function(data){
			if(data.state==1){
				
				console.log(data.data);
				$.fn.zTree.init($("#locTree"), setting,data.data);
				
			}else{
				alert('加载行政区域失败，请刷新重试')
			}
		}
		
	});
	
}
function saveRegion(){
	var treeObj = $.fn.zTree.getZTreeObj("locTree");
	var checkeds=treeObj.getCheckedNodes();
	var polLocIds="";
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
		url:'/region/commonRegion/addCommonRegion',
		data:$('#regionForm').serialize(),
		success:function(data){
			if(data.state==1){
				parent.changeIframe('/inaction/region/commonregion-list.html?id='+upid);
			}else{
				alert(data.message);
			}
		}
			
	});
	 
}
function deepTree(id, myuplocid) {
	$.ajax({
		url : '/region/commonRegion/getTreeCommonRegion?id=' + id,
		dataType : 'json',
		type : 'get',
		success : function(tree) {
			if (tree.state == 1) {
				var html = "";
				var up = 0;
				$.each(tree.data, function(i, item) {
					html += "<option value='" + item.id + "'";
					if (myuplocid == item.id) {
						html += " selected='selected'";
					}
					up = item.pId;
					html += " >" + item.name + "</option>"
				});
				//某一层的 循环查找插入  那么久插入
				$('#upRegionId option').each(function() {
					var id = $(this).val();
					if (id == up) {
						$(this).after(html);
					}
				});
				$.each(tree.data, function(i, item) {
					deepTree(item.id, myuplocid);
				});

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