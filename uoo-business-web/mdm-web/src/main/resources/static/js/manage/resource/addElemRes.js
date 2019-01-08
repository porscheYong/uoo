var toastr = window.top.toastr;

function backToList(){
    window.location.href = "elemResList.html";
}

function addRes(){
    $http.post('/system/SysElement/add', JSON.stringify({   
        elementName : $("#elementName").val(),
        elementCode : $("#elementCode").val(),
        elementType : $("#elementType").val(),
        menuCode : $("#menuName").val(),
        urlAddr : $("#urlAddr").val(),
        statusCd : $("#statusCd").val()
    }), function (message) {
        //新增
        backToList();
        toastr.success("新增成功！");
    }, function (err) {
        toastr.error("新增失败！");
    })
}