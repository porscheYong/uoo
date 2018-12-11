var setting = {
	data : {
		key : {
			name : "postName"
		},
		simpleData : {
			enable : true,
			idKey : "postId",
			pIdKey : "parentPostId",
		}
	},
	view : {
		showLine : false,
		showIcon : false,
		dblClickExpand : false,
		fontCss : getFontCss
	},
	callback : {
		onClick : zTreeOnClick
	}
};


$(document).ready(function() {
	loadPostTree();
});
function zTreeOnClick(event, treeId, treeNode) {
	changeIframe('/inaction/position/post/list.html?id=' + treeNode.postId);
}
function getFontCss(treeId, treeNode) {
	return (!!treeNode.highlight) ? {
		color : "#A60000",
		"font-weight" : "bold"
	} : {
		color : "#333",
		"font-weight" : "normal"
	};
}
function loadPostTree() {
	$.ajax({
		url : '/tbPost/postTree',
		dataType : 'json',
		type : 'get',
		success : function(data) {
			if (data.state == 1000) {
				$.fn.zTree.init($("#standardTree"), setting, data.data);
			} else {
				alert('加载职位树失败，请重试');
			}
		}
	});
}
function changeIframe(url) {
	$('#myFrame').attr("src", url);
}
