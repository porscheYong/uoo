var orgId = getQueryString('id');
var orgName = getQueryString('name');
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
$('#orgName').focus();

// lulu ui select插件
seajs.use('/vendors/lulu/js/common/ui/Select', function () {
    $('select').selectMatch();
})

seajs.use('/vendors/lulu/js/common/ui/Validate', function (Validate) {
    var orgAddForm = $('#orgAddForm');
    var formValidate = new Validate(orgAddForm);
    formValidate.immediate();
    orgAddForm.find(':input').each(function () {
        $(this).hover(function () {
            formValidate.isPass($(this));
        });
    })
})

// 选中联系人
function personChoose () {
    var dialog =$("#myModal",parent.document)
    parent.layerOpen({
        type: 1,
        title: '选择联系人',
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
}

