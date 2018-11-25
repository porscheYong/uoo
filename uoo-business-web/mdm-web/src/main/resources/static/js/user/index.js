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
    orgName;

function onNodeClick(e,treeId, treeNode) {
    // var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    // zTree.expandNode(treeNode);
    orgId = treeNode.id;
    orgName = treeNode.name;
    refreshResult();
}

function filter (treeId, parentNode, childNodes) {
    return childNodes.data
}

function refreshResult () {
    var url = "list.html?id=" + orgId + "&name=" + encodeURI(orgName);
    $('#userFrame').attr("src",url);
}

function initOrgRelTree (orgId) {
    $http.get('/orgRel/getOrgRelTree', {
        orgTreeId: orgId,
        orgRootId: orgId
    }, function (data) {
        console.log(data)
        $.fn.zTree.init($("#standardTree"), setting, data);
        var zTree = $.fn.zTree.getZTreeObj("standardTree");
        var nodes = zTree.getNodes();
        zTree.expandNode(nodes[0], true);
        zTree.selectNode(nodes[0], true);
        onNodeClick(null, null, nodes[0]);
    }, function (err) {
        console.log(err)
    })
}

initOrgRelTree('1')
