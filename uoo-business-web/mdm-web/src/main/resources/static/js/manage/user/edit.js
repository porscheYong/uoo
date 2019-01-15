var orgId = getQueryString('id');
var userId = getQueryString('userId');
var orgName = getQueryString('name');
var userData = {}; //用户详情
var sysUserDeptPositionVos = [];
var sysOrgPostObj = {};
var psonOrgVoList = [],
    orgList = [],
    postList = [];
var editFlag = 0; //编辑新增标志
var orgTable;
var psnImageId;
var imgUrl = "";
var userFormValidate;
var orgValidate;
var loading = parent.loading;
var toastr = window.top.toastr;

var account = window.top.account;
//字典数据
var certTypeData = window.top.dictionaryData.certType();
var acctLevelData = window.top.dictionaryData.acctLevel();
var genderData = window.top.dictionaryData.gender();

// 获取人员信息
function getSysUerInfo () {
    $http.get('/system/getSysUserDeptPosition', {
        userId: userId,
        pageNo: '1',
        pageSize: 100
    }, function (data) {
        userData = data;
        userData.certTypeData = certTypeData;
        userData.acctLevelData = acctLevelData;
        userData.genderData = genderData;
        initUser();
        sysUserDeptPositionVos = data.sysUserDeptPositionVos.records;
        initOrgTable(sysUserDeptPositionVos);
        orgList.push({id: sysUserDeptPositionVos.orgCode, sysUserDeptPositionVos: data.orgName});
        postList = sysUserDeptPositionVos.userPositionRefList;
    })
}

// 获取登陆信息
function initLoginTable () {
    $("#loginTable").DataTable({
        'destroy':true,
        'searching': false,
        'autoWidth': false,
        'ordering': true,
        'lSort': true,
        "scrollY": "395px",
        'scrollCollapse': true,
        'columns': [
            { 'data': null, 'title': '序号', 'className': 'row-no',
                'render': function (data, type, row, meta) {
                    return meta.row + 1 + meta.settings._iDisplayStart;
                }
            },
            { 'data': "createDate", 'title': '登陆时间', 'className': 'row-name',
                'render': function (data) {
                    return formatDateTime(data)
                }
            },
            { 'data': "ip", 'title': '登陆IP', 'className': 'user-account' },
            { 'data': "statusCd", 'title': '状态', 'className': 'role-type',
                'render': function (data, type, row, meta) {
                    var statusStr = '';
                    if (row.statusCd == '1000') {
                        statusStr = '<span>有效</span>';
                    } else {
                        statusStr = '<span>无效</span>';
                    }
                    return statusStr
                }
            },
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
            'info': '总_TOTAL_条',
            'infoEmpty': '没有数据'
        },
        "aLengthMenu": [[10, 20, 50], ["10条/页", "20条/页", "50条/页"]],
        'pagingType': 'simple_numbers',
        'dom': '<"top"f>t<"bottom"ipl>',
        'serverSide': true,  //启用服务器端分页
        'ajax': function (data, callback) {
            var param = {};
            param.pageSize = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
            param.pageNo = (data.start / data.length) + 1;//当前页码
            param.accout = account;
            $http.get('/system/sysLoginLog/listAccoutLog', param, function (result) {
                var returnData = {};
                returnData.recordsTotal = result.total;
                returnData.recordsFiltered = result.total;
                returnData.data = result.records;
                callback(returnData);
            })
        }
    });
}

// 获取登陆信息
function initUpdateTable () {
    $("#updateTable").DataTable({
        'destroy':true,
        'searching': false,
        'autoWidth': false,
        'ordering': true,
        'lSort': true,
        "scrollY": "395px",
        'scrollCollapse': true,
        'columns': [
            { 'data': null, 'title': '序号', 'className': 'row-no',
                'render': function (data, type, row, meta) {
                    return meta.row + 1 + meta.settings._iDisplayStart;
                }
            },
            { 'data': "createDate", 'title': '登陆时间', 'className': 'row-name',
                'render': function (data) {
                    return formatDateTime(data)
                }
            },
            { 'data': "ip", 'title': '登陆IP', 'className': 'user-account' },
            { 'data': "statusCd", 'title': '状态', 'className': 'role-type',
                'render': function (data, type, row, meta) {
                    var statusStr = '';
                    if (row.statusCd == '1000') {
                        statusStr = '<span>有效</span>';
                    } else {
                        statusStr = '<span>无效</span>';
                    }
                    return statusStr
                }
            },
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
            'info': '总_TOTAL_条',
            'infoEmpty': '没有数据'
        },
        "aLengthMenu": [[10, 20, 50], ["10条/页", "20条/页", "50条/页"]],
        'pagingType': 'simple_numbers',
        'dom': '<"top"f>t<"bottom"ipl>',
        'serverSide': true,  //启用服务器端分页
        'ajax': function (data, callback) {
            var param = {};
            param.pageSize = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
            param.pageNo = (data.start / data.length) + 1;//当前页码
            param.tableName = 'SYS_ORGANIZATION';
            param.recordId = userId;
            $http.get('/public/modifyHistory/listByRecord', param, function (result) {
                var returnData = {};
                returnData.recordsTotal = result.total;
                returnData.recordsFiltered = result.total;
                returnData.data = result.records;
                callback(returnData);
            })
        }
    });
}

function initUser(){
    $('#userEditButton').show();
    //预编译模板
    var userTemplate = Handlebars.compile($("#userTemplate").html());
    //匹配json内容
    var userHtml = userTemplate({userData: userData});
    //输入模板
    $('#userInfo').html(userHtml);
}

function  editUser() {
    $('#userEditButton').hide();
    //预编译模板
    var userEditTemplate = Handlebars.compile($("#userEditTemplate").html());
    //匹配json内容
    var userEditHtml = userEditTemplate({userData: userData});
    //输入模板
    $('#userInfo').html(userEditHtml);
    laydate.render({
        elem: 'input[isTime="yes"]'
    });

    $('.icon-del').on('click', function () {
        $(this).parent().remove();
    });
    $("#choseFileImg").change( function() {
        addPsonImg();
    });
    $('select').selectMatch();
    var userEditForm = $('#userEditForm');
    userFormValidate = new Validate(userEditForm);
    userFormValidate.immediate();
    userFormValidate.isAllPass();
    userEditForm.find(':input').each(function () {
        $(this).hover(function () {
            userFormValidate.isPass($(this));
        });
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

/***
 * 归属组织职位信息
 */
function  initOrgTable (results) {
    orgTable = $("#orgTable").DataTable({
        'data': results,
        'destroy':true,
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
                    return '<span title="' + positionStr + '">' + positionStr + '</span>'
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
    $('#delBtn').hide();
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
    orgValidate.isAllPass();
    orgEditForm.find(':input').each(function () {
        $(this).hover(function () {
            orgValidate.isPass($(this));
        });
    });
}
//新增/修改 归属组织职位信息
function addOrgList () {
    if (!orgValidate.isAllPass())
        return;
    if (editFlag) {
        //修改归属组织职位信息
        sysOrgPostObj = sysUserDeptPositionVos[editFlag-1];
        sysOrgPostObj.orgCode = orgList[0].id;
        sysOrgPostObj.orgName = orgList[0].name;
        sysOrgPostObj.userCode = userData.userCode;
        sysOrgPostObj.userPositionRefList = [];
        for (var i = 0; i < postList.length; i++) {
            sysOrgPostObj.userPositionRefList.push({positionCode: postList[i].positionCode})
        }
        $http.post('/sysUserDeptRef/updateUserDeptPositionDef', JSON.stringify(sysOrgPostObj), function () {
            $('#orgPostEditBtn').show();
            $('#delBtn').show();
            $('#orgEdit').removeClass( 'edit-form');
            $('#orgEdit').html('');
            loading.screenMaskDisable('container');
            toastr.success('删除成功！');
            getSysUerInfo();
        }, function () {
            loading.screenMaskDisable('container');
        })
    }
    else {
        //新增归属组织职位信息
        sysOrgPostObj  = {};
        sysOrgPostObj.orgCode = orgList[0].id;
        sysOrgPostObj.orgName = orgList[0].name;
        sysOrgPostObj.userCode = userData.userCode;
        sysOrgPostObj.userPositionRefList = [];
        for (var i = 0; i < postList.length; i++) {
            sysOrgPostObj.userPositionRefList.push({positionCode: postList[i].positionCode})
        }
        $http.post('/sysUserDeptRef/addUserDeptPositionDef', JSON.stringify(sysOrgPostObj), function () {
            $('#orgPostEditBtn').show();
            $('#delBtn').show();
            $('#orgEdit').removeClass( 'edit-form');
            $('#orgEdit').html('');
            loading.screenMaskDisable('container');
            toastr.success('删除成功！');
            getSysUerInfo();
        }, function () {
            loading.screenMaskDisable('container');
        })
    }
    // $('#orgPostEditBtn').show();
    // $('#delBtn').show();
    // $('#orgEdit').removeClass( 'edit-form');
    // $('#orgEdit').html('');
}
// 删除一条归属组织信息
function deleteOrgList (row) {
    parent.layer.confirm('此操作将删除该归属组织职位信息, 是否继续?', {
        icon: 0,
        title: '提示',
        btn: ['确定','取消']
    }, function(index){
        parent.layer.close(index);
        loading.screenMaskEnable('container');
        $http.delet('/sysUserDeptRef/delUserDeptPositionDef', JSON.stringify(sysUserDeptPositionVos[row]), function () {
            loading.screenMaskDisable('container');
            toastr.success('删除成功！');
            getSysUerInfo();
        }, function () {
            loading.screenMaskDisable('container');
        })
    });
    // $http.delete('/sysUserDeptRef/delUserDeptPositionDef', JSON.stringify(sysUserDeptPositionVos[index]), function (data) {
    //     orgTable.row(index).remove().draw();
    //     sysUserDeptPositionVos.splice(index);
    // })
}
// 取消归属组织信息编辑
function cancelOrgEdit () {
    $('#orgPostEditBtn').show();
    $('#delBtn').show();
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
            orgList = checkNode;
            if (orgList.length > 0)
                $('#orgName').val(orgList[0].name);
            else
                $('#orgName').val('');
            parent.layer.close(index);
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
        }
    });
}

function openChoseImg(){
    $('#choseFileImg').click();
}

function addPsonImg(){
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
        var $file = $('#choseFileImg');
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
}

function convertToFile(base64Codes){
    var formData = new FormData();

    // formData.append("psnImageId", $('#psnImageId').val());
    formData.append("multipartFile",convertBase64UrlToBlob(base64Codes));	
    $.ajax({			//提交表单，异步上传图片
        url : "/psnImage/uploadImg",  //上传图片调用的服务
        type : "POST",
        data : formData,
        // dataType:"json",
        processData : false,         // 告诉jQuery不要去处理发送的数据
        contentType : false,        // 告诉jQuery不要去设置Content-Type请求头
        success:function(data){
            toastr.success(data.message);
            psnImageId = data.data.psnImageId;
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

// 更新用户信息
function updateSysUser(){
    if (!userFormValidate.isAllPass())
        return;
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
    var userCode = $('#userCode').val();

    $http.post('/system/updateSysUser', JSON.stringify({
        userId: userId,
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
        userCode: userCode
    }), function () {
        loading.screenMaskDisable('container');
        toastr.success('更新成功！');
    }, function () {
        loading.screenMaskDisable('container');
    })
}

// 删除平台用户
function deleteSysUser(){
    parent.layer.confirm('此操作将删除该用户, 是否继续?', {
        icon: 0,
        title: '提示',
        btn: ['确定','取消']
    }, function(index){
        parent.layer.close(index);
        loading.screenMaskEnable('container');
        $http.post('/system/deleteUser', JSON.stringify({
            userId: userData.userId,
            userCode: userData.userCode
        }), function () {
            window.location.replace("list.html?id=" + orgId + "&name=" + encodeURI(orgName));
            loading.screenMaskDisable('container');
            toastr.success('删除成功！');
        }, function () {
            loading.screenMaskDisable('container');
        })
    });
}

getSysUerInfo();
initLoginTable();
initUpdateTable();

// datatable应用于tab切换出现表头错位
$('#navTabs a').click(function (e) {
    e.preventDefault();
    $(this).tab('show');
    $.fn.dataTable.tables( {visible: true, api: true} ).columns.adjust();
});

Handlebars.registerHelper('eq', function(v1, v2, opts) {
    if(v1 == v2){
        return opts.fn(this);
    }
    else
        return opts.inverse(this);
});

Handlebars.registerHelper('format', getDate);
