var toastr = window.top.toastr;
function gotoAdd(){
	parent.changeIframe('/inaction/region/commonregion-add.html?upRegionId='+parent.getCurrentSelectedNode()[0].id+'&upRegionName='+parent.getCurrentSelectedNode()[0].name)
}
function goEdit(){
	var cid=$('#regionStrCurrent').attr('cid');
	var upnode=parent.getCurrentSelectedNode()[0].getParentNode();
	var pid=0,pname="最上级";
	if(upnode!=null){
		pid=upnode.id;pname=upnode.name;
	}
	parent.changeIframe('/inaction/region/commonregion-edit.html?id='+cid+'&upRegionId='+pid+'&upRegionName='+pname);
}
function fastGo(id){
	parent.selectNodeById(id);
}
function getRegionList (id) {
	//初始化页面显示
	var nodes= parent.getCurrentSelectedNode();
	$('#regionStrCurrent').text(nodes[0].name);
	var treeNode=nodes[0];
	var str=treeNode.name;
	var parentNode=treeNode.getParentNode();
	while(parentNode!=null){
		str="<a href='javascript:void(0)' onclick=\"fastGo('"+parentNode.id+"')\">"+parentNode.name+"</a> >"+str;
		parentNode=parentNode.getParentNode();
	}
	$('#regionStrFull').html(str);
	$('#regionStrCurrent').attr('cid',nodes[0].id);
	$http.get('/region/commonRegion/getChildCommonRegionInfo/'+id,{},function(data){
		initTable(data);
	});
}

function getDetail(id){
	changeIframe('/inaction/region/commonregion-edit.html?id='+id);
}
function quickList(id){
	var tree=parent.getTree();
	var upnode=parent.getCurrentSelectedNode()[0];
	tree.expandNode(tree.getSelectedNodes[0],true,false ,true);
	var qnode=tree.getNodesByParam('id',id,tree.getSelectedNodes[0])[0];
	tree.selectNode(qnode,false);
	parent.changeIframe('/inaction/region/commonregion-edit.html?id=' + qnode.id+'&upRegionId='+upnode.id+'&upRegionName='+upnode.name);
}
function initTable (results) {
	var i=0;
    var table = $("#regionTable").DataTable({
        'data': results,
        'searching': false,
        'autoWidth': true,
        'ordering': true,
        'destroy':true,
        "scrollY": "375px",
        'columns': [
            { 'data': "commonRegionId","title":"序号" , 'className': 'user-account','defaultContent':'',
            	'render':function(data, type, row, meta){i++;
            		return i;
            	},'width':'30px'
            },
            { 'data': "regionNbr",
              'title': '区域编码', 'className': 'user-account'
            	  ,'defaultContent':''
              /*'className': 'row-sex',
              // 'render': function (data) {
              //   return data[0].orgTypeName
              // } 
*/            },
            { 'data': "regionName", 'title': '区域名称', 'className': 'user-account' ,'defaultContent':'',
				'render':function(data, type, row, meta){
					return "<a href='javascript:void(0)' onclick=\"quickList('"+row.commonRegionId+"')\">"+data+"</a>";
				}
            },
            { 'data': "regionType", 'title': '区域类型' , 'className': 'user-account' ,'defaultContent':'',
            	'render': function (data, type, row, meta) {
              		var types='';
            	  	for(var i=0;i<parent.typeArray.length;i++){
            	  		if(parent.typeArray[i].itemValue==data){
            	  			types=parent.typeArray[i].itemCnname;
            	  		 
            	  			break;
            	  		}
            	  	}
                	 return types;
                		 //return '<a href="list.html?id='+ row.orgId +'" onclick="parent.openTreeById('+orgId+','+row.orgId+')">'+ row.orgName +'</a>'
                    }  , 'className': 'user-account'
                 
            },
            { 'data': "locationNames", 'title': '关联行政区域名称', 'className': 'user-account'  ,'defaultContent':''},
            { 'data': "areaCode.areaCode", 'title': '关联区号', 'className': 'user-account'  ,'defaultContent':''},
           /* { 'data': "commonRegionId", 'title': '编辑', 
            'render': function (data, type, row, meta) {
            	var html="<a href=\"javascript:void(0)\" onClick=\"parent.changeIframe('/inaction/region/commonregion-edit.html?id="+row.commonRegionId+"&upRegionId="+parent.getCurrentSelectedNode()[0].id+"&upRegionName="+parent.getCurrentSelectedNode()[0].name+"')\">> </a>";
            	//html+="<a class=\"glyphicon glyphicon-remove\"   href=\"javascript:void(0)\" onclick=\"deleteRegion('"+row.COMMON_REGION_ID+"')\" style=\"vertical-align: top;\"></a>";
            	return html;
            		 //return '<a href="list.html?id='+ row.orgId +'" onclick="parent.openTreeById('+orgId+','+row.orgId+')">'+ row.orgName +'</a>'
                }  , 'className': 'user-account', 'width':'50px'
            },*/
             
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
/*
function goDel(){
	if(confirm('确定删除这条数据？')){
		$.ajax({
			url:'/region/commonRegion/deleteCommonRegion',
			data:{"commonRegionId":listId},
			dataType:'json',
			type:'post',
			success:function(data){
				if(data.state==1000){
					toastr.success('操作成功');

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
						url:'/region/commonRegion/getChildCommonRegionInfo/'+nodes.id,
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
					toastr.error('操作失败'+data.message);
				}
				
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
				if(data.state==1000){
					toastr.success('操作成功');
				}else{
					toastr.error('操作失败'+data.message);
				}
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
}*/