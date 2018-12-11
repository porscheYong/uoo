function gotoAdd(){
	parent.changeIframe('/inaction/region/polloc-add.html?upLocId='+parent.getCurrentSelectedNode()[0].id+'&upLocName='+parent.getCurrentSelectedNode()[0].name)
}
function goEdit(){
	var cid=$('#regionStrCurrent').attr('cid');
	var upnode=parent.getCurrentSelectedNode()[0].getParentNode();
	var pid=0,pname="最上级";
	if(upnode!=null){
		pid=upnode.id;pname=upnode.name;
	}
	parent.changeIframe('/inaction/region/polloc-edit.html?id='+cid+'&parentLocId='+pid+'&parentLocName='+pname);
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
	var upnode=parent.getCurrentSelectedNode()[0];
	tree.expandNode(tree.getSelectedNodes[0],true,false ,true);
	var qnode=tree.getNodesByParam('id',id,tree.getSelectedNodes[0])[0];
	tree.selectNode(qnode,false);
	parent.changeIframe('/inaction/region/polloc-edit.html?id=' + qnode.id+'&parentLocName='+upnode.name+'&parentLocId='+upnode.id);
}
function initTable (results) {
	var i=0;
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
            { 'data': "LOC_ID","title":"序号" , 'className': 'user-account','defaultContent':'','width':'30px',
            	'render':function(data, type, row, meta){
            		return ++i;
            	}
             
            },
            { 'data': "LOC_CODE",
              'title': '区域编码', 'className': 'user-account'
            	  ,'defaultContent':''
              /*'className': 'row-sex',
              // 'render': function (data) {
              //   return data[0].orgTypeName
              // } 
*/            },
            { 'data': "LOC_NAME", 'title': '区域名称', 'className': 'user-account' ,'defaultContent':'',
            	'render':function(data, type, row, meta){
            		return "<a href='javascript:void(0)' onclick=\"quickList('"+row.LOC_ID+"')\">"+data+"</a>";
            	}
            },
            { 'data': "LOC_TYPE", 'title': '区域类型' , 'className': 'user-account' ,'defaultContent':'',
              'render': function (data, type, row, meta) {
              		var types='未知类型：'+data;
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
            { 'data': "LOC_ABBR", 'title': '区域简称', 'className': 'user-account'  ,'defaultContent':''},
            /*{ 'data': "COMMON_REGION_ID", 'title': '操作', 
            'render': function (data, type, row, meta) {
            	var html="<a href=\"javascript:void(0)\" onClick=\"parent.changeIframe('/inaction/region/polloc-edit.html?id="+row.LOC_ID+"&parentLocId="+parent.getCurrentSelectedNode()[0].id+"&parentLocName="+parent.getCurrentSelectedNode()[0].name+"')\">> </a>";
            	html+="&nbsp;&nbsp;&nbsp;&nbsp;";
            	//html+="<a class=\"glyphicon glyphicon-remove\"   href=\"javascript:void(0)\" onclick=\"deleteRegion('"+row.LOC_ID+"')\" style=\"vertical-align: top;\"></a>";
            	return html;
            		 //return '<a href="list.html?id='+ row.orgId +'" onclick="parent.openTreeById('+orgId+','+row.orgId+')">'+ row.orgName +'</a>'
                }  , 'className': 'user-account'
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
			    }, function(){
			    });
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