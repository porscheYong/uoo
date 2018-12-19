var isIE=!!window.ActiveXObject;
var isIE8=isIE&&document.documentMode<9;

var orgId = getQueryString('id');
var orgTreeId = getQueryString('orgTreeId');
var pid = getQueryString('pid');
var orgName = getQueryString('name');
var orgTreeName = getQueryString('orgTreeName');
var table;
var checked = false;
var sortFlag = 0;
var currentPage = 0;

$('#orgName').html(orgName);
parent.getOrgExtInfo();

// function orgInfo() {
//     var url = '/inaction/business/orgInfo.html?id=' + orgId + '&orgTreeId=' + orgTreeId + '&pid=' + pid + '&name=' + encodeURI(orgName);
//     window.location.href = url;
// }

function initOrgPersonnelTable (isSearchlower,search) {
    table = $("#personnelTable").DataTable({
        'searching': false,
        'destroy': true,
        'autoWidth': false,
        'ordering': true,
        "scrollY": "375px",
        'scrollCollapse': true,
        'columns': [
            { 'data': null, 'title': '序号', 'className': 'row-no'},
            { 'data': "psnName", 'title': '姓名', 'className': 'row-name',
                'render': function (data, type, row, meta) {
                    return "<a href='edit.html?id=" + row.orgId + "&orgRootId=" + row.orgRootId + "&personnelId=" + row.personnelId + 
                                        "&name="+ orgName +"&orgTreeId="+orgTreeId+"'>" + row.psnName + "</a>";
                }
            },
            { 'data': "doubleName", 'title': '重名称谓', 'className': 'row-mobile' },
            { 'data': "psnNbr", 'title': '员工工号', 'className': 'cert-no' },
            { 'data': "postName", 'title': '职位名称', 'className': 'post-name' },
            { 'data': "orgName", 'title': '所属组织', 'className': '' },
            { 'data': "statusCd", 'title': '状态', 'className': 'status-code',
                'render': function (data, type, row, meta) {
                    var statusStr = '';
                    if (row.statusCd == '1000') {
                        statusStr = '<span>有效</span>';
                    } else {
                        statusStr = '<span>无效</span>';
                    }
                    return statusStr
                }
            },
            { 'data': "orgId", 'title': '', 'className': 'row-orgId'},
            { 'data': "orgRootId", 'title': '', 'className': 'row-orgRootId'},
            { 'data': "personnelId", 'title': '', 'className': 'row-personnelId'}
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
        },
        'serverSide': true,  //启用服务器端分页
        'ajax': function (data, callback, settings) {
            var param = {};
            param.pageSize = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
            param.pageNo = (data.start / data.length) + 1;//当前页码
            param.isSearchlower = isSearchlower;//是否显示下级组织人员
            param.search = search;
            param.orgTreeId = orgTreeId;
            param.orgId = orgId;
            $http.get('/orgPersonRel/getPerOrgRelPage', param, function (result) {
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

    initSort(".row-name","psnName");
    initSort(".row-mobile","doubleName");
    initSort(".cert-no","psnNbr");
    initSort(".post-name","postName");
    initSort(".org-name","orgName");
    
    var loading = parent.loading;
    loading.screenMaskDisable('container');
}

function initFreePersonnelTable () {
    var num = 1;
    table = $("#personnelTable").DataTable({
        'searching': false,
        'destroy': true,
        'autoWidth': false,
        'ordering': true,
        "scrollY": "375px",
        'scrollCollapse': true,
        'columns': [
            { 'data': "psnName", 'title': '序号', 'className': 'row-no' ,
                'render': function (data, type, row, meta) {
                    return num++;
                }
            },
            { 'data': "psnName", 'title': '姓名', 'className': 'row-name',
                'render': function (data, type, row, meta) {
                    return "<a href='edit.html?id=" + row.orgId + "&orgRootId=" + row.orgRootId + "&personnelId=" + row.personnelId +
                        "&name="+ orgName +"&orgTreeId="+orgTreeId+"'>" + row.psnName + "</a>";
                }
            },
            { 'data': "psnNbr", 'title': '员工工号', 'className': 'cert-no' },
            // { 'data': "postName", 'title': '职位名称', 'className': 'post-name' },
            // { 'data': "orgName", 'title': '所属组织', 'className': 'org-name' },
            { 'data': "statusCd", 'title': '状态', 'className': 'status-code',
                'render': function (data, type, row, meta) {
                    var statusStr = '';
                    if (row.statusCd == '1000') {
                        statusStr = '<span>有效</span>';
                    } else {
                        statusStr = '<span>无效</span>';
                    }
                    return statusStr
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
        },
        'serverSide': true,  //启用服务器端分页
        'ajax': function (data, callback, settings) {
            var param = {};
            param.pageSize = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
            param.pageNo = (data.start / data.length) + 1;//当前页码
            $http.get('/personnel/getFreePsnInfo', param, function (result) {
                var returnData = {};
                // returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                returnData.recordsTotal = result.total;//返回数据全部记录
                returnData.recordsFiltered = result.total;//后台不实现过滤功能，每次查询均视作全部结果
                returnData.data = result.records;//返回的数据列表
                callback(returnData);
            }, function (err) {

            })
        }
    });

    initSortFree(".row-name","psnName");
    initSortFree(".cert-no","psnNbr");

    var loading = parent.loading;
    loading.screenMaskDisable('container');
}

//勾选显示下级组织人员
function showLower() {
    sortFlag = 0;
    checked = $('#isShowLower').is(':checked')? 1: 0;
    if(isIE8 && checked == 1){
        $(".ui-checkbox").css("background-position","0 -40px");
    }else if(isIE8 && checked == 0){
        $(".ui-checkbox").css("background-position","0px 0px");
    }
    initOrgPersonnelTable(checked, '');
}

if (orgId == 'noSort') {
    $('#titleName').html('游离人员');
    $('#isShow').hide();
    initFreePersonnelTable();
}
else {
    $('#titleName').html('组织人员');
    $('#isShow').show();
    initOrgPersonnelTable(0, '');
}
// $('#editBtn').on('click', function () {
//     var url = 'edit.html?id=' + orgId;
//     $(this).attr('href', url);
// })
$('#addBtn').on('click', function () {
   var url = "add.html?id=" + orgId + "&orgTreeId=" + orgTreeId + "&orgTreeName=" + encodeURI(orgTreeName) + "&name=" + encodeURI(orgName);
   $(this).attr('href', url);
})

function arrSort (arr, dataLeven) { // 参数：arr 排序的数组; dataLeven 数组内的需要比较的元素属性 
    /* 获取数组元素内需要比较的值 */
    function getValue (option) { // 参数： option 数组元素
      if (!dataLeven) return option
      var data = option
      dataLeven.split('.').filter(function (item) {
        data = data[item]
      })
      return data + ''
    }
    arr.sort(function (item1, item2) {
      return getValue(item1).localeCompare(getValue(item2), 'zh-CN');
    })
  }

  function descSort(asc,desc){      //desc排序
    for(var i=asc.length-1;i>=0;i--){
        desc.push(asc[i]);
    }
    return desc;
  }

  function sortToTable(arr){   //将排完序的数据写入表格
    for(var i =0;i<arr.length;i++){
        sortFlag = 1;
        table
            .row(i)
            .data({
                "psnName":arr[i].psnName,
                "doubleName":arr[i].doubleName,
                "psnNbr":arr[i].psnNbr,
                "postName":arr[i].postName,
                "orgName":arr[i].orgName,
                "statusCd":arr[i].statusCd,
                "orgId":arr[i].orgId,
                "orgRootId":arr[i].orgRootId,
                "personnelId":arr[i].personnelId

            })
            .draw();
    }
  }

  function sortToTableFree(arr){   //将排完序的数据写入表格(游离人员)
    for(var i =0;i<arr.length;i++){
        sortFlag = 1;
        table
            .row(i)
            .data({
                "psnName":arr[i].psnName,
                "psnNbr":arr[i].psnNbr,
                "personnelId":arr[i].personnelId
            })
            .draw();
    }
  }

    //初始化排序
  function initSort(thClass,param){       
    $(thClass).on('click', function () {
        var tableLength = table.data().length;
        var arr = [];
        var descArr = [];
    
        for(var i = 0;i < tableLength;i++){
            arr.push(table.row(i).data());
        }
        
        arrSort(arr,param);
        
        if($(this).hasClass("sorting_desc")){
            descArr = descSort(arr,descArr);
            sortToTable(descArr);
        }else{
            sortToTable(arr);
        }
        table.page(currentPage).draw( false );
    });
}


 //初始化排序(游离人员)
function initSortFree(thClass,param){       
    $(thClass).on('click', function () {
        var tableLength = table.data().length;
        var arr = [];
        var descArr = [];
    
        for(var i = 0;i < tableLength;i++){
            arr.push(table.row(i).data());
        }
        
        arrSort(arr,param);
        
        if($(this).hasClass("sorting_desc")){
            descArr = descSort(arr,descArr);
            sortToTableFree(descArr);
        }else{
            sortToTableFree(arr);
        }
        table.page(currentPage).draw( false );
    });
}