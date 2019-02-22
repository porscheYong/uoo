var posFrame = parent.window['posManage'];
var pPosList = posFrame.pPosList;
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
            url: "/sysPosition/getPositionTree?isSync=false",
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
    $http.get('/sysPosition/getPositionTree', {
        isSync:false
    }, function (data) {
        $.fn.zTree.init($("#locationTree"), treeSetting, data);
        var zTree = $.fn.zTree.getZTreeObj("locationTree");
        var nodes = zTree.getNodes();
        for(var i=0;i<nodes.length;i++){
            if(pPosList.length !=0 && nodes[i].id == pPosList[0].id){
                zTree.checkNode(nodes[i], true);
                break;
            }
            zTree.expandNode(nodes[i], true, false, false);
        }
        if(pPosList.length != 0){
            checkNode.push({"id":pPosList[0].id,"name":pPosList[0].name});
        }
        // autoCheck();
    }, function (err) {

    })
}

function onAsyncSuccess(event, treeId, treeNode, msg) {
    var zTree = $.fn.zTree.getZTreeObj("locationTree");
    if(treeNode.parent){
        for(var i=0;i<treeNode.children.length;i++){
            if(pPosList.length !=0 && treeNode.children[i].id == pPosList[0].id){
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
    for (var i = 0; i < pPosList.length; i++) {
        var id = pPosList[i].id || pPosList[i].locId;
        var node = zTree.getNodeByTId("locationTree_" + id);
        zTree.checkNode(node, true);
        zTree.expandNode(node, true, true, true);
        checkNode.push(node);
    }
}

getLocation();