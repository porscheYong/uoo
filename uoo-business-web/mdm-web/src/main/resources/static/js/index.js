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


function initUserInfo(){  //初始化首页人员信息          
    $http.get('/system/getCurrentLoginUserInfo', { }, 
    function (data) {
      getAcctInfo(data.acctId);
    }, function (err) {
        console.log(err)
    })
}

function getAcctInfo(acctId){ //获取账号信息
    $http.get('/user/getUser', { 
        acctId:acctId,
        userType:"1"
    }, 
    function (data) {
      //console.log(data);   
      $("#psnName").text(data.psnName);
    }, function (err) {
        console.log(err)
    })
}

// function initUserPermission(){    //初始化人员权限
//     $http.post('/permission/tbRoles/getPermissionMenu/19521/0', { }, 
//     function (data) {
//         console.log(data);
//         initSideBar(data);
//         // layui admin
//         layui.config({
//             base: '/vendors/layuiadmin/' //静态资源所在路径
//             }).extend({
//             index: 'lib/index' //主入口模块
//             }).use('index');
//     }, function (err) {
//         console.log(err)
//     })
// }

// function initSideBar(results){     //初始化侧边菜单
//     var pemList = '';
//     var parList = [];
//     var childList = [];
//     var flag = 0;

//     for(var i = 0;i<results.length-1;i++){
//         if(results[i].parMenuId == 0){
//             parList.push(results[i]);
//         }else{
//             childList.push(results[i]);
//         }
//     }
//     console.log(parList);
//     console.log(childList);
//     for(var i = 0;i<parList.length;i++){
//         var dd = "<dl class='layui-nav-child'>";
        
//         for(var j=0;j<childList.length;j++){
//             if(childList[j].parMenuId == parList[i].menuId){
//                 flag = 1;
//                 dd += "<dd><a lay-href='" + childList[j].urlAddr + "'>" + childList[j].menuName + "</a></dd>"
//             }
//         }
//         if(flag == 1){
//             pemList += "<li class='layui-nav-item'><a href='javascript:;'>" +
//                 "<i class='layui-icon layui-icon-component'></i><cite>" + parList[i].menuName + 
//                 "</cite><span class='layui-nav-more'></span></a>" + dd + "</dl></li>";
//         }else{
//             pemList += "<li class='layui-nav-item'><a lay-href='" + parList[i].urlAddr + "'>" +
//                 "<i class='layui-icon layui-icon-component'></i><cite>" + parList[i].menuName + "</cite></a></li>";
//         }
        
//         flag = 0;
//     }
//     console.log(pemList);
//     $("#LAY-system-side-menu").append(pemList);
   
// }

initUserInfo();
// initUserPermission();

// layui admin
layui.config({
    base: '/vendors/layuiadmin/' //静态资源所在路径
    }).extend({
    index: 'lib/index' //主入口模块
    }).use('index');