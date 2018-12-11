var tableProcess = {
	initPostTable : function(results) {
		var table = $("#postTable")
				.DataTable(
						{
							'data' : results,
							'searching' : false,
							'autoWidth' : false,
							'ordering' : true,
							'initComplete' : function(settings, json) {
								console.log(settings, json)
							},
							'destroy' : true,
							"scrollY" : "375px",
							'columns' : [
									{
										'data' : "postName",
										"title" : "职位名称",
										'className' : 'user-account',
										'defaultContent' : '',
										'render' : function(data, type, row,
												meta) {
											return "<a href='javascript:void(0)' onclick=\"quickList('"
													+ data
													+ "')\">"
													+ data
													+ "</a>";
										}

									},
									 
									{
										'data' : "postType",
										'title' : '职位类型',
										'className' : 'user-account',
										'defaultContent' : '',
										'render' : function(data, type, row,
												meta) {
											var types = '未知类型：' + data;
											for (var i = 0; i < parent.typeArray.length; i++) {
												if (parent.typeArray[i].itemValue == data) {
													types = parent.typeArray[i].itemCnname;

													break;
												}
											}
											return types;
										},
										'className' : 'user-account'

									},
									{
										'data' : "postId",
										'title' : '职位编码',
										'className' : 'user-account',
										'defaultContent' : ''
									},
									{
										'data' : "postId",
										'title' : '操作',
										'render' : function(data, type, row,
												meta) {
											var html = "<a href=\"javascript:void(0)\" onClick=\"parent.changeIframe('/inaction/region/polloc-edit.html?id="
													+ row.LOC_ID
													+ "')\">查看 </a>";
											html += "&nbsp;&nbsp;&nbsp;&nbsp;";
											html += "<a class=\"glyphicon glyphicon-remove\"   href=\"javascript:void(0)\" onclick=\"deleteRegion('"
													+ row.LOC_ID
													+ "')\" style=\"vertical-align: top;\"></a>";
											return html;
											// return '<a href="list.html?id='+
											// row.orgId +'"
											// onclick="parent.openTreeById('+orgId+','+row.orgId+')">'+
											// row.orgName +'</a>'
										},
										'className' : 'user-account'
									},

							],
							'language' : {
								'emptyTable' : '没有数据',
								'loadingRecords' : '加载中...',
								'processing' : '查询中...',
								'search' : '检索:',
								'lengthMenu' : ' _MENU_ ',
								'zeroRecords' : '没有数据',
								'paginate' : {
									'first' : '首页',
									'last' : '尾页',
									'next' : '下一页',
									'previous' : '上一页'
								},
								'info' : '总_TOTAL_条',
								'infoEmpty' : '没有数据'
							},
							"aLengthMenu" : [ [ 10, 20, 50 ],
									[ "10条/页", "20条/页", "50条/页" ] ],
							'pagingType' : 'simple_numbers',
							'dom' : '<"top"f>t<"bottom"ipl>'

						});
	}
}