var toastr = window.top.toastr;
var formValidate;

seajs.use('/vendors/lulu/js/common/ui/Validate', function (Validate) {
    var posEditForm = $('#editInfo');
    formValidate = new Validate(posEditForm);
    formValidate.immediate();
    posEditForm.find(':input').each(function () {
      $(this).bind({
          paste : function(){
              formValidate.isPass($(this));
              $(this).removeClass('error');
          }
      });
    });
});

function backToList(){
    window.location.href = "fileResList.html";
}

//创建文件资源
function addSysFile(){
    if(!formValidate.isAllPass()){
        return;
    }
    
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