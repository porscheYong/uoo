var logTable;
var query;
var loading = parent.loading;

loading.screenMaskEnable('LAY_app_body');

//初始化日志列表
function initLogTable(keyWord,logEnum){
    logTable = $("#logTable").DataTable({
        'destroy':true,
        'searching': false,
        'autoWidth': true,
        'ordering': true,
        'lSort': true,
        'info': true,
        "scrollY": "375px",
        'scrollCollapse': true,
        'columns': [
            { 'data': null, 'title': '序号', 'className': 'row-num' ,
                'render': function (data, type, row, meta) {
                    return meta.row + 1 + meta.settings._iDisplayStart;
                }
            },
            { 'data': null, 'title': '账号', 'className': 'row-acct',
                'render': function (data, type, row, meta) {
                    return "<a href='javascript:void(0);' onclick='getlogInfo(\""+row.logEnum+"\","+row.logId+")'>"+row.opUser+"</span>";
                }
            },
            { 'data': "logName", 'title': '日志名称', 'className': 'row-lName'},
            { 'data': null, 'title': '类型', 'className': 'row-lType',
                'render': function (data, type, row, meta) {
                    if(row.logEnum == "OPT"){
                        return "操作日志";
                    }else{
                        return "登录日志";
                    }
                }
            },
            { 'data': null, 'title': '是否成功', 'className': 'row-isSuc',
                'render': function (data, type, row, meta) {
                    if(row.succeed == 1){
                        return "是";
                    }else{
                        return "否";
                    }
                }
            },
            { 'data': "ip", 'title': 'IP地址', 'className': 'row-iAddr'},
            { 'data': null, 'title': '操作时间', 'className': 'row-date',
                'render': function (data, type, row, meta) {
                    return formatDateTime(row.createDate);
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
            'info': '总_TOTAL_个',  
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
            param.logEnum = logEnum;
            param.keyWord = keyWord;
            $http.get('/system/sysOperationLog/listPage', param, function (result) {
                var returnData = {};
                // returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                returnData.recordsTotal = result.total;//返回数据全部记录
                returnData.recordsFiltered = result.total;//后台不实现过滤功能，每次查询均视作全部结果
                returnData.data = result.records;//返回的数据列表
                //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                callback(returnData);
                loading.screenMaskDisable('LAY_app_body');
            }, function (err) {
                loading.screenMaskDisable('LAY_app_body');
            })
        }
    });
}

// 搜索日志
function search () {
    loading.screenMaskEnable('LAY_app_body');
    query = $('.ui-input-search').val();
    initLogTable(query,"");
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
            if(temp2.indexOf(":") == 1){
                temp2 = temp2.substring(1, temp2.length);
            }else{
                temp2 = temp2.substring(2, temp2.length);
            }
            var temp5 = temp4 + temp2; // 17:17:43
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
        if(temp2.indexOf(":") == 1){
            temp2 = temp2.substring(1,temp2.length);
        }else{
            temp2 = temp2.substring(2,temp2.length);
        }
        var temp5 = temp4 + temp2; // 07:17:43
        now = temp1 + temp5; // 2014/7/6 07:17:43
        now = now.replace("/","-"); //  2014-7/6 07:17:43
        now = now.replace("/","-"); //  2014-7-6 07:17:43
    }
    return now;
}

function getlogInfo(logEnum,logId){
    if(logEnum == "OPT"){
        window.location.href = "optLogInfo.html?logId="+logId+"&logEnum="+logEnum;
    }else{
        window.location.href = "loginLogInfo.html?logId="+logId+"&logEnum="+logEnum;
    }
}

initLogTable("","");