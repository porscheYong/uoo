var fullOrgName = getQueryString('fullorgname');
var orgName = getQueryString('name');


 laydate.render({
    elem: '#effectDate' //指定元素
  }); 

  laydate.render({
    elem: '#invalidDate' //指定元素
  }); 
  
$('#orgName').html(orgName);
$(".breadcrumb").html(fullOrgName);
