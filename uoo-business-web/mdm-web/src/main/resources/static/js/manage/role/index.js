// loadingMask
var loading = new Loading();

var roleId,
    roleName,
    nodeName,
    nodeArr,
    parent;
    
loading.screenMaskEnable('container');

function onNodeClick(e,treeId, treeNode) {
    roleId = treeNode.id;
    roleName = treeNode.name;
    var currentNode = {node: treeNode, current: true};//获取当前选中节点
    var parentNode = treeNode.getParentNode();
    nodeArr = [];
    getParentNodes(parentNode, currentNode);
    // refreshResult();
}

// 获取父节点路径
function getParentNodes(parentNode, currentNode) {
    if(parentNode!=null){
        parent = {node: parentNode, current: false};
        var curNode = parentNode.getParentNode();
        nodeArr.push(currentNode);
        getParentNodes(curNode, parent);
    }else{
        //根节点
        nodeArr.push(currentNode);
    }
}

function filter (treeId, parentNode, childNodes) {
    return childNodes.data
}

function refreshResult () {
    var url = "list.html?roleId=" + roleId + "&roleName=" + roleName;
    $('#posFrame').attr("src",url);
}

//初始化组织
function initRoleRelTree () {

    var setting = {
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

    $http.get('/system/sysRole/treeRole', {
    }, function (data) {
        $.fn.zTree.init($("#roleTree"), setting, data);
        var zTree = $.fn.zTree.getZTreeObj("roleTree");
        var nodes = zTree.getNodes();
        zTree.expandNode(nodes[0], true);
        zTree.selectNode(nodes[0], true);
        onNodeClick(null, null, nodes[0]);
        // loading.screenMaskDisable('container');
    }, function (err) {
        loading.screenMaskDisable('container');
    });
}

// 根据组织ID展开并选中组织
function openTreeById (sId, id) {
    var tId = 'posTree_' + id;
    var sId = 'posTree_' + sId;
    var orgTree = $.fn.zTree.getZTreeObj("roleTree");
    var selectNode = orgTree.getNodeByTId(sId); //获取当前选中的节点并取消选择状态
    if (!selectNode.open) {
        orgTree.expandNode(selectNode, true);
    }
    var node = orgTree.getNodeByTId(tId);
    orgTree.selectNode(node);
    $('.curSelectedNode').trigger('click');
}

initRoleRelTree();

