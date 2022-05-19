## 业务框架

如果说SOFABolt为了让 Java 程序员能将更多的精力放在基于网络通信的业务逻辑实现上。**而业务框架正是解决业务逻辑如何方便的实现这一问题上**。

业务框架是游戏框架的一部份，职责是简化程序员的业务逻辑实现。

业务框架使程序员能够快速的开始编写游戏业务。在开发阶段可快速定位业务代码，请求响应监控，方便日志定位（插件实现）。

### 特点：

- 跨NIO框架（屏蔽 NIO）, 可以做到业务代码不做任何的改动，就能轻松实现NIO框架的切换.(netty mina 这些都属于NIO框架)

- 线程模型，基于[disruptor](https://www.yuque.com/iohao/game/gmfy1k) 环形数组来消费业务

- [注重开发体验](https://www.yuque.com/iohao/game/wotnhl)

- [插件机制，扩展性高](https://www.yuque.com/iohao/game/gmxz33)

- [异常机制](https://www.yuque.com/iohao/game/avlo99)

- [游戏文档生成](https://www.yuque.com/iohao/game/irth38)

- [支持 JSR303+](https://www.yuque.com/iohao/game/ghng6g)

- [jprotobuf通信协议的友好支持](https://www.yuque.com/iohao/game/mbr9in)

业务框架处理入口方法
> BarSkeleton.handle() 
> 
> BrokerClientRequestMessageProcessor
