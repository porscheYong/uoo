var isMobile = /^[1][3,4,5,7,8,9][0-9]{9}$/;
var isAccount = /^[a-zA-Z]+([0-9])*$/;
var isEmail = /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/;
var accData;
var show_num = [];
var isError = 0;
var show_num = [];
var index;

if (window != window.top) {
    window.top.location.replace(window.location)
    // 这是直接代替外窗，你也可以干别的
}

$(function (){
    toastr.options = {
                        "timeOut": "1000",
                        "preventDuplicates": true,
                        "preventManyTimes": true,
                        "hideDuration": "1"
                    };
});
$('input').placeholder();
// draw(show_num);

$("#canvas").on('click',function(){
    draw(show_num);
});
var wait = 60;
function time() {
    if(wait == 0) {
    	$("#checkCodeA").removeAttr("disabled");
    	$("#checkCodeA").text('获取验证码')
        wait = 60;
    } else {
    	$("#checkCodeA").attr({"disabled":"disabled"});
    	$("#checkCodeA").text("重新发送(" + wait + ")")
        wait--;
        setTimeout(function() {
                time()
            },
            1000)
    }
}
function sendCheckCode(){
	var phone=$('#phone').val();
	if(!isMobile.test(phone)){
		toastr.error("手机号码格式错误");
		return false;
	}
	$.ajax({			//提交表单
        type : "GET",
		url : "/system/forgetpwd/sendcheckcode",  //接口
		data : {phone:phone},
        dataType: "json",
		success:function(result){
            if(result.state == 1000){
            	$("#checkCodeA").attr({"disabled":"disabled"});
        		time() ;
            }else{
                toastr.error(result.message);
                $("#checkCodeA").removeAttr("disabled");
            }
        },
        error:function(){
            layer.close(index);  
            toastr.error("网络连接失败！"); 
        }
    });
	
	return false;
	 
}
function check(){
	var phone=$('#phone').val();
	var pwd1=$('#pwd1').val();
	var pwd2=$('#pwd2').val();
	var checkCode=$('#phonecode').val();
	if(phone==""){
		toastr.error("请填写手机号");
		return false ;
	}
	if(!isMobile.test(phone)){
		toastr.error("手机号码格式错误");
		return false ;
	}
	if(pwd1==""||pwd2==""){
		toastr.error("请填写完整密码");
		return false ;
	}
	if(checkCode==""){
		toastr.error("请填写验证码");
		return false ;
	}
	if(pwd1.length < 6){
        toastr.error("密码长度小于6位！");
        return false ;
	}
	if(pwd1!=pwd2){
		toastr.error("两次密码不一致");
		return false ;
	}
	var pwd=hex_md5(pwd1);
	var accData={
			pwd:pwd,checkCode:checkCode,phone:phone
	};
	$.ajax({			//提交表单
        type : "GET",
		url : "/system/forgetpwd/valid",  //接口
		data : accData,
        dataType: "json",
		success:function(result){
            if(result.state == 1000){
                toastr.success("修改成功，前往登陆页面");
                window.location.href = "/login";
            }else{
                toastr.error(result.message);
            }
        },
        error:function(){
            toastr.error("网络连接失败！"); 
        }
    });
	return false ;
}
 


