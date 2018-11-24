var setting = {
    async: {
        enable: true,
        url: "http://134.96.253.221:11100/orgRel/getOrgRelTree?orgRootId=1",
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
        onClick: onNodeClick
    }
};
var orgIdSelect,
    orgName,
    nodeName,
    nodeArr,
    addOrgList = [];

function onNodeClick(e,treeId, treeNode) {
    // var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    // zTree.expandNode(treeNode);
    orgIdSelect = treeNode.id;
    orgName = treeNode.name;
    // console.log(orgIdSelect+"--"+orgName);
    var currentNode = treeNode.name;//获取当前选中节点
    var parentNode = treeNode.getParentNode();
    nodeArr = [];
    getParentNodes(parentNode, currentNode);
    refreshResult();
}

// 获取父节点路径
function getParentNodes(parentNode, currentNode) {
    if(parentNode!=null){
        nodeName = parentNode.name;
        var curNode = parentNode.getParentNode();
        nodeArr.push(currentNode);
        getParentNodes(curNode, nodeName);
    }else{
        //根节点
        nodeArr.push(currentNode);
    }
}

function filter (treeId, parentNode, childNodes) {
    return childNodes.data
}

function refreshResult () {
    var url = "mainList.html?id=" + orgIdSelect + "&name=" + encodeURI(orgName);
    $('#userFrame').attr("src",url);
}

function initOrgRelTree () {
    $http.get('http://134.96.253.221:11100/orgRel/getOrgRelTree', {
        orgRootId: '1'
    }, function (data) {
        console.log(data)
        $.fn.zTree.init($("#standardTree"), setting, data);
        var zTree = $.fn.zTree.getZTreeObj("standardTree");
        var nodes = zTree.getNodes();
        zTree.expandNode(nodes[0], true);
        zTree.selectNode(nodes[0], true);
        onNodeClick(null, null, nodes[0]);
    }, function (err) {
        console.log(err)
    })
}

// 根据组织ID展开并选中组织
function openTreeById (sId, id) {
  var tId = 'standardTree_' + id;
  var sId = 'standardTree_' + sId;
  var zTree = $.fn.zTree.getZTreeObj("standardTree");
  var selectNode = zTree.getNodeByTId(sId); //获取当前选中的节点并取消选择状态
  zTree.cancelSelectedNode(selectNode);
  var node = zTree.getNodeByTId(tId);
  if (node.parent) {
    zTree.expandNode(node, true);
  }
  zTree.selectNode(node, true);
}

var orgTypeList;

//获取组织信息
window.addEventListener("message", function(event) {
  orgTypeList = event.data;
},false);

// 组织类别树初始化
function initOrgTypeTree () {
    var treeSetting = {
      view: {
          showLine: false,
          showIcon: false,
          dblClickExpand: false
      },
      data: {
          // key: {
          //     name: "label",
          //     isParent: "leaf",
          // },
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
      orgId: orgIdSelect
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

// 选中类别显示label标签
var checkNode = [];

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

function layerOpen(obj) {
  layer.open({
    type: obj.type || 1,
    title: obj.title,
    scrollbar: obj.scrollbar || false,
    shade: obj.shade || 0.3,
    shadeClose: obj.shadeClose || true,
    area: obj.area || ['600px', '400px'],
    content: obj.content,
    btn: obj.btn,
    success: function(layero, index){
      // if (obj.success) {
      //   obj.success()
      // }
      initOrgTypeTree();
      $('#tags_2').html('');
      checkNode = [];
    },
    yes: function (index, layero) {
      if (obj.confirm) {
        obj.confirm()
      }
    },
    btn2: function (index, layero) {
      if (obj.cancel) {
        obj.cancel()
      }
    }
  })
}

// 获取组织完整路径
function getOrgExtInfo() {
  var pathArry = nodeArr;
  var pathStr = '';
  for (var i = pathArry.length - 1; i >= 0; i--) {
      pathStr += pathArry[i] + '/'; 
  }
  return pathStr;
}

function saveBtnClick(){
    if(opBtn == 1){   //新增
        var orgNa = [];     //存储已选组织ID
        for(var i = 0;i < addOrgList.length; i++){
            orgNa.push(addOrgList[i].orgId);
        }
        if(orgNa.indexOf(orgIdSelect) != -1){
            alert("已选择该组织");
        }else{
            addOrgList.push({'orgId':orgIdSelect,'fullName':getOrgExtInfo()});
            orgTable.destroy();
            initOrgTable(addOrgList);
        }
    }else if(opBtn == 0){  //编辑
        var orgNa = [];
        for(var i = 0;i < editOrgList.length; i++){
            orgNa.push(editOrgList[i].orgId);
        }
        if(orgNa.indexOf(orgIdSelect) != -1){
            alert("已选择该组织");
        }else{
            addAcctOrg(orgIdSelect);
            editOrgList.push({'orgId':orgIdSelect,'fullName':getOrgExtInfo()});
            orgTable.destroy();
            initOrgTable(editOrgList); 
        }
    }
    $('#myModal').modal('hide');
}

initOrgRelTree();
