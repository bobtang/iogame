package com.iohao.little.game.action.skeleton.core.action;

import com.iohao.little.game.action.skeleton.annotation.ActionController;
import com.iohao.little.game.action.skeleton.annotation.ActionMethod;
import com.iohao.little.game.action.skeleton.core.action.pojo.BeeApple;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ActionController(ActionCont.BeeActionCont.CMD)
public class BeeAction {

    /**
     * <pre>
     *     打招呼
     *     实现注解 ActionMethod 告知框架这是一个对外开放的action (既一个方法就是一个对外的处理)
     * </pre>
     *
     * @param beeApple a
     * @return 返回具体信息
     */
    @ActionMethod(ActionCont.BeeActionCont.HELLO)
    public String hello(BeeApple beeApple) {
        log.debug("beeApple: {}", beeApple);
        // 响应给客户端的数据 string 类型. 框架可根据返回参数类型将返回结果装到响应体中
        return " : hello: ";
    }

    @ActionMethod(ActionCont.BeeActionCont.NAME)
    public String name(BeeApple beeApple) {
        log.debug("beeApple: {}", beeApple);
        // 响应给客户端的数据 string 类型. 框架可根据返回参数类型将返回结果装到响应体中
        return " : name: ";
    }
}
