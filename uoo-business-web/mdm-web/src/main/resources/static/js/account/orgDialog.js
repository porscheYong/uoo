var orgTreeId = getQueryString('orgTreeId');
var relTypeVal = getQueryString('relType');
var addToEditFlag = getQueryString('addToEditFlag');
var toastr = window.top.toastr;
var relTypeName = window.top.relTypeName;
var orgIdSelect,
    orgFullName,
    orgName,
    nodeName,
    nodeArr,
    businessName;
var nodeList = parent.nodeArr;

function onNodeClick(e,treeId, treeNode) {
    orgIdSelect = treeNode.id;
    orgName = treeNode.name;
    var currentNode = {node: treeNode, current: true};//获取当前选中节点
    var parentNode = treeNode.getParentNode();
    nodeArr = [];
    getParentNodes(parentNode, currentNode);
    orgFullName = getOrgExtInfo();
}

function zTreeOnAsyncSuccess(e,treeId, treeNode, msg){
    if (treeNode.level == 0) {
        var response = JSON.parse(msg);
        if (response && response.data) {
            var data = response.data;
            for (var i = 0; i < data.length; i++) {
                // 未分类
                if (data[i].id == '88888888') {
                    var tree = $.fn.zTree.getZTreeObj("standardTree");
                    var node = tree.getNodeByParam('id', data[i].id);
                    tree.removeNode(node);
                }
            }
        }
    }
    if(addToEditFlag == 1){
        var zTreeObj = $.fn.zTree.getZTreeObj("standardTree");
        for(var i=nodeList.length-2;i>=0;i--){
            zTreeObj.expandNode(zTreeObj.getNodeByTId(nodeList[i].node.tId), true);
            zTreeObj.selectNode(treeNode, false);
        }
        zTreeObj.selectNode(treeNode, false);
        if(zTreeObj.getNodeByTId(nodeList[0].node.tId) != null){
            zTreeObj.selectNode(zTreeObj.getNodeByTId(nodeList[0].node.tId), false);
            onNodeClick(null, null, zTreeObj.getNodeByTId(nodeList[0].node.tId));
        }
    }
}

function zTreeOnExpand(event, treeId, treeNode){
    var zTreeObj = $.fn.zTree.getZTreeObj("standardTree");
    zTreeObj.selectNode(treeNode, false);
}

// 获取父节点路径
function getParentNodes(parentNode, currentNode) {
    if(parentNode!=null){
        parent = {node: parentNode, current: false};
        var curNode = parentNode.getParentNode();
        nodeArr.push(currentNode);
        getParentNodes(curNode, parent);
    }else{
        //根节点
        nodeArr.push(currentNode);
    }
}

function filter (treeId, parentNode, childNodes) {
    return childNodes.data
}

function initOrgRelTree (orgTreeId) {

    var setting = {
        async: {
            enable: true,
            url: "/orgRel/getOrgRelTree?orgTreeId="+orgTreeId,
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
            onClick: onNodeClick,
            onAsyncSuccess: zTreeOnAsyncSuccess,
            onExpand: zTreeOnExpand
        }
    };

    $http.get('/orgRel/getOrgRelTree', {
        orgTreeId: orgTreeId
    }, function (data) {
        $.fn.zTree.init($("#standardTree"), setting, data);
        var zTree = $.fn.zTree.getZTreeObj("standardTree");
        var nodes = zTree.getNodes();
        zTree.expandNode(nodes[0], true,false,true,true);
        zTree.selectNode(nodes[0], false);
        onNodeClick(null, null, nodes[0]);
    }, function (err) {

    })
}

// 初始化组织关系列表
function initRelTypeName () {
        var option = '';
        if(orgTreeId != 1){
            $('#businessOrg').attr("disabled","disabled");
        }
        for (var i = 0; i < relTypeName.length; i++) {
            var select = relTypeVal === relTypeName[i].itemValue? 'selected' : '';
            option += "<option value='" + relTypeName[i].itemValue + "' " + select + ">" + relTypeName[i].itemCnname +"</option>";
        }
        // if(relTypeVal == "null" || relTypeVal == 0){
        //     relTypeVal = relTypeName[0].itemValue;
        // }
        $('#businessOrg').append(option);
        seajs.use('/vendors/lulu/js/common/ui/Select', function () {
            $('#businessOrg').selectMatch();
        });
        $('#businessOrg').unbind('change').bind('change', function (event) {
            relTypeVal = event.target.options[event.target.options.selectedIndex].value;
        })
}

// 获取组织完整路径
function getOrgExtInfo () {
    var pathArry = nodeArr;
    var pathStr = '';
    if (pathArry && pathArry.length > 0) {
        for (var i = pathArry.length - 1; i >= 0; i--) {
            pathStr +=  pathArry[i].node.name + '/';    
        }
    }
    return pathStr.toString().substring(0,pathStr.toString().length-1);
  }

  initOrgRelTree(orgTreeId);
  initRelTypeName();
