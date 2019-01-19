var orgId = getQueryString('id');
var personnelId = getQueryString('personnelId');
var orgName = getQueryString('name');
var orgTreeId = getQueryString('orgTreeId');
var orgNum;
var toastr = window.top.toastr;


//删除人员组织关系
function deleteOrgPsn(){        
    var psnInfo={
        "orgTreeId": orgTreeId,
        "orgId": orgId,
        "personnelId": parseInt(personnelId)
      };

    $.ajax({
        url: '/orgPersonRel/deleteOrgPsn',
        type: 'POST',
        contentType: "application/json",
        data: JSON.stringify(psnInfo),
        dataType:"JSON",
        success: function (data) { //返回json结果
          if(data.state == 1000){
            toastr.success('删除成功');
            window.location.href = "list.html?id="+orgId+"&name="+orgName+"&orgTreeId="+orgTreeId;
          }else{
            toastr.error('删除失败');
          }
        },
        error:function(err){
          toastr.error('删除失败');
        }
    });
}

//删除人员组织关系
function deletePersonnel(){
	
	 $http.delet('/personnel/deletePersonnel?personnelId=' + personnelId,{},function(data){
	        toastr.success('操作成功');
	        window.location.href = "list.html?id="+orgId+"&name="+orgName+"&orgTreeId="+orgTreeId;
	    });
}

function deleteTbAcct(){    //删除人员
	$http.get('/user/getUserList', {personnelId:personnelId}, function (data) {
		if(data.total>0){
			var hasMainAcct=false,hasSubAcct=false;
			for(var i=0;i<data.total;i++){
				if(data.records[i].type==1){
					hasMainAcct=true;
				}else{
					hasSubAcct=true;
				}
			}
			if(hasMainAcct&&hasSubAcct){
				parent.layer.confirm('人员已关联主账号和从账号，此操作将删除该人员、主账号、从账号信息，是否继续？', {
				      icon: 0,
				      title: '提示',
				      btn: ['确定','取消']
				  }, function(index, layero){
				      // 未分类
				      if (orgId == '88888888')
				          deletePersonnel();
				      else
				        deleteOrgPsn();
				      parent.layer.close(index);
				    }, function(){
				  
				    });
			}else if(hasMainAcct&&!hasSubAcct){
				parent.layer.confirm('人员已关联主账号，此操作将删除该人员和主账号信息，是否继续？', {
				      icon: 0,
				      title: '提示',
				      btn: ['确定','取消']
				  }, function(index, layero){
				      // 未分类
				      if (orgId == '88888888')
				          deletePersonnel();
				      else
				        deleteOrgPsn();
				      parent.layer.close(index);
				    }, function(){
				  
				    });
			}
		}else{
			parent.layer.confirm('此操作将删除该用户, 是否继续?', {
			      icon: 0,
			      title: '提示',
			      btn: ['确定','取消']
			  }, function(index, layero){
			      // 未分类
			      if (orgId == '88888888')
			          deletePersonnel();
			      else
			        deleteOrgPsn();
			      parent.layer.close(index);
			    }, function(){
			  
			    });
		}
		 
    }, function (err) {
    })
  }
