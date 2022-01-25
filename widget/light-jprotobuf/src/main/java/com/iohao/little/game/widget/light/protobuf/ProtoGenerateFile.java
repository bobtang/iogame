package com.iohao.little.game.widget.light.protobuf;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * 生成 pb 文件
 *
 * @author 洛朱
 * @date 2022-01-25
 */
@Slf4j
@Builder
public class ProtoGenerateFile {
    /** pb 源码目录 */
    String protoSourcePath;
    /** pb class 目录 */
    String protoPackagePath;
    /** 生成 proto file 目录 */
    String generateFolder;

    private void checked() {
        Objects.requireNonNull(protoSourcePath);
        Objects.requireNonNull(protoPackagePath);
        Objects.requireNonNull(generateFolder);

        FileUtil.mkdir(this.generateFolder);
    }

    public void generate() {
        checked();

        ProtoJavaAnalyse analyse = new ProtoJavaAnalyse();
        Map<ProtoJavaRegionKey, ProtoJavaRegion> regionMap = analyse.analyse(protoPackagePath, protoSourcePath);

        Consumer<ProtoJavaRegion> javaRegionConsumer = javaRegion -> {
            String fileName = javaRegion.getFileName();

            List<ProtoJava> protoJavaList = javaRegion.getProtoJavaList();

            log.info("fileName: {} - {}", fileName, protoJavaList.size());

            String protoString = javaRegion.toProtoFile();
            log.info("-------------{}---------------------", fileName);
            log.info("{}", protoString);

            String protoFilePath = StrUtil.format("{}{}{}"
                    , this.generateFolder
                    , File.separator
                    , fileName
            );

            FileUtil.writeUtf8String(protoString, protoFilePath);
        };


        regionMap.values().forEach(javaRegionConsumer);

    }
}
