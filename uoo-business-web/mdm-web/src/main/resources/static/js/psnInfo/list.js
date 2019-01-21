var acctId = getQueryString('acctId');
var personnelId = getQueryString('personnelId');

var acctInfoList = [];

var cerTypeList = window.top.dictionaryData.certType();
var statusCdList = window.top.dictionaryData.statusCd();

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

  })
}

//查看并编辑主账号
function getUser(acctId) {           
    $http.get('/user/getUser', {   
        acctId: acctId,
        userType: "1"
    }, function (data) {
        initAcctInfo(data);
        initAcctInfoCheck(data);
        setAcctInfoTables();
    }, function (err) {

    })
}

//初始化主从账号信息
function initAcctInfo(results){
    var acct = results.acctOrgVoPage.records;
    var slave = results.slaveAcctOrgVoPage.records;
    acctInfoList = [];
    for(var i=0;i<acct.length;i++){
        acctInfoList.push({"acct":acct[i],"slaveAcct":[]});
    }
    for(var i=0;i<acctInfoList.length;i++){
        for(var j=0;j<slave.length;j++){
          if(acctInfoList[i].acct.orgTreeId == slave[j].orgTreeId && acctInfoList[i].acct.orgId == slave[j].orgId){
              acctInfoList[i].slaveAcct.push(slave[j]);
          }
        }
    }
    // console.log(acctInfoList);
}

//展示主从账号信息
function setAcctInfoTables(){
    var acctHtml = ""; 
    var currentId;
    $("#acctOrgDiv").empty();
    for(var i=0;i<acctInfoList.length;i++){
        acctHtml += "<div style=''>"+
                        "<div class='curDiv' style='padding:10px 0;'>"+
                            "<span class='Name Dot Gray3 id='orgTreeName_"+i+"'>"+acctInfoList[i].acct.orgTreeName+"</span>"+
                            "<span class='Tag' style='cursor:pointer;' title='"+acctInfoList[i].acct.fullName+"' id='orgName_"+i+"'>"+acctInfoList[i].acct.orgName+"</span></div>"+
                        "<div id='table-container_"+i+"' style='width: 100%; font-size: 14px; overflow: hidden;margin-left:-3.3%;'>"+
                        "<table id='orgTable_"+i+"' class='stripe' width='100%'></table></div>"; 
    }
    $("#acctOrgDiv").append(acctHtml);

    for(var i=0;i<acctInfoList.length;i++){
        if(acctInfoList[i].slaveAcct.length != 0){
            $("#table-container_"+i).css("padding","10px 10px 30px 10px");
            $("#orgTable_"+i).DataTable({
              'data': acctInfoList[i].slaveAcct,
              'destroy':true,
              'searching': false,
              'autoWidth': false,
              'ordering': true,
              'paging': false,
              'info': false,
              "scrollY": "375px",
              'scrollCollapse': true,
              'columns': [
                  { 'data': null, 'title': '序号', 'className': 'row-number' ,
                      'render': function (data, type, row, meta) {
                          return meta.row + 1 + meta.settings._iDisplayStart;
                      }
                  },
                  { 'data': "slaveAcct", 'title': '从账号', 'className': 'row-acc' ,
                  'render': function (data, type, row, meta) {
                      return '<a title="'+ row.slaveAcct +'" href="editSubAccount.html?curOrgId='+curOrgId+'&curOrgTreeId='+curOrgTreeId+
                              '&orgTreeId=' + orgTreeId + '&toMainType=' + hType +'&orgName=' + encodeURI(orgName) + '&orgId=' + orgId +
                              '&hType=th&mainAcctId='+acctId+'&acctId='+row.slaveAcctId+'&statusCd='+row.statusCd+'">'+row.slaveAcct+'</a>';
                  }
                },
                  { 'data': "slaveAcctType", 'title': '类型', 'className': 'row-acctype' },
                  { 'data': "systemName", 'title': '系统', 'className': 'row-system'},
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
    }
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
  
  for(var i=0;i<cerTypeList.length;i++){
    if(results.certType === cerTypeList[i].itemValue){
      $("#cerNoTxt").text(cerTypeList[i].itemCnname+"号码：");
      break;
    }
  }

  for(var i = 0; i <results.tbRolesList.length; i++){
    if(i != 0 && i%3 == 0){
      $("#roleTab").append($("<br><span class='roleTag'>"+results.tbRolesList[i].roleName+"</span>"));
    }else if(i != 0 && i%3 != 0){
      $("#roleTab").append($("<span class='roleTag'>"+results.tbRolesList[i].roleName+"</span>"));
    }else{
      $("#roleTab").append($("<span class='roleTag'>"+results.tbRolesList[i].roleName+"</span>"));
    }
  }
  userRoleList = results.tbRolesList;
}

function isNull(s,r){    //判断是否为null
    if(r == null){
      $(s).text("");
    }else{
      $(s).text(r);
    }
}

getUser(acctId);