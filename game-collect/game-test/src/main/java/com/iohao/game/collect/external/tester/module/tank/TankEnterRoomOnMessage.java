package com.iohao.game.collect.external.tester.module.tank;

import com.iohao.game.collect.external.tester.module.OnMessage;
import com.iohao.game.collect.proto.tank.TankEnterRoom;
import com.iohao.game.collect.tank.action.TankCmd;
import com.iohao.little.game.action.skeleton.core.CmdKit;
import com.iohao.little.game.common.kit.ProtoKit;
import com.iohao.little.game.net.external.bootstrap.message.ExternalMessage;

/**
 * @author 洛朱
 * @date 2022-01-29
 */
public class TankEnterRoomOnMessage implements OnMessage {
    @Override
    public Object process(ExternalMessage message, byte[] data) {
        TankEnterRoom tankEnterRoom = ProtoKit.parseProtoByte(data, TankEnterRoom.class);
        return tankEnterRoom;
    }

    @Override
    public int getCmdMerge() {
        return CmdKit.merge(TankCmd.cmd, TankCmd.enterRoom);
    }

    @Override
    public Object requestData() {
        TankEnterRoom tankEnterRoom = new TankEnterRoom();

        tankEnterRoom.roomId = 1;

        return tankEnterRoom;
    }

    public static TankEnterRoomOnMessage me() {
    	return Holder.ME;
    }

    /** 通过 JVM 的类加载机制, 保证只加载一次 (singleton) */
    private static class Holder {
        static final TankEnterRoomOnMessage ME = new TankEnterRoomOnMessage();
     }
}
