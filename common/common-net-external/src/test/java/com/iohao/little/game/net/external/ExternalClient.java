package com.iohao.little.game.net.external;

import com.iohao.little.game.net.external.bootstrap.handler.codec.ExternalEncoder;
import com.iohao.little.game.net.external.bootstrap.message.ExternalMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 洛朱
 * @date 2022-01-10
 */
@Slf4j
public class ExternalClient {
    private String host = "127.0.0.1";
    private int port = 22022;
    private Bootstrap bootstrap;
    private static Channel channel;
    EventLoopGroup group = new NioEventLoopGroup();

    public ExternalClient() {
        this.init();
    }

    private void init() {
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        // 编排网关业务
                        ChannelPipeline pipeline = ch.pipeline();

                        // 编解码
                        pipeline.addLast("encoder", new ExternalEncoder());

                        pipeline.addLast(new ClientMessageHandler());
                    }
                });
    }

    public ChannelFuture connect() {
        synchronized (ExternalClient.this) {
            final ChannelFuture f = bootstrap.connect(this.host, this.port);
            f.addListener((ChannelFutureListener) future -> {
                if (future.isSuccess()) {
                    channel = future.channel();
                } else {
                    future.channel().pipeline().fireChannelInactive();
                }
            });
            return f;
        }
    }

    public static boolean send(ExternalMessage msg) {
        if (channel == null || !channel.isActive()) {
            log.debug("未连接服务器，发送失败");
            return false;
        }

        ChannelFuture f = channel.writeAndFlush(msg);
        return f.isSuccess();
    }

    public static void main(String[] args) throws InterruptedException {
        final ChannelFuture connect = new ExternalClient().connect();
        if (connect.sync().isSuccess()) {

            ExternalMessage request = new ExternalMessage();
            request.setCmdCode((short) 1);
            // 600 , 700
            request.setCmdMerge(39322300);
            request.setProtocolSwitch((byte) 2);

//            String contentStr = "hello, world!";
            String contentStr = "aa aa aa";
            request.setData(contentStr.getBytes());

            send(request);

//            send(MyMessage.StringMessage("测试自定义协议消息发送"));
        }
    }

    static class ClientMessageHandler extends SimpleChannelInboundHandler<ExternalMessage> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, ExternalMessage msg) throws Exception {
            System.out.println("connection server");
        }
    }
}


