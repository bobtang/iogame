package com.iohao.little.game.widget.light.protobuf;

import cn.hutool.system.SystemUtil;
import com.iohao.little.game.widget.light.protobuf.pojo.TempProtoFile;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author 洛朱
 * @date 2022-01-24
 */
@Slf4j
public class ProtoJavaTest {

    @Test
    public void name() {

        if (!SystemUtil.getOsInfo().isMac()) {
            return;
        }

        // 源码目录
        String protoSourcePath = "/Users/join/gitme/little-game-sun/widget/light-jprotobuf/src/test/java/com/iohao/little/game/widget/light/protobuf/pojo";
        // 需要扫描的包名
        String protoPackagePath = TempProtoFile.class.getPackageName();
        // 生成 .proto 文件存放的目录
        String generateFolder = "/Users/join/gitme/little-game-sun/widget/light-jprotobuf/target/proto";

        ProtoGenerateFile protoGenerateFile = ProtoGenerateFile.builder()
                .protoSourcePath(protoSourcePath)
                .protoPackagePath(protoPackagePath)
                .generateFolder(generateFolder)
                .build();

        // 生成 .proto 文件
        protoGenerateFile.generate();

        /*
         * 本示例会生成两个 .proto 文件
         * one.proto 和 common.proto
         */
    }
}