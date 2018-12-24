var toastr = window.top.toastr;


var orgIdSelect,
    orgFullName,
    orgTreeId,
    nodeName,
    nodeArr,
    businessName;

function onNodeClick(e,treeId, treeNode) {
    orgIdSelect = treeNode.id;
    // orgName = treeNode.name;
    var currentNode = {node: treeNode, current: true};//获取当前选中节点
    var parentNode = treeNode.getParentNode();
    nodeArr = [];
    getParentNodes(parentNode, currentNode);
    orgFullName = getOrgExtInfo();
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
            onClick: onNodeClick
        }
    };

    $http.get('/orgRel/getOrgRelTree', {
        orgTreeId: orgTreeId,
        orgRootId: orgTreeId
    }, function (data) {
        $.fn.zTree.init($("#standardTree"), setting, data);
        var zTree = $.fn.zTree.getZTreeObj("standardTree");
        var nodes = zTree.getNodes();
        zTree.expandNode(nodes[0], true);
        zTree.selectNode(nodes[0], true);
        onNodeClick(null, null, nodes[0]);
    }, function (err) {

    })
}

// 初始化业务组织列表
function initBusinessList () {
    $http.get('/orgTree/getOrgTreeList', {}, function (data) {
        var option = '';
        for (var i = 0; i < data.length; i++) {
            var select = i === 0? 'selected' : '';
            option += "<option value='" + data[i].orgTreeId + "' " + select + ">" + data[i].orgTreeName +"</option>";
        }
        $('#businessOrg').append(option);
        seajs.use('/vendors/lulu/js/common/ui/Select', function () {
            $('#businessOrg').selectMatch();
        });
        initOrgRelTree(data[0].orgTreeId);
        orgTreeId = data[0].orgTreeId;
        businessName = data[0].orgTreeName;
        $('#businessOrg').unbind('change').bind('change', function (event) {
            orgTreeId = event.target.options[event.target.options.selectedIndex].value;
            businessName = event.target.options[event.target.options.selectedIndex].innerHTML;
            initOrgRelTree(orgTreeId);
        })
    }, function (err) {
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

// function saveBtnClick(){
//         var orgNa = [];
//         for(var i = 0;i < editOrgList.length; i++){
//             orgNa.push(editOrgList[i].orgId);
//         }
//         if(orgNa.indexOf(parseInt(orgIdSelect)) != -1){
//             toastr.warning("已选择该组织");
//         }else{
//             addAcctOrg(orgIdSelect);
//             editOrgList.push({'orgId':orgIdSelect,'fullName':getOrgExtInfo()});
//         }
//     $('#myModal').modal('hide');
// }


initBusinessList();
