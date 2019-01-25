var isIE=!!window.ActiveXObject;
var isIE8=isIE&&document.documentMode<9;

var positionCodeCur = getQueryString('positionCode');
// var positionId = getQueryString('positionId');
var posName = getQueryString('posName');
var lChBox =document.getElementById("lowerCheckBox");   //是否显示下级职位
var posTable;
var isCheck = 0;
var roleList = [];
var posRole = parent.posRole;
var posFullName = "";
var positionCodeList = [];

// 获取组织完整路径
function getPosExtInfo () {
    var pathArry = parent.nodeArr;
    var pathStr = '';
    if (pathArry && pathArry.length > 0) {
        for (var i = pathArry.length - 1; i >= 0; i--) {
            var node = pathArry[i].node;
            if (pathArry[i].current) {
                pathStr +=  '<span class="breadcrumb-item"><a href="javascript:void(0);">' + node.name + '</a></span>';
            } else {
                pathStr += '<span class="breadcrumb-item"><a href="javascript:void(0);" onclick="parent.openTreeById('+positionCodeCur+','+node.id+')">' + node.name + '</a><span class="breadcrumb-separator" style="margin: 0 9px;">/</span></span>';
            }
            posFullName += node.name + ' / '; 
        }
        posFullName = posFullName.toString().substring(0,posFullName.toString().length-2);
        $('.breadcrumb').html(pathStr);
    }
}

//初始化职位表格
function initPosTable(isCheck,search){
    $("#perms").empty();
    positionCodeList = [];
    posTable = $("#posTable").DataTable({
        'destroy':true,
        'searching': false,
        'autoWidth': true,
        'ordering': true,
        'lSort': true,
        'info': true,
        "scrollY": "375px",
        'scrollCollapse': true,
        'columns': [
            { 'data': null, 'title': '序号', 'className': 'row-num' ,
                'render': function (data, type, row, meta) {
                    return meta.row + 1 + meta.settings._iDisplayStart;
                }
            },
            { 'data': "positionName", 'title': '职位名称', 'className': 'row-pos',
                'render': function (data, type, row, meta) {
                    positionCodeList.push(row.positionCode);
                    return "<a href='javascript:void(0);' title='"+row.positionName+"' onclick='setPosInfo("+meta.row+")'>"+row.positionName+"</span>";
                }
            },
            { 'data': "roleNames", 'title': '所含角色', 'className': 'row-role' ,
                'render': function (data, type, row, meta) {
                    return "<span title='"+row.roleNames+"' style='cursor:pointer;'>"+row.roleNames+"</span>";
                }
            },
            { 'data': "regionName", 'title': '管理区域', 'className': 'row-area' },
            { 'data': "orgPositionNum", 'title': '职位授予组织数', 'className': 'row-orgNum' },
            { 'data': "orgUserNum", 'title': '职位授予用户数', 'className': 'row-userNum' }
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
            param.positionCode = positionCodeCur;
            param.isSearchlower = isCheck;
            param.search = search;
            $http.get('/sysPosition/getPositionRelPage', param, function (result) {
                var returnData = {};
                // returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                returnData.recordsTotal = result.total;//返回数据全部记录
                returnData.recordsFiltered = result.total;//后台不实现过滤功能，每次查询均视作全部结果
                returnData.data = result.records;//返回的数据列表
                //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                callback(returnData);
            }, function (err) {
            })
        }
    });

    if(posRole){
        roleList = posRole.split(','); 
    }
    for(var i=0;i<roleList.length;i++){ 
        $("#perms").append("<span class='perTab'>"+roleList[i]+"</span>");
    }
}

function setPosInfo(id){
    var positionCode = positionCodeList[id];
    window.location.href = "posInfo.html?positionCode=" + positionCode + "&posName=" + posName + "&posFullName="+posFullName+"&positionCodeCur="+positionCodeCur;
}

function boxClick(){            //点击复选框
    if(lChBox.checked == true){
        isCheck = 1;
        if(isIE8){
            $(".ui-checkbox").css("background-position","0px -40px");
        }
    }else{
        isCheck = 0;
        if(isIE8){
            $(".ui-checkbox").css("background-position","0px 0px");
        }
    }
    initPosTable(isCheck,'');
}

$("#addBtn").on('click',function(){
    window.location.href = "add.html?posName=" + posName + "&posFullName=" + posFullName + "&positionCode=" + positionCodeCur;
})

$("#infoBtn").on('click',function(){
    window.location.href = "posInfo.html?positionCode=" + positionCodeCur + "&posName=" + posName + "&posFullName="+posFullName+"&positionCodeCur="+positionCodeCur;
})

$("#posName").on('click',function(){
    window.location.href = "posInfo.html?positionCode=" + positionCodeCur + "&posName=" + posName + "&posFullName="+posFullName+"&positionCodeCur="+positionCodeCur;
})

$("#posName").html(posName);
getPosExtInfo();
initPosTable(isCheck,"");
