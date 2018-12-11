var orgId = getQueryString('orgId');
var orgName = getQueryString('orgName');
var orgTreeId = getQueryString('orgTreeId');
var lChBox =document.getElementById("lowerCheckBox");   //是否显示下级人员
var orgFullName = '';
var table;
var isCheck = 0;
var currentData = [];

// 获取组织完整路径
function getOrgExtInfo () {
    var pathArry = parent.nodeArr;
    var pathStr = '';
    if (pathArry && pathArry.length > 0) {
        for (var i = pathArry.length - 1; i >= 0; i--) {
            var node = pathArry[i].node;
            if (pathArry[i].current) {
                pathStr +=  '<span class="breadcrumb-item"><a href="javascript:void(0);">' + node.name + '</a></span>';
            } else {
                pathStr += '<span class="breadcrumb-item"><a href="javascript:void(0);" onclick="parent.openTreeById('+orgId+','+node.id+')">' + node.name + '</a><span class="breadcrumb-separator" style="margin: 0 9px;">/</span></span>';
            }
            orgFullName += node.name + '/'; 
        }
        orgFullName = orgFullName.toString().substring(0,orgFullName.toString().length-1);
        // $('#userFrame').contents().find('.breadcrumb').html(pathStr);
        $('.breadcrumb').html(pathStr);
    }
}

function initMainTable(isCheck){
    table = $("#mainTable").DataTable({
        'destroy':true,
        'searching': false,
        'autoWidth': false,
        'ordering': true,
        'info': true,
        // 'lengthChange':false,
        'initComplete': function (settings, json) {
            console.log(settings, json)
        },
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
            'search': '搜索：',  
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
                console.log(err)
            })
        }
    });
}


$('#orgName').html(orgName);
getOrgExtInfo();
// getUserList(orgId);
initMainTable(isCheck);



$('#addBtn').on('click', function () {
    var url = 'add.html?&orgName=' + orgName +'&orgId=' + orgId + '&orgTreeId=' + orgTreeId + "&orgFullName=" + orgFullName;
    $(this).attr('href', url);
})

function boxClick(){            //点击复选框
    console.log(lChBox.checked);
    if(lChBox.checked == true){
        isCheck = 1;
    }else{
        isCheck = 0;
    }
    initMainTable(isCheck);
}