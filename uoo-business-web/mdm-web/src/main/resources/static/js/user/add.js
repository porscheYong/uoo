var orgId = getQueryString('id'); //组织ID
var orgTreeId = getQueryString('orgTreeId'); //组织树ID
var orgName = getQueryString('name');
var orgTreeName = getQueryString('orgTreeName');
var engine, template, empty, selectNode;
var table;
var workTable;
var eduTable;
var familyTable;
var orgTable;
var workFlag = 0;
var eduFlag = 0;
var familyFlag = 0;
var orgFlag = 0;
var doubleNameFlag = 0;
var eduList = [],
    emailList = [],
    psonOrgVoList = [],
    orgList = [],
    familyList = [],
    mobileList = [],
    psnjobList = [],
    orgFullName,
    doubleName = '',
    orgPostList = [];
var loading = parent.loading;
var formValidate;

$('#orgName').html(orgName);
// 步骤条
$("#userAdd").steps({
    headerTag: "h2",
    bodyTag: "div",
    transitionEffect: "slideLeft",
    onStepChanging: function () {
        // 插入一条默认归属组织信息
        if (!doubleNameFlag && orgList.length == 0) {
            doubleNameFlag = 1;
            orgList.push({
                orgTreeName: orgTreeName,
                orgFullName: orgName,
                orgTreeId: orgTreeId,
                orgId: orgId,
                doubleName: $('#psnName').val(), // 重谓名第一条默认显示用户名
                property: '10000', // 默认合同制
                refType: '10010', // 专兼职默认专制
                propertyName: '合同制',
                refTypeName: '专职',
                postName: ''
            });
            initOrgTable(orgList);
        }
        return formValidate.isAllPass();
    },
    onFinished: function (event, currentIndex) {
        savePersonnel();
    }
});

seajs.use('/vendors/lulu/js/common/ui/Validate', function (Validate) {
    var userAddForm = $('#userAddForm');
    formValidate = new Validate(userAddForm);
    formValidate.immediate();
    formValidate.isAllPass();
    userAddForm.find(':input').each(function () {
        $(this).hover(function () {
            formValidate.isPass($(this));
        });
    });
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
function getProperty (val) {
    $http.get('/tbDictionaryItem/getList/PROPERTY', {}, function (data) {
        var option = '<option></option>';
        for (var i = 0; i < data.length; i++) {
            var select = val === data[i].itemValue? 'selected' : '';
            option += "<option value='" + data[i].itemValue + "'" + select +  ">" + data[i].itemCnname +"</option>";
        }
        $('#property').append(option);
        $('#property').selectMatch();
    }, function (err) {
        console.log(err)
    })
}

// 获取专兼职字典数据
function getRefType (val) {
    $http.get('/tbDictionaryItem/getList/REF_TYPE', {}, function (data) {
        var option = '<option></option>';
        for (var i = 0; i < data.length; i++) {
            var select = val === data[i].itemValue? 'selected' : '';
            option += "<option value='" + data[i].itemValue + "'" + select +  ">" + data[i].itemCnname +"</option>";
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
    $('#tbMobileVoList').append("<div class='ui-input ui-input-del'> <input required> <a class='icon-del'><span class='fa fa-minus-circle'></span></a> </div>");
    $('.icon-del').on('click', function () {
        $(this).parent().remove();
    });
}
// 点击邮箱新增btn
function addEmail () {
    $('#tbEamilVoList').append("<div class='ui-input ui-input-del'> <input required> <a class='icon-del'><span class='fa fa-minus-circle'></span></a> </div>");
    $('.icon-del').on('click', function () {
        $(this).parent().remove();
    });
}

empty = Handlebars.compile($(".typeahead-menu").html());

engine = new Bloodhound({
    identify: function(o) { return o.id_str; },
    queryTokenizer: Bloodhound.tokenizers.whitespace,
    datumTokenizer: Bloodhound.tokenizers.obj.whitespace('psnName'),
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
                    return "<a href='edit.html?id=" + orgId + "&orgTreeId=" + orgTreeId + "&personnelId=" + row.personnelId +"'>查看</a>";
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
  .on('typeahead:asyncrequest', function(a, b) {
      console.log(a, b)
        $('.Typeahead-spinner').show();
        if ($("#psnName").val())
            initTable($("#psnName").val());
    })
  .on('typeahead:asynccancel typeahead:asyncreceive', function() {
        $('.Typeahead-spinner').hide();
    });

/***
 * 工作经历编辑
 */
function  initWorkTable (results) {
    workTable = $("#workTable").DataTable({
        'data': results,
        'searching': false,
        'autoWidth': false,
        'ordering': false,
        'columns': [
            { 'data': null, 'title': '序号', 'className': 'row-no' },
            { 'data': "name", 'title': '公司名称', 'className': 'row-name'},
            { 'data': "status", 'title': '任职状态', 'className': 'row-mobile' },
            { 'data': "beginTime", 'title': '任职开始时间', 'className': 'cert-no' },
            { 'data': "endTime", 'title': '任职结束时间', 'className': 'cert-no' },
            { 'data': null, 'title': '操作', 'className': 'status-code',
                'render': function (data, type, row, meta) {
                    return "<div><a class='work-edit' onclick='editWrokList(" + (meta.row + 1) + ")'>编辑</a><a class='work-delte' href='javascript: void(0);' onclick='deleteWrokList(" + meta.row + ")'>删除</a></div>";
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
// 工作经历信息编辑
function editWrokList (index) {
    $('#workEditBtn').hide();
    $('#workEdit').addClass( 'edit-form');
    var data;
    if (index) {
        workFlag = index;
        data = workTable.row(index - 1).data();
    }
    else
        workFlag = 0;
    var workEditTemplate = Handlebars.compile($("#workEditTemplate").html());
    var workEditHtml = workEditTemplate();
    $('#workEdit').html(workEditHtml);
    laydate.render({
        elem: '#beginTime'
    });
    laydate.render({
        elem: '#endTime'
    });
    $('#companyName').focus();
    if (data) {
        $('#companyName').val(data.name);
        $('#status').val(data.status);
        $('#beginTime').val(data.beginTime);
        $('#endTime').val(data.endTime);
    }
}
//新增/修改 工作经历信息
function addWrokList () {
    var name = $('#companyName').val();
    var status = $('#status').val();
    var beginTime = $('#beginTime').val();
    var endTime = $('#endTime').val();
    if (workFlag) {
        //修改工作经历信息
        var psnjobObj = psnjobList[workFlag-1];
        psnjobObj.name = name;
        psnjobObj.status = status;
        psnjobObj.beginTime = beginTime;
        psnjobObj.endTime = endTime;
        workTable.row(workFlag-1).data({
            'name': name,
            'status': status,
            'beginTime': beginTime,
            'endTime': endTime
        }).draw();
    }
    else {
        //新增工作经历信息
        psnjobList.push({name: name, status: status, beginTime: beginTime, endTime: endTime});
        workTable.row.add({
            'name': name,
            'status': status,
            'beginTime': beginTime,
            'endTime': endTime
        }).draw();
    }
    $('#workEditBtn').show();
    $('#workEdit').removeClass( 'edit-form');
    $('#workEdit').html('');
    console.log(psnjobList)
}
// 删除一条工作经历
function deleteWrokList (index) {
    workTable.row(index).remove().draw();
    psnjobList.splice(index);
}
// 取消工作经历信息编辑
function cancelWrokEdit () {
    $('#workEditBtn').show();
    $('#workEdit').removeClass( 'edit-form');
    $('#workEdit').html('');
}
initWorkTable([]);

/***
 * 教育经历编辑
 */
function  initEduTable (results) {
    eduTable = $("#eduTable").DataTable({
        'data': results,
        'searching': false,
        'autoWidth': false,
        'ordering': false,
        'columns': [
            { 'data': null, 'title': '序号', 'className': 'row-no' },
            { 'data': "school", 'title': '学校名称', 'className': 'row-name'},
            { 'data': "major", 'title': '专业', 'className': 'row-mobile' },
            { 'data': "begindate", 'title': '入学时间', 'className': 'cert-no' },
            { 'data': "enddate", 'title': '毕业时间', 'className': 'cert-no' },
            // { 'data': "schoolType", 'title': '学习方式', 'className': 'cert-no' },
            { 'data': "education", 'title': '学历', 'className': 'cert-no' },
            { 'data': "degree", 'title': '学位', 'className': 'cert-no' },
            { 'data': "lastEducation", 'title': '最高学历', 'className': 'cert-no',
                'render': function (data, type, row, meta) {
                    var str = '';
                    if (row.lastEducation == '0')
                        str = '<span>否</span>'
                    else
                        str = '<span>是</span>'
                    return str;
                }
            },
            { 'data': "lastDegree", 'title': '最高学位', 'className': 'cert-no',
                'render': function (data, type, row, meta) {
                    var str = '';
                    if (row.lastDegree == '0')
                        str = '<span>否</span>'
                    else
                        str = '<span>是</span>'
                    return str;
                }
             },
            { 'data': null, 'title': '操作', 'className': 'status-code',
                'render': function (data, type, row, meta) {
                    return "<div><a class='work-edit' onclick='editEduList(" + (meta.row + 1) + ")'>编辑</a><a class='work-delte' href='javascript: void(0);' onclick='deleteEduList(" + meta.row + ")'>删除</a></div>";
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
// 教育经历信息编辑
function editEduList (index) {
    $('#eduEditBtn').hide();
    $('#eduEdit').addClass( 'edit-form');
    var data;
    if (index) {
        eduFlag = index;
        data = eduTable.row(index - 1).data();
    }
    else
        eduFlag = 0;
    if (data) {
        var eduEditTemplate = Handlebars.compile($("#eduEditTemplate").html());
        var eduEditHtml = eduEditTemplate({eduData: data});
        $('#eduEdit').html(eduEditHtml);
        laydate.render({
            elem: '#begindate',
            value: new Date(data.begindate)
        });
        laydate.render({
            elem: '#enddate',
            value: new Date(data.enddate)
        });
    }
    else {
        var eduEditTemplate = Handlebars.compile($("#eduEditTemplate").html());
        var eduEditHtml = eduEditTemplate({eduData: {}});
        $('#eduEdit').html(eduEditHtml);
        laydate.render({
            elem: '#begindate'
        });
        laydate.render({
            elem: '#enddate'
        });
    }
    seajs.use('/vendors/lulu/js/common/ui/Select', function () {
        $('select').selectMatch();
    })
}
//新增/修改 教育经历信息
function addEduList () {
    var school = $('#school').val();
    var major = $('#major').val();
    var begindate = $('#begindate').val();
    var enddate = $('#enddate').val();
    var schoolType = $('#schoolType').val();
    var education = $('#education').val();
    var degree = $('#degree').val();
    var lastEducation = $('#lastEducation option:selected').val();
    var lastDegree = $('#lastDegree option:selected').val();
    if (eduFlag) {
        //修改工作经历信息
        var eduObj = eduList[eduFlag-1];
        eduObj.school = school;
        eduObj.major = major;
        eduObj.begindate = begindate;
        eduObj.enddate = enddate;
        eduObj.schoolType = schoolType;
        eduObj.education = education;
        eduObj.degree = degree;
        eduObj.lastEducation = lastEducation;
        eduObj.lastDegree = lastDegree;
        eduTable.row(eduFlag-1).data(eduObj).draw();
    }
    else {
        //新增工作经历信息
        var eduObj  = {};
        eduObj.school = school;
        eduObj.major = major;
        eduObj.begindate = begindate;
        eduObj.enddate = enddate;
        eduObj.schoolType = schoolType;
        eduObj.education = education;
        eduObj.degree = degree;
        eduObj.lastEducation = lastEducation;
        eduObj.lastDegree = lastDegree;
        eduList.push(eduObj);
        eduTable.row.add(eduObj).draw();
    }
    $('#eduEditBtn').show();
    $('#eduEdit').removeClass( 'edit-form');
    $('#eduEdit').html('');
    console.log(eduList)
}
// 删除一条教育信息
function deleteEduList (index) {
    eduTable.row(index).remove().draw();
    eduList.splice(index);
}
// 取消工作经历信息编辑
function cancelEduEdit () {
    $('#eduEditBtn').show();
    $('#eduEdit').removeClass( 'edit-form');
    $('#eduEdit').html('');
}
initEduTable([]);

/***
 * 家庭成员编辑
 */
function  initFamilyTable (results) {
    familyTable = $("#familyTable").DataTable({
        'data': results,
        'searching': false,
        'autoWidth': false,
        'ordering': false,
        'columns': [
            { 'data': null, 'title': '序号', 'className': 'row-no' },
            { 'data': "memName", 'title': '家庭成员姓名', 'className': 'row-name'},
            { 'data': "appellation", 'title': '称谓', 'className': 'row-mobile' },
            { 'data': "gender", 'title': '性别', 'className': 'cert-no',
                'render': function (data, type, row, meta) {
                    var str = '';
                    console.log(row.gender)
                    if (row.gender == "1")
                        str = '<span>男</span>'
                    else
                        str = '<span>女</span>'
                    return str;
                }
            },
            { 'data': "relaPhone", 'title': '联系电话', 'className': 'cert-no' },
            { 'data': "relaAddr", 'title': '联系地址', 'className': 'cert-no' },
            { 'data': null, 'title': '操作', 'className': 'status-code',
                'render': function (data, type, row, meta) {
                    return "<div><a class='work-edit' onclick='editFamilyList(" + (meta.row + 1) + ")'>编辑</a><a class='work-delte' href='javascript: void(0);' onclick='deleteFamilyList(" + meta.row + ")'>删除</a></div>";
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
// 家庭成员信息编辑
function editFamilyList (index) {
    $('#familyEditBtn').hide();
    $('#familyEdit').addClass( 'edit-form');
    var data;
    if (index) {
        familyFlag = index;
        data = familyTable.row(index - 1).data();
    }
    else
        familyFlag = 0;
    if (data) {
        var familyEditTemplate = Handlebars.compile($("#familyEditTemplate").html());
        var familyEditHtml = familyEditTemplate({familyData: data});
        $('#familyEdit').html(familyEditHtml);
    }
    else {
        var familyEditTemplate = Handlebars.compile($("#familyEditTemplate").html());
        var familyEditHtml = familyEditTemplate({familyData: {}});
        $('#familyEdit').html(familyEditHtml);
    }
    seajs.use('/vendors/lulu/js/common/ui/Select', function () {
        $('select').selectMatch();
    });
}
//新增/修改 家庭成员信息
function addFamilyList () {
    var memName = $('#memName').val();
    var appellation = $('#appellation').val();
    var gender = $('#gender2 option:selected').val();
    var relaPhone = $('#relaPhone').val();
    var relaAddr = $('#relaAddr').val();
    if (familyFlag) {
        //修改家庭成员信息
        var familyObj = familyList[familyFlag-1];
        familyObj.memName = memName;
        familyObj.appellation = appellation;
        familyObj.gender = gender;
        familyObj.relaPhone = relaPhone;
        familyObj.relaAddr = relaAddr;
        familyTable.row(familyFlag-1).data(familyObj).draw();
    }
    else {
        //新增工作经历信息
        var familyObj  = {};
        familyObj.memName = memName;
        familyObj.appellation = appellation;
        familyObj.gender = gender;
        familyObj.relaPhone = relaPhone;
        familyObj.relaPhone = relaPhone;
        familyObj.relaAddr = relaAddr;
        familyList.push(familyObj);
        familyTable.row.add(familyObj).draw();
    }
    $('#familyEditBtn').show();
    $('#familyEdit').removeClass( 'edit-form');
    $('#familyEdit').html('');
}
// 删除一条家庭成员信息
function deleteFamilyList (index) {
    familyTable.row(index).remove().draw();
    familyList.splice(index);
}
// 取消家庭成员信息编辑
function cancelFamilyEdit () {
    $('#familyEditBtn').show();
    $('#familyEdit').removeClass( 'edit-form');
    $('#familyEdit').html('');
}
initFamilyTable([]);

/***
 * 归属组织编辑
 */
function  initOrgTable (results) {
    orgTable = $("#orgTable").DataTable({
        'data': results,
        'searching': false,
        'autoWidth': false,
        'ordering': false,
        'columns': [
            { 'data': null, 'title': '序号', 'className': 'row-no' },
            { 'data': "orgTreeName", 'title': '组织树名称', 'className': 'row-name'},
            { 'data': "orgFullName", 'title': '组织全称', 'className': 'row-mobile' },
            { 'data': "doubleName", 'title': '重名称谓', 'className': 'cert-no' },
            { 'data': "propertyName", 'title': '用工性质', 'className': 'cert-no' },
            { 'data': "refTypeName", 'title': '专兼职', 'className': 'cert-no' },
            { 'data': "postName", 'title': '职位', 'className': 'cert-no' },
            { 'data': null, 'title': '操作', 'className': 'status-code',
                'render': function (data, type, row, meta) {
                    return "<div><a class='work-edit' onclick='editOrgList(" + (meta.row + 1) + ")'>编辑</a><a class='work-delte' href='javascript: void(0);' onclick='deleteOrgList(" + meta.row + ")'>删除</a></div>";
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
// 归属组织信息编辑
function editOrgList (index) {
    $('#orgEditBtn').hide();
    $('#orgEdit').addClass( 'edit-form');
    var data = {};
    if (index) {
        orgFlag = index;
        data = orgTable.row(index - 1).data();
    }
    else
        orgFlag = 0;
    if (data.orgTreeName) {
        var orgEditTemplate = Handlebars.compile($("#orgEditTemplate").html());
        var orgEditHtml = orgEditTemplate({orgData: data});
        $('#orgEdit').html(orgEditHtml);
    }
    else {
        var orgEditTemplate = Handlebars.compile($("#orgEditTemplate").html());
        var orgEditHtml = orgEditTemplate({orgData: {orgTreeName: orgTreeName, orgFullName: orgName}});
        $('#orgEdit').html(orgEditHtml);
    }
    getProperty(data.property);
    getRefType(data.refType);
    seajs.use('/vendors/lulu/js/common/ui/Select', function () {
        $('select').selectMatch();
    });
}
//新增/修改 归属组织信息
function addOrgList () {
    var doubleName = $('#doubleName').val();
    var property = $('#property option:selected').val();
    var refType = $('#refType option:selected').val();
    var propertyName = $('#property option:selected').text();
    var refTypeName = $('#refType option:selected').text();
    var postName = $('#postName').val();
    if (orgFlag) {
        //修改归属组织信息
        var orgObj = orgList[orgFlag-1];
        orgObj.orgTreeName = orgTreeName;
        orgObj.orgFullName = orgName;
        orgObj.orgTreeId = orgTreeId;
        orgObj.orgId = orgId;
        orgObj.doubleName = doubleName;
        orgObj.property = property;
        orgObj.refType = refType;
        orgObj.propertyName = propertyName;
        orgObj.refTypeName = refTypeName;
        orgObj.postName = postName;
        orgObj.postId = orgPostList[0].postId;
        orgTable.row(orgFlag-1).data(orgObj).draw();
    }
    else {
        //新增归属组织信息
        var orgObj  = {};
        orgObj.orgTreeName = orgTreeName;
        orgObj.orgFullName = orgName;
        orgObj.orgTreeId = orgTreeId;
        orgObj.orgId = orgId;
        orgObj.doubleName = doubleName;
        orgObj.property = property;
        orgObj.refType = refType;
        orgObj.propertyName = propertyName;
        orgObj.refTypeName = refTypeName;
        orgObj.postName = postName;
        orgObj.postId = orgPostList.postId;
        orgList.push(orgObj);
        orgTable.row.add(orgObj).draw();
    }
    $('#orgEditBtn').show();
    $('#orgEdit').removeClass( 'edit-form');
    $('#orgEdit').html('');
}
// 删除一条归属组织信息
function deleteOrgList (index) {
    orgTable.row(index).remove().draw();
    orgList.splice(index);
}
// 取消归属组织信息编辑
function cancelOrgEdit () {
    $('#orgEditBtn').show();
    $('#orgEdit').removeClass( 'edit-form');
    $('#orgEdit').html('');
}

Handlebars.registerHelper('eq', function(v1, v2, opts) {
    if(v1 == v2){
        return opts.fn(this);
    }
    else
        return opts.inverse(this);
});


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
    $('#tbMobileVoList').find(':input').each(function (index) {
        var firstFlag = index == 0? 1: 0;
        mobileList.push({contactType: 1, content: $(this).val(), firstFlag: firstFlag});
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
        psonOrgVoList: orgList,
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


