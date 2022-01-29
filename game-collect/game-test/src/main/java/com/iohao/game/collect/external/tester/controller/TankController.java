package com.iohao.game.collect.external.tester.controller;

import com.iohao.game.collect.external.tester.module.tank.TankEnterRoomOnMessage;
import com.iohao.game.collect.external.tester.module.tank.TankMoveOnMessage;
import com.iohao.game.collect.external.tester.websocket.MyWebsocketClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 洛朱
 * @date 2022-01-29
 */
@Slf4j
@RestController
@RequestMapping("tank")
public class TankController {

    MyWebsocketClient client = MyWebsocketClient.me();

    @GetMapping("/enterRoom")
    public String enterRoom() {

        client.send(TankEnterRoomOnMessage.me());

        return "enterRoom";
    }

    @GetMapping("/tankMove")
    public String tankMove() {

        client.send(TankMoveOnMessage.me());

        return "tankMove";
    }
}
