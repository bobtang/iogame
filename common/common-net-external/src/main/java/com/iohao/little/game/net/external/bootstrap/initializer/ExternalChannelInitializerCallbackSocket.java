/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2022 double joker （262610965@qq.com） . All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License..
 */
package com.iohao.little.game.net.external.bootstrap.initializer;

import com.iohao.little.game.net.external.bootstrap.ExternalChannelInitializerCallback;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * ChannelPipeline 初始化 for tcp
 * <pre>
 *     给初始化的 channel 编排 handler
 * </pre>
 *
 * @author 洛朱
 * @date 2022-01-09
 */
@Setter
@Accessors(chain = true)
public class ExternalChannelInitializerCallbackSocket extends ChannelInitializer<SocketChannel> implements ExternalChannelInitializerCallback {

    ExternalChannelInitializerCallbackOption option;

    @Override
    public void initChannelPipeline(SocketChannel ch) {

        // 编排网关业务
        ChannelPipeline pipeline = ch.pipeline();

        // 数据包长度 = 长度域的值 + lengthFieldOffset + lengthFieldLength + lengthAdjustment。
        pipeline.addLast(new LengthFieldBasedFrameDecoder(option.packageMaxSize,
                // 长度字段的偏移量， 从 0 开始
                0,
                // 长度字段的长度, 使用的是 short ，占用2位；（消息头用的 byteBuf.writeShort 来记录长度的）
                2,
                // 长度调整值： 这里不做任何调整
                -2,
                // 跳过的初始字节数： 跳过0位; (跳过消息头的 0 位长度)
                0));

        // 编解码
//        pipeline.addLast("decoder", new ExternalDecoder());
//        pipeline.addLast("encoder", new ExternalEncoder());

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
