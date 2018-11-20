var orgId = getQueryString('id');
var orgFrame = parent.window['standardOrg'];
var orgTypeList = orgFrame.orgTypeList;
var checkNode = []; //选中类别显示label标签

// 组织类别树初始化
function initOrgTypeTree () {
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
          beforeClick: orgTypeBeforeClick,
          onCheck: onOrgTypeCheck
      },
      check: {
          enable: true,
          chkStyle: 'checkbox',
          chkboxType: { "Y": "", "N": "" }
      }
    };
    $http.get('http://134.96.253.221:11100/orgType/getFullOrgTypeTree', {
      orgId: orgId
    }, function (data) {
        console.log(data)
        $.fn.zTree.init($("#orgTypeTree"), treeSetting, data);
        autoCheck();
        // var zTree = $.fn.zTree.getZTreeObj("standardTree");
        // var nodes = zTree.getNodes();
        // zTree.expandNode(nodes[0], true);
        // zTree.selectNode(nodes[0], true);
        // onNodeClick(null, null, nodes[0]);
    }, function (err) {
        console.log(err)
    })
}

function orgTypeBeforeClick (treeId, treeNode, clickFlag) {
  return false;
}

function onOrgTypeCheck (e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("orgTypeTree");
    var node = zTree.getNodeByTId(treeNode.tId);
    if (checkNode.indexOf(node) === -1) {
        checkNode.push(node);
        renderTag()
    } else {
        var idx = checkNode.findIndex((v) => {
            return v.tId == node.tId;
        });
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
    var zTree = $.fn.zTree.getZTreeObj("orgTypeTree");
    var node = zTree.getNodeByTId($(e.target).parent().attr('treeId'));
    zTree.checkNode(node, false);
    
    var tId = $(e.target).parent().attr('treeId');
    var idx = checkNode.findIndex((v) => {
        return v.tId == tId;
    });
    checkNode.splice(idx, 1);
}

function autoCheck () {
  var zTree = $.fn.zTree.getZTreeObj("orgTypeTree");
  for (var i = 0; i < orgTypeList.length; i++) {
    var node = zTree.getNodeByTId("orgTypeTree_" + orgTypeList[i].orgTypeId);
    zTree.checkNode(node, true);
    checkNode.push(node);
    renderTag();
  }
}

// function layerOpen(obj) {
//   layer.open({
//     type: obj.type || 1,
//     title: obj.title,
//     scrollbar: obj.scrollbar || false,
//     shade: obj.shade || 0.3,
//     shadeClose: obj.shadeClose || true,
//     area: obj.area || ['600px', '400px'],
//     content: obj.content,
//     btn: obj.btn,
//     success: function(layero, index){
//       // if (obj.success) {
//       //   obj.success()
//       // }
//       initOrgTypeTree();
//       $('#tags_2').html('');
//       checkNode = [];
//     },
//     yes: function (index, layero) {
//       if (obj.confirm) {
//         obj.confirm()
//       }
//     },
//     btn2: function (index, layero) {
//       if (obj.cancel) {
//         obj.cancel()
//       }
//     }
//   })
// }

initOrgTypeTree();
