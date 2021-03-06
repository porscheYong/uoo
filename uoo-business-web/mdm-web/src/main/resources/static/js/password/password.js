var toastr = window.top.toastr;
// var pswCheck = /^[A-Za-z0-9]+$/;
var pswCheck = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z_]{8,16}$/;

// lulu ui tips插件
seajs.use('/vendors/lulu/js/common/ui/Tips', function () {
    $('#newPswA').tips({
        align: 'rotate'
    });

    $('#newPsw').tips({
        align: 'rotate'
    });
});

function submitPwd(){  //保存
    var oldPsw = $("#oldPsw").val();
    var newPsw = $("#newPsw").val();
    var newPswA = $("#newPswA").val();
    var pwdList;

    if(newPsw != newPswA){
        toastr.error("两次输入的新密码不一致");
    }else if(oldPsw == '' && newPsw == '' && newPswA == ''){
        toastr.error("密码不能为空");
    }else if(oldPsw == newPsw){
        toastr.error("新密码不能和旧密码一致");
    }else if(!pswCheck.test(newPsw)){
        toastr.error("请输入8位字母和数字组合的密码");
    }else{
        pwdList = {
            "account":parent.account,
            "passwd":hex_md5(oldPsw),
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
        data : pwdList,
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

