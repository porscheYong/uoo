var loading = parent.loading;
var orgFullName;
loading.screenMaskEnable('container');
// 步骤条
$("#wizard").steps({
    headerTag: "h2",
    bodyTag: "form",
    transitionEffect: "slideLeft",
    onFinished: function (event, currentIndex) {
        savePersonnel();
    }
});

// 时间插件
laydate.render({
    elem: '#toWorkTime'
});

// 归属组织信息编辑
function openOrgEdit () {
    //预编译模板
    var orgTemplate = Handlebars.compile($("#orgTemplate").html());
    //匹配json内容
    var orgEditHtml = orgTemplate();
    //输入模板
    $('#orgBelong').html(orgEditHtml);
}
// 取消归属组织信息编辑
function cancelOrgEdit () {
    var orgListTemplate = Handlebars.compile($("#orgListTemplate").html());
    var orgListHtml = orgListTemplate();
    $('#orgBelong').html(orgListHtml);
}

// 获取组织全称
function getOrgFullName() {
    parent.layer.open({
        type: 2,
        title: '选中组织类别',
        shadeClose: true,
        shade: 0.8,
        area: ['50%', '80%'],
        maxmin: true,
        content: 'orgNameDialog.html',
        btn: ['确认', '取消'],
        yes: function(index, layero){
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            checkNode = iframeWin.checkNode;
            parent.layer.close(index);
            $('#orgFullName').val(checkNode.name);
            orgFullName = checkNode;
        },
        btn2: function(index, layero){},
        cancel: function(){}
    });
}

// 新增人员信息
function savePersonnel () {
    loading.screenMaskEnable('container');

    var psnName = $('#psnName').val();
    var gender = $('#gender input[type=radio]:checked').val();
    var certType = $('#certType option:selected') .val();
    var psnNbr = $('#psnNbr').val();
    var certNo = $('#certNo').val();
    var psnCode = $('#psnCode').val();
    var nationality = $('#nationality').val();
    var ncCode = $('#ncCode').val();
    var nation = $('#nation option:selected') .val();
    var date = $('#toWorkTime').val();
    var toWorkTime;
    if (date) {
        toWorkTime = new Date(date).getTime();
    }
    var pliticalStatus = $('#pliticalStatus option:selected') .val();
    var marriage = $('#marriage option:selected') .val();
    var tbMobileVoList = $('#tbMobileVoList').val();
    var tbEamilVoList = $('#tbEamilVoList').val();
    var tbFamilyList = $('#tbFamilyList').val();
    $http.post('/personnel/savePersonnel', JSON.stringify({
        // orgRootId: '1',
        // orgId: orgId,
        psnName: psnName,
        gender: gender,
        certType: certType,
        psnNbr: psnNbr,
        certNo: certNo,
        psnCode: psnCode,
        nationality: nationality,
        ncCode: ncCode,
        nation: nation,
        toWorkTime: toWorkTime,
        pliticalStatus: pliticalStatus,
        marriage: marriage,
        tbMobileVoList: [],
        tbEamilVoList: [],
        tbFamilyList: []
    }), function (data) {

    }, function (err) {

    })
}