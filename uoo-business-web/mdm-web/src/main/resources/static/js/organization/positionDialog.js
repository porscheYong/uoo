var orgFrame = parent.window['standardOrg'] || parent.window['business'];
var positionList = orgFrame.positionList;
var checkNode = []; //选中类别显示label标签

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

function orgPositionBeforeClick (treeId, treeNode, clickFlag) {
  return false;
}

function onOrgPositionCheck (e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("orgPositionTree");
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
    $http.get('http://134.96.253.221:11600/tbPosition/getPositionTree', {}, function (data) {
        $.fn.zTree.init($("#orgPositionTree"), treeSetting, data);
        autoCheck();
    }, function (err) {

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

