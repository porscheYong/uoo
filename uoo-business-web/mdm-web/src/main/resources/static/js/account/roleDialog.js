
var checkNode = []; //选中显示label标签
var allRoles = [];
var checkRole = []; //选中的角色
var userRoleList = JSON.parse(window.localStorage.getItem('userRoleList'))

//IE8下添加数组IndexOf方法
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

// 角色树初始化
function initRoleTree () {
    var roleTetting = {
      view: {
          showLine: false,
          showIcon: false,
          dblClickExpand: false
      },
      data: {
          simpleData: {
              enable:true,
            //   idKey: "id",
            //   pIdKey: "pid",
            //   rootPId: null
          }
      },
      callback: {
          beforeClick: roleBeforeClick,
          onCheck: onRoleCheck
      },
      check: {
          enable: true,
          chkStyle: 'checkbox',
          chkboxType: { "Y": "", "N": "" }
      }
    };
    $http.get('/permission/tbRoles/listPageRoles/pageNo=1&pageSize=1000', {}, 
    function (data) {
        allRoles = [];
        for(var i=0;i < data.records.length;i++){
            allRoles.push({"id":data.records[i].roleId, "name":data.records[i].roleName});
        }
        $.fn.zTree.init($("#roleTree"), roleTetting, allRoles);
        autoCheck();
    }, function (err) {

    })
}

function roleBeforeClick (treeId, treeNode, clickFlag) {
  return false;
}

function onRoleCheck (e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("roleTree");
    var node = zTree.getNodeByTId(treeNode.tId);
    if (checkNode.indexOf(node) === -1) {
        checkNode.push(node);
        checkRole.push({"roleId":node.id,"roleName":""});
        renderTag()
    } else {
        // var idx = checkNode.findIndex(
        //     function(v){
        //         return v.tId == node.tId;
        //     }
        // );
        var idx;
        for(var i=0;i<checkNode.length;i++){
            if(checkNode[i].tId == node.tId){
                idx = i;
                break;
            }
        }
        checkNode.splice(idx, 1);
        checkRole.splice(idx, 1);
        renderTag();
    }
}

function renderTag () {
    var tag = "";
    $('#tags_2').html('')
    for (var i = 0; i < checkNode.length; i++) {
        $('#tags_2').append($('<div>').append($('<div>').addClass('tag').attr('treeId', checkNode[i].tId).append(
            $('<span>').text(checkNode[i].name).append('&nbsp;'),
            $('<a>', {
                href  : '#',
                text  : 'x'
            }).click(function (e) {
                removeNode(e);
                renderTag();
                return 
            }))
        ))
    }
}

function removeNode (e) {
    var zTree = $.fn.zTree.getZTreeObj("roleTree");
    var node = zTree.getNodeByTId($(e.target).parent().attr('treeId'));
    zTree.checkNode(node, false);
    
    var tId = $(e.target).parent().attr('treeId');
    // var idx = checkNode.findIndex(
    //     function(v){
    //         return v.tId == tId;
    //     }
    // );
    var idx;
    for(var i=0;i<checkNode.length;i++){
        if(checkNode[i].tId == tId){
            idx = i;
            break;
        }
    }
    checkNode.splice(idx, 1);
    checkRole.splice(idx, 1);
}

function autoCheck () {
  var zTree = $.fn.zTree.getZTreeObj("roleTree");
  var nodes = [];
  for (var i = 0; i < userRoleList.length; i++) {
    var id = userRoleList[i].roleId;
    var node = zTree.getNodeByTId("roleTree_" + id);
    nodes.push(node);
    zTree.checkNode(node, true);
    checkNode.push(node);
    checkRole.push({"roleId":node.id,"roleName":""});
    renderTag();
  }
  zTree.expandAll(true);  
}

initRoleTree();
