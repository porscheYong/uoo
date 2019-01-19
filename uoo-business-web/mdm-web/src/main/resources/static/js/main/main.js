var orgVal,
    psnVal,
    acctVal,
    slaveAcctVal,
    psnValData = [],
    psnNameData = [],
    orgValData = [],
    orgNameData = [];

// var loading = new Loading();
var loading = parent.loadingHome;

// loading.screenMaskEnable('LAY_app_body');

function getHomeStatistics(){
    $http.get('/homeStatistics/getHomeStatistics', {}, 
        function (data) {
            for(var i=0;i<data.length;i++){
                switch(data[i].labelType){
                    case "LOCAL_ORG":
                        orgValData.push(data[i].labelVal);
                        orgNameData.push(data[i].labelName);
                        break;
                    case "LOCAL_PSN":
                        psnValData.push(data[i].labelVal);
                        psnNameData.push(data[i].labelName);
                        break;
                    case "ORG_PSN":
                        if(data[i].labelKey == "ORG_PSN_1"){
                            orgVal = formatVal(data[i].labelVal);
                        }else if(data[i].labelKey == "ORG_PSN_2"){
                            psnVal = formatVal(data[i].labelVal);
                        }else if(data[i].labelKey == "ORG_PSN_3"){
                            acctVal = formatVal(data[i].labelVal);
                        }else{
                            slaveAcctVal = formatVal(data[i].labelVal);
                        }
                        break;
                }
            }
            setSysInfo();
            setPsnChart();
            setOrgChart();
            // setSysChart();
      }, function (err) {
            parent.loadingHome.screenMaskDisable('LAY_app_body');
      })
}


//各地市人员
function setPsnChart(){
    var myChart = echarts.init(document.getElementById('Personnel')); 
    option = {
        //鼠标悬停显示数值
        // tooltip : {
        //     trigger: 'axis',
        //     axisPointer : {            // 坐标轴指示器，坐标轴触发有效
        //         type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        //     }, 
        //     formatter: function(params){
        //         return params[0].data;
        //     }
        // },//end
        grid: { //设置图标大小
            left: 50, //100
            right: 20, //60
            bottom:40,
            top: 20,
        },
        calculable : true,
        xAxis : [
            {
            type: 'category',
            axisLabel: {
                interval: 0,
                length:10,
                textStyle:{
                    fontSize:14,
                    color: '#999',
                    interval:0,//横轴信息全部显示
                    rotate:-30,//-30度角倾斜显示
                },
            },
            data : psnNameData   //['省公司','杭州','宁波','温州','绍兴','湖州','嘉兴','金华','衢州','台州','丽水','舟山']
            }
        ],
        yAxis: {
            splitLine:{
                lineStyle: {
                    color: '#EEE'
                    }
            },
            axisLabel: {
                textStyle: {
                    color: '#CCC',
                    fontSize: 12
                }
            }
        },
        series : [
            {
                name:'人员',
                barWidth : 22,//柱图宽度
                slient:true,
                barGap:'-100%',
                label: {
                    normal: {
                        show: true, //设置柱状图上方的数据是否显示
                        position: 'top',
                        textStyle:{
                            color:'#CCC',
                            fontSize: 12
                        },
                    }
                },
                type:'bar',
                data: psnValData,
                itemStyle:{
                    normal:{
                        color:'#a0d98c',
                    }
                },
            }
        ]
    }; 
    myChart.setOption(option); 
}

//各地市组织
function setOrgChart(){
    var myChart = echarts.init(document.getElementById('Org')); 
    option = {
        grid: {
            left: 50,
            right: 20,
            bottom:40,
            top: 20,
        },
        calculable : true,
        xAxis : [
            {
            type: 'category',
            axisLabel: {
                interval: 0,
                textStyle:{
                    fontSize:14,
                    color: '#999',
                    interval:0,//横轴信息全部显示
                    rotate:-30,//-30度角倾斜显示
                },
            },
                data : orgNameData
            }
        ],
        yAxis: {
            splitLine:{
                lineStyle: {
                    color: '#EEE'
                    }
            },
            axisLabel: {
                textStyle: {
                    color: '#CCC',
                    fontSize: 12
                }
            }
        },
        series : [
            {
                name:'组织',
                barWidth : 22,//柱图宽度
                slient:true,
                barGap:'-100%',
                label: {
                    normal: {
                        show: true,
                        position: 'top',
                        textStyle:{
                            color:'#CCC',
                            fontSize: 12
                        },
                    }
                },
                type:'bar',
                data: orgValData,          //[234, 456, 345, 228, 156, 181, 229, 314, 276, 137, 256, 125],
                itemStyle:{
                    normal:{
                        color:'#61c7ff',
                    }
                },
            }
        ]
    };
    myChart.setOption(option); 
    parent.loadingHome.screenMaskDisable('LAY_app_body');
}

//专业系统下发数
// function setSysChart(){
//     var myChart = echarts.init(document.getElementById('IncomeFrom')); 

//     option = {
//         tooltip : {
//             trigger: 'item',
//             formatter: "{b}: <br/>{c} ({d}%)",
//                 textStyle: {
//                     color: '#CCC',
//                     fontSize: 18
//                 }
//         },
//         legend: {
//             orient: 'vertical',
//             x: 'left',
//             data:['待办','在途','待审批','待推进']
//             },
//         series: [
//             {
//                 name:'收入构成',
//                 type:'pie',
//                 radius: ['45%', '70%'],
//                 avoidLabelOverlap: false,
//                 label: {
//                     normal: {
//                         show: false,
//                         position: 'center'
//                     },
//                     emphasis: {
//                         show: true,
//                         textStyle: {
//                             fontSize: '22',
//                         }
//                     }
//                 },
//                 labelLine: {
//                     normal: {
//                         show: false
//                     }
//                 },
//                 data:[
//                     {value:178, name:'待办',
//                     itemStyle:{
//                             normal:{color:'#a0d98c',borderWidth: 2,borderColor: '#FFF'}
//                         }
//                     },
//                     {value:256, name:'在途',
//                     itemStyle:{
//                             normal:{color:'#61c7ff',borderWidth: 2,borderColor: '#FFF'}
//                         }
//                     },
//                     {value:211, name:'待审批',
//                     itemStyle:{
//                             normal:{color:'#ffa9ad',borderWidth: 2,borderColor: '#FFF'}
//                         }
//                     },
//                     {value:95, name:'待推进',
//                     itemStyle:{
//                             normal:{color:'#ffc870',borderWidth: 2,borderColor: '#FFF'}
//                         }
//                     }
//                 ]
//             }
//         ]
//     };
//         myChart.setOption(option);
//         var currentIndex = -1;

//         setInterval(function () {
//             var dataLen = option.series[0].data.length;
//         // 取消之前高亮的图形
//         myChart.dispatchAction({
//             type: 'downplay',
//             seriesIndex: 0,
//             dataIndex: currentIndex
//         });
//         currentIndex = (currentIndex + 1) % dataLen;
//         // 高亮当前图形
//         myChart.dispatchAction({
//             type: 'highlight',
//             seriesIndex: 0,
//             dataIndex: currentIndex
//         });
//         // 显示 tooltip
//         myChart.dispatchAction({
//             type: 'showTip',
//             seriesIndex: 0,
//             dataIndex: currentIndex
//         });
//     }, 3000);
//     parent.loadingHome.screenMaskDisable('LAY_app_body');
// }

//设置系统概览面板
function setSysInfo(){
    $("#orgVal").text(orgVal);
    $("#psnVal").text(psnVal);
    $("#acctVal").text(acctVal);
    $("#slaveAcctVal").text(slaveAcctVal);
}

//数值超过3位加间隔
function formatVal(b){
    var len=b.length;
    if(len<=3){return b;}
    var r=len%3;
    return r>0?b.slice(0,r)+","+b.slice(r,len).match(/\d{3}/g).join(","):b.slice(r,len).match(/\d{3}/g).join(",");
}

getHomeStatistics();
