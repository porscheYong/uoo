var setting = {
    async: {
        enable: true,
        url: base + "orgRel/getOrgRelTree?orgRootId=1",
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

function onNodeClick(e,treeId, treeNode) {
    // var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    // zTree.expandNode(treeNode);
    var orgId = treeNode.id;
    refreshResult(orgId);
}

function filter (treeId, parentNode, childNodes) {
    return childNodes.data
}

function refreshResult (orgId) {
    var url = "list.html?id=" + orgId;
    $('#orgFrame').attr("src",url);
}

function initOrgRelTree (orgId) {
    $http.get('orgRel/getOrgRelTree', {
        orgRootId: orgId
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

initOrgRelTree('1')

function layerOpen(obj) {
  layer.open({
    type: obj.type || 1,
    title: obj.title,
    scrollbar: obj.scrollbar || false,
    shade: obj.shade || 0.3,
    shadeClose: obj.shadeClose || true,
    area: obj.area || ['600px', '400px'],
    content: obj.content,
    btn: obj.btn,
    success: function(layero, index){
      if (obj.success) {
        obj.success()
      }
    },
    yes: function (index, layero) {
      if (obj.confirm) {
        obj.confirm()
      }
    },
    btn2: function (index, layero) {
      if (obj.cancel) {
        obj.cancel()
      }
    }
  })
}
