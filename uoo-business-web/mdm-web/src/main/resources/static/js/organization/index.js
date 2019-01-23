// loadingMask
var loading = new Loading();
loading.screenMaskEnable('container');

var setting = {
    async: {
        enable: true,
        url: "/orgRel/getOrgRelTree?orgRootId=1&orgTreeId=1",
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
    parent,
    nodeArr;

function onNodeClick(e,treeId, treeNode) {
    // var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    // zTree.expandNode(treeNode);
    orgId = treeNode.id;
    pid = treeNode.pid;
    orgName = treeNode.name;
    var currentNode = {node: treeNode, current: true};//获取当前选中节点
    var parentNode = treeNode.getParentNode();
    nodeArr = [];
    getParentNodes(parentNode, currentNode);
    refreshResult();
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

// 获取组织完整路径
function getOrgExtInfo () {
    var pathArry = nodeArr;
    var pathStr = '';
    if (pathArry && pathArry.length > 0) {
        for (var i = pathArry.length - 1; i >= 0; i--) {
            var node = pathArry[i].node;
            if (pathArry[i].current) {
                pathStr +=  '<span class="breadcrumb-item">' + node.name + '</span>';
            } else {
                pathStr += '<span class="breadcrumb-item"><a href="javascript:void(0);" onclick="parent.openTreeById('+orgId+','+node.id+')">' + node.name + '</a><span class="breadcrumb-separator" style="margin: 0 9px;">/</span></span>';
            }
        }
        $('#orgFrame').contents().find('.breadcrumb').html(pathStr);
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
    $http.get('/orgRel/getOrgRelTree', {
        orgRootId: '1',
        orgTreeId: '1'
    }, function (data) {
        $.fn.zTree.init($("#standardTree"), setting, data);
        var zTree = $.fn.zTree.getZTreeObj("standardTree");
        var nodes = zTree.getNodes();
        zTree.expandNode(nodes[0], true);
        zTree.selectNode(nodes[0], true);
        onNodeClick(null, null, nodes[0]);
    }, function (err) {

    })
}

// 根据组织ID展开并选中组织
function openTreeById (sId, id) {
    var tId = 'standardTree_' + id;
    var sId = 'standardTree_' + sId;
    var zTree = $.fn.zTree.getZTreeObj("standardTree");
    var node = zTree.getNodeByTId(tId);
    zTree.selectNode(node);
    $('.curSelectedNode').trigger('click');
}

// 添加子节点
function addNodeById (sId, index, newNode) {
    var zTree = $.fn.zTree.getZTreeObj("standardTree");
    var selectNode = zTree.getNodeByTId(sId); //获取当前选中的节点并取消选择状态
    if (selectNode)
        var newNode = zTree.addNodes(selectNode, index, newNode);
}

// 修改节点名称
function changeNodeName(orgId, name) {
    var tId = 'standardTree_' + orgId;
    var zTree = $.fn.zTree.getZTreeObj("standardTree");
    var treeNode = zTree.getNodeByTId(tId);
    if (treeNode) {
        treeNode.name = name;
        $('#standardTree_' + orgId + '_span').html(name);
    }
}

//移动节点至指定位置
function moveNode(pid, orgId, index) {
    if (index) {
        var tree = $.fn.zTree.getZTreeObj('standardTree');
        var pNode = tree.getNodeByParam('id', pid);
        var node = tree.getNodeByParam('id', orgId);
        var childNode = pNode.children;
        if (childNode && childNode.length > 0) {
            childNode.splice($.inArray(node, childNode), 1);
            var targetNode = childNode[index - 1];
            if (childNode.length > 0) {
                if (targetNode)
                    tree.moveNode(targetNode, node, 'prev');
                else
                    tree.moveNode(childNode[childNode.length - 1], node, 'next'); //移动到最后一个元素
            }
        }
    }
}

// 删除节点
function deleteNode(orgId) {
    var tId = 'standardTree_' + orgId;
    var zTree = $.fn.zTree.getZTreeObj("standardTree");
    var selectNode = zTree.getNodeByTId(tId); //获取当前选中的节点并取消选择状态
    zTree.removeNode(selectNode);
}

// 选择根节点
function selectRootNode () {
    var rootId = 'standardTree_1';
    var zTree = $.fn.zTree.getZTreeObj("standardTree");
    var rootNode = zTree.getNodeByTId(rootId);
    zTree.selectNode(rootNode);
    $('.curSelectedNode').trigger('click');
}

initOrgRelTree();
