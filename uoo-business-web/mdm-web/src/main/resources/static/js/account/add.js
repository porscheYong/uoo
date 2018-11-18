function check(){

    var userType = $("#userType").val();
    //console.log(userType);

    if(userType == '主账号'){
        window.location.href = 'addMainAccount.html?account=123&title=添加主账号&opBtn=1'; //?id=' + orgId + '&name=' + orgName
    }else{
        window.location.href = 'addSubAccount.html?account=123&title=添加从账号&opBtn=1';
    }

}