var id = getQueryString('id');
var tabId;
var colId;
var toastr = window.top.toastr;
var ruleOperatorData = window.top.dictionaryData.ruleOperator();

function getResInfo(){
    $http.get('/system/sysDataRule/getDataRule', {  
        id : id
    }, function (data) {
        initDataInfo(data);
        tabId = data.tabId;
        colId = data.colId;
    }, function (err) {
        toastr.error("获取信息失败！");
    })
}  

//初始化数据资源
function initDataInfo(result){
    isNull("tabName",result.tabName);
    isNull("colName",result.colName);
    isNull("colValue",result.colValue);
    initRuleOprator(ruleOperatorData,result.ruleOperator);
}

//判断是否为null
function isNull(el,str){
    if(str != null){
        $("#"+el).val(str);
    }
}

//初始化规则操作符
function initRuleOprator(result,curROVal){
    var selected = "";
    for(var i=0;i<result.length;i++){
        if(result[i].itemValue == curROVal){
            selected = "selected";
        }
        $("#ruleOperator").append("<option value='" + result[i].itemValue + "'"+selected+">" + result[i].itemCnname +"</option>");
        selected = "";
    }
    seajs.use('/vendors/lulu/js/common/ui/Select', function () {
        $('#ruleOperator').selectMatch();
    })
}

//更新文件资源信息
function updateRes(){
    $http.post('/system/sysDataRule/updateDataRule', JSON.stringify({  
        tabId : tabId,
        colId : colId,
        ruleOperator : $("#ruleOperator").val(),
        colValue : $("#colValue").val(),
        statusCd : $("#statusCd").val(),
        dataRuleId : id
    }), function (message) {
        backToList();
        toastr.success("保存成功！");
    }, function (err) {
        // toastr.error("保存失败！");
    })
}

//删除文件资源信息
function deleteRes(){
    parent.layer.confirm('是否删除该数据资源？', {
        icon: 0,
        title: '提示',
        btn: ['确定','取消']
        }, function(index, layero){
            $http.post('/system/sysDataRule/deleteDataRule', JSON.stringify({  
                dataRuleId : id
            }), function (message) {
                backToList();
                toastr.success("删除成功！");
                parent.layer.close(index);
            }, function (err) {
                toastr.error("删除失败！");
                parent.layer.close(index);
            })
        }, function(){
      
        });
}

function backToList(){
    window.location.href = "dataResList.html";
}

var logTable = $("#logTable").DataTable({
    'data': logData,
    'searching': false,
    'autoWidth': false,
    'ordering': true,
    'initComplete': function (settings, json) {
        console.log(settings, json)
    },
    "scrollY": "375px",
    'scrollCollapse': true,
    'columns': [
        { 'data': "num", 'title': '订单号', 'className': 'row-num' },
        { 'data': "explain", 'title': '操作说明', 'className': 'row-explain'},
        { 'data': "person", 'title': '操作人', 'className': 'row-psn' },
        { 'data': "org", 'title': '操作人组织', 'className': 'row-org' },
        { 'data': "acct", 'title': '操作账号', 'className': 'row-acct' },
        { 'data': "date", 'title': '时间', 'className': 'row-date' },
        { 'data': "state", 'title': '状态', 'className': 'row-state' }
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
    'dom': '<"top"f>t<"bottom"ipl>'
});

// datatable应用于tab切换出现表头错位
$('#myTabs a').click(function (e) {
    e.preventDefault();
    $(this).tab('show');
    $.fn.dataTable.tables( {visible: true, api: true} ).columns.adjust();
})

getResInfo();
