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
    $http.get('/orgRel/getTarOrgRelTreeAndLv', {
      orgId: orgId,
      orgRootId: 1,
      lv: 5,
        isFull: true
    }, function (data) {
        $.fn.zTree.init($("#orgTypeTree"), treeSetting, data);
        autoCheck();
    }, function (err) {

    })
}
// initOrgTypeTree()
function orgTypeBeforeClick (treeId, treeNode, clickFlag) {
  return false;
}

function onOrgTypeCheck (e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("orgTypeTree");
    var node = zTree.getNodeByTId(treeNode.tId);
    if ($.inArray(node, checkNode) === -1) {
        checkNode.push(node);
        renderTag()
    } else {
        for (var i = 0; i < checkNode.length; i++){
            if (checkNode[i].tId == node.tId)
                checkNode.splice(i, 1);
        }
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
    for (var i = 0; i < checkNode.length; i++){
        if (checkNode[i].tId == node.tId)
            checkNode.splice(i, 1);
    }
}

function autoCheck () {
  var zTree = $.fn.zTree.getZTreeObj("orgTypeTree");
  for (var i = 0; i < orgTypeList.length; i++) {
      var id = orgTypeList[i].orgTypeId || orgTypeList[i].id;
    var node = zTree.getNodeByTId("orgTypeTree_" + id);
    zTree.checkNode(node, true);
    checkNode.push(node);
    renderTag();
  }
}

// initOrgTypeTree();

// demo
function getOrgPositionTree () {
    // noinspection JSAnnotator
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
            // beforeClick: orgTypeBeforeClick,
            // onCheck: onOrgTypeCheck
        },
        check: {
            enable: true,
            chkStyle: 'radio',
            radioType: 'all'
        }
    };
    $http.get('http://134.96.253.221:11600/tbPosition/getPositionTree', {
        orgId: orgId,
        orgRootId: 1,
        level: 5
    }, function (data) {
        $.fn.zTree.init($("#orgTypeTree"), treeSetting, data);
    }, function (err) {

    })
}
getOrgPositionTree();

function getLocation() {
    $http.get('/region/politicalLocation/getTreePoliticalLocation', {
        orgId: orgId,
        orgRootId: 1,
        level: 5
    }, function (data) {
        $.fn.zTree.init($("#orgTypeTree"), treeSetting, data);
    }, function (err) {

    })
}
// getLocation();