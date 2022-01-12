package com.iohao.game.collect.external.client;

import com.iohao.game.collect.common.ActionCont;
import com.iohao.game.collect.common.GameConfig;
import com.iohao.game.collect.proto.LoginVerify;
import com.iohao.little.game.net.external.bootstrap.ExternalEncoder;
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
public class ExternalTestClient {
    private String host = GameConfig.externalIp;
    private int port = GameConfig.externalPort;

    private Bootstrap bootstrap;
    private static Channel channel;
    EventLoopGroup group = new NioEventLoopGroup();

    public static void main(String[] args) throws InterruptedException {
        final ChannelFuture connect = new ExternalTestClient().connect();
        if (connect.sync().isSuccess()) {

            ExternalMessage request = getExternalMessage();

            // 发送
            send(request);

        }
    }

    public ExternalTestClient() {
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
        synchronized (ExternalTestClient.this) {
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
        loginVerify.setToken("abc");
        request.setData(loginVerify);

        System.out.println(request);
        return request;
    }

    static class ClientMessageHandler extends SimpleChannelInboundHandler<ExternalMessage> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, ExternalMessage msg) throws Exception {
            System.out.println("connection server");
        }
    }
}


