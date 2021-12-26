package com.iohao.little.game.widget.light.domain.event.example.student;

import com.iohao.little.game.widget.light.domain.event.annotation.DomainEvent;
import com.iohao.little.game.widget.light.domain.event.message.DomainEventHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 学生回家事件
 *
 * @author 洛朱
 * @date 2021-12-26
 */
@Slf4j
@DomainEvent
public final class StudentGoHomeEventHandler2 implements DomainEventHandler<StudentEo> {
    @Override
    public void onEvent(StudentEo studentEo, boolean endOfBatch) {
        log.debug("学生回家: {} , {}", studentEo, endOfBatch);
    }
}

