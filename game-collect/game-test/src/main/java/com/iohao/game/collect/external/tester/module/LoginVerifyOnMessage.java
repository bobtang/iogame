package com.iohao.game.collect.external.tester.module;

import cn.hutool.core.util.RandomUtil;
import com.iohao.game.collect.hall.HallCmd;
import com.iohao.game.collect.proto.common.LoginVerify;
import com.iohao.game.collect.proto.common.UserInfo;
import com.iohao.little.game.action.skeleton.core.CmdKit;
import com.iohao.little.game.common.kit.ProtoKit;
import com.iohao.little.game.net.external.bootstrap.message.ExternalMessage;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 洛朱
 * @date 2022-01-29
 */
@Slf4j
public class LoginVerifyOnMessage implements OnMessage {

    @Override
    public Object process(ExternalMessage message, byte[] data) {
        UserInfo userInfo = ProtoKit.parseProtoByte(data, UserInfo.class);

        return userInfo;
    }

    @Override
    public int getCmdMerge() {
        return CmdKit.merge(HallCmd.cmd, HallCmd.loginVerify);
    }

    @Override
    public Object requestData() {
        LoginVerify loginVerify = new LoginVerify();
        loginVerify.jwt = "test";
        loginVerify.jwt = RandomUtil.randomInt(10000) + "";

        log.info("建立连接后 执行的方法 {}", loginVerify);
        return loginVerify;
    }

    public static LoginVerifyOnMessage me() {
        return Holder.ME;
    }

    /** 通过 JVM 的类加载机制, 保证只加载一次 (singleton) */
    private static class Holder {
        static final LoginVerifyOnMessage ME = new LoginVerifyOnMessage();
    }

}
