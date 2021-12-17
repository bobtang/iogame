package com.iohao.little.game.widget.mq;

import lombok.Data;

import java.io.Serializable;

/**
 * 数据适配
 *
 * @author 洛朱
 * @date 2021/12/17
 */
@Data
public class MessageExtWidget implements Serializable {
    Object data;
}
