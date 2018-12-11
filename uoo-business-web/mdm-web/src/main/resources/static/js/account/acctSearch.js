var orgId = getQueryString('orgId');
var orgTreeId = getQueryString('orgTreeId');
var orgName = getQueryString('orgName');
var engine;
var empty;

empty = Handlebars.compile($(".typeahead-menu").html());

engine = new Bloodhound({
    identify: function(o) { return o.id_str; },
    queryTokenizer: Bloodhound.tokenizers.whitespace,
    datumTokenizer: Bloodhound.tokenizers.obj.whitespace('name', 'acctName'),
    dupDetector: function(a, b) { return a.id_str === b.id_str; },
    remote: {
        url: '/orgPersonRel/getUserOrgRelPage?search=%QUERY&pageNo=1&pageSize=10&orgId='+orgId+'&orgTreeId='+orgTreeId,
        wildcard: '%QUERY',
        filter: function (response) {
            if (response.data && response.data.records.length == 0)
                return;
            
        }
    }
});

function initAcctTable(search) {
    table.destroy();
    table = $("#mainTable").DataTable({
        'searching': false,
        'autoWidth': false,
        'ordering': true,
        "scrollY": "375px",
        'columns': [
            { 'data': "psnName", 'title': '人员姓名', 'className': 'row-psnName' ,
            'render': function (data, type, row, meta) {
                if(row.typeName == '主账号'){
                    return '<a href="addMainAccount.html?orgTreeId=' + orgTreeId + '&orgName=' + orgName +'&orgId=' + orgId + '&acctId='+ row.accId + '&statusCd='+row.statusCd+'&opBtn=0&hType=mh">'+ row.psnName +'</a>'
                }else{
                    return '<a href="addSubAccount.html?orgTreeId=' + orgTreeId + '&orgName=' + orgName +'&orgId=' + orgId + '&acctId='+ row.accId +'&statusCd='+row.statusCd+'&opBtn=0&hType=mh">'+ row.psnName +'</a>'
                } 
              }
            },
            { 'data': "typeName", 'title': '用户类型', 'className': 'row-typeName' },
            { 'data': "acct", 'title': '用户名', 'className': 'row-acc' },
            { 'data': "orgName", 'title': '归属组织', 'className': 'row-org' },
            { 'data': "certNo", 'title': '证件号码', 'className': 'row-certNo' },
            { 'data': "statusCd", 'title': '状态', 'className': 'row-statusCd' ,
            'render': function (data, type, row, meta) {
                if(row.statusCd == 1000){
                    return '生效';
                }else{
                    return '失效';
                }
            }
            }
        ],
        'language': {
            'emptyTable': '没有数据',  
            'loadingRecords': '加载中...',  
            'processing': '查询中...',  
            'search': '检索:',  
            'lengthMenu': ' _MENU_ ',  
            'zeroRecords': '没有数据',  
            'paginate': {  
                'first':      '首页',  
                'last':       '尾页',  
                'next':       '下一页',  
                'previous':   '上一页'  
            },  
            'info': '总_TOTAL_人',  
            'infoEmpty': '没有数据'
        },
        "aLengthMenu": [[10, 20, 50], ["10条/页", "20条/页", "50条/页"]],
        'pagingType': 'simple_numbers',
        'dom': '<"top"f>t<"bottom"ipl>',
        'serverSide': true,  //启用服务器端分页
        'ajax': function (data, callback, settings) {
            var param = {};
            param.pageSize = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
            param.pageNo = (data.start / data.length) + 1;//当前页码
            param.orgTreeId = orgTreeId;
            param.orgId = orgId;
            param.search = search;
            param.isSearchlower = isCheck;
            $http.get('/orgPersonRel/getUserOrgRelPage', param, function (result) {
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
        $('#mainTable').html('');
        table.destroy();
        initMainTable(isCheck);
    }
    else {
        engine.search(q, sync, async);
    }
}

$('#acctName').typeahead({
    hint: $('.typeahead-hint'),
    menu: $('.user-table'),
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
        initAcctTable($("#acctName").val());
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

