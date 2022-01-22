package com.iohao.game.collect.tank.action;

import com.iohao.game.collect.common.room.GameFlowService;
import com.iohao.game.collect.common.room.RoomService;
import com.iohao.game.collect.proto.tank.TankBulletConfigRes;
import com.iohao.game.collect.proto.tank.TankEnterRoom;
import com.iohao.game.collect.proto.tank.TankMove;
import com.iohao.game.collect.tank.mapstruct.TankMapstruct;
import com.iohao.game.collect.tank.room.TankPlayerEntity;
import com.iohao.game.collect.tank.room.TankRoomEntity;
import com.iohao.game.collect.tank.room.flow.*;
import com.iohao.game.collect.tank.service.TankConfigService;
import com.iohao.little.game.action.skeleton.annotation.ActionController;
import com.iohao.little.game.action.skeleton.annotation.ActionMethod;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Objects;

/**
 * 井架
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

    static {
        gameFlowService.setRoomRuleInfoCustom(new TankRoomRuleInfoCustom());
        gameFlowService.setRoomGameStartCustom(new TankRoomGameStartCustom());
        gameFlowService.setRoomPlayerCreateCustom(new TankRoomPlayerCreateCustom());
        gameFlowService.setRoomCreateCustom(new TankRoomCreateCustom());
        gameFlowService.setRoomEnterCustom(new TankRoomEnterCustom());
    }

    /**
     * 坦克移动
     *
     * @param userId   userId
     * @param tankMove move
     * @return move
     */
    @ActionMethod(TankCmd.tankMove)
    public TankMove tankMove(long userId, TankMove tankMove) {
        tankMove.userId = userId;

        TankRoomEntity room = roomService.getRoomByUserId(userId);

        TankPlayerEntity player = room.getPlayerById(userId);

        player.setTankMove(tankMove);

        return tankMove;
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
     * 进入房间
     *
     * @param userId    userId
     * @param enterRoom enterRoom
     * @return TankEnterRoom
     */
    @ActionMethod(TankCmd.enterRoom)
    public TankEnterRoom enterRoom(long userId, TankEnterRoom enterRoom) {
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

        TankPlayerEntity player = room.getPlayerById(userId);

        // 如果检查是否在房间内
        if (Objects.isNull(player)) {
            // 如果不在房间内先加入房间
            player = gameFlowService.getRoomPlayerCreateCustom().createPlayer();
            player.setId(userId);
            player.setRoomId(roomId);

            roomService.addPlayer(room, player);
        }

        // 进入房间
        Collection<TankPlayerEntity> players = room.listPlayer();
        enterRoom.tankPlayerList = TankMapstruct.ME.convertList(players);


        return enterRoom;
    }

}
