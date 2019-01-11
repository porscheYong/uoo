var orgId = getQueryString('orgId');
var orgName = getQueryString('orgName');
var orgFullName = getQueryString('orgFullName');
var orgTreeId = getQueryString('orgTreeId');
var businessName = getQueryString('businessName');
var engine, template, empty, selectNode;
var table;

empty = Handlebars.compile($("#empty-template").html());
engine = new Bloodhound({
    identify: function(o) { return o.id_str; },
    queryTokenizer: Bloodhound.tokenizers.whitespace,
    datumTokenizer: Bloodhound.tokenizers.obj.whitespace('name', 'orgName'),
    dupDetector: function(a, b) { return a.id_str === b.id_str; },
    // prefetch: remoteHost + '/demo/prefetch',
    remote: {
        url: '/personnel/getPsnBasicInfo?keyWord=%QUERY&pageNo=1&pageSize=10',
        wildcard: '%QUERY',
        filter: function (response) {
            return;
        }
    }
});

function initTable(keyWord){
    table = $("#psnTable").DataTable({
        //'data': response.data.records,
        'destroy': true,
        'searching': false,
        'autoWidth': false,
        'ordering': true,
        'lsort':true,
        'columns': [
            { 'data': null, 'title': '选择', 'className': 'row-select',
                'render': function (data, type, row, meta) {
                    return "<input type='checkbox' id='"+row.psnNbr+"' name='checkbox' onclick=''><label for='"+row.psnNbr+"' class='ui-checkbox'></label>";
                }
            },
            { 'data': "psnName", 'title': '人员姓名', 'className': 'row-name'},
            { 'data': "psnName", 'title': '重名称谓', 'className': 'row-mobile' },
            { 'data': "psnNbr", 'title': '员工工号', 'className': 'cert-no' },
            { 'data': "content", 'title': '联系方式', 'className': 'row-mobile' },
            { 'data': "createDate", 'title': '创建时间', 'className': 'status-code',
                'render': function (data, type, row, meta) {
                    var time = formatDateTime(row.createDate);
                    return time;
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
            param.keyWord = keyWord;
            $http.get('/personnel/getPsnBasicInfo', param, function (result) {
                var returnData = {};
                // returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                returnData.recordsTotal = result.total;//返回数据全部记录
                returnData.recordsFiltered = result.total;//后台不实现过滤功能，每次查询均视作全部结果
                returnData.data = result.records;//返回的数据列表
                //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                callback(returnData);

                //选中行
                $('#psnTable tbody tr').click(function () {
                    var trData = table.row('.selected').data();
                    if ($(this).hasClass('selected') ) {
                        document.getElementById(trData.psnNbr).checked = false;
                        $(this).removeClass('selected');
                    } else {
                        if(trData != undefined){
                            document.getElementById(trData.psnNbr).checked = false;
                        }
                        table.$('tr.selected').removeClass('selected');
                        $(this).addClass('selected');
                        document.getElementById(table.row('.selected').data().psnNbr).checked = true;
                    }
                });

                // $('#psnTable tbody tr input').click(function () {
                   
                // });
            }, function (err) {

            })
        }
    });
}


function engineWithDefaults(q, sync, async) {
    if (q === '') {
        // sync(engine.get(''));
        // async([]);
        engine.search('', sync, async);
    }

    else {
        engine.search(q, sync, async);
    }
}

$('#user-input').typeahead({
    hint: $('.typeahead-hint'),
    menu: $('.menu'),
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
        suggestion: empty,
        empty: empty
    }
})
    .on('typeahead:asyncrequest', function() {
        $('.Typeahead-spinner').show();
        initTable($("#user-input").val());
    })
    .on('typeahead:asynccancel typeahead:asyncreceive', function() {
        $('.Typeahead-spinner').hide();
    });

// function initPsnTable () {
//     table = $("#psnTable").DataTable({
//         // 'data': data.records,
//         'destroy': true,
//         'searching': false,
//         'autoWidth': false,
//         'ordering': true,
//         'columns': [
//             { 'data': null, 'title': '选择', 'className': 'row-select',
//                 'render': function (data, type, row, meta) {
//                     rowTotalSelr = meta.row+1;
//                     return "<input type='checkbox' id='"+row.id+"' name='checkbox' onclick=''><label for='"+row.id+"' class='ui-checkbox'></label>";
//                 }
//             },
//             { 'data': "psnName", 'title': '人员姓名', 'className': 'row-name'},
//             { 'data': "psnName", 'title': '重名称谓', 'className': 'row-mobile' },
//             { 'data': "psnNbr", 'title': '员工工号', 'className': 'cert-no' },
//             { 'data': "content", 'title': '联系方式', 'className': 'row-mobile' },
//             { 'data': "createDate", 'title': '创建时间', 'className': 'status-code',
//                 'render': function (data, type, row, meta) {
//                     var time = formatDateTime(row.createDate);
//                     return time;
//                 }
//             }
//         ],
//         'language': {
//             'emptyTable': '没有数据',
//             'loadingRecords': '加载中...',
//             'processing': '查询中...',
//             'search': '检索:',
//             'lengthMenu': ' _MENU_ ',
//             'zeroRecords': '没有数据',
//             'paginate': {
//                 'first':      '首页',
//                 'last':       '尾页',
//                 'next':       '下一页',
//                 'previous':   '上一页'
//             },
//             'info': '总_TOTAL_人',
//             'infoEmpty': '没有数据'
//         },
//         "aLengthMenu": [[10, 20, 50], ["10条/页", "20条/页", "50条/页"]],
//         'pagingType': 'simple_numbers',
//         'dom': '<"top"f>t<"bottom"ipl>',
//         'serverSide': true,  //启用服务器端分页
//         'ajax': function (data, callback, settings) {
//             var param = {};
//             param.pageSize = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
//             param.pageNo = (data.start / data.length) + 1;//当前页码
//             param.keyWord = '';
//             $http.get('/personnel/getPsnBasicInfo', param, function (result) {
//                 var returnData = {};
//                 returnData.recordsTotal = result.total;
//                 returnData.recordsFiltered = result.total;
//                 returnData.data = result.records;
//                 callback(returnData);

//                 $('#psnTable tbody tr').click(function () {
//                     if ($(this).hasClass('selected') ) {
//                         $(this).removeClass('selected');
//                     } else {
//                         table.$('tr.selected').removeClass('selected');
//                         $(this).addClass('selected');
//                     }
//                 });
//             }, function (err) {

//             })
//         }
//     });
// }

//获取选中行数据
function getSelectUser () {
    selectNode = table.rows('.selected').data();
    return table.rows('.selected').data();
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
            var temp5 = temp4 + temp2.substring(2, temp2.length); // 17:17:43
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
        var temp5 = temp4 + temp2.substring(index-1,temp2.length); // 07:17:43
        now = temp1 + temp5; // 2014/7/6 07:17:43
        now = now.replace("/","-"); //  2014-7/6 07:17:43
        now = now.replace("/","-"); //  2014-7-6 07:17:43
    }
    return now;
}

initTable("");
