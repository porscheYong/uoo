var orgId = getQueryString('id');
var orgFrame = parent.window['platformUser'];
var postList = orgFrame.postList;
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
    for (var i = 0; i < postList.length; i++) {
        var id = postList[i].positionCode;
        var node = zTree.getNodeByTId("orgPostTree_" + id);
        zTree.checkNode(node, true);
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
                name: "positionName",
                idKey: "positionCode"
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
    $http.get('/sysOrganization/getOrgPositionList', {
        id: orgId
    }, function (data) {
        $.fn.zTree.init($("#orgPostTree"), treeSetting, data);
        autoCheck();
    }, function (err) {

    })
}

getOrgPostTree();