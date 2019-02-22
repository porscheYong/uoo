var roleId = getQueryString('roleId');
var roleCode = getQueryString('roleCode');
var locationList = [];
var parRoleList = [];
var permList = [];
var parRoleCode = "";
var locationCode;
var toastr = window.top.toastr;
var formValidate;
var loading = parent.loading;

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
            $('#parRole').importTags(checkNode, {unique: true});
            $('.ui-tips-error').css('display', 'none');
            $("#parRole_tagsinput").removeClass("not_valid");
            parRoleList = checkNode;
            if(checkNode.length != 0){
                parRoleCode = checkNode[0].extField1;
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

//新增角色
function addRole(){
    loading.screenMaskEnable('container');
    if(!formValidate.isAllPass()){
        loading.screenMaskDisable('container');
        return;
    }

    var permCodeString = "";
    for(var i=0;i<permList.length;i++){
        permCodeString += permList[i].code + ",";
    }
    permCodeString = permCodeString.substring(0,permCodeString.length-1);
    $http.post('/system/sysRole/add', JSON.stringify({  
        notes : $("#notes").val(),
        parentRoleCode : parRoleCode,
        permissionCodes : permCodeString,
        regionNbr : locationCode,
        statusCd : $("#statusCd").val(),
        sortNum : $("#sortNum").val(),
        roleCode : $("#roleCode").val(),
        roleName : $("#roleName").val()
    }), function (message) {
        loading.screenMaskDisable('container');
        backToList();
        parent.initRoleRelTree();
        toastr.success("创建成功！");
    }, function (err) {
        loading.screenMaskDisable('container');
        // toastr.error("保存失败！");
    })
}

function backToList(){
    window.location.href = "list.html?roleId="+roleId+"&roleCode="+roleCode;
}