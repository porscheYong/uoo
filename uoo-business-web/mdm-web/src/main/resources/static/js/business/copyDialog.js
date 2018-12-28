var orgId = getQueryString('id');
var orgTreeId = getQueryString('orgTreeId');
var businessFrame = parent.window['business'];
var orgCopyList = businessFrame.orgCopyList;
var checkNode = []; //选中类别显示label标签
var targetId = '';

// 组织列表初始化
function initBusinessList () {
    $http.get('/orgTree/getOrgTreeList', {}, function (data) {
        var option = '';
        for (var i = 0; i < data.length; i++) {
            var active = i === 0? 'active' : '';
            option += "<label class='" + active + "'><a href='javascript:;' value='" + data[i].orgTreeId + "'>" + data[i].orgTreeName + "</a></label>"
        }
        $('#treeList').html(option);
        targetId = data[0].orgTreeId;
        initTree(targetId);

        $('#treeList a').unbind('click').bind('click', function (event) {
            if ($(this).parent().hasClass('active') ) {
                return;
            } else {
                $('#treeList label.active').removeClass('active');
                $(this).parent().addClass('active');
                targetId = $(event.target).attr('value');
                initTree(targetId);
            }
        })
    }, function (err) {

    })
}

// 组织树初始化
function initTree (targetId) {
    var setting = {
        async: {
            enable: true,
            url: "/orgRel/getTarOrgRelTreeAndLv?orgRootId=" + targetId + "&orgTreeId=" + targetId +  "&lv=4&isFull=false",
            autoParam: ["id=curOrgid"],
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
            beforeClick: orgRelTreeBeforeClick,
            onCheck: onOrgRelTreeCheck
        },
        check: {
            enable: true,
            chkStyle: 'checkbox',
            chkboxType: { 'Y': 'p', 'N': 's' }
        }
    };

    $http.get('/orgRel/getTarOrgRelTreeAndLv', {
        orgRootId: targetId,
        orgTreeId: targetId,
        lv: '4',
        isFull: false
    }, function (data) {
        $.fn.zTree.init($("#standardTree"), setting, data);
        var tree = $.fn.zTree.getZTreeObj("standardTree");
        var nodes = tree.getNodes();
        tree.expandNode(nodes[0], true);
        autoCheck();
    }, function (err) {

    })
}

function filter (treeId, parentNode, childNodes) {
    return childNodes.data
}

// 自动选中
function autoCheck () {
    var tree = $.fn.zTree.getZTreeObj("standardTree");
    for (var i = 0; i < orgCopyList.length; i++) {
        var id = orgCopyList[i].orgTypeId || orgCopyList[i].id;
        var node = tree.getNodeByTId("standardTree_" + id);
        tree.checkNode(node, true);
        checkNode.push(node);
    }
}

function orgRelTreeBeforeClick (treeId, treeNode, clickFlag) {
    var tree = $.fn.zTree.getZTreeObj("standardTree");
    tree.checkNode(treeNode, !treeNode.checked, true);
    return false;
}

function onOrgRelTreeCheck (e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("standardTree");
    var node = zTree.getNodeByTId(treeNode.tId);
    if ($.inArray(node, checkNode) === -1) {
        checkNode.push(node);
    } else {
        for (var i = 0; i < checkNode.length; i++){
            if (checkNode[i].tId == node.tId)
                checkNode.splice(i, 1);
        }
        checkNode.splice(idx, 1);
    }
}

// 获取所有勾选的节点
function getCheckdNodes () {
    var treeObj = $.fn.zTree.getZTreeObj("standardTree");
    var nodes = treeObj.getCheckedNodes(true);
    if (nodes.length == 0) {
        nodes = checkNode;
    }
    return nodes;
}

initBusinessList();
