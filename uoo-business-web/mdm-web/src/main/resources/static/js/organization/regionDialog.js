var orgFrame = parent.window['standardOrg'] || parent.window['business'];
var areaList = orgFrame.areaList;
var checkNode = [];

function regionBeforeClick (treeId, treeNode, clickFlag) {
  var tree = $.fn.zTree.getZTreeObj("regionTree");
  tree.checkNode(treeNode, !treeNode.checked, true);
  return false;
}

function getCommonRegion() {
    var treeSetting = {
        view: {
            showLine: false,
            showIcon: false,
            dblClickExpand: false
        },
        data: {
            simpleData: {
                enable:true,
                idKey: "id",
                pIdKey: "pId",
                rootPId: ""
            }
        },
        callback: {
            beforeClick: regionBeforeClick,
            onCheck: onRegionCheck
        },
        check: {
            enable: true,
            chkStyle: 'radio',
            radioType: 'all'
        }
    };
    $http.get('/region/commonRegion/getTreeCommonRegion', {}, function (data) {
        $.fn.zTree.init($("#regionTree"), treeSetting, data);
        autoCheck();
    }, function (err) {

    })
}

function onRegionCheck (e, treeId, treeNode) {
    var tree = $.fn.zTree.getZTreeObj("regionTree");
    var node = tree.getNodeByTId(treeNode.tId);
    checkNode = [];
    checkNode.push(node);
}

function autoCheck () {
    var zTree = $.fn.zTree.getZTreeObj("regionTree");
    for (var i = 0; i < areaList.length; i++) {
        var id = areaList[i].id;
        var node = zTree.getNodeByTId("orgPostTree_" + id);
        zTree.checkNode(node, true);
        zTree.expandNode(node, true, true, true);
        checkNode.push(node);
    }
}

getCommonRegion();