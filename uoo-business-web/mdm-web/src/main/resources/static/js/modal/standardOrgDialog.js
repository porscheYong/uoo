var orgTreeId = getQueryString('orgTreeId');
var infoFlag =  ~~getQueryString('infoFlag');
var orgFrame = parent.window['standardOrg'] || parent.window['business'];
var checkNode = [];
var parentNode = parent.nodeArr.concat();
if (!infoFlag && parentNode && parentNode.length > 0) {
    parentNode.splice(0, 1)
}

var nodePath = orgFrame.nodeArr || parentNode;
var parentObj, nodeArr;

// 获取父节点路径
function getParentNodes(parentNode, currentNode) {
    if(parentNode!=null){
        parentObj = {node: parentNode, current: false};
        var curNode = parentNode.getParentNode();
        nodeArr.push(currentNode);
        getParentNodes(curNode, parentObj);
    }else{
        //根节点
        nodeArr.push(currentNode);
    }
}

function filter (treeId, parentNode, childNodes) {
    return childNodes.data
}

function orgTreeBeforeClick (treeId, treeNode) {
    var tree = $.fn.zTree.getZTreeObj("orgTree");
    tree.checkNode(treeNode, !treeNode.checked, true);
    return false;
}

function onOrgTreeCheck (e, treeId, treeNode) {
    var tree = $.fn.zTree.getZTreeObj("orgTree");
    var node = tree.getNodeByTId(treeNode.tId);
    var currentNode = {node: treeNode, current: true};//获取当前选中节点
    var parentNode = treeNode.getParentNode();
    nodeArr = [];
    getParentNodes(parentNode, currentNode);
    if ($.inArray(node, checkNode) === -1) {
        checkNode = [];
        checkNode.push(node);
    } else {
        checkNode = [];
    }
}

function onAsyncSuccess (event, treeId, treeNode, msg) {
    //自动展开删除'未分类'
    // if (treeNode.level == 0) {
    //     var response = JSON.parse(msg);
    //     if (response && response.data) {
    //         var data = response.data;
    //         for (var i = 0; i < data.length; i++) {
    //             // 未分类
    //             if (data[i].id == '88888888') {
    //                 var tree = $.fn.zTree.getZTreeObj("orgFullNameTree");
    //                 var node = tree.getNodeByParam('id', data[i].id);
    //                 tree.removeNode(node);
    //             }
    //         }
    //     }
    // }
    // var nodeObj = {current: false, node: nodePath[2].node}
    // var idx = $.inArray(nodeObj, nodePath);
    // if (idx != 0) {
    //     var node = tree.getNodeByParam('id', nodePath[idx].node.id);
    //     tree.expandNode(node, true, false, true, true);
    // }
    var idx;
    var tree = $.fn.zTree.getZTreeObj("orgTree");
    for (var i = nodePath.length - 1; i >= 0; i--) {
        var node = nodePath[i].node;
        if (node.id == treeNode.id) {
            idx = i;
            break;
        }
    }

    //未修改过的
    // if (idx > 0) {
    //     var openNode = tree.getNodeByParam('id', nodePath[idx - 1].node.id);
    //     tree.expandNode(openNode, true, false, true, true);
    // }
    // if (idx == 1) {
    //     var openNode = tree.getNodeByParam('id', nodePath[0].node.id);
    //     tree.checkNode(openNode, true);
    //     setTimeout(function () {
    //         tree.showNodeFocus(openNode);
    //     }, 100)
    // }

    //修改过的
    if (idx > 1) {
        var openNode = tree.getNodeByParam('id', nodePath[idx - 1].node.id);
        tree.expandNode(openNode, true, false, true, true);
    }else{
        var openNode = tree.getNodeByParam('id', nodePath[0].node.id);
        tree.checkNode(openNode, true);
        setTimeout(function () {
            tree.showNodeFocus(openNode);
        }, 100)
    }

    // var response = JSON.parse(msg);
    // for (var i = nodePath.length - 1; i >= 0; i--) {
    //     var tree = $.fn.zTree.getZTreeObj("orgTree");
    //     var node = tree.getNodeByParam('id', nodePath[i].node.id);
    //     if (i != 0) {
    //         tree.expandNode(node, true, false, true, true);
    //     }
    //     else if (node) {
    //         console.log(node)
    //         tree.checkNode(node, true);
    //     }
    // }
}

function initOrgTree() {
    var treeSetting = {
        async: {
            enable: true,
            url: "/orgRel/getOrgRelTree?orgTreeId=" + orgTreeId,
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
                isParent: "parent"
            },
            simpleData: {
                enable:true,
                idKey: "id",
                pIdKey: "pid",
                rootPId: ""
            }
        },
        callback: {
            beforeClick: orgTreeBeforeClick,
            onCheck: onOrgTreeCheck,
            onAsyncSuccess: onAsyncSuccess
        },
        check: {
            enable: true,
            chkStyle: 'radio',
            radioType: 'all'
        }
    };
    $http.get('/orgRel/getOrgRelTree', {
        orgTreeId: orgTreeId
    }, function (data) {
        $.fn.zTree.init($("#orgTree"), treeSetting, data);
        var zTree = $.fn.zTree.getZTreeObj("orgTree");
        var nodes = zTree.getNodes();
        zTree.expandNode(nodes[0], true);
    }, function (err) {

    })
}

// 获取所有勾选的节点
function getCheckdNodes () {
    var tree = $.fn.zTree.getZTreeObj("orgTree");
    var nodes = tree.getCheckedNodes(true);
    return nodes;

}

initOrgTree();