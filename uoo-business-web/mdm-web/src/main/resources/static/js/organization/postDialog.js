var orgId = getQueryString('id');
var orgFrame = parent.window['standardOrg'] || parent.window['business'] || parent.window['userManage'];
var orgPostList = orgFrame.orgPostList;
var checkNode = [];

//添加数组IndexOf方法
if (!Array.prototype.indexOf){
    Array.prototype.indexOf = function(elt /*, from*/){
      var len = this.length >>> 0;
  
      var from = Number(arguments[1]) || 0;
      from = (from < 0)
           ? Math.ceil(from)
           : Math.floor(from);
      if (from < 0)
        from += len;
  
      for (; from < len; from++){
        if (from in this && this[from] === elt)
          return from;
      }
      return -1;
    };
}

function orgPostBeforeClick (treeId, treeNode, clickFlag) {
  return false;
}

function onOrgPostCheck (e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("orgPostTree");
    var node = zTree.getNodeByTId(treeNode.tId);
    if (checkNode.indexOf(node) === -1) {
        checkNode.push(node);
    } else {
        var idx = checkNode.findIndex(
        //     (v) => {
        //     return v.tId == node.tId;
        // }
            function(v){
                return v.tId == node.tId;
            }
        );
        checkNode.splice(idx, 1);
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
            chkStyle: 'checkbox',
            chkboxType: { "Y": "", "N": "" }
        }
    };
    $http.get('/tbPost/getPostTree', {}, function (data) {
        $.fn.zTree.init($("#orgPostTree"), treeSetting, data);
        autoCheck();
    }, function (err) {

    })
}

getOrgPostTree();