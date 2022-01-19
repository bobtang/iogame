package com.iohao.little.game.net.external.bootstrap.handler.websocket.old;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 洛朱
 * @date 2022-01-19
 */
@Slf4j
@ChannelHandler.Sharable
public class WebSocketHandShakeHandle extends SimpleChannelInboundHandler<Object> {
    /**
     * <pre>
     *     接收客户端发送的消息 channel 通道 Read
     *     从通道中读取数据，也就是服务端接收客户端发来的数据。
     *     这个数据在不进行解码时它是ByteBuf类型的
     * </pre>
     *
     * @param ctx ctx
     * @param msg msg
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
        // 传统的HTTP接入
        if (msg instanceof FullHttpRequest) {
            try {
                // websocket 握手方法
                handleHttpRequest(ctx, ((FullHttpRequest) msg));
                ctx.fireChannelReadComplete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 移除自己
        ctx.pipeline().remove(this.getClass());
    }


    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
        // 如果HTTP解码失败，返回 HTTP 异常
        if (!req.decoderResult().isSuccess() || (!"websocket".equals(req.headers().get("Upgrade")))) {
            sendHttpResponse(ctx, req,
                    new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }

        String webSocketURL = "ws://" + req.headers().get(HttpHeaderNames.HOST) + req.uri();
        if (log.isDebugEnabled()) {
            log.debug("webSocketURL: {}", webSocketURL);
        }

        // 创建握手工厂
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
                webSocketURL, null, false);

        // 创建一个握手处理器
        WebSocketServerHandshaker handshaker = wsFactory.newHandshaker(req);

        if (handshaker == null) {
            log.info("不支持的连接类型");
            // 不支持的版本
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            handshaker.handshake(ctx.channel(), req);
        }
    }

    private void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, DefaultFullHttpResponse res) {
        // 返回应答给客户端
        if (res.status().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
        }

        // 如果是非Keep-Alive，关闭连接
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!HttpUtil.isKeepAlive(req) || res.status().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    public static WebSocketHandShakeHandle me() {
        return Holder.ME;
    }

    /** 通过 JVM 的类加载机制, 保证只加载一次 (singleton) */
    private static class Holder {
        static final WebSocketHandShakeHandle ME = new WebSocketHandShakeHandle();
    }
}
