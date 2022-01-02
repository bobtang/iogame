![JDK 17](https://img.shields.io/badge/JDK-17-green.svg#crop=0&crop=0&crop=1&crop=1&id=D7dOD&originHeight=20&originWidth=54&originalType=binary&ratio=1&rotation=0&showTitle=true&status=done&style=none&title=JDK%2017 "JDK 17")
### 愿景
让游戏服务端的编程变得简单！
​

开源地址: [https://gitee.com/iohao/bolt-game-sun](https://gitee.com/iohao/bolt-game-sun)

在线文档: [https://www.yuque.com/iohao/game](https://www.yuque.com/iohao/game)
​

### 游戏框架简介
基于 [sofa-bolt](https://www.sofastack.tech/projects/sofa-bolt/overview/) 的游戏框架

游戏框架由 [通讯框架 - [sofa-bolt](https://www.sofastack.tech/projects/sofa-bolt/overview/)] 和 [业务框架] 组成

通讯框架( [sofa-bolt](https://www.sofastack.tech/projects/sofa-bolt/overview/))：负责端端之间的传输数据

业务框架：负责业务类的处理方式和编写方式
​

致力于为游戏服务端程序员提供：稳定、高性能的服务、高可扩展、简单上手

即使之前没有游戏编程的经验，也能参与到游戏编程中；

游戏框架致力于屏蔽通信细节，为了让 Java 程序员能将更多的精力放在基于游戏框架的业务逻辑实现上！
​

### 通讯框架 - SOFABolt
SOFABolt 是蚂蚁金融服务集团开发的一套基于 Netty 实现的网络通信框架。

- 为了让 Java 程序员能将更多的精力放在基于网络通信的业务逻辑实现上，而不是过多的纠结于网络底层 NIO 的实现以及处理难以调试的网络问题，Netty 应运而生。
- 为了让中间件开发者能将更多的精力放在产品功能特性实现上，而不是重复地一遍遍制造通信框架的轮子，SOFABolt 应运而生。

Bolt 名字取自迪士尼动画-闪电狗，是一个基于 Netty 最佳实践的轻量、易用、高性能、易扩展的通信框架。


### 业务框架
#### 简介
游戏业务类的核心处理框架，处理程序员的业务逻辑实现

与SOFABolt配合，如果说SOFABolt为了让 Java 程序员能将更多的精力放在基于网络通信的业务逻辑实现上。

而业务框架正是解决业务逻辑如何实现的这一问题上。


#### 特点：

- 跨NIO框架（屏蔽 NIO）, 可以做到业务代码不做任何的改动，就能轻松实现NIO框架的切换.(netty mina 这些都属于NIO框架)
- 插件机制，扩展性高
- 易上手，使程序员能快速开始编写业务
- 在开发阶段可快速定位业务代码（插件实现）
- 请求响应监控 方便日志定位（插件实现）
- 线程模型，基于disruptor环形数组来消费业务（可灵活的定制业务线程模型）

#### 快速入门-业务代码
下面定义一个普通的 java类，并在这个类中定义了一个方法.
​

类中使用了两个注解，分别是

1. @ActionController 主路由 （作用在业务类上）
1. @ActionMethod 子路由（作用在业务方法上）

​

```java
@ActionController(1)
public class DemoAction {
    @ActionMethod(0)
    public String hello(String name) {
        return name + ",hello client. I'm here ";
    }

}
```


这样就完成了一个对外开放的访问方法了；一个方法在业务框架中表示一个 Action（既一个业务动作）。

如果只负责编写游戏业务，那么对于业务框架的学习可以到此为止了。
​

可以看出游戏编程就是如此简单！
​

关于路由的解释可以参考 [路由文档](https://www.yuque.com/iohao/game/soxp4u)

### 其它小记
​

```
可用于页游，手游，游戏服务器端；

可扩展性强，基于小部件机制

小部件
    逻辑服广播到网关服务器 - (完成)
    分步式锁 - (开发中)
    
已完成
		调通了通讯部分
		完成子逻辑服之间的通讯 （如： 子模块A 访问 子模块B 的某个方法，因为只有子模块B持有这些数据）
    内建服务发现、负载均衡
    端端之间的断线重连、心跳检测 
```


