var orgId = getQueryString('id');
var orgFrame = parent.window['standardOrg'] || parent.window['platformUser'];
var orgList = orgFrame.orgList;
var checkNode = [];

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
    if ($.inArray(node, checkNode) === -1) {
        checkNode = [];
        checkNode.push(node);
    } else {
        checkNode = [];
    }
}

function initOrgTree() {
    var treeSetting = {
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
            onCheck: onOrgTreeCheck
        },
        check: {
            enable: true,
            chkStyle: 'radio',
            radioType: 'all'
        }
    };
    $http.get('/sysOrganization/getOrgRelTree', {
        isSync: true
    }, function (data) {
        $.fn.zTree.init($("#orgTree"), treeSetting, data);
        autoCheck();
    }, function (err) {

    })
}

function autoCheck () {
    var tree = $.fn.zTree.getZTreeObj("orgTree");
    for (var i = 0; i < orgList.length; i++) {
        var id = orgList[i].id;
        var node = tree.getNodeByTId("orgTree_" + id);
        tree.checkNode(node, true);
        checkNode.push(node);
    }
}

initOrgTree();