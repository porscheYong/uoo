var toastr = window.top.toastr;

function backToList(){
    window.location.href = "fileResList.html";
}

//创建文件资源
function addSysFile(){
    $http.post('/sysFile/addSysFile', JSON.stringify({   
        fileName : $("#fileName").val(),
        fileType : $("#fileType").val(),
        fileAddr : $("#fileAddr").val(),
        fileSize : $("#fileSize").val(),
        fileVersion : $("#fileVersion").val(),
        statusCd : $("#statusCd").val(),
        fileDesc : $("#fileDesc").val()
    }), function (message) {
        //新增
        backToList();
        toastr.success("新增成功！");
    }, function (err) {
        // toastr.error("新增失败！");
    })
}