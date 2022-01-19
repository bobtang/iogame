package com.iohao.little.game.net.external.bootstrap.initializer;

import com.iohao.little.game.net.external.bootstrap.handler.socket.ExternalDecoder;
import com.iohao.little.game.net.external.bootstrap.handler.socket.ExternalEncoder;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.Setter;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author 洛朱
 * @date 2022-01-13
 */
@Setter
public class ExternalChannelInitializerCallbackOption {
    /** 心跳 */
    ChannelHandler idleHandler;
    /** 心跳时间 */
    int idleTime = 10;

    /** user processors of rpc server */
    Map<String, ChannelHandler> channelHandlerProcessors;
    /** 默认数据包最大 2MB */
    int packageMaxSize = 2 * 1024 * 1024;
    /** http 升级 websocket 协议地址 */
    String websocketPath = "/websocket";


    void codec(ChannelPipeline pipeline) {
        // 编解码
        pipeline.addLast("decoder", new ExternalDecoder());
        pipeline.addLast("encoder", new ExternalEncoder());

    }

    void idleHandler(ChannelPipeline pipeline) {
        // 心跳处理
        if (Objects.nonNull(this.idleHandler)) {
            pipeline.addLast("idleStateHandler",
                    new IdleStateHandler(0, 0, this.idleTime, TimeUnit.MILLISECONDS));
            pipeline.addLast("idleHandler", this.idleHandler);
        }
    }

    void channelHandlerProcessors(ChannelPipeline pipeline) {
        if (Objects.nonNull(this.channelHandlerProcessors)) {
            for (Map.Entry<String, ChannelHandler> entry : this.channelHandlerProcessors.entrySet()) {
                pipeline.addLast(entry.getKey(), entry.getValue());
            }
        }
    }
}
