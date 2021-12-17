package com.iohao.little.game.net.message.common;

import com.iohao.little.game.action.skeleton.protocol.ResponseMessage;
import lombok.Data;

import java.io.Serializable;

/**
 * 广播消息
 */
@Data
public class BroadcastMessage implements Serializable {
    // 广播的消息
    ResponseMessage message;
}
