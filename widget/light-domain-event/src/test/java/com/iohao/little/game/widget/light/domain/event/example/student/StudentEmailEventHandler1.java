package com.iohao.little.game.widget.light.domain.event.example.student;

import com.iohao.little.game.widget.light.domain.event.annotation.DomainEvent;
import com.iohao.little.game.widget.light.domain.event.message.DomainEventHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 给学生发送email事件
 *
 * @author 洛朱
 * @date 2021-12-26
 */
@Slf4j
@DomainEvent
public final class StudentEmailEventHandler1 implements DomainEventHandler<StudentEo> {
    @Override
    public void onEvent(StudentEo studentEo, boolean endOfBatch) {
        log.debug("给这个学生发送一个email消息: {}", studentEo);
    }
}
