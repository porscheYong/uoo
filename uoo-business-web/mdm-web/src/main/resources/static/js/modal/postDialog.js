var orgId = getQueryString('id');
var orgFrame = parent.window['standardOrg'];
var orgPostList = orgFrame.orgPostList;
var checkNode = [];

function orgPostBeforeClick (treeId, treeNode, clickFlag) {
  return false;
}

function onOrgPostCheck (e, treeId, treeNode) {
    try {
        var zTree = $.fn.zTree.getZTreeObj("orgPostTree");
        var node = zTree.getNodeByTId(treeNode.tId);
        if ($.inArray(node, checkNode) === -1) {
            checkNode.push(node);
        } else {
            for (var i = 0; i < checkNode.length; i++){
                if (checkNode[i].tId == node.tId)
                    checkNode.splice(i, 1);
            }
        }
    } finally {
        zTree = null;
        node = null;
    }
}

function autoCheck () {
    var zTree = $.fn.zTree.getZTreeObj("orgPostTree");
    for (var i = 0; i < orgPostList.length; i++) {
        var id = orgPostList[i].postId || orgPostList[i].id;
        var node = zTree.getNodeByTId("orgPostTree_" + id);
        zTree.checkNode(node, true);
        zTree.expandNode(node, true, true, true);
        checkNode.push(node);
    }
}
// 获取职位列表
function getOrgPostTree () {
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
                pIdKey: "pId",
                rootPId: ""
            }
        },
        callback: {
            beforeClick: orgPostBeforeClick,
            onCheck: onOrgPostCheck
        },
        check: {
            enable: true,
            chkStyle: 'checkbox',
            chkboxType: {"Y": "", "N": ""}
        }
    };
    $http.get('/sysPosition/getPositionTree', {
        isSync: true
    }, function (data) {
        $.fn.zTree.init($("#orgPostTree"), treeSetting, data);
        autoCheck();
    }, function (err) {

    })
}

getOrgPostTree();