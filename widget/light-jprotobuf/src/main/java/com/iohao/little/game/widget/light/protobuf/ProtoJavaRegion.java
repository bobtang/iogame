package com.iohao.little.game.widget.light.protobuf;

import cn.hutool.core.util.StrUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.*;

/**
 * @author 洛朱
 * @date 2022-01-25
 */
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Accessors(chain = true)
public class ProtoJavaRegion {
    String fileName;
    String filePackage;

    final Map<Class<?>, ProtoJava> protoJavaMap = new HashMap<>();
    final List<ProtoJava> protoJavaList = new ArrayList<>();
    final ProtoJavaRegionHead regionHead = new ProtoJavaRegionHead();

    public void addProtoJava(ProtoJava protoJava) {
        this.protoJavaList.add(protoJava);
        this.protoJavaMap.put(protoJava.getClazz(), protoJava);
    }

    public void addOtherProtoFile(ProtoJava protoJava) {
        String fileName = protoJava.getFileName();
        this.regionHead.fileNameSet.add(fileName);
    }

    static class ProtoJavaRegionHead {
        Set<String> fileNameSet = new HashSet<>();
        String filePackage;

        private String toProtoHead() {

            String templateFileName = """
                    import "{}";
                    """;

            StringBuilder fileNameBuilder = new StringBuilder();

            for (String filePackage : fileNameSet) {
                String filePackageString = StrUtil.format(templateFileName, filePackage);
                fileNameBuilder.append(filePackageString);
            }


            String templateHead = """
                    syntax = "proto3";
                    package {};
                    {}
                    """;

            return StrUtil.format(templateHead, this.filePackage, fileNameBuilder.toString());
        }
    }

    public String toProtoFile() {
        this.regionHead.filePackage = this.filePackage;
        String protoHead = this.regionHead.toProtoHead();

        StringBuilder builder = new StringBuilder();
        builder.append(protoHead);

        for (ProtoJava protoJava : protoJavaList) {
            String protoMessage = protoJava.toProtoMessage();
            builder.append(protoMessage);
        }

        return builder.toString();
    }
}
