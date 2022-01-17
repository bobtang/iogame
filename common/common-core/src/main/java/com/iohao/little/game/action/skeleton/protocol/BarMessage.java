package com.iohao.little.game.action.skeleton.protocol;

import com.iohao.little.game.action.skeleton.core.BarErrorCode;
import com.iohao.little.game.action.skeleton.core.CmdInfo;
import com.iohao.little.game.action.skeleton.core.CmdInfoFlyweightFactory;
import com.iohao.little.game.action.skeleton.core.CmdKit;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;

/**
 * 消息基类
 *
 * @author 洛朱
 * @Date 2021-12-20
 */
@ToString
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class BarMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 562068269463876111L;
    /** 目标路由 */
    int cmd;
    /** 目标子路由 */
    int subCmd;
    /**
     * 合并两个参数,分别存放在 [高16 和 低16]
     * <pre>
     *     例如 cmd = 600; subCmd = 700;
     *     merge 的结果: 39322300
     *     那么merge对应的二进制是: 0000 0010 0101 1000 0000 0010 1011 1100
     * </pre>
     */
    int cmdMerge;

    /** 响应错误码 */
    int errorCode;
    /** JSR303、JSR 349 验证信息 */
    String validatorMsg;

    /** userId */
    long userId;

    /** 实际请求的业务参数 */
    Object data;
    /** 实际请求的业务参数 byte[] */
    byte[] dataContent;

    public void setData(Object data) {
        this.data = data;
    }

    public void setCmdInfo(CmdInfo cmdInfo) {
        this.cmd = cmdInfo.getCmd();
        this.subCmd = cmdInfo.getSubCmd();
        this.cmdMerge = cmdInfo.getCmdMerge();
    }

    public CmdInfo getCmdInfo() {
        return CmdInfoFlyweightFactory.me().getCmdInfo(this.cmd, this.subCmd);
    }

    public void setCmdMerge(int cmdMerge) {
        this.cmdMerge = cmdMerge;
        this.cmd = CmdKit.getCmd(cmdMerge);
        this.subCmd = CmdKit.getSubCmd(cmdMerge);
    }

    /**
     * 设置验证的错误信息
     *
     * @param validatorMsg 错误信息
     */
    public void setValidatorMsg(String validatorMsg) {
        if (validatorMsg != null) {
            this.validatorMsg = validatorMsg;
            this.errorCode = BarErrorCode.validateErrCode;
        }
    }

    /**
     * 是否有错误
     * <pre>
     *     this.errorCode != 0 表示有错误
     * </pre>
     *
     * @return true 有错误码
     */
    public boolean hasError() {
        return this.errorCode != 0;
    }
}
