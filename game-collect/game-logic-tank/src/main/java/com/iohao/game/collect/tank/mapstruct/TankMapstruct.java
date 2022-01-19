package com.iohao.game.collect.tank.mapstruct;

import com.iohao.game.collect.proto.tank.TankPlayer;
import com.iohao.game.collect.tank.room.TankPlayerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

/**
 * 实体映射工具
 * <pre>
 *     https://mapstruct.org/documentation/stable/reference/html/
 *
 *     bean 相互转换
 * </pre>
 *
 * @author 洛朱
 * @date 2022-01-15
 */
@Mapper
public interface TankMapstruct {
    TankMapstruct ME = Mappers.getMapper(TankMapstruct.class);

    TankPlayer convert(TankPlayerEntity tankPlayerEntity);

    List<TankPlayer> convertList(Collection<TankPlayerEntity> tankPlayerEntityList);
}
