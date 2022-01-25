package com.iohao.little.game.widget.light.protobuf;

import java.lang.annotation.*;

/**
 * pb 文件合并归类
 *
 * @author 洛朱
 * @date 2022-01-24
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ProtoFileMerge {
    /** 原生 .proto 的文件名 */
    String fileName();

    /** 原生 .proto 的包名 package */
    String filePackage();
}
