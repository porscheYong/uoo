var orgTreeId = getQueryString('orgTreeId');
var syncRuleTable ,seq=0;
loadData();
function loadData(){
	var results=[];
	for(var i=0;i<3;i++){
		results.push({
		'treeName':'树树树树树树树'+i,
		'operate':'operate',
		'isDouble':'isDouble',
		'id':i
		});
	}
	initUserRoleTable(results)
}
function initUserRoleTable(results){
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
	        { 'data': 'orgTreeName', 'title': '同步的业务树名称', 
	        },
	        { 'data': 'operate', 'title': '同步操作', 
	        },
	        { 'data': 'isDouble', 'title': '是否开启双向同步', 
	        },
	        { 'data': 'id', 'title': '操作', 
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
});