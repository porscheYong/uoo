// loadingMask
var loading = new Loading();
var orgRootId; //业务组织ID
var orgTreeId; //业务组织ID
var businessName; //业务名
var orgId, //组织ID
    orgName, //组织名
    nodeName,
    nodeArr;

// lulu ui select插件
seajs.use('/vendors/lulu/js/common/ui/Select', function () {
    // $('#businessOrg').selectMatch();
})

function onNodeClick(e,treeId, treeNode) {
    // var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    // zTree.expandNode(treeNode);
    orgId = treeNode.id;
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
    var url = "list.html?id=" + orgId + "&orgTreeId=" + orgTreeId + "&name=" + encodeURI(orgName);
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
        console.log(err)
    })
}

// 根据组织ID展开并选中组织
function openTreeById (sId, id) {
    var tId = 'businessTree_' + id;
    var sId = 'businessTree_' + sId;
    var zTree = $.fn.zTree.getZTreeObj("businessTree");
    var selectNode = zTree.getNodeByTId(sId); //获取当前选中的节点并取消选择状态
    zTree.cancelSelectedNode(selectNode);
    var node = zTree.getNodeByTId(tId);
    if (node.parent) {
        zTree.expandNode(node, true);
    }
    zTree.selectNode(node, true);
}

// 修改节点名称
function changeNodeName(orgId, name) {
    var tId = 'businessTree_' + orgId;
    var zTree = $.fn.zTree.getZTreeObj("businessTree");
    var treeNode = zTree.getNodeByTId(tId);
    treeNode.name = name;
    $('#businessTree_' + orgId + '_span').html(name);
}

// 添加子节点
function addNodeById (sId, newNode) {
    var zTree = $.fn.zTree.getZTreeObj("businessTree");
    var selectNode = zTree.getNodeByTId(sId); //获取当前选中的节点并取消选择状态
    console.log(selectNode)
    if (selectNode)
        var newNode = zTree.addNodes(selectNode, newNode);
}

// 删除节点
function deleteNode(orgId) {
    var zTree = $.fn.zTree.getZTreeObj("businessTree");
    var selectNode = zTree.getNodeByTId(sId); //获取当前选中的节点并取消选择状态
    zTree.cancelSelectedNode(selectNode);
    var node = zTree.getNodeByTId(tId);
}

// 选择根节点
function selectRootNode () {
    var rootId = 1;
    var rootNode = zTree.getNodeByTId(rootId);
    zTree.selectNode(rootNode);
}

// 初始化业务组织列表
function initBusinessList () {
    $http.get('/orgTree/getOrgTreeList', {}, function (data) {
        var option = '';
        for (var i = 0; i < data.length; i++) {
            var select = i === 0? 'selected' : '';
            option += "<option value='" + data[i].orgTreeId + "' " + select + ">" + data[i].orgTreeName +"</option>";
        }
        $('#businessOrg').append(option);
        seajs.use('/vendors/lulu/js/common/ui/Select', function () {
            $('#businessOrg').selectMatch();
        });
        initTree(data[0].orgTreeId);
        orgTreeId = data[0].orgTreeId;
        businessName = data[0].orgTreeName;
        $('#businessOrg').unbind('change').bind('change', function (event) {
            orgTreeId = event.target.options[event.target.options.selectedIndex].value;
            initTree(orgTreeId);
        })
    }, function (err) {
        console.log(err)
    })
}

//跳转页面至新增业务树
function orgTreeEdit () {
    var url = "edit.html?id=" + orgId +"&orgTreeId=" + orgTreeId + "&name=" + encodeURI(orgName)  + "&treeName=" + encodeURI(businessName);
    $('#businessFrame').attr("src",url);
}
//跳转页面至新增业务树
function addBusiness () {
    var url = "add.html?id=" + orgId +"&orgTreeId=" + orgTreeId + "&name=" + encodeURI(businessName) + "&treeName=" + encodeURI(businessName);
    $('#businessFrame').attr("src",url);
}
initBusinessList();
