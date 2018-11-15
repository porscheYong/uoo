var isMobile = /^[1][3,4,5,7,8,9][0-9]{9}$/;
var isAccount = /^[a-zA-Z]+([0-9])*$/;
var isEmail = /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/;
var accData;

function check(){

    var user = $(".user").val();
    var pwd = $(".psw").val(); 

    if(user=="" || pwd==""){

        $("#errorAcc").text("账号或密码未填写！");
        $("#errorAcc").show();
        return false;

    }else if(!(isMobile.test(user) || isAccount.test(user) || isEmail.test(user))){  

        $("#errorAcc").text("输入的帐号格式不正确！");
        $("#errorAcc").show();
        return false;

    }else if(pwd.length < 6){

        $("#errorAcc").text("密码长度小于6位！");
        $("#errorAcc").show();
        return false;

    }else{

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
			url : "http://134.96.253.221:9100/system/sysUserLogin",  //接口
			data : accData,
            dataType: "json",
			success:function(result){
                if(result.state==1000){
                    window.location.href = "templates/index.html";
                }else{
                    alert("用户名不存在或密码不正确！");
                }
            },
            error:function(){
                alert("Error");
            }
        });

        return false;
    }
}


