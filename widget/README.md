### 小部件集合

- 消息发布订阅 (widget-broadcast)



### 小部件的解释

每个小部件解决业务中的一类问题；

提供统一的编码方式，在不改变使用方式的情况下可以随时切换内部实现；






### 小部件的开发过程

尽量以抽象的方式，如接口对外提供服务。

但前期的抽象并不是必须的，这是一个矛盾的过程。

矛盾的过程分为这几个阶段：

1 先以完成功能为前提（即能用）

2 改善来源于自己编码思想的提升与小部件使用方的反馈

3 重构不理想的小部件

使小部件最后的抽象达到现阶段满意的情况，并循环往复矛盾过程；



### 小部件的实践

#### 小部件接口说明
小部件标记接口
```java
public interface WidgetComponent {
}
```

小部件管理器 - WidgetComponents

```java
public class WidgetComponents {
  // 管理所有小部件 WidgetComponent
}
```



#### 示例使用

发布订阅的伪代码

```java
// 小部件-构建示例； 一个发布订阅的相关的小部件 
public void test1(WidgetComponents widgetComponents) {
    // 消息队列配置项
    MessageQueueConfigWidget messageQueueConfigWidget = new MessageQueueConfigWidget();
    // 消息队列小部件
    MessageQueueWidget messageQueueWidget = null;
    // 消息队列小部件 - 使用内网的实现 (也可以换成 redis[Redisson， Lettuce], MQ[Apache Pulsar, RocketMQ]等)
    messageQueueWidget = new InternalMessageQueueWidget(messageQueueConfigWidget);

    // 添加发布订阅消息处理类
    messageQueueWidget.addMessageListener(new BroadcastMessageListenerWidget());

    // 添加到部件管理中
    widgetComponents.option(MessageQueueWidget.class, messageQueueWidget);
}

// 小部件-使用示例
public void test2() {
  	// 通过 MessageQueueWidget.class 获取小部件实现类
		MessageQueueWidget messageQueueWidget = widgetComponents.option(MessageQueueWidget.class);
  	// 发布一条消息
	  messageQueueWidget.publish(“hello messagee”);
  
}
```

