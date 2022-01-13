package com.iohao.game.collect.external.tester.websocket;

import com.iohao.game.collect.proto.LoginVerify;
import com.iohao.little.game.net.external.bootstrap.ExternalCont;
import com.iohao.little.game.net.external.bootstrap.codec.ExternalDecoder;
import com.iohao.little.game.net.external.bootstrap.codec.ExternalEncoder;
import com.iohao.little.game.net.external.bootstrap.message.ExternalMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.LongAdder;

/**
 * @author 洛朱
 * @date 2022-01-13
 */
@ChannelHandler.Sharable
@Slf4j
public class TestExternalHandlerWebsocket extends SimpleChannelInboundHandler<Object> {

    private WebSocketServerHandshaker handshaker;
    LongAdder id = new LongAdder();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            handleHttpRequest(ctx, ((FullHttpRequest) msg));
        } else if (msg instanceof WebSocketFrame) {
            handlerWebSocketFrame(ctx, (WebSocketFrame) msg);
        }
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {

        // !"websocket".equals(req.headers().get(HttpHeaderNames.UPGRADE)
        if (!req.decoderResult().isSuccess() || (!"websocket".equals(req.headers().get("Upgrade")))) {
            sendHttpResponse(ctx, req,
                    new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }

        var url = "ws://localhost:10088";

        String location = "ws://" + req.headers().get(HttpHeaderNames.HOST) + req.uri();
        log.info("temp: {}", location);

        WebSocketServerHandshakerFactory wsFactory =
                new WebSocketServerHandshakerFactory(location, null, false);

        handshaker = wsFactory.newHandshaker(req);

        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            handshaker.handshake(ctx.channel(), req);
        }
    }

    private void handlerWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        // 判断是否关闭链路的指令
        if (frame instanceof CloseWebSocketFrame) {
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            return;
        }

        // 判断是否ping消息
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        } else if (frame instanceof TextWebSocketFrame) {
            log.info("[WsGatewayServerHandler]-[handlerWebSocketFrame] 主动断开连接, 因为错误的通讯格式 TextWebSocketFrame ， ip:port [{}]", ctx.channel().remoteAddress());
            // TODO: 2022/1/13 close ctx
            ctx.close();
            return;
        }

        BinaryWebSocketFrame binary = (BinaryWebSocketFrame) frame;

        ByteBuf in = binary.content();
        // 消息头
        in.skipBytes(2);

        ExternalMessage message = ExternalDecoder.decode(in);

        log.info("接收客户端消息 {}", message);

        LoginVerify loginVerify = new LoginVerify();
        loginVerify.setJwt("jwt:" + id.toString());
        message.setData(loginVerify);

        int headLen = ExternalCont.HEADER_LEN + message.getDataLength();
        ByteBuf byteBuf = Unpooled.buffer(headLen);
        ExternalEncoder.encode(message, byteBuf);

        BinaryWebSocketFrame tws = new BinaryWebSocketFrame(byteBuf);

        ctx.writeAndFlush(tws);
    }


    private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, DefaultFullHttpResponse res) {
        // 返回应答给客户端
        if (res.status().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
        }
        // 如果是非Keep-Alive，关闭连接
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (res.status().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("玩家下线");
    }
}
