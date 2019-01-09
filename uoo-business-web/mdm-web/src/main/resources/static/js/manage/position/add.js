var positionId = getQueryString('positionId');
var posFullName = getQueryString('posFullName');
var posName = getQueryString('posName');
var roleList = [];
var locationList = [];
var pPosList = [];
var toastr = window.top.toastr;

// locationList = [{"id":"","name":""}];
// pPosList = [{"id":"","name":""}];

//返回
function backToList(){
    window.location.href = "list.html?posName=" + posName + "&posFullName=" + posFullName + "&positionId=" + positionId;
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
            $('#supPos').importTags(checkNode, {unique: true});
            $('.ui-tips-error').css('display', 'none');
            pPosList = checkNode;
            parent.layer.close(index);
        },
        btn2: function(index, layero){},
        cancel: function(){}
    });
}

//创建职位
function addPosition(){
    var roleIdList = [];
    for(var i=0;i<roleList.length;i++){
        roleIdList.push({"roleId":roleList[i].id});
    }
    if(locationList.length != 0){
        var lc = locationList[0].id;
    }
    if(pPosList.length != 0){
        var pPos = pPosList[0].id;
    }
    $http.post('/sysPosition/addPosition', JSON.stringify({  
        notes : $("#notes").val(),
        pPositionId : pPos,
        regionNbr : lc,
        statusCd : $("#state").val(),
        sortNum : $("#sort").val(),
        sysRoleDTOList : roleIdList,
        positionName : $("#posNameInput").val(),
        positionCode : $("#posNum").val()
    }), function (message) {
        backToList();
        parent.initPosRelTree();
        toastr.success("创建成功！");
    }, function (err) {
        // toastr.error("保存失败！");
    })
}