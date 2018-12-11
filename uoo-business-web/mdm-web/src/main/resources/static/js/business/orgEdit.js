var orgId = getQueryString('id');
var orgTreeId = getQueryString('orgTreeId');
var pid = getQueryString('pid');
var orgName = getQueryString('name');
var areaCodeId = ''; //区号ID
var locationList = [];
var orgTypeList;
var orgMartCode; //划小组织编码
var expandovalueVoList; //划小扩展字段
var nodeTypeId;
var areaTypeId;
var countTypeId;
var contractTypeId;
var positionList;
var orgPostList;
var regionList = [];
var areaList = [];
var checkNode;
var selectUser = [];
var formValidate;
var loading = parent.loading;
var toastr = window.top.toastr;
var editSmallField = false;

//字典数据
var scaleData = window.top.dictionaryData.scale();
var cityVillageData = window.top.dictionaryData.cityVillage();
var orgPostLevelData = window.top.dictionaryData.orgPostLevel();
var statusCdData = window.top.dictionaryData.statusCd();
var nodeTypeData = window.top.dictionaryData.nodeType();
var areaTypeData = window.top.dictionaryData.areaType();
var countTypeData = window.top.dictionaryData.countType();
var contractTypeData = window.top.dictionaryData.contractType();

$('.orgName').html(orgName);
parent.getOrgExtInfo();

// lulu ui select插件
seajs.use('/vendors/lulu/js/common/ui/Select', function () {
    $('select').selectMatch();
});

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
  $('#locationList').tagsInput({unique: true});
  $('#orgTypeList').tagsInput({unique: true});
  $('#positionList').tagsInput();
  $('#postList').tagsInput();
  $('#regionId').tagsInput();
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
        content: '/inaction/organization/contactDialog.html?id=' + orgId,
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
        content: '/inaction/organization/typeDialog.html?id=' + orgId,
        btn: ['确认', '取消'],
        yes: function(index, layero){
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            checkNode = iframeWin.checkNode;
            parent.layer.close(index);
            $('#orgTypeList').importTags(checkNode, {unique: true});
            $('.ui-tips-error').css('display', 'none');
            orgTypeList = checkNode;
            //选择组织类别为营销组织类型
            if (orgTypeList.length == 0 && editSmallField) {
                editSmallField = false;
            }
            else {
                for (var i = 0; i < orgTypeList.length; i++) {
                    var isSmallFieldExit = false;
                    if (((orgTypeList[i].orgTypeCode && orgTypeList[i].orgTypeCode.substr(0, 3) == 'N11') ||
                        (orgTypeList[i].extField1 && orgTypeList[i].extField1.substr(0, 3) == 'N11'))) {
                        if (!editSmallField) {
                            var smallTemplate = Handlebars.compile($("#smallTemplate").html());
                            var smallHtml = smallTemplate();
                            $('#small').html(smallHtml);
                            $('#orgMartCode ').val(orgMartCode);
                            editSmallField = true;
                            getNodeType();
                            getAreaType();
                            getCountType();
                            getContractType();
                            return
                        }
                        else {
                            isSmallFieldExit = true;
                            return
                        }
                    }
                    if (i == orgTypeList.length - 1 && !isSmallFieldExit )
                        editSmallField = false;
                }
            }
            if (!editSmallField)
                $('#small').html('');
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
        content: '/inaction/organization/positionDialog.html?id=' + orgId,
        btn: ['确认', '取消'],
        yes: function(index, layero){
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            checkNode = iframeWin.checkNode;
            parent.layer.close(index);
            $('#positionList').importTags(checkNode);
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
        content: '/inaction/organization/postDialog.html',
        btn: ['确认', '取消'],
        yes: function(index, layero){
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            checkNode = iframeWin.checkNode;
            parent.layer.close(index);
            $('#postList').importTags(checkNode);
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
        content: '/inaction/organization/locationDialog.html',
        btn: ['确认', '取消'],
        yes: function(index, layero){
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            checkNode = iframeWin.checkNode;
            parent.layer.close(index);
            $('#locationList').importTags(checkNode, {unique: true});
            $('.ui-tips-error').css('display', 'none');
            locationList = checkNode;
        },
        btn2: function(index, layero){},
        cancel: function(){}
    });
}

//电信管理区域选择
function openRegionDialog() {
    parent.layer.open({
        type: 2,
        title: '电信管理区域',
        shadeClose: true,
        shade: 0.8,
        area: ['50%', '80%'],
        maxmin: true,
        content: '/inaction/organization/regionDialog.html',
        btn: ['确认', '取消'],
        yes: function(index, layero){
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            checkNode = iframeWin.checkNode;
            $('#regionId').importTags(checkNode);
            regionList = checkNode;
            parent.layer.close(index);
            getAreaId(checkNode[0].id);
        },
        btn2: function(index, layero){},
        cancel: function(){}
    });
}

//根据电信管理区域ID获取区号
function getAreaId(regionId) {
    $http.get('/region/commonRegion/getCommonRegion/id='+ regionId, {}, function (data) {
        areaCodeId = data.areaCode.areaCodeId;
        $('#areaCode').val(data.areaCode.areaCode);
    }, function (err) {

    })
}

//根据电信管理区域ID获取区域label
function getAreaLabel() {
    $http.get('/region/commonRegion/getTreeCommonRegion', {}, function (data) {
        for (var i = 0; i < data.length; i++){
            if (areaCodeId == data[i].extParams.areaCodeId) {
                areaList.push(data[i]);
                $('#regionId').importTags(areaList);
                return;
            }
        }
    }, function (err) {

    })
}

//证件信息初始化
function initCredentialTable (results) {
    var table = $("#orgRelTable").DataTable({
        'data': results,
        'searching': false,
        'autoWidth': false,
        'ordering': true,
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
        }
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
            { 'data': "orgBizName", 'title': '组织称谓', 'className': 'user-account' },
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
    var option = '';
    for (var i = 0; i < scaleData.length; i++) {
        var select = orgScale === scaleData[i].itemValue? 'selected' : '';
        option += "<option value='" + scaleData[i].itemValue + "' " + select + ">" + scaleData[i].itemCnname +"</option>";
    }
    $('#orgScale').append(option);
    $('#orgScale').selectMatch();
}

// 获取城乡字典数据
function getCityVillage (cityTown) {
    var option = '';
    for (var i = 0; i < cityVillageData.length; i++) {
        var select = cityTown === cityVillageData[i].itemValue? 'selected' : '';
        option += "<option value='" + cityVillageData[i].itemValue + "' " + select + ">" + cityVillageData[i].itemCnname +"</option>";
    }
    $('#cityTown').append(option);
    $('#cityTown').selectMatch();
}

// 获取组织最高岗位级别字典数据
function getOrgPostLevel (val) {
    var option = '';
    for (var i = 0; i < orgPostLevelData.length; i++) {
        var select = val === orgPostLevelData[i].itemValue? 'selected' : '';
        option += "<option value='" + orgPostLevelData[i].itemValue + "' " + select + ">" + orgPostLevelData[i].itemCnname +"</option>";
    }
    $('#orgPositionLevel').append(option);
    $('#orgPositionLevel').selectMatch();
}

// 获取状态数据
function getStatusCd (statusCd) {
    var option = '';
    for (var i = 0; i < statusCdData.length; i++) {
        var select = statusCd === statusCdData[i].itemValue? 'selected' : '';
        option += "<option value='" + statusCdData[i].itemValue + "' " + select + ">" + statusCdData[i].itemCnname +"</option>";
    }
    $('#statusCd').append(option);
    $('#statusCd').selectMatch();
}

// 获取组织节点类型字典数据
function getNodeType (type) {
    var option = '<option></option>';
    for (var i = 0; i < nodeTypeData.length; i++) {
        var select = type === nodeTypeData[i].itemValue? 'selected' : '';
        option += "<option value='" + nodeTypeData[i].itemValue + "' " + select + ">" + nodeTypeData[i].itemCnname +"</option>";
    }
    $('#nodeType').append(option);
    $('#nodeType').selectMatch();
}

// 获取区域级别字典数据
function getAreaType (type) {
    var option = '<option></option>';
    for (var i = 0; i < areaTypeData.length; i++) {
        var select = type === areaTypeData[i].itemValue? 'selected' : '';
        option += "<option value='" + areaTypeData[i].itemValue + "' " + select + ">" + areaTypeData[i].itemCnname +"</option>";
    }
    $('#areaType').append(option);
    $('#areaType').selectMatch();
}

// 获取统计属性字典数据
function getCountType (type) {
    var option = '<option></option>';
    for (var i = 0; i < countTypeData.length; i++) {
        var select = type === countTypeData[i].itemValue? 'selected' : '';
        option += "<option value='" + countTypeData[i].itemValue + "' " + select + ">" + countTypeData[i].itemCnname +"</option>";
    }
    $('#countType').append(option);
    $('#countType').selectMatch();
}

// 获取承包类型字典数据
function getContractType (type) {
    var option = '<option></option>';
    for (var i = 0; i < contractTypeData.length; i++) {
        var select = type === contractTypeData[i].itemValue? 'selected' : '';
        option += "<option value='" + contractTypeData[i].itemValue + "' " + select + ">" + contractTypeData[i].itemCnname +"</option>";
    }
    $('#contractType').append(option);
    $('#contractType').selectMatch();
}

// 获取组织基础信息
function getOrg (orgId) {
    $http.get('/org/getOrg', {
        orgTreeId: orgTreeId,
        orgId: orgId
    }, function (data) {
        $('#orgName').val(data.orgName).focus();
        $('#orgCode').val(data.orgCode);
        $('#shortName').val(data.shortName);
        $('#fullName').val(data.fullName);
        $('#orgNameEn').val(data.orgNameEn);
        orgMartCode = data.orgMartCode;
        laydate.render({
          elem: '#foundingTime',
          value: new Date(data.foundingTime)
        });
        if (data.psonOrgVoList && data.psonOrgVoList.length > 0) {
            $('#psonOrgVoList').val(data.psonOrgVoList[0].psnName);
        }
        $('#officePhone').val(data.officePhone);
        $('#sort').val(data.sort);
        areaCodeId = data.areaCodeId;
        getAreaLabel();
        $('#areaCode').val(data.areaCode);
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
        selectUser = data.psonOrgVoList;
        $('#locationList').addTag(locationList);
        $('#orgTypeList').addTag(orgTypeList);
        $('#positionList').addTag(positionList);
        $('#postList').addTag(orgPostList);
        expandovalueVoList = data.expandovalueVoList;
        for (var i = 0; i < orgTypeList.length; i++) {
            if (orgTypeList[i].orgTypeCode && orgTypeList[i].orgTypeCode.substr(0, 3) == 'N11') {
                var smallTemplate = Handlebars.compile($("#smallTemplate").html());
                var smallHtml = smallTemplate();
                $('#small').html(smallHtml);
                $('#orgMartCode ').val(orgMartCode);
                editSmallField = true;
                for (var i = 0; i < expandovalueVoList.length; i++) {
                    if (expandovalueVoList[i].columnName == 'nodeType') {
                        getNodeType(expandovalueVoList[i].data);
                        nodeTypeId = expandovalueVoList[i].valueId;
                    }
                    if (expandovalueVoList[i].columnName == 'areaType') {
                        getAreaType(expandovalueVoList[i].data);
                        areaTypeId = expandovalueVoList[i].valueId;
                    }
                    if (expandovalueVoList[i].columnName == 'countType') {
                        getCountType(expandovalueVoList[i].data);
                        countTypeId = expandovalueVoList[i].valueId;
                    }
                    if (expandovalueVoList[i].columnName == 'contractType') {
                        getContractType(expandovalueVoList[i].data);
                        contractTypeId = expandovalueVoList[i].valueId;
                    }
                }
                $('#small').find(':input').each(function () {
                    $(this).hover(function () {
                        formValidate.isPass($(this));
                    });
                });
                // formValidate.isPass($('#smallCode'));
                // formValidate.isPass($('#orgNodeType'));
                // formValidate.isPass($('#areaType'));
                // formValidate.isPass($('#statisticProperty'));
                // formValidate.isPass($('#contractType'));
                return
            }
        }
    }, function (err) {

    })
}

// 获取组织关系信息
function getOrgRel (orgId) {
    $http.get('/orgRel/getOrgRelTypePage', {
        orgTreeId: orgTreeId,
        orgId: orgId,
        pageSize: 10,
        pageNo: 1
    }, function (data) {
        initOrgRelTable(data.records);
    }, function (err) {

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
  if (selectUser && selectUser.length > 0) {
    for (var i = 0; i < selectUser.length; i++) {
        userList.push({personnelId: selectUser[i].personnelId});
    }
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
  var foundDate = $('#foundingTime').val();
  // var foundDate;
  // if (date) {
  //     foundDate = new Date(date).getTime();
  // }
  var orgPositionLevel = $('#orgPositionLevel option:selected') .val();
  var officePhone = $('#officePhone').val();
  var statusCd = $('#statusCd option:selected') .val();
  var sort = $('#sort').val();
  var address = $('#address').val();
  var orgContent = $('#orgContent').val();
  var orgDesc = $('#orgDesc').val();
  //划小扩展字段
  var nodeType = $('#nodeType option:selected') .val();
  var areaType = $('#areaType option:selected') .val();
  var countType = $('#countType option:selected') .val();
  var contractType = $('#contractType option:selected') .val();
  expandovalueVoList = [
    {columnName: 'nodeType', data: nodeType, valueId: nodeTypeId},
    {columnName: 'areaType', data: areaType, valueId: areaTypeId},
    {columnName: 'countType', data: countType, valueId: countTypeId},
    {columnName: 'contractType', data: contractType, valueId: contractTypeId}
  ];
  $http.post('/org/updateOrg', JSON.stringify({
      orgRootId: '1',
      orgTreeId: orgTreeId,
      orgId: orgId,
      supOrgId: pid,
      orgName: orgName,
      shortName: shortName,
      cityTown: cityTown,
      orgScale: orgScale,
      foundingTime: foundDate,
      psonOrgVoList: userList,
      orgNameEn: orgNameEn,
      orgPositionLevel: orgPositionLevel,
      officePhone: officePhone,
      statusCd: statusCd,
      sort: sort,
      areaCodeId: areaCodeId,
      address: address,
      politicalLocationList: location,
      orgTypeList: orgType,
      positionList: position,
      postList: post,
      orgContent: orgContent,
      orgDesc: orgDesc,
      expandovalueVoList: expandovalueVoList
  }), function () {
      parent.changeNodeName(orgId, orgName);
      window.location.replace("list.html?id=" + orgId + '&orgTreeId=' + orgTreeId + '&pid=' + pid + "&name=" + encodeURI(orgName));
      loading.screenMaskDisable('container');
      toastr.success('更新成功！');
  }, function () {
      loading.screenMaskDisable('container');
  })
}

// 删除组织
function deleteOrg () {
    parent.layer.confirm('此操作将删除该组织, 是否继续?', {
        icon: 0,
        title: '提示',
        btn: ['确定','取消']
    }, function(index, layero){
        parent.layer.close(index);
        loading.screenMaskEnable('container');
        $http.get('/org/deleteOrg', {
            orgTreeId: orgTreeId,
            orgId: orgId,
            supOrgId: pid
        }, function () {
            parent.deleteNode(orgId);
            parent.selectRootNode();
            loading.screenMaskDisable('container');
            toastr.success('删除成功！');
        }, function (err) {
            loading.screenMaskDisable('container');
        })
    }, function(){

    });

}

// 取消
function cancel () {
    var url = "list.html?id=" + orgId + "&orgTreeId=" + orgTreeId + "&name=" + encodeURI(orgName);
    window.location.href = url;
}

