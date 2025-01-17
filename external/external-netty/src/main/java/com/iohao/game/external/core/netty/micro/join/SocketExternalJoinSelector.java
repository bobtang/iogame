/*
 * ioGame
 * Copyright (C) 2021 - 2023  渔民小镇 （262610965@qq.com、luoyizhu@gmail.com） . All Rights Reserved.
 * # iohao.com . 渔民小镇
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package com.iohao.game.external.core.netty.micro.join;

import com.iohao.game.external.core.ExternalCoreSetting;
import com.iohao.game.external.core.hook.internal.IdleProcessSetting;
import com.iohao.game.external.core.micro.MicroBootstrap;
import com.iohao.game.external.core.micro.join.ExternalJoinSelector;
import com.iohao.game.external.core.netty.DefaultExternalCoreSetting;
import com.iohao.game.external.core.netty.hook.DefaultSocketIdleHook;
import com.iohao.game.external.core.netty.micro.SocketMicroBootstrap;
import com.iohao.game.external.core.netty.session.SocketUserSessions;
import com.iohao.game.external.core.session.UserSessions;

import java.util.Objects;

/**
 * @author 渔民小镇
 * @date 2023-05-31
 */
abstract class SocketExternalJoinSelector implements ExternalJoinSelector {
    @Override
    public void defaultSetting(ExternalCoreSetting coreSetting) {
        DefaultExternalCoreSetting setting = (DefaultExternalCoreSetting) coreSetting;
        // microBootstrap；如果开发者没有手动赋值，则根据当前连接方式生成
        MicroBootstrap microBootstrap = setting.getMicroBootstrap();
        if (Objects.isNull(microBootstrap)) {
            setting.setMicroBootstrap(new SocketMicroBootstrap());
        }

        // UserSessions 管理器；如果开发者没有手动赋值，则根据当前连接方式生成
        UserSessions<?, ?> userSessions = setting.getUserSessions();
        if (Objects.isNull(userSessions)) {
            setting.setUserSessions(new SocketUserSessions());
        }

        // IdleHook 心跳钩子；长连接方式开启了心跳，强制给一个心跳钩子
        IdleProcessSetting idleProcessSetting = setting.getIdleProcessSetting();
        if (Objects.nonNull(idleProcessSetting)) {
            if (Objects.isNull(idleProcessSetting.getIdleHook())) {
                idleProcessSetting.setIdleHook(new DefaultSocketIdleHook());
            }
        }
    }
}
