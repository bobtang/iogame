package com.iohao.game.collect.proto.tank;

import com.baidu.bjf.remoting.protobuf.annotation.EnableZigZap;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;

import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author 洛朱
 * @date 2022-01-15
 */
@ToString
@Setter
@ProtobufClass(description = "房间广播 定时 帧")
@EnableZigZap
public class TankRoomBroadcastRes {
    @Protobuf(description = "玩家坦克列表")
    public List<TankPlayer> tankPlayers;
}
