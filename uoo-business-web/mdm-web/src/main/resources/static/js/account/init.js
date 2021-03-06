//var base = 'http://127.0.0.1:18000/'; //开发地址
var toastr = window.top.toastr;

$(function (){
  toastr.options = {
                      "timeOut": "1000",
                      "preventDuplicates": true,
                      "preventManyTimes": true,
                      "hideDuration": "1"
                  };  
})
// 全局异步封装
var $http = {
  // baseUri: base,
  get: function (path, data, successCallback, errorCallback) {
    this.ajax('get', path, data, successCallback, errorCallback)
  },
  post: function (path, data, successCallback, errorCallback) {
    this.ajax('POST', path, data, successCallback, errorCallback)
  },
  ajax: function (type, path, data, successCallback, errorCallback) {
    errorCallback = errorCallback || function () {}
    var httpSuccess = function (response) {
      var responseData = response.data
      var state = response.state
      var message = response.message
      if (state === 1000 || state === 1) {
        successCallback(responseData, state, message)
      }
      else {
        switch (state) {
          case 1100:
            toastr.error(message);
            break;
          default:
            toastr.error(message);
            break;
        }
        var httpStatus = response.status
        errorCallback(httpStatus, state, message)
      }
    }
    var httpError = function (response) {
      var httpStatus = response.status
      var state = 0
      var message = 'HTTP请求错误'
      errorCallback(httpStatus, state, message)
    }
    $.ajax({
       contentType: "application/json",
       type: type,
       url: path,
       data: data,
       success: httpSuccess,
       error: httpError,
       dataType: 'json'
    })
  },
  getQuery: function (name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i")
    var r = window.location.search.substr(1).match(reg)
    if (r != null) {
      return decodeURI(r[2])
    }
    return null
  }
}

// 获取url参数
function getQueryString(name)
{
  var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
  var r = window.location.search.substr(1).match(reg);
  if(r!=null)return  decodeURI(r[2]); return null;
}

// 时间戳格式化
function getDate (value) {
  var dataObj = new Date(value)
  var year  = dataObj.getFullYear()
  var month = dataObj.getMonth() + 1
  var date  = dataObj.getDate()
  return year + '-' + month + '-' + date
}