// var fullOrgName = getQueryString('fullorgname');
var account = getQueryString('account');
var title = getQueryString('title');
var opBtn = getQueryString('opBtn');

$('#main-title').html(title);

if(opBtn==0){
    $('#opBtn').css("display","none");
    $('.fright').css("display","none");
}

laydate.render({
  elem: '#effectDate' //指定元素
}); 

laydate.render({
  elem: '#invalidDate' //指定元素
}); 

