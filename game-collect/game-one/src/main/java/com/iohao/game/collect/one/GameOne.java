package com.iohao.game.collect.one;

import com.iohao.game.collect.GameLogicAll;
import com.iohao.game.collect.external.ExternalBoot;
import com.iohao.game.collect.gateway.GameGatewayBoot;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author 洛朱
 * @date 2022-01-13
 */
public class GameOne {
    public static void main(String[] args) {
        GameOne gameOne = new GameOne();

        // 启动网关
        new Thread(gameOne::gatewayServer).start();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                // 启动逻辑服
                gameOne.logicServer();

                // 启动对外服
                gameOne.externalServer();

            }
        }, 1000);


    }

    public void gatewayServer() {
        GameGatewayBoot gameGatewayBoot = new GameGatewayBoot();
        gameGatewayBoot.init();
    }

    public void externalServer() {
        ExternalBoot externalBoot = new ExternalBoot();

        externalBoot.init();
    }

    public void logicServer() {
        GameLogicAll gameLogicAll = new GameLogicAll();
        gameLogicAll.init();
    }
}