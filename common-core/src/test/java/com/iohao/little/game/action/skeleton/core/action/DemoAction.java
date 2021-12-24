package com.iohao.little.game.action.skeleton.core.action;

import com.iohao.little.game.action.skeleton.annotation.ActionController;
import com.iohao.little.game.action.skeleton.annotation.ActionMethod;

/**
 * @author 洛朱
 * @date 2021-12-24
 */
@ActionController(ActionCont.DemoActionCont.cmd)
public class DemoAction {
    @ActionMethod(ActionCont.DemoActionCont.hello)
    public String hello(String name) {
        return name + ", I'm here ";
    }
}
