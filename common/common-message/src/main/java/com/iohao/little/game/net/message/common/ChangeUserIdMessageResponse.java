package com.iohao.little.game.net.message.common;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 变更 userId 响应
 *
 * @author 洛朱
 * @date 2022-01-19
 */
@Data
public class ChangeUserIdMessageResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = -3776596417948970990L;
    /** true: userId 变更成功 */
    boolean success;
    /** 变更后的 userId */
    long userNewId;
}
