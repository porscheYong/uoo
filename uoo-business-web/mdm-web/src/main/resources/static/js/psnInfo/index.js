var acctId = getQueryString('acctId');
var personnelId = getQueryString('personnelId');
var userId = getQueryString('userId');

$("#infoFrame").attr("src","list.html?personnelId=" + personnelId+"&acctId=" + acctId + "&userId=" + userId);