package com.iohao.game.collect.user.action;

import com.iohao.game.collect.proto.LoginVerify;
import com.iohao.game.collect.proto.UserInfo;
import com.iohao.little.game.action.skeleton.annotation.ActionController;
import com.iohao.little.game.action.skeleton.annotation.ActionMethod;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author 洛朱
 * @date 2022-01-11
 */
@ActionController(UserCmd.cmd)
public class LoginAction {

    final ConcurrentHashMap<String, Long> userMap = new ConcurrentHashMap<>();

    LongAdder userIdAdder = new LongAdder();

    @ActionMethod(UserCmd.loginVerify)
    public UserInfo loginVerify(LoginVerify loginVerify) {

        String jwt = loginVerify.jwt;

        Long userId = userMap.get(jwt);

        if (Objects.isNull(userId)) {
            userIdAdder.increment();

            userId = userIdAdder.longValue();
            userMap.put(jwt, userId);
        }

        UserInfo userInfo = new UserInfo();
        userInfo.id = userId;
        userInfo.name = jwt;

        return userInfo;
    }
}
