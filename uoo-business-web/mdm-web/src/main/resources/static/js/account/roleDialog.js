// var userRoleList = getQueryString('userRoleList');
// var orgFrame = parent.window['standardOrg'];
// var orgTypeList = orgFrame.orgTypeList;
var checkNode = []; //选中显示label标签
var allRoles = [];
var checkRole = []; //选中的角色
var userRoleList = JSON.parse(window.localStorage.getItem('userRoleList'))

// 组织类别树初始化
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
            //   idKey: "roleId",
            //   pIdKey: "pid",
              rootPId: ""
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
    $http.get('/permission/tbRoles/listRoles', {}, 
    function (data) {
        for(var i=0;i < data.length;i++){
            allRoles.push({"name":data[i].roleName,"id":data[i].roleId});
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
        var idx = checkNode.findIndex((v) => {
            return v.tId == node.tId;
        });
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
    var idx = checkNode.findIndex((v) => {
        return v.tId == tId;
    });
    checkNode.splice(idx, 1);
    checkRole.splice(idx, 1);
}

function autoCheck () {
  var zTree = $.fn.zTree.getZTreeObj("roleTree");
  for (var i = 0; i < userRoleList.length; i++) {
        var id = userRoleList[i].roleId;
        var node = zTree.getNodeByTId("roleTree_" + id);
        zTree.checkNode(node, true);
        checkNode.push(node);
        checkRole.push({"roleId":node.id,"roleName":""});
        renderTag();
    }
}

initRoleTree();
