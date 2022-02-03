package com.iohao.game.collect.tank.action;

import com.iohao.game.collect.common.room.GameFlowService;
import com.iohao.game.collect.common.room.RoomService;
import com.iohao.game.collect.proto.tank.TankBullet;
import com.iohao.game.collect.proto.tank.TankBulletConfigRes;
import com.iohao.game.collect.proto.tank.TankEnterRoom;
import com.iohao.game.collect.proto.tank.TankLocation;
import com.iohao.game.collect.tank.TankCmd;
import com.iohao.game.collect.tank.mapstruct.TankMapstruct;
import com.iohao.game.collect.tank.room.TankPlayerEntity;
import com.iohao.game.collect.tank.room.TankRoomEntity;
import com.iohao.game.collect.tank.room.flow.*;
import com.iohao.game.collect.tank.service.TankConfigService;
import com.iohao.little.game.action.skeleton.annotation.ActionController;
import com.iohao.little.game.action.skeleton.annotation.ActionMethod;
import com.iohao.little.game.action.skeleton.core.exception.MsgException;
import com.iohao.little.game.action.skeleton.core.flow.FlowContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.LongAdder;

/**
 * 坦克相关
 *
 * @author 洛朱
 * @date 2022-01-14
 */
@Slf4j
@ActionController(TankCmd.cmd)
public class TankAction {
    /*=============== 暂时不加 spring 进来， 后面在加入 spring ===============*/
    static GameFlowService gameFlowService = GameFlowService.me();
    RoomService roomService = RoomService.me();
    // TODO: 2022/1/14 开发阶段，只用一个房间
    public static long tempRoomId = 10000;

    static final LongAdder bulletShootIdAdder = new LongAdder();

    static {
        gameFlowService.setRoomRuleInfoCustom(new TankRoomRuleInfoCustom());
        gameFlowService.setRoomGameStartCustom(new TankRoomGameStartCustom());
        gameFlowService.setRoomPlayerCreateCustom(new TankRoomPlayerCreateCustom());
        gameFlowService.setRoomCreateCustom(new TankRoomCreateCustom());
        gameFlowService.setRoomEnterCustom(new TankRoomEnterCustom());
    }


//    @ActionMethod(TankCmd.hurt)
//    public void hurt(FlowContext flowContext) {
//
//    }
//
//    /**
//     * 子弹消失（死亡）
//     *
//     * @param flowContext flowContext
//     */
//    @ActionMethod(TankCmd.bulletDead)
//    public void bulletDead(FlowContext flowContext) {
//        long userId = flowContext.getUserId();
//    }

    /**
     * 坦克射击(发射子弹)
     *
     * @param flowContext flowContext
     * @param tankBullet  tankBullet
     */
    @ActionMethod(TankCmd.shooting)
    public void shooting(FlowContext flowContext, TankBullet tankBullet) throws MsgException {
        long userId = flowContext.getUserId();

        // 广播这颗子弹的消息
        TankRoomEntity room = roomService.getRoomByUserId(userId);

        TankPlayerEntity player = room.getPlayerById(userId);

        player.shooting(tankBullet);

        bulletShootIdAdder.increment();
        tankBullet.shootId = bulletShootIdAdder.longValue();


        Map<Integer, Integer> tankBulletMap = player.getTankBulletMap();


        room.broadcast(flowContext, tankBullet);
    }

    /**
     * 坦克移动
     *
     * @param flowContext  flowContext
     * @param tankLocation tankLocation
     */
    @ActionMethod(TankCmd.tankMove)
    public void tankMove(FlowContext flowContext, TankLocation tankLocation) {
        long userId = flowContext.getUserId();
        tankLocation.playerId = userId;

        TankRoomEntity room = roomService.getRoomByUserId(userId);

        TankPlayerEntity player = room.getPlayerById(userId);

        player.setTankLocation(tankLocation);

        //  广播坦克移动
        room.broadcast(flowContext, tankLocation);
    }

    /**
     * 子弹的配置
     *
     * @return 子弹的配置列表
     * @apiNote 子弹配置
     */
    @ActionMethod(TankCmd.getTankBulletConfigRes)
    public TankBulletConfigRes getTankBulletConfigRes() {
        // 子弹配置
        return TankConfigService.me().getTankBulletConfigRes();
    }

    /**
     * 玩家进入房间
     *
     * @param flowContext flowContext
     * @param enterRoom   enterRoom
     * @return TankEnterRoom
     */
    @ActionMethod(TankCmd.enterRoom)
    public TankEnterRoom enterRoom(FlowContext flowContext, TankEnterRoom enterRoom) {

        // TODO: 2022/1/14 开发阶段，只用一个房间
        enterRoom.roomId = tempRoomId;

        long roomId = enterRoom.roomId;

        // 房间
        TankRoomEntity room = roomService.getRoom(roomId);

        // 房间不存在，创建一个房间
        if (Objects.isNull(room)) {
            room = gameFlowService.getRoomCreateCustom().createRoom(null);
            // TODO: 2022/1/14 开发阶段，只用一个房间
            room.setRoomId(tempRoomId);

            roomService.addRoom(room);
        }

        long userId = flowContext.getUserId();
        TankPlayerEntity player = room.getPlayerById(userId);

        // 如果检查是否在房间内
        if (Objects.isNull(player)) {
            // 如果不在房间内先加入房间
            player = gameFlowService.getRoomPlayerCreateCustom().createPlayer();
            player.setUserId(userId);
            player.setRoomId(roomId);

            roomService.addPlayer(room, player);
        }

        // 进入房间
        Collection<TankPlayerEntity> players = room.listPlayer();
        enterRoom.tankPlayerList = TankMapstruct.ME.convertList(players);


        return enterRoom;
    }

}
