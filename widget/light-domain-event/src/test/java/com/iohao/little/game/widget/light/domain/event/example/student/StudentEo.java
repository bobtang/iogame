package com.iohao.little.game.widget.light.domain.event.example.student;

import com.iohao.little.game.widget.light.domain.event.message.Eo;

/**
 * 领域消息 - 学生
 * <pre>
 *     推荐定义领域事件实体类的时候都使用final
 *     避免某个领域事件对该实体进行数据修改
 * </pre>
 *
 * @author 洛朱
 * @date 2021-12-26
 */
public record StudentEo(int id) implements Eo {
}
