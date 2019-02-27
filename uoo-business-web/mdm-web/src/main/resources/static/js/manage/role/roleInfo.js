var id = getQueryString('id');
var roleId = getQueryString('roleId');
var roleCode = getQueryString('roleCode');
var locationList = [];
var parRoleList = [];
var permList = [];
var permNameList = [];
var permIdList = [];
var permCodeList = [];
var parRoleCode = "";
var locationCode;
var formValidate;
var toastr = window.top.toastr;
var loading = parent.loading;

loading.screenMaskEnable('container');

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

//获取角色信息
function getRoleInfo(){
    $http.get('/system/sysRole/get/'+id, {
    }, function (data) {
        $("#roleNameTitle").html(data.roleName);
        initRoleInfo(data);
        initLogTable(id);
    }, function (err) {
        // loading.screenMaskDisable('container');
    });
}

//初始化角色信息
function initRoleInfo(result){
    $("#perms").empty();
    isNull("roleCodeLable",result.roleCode);
    isNull("parRoleLable",result.parentRoleName);
    isNull("areaLable",result.commonRegionName);
    isNull("remarkLable",result.notes);
    isNull("sortLable",result.sortNum);
    $("#stateLable").text("有效");
    if(result.permissionNames){
        permNameList = result.permissionNames.split(',');
        permIdList = result.permissionIds.split(',');
        permCodeList = result.permissionCodes.split(',');
    }
    for(var i=0;i<permNameList.length;i++){ 
        $("#perms").append("<span class='perTab'>"+permNameList[i]+"</span>");
        permList.push({"id":permIdList[i],"name":permNameList[i],"code":permCodeList});
    }
    isInputNull("roleName",result.roleName);
    isInputNull("roleCode",result.roleCode);
    isInputNull("sortNum",result.sortNum);
    isInputNull("notes",result.notes);
    $('#perm').addTag(permList);
    if(result.commonRegionName){
        locationCode = result.regionNbr;
        locationList = [{"id":result.locId,"name":result.commonRegionName}];
        $('#location').addTag(locationList);
    }
    if(result.parentRoleName){
        parRoleCode = result.parentRoleCode;
        parRoleList = [{"id":result.parentRoleId,"name":result.parentRoleName,"extField1":parRoleCode}];
        $('#parRole').addTag(parRoleList);
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

function editInfo(){
    $("#roleInfo").css("display","none");
    $("#editInfo").css("display","block");
}

function cancel(){
    $("#roleInfo").css("display","block");
    $("#editInfo").css("display","none");
}

//初始化更新记录表格
function initLogTable(roleId){
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
                    return formatDateTime(row.createDate);
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
            param.recordId = roleId;
            param.tableName = "SYS_ROLE";
            $http.get('/public/modifyHistory/listByRecord', param, function (result) {
                var returnData = {};
                // returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                returnData.recordsTotal = result.total;//返回数据全部记录
                returnData.recordsFiltered = result.total;//后台不实现过滤功能，每次查询均视作全部结果
                returnData.data = result.records;//返回的数据列表
                //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                callback(returnData);
                loading.screenMaskDisable('container');
            }, function (err) {
                loading.screenMaskDisable('container');
            })
        }
    });
}

// tags init
if(typeof $.fn.tagsInput !== 'undefined'){
    $('#parRole').tagsInput();
    $('#location').tagsInput();
    $('#perm').tagsInput();
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
        content: '/inaction/manage/locationDialog.html',
        btn: ['确认', '取消'],
        yes: function(index, layero){
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            var checkNode = iframeWin.checkNode;
            $('#location').importTags(checkNode, {unique: true});
            $('.ui-tips-error').css('display', 'none');
            locationList = checkNode;
            if(checkNode.length != 0){
                locationCode = checkNode[0].extParams.locCode;
            }
            parent.layer.close(index);
        },
        btn2: function(index, layero){},
        cancel: function(){}
    });
}

//上级角色选择
function openParRoleDialog() {
    parent.layer.open({
        type: 2,
        title: '选择上级角色',
        shadeClose: true,
        shade: 0.8,
        area: ['50%', '80%'],
        maxmin: true,
        content: 'parRoleDialog.html',
        btn: ['确认', '取消'],
        yes: function(index, layero){
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            var checkNode = iframeWin.checkNode;
            if(checkNode.length !=0 && checkNode[0].id == id){
                toastr.error("不能选择当前角色为上级角色!");
                // parRoleCode = checkNode[0].extField1;
            }else{
                $('#parRole').importTags(checkNode, {unique: true});
                $('.ui-tips-error').css('display', 'none');
                $("#parRole_tagsinput").removeClass("not_valid");
                parRoleList = checkNode;
                // parRoleCode = "";
            }
            parent.layer.close(index);
        },
        btn2: function(index, layero){},
        cancel: function(){}
    });
}

//权限选择
function openPermDialog() {
    parent.layer.open({
        type: 2,
        title: '选择权限',
        shadeClose: true,
        shade: 0.8,
        area: ['60%', '80%'],
        maxmin: true,
        content: 'permDialog.html',
        btn: ['确认', '取消'],
        yes: function(index, layero){
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            var checkNode = iframeWin.checkNode;
            $('#perm').importTags(checkNode, {unique: true});
            $('.ui-tips-error').css('display', 'none');
            permList = checkNode;
            parent.layer.close(index);
        },
        btn2: function(index, layero){},
        cancel: function(){}
    });
}

//更新角色
function updateRole(){
    loading.screenMaskEnable('container');
    if(!formValidate.isAllPass()){
        loading.screenMaskDisable('container');
        return;
    }

    var pRole = "";
    var permCodeString = "";
    for(var i=0;i<permList.length;i++){
        permCodeString += permList[i].code + ",";
    }

    if(parRoleList.length != 0){
        pRole = parRoleList[0].extField1;
    }

    $http.post('/system/sysRole/update', JSON.stringify({  
        notes : $("#notes").val(),
        parentRoleCode : pRole,
        permissionCodes : permCodeString,
        regionNbr : locationCode,
        statusCd : $("#statusCd").val(),
        sortNum : $("#sortNum").val(),
        roleCode : $("#roleCode").val(),
        roleName : $("#roleName").val(),
        roleId : id
    }), function (message) {
        loading.screenMaskDisable('container');
        backToList();
        parent.initRoleRelTree();
        toastr.success("保存成功！");
    }, function (err) {
        loading.screenMaskDisable('container');
        // toastr.error("保存失败！");
    })
}

//删除角色
function deleteRole(){ 
    parent.layer.confirm('是否删除该角色？', {
        icon: 0,
        title: '提示',
        btn: ['确定','取消']
        }, function(index, layero){
            loading.screenMaskEnable('container');
            $http.post('/system/sysRole/delete', JSON.stringify({  
                roleId : id
            }), function (message) {
                parent.layer.close(index);
                loading.screenMaskDisable('container');
                backToList();
                parent.initRoleRelTree();
                toastr.success("删除成功！");
            }, function (err) {
                loading.screenMaskDisable('container');
                parent.layer.close(index);
                // toastr.error("删除失败！");
            })
        }, function(){
      
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
    window.location.href = "list.html?roleCode="+roleCode+"&roleId="+roleId;
}

$(".breadcrumb").html(parent.getRoleExtInfo(0));
getRoleInfo();