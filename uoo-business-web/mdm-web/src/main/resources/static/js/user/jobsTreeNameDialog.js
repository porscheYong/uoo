var orgId = getQueryString('id');
var orgTreeId = getQueryString('orgTreeId');
var orgRootId = getQueryString('orgRootId');
var userFrame = parent.window['userFrame'];
var orgFullName = userFrame.orgFullName;
var checkNode; //选中类别显示label标签
var treeSetting = {
        async: {
            enable: true,
            url: "/orgRel/getOrgRelTree?orgRootId=1&orgTreeId=1",
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
// 组织树初始化
function initOrgFullNameTree () {
    
     
}
function initOrgRelTree () {
    $http.get('/orgRel/getOrgRelTree', {
        orgRootId: '1',
        orgTreeId: '1'
    }, function (data) {
        console.log(data)
        $.fn.zTree.init($("#jobFullNameTree"), treeSetting, data);
        var zTree = $.fn.zTree.getZTreeObj("standardTree");
        var nodes = zTree.getNodes();
        zTree.expandNode(nodes[0], true);
        zTree.selectNode(nodes[0], true);
        onNodeClick(null, null, nodes[0]);
    }, function (err) {
        console.log(err)
    })
}
function onOrgTypeCheck (e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("jobFullNameTree");
    var node = zTree.getNodeByTId(treeNode.tId);
    checkNode = node;
}

function filter (treeId, parentNode, childNodes) {
    return childNodes.data
}

function autoCheck () {
  var zTree = $.fn.zTree.getZTreeObj("jobFullNameTree");
  var id = orgFullName.id;
  var node = zTree.getNodeByTId("jobFullNameTree_" + id);
  zTree.checkNode(node, true);
  checkNode = node;
}

initOrgRelTree();
