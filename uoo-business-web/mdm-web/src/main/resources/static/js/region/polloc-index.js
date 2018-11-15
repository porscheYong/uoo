var setting = {
			async: {
				enable: true,
				url:base + "region/politicalLocation/getTreePoliticalLocation/0",
				/*autoParam:["id", "name=n", "level=lv"],
				otherParam:{"otherParam":"zTreeAsyncTest"*/},
				dataFilter: filter,
				type: "get"
			},
			callback: {
				beforeAsync: beforeAsync,
				onAsyncSuccess: onAsyncSuccess,
				onAsyncError: onAsyncError
			}
		};

		function filter(treeId, parentNode, childNodes) {
			if (!childNodes) return null;
			for (var i=0, l=childNodes.length; i<l; i++) {
				childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
			}
			return childNodes;
		}

		function beforeAsync() {
			curAsyncCount++;
		}
		
		function onAsyncSuccess(event, treeId, treeNode, msg) {
			curAsyncCount--;
			if (curStatus == "expand") {
				expandNodes(treeNode.children);
			} else if (curStatus == "async") {
				asyncNodes(treeNode.children);
			}

			if (curAsyncCount <= 0) {
				if (curStatus != "init" && curStatus != "") {
					$("#demoMsg").text((curStatus == "expand") ? demoMsg.expandAllOver : demoMsg.asyncAllOver);
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

		var curStatus = "init", curAsyncCount = 0, asyncForAll = false,
		goAsync = false;
		function expandAll() {
			if (!check()) {
				return;
			}
			var zTree = $.fn.zTree.getZTreeObj("standardTree");
			if (asyncForAll) {
				$("#demoMsg").text(demoMsg.expandAll);
				zTree.expandAll(true);
			} else {
				expandNodes(zTree.getNodes());
				if (!goAsync) {
					$("#demoMsg").text(demoMsg.expandAll);
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
				$("#demoMsg").text(demoMsg.asyncAll);
			} else {
				asyncNodes(zTree.getNodes());
				if (!goAsync) {
					$("#demoMsg").text(demoMsg.asyncAll);
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
			$.fn.zTree.init($("#standardTree"), setting);
			asyncAll();
			//$("#expandAllBtn").bind("click", expandAll);
			/*$("#asyncAllBtn").bind("click", asyncAll);
			$("#resetBtn").bind("click", reset);*/
		});