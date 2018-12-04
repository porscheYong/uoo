var isMobile = /^[1][3,4,5,7,8,9][0-9]{9}$/;
var isAccount = /^[a-zA-Z]+([0-9])*$/;
var isEmail = /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/;
var accData;
var show_num = [];
var isError = 0;
var show_num = [];

$(function (){
    toastr.options = {
                        "timeOut": "2000",
                        "preventDuplicates": true,
                        "preventManyTimes": true,
                        "hideDuration": "1"
                    };  
})

draw(show_num);

$("#canvas").on('click',function(){
    draw(show_num);
});

function check(){

    var user = $(".user").val();
    var pwd = $(".psw").val(); 
    
    if(user=="" || pwd==""){

        // $("#errorAcc").text("账号或密码未填写！");
        // $("#errorAcc").show();
        toastr.error("账号或密码未填写！");
        return false;

    }else if(!(isMobile.test(user) || isAccount.test(user) || isEmail.test(user))){  

        // $("#errorAcc").text("输入的帐号格式不正确！");
        // $("#errorAcc").show();
        toastr.error("输入的帐号格式不正确！");
        return false;

    }else if(pwd.length < 6){

        // $("#errorAcc").text("密码长度小于6位！");
        // $("#errorAcc").show();
        toastr.error("密码长度小于6位！");
        return false;

    }else{
        if(isError == 1){
            var val = $(".code").val().toLowerCase();
            var num = show_num.join("");
            if(val==''){
                // $("#errorAcc").text("请输入验证码！");
                // $("#errorAcc").show();
                toastr.error("请输入验证码！");
                return false;
            }else if(val == num){
                $(".code").val('');
                draw(show_num);   
                $("#errorAcc").text("");
                $("#errorAcc").show(); 
            }else{
                // $("#errorAcc").text("验证码错误！请重新输入！");
                // $("#errorAcc").show();
                toastr.error("验证码错误！请重新输入！");
                $(".code").val('');
                draw(show_num);
                return false;
            }
        }

        if(isMobile.test(user)){          //判断账号类型
            accData = {
                "mobile" : user,
                "passwd": hex_md5(pwd)
            };

        }else if(isAccount.test(user)){
            accData = {
                "accout" : user,
                "passwd": hex_md5(pwd)
            };
        }else{
            accData = {
                "email" : user,
                "passwd": hex_md5(pwd)
            };
        }

        $.ajax({			//提交表单
            type : "POST",
			url : "/system/sysUserLogin",  //接口
			data : accData,
            dataType: "json",
			success:function(result){
                if(result.state == 1000){
                    toastr.success("登录成功");
                    window.location.href = "index.html";
                }else{
                    // alert(result.message);
                    toastr.error(result.message);
                    $(".psw").val('');
                    isError = 1;
                    $(".code").show();
                    $("#canvas").show();
                }
            },
            error:function(){
                toastr.error("网络连接失败！");
            }
        });

        return false;
    }
}

function draw(show_num) {       //生成验证码
	var canvas_width=$('#canvas').width();
	var canvas_height=$('#canvas').height();
	var canvas = document.getElementById("canvas");//获取到canvas的对象，演员
	var context = canvas.getContext("2d");//获取到canvas画图的环境，演员表演的舞台
	canvas.width = canvas_width;
	canvas.height = canvas_height;
	var sCode = "A,B,C,E,F,G,H,J,K,L,M,N,P,Q,R,S,T,W,X,Y,Z,1,2,3,4,5,6,7,8,9,0";
	var aCode = sCode.split(",");
	var aLength = aCode.length;//获取到数组的长度
	
	for (var i = 0; i <= 3; i++) {
		var j = Math.floor(Math.random() * aLength);//获取到随机的索引值
		var deg = Math.random() * 30 * Math.PI / 180;//产生0~30之间的随机弧度
		var txt = aCode[j];//得到随机的一个内容
		show_num[i] = txt.toLowerCase();
		var x = 10 + i * 20;//文字在canvas上的x坐标
		var y = 20 + Math.random() * 8;//文字在canvas上的y坐标
		context.font = "bold 23px 微软雅黑";

		context.translate(x, y);
		context.rotate(deg);

		context.fillStyle = randomColor();
		context.fillText(txt, 0, 0);

		context.rotate(-deg);
		context.translate(-x, -y);
	}
	for (var i = 0; i <= 5; i++) { //验证码上显示线条
		context.strokeStyle = randomColor();
		context.beginPath();
		context.moveTo(Math.random() * canvas_width, Math.random() * canvas_height);
		context.lineTo(Math.random() * canvas_width, Math.random() * canvas_height);
		context.stroke();
	}
	for (var i = 0; i <= 30; i++) { //验证码上显示小点
		context.strokeStyle = randomColor();
		context.beginPath();
		var x = Math.random() * canvas_width;
		var y = Math.random() * canvas_height;
		context.moveTo(x, y);
		context.lineTo(x + 1, y + 1);
		context.stroke();
	}
}

function randomColor() {//得到随机的颜色值
	var r = Math.floor(Math.random() * 256);
	var g = Math.floor(Math.random() * 256);
	var b = Math.floor(Math.random() * 256);
	return "rgb(" + r + "," + g + "," + b + ")";
}


