// loadingMask
var loading = new Loading();
loading.screenMaskEnable('container');

var orgId,
    pid,
    orgName,
    parent,
    nodeArr,
    orgFlag; //是否选中平台组织

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
                pathStr += '<span class="breadcrumb-item"><a href="javascript:void(0);" onclick="parent.selectNodeById(\''+node.id+'\')">' + node.name + '</a><span class="breadcrumb-separator" style="margin: 0 9px;">/</span></span>';
            }
        }
        $('#platformUserFrame').contents().find('.breadcrumb').html(pathStr);
    }
}


function filter (treeId, parentNode, childNodes) {
    return childNodes.data
}

function refreshResult () {
    var url = "list.html?id=" + orgId + '&pid=' + pid + '&orgFlag=' + orgFlag + "&name=" + encodeURI(orgName);
    $('#platformUserFrame').attr("src",url);
}

// 初始化平台组织树
function initOrgTree (obj) {
    if ($(obj) && $(obj).hasClass('active'))
        return;
    $(obj).next().removeClass('active');
    $(obj).addClass('active');
    var setting = {
        async: {
            enable: true,
            url: '/sysOrganization/getOrgRelTree',
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
    $http.get('/sysOrganization/getOrgRelTree', {
        orgRootId: '1',
        orgTreeId: '1'
    }, function (data) {
        $.fn.zTree.init($("#tree"), setting, data);
        var tree = $.fn.zTree.getZTreeObj("tree");
        var nodes = tree.getNodes();
        tree.expandNode(nodes[0], true);
        tree.selectNode(nodes[0], true);
        orgFlag = 1;
        onNodeClick(null, null, nodes[0]);
    }, function (err) {

    })
}

// 初始化平台职位树
function initOrgPostTree (obj) {
    if ($(obj) && $(obj).hasClass('active'))
        return;
    $(obj).prev().removeClass('active');
    $(obj).addClass('active');
    var setting = {
        async: {
            enable: true,
            url: '/sysPosition/getPositionTree',
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
    $http.get('/sysPosition/getPositionTree', {}, function (data) {
        $.fn.zTree.init($("#tree"), setting, data);
        var tree = $.fn.zTree.getZTreeObj("tree");
        var nodes = tree.getNodes();
        tree.expandNode(nodes[0], true);
        tree.selectNode(nodes[0], true);
        orgFlag = 0;
        onNodeClick(null, null, nodes[0]);
    }, function (err) {

    })
}

// 根据组织ID选中组织
function selectNodeById (id) {
    var tId = 'tree_' + id;
    var tree = $.fn.zTree.getZTreeObj("tree");
    var node = tree.getNodeByTId(tId);
    tree.selectNode(node);
    $('.curSelectedNode').trigger('click');
}

//将时间戳改为yyyy-MM-dd HH-mm-ss
function formatDateTime(unix) {
    var now = new Date(parseInt(unix) * 1);
    now =  now.toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ");
    if(now.indexOf("下午") > 0) {
        if (now.length == 18) {
            var temp1 = now.substring(0, now.indexOf("下午"));   //2014/7/6
            var temp2 = now.substring(now.indexOf("下午") + 2, now.length);  // 5:17:43
            var temp3 = temp2.substring(0, 1);    //  5
            var temp4 = parseInt(temp3); // 5
            temp4 = 12 + temp4;  // 17
            var temp5 = temp4 + temp2.substring(1, temp2.length); // 17:17:43
            now = temp1 + temp5; // 2014/7/6 17:17:43
            now = now.replace("/", "-"); //  2014-7/6 17:17:43
            now = now.replace("/", "-"); //  2014-7-6 17:17:43
        }else {
            var temp1 = now.substring(0, now.indexOf("下午"));   //2014/7/6
            var temp2 = now.substring(now.indexOf("下午") + 2, now.length);  // 5:17:43
            var temp3 = temp2.substring(0, 2);    //  5
            if (temp3 == 12){
                temp3 -= 12;
            }
            var temp4 = parseInt(temp3); // 5
            temp4 = 12 + temp4;  // 17
            if(temp2.indexOf(":") == 1){
                temp2 = temp2.substring(1, temp2.length);
            }else{
                temp2 = temp2.substring(2, temp2.length);
            }
            var temp5 = temp4 + temp2; // 17:17:43
            now = temp1 + temp5; // 2014/7/6 17:17:43
            now = now.replace("/", "-"); //  2014-7/6 17:17:43
            now = now.replace("/", "-"); //  2014-7-6 17:17:43
        }
    }else {
        var temp1 = now.substring(0,now.indexOf("上午"));   //2014/7/6
        var temp2 = now.substring(now.indexOf("上午")+2,now.length);  // 5:17:43
        var temp3 = temp2.substring(0,1);    //  5
        var index = 1;
        var temp4 = parseInt(temp3); // 5
        if(temp4 == 0 ) {   //  00
            temp4 = "0"+temp4;
        }else if(temp4 == 1) {  // 10  11  12
            index = 2;
            var tempIndex = temp2.substring(1,2);
            if(tempIndex != ":") {
                temp4 = temp4 + "" + tempIndex;
            }else { // 01
                temp4 = "0"+temp4;
            }
        }else {  // 02 03 ... 09
            temp4 = "0"+temp4;
        }
        if(temp2.indexOf(":") == 1){
            temp2 = temp2.substring(1,temp2.length);
        }else{
            temp2 = temp2.substring(2,temp2.length);
        }
        var temp5 = temp4 + temp2; // 07:17:43
        now = temp1 + temp5; // 2014/7/6 07:17:43
        now = now.replace("/","-"); //  2014-7/6 07:17:43
        now = now.replace("/","-"); //  2014-7-6 07:17:43
    }
    return now;
}

seajs.use('/vendors/lulu/js/common/ui/Select', function () {
    $('#orgSelect').selectMatch();
});
initOrgTree();
