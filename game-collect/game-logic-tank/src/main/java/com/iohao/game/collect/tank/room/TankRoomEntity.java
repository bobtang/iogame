package com.iohao.game.collect.tank.room;

import com.iohao.game.collect.common.FrameKit;
import com.iohao.game.collect.common.room.AbstractRoom;
import com.iohao.game.collect.proto.tank.TankLocation;
import com.iohao.game.collect.tank.config.TankKit;
import com.iohao.little.game.action.skeleton.core.flow.FlowContext;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 坦克 房间
 *
 * @author 洛朱
 * @date 2022-01-14
 */
@Data
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TankRoomEntity extends AbstractRoom {
    @Serial
    private static final long serialVersionUID = 5354718988786951601L;
    /** 帧同步 id */
    int frameId;

    /** 房间最大帧数 */
    private final int maxFrameId;

    public TankRoomEntity(int maxFrameId) {
        this.maxFrameId = maxFrameId;
    }

    public TankRoomEntity() {
        this.maxFrameId = 60 * FrameKit.MINUTE;
    }

    /**
     * <pre>
     *     key : frame
     * </pre>
     */
    Map<Integer, CopyOnWriteArrayList<TankLocation>> moveMap = new ConcurrentHashMap<>();

    /**
     * 广播
     *
     * @param flowContext  flow 上下文
     * @param methodResult 广播的业务数据
     */
    public void broadcast(FlowContext flowContext, Object methodResult) {
        flowContext.setMethodResult(methodResult);

        Collection<Long> listPlayerId = this.listPlayerId();
        TankKit.boltClientProxy.broadcast(flowContext, listPlayerId);
    }
}
