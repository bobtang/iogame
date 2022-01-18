package com.iohao.example.common;

/**
 * 用于推送
 * @author 洛朱
 * @date 2022-01-18
 */
public interface ExampleBroadcastCmd  extends ExampleActionCont.TheInfo {
    /** 推送模块 主cmd */
    int cmd = 1;
    ExampleActionCont.TheInfo info = () -> cmd;
    /** 用户账户信息 */
    int user_account = 0;
}
