// loadingMask
var loading = new Loading();

var roleId,
    roleCode,
    roleName,
    nodeName,
    nodeArr,
    parent;
    
loading.screenMaskEnable('container');

function onNodeClick(e,treeId, treeNode) {
    roleId = treeNode.id;
    roleCode = treeNode.extField1;
    roleName = treeNode.name;
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

function filter (treeId, parentNode, childNodes) {
    return childNodes.data
}

function refreshResult () {
    var url = "list.html?roleId=" + roleId + "&roleName=" + roleName + "&roleCode=" + roleCode;
    $('#roleFrame').attr("src",url);
}

//初始化组织
function initRoleRelTree () {

    var setting = {
        async: {
            enable: true,
            url: "/system/sysRole/treeRole",
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

    $http.get('/system/sysRole/treeRole', {
        id : 0
    }, function (data) {
        $.fn.zTree.init($("#roleTree"), setting, data);
        var zTree = $.fn.zTree.getZTreeObj("roleTree");
        var nodes = zTree.getNodes();
        zTree.expandNode(nodes[0], true);
        zTree.selectNode(nodes[0], true);
        onNodeClick(null, null, nodes[0]);
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

// 获取组织完整路径
function getRoleExtInfo (flag) {
    var pathArry = nodeArr;
    var roleFullName = "";
    var pathStr = "";
    if (pathArry && pathArry.length > 0) {
        for (var i = pathArry.length - 1; i >= 0; i--) {
            var node = pathArry[i].node;
            if (pathArry[i].current) {
                pathStr +=  '<span class="breadcrumb-item"><a href="javascript:void(0);">' + node.name + '</a></span>';
            } else {
                pathStr += '<span class="breadcrumb-item"><a href="javascript:void(0);" onclick="parent.openTreeById('+node.id+','+node.id+')">' + node.name + '</a><span class="breadcrumb-separator" style="margin: 0 9px;">/</span></span>';
            }
            roleFullName += node.name + ' / '; 
        }
        roleFullName = roleFullName.toString().substring(0,roleFullName.toString().length-2);
        // $('.breadcrumb').html(pathStr);
        if(flag == 0){
            return roleFullName;
        }else{
            return pathStr;
        }
    }
}

initRoleRelTree();

