
$('#userType').get(0).selectedIndex=1;

function check(){

    var userType = $("#userType").val();
    var personnelId = $("#personnelIdTel").val();

    if(userType == '主账号'){
        window.location.href = 'addMainAccount.html?personnelId='+personnelId+'&userType=1&title=添加主账号&opBtn=1'; //?id=' + orgId + '&name=' + orgName
    }else if(userType == '从账号'){
        window.location.href = 'addSubAccount.html?account=123&title=添加从账号&opBtn=1';
    }else{
        alert('请选择用户类型');
    }

}