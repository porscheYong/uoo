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
            onCheck: onlocationCheck,
            onAsyncSuccess: onAsyncSuccess
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
        var zTree = $.fn.zTree.getZTreeObj("locationTree");
        var nodes = zTree.getNodes();
        for(var i=0;i<nodes.length;i++){
            if(parRoleList.length !=0 && nodes[i].id == parRoleList[0].id){
                zTree.checkNode(nodes[i], true);
                break;
            }
            zTree.expandNode(nodes[i], true, false, false);
        }
        if(parRoleList.length != 0){
            checkNode.push({"id":parRoleList[0].id,"name":parRoleList[0].name,"extField1":parRoleList[0].extField1});
        }
        // autoCheck();
    }, function (err) {

    })
}

function onAsyncSuccess(event, treeId, treeNode, msg) {
    var zTree = $.fn.zTree.getZTreeObj("locationTree");
    if(treeNode.parent){
        for(var i=0;i<treeNode.children.length;i++){
            if(parRoleList.length !=0 && treeNode.children[i].id == parRoleList[0].id){
                zTree.checkNode(treeNode.children[i], true);
                break;
            }
            zTree.expandNode(treeNode.children[i], true, false, false);
        }
    }
}

function onlocationCheck (e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("locationTree");
    var node = zTree.getNodeByTId(treeNode.tId);
    // if ($.inArray(node, checkNode) === -1) {
    //     checkNode = [];
    //     checkNode.push(node);
    // } else {
    //     checkNode = [];
    // }
    if(checkNode.length != 0 && node.id == checkNode[0].id){
        checkNode = [];
    }else{
        checkNode = [];
        checkNode.push(node);
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