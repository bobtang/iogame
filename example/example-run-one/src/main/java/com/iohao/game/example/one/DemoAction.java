package com.iohao.game.example.one;

import com.iohao.little.game.action.skeleton.annotation.ActionController;
import com.iohao.little.game.action.skeleton.annotation.ActionMethod;
import com.iohao.little.game.action.skeleton.core.exception.MsgException;

/**
 * @author 洛朱
 * @date 2022-02-24
 */
@ActionController(1)
public class DemoAction {
    /**
     * 示例 here 方法
     *
     * @param helloReq helloReq
     * @return HelloReq
     */
    @ActionMethod(0)
    public HelloReq here(HelloReq helloReq) {
        HelloReq newHelloReq = new HelloReq();
        newHelloReq.name = helloReq.name + ", I'm here ";
        return newHelloReq;
    }

    /**
     * 示例 异常机制演示
     *
     * @param helloReq helloReq
     * @return HelloReq
     * @throws MsgException e
     */
    @ActionMethod(1)
    public HelloReq jackson(HelloReq helloReq) throws MsgException {
        String jacksonName = "jackson";
        if (!jacksonName.equals(helloReq.name)) {
            throw new MsgException(100, "异常机制测试，name 必需是 jackson !");
        }

        helloReq.name = helloReq.name + ", hello, jackson !";

        return helloReq;
    }
}
