package com.iohao.little.game.net.external.bootstrap;

import com.iohao.little.game.net.external.bootstrap.message.ExternalRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 洛朱
 * @date 2022-01-10
 */
@Slf4j
public class ExternalHandler extends SimpleChannelInboundHandler<ExternalRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ExternalRequest request) {
        log.info("{}", request);

        byte[] content = request.getContent();
        log.info("content {}", new String(content));

        // TODO: 2022/1/11 转发到网关


    }
}
