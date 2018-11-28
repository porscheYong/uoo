var orgId = getQueryString('id');
var personnelId = getQueryString('personnelId');
var orgName = getQueryString('name');
var orgNum;


//删除人员组织关系
function deleteOrgPsn(){        
    var psnInfo={
        "orgTreeId": "1",
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
          console.log(data);
          alert('删除成功');
          window.location.href = "list.html?id="+orgId+"&name="+orgName;
        },
        error:function(err){
          console.log(err);
          alert('删除失败');
        }
    });
}

//判断人员组织数量
function getOrgNum(){
    $http.get('/orgPersonRel/getPerOrgRelList', {  
        personnelId: personnelId
    }, function (data) {
        orgNum = data.length;
    }, function (err) {
        console.log(err)
    })
}

function isDelete(){    //询问是否删除
    var r=confirm("是否删除人员");
    if(r == true){
        deleteOrgPsn();   //确定，删除
    }
  }

//点击删除按钮
function delClick(){
    if(orgNum == 1){
        alert("不能删除所有组织下的人员");
    }else{
        isDelete();
    }
}

getOrgNum();
