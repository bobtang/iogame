package com.iohao.little.game.action.skeleton.protocol;

import com.iohao.little.game.action.skeleton.core.CmdInfo;
import com.iohao.little.game.action.skeleton.core.CmdInfoFlyweightFactory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
@ToString
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class BarMessage implements Serializable {
    /**
     * 目标路由
     */
    int cmd;
    /**
     * 目标子路由
     */
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
    /**
     * 错误码
     */
    int errorCode;
    long userId;

    String dataClazzName;
    Object data;

    public void setData(Object data) {
        this.data = data;
        this.dataClazzName = data.getClass().getName();
    }

    public void setCmdInfo(CmdInfo cmdInfo) {
        this.cmd = cmdInfo.getCmd();
        this.subCmd = cmdInfo.getSubCmd();
        this.cmdMerge = cmdInfo.getCmdMerge();
    }

    public CmdInfo getCmdInfo() {
        CmdInfoFlyweightFactory factory = CmdInfoFlyweightFactory.me();
        return factory.getCmdInfo(this.cmd, this.subCmd);
    }
}
