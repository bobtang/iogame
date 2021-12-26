package com.iohao.little.game.widget.light.domain.event.example.student;

import lombok.extern.slf4j.Slf4j;
import com.iohao.little.game.widget.light.domain.event.annotation.DomainEvent;
import com.iohao.little.game.widget.light.domain.event.message.DomainEventHandler;

/**
 * 学生睡觉
 *
 * @author 洛朱
 * @date 2021-12-26
 */
@Slf4j
@DomainEvent
public final class StudentSleepEventHandler3 implements DomainEventHandler<StudentEo> {
    @Override
    public void onEvent(StudentEo studentEo, boolean endOfBatch) {
        log.debug("学生睡觉: {}", studentEo);
    }
}
