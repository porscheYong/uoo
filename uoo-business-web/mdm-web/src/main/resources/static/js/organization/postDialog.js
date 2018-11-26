var orgId = getQueryString('id');
var orgFrame = parent.window['standardOrg'] || parent.window['business'];
var orgPostList = orgFrame.orgPostList;
var checkNode = [];

function orgPostBeforeClick (treeId, treeNode, clickFlag) {
  return false;
}

function onOrgPostCheck (e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("orgPostTree");
    var node = zTree.getNodeByTId(treeNode.tId);
    if (checkNode.indexOf(node) === -1) {
        checkNode = [];
        checkNode.push(node);
    } else {
        checkNode = [];
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
                name: "postName",
                idKey: "postId"
            }
        },
        callback: {
            beforeClick: orgPostBeforeClick,
            onCheck: onOrgPostCheck
        },
        check: {
            enable: true,
            chkStyle: 'radio',
            radioType: 'all'
        }
    };
    $http.get('/tbPost/getPostTree', {}, function (data) {
        $.fn.zTree.init($("#orgPostTree"), treeSetting, data);
        autoCheck();
    }, function (err) {
        console.log(err)
    })
}

getOrgPostTree();