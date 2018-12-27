var a = " http://134.129.68.158:7001/group/166809/ywsgl?p_p_id=uommajortree_WAR_fjupappsportlet&p_p_lifecycle=2&p_p_state=normal&p_p_mode=view&p_p_cacheability=cacheLevelPage&p_p_col_id=column-1&p_p_col_count=1"
var setting = {
    async: {
        enable: false,
        url:a,
        autoParam:["id = orgId", "name", "level"],
        // dataFilter: filter
    },
    view: {
        showLine: false,
        showIcon: false,
        dblClickExpand: false
    },
    data: {
        // key: {
        //     name: "label",
        //     isParent: "leaf",
        // },
        simpleData: {
            enable:true,
            idKey: "id",
            pIdKey: "pId",
            rootPId: ""
        }
    },
    callback: {
        onClick: onNodeClick,
        onCheck: onNodeCheck
    },
    check: {
        enable: false,
        // chkStyle: 'checkbox',
        chkboxType: { "Y": "", "N": "" }
    }
};

var zNodes =[
    {id: 5000, name: '角色', isParent: true,open: true},
    {id: 5001, name: '标准组织树维护', isParent: true, open: true,pId: 5000},
    {id: 5002, name: '所有业务组织树维护', isParent: true, open: true,pId: 5000},
    {id: 5003, name: 'MIS业务树维护', isParent: true,open: true,pId: 5002},
    {id: 5004, name: 'MIS业务树组织查看', isParent: false,pId: 5003},
    {id: 5004, name: 'MIS树组织基本属性维护', isParent: false,pId: 5003},
    {id: 5004, name: 'MIS树组织关系属性维护', isParent: false,pId: 5003},
    {id: 5009, name: '标准组织树基本属性维护', isParent: false, pId: 5001},
    {id: 5009, name: '标准组织树关系属性维护', isParent: false, pId: 5001},
    {id: 5009, name: '标准组织树查看', isParent: false, pId: 5001},
];

var zNodes1 =[
    {id: 5000, name: '浙江省单位管理员', isParent: true,open: true},
    {id: 5001, name: '杭州市单位管理员', isParent: true, open: true,pId: 5000},
    {id: 5002, name: '营销省专业管理员', isParent: true, open: true,pId: 5000},
    {id: 5006, name: '温州市单位管理员', isParent: false, open: true,pId: 5000},
    {id: 5003, name: '营销杭州专业管理员', isParent: false,open: true,pId: 5002},
    {id: 5009, name: '西湖区单位管理员', isParent: false, pId: 5001}
];

function onNodeClick(e,treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("roleTree");
    var zTree1 = $.fn.zTree.getZTreeObj("posTree");
    zTree.expandNode(treeNode);
}

function onNodeCheck (e, treeId, treeNode) {
    console.log(e, treeId, treeNode)
    var zTree = $.fn.zTree.getZTreeObj("roleTree");
    var node = zTree.getNodeByTId(treeNode.tId);
    // zTree.checkNode(node, false, false);
    addTag("tags_2", node.name);
    console.log(node)
}

function checkNode (e) {
    var zTree = $.fn.zTree.getZTreeObj("roleTree");
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

$.fn.zTree.init($("#roleTree"), setting, zNodes);
$.fn.zTree.init($("#posTree"), setting, zNodes1);