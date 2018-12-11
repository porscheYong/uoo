// lulu ui select插件
seajs.use('../../../static/vendors/lulu/js/common/ui/Select', function () {
  $('select').selectMatch();
})

// 地址级联选择
var data = [
  {
      value: 'A',
      label: 'a',
      children: [
          {
              value: 'AA1',
              label: 'aa1',
          },
          {
              value: 'BB1',
              label: 'bb1'
          }
      ]
  },
  {
      value: 'B',
      label: 'b',
      children: [
          {
              value: 'AA2',
              label: 'aa2',
              children: [
                  {
                      value: 'AAA3',
                      label: 'aaa3',
                      children: [
                          {
                              value: 'AAA3',
                              label: 'aaa3',
                              children: [
                                  {
                                      value: 'AAA3',
                                      label: 'aaa3',
                                  },
                                  {
                                      value: 'BBB3',
                                      label: 'bbb3'
                                  }
                              ]
                          }
                      ]
                  },
                  {
                      value: 'BBB3',
                      label: 'bbb3'
                  }
              ]
          },
          {
              value: 'BB2',
              label: 'bb2',
              children: [
                  {
                      value: 'AAA4',
                      label: 'aaa4',
                  },
                  {
                      value: 'BBB4',
                      label: 'bbb4'
                  }
              ]
          }
      ]
  },
  {
      value: 'C',
      label: 'c',
  }
]
$('#address').cascader({
    data: data,
    value: ["B", "BB2", "BBB4"],
    success: function (valData,labelData) {
        console.log(valData);
        console.log(labelData);
    }
})
// 组织关系信息初始化
function initOrgRelTable (results) {
    var table = $("#orgRelTable").DataTable({
        'data': results,
        'searching': false,
        'autoWidth': false,
        'ordering': true,
        'initComplete': function (settings, json) {
            console.log(settings, json)
        },
        "scrollY": "375px",
        'columns': [
            { 'data': null, 'title': '序号', 'className': 'row-name' },
            { 'data': "orgName",
              'title': '组织名称',
              'className': 'row-sex',
              // 'render': function (data) {
              //   return data[0].orgTypeName
              // } 
            },
            { 'data': "refName", 'title': '关系类型', 'className': 'user-account' },
            { 'data': "supOrgName", 'title': '上级组织', 'className': 'user-type' },
            { 'data': "createDate", 'title': '添加时间', 'className': 'role-type' }
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
}

// 获取组织基础信息
function getOrg (orgId) {
    $http.get('org/getOrg', {
        orgId: orgId
    }, function (data) {
        console.log(data)
    }, function (err) {
        console.log(err)
    })
}

// 获取组织关系信息
function getOrgRel (orgId) {
    $http.get('orgRel/getOrgRelTypePage', {
        orgId: orgId
    }, function (data) {
        initOrgRelTable(data.records);
    }, function (err) {
        console.log(err)
    })
}

var orgId = getQueryString('id');
getOrg(orgId);
getOrgRel(orgId);

// datatable应用于tab切换出现表头错位
$('#myTabs a').click(function (e) {
  e.preventDefault();
  $(this).tab('show');
  $.fn.dataTable.tables( {visible: true, api: true} ).columns.adjust();
})

function cancel () {
  var url = "list.html?id=" + orgId;
  window.location.href = url;
}