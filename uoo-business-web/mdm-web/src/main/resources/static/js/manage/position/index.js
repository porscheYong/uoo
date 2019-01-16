// loadingMask
var loading = new Loading();

var posId,
    posName,
    posRole,
    nodeName,
    nodeArr,
    parent;
    
loading.screenMaskEnable('container');

function onNodeClick(e,treeId, treeNode) {
    posId = treeNode.id;
    posName = treeNode.name;
    posRole = treeNode.extField1;
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
    var url = "list.html?positionCode=" + posId + "&posName=" + posName;
    $('#posFrame').attr("src",url);
}

//初始化组织
function initPosRelTree () {

    var setting = {
        async: {
            enable: true,
            url: "/sysPosition/getPositionTree?isSync=false",
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

    $http.get('/sysPosition/getPositionTree', {
        isSync: false
    }, function (data) {
        $.fn.zTree.init($("#posTree"), setting, data);
        var zTree = $.fn.zTree.getZTreeObj("posTree");
        var nodes = zTree.getNodes();
        zTree.expandNode(nodes[0], true);
        zTree.selectNode(nodes[0], true);
        onNodeClick(null, null, nodes[0]);
        // loading.screenMaskDisable('container');
    }, function (err) {
        loading.screenMaskDisable('container');
    });
}

// 获取组织完整路径
// function getOrgExtInfo () {
//     var pathArry = nodeArr;
//     var pathStr = '';
//     if (pathArry && pathArry.length > 0) {
//         for (var i = pathArry.length - 1; i >= 0; i--) {
//             var node = pathArry[i].node;
//             if (pathArry[i].current) {
//                 pathStr +=  '<span class="breadcrumb-item"><a href="javascript:void(0);">' + node.name + '</a></span>';
//             } else {
//                 pathStr += '<span class="breadcrumb-item"><a href="javascript:void(0);" onclick="parent.openTreeById('+posId+','+node.id+')">' + node.name + '</a><span class="breadcrumb-separator" style="margin: 0 9px;">/</span></span>';
//             }
//         }
//         $('#userFrame').contents().find('.breadcrumb').html(pathStr);
//     }
// }

// 根据组织ID展开并选中组织
function openTreeById (sId, id) {
    var tId = 'posTree_' + id;
    var sId = 'posTree_' + sId;
    var orgTree = $.fn.zTree.getZTreeObj("posTree");
    var selectNode = orgTree.getNodeByTId(sId); //获取当前选中的节点并取消选择状态
    if (!selectNode.open) {
        orgTree.expandNode(selectNode, true);
    }
    var node = orgTree.getNodeByTId(tId);
    orgTree.selectNode(node);
    $('.curSelectedNode').trigger('click');
}

initPosRelTree();

