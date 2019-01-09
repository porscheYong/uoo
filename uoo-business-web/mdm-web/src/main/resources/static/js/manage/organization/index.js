// loadingMask
var loading = new Loading();
loading.screenMaskEnable('container');

var orgId,
    pid,
    orgName,
    parent,
    nodeArr;

function onNodeClick(e,treeId, treeNode) {
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
        $('#platformOrgFrame').contents().find('.breadcrumb').html(pathStr);
    }
}


function filter (treeId, parentNode, childNodes) {
    return childNodes.data
}

function refreshResult () {
    var url = "list.html?id=" + orgId + '&pid=' + pid + "&name=" + encodeURI(orgName);
    $('#platformOrgFrame').attr("src",url);
}

function initOrgRelTree () {
    var setting = {
        async: {
            enable: true,
            url: '/sysOrganization/getOrgRelTree',
            autoParam: ['id'],
            type: 'get',
            dataFilter: filter
        },
        view: {
            showLine: false,
            showIcon: false,
            dblClickExpand: false
        },
        data: {
            key: {
                isParent: 'parent'
            },
            simpleData: {
                enable:true,
                idKey: 'id',
                pIdKey: 'pid',
                rootPId: ''
            }
        },
        callback: {
            onClick: onNodeClick
        }
    };
    $http.get('/sysOrganization/getOrgRelTree', {}, function (data) {
        $.fn.zTree.init($("#platformOrgTree"), setting, data);
        var tree = $.fn.zTree.getZTreeObj("platformOrgTree");
        var nodes = tree.getNodes();
        tree.expandNode(nodes[0], true);
        tree.selectNode(nodes[0], true);
        onNodeClick(null, null, nodes[0]);
    }, function (err) {

    })
}

// 根据组织ID选中组织
function selectNodeById (id) {
    var tId = 'platformOrgTree_' + id;
    var zTree = $.fn.zTree.getZTreeObj("platformOrgTree");
    var node = zTree.getNodeByTId(tId);
    zTree.selectNode(node);
    $('.curSelectedNode').trigger('click');
}

// 根据组织ID获取组织名称
function getNodeName (id) {
    var tId = 'platformOrgTree_' + id;
    var tree = $.fn.zTree.getZTreeObj("platformOrgTree");
    var treeNode = tree.getNodeByTId(tId);
    return treeNode.name;
}

// 添加子节点
function addNodeById (sId, newNode) {
    var zTree = $.fn.zTree.getZTreeObj("platformOrgTree");
    var selectNode = zTree.getNodeByTId(sId); //获取当前选中的节点并取消选择状态
    if (selectNode)
        var newNode = zTree.addNodes(selectNode, newNode);
}

// 修改节点名称
function changeNodeName(orgId, name) {
    var tId = 'platformOrgTree_' + orgId;
    var zTree = $.fn.zTree.getZTreeObj("platformOrgTree");
    var treeNode = zTree.getNodeByTId(tId);
    treeNode.name = name;
    $('#platformOrgTree_' + orgId + '_span').html(name);
}

// 删除节点
function deleteNode(orgId) {
    var tId = 'platformOrgTree_' + orgId;
    var zTree = $.fn.zTree.getZTreeObj("platformOrgTree");
    var selectNode = zTree.getNodeByTId(tId); //获取当前选中的节点并取消选择状态
    zTree.removeNode(selectNode);
}

initOrgRelTree();
