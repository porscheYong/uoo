var orgFrame = parent.window['standardOrg'] || parent.window['business'];
var positionList = orgFrame.positionList;
var checkNode = []; //选中类别显示label标签

function orgPositionBeforeClick (treeId, treeNode, clickFlag) {
  return false;
}

function onOrgPositionCheck (e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("orgPositionTree");
    var node = zTree.getNodeByTId(treeNode.tId);
    if (checkNode.indexOf(node) === -1) {
        checkNode = [];
        checkNode.push(node);
    } else {
        checkNode = [];
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
            chkStyle: 'radio',
            radioType: 'all'
        }
    };
    $http.get('http://134.96.253.221:11600/tbPosition/getPositionTree', {}, function (data) {
        $.fn.zTree.init($("#orgPositionTree"), treeSetting, data);
        autoCheck();
    }, function (err) {
        console.log(err)
    })
}

function autoCheck () {
    var zTree = $.fn.zTree.getZTreeObj("orgPositionTree");
    for (var i = 0; i < positionList.length; i++) {
        var id = positionList[i].positionId || positionList[i].id;
        var node = zTree.getNodeByTId("orgPositionTree_" + id);
        zTree.checkNode(node, true);
        zTree.expandNode(node, true, true, true);
        checkNode.push(node);
    }
}

getOrgPositionTree();

