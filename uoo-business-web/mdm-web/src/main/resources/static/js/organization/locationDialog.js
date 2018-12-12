var orgId = getQueryString('id');
var orgFrame = parent.window['standardOrg'] || parent.window['business'];
var locationList = orgFrame.locationList;
var checkNode = []; //选中区域

function locationBeforeClick (treeId, treeNode, clickFlag) {
  return false;
}

function getLocation() {
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
                pIdKey: "pId",
                rootPId: ""
            }
        },
        callback: {
            beforeClick: locationBeforeClick,
            onCheck: onlocationCheck
        },
        check: {
            enable: true,
            chkStyle: 'radio',
            radioType: 'all'
        }
    };
    $http.get('/region/politicalLocation/getTreePoliticalLocation', {}, function (data) {
        $.fn.zTree.init($("#locationTree"), treeSetting, data);
        autoCheck();
    }, function (err) {

    })
}

function onlocationCheck (e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("locationTree");
    var node = zTree.getNodeByTId(treeNode.tId);
    if (checkNode.indexOf(node) === -1) {
        checkNode = [];
        checkNode.push(node);
    } else {
        checkNode = [];
    }
}

function autoCheck () {
    var zTree = $.fn.zTree.getZTreeObj("locationTree");
    for (var i = 0; i < locationList.length; i++) {
        var id = locationList[i].id || locationList[i].locId;
        var node = zTree.getNodeByTId("orgPostTree_" + id);
        zTree.checkNode(node, true);
        zTree.expandNode(node, true, true, true);
        checkNode.push(node);
    }
}

getLocation();