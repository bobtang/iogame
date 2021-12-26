package com.iohao.little.game.widget.light.domain.event.example.user;

import com.iohao.little.game.widget.light.domain.event.message.DomainEventHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户登录就发送 email
 *
 * @author 洛朱
 * @date 2021-12-26
 */
@Slf4j
public class UserLoginEmailEventHandler implements DomainEventHandler<UserLogin> {
    @Override
    public void onEvent(UserLogin userLogin, boolean endOfBatch) {
        log.info("给登录用户发送 email ! {}", userLogin);
    }
}
