package com.iohao.little.game.action.skeleton.core.action;

import com.alipay.remoting.BizContext;
import com.iohao.little.game.action.skeleton.annotation.ActionController;
import com.iohao.little.game.action.skeleton.annotation.ActionMethod;
import com.iohao.little.game.action.skeleton.core.action.pojo.Bird;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

@Slf4j
@ActionController(ActionCont.BirdActionCont.CMD)
public class BirdAction {
    /**
     * <pre>
     *     打招呼
     *     实现注解 ActionMethod 告知框架这是一个对外开放的action (既一个方法就是一个对外的处理)
     * </pre>
     *
     * @param bird a
     * @return 返回具体信息
     */
    @ActionMethod(ActionCont.BirdActionCont.BIRD_NAME)
    public String hello(List<Bird> birdList, Bird bird) {
        log.debug("bird: {}", bird, birdList);
        // 响应给客户端的数据 string 类型. 框架可根据返回参数类型将返回结果装到响应体中
        return " : BIRD_NAME hello: ";
    }

    @ActionMethod(ActionCont.BirdActionCont.BIRD_AGE)
    public List<Bird> name(Bird bird, BizContext bizContext) {
        log.debug("bird: {}", bird);
        // 响应给客户端的数据 string 类型. 框架可根据返回参数类型将返回结果装到响应体中
//        return " : BIRD_AGE name: ";
        return Collections.emptyList();
    }
}
