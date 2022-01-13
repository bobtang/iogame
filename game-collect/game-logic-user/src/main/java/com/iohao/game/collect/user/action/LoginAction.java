package com.iohao.game.collect.user.action;

import com.iohao.game.collect.common.ActionCont;
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
@ActionController(ActionCont.UserModule.cmd)
public class LoginAction {

    final ConcurrentHashMap<String, Long> userMap = new ConcurrentHashMap<>();

    LongAdder userIdAdder = new LongAdder();

    @ActionMethod(ActionCont.UserModule.loginVerify)
    public UserInfo loginVerify(LoginVerify loginVerify) {

        String token = loginVerify.getJwt();

        Long userId = userMap.get(token);

        if (Objects.isNull(userId)) {
            userIdAdder.increment();

            userId = userIdAdder.longValue();
            userMap.put(token, userId);
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setId(userId);
        userInfo.setName(token);

        return userInfo;
    }
}
