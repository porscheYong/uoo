var setting = {
    async: {
        enable: true,
        url: "http://134.96.253.221:11100/orgRel/getOrgRelTree?orgRootId=1&orgTreeId=1",
        autoParam: ["id"],
        type: "get",
        dataFilter: filter
    },
    view: {
        showLine: false,
        showIcon: false,
        dblClickExpand: false
    },
    data: {
        key: {
            isParent: "parent",
        },
        simpleData: {
            enable:true,
            idKey: "id",
            pIdKey: "pid",
            rootPId: ""
        }
    },
    callback: {
        onClick: onNodeClick
    }
};

var orgIdSelect,
    orgName,
    nodeName,
    nodeArr,
    addOrgList = [];

function onNodeClick(e,treeId, treeNode) {
    // var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    // zTree.expandNode(treeNode);
    orgIdSelect = treeNode.id;
    orgName = treeNode.name;
    var currentNode = treeNode.name;//获取当前选中节点
    var parentNode = treeNode.getParentNode();
    nodeArr = [];
    getParentNodes(parentNode, currentNode);
}

// 获取父节点路径
function getParentNodes(parentNode, currentNode) {
    if(parentNode!=null){
        nodeName = parentNode.name;
        var curNode = parentNode.getParentNode();
        nodeArr.push(currentNode);
        getParentNodes(curNode, nodeName);
    }else{
        //根节点
        nodeArr.push(currentNode);
    }
}

function filter (treeId, parentNode, childNodes) {
    return childNodes.data
}

function initOrgRelTree () {
    $http.get('http://134.96.253.221:11100/orgRel/getOrgRelTree', {
        orgTreeId: '1',
        orgRootId: '1'
    }, function (data) {
        console.log(data)
        $.fn.zTree.init($("#standardTree"), setting, data);
        var zTree = $.fn.zTree.getZTreeObj("standardTree");
        var nodes = zTree.getNodes();
        zTree.expandNode(nodes[0], true);
        zTree.selectNode(nodes[0], true);
        onNodeClick(null, null, nodes[0]);
    }, function (err) {
        console.log(err)
    })
}

// 根据组织ID展开并选中组织
function openTreeById (sId, id) {
    var tId = 'standardTree_' + id;
    var sId = 'standardTree_' + sId;
    var zTree = $.fn.zTree.getZTreeObj("standardTree");
    var selectNode = zTree.getNodeByTId(sId); //获取当前选中的节点并取消选择状态
    zTree.cancelSelectedNode(selectNode);
    var node = zTree.getNodeByTId(tId);
    if (node.parent) {
    zTree.expandNode(node, true);
    }
    zTree.selectNode(node, true);
}

// 添加子节点
function addNodeById (sId, newNode) {
    var zTree = $.fn.zTree.getZTreeObj("standardTree");
    var selectNode = zTree.getNodeByTId(sId); //获取当前选中的节点并取消选择状态
    console.log(selectNode)
    if (selectNode)
        var newNode = zTree.addNodes(selectNode, newNode);
}

    

// 获取组织完整路径
function getOrgExtInfo() {
  var pathArry = nodeArr;
  var pathStr = '';
  for (var i = pathArry.length - 1; i >= 0; i--) {
      pathStr += pathArry[i] + '/'; 
  }
  return pathStr.toString().substring(0,pathStr.toString().length-1);;
}

function saveBtnClick(){
    if(opBtn == 1){   //新增
        var orgNa = [];     //存储已选组织ID
        for(var i = 0;i < addOrgList.length; i++){
            orgNa.push(addOrgList[i].orgId);
        }
        if(orgNa.indexOf(orgIdSelect) != -1){
            alert("已选择该组织");
        }else{
            addOrgList.push({'orgId':orgIdSelect,'fullName':getOrgExtInfo()});
            console.log(addOrgList);
            orgTable.destroy();
            initOrgTable(addOrgList);
        }
    }else if(opBtn == 0){  //编辑
        var orgNa = [];
        for(var i = 0;i < editOrgList.length; i++){
            orgNa.push(editOrgList[i].orgId);
        }
        if(orgNa.indexOf(parseInt(orgIdSelect)) != -1){
            alert("已选择该组织");
        }else{
            addAcctOrg(orgIdSelect);
            editOrgList.push({'orgId':orgIdSelect,'fullName':getOrgExtInfo()});
            orgTable.destroy();
            initOrgTable(editOrgList); 
        }
    }
    $('#myModal').modal('hide');
}

initOrgRelTree();
