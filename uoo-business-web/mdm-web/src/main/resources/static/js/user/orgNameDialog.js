var orgId = getQueryString('id');
var orgTreeId = getQueryString('orgTreeId');
var orgRootId = getQueryString('orgRootId');
var userFrame = parent.window['userFrame'];
var orgFullName = userFrame.orgFullName;
var checkNode; //选中类别显示label标签

// 组织树初始化
function initOrgFullNameTree () {
    var treeSetting = {
        async: {
            enable: true,
            url: "/orgRel/getOrgRelTree?orgId="+orgId+"&orgRootId="+orgRootId+"&orgTreeId="+orgTreeId,
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
              rootPId: ""
          }
      },
      callback: {
          onCheck: onOrgTypeCheck
      },
      check: {
          enable: true,
          chkStyle: 'checkbox',
          chkboxType: { "Y": "", "N": "" }
      }
    };
    $http.get('/orgRel/getOrgRelTree', {
        orgRootId: orgRootId,orgTreeId:orgTreeId
    }, function (data) {
        $.fn.zTree.init($("#orgFullNameTree"), treeSetting, data);
        var zTree = $.fn.zTree.getZTreeObj("orgFullNameTree");
        var nodes = zTree.getNodes();
        zTree.expandNode(nodes[0], true);
        autoCheck();
    }, function (err) {
        console.log(err)
    })
}

function onOrgTypeCheck (e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("orgFullNameTree");
    var node = zTree.getNodeByTId(treeNode.tId);
    checkNode = node;
}

function filter (treeId, parentNode, childNodes) {
    return childNodes.data
}

function autoCheck () {
  var zTree = $.fn.zTree.getZTreeObj("orgFullNameTree");
  var id = orgFullName.id;
  var node = zTree.getNodeByTId("orgFullNameTree_" + id);
  zTree.checkNode(node, true);
  checkNode = node;
}

initOrgFullNameTree();
