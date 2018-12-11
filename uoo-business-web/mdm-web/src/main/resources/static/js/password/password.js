var toastr = window.top.toastr;

function submitPwd(){  //保存
    var oldPsw = $("#oldPsw").val();
    var newPsw = $("#newPsw").val();
    var newPswA = $("#newPswA").val();
    var pwdList;

    if(newPsw != newPswA){
        toastr.error("两次输入的新密码不一致");
    }else if(oldPsw == '' && newPsw == '' && newPswA == ''){
        toastr.error("密码不能为空");
    }else{
        pwdList = {
            "oldPwd":hex_md5(oldPsw),
            "newPwd":hex_md5(newPsw),
            "newPwd2":hex_md5(newPswA)
        };
        alterPwd(pwdList);
    }
}

function alterPwd(pwdList){
    $.ajax({			
        type : "POST",
        url : "/system/alterPwd",  //接口
        data : JSON.stringify(pwdList),
        dataType: "json",
        success:function(result){
            if(result.state == 1000){
                toastr.success(result.message);
                parent.cancel();
            }else{
                toastr.error(result.message);
            }
        }, 
        error:function(){
            toastr.error("保存失败"); 
        }
    });
}

