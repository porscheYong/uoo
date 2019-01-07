// loadingMask
var loading = new Loading();
loading.screenMaskEnable('container');

var orgId,
    pid,
    orgName,
    parent,
    nodeArr;

function onNodeClick(e,treeId, treeNode) {
    orgId = treeNode.id;
    pid = treeNode.pid;
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

// 获取组织完整路径
function getOrgExtInfo () {
    var pathArry = nodeArr;
    var pathStr = '';
    if (pathArry && pathArry.length > 0) {
        for (var i = pathArry.length - 1; i >= 0; i--) {
            var node = pathArry[i].node;
            if (pathArry[i].current) {
                pathStr +=  '<span class="breadcrumb-item">' + node.name + '</span>';
            } else {
                pathStr += '<span class="breadcrumb-item"><a href="javascript:void(0);" onclick="parent.openTreeById('+orgId+','+node.id+')">' + node.name + '</a><span class="breadcrumb-separator" style="margin: 0 9px;">/</span></span>';
            }
        }
        $('#platformUserFrame').contents().find('.breadcrumb').html(pathStr);
    }
}


function filter (treeId, parentNode, childNodes) {
    return childNodes.data
}

function refreshResult () {
    var url = "list.html?id=" + orgId + '&pid=' + pid + "&name=" + encodeURI(orgName);
    $('#platformUserFrame').attr("src",url);
}

function initPlatFormOrgTree () {
    var setting = {
        async: {
            enable: true,
            url: '/orgRel/getOrgRelTree?orgRootId=1&orgTreeId=1',
            autoParam: ['id'],
            type: 'get',
            dataFilter: filter
        },
        view: {
            showLine: false,
            showIcon: false,
            dblClickExpand: false
        },
        data: {
            key: {
                isParent: 'parent'
            },
            simpleData: {
                enable:true,
                idKey: 'id',
                pIdKey: 'pid',
                rootPId: ''
            }
        },
        callback: {
            onClick: onNodeClick
        }
    };
    $http.get('/orgRel/getOrgRelTree', {
        orgRootId: '1',
        orgTreeId: '1'
    }, function (data) {
        $.fn.zTree.init($("#platformUserTree"), setting, data);
        var tree = $.fn.zTree.getZTreeObj("platformUserTree");
        var nodes = tree.getNodes();
        tree.expandNode(nodes[0], true);
        tree.selectNode(nodes[0], true);
        onNodeClick(null, null, nodes[0]);
    }, function (err) {

    })
}

// 根据组织ID选中组织
function openTreeById (sId, id) {
    var tId = 'platformUserTree_' + id;
    var sId = 'platformUserTree_' + sId;
    var zTree = $.fn.zTree.getZTreeObj("platformUserTree");
    var node = zTree.getNodeByTId(tId);
    zTree.selectNode(node);
    $('.curSelectedNode').trigger('click');
}

seajs.use('/vendors/lulu/js/common/ui/Select', function () {
    $('#orgSelect').selectMatch();
});
initPlatFormOrgTree();
