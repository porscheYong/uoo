var i=0,showCheck=getQueryString('showCheck');
var  dataTable,checkAreaCode;
function retrieveData(data, callback, settings) {
 var pageSize=0,pageNo=0;
 
/* for(var i=0;i<aoData.length;i++){
	 if(aoData[i].name=='length'){
	 }
 }*/
 pageSize= data.length;
/* for(var i=0;i<aoData.length;i++){
	 if(aoData[i].name=='start'){
	 }
 }*/
 pageNo= data.start/pageSize+1;
 i=(pageNo-1)*pageSize;
 $.ajax({
     url : '/region/areaCode/listAreaCode',//这个就是请求地址对应sAjaxSource
     data : {'pageNo':pageNo,'pageSize':pageSize,keyWord:$('#keyword').val()},//这个是把datatable的一些基本数据传给后台,比如起始位置,每页显示的行数
     type : 'get',
     dataType : 'json',
     success : function(result) {
		var returnData = {};
		if(result.state==1000){
			returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
	       	returnData.recordsTotal = result.totalRecords;//总记录数
	       	returnData.recordsFiltered = result.totalRecords;//后台不实现过滤功能，每次查询均视作全部结果
	       	returnData.data = result.data;
	       	//调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
	       	//此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
	      	callback(returnData);
		} 
     },
     error : function(msg) {
     }
 });
}
function checkTableRow(obj){
	if(showCheck=='1'){
		$('#areaCodeTable tbody tr').each(function(){$(this).removeClass('selected')});
		obj.toggleClass('selected');
		var data=dataTable.rows('.selected').data();
		checkAreaCode=(data[0]);
	}
}
function searchAreaCode(){
	dataTable.ajax.reload( function( json ) {
	} , true);
	
}
function loadAreaCode(){
	 dataTable = $("#areaCodeTable").DataTable({
        'ajax': retrieveData, //执行函数
        'serverSide':true,
        "processing": true,
        'searching': false,
        'autoWidth': true,
        'ordering': false,
       /* 'initComplete': function (settings, json) {
            console.log(settings, json)
        },*/
        //'destroy':true,
        'columns': [
            { 'data': "areaCodeId","title":"序号" , 'className': 'user-account','defaultContent':'','width':'30px',
            	'render':function(data, type, row, meta){
            		i++;
            		return i;
            	}
            },
            { 'data': "areaCode","title":"区号" , 'className': 'user-account','defaultContent':'',
            	'render': function (data, type, row, meta) {
            	var html="<a href=\"javascript:void(0)\" onClick=\"location.href=('/inaction/region/areacode-edit.html?id="+row.areaCodeId+"')\">"+data+" </a>";
            	return html;
                }  ,
            },
            { 'data': "areaNbr",
              'title': '区号编码', 'className': 'user-account'
            	  ,'defaultContent':'',
            	  'render': function (data, type, row, meta) {
                  	var html="<a href=\"javascript:void(0)\" onClick=\"location.href=('/inaction/region/areacode-edit.html?id="+row.areaCodeId+"')\">"+data+" </a>";
                  	return html;
                      }  ,
            },
			{
            	'data':'regionNames','title':'关联电信区域','className': 'user-account','visible':showCheck!='1'
			},
            /*{ 'data': "LOC_NAME", 'title': '区域名称', 'className': 'user-account' ,'defaultContent':'',
            	'render':function(data, type, row, meta){
            		return "<a href='javascript:void(0)' onclick=\"quickList('"+row.LOC_ID+"')\">"+data+"</a>";
            	}
            },
            { 'data': "LOC_TYPE", 'title': '区域类型' , 'className': 'user-account' ,'defaultContent':'',
                 
            },
            { 'data': "LOC_ABBR", 'title': '区域简称', 'className': 'user-account'  ,'defaultContent':''},*/
             
             
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
loadAreaCode();
$(document).ready(function(){
	 $('#areaCodeTable tbody').on( 'click', 'tr', function () {
		 
		 checkTableRow($(this));
	    });
});

function goEdit(){
	var cid=$('#regionStrCurrent').attr('cid');
	parent.changeIframe('/inaction/region/polloc-edit.html?id='+cid);
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
		url:'/region/politicalLocation/getChildPoliticalLocationInfo/'+id,
		success:function(data){
			if(data.state==1000){
				initTable(data.data)
			}else{
				
			}
		},
		dataType:'json',
		type:'get'
	});
}

function getDetail(id){
	changeIframe('/inaction/region/polloc-edit.html?id='+id);
}
function quickList(id){
	var tree=parent.getTree();
	tree.expandNode(tree.getSelectedNodes[0],true,false ,true);
	var qnode=tree.getNodesByParam('id',id,tree.getSelectedNodes[0])[0];
	tree.selectNode(qnode,false);
	parent.changeIframe('/inaction/region/polloc-list.html?id=' + qnode.id);
}
function initTable (results) {
    
}



 
function goDel(){
	if(confirm('确定删除这条数据？')){
		$.ajax({
			url:'/region/politicalLocation/deletePoliticalLocation',
			data:{"locId":listId},
			dataType:'json',
			type:'post',
			success:function(data){
				parent.layer.confirm(data.state==1000?'操作成功':'操作失败，'+data.message, {
			        icon: 0,
			        title: '提示',
			        btn: ['确定' ]
			    }, function(index, layero){
			        parent.layer.close(index);
			        if(data.state==1000){
			        	//
			        	var selectNode=parent.getCurrentSelectedNode()[0];
			        	var zTree=parent.getTree();
			        	if(selectNode.pId==0||selectNode.pId==null){
			        		parent.changeIframe('/inaction/region/none.html');
			        	}
			        	zTree.removeNode(selectNode, null, "prev");
			        	var nodes= selectNode.getParentNode();
			        	zTree.selectNode(nodes);
			        	$('#regionStrCurrent').text(nodes.name);
			        	var treeNode=nodes;
			        	var str=treeNode.name;
			        	var parentNode=treeNode.getParentNode();
			        	while(parentNode!=null){
			        		str=parentNode.name+">"+str;
			        		parentNode=parentNode.getParentNode();
			        	}
			        	$('#regionStrFull').text(str);
			        	$('#regionStrCurrent').attr('cid',nodes.id);
			        	
			        	$.ajax({
			        		url:'/region/politicalLocation/getChildPoliticalLocationInfo/'+nodes.id,
			        		success:function(data){
			        			if(data.state==1000){
			        				initTable(data.data)
			        			}else{
			        				
			        			}
			        		},
			        		dataType:'json',
			        		type:'get'
			        	});
			        }else{
			        }
			    }, function(){
			    });
				
			}
		});
	}
}
function deleteRegion(id){
	if(confirm('确定删除这条数据？')){
		$.ajax({
			url:'/region/politicalLocation/deletePoliticalLocation',
			data:{"locId":id},
			dataType:'json',
			type:'post',
			success:function(data){
				parent.layer.confirm(data.state==1000?'操作成功':'操作失败，'+data.message, {
			        icon: 0,
			        title: '提示',
			        btn: ['确定' ]
			    }, function(index, layero){
			        parent.layer.close(index);
			    }, function(){
			    });
				getRegionList(listId);
				if(data.state==1000){
					var zTree=parent.getTree();
					var nodes= parent.getCurrentSelectedNode()[0];
					nodes=nodes.children;
					for(var i=0;i<nodes.length;i++){
						if(nodes[i].id==id)zTree.removeNode(nodes[i], parent.getCurrentSelectedNode()[0], "prev");
					}
				}
			}
		});
	}
}