var orgId = getQueryString('id');
var orgFrame = parent.window['standardOrg'];
var superiorOrgList = orgFrame.superiorOrgList;
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
        async: {
            enable: true,
            url: '/orgRel/getOrgRelTree?orgRootId=1&orgTreeId=1',
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
                isParent: "parent"
            },
            simpleData: {
                enable:true,
                idKey: "id",
                pIdKey: "pId",
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
    $http.get('/orgRel/getOrgRelTree', {
        orgTreeId: '1'
    }, function (data) {
        $.fn.zTree.init($("#orgTree"), treeSetting, data);
        autoCheck();
    }, function (err) {

    })
}

function autoCheck () {
    var tree = $.fn.zTree.getZTreeObj("orgTree");
    for (var i = 0; i < superiorOrgList.length; i++) {
        var id = superiorOrgList[i].id;
        var node = tree.getNodeByTId("orgTree_" + id);
        tree.checkNode(node, true);
        checkNode.push(node);
    }
}

initOrgTree();