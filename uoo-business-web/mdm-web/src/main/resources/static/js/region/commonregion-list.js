function goEdit(){
	var cid=$('#regionStrCurrent').attr('cid');
	console.log('goto :'+cid);
	parent.changeIframe('/inaction/region/commonregion-edit.html?id='+cid);
}
function getRegionList (id) {
	//初始化页面显示
	var nodes= parent.getCurrentSelectedNode();
	$('#regionStrCurrent').text(nodes[0].name);
	var treeNode=nodes[0];
	var str=treeNode.name;
	var parentNode=treeNode.getParentNode();
	while(parentNode!=null){
		str=parentNode.name+">"+str;
		parentNode=parentNode.getParentNode();
	}
	$('#regionStrFull').text(str);
	$('#regionStrCurrent').attr('cid',nodes[0].id);
	$.ajax({
		url:'/region/commonRegion/getChildCommonRegionInfo/'+id,
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

function getDetail(id){
	changeIframe('/inaction/region/commonregion-edit.html?id='+id);
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
            { 'data': "COMMON_REGION_ID","title":"序号" , 'className': 'user-account','defaultContent':''
            },
            { 'data': "REGION_NBR",
              'title': '区域编码', 'className': 'user-account'
            	  ,'defaultContent':''
              /*'className': 'row-sex',
              // 'render': function (data) {
              //   return data[0].orgTypeName
              // } 
*/            },
            { 'data': "REGION_NAME", 'title': '区域名称', 'className': 'user-account' ,'defaultContent':''},
            { 'data': "REGION_TYPE", 'title': '区域类型' , 'className': 'user-account' ,'defaultContent':''},
            { 'data': "LOC_NAME", 'title': '关联行政区域名称', 'className': 'user-account'  ,'defaultContent':''},
            { 'data': "COMMON_REGION_ID", 'title': '操作', 
            'render': function (data, type, row, meta) {
            	var html="<a href=\"javascript:void(0)\" onClick=\"parent.changeFrame('"+row.COMMON_REGION_ID+"')\">查看 </a>";
            	html+="&nbsp;&nbsp;&nbsp;&nbsp;";
            	html+="<a class=\"glyphicon glyphicon-remove\"   href=\"javascript:void(0)\" onclick=\"deleteRegion('"+row.COMMON_REGION_ID+"')\" style=\"vertical-align: top;\"></a>";
            	return html;
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



var listId = getQueryString('id');
getRegionList(listId);
/*$('#editBtn').on('click', function () {
    var url = 'edit.html?id=' + orgId;
    $(this).attr('href', url);
})
$('#searchBtn').on('click', function () {
   var url = 'search.html?id=' + orgId;
   $(this).attr('href', url);
})*/
function goDel(){
	if(confirm('确定删除这条数据？')){
		$.ajax({
			url:'/region/commonRegion/deleteCommonRegion',
			data:{"commonRegionId":listId},
			dataType:'json',
			type:'post',
			success:function(data){
				alert(data.state==1?'删除成功':data.message);
				//
				getRegionList( parent.getCurrentSelectedNode()[0].id);
			}
		});
	}
}
function deleteRegion(id){
	if(confirm('确定删除这条数据？')){
		$.ajax({
			url:'/region/commonRegion/deleteCommonRegion',
			data:{"commonRegionId":id},
			dataType:'json',
			type:'post',
			success:function(data){
				alert(data.state==1?'删除成功':data.message);
				getRegionList(listId);
			}
		});
	}
}