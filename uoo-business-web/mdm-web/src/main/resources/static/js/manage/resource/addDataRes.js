var toastr = window.top.toastr;

function backToList(){
    window.location.href = "dataResList.html";
}

//创建数据资源
function addDataRule(){
    $http.post('/system/sysDataRule/addDataRule', JSON.stringify({   
        tabName : $("#tabName").val(),
        colName : $("#colName").val(),
        ruleOperator : $("#ruleOperator").val(),
        colValue : $("#colValue").val(),
        statusCd : $("#statusCd").val()
    }), function (message) {
        //新增
        backToList();
        toastr.success("新增成功！");
    }, function (err) {
        // toastr.error("新增失败！");
    })
}