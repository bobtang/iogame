package com.iohao.little.game.net.external.bootstrap.message;

import com.iohao.little.game.action.skeleton.core.CmdKit;
import com.iohao.little.game.common.kit.ProtoKit;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * 对外数据的协议
 *
 * @author 洛朱
 * @date 2022-01-10
 */
@FieldDefaults(level = AccessLevel.PROTECTED)
@Data
public class ExternalMessage {
    /** 请求命令类型: 0 心跳，1 业务 */
    short cmdCode;
    /** 协议开关，用于一些协议级别的开关控制，比如 安全加密校验等。 : 0 不校验 */
    byte protocolSwitch;
    /** 业务路由（高16为主, 低16为子） */
    int cmdMerge;

    /**
     * 响应码。
     * <pre>
     *     从字段精简的角度，我们不可能每次响应都带上完整的异常栈给客户端排查问题，
     *     因此，我们会定义一些响应码，通过编号进行网络传输，方便客户端定位问题。
     *
     *     0:成功
     *     >0: 有错误
     * </pre>
     */
    short responseStatus;
    /** 业务请求体长度 */
    int dataLength = 0;
    /** 业务请求体 字节数组 */
    byte[] data;

    /**
     * 业务数据
     *
     * @param data 业务数据
     */
    public void setData(byte[] data) {
        if (data != null) {
            this.data = data;
            this.dataLength = data.length;
        }
    }

    /**
     * 业务数据
     *
     * @param data 业务数据
     */
    public void setData(Object data) {
        byte[] bytes = ProtoKit.toBytes(data);
        setData(bytes);
    }

    /**
     * 业务路由
     *
     * @param cmd    主路由
     * @param subCmd 子路由
     */
    public void setCmdMerge(int cmd, int subCmd) {
        this.cmdMerge = CmdKit.merge(cmd, subCmd);
    }
}
