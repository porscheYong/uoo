var orgId = getQueryString('id');
var pid = getQueryString('pid');
var orgName = getQueryString('name');
var orgRelTypeList = [];
var orgTypeList = [];
var orgCopyList = [];
var formValidate;
var loading = parent.loading;

// 获取组织完整路径
function getOrgExtInfo () {
    var pathArry = parent.nodeArr;
    console.log(pathArry)
    var pathStr = '';
    for (var i = pathArry.length - 1; i >= 0; i--) {
        if (i === 0) {
            pathStr +=  '<span class="breadcrumb-item"><a href="javascript:viod(0);">' + pathArry[i] + '</a></span>';
        } else {
            pathStr += '<span class="breadcrumb-item"><a href="javascript:viod(0);">' + pathArry[i] + '</a><span class="breadcrumb-separator" style="margin: 0 9px;">/</span></span>';
        }
    }
    $('.breadcrumb').html(pathStr);
}
$('.orgName').html(orgName);
getOrgExtInfo();

// lulu ui select插件
seajs.use('/vendors/lulu/js/common/ui/Select', function () {

})

seajs.use('/vendors/lulu/js/common/ui/Validate', function (Validate) {
    var businessAddForm = $('#businessAdd');
    formValidate = new Validate(businessAddForm);
    formValidate.immediate();
    businessAddForm.find(':input').each(function () {
        $(this).hover(function () {
            formValidate.isPass($(this));
        });
    });
})

// tags init
if(typeof $.fn.tagsInput !== 'undefined'){
  $('#orgRelType').tagsInput();
  $('#orgTypeList').tagsInput();
  $('#orgCopyList').tagsInput();
}

//组织关系类型选择
function openRelTypeDialog() {
    parent.layer.open({
        type: 2,
        title: '组织关系类型',
        shadeClose: true,
        shade: 0.8,
        area: ['50%', '80%'],
        maxmin: true,
        content: 'relTypeDialog.html?id=' + orgId,
        btn: ['确认', '取消'],
        yes: function(index, layero){
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            checkNode = iframeWin.checkNode;
            parent.layer.close(index);
            $('#orgRelType').importTags(checkNode);
            $('.ui-tips-error').css('display', 'none');
            orgRelTypeList = checkNode;
        },
        btn2: function(index, layero){},
        cancel: function(){}
    });
}

//组织类别选择
function openTypeDialog() {
    parent.layer.open({
        type: 2,
        title: '选中组织类别',
        shadeClose: true,
        shade: 0.8,
        area: ['70%', '85%'],
        maxmin: true,
        content: 'typeDialog.html?id=' + orgId,
        btn: ['确认', '取消'],
        yes: function(index, layero){
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            checkNode = iframeWin.checkNode;
            parent.layer.close(index);
            $('#orgTypeList').importTags(checkNode);
            $('.ui-tips-error').css('display', 'none');
            orgTypeList = checkNode;
        },
        btn2: function(index, layero){},
        cancel: function(){}
    });
}

//拷贝组织节点
function openCopyDialog() {
    parent.layer.open({
        type: 2,
        title: '拷贝组织节点',
        shadeClose: true,
        shade: 0.8,
        area: ['70%', '85%'],
        maxmin: true,
        content: 'copyDialog.html?id=' + orgId,
        btn: ['确认', '取消'],
        yes: function(index, layero){
            //获取layer iframe对象
            var iframeWin = parent.window[layero.find('iframe')[0].name];
            checkNode = iframeWin.checkNode;
            parent.layer.close(index);
            $('#orgCopyList').importTags(checkNode);
            $('.ui-tips-error').css('display', 'none');
            orgCopyList = checkNode;
        },
        btn2: function(index, layero){},
        cancel: function(){}
    });
}


// 获取组织树类型数据
function getOrgTreeType () {
    $http.get('/tbDictionaryItem/getList/ORG_TREE_TYPE', {}, function (data) {
        var option = '';
        for (var i = 0; i < data.length; i++) {
            var select = i === 0? 'selected' : '';
            option += "<option value='" + data[i].itemValue + "' " + select + ">" + data[i].itemCnname +"</option>";
        }
        $('#orgTreeType').append(option);
        $('#orgTreeType').selectMatch();
    }, function (err) {
        console.log(err)
    })
}

// 获取用工性质
function getProperty () {
    $http.get('http://134.96.253.221:11500/tbDictionaryItem/getList/PROPERTY', {}, function (data) {
        var option = '<option></option>';
        for (var i = 0; i < data.length; i++) {
          option += "<option value='" + data[i].itemValue + "'>" + data[i].itemCnname +"</option>";
        }
        $('#property').append(option);
        $('#property').selectMatch();
    }, function (err) {
        console.log(err)
    })
}

// 更新组织信息
function updateOrg () {
  if (!formValidate.isAllPass())
      return;
  loading.screenMaskEnable('container');
  var orgType = [];
  for (var i = 0; i < orgTypeList.length; i++) {
      var orgTypeId = orgTypeList[i].orgTypeId || orgTypeList[i].id;
      orgType.push({orgTypeId: orgTypeId});
  }
  var orgName = $('#orgName').val();
  var orgScale = $('#orgScale option:selected') .val();
  var shortName = $('#shortName').val();
  var orgNameEn = $('#orgNameEn').val();
  var cityTown = $('#cityTown option:selected') .val();
  var date = $('#createDate').val();
  var createDate;
  if (date) {
      createDate = new Date(date).getTime();
  }
  var locId = $('#locId option:selected') .val();
  var orgPositionLevel = $('#orgPositionLevel option:selected') .val();
  var officePhone = $('#officePhone').val();
  var statusCd = $('#statusCd option:selected') .val();
  var sort = $('#sort').val();
  var address = $('#address').val();
  var orgContent = $('#orgContent').val();
  var orgDesc = $('#orgDesc').val();
  $http.post('http://134.96.253.221:11100/org/updateOrg', JSON.stringify({
      orgRootId: '1',
      orgId: orgId,
      supOrgId: pid,
      orgName: orgName,
      orgScale: orgScale,
      shortName: shortName,
      orgNameEn: orgNameEn,
      cityTown: cityTown,
      createDate: createDate,
      locId: locId,
      orgPositionLevel: orgPositionLevel,
      officePhone: officePhone,
      statusCd: statusCd,
      sort: sort,
      address: address,
      orgTypeList: orgType,
      orgContent: orgContent,
      orgDesc: orgDesc
  }), function (data) {
      parent.changeNodeName(orgId, orgName);
      window.location.replace("list.html?id=" + orgId + '&pid=' + pid + "&name=" + encodeURI(orgName));
      loading.screenMaskDisable('container');
  }, function (err) {

  })
}

// 取消
function cancel () {
    var url = "list.html?id=" + orgId + "&name=" + row.orgName;
    window.location.href = url;
}

getOrgTreeType();
getProperty();
