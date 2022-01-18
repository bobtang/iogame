package com.iohao.example.common;

/**
 * 模块A
 *
 * @author 洛朱
 * @date 2022-01-18
 */
public interface ExampleAppleCmd extends ExampleActionCont.TheInfo {
    /**
     * 模块A - 主 cmd
     */
    int cmd = 2;

    ExampleActionCont.TheInfo info = () -> cmd;

    int name = 0;
    int age = 1;
    /** jsr 303 测试 */
    /** jsr 303 测试 */
    int validate = 2;
}
