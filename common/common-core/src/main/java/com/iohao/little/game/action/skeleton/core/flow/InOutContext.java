package com.iohao.little.game.action.skeleton.core.flow;

import com.iohao.little.game.action.skeleton.core.ActionCommand;
import com.iohao.little.game.action.skeleton.core.ParamContext;
import com.iohao.little.game.action.skeleton.protocol.RequestMessage;
import com.iohao.little.game.action.skeleton.protocol.ResponseMessage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.HashMap;
import java.util.Map;

/**
 * inout 上下文
 *
 * @author 洛朱
 * @date 2021-12-21
 */
@Setter
@Getter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InOutContext {
    private final Map<String, Object> dataMap = new HashMap<>();
    /** 参数上下文 */
    ParamContext paramContext;
    /** command */
    ActionCommand actionCommand;
    /** 控制器类对象 */
    Object actionController;
    /** 请求对象 */
    RequestMessage requestMessage;
    /** 响应对象, 在InOut.fuckOut 时才会有值 */
    ResponseMessage responseMessage;
    /** 业务方法参数 */
    Object[] methodParams;

    public void put(String key, Object value) {
        dataMap.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) dataMap.get(key);
    }

    public long getLong(String key) {
        return get(key);
    }
}
