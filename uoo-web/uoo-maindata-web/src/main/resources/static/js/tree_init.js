if (typeof NProgress != 'undefined') {
  $(document).ready(function () {
      NProgress.start();
  });
}

var a = " http://134.129.68.158:7001/group/166809/ywsgl?p_p_id=uommajortree_WAR_fjupappsportlet&p_p_lifecycle=2&p_p_state=normal&p_p_mode=view&p_p_cacheability=cacheLevelPage&p_p_col_id=column-1&p_p_col_count=1"
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
        onClick: onNodeClick,
        onCheck: onNodeCheck
    },
    // check: {
    //     enable: true,
    //     // chkStyle: 'checkbox',
    //     chkboxType: { "Y": "", "N": "" }
    // }
};

var zNodes =[
    // {id:1, pId:0, name:"广东", open:true},  
    // {id:101, pId:1, name:"广州", file:"core/standardData"},  
    // {id:102, pId:1, name:"深圳", file:"core/simpleData"},  
    // {id:103, pId:1, name:"东莞", file:"core/noline"}
    // {id: 5000, label: '省电信公司', leaf: true, querable: true},
    // {id: 5001, label: '福州市分公司', leaf: true, querable: true},
    // {id: 5002, label: '宁德市分公司', leaf: true, querable: true},
    // {id: 5003, label: '厦门市分公司', leaf: true, querable: true},
    // {id: 5004, label: '莆田市分公司', leaf: true, querable: true},
    // {id: 5005, label: '泉州市分公司', leaf: false, querable: true},
    // {id: 5006, label: '漳州市分公司', leaf: true, querable: true},
    // {id: 5007, label: '龙岩市分公司', leaf: true, querable: true},
    // {id: 5008, label: '三明市分公司', leaf: true, querable: true},
    // {id: 5009, label: '南平市分公司', leaf: true, querable: true}
    {id: 5000, name: '省电信公司', isParent: true},
    {id: 5001, name: '杭州分公司', isParent: true, open: true},
    {id: 5002, name: '宁波分公司', isParent: true},
    {id: 5003, name: '温州分公司', isParent: true},
    {id: 5004, name: '绍兴分公司', isParent: true},
    {id: 5005, name: '湖州分公司', isParent: true},
    {id: 5006, name: '嘉兴分公司', isParent: true},
    {id: 5007, name: '台州分公司', isParent: true},
    {id: 5008, name: '丽水分公司', isParent: true},
    {id: 5009, name: '衢州分公司', isParent: true},
    {id: 5009, name: '市场部', isParent: false, pId: 5001},
    {id: 5009, name: '财务部', isParent: false, pId: 5001},
    {id: 5009, name: '人力资源部', isParent: false, pId: 5001},
    {id: 5009, name: '电子渠道部', isParent: false, pId: 5001}
];
function onNodeClick(e,treeId, treeNode) {
    // var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    // zTree.expandNode(treeNode);
    console.log(treeNode.id)
    getOrgList(treeNode.id);
    getOrgPersonnerList(treeNode.id);
}

function filter (treeId, parentNode, childNodes) {
    NProgress.done();
    return childNodes.data
}

function onNodeCheck (e, treeId, treeNode) {
    console.log(e, treeId, treeNode)
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    var node = zTree.getNodeByTId(treeNode.tId);
    // zTree.checkNode(node, false, false);
    addTag("tags_2", node.name);
    console.log(node)
}

function checkNode (e) {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    var node = zTree.getNodeByTId("treeDemo_4_span");

}

function addTag (id, value) {
    $('#' + id).append($('<div>').addClass('tag').append(
        $('<span>').text(value).append('&nbsp;'),
        $('<a>', {
            href  : '#',
            text  : 'x'
        }).click(function (e) {
            return removeTag(e);
        })
    )); 
}

function removeTag (e) {
    // $(e.target).parent().remove()
}
//tags input
function init_TagsInput() {
      
    if(typeof $.fn.tagsInput !== 'undefined'){  
     
        $('#tags_1').tagsInput({
          width: 'auto'
        });
    
    }
    
};

// $.fn.zTree.init($("#treeDemo"), setting, zNodes);

function initOrgRelTree (orgId) {
    $http.get('orgRel/getOrgRelTree', {
        orgRootId: orgId
    }, function (data) {
        console.log(data)
        $.fn.zTree.init($("#treeDemo"), setting, data);
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        var nodes = zTree.getNodes();
        zTree.expandNode(nodes[0], true);
        zTree.selectNode(nodes[0], true);
        onNodeClick(null, null, nodes[0]);
        // NProgress.done();
    }, function (err) {
        console.log(err)
    })
}

initOrgRelTree('1')
