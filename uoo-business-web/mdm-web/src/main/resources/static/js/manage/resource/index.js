
var oldStep = "#funRes";
$('#resFrame').attr("src","funResList.html");

$("li").on('click',function(){
    var id = $(this).attr('id');
    var url = "";
    if(!$(this).hasClass("current")){
        $(oldStep).removeClass("current");
        $(this).addClass("current");
        oldStep = "#"+id;
        url = id+"List.html";
        $('#resFrame').attr("src",url);
    }
})