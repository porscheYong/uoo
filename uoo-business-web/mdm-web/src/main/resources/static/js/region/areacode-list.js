function retrieveData(sSource,aoData,fnCallback) {
/* var startDate = {
   "name":"startDate",
   "value":$("#from").val()
 }
 var endDate = {
   "name":"endDate",
   "value":$("#to").val()
 }
 //我这里按照请求数据的格式增加了自己的查询条件 请求数据格式固定为 name-value的格式 可以使用
 //alert打印查看 包含了基本的页码、页面数据元素、等信息以及新增的查询条件
 aoData.push(startDate);
 aoData.push(endDate);*/
 var pageSize=0,pageNo=0;
 
 //start=(currentPage-1)*pageSize
 for(var i=0;i<aoData.length;i++){
	 if(aoData[i].name=='length'){
		 pageSize= aoData[i].value;
	 }
 }
 for(var i=0;i<aoData.length;i++){
	 if(aoData[i].name=='start'){
		 pageNo= aoData[i].value/pageSize+1;
	 }
 }
 $.ajax({
     url : '/region/areaCode/listAreaCode',//这个就是请求地址对应sAjaxSource
     data : {'pageNo':pageNo,'pageSize':pageSize},//这个是把datatable的一些基本数据传给后台,比如起始位置,每页显示的行数
     type : 'get',
     dataType : 'json',
     async : false,
     success : function(result) {
         fnCallback(result);//把返回的数据传给这个方法就可以了,datatable会自动绑定数据的
     },
     error : function(msg) {
     }
 });
}
 
function loadAreaCode(){
	var table = $("#areaCodeTable").DataTable({
        'fnServerData': retrieveData, //执行函数
        'serverSide':true,
        'searching': false,
        'autoWidth': false,
        'ordering': true,
        'initComplete': function (settings, json) {
            console.log(settings, json)
        },
        'destroy':true,
        "scrollY": "375px",
        'columns': [
            { 'data': "areaCodeId","title":"序号" , 'className': 'user-account','defaultContent':'',
             
            },
            { 'data': "areaCode","title":"区号" , 'className': 'user-account','defaultContent':'',
            	'render':function(data, type, row, meta){
            		return "<a href='javascript:void(0)' onclick=\"quickList('"+data+"')\">"+data+"</a>";
            	}
            
            },
            { 'data': "areaNbr",
              'title': '区号编码', 'className': 'user-account'
            	  ,'defaultContent':'',
            	  
              /*'className': 'row-sex',
              // 'render': function (data) {
              //   return data[0].orgTypeName
              // } 
*/            },
            /*{ 'data': "LOC_NAME", 'title': '区域名称', 'className': 'user-account' ,'defaultContent':'',
            	'render':function(data, type, row, meta){
            		return "<a href='javascript:void(0)' onclick=\"quickList('"+row.LOC_ID+"')\">"+data+"</a>";
            	}
            },
            { 'data': "LOC_TYPE", 'title': '区域类型' , 'className': 'user-account' ,'defaultContent':'',
                 
            },
            { 'data': "LOC_ABBR", 'title': '区域简称', 'className': 'user-account'  ,'defaultContent':''},*/
            { 'data': "COMMON_REGION_ID", 'title': '操作', 
            'render': function (data, type, row, meta) {
            	var html="<a href=\"javascript:void(0)\" onClick=\"parent.changeIframe('/inaction/region/polloc-edit.html?id="+row.LOC_ID+"')\">查看 </a>";
            	html+="&nbsp;&nbsp;&nbsp;&nbsp;";
            	//html+="<a class=\"glyphicon glyphicon-remove\"   href=\"javascript:void(0)\" onclick=\"deleteRegion('"+row.LOC_ID+"')\" style=\"vertical-align: top;\"></a>";
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
loadAreaCode();
function goEdit(){
	var cid=$('#regionStrCurrent').attr('cid');
	console.log('goto :'+cid);
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
			console.log(data);
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
				if(data.state==1000){
					//
					var selectNode=parent.getCurrentSelectedNode()[0];
					console.log(parent.getCurrentSelectedNode()[0])
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
							console.log(data);
							if(data.state==1000){
								initTable(data.data)
							}else{
								
							}
						},
						dataType:'json',
						type:'get'
					});
				}else{
					alert(data.state==1000?'删除成功':data.message);
				}
				
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
				alert(data.state==1000?'删除成功':data.message);
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