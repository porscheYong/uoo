var toastr = window.top.toastr;

function backToList(){
    window.location.href = "funResList.html";
}

function addRes(){
    $http.post('/system/sysFunction/add', JSON.stringify({  
        funcName : $("#funcName").val(),
        funcCode : $("#funcCode").val(),
        funcApi : $("#funcApi").val(),
        statusCd : $("#statusCd").val()
    }), function (message) {
        //新增
        backToList();
        toastr.success("新增成功！");
    }, function (err) {
        toastr.error("新增失败！");
    })
}