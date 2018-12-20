

function getPsnTl(){
    $http.get('/homeStatistics/getHomeStatistics?labelType=ORG_PSN', {}, 
        function (data) {
          //新增
            console.log("OK");
      }, function (err) {
    
      })
}


//各地市人员
function setPsnChart(){
    var myChart = echarts.init(document.getElementById('Personnel')); 
    option = {
        grid: {
            left: 100,
            right: 60,
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
            data : ['省公司','杭州','宁波','温州','绍兴','湖州','嘉兴','金华','衢州','台州','丽水','舟山']
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
                        show: true,
                        position: 'top',
                        textStyle:{
                            color:'#CCC',
                            fontSize: 18
                        },
                    }
                },
                type:'bar',
                data:[234, 456, 345, 228, 156, 181, 229, 314, 276, 137, 256, 125],
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
            left: 100,
            right: 60,
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
                data : ['省公司','杭州','宁波','温州','绍兴','湖州','嘉兴','金华','衢州','台州','丽水','舟山']
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
                            fontSize: 18
                        },
                    }
                },
                type:'bar',
                data:[234, 456, 345, 228, 156, 181, 229, 314, 276, 137, 256, 125],
                itemStyle:{
                    normal:{
                        color:'#61c7ff',
                    }
                },
            }
        ]
    };
    myChart.setOption(option); 
}

//专业系统下发数
function setSysChart(){
    var myChart = echarts.init(document.getElementById('IncomeFrom')); 

    option = {
        tooltip : {
            trigger: 'item',
            formatter: "{b}: <br/>{c} ({d}%)",
                textStyle: {
                    color: '#CCC',
                    fontSize: 18
                }
        },
        legend: {
            orient: 'vertical',
            x: 'left',
            data:['待办','在途','待审批','待推进']
            },
        series: [
            {
                name:'收入构成',
                type:'pie',
                radius: ['45%', '70%'],
                avoidLabelOverlap: false,
                label: {
                    normal: {
                        show: false,
                        position: 'center'
                    },
                    emphasis: {
                        show: true,
                        textStyle: {
                            fontSize: '22',
                        }
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                data:[
                    {value:178, name:'待办',
                    itemStyle:{
                            normal:{color:'#a0d98c',borderWidth: 2,borderColor: '#FFF'}
                        }
                    },
                    {value:256, name:'在途',
                    itemStyle:{
                            normal:{color:'#61c7ff',borderWidth: 2,borderColor: '#FFF'}
                        }
                    },
                    {value:211, name:'待审批',
                    itemStyle:{
                            normal:{color:'#ffa9ad',borderWidth: 2,borderColor: '#FFF'}
                        }
                    },
                    {value:95, name:'待推进',
                    itemStyle:{
                            normal:{color:'#ffc870',borderWidth: 2,borderColor: '#FFF'}
                        }
                    },
                ]
            }
        ]
    };
        myChart.setOption(option); 
        var currentIndex = -1;

        setInterval(function () {
            var dataLen = option.series[0].data.length;
        // 取消之前高亮的图形
        myChart.dispatchAction({
            type: 'downplay',
            seriesIndex: 0,
            dataIndex: currentIndex
        });
        currentIndex = (currentIndex + 1) % dataLen;
        // 高亮当前图形
        myChart.dispatchAction({
            type: 'highlight',
            seriesIndex: 0,
            dataIndex: currentIndex
        });
        // 显示 tooltip
        myChart.dispatchAction({
            type: 'showTip',
            seriesIndex: 0,
            dataIndex: currentIndex
        });
    }, 3000);
}

getPsnTl();
setPsnChart();
setOrgChart();
setSysChart();