var orgTreeId = getQueryString('orgTreeId');
var toastr = window.top.toastr;
var syncRuleTable ;
var currentRules;
loadData();
function loadData(){
	$http.get('/org/orgTreeSynchRule/list',{'orgTreeId':orgTreeId},function (data){
		currentRules=data;
		initTable(data);
	})
}
function initTable(results){
	var seq=0;
	syncRuleTable = $("#syncRuleTable").DataTable({
		"pageLength": 20,
	    'destroy':true,
	    'searching': false,
	    'autoWidth': true,
	    'ordering': false,
	    'lSort': false,
	    'info': true,
	    'data': results,
	    'scrollCollapse': true,
	    'pageLength':10000,
	    'columns': [
	        { 'data': null, 'title': "序号",
	            'render': function (data, type, row, meta) {
	            	seq++;
	                return seq;
	            }
	        },
	        { 'data': 'fromOrgTreeName', 'title': '同步的业务树名称', 
	        },
	        { 'data': 'operateType', 'title': '同步操作',
	        	'render':function(data, type, row, meta){
	        		var operateTypes='全部';
	        		if(data == 'insert'){
	        			operateTypes='新增';
	        		}else if(data == 'update'){
	        			operateTypes='更新';
	        		}else if(data == 'delete'){
	        			operateTypes='删除';
	        		}
	        		return operateTypes;
	        	}
	        },
	        { 'data': 'synchModel', 'title': '是否开启双向同步', 
	        	'render':function(data, type, row, meta){
	        		return data== '0' ? '否':'是';
	        	}
	        },
	        { 'data': 'orgTreeSynchRuleId', 'title': '操作', 
	        	'render': function (data, type, row, meta) {
	        		var html="";
	        		html+="<a href='javascript:void(0)' onclick=\"editRule('"+data+"')\"> 编辑</a>";
	        		html+="&nbsp;&nbsp;&nbsp;&nbsp;";
	        		html+="<a href='javascript:void(0)' onclick=\"removeRule('"+data+"')\"> 删除</a>";
	                return html;
	            }
	        },
	         
	         
	    ],
	    'language': {
	        'loadingRecords': '加载中...',  
	        'processing': '查询中...',  
	        'search': '搜索关键字:',  
	        'emptyTable':'没有数据',
	        'lengthMenu': ' _MENU_ ',  
	        'zeroRecords': '',
	        'paginate': {  
	            'first':      '首页',  
	            'last':       '尾页',  
	            'next':       '下一页',  
	            'previous':   '上一页'  
	        },  
	        'info': '',  
	       'infoEmpty': '',
	    },
	    'pagingType': 'simple_numbers',
	    'dom': '<"top"f>t<"bottom"ipl>',
	    "paging": false, // 禁止分页
	});
}
$('#addBtn').click(function(){
	$('#forms').show();
	initFromOrgTree()
});
function initFromOrgTree(){
	$http.get('/org/orgTreeSynchRule/getTree',{'id':orgTreeId},function (data0){
		$('#fromOrgTreeId').html('');
		var html="";
		A:for(var i=0;i<data0.length;i++){
			for(var j=0;j<currentRules.length;j++){
				if(currentRules[j].fromOrgTreeId==data0[i].orgTreeId){
					continue A;
				}
			}
			html+="<option value='"+data0[i].orgTreeId+"'>"+data0[i].orgTreeName+"</option>";
		}
		if(html == ""){
			html="<option value='0'>没有需要同步的树</option>"
		}
		$('#fromOrgTreeId').html(html);
		
		seajs.use('/vendors/lulu/js/common/ui/Select', function () {
			$('#fromOrgTreeId').selectMatch();
		})
	})
}
var currentId=0;
function save(){
	var obj={
			toOrgTreeId:orgTreeId,
			fromOrgTreeId:$('#fromOrgTreeId').val(),
			operateType:$('#operateType').val(),
			synchModel:$('#synchModel').val(),
			orgTreeSynchRuleId:currentId
	}
	if(obj.fromOrgTreeId == '0'){
		toastr.error('没有需要同步的树');
		return ;
	}
	
	$http.post(currentId==0?'/org/orgTreeSynchRule/add':'/org/orgTreeSynchRule/update',JSON.stringify(obj),function(data){
		toastr.success('操作成功');
		initFromOrgTree()
		loadData();
		currentId=0;
	})
}
function removeRule(id){
	parent.layer.confirm('确定删除?', {
        icon: 0,
        title: '提示',
        btn: ['确定','取消']
    }, function(index, layero){
        parent.layer.close(index);
        var obj={
    			orgTreeSynchRuleId:id
    	}
        $http.post('/org/orgTreeSynchRule/delete',JSON.stringify(obj),function(data){
        	toastr.success('操作成功');
        	initFromOrgTree()
    		loadData();
    		currentId=0;
        });
    }, function(){

    });
}
function editRule(id){
	$('#forms').show();
	currentId=id;
	syncRuleTable.data().each( function (d) {
	    if(d.orgTreeSynchRuleId==id){
	    	console.log(d)
	    	if('0'==$('#fromOrgTreeId').val()){
	    		$('#fromOrgTreeId').html('');
	    	}
	    	$('#fromOrgTreeId').append("<option value='"+d.fromOrgTreeId+"'>"+d.fromOrgTreeName+"</option>")
	    	$('#fromOrgTreeId').val(d.fromOrgTreeId);
	        //$('#operateType').find('option').eq(1).prop('selected', true);
	    	$('#operateType').val(d.operateType);
	    	//$('#operateType').selectMatch();
	    	$('#synchModel').val(d.synchModel);
	    	
	    }else{
	    	$('#fromOrgTreeId').find("option[value='"+d.fromOrgTreeId+"']").remove();
	    }
	    seajs.use('/vendors/lulu/js/common/ui/Select', function () {
			$('select').selectMatch();
		})
	} );
	
	
	/**/
}