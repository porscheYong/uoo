var orgId = getQueryString('id');
var pid = getQueryString('pid');
var orgName = getQueryString('name');
var locationList;
var orgTypeList;
var positionList;
var orgPostList;
var checkNode;
var selectUser = [];
var formValidate;
var loading = parent.loading;

// var toastr = parent.parent.toastr;
// toastr.success('提交成功！')

// 获取组织完整路径
function getOrgExtInfo () {
    var pathArry = parent.nodeArr;
    console.log(pathArry)
    var pathStr = '';
    for (var i = pathArry.length - 1; i >= 0; i--) {
        if (i === 0) {
            pathStr +=  '<span class="breadcrumb-item"><a href="javascript:viod(0);">' + pathArry[i] + '</a></span>';
        } else {
            pathStr += '<span class="breadcrumb-item"><a href="javascript:viod(0);">' + pathArry[i] + '</a><span class="breadcrumb-separator" style="margin: 0 9px;">/</span></span>';
        }
    }
    $('.breadcrumb').html(pathStr);
}
$('.orgName').html(orgName);
getOrgExtInfo();

// lulu ui select插件
seajs.use('/vendors/lulu/js/common/ui/Select', function () {

})

seajs.use('/vendors/lulu/js/common/ui/Validate', function (Validate) {
    var orgEditForm = $('#orgEditForm');
    formValidate = new Validate(orgEditForm);
    formValidate.immediate();
    orgEditForm.find(':input').each(function () {
        $(this).hover(function () {
            // if (!blurForm.data('immediate')) {
            //     $.validate.focusable = 0;
            formValidate.isPass($(this));
            // }
        });
    });
    // $('#postList').hover(function () {
    //     formValidate.isPass($(this));
    // })
})

// tags init
if(typeof $.fn.tagsInput !== 'undefined'){
  $('#locationList').tagsInput();
  $('#orgTypeList').tagsInput();
  $('#positionList').tagsInput();
  $('#postList').tagsInput();
}

//联系人选择
function openContactDialog() {
    parent.layer.open({
        type: 2,
        title: '联系人',
        shadeClose: true,
        shade: 0.8,
        area: ['70%', '85%'],
        maxmin: true,
        content: 'contactDialog.html?id=' + orgId,
        btn: ['确认', '取消'],
        yes: function(index, layero){
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            checkNode = iframeWin.checkNode;
            var selectObj = iframeWin.getSelectUser();
            if (selectObj.length > 0) {
                selectUser = selectObj;
                $('#psonOrgVoList').val(selectUser[0].psnName);
            }
            parent.layer.close(index);
        },
        btn2: function(index, layero){},
        cancel: function(){}
    });
}

//组织类别选择
function openTypeDialog() {
    parent.layer.open({
        type: 2,
        title: '选中组织类别',
        shadeClose: true,
        shade: 0.8,
        area: ['70%', '85%'],
        maxmin: true,
        content: 'typeDialog.html?id=' + orgId,
        btn: ['确认', '取消'],
        yes: function(index, layero){
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            checkNode = iframeWin.checkNode;
            parent.layer.close(index);
            $('#orgTypeList').importTags(checkNode);
            $('.ui-tips-error').css('display', 'none');
            orgTypeList = checkNode;
        },
        btn2: function(index, layero){},
        cancel: function(){}
    });
}

//组织岗位选择
function openPositionDialog() {
    parent.layer.open({
        type: 2,
        title: '选中岗位职位',
        shadeClose: true,
        shade: 0.8,
        area: ['50%', '80%'],
        maxmin: true,
        content: 'positionDialog.html?id=' + orgId,
        btn: ['确认', '取消'],
        yes: function(index, layero){
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            checkNode = iframeWin.checkNode;
            parent.layer.close(index);
            $('#positionList').importTags(checkNode);
            $('.ui-tips-error').css('display', 'none');
            positionList = checkNode;
        },
        btn2: function(index, layero){},
        cancel: function(){}
    });
}

//组织职位选择
function openPostDialog() {
    parent.layer.open({
        type: 2,
        title: '组织职位',
        shadeClose: true,
        shade: 0.8,
        area: ['50%', '80%'],
        maxmin: true,
        content: 'postDialog.html',
        btn: ['确认', '取消'],
        yes: function(index, layero){
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            checkNode = iframeWin.checkNode;
            parent.layer.close(index);
            $('#postList').importTags(checkNode);
            //TODO 防止选中标签显示错误提示
            $('.ui-tips-error').css('display', 'none');
            orgPostList = checkNode;
        },
        btn2: function(index, layero){},
        cancel: function(){}
    });
}

//行政管理区域选择
function openLocationDialog() {
    parent.layer.open({
        type: 2,
        title: '行政管理区域',
        shadeClose: true,
        shade: 0.8,
        area: ['50%', '80%'],
        maxmin: true,
        content: 'locationDialog.html',
        btn: ['确认', '取消'],
        yes: function(index, layero){
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            checkNode = iframeWin.checkNode;
            parent.layer.close(index);
            $('#locationList').importTags(checkNode);
            $('.ui-tips-error').css('display', 'none');
            locationList = checkNode;
        },
        btn2: function(index, layero){},
        cancel: function(){}
    });
}

//证件信息初始化
function initCredentialTable (results) {
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

// 获取规模字典数据
function getScale (orgScale) {
    $http.get('/tbDictionaryItem/getList/SCALE', {}, function (data) {
        var option = '';
        for (var i = 0; i < data.length; i++) {
            var select = orgScale === data[i].itemValue? 'selected' : '';
            option += "<option value='" + data[i].itemValue + "' " + select + ">" + data[i].itemCnname +"</option>";
        }
        $('#orgScale').append(option);
        $('#orgScale').selectMatch();
    }, function (err) {
        console.log(err)
    })
}

// 获取城乡字典数据
function getCityVillage (cityTown) {
    $http.get('/tbDictionaryItem/getList/CITY_VILLAGE', {}, function (data) {
        var option = '';
        for (var i = 0; i < data.length; i++) {
          var select = cityTown === data[i].itemValue? 'selected' : '';
          option += "<option value='" + data[i].itemValue + "' " + select + ">" + data[i].itemCnname +"</option>";
        }
        $('#cityTown').append(option);
        $('#cityTown').selectMatch();
    }, function (err) {
        console.log(err)
    })
}

// 获取组织最高岗位级别字典数据
function getOrgPostLevel (orgPositionLevel) {
    $http.get('/tbDictionaryItem/getList/ORG_POST_LEVEL', {}, function (data) {
        var option = '';
        for (var i = 0; i < data.length; i++) {
            var select = orgPositionLevel === data[i].itemValue? 'selected' : '';
            option += "<option value='" + data[i].itemValue + "' " + select + ">" + data[i].itemCnname +"</option>";
        }
        $('#orgPositionLevel').append(option);
        $('#orgPositionLevel').selectMatch();
    }, function (err) {
        console.log(err)
    })
}

// 获取状态数据
function getStatusCd (statusCd) {
    // var statusArry = [{"itemValue":"1000","itemCnname":"有效"},
    //                    {"itemValue":"1100","itemCnname":"无效"}];
    // var option = '';
    // for (var i = 0; i < statusArry.length; i++) {
    //     var select = statusCd === statusArry[i].itemValue? 'selected' : '';
    //     option += "<option value='" + statusArry[i].itemValue + "' " + select + ">" + statusArry[i].itemCnname +"</option>";
    // }
    // $('#statusCd').append(option);
    // $('#statusCd').selectMatch();
    $http.get('/tbDictionaryItem/getList/STATUS_CD', {}, function (data) {
        var option = '';
        for (var i = 0; i < data.length; i++) {
            var select = statusCd === data[i].itemValue? 'selected' : '';
            option += "<option value='" + data[i].itemValue + "' " + select + ">" + data[i].itemCnname +"</option>";
        }
        $('#statusCd').append(option);
        $('#statusCd').selectMatch();
    }, function (err) {
        console.log(err)
    })
}

// 获取组织基础信息
function getOrg (orgId) {
    $http.get('/org/getOrg', {
        orgTreeId: '1',
        orgId: orgId
    }, function (data) {
        $('#orgName').val(data.orgName).focus();
        $('#orgCode').val(data.orgCode);
        $('#shortName').val(data.shortName);
        $('#fullName').val(data.fullName);
        $('#orgMartCode ').val(data.orgMartCode);
        $('#orgNameEn').val(data.orgNameEn);
        laydate.render({
          elem: '#createDate',
          value: new Date(data.createDate)
        });
        // $('#psonOrgVoList').val(data.psonOrgVoList[0].name);
        $('#officePhone').val(data.officePhone);
        $('#sort').val(data.sort);
        $('#address').val(data.address);
        $('#orgContent').val(data.orgContent);
        $('#orgDesc').val(data.orgDesc);

        getScale(data.orgScale);
        getCityVillage(data.cityTown);
        getOrgPostLevel(data.orgPositionLevel);
        getStatusCd(data.statusCd);
        locationList = data.politicalLocationList;
        orgTypeList = data.orgTypeList;
        positionList = data.positionList;
        orgPostList = data.postList;
        //TODO id?
        $('#locationList').addTag(locationList);
        $('#orgTypeList').addTag(orgTypeList);
        $('#positionList').addTag(positionList);
        $('#postList').addTag(orgPostList);
    }, function (err) {
        console.log(err)
    })
}

// 获取组织关系信息
function getOrgRel (orgId) {
    $http.get('/orgRel/getOrgRelTypePage', {
        orgTreeId: '1',
        orgId: orgId,
        pageSize: 10,
        pageNo: 1
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

// 更新组织信息
function updateOrg () {
  if (!formValidate.isAllPass())
      return;
  loading.screenMaskEnable('container');
  var userList = [];
  var location = [];
  var position = [];
  var post = [];
  var orgType = [];
  //联系人
  for (var i = 0; i < selectUser.length; i++) {
      userList.push({personnelId: selectUser[i].personnelId});
  }
    //行政管理区域
    for (var i = 0; i < locationList.length; i++) {
        var locId = locationList[i].locId || locationList[i].id;
        location.push({locId: locId});
    }
    //组织岗位
    for (var i = 0; i < positionList.length; i++) {
        position.push({positionId: positionList[i].positionId});
    }
    //组织职位
    for (var i = 0; i < orgPostList.length; i++) {
        post.push({postId: orgPostList[i].postId});
    }
  //组织类别
  for (var i = 0; i < orgTypeList.length; i++) {
      var orgTypeId = orgTypeList[i].orgTypeId || orgTypeList[i].id;
      orgType.push({orgTypeId: orgTypeId});
  }
  var orgName = $('#orgName').val();
  var orgScale = $('#orgScale option:selected') .val();
  var shortName = $('#shortName').val();
  var orgNameEn = $('#orgNameEn').val();
  var cityTown = $('#cityTown option:selected') .val();
  var date = $('#createDate').val();
  var createDate;
  if (date) {
      createDate = new Date(date).getTime();
  }
  var orgPositionLevel = $('#orgPositionLevel option:selected') .val();
  var officePhone = $('#officePhone').val();
  var statusCd = $('#statusCd option:selected') .val();
  var sort = $('#sort').val();
  var address = $('#address').val();
  var orgContent = $('#orgContent').val();
  var orgDesc = $('#orgDesc').val();
  $http.post('/org/updateOrg', JSON.stringify({
      orgRootId: '1',
      orgTreeId: '1',
      orgId: orgId,
      supOrgId: pid,
      orgName: orgName,
      shortName: shortName,
      cityTown: cityTown,
      orgScale: orgScale,
      createDate: createDate,
      psonOrgVoList: userList,
      orgNameEn: orgNameEn,
      orgPositionLevel: orgPositionLevel,
      officePhone: officePhone,
      statusCd: statusCd,
      sort: sort,
      address: address,
      politicalLocationList: location,
      orgTypeList: orgType,
      positionList: position,
      postList: post,
      orgContent: orgContent,
      orgDesc: orgDesc
  }), function () {
      parent.changeNodeName(orgId, orgName);
      window.location.replace("list.html?id=" + orgId + '&pid=' + pid + "&name=" + encodeURI(orgName));
      loading.screenMaskDisable('container');
  }, function (err) {

  })
}

// 删除组织
function deleteOrg () {
    loading.screenMaskEnable('container');
    $http.get('/org/deleteOrg', {
        orgTreeId: '1',
        orgId: orgId,
        supOrgId: pid
    }, function () {
        parent.deleteNode(orgId);
        parent.selectRootNode();
        loading.screenMaskDisable('container');
    }, function (err) {
        console.log(err);
        loading.screenMaskDisable('container');
    })
}

// 取消
function cancel () {
    var url = "list.html?id=" + orgId + "&name=" + orgName;
    window.location.href = url;
}

// $('#saveBtn').on('click', function () {
//     // seajs.use('/vendors/lulu/js/common/ui/Validate', function (Validate) {
//     //     var blurForm = $('#blurForm');
//     //     var blurValidate = new Validate(blurForm);
//     //     blurForm.find(':input').each(function () {
//     //         $(this).blur(function () {
//     //             if (!blurForm.data('immediate')) {
//     //                 $.validate.focusable = 0;
//     //                 blurValidate.isPass($(this));
//     //             }
//     //         });
//     //     })
//     // })
//     // if (formValidate.isAllPass())
//     //     alert(123)
//     //TODO 修改组织类型validate
//     console.log(formValidate.isAllPass())
//   var time = new Date($('#createDate').val()).getTime();
//     var statusCd = $('#statusCd option:selected') .val();
//   console.log(time)
// })
