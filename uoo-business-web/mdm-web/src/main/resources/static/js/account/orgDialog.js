var orgTreeId = getQueryString('orgTreeId');
var relTypeVal = getQueryString('relType');
var toastr = window.top.toastr;
var relTypeName = parent.relTypeName;
var orgIdSelect,
    orgFullName,
    orgName,
    nodeName,
    nodeArr,
    businessName;

function onNodeClick(e,treeId, treeNode) {
    orgIdSelect = treeNode.id;
    orgName = treeNode.name;
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
            onClick: onNodeClick
        }
    };

    $http.get('/orgRel/getOrgRelTree', {
        orgTreeId: orgTreeId
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
function initRelTypeName () {
        var option = '';
        for (var i = 0; i < relTypeName.length; i++) {
            var select = relTypeVal === relTypeName[i].itemValue? 'selected' : '';
            option += "<option value='" + relTypeName[i].itemValue + "' " + select + ">" + relTypeName[i].itemCnname +"</option>";
        }
        if(relTypeVal == "null" || relTypeVal == 0){
            relTypeVal = relTypeName[0].itemValue;
        }
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
