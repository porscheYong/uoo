// loadingMask
var loading = new Loading();
var orgRootId; //业务组织ID
var orgTreeId; //业务组织ID
var businessName; //业务名
var refCode; //唯一标识
var standardFlag; //编辑标识
var orgId, //组织ID
    pid,
    orgName, //组织名
    parent,
    nodeArr,
    isChannel;
loading.screenMaskEnable('container');

// lulu ui select插件
seajs.use('/vendors/lulu/js/common/ui/Select', function () {
    // $('#businessOrg').selectMatch();
});

function onNodeClick(e,treeId, treeNode) {
    // var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    // zTree.expandNode(treeNode);
    orgId = treeNode.id;
    pid = treeNode.pid;
    orgName = treeNode.name;
    standardFlag = treeNode.standardFlag;
    isChannel = treeNode.isChannel;
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
            }
            else {
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
        $('#businessFrame').contents().find('.breadcrumb').html(pathStr);
    }
}

function filter (treeId, parentNode, childNodes) {
    return childNodes.data
}

function refreshResult () {
    var url = "list.html?id=" + orgId + "&orgTreeId=" + orgTreeId + '&pid=' + pid + "&refCode=" + refCode + "&standardFlag=" + standardFlag + "&name=" + encodeURI(orgName) + "&isChannel=" + isChannel;
    $('#businessFrame').attr("src",url);
}

function initTree (orgTreeId) {
    //树参数设定
    var setting = {
        async: {
            enable: true,
            url: "/orgRel/getOrgRelTree?orgRootId=" + orgTreeId + "&orgTreeId=" + orgTreeId,
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
    $http.get('/orgRel/getOrgRelTree', {
        orgRootId: orgTreeId,
        orgTreeId: orgTreeId
    }, function (data) {
        $.fn.zTree.init($("#businessTree"), setting, data);
        var zTree = $.fn.zTree.getZTreeObj("businessTree");
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
    var zTree = $.fn.zTree.getZTreeObj("businessTree");
    var node = zTree.getNodeByTId(tId);
    zTree.selectNode(node);
    $('.curSelectedNode').trigger('click');
}

// 修改节点名称
function changeNodeName(orgId, name) {
    var tId = 'businessTree_' + orgId;
    var zTree = $.fn.zTree.getZTreeObj("businessTree");
    var treeNode = zTree.getNodeByTId(tId);
    if (treeNode) {
        treeNode.name = name;
        $('#businessTree_' + orgId + '_span').html(name);
    }
}

// 添加子节点
function addNodeById (sId, index, newNode) {
    var zTree = $.fn.zTree.getZTreeObj("businessTree");
    var selectNode = zTree.getNodeByTId(sId); //获取当前选中的节点并取消选择状态
    if (selectNode)
        var newNode = zTree.addNodes(selectNode, index, newNode);
}

//移动节点至指定位置
function moveNode(pid, orgId, index) {
    if (index) {
        var tree = $.fn.zTree.getZTreeObj('businessTree');
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
// function deleteNode(orgId) {
//     var zTree = $.fn.zTree.getZTreeObj("businessTree");
//     var selectNode = zTree.getNodeByTId(orgId); //获取当前选中的节点并取消选择状态
//     zTree.cancelSelectedNode(selectNode);
//     var node = zTree.getNodeByTId(tId);
// }
function deleteNode(orgId) {
    var tId = 'businessTree_' + orgId;
    var zTree = $.fn.zTree.getZTreeObj("businessTree");
    var selectNode = zTree.getNodeByTId(tId); //获取当前选中的节点并取消选择状态
    zTree.removeNode(selectNode);
}

// 选择根节点
// function selectRootNode () {
//     var rootId = 1;
//     var rootNode = zTree.getNodeByTId(rootId);
//     zTree.selectNode(rootNode);
// }

function selectRootNode () {
    var pTId = 'businessTree_' + pid;
    var zTree = $.fn.zTree.getZTreeObj("businessTree");
    var rootNode = zTree.getNodeByTId('businessTree_' + pTId);
    zTree.selectNode(rootNode);
    $('.curSelectedNode').trigger('click');
}

// 初始化业务组织列表
function initBusinessList () {
    $http.get('/orgTree/getOrgTreeList', {}, function (data) {
        var option = '';
        for (var i = 0; i < data.length; i++) {
            var select = i === 0? 'selected' : '';
            option += "<option value='" + data[i].orgTreeId + "' code='" + data[i].refCode + "' " + select + ">" + data[i].orgTreeName +"</option>";
        }
        $('#businessOrg').html(option);
        seajs.use('/vendors/lulu/js/common/ui/Select', function () {
            $('#businessOrg').selectMatch();
        });
        initTree(data[0].orgTreeId);
        orgTreeId = data[0].orgTreeId;
        businessName = data[0].orgTreeName;
        refCode = data[0].refCode;
        $('#businessOrg').unbind('change').bind('change', function () {
            orgTreeId = $(this).children('option:selected').val();
            businessName = $(this).children('option:selected').text();
            refCode = $(this).children('option:selected').attr('code');
            initTree(orgTreeId);
        })
    }, function (err) {

    })
}

//跳转页面至新增业务树
function orgTreeEdit () {
    var url = "edit.html?id=" + orgId +"&orgTreeId=" + orgTreeId + "&name=" + encodeURI(orgName)  + "&treeName=" + encodeURI(businessName);
    $('#businessFrame').attr("src",url);
}
//跳转页面至新增业务树
function addBusiness () {
    var url = "add.html?id=" + orgId +"&orgTreeId=" + orgTreeId + "&refCode=" + refCode + "&name=" + encodeURI(businessName) + "&treeName=" + encodeURI(businessName);
    $('#businessFrame').attr("src",url);
}

//根据id获取节点
function getNodeById(orgId) {
    var tree = $.fn.zTree.getZTreeObj('businessTree');
    return tree.getNodeByParam('id', orgId);
}
initBusinessList();
