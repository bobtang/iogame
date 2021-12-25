package com.iohao.little.game.widget.light.timer.task.example;

import com.iohao.little.game.widget.light.timer.task.AbstractTimerTask;
import com.iohao.little.game.widget.light.timer.task.TimerTaskRegion;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 洛朱
 * @date 2021-12-25
 */
@Slf4j
@Accessors(chain = true)
public class HelloTask extends AbstractTimerTask {

    /** 说话内容 */
    @Setter
    private String sayContent;

    @Override
    public void execute() {
        log.info("对着河道说: {}", sayContent);
    }


    @Override
    protected TimerTaskRegion getTimerTaskStore() {
        return TimerTaskEnum.HELLO;
    }
}
