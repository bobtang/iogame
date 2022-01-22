package com.iohao.game.collect.common;

import lombok.experimental.UtilityClass;

/**
 * 帧间隔计算工具
 *
 * @author 洛朱
 * @date 2022-01-15
 */
@UtilityClass
public class FrameKit {
    /** 帧率 给前端发送消息的间隔时间(单位:毫秒) */
    public static final int FRAME_RATE = 50;

    /** 1 秒时间 */
    public static final int SECONDS = 1000;

    /** 1 分钟: 60 * 1000 */
    public static final int MINUTE = 60 * SECONDS;


    /**
     * 每秒总帧数
     * <pre>
     *     填写 60 表示每秒运行 60 帧
     * </pre>
     */
    int FRAME_COUNT = 15;

    /**
     * 帧间隔 (帧速 = 每秒/总帧数)
     *
     * @param frameCount 每秒总帧数 , 填写 60 表示每秒运行 60 帧
     * @return 间隔时间
     */
    public int framePerMillis(int frameCount) {
        return 1000 / frameCount;
    }


}
