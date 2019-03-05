var orgTable;
var orgTreeId;
var addList = [];   //新增的节点,只有id
var addNodeList = [];  //新增的节点,包含pid
var delList = [];  //删除的节点
var exNodes = [];
var isDel = [];
var isAdd = [];

var addNodes = [];
var updateNodes = [];

var unloadCount; //已挂渠道组织数量
var loadCount; //全部渠道组织数量

var toastr = window.top.toastr;



//获取渠道组织扩展信息
function getChannelOrgExtInfo(orgTreeId){
    $http.get('/org/getChannelOrgExtInfo', {    
        orgTreeId : orgTreeId
      }, function (data) {
        unloadCount = data.unloadCount;
        loadCount = data.loadCount;
        $("#orgExtInfo").text("（已挂 "+data.unloadCount+"/"+data.loadCount+"）");
      }, function (err) {
    })
}

//获取渠道组织数据
function getChannelOrgPage(orgTreeId){
    $http.get('/org/getChannelOrgPage', {    
        pageSize : 10000,
        pageNo : 1,
        orgTreeId : orgTreeId,
        search : ""
      }, function (data) {
        initOrgTable(data.records);
      }, function (err) {
    })
}

function filter (treeId, parentNode, childNodes) {
    return childNodes.data
}

function initOrgTree(orgTreeId){
    var setting = {
        async: {
            enable: true,
            url: "/orgRel/getOrgRelTree?orgTreeId="+orgTreeId,
            autoParam: ["id"],
            type: "get",
            dataFilter: filter
        },
        edit: {
            enable: true,
            showRemoveBtn: showRemoveBtn,
            showRenameBtn: false,
            removeTitle: "删除组织"
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
        view: {
            showLine: false,
            showIcon: false,
            dblClickExpand: false,
            selectedMulti: false,
            fontCss: setFontCss
        },
        callback: {
            beforeRemove: zTreeBeforeRemove,
            onAsyncSuccess: zTreeOnAsyncSuccess
        }
    };
    $http.get('/orgRel/getOrgRelTree', {
        orgTreeId: orgTreeId
    }, function (data) {
        var zTreeObj = $.fn.zTree.init($("#tree"), setting, data);
        var nodes = zTreeObj.getNodes();
        zTreeObj.expandNode(nodes[0], true);
        zTreeObj.selectNode(nodes[0], true);
        // loading.screenMaskDisable('container');
    }, function (err) {
        // loading.screenMaskDisable('container');
    });
}

//异步调用成功回调函数
function zTreeOnAsyncSuccess(e,treeId, treeNode, msg){
    if(exNodes.length != 0){
        var idx;
        var tree = $.fn.zTree.getZTreeObj("tree");
        for (var i = exNodes.length - 1; i >= 0; i--) {
            if (exNodes[i] == treeNode.id) {
                idx = i;
                break;
            }
        }
    
        if (idx > 1) {
            var openNode = tree.getNodeByParam('id', exNodes[idx - 1]);
            tree.expandNode(openNode, true, false, true, true);
        }
        else {
            var selNode = tree.getNodeByParam('id', exNodes[0]);
            tree.selectNode(selNode, false);
            exNodes = [];
            setTimeout(function () {
                tree.showNodeFocus(selNode);
            }, 300)
        }
    }
}

//初始化渠道组织表格
function initOrgTable(results){
    orgTable = $("#orgTable").DataTable({
        'data':results,
        'destroy':true,
        'searching': true,
        'autoWidth': true,
        'ordering': false,
        'paging': true,
        'info': false,
        "lengthChange": false,
        "scrollY": "420px",
        'scrollCollapse': true,
        'columns': [
            { 'data': null, 'title': '选择', 'className': 'row-select' ,
                'render': function (data, type, row, meta) {
                    if(row.channelOrgLoadSign == "1"){
                        return "<input type='checkbox' id='org_"+row.orgId+"' name='checkbox' checked disabled><label for='org_"+row.orgId+"' class='ui-checkbox'></label>";
                    }else{
                        return "<input type='checkbox' id='org_"+row.orgId+"' name='checkbox'><label for='org_"+row.orgId+"' class='ui-checkbox'></label>";
                    } 
                }
            },
            { 'data': null, 'title': '组织', 'className': 'row-fName',
                'render': function (data, type, row, meta) {
                    return "<a id='a_"+row.orgId+"' style='cursor:pointer;' orgPaths='"+row.orgPaths+"' onclick='selectChannelNode("+row.orgId+")'>"+row.orgName+"</a>";
                }
            }
        ],
        // "iDisplayLength":6,
        'language': {
            'emptyTable': '没有数据',  
            'loadingRecords': '加载中...',  
            'processing': '查询中...',  
            'search': '',   
            'zeroRecords': '没有数据',  
            'paginate': {  
                'first':      '首页',  
                'last':       '尾页',  
                'next':       '下一页',  
                'previous':   '上一页'  
            },   
            'infoEmpty': '没有数据'
        }
    });
    inputClick("orgTable");
    $("#orgTable_filter").parent().prev().css("display","none");
    $("#orgTable_filter input").attr("placeholder","搜索渠道组织");
}

//初始化变量值
function reflashVar(){
    addList = [];   //新增的节点,只有id
    addNodeList = [];  //新增的节点,包含pid
    delList = [];  //删除的节点
    exNodes = [];
    isDel = [];
    isAdd = [];
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
        initOrgTree(data[0].orgTreeId);
        getChannelOrgPage(data[0].orgTreeId);
        getChannelOrgExtInfo(data[0].orgTreeId);
        orgTreeId = data[0].orgTreeId;
        $('#businessOrg').unbind('change').bind('change', function (event) {
            reflashVar();
            orgTreeId = event.target.options[event.target.options.selectedIndex].value;
            initOrgTree(orgTreeId);
            getChannelOrgPage(orgTreeId);
            getChannelOrgExtInfo(orgTreeId);
        })
    }, function (err) {
        // loading.screenMaskDisable('container');
    })
}

//设置游离组织字体颜色
function setFontCss(treeId, treeNode) {
	return treeNode.isChannel == "Y" ? {color:"#00a4ff"} : {};
};

//是否展示删除按钮
function showRemoveBtn(treeId, treeNode) {
    return treeNode.isChannel == "Y";
}

//删除之前询问
function zTreeBeforeRemove(treeId, treeNode){
    var zTree = $.fn.zTree.getZTreeObj("tree");
    var aList = [];
    parent.layer.confirm('是否移除该组织？', {
        icon: 0,
        title: '提示',
        btn: ['确定','取消']
        }, function(index, layero){
            if(treeNode.isExistsAcct != null){
                parent.layer.close(index);
                toastr.error("该组织下存在用户，无法删除！");
            }else{
                zTree.removeNode(treeNode);
                unloadCount--;
                $("#org_"+treeNode.id).removeAttr("disabled");
                $("#org_"+treeNode.id).parent().parent().removeClass('select');
                $("#org_"+treeNode.id).removeAttr("checked");   

                if(addList.indexOf(~~treeNode.id) != -1){
                    addList.splice(addList.indexOf(~~treeNode.id),1);
                }else{
                    delList.push({"id":~~treeNode.id,"pid":treeNode.pid,"name":treeNode.name});
                }

                for(var i=0;i<addNodeList.length;i++){
                    if(addNodeList[i].id == ~~treeNode.id){
                        addNodeList.splice(i,1);
                        break;
                    }
                }

                if($("#org_"+treeNode.id).length == 0){
                    isDel.push(~~treeNode.id); 
                }
                parent.layer.close(index);
                $("#orgExtInfo").text("（已挂 "+unloadCount+"/"+loadCount+"）");
            }
        }, function(){

        }
    );
    return false;
}

//复选框点击事件
function inputClick(tableName){
    $('#'+tableName).delegate('tbody tr td input','click',function(){
        if ($(this).parent().parent().hasClass('select') ) {
            $(this).parent().parent().removeClass('select');
            $(this).removeAttr("checked");
        } else {
            $(this).parent().parent().addClass('select');
            $(this).prop("checked",true);
        }
    })
}

//定位渠道组织
function selectChannelNode(id){
    var str = $("#a_"+id).attr("orgPaths");
    var orgPaths = str.split(",");
    var zTree = $.fn.zTree.getZTreeObj("tree");
    var curNode = zTree.getNodeByParam('id', id);
    if(curNode != null){
        zTree.selectNode(curNode, false);
        setTimeout(function () {
            zTree.showNodeFocus(curNode);
        }, 300)
    }else if(orgPaths != "null"){
        for(var i=0;i<orgPaths.length;i++){
            var node = zTree.getNodeByParam('id', orgPaths[i]);
            if(!node.open){
                orgPaths.splice(0,i);
                orgPaths.reverse();
                exNodes = orgPaths;
                zTree.expandNode(node, true, false, true, true);
                break;
            }
        }
    } 
}

//获取选中行数据
function getSelectRow (addList) {
    var selectedList = orgTable.rows('.select').data();
    var rList = [];
    for(var j=0;j<selectedList.length;j++){
        if(addList.indexOf(selectedList[j].orgId) == -1){
            rList.push({"id":selectedList[j].orgId,"name":selectedList[j].orgName,"isChannel":selectedList[j].isChannel});
        }
    }
    return rList;
}

//新增节点
function add() {
    var zTree = $.fn.zTree.getZTreeObj("tree"),
    nodes = zTree.getSelectedNodes(),
    treeNode = nodes[0],
    clist = getSelectRow(addList),
    nodeList = [],
    nodeIdArr = [];

    if (nodes.length > 0) {
        var nodeAllPath = nodes[0].getPath();
        for(var i=0;i<nodeAllPath.length;i++){
            nodeIdArr.push(nodeAllPath[i].id);
        }
    }

    for(var i=0;i<clist.length;i++){
        nodeIdArr.push(clist[i].id);
        $("#a_"+clist[i].id).attr("orgPaths",nodeIdArr.join(","));
        nodeIdArr.splice(nodeIdArr.length-1,1);
    }

    if (treeNode && clist.length != 0 && treeNode.isChannel != "Y") {
        for(var i=0;i<clist.length;i++){
            addNodeList.push({"id":clist[i].id,"pid":treeNode.id});
            addList.push(clist[i].id);
            nodeList.push(clist[i]);
            $("#org_"+clist[i].id).attr("disabled","true");
            if($("#org_"+clist[i].id).length == 0){
                isAdd.push(clist[i].id); 
            }
            unloadCount++;
        }
        treeNode = zTree.addNodes(treeNode, nodeList);
        $("#orgExtInfo").text("（已挂 "+unloadCount+"/"+loadCount+"）");
    }else if(treeNode == undefined){
        alert("请先选择上级组织！");
    }else if(treeNode.isChannel == "Y"){
        alert("无法在该组织下挂载游离组织！");
    }
};

//保存按钮
function saveOrgTree(){
    var zTree = $.fn.zTree.getZTreeObj("tree");
    var pIdList = [];

    for(var i=0;i<addNodeList.length;i++){
        if(pIdList.indexOf(addNodeList[i].pid) == -1){
            pIdList.push(addNodeList[i].pid);
        }
    }

    for(var j=0;j<pIdList.length;j++){
        var node = zTree.getNodeByTId("tree_"+pIdList[j]);
        for(var k=0;k<node.children.length;k++){
            if(addList.indexOf(~~node.children[k].id) != -1){
                addNodes.push({"id":~~node.children[k].id,"name":node.children[k].name,"pid":pIdList[j],"sort":k+1});
            }else{
                updateNodes.push({"id":~~node.children[k].id,"name":node.children[k].name,"pid":pIdList[j],"sort":k+1});
            }
        }
    }
    var channelOrgVo = {
        "addNodes":addNodes,
        "delNodes":delList,
        "updateNodes":updateNodes,
        "orgTreeId":orgTreeId
    };
    console.log(channelOrgVo);
    $http.post('/org/addChannelOrg', JSON.stringify(  
        channelOrgVo
    ), function (message) {
        window.location.href = "index.html";
        toastr.success("创建成功！");
    }, function (err) {
        // loading.screenMaskDisable('LAY_app_body');
        // toastr.error("保存失败！");
    })
}

$("#addBtn").text("添加>>");

initBusinessList();

$(document).ready(function (){
    window.scrollTo(0,70);
});