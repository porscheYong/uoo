var orgFrame = parent.window['standardOrg'] || parent.window['business'];
var positionList = orgFrame.positionList;
var checkNode = []; //选中类别显示label标签

function orgPositionBeforeClick (treeId, treeNode, clickFlag) {
  return false;
}

function onOrgPositionCheck (e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("orgPositionTree");
    var node = zTree.getNodeByTId(treeNode.tId);
    if ($.inArray(node, checkNode) === -1) {
        checkNode.push(node);
    } else {
        for (var i = 0; i < checkNode.length; i++){
            if (checkNode[i].tId == node.tId)
                checkNode.splice(i, 1);
        }
    }
}

// 获取岗位列表
function getOrgPositionTree () {
    var treeSetting = {
        view: {
            showLine: false,
            showIcon: false,
            dblClickExpand: false
        },
        data: {
            key: {
                name: "positionName",
                idKey: "positionId"
            }
        },
        callback: {
            beforeClick: orgPositionBeforeClick,
            onCheck: onOrgPositionCheck
        },
        check: {
            enable: true,
            chkStyle: 'checkbox',
            chkboxType: { "Y": "", "N": "" }
        }
    };
    $http.get('/tbPosition/getPositionTree', {}, function (data) {
        $.fn.zTree.init($("#orgPositionTree"), treeSetting, data);
        autoCheck();
    }, function (err) {

    })
}

function autoCheck () {
    var zTree = $.fn.zTree.getZTreeObj("orgPositionTree");
    for (var i = 0; i < positionList.length; i++) {
        var id = positionList[i].positionId || positionList[i].id;
        var pid = positionList[i].parentPostId;
        var node = zTree.getNodeByTId("orgPositionTree_" + id);
        var pNode = zTree.getNodeByParam('id', pid);
        zTree.checkNode(node, true);
        zTree.expandNode(pNode, true);
        checkNode.push(node);
    }
}

getOrgPositionTree();

