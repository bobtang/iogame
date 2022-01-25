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

        String protoSourcePath = ArrayUtil.join(protoSourcePathArray, File.separator);

        String[] generateFolderArray = new String[]{
                SystemUtil.getUserInfo().getCurrentDir()
                , "game-collect"
                , "game-common-proto"
                , "target"
                , "proto"
        };

        String generateFolder = ArrayUtil.join(generateFolderArray, File.separator);

        ProtoGenerateFile protoGenerateFile = ProtoGenerateFile.builder()
                .protoSourcePath(protoSourcePath)
                .protoPackagePath(protoPackagePath)
                .generateFolder(generateFolder)
                .build();

        protoGenerateFile.generate();
    }
}
