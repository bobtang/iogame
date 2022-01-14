package com.iohao.game.collect.tank.action;

import com.iohao.game.collect.common.room.GameFlowService;
import com.iohao.game.collect.tank.room.flow.*;
import com.iohao.little.game.action.skeleton.annotation.ActionController;
import com.iohao.little.game.action.skeleton.annotation.ActionMethod;

/**
 * @author 洛朱
 * @date 2022-01-14
 */
@ActionController(TankCmd.cmd)
public class TankAction {

    GameFlowService gameFlowService;

    public TankAction() {
        gameFlowService = new GameFlowService();
        gameFlowService.setRoomRuleInfoCustom(new TankRoomRuleInfoCustom());
        gameFlowService.setRoomGameStartCustom(new TankRoomGameStartCustom());
        gameFlowService.setRoomPlayerCreateCustom(new TankRoomPlayerCreateCustom());
        gameFlowService.setRoomCreateCustom(new TankRoomCreateCustom());
        gameFlowService.setRoomEnterCustom(new TankRoomEnterCustom());

    }

    @ActionMethod(TankCmd.enterRoom)
    public void enterRoom(long userId) {

        // 玩家进入房间

        // 如果检查是否在房间内


        // 如果不在房间内先加入房间

        // 进入房间
    }


}
