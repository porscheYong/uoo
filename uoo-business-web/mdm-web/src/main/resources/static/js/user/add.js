var orgId = getQueryString('id'); //组织ID
var orgTreeId = getQueryString('orgTreeId'); //组织树ID
var orgName = getQueryString('name');
var engine, template, empty, selectNode;
var table;
var eduList = [],
    emailList = [],
    psonOrgVoList = [],
    familyList = [],
    mobileList = [],
    psnjobList = [],
    orgFullName,
    orgPostList = [];
var loading = parent.loading;

$('#orgName').html(orgName);
// 步骤条
$("#userAdd").steps({
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

// 获取证件类型字典数据
function getCertType () {
    $http.get('/tbDictionaryItem/getList/CERT_TYPE', {}, function (data) {
        var option = '<option></option>';
        for (var i = 0; i < data.length; i++) {
            option += "<option value='" + data[i].itemValue + "'>" + data[i].itemCnname +"</option>";
        }
        $('#certType').append(option);
        $('#certType').selectMatch();
    }, function (err) {
        console.log(err)
    })
}

// 获取民族字典数据
function getNation () {
    $http.get('/tbDictionaryItem/getList/NATION', {}, function (data) {
        var option = '<option></option>';
        for (var i = 0; i < data.length; i++) {
            option += "<option value='" + data[i].itemValue + "'>" + data[i].itemCnname +"</option>";
        }
        $('#nation').append(option);
        $('#nation').selectMatch();
    }, function (err) {
        console.log(err)
    })
}

// 获取政治面貌字典数据
function getPliticalStatus () {
    $http.get('/tbDictionaryItem/getList/PLITICAL_STATUS', {}, function (data) {
        var option = '<option></option>';
        for (var i = 0; i < data.length; i++) {
            option += "<option value='" + data[i].itemValue + "'>" + data[i].itemCnname +"</option>";
        }
        $('#pliticalStatus').append(option);
        $('#pliticalStatus').selectMatch();
    }, function (err) {
        console.log(err)
    })
}

// 获取婚姻状况字典数据
function getMarriage () {
    $http.get('/tbDictionaryItem/getList/MARRIAGE', {}, function (data) {
        var option = '<option></option>';
        for (var i = 0; i < data.length; i++) {
            option += "<option value='" + data[i].itemValue + "'>" + data[i].itemCnname +"</option>";
        }
        $('#marriage').append(option);
        $('#marriage').selectMatch();
    }, function (err) {
        console.log(err)
    })
}

// 获取用工性质字典数据
function getProperty () {
    $http.get('/tbDictionaryItem/getList/PROPERTY', {}, function (data) {
        var option = '<option></option>';
        for (var i = 0; i < data.length; i++) {
            option += "<option value='" + data[i].itemValue + "'>" + data[i].itemCnname +"</option>";
        }
        $('#property').append(option);
        $('#property').selectMatch();
    }, function (err) {
        console.log(err)
    })
}

// 获取专兼职字典数据
function getRefType () {
    $http.get('/tbDictionaryItem/getList/REF_TYPE', {}, function (data) {
        var option = '<option></option>';
        for (var i = 0; i < data.length; i++) {
            option += "<option value='" + data[i].itemValue + "'>" + data[i].itemCnname +"</option>";
        }
        $('#refType').append(option);
        $('#refType').selectMatch();
    }, function (err) {
        console.log(err)
    })
}

// 获取组织树列表
function getOrgTreeList () {
    $http.get('/orgTree/getOrgTreeList', {}, function (data) {
        var option = '';
        for (var i = 0; i < data.length; i++) {
            var select = orgTreeId === data[i].orgId? 'selected' : '';
            option += "<option value='" + data[i].orgId + "' " + select + ">" + data[i].orgTreeName +"</option>";
        }
        $('#orgTreeName').append(option);
        seajs.use('/vendors/lulu/js/common/ui/Select', function () {
            $('#orgTreeName').selectMatch();
        });
    }, function (err) {
        console.log(err)
    })
}

// 获取组织全称
function getOrgFullName() {
    parent.layer.open({
        type: 2,
        title: '组织全称',
        shadeClose: true,
        shade: 0.8,
        area: ['50%', '80%'],
        maxmin: true,
        content: 'orgNameDialog.html?orgTreeId='+orgTreeId+'&orgRootId='+orgTreeId,
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

// 职位选择
function getPostName () {
    parent.layer.open({
        type: 2,
        title: '组织职位',
        shadeClose: true,
        shade: 0.8,
        area: ['50%', '80%'],
        maxmin: true,
        content: '/inaction/organization/postDialog.html?orgTreeId='+orgTreeId+'&orgRootId='+orgTreeId,
        btn: ['确认', '取消'],
        yes: function(index, layero){
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            checkNode = iframeWin.checkNode;
            parent.layer.close(index);
            $('#postName').val(checkNode[0].postName);
            orgPostList = checkNode;
        },
        btn2: function(index, layero){},
        cancel: function(){}
    });
}

// 点击电话新增btn
function addMobile () {
    $('#tbMobileVoList').append("<input style='margin-top: 10px;'>");
}
// 点击邮箱新增btn
function addEmail () {
    $('#tbEamilVoList').append("<input style='margin-top: 10px;'>");
}

empty = Handlebars.compile($(".typeahead-menu").html());

engine = new Bloodhound({
    identify: function(o) { return o.id_str; },
    queryTokenizer: Bloodhound.tokenizers.whitespace,
    datumTokenizer: Bloodhound.tokenizers.obj.whitespace('name', 'orgName'),
    dupDetector: function(a, b) { return a.id_str === b.id_str; },
    remote: {
        url: '/personnel/getPsnBasicInfo?keyWord=%QUERY&pageNo=1&pageSize=10',
        wildcard: '%QUERY',
        filter: function (response) {
            if (response.data && response.data.records.length == 0)
                return;
            // table = $("#userTable").DataTable({
            //     'data': response.data.records,
            //     'destroy': true,
            //     'searching': false,
            //     'autoWidth': false,
            //     'ordering': true,
            //     'initComplete': function (settings, json) {
            //         console.log(settings, json)
            //     },
            //     'columns': [
            //         { 'data': "psnName", 'title': '人员姓名', 'className': 'row-name' },
            //         { 'data': "psnNbr", 'title': '员工工号', 'className': 'cert-no' },
            //         { 'data': "content", 'title': '联系方式', 'className': 'row-mobile' },
            //         { 'data': "createDate", 'title': '创建时间', 'className': 'status-code',
            //             'render': function (data, type, row, meta) {
            //                 var time = formatDateTime(row.createDate);
            //                 return time;
            //             }
            //         },
            //         { 'data': "", 'title': '操作', 'className': 'status-code',
            //             'render': function (data, type, row, meta) {
            //                 return "<a href='edit.html?orgId=" + orgId + "&orgTreeId=" + orgTreeId + "&personnelId=" + row.personnelId +"'>选择</a>";
            //             }
            //         }
            //     ],
            //     'language': {
            //         'emptyTable': '没有数据',
            //         'loadingRecords': '加载中...',
            //         'processing': '查询中...',
            //         'search': '检索:',
            //         'lengthMenu': ' _MENU_ ',
            //         'zeroRecords': '没有数据',
            //         'paginate': {
            //             'first':      '首页',
            //             'last':       '尾页',
            //             'next':       '下一页',
            //             'previous':   '上一页'
            //         },
            //         'info': '总_TOTAL_人',
            //         'infoEmpty': '没有数据'
            //     },
            //     "aLengthMenu": [[10, 20, 50], ["10条/页", "20条/页", "50条/页"]],
            //     'pagingType': 'simple_numbers',
            //     'dom': '<"top"f>t<"bottom"ipl>'
            // });
        }
    }
});

function initTable(keyWord){
    table = $("#userTable").DataTable({
        //'data': response.data.records,
        'destroy': true,
        'searching': false,
        'autoWidth': false,
        'ordering': true,
        'initComplete': function (settings, json) {
            console.log(settings, json)
        },
        'columns': [
            { 'data': "psnName", 'title': '人员姓名', 'className': 'row-name'},
            { 'data': "psnNbr", 'title': '员工工号', 'className': 'cert-no' },
            { 'data': "content", 'title': '联系方式', 'className': 'row-mobile' },
            { 'data': "createDate", 'title': '创建时间', 'className': 'status-code',
            'render': function (data, type, row, meta) {
                var time = formatDateTime(row.createDate);
                return time;
            }
            },
            { 'data': "", 'title': '操作', 'className': 'status-code',
                'render': function (data, type, row, meta) {
                    return "<a href='edit.html?orgId=" + orgId + "&orgTreeId=" + orgTreeId + "&personnelId=" + row.personnelId +"'>选择</a>";
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
            'info': '总_TOTAL_人',
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
            param.keyWord = keyWord;
            $http.get('/personnel/getPsnBasicInfo', param, function (result) {
                var returnData = {};
                // returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                returnData.recordsTotal = result.total;//返回数据全部记录
                returnData.recordsFiltered = result.total;//后台不实现过滤功能，每次查询均视作全部结果
                returnData.data = result.records;//返回的数据列表
                //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                callback(returnData);
            }, function (err) {
                console.log(err)
            })
        }
    });
}

function engineWithDefaults(q, sync, async) {
    if (q === '') {
        $('#userTable').html('');
        $('#userTable_wrapper .bottom').remove();
    }
    else {
        engine.search(q, sync, async);
    }
}

$('#psnName').typeahead({
    hint: $('.typeahead-hint'),
    menu: $('.user-table'),
    minLength: 0,
    highlight:true,
    classNames: {
        open: 'is-open',
        empty: 'is-empty',
        cursor: 'is-active',
        suggestion: 'Typeahead-suggestion',
        selectable: 'Typeahead-selectable'
    }
}, {
    source: engineWithDefaults,
    displayKey: 'orgName',
    templates: {
        suggestion: empty
    }
})
  .on('typeahead:asyncrequest', function() {
        $('.Typeahead-spinner').show();
        initTable($("#psnName").val());
    })
  .on('typeahead:asynccancel typeahead:asyncreceive', function() {
        $('.Typeahead-spinner').hide();
    });

Handlebars.registerHelper('eq', function(v1, v2, opts) {
    if(v1 == v2){
        return opts.fn(this);
    }
    else
        return opts.inverse(this);
});

Handlebars.registerHelper("addOne", function (index) {
    return index + 1;
});

// 归属组织信息编辑
function openOrgEdit () {
    //预编译模板
    var orgTemplate = Handlebars.compile($("#orgTemplate").html());
    //匹配json内容
    var orgEditHtml = orgTemplate();
    //输入模板
    $('#orgPerson').html(orgEditHtml);
    $('.wizard .actions').hide();
    getOrgTreeList();
    getRefType();
    getProperty();
}
// 取消归属组织信息编辑
function cancelOrgEdit () {
    var orgListTemplate = Handlebars.compile($("#orgListTemplate").html());
    var orgListHtml = orgListTemplate();
    $('#orgPerson').html(orgListHtml);
    $('.wizard .actions').show();
}

// 保存归属组织信息
function saveOrgTree () {
    var orgTreeName = $('#orgTreeName option:selected') .val();
    var fullName = orgFullName.name || orgName;
    var doubleName = $('#doubleName').val();
    var property = $('#property option:selected') .val();
    var refType = $('#refType option:selected') .val();
    var postName = orgPostList[0].postName;
    var postId = orgPostList[0].postId;
    psonOrgVoList.push({
        orgTreeName: orgTreeName,
        orgTreeId: orgTreeId,
        fullName: fullName,
        orgId: orgFullName.id || orgId,
        doubleName: doubleName,
        property: property,
        refType: refType,
        postName: postName,
        postId: postId
    });
    var orgListTemplate = Handlebars.compile($("#orgListTemplate").html());
    var orgListHtml = orgListTemplate({psonOrgVoList: psonOrgVoList});
    $('#orgPerson').html(orgListHtml);
    $('.wizard .actions').show();
}
// 工作经历信息编辑
function editWrokExperience () {
    //预编译模板
    var jobTemplate = Handlebars.compile($("#jobTemplate").html());
    //匹配json内容
    var jobEditHtml = jobTemplate();
    //输入模板
    $('#workExperience').html(jobEditHtml);
    laydate.render({
        elem: '#beginTime'
    });
    laydate.render({
        elem: '#endTime'
    });
}
// 工作经历信息保存
function addWrokExperience () {
    var name = $('#companyName').val();
    var status = $('#status').val();
    var beginTime = $('#beginTime').val();
    var endTime = $('#endTime').val();
    psnjobList.push({name: name, status: status, beginTime: beginTime, endTime: endTime});
    var handleHelper = Handlebars.registerHelper("addOne", function (index) {
        //返回+1之后的结果
        return index + 1;
    });
    //预编译模板
    var jobListTemplate = Handlebars.compile($("#jobListTemplate").html());
    //匹配json内容
    var jobListHtml = jobListTemplate({psnjobList: psnjobList});
    //输入模板
    $('#workExperience').html(jobListHtml);
}
// 取消工作经历信息编辑
function cancelWrokExperienceEdit () {
    //预编译模板
    var jobListTemplate = Handlebars.compile($("#jobListTemplate").html());
    //匹配json内容
    var jobListHtml = jobListTemplate({psnjobList: psnjobList});
    //输入模板
    $('#workExperience').html(jobListHtml);
}
// 教育经历信息编辑
function editEduExperience () {
    //预编译模板
    var eduTemplate = Handlebars.compile($("#eduTemplate").html());
    //匹配json内容
    var eduEditHtml = eduTemplate();
    //输入模板
    $('#eduExperience').html(eduEditHtml);
    laydate.render({
        elem: '#begindate'
    });
    laydate.render({
        elem: '#enddate'
    });
}
// 教育经历信息保存
function addEduExperience () {
    var school = $('#school').val();
    var major = $('#major').val();
    var begindate = $('#begindate').val();
    var enddate = $('#enddate').val();
    var learning = $('#learning').val();
    var education = $('#education').val();
    var degree = $('#degree').val();
    var firstEducation = $('#firstEducation option:selected') .val();
    var lastEducation = $('#lastEducation option:selected') .val();
    var lastDegree = $('#lastDegree option:selected') .val();
    eduList.push({
        school: school,
        major: major,
        begindate: begindate,
        enddate: enddate,
        learning: learning,
        education: education,
        degree: degree,
        firstEducation: firstEducation,
        lastEducation: lastEducation,
        lastDegree: lastDegree,
    });
    //预编译模板
    var eduListTemplate = Handlebars.compile($("#eduListTemplate").html());
    //匹配json内容
    var eduListHtml = eduListTemplate({eduList: eduList});
    //输入模板
    $('#eduExperience').html(eduListHtml);
}
// 取消教育经历信息编辑
function cancelEduExperienceEdit () {
    //预编译模板
    var eduListTemplate = Handlebars.compile($("#eduListTemplate").html());
    //匹配json内容
    var eduListHtml = eduListTemplate({eduList: eduList});
    //输入模板
    $('#eduExperience').html(eduListHtml);
}
// 家庭成员信息编辑
function editFamily () {
    //预编译模板
    var familyTemplate = Handlebars.compile($("#familyTemplate").html());
    //匹配json内容
    var familyHtml = familyTemplate();
    //输入模板
    $('#familyMember').html(familyHtml);
}
// 家庭成员信息保存
function addFamily () {
    var memName = $('#memName').val();
    var appellation = $('#appellation').val();
    var gender = $('#gender option:selected') .val();
    var relaPhone = $('#relaPhone').val();
    var relaAddr = $('#relaAddr').val();
    familyList.push({
        memName: memName,
        appellation: appellation,
        gender: gender,
        relaPhone: relaPhone,
        relaAddr: relaAddr
    });
    //预编译模板
    var familyListTemplate = Handlebars.compile($("#familyListTemplate").html());
    //匹配json内容
    var familyListHtml = familyListTemplate({familyList: familyList});
    //输入模板
    $('#familyMember').html(familyListHtml);
}
// 家庭成员信息编辑
function cancelFamilyEdit () {
    //预编译模板
    var familyListTemplate = Handlebars.compile($("#familyListTemplate").html());
    //匹配json内容
    var familyListHtml = familyListTemplate({familyList: familyList});
    //输入模板
    $('#familyMember').html(familyListHtml);
}

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

//将时间戳改为yyyy-MM-dd HH-mm-ss
function formatDateTime(unix) {
    var now = new Date(parseInt(unix) * 1);
    now =  now.toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ");
    if(now.indexOf("下午") > 0) {
        if (now.length == 18) {
            var temp1 = now.substring(0, now.indexOf("下午"));   //2014/7/6
            var temp2 = now.substring(now.indexOf("下午") + 2, now.length);  // 5:17:43
            var temp3 = temp2.substring(0, 1);    //  5
            var temp4 = parseInt(temp3); // 5
            temp4 = 12 + temp4;  // 17
            var temp5 = temp4 + temp2.substring(1, temp2.length); // 17:17:43
            now = temp1 + temp5; // 2014/7/6 17:17:43
            now = now.replace("/", "-"); //  2014-7/6 17:17:43
            now = now.replace("/", "-"); //  2014-7-6 17:17:43
        }else {
            var temp1 = now.substring(0, now.indexOf("下午"));   //2014/7/6
            var temp2 = now.substring(now.indexOf("下午") + 2, now.length);  // 5:17:43
            var temp3 = temp2.substring(0, 2);    //  5
            if (temp3 == 12){
                temp3 -= 12;
            }
            var temp4 = parseInt(temp3); // 5
            temp4 = 12 + temp4;  // 17
            var temp5 = temp4 + temp2.substring(2, temp2.length); // 17:17:43
            now = temp1 + temp5; // 2014/7/6 17:17:43
            now = now.replace("/", "-"); //  2014-7/6 17:17:43
            now = now.replace("/", "-"); //  2014-7-6 17:17:43
        }
    }else {
        var temp1 = now.substring(0,now.indexOf("上午"));   //2014/7/6
        var temp2 = now.substring(now.indexOf("上午")+2,now.length);  // 5:17:43
        var temp3 = temp2.substring(0,1);    //  5
        var index = 1;
        var temp4 = parseInt(temp3); // 5
        if(temp4 == 0 ) {   //  00
            temp4 = "0"+temp4;
        }else if(temp4 == 1) {  // 10  11  12
            index = 2;
            var tempIndex = temp2.substring(1,2);
            if(tempIndex != ":") {
                temp4 = temp4 + "" + tempIndex;
            }else { // 01
                temp4 = "0"+temp4;
            }
        }else {  // 02 03 ... 09
            temp4 = "0"+temp4;
        }
        var temp5 = temp4 + temp2.substring(index,temp2.length); // 07:17:43
        now = temp1 + temp5; // 2014/7/6 07:17:43
        now = now.replace("/","-"); //  2014-7/6 07:17:43
        now = now.replace("/","-"); //  2014-7-6 07:17:43
    }
    return now;
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
    mobileList = [];
    emailList = [];
    $('#tbMobileVoList').find(':input').each(function () {
        mobileList.push({contactType: 1, content: $(this).val()});
    });
    $('#tbEamilVoList').find(':input').each(function () {
        emailList.push({contactType: 2, content: $(this).val()});
    });
    var tbMobileVoList = mobileList;
    var tbEamilVoList = emailList;
    $http.post('/personnel/savePersonnel', JSON.stringify({
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
        tbMobileVoList: tbMobileVoList,
        tbEamilVoList: tbEamilVoList,
        tbEduList: eduList,
        psonOrgVoList: psonOrgVoList,
        tbFamilyList: familyList,
        tbPsnjobList: psnjobList
    }), function () {
        parent.initBusinessList();
        loading.screenMaskDisable('container');
    }, function (err) {
        console.log(err);
        loading.screenMaskDisable('container');
    })
}

// getOrgExtInfo();
getCertType();
getNation();
getPliticalStatus();
getMarriage();


