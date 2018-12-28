var orgId = getQueryString('orgId');
var orgName = getQueryString('orgName');
var orgFullName = getQueryString('orgFullName');
var hType = getQueryString('hType');
var orgTreeId = getQueryString('orgTreeId');
var orgRootId = getQueryString('orgRootId');
var tabPage = getQueryString('tabPage');
var acctId = getQueryString('acctId');
var orgTreeName = getQueryString('orgTreeName');

var personnelId;
var orgTable;
var orgNum = 0;
var slaveOrgIdList = [];
var treeNameList = [];
var toastr = window.top.toastr;

$('#statusCd').get(0).selectedIndex=0; //判断状态，默认生效
$('#cerType').get(0).selectedIndex=0;  //判断证件类型,默认身份证


// lulu ui tips插件
seajs.use('/vendors/lulu/js/common/ui/Tips', function () {
  $('#defaultPswTel').tips({
      align: 'right'
  });
});

//添加数组IndexOf方法
if (!Array.prototype.indexOf){
  Array.prototype.indexOf = function(elt /*, from*/){
    var len = this.length >>> 0;

    var from = Number(arguments[1]) || 0;
    from = (from < 0)
         ? Math.ceil(from)
         : Math.floor(from);
    if (from < 0)
      from += len;

    for (; from < len; from++){
      if (from in this && this[from] === elt)
        return from;
    }
    return -1;
  };
}

//获取人员头像
function getPsnImage(){
  $http.get('/psnImage/getPsnImage', {
      personnelId: personnelId
  }, function (data) {
      if(data != null){
          imgUrl =  "data:image/png;base64," + data.image;
          $("#psnImg").attr("src",imgUrl);
      }
  }, function (err) {
      toastr.error(err);
  })
}

function getUser(acctId) {           //查看并编辑主账号
    var date = new Date();
    $http.get('/user/getUser', {   
        acctId: acctId,
        userType: "1",
        _:date.getTime()
    }, function (data) {
        personnelId = data.personnelId;
        orgNum = data.acctOrgVoPage.records.length;
        initAcctInfoCheck(data);
        initOrgTable(data.acctOrgVoPage.records);
        initSlaveOrgTable(data.slaveAcctOrgVoPage.records);
    }, function (err) {

    })
}

function initOrgTable(results){         //主账号组织数据表格
    orgTable = $("#orgTable").DataTable({
    'data': results,
    'destroy':true,
    'searching': false,
    'autoWidth': false,
    'ordering': true,
    'paging': false,
    'info': false,
    "scrollY": "375px",
    'scrollCollapse': true,
    'columns': [
        { 'data': "id", 'title': '序号', 'className': 'row-id'},
        { 'data': "orgTreeName", 'title': '组织树', 'className': 'row-orgTree'},
        { 'data': "fullName", 'title': '组织名称', 'className': 'row-fullName' ,
        'render': function (data, type, row, meta) {
          if(row.fullName != null){
              return "<span class='spanPoint' title='"+row.fullName+"'>"+row.fullName+"</span>";
            }else{
              return "";
            }
            }
        },
        { 'data': "orgId", 'title': 'orgId', 'className': 'row-orgId'}
    ],
    'language': {
        'emptyTable': '没有数据',  
        'loadingRecords': '加载中...',  
        'processing': '查询中...',  
        'search': '检索:',  
        'lengthMenu': ' _MENU_ ',  
        'zeroRecords': '没有数据', 
        'infoEmpty': '没有数据'
    }
  });
}

function initSlaveOrgTable(results){    //从账号组织数据
  var subTable = $("#subInfoTable").DataTable({
    'data': results,
    'destroy':true,
    'searching': false,
    'autoWidth': false,
    'ordering': true,
    'paging': false,
    'info': false,
    "scrollY": "375px",
    'scrollCollapse': true,
    'columns': [
        { 'data': "id", 'title': '序号', 'className': 'row-number' },
        { 'data': "slaveAcct", 'title': '账号名', 'className': 'row-acc' ,
        'render': function (data, type, row, meta) {
            return '<span title="'+ row.slaveAcct +'">'+ row.slaveAcct +'</span>';
        }
      },
        { 'data': "slaveAcctType", 'title': '从账号类型', 'className': 'row-acctype' },
        { 'data': "orgTreeName", 'title': '组织树', 'className': 'row-orgtree' },
        { 'data': "systemName", 'title': '系统', 'className': 'row-system'
          // 'render': function (data, type, row, meta) {
          //     return '营销系统';
          // }
        },
        { 'data': "fullName", 'title': '归属组织', 'className': 'row-org' ,
        'render': function (data, type, row, meta) {
          if(row.fullName != null){
            return "<span class='spanPoint' title='"+row.fullName+"'>"+row.fullName+"</span>";
          }else{
            return "";
          }
        }
      },
        { 'data': "statusCd", 'title': '状态', 'className': 'row-state' ,
        'render': function (data, type, row, meta) {
          return "生效";
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
    }
  });
}

function initAcctInfoCheck(results){     //初始化用户信息(查看)

  getPsnImage();
  
  isNull("#psnNameLable",results.psnName);
  isNull("#mobileLable",results.mobilePhone);
  isNull("#emailLable",results.eamil);
  isNull("#acctLable",results.tbAcct.acct);
  isNull("#psnNumLable",results.psnNbr);
  isNull("#cerNoLable",results.certNo);
  isNull("#effectDateLable",results.tbAcct.enableDate);
  isNull("#invalidDateLable",results.tbAcct.disableDate);

  for(var i = 0; i <results.tbRolesList.length; i++){
    $("#nameAndRole").append($("<span class='roleTag'>"+results.tbRolesList[i].roleName+"</span>"));
  }
}

function isEnableStatus(statusCd){    //判断状态
  if(statusCd == "1000"){                
    $('#statusCd').get(0).selectedIndex=0;
  }else if(statusCd == "1100"){
    $('#statusCd').get(0).selectedIndex=1;
  }
}

function isNull(s,r){    //判断是否为null
    if(r == null){
      $(s).text("");
    }else{
      $(s).text(r);
    }
}

getUser(acctId);






