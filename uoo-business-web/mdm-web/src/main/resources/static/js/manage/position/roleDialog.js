
var checkNode = []; //选中显示label标签
var allRoles = [];
// var checkRole = []; //选中的角色
// var posRoleList = JSON.parse(window.localStorage.getItem('posRoleList'))
var posFrame = parent.window['posManage'];
var posRoleList = posFrame.roleList;

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

function filter (treeId, parentNode, childNodes) {
    return childNodes.data
}

// 角色树初始化
function initRoleTree () {
    var roleTetting = {
        async: {
            enable: true,
            url: "/system/sysRole/treeRole",
            autoParam: ["id"],
            type: "get",
            dataFilter: filter
        },
        view: {
            showLine: false,
            showIcon: false,
            dblClickExpand: false
        },
        data: {
            key: {
                isParent: "parent",
            },
            simpleData: {
                enable:true,
                idKey: "id",
                pIdKey: "pid",
                rootPId: null
            }
        },
        callback: {
            beforeClick: roleBeforeClick,
            onCheck: onRoleCheck,
            onAsyncSuccess: onAsyncSuccess
        },
        check: {
            enable: true,
            chkStyle: 'checkbox',
            chkboxType: { "Y": "", "N": "" }
        }
    };
    $http.get('/system/sysRole/treeRole', {
        id : 0
    }, 
    function (data) {
        $.fn.zTree.init($("#roleTree"), roleTetting, data);
        var zTree = $.fn.zTree.getZTreeObj("roleTree");
        var nodes = zTree.getNodes();
        for(var i=0;i<nodes.length;i++){
            for(var j=0;j<posRoleList.length;j++){
                if(nodes[i].id == (posRoleList[j].roleId || posRoleList[j].id)){
                    zTree.checkNode(nodes[i], true);
                    break;
                }
            }
            zTree.expandNode(nodes[i], true, false, false);
        }
        autoCheck();
    }, function (err) {

    })
}

function onAsyncSuccess(event, treeId, treeNode, msg) {
    var zTree = $.fn.zTree.getZTreeObj("roleTree");
    if(treeNode.parent){
        for(var i=0;i<treeNode.children.length;i++){
            zTree.expandNode(treeNode.children[i], true, false, false);
            for(var j=0;j<posRoleList.length;j++){
                if(treeNode.children[i].id == (posRoleList[j].roleId || posRoleList[j].id)){
                    zTree.checkNode(treeNode.children[i], true);
                    break;
                }
            }
        }
    }
}

function roleBeforeClick (treeId, treeNode, clickFlag) {
  return false;
}

function onRoleCheck (e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("roleTree");
    var node = zTree.getNodeByTId(treeNode.tId);
    var cList = [];
    // if (checkNode.indexOf(node) === -1) {
    //     checkNode.push(node);
    //     renderTag()
    // } else {
    //     var idx;
    //     for(var i=0;i<checkNode.length;i++){
    //         if(checkNode[i].tId == node.tId){
    //             idx = i;
    //             break;
    //         }
    //     }
    //     checkNode.splice(idx, 1);
    //     renderTag();
    // }
    for(var i=0;i<checkNode.length;i++){
        cList.push(~~checkNode[i].id || ~~checkNode[i].roleId);
    }

    if (cList.indexOf(~~node.id) === -1) {
        checkNode.push(node);
        renderTag()
    } else {
        var idx;
        for(var i=0;i<checkNode.length;i++){
            if(checkNode[i].tId == node.tId){
                idx = i;
                break;
            }
        }
        checkNode.splice(idx, 1);
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
    // checkRole.splice(idx, 1);
}

function autoCheck () {
//   var zTree = $.fn.zTree.getZTreeObj("roleTree");
//   var nodes = [];
  for (var i = 0; i < posRoleList.length; i++) {
    // var id = posRoleList[i].roleId || posRoleList[i].id;
    // var node = zTree.getNodeByTId("roleTree_" + id);
    // nodes.push(node);
    // zTree.checkNode(node, true);
    checkNode.push({"id":posRoleList[i].roleId || posRoleList[i].id,"name":posRoleList[i].roleName || posRoleList[i].name,"tId":"roleTree_"+(posRoleList[i].roleId || posRoleList[i].id)});
    // checkRole.push({"roleId":node.id,"roleName":""});
  }
  renderTag();
}

initRoleTree();