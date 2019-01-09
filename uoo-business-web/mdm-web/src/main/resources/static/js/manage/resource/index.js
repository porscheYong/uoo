
var oldStep = "#funRes";
var menuList = []; //元素资源的菜单列表
$('#resFrame').attr("src","funResList.html");

$("li").on('click',function(){
    var id = $(this).attr('id');
    var url = id+"List.html";
    $('#resFrame').attr("src",url);
    if(!$(this).hasClass("current")){
        $(oldStep).removeClass("current");
        $(this).addClass("current");
        oldStep = "#"+id;
    }
})