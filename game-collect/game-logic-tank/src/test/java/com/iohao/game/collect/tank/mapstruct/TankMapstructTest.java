package com.iohao.game.collect.tank.mapstruct;

import com.iohao.game.collect.proto.tank.PlayerTank;
import com.iohao.game.collect.tank.room.TankPlayer;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author 洛朱
 * @date 2022-01-15
 */
@Slf4j
public class TankMapstructTest {
    @Test
    public void testCon() {
        TankPlayer tankPlayer = new TankPlayer();
        tankPlayer.setHp(1000);
        tankPlayer.setId(200);
        tankPlayer.setTeam(20);

        log.info("{}", tankPlayer);
        PlayerTank playerTank =
                TankMapstruct.ME.convert(tankPlayer);

        log.info("{}", playerTank);
    }
}