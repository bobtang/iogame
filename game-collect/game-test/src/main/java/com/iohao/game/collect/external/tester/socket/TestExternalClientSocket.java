package com.iohao.game.collect.external.tester.socket;

import com.iohao.game.collect.common.ActionCont;
import com.iohao.game.collect.common.GameConfig;
import com.iohao.game.collect.proto.LoginVerify;
import com.iohao.little.game.net.external.bootstrap.codec.ExternalDecoder;
import com.iohao.little.game.net.external.bootstrap.codec.ExternalEncoder;
import com.iohao.little.game.net.external.bootstrap.message.ExternalMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 洛朱
 * @date 2022-01-10
 */
@Slf4j
public class TestExternalClientSocket {
    private String host = GameConfig.externalIp;
    private int port = GameConfig.externalPort;

    private Bootstrap bootstrap;
    private static Channel channel;
    EventLoopGroup group = new NioEventLoopGroup();

    public static void main(String[] args) throws InterruptedException {
        final ChannelFuture connect = new TestExternalClientSocket().connect();
        if (connect.sync().isSuccess()) {

            ExternalMessage request = getExternalMessage();

            // 发送
            for (int i = 0; i < 6; i++) {
                send(request);
            }

        }
    }

    public TestExternalClientSocket() {
        this.init();
    }

    static final int MB = 1024 * 1024;
    // 数据包最大2MB
    static int PACKAGE_MAX_SIZE = 2 * MB;

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

                        // 数据包长度 = 长度域的值 + lengthFieldOffset + lengthFieldLength + lengthAdjustment。
                        pipeline.addLast(new LengthFieldBasedFrameDecoder(PACKAGE_MAX_SIZE,
                                // 长度字段的偏移量， 从 0 开始
                                0,
                                // 长度字段的长度, 使用的是 short ，占用2位；（消息头用的 byteBuf.writeShort 来记录长度的）
                                2,
                                // 长度调整值： 这里不做任何调整
                                -2,
                                // 跳过的初始字节数： 跳过2位; (跳过消息头的 2 位长度)
                                2));


                        // 编解码
                        pipeline.addLast("decoder", new ExternalDecoder());
                        pipeline.addLast("encoder", new ExternalEncoder());

                        pipeline.addLast(new ClientMessageHandler());
                    }
                });
    }

    public ChannelFuture connect() {
        synchronized (TestExternalClientSocket.this) {
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


    private static ExternalMessage getExternalMessage() {
        ExternalMessage request = new ExternalMessage();
        request.setCmdCode((short) 1);

        // 路由
        int cmd = ActionCont.UserModule.cmd;
        int subCmd = ActionCont.UserModule.loginVerify;
        request.setCmdMerge(cmd, subCmd);

        // 业务数据
        LoginVerify loginVerify = new LoginVerify();
        loginVerify.setJwt("abc");
        request.setData(loginVerify);

        System.out.println(request);
        return request;
    }

    static class ClientMessageHandler extends SimpleChannelInboundHandler<ExternalMessage> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, ExternalMessage msg) {
            log.info("connection server {}", msg);

        }
    }
}


