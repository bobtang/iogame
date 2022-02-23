package com.iohao.little.game.net.external.bootstrap.initializer;

import com.iohao.little.game.net.external.bootstrap.ExternalChannelInitializerCallback;
import com.iohao.little.game.net.external.bootstrap.handler.codec.ExternalCodecWebsocketProto;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolConfig;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * ChannelPipeline 初始化 for Websocket
 * <pre>
 *     给初始化的 channel 编排 handler
 * </pre>
 *
 * @author 洛朱
 * @date 2022-01-13
 */
@Setter
@Accessors(chain = true)
public class ExternalChannelInitializerCallbackWebsocket extends ChannelInitializer<SocketChannel> implements ExternalChannelInitializerCallback {

    ExternalChannelInitializerCallbackOption option;

    @Override
    public void initChannelPipeline(SocketChannel ch) {
        // 业务编排
        ChannelPipeline pipeline = ch.pipeline();

        /*
         * 将请求和应答消息解码为HTTP消息
         * 将字节码解码为 HttpRequest,HttpContent和LastHttpContent.
         * 并将 HttpRequest, HttpContent和LastHttpContent 编码为字节码
         */
        pipeline.addLast("http-codec", new HttpServerCodec());

        /*
         * 将一个 HttpMessage 和跟随它的多个 HttpContent 聚合为
         * 单个 FullHTTPRequest 或者 FullHTTPResponse(取决于它是被用来处理请求还是响应).
         *
         * 安装了这个之后, ChannelPipeline 中的下一个 ChannelHandler 将只会接收
         * 到完整的 Http 请求或响应
         */
        pipeline.addLast("aggregator", new HttpObjectAggregator(65536));

        // WebSocket 数据压缩
        pipeline.addLast("compression", new WebSocketServerCompressionHandler());

        /*
         * 处理 websocket 的解码器
         * 1 协议包长度限制
         * 2 验证协议url。
         *
         * 按照 WebSocket 规范的要求, 处理 :
         * 1 WebSocket 升级握手, 验证GET的请求升级
         * 2 PingWebSocketFrame
         * 3 PongWebSocketFrame
         * 4 CloseWebSocketFrame
         *
         * 移除 HTTPRequest HTTPResponse
         */
        WebSocketServerProtocolConfig config = WebSocketServerProtocolConfig.newBuilder()
                // 验证 URL
                .websocketPath(option.websocketPath)
                // 默认数据包大小
                .maxFramePayloadLength(option.packageMaxSize)
                .checkStartsWith(true)
                // 开启 WebSocket 扩展
                .allowExtensions(true)
                .build();

        pipeline.addLast("WebSocketServerProtocolHandler", new WebSocketServerProtocolHandler(config));

        // websocket 编解码
        pipeline.addLast("codec", new ExternalCodecWebsocketProto());
//        pipeline.addLast("codec", new ExternalCodecWebsocketProtoForCSharp());

        // 心跳
        option.idleHandler(pipeline);

        // 业务 handler
        option.channelHandlerProcessors(pipeline);
    }

    @Override
    protected void initChannel(SocketChannel ch) {
        this.initChannelPipeline(ch);
    }
}
