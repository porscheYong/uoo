var nodes = parent.getCurrentSelectedNode();
var node = nodes[0];
var upid = node.id;
function loadRegionType(){
	for(var i=0;i<parent.typeArray.length;i++){
		$('#regionType').append("<option value='"+parent.typeArray[i].itemValue+"'>"+parent.typeArray[i].itemCnname+"</option>");
  	}
}
function loadUpRegionList() {
	$.ajax({
		url : '/region/commonRegion/getTreeCommonRegion',
		dataType : 'json',
		type : 'get',
		success : function(tree) {
			if (tree.state == 1) {
				$.each(tree.data, function(i, item) {
					var up = 0;
					var html = "";
					html += "<option value='" + item.id + "'";
					if (parent.getTree().getSelectedNodes().length>0&&parent.getTree().getSelectedNodes()[0].id == item.id) {
						html += " selected='selected'";
					}
					up = item.pId;
					html += " >" + item.name + "</option>"
					//某一层的 循环查找插入  那么久插入
					$('#upRegionId').append(html);
					/*$('#upRegionId option').each(function() {
						var id = $(this).val();
						if (id == up) {
							$(this).after(html);
						}
					});*/
				});
				
				

			}
		}
	});
}
$(document).ready(function(){
	loadRegionType();
	loadUpRegionList();
	initLocTree();
	//$("[data-toggle='tooltip']").tooltip();
	$('#saveBtn').bind('click',saveRegion);
	
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
	if(!validFormData()){
		return;
	}
	
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
				//在父节点增加数据啊
				var treeObj =parent.getTree();
				var upId=$('#upRegionId').val();
				var myNodes=treeObj.getNodesByParam("id",upId,null);
				var newNodes = [{name:$('#regionName').val(),id:data.data.commonRegionId,parent:false,open:false,pId:upId.id}];
				if(myNodes.length<=0){
					//插入到根目录
					newNodes = treeObj.addNodes(null,-1, newNodes);
					parent.changeIframe('/inaction/region/commonregion-list.html?id='+upid);
				}else{
					newNodes = treeObj.addNodes(myNodes[0],-1, newNodes);
					parent.changeIframe('/inaction/region/commonregion-list.html?id='+upid);
				}
			}else{
				alert(data.message);
			}
		}
			
	});
	 
}
function findNodeById(node){
	return (node.Id);
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