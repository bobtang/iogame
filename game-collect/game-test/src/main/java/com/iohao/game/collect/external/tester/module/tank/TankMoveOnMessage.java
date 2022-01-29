package com.iohao.game.collect.external.tester.module.tank;

import com.iohao.game.collect.external.tester.module.OnMessage;
import com.iohao.game.collect.proto.tank.TankLocation;
import com.iohao.game.collect.tank.action.TankCmd;
import com.iohao.little.game.action.skeleton.core.CmdKit;
import com.iohao.little.game.common.kit.ProtoKit;
import com.iohao.little.game.net.external.bootstrap.message.ExternalMessage;

/**
 * @author 洛朱
 * @date 2022-01-29
 */
public class TankMoveOnMessage implements OnMessage {
    @Override
    public Object process(ExternalMessage message, byte[] data) {
        TankLocation tankLocation = ProtoKit.parseProtoByte(data, TankLocation.class);

        return tankLocation;
    }

    @Override
    public int getCmdMerge() {
        return CmdKit.merge(TankCmd.cmd, TankCmd.tankMove);
    }

    @Override
    public Object requestData() {
        TankLocation tankLocation = new TankLocation();
        tankLocation.x = 10;
        tankLocation.y = 20;

        return tankLocation;
    }

    public static TankMoveOnMessage me() {
        return Holder.ME;
    }

    /** 通过 JVM 的类加载机制, 保证只加载一次 (singleton) */
    private static class Holder {
        static final TankMoveOnMessage ME = new TankMoveOnMessage();
    }
}
