function check(){

    var userType = $("#userType").val();
    console.log(userType);

    if(userType!='主账号'){
        window.location.href = 'addMainAccount.html'; //?id=' + orgId + '&name=' + orgName
    }else{
        window.location.href = 'addSubAccount.html'; //?id=' + orgId + '&name=' + orgName
    }

}