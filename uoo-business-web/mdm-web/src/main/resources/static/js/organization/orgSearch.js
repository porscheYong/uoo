var engine;
var empty;
var table;
var Regx = /^[A-Za-z0-9]*$/;
var orgIdList = [];

var settingA = {
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
            selectedMulti: false,
            showLine: false,
            showIcon: false,
            dblClickExpand: false
		}
	};

empty = Handlebars.compile($(".typeahead-menu").html());

engine = new Bloodhound({
    identify: function(o) { return o.id_str; },
    queryTokenizer: Bloodhound.tokenizers.whitespace,
    datumTokenizer: Bloodhound.tokenizers.obj.whitespace('name', 'orgName'),
    dupDetector: function(a, b) { return a.id_str === b.id_str; },
    remote: {
        url: '/orgRel/getFuzzyOrgRelPage?search=%QUERY&pageNo=1&pageSize=10&orgRootId=1&orgTreeId=1',
        wildcard: '%QUERY',
        filter: function (response) {
            if (response.data && response.data.records.length == 0)
                return;
            
        }
    }
});


function initOrgSearchTable(search) {
    //table.destroy();
    table = $("#orgTable").DataTable({
        'destroy':true,
        'searching': false,
        'autoWidth': false,
        'ordering': true,
        'initComplete': function (settings, json) {
            console.log(settings, json)
        },
        "scrollY": "200px",
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
                'next':       '>',  
                'previous':   '<'  
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
            param.orgTreeId = '1';
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
                console.log(err)
            })
        }
    });
    // var loading = parent.loading;
    // loading.screenMaskDisable('container');
}

function engineWithDefaults(q, sync, async) {
    if (q === '') {
        $('#orgTable').html('');
        $(".org-table").removeClass("is-open");
        initOrgRelTree();
    }
    else {
        engine.search(q, sync, async);
    }
}

$('#orgName').typeahead({
    hint: $('.typeahead-hint'),
    menu: $('.org-table'),
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
    displayKey: 'orgName',
    templates: {
        suggestion: empty
    }
})
  .on('typeahead:asyncrequest', function() {
        $('.Typeahead-spinner').show();
        if($("#orgName").val() != '' && !Regx.test($("#orgName").val())){
            initOrgSearchTable($("#orgName").val());
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


function initRestructOrgRelTree (orgId) {        //初始化树
    $http.get('/orgRel/getRestructOrgRelTree', {
        orgId: orgId,
        orgTreeId: '1'
    }, function (data) {
        var zTreeNodes = [];
        nodeArr = [];

        for(var i=0;i<data.length;i++){     //获取要显示的节点id pid name
            zTreeNodes.push({"id":data[i].id,"pid":data[i].pid,"name":data[i].name});
            if(i == data.length-1){
                nodeArr.push({"node":{"id":data[i].id,"pid":data[i].pid,"name":data[i].name},"current":true});
            }else{
                nodeArr.push({"node":{"id":data[i].id,"pid":data[i].pid,"name":data[i].name},"current":false});
            }
        }

        var zTree = $.fn.zTree.init($("#standardTree"), settingA, zTreeNodes);
        var node = zTree.getNodeByTId("standardTree_"+orgId);
        //console.log(node);
        var url = "list.html?id=" + orgId + '&pid=' + data[0].pid + "&name=" + encodeURI(data[0].name);
        zTree.expandAll(true);
        zTree.selectNode(node);
        $('#orgFrame').attr("src",url);
    }, function (err) {
        console.log(err)
    })
}       

function orgClick(orgId){
    console.log(orgId);
    $(".org-table").removeClass("is-open");
    initRestructOrgRelTree(orgId);
}