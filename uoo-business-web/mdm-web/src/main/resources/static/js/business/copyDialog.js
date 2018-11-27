var orgId = getQueryString('id');
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

        initTree(data[0].orgTreeId);
        targetId = 1;
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
        console.log(err)
    })
}

// 组织树初始化
function initTree () {
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
            beforeClick: orgRelTreeBeforeClick,
            onCheck: onOrgRelTreeCheck
        },
        check: {
            enable: true,
            chkStyle: 'radio'
        }
    };

    $http.get('/orgRel/getOrgRelTree', {
        orgRootId: '1',
        orgTreeId: '1'
    }, function (data) {
        $.fn.zTree.init($("#standardTree"), setting, data);
        var zTree = $.fn.zTree.getZTreeObj("standardTree");
        var nodes = zTree.getNodes();
        zTree.expandNode(nodes[0], true);
    }, function (err) {
        console.log(err)
    })
}

function filter (treeId, parentNode, childNodes) {
    return childNodes.data
}

function orgRelTreeBeforeClick (treeId, treeNode, clickFlag) {
    return false;
}

function onOrgRelTreeCheck (e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("standardTree");
    var node = zTree.getNodeByTId(treeNode.tId);
    if (checkNode.indexOf(node) === -1) {
        checkNode.push(node);
    } else {
        var idx = checkNode.findIndex((v) => {
            return v.tId == node.tId;
        })
        checkNode.splice(idx, 1);
    }
}

initBusinessList();
