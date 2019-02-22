var orgId = getQueryString('id'); //组织ID
var orgName = getQueryString('name');
var orgFlag = ~~getQueryString('orgFlag');
var sysUserDeptPositionVos = [];
var psonOrgVoList = [],
    orgList = [],
    postList = [];
var editFlag = 0; //编辑新增标志
var orgTable;
var formValidate;
var orgValidate;
var loading = parent.loading;
var toastr = window.top.toastr;
var psnImageId;

//字典数据
var certTypeData = window.top.dictionaryData.certType();
var statusCdData = window.top.dictionaryData.statusCd();
var acctLevelData = window.top.dictionaryData.acctLevel();
var genderData = window.top.dictionaryData.gender();

// 获取组织完整路径
function getOrgExtInfo () {
    var pathArry = parent.nodeArr;
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

// 获取证件类型字典数据
function getCertType () {
    var option = '';
    for (var i = 0; i < certTypeData.length; i++) {
        option += "<option value='" + certTypeData[i].itemValue + "'>" + certTypeData[i].itemCnname +"</option>";
    }
    $('#certType').append(option);
    seajs.use('/vendors/lulu/js/common/ui/Select', function () {
        $('#certType').selectMatch();
    });
}

// 获取状态数据
function getStatusCd (statusCd) {
    var option = '';
    for (var i = 0; i < statusCdData.length; i++) {
        var select = statusCd === statusCdData[i].itemValue? 'selected' : '';
        option += "<option value='" + statusCdData[i].itemValue + "' " + select +">" + statusCdData[i].itemCnname +"</option>";
    }
    $('#statusCd').append(option);
}

// 获取账号等级字典数据
function getAcctLevel () {
    var option = '';
    for (var i = 0; i < acctLevelData.length; i++) {
        option += "<option value='" + acctLevelData[i].itemValue + "'>" + acctLevelData[i].itemCnname +"</option>";
    }
    $('#acctLevel').append(option);
    seajs.use('/vendors/lulu/js/common/ui/Select', function () {
        $('#acctLevel').selectMatch();
    });
}

// 获取性别字典数据
function getGender () {
    var option = '';
    for (var i = 0; i < genderData.length; i++) {
        option += "<option value='" + genderData[i].itemValue + "'>" + genderData[i].itemCnname +"</option>";
    }
    $('#gender').append(option);
    seajs.use('/vendors/lulu/js/common/ui/Select', function () {
        $('#gender').selectMatch();
    });
}

//选择证件类型
function getSelectedCert () {
    getIdCardInfo();
    return;
}

//正则身份证信息
function getIdCardInfo () {
    var certNo = $('#certNo').val();
    certType = $('#certType option:selected') .val();
    if(certType == '1' && validCardByCard(certNo)){
        var sex = getGenderByCard(certNo);
        if (sex)
            $("#gender").val('1');
        else
            $("#gender").val('2');
        $('#gender').selectMatch();
        $('#birthday').val(getBirthdayByCard(certNo));
    }
}

//邮箱自动填充
function autoFillMail() {
    var phoneNo = $('#mobile').val();
    var emailNo = $('#email').val();
    var reg = /^1[34578]\d{9}$/;
    if (!emailNo && reg.test(phoneNo)) {
        $('#email').val(phoneNo + '@189.cn');
        $('#email').focus();
    }
}

// 点击电话新增btn
function addMobile () {
    $('#tbMobileVoList').append("<div class='form-item' style='width: 100%; position: relative;'><input class='ui-input col-md-8 col-sm-8 col-xs-12 col-md-offset-4 col-sm-offset-4'><div class='fright del' id='mobileDel'><a class='icon-del' href='javascript:void(0)'><span class='fa fa-minus-circle'></span></a></div></div>");
    $('#mobileDel').on('click', function () {
        $(this).parent().remove();
    });
}
// 点击邮箱新增btn
function addEmail () {
    $('#tbEamilVoList').append("<div class='form-item' style='width: 100%; position: relative;'><input class='ui-input col-md-8 col-sm-8 col-xs-12 col-md-offset-4 col-sm-offset-4'><div class='fright del' id='emailDel'><a class='icon-del' href='javascript:void(0)'><span class='fa fa-minus-circle'></span></a></div></div>");
    $('#emailDel').on('click', function () {
        $(this).parent().remove();
    });
}

/***
 * 归属组织职位信息
 */
function  initOrgTable (results) {
    orgTable = $("#orgTable").DataTable({
        'data': results,
        'searching': false,
        'autoWidth': false,
        'ordering': false,
        'columns': [
            { 'data': null, 'title': '序号', 'className': 'row-no' },
            { 'data': "orgName", 'title': '归属组织', 'className': 'row-org' },
            { 'data': "userPositionRefList", 'title': '所属职位', 'className': 'row-position',
                'render': function (data, type, row, meta) {
                    var positionStr = '';
                    for (var i = 0; i < row.userPositionRefList.length; i++) {
                        if (i == row.userPositionRefList.length - 1)
                            positionStr = positionStr + row.userPositionRefList[i].positionName;
                        else
                            positionStr = positionStr + row.userPositionRefList[i].positionName + ', ';
                    }
                    return positionStr
                }
            },
            { 'data': null, 'title': '操作', 'className': 'status-code',
                'render': function (data, type, row, meta) {
                    var index = $.isArray(meta.row)? meta.row[0] : meta.row;
                    return "<div><a class='org-edit' onclick='editOrgPost(" + (index + 1) + ")'>编辑</a><a class='org-delte' href='javascript: void(0);' onclick='deleteOrgList(" + index + ")'>删除</a></div>";
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
            'infoEmpty': '没有数据'
        },
        'dom': '<"top"f>t',
        'drawCallback': function(){
            this.api().column(0).nodes().each(function(cell, i) {
                cell.innerHTML =  i + 1;
            });
        }
    });
}
// 归属组织职位信息编辑
function editOrgPost (index) {
    $('#orgPostEditBtn').hide();
    $('.actions').hide();
    $('#orgEdit').addClass( 'edit-form');
    var data = {};
    orgList = [];
    postList = [];
    if (index) {
        editFlag = index;
        data = orgTable.row(index - 1).data();
        orgList.push({id: data.orgCode, name: data.orgName});
        postList = data.userPositionRefList;
    }
    else
        editFlag = 0;
    if (data.orgCode) {
        var orgEditTemplate = Handlebars.compile($("#orgEditTemplate").html());
        var orgEditHtml = orgEditTemplate({orgPostData: data});
        $('#orgEdit').html(orgEditHtml);
        var postStr = '';
        for (var i = 0; i < postList.length; i++) {
            if (i == postList.length - 1)
                postStr = postStr + postList[i].positionName;
            else
                postStr = postStr + postList[i].positionName + ', ';
        }
        $('#postName').val(postStr);
    }
    else {
        var orgEditTemplate = Handlebars.compile($("#orgEditTemplate").html());
        var orgEditHtml = orgEditTemplate({orgPostData: {}});
        $('#orgEdit').html(orgEditHtml);
    }
    var orgEditForm = $('#orgEdit');
    orgValidate = new Validate(orgEditForm);
    orgValidate.immediate();
    // orgValidate.isAllPass();
    // orgEditForm.find(':input').each(function () {
    //     $(this).hover(function () {
    //         orgValidate.isPass($(this));
    //     });
    // });
    if(editFlag != 0){
        $("#orgName").attr("disabled","true");
    }else{
        $("#orgName").removeAttr("disabled");
    }
}
//新增/修改 归属组织职位信息
function addOrgList () {
    if (!orgValidate.isAllPass())
        return;
    var hasOrg = 0;
    if (editFlag) {
        //修改归属组织职位信息
        var sysOrgPostObj = sysUserDeptPositionVos[editFlag-1];
        sysOrgPostObj.orgCode = orgList[0].id;
        sysOrgPostObj.orgName = orgList[0].name;
        sysOrgPostObj.userPositionRefList = postList;
        orgTable.row(editFlag-1).data(sysOrgPostObj).draw();
    }
    else {
        //新增归属组织职位信息
        for(var i=0;i<sysUserDeptPositionVos.length;i++){
            if(orgList[0].id == sysUserDeptPositionVos[i].orgCode){
                hasOrg = 1;
                break;
            }
        }

        if(hasOrg == 0){
            var sysOrgPostObj  = {};
            sysOrgPostObj.orgCode = orgList[0].id;
            sysOrgPostObj.orgName = orgList[0].name;
            sysOrgPostObj.userPositionRefList = postList;
            sysUserDeptPositionVos.push(sysOrgPostObj);
            orgTable.row.add(sysOrgPostObj).draw();
        }else{
            toastr.error('归属组织信息已存在！');
        }
    }
    $('#orgPostEditBtn').show();
    $('.actions').show();
    $('#orgEdit').removeClass( 'edit-form');
    $('#orgEdit').html('');
}
// 删除一条归属组织信息
function deleteOrgList (index) {
    orgTable.row(index).remove().draw();
    sysUserDeptPositionVos.splice(index);
}
// 取消归属组织信息编辑
function cancelOrgEdit () {
    $('#orgPostEditBtn').show();
    $('.actions').show();
    $('#orgEdit').removeClass( 'edit-form');
    $('#orgEdit').html('');
}
//组织名称选择
function getOrg() {
    parent.layer.open({
        type: 2,
        title: '归属组织',
        shadeClose: true,
        shade: 0.8,
        area: ['50%', '80%'],
        maxmin: true,
        content: '/inaction/modal/orgDialog.html',
        btn: ['确认', '取消'],
        yes: function(index, layero){
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            checkNode = iframeWin.checkNode;
            if(checkNode.length != 0 && orgList.length != 0 && checkNode[0].id != orgList[0].id){
                $("#postName").val("");
                postList = [];
            }else if(checkNode.length == 0){
                $("#postName").val("");
                postList = [];
            }
            orgList = checkNode;
            if (orgList.length > 0) {
                $('#orgName').val(orgList[0].name);
            }
            else {
                $('#orgName').val('');
                postList = [];
                $('#postName').val('');
            }
            parent.layer.close(index);
            // orgValidate.isPass($('#orgName'));
            orgValidate.isAllPass();
        }
    });
}
// 职位选择
function getPost() {
    if (orgList.length == 0) {
        parent.layer.confirm('请选择归属组织', {
            icon: 0,
            title: '提示',
            btn: ['确定']
        }, function(index){
            parent.layer.close(index);
        });
        return;
    }
    parent.layer.open({
        type: 2,
        title: '组织职位',
        shadeClose: true,
        shade: 0.8,
        area: ['50%', '80%'],
        maxmin: true,
        content: '/inaction/modal/orgPostDialog.html?id=' + orgList[0].id,
        btn: ['确认', '取消'],
        yes: function(index, layero){
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            checkNode = iframeWin.checkNode;
            postList = checkNode;
            var postStr = '';
            for (var i = 0; i < postList.length; i++) {
                if (i == postList.length - 1)
                    postStr = postStr + postList[i].positionName;
                else
                    postStr = postStr + postList[i].positionName + ', ';
            }
            $('#postName').val(postStr);
            parent.layer.close(index);
            orgValidate.isAllPass();
        }
    });
}

//设置人员头像
function setPsnImg(){
    $("#psnImgFile").click();
}

$("#psnImgFile").change(function(){
    var isIE=!!window.ActiveXObject;
    var isIE8910=isIE&&document.documentMode<11;
    
    if(isIE8910){
        xmlHttp = new ActiveXObject("MSXML2.XMLHTTP");
        xmlHttp.open("POST",this.value, false);
        xmlHttp.send("");
        xml_dom = new ActiveXObject("MSXML2.DOMDocument");
        tmpNode = xml_dom.createElement("tmpNode");
        tmpNode.dataType = "bin.base64";
        tmpNode.nodeTypedValue = xmlHttp.responseBody;
        imgData = "data:image/"+ "bmp" +";base64," + tmpNode.text.replace(/\n/g,"");
        $("#psnImg").attr('src', imgData);
        convertToFile(imgData);
    }else{
        var $file = $('#psnImgFile');
        var fileObj = $file[0];
        if(fileObj.value!=""){
            var dataURL;
            var fr = new FileReader;
            var $img = $("#psnImg");
            fr.readAsDataURL(fileObj.files[0]);
            fr.onload=function(){
                dataURL = fr.result;
                // console.log(dataURL);
                $img.attr('src', dataURL);
                setTimeout(convertToFile(dataURL),"500");
            } 
        }
    }
})

function convertToFile(base64Codes){
    var formData = new FormData();

    formData.append("multipartFile",convertBase64UrlToBlob(base64Codes));
    $.ajax({			//提交表单，异步上传图片
        url : "/psnImage/uploadImg",  //上传图片调用的服务
        type : "POST",
        data : formData,
        // dataType:"json",
        processData : false,         // 告诉jQuery不要去处理发送的数据
        contentType : false,        // 告诉jQuery不要去设置Content-Type请求头
        success:function(data){
            psnImageId = data.data.psnImageId();
        }
    });
}

function convertBase64UrlToBlob(urlData){
    var bytes=window.atob(urlData.split(',')[1]);        //去掉url的头，并转换为byte
    var ab = new ArrayBuffer(bytes.length);
    var ia = new Uint8Array(ab);
    for (var i = 0; i < bytes.length; i++) {
        ia[i] = bytes.charCodeAt(i);
    }
    return new Blob( [ab] , {type : 'image/png'});
}

// 新增人员信息
function addSysUser () {
    if (!formValidate.isAllPass())
        return;
    if (sysUserDeptPositionVos.length == 0) {
        parent.layer.confirm('请新增一条归属组织职位信息', {
            icon: 0,
            title: '提示',
            btn: ['确定']
        }, function(index){
            parent.layer.close(index);
        });
        return;
    }
    if (sysUserDeptPositionVos[0].userPositionRefList.length == 0) {
        parent.layer.confirm('请选择所属职位', {
            icon: 0,
            title: '提示',
            btn: ['确定']
        }, function(index){
            parent.layer.close(index);
        });
        return;
    }
    loading.screenMaskEnable('container');

    var userName = $('#userName').val();
    var passwd = $('#passwd').val();
    var certType = $('#certType option:selected') .val();
    var certNo = $('#certNo').val();
    var accout = $('#accout').val();
    var acctLevel = $('#acctLevel option:selected') .val();
    var birthday;
    if ($('#birthday').val()) {
        birthday = new Date($('#birthday').val()).getTime();
    }
    var gender = $('#gender option:selected') .val();
    var mobile = $('#mobile').val();
    var email = $('#email').val();

    var sysUserDeptPositionList = [];
    for (var i = 0; i < sysUserDeptPositionVos.length; i++) {
        var userPositionRefList = [];
        for (var j = 0; j < sysUserDeptPositionVos[i].userPositionRefList.length; j++) {
            userPositionRefList.push({positionCode: sysUserDeptPositionVos[i].userPositionRefList[j].positionCode})
        }
        sysUserDeptPositionList.push({orgCode: sysUserDeptPositionVos[i].orgCode, orgName: sysUserDeptPositionVos[i].orgName, userPositionRefList: userPositionRefList})
    }
    $http.post('/system/addsysUserDeptRef', JSON.stringify({
        userName: userName,
        passwd: passwd,
        certType: certType,
        certId: certNo,
        accout: accout,
        acctLevel: acctLevel,
        birthday: birthday,
        gender: gender,
        mobile: mobile,
        email: email,
        sysUserDeptPositionVos: sysUserDeptPositionList
    }), function () {
        window.location.replace("list.html?id=" + orgId + '&orgFlag=' + orgFlag + "&name=" + encodeURI(orgName));
        loading.screenMaskDisable('container');
        toastr.success('更新成功！');
    }, function () {
        loading.screenMaskDisable('container');
    })
}

// 取消新增
function cancel() {
    var url = "list.html?id=" + orgId + '&orgFlag=' + orgFlag + "&name=" + encodeURI(orgName);
    window.location.href = url;
}

$('.org-name').html(orgName);
parent.getOrgExtInfo();

// 添加默认归属组织
// if (orgId) {
//     sysUserDeptPositionVos.push({orgCode: orgId, orgName: orgName, userPositionRefList: postList})
// }
if(orgFlag == 1){
    sysUserDeptPositionVos.push({orgCode: orgId, orgName: orgName, userPositionRefList: postList})
}

var userAddForm = $('#userAddForm');
formValidate = new Validate(userAddForm);
formValidate.immediate();
formValidate.isAllPass();
userAddForm.find(':input').each(function () {
    $(this).bind({
        mouseenter : function(){
            formValidate.isPass($(this));
        },
        paste : function(){
            formValidate.isPass($(this));
            $(this).removeClass('error');
        }
    });
});
$('select').selectMatch();
// 时间插件
laydate.render({
    elem: '#birthday'
});

getCertType();
getStatusCd();
getAcctLevel();
getGender();
if(orgFlag == 1){
    initOrgTable(sysUserDeptPositionVos);
}else{
    initOrgTable("");
}
editOrgPost(1);
