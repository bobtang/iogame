package com.iohao.example.common.md;

import cn.hutool.core.util.RandomUtil;
import com.iohao.little.game.action.skeleton.annotation.ActionController;
import com.iohao.little.game.action.skeleton.annotation.ActionMethod;

/**
 *
 * @author 洛朱
 * @Date 2021-12-12
 */
@ActionController(ActivityModule.cmd)
public class ActivityAction {
    /**
     * <pre>
     *     打招呼 hello
     *     实现注解 ActionMethod 告知业务框架这是一个对外开放的action (既一个方法就是一个对外的处理)
     *     这里请求参数是一个对象，响应是一个对象的示例
     * </pre>
     *
     * @param active 请求参数，是由掉用方传入 (通常是游戏的客户端)
     * @return 响应 （对象）
     */
    @ActionMethod(ActivityModule.hello)
    public Active hello(Active active) {
        Active joker = new Active();
        joker.setName("say: hello, " + active.getName());
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
    @ActionMethod(ActivityModule.age)
    public int age(int age) {
        // 从这里可以看出，参数与返回值可以是任意类型
        return age + 10;
    }
}
