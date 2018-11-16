var orgId = getQueryString('id');
var orgName = getQueryString('name');

$('#orgName').html(orgName);

// lulu ui select插件
seajs.use('/vendors/lulu/js/common/ui/Select', function () {
  $('select').selectMatch();
})

// tags init
if(typeof $.fn.tagsInput !== 'undefined'){  
  $('#orgTypeList').tagsInput();
}

$('#orgTypeList_tagsinput').on('click', function() {
    var dialog =$("#myModal",parent.document)
    parent.layerOpen({
      type: 1,
      title: '选中组织类别',
      shadeClose: true,
      shade: 0.8,
      area: ['70%', '85%'],
      maxmin: true,
      content: dialog,
      success: function(layero, index){
        initOrgTypeTree();
      },
      btn: ['确认', '取消'],
      confirm: function(index, layero){
        alert(1)
      }
    })
    // layer.open({
    //   type: 1,
    //   title: '选中组织类别',
    //   shadeClose: true,
    //   shade: 0.8,
    //   area: ['70%', '85%'],
    //   maxmin: true,
    //   content: dialog,
    //   success: function(layero, index){
    //     initOrgTree()
    //   },
    //   btn: ['确认', '取消'],
    //   yes: function(index, layero){
    //     alert(1)
    //   },
    //   btn2: function(index, layero){},
    //   cancel: function(){}
    // });
})

// 组织类别初始化
function initOrgTypeTree (orgId) {
  var setting = {
    view: {
        showLine: false,
        showIcon: false,
        dblClickExpand: false
    },
    data: {
        // key: {
        //     name: "label",
        //     isParent: "leaf",
        // },
        simpleData: {
            enable:true,
            idKey: "id",
            pIdKey: "pId",
            rootPId: ""
        }
    },
    // callback: {
    //     onClick: onNodeClick,
    //     onCheck: onNodeCheck
    // },
    check: {
        enable: true,
        chkStyle: 'checkbox',
        chkboxType: { "Y": "", "N": "" }
    }
  };
  $http.get('http://134.96.253.221:11100/orgType/getFullOrgTypeTree', null, function (data) {
      console.log(data)
      // $.fn.zTree.init($("#standardTree"), setting, data);
      // var zTree = $.fn.zTree.getZTreeObj("standardTree");
      // var nodes = zTree.getNodes();
      // zTree.expandNode(nodes[0], true);
      // zTree.selectNode(nodes[0], true);
      // onNodeClick(null, null, nodes[0]);
      parent.postMessage(data);
  }, function (err) {
      console.log(err)
  })
}

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

var orgScale;

// 获取城乡字典数据
function getCityVillage () {
    $http.get('http://134.96.253.221:11500/tbDictionaryItem/getList/CITY_VILLAGE', {}, function (data) {
        console.log(data)
    }, function (err) {
        console.log(err)
    })
}
getCityVillage()

// 获取组织基础信息
function getOrg (orgId) {
    $http.get('http://134.96.253.221:11100/org/getOrg', {
        orgId: orgId
    }, function (data) {
        $('#orgName').val(data.orgName).focus();
        laydate.render({
          elem: '#createDate',
          value: new Date(data.createDate)
        });
        // $('#createDate').val(getDate(data.createDate));
        var cityVillage = [{"itemId":1000,"dictionaryId":1000,"parItemId":null,"itemValue":"1","itemCnname":"城市","sort":0,"statusCd":"1000","createDate":1542006293000,"createUser":0,"updateDate":1542006293000,"updateUser":0,"statusDate":1542006293000},
                   {"itemId":1001,"dictionaryId":1000,"parItemId":null,"itemValue":"2","itemCnname":"农村","sort":0,"statusCd":"1000","createDate":1542006293000,"createUser":0,"updateDate":1542006293000,"updateUser":0,"statusDate":1542006293000}];
        var option;
        var cityTown = '1';
        for (var i = 0; i < cityVillage.length; i++) {
          var select = cityTown === cityVillage[i].itemValue? 'selected' : '';
          option = "<option value='" + cityVillage[i].itemValue + "' " + select + ">" + cityVillage[i].itemCnname +"</option>";
          $('#cityTown').append(option);
        }
        $('#cityTown').selectMatch();
        parent.postMessage(data.orgTypeList, '*');
    }, function (err) {
        console.log(err)
    })
}

// 获取组织关系信息
function getOrgRel (orgId) {
    $http.get('http://134.96.253.221:11100/orgRel/getOrgRelTypePage', {
        orgId: orgId
    }, function (data) {
        initOrgRelTable(data.records);
    }, function (err) {
        console.log(err)
    })
}

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

// 更新组织信息
function updateOrg () {
  var orgId = orgId;
  var orgName = $('#orgName').val();
  var shortName = $('#shortName').val();
  var orgNameEn = $('#orgNameEn').val();
  var cityTown = $("#cityTown").val();
  var date = $('#createDate').val();
  var time;
  if (date) {
    time = new Date(createDate).getTime();
  }
  var createDate = time
  var locId = $('#locId').val();
  var orgPositionLevel = $('#orgPositionLevel').val();
  var officePhone = $('#officePhone').val();
  var address = $('#address').val();
}

$('#saveBtn').on('click', function () {
    seajs.use('/vendors/lulu/js/common/ui/Validate', function (Validate) {
        var immediateForm = $('#immediateForm');
        var immediateValidate = new Validate(immediateForm);
        immediateValidate.immediate();
    })

  var time = new Date($('#createDate').val()).getTime();
  console.log(time)
})
