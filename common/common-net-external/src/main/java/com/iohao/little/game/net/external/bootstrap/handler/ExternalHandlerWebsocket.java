package com.iohao.little.game.net.external.bootstrap.handler;

import com.alipay.remoting.exception.RemotingException;
import com.alipay.remoting.rpc.RpcClient;
import com.iohao.little.game.action.skeleton.protocol.RequestMessage;
import com.iohao.little.game.net.external.bootstrap.ExternalServerKit;
import com.iohao.little.game.net.external.bootstrap.codec.ExternalDecoder;
import com.iohao.little.game.net.external.bootstrap.message.ExternalMessage;
import com.iohao.little.game.net.external.session.UserSession;
import com.iohao.little.game.net.external.session.UserSessionKit;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 洛朱
 * @date 2022-01-13
 */
@ChannelHandler.Sharable
@Slf4j
public class ExternalHandlerWebsocket extends SimpleChannelInboundHandler<Object> {

    private WebSocketServerHandshaker handshaker;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            handleHttpRequest(ctx, ((FullHttpRequest) msg));
        } else if (msg instanceof WebSocketFrame) {
            handlerWebSocketFrame(ctx, (WebSocketFrame) msg);
        }
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {

        if (!req.decoderResult().isSuccess() || (!"websocket".equals(req.headers().get("Upgrade")))) {
            sendHttpResponse(ctx, req,
                    new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }

        String location = "ws://" + req.headers().get(HttpHeaderNames.HOST) + req.uri();
        log.info("location: {}", location);

        // 创建握手工厂
        WebSocketServerHandshakerFactory wsFactory =
                new WebSocketServerHandshakerFactory(location, null, false);

        // 创建一个握手处理器
        handshaker = wsFactory.newHandshaker(req);

        if (handshaker == null) {
            // 不支持的版本
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            handshaker.handshake(ctx.channel(), req);
        }
    }

    final UserSession sessionManager = UserSession.me();

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
        // 跳过消息头 2字节
        in.skipBytes(2);

        ExternalMessage message = ExternalDecoder.decode(in);

        log.info("接收客户端消息 {}", message);

        // 转发到网关
        // 将 message 转换成 RequestMessage
        RequestMessage requestMessage = ExternalKit.convertRequestMessage(message);
        long userId = UserSession.me().getUserId(ctx.channel());
        requestMessage.setUserId(userId);

        String address = ExternalServerKit.address();
        RpcClient rpcClient = ExternalServerKit.rpcClient;

        try {
            log.info("external 请求 网关");
            rpcClient.oneway(address, requestMessage);
        } catch (RemotingException | InterruptedException e) {
            e.printStackTrace();
        }
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


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("创建 websocket 链接玩家 {}", 1);

        UserSessionKit.channelActive(ctx);
    }
}
