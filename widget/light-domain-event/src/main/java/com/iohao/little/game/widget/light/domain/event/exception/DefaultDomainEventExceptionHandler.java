package com.iohao.little.game.widget.light.domain.event.exception;

import com.lmax.disruptor.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 默认领域事件 异常处理类
 *
 * @author 洛朱
 * @date 2022-01-14
 */
@Slf4j
public class DefaultDomainEventExceptionHandler implements ExceptionHandler {
    @Override
    public void handleEventException(Throwable ex, long sequence, Object event) {
        log.error("{} - {}", ex, event);
    }

    @Override
    public void handleOnStartException(Throwable ex) {
        log.error("{}", ex.getMessage());
    }

    @Override
    public void handleOnShutdownException(Throwable ex) {
        log.error("{}", ex.getMessage());
    }
}
