package com.iohao.little.game.net.external.session;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

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
public class UserSessionManager {
    final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    /**
     * key is 玩家 id
     * value is channelId
     */
    final ConcurrentHashMap<Long, ChannelId> channelIdMap = new ConcurrentHashMap<>();


    /**
     * 添加 channel 关联
     *
     * @param userId  用户主键
     * @param channel channel
     */
    public void add(long userId, Channel channel) {
        channel.attr(UserSessionAttr.userId).setIfAbsent(userId);

        ChannelId channelId = channel.id();
        channelIdMap.putIfAbsent(userId, channelId);
        channelGroup.add(channel);
    }

    public void remove(Channel channel) {
        if (Objects.isNull(channel)) {
            return;
        }

        log.info("玩家退出 -- tcpSession. channel: {}", channel);

        long playerId = this.getUserId(channel);
        if (playerId == 0) {
            channelIdMap.remove(playerId);
            // TODO: 2022/1/11  离线通知
            //  #offline(userId);
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
        return Objects.isNull(userId) ? 0 : userId;
    }

    /**
     * 获取玩家ip
     *
     * @param playerId 玩家
     * @return ip地址
     */
    public String getIp(long playerId) {
        Channel channel = this.getChannel(playerId);
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

    UserSessionManager() {

    }

    public static UserSessionManager me() {
        return Holder.ME;
    }

    /** 通过 JVM 的类加载机制, 保证只加载一次 (singleton) */
    private static class Holder {
        static final UserSessionManager ME = new UserSessionManager();
    }
}
