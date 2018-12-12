var orgId = getQueryString('orgId');
var orgName = getQueryString('orgName');
var orgTreeId = getQueryString('orgTreeId');
var lChBox =document.getElementById("lowerCheckBox");   //是否显示下级人员
var orgFullName = '';
var table;
var isCheck = 0;
var sortFlag = 0;
var currentPage = 0;

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
        $('.breadcrumb').html(pathStr);
    }
}

function initMainTable(isCheck,search){
    table = $("#mainTable").DataTable({
        'destroy':true,
        'searching': false,
        'autoWidth': false,
        'ordering': true,
        'info': true,
        "scrollY": "375px",
        'columns': [
            { 'data': "psnName", 'title': '人员姓名', 'className': 'row-psnName' ,
            'render': function (data, type, row, meta) {
                if(row.typeName == '主账号'){
                    return '<a href="addMainAccount.html?orgTreeId=' + orgTreeId + '&orgName=' + encodeURI(orgName) +'&orgId=' + orgId + '&acctId='+ row.accId + '&statusCd='+row.statusCd+'&opBtn=0&hType=mh">'+ row.psnName +'</a>'
                }else{

                    return '<a href="addSubAccount.html?orgTreeId=' + orgTreeId + '&orgName=' + encodeURI(orgName) +'&orgId=' + orgId + '&acctId='+ row.accId +'&statusCd='+row.statusCd+'&opBtn=0&hType=mh">'+ row.psnName +'</a>'
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
            },
            { 'data': "accId", 'title': '用户类型', 'className': 'row-acctId' }
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
            param.search = search;
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

    initSort(".row-psnName","psnName");
    initSort(".row-typeName","typeName");
    initSort(".row-acc","acct");
    initSort(".row-org","orgName");
    initSort(".row-certNo","certNo");
    initSort(".row-statusCd","statusCd");
}


$('#orgName').html(orgName);
getOrgExtInfo();
// getUserList(orgId);
initMainTable(isCheck,'');


$('#addBtn').on('click', function () {
    var url = 'add.html?&orgName=' + encodeURI(orgName) +'&orgId=' + orgId + '&orgTreeId=' + orgTreeId + "&orgFullName=" + encodeURI(orgFullName);
    $(this).attr('href', url);
})

function boxClick(){            //点击复选框
    sortFlag = 0;
    if(lChBox.checked == true){
        isCheck = 1;
    }else{
        isCheck = 0;
    }
    initMainTable(isCheck,'');
}

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
                "typeName":arr[i].typeName,
                "acct":arr[i].acct,
                "orgName":arr[i].orgName,
                "certNo":arr[i].certNo,
                "statusCd":arr[i].statusCd,
                "accId":arr[i].accId
            })
            .draw();
    }
  }
      
function initSort(thClass,param){        //初始化用户姓名排序
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