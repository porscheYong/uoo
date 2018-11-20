function getRegionList (id) {
	$.ajax({
		url:'/region/politicalLocation/getChildPoliticalLocationInfo/'+id,
		success:function(data){
			console.log(data);
			if(data.state==1){
				
				initTable(data.data)
			}else{
				
			}
		},
		dataType:'json',
		type:'get'
	});
}

function getOrgPersonnerList (orgId) {
    $http.get('orgPersonRel/getPerOrgRelPage', {
        orgId: orgId,
        orgRootId: '1'
    }, function (data) {
        initOrgPersonnelTable(data.records)
    }, function (err) {
        console.log(err)
    })
}
function initTable (results) {
    var table = $("#regionTable").DataTable({
        'data': results,
        'searching': false,
        'autoWidth': false,
        'ordering': true,
        'initComplete': function (settings, json) {
            console.log(settings, json)
        },
        'destroy':true,
        "scrollY": "375px",
        'columns': [
            { 'data': "LOC_ID","title":"序号" , 'className': 'user-account'
            },
            { 'data': "LOC_CODE",
              'title': '行政区域编码', 'className': 'user-account'
              /*'className': 'row-sex',
              // 'render': function (data) {
              //   return data[0].orgTypeName
              // } 
*/            },
            { 'data': "LOC_NAME", 'title': '行政区域名称', 'className': 'user-account' },
            { 'data': "LOC_TYPE", 'title': '行政区域类型' , 'className': 'user-account' },
            { 'data': "LOC_ABBR", 'title': '行政区域简称', 'className': 'user-account'  },
            { 'data': "LOC_ID", 'title': '查看', 
            'render': function (data, type, row, meta) {
            		return "<a href=\"javascript:void(0)\" onClick=detail('"+row.LOC_ID+"')>> </a>";
            		 //return '<a href="list.html?id='+ row.orgId +'" onclick="parent.openTreeById('+orgId+','+row.orgId+')">'+ row.orgName +'</a>'
                }  , 'className': 'user-account'
            },
             
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
            'info': '总_TOTAL_条',  
            'infoEmpty': '没有数据'
        },
        "aLengthMenu": [[10, 20, 50], ["10条/页", "20条/页", "50条/页"]],
        'pagingType': 'simple_numbers',
        'dom': '<"top"f>t<"bottom"ipl>'
         
    });
}



/*var orgId = getQueryString('id');
getOrgList(orgId);
getOrgPersonnerList(orgId);*/

/*$('#editBtn').on('click', function () {
    var url = 'edit.html?id=' + orgId;
    $(this).attr('href', url);
})
$('#searchBtn').on('click', function () {
   var url = 'search.html?id=' + orgId;
   $(this).attr('href', url);
})*/