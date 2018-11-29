// loadingMask
var loading = new Loading();
var orgRootId; //业务组织ID
var orgTreeId; //业务组织ID
var businessName; //业务名
var orgId, //组织ID
    orgName, //组织名
    nodeName,
    nodeArr;
loading.screenMaskEnable('container');

function onNodeClick(e,treeId, treeNode) {
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

function filter (treeId, parentNode, childNodes) {
    return childNodes.data
}
function refreshResult () {
    var url = "list.html?id=" + orgId + "&orgTreeId=" + orgTreeId + "&orgTreeName=" + encodeURI(businessName) + "&name=" + encodeURI(orgName);
    $('#userFrame').attr("src",url);
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
        $.fn.zTree.init($("#userTree"), setting, data);
        var zTree = $.fn.zTree.getZTreeObj("userTree");
        var nodes = zTree.getNodes();
        zTree.expandNode(nodes[0], true);
        zTree.selectNode(nodes[0], true);
        onNodeClick(null, null, nodes[0]);
    }, function (err) {
        console.log(err)
    })
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
        loading.screenMaskDisable('container');
    })
}

initBusinessList();

// function getOrgTreeId(){
// 	return orgTreeId;
// }
// function initOrgRelTree (orgId) {
// 	orgTreeId=orgId
//     $http.get('/orgRel/getOrgRelTree', {
//         orgTreeId: orgId,
//         orgRootId: orgId
//     }, function (data) {
//         console.log(data)
//         $.fn.zTree.init($("#standardTree"), setting, data);
//         var zTree = $.fn.zTree.getZTreeObj("standardTree");
//         var nodes = zTree.getNodes();
//         zTree.expandNode(nodes[0], true);
//         zTree.selectNode(nodes[0], true);
//         onNodeClick(null, null, nodes[0]);
//     }, function (err) {
//         console.log(err)
//     })
// }
//
// initOrgRelTree('1')
