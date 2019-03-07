var engine;
var empty;
var table;
var isCheckedOrg = 0;

empty = Handlebars.compile($(".typeahead-menu").html());

engine = new Bloodhound({
    identify: function(o) { return o.id_str; },
    queryTokenizer: Bloodhound.tokenizers.whitespace,
    datumTokenizer: Bloodhound.tokenizers.obj.whitespace('name', 'busOrgName'),
    dupDetector: function(a, b) { return a.id_str === b.id_str; },
    remote: {
        url: '/orgRel/getFuzzyOrgRelPage?search=%QUERY&pageNo=1&pageSize=10&orgRootId=1&orgTreeId='+orgTreeId,
        wildcard: '%QUERY',
        filter: function (response) {
            if (response.data && response.data.records.length == 0)
                return;
            
        }
    }
});


function initOrgSearchTable(search) {
    //table.destroy();
    table = $("#busTable").DataTable({
        'destroy':true,
        'searching': false,
        'autoWidth': false,
        'ordering': true,
        'columns': [
            { 'data': "fullName", 'title': '组织搜索结果', 'className': 'row-fullname',
                'render': function (data, type, row, meta) {
                    return "<a href='#' onclick='orgClick("+row.orgId+")'>" + row.fullName + "</a>";
                }
            }
        ],
        'language': {
            'emptyTable': '没有数据',  
            'loadingRecords': '加载中...',  
            'processing': '查询中...',  
            // 'lengthMenu': ' _MENU_ ',  
            'zeroRecords': '没有数据',  
            'paginate': {  
                'next':       '下一页',  
                'previous':   '上一页'  
            },  
            'info': '总_TOTAL_条',  
            'infoEmpty': '没有数据'
        },
        // "aLengthMenu": [[10, 20, 50], ["10条/页", "20条/页", "50条/页"]],
        "pagingType": "simple",
        "iDisplayLength": 3,
        'dom': '<"top"f>t<"bottom"ipl>',
        'serverSide': true,  //启用服务器端分页
        'ajax': function (data, callback, settings) {
            var param = {};
            param.pageSize = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
            param.pageNo = (data.start / data.length) + 1;//当前页码
            param.orgTreeId = orgTreeId;
            param.orgRootId = '1';
            param.search = search;
            $http.get('/orgRel/getFuzzyOrgRelPage', param, function (result) {
                var returnData = {};
                // returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                returnData.recordsTotal = result.total;//返回数据全部记录
                returnData.recordsFiltered = result.total;//后台不实现过滤功能，每次查询均视作全部结果
                returnData.data = result.records;//返回的数据列表
                //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                callback(returnData);
            }, function (err) {

            })
        }
    });
    // var loading = parent.loading;
    // loading.screenMaskDisable('container');
}

function engineWithDefaults(q, sync, async) {
    if (q === '') {
        $('#busTable').html('');
        $(".bus-table").removeClass("is-open");
    }
    else {
        engine.search(q, sync, async);
    }
}

$('#busOrgName').typeahead({
    hint: $('.typeahead-hint'),
    menu: $('.bus-table'),
    minLength: 0,
    highlight:true,
    classNames: {
        open: 'is-open',
        empty: 'is-empty',
        cursor: 'is-active',
        suggestion: 'Typeahead-suggestion',
        selectable: 'Typeahead-selectable'
    }
}, {
    source: engineWithDefaults,
    displayKey: 'busOrgName',
    templates: {
        suggestion: empty
    }
})
  .on('typeahead:asyncrequest', function() {
        $('.Typeahead-spinner').show();
        if($("#busOrgName").val() != ''){ 
            initOrgSearchTable($("#busOrgName").val());
        }
    })
  .on('typeahead:asynccancel', function() {
        $('.Typeahead-spinner').hide();
    });

Handlebars.registerHelper('eq', function(v1, v2, opts) {
    if(v1 == v2){
        return opts.fn(this);
    }
    else
        return opts.inverse(this);
});

Handlebars.registerHelper("addOne", function (index) {
    return index + 1;
});

//获取查询组织的路径id
function getRestructOrgRelTree(orgId){
    $http.get('/orgRel/getRestructOrgRelTree', {
        orgId: orgId,
        orgTreeId: orgTreeId
    }, function (data) {
        var zTree = $.fn.zTree.getZTreeObj("tree");
        var curNode = zTree.getNodeByParam('id', orgId);
        var orgPaths = [];
        var n = 0;

        if(curNode != null){
            $("#treeDiv").scrollTop(0);
            zTree.selectNode(curNode, false);
            setTimeout(function () {
                zTree.showNodeFocus(curNode);
            }, 300)
        }else{
            for(var i=0;i<data.length;i++){
                orgPaths.push(data[i].id);
            }
    
            for(var i=orgPaths.length-1;i>=0;i--){
                var node = zTree.getNodeByParam('id', orgPaths[i]);
                if(!node.open){
                    orgPaths.splice(orgPaths.length-1,n);
                    exNodes = orgPaths;
                    zTree.expandNode(node, true, false, true, true);
                    break;
                }
                n++;
            }
        }
    }, function (err) {

    })
}

function orgClick(orgId){
    $(".bus-table").removeClass("is-open");
    getRestructOrgRelTree(orgId);
}

$("#busOrgName").focus(function (){     
    if($("#busOrgName").val() == ''){
        $(".bus-table").removeClass("is-open");
    }
})