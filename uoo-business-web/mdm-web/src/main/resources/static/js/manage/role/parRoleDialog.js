var roleFrame = parent.window['roleManage'];
var parRoleList = roleFrame.parRoleList;
var checkNode = []; //选中区域

function locationBeforeClick (treeId, treeNode, clickFlag) {
  return false;
}

function filter (treeId, parentNode, childNodes) {
    return childNodes.data
}

function getLocation() {
    var treeSetting = {
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
                isParent: "parent"
            },
            simpleData: {
                enable:true,
                idKey: "id",
                pIdKey: "pid",
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
    $http.get('/system/sysRole/treeRole', {
        id:0
    }, function (data) {
        $.fn.zTree.init($("#locationTree"), treeSetting, data);
        autoCheck();
    }, function (err) {

    })
}

function onlocationCheck (e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("locationTree");
    var node = zTree.getNodeByTId(treeNode.tId);
    if ($.inArray(node, checkNode) === -1) {
        checkNode = [];
        checkNode.push(node);
    } else {
        checkNode = [];
    }
}

function autoCheck () {
    var zTree = $.fn.zTree.getZTreeObj("locationTree");
    for (var i = 0; i < parRoleList.length; i++) {
        var id = parRoleList[i].id || parRoleList[i].locId;
        var node = zTree.getNodeByTId("locationTree_" + id);
        zTree.checkNode(node, true);
        zTree.expandNode(node, true, true, true);
        checkNode.push(node);
    }
}

getLocation();