var base = 'http://134.96.253.221:11100/'; //开发地址

// 全局异步封装
var $http = {
  baseUri: base,
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
      if (state === 1000) {
        successCallback(responseData, state, message)
      }
      else {
        switch (state) {
          case 11000:
            console.log(message)
            break
          case 11001:
            console.log(message)
            break
          case 11002:
            console.log(message)
            break
          case 11003:
            console.log(message)
            break
          case 11004:
            console.log(message)
            break
          case 11005:
            console.log(message)
            break
          case 11006:
            console.log(message)
            break
          case 11007:
            console.log(message)
            break
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
       type: type,
       url: this.baseUri + path,
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
      return unescape(r[2])
    }
    return null
  }
}

// 获取url参数
function getQueryString(name)
{
  var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
  var r = window.location.search.substr(1).match(reg);
  if(r!=null)return  unescape(r[2]); return null;
}

// 时间戳格式化
function getDate (value) {
  var dataObj = new Date(value)
  var year  = dataObj.getFullYear()
  var month = dataObj.getMonth() + 1
  var date  = dataObj.getDate()
  return year + '-' + month + '-' + date
}