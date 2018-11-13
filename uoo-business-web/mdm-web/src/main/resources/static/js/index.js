// layui admin
layui.config({
    base: '../static/vendors/layuiadmin/' //静态资源所在路径
    }).extend({
    index: 'lib/index' //主入口模块
    }).use('index');

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
}
