var logId = getQueryString('logId');
var logEnum = getQueryString('logEnum');
var loading = parent.loading;

loading.screenMaskEnable('LAY_app_body');

//获取日志信息
function getLogInfo(){
    $http.get('/system/sysOperationLog/get', {
        id : logId,
        logEnum : logEnum
    }, function (data) {
        initLogInfo(data);
    }, function (err) {

    })
}

//初始化日志信息
function initLogInfo(result){
    isNull("logNameLable",result.logName);
    isNull("loadAcctLable",result.accout);
    isNull("typeLable",result.operateName);
    isNull("dateLable",parent.formatDateTime(result.createDate));
    isNull("formDataLable",result.formData);
    isNull("notesLable",result.notes);
    isNull("menuNameLable",result.menuName);

    if(result.succeed == 1){
        isNull("isSucLable","是");
    }else{
        isNull("isSucLable","否");
    }

    if(result.statusCd == 1000){
        isNull("stateLable","有效");
    }else{
        isNull("stateLable","无效");
    }
    loading.screenMaskDisable('LAY_app_body');
}

//判断返回值是否为null
function isNull(el,str){
    if(str != null){
        $("#"+el).text(str);
    }
}

getLogInfo();