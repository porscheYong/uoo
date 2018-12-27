// loadingMask
var loading = new Loading();

var orgId,
    orgName,
    nodeName,
    nodeArr,
    parent,
    orgTreeId,
    businessName;
    
loading.screenMaskEnable('container');

function onNodeClick(e,treeId, treeNode) {
    orgId = treeNode.id;
    orgName = treeNode.name;
    var currentNode = {node: treeNode, current: true};//获取当前选中节点
    var parentNode = treeNode.getParentNode();
    nodeArr = [];
    getParentNodes(parentNode, currentNode);
    refreshResult();
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

function refreshResult () {
    var url = "mainList.html?orgId=" + orgId + "&orgName=" + encodeURI(orgName) + "&orgTreeId=" + orgTreeId;
    $('#userFrame').attr("src",url);
}

//初始化组织
function initOrgRelTree (orgTreeId) {

    var setting = {
        async: {
            enable: true,
            url: "/orgRel/getOrgRelTree?orgTreeId="+orgTreeId + "&orgRootId=" + orgTreeId,
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
        loading.screenMaskDisable('container');
    }, function (err) {
        loading.screenMaskDisable('container');
    });
}

// 初始化业务组织列表
function initBusinessList () {
    $http.get('/orgTree/getOrgTreeList', {}, function (data) {
        var option = '';
        for (var i = 0; i < data.length; i++) {
            var select = i === 0? 'selected' : '';
            option += "<option value='" + data[i].orgTreeId + "' " + select + ">" + data[i].orgTreeName +"</option>";
        }
        $('#businessOrg').html(option);
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
        loading.screenMaskDisable('container');
    })
}

// 获取组织完整路径
function getOrgExtInfo () {
    var pathArry = nodeArr;
    var pathStr = '';
    if (pathArry && pathArry.length > 0) {
        for (var i = pathArry.length - 1; i >= 0; i--) {
            var node = pathArry[i].node;
            if (pathArry[i].current) {
                pathStr +=  '<span class="breadcrumb-item"><a href="javascript:void(0);">' + node.name + '</a></span>';
            } else {
                pathStr += '<span class="breadcrumb-item"><a href="javascript:void(0);" onclick="parent.openTreeById('+orgId+','+node.id+')">' + node.name + '</a><span class="breadcrumb-separator" style="margin: 0 9px;">/</span></span>';
            }
        }
        $('#userFrame').contents().find('.breadcrumb').html(pathStr);
    }
}

// 根据组织ID展开并选中组织
function openTreeById (sId, id) {
    var tId = 'standardTree_' + id;
    var sId = 'standardTree_' + sId;
    var orgTree = $.fn.zTree.getZTreeObj("standardTree");
    var selectNode = orgTree.getNodeByTId(sId); //获取当前选中的节点并取消选择状态
    if (!selectNode.open) {
        orgTree.expandNode(selectNode, true);
    }
    var node = orgTree.getNodeByTId(tId);
    orgTree.selectNode(node);
    $('.curSelectedNode').trigger('click');
}

initBusinessList();

