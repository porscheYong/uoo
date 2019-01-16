//var base = 'http://127.0.0.1:18000/'; //开发地址

// 全局异步封装
var $http = {
  // baseUri: base,
  get: function (path, data, successCallback, errorCallback) {
    data._ = new Date().getTime();
    this.ajax('get', path, data, successCallback, errorCallback)
  },
  post: function (path, data, successCallback, errorCallback) {
    this.ajax('POST', path, data, successCallback, errorCallback)
  },
  put: function (path, data, successCallback, errorCallback) {
	  data._ = new Date().getTime();
	  this.ajax('put', path, data, successCallback, errorCallback)
  },
  delet: function (path, data, successCallback, errorCallback) {
    this.ajax('DELETE', path, data, successCallback, errorCallback)
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
          case 1100:
            if (parent.layer) {
                parent.layer.confirm(message, {
                    icon: 0,
                    title: '提示',
                    btn: ['确定']
                }, function(index, layero){
                    parent.layer.close(index);
                }, function(){

                });
            }
            break;
          case 1200:
            if (parent.loading)
              parent.loading.screenMaskDisable('container');
            if (parent.layer) {
                parent.layer.confirm(message, {
                    icon: 0,
                    title: '提示',
                    btn: ['确定']
                }, function(index, layero){
                    parent.layer.close(index);
                }, function(){

                });
            }
            break;
            default:
                if (parent.layer) {
                    parent.layer.confirm(message, {
                        icon: 0,
                        title: '提示',
                        btn: ['确定']
                    }, function(index, layero){
                        parent.layer.close(index);
                        if(message === "主账号不存在,请新建主账号"){
                            window.history.back();
                        }
                    }, function(){

                    });
                }
            break;
        }
        var httpStatus = response.status
        errorCallback(httpStatus, state, message)
      }
    }
    var httpError = function (response) {
      if (parent.loading)
        parent.loading.screenMaskDisable('container');
      var httpStatus = response.status
      var state = 0
      var message = 'HTTP请求错误'
      errorCallback(httpStatus, state, message)
    }
    $.ajax({
       headers: {'MENU_CODE': window.top.menuCode},
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

//将时间戳改为yyyy-MM-dd HH-mm-ss
function formatDateTime(unix) {
    var now = new Date(parseInt(unix) * 1);
    now =  now.toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ");
    if(now.indexOf("下午") > 0) {
        if (now.length == 18) {
            var temp1 = now.substring(0, now.indexOf("下午"));   //2014/7/6
            var temp2 = now.substring(now.indexOf("下午") + 2, now.length);  // 5:17:43
            var temp3 = temp2.substring(0, 1);    //  5
            var temp4 = parseInt(temp3); // 5
            temp4 = 12 + temp4;  // 17
            var temp5 = temp4 + temp2.substring(1, temp2.length); // 17:17:43
            now = temp1 + temp5; // 2014/7/6 17:17:43
            now = now.replace("/", "-"); //  2014-7/6 17:17:43
            now = now.replace("/", "-"); //  2014-7-6 17:17:43
        }else {
            var temp1 = now.substring(0, now.indexOf("下午"));   //2014/7/6
            var temp2 = now.substring(now.indexOf("下午") + 2, now.length);  // 5:17:43
            var temp3 = temp2.substring(0, 2);    //  5
            if (temp3 == 12){
                temp3 -= 12;
            }
            var temp4 = parseInt(temp3); // 5
            temp4 = 12 + temp4;  // 17
            var temp5 = temp4 + temp2.substring(2, temp2.length); // 17:17:43
            now = temp1 + temp5; // 2014/7/6 17:17:43
            now = now.replace("/", "-"); //  2014-7/6 17:17:43
            now = now.replace("/", "-"); //  2014-7-6 17:17:43
        }
    }else {
        var temp1 = now.substring(0,now.indexOf("上午"));   //2014/7/6
        var temp2 = now.substring(now.indexOf("上午")+2,now.length);  // 5:17:43
        var temp3 = temp2.substring(0,1);    //  5
        var index = 1;
        var temp4 = parseInt(temp3); // 5
        if(temp4 == 0 ) {   //  00
            temp4 = "0"+temp4;
        }else if(temp4 == 1) {  // 10  11  12
            index = 2;
            var tempIndex = temp2.substring(1,2);
            if(tempIndex != ":") {
                temp4 = temp4 + "" + tempIndex;
            }else { // 01
                temp4 = "0"+temp4;
            }
        }else {  // 02 03 ... 09
            temp4 = "0"+temp4;
        }
        var temp5 = temp4 + temp2.substring(index,temp2.length); // 07:17:43
        now = temp1 + temp5; // 2014/7/6 07:17:43
        now = now.replace("/","-"); //  2014-7/6 07:17:43
        now = now.replace("/","-"); //  2014-7-6 07:17:43
    }
    return now;
}