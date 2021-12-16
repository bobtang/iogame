package com.iohao.little.game.widget.mq;

/**
 * @author 洛朱
 * @date 2021/12/16
 */
public interface WidgetMessageListener {
    void onMessage(String channel, Object message);
}
