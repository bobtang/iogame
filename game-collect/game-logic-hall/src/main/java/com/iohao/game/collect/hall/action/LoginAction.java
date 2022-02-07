package com.iohao.game.collect.hall.action;

import com.iohao.game.collect.hall.HallCmd;
import com.iohao.game.collect.proto.common.LoginVerify;
import com.iohao.game.collect.proto.common.UserInfo;
import com.iohao.little.game.action.skeleton.annotation.ActionController;
import com.iohao.little.game.action.skeleton.annotation.ActionMethod;
import com.iohao.little.game.action.skeleton.core.flow.FlowContext;
import com.iohao.little.game.net.client.kit.ChangeUserIdKit;
import lombok.extern.slf4j.Slf4j;
import org.jctools.maps.NonBlockingHashMap;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.LongAdder;

/**
 * 登录相关
 *
 * @author 洛朱
 * @date 2022-02-02
 */
@Slf4j
@ActionController(HallCmd.cmd)
public class LoginAction {

    final Map<String, Long> userMap = new NonBlockingHashMap<>();

    LongAdder userIdAdder = new LongAdder();

    /**
     * 登录验证
     *
     * @param loginVerify 登录验证pb
     * @param flowContext f
     * @return 用户信息
     */
    @ActionMethod(HallCmd.loginVerify)
    public UserInfo loginVerify(LoginVerify loginVerify, FlowContext flowContext) {
        log.info("loginVerify {} ", loginVerify);

        String jwt = loginVerify.jwt;

        // TODO: 2022/2/6 从数据库中
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
