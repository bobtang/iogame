package com.iohao.game.collect.tank.mapstruct;

import com.iohao.game.collect.proto.tank.TankPlayer;
import com.iohao.game.collect.tank.room.TankPlayerEntity;
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
        TankPlayerEntity tankPlayerEntity = new TankPlayerEntity();
        tankPlayerEntity.setHp(1000);
        tankPlayerEntity.setId(200);
        tankPlayerEntity.setTeam(20);

        log.info("{}", tankPlayerEntity);
        TankPlayer tankPlayer =
                TankMapstruct.ME.convert(tankPlayerEntity);

        log.info("{}", tankPlayer);
    }
}