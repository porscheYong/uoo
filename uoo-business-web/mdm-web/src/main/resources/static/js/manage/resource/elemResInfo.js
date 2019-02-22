var id = getQueryString('id');
var toastr = window.top.toastr;
var logTable;
var formValidate;

seajs.use('/vendors/lulu/js/common/ui/Validate', function (Validate) {
    var posEditForm = $('#editInfo');
    formValidate = new Validate(posEditForm);
    formValidate.immediate();
    posEditForm.find(':input').each(function () {
      $(this).bind({
          paste : function(){
              formValidate.isPass($(this));
              $(this).removeClass('error');
          }
      });
    });
});

function getResInfo(){  
    $http.get('/system/SysElement/get', {  
        id : id
    }, function (data) {
        initElemInfo(data);
    }, function (err) {
        toastr.error("获取信息失败！");
    })
}  

 //初始化元素资源信息
 function initElemInfo(result){     
    isNull("elementName",result.elementName);
    isNull("elementCode",result.elementCode);
    isNull("elementType",result.elementType);
    isNull("elementDesc",result.elementDesc);
    isNull("urlAddr",result.urlAddr);
    initMenuName(parent.menuList,result.menuCode);
    initUpdateTable();
}

//判断时候为null
function isNull(el,str){
    if(str != null){
        $("#"+el).val(str);
    }
}

//初始化菜单select
function initMenuName(result,currentMenu){
    var selected = "";
    for(var i=0;i<result.length;i++){
        if(currentMenu == result[i].menuCode){
            selected = "selected";
        }else{
            selected = "";
        }
        $("#menuName").append("<option value='" + result[i].menuCode + "'"+selected+">" + result[i].menuName +"</option>");
    }
    seajs.use('/vendors/lulu/js/common/ui/Select', function () {
        $('#menuName').selectMatch();
    });
}

//更新元素资源信息
function updateRes(){
    if(!formValidate.isAllPass()){
        return;
    }
    
    $http.post('/system/SysElement/update', JSON.stringify({  
        elementName : $("#elementName").val(),
        elementCode : $("#elementCode").val(),
        elementType : $("#elementType").val(),
        elementDesc : $("#elementDesc").val(),
        menuCode : $("#menuName").val(),
        urlAddr : $("#urlAddr").val(),
        statusCd : $("#statusCd").val(),
        elementId : id
    }), function (message) {
        backToList();
        toastr.success("保存成功！");
    }, function (err) {
        // toastr.error("保存失败！");
    })
}

//删除元素资源信息
function deleteRes(){
    parent.layer.confirm('是否删除该元素资源？', {
        icon: 0,
        title: '提示',
        btn: ['确定','取消']
        }, function(index, layero){
            $http.post('/system/SysElement/delete', JSON.stringify({  
                elementId : id
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
    window.location.href = "elemResList.html";
}

//初始化更新记录列表
function initUpdateTable(){
    logTable = $("#logTable").DataTable({
        'destroy':true,
        'searching': false,
        'autoWidth': true,
        'ordering': true,
        'lSort': true,
        'info': true,
        "scrollY": "390px",
        'scrollCollapse': true,
        'columns': [
            { 'data': null, 'title': '订单号', 'className': 'row-num', 
                'render': function (data, type, row, meta) {
                    return "<span style='cursor:pointer;' title='"+row.batchNumber+"'>"+row.batchNumber+"</span>";
                }     
            },
            { 'data': "operateType", 'title': '操作说明', 'className': 'row-explain'},
            { 'data': "userName", 'title': '操作人', 'className': 'row-psn' },
            { 'data': "userOrgName", 'title': '操作人组织', 'className': 'row-org' },
            { 'data': "userAccout", 'title': '操作账号', 'className': 'row-acct' },
            { 'data': null, 'title': '时间', 'className': 'row-date' ,
                'render': function (data, type, row, meta) {
                    return parent.formatDateTime(row.createDate);
                }     
            },
            { 'data': null, 'title': '状态', 'className': 'row-state', 
                'render': function (data, type, row, meta) {
                    if(row.statusCd == 1000){
                        return "有效";
                    }else{
                        return "失效";
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
            param.recordId = id;
            param.tableName = "SYS_ELEMENT";
            $http.get('/public/modifyHistory/listByRecord', param, function (result) {
                var returnData = {};
                // returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                returnData.recordsTotal = result.total;//返回数据全部记录
                returnData.recordsFiltered = result.total;//后台不实现过滤功能，每次查询均视作全部结果
                returnData.data = result.records;//返回的数据列表
                //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                callback(returnData);
                // loading.screenMaskDisable('container');
            }, function (err) {
                loading.screenMaskDisable('container');
            })
        }
    });
}

// datatable应用于tab切换出现表头错位
$('#myTabs a').click(function (e) {
    e.preventDefault();
    $(this).tab('show');
    $.fn.dataTable.tables( {visible: true, api: true} ).columns.adjust();
})

getResInfo();