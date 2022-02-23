## 愿景

让游戏服务端的编程变得轻松简单！



开源地址: [基于 sofa-bolt 的游戏框架-源码](https://gitee.com/iohao/bolt-game-sun)

在线文档: [基于 sofa-bolt 的游戏框架-文档](https://gitee.com/link?target=https%3A%2F%2Fwww.yuque.com%2Fiohao%2Fgame)

## 游戏框架简介

基于 [sofa-bolt](https://www.sofastack.tech/projects/sofa-bolt/overview/) 的游戏框架，游戏框架由 [通讯框架] 和 [业务框架] 组成。

通讯框架：负责端端之间的数据传输

业务框架：负责业务逻辑的处理方式和编写方式



致力于为游戏服务端程序员提供：稳定、高性能的服务、高可扩展、简单上手。即使之前没有游戏编程的经验，也能参与到游戏编程中。

游戏框架致力于屏蔽通信细节，为了让 Java 程序员能将更多的精力放在基于游戏框架的业务逻辑实现上！

如果你之前具备一些游戏开发或者 web MVC 的知识会更容易的上手游戏服务端的开发。

## 通讯框架 - SOFABolt

[SOFABolt](https://www.sofastack.tech/projects/sofa-bolt/overview/) 是蚂蚁金融服务集团开发的一套基于 Netty 实现的网络通信框架。

- 为了让 Java 程序员能将更多的精力放在基于网络通信的业务逻辑实现上，而不是过多的纠结于网络底层 NIO 的实现以及处理难以调试的网络问题，Netty 应运而生。
- 为了让中间件开发者能将更多的精力放在产品功能特性实现上，而不是重复地一遍遍制造通信框架的轮子，SOFABolt 应运而生。



Bolt 名字取自迪士尼动画-闪电狗，是一个基于 Netty 最佳实践的轻量、易用、高性能、易扩展的通信框架。

## 业务框架

如果说SOFABolt为了让 Java 程序员能将更多的精力放在基于网络通信的业务逻辑实现上。而业务框架正是解决业务逻辑如何方便的实现这一问题上。

业务框架是游戏框架的一部份，职责是简化程序员的业务逻辑实现。业务框架使程序员能够快速的开始编写游戏业务。

在开发阶段可快速定位业务代码，请求响应监控，方便日志定位（插件实现）。



### 特点：

- 跨NIO框架（屏蔽 NIO）, 可以做到业务代码不做任何的改动，就能轻松实现NIO框架的切换.(netty mina 这些都属于NIO框架)
- 线程模型，基于[disruptor](https://www.yuque.com/iohao/game/gmfy1k)环形数组来消费业务

- [注重开发体验](https://www.yuque.com/iohao/game/wotnhl) 
- [插件机制，扩展性高](https://www.yuque.com/iohao/game/gmxz33)

- [异常机制](https://www.yuque.com/iohao/game/avlo99)
- [游戏文档生成](https://www.yuque.com/iohao/game/irth38)

- [支持 JSR303+](https://www.yuque.com/iohao/game/ghng6g)
- [jprotobuf通信协议的友好支持](https://www.yuque.com/iohao/game/mbr9in)



## 快速入门

### 业务交互



![img](https://gitee.com/iohao/xmindpic/tree/master/game/interaction.jpeg)



抽象的说，游戏前端与游戏服务端的的交互由上图组成。游戏前端与游戏服务端可以自由的双向交互，交互的业务数据由 .proto 作为载体。



### 协议文件

协议文件是对业务数据的描述载体，用于游戏前端与游戏服务端的数据交互。Protocol Buffers (ProtocolBuffer/ protobuf )是Google公司开发的一种数据描述语言。也简称 [PB](https://www.oschina.net/p/protocol+buffers)。

当然协议文件描述还可以是 json、xml或者任意自定义的，因为最后传输时会转换为二进制。但游戏开发中 PB 是目前的最佳。



### 游戏前端

游戏前端可以是 [Unity](https://unity.cn/)、 [UE(虚幻)](https://www.unrealengine.com/zh-CN/)、 [Cocos](https://www.cocos.com/)或者其他的游戏引擎。游戏前端与用户的接触最为直接。



### 游戏服务端

游戏服务端处理实际的用户等其他的业务数据。



### 快速入门代码示例

这里主要介绍游戏服务端，毕竟是游戏服务端的框架。下面这个示例介绍了服务端编程可以变得如此简单。



#### Proto 协议文件定义

首先我们自定义一个协议文件，这个协议文件作为我们的业务载体描述。这个协议是纯java代码编写的，使用的是 jprotobuf, jprotobuf 是对 [google protobuf](https://www.oschina.net/p/protocol+buffers) 的简化使用，性能同等。

```java
/** 请求 */
@ProtobufClass
@FieldDefaults(level = AccessLevel.PUBLIC)
public class HelloReq {
    String name;
}
```



#### Action

游戏服务端的编程，游戏服务端接收业务数据后，对业务数据进行处理；

```java
@ActionController(1)
public class DemoAction {
    @ActionMethod(0)
    public HelloReq here(HelloReq helloReq) {
        helloReq.name = helloReq.name + ", I'm here ";
        return helloReq;
    }
    
	@ActionMethod(1)
    public HelloReq say(HelloReq helloReq) {
        helloReq.name = helloReq.name + ", I'm say ";
        return helloReq;
    }
}
```

一个方法在业务框架中表示一个 Action（既一个业务动作）。

方法声名的参数是用于接收前端传入的业务数据，在方法 return 时，数据就可以被游戏前端接收到。程序员可以不需要关心业务框架的内部细节。



从上面的示例可以看出，这和普通的 java 类并无区别。如果只负责编写游戏业务，那么对于业务框架的学习可以到此为止了。

游戏编程就是如此简单！



问：我可以开始游戏服务端的编程了吗？

是的，你已经可以开始游戏服务端的编程了。



## 适合人群？

1. 长期从事 web 内部系统开发人员， 想了解游戏的
2. 刚从事游戏开发的

1. 未从事过游戏开发但却对其感兴趣的



推荐实际编程经验一年以上的人员



## 参考

什么是 [Action](https://www.yuque.com/iohao/game/sqcevl)。



## 加群



### QQ

![img](https://gitee.com/iohao/xmindpic/tree/master/game/QQ1.jpeg)