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
package com.iohao.little.game.net.external.session;

import com.iohao.little.game.net.external.session.hook.UserHook;
import com.iohao.little.game.net.external.session.hook.UserHookDefault;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jctools.maps.NonBlockingHashMapLong;

import java.net.InetSocketAddress;
import java.util.Objects;

/**
 * 用户 session 管理
 * <pre>
 *     channel
 * </pre>
 *
 * @author 洛朱
 * @date 2022-01-11
 */
@Slf4j
public class UserSession {
    final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    /**
     * key is 玩家 id
     * value is channelId
     */
    final NonBlockingHashMapLong<ChannelId> channelIdMap = new NonBlockingHashMapLong<>();

    /** UserHook 上线时、下线时会触发 */
    @Setter
    UserHook userHook = new UserHookDefault();

    /**
     * 添加 channel 关联
     *
     * @param userId  用户主键
     * @param channel channel
     */
    public void add(long userId, Channel channel) {

        if (userId == 0) {
            throw new RuntimeException("userId is 0");
        }

        channel.attr(UserSessionAttr.userId).setIfAbsent(userId);

        ChannelId channelId = channel.id();

        channelIdMap.putIfAbsent(userId, channelId);
        channelGroup.add(channel);
    }

    public boolean changeUserId(long userId, long newUserId) {
        Channel channel = this.getChannel(userId);

        if (!isActive(channel)) {
            return false;
        }

        ChannelId channelId = channelIdMap.remove(userId);

        if (Objects.isNull(channelId)) {
            return false;
        }

        channel.attr(UserSessionAttr.userId).set(newUserId);
        channel.attr(UserSessionAttr.verifyIdentity).set(true);

        channelIdMap.putIfAbsent(newUserId, channelId);

        // 上线通知
        userHookInto(newUserId, channel);

        return true;
    }

    public void remove(Channel channel) {
        if (Objects.isNull(channel)) {
            return;
        }

        long userId = this.getUserId(channel);


        if (userId != 0) {
            ChannelId remove = channelIdMap.remove(userId);

            if (Objects.nonNull(remove)) {
                // 离线通知
                userHookQuit(userId, channel);
            }
        }

        this.close(channel);
    }

    /**
     * 获取用户 channel
     *
     * @param userId 用户主键
     * @return channel
     */
    public Channel getChannel(long userId) {
        ChannelId channelId = channelIdMap.get(userId);
        if (Objects.isNull(channelId)) {
            return null;
        }

        Channel channel = channelGroup.find(channelId);
        if (Objects.isNull(channel)) {
            channelIdMap.remove(userId);
        }

        return channel;
    }


    /**
     * channel 中获取用户主键
     *
     * @param channel channel
     * @return 用户id
     */
    public long getUserId(Channel channel) {

        Long userId = channel.attr(UserSessionAttr.userId).get();

        if (Objects.nonNull(userId)) {
            return userId;
        }

        return 0;
    }

    /**
     * 获取玩家ip
     *
     * @param userId 玩家
     * @return ip地址
     */
    public String getIp(long userId) {
        Channel channel = this.getChannel(userId);
        if (isActive(channel)) {
            InetSocketAddress inetSocketAddress = (InetSocketAddress) channel.remoteAddress();
            return inetSocketAddress.getHostString();
        }

        return "";
    }

    public long countOnline() {
        return this.channelGroup.size();
    }

    /**
     * 全员消息广播
     * 消息类型 response
     *
     * @param msg 消息
     */
    public void broadcast(Object msg) {
        channelGroup.writeAndFlush(msg);
    }

    private boolean isActive(Channel channel) {
        return Objects.nonNull(channel) && channel.isActive();
    }

    private void close(Channel channel) {
        if (isActive(channel)) {
            channel.close();
        }

        channelGroup.remove(channel);
    }

    /**
     * 上线通知。注意：只有进行身份验证通过的，才会触发此方法
     *
     * @param userId  userId
     * @param channel channel
     */
    private void userHookInto(long userId, Channel channel) {
        if (Objects.isNull(userHook)
                || !channel.hasAttr(UserSessionAttr.verifyIdentity)
                || !channel.attr(UserSessionAttr.verifyIdentity).get()
        ) {
            return;
        }

        this.userHook.into(userId, channel);
    }

    /**
     * 离线通知。注意：只有进行身份验证通过的，才会触发此方法
     *
     * @param userId  userId
     * @param channel channel
     */
    private void userHookQuit(long userId, Channel channel) {
        if (Objects.isNull(userHook)
                || !channel.hasAttr(UserSessionAttr.verifyIdentity)
                || !channel.attr(UserSessionAttr.verifyIdentity).get()
        ) {
            return;
        }

        this.userHook.quit(userId, channel);
    }

    public static UserSession me() {
        return Holder.ME;
    }

    /** 通过 JVM 的类加载机制, 保证只加载一次 (singleton) */
    private static class Holder {
        static final UserSession ME = new UserSession();
    }
}
