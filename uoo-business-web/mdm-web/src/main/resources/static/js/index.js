var loading = new Loading();
var loadingHome = new Loading();
var dictionaryData = new Dictionary();
var account;
var personnelId;
// toastr
toastr.options = {
  "closeButton": false,
  "debug": false,
  "newestOnTop": false,
  "progressBar": false,
  "positionClass": "toast-top-center", //弹出的位置
  "preventDuplicates": false,
  "onclick": null, //点击消息框自定义事件 
  "showDuration": "300", //显示动作时间 
  "hideDuration": "1000", //隐藏动作时间 
  "timeOut": "2000", //自动关闭超时时间
  "extendedTimeOut": "1000", //控制时间
  "showEasing": "swing",
  "hideEasing": "linear",
  "showMethod": "fadeIn", //显示的方式，和jquery相同 
  "hideMethod": "fadeOut" //隐藏的方式，和jquery相同
};

loading.screenMaskEnable('container');
loadingHome.screenMaskEnable('LAY_app_body');

function initUserInfo(){  //初始化首页人员信息          
    $http.get('/system/getCurrentLoginUserInfo', { }, 
    function (data) {
        account = data.accout;
        $("#psnName").text(data.userName);
        // getPsnId();
    }, function (err) {
    })
}

function initUserPermission(){    //初始化人员权限
    $http.get('/system/getAccoutMenu', { }, 
    function (data) {
        initSideBar(data);
        // layui admin
        layui.config({
            base: '/vendors/layuiadmin/' //静态资源所在路径
            }).extend({
            index: 'lib/index' //主入口模块
            }).use('index');
    }, function (err) {
        loading.screenMaskDisable('container');
    })
}

function initSideBar(results){     //初始化侧边菜单
    var pemList = '';
    var parList = [];
    var childList = [];
    var flag = 0;

    for(var i = 0;i<results.length;i++){
        if(results[i].parentMenuCode === null || results[i].parentMenuCode === ""){
            parList.push(results[i]);
        }else{
            childList.push(results[i]);
        }
    }
    for(var i = 0;i<parList.length;i++){
        var icon = setIcon(parList[i].menuName);
        var dd = "<dl class='layui-nav-child'>";
        
        for(var j=0;j<childList.length;j++){
            if(childList[j].parentMenuCode == parList[i].menuCode){
                flag = 1;
                dd += "<dd><a lay-href='" + childList[j].menuUrl + "'>" + childList[j].menuName + "</a></dd>"
            }
        }
        if(flag == 1){
            pemList += "<li class='layui-nav-item'><a href='javascript:;' lay-tips='" + parList[i].menuName + "'>" +
                icon + "</i><cite>" + parList[i].menuName + 
                "</cite><span class='layui-nav-more'></span></a>" + dd + "</dl></li>";
        }else{
            pemList += "<li class='layui-nav-item'><a lay-href='" + parList[i].menuUrl + "' lay-tips='" + parList[i].menuName + "'>" +
                icon + "</i><cite>" + parList[i].menuName + "</cite></a></li>";
        }
        
        flag = 0;
    }
    $("#LAY-system-side-menu").append(pemList);
    loading.screenMaskDisable('container');
}

function setIcon(menuName){         //设置菜单icon
    var icon;
    switch(menuName){
        case '组织管理': 
            icon = "<i class='layui-icon layui-icon-component'>";
            break;

        case '人员管理':
            icon = "<i class='layui-icon layui-icon-form'>";
            break;

        case '账号管理':
            icon = "<i class='layui-icon layui-icon-release'>";
            break;

        case '职位管理':
            icon = "<i class='layui-icon layui-icon-star'>";
            break;

        case '权限管理':
            icon = "<i class='layui-icon layui-icon-auz'>";
            break;

        case '角色管理':
            icon = "<i class='layui-icon layui-icon-user'>";
            break;

        case '区域管理':
            icon = "<i class='layui-icon layui-icon-app'>";
            break;

        case '资源管理':
            icon = "<i class='layui-icon layui-icon-template'>";
            break;
        default:
            icon = "<i class='layui-icon layui-icon-template'>";
            break;
    }
    return icon;
}


// 获取字典数据
function getDictionaryData () {
    $http.get('/tbDictionaryItem/getAllList', {}, function (data) {
        dictionaryData.cityVillage(data.city_VILLAGE);
        dictionaryData.scale(data.scale);
        dictionaryData.orgPostLevel(data.org_POST_LEVEL);
        dictionaryData.columType(data.colum_TYPE);
        dictionaryData.colNullable(data.col_NULLABLE);
        dictionaryData.editTable(data.editable);
        dictionaryData.statusCd(data.status_CD);
        dictionaryData.yesNo(data.yes_NO);
        dictionaryData.ruleOperator(data.rule_OPERATOR);
        dictionaryData.nationality(data.nationality);
        dictionaryData.gender(data.gender);
        dictionaryData.nation(data.nation);
        dictionaryData.marriage(data.marriage);
        dictionaryData.pliticalStatus(data.plitical_STATUS);
        dictionaryData.schoolType(data.school_TYPE);
        dictionaryData.memRelation(data.mem_RELATION);
        dictionaryData.certType(data.cert_TYPE);
        dictionaryData.acctType(data.acct_TYPE);
        dictionaryData.userHostType(data.user_HOST_TYPE);
        dictionaryData.orgTreeType(data.org_TREE_TYPE);
        dictionaryData.relaType(data.rela_TYPE);
        dictionaryData.property(data.property);
        dictionaryData.refType(data.ref_TYPE);
        dictionaryData.roleType(data.role_TYPE);
        dictionaryData.grantObjType(data.grant_OBJ_TYPE);
        dictionaryData.privType(data.priv_TYPE);
        dictionaryData.privRefType(data.priv_REF_TYPE);
        dictionaryData.compType(data.comp_TYPE);
        dictionaryData.intfType(data.intf_TYPE);
        dictionaryData.connectType(data.connect_TYPE);
        dictionaryData.regionType(data.region_TYPE);
        dictionaryData.locType(data.loc_TYPE);
        dictionaryData.postType(data.post_TYPE);
        dictionaryData.positionType(data.position_TYPE);
        dictionaryData.nodeType(data.nodeType);
        dictionaryData.areaType(data.areaType);
        dictionaryData.countType(data.countType);
        dictionaryData.contractType(data.contractType);
    }, function (err) {

    })
}

function getPsnId(){
    $http.get('/acct/getCurrentAcct', {}, 
    function (data) {
        $("#psnInfo").attr("lay-href","/inaction/psnInfo/index.html?personnelId=" + data.personnelId+"&acctId=" + data.acctId);
    }, function (err) {
    })
}

function logOut(){  //退出登录
    parent.layer.confirm('是否退出登录?', {
        icon: 0,
        title: '提示',
        btn: ['确定','取消']
    }, function(index, layero){
        parent.layer.close(index);
        window.location.href = "/logout";
      }, function(){
    
    });
}

// $("#psnInfo").on('click',function(){
//     window.location.href = "/inaction/user/edit.html?personnelId=" + personnelId;
// })

function cancel(){
    $("#LAY_app_tabsheader").children(".layui-this").children(".layui-tab-close").trigger("click");
}

initUserInfo();
initUserPermission();
getDictionaryData();
// layui admin
// layui.config({
//     base: '/vendors/layuiadmin/' //静态资源所在路径
//     }).extend({
//     index: 'lib/index' //主入口模块
//     }).use('index');