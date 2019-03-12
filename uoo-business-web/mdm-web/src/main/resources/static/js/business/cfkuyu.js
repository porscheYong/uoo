//获取IE浏览器版本
var browser = navigator.appName, b_version = navigator.appVersion;
var trim_Version="";
try {
    var version = b_version.split(";");
	trim_Version = version[1].replace(/[ ]/g, "");
}catch(err) {}
var IEless9 = false;
/*判断IE版本是否低于9*/
if (browser == "Microsoft Internet Explorer"){if(trim_Version == "MSIE6.0" || trim_Version == "MSIE7.0" || trim_Version == "MSIE8.0"){ IEless9 = true; }}
function $getObj(id){return document.getElementById(id);}
function TxtOnfocus(o,s){o.style.color='#000';if(o.value==s){o.value='';}}
function TxtOnblur(o,s){if(o.value==''){o.value=s;o.style.color='#999';}}

$(function(){

	//切换
	if($(".tab_menu").length>0){
		new cf_comTabFun(".tab","li");
	}

	/*失去焦点*/
	$(document).bind("click",function(e){
		var target = $(e.target);
		/*2018-5-30 修改*/
		if(target.closest(".cf_floatLayer, .cf_floatLayer_but").length == 0){ $(".cf_floatLayer").hide(); }
	});

	/*浮层 2018-5-30修改*/
	if($('.cf_floatLayer_but').length>0){
		$('.cf_floatLayer_but').on("click",function(){
			$(this).parent().parent("li").addClass("showLi");
			$(".cf_floatLayer").hide();
			var o = $("#"+$(this).attr("data-id"));
			if(o.is(":hidden")){
				o.show();
				var winW = $(window).width();
				var mW = o.find(".m").outerWidth();
				var o2 = $(this);
				var t = o2.offset().top+o2.outerHeight()+8;
				var l = o2.offset().left, l2 = 0;
				if(mW>l){ l = l/2; l2 = l; }
				if((l+mW) > winW){
					l = l-mW+o2.outerWidth()+10;
					l2 = mW-o2.outerWidth()-10;
				}
				o.css({"left":l+"px","top":t+"px"});
				var jt = o.find(".jt");
				jt.css({"left":l2+o2.outerWidth()/2-jt.find("i").outerWidth()/2+"px"});
			}
		});
		//$('[data-toggle="popoverMain"]').popover();
	}

});

//弹出窗口s 对象，w宽,h内容高
function cf_popupWinFun(s,w,h){
	if($(".cf_mask").length<=0){ $("body").append("<div class='cf_mask'></div>"); }
	var o = $(s);
	if(o.length>0){
		w = w?w+"px":"860px";
		h = h?h+"px":"auto";
		o.css({"width":w});
		o.find(".content").css({"height":h});
		o.show(0,function(){
			if(IEless9){ o.css({"margin-top":-o.innerHeight()/2+"px","margin-left":-o.innerWidth()/2+"px"}); }
		});
		$('.cf_mask').show();
		if($('.close,.quxiao').length>0){
			$('.close,.quxiao').on("click",function(){
				$('.cf_dialog').hide();
				$('.cf_mask').hide();
			});
		}
	}
}

//公用切换
function cf_comTabFun(o,o2){
	$(o+"_menu").each(function(i){
		$(this).attr({"data-id":o+"_box"}).find(o2).on("click",function(){
			$(this).addClass('Current').siblings().removeClass('Current');
			var o = $(this).parent();
			var index = o.children().index(this);
			o.siblings(o.attr("data-id")).children("div").eq(index).show().siblings().hide();
		});
	});
}
//公用切换-手动控制
function cf_comTabFunHand(o,num){
	$(o).find(".tab_menu li").eq(num).addClass('Current').siblings().removeClass('Current');
	$(o).find(".tab_box").children("div").eq(num).show().siblings().hide();
}
