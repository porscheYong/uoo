var positionCode = getQueryString('positionCode');  //当前职位id
var positionCodeCur = getQueryString('positionCodeCur');
var posFullName = getQueryString('posFullName');
var posName = getQueryString('posName');
var roleList = [];
var locationList = [];
var pPosList = [];
var positionId;
var logTable;
var toastr = window.top.toastr;
var loading = parent.loading;

function editInfo(){
    $("#posInfo").css("display","none");
    $("#editInfo").css("display","block");
}

function cancel(){
    $("#posInfo").css("display","block");
    $("#editInfo").css("display","none");
}

//获取职位信息
function getPosition(){
    $http.get('/sysPosition/getPosition', {
        positionCode: positionCode
    }, function (data) {
        positionId = data.positionId;
        initPosInfo(data);
        initLogTable(positionId);
    }, function (err) {

    })
}

//初始化职位信息
function initPosInfo(result){
    isNull("posCodeLable",result.positionCode);
    isNull("supPosLable",result.pPositionName);
    isNull("areaLable",result.regionName);
    isNull("sortLable",result.sortNum);
    isNull("notesLable",result.notes);
    $("#stateLable").text("有效");
    roleList = result.sysRoleDTOList;
    for(var i=0;i<roleList.length;i++){ 
        $("#perms").append("<span class='perTab'>"+roleList[i].roleName+"</span>");
    }

    isInputNull("posNameInput",result.positionName);
    $("#posName").html(result.positionName);
    isInputNull("posNum",result.positionCode);
    isInputNull("supPos",result.pPositionName);
    isInputNull("sort",result.sortNum);
    isInputNull("notes",result.notes);
    $('#role').addTag(roleList);
    if(result.regionName){
        locationList = [{"id":result.regionNbr,"name":result.regionName}];
        $('#location').addTag(locationList);
    }
    if(result.pPositionName){
        pPosList = [{"id":result.pPositionId,"name":result.pPositionName}];
        $('#supPos').addTag(pPosList);
    }
}

//判断返回值是否为null
function isNull(el,str){
    if(str != null){
        $("#"+el).text(str);
    }
}

//判断填入input中的值是否为null
function isInputNull(el,str){
    if(str != null){
        $("#"+el).val(str);
    }
}

//初始化更新记录表格
function initLogTable(posId){
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
            param.recordId = posId;
            param.tableName = "SYS_POSITION";
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

//返回
function backToList(){
    window.location.href = "list.html?positionCode="+positionCodeCur+"&posName="+posName;
}

// tags init
if(typeof $.fn.tagsInput !== 'undefined'){
    $('#role').tagsInput();
    $('#location').tagsInput();
    $('#supPos').tagsInput();
  }

//角色选择
function openTypeDialog() {
    parent.layer.open({
        type: 2,
        title: '选择角色',
        shadeClose: true,
        shade: 0.8,
        area: ['70%', '85%'],
        maxmin: true,
        content: 'roleDialog.html',
        btn: ['确认', '取消'],
        yes: function(index, layero){
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            // var checkRole = iframeWin.checkRole;
            var checkNode = iframeWin.checkNode;
            $('#role').importTags(checkNode);
            // $('#role',window.parent.document).importTags(checkNode);
            $('.ui-tips-error').css('display', 'none');
            // window.localStorage.setItem('userRoleList',JSON.stringify(checkRole));
            roleList = checkNode;
            parent.layer.close(index);
        },
        btn2: function(index, layero){},
        cancel: function(){}
    });
}

//管理区域选择
function openLocationDialog() {
    parent.layer.open({
        type: 2,
        title: '管理区域',
        shadeClose: true,
        shade: 0.8,
        area: ['50%', '80%'],
        maxmin: true,
        content: 'locationDialog.html',
        btn: ['确认', '取消'],
        yes: function(index, layero){
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            var checkNode = iframeWin.checkNode;
            $('#location').importTags(checkNode, {unique: true});
            $('.ui-tips-error').css('display', 'none');
            locationList = checkNode;
            parent.layer.close(index);
        },
        btn2: function(index, layero){},
        cancel: function(){}
    });
}

//上级职位选择
function openParPosDialog() {
    parent.layer.open({
        type: 2,
        title: '上级职位选择',
        shadeClose: true,
        shade: 0.8,
        area: ['50%', '80%'],
        maxmin: true,
        content: 'parPosDialog.html',
        btn: ['确认', '取消'],
        yes: function(index, layero){
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            var checkNode = iframeWin.checkNode;
            if(checkNode[0].id == positionCode){
                toastr.error("不能选择当前职位为上级职位!");
            }else{
                $('#supPos').importTags(checkNode, {unique: true});
                $('.ui-tips-error').css('display', 'none');
                pPosList = checkNode;
            }
            parent.layer.close(index);
        },
        btn2: function(index, layero){},
        cancel: function(){}
    });
}

//更新职位
function updatePosition(){
    var roleIdList = [];
    if(locationList.length != 0){
        var lc = locationList[0].id;
    }
    if(pPosList.length != 0){
        var pPos = pPosList[0].id;
    }

    for(var i=0;i<roleList.length;i++){
        roleIdList.push({"roleId":roleList[i].roleId || roleList[i].id});
    }
    $http.post('/sysPosition/updatePosition', JSON.stringify({  
        notes : $("#notes").val(),
        parentPositionCode : pPos,
        positionId : positionId,
        regionNbr : lc,
        statusCd : $("#state").val(),
        sortNum : $("#sort").val(),
        sysRoleDTOList : roleIdList,
        positionName : $("#posNameInput").val(),
        positionCode : $("#posNum").val()
    }), function (message) {
        backToList();
        parent.initPosRelTree();
        toastr.success("保存成功！");
    }, function (err) {
        // toastr.error("保存失败！");
    })
}

//删除职位
function deletePosition(){ 
    parent.layer.confirm('是否删除该职位？', {
        icon: 0,
        title: '提示',
        btn: ['确定','取消']
        }, function(index, layero){
            $http.post('/sysPosition/deletePosition', JSON.stringify({  
                positionId : positionId
            }), function (message) {
                backToList();
                parent.initPosRelTree();
                toastr.success("删除成功！");
                parent.layer.close(index);
            }, function (err) {
                toastr.error("删除失败！");
                parent.layer.close(index);
            })
        }, function(){
      
        });
}

$("#role_tagsinput").on('click',function(){
    openTypeDialog();
})

// $(".breadcrumb").html(posFullName);
getPosition();