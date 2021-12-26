package com.iohao.little.game.widget.light.domain.event.message;

/**
 * 主题
 * <pre>
 *     领域消息主题
 * </pre>
 *
 * @author 洛朱
 * @date 2021-12-26
 */
public interface Topic {
    /**
     * 获取主题
     *
     * @return 主题
     */
    Class<?> getTopic();
}