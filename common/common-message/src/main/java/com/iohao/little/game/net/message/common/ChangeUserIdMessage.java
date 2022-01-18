package com.iohao.little.game.net.message.common;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author 洛朱
 * @date 2022-01-18
 */
@Data
public class ChangeUserIdMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = -7385687951893601229L;
    /** user 临时的 userId */
    long userId;
    /** user 新的 userId, 一般从用户数据库获取 */
    long newUserId;
}
