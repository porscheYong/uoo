var fileSign = getQueryString('fileSign');

function initCorrectTable () {
   $("#correctTable").DataTable({
        'searching': false,
        'autoWidth': false,
        'ordering': true,
        'lSort': true,
        "scrollY": "395px",
        'scrollCollapse': true,
        'columns': [
            { 'data': null, 'title': '序号', 'className': 'row-no',
                'render': function (data, type, row, meta) {
                    return meta.row + 1 + meta.settings._iDisplayStart;
                }
            },
            { 'data': "orgId", 'title': '当前组织标识', 'className': 'row-id' },
            { 'data': "orgName", 'title': '当前组织名称', 'className': 'row-name' },
            { 'data': "parentOrgId", 'title': '需要调整的上级组织标识', 'className': 'parent-orgId' }
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
            'info': '总_TOTAL_条',
            'infoEmpty': '没有数据'
        },
        "aLengthMenu": [[10, 20, 50], ["10条/页", "20条/页", "50条/页"]],
        'pagingType': 'simple_numbers',
        'dom': '<"top"f>t<"bottom"ipl>',
        'serverSide': true,  // 服务端分页
        'ajax': function (data, callback, settings) {
            var param = {};
            param.pageSize = data.length;
            param.pageNo = (data.start / data.length) + 1;
            param.fileSign = fileSign;
            param.dataSign = 0;
            $http.get('/excelOrgImport/getExcelFileData', param, function (result) {
                var returnData = {};
                returnData.recordsTotal = result.total;
                returnData.recordsFiltered = result.total;
                returnData.data = result.records;
                callback(returnData);
            }, function (err) {

            })
        }
    });
    
    var loading = parent.loading;
    loading.screenMaskDisable('container');
}

function initErrorTable () {
    $("#errorTable").DataTable({
        'searching': false,
        'destroy': true,
        'autoWidth': false,
        'ordering': true,
        'lSort': true,
        "scrollY": "395px",
        'scrollCollapse': true,
        'columns': [
            { 'data': null, 'title': '序号', 'className': 'row-no',
              'render': function (data, type, row, meta) {
                return meta.row + 1 + meta.settings._iDisplayStart;
              }
            },
            { 'data': "orgId", 'title': '当前组织标识', 'className': 'row-id' },
            { 'data': "orgName", 'title': '当前组织名称', 'className': 'row-name' },
            { 'data': "parentOrgId", 'title': '需要调整的上级组织标识', 'className': 'parent-orgId' }
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
        'serverSide': true,  // 服务端分页
        'ajax': function (data, callback, settings) {
            var param = {};
            param.pageSize = data.length;
            param.pageNo = (data.start / data.length) + 1;
            param.fileSign = fileSign;
            param.dataSign = 1;
            $http.get('/excelOrgImport/getExcelFileData', param, function (result) {
                var returnData = {};
                returnData.recordsTotal = result.total;
                returnData.recordsFiltered = result.total;
                returnData.data = result.records;
                callback(returnData);
            }, function (err) {

            })
        }
    });
}

function uploadConfirm () {
    $http.post('/excelOrgImport/addExcelFileData', JSON.stringify({
        fileSign: fileSign
    }), function () {
        // loading.screenMaskDisable('container');
        toastr.success('更新成功！');
    }, function () {
        // loading.screenMaskDisable('container');
    })
}

initCorrectTable();
initErrorTable();
