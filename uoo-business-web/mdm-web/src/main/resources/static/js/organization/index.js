// loadingMask
var loading = new Loading();
loading.screenMaskEnable('container');

var testData = [{
    "id": "31948",
    "state": null,
    "pid": "31937",
    "icon": null,
    "iconClose": null,
    "iconOpen": null,
    "name": "高亭支局",
    "open": false,
    "level": 4,
    "checked": false,
    "chkDisabled": false,
    "extField1": null,
    "standardFlag": "0",
    "parent": true,
    "tId": "standardTree_31948",
    "parentTId": "standardTree_31937",
    "zAsync": false,
    "isFirstNode": false,
    "isLastNode": false,
    "isAjaxing": false,
    "checkedOld": false,
    "nocheck": false,
    "halfCheck": false,
    "check_Child_State": -1,
    "check_Focus": false,
    "isHover": true,
    "editNameFlag": false,
}, {
    "id": "31937",
    "state": null,
    "pid": "11",
    "icon": null,
    "iconClose": null,
    "iconOpen": null,
    "name": "岱山县分公司",
    "open": true,
    "level": 3,
    "checked": false,
    "chkDisabled": false,
    "extField1": null,
    "standardFlag": "0",
    "parent": true,
    "tId": "standardTree_31937",
    "parentTId": "standardTree_11",
    "zAsync": true,
    "isFirstNode": false,
    "isLastNode": false,
    "isAjaxing": null,
    "checkedOld": false,
    "nocheck": false,
    "halfCheck": false,
    "check_Child_State": 0,
    "check_Focus": false,
    "isHover": false,
    "editNameFlag": false,
}, {
    "id": "11",
    "state": null,
    "pid": "1",
    "icon": null,
    "iconClose": null,
    "iconOpen": null,
    "name": "舟山市分公司",
    "open": true,
    "level": 2,
    "checked": false,
    "chkDisabled": false,
    "extField1": null,
    "standardFlag": "0",
    "parent": true,
    "tId": "standardTree_11",
    "parentTId": "standardTree_1",
    "zAsync": true,
    "isFirstNode": false,
    "isLastNode": false,
    "isAjaxing": null,
    "checkedOld": false,
    "nocheck": false,
    "halfCheck": false,
    "check_Child_State": 0,
    "check_Focus": false,
    "isHover": false,
    "editNameFlag": false,
}, {
    "id": "1",
    "state": null,
    "pid": "10000001",
    "icon": null,
    "iconClose": null,
    "iconOpen": null,
    "name": "浙江省电信公司",
    "open": true,
    "level": 1,
    "checked": false,
    "chkDisabled": false,
    "extField1": null,
    "standardFlag": "0",
    "parent": true,
    "tId": "standardTree_1",
    "parentTId": "standardTree_10000001",
    "zAsync": true,
    "isFirstNode": false,
    "isLastNode": false,
    "isAjaxing": null,
    "checkedOld": false,
    "nocheck": false,
    "halfCheck": false,
    "check_Child_State": 0,
    "check_Focus": false,
    "isHover": false,
    "editNameFlag": false,
}, {
    "id": "10000001",
    "state": null,
    "pid": "",
    "icon": null,
    "iconClose": null,
    "iconOpen": null,
    "name": "中国电信股份有限公司浙江分公司",
    "open": true,
    "level": 0,
    "checked": false,
    "chkDisabled": false,
    "extField1": null,
    "standardFlag": "0",
    "parent": true,
    "tId": "standardTree_10000001",
    "parentTId": null,
    "zAsync": true,
    "isFirstNode": true,
    "isLastNode": true,
    "isAjaxing": null,
    "checkedOld": false,
    "nocheck": false,
    "halfCheck": false,
    "check_Child_State": 0,
    "check_Focus": false,
    "isHover": false,
    "editNameFlag": false,
}];
var changeFlg = false;
var moveId;

function test() {
    var tree = $.fn.zTree.getZTreeObj('standardTree');
    changeFlg = true;
    for (var i = testData.length - 1; i >= 0; i--) {
        var node = tree.getNodeByParam('id', testData[i].id);
        if (!node.open)
            return tree.expandNode(node, true, false, true, true);
    }
}

function onAsyncSuccess (event, treeId, treeNode, msg) {
    var tree = $.fn.zTree.getZTreeObj('standardTree');
    if (treeNode.id == testData[0].id) {
        var pNode = tree.getNodeByParam('id', treeNode.id);
        var node = tree.getNodeByParam('id', moveId);
        tree.moveNode(pNode, node, 'inner');
        setTimeout(function () {
            tree.showNodeFocus(node);
        }, 300)
    }
    if (changeFlg) {
        for (var i = testData.length - 1; i >= 0; i--) {
            var node = tree.getNodeByParam('id', testData[i].id);
            if (!node.open)
                tree.expandNode(node, true, false, true, true);
        }
    }
}

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
        onClick: onNodeClick,
        onAsyncSuccess: onAsyncSuccess
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
function getOrgExtInfo (orgName) {
    var pathArry = nodeArr;
    var pathStr = '';
    if (pathArry && pathArry.length > 0) {
        for (var i = pathArry.length - 1; i >= 0; i--) {
            var node = pathArry[i].node;
            if (orgName) {
                pathStr += '<span class="breadcrumb-item"><a href="javascript:void(0);" onclick="parent.openTreeById('+orgId+','+node.id+')">' + node.name + '</a><span class="breadcrumb-separator" style="margin: 0 9px;">/</span></span>';
            } else {
                if (pathArry[i].current) {
                    pathStr +=  '<span class="breadcrumb-item">' + node.name + '</span>';
                } else {
                    pathStr += '<span class="breadcrumb-item"><a href="javascript:void(0);" onclick="parent.openTreeById('+orgId+','+node.id+')">' + node.name + '</a><span class="breadcrumb-separator" style="margin: 0 9px;">/</span></span>';
                }
            }

        }
        if (orgName) {
            pathStr +=  '<span class="breadcrumb-item">' + orgName + '</span>';
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
        if(pNode){
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

//根据id获取节点
function getNodeById(orgId) {
    var tree = $.fn.zTree.getZTreeObj('standardTree');
    return tree.getNodeByParam('id', orgId);
}

initOrgRelTree();


