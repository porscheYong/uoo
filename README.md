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

## 系统平台自身【common-system】
系统平台与核心业务模块是相互分来的，就是剥离掉核心业务模块，系统平台仍然能被其他人复用。所以在设计阶段，就将平台这块独立自成体系。

### 逻辑模型

 ![avatar](https://github.com/rothschil/static/raw/master/UOO角色权限设计V0.2.jpg)

### 用户管理

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
|   |   |——base-common------------------------------------基础部分
|   |   |   |
|   |   |   |——common-tool--------------------------------工具脚手架类
|   |   |   |
|   |   |   |——common-abstract----------------------------通用持久层，、可复用配置包
|   |   |   |
|   |   |   |——common-persitence--------------------------通用持久层
|   |   |   |   |
|   |   |   |   |——persistence-jpa------------------------JPA实现类
|   |   |
|   |   |——uoo-register-----------------------------------注册中心
|   |   |
|   |   |——uoo-gateway------------------------------------网关中心
|   |   |
|   |   |——uoo-sleuth-------------------------------------链路追踪中心
|   |   |
|   |   |——uoo-monitor------------------------------------监控中心
|   |
|   |——uoo-common-----------------------------------------公共模块，平台自身
|   |   |
|   |   |——common-system----------------------------------平台用户、部门等
|   |   |
|   |   |——common-activiti--------------------------------平台工作流
|   |   |   |
|   |   |   |——activiti-core------------------------------工作流模块，目前仅内蒙项目
|   |   |
|   |   |——common-cache-----------------------------------平台缓存服务中心
|   |   |
|   |   |——common-queues----------------------------------队列管理服务中心
|   |   |   |
|   |   |   |——queues-manager-----------------------------rabbitMQ队列管理能力
|   |   |
|   |   |——common-im--------------------------------------实时通讯服务中心
|   |
|   |——uoo-business---------------------------------------UOO核心模块
|   |   |
|   |   |——business-syn-msg-------------------------------数据共享组件服务中心
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
|   |   |——business-authentication------------------------认证服务中心
|   |
|   |——uoo-business-api-----------------------------------UOO开放式服务API
|   |   |
|   |   |——api-user---------------------------------------用户域开放式API
|   |   |
|   |   |——api-org----------------------------------------组织域开放式API
|   |   |
|   |   |——api-security-----------------------------------安全域开放式API
|   |   |   |
|   |   |   |——security-authorize-------------------------授权管理服务API
|   |   |   |
|   |   |   |——security-authentication--------------------身份认证服务API
|   |   |   |   |
|   |   |   |   |——authentication-cs----------------------身份认证服务API-CS方式
|   |   |   |   |
|   |   |   |   |——authentication-bs----------------------身份认证服务API-BS方式
|   |
|   |——uoo-business-web-----------------------------------UOO WEB端
|   |   |
|   |   |——mdm-web----------------------------------------主数据中心维护支撑WEB
|   |   |
|   |   |——auth-web---------------------------------------认证中心维护支撑WEB

~~~~

## 服务中心部署

### 多环境配置Shell脚本部署

#### 说明
实际开发过程中，开发环境、测试环境、生产环境的配置是不一样的，spring boot定义的规则是允许按照命名方式不同来激活响应的配置文件。
如在启动过程中，根据 “--spring.profiles.active={自己定义}”，分别激活响应的配置

![avatar](https://github.com/rothschil/static/raw/master/Snipaste_2018-12-06_18-04-13.jpg)
要求 application.yml/application.properties具备多个spring.profiles.active，用以区分环境，案例如下：
~~~
spring:
  profiles: 221
  application:
    name: uoo-register
eureka:
  instance:
    prefer-ip-address: true
    hostname: 134.96.253.221
  client:
    serviceUrl:
      defaultZone: http://134.96.253.222:8761/eureka/,http://134.96.253.223:8761/eureka/
server:
  port: 8761

---
spring:
  profiles: 222
  application:
    name: uoo-register
eureka:
  instance:
    prefer-ip-address: true
    hostname: 134.96.253.222
  client:
    serviceUrl:
      defaultZone: http://134.96.253.221:8761/eureka/,http://134.96.253.223:8761/eureka/
server:
  port: 8761

---
spring:
  profiles: 223
  application:
    name: uoo-register
eureka:
  instance:
    prefer-ip-address: true
    hostname: 134.96.253.223
  client:
    serviceUrl:
      defaultZone: http://134.96.253.221:8761/eureka/,http://134.96.253.222:8761/eureka/
server:
  port: 8761
~~~

#### shell脚本

[spring-boot源码](https://github.com/rothschil/static/raw/master/spring-boot.sh)


    sh spring-boot.sh start YOUR_APP_NAME.jar --spring.profiles.active={自己定义}


- 第一个参数: start为启动命令 (也可以为,stop,restart,status)
- 第二个参数: YOUR_APP_NAME.jar为需启动的jar包
- 第三个参数: --spring.profiles.active 启动参数(为空则使用激活默认，加参数的条件下，则激活相应的配置，如”--spring.profiles.active=223“，则激活spring.profiles名字为223)

~~~

function start()
{
    count='ps -ef |grep java|grep $SpringBoot|grep -v grep|wc -l'
    
    if [[ $count -eq 0 ]];
    then
        echo "$SpringBoot is running..."
    else
        echo "Start $SpringBoot success..."
        nohup java -jar $SpringBoot  $START_OPTS > /dev/null 2>&1 &
    fi
}
~~~

#### 执行
- 253.221机器上执行
    
    sh spring-boot.sh start uoo-register-1.0.1.RELEASE.jar --spring.profiles.active=221
- 253.222机器上执行
    
    sh spring-boot.sh start uoo-register-1.0.1.RELEASE.jar --spring.profiles.active=222
- 253.223机器上执行
    
    sh spring-boot.sh start uoo-register-1.0.1.RELEASE.jar --spring.profiles.active=223

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
| 86 | uoo-sleuth | 链路追踪中心 | 8600 | 8611、8622 |
| 87 | uoo-register | 注册中心 | 8751 | 8761、8771 |
| 88 | uoo-gateway | 通用网关 | 8800 | 8811、8822 |
| 89 | uoo-monitor | 监控中心 | 8900 | 8911、8922 |

#### uoo-common公用模块

| 序号 | 名称 | 描述 | 阶段I部署端口 | 阶段II部署端口 |
| ------ | ------ | ------ | ------ | ------ |
| 91 | common-system | 平台用户、部门等 | 9100 | 9111、9122 |
| 92 | activiti-core | 工作流模块 | 9200 | 9211、9222 |
| 93 | common-cache | 平台缓存服务中心 | 9300 | 9311、9322 |
| 94 | queues-manager | 队列管理能力 | 9400 |9411、9422 |
| 95 | common-im | 实时通讯服务中心 | 9500 | 9511、9522 |

#### uoo-business UOO核心模块

| 序号 | 名称 | 描述 | 阶段I部署端口 | 阶段II部署端口 |
| ------ | ------ | ------ | ------ | ------ |
| 110 | business-syn-msg | 数据共享组件服务中心 | 11000 | 11022、11033 |
| 111 | business-organization |组织域服务中心 | 11100 | 11111、11122 |
| 112 | business-personnel | 人员域服务中心 | 11200 | 11211、11222 |
| 113 | business-user | 用户业务服务中心 | 11300 | 11311、11322 |
| 114 | business-permission | 权限域服务中心 | 11400 | 11411、11422 |
| 115 | business-public | 公共域服务中心 | 11500 | 11511、11522 |
| 116 | business-position | 职位域服务中心 | 11600 | 11611、11622 |
| 117 | business-region | 区域服务中心 | 11700 | 11711、11722 |
| 118 | business-authentication | 认证服务中心 | 11800 | 11811、11822 |

#### uoo-business-web业务模块

| 序号 | 名称 | 描述 | 阶段I部署端口 | 阶段II部署端口 |
| ------ | ------ | ------ | ------ | ------ |
| 180 | mdm-web | 主数据中心维护支撑WEB | 18000 | 18022、18033 |
| 181 | auth-web | 认证中心维护支撑WEB | 18100 | 18111、18122 |

#### uoo-business-api业务模块

| 序号 | 名称 | 描述 |  阶段I部署端口 | 阶段II部署端口 |
| ------ | ------ | ------ | ------ | ------ |
| 200 | api-user | 用户域开放式API | 20000 | 20022、20033 |
| 201 | api-org | 组织域开放式API | 20100 | 20111、20122 |
| 202 | security-authorize | 授权管理服务API | 20200 | 20211、20222 |
| 203 | authentication-bs | 身份认证服务API-BS方式 | 20300 | 20311、20322 |
| 204 | authentication-cs | 身份认证服务API-CS方式 | 20400 | 20411、20422 |

### 正式生产环境
#### uoo-base基础模块

| 序号 | 名称 | 描述 | 阶段I部署端口 | 阶段II部署端口 |
| ------ | ------ | ------ | ------ | ------ |
| 86 | uoo-sleuth | 链路追踪中心 | 8600、8611 | 8622、8633、8644 |
| 87 | uoo-register | 注册中心 | 8751、8761 | 8771，8781、8791 |
| 88 | uoo-gateway | 通用网关 | 8800、8811 | 8822、8833、8444 |
| 89 | uoo-monitor | 监控中心 | 8900、8911 | 8922、8933、8944 |

#### uoo-common公用模块

| 序号 | 名称 | 描述 | 阶段I部署端口 | 阶段II部署端口 |
| ------ | ------ | ------ | ------ | ------ |
| 91 | common-system | 平台用户、部门等 | 9100、9111 | 9122、9133、9144 |
| 92 | activiti-core | 工作流模块 | 9200、9211 | 9222、9233、9244 |
| 93 | common-cache | 平台缓存服务中心 | 9300、9311 | 9322、9333、9344 |
| 94 | queues-manager | 队列管理能力 | 9400、9411 | 9422、9433、9444 |
| 95 | common-im | 实时通讯服务中心 | 9500、9511 | 9522、9533、9544 |

#### uoo-business UOO核心模块

| 序号 | 名称 | 描述 | 阶段I部署端口 | 阶段II部署端口 |
| ------ | ------ | ------ | ------ | ------ |
| 110 | business-syn-msg | 数据共享组件服务中心 | 11000、11022 | 11033、11044、11055 |
| 111 | business-organization |组织域服务中心 | 11100、11111 | 11122、11133、11144 |
| 112 | business-personnel | 人员域服务中心 | 11200、11211 | 11222、11233、11244 |
| 113 | business-user | 用户域服务中心 | 11300、11311 | 11322、11333、11344 |
| 114 | business-permission | 权限域服务中心 | 11400、11411 | 11422、11433、11444 |
| 115 | business-public | 公共域服务中心 | 11500 | 11511、 11522、11533、11544 |
| 116 | business-position | 职位域服务中心 | 11600 | 11611、11622、11633、11644 |
| 117 | business-region | 区域服务中心 | 11700 | 11711、11722、11733、11744 |
| 118 | business-authentication | 认证服务中心 | 11800 | 11811、11822、11833、11844 |

#### uoo-business-web业务模块

| 序号 | 名称 | 描述 | 阶段I部署端口 | 阶段II部署端口 |
| ------ | ------ | ------ | ------ | ------ |
| 180 | mdm-web | 主数据中心维护支撑WEB | 18000 | 18011、18022、18033、18044 |
| 181 | auth-web | 认证中心维护支撑WEB | 18100 | 18111、18122、18133、18144 |

#### uoo-business-api业务模块

| 序号 | 名称 | 描述 | 阶段I部署端口 | 阶段II部署端口 |
| ------ | ------ | ------ | ------ | ------ |
| 200 | api-user | 用户域开放式API | 20000、20022 | 20033、20033、20044  |
| 201 | api-org | 组织域开放式API | 20100、20111 | 20122、20133、20144 |
| 202 | security-authorize | 授权管理服务API | 20200、20211 | 20222、20233 、20244  |
| 203 | authentication-bs | 身份管理服务API-BS方式 | 20300、20311 | 20322、20333、20344 |
| 204 | authentication-cs | 身份管理服务API-CS方式 | 20400、20411 | 20422、20433、20444 |

### 详细部署清单

![avatar](https://github.com/rothschil/static/raw/master/Snipaste_2018-12-04_21-22-03.jpg)


### 业务规则

#### 人员编码、人员工号、账号

- 合同制、外包制等集团管理的人员对人力编码、人员编码、人员编码、主账号按照转换规则沿用
- 合作伙伴等第三方人员人员编码、人员工号、主账号直接H开头，8位序列

举个栗子

| 类型 | 人力编码 | 人员编码 | 人员工号 | 主账号标识 | 主账号 | 从账号标识 | 从账号 |
| ------ | ------ | ------ | ------ | ------ | ------ | ------ | ------ |
| 合同制 | 33028413@ZJ | 33028413 | 33028413 | 序列1  | 33028413  | 序列2  | yx.ff  |
| 合同制 | 71987581@ZJ | 71987581 | 71987581 | 序列1  | 71987581  | 序列2  | yx.ff  |
| 外包 | W33028413@ZJ | W33028413 | W33028413 | 序列1  | W33028413  | 序列2  | yx.ff  |
| 合作伙伴 | 无 | H12345678 | H12345678 | 序列1  | H12345678  | 序列2  | yx.ff  |

#### 标准组织树、专业组织树定义
##### 标准树
在项目开始阶段，以营销支撑为试点，按照局方管经理要求，需结合集团统一目录中1-5级组织机构并参照营销支撑中1~4级二者融合，作为今后标准树的躯干，往后在陆续接入各专业域系统过程中，完善该标准树分支节点。
当前的标准树缺少班组、团队以及渠道单元，需要再接入渠道视图、服开、服保、客服、ITSM等系统。

~~~
营销支撑接入中标准树样例如下

|——中国电信股份有限公司浙江分公司
|   |——浙江省电信公司
|   |   |——衢州市分公司
|   |   |   |——衢州未落地区域
|   |   |   |——衢州市分公司本级
|   |   |   |——衢州市本级代理商
|   |   |   |——衢州安保部
|   |   |   |——常山分公司
|   |   |   |   |——常山联营站<人力独有>
|   |   |   |   |——常山网络运营部<人力独有>
|   |   |   |   |——常山3G辅导中心
|   |   |   |   |——常山分公司本级
|   |   |   |   |——常山工会
|   |   |   |   |——常山城西支局
|   |   |   |   |——常山城东支局
|   |   |   |   |——常山中小幼包区2
|   |   |   |   |——常山大企业包区
|   |   |   |——龙游分公司
|   |   |——舟山市分公司
|   |   |——台州市分公司
|   |   |——.......
~~~

##### 专业树
- 营销专业树：营销支撑中原封不动
- 人力专业树：HR人力组织中的全部

##### 人员组织关系、账号组织关系
- 人员组织关系，以人力专业树为基准
- 账号组织关系，以标准树为基准（由于营销支撑作为试点，营销支撑包区单元下的账号，此类属于特殊情况，其账号组织关系应该以营销专业树为准）