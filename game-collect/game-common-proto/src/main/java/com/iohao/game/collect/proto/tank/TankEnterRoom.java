package com.iohao.game.collect.proto.tank;

import com.baidu.bjf.remoting.protobuf.annotation.EnableZigZap;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.ToString;

import java.util.List;

/**
 * @author 洛朱
 * @date 2022-01-15
 */
@ToString
@ProtobufClass(description = "进入(坦克)房间")
@EnableZigZap
public class TankEnterRoom {
    /** id */
    @Protobuf(description = "房间id")
    public long roomId;

    @Protobuf(description = "坦克 所属队伍 (加入哪个队伍)")
    public int team;

    @Protobuf(description = "玩家坦克列表")
    public List<TankPlayer> tankPlayerList;
}