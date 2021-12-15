package com.iohao.little.game.net.message.common;

import com.iohao.little.game.action.skeleton.protocol.RequestMessage;
import lombok.Data;

import java.io.Serializable;

/**
 * 子模块之间的访问
 * <pre>
 *     如： 子模块A 访问 子模块B 的某个方法，因为只有子模块B持有这些数据
 * </pre>
 */
@Data
public class InnerModuleMessage implements Serializable {
    RequestMessage requestMessage;
}
