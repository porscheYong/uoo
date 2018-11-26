var orgId = getQueryString('id');
var orgName = getQueryString('name');
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
            table.destroy();
            table = $("#contactTable").DataTable({
                'data': response.data.records,
                'destroy': true,
                'searching': false,
                'autoWidth': false,
                'ordering': true,
                'initComplete': function (settings, json) {
                    console.log(settings, json)
                },
                'columns': [
                    { 'data': "psnName", 'title': '人员姓名', 'className': 'row-name',
                        // 'render': function (data, type, row, meta) {
                        //     return "<a href='edit.html?id=" + row.orgId + "&orgRootId=" + row.orgRootId + "&personnelId=" + row.personnelId + "'>" + row.psnName + "</a>";
                        // }
                    },
                    { 'data': "psnName", 'title': '重名称谓', 'className': 'row-mobile' },
                    { 'data': "psnNbr", 'title': '员工工号', 'className': 'cert-no' },
                    { 'data': "content", 'title': '联系方式', 'className': 'row-mobile' },
                    { 'data': "psnName", 'title': '所属组织全称', 'className': 'org-name' },
                    { 'data': "psnName", 'title': '职位名称', 'className': 'post-name' },
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
                'drawCallback': function(){
                    this.api().column(0).nodes().each(function(cell, i) {
                        cell.innerHTML =  i + 1;
                    });
                }
                // 'serverSide': true,  //启用服务器端分页
                // 'ajax': function (data, callback, settings) {

                //     //手动控制遮罩
                //     $('#table-container').spinModal();
                //     var param = {};
                //     param.pageSiz = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
                //     param.startPage = (data.start / data.length)+1;//当前页码
                //     param.p_p_resource_id = 'getUomDepartments';
                //     param.treeId = '<%=treeId%>';
                //     param.orgId = '<%=orgId%>';
                //     param.queryCondition = data.search.value;

                //     $.ajax({
                //         type: "POST",
                //         url: "<%=ajaxUrl%>",
                //         data: param,  //传入组装的参数
                //         success: function (result) {
                //             var result = JSON.parse(result);
                //             //封装返回数据
                //             var returnData = {};
                //             returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                //             returnData.recordsTotal = result.count;//返回数据全部记录
                //             returnData.recordsFiltered = result.count;//后台不实现过滤功能，每次查询均视作全部结果
                //             returnData.data = result.userList;//返回的数据列表
                //             //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                //             //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                //             callback(returnData);
                //         },
                //         error: function () {
                //             $('#table-container').spinModal(false);
                //         }
                //     })
                // }
                // 'serverSide': true,  // 服务端分页
                // 'ajax': {
                //     data : function(d) {
                //         // console.log('custom request', d);
                //         //删除多余请求参数
                //         for(var key in d){
                //             if(key.indexOf("columns")==0||key.indexOf("order")==0||key.indexOf("search")==0){
                //               //以columns开头的参数删除
                //               delete d[key];
                //             }
                //         }
                //         var searchParams= {
                //             "retryType":$("#retryType").val(),
                //             "departmentCode":$("#departmentCode").val()!=""?$("#departmentCode").val():null,
                //             "projectCode":$("#projectCode").val()!=""?$("#projectCode").val():null,
                //             "serviceName":$("#serviceName").val()!=""?$("#serviceName").val():null,
                //             "csrfmiddlewaretoken":csrftoken
                //         };
                //         //附加查询参数
                //         if(searchParams){
                //             $.extend(d,searchParams); //给d扩展参数
                //         }
                //     },
                //     dataSrc: function (json) {
                //         // console.log('process response data from server side before display.');
                //         return json.data;
                //     },
                //     dataType: "json",
                //     dataFilter: function (json) {//json是服务器端返回的数据
                //        // json = JSON.parse(json);
                //        // var returnData = {};
                //        // returnData.draw = json.data.draw;
                //        // returnData.recordsTotal = json.data.total;//返回数据全部记录
                //        // returnData.recordsFiltered = json.data.total;//后台不实现过滤功能，每次查询均视作全部结果
                //        // returnData.data = json.data.retryProjectList;//返回的数据列表
                //        // return JSON.stringify(returnData);//这几个参数都是datatable需要的，必须要
                //     },
                //     url : "/queryWarningInfo.do",
                //     type : "POST",
                //     crossDomain: true
                // }
            });

            $('#contactTable tbody tr').click(function () {
                if ($(this).hasClass('selected') ) {
                    $(this).removeClass('selected');
                } else {
                    table.$('tr.selected').removeClass('selected');
                    $(this).addClass('selected');
                }
            });
            return response.data.records;
        }
    }
});

// ensure default users are read on initialization
// engine.get('1090217586', '58502284', '10273252', '24477185')

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
    })
    .on('typeahead:asynccancel typeahead:asyncreceive', function() {
        $('.Typeahead-spinner').hide();
    });

// typeahead获取选中的节点
// $('#demo-input').bind('typeahead:select', function(ev, suggestion) {
//     selectNode = suggestion;
// });

$('#addBtn').on('click', function () {
    var url = 'add.html?id=' + orgId  + '&name=' + orgName;;
    $(this).attr('href', url);
})

function  addTreeNode () {
    var loading = parent.loading;
    loading.screenMaskEnable('container');
    $http.post('/orgRel/addOrgRel', JSON.stringify({
        orgRootId: '1',
        supOrgId: orgId,
        orgId: selectNode.orgId
    }), function (data) {
        var newNode = {
            name: selectNode.orgName,
            id: selectNode.orgId
        }
        parent.addNodeById(orgId, newNode);
        loading.screenMaskDisable('container');
    }, function (err) {
        console.log(err);
        loading.screenMaskDisable('container');
    })
}

function cancel () {
    var url = "list.html?id=" + orgId;
    window.location.href = url;
}

// 获取组织完整路径
function getOrgExtInfo () {
    var pathArry = parent.nodeArr;
    var pathStr = '';
    for (var i = pathArry.length - 1; i >= 0; i--) {
        if (i === 0) {
            pathStr +=  '<span class="breadcrumb-item"><a href="javascript:viod(0);">' + pathArry[i] + '</a></span>';
        } else {
            pathStr += '<span class="breadcrumb-item"><a href="javascript:viod(0);">' + pathArry[i] + '</a><span class="breadcrumb-separator" style="margin: 0 9px;">/</span></span>';
        }
    }
    $('.breadcrumb').html(pathStr);
}

$('#orgName').html(orgName);
getOrgExtInfo();

// function initContactTable () {
//     table = $("#contactTable").DataTable({
//         'data': [],
//         'destroy': true,
//         'paging': false,
//         'searching': false,
//         'autoWidth': false,
//         'ordering': true,
//         'columns': [
//             { 'data': null, 'title': '序号', 'className': 'row-no' },
//             { 'data': "psnName", 'title': '姓名', 'className': 'row-name',
//                 // 'render': function (data, type, row, meta) {
//                 //     return "<a href='edit.html?id=" + row.orgId + "&orgRootId=" + row.orgRootId + "&personnelId=" + row.personnelId + "'>" + row.psnName + "</a>";
//                 // }
//             },
//             { 'data': "psnName", 'title': '重名称谓', 'className': 'row-mobile' },
//             { 'data': "psnName", 'title': '员工工号', 'className': 'cert-no' },
//             { 'data': "psnName", 'title': '职位名称', 'className': 'post-name' },
//             { 'data': "psnName", 'title': '所属组织', 'className': 'org-name' },
//             { 'data': "psnName", 'title': '状态', 'className': 'status-code' }
//         ],
//         'language': {
//             'emptyTable': '没有数据'
//         },
//         'dom': '<"top"f>t<"bottom"pl>',
//     });
//     var loading = parent.loading;
//     loading.screenMaskDisable('container');
// }

function initContactTable () {
    // $http.get('/personnel/getPsnBasicInfo', {
    //     keyWord: '',
    //     pageSize: 10,
    //     pageNo: 1
    // }, function (data) {
        table = $("#contactTable").DataTable({
            // 'data': data.records,
            'destroy': true,
            'searching': false,
            'autoWidth': false,
            'ordering': true,
            'initComplete': function (settings, json) {
                console.log(settings, json)
            },
            'columns': [
                { 'data': "psnName", 'title': '人员姓名', 'className': 'row-name',
                    // 'render': function (data, type, row, meta) {
                    //     return "<a href='edit.html?id=" + row.orgId + "&orgRootId=" + row.orgRootId + "&personnelId=" + row.personnelId + "'>" + row.psnName + "</a>";
                    // }
                },
                { 'data': "psnName", 'title': '重名称谓', 'className': 'row-mobile' },
                { 'data': "psnNbr", 'title': '员工工号', 'className': 'cert-no' },
                { 'data': "content", 'title': '联系方式', 'className': 'row-mobile' },
                { 'data': "psnName", 'title': '所属组织全称', 'className': 'org-name' },
                { 'data': "psnName", 'title': '职位名称', 'className': 'post-name' },
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
                param.keyWord = '';
                $http.get('/personnel/getPsnBasicInfo', param, function (result) {
                    var returnData = {};
                    returnData.recordsTotal = result.total;
                    returnData.recordsFiltered = result.total;
                    returnData.data = result.records;
                    callback(returnData);
                }, function (err) {
                    console.log(err)
                })
            }
        });

        $('#contactTable tbody tr').click(function () {
            if ($(this).hasClass('selected') ) {
                $(this).removeClass('selected');
            } else {
                table.$('tr.selected').removeClass('selected');
                $(this).addClass('selected');
            }
        });
    // }, function (err) {
    //     console.log(err)
    // })
}

//获取选中行数据
function getSelectUser () {
    selectNode = table.rows('.selected').data();
    return table.rows('.selected').data();
}

    table = $("#contactTable").DataTable({
        'data': [],
        'destroy': true,
        'paging': false,
        'searching': false,
        'autoWidth': false,
        'ordering': true,
        'columns': [
            { 'data': null, 'title': '序号', 'className': 'row-no' },
            { 'data': "", 'title': '姓名', 'className': 'row-name'},
            { 'data': "", 'title': '重名称谓', 'className': 'row-mobile' },
            { 'data': "", 'title': '员工工号', 'className': 'cert-no' },
            { 'data': "", 'title': '职位名称', 'className': 'post-name' },
            { 'data': "", 'title': '所属组织', 'className': 'org-name' },
            { 'data': "", 'title': '状态', 'className': 'status-code' }
        ],
        'language': {
            'emptyTable': '没有数据'
        },
        'dom': '<"top"f>t<"bottom"pl>',
    });

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
        var temp5 = temp4 + temp2.substring(index,temp2.length); // 07:17:43
        now = temp1 + temp5; // 2014/7/6 07:17:43
        now = now.replace("/","-"); //  2014-7/6 07:17:43
        now = now.replace("/","-"); //  2014-7-6 07:17:43
    }
    return now;
}
initContactTable();



