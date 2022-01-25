package com.iohao.game.collect.user.action;

import com.iohao.game.collect.proto.common.LoginVerify;
import com.iohao.game.collect.proto.common.UserInfo;
import com.iohao.little.game.action.skeleton.annotation.ActionController;
import com.iohao.little.game.action.skeleton.annotation.ActionMethod;
import com.iohao.little.game.action.skeleton.core.flow.FlowContext;
import com.iohao.little.game.net.client.kit.ChangeUserIdKit;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

/**
 * 登录相关
 * @author 洛朱
 * @date 2022-01-11
 */
@Slf4j
@ActionController(UserCmd.cmd)
public class LoginAction {

    final ConcurrentHashMap<String, Long> userMap = new ConcurrentHashMap<>();

    LongAdder userIdAdder = new LongAdder();

    /**
     * 登录验证
     *
     * @param loginVerify 登录验证pb
     * @param flowContext f
     * @return 用户信息
     */
    @ActionMethod(UserCmd.loginVerify)
    public UserInfo loginVerify(LoginVerify loginVerify, FlowContext flowContext) {
        log.info("loginVerify {} ", loginVerify);

        String jwt = loginVerify.jwt;

        Long newUserId = userMap.get(jwt);

        if (Objects.isNull(newUserId)) {
            userIdAdder.increment();
            userIdAdder.increment();

            newUserId = userIdAdder.longValue();
            userMap.put(jwt, newUserId);
        }

        UserInfo userInfo = new UserInfo();
        userInfo.id = newUserId;
        userInfo.name = jwt;

        boolean success = ChangeUserIdKit.changeUserId(flowContext, newUserId);

        if (!success) {
            // TODO: 2022/1/19 抛异常码
        }

        return userInfo;
    }


}
