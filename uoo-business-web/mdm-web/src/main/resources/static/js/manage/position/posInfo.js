var positionCode = getQueryString('positionCode');  //当前职位id
var positionCodeCur = getQueryString('positionCodeCur');
var posFullName = getQueryString('posFullName');
var posName = getQueryString('posName');
var roleList = [];
var locationList = [];
var pPosList = [];
var positionId;
var toastr = window.top.toastr;

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
    isInputNull("posNum",result.positionCode);
    isInputNull("supPos",result.pPositionName);
    isInputNull("sort",result.sortNum);
    isInputNull("notes",result.notes);
    $('#role').addTag(roleList);
    if(result.regionName){
        locationList = [{"id":result.regionNbr,"name":result.regionName}];
        $('#location').addTag(locationList);
    }
    if(result.pPositionId){
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

var logTable = $("#logTable").DataTable({
    'data': logData,
    'searching': false,
    'autoWidth': false,
    'ordering': true,
    'initComplete': function (settings, json) {
        console.log(settings, json)
    },
    "scrollY": "375px",
    'scrollCollapse': true,
    'columns': [
        { 'data': "num", 'title': '订单号', 'className': 'row-num' },
        { 'data': "explain", 'title': '操作说明', 'className': 'row-explain'},
        { 'data': "person", 'title': '操作人', 'className': 'row-psn' },
        { 'data': "org", 'title': '操作人组织', 'className': 'row-org' },
        { 'data': "acct", 'title': '操作账号', 'className': 'row-acct' },
        { 'data': "date", 'title': '时间', 'className': 'row-date' },
        { 'data': "state", 'title': '状态', 'className': 'row-state' }
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
    'dom': '<"top"f>t<"bottom"ipl>'
});

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
$("#posName").html(posName);
$(".breadcrumb").html(posFullName);
getPosition();