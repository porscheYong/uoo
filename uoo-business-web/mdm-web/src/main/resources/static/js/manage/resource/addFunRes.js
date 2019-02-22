var toastr = window.top.toastr;
var loading = parent.loading;
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
    window.location.href = "funResList.html";
}

function addRes(){
    if(!formValidate.isAllPass()){
        return;
    }

    $http.post('/system/sysFunction/add', JSON.stringify({  
        funcName : $("#funcName").val(),
        funcCode : $("#funcCode").val(),
        funcApi : $("#funcApi").val(),
        statusCd : $("#statusCd").val()
    }), function (message) {
        //新增
        // loading.screenMaskDisable('container');
        backToList();
        toastr.success("新增成功！");
    }, function (err) {
        // loading.screenMaskDisable('container');
        toastr.error("新增失败！");
    })
}