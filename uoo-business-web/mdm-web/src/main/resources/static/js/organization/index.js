// loadingMask
var loading = new Loading();

var setting = {
    async: {
        enable: true,
<<<<<<< HEAD
        url: "http://134.96.253.221:11100/orgRel/getOrgRelTree?orgRootId=1&orgTreeId=1",
=======
        url: "http://134.96.253.221:11100/orgRel/getOrgRelTree?orgRootId=1",
>>>>>>> 191bc73cdae51fe1bf46616ca6f3445957d2bcf6
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
var orgId,
    pid,
    orgName,
    nodeName,
    nodeArr;

function onNodeClick(e,treeId, treeNode) {
    // var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    // zTree.expandNode(treeNode);
    orgId = treeNode.id;
    pid = treeNode.pid;
    orgName = treeNode.name;
    var currentNode = treeNode.name;//获取当前选中节点
    var parentNode = treeNode.getParentNode();
    nodeArr = [];
    getParentNodes(parentNode, currentNode);
    refreshResult();
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

function refreshResult () {
    var url = "list.html?id=" + orgId + '&pid=' + pid + "&name=" + encodeURI(orgName);
    $('#orgFrame').attr("src",url);
}

function initOrgRelTree () {
    $http.get('http://134.96.253.221:11100/orgRel/getOrgRelTree', {
<<<<<<< HEAD
        orgTreeId: '1',
=======
>>>>>>> 191bc73cdae51fe1bf46616ca6f3445957d2bcf6
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

// 修改节点名称
function changeNodeName(orgId, name) {
    var tId = 'standardTree_' + orgId;
    var zTree = $.fn.zTree.getZTreeObj("standardTree");
    var treeNode = zTree.getNodeByTId(tId);
    treeNode.name = name;
    $('#standardTree_' + orgId + '_span').html(name);
}

initOrgRelTree();
