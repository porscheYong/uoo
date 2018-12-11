var orgId = getQueryString('orgId');
var orgName = getQueryString('orgName');
var orgFullName = getQueryString('orgFullName');
var orgTreeId = getQueryString('orgTreeId');
var Regx = /^[A-Za-z0-9]*$/;
var table;
var engine;
var empty;
var sortFlag = 0;
var currentPage = 0;

$('#userType').get(0).selectedIndex=1;

empty = Handlebars.compile($(".typeahead-menu").html());

engine = new Bloodhound({
    identify: function(o) { return o.id_str; },
    queryTokenizer: Bloodhound.tokenizers.whitespace,
    datumTokenizer: Bloodhound.tokenizers.obj.whitespace('psnName'),
    dupDetector: function(a, b) { return a.id_str === b.id_str; },
    remote: {
        url: '/personnel/getPsnBasicInfo?keyWord=%QUERY&pageNo=1&pageSize=10',
        wildcard: '%QUERY',
        filter: function (response) {
            if (response.data && response.data.records.length == 0)
                return;
            
        }
    }
});

function initPsnTable(keyWord) {
    table = $("#psnTable").DataTable({
        'destroy':true,
        'searching': false,
        'autoWidth': false,
        'ordering': true,
        'info': true,
        'lengthChange':true,
        'initComplete': function (settings, json) {
            console.log(settings, json)
        },
        // "scrollY": "185px",
        // "scrollCollapse": true,
        'columns': [
                { 'data': "psnName", 'title': '姓名', 'className': 'row-psnName' ,
                'render': function (data, type, row, meta) {
                    return "<a href='javascript:void(0);' onclick='psnNameClick(" + row.personnelId + ")' >"+ row.psnName +"</a>";
                }
            },
                { 'data': "content", 'title': '联系方式', 'className': 'row-content'},
                { 'data': "psnNbr", 'title': '人员编码', 'className': 'row-psnNbr'}
        ],
        'language': {
            'emptyTable': '没有数据',  
            'loadingRecords': '加载中...',  
            'processing': '查询中...',  
            'search': '检索:',  
            'lengthMenu': ' _MENU_ ',  
            'zeroRecords': '没有数据',  
            'paginate': {  
                'first':      '首页',  
                'last':       '尾页',  
                'next':       '下一页',  
                'previous':   '上一页'  
            },  
            'info': '总_TOTAL_人',  
            'infoEmpty': '没有数据'
        },
        "iDisplayLength":5,
        "aLengthMenu": [[10, 20, 50], ["10条/页", "20条/页", "50条/页"]],
        'pagingType': 'simple_numbers',
        'dom': '<"top"f>t<"bottom"ipl>',
        'serverSide': true,  //启用服务器端分页
        'ajax': function (data, callback, settings) {
            var param = {};
            param.pageSize = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
            param.pageNo = (data.start / data.length) + 1;//当前页码
            param.keyWord = keyWord;
            $http.get('/personnel/getPsnBasicInfo', param, function (result) {
                var returnData = {};
                // returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                returnData.recordsTotal = result.total;//返回数据全部记录
                returnData.recordsFiltered = result.total;//后台不实现过滤功能，每次查询均视作全部结果
                returnData.data = result.records;//返回的数据列表
                //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                callback(returnData);
            }, function (err) {
                console.log(err)
            })
        }
    });

    initPsnNameSort();
    initContentSort();
    initPsnNbrSort();
}

function engineWithDefaults(q, sync, async) {
    if (q === '') {
        $("#psnTable").html('');
        // $("#psntable_wrapper .bottom").remove();
        $(".psn-table").removeClass("is-open");
    }
    else {
        engine.search(q, sync, async);
    }
}

$('#psnName').typeahead({
    hint: $('.typeahead-hint'),
    menu: $('.psn-table'),
    minLength: 0,
    highlight:true,
    classNames: {
        open: 'is-open',
        empty: 'is-empty',
        cursor: 'is-active',
        suggestion: 'Typeahead-suggestion',
        selectable: 'Typeahead-selectable'
    }
}, {
    source: engineWithDefaults,
    displayKey: 'psnName',
    templates: {
        suggestion: empty
    }
})
  .on('typeahead:asyncrequest', function() {
        $('.Typeahead-spinner').show();
        if($("#psnName").val() != '' && !Regx.test($("#psnName").val())){
            initPsnTable($("#psnName").val());
        }else{
            $(".psn-table").removeClass("is-open");
        }
    })
  .on('typeahead:asynccancel', function() {
        $('.Typeahead-spinner').hide();
    });

Handlebars.registerHelper('eq', function(v1, v2, opts) {
    if(v1 == v2){
        return opts.fn(this);
    }
    else
        return opts.inverse(this);
});

Handlebars.registerHelper("addOne", function (index) {
    return index + 1;
});

function cancel() {
    var url = "mainList.html?orgTreeId=" + orgTreeId + "&orgName=" + orgName + "&orgId=" + orgId;
    window.location.href = url;
}

function addPsn(){
    window.location.href = "/inaction/user/add.html?name="+orgName;
}

function psnNameClick(personnelId){
    var url = "";
    if($("#userType").val() == '主账号'){
        url = "addMainAccount.html?orgFullName=" + orgFullName + "&orgTreeId=" + orgTreeId + "&orgName=" + orgName + "&orgId=" + orgId + "&personnelId=" + personnelId + "&opBtn=1&hType=ah";
    }else if($("#userType").val() == '从账号'){
        url = "addSubAccount.html?orgTreeId=" + orgTreeId + "&orgName=" + orgName + "&orgId=" + orgId + "&personnelId=" + personnelId + "&opBtn=1&hType=ah";
    }
    window.location.href = url;
}

$("#psnName").focus(function (){     
    if($("#psnName").val() == ''){
        $(".psn-table").removeClass("is-open");
    }
})

function arrSort (arr, dataLeven) { // 参数：arr 排序的数组; dataLeven 数组内的需要比较的元素属性 
    /* 获取数组元素内需要比较的值 */
    function getValue (option) { // 参数： option 数组元素
      if (!dataLeven) return option
      var data = option
      dataLeven.split('.').filter(function (item) {
        data = data[item]
      })
      return data + ''
    }
    arr.sort(function (item1, item2) {
      return getValue(item1).localeCompare(getValue(item2), 'zh-CN');
    })
  }

  function descSort(asc,desc){      //desc排序
    for(var i=asc.length-1;i>=0;i--){
        desc.push(asc[i]);
    }
    return desc;
  }

  function sortToTable(arr){   //将排完序的数据写入表格
    for(var i =0;i<arr.length;i++){
        sortFlag = 1;
        table
            .row(i)
            .data({
                "psnName":arr[i].psnName,
                "content":arr[i].content,
                "psnNbr":arr[i].psnNbr
            })
            .draw();
    }
  }

function initPsnNameSort(){        //初始化用户姓名排序
    $('.row-psnName').on('click', function () {
        var tableLength = table.data().length;
        var arr = [];
        var descArr = [];
    
        for(var i = 0;i < tableLength;i++){
            arr.push(table.row(i).data());
        }
        
        arrSort(arr,'psnName');
        
        if($(this).hasClass("sorting_desc")){
            descArr = descSort(arr,descArr);
            sortToTable(descArr);
        }else{
            sortToTable(arr);
        }
        table.page(currentPage).draw( false );
    });
}

function initContentSort(){        //初始化联系方式排序
    $('.row-content').on('click', function () {
        var tableLength = table.data().length;
        var arr = [];
        var descArr = [];
    
        for(var i = 0;i < tableLength;i++){
            arr.push(table.row(i).data());
        }
        
        arrSort(arr,'content');
        
        if($(this).hasClass("sorting_desc")){
            descArr = descSort(arr,descArr);
            sortToTable(descArr);
        }else{
            sortToTable(arr);
        }
        table.page(currentPage).draw( false );
    });
}

function initPsnNbrSort(){        //初始化人员编码排序
    $('.row-psnNbr').on('click', function () {
        var tableLength = table.data().length;
        var arr = [];
        var descArr = [];
    
        for(var i = 0;i < tableLength;i++){
            arr.push(table.row(i).data());
        }
        
        arrSort(arr,'psnNbr');
        
        if($(this).hasClass("sorting_desc")){
            descArr = descSort(arr,descArr);
            sortToTable(descArr);
        }else{
            sortToTable(arr);
        }
        table.page(currentPage).draw( false );
    });
}