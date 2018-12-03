# 用户组织运营平台 UOO
## 项目简介
uoo是采用Spring Cloud微服务化开发平台，具有统一授权、认证后台管理系统，也可以作为后端服务的开发脚手架。代码要求简洁、架构清晰，适合对微服务学习和直接项目中使用。核心技术采用Spring Boot 1.5.15.RELEASE以及Spring Cloud (1.4.5.RELEASE)相关核心组件，当下前端采用Thymeleaf的HTML来开发的，后续准备采用VUE进行二次改造。

## 架构概述

### 服务注册发现
基于Eureka来实现的服务注册与调用，在Spring Cloud中使用Feign, 我们可以做到使用HTTP请求远程服务时能与调用本地方法一样的编码体验，开发者完全感知不到这是远程方法，更感知不到这是个HTTP请求。

### 服务鉴权
通过JWT的方式来加强服务之间调度的权限验证，保证内部服务的安全性

### 监控
利用Zipkin对服务链路的监控和分析

### 熔断机制
因为采取了服务的分布，为了避免服务之间的调用“雪崩”，采用了Hystrix的作为熔断器，避免了服务之间的“雪崩”。

### 负载均衡
将服务保留的Rest API进行代理和网关控制，除了平常经常使用的node.js、nginx外，Spring Cloud系列的zuul和ribbon，可以帮我们进行正常的网关管控和负载均衡。
其中扩展基于JWT的Zuul限流插件，方面进行限流。

## 功能
### uoo-base 基类
- base-common 基类通用，可对工具类和对底层访问的Dao进行封装，建议提供基于不同数据库和不同持久化工具进行分装，供各个模块自行引用
- uoo-register Eureka注册中心,建议最少三个节点部署，分三台机器
- uoo-gateway 统一配置网关服务，提供对外路由的配置
- uoo-sleuth 基于Zipkin来对服务链路追踪分析,可以可视化地分析服务调用链路和服务间的依赖关系
- uoo-monitor 微服务监控中心
### uoo-common 平台通用
- common-system 提供基于平台的用户、部门、角色、字典的管理
- activiti-core 工作流的基本服务,目前上面仅有内蒙项目组的代码
- common-cache 提供统一的基于缓存中间的服务，这块规划采用redis集群构建缓存中间服务
- queues-manager 基于RabbitMQ对外统一提供队列的基本管理服务功能
- common-im 提供基于短信、邮件、代办的集中服务功能模块，方便后期版本升级迭代

### uoo-business 业务领域
- business-syn-msg 对外数据共享同步的服务模块
- business-organization 组织机构相关业务服务，包括组织分类、组织关系、组织树等
- business-personnel 人员信息相关业务服务，包括证件、教育经理、履历等
- business-user 用户相关业务，包括账号、从账号、账号与组织归属关系
- business-permission 权限业务领域模块，包括角色、资源等
- business-position 岗位职位业务领域，当前岗位、职位的定义还较为欠缺，需要补充
- business-region 区域管理业务领域
- business-authentication 认证鉴权所用的业务，可对认证管理WEB提供服务

## 开发必读
- 环境准备： JDK1.8、Maven3+、IDEA
- 内存推荐：4G+
- 下载源码：[git@wongs.xyz:uoo.git](git@wongs.xyz:uoo.git)

## 技术选型
- 核心框架

| 序号 | 名称 | 版本 |
| ------ | ------ | ------ |
| 1 | Spring Boot | 1.5.15.RELEASE |
| 2 | Spring Cloud | 1.4.5.RELEASE |

- 持久层框架

| 序号 | 名称 | 版本 |
| ------ | ------ | ------ |
| 1 | MyBatis | 3.4.6 |
| 2 | JPA | 1.11.14.RELEASE |
| 3 | Hibernate Validation | 5.4.2.Final |
| 4 | Alibaba Druid | 1.1.19 |

- 视图层

| 序号 | 名称 | 版本 |
| ------ | ------ | ------ |
| 1 | Spring MVC | 4.3.18 |
| 2 | Thymeleaf | 3.0.9.RELEASE |
| 3 | CSS框架-Bootstarp | 3.3 |
| 4 | JS框架及组件-jQuery | 1.12.4 |
| 5 | JS框架及组件-layer | 3.1.2 |
| 6 | JS框架及组件-layui admin | 1.1.0 |
| 7 | JS框架及组件-zTree | 3.5.37 |


## 技术原理概述
### SpringCloud调用机制

![avatar](https://github.com/rothschil/static/raw/master/1-springcloud.jpg)




## 微服务模块化结构

~~~
|——uoo----------------------------------------------------父项目，公共依赖
|   |
|   |——uoo-base-------------------------------------------基础模块
|   |   |
|   |   |——base-common------------------------------------基础部分，可被复用
|   |   |   |
|   |   |   |——common-tool--------------------------------工具类，定义时间、文件、日期、字符串的操作脚手架
|   |   |   |
|   |   |   |——common-abstract----------------------------基于mybatis的持久层、拦截器、配置基础包，可被模块引用，减少开发工作量
|   |   |   |
|   |   |   |——common-persitence--------------------------持久层
|   |   |   |   |
|   |   |   |   |——persistence-jpa------------------------JPA持久层通用类
|   |   |
|   |   |——uoo-register-----------------------------------微服务注册中心
|   |   |
|   |   |——uoo-gateway------------------------------------微服务网关中心
|   |   |
|   |   |——uoo-sleuth-------------------------------------微服务链路追踪中心
|   |   |
|   |   |——uoo-monitor------------------------------------微服务监控中心
|   |
|   |——uoo-common-----------------------------------------公用模块，微服务本身的管理公共模块，可以被任何其他想尝试微服务的项目重用
|   |   |
|   |   |——common-auth------------------------------------公用登录认证服务中心<取消该模块与common-system合并>
|   |   |
|   |   |——common-system----------------------------------系统公共部分包含用户、部门、菜单、角色、区域、字典
|   |   |
|   |   |——common-activiti--------------------------------公共工作流服务中心
|   |   |   |
|   |   |   |——activiti-core------------------------------工作流模块，目前，内蒙项目所用
|   |   |
|   |   |——common-cache-----------------------------------公共缓存服务中心
|   |   |
|   |   |——common-queues----------------------------------公共队列的基本管理服务中心
|   |   |   |
|   |   |   |——queues-manager-----------------------------提供基于rabbitMQ的队列管理能力
|   |   |
|   |   |——common-im--------------------------------------短信、邮件服务中心
|   |
|   |——uoo-business---------------------------------------业务模块
|   |   |
|   |   |——business-syn-msg-------------------------------数据共享-数据封装同步组件
|   |   |
|   |   |——business-organization--------------------------组织域服务中心
|   |   |
|   |   |——business-personnel-----------------------------人员域服务中心
|   |   |
|   |   |——business-user----------------------------------用户域服务中心
|   |   |
|   |   |——business-permission----------------------------权限域服务中心
|   |   |
|   |   |——business-position------------------------------职位域服务中心
|   |   |
|   |   |——business-region--------------------------------区域服务中心
|   |   |
|   |   |——business-public--------------------------------公共服务中心，目前被uoo用做系统管理
|   |   |
|   |   |——business-authentication------------------------用户认证管理服务中心
|   |
|   |——uoo-business-api-----------------------------------可对外开放，被第三方使用的服务API，需要考虑数据安全
|   |   |
|   |   |——api-user---------------------------------------用户对外API
|   |   |
|   |   |——api-org----------------------------------------组织域服务API
|   |   |
|   |   |——api-security-----------------------------------业务安全域服务API
|   |   |   |
|   |   |   |——security-authorize-------------------------授权管理服务API
|   |   |   |
|   |   |   |——security-authentication--------------------用户身份认证服务API
|   |   |   |   |
|   |   |   |   |——authentication-cs----------------------用户身份认证服务API-CS方式
|   |   |   |   |
|   |   |   |   |——authentication-bs----------------------用户身份认证服务API-BS方式
|   |
|   |——uoo-business-web-----------------------------------WEB端
|   |   |
|   |   |——mdm-web----------------------------------------主数据中心维护支撑WEB
|   |   |
|   |   |——auth-web---------------------------------------认证中心维护支撑WEB

~~~~

## 服务中心部署

### 测试环境

### 地址
测试环境资源有限情况下，每个服务可在每台机器部署一个节点

![avatar](https://github.com/rothschil/static/raw/master/tuop.png)

| 序号 | 地址 | 描述 |
| ------ | ------ | ------ |
| 1 | 134.96.253.221 | 无 |
| 2 | 134.96.253.222 | 无 |
| 3 | 134.96.253.223 | 无 |

#### uoo-base基础模块

| 序号 | 名称 | 描述 | 阶段I部署端口 | 阶段II部署端口 |
| ------ | ------ | ------ | ------ | ------ 
| 86 | uoo-sleuth | 服务追踪 | 8600 | 8611、8622 |
| 87 | uoo-register | 注册中心 | 8751 | 8761、8771 |
| 88 | uoo-gateway | 通用网关 | 8800 | 8811、8822 |
| 89 | uoo-monitor | 微服务监控 | 8900 | 8911、8922 |

#### uoo-common公用模块

| 序号 | 名称 | 描述 | 阶段I部署端口 | 阶段II部署端口 |
| ------ | ------ | ------ | ------ | ------ |
| 90 | common-auth | 公用登录认证鉴权 | 9000 | 9011、9022 |
| 91 | common-system | 公用系统公共部分包含用户、部门、菜单等 | 9100 | 9111、9122 |
| 92 | activiti-core | 公共工作流服务中心 | 9200 | 9211、9222 |
| 93 | common-cache | 公共缓存服务中心 | 9300 | 9311、9322 |
| 94 | queues-manager | 公共队列的基本管理服务中心 | 9400 |9411、9422 |
| 95 | common-im | 短信、邮件服务中心基本管理服务中心 | 9500 | 9511、9522 |

#### uoo-business业务模块

| 序号 | 名称 | 描述 | 阶段I部署端口 | 阶段II部署端口 |
| ------ | ------ | ------ | ------ | ------ |
| 110 | business-syn-msg | 数据共享-数据封装组件 | 11000 | 11022、11033 |
| 111 | business-organization |组织域服务中心 | 11100 | 11111、11122 |
| 112 | business-personnel | 人员域服务中心 | 11200 | 11211、11222 |
| 113 | business-user | 用户业务服务中心 | 11300 | 11311、11322 |
| 114 | business-permission | 权限域服务中心 | 11400 | 11411、11422 |
| 115 | business-public | 公共域服务中心 | 11500 | 11511、11522 |
| 116 | business-position | 职位域服务中心 | 11600 | 11611、11622 |
| 117 | business-region | 区域模块 | 11700 | 11711、11722 |
| 118 | business-authentication | 认证管理 | 11800 | 11811、11822 |

#### uoo-business-web业务模块

| 序号 | 名称 | 描述 | 阶段I部署端口 | 阶段II部署端口 |
| ------ | ------ | ------ | ------ | ------ |
| 180 | mdm-web | 用户组织运营平台WEB | 18000 | 18022、18033 |
| 181 | auth-web | 认证管理平台WEB | 18100 | 18111、18122 |

#### uoo-business-api业务模块

| 序号 | 名称 | 描述 |  阶段I部署端口 | 阶段II部署端口 |
| ------ | ------ | ------ | ------ | ------ |
| 200 | api-user | 组织域服务API | 20000 | 20022、20033 |
| 201 | api-org | 人员域服务API | 20100 | 20111、20122 |
| 202 | security-authorize | 授权管理服务API | 20200 | 20211、20222 |
| 203 | authentication-bs | 用户身份认证服务API-BS方式 | 20300 | 20311、20322 |
| 204 | authentication-cs | 用户身份认证服务API-CS方式 | 20400 | 20411、20422 |

### 正式生产环境
#### uoo-base基础模块

| 序号 | 名称 | 描述 | 阶段I部署端口 | 阶段II部署端口 |
| ------ | ------ | ------ | ------ | ------ |
| 86 | uoo-sleuth | 服务追踪 | 8600、8611 | 8622、8633、8644 |
| 87 | uoo-register | 注册中心 | 8751、8761 | 8771，8781、8791 |
| 88 | uoo-gateway | 通用网关 | 8800、8811 | 8822、8833、8444 |
| 89 | uoo-monitor | 微服务监控 | 8900、8911 | 8922、8933、8944 |

#### uoo-common公用模块

| 序号 | 名称 | 描述 | 阶段I部署端口 | 阶段II部署端口 |
| ------ | ------ | ------ | ------ | ------ |
| 90 | common-auth | 公用登录认证鉴权 | 9000、9011 | 9022、9033、9044 |
| 91 | common-system | 公用系统公共部分包含用户、部门、菜单等 | 9100、9111 | 9122、9133、9144 |
| 92 | activiti-core | 公共工作流服务中心 | 9200、9211 | 9222、9233、9244 |
| 93 | common-cache | 公共缓存服务中心 | 9300、9311 | 9322、9333、9344 |
| 94 | queues-manager | 公共队列的基本管理服务中心 | 9400、9411 | 9422、9433、9444 |
| 95 | common-im | 短信、邮件服务中心基本管理服务中心 | 9500、9511 | 9522、9533、9544 |

#### uoo-business业务模块

| 序号 | 名称 | 描述 | 阶段I部署端口 | 阶段II部署端口 |
| ------ | ------ | ------ | ------ | ------ |
| 110 | business-syn-msg | 数据共享-数据封装组件 | 11000、11022 | 11033、11044、11055 |
| 111 | business-organization |组织域服务中心 | 11100、11111 | 11122、11133、11144 |
| 112 | business-personnel | 人员域服务中心 | 11200、11211 | 11222、11233、11244 |
| 113 | business-user | 用户业务服务中心 | 11300、11311 | 11322、11333、11344 |
| 114 | business-permission | 权限域服务中心 | 11400、11411 | 11422、11433、11444 |
| 115 | business-public | 公共域服务中心 | 11500、11511 | 11522、11533、11544 |
| 116 | business-position | 职位域服务中心 | 11600 | 11611、11622、11633、11644 |
| 117 | business-region | 区域模块 | 11700 | 11711、11722、11733、11744 |
| 118 | business-authentication | 认证管理 | 11800 | 11811、11822、11833、11844 |

#### uoo-business-web业务模块

| 序号 | 名称 | 描述 | 阶段I部署端口 | 阶段II部署端口 |
| ------ | ------ | ------ | ------ | ------ |
| 180 | mdm-web | 用户组织运营平台WEB | 18000 | 18011、18022、18033、18044 |
| 181 | auth-web | 认证管理平台WEB | 18100 | 18111、18122、18133、18144 |

#### uoo-business-api业务模块

| 序号 | 名称 | 描述 | 阶段I部署端口 | 阶段II部署端口 |
| ------ | ------ | ------ | ------ | ------ |
| 200 | api-user | 组织域服务API | 20000、20022 | 20033、20033、20044  |
| 201 | api-org | 人员域服务API | 20100、20111 | 20122、20133、20144 |
| 202 | security-authorize | 授权管理服务API | 20200、20211 | 20222、20233 、20244  |
| 203 | authentication-bs | 用户身份管理服务API-BS方式 | 20300、20311 | 20322、20333、20344 |
| 204 | authentication-cs | 用户身份管理服务API-CS方式 | 20400、20411 | 20422、20433、20444 |

### 详细部署清单

<table border=0 cellpadding=0 cellspacing=0 width=1611 style='border-collapse:
 collapse;table-layout:fixed;width:1209pt'>
 <col width=72 style='width:54pt'>
 <col width=172 style='mso-width-source:userset;mso-width-alt:5504;width:129pt'>
 <col width=237 style='mso-width-source:userset;mso-width-alt:7584;width:178pt'>
 <col width=321 style='mso-width-source:userset;mso-width-alt:10272;width:241pt'>
 <col class=xl70 width=270 style='mso-width-source:userset;mso-width-alt:8640;
 width:203pt'>
 <col width=140 span=2 style='mso-width-source:userset;mso-width-alt:4480;
 width:105pt'>
 <col width=259 style='mso-width-source:userset;mso-width-alt:8288;width:194pt'>
 <tr height=28 style='height:21.0pt'>
  <td height=28 class=xl67 width=72 style='height:21.0pt;width:54pt'>序号</td>
  <td class=xl68 width=172 style='border-left:none;width:129pt'>IP</td>
  <td class=xl68 width=237 style='border-left:none;width:178pt'>应用名</td>
  <td class=xl68 width=321 style='border-left:none;width:241pt'>描述</td>
  <td class=xl71 width=270 style='border-left:none;width:203pt'>阶段I部署端口</td>
  <td class=xl68 width=140 style='border-left:none;width:105pt'>负责人</td>
  <td class=xl68 width=140 style='border-left:none;width:105pt'>状态</td>
  <td class=xl69 width=259 style='border-left:none;width:194pt'>阶段II部署端口&lt;后续拓展&gt;</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl65 align=right style='height:14.25pt;border-top:none'>1</td>
  <td rowspan=7 class=xl74 style='border-bottom:.5pt solid black;border-top:
  none'>134.96.217.231</td>
  <td class=xl65 style='border-top:none;border-left:none'>rabbitMQ</td>
  <td class=xl65 style='border-top:none;border-left:none'>消息中间件</td>
  <td class=xl66 style='border-top:none;border-left:none'>15672,25672,5672,4369</td>
  <td class=xl65 style='border-top:none;border-left:none'>吴豪</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl65 align=right style='height:14.25pt;border-top:none'>2</td>
  <td class=xl65 style='border-top:none;border-left:none'>uoo-register</td>
  <td class=xl65 style='border-top:none;border-left:none'>注册中心</td>
  <td class=xl66 style='border-top:none;border-left:none'>8600、8611</td>
  <td class=xl73 style='border-top:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl65 align=right style='height:14.25pt;border-top:none'>3</td>
  <td class=xl65 style='border-top:none;border-left:none'>common-system</td>
  <td class=xl65 style='border-top:none;border-left:none'>公用系统公共部分包含用户、部门、菜单等</td>
  <td class=xl66 style='border-top:none;border-left:none'>9100、9111</td>
  <td class=xl73 style='border-top:none'>刘晓东</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl65 align=right style='height:14.25pt;border-top:none'>4</td>
  <td class=xl65 style='border-top:none;border-left:none'>queues-manager</td>
  <td class=xl65 style='border-top:none;border-left:none'>公共队列的基本管理服务中心</td>
  <td class=xl66 style='border-top:none;border-left:none'>9400、9411</td>
  <td class=xl73 style='border-top:none'>吴豪</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl65 align=right style='height:14.25pt;border-top:none'>5</td>
  <td class=xl65 style='border-top:none;border-left:none'>business-syn-msg</td>
  <td class=xl65 style='border-top:none;border-left:none'>数据共享-数据封装组件</td>
  <td class=xl66 style='border-top:none;border-left:none'>11000、11022</td>
  <td class=xl73 style='border-top:none'>吴豪</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl65 align=right style='height:14.25pt;border-top:none'>6</td>
  <td class=xl65 style='border-top:none;border-left:none'>business-organization</td>
  <td class=xl65 style='border-top:none;border-left:none'>组织域服务中心</td>
  <td class=xl66 style='border-top:none;border-left:none'>11100、11111</td>
  <td class=xl73 style='border-top:none'>郭章斌</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl65 align=right style='height:14.25pt;border-top:none'>7</td>
  <td class=xl72 style='border-left:none'>mdm-web</td>
  <td class=xl72 style='border-left:none'>用户组织运营平台WEB</td>
  <td class=xl70>18000</td>
  <td></td>
  <td class=xl65 style='border-top:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl65 align=right style='height:14.25pt;border-top:none'>8</td>
  <td rowspan=7 class=xl74 style='border-bottom:.5pt solid black;border-top:
  none'>134.96.217.232</td>
  <td class=xl65 style='border-left:none'>rabbitMQ</td>
  <td class=xl65 style='border-left:none'>消息中间件</td>
  <td class=xl66 style='border-left:none'>15672,25672,5672,4369</td>
  <td class=xl73>吴豪</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl65 align=right style='height:14.25pt;border-top:none'>9</td>
  <td class=xl65 style='border-top:none;border-left:none'>uoo-register</td>
  <td class=xl65 style='border-top:none;border-left:none'>注册中心</td>
  <td class=xl66 style='border-top:none;border-left:none'>8600、8611</td>
  <td class=xl73 style='border-top:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl65 align=right style='height:14.25pt;border-top:none'>10</td>
  <td class=xl65 style='border-top:none;border-left:none'>common-system</td>
  <td class=xl65 style='border-top:none;border-left:none'>公用系统公共部分包含用户、部门、菜单等</td>
  <td class=xl66 style='border-top:none;border-left:none'>9100、9111</td>
  <td class=xl73 style='border-top:none'>刘晓东</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl65 align=right style='height:14.25pt;border-top:none'>11</td>
  <td class=xl65 style='border-top:none;border-left:none'>queues-manager</td>
  <td class=xl65 style='border-top:none;border-left:none'>公共队列的基本管理服务中心</td>
  <td class=xl66 style='border-top:none;border-left:none'>9400、9411</td>
  <td class=xl73 style='border-top:none'>吴豪</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl65 align=right style='height:14.25pt;border-top:none'>12</td>
  <td class=xl65 style='border-top:none;border-left:none'>business-syn-msg</td>
  <td class=xl65 style='border-top:none;border-left:none'>数据共享-数据封装组件</td>
  <td class=xl66 style='border-top:none;border-left:none'>11000、11022</td>
  <td class=xl73 style='border-top:none'>吴豪</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl65 align=right style='height:14.25pt;border-top:none'>13</td>
  <td class=xl65 style='border-top:none;border-left:none'>business-organization</td>
  <td class=xl65 style='border-top:none;border-left:none'>组织域服务中心</td>
  <td class=xl66 style='border-top:none;border-left:none'>11100、11111</td>
  <td class=xl73 style='border-top:none'>郭章斌</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl65 align=right style='height:14.25pt;border-top:none'>14</td>
  <td class=xl72 style='border-left:none'>mdm-web</td>
  <td class=xl72 style='border-left:none'>用户组织运营平台WEB</td>
  <td class=xl70>18000</td>
  <td></td>
  <td class=xl65 style='border-top:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl65 align=right style='height:14.25pt;border-top:none'>15</td>
  <td rowspan=7 class=xl74 style='border-bottom:.5pt solid black;border-top:
  none'>134.96.217.233</td>
  <td class=xl65 style='border-left:none'>rabbitMQ</td>
  <td class=xl65 style='border-left:none'>消息中间件</td>
  <td class=xl66 style='border-left:none'>15672,25672,5672,4369</td>
  <td class=xl73>吴豪</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl65 align=right style='height:14.25pt;border-top:none'>16</td>
  <td class=xl65 style='border-top:none;border-left:none'>uoo-register</td>
  <td class=xl65 style='border-top:none;border-left:none'>注册中心</td>
  <td class=xl66 style='border-top:none;border-left:none'>8600、8611</td>
  <td class=xl73 style='border-top:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl65 align=right style='height:14.25pt;border-top:none'>17</td>
  <td class=xl65 style='border-top:none;border-left:none'>common-system</td>
  <td class=xl65 style='border-top:none;border-left:none'>公用系统公共部分包含用户、部门、菜单等</td>
  <td class=xl66 style='border-top:none;border-left:none'>9100、9111</td>
  <td class=xl73 style='border-top:none'>刘晓东</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl65 align=right style='height:14.25pt;border-top:none'>18</td>
  <td class=xl65 style='border-top:none;border-left:none'>queues-manager</td>
  <td class=xl65 style='border-top:none;border-left:none'>公共队列的基本管理服务中心</td>
  <td class=xl66 style='border-top:none;border-left:none'>9400、9411</td>
  <td class=xl73 style='border-top:none'>吴豪</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl65 align=right style='height:14.25pt;border-top:none'>19</td>
  <td class=xl65 style='border-top:none;border-left:none'>business-syn-msg</td>
  <td class=xl65 style='border-top:none;border-left:none'>数据共享-数据封装组件</td>
  <td class=xl66 style='border-top:none;border-left:none'>11000、11022</td>
  <td class=xl73 style='border-top:none'>吴豪</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl65 align=right style='height:14.25pt;border-top:none'>20</td>
  <td class=xl65 style='border-top:none;border-left:none'>business-organization</td>
  <td class=xl65 style='border-top:none;border-left:none'>组织域服务中心</td>
  <td class=xl66 style='border-top:none;border-left:none'>11100、11111</td>
  <td class=xl73 style='border-top:none'>郭章斌</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl65 align=right style='height:14.25pt;border-top:none'>21</td>
  <td class=xl72 style='border-left:none'>mdm-web</td>
  <td class=xl72 style='border-left:none'>用户组织运营平台WEB</td>
  <td class=xl70>18000</td>
  <td></td>
  <td class=xl65 style='border-top:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl65 align=right style='height:14.25pt;border-top:none'>22</td>
  <td rowspan=5 class=xl74 style='border-bottom:.5pt solid black;border-top:
  none'>134.96.217.234</td>
  <td class=xl65 style='border-left:none'>rabbitMQ</td>
  <td class=xl65 style='border-left:none'>消息中间件</td>
  <td class=xl66 style='border-left:none'>15672,25672,5672,4369</td>
  <td class=xl73>吴豪</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl65 align=right style='height:14.25pt;border-top:none'>23</td>
  <td class=xl65 style='border-top:none;border-left:none'>business-organization</td>
  <td class=xl65 style='border-top:none;border-left:none'>组织域服务中心</td>
  <td class=xl66 style='border-top:none;border-left:none'>11100、11111</td>
  <td class=xl73 style='border-top:none'>郭章斌</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl65 align=right style='height:14.25pt;border-top:none'>24</td>
  <td class=xl65 style='border-top:none;border-left:none'>business-personnel</td>
  <td class=xl65 style='border-top:none;border-left:none'>人员域服务中心</td>
  <td class=xl66 style='border-top:none;border-left:none'>11200、11211</td>
  <td class=xl73 style='border-top:none'>吴德津</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl65 align=right style='height:14.25pt;border-top:none'>25</td>
  <td class=xl65 style='border-top:none;border-left:none'>business-user</td>
  <td class=xl65 style='border-top:none;border-left:none'>用户业务服务中心</td>
  <td class=xl66 style='border-top:none;border-left:none'>11300、11311</td>
  <td class=xl73 style='border-top:none'>吴德津</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl65 align=right style='height:14.25pt;border-top:none'>26</td>
  <td class=xl72 style='border-left:none'>mdm-web</td>
  <td class=xl72 style='border-left:none'>用户组织运营平台WEB</td>
  <td class=xl70>18000</td>
  <td></td>
  <td class=xl65 style='border-top:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl65 align=right style='height:14.25pt;border-top:none'>27</td>
  <td rowspan=4 class=xl74 style='border-bottom:.5pt solid black;border-top:
  none'>134.96.217.235</td>
  <td class=xl65 style='border-left:none'>rabbitMQ</td>
  <td class=xl65 style='border-left:none'>　</td>
  <td class=xl66 style='border-left:none'>15672,25672,5672,4369</td>
  <td class=xl65 style='border-left:none'>吴豪</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl65 align=right style='height:14.25pt;border-top:none'>28</td>
  <td class=xl65 style='border-top:none;border-left:none'>business-personnel</td>
  <td class=xl65 style='border-top:none;border-left:none'>人员域服务中心</td>
  <td class=xl66 style='border-top:none;border-left:none'>11200、11211</td>
  <td class=xl73 style='border-top:none'>吴德津</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl65 align=right style='height:14.25pt;border-top:none'>29</td>
  <td class=xl65 style='border-top:none;border-left:none'>business-user</td>
  <td class=xl65 style='border-top:none;border-left:none'>用户业务服务中心</td>
  <td class=xl66 style='border-top:none;border-left:none'>11300、11311</td>
  <td class=xl73 style='border-top:none'>吴德津</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl65 align=right style='height:14.25pt;border-top:none'>30</td>
  <td class=xl72 style='border-left:none'>mdm-web</td>
  <td class=xl72 style='border-left:none'>用户组织运营平台WEB</td>
  <td class=xl70>18000</td>
  <td></td>
  <td class=xl65 style='border-top:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl65 align=right style='height:14.25pt;border-top:none'>31</td>
  <td rowspan=3 class=xl74 style='border-bottom:.5pt solid black;border-top:
  none'>134.96.217.236</td>
  <td class=xl65 style='border-left:none'>rabbitMQ</td>
  <td class=xl65 style='border-left:none'>消息中间件</td>
  <td class=xl66 style='border-left:none'>15672,25672,5672,4369</td>
  <td class=xl65 style='border-left:none'>吴豪</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl65 align=right style='height:14.25pt;border-top:none'>32</td>
  <td class=xl65 style='border-top:none;border-left:none'>business-personnel</td>
  <td class=xl65 style='border-top:none;border-left:none'>人员域服务中心</td>
  <td class=xl66 style='border-top:none;border-left:none'>11200、11211</td>
  <td class=xl73 style='border-top:none'>吴德津</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl65 align=right style='height:14.25pt;border-top:none'>33</td>
  <td class=xl65 style='border-top:none;border-left:none'>business-user</td>
  <td class=xl65 style='border-top:none;border-left:none'>用户业务服务中心</td>
  <td class=xl66 style='border-top:none;border-left:none'>11300、11311</td>
  <td class=xl73 style='border-top:none'>吴德津</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl65 align=right style='height:14.25pt;border-top:none'>34</td>
  <td rowspan=2 class=xl74 style='border-bottom:.5pt solid black;border-top:
  none'>134.96.217.242</td>
  <td class=xl65 style='border-top:none;border-left:none'>business-personnel</td>
  <td class=xl65 style='border-top:none;border-left:none'>人员域服务中心</td>
  <td class=xl66 style='border-top:none;border-left:none'>11200、11211</td>
  <td class=xl73 style='border-top:none'>吴德津</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl65 align=right style='height:14.25pt;border-top:none'>35</td>
  <td class=xl65 style='border-top:none;border-left:none'>business-user</td>
  <td class=xl65 style='border-top:none;border-left:none'>用户业务服务中心</td>
  <td class=xl66 style='border-top:none;border-left:none'>11300、11311</td>
  <td class=xl73 style='border-top:none'>吴德津</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl65 align=right style='height:14.25pt;border-top:none'>36</td>
  <td rowspan=3 class=xl74 style='border-bottom:.5pt solid black;border-top:
  none'>134.96.217.243</td>
  <td class=xl65 style='border-top:none;border-left:none'>business-public</td>
  <td class=xl65 style='border-top:none;border-left:none'>公共域服务中心</td>
  <td class=xl66 style='border-top:none;border-left:none'>11500</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl65 align=right style='height:14.25pt;border-top:none'>37</td>
  <td class=xl65 style='border-top:none;border-left:none'>business-position</td>
  <td class=xl65 style='border-top:none;border-left:none'>职位域服务中心</td>
  <td class=xl66 style='border-top:none;border-left:none'>11600</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl65 align=right style='height:14.25pt;border-top:none'>38</td>
  <td class=xl65 style='border-top:none;border-left:none'>business-region</td>
  <td class=xl65 style='border-top:none;border-left:none'>区域模块</td>
  <td class=xl66 style='border-top:none;border-left:none'>11700</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl65 align=right style='height:14.25pt;border-top:none'>39</td>
  <td rowspan=3 class=xl74 style='border-bottom:.5pt solid black;border-top:
  none'>134.96.217.244</td>
  <td class=xl65 style='border-top:none;border-left:none'>business-public</td>
  <td class=xl65 style='border-top:none;border-left:none'>公共域服务中心</td>
  <td class=xl66 style='border-top:none;border-left:none'>11500</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl65 align=right style='height:14.25pt;border-top:none'>40</td>
  <td class=xl65 style='border-top:none;border-left:none'>business-position</td>
  <td class=xl65 style='border-top:none;border-left:none'>职位域服务中心</td>
  <td class=xl66 style='border-top:none;border-left:none'>11600</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl65 align=right style='height:14.25pt;border-top:none'>41</td>
  <td class=xl65 style='border-top:none;border-left:none'>business-region</td>
  <td class=xl65 style='border-top:none;border-left:none'>区域模块</td>
  <td class=xl66 style='border-top:none;border-left:none'>11700</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl65 style='height:14.25pt;border-top:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl66 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl65 style='height:14.25pt;border-top:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl66 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl65 style='height:14.25pt;border-top:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl66 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl65 style='height:14.25pt;border-top:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl66 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl65 style='height:14.25pt;border-top:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl66 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl65 style='height:14.25pt;border-top:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl66 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
  <td class=xl65 style='border-top:none;border-left:none'>　</td>
 </tr>
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=72 style='width:54pt'></td>
  <td width=172 style='width:129pt'></td>
  <td width=237 style='width:178pt'></td>
  <td width=321 style='width:241pt'></td>
  <td width=270 style='width:203pt'></td>
  <td width=140 style='width:105pt'></td>
  <td width=140 style='width:105pt'></td>
  <td width=259 style='width:194pt'></td>
 </tr>
 <![endif]>
</table>