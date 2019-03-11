var orgId = getQueryString('id');
var orgTreeId = getQueryString('orgTreeId');
var pid = getQueryString('pid');
var orgName = getQueryString('name');
var refCode = getQueryString('refCode');
var formData;
var loading = parent.loading;
var toastr = window.top.toastr;

function importFile (e) {
    var files = e.files;

    if (!files) {
        return;
    }
    uploadFile(files);
    $("#load").val(null);
}

function uploadFile (files) {
    var postFiles = Array.prototype.slice.call(files);
    if (postFiles.length === 0) return;
    handleBeforeUpload(postFiles[0]);
}

function handleBeforeUpload (file) {
    var fileExt = file.name.split('.').pop().toLocaleLowerCase();
    if (fileExt === 'xlsx' || fileExt === 'xls') {
        readFile(file);
    } else {
        toastr.warning('文件类型错误', '文件：' + file.name + '不是EXCEL文件，请选择后缀为.xlsx或者.xls的EXCEL文件。')
    }
    return false
}

// 读取文件
function readFile (file) {
    var reader = new FileReader();
    reader.readAsArrayBuffer(file);

    reader.onerror = function (e) {
        toastr.error('文件读取出错');
    };

    reader.onload = function (e) {
        toastr.success('文件读取成功');
        $('#fileName').html(file.name);
        var data = e.target.result;
        formData = new FormData();
        formData.append('fileInfo', file);
        formData.append("orgTreeId", orgTreeId);
        formData.append("impType", 2);
    }
}

//上传
function upload () {
    if (!formData) {
        parent.layer.confirm('请选择后缀为.xlsx或者.xls的EXCEL文件', {
            icon: 0,
            title: '提示',
            btn: ['确定']
        }, function(index){
            parent.layer.close(index);
        });
        return false;
    }
    loading.screenMaskEnable('container');
    var url =  "/excelOrgImport/importExcelFileData";
    var xhr = new XMLHttpRequest();
    xhr.onerror = function error(e) {
        parent.layer.confirm(e, {
            icon: 0,
            title: '提示',
            btn: ['确定']
        }, function(index){
            loading.screenMaskDisable('container');
            parent.layer.close(index);
        }, function(){

        });
    };

    xhr.onload = function onload() {
        var response = JSON.parse(xhr.responseText || xhr.response);
        if (xhr.status < 200 || xhr.status >= 300) {
            parent.layer.confirm(response.message, {
                icon: 0,
                title: '提示',
                btn: ['确定']
            }, function(index){
                loading.screenMaskDisable('container');
                parent.layer.close(index);
            }, function(){

            });
            return false;
        }
        else {
            var data = response.data;
            var url = 'editUpload-excel.html?fileSign=' + data + '&id=' + orgId + "&orgTreeId=" + orgTreeId + "&refCode=" + refCode + '&pid=' + pid + "&name=" + encodeURI(orgName);
            window.location.href = url;
            loading.screenMaskDisable('container');
        }
    };

    xhr.open('post', url, true);
    xhr.send(formData);
}

$('.button-load').on('click', function () {
    $("#load").click();
});

// 取消
function cancel () {
    var url = "list.html?id=" + orgId + "&orgTreeId=" + orgTreeId + "&refCode=" + refCode + '&pid=' + pid + "&name=" + encodeURI(orgName);
    window.location.href = url;
}
