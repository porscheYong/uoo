var curStatus = "init", curAsyncCount = 0, asyncForAll = false,
		goAsync = false;
var setting = {
 			async: {
				enable: true,
				url:  "/region/politicalLocation/getTreePoliticalLocation",
				autoParam:["id" ],
				/*otherParam:{"otherParam":"zTreeAsyncTest"},*/
				dataFilter: filter,
				type: "get"
			},
			callback: {
				beforeAsync: beforeAsync,
				onAsyncSuccess: onAsyncSuccess,
				onAsyncError: onAsyncError,
				onClick: zTreeOnClick
			},
			simpleData:{
				enable:true,
				idKey:"id",
				pIdKey:"pId",
				rootPId:null
				
			},
			view: {
		        showLine: false,
		        showIcon: false,
		        dblClickExpand: false
		    },
		};
		function zTreeOnClick(event, treeId, treeNode) {
			$('#editDiv').hide();
			$('#listDiv').show();
			
		    var currentId=treeNode.id
			//alert(treeNode.getParentNode());
			var currentName= treeNode.name;
			var str=treeNode.name;
			var parentNode=treeNode.getParentNode();
			while(parentNode!=null){
				str=parentNode.name+">"+str;
				parentNode=parentNode.getParentNode();
			}
			$('#pollocStrFull').text(str);
			$('#pollocStrCurrent').attr("cid",currentId);
			$('#pollocStrCurrent').text(currentName);
			//alert(currentId+"=="+str);
			getRegionList(currentId);
		};
		function filter(treeId, parentNode, childNodes) {
			if(childNodes.state!=1){
				return null;
			}
			var c=childNodes.data;
			if (!c) return null;
			for (var i=0, l=c.length; i<l; i++) {
				c[i].isParent= true;
				c[i].name = c[i].name.replace(/\.n/g, '.');
				 
			}
			return c;
		}

		function beforeAsync() {
			curAsyncCount++;
		}
		
		function onAsyncSuccess(event, treeId, treeNode, msg) {
			 
			msg =  eval("("+msg+")");
			if(msg.state != 1){
				console.log(msg.state);
				return;
			}
			
			var data=msg.data;
			
			curAsyncCount--;
			if (curStatus == "expand") {
				expandNodes(treeNode.children);
			} else if (curStatus == "async") {
				asyncNodes(treeNode.children);
			}

			if (curAsyncCount <= 0) {
				if (curStatus != "init" && curStatus != "") {
					//$("#demoMsg").text((curStatus == "expand") ? demoMsg.expandAllOver : demoMsg.asyncAllOver);
					asyncForAll = true;
				}
				curStatus = "";
			}
		}

		function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
			curAsyncCount--;

			if (curAsyncCount <= 0) {
				curStatus = "";
				if (treeNode!=null) asyncForAll = true;
			}
		}

		
		function expandAll() {
			if (!check()) {
				return;
			}
			var zTree = $.fn.zTree.getZTreeObj("standardTree");
			if (asyncForAll) {
				//$("#demoMsg").text(demoMsg.expandAll);
				zTree.expandAll(true);
			} else {
				expandNodes(zTree.getNodes());
				if (!goAsync) {
					//$("#demoMsg").text(demoMsg.expandAll);
					curStatus = "";
				}
			}
		}
		function expandNodes(nodes) {
			if (!nodes) return;
			curStatus = "expand";
			var zTree = $.fn.zTree.getZTreeObj("standardTree");
			for (var i=0, l=nodes.length; i<l; i++) {
				zTree.expandNode(nodes[i], true, false, false);
				if (nodes[i].isParent && nodes[i].zAsync) {
					expandNodes(nodes[i].children);
				} else {
					goAsync = true;
				}
			}
		}

		function asyncAll() {
			if (!check()) {
				return;
			}
			var zTree = $.fn.zTree.getZTreeObj("standardTree");
			if (asyncForAll) {
				//$("#demoMsg").text(demoMsg.asyncAll);
			} else {
				asyncNodes(zTree.getNodes());
				if (!goAsync) {
					//$("#demoMsg").text(demoMsg.asyncAll);
					curStatus = "";
				}
			}
		}
		function asyncNodes(nodes) {
			if (!nodes) return;
			curStatus = "async";
			var zTree = $.fn.zTree.getZTreeObj("standardTree");
			for (var i=0, l=nodes.length; i<l; i++) {
				if (nodes[i].isParent && nodes[i].zAsync) {
					asyncNodes(nodes[i].children);
				} else {
					goAsync = true;
					zTree.reAsyncChildNodes(nodes[i], "refresh", true);
				}
			}
		}

		function reset() {
			if (!check()) {
				return;
			}
			asyncForAll = false;
			goAsync = false;
			$.fn.zTree.init($("#standardTree"), setting);
		}

		function check() {
			if (curAsyncCount > 0) {
				return false;
			}
			return true;
		}

		$(document).ready(function(){
			$('#editDiv').hide();
			$('#listDiv').hide();
			$.fn.zTree.init($("#standardTree"), setting);
			$('#editBtn').bind('click',editPolLoc);
			$('#saveBtn').bind('click',savePolLoc);
			$('#addBtn').bind('click',addPolLoc);
			//asyncAll();
			//$("#expandAllBtn").bind("click", expandAll);
			/*$("#asyncAllBtn").bind("click", asyncAll);
			$("#resetBtn").bind("click", reset);*/
		});
		function reloadTable(treeId){
			$('#editDiv').hide();
			$('#listDiv').show();
			var treeObj = $.fn.zTree.getZTreeObj("standardTree");
			var nodes = treeObj.getSelectedNodes();
			var treeNode=nodes[0]
			
			var currentId=treeNode.id;
			//alert(treeNode.getParentNode());
			var currentName= treeNode.name;
			var str=treeNode.name;
			var parentNode=treeNode.getParentNode();
			while(parentNode!=null){
				str=parentNode.name+">"+str;
				parentNode=parentNode.getParentNode();
			}
			$('#pollocStrFull').text(str);
			$('#pollocStrCurrent').attr("cid",currentId);
			$('#pollocStrCurrent').text(currentName);
			//alert(currentId+"=="+str);
			getRegionList(currentId);
		}
		function editPolLoc(){
			var editId=$('#pollocStrCurrent').attr('cid');
			detail(editId);
		}
		function addPolLoc(){
			//$('#treeId').val($('#pollocStrCurrent').attr('cid'));
			 $('#locId').val("");
			 $('#locName').val("");
			 $('#locCode').val("");
			 $('#locAbbr').val("");
			 $('#locDesc').val("");
			 $('#locType').val("1");
			var currentUpId=$('#pollocStrCurrent').attr("cid");
			$('#listDiv').hide();
			$('#editDiv').show();
			
			$.ajax({
				url:'/region/politicalLocation/getTreePoliticalLocation',
				dataType:'json',
				type:'get',
				success:function(tree){
					$('#upLocId').html("");
					if(tree.state==1){
						 $.each(tree.data,function(i,item){
							 var html="";
							 html+="<option value='"+item.id+"'";
							 if(currentUpId==item.id){
								 html+=" selected='selected'";
							 }
							 html+= " >"+item.name+"</option>"
							 $('#upLocId').append(html);
							 deepTree(item.id,currentUpId);
						 });
						 $('#treeId').val($('#pollocStrCurrent').attr('cid'));
					} else{
						alert('数据异常 刷新重试');
					}
				}
			});
			
		}
		function savePolLoc(){
			var data= {
				"locId":$("#locId").val(),
				"upLocId":$("#upLocId").val(),
				"locCode":$("#locCode").val(),
				"locName":$("#locName").val(),
				"locAbbr":$("#locAbbr").val(),
				"locType":$("#locType").val(),
				"locDesc":$("#locDesc").val()
			};
			var tmpId=$("#locId").val();
			var url= (tmpId.length>=1&&tmpId!='undefined')?'/region/politicalLocation/updatePoliticalLocation':'/region/politicalLocation/addPoliticalLocation'
			var treeId=$('#treeId').val();
			$.ajax({
				type:'POST',
				dataType:'json',
				url:url,
				data:$('#locForm').serialize(),
				
				success:function(data){
					if(data.state==1){
						var treeObj = $.fn.zTree.getZTreeObj("standardTree");
						var nodes = treeObj.getSelectedNodes();
						if (nodes.length>0) {
							treeObj.reAsyncChildNodes(nodes[0], "refresh");
						}
						//ztree reload
						var treeObj = $.fn.zTree.getZTreeObj("standardTree");
						var nodes = treeObj.getSelectedNodes();
						treeObj.reAsyncChildNodes(null, "refresh");
						/*var treeObj = $.fn.zTree.getZTreeObj("tree");
						var nodes = treeObj.getNodes();*/
						
						
						
						/*while (nodes.length>0) {
							for(var i=0;i<nodes.length;i++){
								if(nodes[i]==treeId){
									treeObj.selectNode(nodes[i]);
								} 
							}
						}*/
						reloadTable(treeId);
					}else{
						alert(data.message);
					}
				}
					
			});
		}
		function detail(id){
			//1 获取id  2 隐藏表格 3获取id信息  4 保存 5 刷新ztree 6 刷新表格
			//拿取id信息
			$.ajax({
				url:'/region/politicalLocation/getPoliticalLocation/id='+id,
				dataType:'json',
				type:'get',
				success:function(data){
					if(data.state==1){
						$('#listDiv').hide();
						$('#editDiv').show();
						fullData(data.data);
					}else{
						alert('获取数据异常 刷新重试');
					}
				}
			});
		}
		function fullData(data){
			$.ajax({
				url:'/region/politicalLocation/getTreePoliticalLocation',
				dataType:'json',
				type:'get',
				success:function(tree){
					$('#upLocId').html("");
					if(tree.state==1){
						 $.each(tree.data,function(i,item){
							 var html="";
							 html+="<option value='"+item.id+"'";
							 if(data.upLocId==item.id){
								 html+=" selected='selected'";
							 }
							 html+= " >"+item.name+"</option>"
							 $('#upLocId').append(html);
							 deepTree(item.id,data.upLocId);
						 });
						 $('#treeId').val($('#pollocStrCurrent').attr('cid'));
						 $('#locId').val(data.locId);
						 $('#locName').val(data.locName);
						 $('#locCode').val(data.locCode);
						 $('#locAbbr').val(data.locAbbr);
						 $('#locDesc').val(data.locDesc);
						 $('#locType').val(data.locType);
						 console.log('type:'+data.locType);
					} else{
						alert('数据异常 刷新重试');
					}
				}
			});
		}
		 function deepTree(id,myuplocid){
			 $.ajax({
					url:'/region/politicalLocation/getTreePoliticalLocation?id='+id,
					dataType:'json',
					type:'get',
					success:function(tree){
						if(tree.state==1){
							var html="";
							var up=0;
							 $.each(tree.data,function(i,item){
								 html+="<option value='"+item.id+"'";
								 if( myuplocid==item.id){
									 html+=" selected='selected'";
								 }
								 up=item.pId;
								 html+= " >"+item.name+"</option>"
							 });
							 
							 //某一层的 循环查找插入  那么久插入
							 $('#upLocId option').each(function(){
								 var id=$(this).val();
								 if(id == up){
									 console.info('success');
									 $(this).after(html);
								 }
							 }) ;
							 
							 $.each(tree.data,function(i,item){
								 deepTree(item.id,myuplocid);
							 });
							 
						}  
					}
				});
		 }
		