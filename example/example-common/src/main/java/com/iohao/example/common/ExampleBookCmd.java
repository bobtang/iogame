package com.iohao.example.common;

/**
 * @author 洛朱
 * @date 2022-01-18
 */
public interface ExampleBookCmd extends ExampleActionCont.TheInfo {
    int cmd = 3;
    ExampleActionCont.TheInfo info = () -> cmd;
    int name = 0;
    int get_apple_age = 1;
    int message_queue = 2;

    int broadcast_user_info = 3;
}
