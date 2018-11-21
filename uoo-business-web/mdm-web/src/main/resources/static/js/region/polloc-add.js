var nodes = parent.getCurrentSelectedNode();
var node = nodes[0];
var upid = node.id;

function loadUpRegionList() {
	$.ajax({
		url : '/region/politicalLocation/getTreePoliticalLocation',
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
					$('#upLocId').append(html);
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
	loadUpRegionList();
	loadLocType();
	
	$('#saveBtn').bind('click',saveRegion);
	
});
function loadLocType(){
	for(var i=0;i<parent.typeArray.length;i++){
		$('#locType').append("<option value='"+parent.typeArray[i].itemValue+"'>"+parent.typeArray[i].itemCnname+"</option>");
  	}
}
function showMenu(){$('#treeDiv').show();}
function saveRegion(){
	if(!validFormData()){
		return;
	}
	$.ajax({
		type:'POST',
		dataType:'json',
		url:'/region/politicalLocation/addPoliticalLocation',
		data:$('#regionForm').serialize(),
		success:function(data){
			if(data.state==1){
				//在父节点增加数据啊
				var treeObj =parent.getTree();
				var upId=$('#upLocId').val();
				var myNodes=treeObj.getNodesByParam("id",upId,null);
				var newNodes = [{name:$('#locName').val(),id:data.data.locId,parent:false,open:false,pId:upId.id}];
				if(myNodes.length<=0){
					//插入到根目录
					newNodes = treeObj.addNodes(null,-1, newNodes);
					parent.changeIframe('/inaction/region/polloc-list.html?id='+upid);
				}else{
					newNodes = treeObj.addNodes(myNodes[0],-1, newNodes);
					parent.changeIframe('/inaction/region/polloc-list.html?id='+upid);
				}
			}else{
				alert(data.message);
			}
		}
			
	});
	 
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
function findNodeById(node){
	return (node.Id);
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