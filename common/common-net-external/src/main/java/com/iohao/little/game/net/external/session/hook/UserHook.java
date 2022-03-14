/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2022 double joker （262610965@qq.com） . All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.iohao.little.game.net.external.session.hook;

import com.iohao.little.game.net.client.kit.ChangeUserIdKit;
import io.netty.channel.Channel;

/**
 * UserHook 钩子接口。
 * 上线时、下线时会触发
 * <pre>
 *     实际上需要真正登录过，才会触发 ：into和quit 方法
 *     see {@link ChangeUserIdKit#changeUserId}
 *
 *     利用好该接口，可以把用户当前在线状态通知到逻辑服，比如使用 redis PubSub 之类的。
 * </pre>
 *
 * @author 洛朱
 * @date 2022-03-14
 */
public interface UserHook {
    /**
     * 用户进入
     * <pre>
     *     可以理解为上线
     * </pre>
     *
     * @param userId  userId
     * @param channel channel
     */
    void into(long userId, Channel channel);

    /**
     * 用户退出
     * <pre>
     *     可以理解为下线、离线通知等
     * </pre>
     *
     * @param userId  userId
     * @param channel channel
     */
    void quit(long userId, Channel channel);
}
