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
    window.location.href = "elemResList.html";
}

//新增
function addRes(){
    if(!formValidate.isAllPass()){
        return;
    }
    
    $http.post('/system/SysElement/add', JSON.stringify({   
        elementName : $("#elementName").val(),
        elementCode : $("#elementCode").val(),
        elementType : $("#elementType").val(),
        menuCode : $("#menuName").val(),
        urlAddr : $("#urlAddr").val(),
        statusCd : $("#statusCd").val(),
        elementDesc : $("#elementDesc").val()
    }), function (message) {
        //新增
        backToList();
        toastr.success("新增成功！");
    }, function (err) {
        toastr.error("新增失败！");
    })
}

//初始化菜单select
function initMenuName(result){
    for(var i=0;i<result.length;i++){
        $("#menuName").append("<option value='" + result[i].menuCode + "'>" + result[i].menuName +"</option>");
    }
    seajs.use('/vendors/lulu/js/common/ui/Select', function () {
        $('#menuName').selectMatch();
    });
}

initMenuName(parent.menuList);
