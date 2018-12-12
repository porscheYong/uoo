  // Tooltip
  $(document).ready(function() {
      $('[data-toggle="tooltip"]').tooltip({
          container: 'body'
      });
  });


  // NProgress
  if (typeof NProgress != 'undefined') {
      $(document).ready(function () {
          NProgress.start();
      });

      $(window).load(function () {
          NProgress.done();
      });
  }


  function gd(year, month, day) {
    return new Date(year, month - 1, day).getTime();
  }
     
  /* PNotify */
  function init_PNotify() {
    
    if( typeof (PNotify) === 'undefined'){ return; }
  }; 
     
    
  /* DATA TABLES */ 
  function init_DataTables() {
    
    if( typeof ($.fn.DataTable) === 'undefined'){ return; }
    
    var handleDataTableButtons = function() {
      if ($("#datatable-buttons").length) {
      $("#datatable-buttons").DataTable({
        dom: "Blfrtip",
        buttons: [
        {
          extend: "copy",
          className: "btn-sm"
        },
        {
          extend: "csv",
          className: "btn-sm"
        },
        {
          extend: "excel",
          className: "btn-sm"
        },
        {
          extend: "pdfHtml5",
          className: "btn-sm"
        },
        {
          extend: "print",
          className: "btn-sm"
        },
        ],
        responsive: true
      });
      }
    };

    TableManageButtons = function() {
      "use strict";
      return {
      init: function() {
        handleDataTableButtons();
      }
      };
    }();

    $('#datatable').dataTable();

    $('#datatable-keytable').DataTable({
      keys: true
    });

    $('#datatable-responsive').DataTable();

    $('#datatable-scroller').DataTable({
      ajax: "js/datatables/json/scroller-demo.json",
      deferRender: true,
      scrollY: 380,
      scrollCollapse: true,
      scroller: true
    });

    $('#datatable-fixed-header').DataTable({
      fixedHeader: true
    });

    var $datatable = $('#datatable-checkbox');

    $datatable.dataTable({
      'order': [[ 1, 'asc' ]],
      'columnDefs': [
      { orderable: false, targets: [0] }
      ]
    });
    $datatable.on('draw.dt', function() {
      $('checkbox input').iCheck({
      checkboxClass: 'icheckbox_flat-green'
      });
    });

    TableManageButtons.init();
    
  };

  /* ECHRTS */
  function init_echarts() {
    
      if( typeof (echarts) === 'undefined'){ return; }  
      
      var theme = {
        title: {
          left: 'center',
          textStyle: {
            fontWeight: 'normal',
            color: '#408829'
          }
        },

        tooltip: {
          backgroundColor: 'rgba(0,0,0,0.5)',
          axisPointer: {
            lineStyle: {
              color: '#408829',
              type: 'dashed'
            },
            crossStyle: {
              color: '#408829'
            },
            shadowStyle: {
              color: 'rgba(200,200,200,0.3)'
            }
          }
        },

        grid: {
          borderWidth: 0
        },

        categoryAxis: {
          axisLine: {
            lineStyle: {
              color: '#408829'
            }
          },
          splitLine: {
            lineStyle: {
              color: ['#eee']
            }
          }
        },

        valueAxis: {
          axisLine: {
            lineStyle: {
              color: '#408829'
            }
          },
          splitArea: {
            show: true,
            areaStyle: {
              color: ['rgba(250,250,250,0.1)', 'rgba(200,200,200,0.1)']
            }
          },
          splitLine: {
            lineStyle: {
              color: ['#eee']
            }
          }
        }
      };

        //echart Bar
        
      if ($('#mainb').length ){
        
          var echartBar = echarts.init(document.getElementById('mainb'), theme);

          echartBar.setOption({
          title: {
            text: '流量使用(M)'
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'shadow'
            }
          },
          // legend: {
          //   data: ['sales', 'purchases']
          // },
          toolbox: {
            show: false
          },
          calculable: false,
          xAxis: [{
            splitLine:{show: false},
            type: 'category',
            data: ['3月', '4月', '5月', '6月', '7月', '8月']
          }],
          yAxis: [{
            type: 'value',
            axisLine: {
              show: false
            },
            axisTick: {
              show: false
            }
          }],
          series: [{
            name: '总流量',
            type: 'bar',
            data: [4.0, 4.9, 7.0, 23.2, 25.6, 76.7],
            itemStyle: {
              normal: {
                color: function (params) {
                  var colorList = ['#E89589', '#C33531', '#EE9201', '#91c7ae', '#0AAF9F', '#3498DB'];
                  return colorList[params.dataIndex]
                },
                label: {
                  show: true,
                  position: 'top',
                  formatter: '{c0}'
                }
              }
            }
          }]
          });

      }

      if ($('#costb').length ){
        
          var echartBar = echarts.init(document.getElementById('costb'), theme);

          echartBar.setOption({
          title: {
            text: '流量使用TOP5排行'
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'shadow'
            }
          },
          // legend: {
          //   data: ['sales', 'purchases']
          // },
          toolbox: {
            show: false
          },
          calculable: false,
          yAxis: [{
            // splitLine:{show: false},
            axisLine: {
              show: false
            },
            axisTick: {
              show: false
            },
            type: 'category',
            data: ['小程序', '小红帽', '微信', '斗鱼']
          }],
          xAxis: [{
            type: 'value',
            splitLine:{show: false},
            splitArea:{show: false},
            axisLine: {
              show: false
            },
            axisTick: {
              show: false
            }
          }],
          series: [{
            name: '总流量',
            type: 'bar',
            data: [2.0, 4.9, 7.0, 23.2],
            itemStyle: {
              normal: {
                color: function (params) {
                  var colorList = ['#E89589', '#C33531', '#EE9201', '#91c7ae', '#0AAF9F', '#3498DB'];
                  return colorList[params.dataIndex]
                },
                label: {
                  show: true,
                  position: 'right',
                  formatter: '{c0}'
                }
              }
            }
          }]
          });

      }
     
    }
     
  /* bootstrap-table */

  function init_table () {
    $('#codeTable').bootstrapTable({
        url: './errCode.json',
        method: 'get',
        dataType: 'json',
        cache: false,
        striped: true,                      //是否显示行间隔色
        pagination: true,                   //是否显示分页（*）
        sidePagination: 'server',           //分页方式：client客户端分页，server服务端分页（*）
        pageSize: 10,                       //每页的记录行数（*）
        pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
        paginationLoop: false,
        responseHandler: function (res) {
          return {
            "total": res.total,//总页数
            "rows": res.data   //数据
          }
        },
        //列信息和列设置
        columns: [{
            field: 'frontEndCode',
            title: '前台错误码',
        }, {
            field: 'backEndCode',
            title: '后台错误码'
        }, {
            field: 'errorMsg',
            title: '用户提示语'
        }, {
            field: 'description',
            title: '错误描述'
        }, {
            field: 'suggestion',
            title: '处理建议'
        }],
        onLoadSuccess: function () {
          
        },
        onLoadError: function () {
          
        }
    });

    $('#dataIssuedTable').bootstrapTable({
        method: 'get',
        dataType: 'json',
        cache: false,
        striped: true,                      //是否显示行间隔色
        pagination: true,                   //是否显示分页（*）
        sidePagination: 'server',           //分页方式：client客户端分页，server服务端分页（*）
        pageSize: 10,                       //每页的记录行数（*）
        pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
        paginationLoop: false,
        responseHandler: function (res) {
          return {
            "total": res.total,//总页数
            "rows": res.data   //数据
          }
        },
        //列信息和列设置
        columns: [{
            field: 'frontEndCode',
            title: '流水号',
        }, {
            field: 'backEndCode',
            title: 'ITV账号'
        }, {
            field: 'errorMsg',
            title: '用户提示语'
        }, {
            field: 'description',
            title: '操作工号'
        }, {
            field: 'suggestion',
            title: '操作时间'
        }, {
            field: 'suggestion',
            title: '操作结果'
        }, {
            field: 'suggestion',
            title: '后台反馈时间'
        }, {
            field: 'suggestion',
            title: '后台反馈结果'
        }],
        formatNoMatches: function () {  //没有匹配的结果
          return '对不起，没有查找到符合的记录';
        },
        onLoadSuccess: function () {
          
        },
        onLoadError: function () {
          
        }
    });

    $('#restart').bootstrapTable({
        method: 'get',
        dataType: 'json',
        cache: false,
        striped: true,                      //是否显示行间隔色
        pagination: true,                   //是否显示分页（*）
        sidePagination: 'server',           //分页方式：client客户端分页，server服务端分页（*）
        pageSize: 10,                       //每页的记录行数（*）
        pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
        paginationLoop: false,
        responseHandler: function (res) {
          return {
            "total": res.total,//总页数
            "rows": res.data   //数据
          }
        },
        //列信息和列设置
        columns: [{
            field: 'frontEndCode',
            title: '流水号',
        }, {
            field: 'backEndCode',
            title: 'ITV账号'
        }, {
            field: 'errorMsg',
            title: '下发工号'
        }, {
            field: 'description',
            title: '下发时间'
        }, {
            field: 'suggestion',
            title: '下发结果'
        }, {
            field: 'suggestion',
            title: '数据下发反馈时间'
        }, {
            field: 'suggestion',
            title: '数据下发结果'
        }],
        formatNoMatches: function () {  //没有匹配的结果
          return '对不起，没有查找到符合的记录';
        },
        onLoadSuccess: function () {
          
        },
        onLoadError: function () {
          
        }
    });
  }
     
  $(document).ready(function() {
    init_echarts();
    init_DataTables();
    init_PNotify();
    init_table();
  }); 
  

  var config = {
    'base': './build/lulu/peak/js'
  };

  seajs.config(config).use(['common/ui/Select'], function(Select) {
      $('select').each(function () {
          new Select($(this));
      });
  });
