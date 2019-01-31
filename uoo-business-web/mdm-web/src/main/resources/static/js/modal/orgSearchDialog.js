var orgTreeId = getQueryString('orgTreeId'),
    table,
    query,
    delayTime = 500;
var orgFrame = parent.window['standardOrg'] || parent.window['business'];

function initOrgTable (query) {
    table = $("#orgTable").DataTable({
        'destroy': true,
        'searching': false,
        'autoWidth': false,
        'ordering': true,
        'lSort': true,
        'columns': [
            { 'data': "orgName", 'title': '组织名称', 'className': 'org-name',
                'render': function (data, type, row, meta) {
                    return '<span title="'+ row.orgName +'">'+ row.orgName +'</span>'
                }
            },
            { 'data': "fullName", 'title': '组织全称', 'className': 'full-name',
                'render': function (data, type, row, meta) {
                    return '<span title="'+ row.fullName +'">'+ row.fullName +'</span>'
                }
            },
            { 'data': "orgCode", 'title': '组织编码', 'className': 'org-code' },
            { 'data': "orgTreeInfos", 'title': '引用在', 'className': 'org-info' },
            { 'data': "createDate", 'title': '创建时间', 'className': 'create-date',
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
            param.orgTreeId = orgTreeId;
            param.search = query;
            $http.get('/org/getOrgPage', param, function (result) {
                var returnData = {};
                returnData.recordsTotal = result.total;
                returnData.recordsFiltered = result.total;
                returnData.data = result.records;
                callback(returnData);

                $('#orgTable tbody tr.selectable').click(function () {
                    if ($(this).hasClass('selected') ) {
                        $(this).removeClass('selected');
                    } else {
                        table.$('tr.selected').removeClass('selected');
                        $(this).addClass('selected');
                    }
                });
                //该组织已挂在组织树其他节点上提示
                $('#orgTable tbody tr.unselectable').click(function () {
                    if (orgFrame && orgFrame.parent.layer) {
                        orgFrame.parent.layer.confirm('该组织已挂在组织树其他节点上!', {
                            icon: 0,
                            title: '提示',
                            btn: ['确定']
                        }, function(index){
                            parent.layer.close(index);
                        });
                    }
                });
            }, function (err) {

            })
        },
        "createdRow": function( row, data, dataIndex ) {
            if ( !data.flag ) {
                $(row).addClass( 'selectable' );
            }
            else {
                $(row).addClass( 'unselectable' );
            }
        }
    });
}

// 搜索组织
function search () {
    query = $('#orgInput').val();
    clearTimeout(this.timer);
    // 添加的延时
    this.timer = setTimeout(function(){
        initOrgTable(query);
    }, delayTime);
}

//获取选中行数据
function getSelectOrg () {
    selectNode = table.rows('.selected').data();
    return table.rows('.selected').data();
}

initOrgTable();
