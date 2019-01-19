var isIE=!!window.ActiveXObject;
var isIE8=isIE&&document.documentMode<9;

var orgId = getQueryString('orgId');
var orgName = getQueryString('orgName');
var orgTreeId = getQueryString('orgTreeId');
var lChBox =document.getElementById("lowerCheckBox");   //是否显示下级人员
var orgFullName = '';
var table;
var isCheck = 0;
var query,
    delayTime = 500;
var loading = parent.loading;

// 获取组织完整路径
function getOrgExtInfo () {
    var pathArry = parent.nodeArr;
    var pathStr = '';
    if (pathArry && pathArry.length > 0) {
        for (var i = pathArry.length - 1; i >= 0; i--) {
            var node = pathArry[i].node;
            if (pathArry[i].current) {
                pathStr +=  '<span class="breadcrumb-item"><a href="javascript:void(0);">' + node.name + '</a></span>';
            } else {
                pathStr += '<span class="breadcrumb-item"><a href="javascript:void(0);" onclick="parent.openTreeById('+orgId+','+node.id+')">' + node.name + '</a><span class="breadcrumb-separator" style="margin: 0 9px;">/</span></span>';
            }
            orgFullName += node.name + '/'; 
        }
        orgFullName = orgFullName.toString().substring(0,orgFullName.toString().length-1);
        $('.breadcrumb').html(pathStr);
    }
}

function initMainTable(isCheck,search){
    var date = new Date();
    table = $("#mainTable").DataTable({
        'destroy':true,
        'searching': false,
        'autoWidth': false,
        'ordering': true,
        'lSort': true,
        'info': true,
        "scrollY": "395px",
        'scrollCollapse': true,
        'columns': [
            { 'data': "psnNbr", 'title': '人员编码', 'className': 'row-psnNbr' },
            { 'data': "psnName", 'title': '姓名', 'className': 'row-psnName'},
            { 'data': "acct", 'title': '主账号', 'className': 'row-typeName' ,
                'render': function (data, type, row, meta) {
                    return '<a href="editMainAccount.html?curOrgId='+row.orgId+'&curOrgTreeId='+row.orgTreeId+'&orgTreeId=' + orgTreeId + 
                            '&orgName=' + encodeURI(orgName) + '&orgId=' + orgId + '&acctId='+ row.accId + '&hType=mh">'+ row.acct +'</a>';
                }
            },
            { 'data': "slaveAcct", 'title': '从账号', 'className': 'row-acc' ,
                'render': function (data, type, row, meta) {
                   if(row.slaveAcct == null){
                        return "-";
                   }else{
                        return '<a href="editSubAccount.html?curSlaveOrgTreeId='+orgTreeId+'&orgTreeId=' + orgTreeId + '&orgName=' + encodeURI(orgName) +
                                '&orgId=' + orgId + '&mainAcctId='+ row.accId +'&acctId='+ row.slaveAcctId +'&statusCd='+row.statusCd+'&hType=mh">'+ row.slaveAcct +'</a>';
                   }
                }
            },
            { 'data': "orgName", 'title': '归属组织', 'className': 'row-org' ,
                'render': function (data, type, row, meta) {
                    return '<span title="'+ row.orgName +'" class="orgNamePoint">'+row.orgName+'</span>';
                }
            },
            { 'data': "statusCd", 'title': '状态', 'className': 'row-statusCd' ,
                'render': function (data, type, row, meta) {
                    if(row.statusCd == 1000){
                        return '正常';
                    }else{
                        return '锁定';
                    }
                }
            }
        ],
        'language': {
            'emptyTable': '没有数据',  
            'loadingRecords': '加载中...',  
            'processing': '查询中...',  
            'search': '搜索：',  
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
            param.orgTreeId = orgTreeId;
            param.orgId = orgId;
            param.isSearchlower = isCheck;
            param.search = search;
            param._ = date.getTime();
            $http.get('/orgPersonRel/getUserOrgRelPage', param, function (result) {
                var returnData = {};
                // returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                returnData.recordsTotal = result.total;//返回数据全部记录
                returnData.recordsFiltered = result.total;//后台不实现过滤功能，每次查询均视作全部结果
                returnData.data = result.records;//返回的数据列表
                //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                callback(returnData);
                loading.screenMaskDisable('container');
            }, function (err) {
            })
        }
    });
}

// 搜索组织
// function search () {
//     query = $('.ui-input-search').val();
//     clearTimeout(this.timer);
//     // 添加的延时
//     this.timer = setTimeout(function(){
//         initMainTable(isCheck, query);
//     }, delayTime);
// }

// 搜索组织
function search () {
    loading.screenMaskEnable('container');
    query = $('.ui-input-search').val();
    initMainTable(isCheck, query);
    // loading.screenMaskDisable('container');
}


$('#orgName').html(orgName);
getOrgExtInfo();
initMainTable(isCheck,'');

function boxClick(){            //点击复选框
    sortFlag = 0;
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
    // initMainTable(isCheck,'');
    // search();
}

//新增账号
function openAddDialog() {
    parent.layer.open({
        type: 2,
        title: '新增账号',
        shadeClose: true,
        shade: 0.8,
        area: ['70%', '85%'],
        maxmin: true,
        content: 'addDialog.html?orgName=' + encodeURI(orgName) +'&orgId=' + orgId + '&orgTreeId=' + orgTreeId + 
                    "&orgFullName=" + encodeURI(orgFullName)+"&businessName="+encodeURI(parent.businessName),
        btn: ['确认', '取消'],
        yes: function(index, layero){
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            var selectObj = iframeWin.getSelectUser();
            if (selectObj.length > 0) {
                // console.log(selectObj[0].personnelId);
                getPsnUser(selectObj[0].personnelId);
            }
            parent.layer.close(index);
        },
        btn2: function(index, layero){},
        cancel: function(){}
    });
}

//主账号跳转
function getPsnUser(personnelId){       
    var url = "";
    $http.get('/user/getPsnUser', {    
        personnelId: personnelId,
        userType: "1"
      }, function (data) {
        if(data.tbAcct != null){
            url = "editMainAccount.html?acctId="+ data.tbAcct.acctId +"&orgFullName=" + encodeURI(orgFullName) + "&orgTreeId=" + orgTreeId + 
                    "&orgName=" + encodeURI(orgName) + "&orgId=" + orgId + "&hType=mh" + "&orgTreeName="+encodeURI(parent.businessName);
        }else{
            url = "addMainAccount.html?orgFullName=" + encodeURI(orgFullName) + "&orgTreeId=" + orgTreeId + "&orgName=" + encodeURI(orgName) +
                    "&orgId=" + orgId + "&personnelId=" + personnelId + "&hType=mh" + "&orgTreeName="+encodeURI(parent.businessName);
        }
        window.location.href = url;
      }, function (err) {
    
      })
}