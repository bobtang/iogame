package com.iohao.game.collect.proto;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.system.SystemUtil;
import com.iohao.little.game.widget.light.protobuf.ProtoGenerateFile;

import java.io.File;

/**
 * @author 洛朱
 * @date 2022-01-25
 */
public class GenerateFileForProto {
    public static void main(String[] args) {

        // 需要扫描的包名
        String protoPackagePath = GenerateFileForProto.class.getPackageName();

        String[] protoSourcePathArray = new String[]{
                SystemUtil.getUserInfo().getCurrentDir()
                , "game-collect"
                , "game-common-proto"
                , "src"
                , "main"
                , "java"
                , protoPackagePath.replaceAll("\\.", File.separator)
        };

        // 源码目录
        String protoSourcePath = ArrayUtil.join(protoSourcePathArray, File.separator);

        String[] generateFolderArray = new String[]{
                SystemUtil.getUserInfo().getCurrentDir()
                , "game-collect"
                , "game-common-proto"
                , "target"
                , "proto"
        };

        // 生成 .proto 文件存放的目录
        String generateFolder = ArrayUtil.join(generateFolderArray, File.separator);

        ProtoGenerateFile protoGenerateFile = ProtoGenerateFile.builder()
                // 源码目录
                .protoSourcePath(protoSourcePath)
                // 需要扫描的包名
                .protoPackagePath(protoPackagePath)
                // 生成 .proto 文件存放的目录
                .generateFolder(generateFolder)
                .build();

        // 生成 .proto 文件
        protoGenerateFile.generate();
    }
}
