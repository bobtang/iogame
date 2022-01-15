package com.iohao.game.collect.common;

import lombok.experimental.UtilityClass;

/**
 * @author 洛朱
 * @date 2022-01-15
 */
@UtilityClass
public class FrameConfig {
    /** 帧率 给前端发送消息的间隔时间(单位:毫秒) */
    public static final int FRAME_RATE = 50;

    /** 1 秒时间 */
    public static final int SECONDS = 1000;

    /** 1 分钟: 60 * 1000 */
    public static final int MINUTE = 60 * SECONDS;
}
