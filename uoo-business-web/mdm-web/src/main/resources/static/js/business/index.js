// loadingMask
var loading = new Loading();
var orgRootId; //业务组织ID
var businessName; //业务名
var orgId, //组织ID
    orgName, //组织名
    nodeName,
    nodeArr;

// lulu ui select插件
seajs.use('/vendors/lulu/js/common/ui/Select', function () {
    // $('#businessOrg').selectMatch();
})

function onNodeClick(e,treeId, treeNode) {
    // var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    // zTree.expandNode(treeNode);
    orgId = treeNode.id;
    orgName = treeNode.name;
    var currentNode = treeNode.name;//获取当前选中节点
    var parentNode = treeNode.getParentNode();
    nodeArr = [];
    getParentNodes(parentNode, currentNode);
    refreshResult();
}

// 获取父节点路径
function getParentNodes(parentNode, currentNode) {
    if(parentNode!=null){
        nodeName = parentNode.name;
        var curNode = parentNode.getParentNode();
        nodeArr.push(currentNode);
        getParentNodes(curNode, nodeName);
    }else{
        //根节点
        nodeArr.push(currentNode);
    }
}

function filter (treeId, parentNode, childNodes) {
    return childNodes.data
}

function refreshResult () {
    var url = "list.html?id=" + orgId + "&name=" + encodeURI(businessName);
    $('#businessFrame').attr("src",url);
}

function initOrgRelTree () {
    $http.get('http://134.96.253.221:11100/orgRel/getOrgRelTree', {
        orgRootId: '1'
    }, function (data) {
        console.log(data)
        $.fn.zTree.init($("#standardTree"), setting, data);
        var zTree = $.fn.zTree.getZTreeObj("standardTree");
        var nodes = zTree.getNodes();
        zTree.expandNode(nodes[0], true);
        zTree.selectNode(nodes[0], true);
        onNodeClick(null, null, nodes[0]);
    }, function (err) {
        console.log(err)
    })
}

// 根据组织ID展开并选中组织
function openTreeById (sId, id) {
  var tId = 'standardTree_' + id;
  var sId = 'standardTree_' + sId;
  var zTree = $.fn.zTree.getZTreeObj("standardTree");
  var selectNode = zTree.getNodeByTId(sId); //获取当前选中的节点并取消选择状态
  zTree.cancelSelectedNode(selectNode);
  var node = zTree.getNodeByTId(tId);
  if (node.parent) {
    zTree.expandNode(node, true);
  }
  zTree.selectNode(node, true);
}

// 初始化业务组织列表
function initBusinessList () {
    $http.get('http://134.96.253.221:11100/orgTree/getOrgTreeList', {}, function (data) {
        var option = '';
        for (var i = 0; i < data.length; i++) {
            var select = i === 0? 'selected' : '';
            option += "<option value='" + data[i].orgTreeId + "' " + select + ">" + data[i].orgTreeName +"</option>";
        }
        $('#businessOrg').append(option);
        seajs.use('/vendors/lulu/js/common/ui/Select', function () {
            $('#businessOrg').selectMatch();
        });
        initTree(data[0].orgTreeId);
        businessName = data[0].orgTreeName;
        $('#businessOrg').unbind('change').bind('change', function (event) {
            businessName = event.target.options[event.target.options.selectedIndex].value;
            initTree(value);
        })
    }, function (err) {
        console.log(err)
    })
}

function initTree (businessId) {
    //树参数设定
    var setting = {
        async: {
            enable: true,
            url: "http://134.96.253.221:11100/orgRel/getOrgRelTree?orgRootId=" + businessId,
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
    $http.get('http://134.96.253.221:11100/orgRel/getOrgRelTree', {
        orgRootId: businessId
    }, function (data) {
        $.fn.zTree.init($("#businessTree"), setting, data);
        var zTree = $.fn.zTree.getZTreeObj("businessTree");
        var nodes = zTree.getNodes();
        zTree.expandNode(nodes[0], true);
        zTree.selectNode(nodes[0], true);
        onNodeClick(null, null, nodes[0]);
    }, function (err) {
        console.log(err)
    })
}

//跳转页面至新增业务树
function addBusiness () {
    var url = "add.html?id=" + orgId + "&name=" + encodeURI(businessName);
    $('#businessFrame').attr("src",url);
}
initBusinessList();
