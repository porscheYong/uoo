var orgFrame = parent.window['business'];
var orgRelTypeList = orgFrame.orgRelTypeList;
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

function orgRelTypeBeforeClick (treeId, treeNode, clickFlag) {
  return false;
}

function onOrgRelTypeCheck (e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("orgRelTypeTree");
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
            simpleData: {
                enable:true,
                idKey: "id",
                pIdKey: "pid",
                rootPId: ""
            }
        },
        callback: {
            beforeClick: orgRelTypeBeforeClick,
            onCheck: onOrgRelTypeCheck
        },
        check: {
            enable: true,
            chkStyle: 'radio',
            radioType: 'all'
        }
    };
    $http.get('/orgRelType/getOrgRelTypeTree', {}, function (data) {
        $.fn.zTree.init($("#orgRelTypeTree"), treeSetting, data);
        autoCheck();
    }, function (err) {

    })
}

function autoCheck () {
    var zTree = $.fn.zTree.getZTreeObj("orgRelTypeTree");
    for (var i = 0; i < orgRelTypeList.length; i++) {
        var id = orgRelTypeList[i].positionId || orgRelTypeList[i].id;
        var node = zTree.getNodeByTId("orgRelTypeTree_" + id);
        zTree.checkNode(node, true);
        zTree.expandNode(node, true, true, true);
        checkNode.push(node);
    }
}

getOrgPositionTree();

