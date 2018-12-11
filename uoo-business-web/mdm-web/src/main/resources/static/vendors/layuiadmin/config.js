/**

 @Name：layuiAdmin iframe版全局配置
 @Author：贤心
 @Site：http://www.layui.com/admin/
 @License：LPPL（layui付费产品协议）

 */

layui.define(['laytpl', 'layer', 'element', 'util'], function(exports){
  exports('setter', {
    container: 'LAY_app' //容器ID
    ,base: layui.cache.base //记录静态资源所在路径
    ,views: layui.cache.base //动态模板所在目录
    ,entry: 'index' //默认视图文件名
    ,engine: '.html' //视图文件后缀名
    ,pageTabs: true //是否开启页面选项卡功能。iframe版推荐开启

    ,name: 'layuiAdmin'
    ,tableName: 'layuiAdmin' //本地存储表名
    ,MOD_NAME: 'admin' //模块事件名

    ,debug: true //是否开启调试模式。如开启，接口异常时会抛出异常 URL 等信息

    //自定义请求字段
    ,request: {
      tokenName: false //自动携带 token 的字段名（如：access_token）。可设置 false 不携带。
    }

    //自定义响应字段
    ,response: {
      statusName: 'code' //数据状态的字段名称
      ,statusCode: {
        ok: 0 //数据状态一切正常的状态码
        ,logout: 1001 //登录状态失效的状态码
      }
      ,msgName: 'msg' //状态信息的字段名称
      ,dataName: 'data' //数据详情的字段名称
    }

    //扩展的第三方模块
    ,extend: [
      'echarts', //echarts 核心包
      'echartsTheme' //echarts 主题
    ]

    //主题配置
    ,theme: {
        //配色方案，如果用户未设置主题，第一个将作为默认
        color: [{
            main: '#1b212f', //主题色
            logo: '#00a8ff',
            selected: '#ff7800', //选中色
            alias: 'default' //默认别名
        },{
            main: '#03152A',
            logo: '#6b69d6',
            selected: '#8e8cd8',
            alias: 'Violet' //紫罗兰
        },{
            main: '#2E241B',
            selected: '#A48566',
            alias: 'coffee' //咖啡
        },{
            main: '#50314F',
            selected: '#7A4D7B',
            alias: 'purple-red' //紫红
        },{
            main: '#344058',
            logo: '#1E9FFF',
            selected: '#1E9FFF',
            alias: 'ocean' //海洋
        },{
            main: '#3A3D49',
            logo: '#2F9688',
            selected: '#5FB878',
            alias: 'green' //墨绿
        },{
            main: '#20222A',
            logo: '#F78400',
            selected: '#F78400',
            alias: 'red' //橙色
        },{
            main: '#28333E',
            logo: '#dd0e61',
            selected: '#c82a69',
            alias: 'fashion-red' //时尚红
        },{
            main: '#24262F',
            logo: '#3A3D49',
            selected: '#009688',
            alias: 'classic-black' //经典黑
        },{
            main: '#001529',
            logo: '#002140',
            selected: '#1890ff',
            alias: 'ant-black' //ant pro
        }]

      //初始的颜色索引，对应上面的配色方案数组索引
      //如果本地已经有主题色记录，则以本地记录为优先，除非请求本地数据（localStorage）
      ,initColorIndex: 9
    }
  });
});
