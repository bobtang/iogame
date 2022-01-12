package com.iohao.example.a.action;

import cn.hutool.core.util.RandomUtil;
import com.iohao.example.common.ExampleActionCont;
import com.iohao.example.common.Apple;
import com.iohao.little.game.action.skeleton.annotation.ActionController;
import com.iohao.little.game.action.skeleton.annotation.ActionMethod;
import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Slf4j
@ActionController(ExampleActionCont.AppleModule.cmd)
public class AppleAction {

    /**
     * <pre>
     *     打招呼 hello
     *     实现注解 ActionMethod 告知业务框架这是一个对外开放的action (既一个方法就是一个对外的处理)
     *     这里请求参数是一个对象，响应是一个对象的示例
     * </pre>
     *
     * @param apple 请求参数，是由掉用方传入 (通常是游戏的客户端)
     * @return 响应 （对象）
     */
    @ActionMethod(ExampleActionCont.AppleModule.name)
    public Apple hello(Apple apple) {
        log.info("apple: {}", apple);
        Apple joker = new Apple();
        joker.setName("say: hello, " + apple.getName());
        joker.setAge(RandomUtil.randomInt(200, 300));
        return joker;
    }

    /**
     * <pre>
     *     age process
     * </pre>
     *
     * @param age age
     * @return age + 10
     */
    @ActionMethod(ExampleActionCont.AppleModule.age)
    public int age(int age) {
        // 从这里可以看出，参数与返回值可以是任意类型
        return age + 10;
    }
}

