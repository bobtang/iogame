package com.iohao.game.collect.external.demo;

import com.iohao.little.game.action.skeleton.annotation.ActionController;
import com.iohao.little.game.action.skeleton.annotation.ActionMethod;
import com.iohao.little.game.action.skeleton.core.exception.MsgException;

/**
 * @author 洛朱
 * @date 2022-02-24
 */
@ActionController(1)
public class DemoAction {
    @ActionMethod(0)
    public HelloReq here(HelloReq helloReq) {
        helloReq.name = helloReq.name + ", I'm here ";
        return helloReq;
    }

    @ActionMethod(1)
    public HelloReq jackson(HelloReq helloReq) throws MsgException {

        if (!"jackson".equals(helloReq.name)) {
            throw new MsgException(100, "异常机制测试，name 必需是 jackson !");
        }

        helloReq.name = helloReq.name + ", hello, jackson !";

        return helloReq;
    }
}
